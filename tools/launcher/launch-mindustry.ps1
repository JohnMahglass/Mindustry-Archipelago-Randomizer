#Requires -Version 5.1
[CmdletBinding()]
param(
    [switch]$Status,
    [switch]$NoUpdate,
    [switch]$NoHost,
    [switch]$SetupOnly,
    [switch]$PublicHost,
    [switch]$ForceUpdate,
    [switch]$NewRoom,
    [switch]$SoftwareGL,
    [string]$Room,
    [string]$ArchipelagoDir
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
if ([Environment]::OSVersion.Platform -ne [System.PlatformID]::Win32NT) {
    throw 'This launcher requires Windows. Use launch-mindustry.sh on Linux.'
}
if ($NewRoom -and ($NoHost -or $Status -or $Room)) {
    throw '-NewRoom cannot be combined with -NoHost, -Status, or -Room.'
}
[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$windowsRoot = Join-Path $root 'windows'
$gameDir = Join-Path $windowsRoot 'Mindustry-Archipelago'
$stateDir = Join-Path $windowsRoot 'launcher-state'
$downloadDir = Join-Path $stateDir 'downloads'
$backupDir = Join-Path $stateDir 'backups'
$statePath = Join-Path $stateDir 'state.json'
if (-not $ArchipelagoDir) { $ArchipelagoDir = Join-Path $env:ProgramData 'Archipelago' }
$ArchipelagoDir = [IO.Path]::GetFullPath($ArchipelagoDir)
$serverExe = Join-Path $ArchipelagoDir 'ArchipelagoServer.exe'
$generatorExe = Join-Path $ArchipelagoDir 'ArchipelagoGenerate.exe'
$worldPath = Join-Path $ArchipelagoDir 'lib\worlds\mindustry.apworld'
$clientRepo = 'JohnMahglass/Mindustry-Archipelago-Randomizer'
$worldRepo = 'JohnMahglass/Archipelago-Mindustry'
$mesaRepo = 'pal1000/mesa-dist-win'

function Say([string]$Message) { Write-Host $Message }
function Warn([string]$Message) { Write-Warning $Message }

function Read-State {
    $result = @{ ClientTag = ''; ClientExe = ''; ClientHash = ''; ApworldTag = ''; ApworldHash = ''; SoftwareGlTag = ''; SoftwareGlRootHash = ''; SoftwareGlJreHash = '' }
    if (-not (Test-Path -LiteralPath $statePath -PathType Leaf)) { return $result }
    try {
        $saved = Get-Content -LiteralPath $statePath -Raw | ConvertFrom-Json
        foreach ($key in @('ClientTag', 'ClientExe', 'ClientHash', 'ApworldTag', 'ApworldHash', 'SoftwareGlTag', 'SoftwareGlRootHash', 'SoftwareGlJreHash')) {
            if ($saved.PSObject.Properties.Name -contains $key) { $result[$key] = [string]$saved.$key }
        }
    } catch { Warn 'The launcher state is unreadable; installed files will be checked directly.' }
    return $result
}

function Save-State {
    $temporary = Join-Path $stateDir ('state-' + [guid]::NewGuid().ToString('N') + '.json')
    $script:launcherState | ConvertTo-Json -Compress | Set-Content -LiteralPath $temporary -Encoding UTF8
    Move-Item -LiteralPath $temporary -Destination $statePath -Force
}

function Get-ClientInstall {
    $path = $null
    if ($script:launcherState.ClientExe) {
        $tracked = Join-Path $gameDir $script:launcherState.ClientExe
        if (Test-Path -LiteralPath $tracked -PathType Leaf) { $path = $tracked }
    }
    if (-not $path -and (Test-Path -LiteralPath $gameDir -PathType Container)) {
        $found = @(Get-ChildItem -LiteralPath $gameDir -File -Filter 'Mindustry-Archipelago-*.exe' |
            Sort-Object LastWriteTime -Descending)
        if ($found.Count) { $path = $found[0].FullName }
    }
    if (-not $path) { return [pscustomobject]@{ Path = $null; Tag = 'missing'; Changed = $false } }
    if ($script:launcherState.ClientExe -and
        [IO.Path]::GetFileName($path) -ieq $script:launcherState.ClientExe) {
        $hash = (Get-FileHash -LiteralPath $path -Algorithm SHA256).Hash
        if ($script:launcherState.ClientHash -and $hash -ine $script:launcherState.ClientHash) {
            return [pscustomobject]@{ Path = $path; Tag = 'unknown'; Changed = $true }
        }
        if ($script:launcherState.ClientTag) {
            return [pscustomobject]@{ Path = $path; Tag = $script:launcherState.ClientTag; Changed = $false }
        }
    }
    $tag = 'unknown'
    if ([IO.Path]::GetFileName($path) -match 'Mindustry-Archipelago-(v\d+\.\d+\.\d+)\.exe$') {
        $tag = $Matches[1]
    }
    return [pscustomobject]@{ Path = $path; Tag = $tag; Changed = $false }
}

function Get-Processes {
    try { return @(Get-CimInstance -ClassName Win32_Process -ErrorAction Stop) }
    catch {
        $items = @()
        foreach ($process in @(Get-Process)) {
            try {
                if ($process.Path) {
                    $items += [pscustomobject]@{
                        ProcessId = $process.Id
                        Name = $process.ProcessName + '.exe'
                        ExecutablePath = $process.Path
                        CommandLine = ''
                    }
                }
            } catch { }
        }
        return $items
    }
}

function Find-ProcessIn([string]$Directory, [string]$NamePattern) {
    $prefix = [IO.Path]::GetFullPath($Directory).TrimEnd('\') + '\'
    foreach ($process in @(Get-Processes)) {
        if ($process.ExecutablePath -and $process.Name -match $NamePattern -and
            $process.ExecutablePath.StartsWith($prefix, [StringComparison]::OrdinalIgnoreCase)) {
            return $process
        }
    }
    return $null
}

function Get-ServerRoom($Server) {
    if (-not $Server -or -not $Server.CommandLine) { return $null }
    $commandLine = [string]$Server.CommandLine
    $candidate = $null
    if ($commandLine -match '(?i)"([^"]+\.(?:zip|archipelago))"') { $candidate = $Matches[1] }
    elseif ($commandLine -match '(?i)(?:^|\s)([^\s"]+\.(?:zip|archipelago))(?=\s|$)') { $candidate = $Matches[1] }
    if ($candidate) {
        if (-not [IO.Path]::IsPathRooted($candidate)) { $candidate = Join-Path $ArchipelagoDir $candidate }
        return [IO.Path]::GetFullPath($candidate)
    }
    return $null
}

function Get-Release([string]$Repository, [string]$Tag) {
    $url = if ($Tag) {
        'https://api.github.com/repos/' + $Repository + '/releases/tags/' + $Tag
    } else {
        'https://api.github.com/repos/' + $Repository + '/releases?per_page=30'
    }
    $release = Invoke-RestMethod -Uri $url -Headers @{
        'Accept' = 'application/vnd.github+json'
        'User-Agent' = 'mindustry-archipelago-windows-launcher'
    } -TimeoutSec 30
    return $release
}

function Get-Asset($Release, [string]$Pattern) {
    foreach ($asset in @($Release.assets)) {
        if ($asset.name -match $Pattern) { return $asset }
    }
    return $null
}

function Get-LatestClientRelease {
    foreach ($release in @(Get-Release $clientRepo '')) {
        if (-not $release.draft -and (Get-Asset $release '^(Windows|Win)_Mindustry_.*\.zip$')) {
            return $release
        }
    }
    return $null
}

function Get-CompatibleWorldTag($ClientRelease) {
    if ($ClientRelease -and $ClientRelease.body -match '(?i)compatible with version\s+v?(\d+\.\d+\.\d+)\s+of\s+(?:the\s+)?(?:Mindustry\s+)?AP\s*world') {
        return 'v' + $Matches[1]
    }
    return $null
}

function Test-NewerTag([string]$Available, [string]$Installed) {
    try { return ([version]($Available.TrimStart('v')) -gt [version]($Installed.TrimStart('v'))) }
    catch { return $false }
}

function Get-AssetHash($Asset) {
    if (-not $Asset -or $Asset.digest -notmatch '^sha256:([0-9a-fA-F]{64})$') {
        throw 'The release asset has no SHA-256 digest.'
    }
    return $Matches[1].ToUpperInvariant()
}

function Download-Asset($Asset, [string]$Repository, [string]$Prefix) {
    $expected = Get-AssetHash $Asset
    $allowed = 'https://github.com/' + $Repository + '/releases/download/'
    if (-not $Asset.browser_download_url.StartsWith($allowed, [StringComparison]::OrdinalIgnoreCase)) {
        throw ('Unexpected download URL: ' + $Asset.browser_download_url)
    }
    $filename = [IO.Path]::GetFileName([string]$Asset.name)
    $destination = Join-Path $downloadDir ($Prefix + '-' + $filename)
    if ((Test-Path -LiteralPath $destination -PathType Leaf) -and
        (Get-FileHash -LiteralPath $destination -Algorithm SHA256).Hash -ieq $expected) {
        return $destination
    }
    $partial = $destination + '.partial-' + [guid]::NewGuid().ToString('N')
    try {
        Say ('Downloading ' + $filename + '...')
        Invoke-WebRequest -Uri $Asset.browser_download_url -OutFile $partial -UseBasicParsing -Headers @{ 'User-Agent' = 'mindustry-archipelago-windows-launcher' } -TimeoutSec 600 | Out-Null
        if ((Get-FileHash -LiteralPath $partial -Algorithm SHA256).Hash -ine $expected) {
            throw ('SHA-256 mismatch for ' + $filename)
        }
        Move-Item -LiteralPath $partial -Destination $destination -Force
    } finally {
        if (Test-Path -LiteralPath $partial) { Remove-Item -LiteralPath $partial -Force }
    }
    return $destination
}

function Get-ExecutableArchitecture([string]$Path) {
    $stream = [IO.File]::OpenRead($Path)
    $reader = [IO.BinaryReader]::new($stream)
    try {
        if ($reader.ReadUInt16() -ne 0x5A4D) { throw ('Not a Windows executable: ' + $Path) }
        $stream.Position = 0x3C
        $peOffset = $reader.ReadInt32()
        if ($peOffset -lt 0x40 -or $peOffset -gt ($stream.Length - 6)) {
            throw ('Invalid Windows executable: ' + $Path)
        }
        $stream.Position = $peOffset
        if ($reader.ReadUInt32() -ne 0x00004550) { throw ('Invalid Windows executable: ' + $Path) }
        switch ($reader.ReadUInt16()) {
            0x014c { return 'x86' }
            0x8664 { return 'x64' }
            default { throw ('Unsupported Windows executable architecture: ' + $Path) }
        }
    } finally { $reader.Dispose() }
}

function Get-SoftwareGlTargets([string]$ClientPath) {
    $javaBin = Join-Path $gameDir 'jre\bin'
    $javaw = Join-Path $javaBin 'javaw.exe'
    $java = Join-Path $javaBin 'java.exe'
    $javaExecutable = if (Test-Path -LiteralPath $javaw -PathType Leaf) { $javaw } else { $java }
    if (-not (Test-Path -LiteralPath $javaExecutable -PathType Leaf)) {
        throw 'The bundled Java runtime is missing; the software OpenGL renderer cannot be installed.'
    }
    $javaMarkers = @()
    foreach ($path in @($javaw, $java)) {
        if (Test-Path -LiteralPath $path -PathType Leaf) { $javaMarkers += ($path + '.local') }
    }
    return @(
        [pscustomobject]@{
            Name = 'Root'; Directory = $gameDir
            Architecture = Get-ExecutableArchitecture $ClientPath
            Markers = @($ClientPath + '.local')
        },
        [pscustomobject]@{
            Name = 'Jre'; Directory = $javaBin
            Architecture = Get-ExecutableArchitecture $javaExecutable
            Markers = $javaMarkers
        }
    )
}

function Get-SoftwareGlSignature([string]$Directory) {
    $hashes = @()
    foreach ($name in @('opengl32.dll', 'libgallium_wgl.dll', 'dxil.dll')) {
        $path = Join-Path $Directory $name
        if (-not (Test-Path -LiteralPath $path -PathType Leaf)) { return '' }
        $hashes += (Get-FileHash -LiteralPath $path -Algorithm SHA256).Hash
    }
    return ($hashes -join ':')
}

function Test-SoftwareGlReady($Targets) {
    if (-not $script:launcherState.SoftwareGlTag) { return $false }
    foreach ($target in $Targets) {
        $expected = $script:launcherState['SoftwareGl' + $target.Name + 'Hash']
        $actual = Get-SoftwareGlSignature $target.Directory
        if (-not $expected -or -not $actual -or $actual -ne $expected) { return $false }
        foreach ($marker in $target.Markers) {
            if (-not (Test-Path -LiteralPath $marker -PathType Leaf)) { return $false }
        }
    }
    return $true
}

function Get-LatestSoftwareGlRelease {
    foreach ($release in @(Get-Release $mesaRepo '')) {
        if (-not $release.draft -and -not $release.prerelease -and
            (Get-Asset $release '^mesa3d-.*-release-msvc\.7z$')) {
            return $release
        }
    }
    return $null
}

function Install-SoftwareGl($Release, $Targets) {
    $asset = Get-Asset $Release '^mesa3d-.*-release-msvc\.7z$'
    if (-not $asset) { throw ('No Mesa MSVC release archive was found in ' + $Release.tag_name) }
    $archive = Download-Asset $asset $mesaRepo ([string]$Release.tag_name)
    $tar = Get-Command tar.exe -ErrorAction SilentlyContinue
    if (-not $tar) { throw 'Windows tar.exe is required to unpack the Mesa .7z archive.' }
    $stage = Join-Path $stateDir ('mesa-stage-' + [guid]::NewGuid().ToString('N'))
    $backup = Join-Path $backupDir ('software-gl-' + (Get-Date -Format 'yyyyMMdd-HHmmss') + '-' + [guid]::NewGuid().ToString('N'))
    $names = @('opengl32.dll', 'libgallium_wgl.dll', 'dxil.dll')
    $entries = @()
    foreach ($architecture in @($Targets | ForEach-Object { $_.Architecture } | Select-Object -Unique)) {
        foreach ($name in $names) { $entries += ($architecture + '/' + $name) }
    }
    $replaced = @()
    $createdMarkers = @()
    $oldState = $script:launcherState.Clone()
    try {
        New-Item -ItemType Directory -Path $stage -Force | Out-Null
        & $tar.Source -xf $archive -C $stage @entries
        if ($LASTEXITCODE -ne 0) { throw 'Could not extract the Mesa release archive with tar.exe.' }
        foreach ($entry in $entries) {
            $source = Join-Path $stage $entry.Replace('/', '\')
            if (-not (Test-Path -LiteralPath $source -PathType Leaf)) {
                throw ('Mesa archive is missing ' + $entry)
            }
        }
        foreach ($target in $Targets) {
            foreach ($name in $names) {
                $source = Join-Path (Join-Path $stage $target.Architecture) $name
                $destination = Join-Path $target.Directory $name
                $oldCopy = Join-Path (Join-Path $backup $target.Name) $name
                if (Test-Path -LiteralPath $destination -PathType Leaf) {
                    New-Item -ItemType Directory -Path (Split-Path -Parent $oldCopy) -Force | Out-Null
                    Copy-Item -LiteralPath $destination -Destination $oldCopy -Force
                }
                $replaced += [pscustomobject]@{ Path = $destination; Backup = $oldCopy }
                Copy-Item -LiteralPath $source -Destination $destination -Force
            }
            foreach ($marker in $target.Markers) {
                if (-not (Test-Path -LiteralPath $marker)) {
                    New-Item -ItemType File -Path $marker | Out-Null
                    $createdMarkers += $marker
                }
            }
        }
        $script:launcherState.SoftwareGlTag = [string]$Release.tag_name
        $script:launcherState.SoftwareGlRootHash = Get-SoftwareGlSignature $Targets[0].Directory
        $script:launcherState.SoftwareGlJreHash = Get-SoftwareGlSignature $Targets[1].Directory
        Save-State
        Say ('Installed Mesa software OpenGL ' + $Release.tag_name + ' for Mindustry only.')
        if (Test-Path -LiteralPath $backup -PathType Container) {
            Say ('Previous OpenGL DLLs backed up to ' + $backup)
        }
    } catch {
        $installError = $_
        foreach ($item in $replaced) {
            try {
                if (Test-Path -LiteralPath $item.Backup -PathType Leaf) {
                    Copy-Item -LiteralPath $item.Backup -Destination $item.Path -Force
                } elseif (Test-Path -LiteralPath $item.Path -PathType Leaf) {
                    Remove-Item -LiteralPath $item.Path -Force
                }
            } catch { Warn ('Could not restore ' + $item.Path + ': ' + $_.Exception.Message) }
        }
        foreach ($marker in $createdMarkers) {
            if (Test-Path -LiteralPath $marker) { Remove-Item -LiteralPath $marker -Force }
        }
        $script:launcherState = $oldState
        throw $installError
    } finally {
        if (Test-Path -LiteralPath $stage) { Remove-Item -LiteralPath $stage -Recurse -Force }
    }
}

function Check-ZipPaths([string]$ZipPath) {
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $archive = [IO.Compression.ZipFile]::OpenRead($ZipPath)
    try {
        foreach ($entry in $archive.Entries) {
            $name = $entry.FullName.Replace('/', '\')
            if ($name.StartsWith('\') -or $name -match '^[A-Za-z]:' -or
                $name -match '(^|\\)\.\.(\\|$)') {
                throw ('Unsafe path in client ZIP: ' + $entry.FullName)
            }
        }
    } finally { $archive.Dispose() }
}

function Copy-TreeFiles([string]$Source, [string]$Destination) {
    foreach ($file in @(Get-ChildItem -LiteralPath $Source -Recurse -File)) {
        $relative = $file.FullName.Substring($Source.Length).TrimStart('\', '/')
        $target = Join-Path $Destination $relative
        $parent = Split-Path -Parent $target
        New-Item -ItemType Directory -Path $parent -Force | Out-Null
        Copy-Item -LiteralPath $file.FullName -Destination $target -Force
    }
}

function Install-Client($Asset, [string]$Tag) {
    $zipPath = Download-Asset $Asset $clientRepo $Tag
    Check-ZipPaths $zipPath
    $stage = Join-Path $stateDir ('stage-' + [guid]::NewGuid().ToString('N'))
    $backup = $null
    try {
        New-Item -ItemType Directory -Path $stage -Force | Out-Null
        Expand-Archive -LiteralPath $zipPath -DestinationPath $stage
        $executables = @(Get-ChildItem -LiteralPath $stage -File -Filter 'Mindustry-Archipelago-*.exe')
        if ($executables.Count -ne 1 -or
            -not (Test-Path -LiteralPath (Join-Path $stage 'jre\bin\java.exe') -PathType Leaf)) {
            throw 'The Windows client archive does not contain one game EXE and a bundled JRE.'
        }
        if (Test-Path -LiteralPath $gameDir -PathType Container) {
            $oldFiles = @(Get-ChildItem -LiteralPath $gameDir -File -Filter 'Mindustry-Archipelago-*.exe')
            if ($oldFiles.Count) {
                $backup = Join-Path $backupDir ('client-' + (Get-Date -Format 'yyyyMMdd-HHmmss') + '-' + [guid]::NewGuid().ToString('N'))
                New-Item -ItemType Directory -Path $backup -Force | Out-Null
                foreach ($old in $oldFiles) { Copy-Item -LiteralPath $old.FullName -Destination $backup -Force }
                $oldJre = Join-Path $gameDir 'jre'
                if (Test-Path -LiteralPath $oldJre) { Copy-Item -LiteralPath $oldJre -Destination $backup -Recurse -Force }
                Say ('Previous client backed up to ' + $backup)
            }
        }
        New-Item -ItemType Directory -Path $gameDir -Force | Out-Null
        try {
            $defaultOptions = Join-Path $gameDir 'MindustryDefaultOptions.yaml'
            $keepOptions = (Test-Path -LiteralPath $defaultOptions -PathType Leaf)
            if ($keepOptions) {
                Copy-Item -LiteralPath $defaultOptions -Destination (Join-Path $stage 'MindustryDefaultOptions.yaml') -Force
            }
            Copy-TreeFiles $stage $gameDir
        }
        catch {
            if ($backup) { Copy-TreeFiles $backup $gameDir }
            throw
        }
        $installedExe = Join-Path $gameDir $executables[0].Name
        $script:launcherState.ClientTag = $Tag
        $script:launcherState.ClientExe = $executables[0].Name
        $script:launcherState.ClientHash = (Get-FileHash -LiteralPath $installedExe -Algorithm SHA256).Hash
        Save-State
        Say ('Installed Mindustry client ' + $Tag + '; saves and settings were kept.')
    } finally {
        if (Test-Path -LiteralPath $stage) { Remove-Item -LiteralPath $stage -Recurse -Force }
    }
}

function Install-Archipelago($Release) {
    $asset = Get-Asset $Release '^Setup[._ ]Archipelago[._ ].*\.exe$'
    if (-not $asset) { throw ('No Windows Archipelago installer was found in ' + $Release.tag_name) }
    $installer = Download-Asset $asset $worldRepo $Release.tag_name
    $worldAsset = Get-Asset $Release '^mindustry\.apworld$'
    $worldHash = Get-AssetHash $worldAsset
    $downloadedWorld = Download-Asset $worldAsset $worldRepo $Release.tag_name
    $backup = Join-Path $backupDir ('archipelago-' + (Get-Date -Format 'yyyyMMdd-HHmmss') + '-' + [guid]::NewGuid().ToString('N'))
    New-Item -ItemType Directory -Path $backup -Force | Out-Null
    foreach ($file in @((Join-Path $ArchipelagoDir 'host.yaml'), $worldPath)) {
        if (Test-Path -LiteralPath $file -PathType Leaf) { Copy-Item -LiteralPath $file -Destination $backup -Force }
    }
    # The installer's deletelib task would remove installed worlds, so deselect it.
    $arguments = '/SP- /VERYSILENT /SUPPRESSMSGBOXES /NORESTART /NOCLOSEAPPLICATIONS /MERGETASKS=!deletelib /DIR="' + $ArchipelagoDir + '"'
    Say ('Installing the compatible Archipelago bundle ' + $Release.tag_name + ' (Windows may request elevation)...')
    $startInfo = [Diagnostics.ProcessStartInfo]::new()
    $startInfo.FileName = $installer
    $startInfo.Arguments = $arguments
    $startInfo.UseShellExecute = $true
    $startInfo.Verb = 'runas'
    try {
        $process = [Diagnostics.Process]::Start($startInfo)
        if (-not $process) { throw 'Archipelago installer did not start.' }
    } catch {
        Remove-Item -LiteralPath $backup -Recurse -Force -ErrorAction SilentlyContinue
        $cause = $_.Exception
        while ($cause) {
            if ($cause -is [ComponentModel.Win32Exception] -and $cause.NativeErrorCode -eq 1223) {
                throw [OperationCanceledException]::new('Windows elevation was declined; the Archipelago installation did not start.')
            }
            $cause = $cause.InnerException
        }
        throw
    }
    try {
        $process.WaitForExit()
        $installerExitCode = $process.ExitCode
    } finally {
        $process.Dispose()
    }
    if ($installerExitCode -ne 0) { throw ('Archipelago installer exited with code ' + $installerExitCode) }
    if (-not (Test-Path -LiteralPath $serverExe -PathType Leaf) -or
        -not (Test-Path -LiteralPath $generatorExe -PathType Leaf)) {
        throw ('Archipelago Server or Generator is missing from ' + $ArchipelagoDir)
    }
    $worldDir = Split-Path -Parent $worldPath
    New-Item -ItemType Directory -Path $worldDir -Force | Out-Null
    Copy-Item -LiteralPath $downloadedWorld -Destination $worldPath -Force
    if ((Get-FileHash -LiteralPath $worldPath -Algorithm SHA256).Hash -ine $worldHash) {
        throw 'The installed Mindustry APWorld does not match the release SHA-256 digest.'
    }
    $script:launcherState.ApworldTag = [string]$Release.tag_name
    $script:launcherState.ApworldHash = $worldHash
    Save-State
    Say ('Archipelago and the Mindustry APWorld are ready in ' + $ArchipelagoDir)
}

function Get-Rooms {
    $output = Join-Path $ArchipelagoDir 'output'
    if (-not (Test-Path -LiteralPath $output -PathType Container)) { return @() }
    return @(Get-ChildItem -LiteralPath $output -File | Where-Object { $_.Name -match '^AP_.*\.(zip|archipelago)$' })
}

function Stop-LocalServer($Server) {
    if (-not $Server) { return }
    $serverProcessId = [int]$Server.ProcessId
    $currentServer = Find-ProcessIn $ArchipelagoDir '^ArchipelagoServer\.exe$'
    if (-not $currentServer -or [int]$currentServer.ProcessId -ne $serverProcessId) {
        throw 'The local server process changed; rerun -NewRoom before archiving the old room.'
    }
    Say ('Stopping the local Archipelago server (PID ' + $serverProcessId + ')...')
    $process = Get-Process -Id $serverProcessId -ErrorAction SilentlyContinue
    if ($process) {
        $closedGracefully = $false
        try {
            if ($process.CloseMainWindow()) { $closedGracefully = $process.WaitForExit(5000) }
        } catch { }
        if (-not $closedGracefully -and (Get-Process -Id $serverProcessId -ErrorAction SilentlyContinue)) {
            try { Stop-Process -Id $serverProcessId -Force -ErrorAction Stop }
            catch {
                if (Get-Process -Id $serverProcessId -ErrorAction SilentlyContinue) { throw }
            }
        }
    }
    for ($attempt = 0; $attempt -lt 20; $attempt++) {
        if (-not (Find-ProcessIn $ArchipelagoDir '^ArchipelagoServer\.exe$')) {
            Say 'Local Archipelago server stopped.'
            return
        }
        Start-Sleep -Milliseconds 250
    }
    throw 'The local Archipelago server is still running; the old room was not archived.'
}

function Archive-OldRooms {
    $output = Join-Path $ArchipelagoDir 'output'
    if (-not (Test-Path -LiteralPath $output -PathType Container)) { return }
    $files = @(Get-ChildItem -LiteralPath $output -File |
        Where-Object { $_.Name -match '^AP_.*\.(zip|archipelago|apsave)$' })
    if ($files.Count -eq 0) { return }
    $archive = Join-Path (Join-Path $backupDir 'rooms') ((Get-Date -Format 'yyyyMMdd-HHmmss') + '-' + [guid]::NewGuid().ToString('N'))
    New-Item -ItemType Directory -Path $archive -Force | Out-Null
    $moved = @()
    try {
        foreach ($file in $files) {
            Move-Item -LiteralPath $file.FullName -Destination $archive -ErrorAction Stop
            $moved += $file.Name
        }
    } catch {
        $moveError = $_
        foreach ($name in $moved) {
            try { Move-Item -LiteralPath (Join-Path $archive $name) -Destination $output -ErrorAction Stop }
            catch { Warn ('Could not restore ' + $name + ' after an archive error: ' + $_.Exception.Message) }
        }
        throw $moveError
    }
    Say ('Previous rooms and server saves archived in ' + $archive)
}

function Get-ServerPort {
    $configuration = Join-Path $ArchipelagoDir 'host.yaml'
    if (Test-Path -LiteralPath $configuration -PathType Leaf) {
        foreach ($line in @(Get-Content -LiteralPath $configuration)) {
            if ($line -match '^\s*port:\s*(\d+)') { return [int]$Matches[1] }
        }
    }
    return 38281
}

function Test-PortListening([int]$Port) {
    try { return (@(Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction Stop).Count -gt 0) }
    catch {
        $socket = New-Object Net.Sockets.TcpClient
        try { return $socket.ConnectAsync('127.0.0.1', $Port).Wait(500) }
        catch { return $false }
        finally { $socket.Dispose() }
    }
}

function Generate-Room {
    $players = Join-Path $ArchipelagoDir 'Players'
    $templates = Join-Path $players 'Templates\Mindustry.yaml'
    $output = Join-Path $ArchipelagoDir 'output'
    New-Item -ItemType Directory -Path $players, $output -Force | Out-Null
    $options = @(Get-ChildItem -LiteralPath $players -File | Where-Object { $_.Extension -in @('.yaml', '.yml') })
    if ($options.Count -eq 0) {
        if (-not (Test-Path -LiteralPath $templates -PathType Leaf)) {
            throw 'The Mindustry options template is missing from the Archipelago installation.'
        }
        $contents = [IO.File]::ReadAllText($templates)
        $contents = [regex]::Replace($contents, '(?m)^name:\s*Player(?:\{number\})?\s*$', 'name: Mindustry')
        [IO.File]::WriteAllText((Join-Path $players 'Mindustry.yaml'), $contents, [Text.UTF8Encoding]::new($false))
        Say ('Created default player options in ' + $players)
    }
    $stdin = Join-Path $stateDir 'generator-input.txt'
    [IO.File]::WriteAllText($stdin, [Environment]::NewLine)
    $stdout = Join-Path $stateDir 'generator.log'
    $stderr = Join-Path $stateDir 'generator-error.log'
    $arguments = '--player_files_path "' + $players + '" --outputpath "' + $output + '"'
    Say 'Generating an Archipelago room...'
    $process = Start-Process -FilePath $generatorExe -ArgumentList $arguments -WorkingDirectory $ArchipelagoDir -RedirectStandardInput $stdin -RedirectStandardOutput $stdout -RedirectStandardError $stderr -Wait -PassThru
    if ($process.ExitCode -ne 0) { throw ('Room generation failed; see ' + $stdout + ' and ' + $stderr) }
    $rooms = @(Get-Rooms)
    if ($rooms.Count -ne 1) { throw ('Expected one generated room, found ' + $rooms.Count) }
    Say ('Generated ' + $rooms[0].FullName)
    return $rooms[0].FullName
}

function Start-RoomServer([string]$RoomPath, [int]$Port) {
    if (Test-PortListening $Port) { throw ('Port ' + $Port + ' is already in use; no second server was started.') }
    $arguments = '"' + $RoomPath + '"'
    if (-not $PublicHost) { $arguments += ' --host 127.0.0.1' }
    $stdout = Join-Path $stateDir 'server.log'
    $stderr = Join-Path $stateDir 'server-error.log'
    $stdin = Join-Path $stateDir 'server-input.txt'
    [IO.File]::WriteAllText($stdin, '')
    $runner = Join-Path $stateDir 'server-runner.ps1'
    $runnerConfig = Join-Path $stateDir 'server-runner.json'
    $runnerSource = @'
param([Parameter(Mandatory = $true)][string]$ConfigPath)
$ErrorActionPreference = 'Stop'
$errorLog = Join-Path (Split-Path -Parent $ConfigPath) 'server-error.log'
try {
    $config = Get-Content -LiteralPath $ConfigPath -Raw | ConvertFrom-Json
    $process = Start-Process -FilePath $config.ServerExe -ArgumentList $config.Arguments `
        -WorkingDirectory $config.WorkingDirectory -RedirectStandardInput $config.Stdin `
        -RedirectStandardOutput $config.Stdout -RedirectStandardError $config.Stderr `
        -WindowStyle Hidden -Wait -PassThru
    # Windows reports a normal console Ctrl+C shutdown as STATUS_CONTROL_C_EXIT.
    if ($process.ExitCode -ne 0 -and $process.ExitCode -ne -1073741510) {
        Add-Content -LiteralPath $errorLog -Value ('Archipelago server exited with code ' + $process.ExitCode)
    }
} catch {
    Add-Content -LiteralPath $errorLog -Value ('Archipelago server runner failed: ' + $_.Exception.Message)
    exit 1
}
'@
    [IO.File]::WriteAllText($runner, $runnerSource, [Text.UTF8Encoding]::new($false))
    @{
        ServerExe = $serverExe
        Arguments = $arguments
        WorkingDirectory = $ArchipelagoDir
        Stdin = $stdin
        Stdout = $stdout
        Stderr = $stderr
    } | ConvertTo-Json -Compress | Set-Content -LiteralPath $runnerConfig -Encoding UTF8
    # A separate process owns the long-lived server's log handles, so captured
    # launcher output can close as soon as this command finishes.
    $powershellExe = Join-Path $env:WINDIR 'System32\WindowsPowerShell\v1.0\powershell.exe'
    $runnerArguments = '-NoProfile -NonInteractive -ExecutionPolicy Bypass -File "' + $runner + '" -ConfigPath "' + $runnerConfig + '"'
    Start-Process -FilePath $powershellExe -ArgumentList $runnerArguments -WindowStyle Hidden | Out-Null
    for ($attempt = 0; $attempt -lt 30; $attempt++) {
        Start-Sleep -Milliseconds 500
        $running = Find-ProcessIn $ArchipelagoDir '^ArchipelagoServer\.exe$'
        if ($running -and (Test-PortListening $Port)) {
            Say ('Archipelago server started (PID ' + $running.ProcessId + ', port ' + $Port + ').')
            return $running
        }
        if (-not $running -and $attempt -ge 3) { break }
    }
    throw ('Archipelago server did not start; see ' + $stdout + ' and ' + $stderr)
}

function Show-ConnectionHint([string]$RoomPath, [int]$Port) {
    if (-not $RoomPath) { return }
    $defaultOptions = Join-Path $ArchipelagoDir 'Players\Mindustry.yaml'
    if ((Test-Path -LiteralPath $defaultOptions -PathType Leaf) -and
        (@(Get-Content -LiteralPath $defaultOptions | Where-Object { $_ -eq 'name: Mindustry' }).Count -gt 0)) {
        Say ('In Mindustry, press Enter and type: /connect localhost:' + $Port + ' Mindustry')
    } else {
        Say ('In Mindustry, connect through Settings > Archipelago to localhost:' + $Port + ' with your slot name.')
    }
    Say 'Connect before opening Campaign.'
}

$mutex = [Threading.Mutex]::new($false, 'Local\MindustryArchipelagoWindowsLauncher')
$lockHeld = $false
try {
    $lockHeld = $mutex.WaitOne(0)
    if (-not $lockHeld) { Say 'Another launcher invocation is already working.'; return }
    New-Item -ItemType Directory -Path $gameDir, $stateDir, $downloadDir, $backupDir -Force | Out-Null
    $script:launcherState = Read-State
    $client = Get-ClientInstall
    $gameProcess = Find-ProcessIn $gameDir '^(Mindustry-Archipelago-.*|javaw?)\.exe$'
    $serverProcess = Find-ProcessIn $ArchipelagoDir '^ArchipelagoServer\.exe$'
    Say ('Installed Mindustry client: ' + $client.Tag)
    if ($gameProcess) { Say ('Mindustry is running (PID ' + $gameProcess.ProcessId + ').') }
    else { Say 'Mindustry is stopped.' }
    if ($serverProcess) { Say ('Archipelago server is running (PID ' + $serverProcess.ProcessId + ').') }
    else { Say 'Archipelago server is stopped.' }
    if ($NewRoom -and $gameProcess) { throw 'Close Mindustry before using -NewRoom.' }

    $latestClient = $null
    $clientAsset = $null
    $declaredWorldTag = $null
    $requiredWorldTag = $null
    if (-not $NoUpdate) {
        try {
            $latestClient = Get-LatestClientRelease
            if ($latestClient) {
                $clientAsset = Get-Asset $latestClient '^(Windows|Win)_Mindustry_.*\.zip$'
                $declaredWorldTag = Get-CompatibleWorldTag $latestClient
                $requiredWorldTag = $declaredWorldTag
                Say ('Latest Windows client: ' + $latestClient.tag_name)
                if ($requiredWorldTag) { Say ('Matching Mindustry APWorld: ' + $requiredWorldTag) }
            }
        } catch { Warn ('Update check failed: ' + $_.Exception.Message) }
    }
    if (-not $requiredWorldTag -and $script:launcherState.ApworldTag) {
        $requiredWorldTag = $script:launcherState.ApworldTag
    }
    if (-not $requiredWorldTag -and $client.Tag -eq 'v0.5.1') { $requiredWorldTag = 'v0.5.0' }
    $worldRelease = $null
    $worldHashMismatch = $false
    $worldHashMigration = $false
    if (-not $NoHost -and $requiredWorldTag -and
        $script:launcherState.ApworldTag -eq $requiredWorldTag -and
        (Test-Path -LiteralPath $worldPath -PathType Leaf)) {
        $expectedWorldHash = $script:launcherState.ApworldHash
        if ($expectedWorldHash -notmatch '^[0-9a-fA-F]{64}$') { $expectedWorldHash = '' }
        if (-not $expectedWorldHash -and -not $NoUpdate) {
            try {
                $worldRelease = Get-Release $worldRepo $requiredWorldTag
                $worldAsset = Get-Asset $worldRelease '^mindustry\.apworld$'
                $expectedWorldHash = Get-AssetHash $worldAsset
                $script:launcherState.ApworldHash = $expectedWorldHash
                $worldHashMigration = $true
            } catch { Warn ('Could not verify the installed Mindustry APWorld: ' + $_.Exception.Message) }
        }
        if ($expectedWorldHash) {
            $worldHashMismatch = (Get-FileHash -LiteralPath $worldPath -Algorithm SHA256).Hash -ine $expectedWorldHash
            if ($worldHashMismatch) { Say 'Mindustry APWorld differs from the verified release; repair required.' }
        } elseif ($NoUpdate) {
            Warn 'The installed Mindustry APWorld has no recorded digest; run without -NoUpdate to verify it.'
        }
    }

    $roomPath = $null
    if ($Room) {
        $roomPath = [IO.Path]::GetFullPath($Room)
        if (-not (Test-Path -LiteralPath $roomPath -PathType Leaf) -or
            $roomPath -notmatch '\.(zip|archipelago)$') { throw ('Room file not found or invalid: ' + $Room) }
    } elseif (-not $NoHost -and -not $NewRoom) {
        $rooms = @(Get-Rooms)
        if ($rooms.Count -eq 1) { $roomPath = $rooms[0].FullName }
        elseif ($rooms.Count -gt 1) { Warn 'Multiple rooms exist; pass -Room with the one to host.' }
    }
    $activeRoom = Get-ServerRoom $serverProcess
    if ($serverProcess -and $activeRoom -and -not $Room -and -not $NewRoom) {
        if ($roomPath -and -not [string]::Equals($roomPath, $activeRoom, [StringComparison]::OrdinalIgnoreCase)) {
            Warn ('The running server hosts ' + $activeRoom + ', so its room will be used.')
        }
        $roomPath = $activeRoom
    }
    $roomMismatch = $roomPath -and $activeRoom -and
        -not [string]::Equals($roomPath, $activeRoom, [StringComparison]::OrdinalIgnoreCase)
    if ($roomMismatch) { Warn ('The running server hosts ' + $activeRoom + ', not ' + $roomPath) }
    if ($roomPath) { Say ('Local room: ' + $roomPath) }
    if ($Status) {
        if ($latestClient -and $client.Tag -ne $latestClient.tag_name) {
            Say ('Client update available: ' + $client.Tag + ' -> ' + $latestClient.tag_name)
        }
        if (-not $NoHost -and $requiredWorldTag -and
            ($script:launcherState.ApworldTag -ne $requiredWorldTag -or $worldHashMismatch)) {
            Say ('Archipelago bundle to check/install: ' + $requiredWorldTag)
        }
        if ($script:launcherState.SoftwareGlTag) {
            $glStatus = 'needs repair'
            if ($client.Path) {
                try {
                    if (Test-SoftwareGlReady (Get-SoftwareGlTargets $client.Path)) { $glStatus = 'ready' }
                } catch { }
            }
            Say ('Mesa software OpenGL ' + $script:launcherState.SoftwareGlTag + ': ' + $glStatus)
        } elseif ($SoftwareGL) { Say 'Mesa software OpenGL will be installed on the next game launch.' }
        if ($serverProcess -and $roomPath -and -not $roomMismatch) { Show-ConnectionHint $roomPath (Get-ServerPort) }
        return
    }
    if ($roomMismatch -and -not $NoHost) {
        throw 'The selected room differs from the running server. Stop ArchipelagoServer.exe and run the launcher again.'
    }
    if ($NewRoom) {
        Stop-LocalServer $serverProcess
        $serverProcess = $null
    }

    $worldReady = $NoHost
    $bundleUpdateError = ''
    if (-not $NoHost) {
        $installedComponents = (Test-Path -LiteralPath $serverExe -PathType Leaf) -and
            (Test-Path -LiteralPath $generatorExe -PathType Leaf) -and
            (Test-Path -LiteralPath $worldPath -PathType Leaf)
        $needsBundle = (-not $installedComponents) -or $ForceUpdate -or $worldHashMismatch -or
            ($requiredWorldTag -and $script:launcherState.ApworldTag -ne $requiredWorldTag)
        if ($worldHashMigration -and -not $needsBundle) { Save-State }
        if ($needsBundle) {
            if ($serverProcess -or $gameProcess) {
                Warn 'Archipelago update postponed while Mindustry or its server is running.'
                if (-not $installedComponents) { throw 'Archipelago is incomplete and cannot host this room.' }
                if ($worldHashMismatch) { throw 'Stop Mindustry and its server, then rerun to repair the Mindustry APWorld.' }
            } elseif ($NoUpdate) {
                if (-not $installedComponents) { throw 'Archipelago is missing; run again without -NoUpdate.' }
                if ($worldHashMismatch) { throw 'The Mindustry APWorld differs from the verified release; run again without -NoUpdate to repair it.' }
                Warn 'Archipelago version could not be checked while -NoUpdate is set.'
            } else {
                if (-not $requiredWorldTag) {
                    throw 'The client release does not identify a compatible APWorld; automatic local setup was stopped.'
                }
                try {
                    if (-not $worldRelease) { $worldRelease = Get-Release $worldRepo $requiredWorldTag }
                    Install-Archipelago $worldRelease
                    $installedComponents = $true
                    $worldHashMismatch = $false
                } catch {
                    if ($_.Exception -is [OperationCanceledException] -or $ForceUpdate -or $worldHashMismatch -or -not $installedComponents) { throw }
                    $bundleUpdateError = $_.Exception.Message
                    Warn ('Archipelago update failed; keeping the installed version: ' + $bundleUpdateError)
                }
            }
        }
        $worldReady = $installedComponents -and
            (-not $requiredWorldTag -or $script:launcherState.ApworldTag -eq $requiredWorldTag) -and
            -not $worldHashMismatch
        if (-not $worldReady -and -not $client.Path) {
            if ($bundleUpdateError) { throw ('Archipelago setup failed: ' + $bundleUpdateError) }
            throw 'The installed Archipelago version could not be verified against the client release.'
        }
        if ($NewRoom) {
            if (Test-PortListening (Get-ServerPort)) {
                throw 'The Archipelago port is still in use; the old room was not archived.'
            }
            Archive-OldRooms
        }
        if (-not $roomPath -and -not $serverProcess) {
            $rooms = @(Get-Rooms)
            if ($rooms.Count -eq 0) { $roomPath = Generate-Room }
            elseif ($rooms.Count -eq 1) { $roomPath = $rooms[0].FullName }
            else { throw 'Multiple rooms exist; pass -Room with the one to host.' }
        }
        $port = Get-ServerPort
        if (-not $serverProcess -and $roomPath) {
            $serverProcess = Start-RoomServer $roomPath $port
        }
        if (-not $serverProcess) { throw 'No Archipelago server is running. Use -NoHost for a remote room.' }
        if (-not $roomMismatch) { Show-ConnectionHint $roomPath $port }
    }

    if (-not $SetupOnly) {
        $updateClient = $false
        if ($latestClient -and $clientAsset) {
            if (-not $client.Path) { $updateClient = $true }
            elseif ($client.Changed -or $client.Tag -eq 'unknown') { $updateClient = $ForceUpdate }
            elseif ($ForceUpdate -or (Test-NewerTag $latestClient.tag_name $client.Tag)) { $updateClient = $true }
        }
        if ($updateClient -and $gameProcess) {
            Warn 'Client update postponed until Mindustry exits.'
        } elseif ($updateClient -and -not $NoHost -and -not $declaredWorldTag) {
            Warn 'Client update postponed: its release notes do not identify a compatible Mindustry APWorld.'
        } elseif ($updateClient -and -not $NoHost -and -not $worldReady) {
            Warn 'Client update postponed until the matching APWorld is installed.'
        } elseif ($updateClient) {
            try {
                Install-Client $clientAsset $latestClient.tag_name
                $client = Get-ClientInstall
            } catch {
                if (-not $client.Path) { throw }
                Warn ('Client update failed; keeping the installed game: ' + $_.Exception.Message)
            }
        } elseif ($client.Changed) {
            Warn 'The installed EXE was modified; use -ForceUpdate to replace it after a backup.'
        }
        if (-not $client.Path) { throw 'Mindustry is missing. Check the download error and run again.' }
        if ($SoftwareGL -or $script:launcherState.SoftwareGlTag) {
            $glTargets = Get-SoftwareGlTargets $client.Path
            $glReady = Test-SoftwareGlReady $glTargets
            if ($gameProcess) {
                if (-not $glReady) { Warn 'Mesa software OpenGL setup postponed until Mindustry exits.' }
            } else {
                $mesaRelease = $null
                if (-not $NoUpdate) {
                    try { $mesaRelease = Get-LatestSoftwareGlRelease }
                    catch {
                        if (-not $glReady) { throw ('Could not check the Mesa release: ' + $_.Exception.Message) }
                        Warn ('Mesa update check failed; keeping the installed renderer: ' + $_.Exception.Message)
                    }
                }
                $needsMesa = -not $glReady -or ($mesaRelease -and
                    $script:launcherState.SoftwareGlTag -ne [string]$mesaRelease.tag_name)
                if ($needsMesa) {
                    if ($NoUpdate) {
                        throw 'Mesa software OpenGL is missing or incomplete; run again without -NoUpdate.'
                    }
                    if (-not $mesaRelease) { throw 'No Mesa MSVC release is available for software OpenGL.' }
                    try { Install-SoftwareGl $mesaRelease $glTargets }
                    catch {
                        if (-not $glReady) { throw }
                        Warn ('Mesa update failed; keeping the installed renderer: ' + $_.Exception.Message)
                    }
                }
                if (-not (Test-SoftwareGlReady $glTargets)) {
                    throw 'Mesa software OpenGL is incomplete; the game was not started.'
                }
            }
        }
        if (-not $gameProcess) {
            $previousDriver = $env:GALLIUM_DRIVER
            try {
                if ($script:launcherState.SoftwareGlTag) { $env:GALLIUM_DRIVER = 'llvmpipe' }
                $started = Start-Process -FilePath $client.Path -WorkingDirectory $gameDir -PassThru
            } finally { $env:GALLIUM_DRIVER = $previousDriver }
            Say ('Started Mindustry (PID ' + $started.Id + ').')
        }
    }
} finally {
    if ($lockHeld) { $mutex.ReleaseMutex() }
    $mutex.Dispose()
}
