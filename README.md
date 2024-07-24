# Mindustry integration for Archipelago 
(https://github.com/ArchipelagoMW/Archipelago)

 This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repo for more information.

 To generate a World, you will need to add the Mindustry World to your Archipelago folder. You can find the Mindustry World here -> (https://github.com/JohnMahglass/Archipelago-Mindustry)

 Please be aware that Linux build has not tested yet.

## What has been changed?

- **Randomizer is working for Serpulo's campaign only at the moment.**
- **Currently, only the "Skip Tutorial" option has been implemented**, you can change the other option in the YAML but they will most likely make the game unstable right now.
- Most node from the research tree has been replaced with location checks.
- A "Victory" node has been added, researching this node will inform Archipelago that the player has finished their World. (Campaign clear logic not implemented yet)
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Client and Archipelago commands have not yet been added but are coming soon. In the meantime, please use the text client to use commands.


## How to build and play the game.

### **THIS IS NOT A RELEASE. THERE ARE NO EXECUTABLE FILES AT THE MOMENT, YOU WILL NEED TO BUILD THE GAME TO BE ABLE TO PLAY IT.**

### Windows
1. Make sure you have [JDK 16-17](https://adoptium.net/archive.html?variant=openjdk17&jvmVariant=hotspot) installed.

2. Download the .zip file from this repo and unzip the project. You can also clone the project if you have git.

3. Open the folder of the project and open the cmd. **Make sure you are in the project directory.**

4. Type "gradlew desktop:run"

5. The program should begin to build. The first time you run this command it may take a while until the process is done.

6. Once build is over, the game will launch.

7. Go to Settings -> Archipelago and enter your game information to connect.

8. Have fun.

Whenver you want to play the game you will need to repeat step 3 to 7.

## Known bugs


## Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you can find the Discord invite on the Archipelago website. You can find the game's thread by searching "Mindustry" in the "future-game-design" section.