#!/usr/bin/env bash
# Launch the Linux Mindustry Archipelago client and, when available, its local room.
set -Eeuo pipefail
umask 077

ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd -P)
GAME_DIR="$ROOT/randomizer"
GAME_JAR="$GAME_DIR/Mindustry.jar"
BUNDLED_ZIP="$GAME_DIR/Linux_Mindustry_0_5_1.zip"
AP_DIR="${MINDUSTRY_AP_DIR:-$ROOT/archipelago/Archipelago}"
if [[ -d "$AP_DIR" ]]; then AP_DIR=$(realpath -e -- "$AP_DIR"); fi
AP_SERVER="$AP_DIR/ArchipelagoServer"
STATE_DIR="$ROOT/.mindustry-launcher"
VERSION_FILE="$STATE_DIR/client-version"
GAME_LOG="$STATE_DIR/mindustry.log"
SERVER_LOG="$STATE_DIR/archipelago-server.log"
GENERATOR_LOG="$STATE_DIR/archipelago-generator.log"
RELEASES_URL='https://api.github.com/repos/JohnMahglass/Mindustry-Archipelago-Randomizer/releases?per_page=20'

STATUS_ONLY=0
NO_UPDATE=0
NO_HOST=0
FORCE_UPDATE=0
SETUP_ONLY=0
PUBLIC_HOST=0
NEW_ROOM=0
ROOM=""
TMP_DIR=""

say() { printf '%s\n' "$*"; }
warn() { printf 'Warning: %s\n' "$*" >&2; }
fail() { printf 'Error: %s\n' "$*" >&2; exit 1; }

usage() {
    cat <<'EOF'
Usage: ./launch-mindustry.sh [--status] [--no-update] [--no-host]
                             [--room FILE] [--force-update] [--setup-only]
                             [--public-host] [--new-room]

By default, check for a newer Linux client, generate a local solo room on first
run if a compatible Archipelago installation is available, start its server,
and launch Mindustry. Set MINDUSTRY_AP_DIR to use another Archipelago directory.

  --status        Show process, room, and update status without starting anything.
  --no-update     Skip the network check and use the installed game.
  --no-host       Do not start a local Archipelago server.
  --room FILE     Host this .zip or .archipelago room instead of auto-selecting.
  --force-update  Replace a locally changed JAR with the latest release (backup first).
  --setup-only    Prepare and host a room without launching or updating the game.
  --public-host   Let other computers connect to a locally hosted room.
  --new-room      Archive the old room/save, generate a fresh room, and host it.
  --help          Show this help.
EOF
}

while (($#)); do
    case "$1" in
        --status) STATUS_ONLY=1 ;;
        --no-update) NO_UPDATE=1 ;;
        --no-host) NO_HOST=1 ;;
        --force-update) FORCE_UPDATE=1 ;;
        --setup-only) SETUP_ONLY=1; NO_UPDATE=1 ;;
        --public-host) PUBLIC_HOST=1 ;;
        --new-room) NEW_ROOM=1 ;;
        --room)
            (($# >= 2)) || fail "--room needs a file path"
            ROOM=$(realpath -e -- "$2") || fail "room file not found: $2"
            shift
            ;;
        --help|-h) usage; exit 0 ;;
        *) fail "unknown option: $1 (see --help)" ;;
    esac
    shift
done

[[ -z "$ROOM" || -f "$ROOM" ]] || fail "room is not a file: $ROOM"
[[ -z "$ROOM" || "$ROOM" == *.zip || "$ROOM" == *.archipelago ]] || fail "room must be a .zip or .archipelago file"
[[ "$NEW_ROOM" == 0 || "$NO_HOST" == 0 ]] || fail "--new-room cannot be used with --no-host"
[[ "$NEW_ROOM" == 0 || -z "$ROOM" ]] || fail "--new-room cannot be used with --room"
[[ "$NEW_ROOM" == 0 || "$STATUS_ONLY" == 0 ]] || fail "--new-room cannot be used with --status"
mkdir -p -- "$STATE_DIR"
chmod 700 -- "$STATE_DIR"
command -v flock >/dev/null || fail "flock is required"
exec 9>"$STATE_DIR/launcher.lock"
flock -n 9 || { say "Another launcher invocation is already working."; exit 0; }
trap '[[ -z "$TMP_DIR" ]] || rm -rf -- "$TMP_DIR"' EXIT

