# Mindustry integration for Archipelago (v0.5.0)
https://github.com/ArchipelagoMW/Archipelago  
Last update: 2026-02-18

This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repository for more information.

To generate a World(APworld), you will need to install the Mindustry World into your Archipelago custom_worlds folder. You can find the Mindustry World release here -> https://github.com/JohnMahglass/Archipelago-Mindustry/releases

# Table of Contents
1. [Introduction](#introduction)
2. [Installation](#setup)
3. [How to compile](#compile)
4. [Contributors](#contributors)

### What has been changed from the vanilla game? <a name="introduction" />

- Save data are separated from vanilla game so that playing Archipelago doesn't erase your vanilla saves. (You should still backup your saves as this is in development)
- Most node from the research tree has been replaced with location checks.
- A "Victory" node has been added, researching this node will inform Archipelago that the player has completed their goal. If both planet are selected as a campaign, each victory nodes need to be researched to complete the goal.
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Use '/help' in the client to list all client commands.
- It is not possible to construct a fabricator type block if the associated unit has not been researched as well on Erekir planet.
- The research tree now shows every nodes name. This change makes it easier to plan a route if you need to get to a specific location.


## Setup guide <a name="setup" />

### Windows
1. Go to the [latest release](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases).
2. Download the "Win_Mindustry_*.zip" file. (Replace the * symbol with the version)
3. Extract the downloaded files **in its own** directory.
3. Run `Mindustry-Archipelago.exe`
4. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)
5. Have fun.


### Linux
1. Go to the [latest release](https://github.com/JohnMahglass/Mindustry-Archipelago-Randomizer/releases).
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


## Known bugs

- Sometimes when unlocking a research from a new category, the selectable block UI will not update until you exit the sector and enter again or receive another item.

### Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you 
can find the Discord invite on the Archipelago website. You can find the game's thread by searching `Mindustry` in the `future-game-design` section.

You can also open an issue in this repository.

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


## Contributing and Contributors <a name="contributors" />
I accept PR's. If you would like to contribute and have questions, you can contact me on the official Archipelago Discord server. you
can find the Discord invite on the Archipelago website. You can find the game's thread by searching `Mindustry` in the "future-game-design" section.

### Contributors
- @Antydon
- @Flashkirby
- @9382
