Last update: 2026-02-18
# Mindustry options explained
This documentation provides additional informations about the Mindustry specific options for your world generation.

## GOALS AND CAMPAIGNS
### campaign_choice
There are 2 campaign in Mindustry. The Serpulo planet and the Erekir planet. Campaign gameplay and length varies between both campaign. If you are willing to play a longer game you can also choose to play both campaign. Serpulo is a more open-ended campaign with tower defense type gameplay. Erekir is a more linear campaign with an RTS type gameplay.

### goal
There are currently 2 possible goal in Mindustry.  
**Resources**:  
Makes the victory nodes require **an amount of every collectable resources**.(customizable with the 'Amount of resources required' options)  
**Conquest**:  
Makes the victory nodes require **the completion of every named sector** on that planet.

### amount_of_resources_required
This option allow customization of the amount of resources required to unlock the 'Victory node' if the player chose **the Resources goal**.


## RANDOMIZATION
### add_sectors_as_location
Adds sectors as location. Allow the player to send items by unlocking new sectors. This add 17 fillers to Serpulo and 16 fillers to Erekir campaigns.

### add_resources_as_location
Adds resources as location. Allow the player to send items by collecting / harvesting a resource for the first time. This add 20 fillers to Serpulo and 18 fillers to Erekir campaigns.

### randomize_core_units_weapon
This option randomize every core unit "weapon". This mean that your T1 core unit might have a T5 weapon while your T3 core unit might have a self destruct weapon. This option may heavily influence game balance.  
**Serpulo**  
For the Serpulo campaign, the core units **weapons** will be swapped from a list of weapons.  
**Erekir**  
For the Erekir campaign, since the vanilla core unit does not have weapon and is invulnerable, this option will instead give a **random ability** to your core unit and **will make the core unit vulnerable**. Abilities can range from spawning units to having a nice trailing vfx effect on your core unit.

### military_level_tracking
This option will add additional logic tracking the Mindustry items related to defending your base and attacking. Every offensive/defesive research grants Military score points and every sector has a required amount of points for the logic to consider that the player can clear the sector. **It is highly recommanded to have this options turned on** since it is possible (and it will happen) that all your turrets and units are only unlockable in the final sectors.

### logistic_distribution
Change how some logistics research are distributed:  
**Randomized logistics**  
Randomize logistics research. It is possible that you have to play the game without some basic research like bridge and router using this option.  
**Early logistics**  
Logistics research will be placed within sphere 1 of players of the Multiworld.  
**Local early logistics**  
Logistics research will be placed in the Mindustry player world within sphere 1.  
**Starter logistics**  
Logistics will be unlocked when starting the game. This option will add 8 filler item on Serpulo and 6 on Erekir campaign.

### progressive_drills
This options will add drills to the list of progressive items. The items that are affected by this option are:  
**Serpulo**
- Pneumatic Drill (1)
- Laser Drill (2)
- Airblast Drill (3)  

**Erekir**  
- Impact Drill (1)
- Large Plasma Bore (2)
- Eruption Drill (3)

### progressive_generators
This option will add generators to the list of progressive items. The items that are affected by this option are:  
**Serpulo**
- Combustion Generator (1)
- Steam Generator (2)
- Thermal Generator (3)
- Differential Generator (4)
- Thorium Reactor (5)
- Impact Reactor (6)
- RTG Generator (7)

**Erekir**  
- Chemical Combustion Chamber (1)
- Pyrolysis Generator (2)
- Flux Reactor (3)
- Neoplasia Reactor (4)

### progressive_pumps
This option will add pumps to the list of progressive items. The items that are affected by this option are:  
**Serpulo**
- Mechanical pump (1)
- Rotrary pump (2)
- Impulse pump (3)

## QUALITY OF LIFE
### tutorial_skip
This option allows you to skip the first sector of your selected campaigns. This option also make the first sector you choose to land on free of resources cost. This means that even if you enable this option and still choose to launch to the tutorial sector first, **your free launch will be consumed**. This option will also unlock every research that are required to clear the tutorial.

