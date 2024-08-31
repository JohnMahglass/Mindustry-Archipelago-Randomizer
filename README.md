# Mindustry integration for Archipelago (v0.1.1)
(https://github.com/ArchipelagoMW/Archipelago)

 This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repo for more information.

 To generate a World(APworld), you will need to install the Mindustry World to your Archipelago folder. You can find the Mindustry World release here -> (https://github.com/JohnMahglass/Archipelago-Mindustry)

 Please be aware that Linux build has not been tested yet.

 ## What has been changed?

- Save data are separated from vanilla game so that playing Archipelago doesn't erase your vanilla saves. (You should still backup your saves as this is in developement)
- Most node from the research tree has been replaced with location checks.
- A "Victory" node has been added, researching this node will inform Archipelago that the player has finished their World. If both planet are selected as a campaign, each victory nodes need to be researched to complete the goal.
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Use '/help' in the client to list all client commands.
- It is not possible to construct a fabricator if the associated unit has not been researched as well on Erekir planet.
 
### Version 0.1.1 changelog

- Updated death link text to display the name of the player.
- Fixed a bug where a process related to the new Archipelago settings menu was not terminated when the program was closed.
- Fixed a bug where the connection to client was not properly closed when the application was closed.
- Fixed a bug where a death link signal would be sent when dying from another player death link.
- Fixed victory requirement on Erekir not taking into account the planet's ressources scaling.
- Removed some leftover research requirements for unlocking some nodes in Erekir's techtree.


### Version 0.1.0 changelog

- Removed unused feature to prevent confusion.
- Added client commands. To view available commands, use '/help'.
- Improved feedback given to player on their connection status when a change occurs.
- Updated Java Client to version 0.1.19.
- Added "Faster production", "Disable invasions" and "Death link" options when generating an APworld.
- The "Erekir" and "All" campaign are now available to choose when generating a game.

## Setup guide.

### Windows
	1. Download the latest release.

	2. Extract the downloaded file in a directory.

	3. Run "Mindustry-Archipelago.exe"

	3. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)

	4. Have fun.

### Linux
	1. Download the .jar file from the latest release.

	2. Make sure you have Java JRE installed. You can install the Java 17 JRE on Ubuntu using ```sudo apt install openjdk-17-jre``` in the terminal.

	3. Open the terminal

	4. Make sure you are in the directory containing 'Mindustry-Archipelago.jar' (The file you downloaded from the release page.)

	5. Type ```java -jar Mindustry-Archipelago.jar``` in the terminal.

## Known bugs

- Sometimes when unlocking a research from a new category, the selectable block UI will not update until you exit the sector and enter again or receive another item.

- Upon unlocking a node wich auto-unlock other nodes (Like the Tank Fabricator unlocking also the Stell node) The error icon will appear in the toast announcing the new research to the player. (Reason is still unknown.)

- Serpulo Events trigger might not be accurate when viewing in a tracker.

- When playing with the 'Faster production' option, the enemy ai might glitch wich makes the ai passive.

## Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you can find the Discord invite on the Archipelago website. You can find the game's thread by searching "Mindustry" in the "future-game-design" section.