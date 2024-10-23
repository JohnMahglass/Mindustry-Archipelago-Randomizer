# Mindustry options explained

## Campaign choice
There are 2 campaign in Mindustry. The Serpulo planet and the Erekir planet. Campaign gameplay and lenght varies between both campaign. If you are willing to play a longer game you can also select to play both campaign. Serpulo is a more open-ended campaign with tower defense type gameplay. Erekir is a more linear campaign with an RTS type gameplay.

## Tutorial skip
This option allows you to skip the first sector of your selected campaign. This option also make the first sector you choose to land on free of resources cost. This means that even if you enable this option and still choose to launch to the tutorial sector first, your free launch will be consumed. This option will also unlock every research that are required to clear the tutorial (these research are not randomized).

## Disable invasion
This option will only affect the Serpulo campaign. This option will prevent hostile sector from invading the sector you have already conquered. This will prevent the player from loosing progress.

## Faster production
This option will double the rate of production/extraction from the following research:
#### Serpulo
- Graphite Press
- Multi-Press
- Silicon Smelter
- Silicon Crucible
- Kiln
- Plastanium Compressor
- Phase Weaver
- Surge Smelter
- Cryofluid Mixer
- Pyratite Mixer
- Blast Mixer
- Melter
- Separator
- Disassembler
- Spore Press
- Pulverizer
- Coal Centrifuge
- Mechanical Pump
- Rotary Pump
- Impulse Pump
- Mechanical Drill
- Pneumatic Drill
- Laser Drill
- Blast Drill
- Water Extractor
- Oil Extractor
- Cultivator

#### Erekir

- Silicon Arc Furnace
- Electrolyzer
- Atmospheric Concentrator
- Oxidation Chamber
- Carbide Crucible
- Surge Crucible
- Cyanogen Synthesizer
- Phase Synthesizer
- Cliff Crusher
- Plasma Bore
- Impact Drill
- Eruption Drill
- Reinforced Pump

## Death link

Death link is an option that will allow you the receive death link signal. When a player with the death link option dies, they send a death link signal to every other player with the death link option, killing them.

In Mindustry, it is possible to disable death link in the client's option even if you enabled it in the options file (.yaml).

There are several different death link mode that you can choose to customise your game experience:

#### Unit
In this mode, when the Mindustry player core unit dies, it sends a death link signal to the multiworld. When a signal is received, the core unit will die and respawn at its closest core.

#### Core
In this mode, when every core of the Mindustry player has been destoryed on the played sector (When the sector is lost) the death link signal is sent. When a signal is received, every core of the Mindustry player get destroyed (Loosing the sector).

#### Core Russian roulette
This mode has been created for played that feels like the Unit mode is not punishing enough but also think that the Core mode is too punishing. In this mode, when every core of the Mindustry player has been destoryed on the played sector (When the sector is lost) the death link signal is sent. **However**, when receiving a death link signal, there is a default 1/6 chances (That value can be changed in the option file) to have your cores destroyed. If your core has not been destroyed, the chances will drop to 1/5 until the Mindustry player has their core destroyed.

##### Additionnal option for the Core and Core Russian roulette option
In the game client Archipelago setting menu, it is possible to protect captured sector from being destroyed from a death link signal.

## Randomize core units weapon
This option randomize every core unit "weapon". This mean that your T1 core unit might have a T5 weapon while your T3 core unit might have a self destruct weapon. This option may heavily influence game balance.

#### Serpulo
For the Serpulo campaign, the core units weapon will be swapped from a list of weapons.

#### Erekir
For the Erekir campaign, since the vanilla core unit does not have weapon and is invulnerable, this option will instead give a random ability to your core unit and will make the core unit vulnerable. Abilities can range from spawning units to having a nice trailing vfx effect on your core unit.

## Military level tracking
This option will add additional logic tracking for the Mindustry items related to defending your base and attacking. Every offensive/defesive research grants Military score points and every sector has a required amount of points for the logic to consider that the player can clear the sector. It is possible to get out of logic when using this option if you know what you are doing.

## Logistic distribution
Change how some logistics research are distributed:

#### Randomized logistics
Randomize logistics research. It is possible that you have to play your game without some basic research like bridge and router using this option.

#### Early logistics
Logistics research will be placed within sphere 1 of players of the Multiworld.

#### Local early logistics
Logistics research will be placed in the Mindustry player world within sphere 1.

#### Starter logistics
Logistics will be unlocked when starting the game.

## Make early roadblocks local
This option will place some item that are required early on to progress in their respective campaign. This will make sure the player is not stuck waiting to receive some research after 10 - 30 min of playtime. Recommended when playing with a large group of player. The items that are affected by this option are:

#### Serpulo
- Graphite Press
- Silicon Smelter
- Combustion Generator

#### Erekir
- Impact Drill
- Reinforced Conduit
- Ship Fabricator
- Progressive Ships x1 (Elude)

## Amount of resources required
This option allow customization of the amount of resources required to unlock the 'Victory node'.