### disable_invasions
Serpulo campaign only. This option will prevent hostile sector from invading the sector you have already conquered.

### faster_production
This option will **double the rate** of production from the following research:  
**Serpulo**
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

**Erekir**
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
- Large plasma bore
- Impact Drill
- Eruption Drill
- Reinforced Pump

### faster_unit_production
Half the production time for units being built inside a factory type building for the selected campaigns. This will also apply to the enemy.

### faster_conveyor
Doubles the conveyers speed for the selected campaigns. Note that fluid speed is based on the input of fluids in the system, which means that this option has no effects of fluid speed.

### make_early_roadblocks_local
This option will place some items that are required early on to progress in their respective campaign. This will make sure the player is not stuck waiting to receive some research after 10 - 30 min of playtime. Recommended when playing with a large group of player. The items that are affected by this option are:  
**Serpulo**
- Graphite Press
- Silicon Smelter
- Combustion Generator  

**Erekir**
- Impact Drill
- Reinforced Conduit
- Ship Fabricator
- Progressive Ships x1 (Elude)


## DEATH LINK
### death_link_mode
There are several different death link mode that you can choose to customise your game experience:
#### **Unit**
In this mode, when the Mindustry player **core unit** dies, it sends a death link signal to the multiworld. When a signal is received, the **core unit** will die.

#### **Core**
In this mode, **when every core of the Mindustry player has been destoryed** on the played sector (When the sector is lost) the death link signal is sent. When a signal is received, **every core** of the Mindustry player get destroyed (Losing the sector).

#### **Core Russian roulette**
In this mode, **when every core of the Mindustry player has been destoryed** on the played sector (When the sector is lost) the death link signal is sent. **However**, when receiving a death link signal, there is a **default 1/6 chances** (That value can be customized) to **have your cores destroyed**. If your core has not been destroyed, the chances will drop to 1/5 until the Mindustry player has their core destroyed.

### core_russian_roulette_chambers
Customize the amount of chances the Mindustry player starts with when selecting the "Core Russian roulette" death link mode. The default value is 6, meaning a 1 / 6 chances of triggering the players death upon receiving a death link signal.

## FILLERS AND TRAPS
When no filler or trap items are selected, the "Nothing" filler item will be added.
### construction_speed_buffs
Adds the "Construction speed buff" to the filler item pool. This buff will increase the construction speed of the player and its units by increments of 10%.

### research_discount_buffs
Adds the "Research discount buff" to the filler item pool. This buff will reduce the cost of researching nodes in the tech tree by increments of 5%. This buff is capped at a maximum value of 99%. When selecting this option, the randomization will prioritize placing 20 of this filler item before considering adding other filler items.

### factory_malfunction_trap
Requires to be connected to trigger. Adds the "Factory mafunction trap" to the filler item pool. This trap will cause a small amount of blocks from your factory to explode. The core unit is protected from this trap. 

### launch_wave_trap
Serpulo campaign only and requires to be connected to trigger. Adds the "Launch wave trap" to the filler item pool. This trap will instantly spawn a new wave of enemy if possible.

## Client options
Theses options can be found in the settings of the Mindustry client in the Archipelago category.
### Death link: Force disable
It is possible to disable death link even if you enabled it in your .yaml file.
### Death link: Protect captured sector
It is possible to **protect a captured sector from being destroyed** from a death link signal.

### Chat: Disable chat
Will prevent Archipelago chat message from being displayed in the chat.

### Chat: Self item only
Will only display item messages related to the player in the chat. Which mean that you will see the items you send to other players and the items you receive from them but you will not see item messages when a player send an item to another player. 

### Reset AP data
Wipe all saved data (Campaign progress, research, saves and archipelago data). This option is to be used when you want to play a new multiworld. The program will then exit.

### Manually verify victory
Validate that the player has already unlocked the requirement to goal their game. This option may be useful if you accidently goal while not being connected.