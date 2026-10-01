# Mindustry Archipelago on Windows

`launch-mindustry.ps1` downloads and launches the Windows build of the modified Mindustry client. By default it also installs the matching Archipelago bundle, creates a local room on first run, and starts its server. The game ZIP includes its own Java runtime.

## Start

Use 64-bit Windows with Windows PowerShell 5.1 or newer. Keep `launch-mindustry.ps1` in this folder, open PowerShell here, and run:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\launch-mindustry.ps1
```

The execution policy flag applies only to this PowerShell process. On the first local setup, Windows may show an administrator approval prompt for the Archipelago installer. The script downloads the release assets from the two upstream GitHub repositories, checks their published SHA-256 digests, and reuses verified downloads on later runs.

To share the launcher, send `launch-mindustry.ps1` and this README. The `windows\` folder is generated locally and contains downloads, rooms, and backups.

### Windows VMs without OpenGL

A fresh launcher installation uses the Windows graphics driver by default. If Mindustry reports that OpenGL is unavailable in a VM, close the game and opt into software rendering with:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\launch-mindustry.ps1 -SoftwareGL
```

This downloads the [Mesa Windows MSVC build](https://github.com/pal1000/mesa-dist-win) with a published SHA-256 digest and installs its [llvmpipe software OpenGL renderer](https://docs.mesa3d.org/drivers/llvmpipe.html) beside Mindustry and its bundled Java runtime. The launcher chooses the DLL architecture from the executables; the current game bundle needs the **32-bit** DLLs even on 64-bit Windows. It does not change Windows graphics drivers or system DLLs. The opt-in is remembered **for that installation**, so subsequent runs there keep using Mesa and check for Mesa updates. A fresh installation on another PC uses its normal graphics driver. `-NoUpdate` reuses an already complete installation.

llvmpipe draws on the CPU, so the game may run slowly in a VM. The Mesa package download is about 70 MB, and the installed DLLs use more disk space. Close Mindustry before installing or updating these files.

After the game opens, **connect before opening Campaign**. Press Enter in Mindustry and type:

```text
/connect localhost:38281 Mindustry
```

You can also use **Settings → Archipelago** with host `localhost`, port `38281`, and slot name `Mindustry`. If you already have your own player options, use the slot name in your YAML instead. The script prints the configured server port when it starts.

## What the launcher manages

- The Windows client and its bundled Java runtime go into `windows\Mindustry-Archipelago` beside the script. Saves and a customized `MindustryDefaultOptions.yaml` are kept when the client updates. Previous executables and runtimes are backed up.
- Archipelago is installed in `C:\ProgramData\Archipelago` by default. The matching Mindustry APWorld, server, generator, and options template come from the [Mindustry Archipelago release](https://github.com/JohnMahglass/Archipelago-Mindustry/releases). Windows may request administrator approval for this installer. Use `-ArchipelagoDir` if you already use another installation folder.
- On first run, if `Players` has no player YAML files, the script copies the Mindustry template as `Players\Mindustry.yaml` and sets its slot name to `Mindustry`. It then generates a room under `output` and starts a local server. Existing player YAML files and rooms are reused. If several rooms exist, choose one with `-Room`.
- The launcher checks the [Windows client releases](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases) for updates. It waits until the game and server have stopped before replacing files they use. The client update is held until the matching APWorld is available.
- The installed Mindustry APWorld is checked against the release SHA-256 digest on later runs. This also detects an interrupted Archipelago installer that restored an older bundled APWorld. Existing launcher state without a saved digest is verified on the next online run; `-NoUpdate` can check it after that digest has been saved.
- Download cache, update state, logs, and backups are in `windows\launcher-state`. A failed update leaves an existing client available for the next launch.
- After `-SoftwareGL` is used, the launcher keeps Mesa's OpenGL DLLs inside that game folder and backs up any DLLs it replaces. It also checks that those files remain intact before launching.

The local server listens on `127.0.0.1` by default, so only this computer can connect. The server stays running after this PowerShell command exits and is reused by later launches.

## Start a fresh local room

**Settings → Archipelago → Reset AP data** clears the game's local AP progress. If you reconnect to the same room, its server save can send your previous items again. After resetting AP data, let the game exit, then run:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\launch-mindustry.ps1 -NewRoom
```

`-NewRoom` requires the game to be closed. It stops the local Archipelago server, moves all `AP_*` room files and `.apsave` server saves from Archipelago's `output` folder into `windows\launcher-state\backups\rooms`, generates a new room, starts its server, and launches the game. Connect to the new room before opening Campaign. The old files remain in the backup if you need them.

If you already reconnected to the old room after resetting, use **Reset AP data** once more before running `-NewRoom`. That reset also clears the client's campaign saves, saves, and research; you do not need to clear those separately under **Game Data**.

## Useful commands

The examples below use direct script invocation. If execution policy blocks it, use the `powershell.exe -NoProfile -ExecutionPolicy Bypass -File` prefix from the first command.

```powershell
.\launch-mindustry.ps1 -Status                  # show installed/running versions and available updates
.\launch-mindustry.ps1 -NoUpdate                # launch using installed files without a network check
.\launch-mindustry.ps1 -NoHost                  # play in somebody else's Archipelago room
.\launch-mindustry.ps1 -Room 'C:\Rooms\AP_123.zip' # choose a local room to host
.\launch-mindustry.ps1 -NewRoom                # archive the old room and save, then start a fresh room
.\launch-mindustry.ps1 -SetupOnly               # prepare and host a room without starting the game
.\launch-mindustry.ps1 -ForceUpdate             # reinstall the latest release after backing up the client
.\launch-mindustry.ps1 -PublicHost              # allow other computers to reach the local server
.\launch-mindustry.ps1 -ArchipelagoDir 'D:\Archipelago' # use an existing installation path
.\launch-mindustry.ps1 -SoftwareGL             # install CPU-based OpenGL for a VM without working OpenGL
```

For a remote room, use `-NoHost`; then connect in **Settings → Archipelago** using the host, port, slot name, and password supplied by that room. `localhost` only works for a server on your own computer.

If you use `-PublicHost`, also allow the Archipelago server through Windows Firewall and configure your network as needed. The default port is `38281`; the script reads `host.yaml` if you changed it.

## Troubleshooting

- **Campaign shows a connection error:** connect to the Archipelago room in Mindustry before opening Campaign. Check that the slot name matches your player YAML.
- **More than one room exists:** run with `-Room 'path\to\AP_....zip'`. The script will not guess which room to host.
- **An update is postponed:** close the game and the local Archipelago server, then run the script again. The server process is `ArchipelagoServer.exe` in Task Manager.
- **The Mindustry APWorld needs repair:** close the game and local server, then rerun without `-NoUpdate`. The launcher will reinstall the matching bundle and verify the APWorld file.
- **Generation or server startup fails:** read `windows\launcher-state\generator.log`, `generator-error.log`, `server.log`, and `server-error.log`.
- **The first download fails:** check network access to GitHub and rerun. `-NoUpdate` works after the required files have been installed.
- **Mindustry says OpenGL is unsupported in a VM:** run with `-SoftwareGL` as shown above. The first run needs network access and Windows' built-in `tar.exe` to unpack Mesa's `.7z` archive. This is a CPU renderer, so lower the game resolution if performance is poor.

For manual setup and game background, see the project's [main README](../../README.md). For the other launcher, see [Launcher README](README.md).