# Look at /proc as well as our own launcher state, so a manually started game
# is recognized and a stale PID file cannot cause a second copy to start.
find_game_pid() {
    local proc pid cwd arg candidate i
    local -a args
    for proc in /proc/[0-9]*; do
        [[ -r "$proc/cmdline" ]] || continue
        args=()
        mapfile -d '' -t args < "$proc/cmdline" 2>/dev/null || continue
        ((${#args[@]} > 2)) || continue
        for ((i=1; i<${#args[@]}-1; i++)); do
            [[ "${args[i]}" == -jar ]] || continue
            arg=${args[i+1]}
            if [[ "$arg" == /* ]]; then
                candidate=$(realpath -m -- "$arg")
            else
                cwd=$(readlink -f -- "$proc/cwd") || continue
                candidate=$(realpath -m -- "$cwd/$arg")
            fi
            if [[ "$candidate" == "$GAME_JAR" ]]; then
                pid=${proc##*/}
                [[ $(ps -o stat= -p "$pid" 2>/dev/null) == Z* ]] && continue
                printf '%s\n' "$pid"
                return 0
            fi
        done
    done
    return 1
}

find_server_pid() {
    local proc exe pid
    for proc in /proc/[0-9]*; do
        exe=$(readlink -f -- "$proc/exe" 2>/dev/null) || continue
        [[ "$exe" == "$AP_SERVER" ]] || continue
        pid=${proc##*/}
        [[ $(ps -o stat= -p "$pid" 2>/dev/null) == Z* ]] && continue
        printf '%s\n' "$pid"
        return 0
    done
    return 1
}

server_room_for_pid() {
    local proc="/proc/$1" arg cwd
    local -a args
    [[ -r "$proc/cmdline" ]] || return 1
    mapfile -d '' -t args < "$proc/cmdline" 2>/dev/null || return 1
    for arg in "${args[@]:1}"; do
        [[ "$arg" == *.zip || "$arg" == *.archipelago ]] || continue
        if [[ "$arg" == /* ]]; then
            realpath -m -- "$arg"
        else
            cwd=$(readlink -f -- "$proc/cwd") || return 1
            realpath -m -- "$cwd/$arg"
        fi
        return 0
    done
    return 1
}

file_sha() { sha256sum -- "$1" | cut -d ' ' -f 1; }

SERVER_PORT=38281
if [[ -f "$AP_DIR/host.yaml" ]]; then
    configured_port=$(sed -nE 's/^[[:space:]]*port:[[:space:]]*([0-9]+).*/\1/p' "$AP_DIR/host.yaml" | head -n 1)
    [[ "$configured_port" == "" ]] || SERVER_PORT=$configured_port
fi

connection_hint() {
    [[ -n "$server_pid" && -n "$ROOM" ]] || return 0
    local active_room
    active_room=$(server_room_for_pid "$server_pid" || true)
    [[ -z "$active_room" || "$active_room" == "$ROOM" ]] || return 0
    if [[ -f "$AP_DIR/Players/Mindustry.yaml" ]] && \
        grep -Fxq 'name: Mindustry' "$AP_DIR/Players/Mindustry.yaml"; then
        say "Before opening Campaign, type this in Mindustry's chat: /connect localhost:$SERVER_PORT Mindustry"
    else
        say "Before opening Campaign, connect in Settings > Archipelago to localhost:$SERVER_PORT with your slot name."
    fi
}

# The original unpacked JAR can be identified without a previous launcher run.
local_version=unknown
local_sha=""
if [[ -f "$GAME_JAR" ]]; then
    local_sha=$(file_sha "$GAME_JAR")
    if [[ -f "$VERSION_FILE" ]]; then
        recorded_version=""
        recorded_sha=""
        read -r recorded_version recorded_sha < "$VERSION_FILE" || true
        if [[ "$recorded_sha" == "$local_sha" ]]; then
            local_version=$recorded_version
        else
            warn "the JAR differs from the launcher's recorded version; automatic replacement is disabled"
        fi
    elif [[ -f "$BUNDLED_ZIP" ]] && command -v unzip >/dev/null; then
        bundled_sha=$(unzip -p "$BUNDLED_ZIP" Mindustry.jar 2>/dev/null | sha256sum | cut -d ' ' -f 1) || bundled_sha=""
        [[ "$local_sha" != "$bundled_sha" ]] || local_version=v0.5.1
    fi
fi

game_pid=$(find_game_pid || true)
server_pid=$(find_server_pid || true)
if [[ -n "$game_pid" ]]; then say "Mindustry is running (PID $game_pid)."; else say "Mindustry is stopped."; fi
if [[ -n "$server_pid" ]]; then say "Archipelago server is running (PID $server_pid)."; else say "Archipelago server is stopped."; fi
say "Installed client: $local_version"

rooms=()
if [[ -z "$ROOM" && "$NO_HOST" == 0 && "$NEW_ROOM" == 0 && -d "$AP_DIR/output" ]]; then
    shopt -s nullglob
    rooms=("$AP_DIR/output/"AP_*.zip "$AP_DIR/output/"AP_*.archipelago)
    shopt -u nullglob
    if ((${#rooms[@]} == 1)); then ROOM=${rooms[0]}; fi
    if ((${#rooms[@]} > 1)); then
        warn "multiple generated rooms found; use --room FILE to choose one"
    fi
fi
if [[ -n "$ROOM" ]]; then say "Local room: $ROOM"; fi
if [[ -z "$ROOM" && "$NO_HOST" == 0 && -z "$server_pid" && ${#rooms[@]} == 0 ]]; then
    if [[ -x "$AP_DIR/ArchipelagoGenerate" && -f "$AP_DIR/Players/Templates/Mindustry.yaml" ]]; then
        say "No local room found; the launcher will generate a solo Mindustry room."
    else
        say "No local room found; install the compatible Archipelago bundle to host one."
    fi
fi
if [[ -n "$ROOM" && -n "$server_pid" ]]; then
    active_room=$(server_room_for_pid "$server_pid" || true)
    if [[ -n "$active_room" && "$active_room" != "$ROOM" ]]; then
        warn "the running server is hosting $active_room, not $ROOM"
    fi
fi

remote_version=""
remote_url=""
remote_digest=""
if [[ "$NO_UPDATE" == 0 ]]; then
    if command -v curl >/dev/null && command -v jq >/dev/null; then
        TMP_DIR=$(mktemp -d "$STATE_DIR/.tmp.XXXXXXXX")
        if curl --fail --silent --show-error --location --retry 2 \
            --connect-timeout 5 --max-time 25 \
            -H 'Accept: application/vnd.github+json' \
            -H 'User-Agent: mindustry-archipelago-launcher' \
            "$RELEASES_URL" -o "$TMP_DIR/releases.json"; then
            release_row=$(jq -r '
                [.[] | select(.draft == false) | . as $release
                | .assets[] | select(.name | test("^Linux_Mindustry_.*\\.zip$"))
                | [$release.tag_name, .browser_download_url, (.digest // "")]]
                | first | if . == null then empty else @tsv end
            ' "$TMP_DIR/releases.json" 2>/dev/null) || release_row=""
            if [[ -n "$release_row" ]]; then
                IFS=$'\t' read -r remote_version remote_url remote_digest <<< "$release_row"
                say "Latest Linux client: $remote_version"
            else
                warn "GitHub returned no usable Linux release; using the installed game"
            fi
        else
            warn "update check failed; using the installed game"
        fi
    else
        warn "curl and jq are needed for update checks; using the installed game"
    fi
fi

needs_update=0
if [[ -n "$remote_version" ]]; then
    if [[ ! -f "$GAME_JAR" ]]; then
        needs_update=1
    elif [[ "$local_version" == unknown ]]; then
        if [[ "$FORCE_UPDATE" == 1 ]]; then
            needs_update=1
        else
            warn "unknown or changed JAR; use --force-update to replace it after a backup"
        fi
    elif [[ "$remote_version" != "$local_version" ]] && \
        [[ $(printf '%s\n%s\n' "$local_version" "$remote_version" | sort -V | tail -n 1) == "$remote_version" ]]; then
        needs_update=1
    fi
fi

if [[ "$needs_update" == 1 ]]; then
    say "A client update is available ($local_version -> $remote_version)."
fi
if [[ "$STATUS_ONLY" == 1 ]]; then connection_hint; exit 0; fi

install_jar_from_zip() {
    local zip_file=$1 version=$2 staged="$TMP_DIR/Mindustry.jar" backup companion
    command -v unzip >/dev/null || { warn "unzip is needed to install the client"; return 1; }
    unzip -tqq "$zip_file" || { warn "the client archive failed its integrity check"; return 1; }
    unzip -p "$zip_file" Mindustry.jar > "$staged" || { warn "Mindustry.jar is missing from the archive"; return 1; }
    [[ -s "$staged" ]] && unzip -tqq "$staged" || { warn "the extracted JAR is invalid"; return 1; }
    if [[ -f "$GAME_JAR" ]]; then
        mkdir -p -- "$STATE_DIR/backups"
        backup=$(mktemp "$STATE_DIR/backups/Mindustry-$(date +%Y%m%d-%H%M%S)-${local_version}-XXXXXXXX.jar")
        cp -p -- "$GAME_JAR" "$backup" || return 1
        say "Previous JAR backed up to $backup"
    fi
    mkdir -p -- "$GAME_DIR"
    for companion in MindustryDefaultOptions.yaml MindustryLICENSE.txt; do
        if [[ ! -e "$GAME_DIR/$companion" ]] && unzip -Z1 "$zip_file" | grep -Fx "$companion" >/dev/null; then
            unzip -p "$zip_file" "$companion" > "$GAME_DIR/$companion" || \
                warn "could not extract $companion from the client archive"
        fi
    done
    mv -f -- "$staged" "$GAME_JAR" || return 1
    local_sha=$(file_sha "$GAME_JAR")
    printf '%s %s\n' "$version" "$local_sha" > "$TMP_DIR/client-version"
    mv -f -- "$TMP_DIR/client-version" "$VERSION_FILE"
    local_version=$version
    say "Installed client $version."
}

if [[ -n "$game_pid" && "$needs_update" == 1 ]]; then
    say "Update postponed until Mindustry exits."
elif [[ "$needs_update" == 1 ]]; then
    if [[ "$remote_digest" != sha256:* || ! "${remote_digest#sha256:}" =~ ^[0-9a-f]{64}$ ]]; then
        warn "release has no valid SHA-256 digest; leaving the installed JAR alone"
    elif [[ "$remote_url" != https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases/download/* ]]; then
        warn "unexpected release URL; leaving the installed JAR alone"
    elif curl --fail --silent --show-error --location --retry 2 \
        --connect-timeout 10 --max-time 600 "$remote_url" -o "$TMP_DIR/client.zip"; then
        if [[ $(file_sha "$TMP_DIR/client.zip") == "${remote_digest#sha256:}" ]]; then
            install_jar_from_zip "$TMP_DIR/client.zip" "$remote_version" || warn "update failed; using the installed JAR"
        else
            warn "downloaded archive SHA-256 does not match GitHub; using the installed JAR"
        fi
    else
        warn "client download failed; using the installed JAR"
    fi
fi

if [[ "$SETUP_ONLY" == 0 && ! -f "$GAME_JAR" && -f "$BUNDLED_ZIP" ]]; then
    [[ -n "$TMP_DIR" ]] || TMP_DIR=$(mktemp -d "$STATE_DIR/.tmp.XXXXXXXX")
    say "Restoring the bundled client."
    install_jar_from_zip "$BUNDLED_ZIP" v0.5.1 || fail "could not restore the bundled client"
fi
if [[ "$SETUP_ONLY" == 0 ]]; then
    [[ -f "$GAME_JAR" ]] || fail "Mindustry.jar is missing and no usable release is available"
fi

if [[ "$NEW_ROOM" == 1 ]]; then
    [[ -z "$game_pid" ]] || fail "close Mindustry before creating a new room"
    [[ -x "$AP_DIR/ArchipelagoGenerate" && -f "$AP_DIR/Players/Templates/Mindustry.yaml" ]] || \
        fail "the Archipelago generator or Mindustry template is missing"

    if [[ -n "$server_pid" ]]; then
        say "Stopping the current Archipelago server (PID $server_pid)."
        stopped_by_systemd=0
        if command -v systemctl >/dev/null; then
            unit_pid=$(systemctl --user show --property=MainPID --value mindustry-archipelago-server.service 2>/dev/null || true)
            if [[ "$unit_pid" == "$server_pid" ]]; then
                systemctl --user stop mindustry-archipelago-server.service || fail "could not stop the server service"
                stopped_by_systemd=1
            fi
        fi
        if [[ "$stopped_by_systemd" == 0 ]]; then
            kill -TERM "$server_pid" || fail "could not stop the existing server"
        fi
        for ((attempt=0; attempt<20; attempt++)); do
            server_pid=$(find_server_pid || true)
            [[ -n "$server_pid" ]] || break
            sleep 0.5
        done
        [[ -z "$server_pid" ]] || fail "the existing server is still running; its room was left in place"
    fi

    shopt -s nullglob
    old_room_files=("$AP_DIR/output/"AP_*.zip "$AP_DIR/output/"AP_*.archipelago "$AP_DIR/output/"AP_*.apsave)
    shopt -u nullglob
    if ((${#old_room_files[@]})); then
        room_backup="$STATE_DIR/backups/rooms/$(date +%Y%m%d-%H%M%S)-$$"
        mkdir -p -- "$room_backup"
        mv -- "${old_room_files[@]}" "$room_backup/" || fail "could not archive the old room files"
        say "Previous room and server save archived in $room_backup"
    fi
    ROOM=""
    rooms=()
    say "A new Archipelago room will be generated."
fi

if [[ "$NO_HOST" == 0 && -z "$ROOM" && -z "$server_pid" && ${#rooms[@]} == 0 ]]; then
    if [[ ! -x "$AP_DIR/ArchipelagoGenerate" || ! -f "$AP_DIR/Players/Templates/Mindustry.yaml" ]]; then
        warn "the Archipelago generator or Mindustry template is missing"
    else
        mkdir -p -- "$AP_DIR/Players" "$AP_DIR/output"
        shopt -s nullglob
        player_files=("$AP_DIR/Players/"*.yaml "$AP_DIR/Players/"*.yml)
        shopt -u nullglob
        if ((${#player_files[@]} == 0)); then
            sed -E 's/^name: Player(\{number\})?$/name: Mindustry/' \
                "$AP_DIR/Players/Templates/Mindustry.yaml" > "$AP_DIR/Players/Mindustry.yaml"
            say "Created default player options: $AP_DIR/Players/Mindustry.yaml"
        fi
        say "Generating an Archipelago room (log: $GENERATOR_LOG)..."
        if (cd -- "$AP_DIR" && ./ArchipelagoGenerate \
            --player_files_path "$AP_DIR/Players" --outputpath "$AP_DIR/output" \
            <<< '' >> "$GENERATOR_LOG" 2>&1); then
            shopt -s nullglob
            rooms=("$AP_DIR/output/"AP_*.zip "$AP_DIR/output/"AP_*.archipelago)
            shopt -u nullglob
            if ((${#rooms[@]} == 1)); then
                ROOM=${rooms[0]}
                say "Generated local room: $ROOM"
            else
                warn "generation finished but no unique room was found; see $GENERATOR_LOG"
            fi
        else
            warn "room generation failed; see $GENERATOR_LOG"
        fi
    fi
fi

if [[ "$NO_HOST" == 0 && -n "$ROOM" ]]; then
    if [[ -n "$server_pid" ]]; then
        say "Keeping the existing Archipelago server."
    elif [[ ! -x "$AP_SERVER" ]]; then
        warn "the bundled Archipelago server is missing or not executable"
    elif [[ "$ROOM" == *.zip ]] && \
        ! unzip -Z1 "$ROOM" 2>/dev/null | grep -E '\.archipelago$' >/dev/null; then
        warn "room ZIP contains no .archipelago data; server was not started"
    elif command -v ss >/dev/null && [[ -n $(ss -H -ltn "sport = :$SERVER_PORT" 2>/dev/null) ]]; then
        warn "port $SERVER_PORT is already in use; server was not started"
    else
        server_args=("$ROOM")
        if [[ "$PUBLIC_HOST" == 0 ]]; then server_args+=(--host 127.0.0.1); fi
        if command -v systemd-run >/dev/null && \
            systemd-run --user --unit=mindustry-archipelago-server --collect \
                --working-directory="$AP_DIR" \
                --property="StandardOutput=append:$SERVER_LOG" \
                --property="StandardError=append:$SERVER_LOG" \
                "$AP_SERVER" "${server_args[@]}" >/dev/null 2>&1; then
            say "Started Archipelago server as a user service."
        else
            (
                cd -- "$AP_DIR"
                nohup ./ArchipelagoServer "${server_args[@]}" >> "$SERVER_LOG" 2>&1 < /dev/null 9>&- &
            )
        fi
        sleep 2
        server_pid=$(find_server_pid || true)
        if [[ -n "$server_pid" ]]; then
            say "Started Archipelago server (PID $server_pid; log: $SERVER_LOG)."
        else
            warn "Archipelago server exited during startup; see $SERVER_LOG"
        fi
    fi
elif [[ -z "$server_pid" && "$NO_HOST" == 0 ]]; then
    say "No unique local room found; connect Mindustry to a remote room if you have one."
fi

connection_hint

if [[ "$SETUP_ONLY" == 1 ]]; then
    [[ -n "$server_pid" ]] || fail "no local Archipelago server is running; see $GENERATOR_LOG and $SERVER_LOG"
    exit 0
fi

if [[ -z "$game_pid" ]]; then
    JAVA_BIN=${MINDUSTRY_JAVA:-java}
    command -v "$JAVA_BIN" >/dev/null || fail "Java is required to run Mindustry"
    (
        cd -- "$GAME_DIR"
        nohup "$JAVA_BIN" -jar ./Mindustry.jar >> "$GAME_LOG" 2>&1 < /dev/null 9>&- &
    )
    sleep 2
    game_pid=$(find_game_pid || true)
    if [[ -n "$game_pid" ]]; then
        say "Started Mindustry (PID $game_pid; log: $GAME_LOG)."
    else
        fail "Mindustry exited during startup; see $GAME_LOG"
    fi
fi
