# Mindustry randomizer setup guide
## Required software
The custom [Mindustry client for Archipelago](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases)

Mindustry is a free open-source game. If you enjoy the game, please consider supporting the game's developer on [Steam](https://store.steampowered.com/app/1127400/Mindustry/) or [Itch.io](https://anuke.itch.io/mindustry). The Steam version is not free but feature additional features like achievements, seamless multiplayer and map browsing/Workshop support.

## Installation procedures
### Windows
1. Download the latest release.
2. Extract the downloaded file in a directory.
3. Run "Mindustry-Archipelago.exe"

### Linux
1. Download the .jar file from the latest release.
2. Make sure you have Java JRE installed. You can install the Java 17 JRE using the terminal:
Ubuntu -> "sudo apt install openjdk-17-jre"
Arch based -> "sudo pacman -S jdk17-openjdk"
4. Open the terminal
5. Make sure you are in the directory containing 'Mindustry-Archipelago.jar' (The file you downloaded from the release page.)
6. Type "java -jar Mindustry-Archipelago.jar" in the terminal.

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

## Create a config (.yaml) file
There is currently no web pages for generating a Mindustry config file. In the meantime, you can get a default config file from the Mindustry thread in the Future game design section of the Archipelago Discord server. The default config file is commented to help with option description.

## About multiplayer
Multiplayer is currently not supported, however it is a planned feature.