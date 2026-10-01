# Optional launchers

These launchers run the released Mindustry Archipelago client from a separate local installation beside the scripts. They do not build the game from source. The scripts keep existing game and room data, detect running processes, and check for client updates before launching.

Run a launcher from this directory, or copy the script and its README into an empty directory first. Each script uses paths relative to its own location. The generated `randomizer/`, `archipelago/`, `windows/`, and `.mindustry-launcher/` directories are ignored by Git here.

## Windows

See [README-Windows.md](README-Windows.md). The PowerShell script downloads the Windows client and its matching Archipelago installation, verifies GitHub's SHA-256 digests, and creates a local room. Software OpenGL is available with `-SoftwareGL` for a VM whose graphics driver cannot provide OpenGL; it is an explicit opt-in.

## Linux

The Bash script downloads and verifies the Linux client release. For local hosting it uses an existing compatible Archipelago installation. Install the Archipelago server, generator, Mindustry APWorld, and player options template before hosting a room. If you play on somebody else's server, use `--no-host` and you only need the game client.

Requirements: Bash, Java 17 or newer, `curl`, `jq`, `unzip`, `flock`, `sha256sum`, and `realpath`. `ss` is used when available to check whether the server port is occupied. A local server also needs the compatible Linux Archipelago bundle from the [Mindustry APWorld releases](https://github.com/JohnMahglass/Archipelago-Mindustry/releases).

To set up a local server in this directory:

1. Check the [client release notes](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases) for the compatible APWorld version. Download that version's Linux `tar.gz`, `mindustry.apworld`, and `MindustryDefaultOptions.yaml` from the APWorld release page.
2. Extract the Linux bundle so its `Archipelago` directory is at `archipelago/Archipelago` relative to this script.
3. Copy `mindustry.apworld` to `archipelago/Archipelago/lib/worlds/mindustry.apworld` and `MindustryDefaultOptions.yaml` to `archipelago/Archipelago/Players/Templates/Mindustry.yaml`.
4. Run `./launch-mindustry.sh`. The script creates `Players/Mindustry.yaml` with slot name `Mindustry` if there are no player files, generates a room, starts the server, and launches the game.

If Archipelago is installed elsewhere, set `MINDUSTRY_AP_DIR` to the directory containing `ArchipelagoGenerate` and `ArchipelagoServer`. The script detects processes for that installation. It does not modify another Archipelago installation's player files unless it needs to create the default `Mindustry.yaml`.

```bash
./launch-mindustry.sh                 # check for updates, host a room, and launch
./launch-mindustry.sh --status        # inspect processes, room, and client update
./launch-mindustry.sh --no-host       # launch without a local server
./launch-mindustry.sh --setup-only    # host a room without launching the game
./launch-mindustry.sh --new-room      # archive the old local room and server save
./launch-mindustry.sh --room /path/to/AP_123.zip
./launch-mindustry.sh --help
```

Connect in **Settings → Archipelago** or use `/connect localhost:38281 Mindustry` in chat **before** opening Campaign. If you changed the server port or slot name, use those values instead. A locally hosted server listens on `127.0.0.1` by default; `--public-host` allows other computers to connect.

The game client keeps its AP progress locally, while the Archipelago server also keeps the room's checked locations and delivered items. **Reset AP data** in the game does not erase the server's room save. To start a new local game, reset AP data, close the game, run `--new-room`, and connect to the new room before opening Campaign. The command archives old `AP_*` rooms and `.apsave` files under `.mindustry-launcher/backups/rooms`.

The Linux launcher checks for newer game releases. It does not update an existing Archipelago installation or choose a different APWorld version for it; update those together using the compatibility note in the client release. If the server or generator is unavailable, the game can still launch and connect to a remote room.
