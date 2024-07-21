# Mindustry integration for Archipelago 
(https://github.com/ArchipelagoMW/Archipelago)

 This repo is a fork of Anuken's Mindustry (https://github.com/Anuken/Mindustry) and has been modified to be used with Archipelago. Visit their repo for more information.

 To generate a World, you will need to add the Mindustry World to your Archipelago folder. You can find the Mindustry World here -> (https://github.com/JohnMahglass/Archipelago-Mindustry)

 Please be aware that Linux build has not tested yet.

## What has been changed?

- **Randomizer is working for Serpulo's campaign only at the moment.**
- **Currently, only the "Skip Tutorial" option has been implemented**, you can change the other option in the YAML but they will most likely make the game unstable right now.
- Most node from the research tree has been replaced with location checks.
- A new menu has been added in Settings to configure Archipelago's settings.
- You can use the chat to send messages to other client (If they support it).
- Client and Archipelago commands have not yet been added but are coming soon. In the meantime, please use the text client to use commands.


## How to build and play the game.

### Windows
1. Make sure you have [JDK 16-17](https://adoptium.net/archive.html?variant=openjdk17&jvmVariant=hotspot) installed.

2. Make sure you have [git](https://git-scm.com/download/win) installed.

3. Navigate to the directory you would like to install the program.

4. Open cmd and type "git clone https://github.com/JohnMahglass/   Mindustry-Archipelago-Randomizer".

5. Open the folder of the project and open the cmd again. **Make sure you are in the project directory.**

6. Type "gradlew desktop:run"

7. The program should begin to build. The first time you run this command it may take a while until the process is done.

8. Once build is over, the game should launch.

Whenver you want to play the game you will need to repeat step 5 to 8.



## Report a bug.
You can report bugs that you find in the game's thread in the Archipelago Discord server, you can find the Discord invite on the Archipelago website. You can find the game's thread by searching "Mindustry" in the "future-game-design" section.