# Mindustry integration for Archipelago (v0.2.0)
(https://github.com/ArchipelagoMW/Archipelago)

This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repo for more information.

To generate a World(APworld), you will need to install the Mindustry World to your Archipelago folder. You can find the Mindustry World release here -> (https://github.com/JohnMahglass/Archipelago-Mindustry/releases)

### What has been changed from the vanilla game?

- Save data are separated from vanilla game so that playing Archipelago doesn't erase your vanilla saves. (You should still backup your saves as this is in developement)
- Most node from the research tree has been replaced with location checks.
- A "Victory" node has been added, researching this node will inform Archipelago that the player has finished their World. If both planet are selected as a campaign, each victory nodes need to be researched to complete the goal.
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Use '/help' in the client to list all client commands.
- It is not possible to construct a fabricator if the associated unit has not been researched as well on Erekir planet.

## Version 0.2.0 changelog

- It is now required for the game to close and be open again after the first connection to a game. This will make sure that all of the randomizer options are correctly applied.
- Chat will now highlight specific words to make it easier to see important information.
- Chat will now display the color of an item fitting to its classification when receiving an item event message. (Progression, Useful, Filler)
- Added "/clear" client command to clear all chat messages.
- Moved some items into progressive categories for some research that did not make sense.
- Renamed progressive items categories for Serpulo campaign.
- When selecting a campaign, any unselected campaign will now behave as vanilla.
- "Military level tracking" option is now available. Serpulo and Erekir logic has been improved with a "Military level" tracking the player's research that can be used
  to capture sectors to make sure the player has a minimum amount of tools. You can opt out of this option if you would like to live dangerously.
- "Randomize core units weapon" option is now available (Serulo only at the moment).
- "Logistic distribution" option is now available. You can select "Randomize logistics", "Early logistics" and "Starter logistics". Logistics being junction,router and bridge
  for belt,duct and conduit. Also include the Power Node for Serpulo campaign.
- Starting items is now supported by the client.
- Starting items from pool is now supported by the client.
- Added Empty filler item "A fistful of nothing..." to the world to prevent generation failure if an item is taken out of the item pool.

## Setup guide.

### Windows
	1. Download the latest release.

	2. Extract the downloaded file in a directory.

	3. Run "Mindustry-Archipelago.exe"

	3. Go to Settings -> Archipelago and enter your game information to connect. (Or use the chat's client commands)

	4. Have fun.

### Linux
	1. Download the .jar file from the latest release.
    2. Make sure you have Java JRE installed. You can install the Java 17 JRE using the terminal:
    Ubuntu -> "sudo apt install openjdk-17-jre"
    Arch based -> "sudo pacman -S jdk17-openjdk"
    4. Open the terminal
    5. Make sure you are in the directory containing 'Mindustry-Archipelago.jar' (The file you downloaded from the release page.)
    6. Type "java -jar Mindustry-Archipelago.jar" in the terminal.
    7. Have fun.



## Known bugs

- Sometimes when unlocking a research from a new category, the selectable block UI will not update until you exit the sector and enter again or receive another item.

- Upon unlocking a node wich auto-unlock other nodes (Like the Tank Fabricator unlocking also the Stell node) The error icon will appear in the toast announcing the new research to the player.

- Serpulo Events trigger "Produce Slag on Serpulo" might not be accurate when viewing with a tracker.

## Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you can find the Discord invite on the Archipelago website. You can find the game's thread by searching "Mindustry" in the "future-game-design" section.