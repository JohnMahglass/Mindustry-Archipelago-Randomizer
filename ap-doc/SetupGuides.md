Last update: 2026-02-18
# Mindustry randomizer setup guide
## Required software
The custom [Mindustry client for Archipelago](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases)

Mindustry is a free open-source game. If you enjoy the game, please consider supporting the game's developer on [Steam](https://store.steampowered.com/app/1127400/Mindustry/) or [Itch.io](https://anuke.itch.io/mindustry). The Steam version is not free but feature additional features like achievements, seamless multiplayer and map browsing/Workshop support.

## Installation procedures
### Windows
1. Go to the latest release.[https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases]
2. Download the "Win_Mindustry_*.zip" file. (Replace the * symbol with the version)
3. Extract the downloaded files **in its own** directory.
3. Run `Mindustry-Archipelago.exe`
4. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)
5. Have fun.

### Linux
1. (Go to the latest release.[https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases]
2. Download the "Linux_Mindustry_*.zip" file. (Replace the * symbol with the version)
3. Extract the downloaded files **in its own** directory.
2. Make sure you have Java JDK installed. You can install the Java 17 JDK using the terminal:\
   Ubuntu => `sudo apt install openjdk-17-jre`  
   Arch => `sudo pacman -S jdk17-openjdk`
3. Open the terminal
4. Make sure you are in the directory containing `Mindustry-Archipelago.jar` (One of the files you downloaded from the release page.)
5. Run the game by typing `java -jar Mindustry-Archipelago-v*.jar` in the terminal. (Replace the * symbol with the version)
6. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)
7. Have fun.

## Connecting
There is 2 way to connect to an Archipelago multiworld once you are in Mindustry game menu, the interface method and the command method. If you are playing a multiworld that requires a password you will be required to use the interface method.

### Interface method
1. From the game's main menu, open the "Settings" menu.
2. Select the "Archipelago" menu.
3. Under the "Connection options" tab, enter your connection informations.
4. Press the "Apply changes" button. The client will now remember your login information everytime the client is opened.
5. Press the "Connect" button to connect to the multiworld.
6. Return to the game main menu and your connection status will be displayed in the game's chat.

### Command method
1. From the game's main menu, press "Enter" to open chat.
2. Type /connect [address]:[port] [Slot name], replacing every value inside brackets by their appropriate value for your multiworld.
3. Press "Enter" to send the command.
4. The connection status will be displayed in the game's chat.

The client will now remember your connection informations. When resuming play, you will only need to use the ```/connect``` command (without arguments) to connect to the multiworld

## Playing multiple multiworld on the same computer
If you would like to play multiple instance of Mindustry in the same multiworld, you need to 
copy `Mindustry-Archipelago-version.exe` and `jre` to a different directory. Game settings and 
game data are saved in the directory that holds `Mindustry-Archipelago-version.exe`.

## Create a config (.yaml) file
There is currently no web pages for generating a Mindustry config file. In the meantime, you can 
get a default config file from the release page or the apworld release page.

## About multiplayer
Multiplayer is currently not supported, however it is a planned feature.