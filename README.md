# Mindustry integration for Archipelago (v0.3.0)
(https://github.com/ArchipelagoMW/Archipelago)\
Last update: 2025-01-31

This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repo for more information.

To generate a World(APworld), you will need to install the Mindustry World to your Archipelago folder. You can find the Mindustry World release here -> (https://github.com/JohnMahglass/Archipelago-Mindustry/releases)

# Table of Contents
1. [Introduction](#introduction)
2. [Changelog](#changelog)
3. [Installation](#setup)
4. [How to compile](#compile)

### What has been changed from the vanilla game? <a name="introduction" />

- Save data are separated from vanilla game so that playing Archipelago doesn't erase your vanilla saves. (You should still backup your saves as this is in developement)
- Most node from the research tree has been replaced with location checks.
- A "Victory" node has been added, researching this node will inform Archipelago that the player has finished their World. If both planet are selected as a campaign, each victory nodes need to be researched to complete the goal.
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Use '/help' in the client to list all client commands.
- It is not possible to construct a fabricator if the associated unit has not been researched as well on Erekir planet.
- The research tree now shows every nodes. This change makes it easier to plan a route if you need to get a specific location.
- On Serpulo, some derelict has been removed to prevent the player from breaking out of logic.

## Version 0.3.0 changelog <a name="changelog" />
### Changes
- Made the Archipelago menu in Settings prettier
- Added client options to filter chat messages. You can find theses new options in Settings -> Archipelago.
- Death link now have 3 mode. "Unit" will kill the player unit and send a signal when the player's unit dies. "Core" Will destroy every player core and will send a signal when a sector is lost. "Core Russian roulette" behave like "Core" but will have a chance of destroying the core upon receiving a signal (customizable).
- Added client options to protect captured sector from being destroyed by some Death link modes. You can find this new option in the Settings -> Archipelago
- Added the "Make early roadblocks local" option. This option make sure that some basic research required to progress early on are local.
- Added the "Amount of resources required" option. You can now ajust how much of each resources you need to unlock the "Victory" nodes.
- Removed some derelict from Serpulo map to prevent player from going out of logic.
- Changed text and added tooltip for the "Clear data" button to prevent confusion.
- Removed some old buttons in the Settings -> Archipelago menu.
- Added warning text on the 'Game data' setting page to inform player that theses options will not reset AP data.

### Fix
- Fixed Randomized Serpulo weapon position to better fit the core unit.
- Fixed a bug where the "Error" icon would be displayed when unlocking multiple nodes.
- Fixed a bug where progressive items would not be unlocked when received.
- Fixed a bug where invasion could still happen when the player selected the "Disable invasion" option.
- In the generated spoiler, the "->" symbol has been changed to "to" to prevent confusion of overlapping symbols.
- Fixed a bug where upon not reseting the client after goaling a game the goal signal could be sent again when unlocking a node in a new game.
- Fixed a bug where multiple Mindustry player in the same multiworld would have the same randomized weapons.
- Possible fix for a crash happening on the window showing up the first time a player connect to a game.


## Setup guide <a name="setup" />

### Windows
1. Download the latest release.
2. Extract the downloaded file in a directory.
3. Run `Mindustry-Archipelago.exe`
4. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)
5. Have fun.


### Linux
1. Download the .jar file from the latest release.
2. Make sure you have Java JRE installed. You can install the Java 17 JRE using the terminal:\
   Ubuntu => `sudo apt install openjdk-17-jre`\
   Arch based => `sudo pacman -S jdk17-openjdk`
3. Open the terminal
4. Make sure you are in the directory containing `Mindustry-Archipelago.jar` (The file you downloaded from the release page.)
5. Type `java -jar Mindustry-Archipelago.jar` in the terminal.
6. Have fun.


## Known bugs

- Sometimes when unlocking a research from a new category, the selectable block UI will not update until you exit the sector and enter again or receive another item.
- Serpulo Events trigger "Produce Slag on Serpulo" might not be accurate when viewing with a tracker.
- On the "Basin" sector in Erekir campaign, the AI might not launch attack on the player, stacking massive quantities of units in their bases.
- When playing with the death link option, it is possible that the client stop receiving and sending death link signal. Restarting the game will solve the issue.

### Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you can find the Discord invite on the Archipelago website. You can find the game's thread by searching "Mindustry" in the "future-game-design" section.

## How to compile <a name="compile" />
If you would like to compile this code on your machine you can follow these instructions:
1. Create a directory for the project.
2. Open a terminal and clone this repo `git clone https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer.git`
3. Wait until the repo is done being downloaded.
4. In the same terminal, type `git clone https://github.com/JohnMahglass/mindustry-v146-arc`, 
   this library is required for the project to compile.
5. Open the directory named `mindustry-v146-arc` there should be a single directory named `Arc`. 
6. Move the `Arc` directory to be on the same level as your `Mindustry-Archipelago-Randomizer` 
   directory. (The first one you cloned)
7. Delete the `mindustry-v146-arc` directory.
8. You should now have a directory with `Mindustry-Archipelago-Randomizer` and `Arc`
9. Open the `Mindustry-Archipelago-Randomizer` directory and open the terminal.
10. Type `gradlew desktop:run`
11. The project should compile and run!