package mindustry.randomizer;

import static arc.math.Mathf.rand;
import static mindustry.Vars.state;
import static mindustry.Vars.ui;
import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;
import static arc.Core.settings;

import arc.Events;
import io.github.archipelagomw.ClientStatus;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.randomizer.client.DeathLink;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.client.APClient;
import mindustry.randomizer.constant.FillerTrapIdsConstant;
import mindustry.randomizer.constant.RandomizerConstant;
import mindustry.randomizer.enums.LogisticsDistribution;
import mindustry.randomizer.ui.APApplyOptionsDialog;
import mindustry.randomizer.ui.APChat.APMessage;
import mindustry.randomizer.utils.RandomizerMessageHandler;
import mindustry.type.ItemStack;
import mindustry.type.Sector;
import mindustry.type.SectorPreset;
import mindustry.world.Block;

import java.util.ArrayList;
import java.util.List;

import static mindustry.randomizer.enums.SettingStrings.*;
import static mindustry.randomizer.enums.ApChatColors.*;


/**
 * Randomizer for Archipelago multiworld randomizer.
 *
 * @author John Mahglass
 * @version 0.1.0 2024-05-12
 */
public class Randomizer {

    /**
     * Debug mode for the randomizer.
     */
    public boolean debug = false;

    /**
     * Client for the randomizer.
     */
    public APClient client;

    /**
     * Represent the state the APWorld is in.
     */
    public WorldState worldState;

    /**
     * True if the played has connected to the game previously.
     */
    public boolean hasConnectedPreviously;


    /**
     * Unlock a UnlockableContent.
     */
    public void unlock(Long id, UnlockableContent content){
        try {
            if (content != null) {
                if (worldState.isProgressive(id)) {
                    processProgressiveItem(id);
                }
                if (worldState.complexLocations.isSharedRessource(content)) {
                    worldState.complexLocations.unlockSharedRessource(content);
                    return;
                }
                content.quietUnlock();
            } else {
                RandomizerMessageHandler.printErrorWithReason(RandomizerConstant.NULL_CONTENT_UNLOCK_ERROR);
            }
        } catch (Exception e) {
            //TODO Log the error
            RandomizerMessageHandler.printErrorWithReason("Unknown error while sending a location check to Archipelago.");
        }
    }

    /**
     * Forward the check to Archipelago.
     * @param locationId The id of the location
     */
    public void checkLocation(Long locationId){
        try {
            verifyVictoryConditions(locationId);
            boolean success = false;
            if (client.isConnected()) {
                //Try to send check to archipelago
                success = client.checkLocation(locationId);
            }
            if (!client.isConnected() || !success) {
                //Check could not be send, added to check pending list.
                worldState.addCheck(worldState.checkPending, locationId);
                RandomizerMessageHandler.printErrorWithReason(RandomizerConstant.NOT_CONNECTED_PENDING_CHECKS);
            }
            //Add location to checked list and save world state.
            worldState.addCheck(worldState.locationsChecked, locationId);
            worldState.saveStates();
        } catch (Exception e) {
            //TODO Log the error
            RandomizerMessageHandler.printErrorWithReason("Unknown error while sending a location check to Archipelago.");
        }
    }

    /**
     * Unlock a sector from a planet using randomization.
     * @param sectorName the name of the sector.
     */
    public void unlockSector(String sectorName){
        boolean isAlreadyUnlocked = false;
        for (String sector : worldState.unlockedSector) {
            if (sector.equals(sectorName)) {
                isAlreadyUnlocked = true;
            }
        }
        if (!isAlreadyUnlocked) {
            worldState.unlockedSector.add(sectorName);
        }
    }

    /**
     * Verify if the location is a victory node and check if the player has cleared all their goal.
     */
    private void verifyVictoryConditions(Long locationId) {
        if (locationId - MINDUSTRY_BASE_ID == 998) { //Victory condition for Serpulo met.
           settings.put(SERPULO_VICTORY.value, true);
           RandomizerMessageHandler.printGoalCompleted(SERPULO, RandomizerConstant.SERPULO);
        }
        if (locationId - MINDUSTRY_BASE_ID == 999) { //Victory condition for Erekir met.
            settings.put(EREKIR_VICTORY.value, true);
            RandomizerMessageHandler.printGoalCompleted(EREKIR, RandomizerConstant.EREKIR);
        }

        if (worldState.isVictoryConditionMet()) {
            sendGoalSignal();
        }
    }

    /**
     * Verify if the player is connected and send the goal signal to archipelago.
     */
    public void sendGoalSignal() {
        if (client.isConnected()) {
            client.setGameState(ClientStatus.CLIENT_GOAL);
            RandomizerMessageHandler.printAllGoalCompleted();
            //Goal value turned off to prevent sending a goal signal if the player is playing a new game without reseting their game data.
            settings.put(SERPULO_VICTORY.value, false);
            settings.put(EREKIR_VICTORY.value, false);
        } else {
            RandomizerMessageHandler.printErrorWithReason(RandomizerConstant.NOT_CONNECTED_GOAL_COMPLETED);
        }
    }

    /**
     * Send pending location to Archipelago
     */
    public void sendPendingLocations () {
        boolean succes;
        sendLocalMessage(RandomizerConstant.SENDING_PENDING_CHECK);
        int amountPending = worldState.checkPending.size();
        for (Long locationId : worldState.checkPending) {
            succes = checkPendingLocation(locationId);
            if (succes) {
                amountPending--;
            }
        }
        if (amountPending == 0) { //Every check has been succesfully sent
            worldState.checkPending.clear();
            worldState.saveStates();
            sendLocalMessage(RandomizerConstant.PENDING_CHECK_SENT);
        } else { //Not every check has been sent.
            RandomizerMessageHandler.printErrorWithReason(RandomizerConstant.PENDING_CHECK_REMAINING);
        }
    }

    /**
     * Check if the item is a sector
     * @param id The id of the item
     * @return Return True if the item is a sector
     */
    public boolean isSector(Long id){
        return (id >= MINDUSTRY_BASE_ID + 138 && id <= MINDUSTRY_BASE_ID + 154) ||
                (id >= MINDUSTRY_BASE_ID + 312 && id <= MINDUSTRY_BASE_ID + 327);
    }

    /**
     * Return UnlockableContent matching the itemId.
     * @param itemId The itemId of the item.
     * @return The UnlockableContent matching the itemId, or null if no match.
     */
    public UnlockableContent itemIdToUnlockableContent(Long itemId) {
        UnlockableContent content = null;
        if (worldState.isMindustryAPItem(itemId)) {
            if (worldState.isProgressive(itemId)) {
                for (ProgressiveItem item : worldState.progressiveItems) {
                    if (item.id.equals(itemId) && !item.allReceived) {
                        content = item.items.get(item.amountItemReceived);
                    }
                }
            } else {
                content = worldState.items.get(itemId);
            }
        }
        return content;
    }

    /**
     * Send a death link signal to Archipelago.
     * @param source The source of the signal.
     * @param cause The cause of death.
     */
    public void sendDeathLink(String source, String cause){
        if (client.isConnected() && worldState.options.getDeathLink() && !worldState.deathLinkDying) {
            DeathLink.SendDeathLink(source, cause);
        }
    }

    /**
     * Initialize the randomizer
     */
    public void initialize() {
        if (worldState.options != null) {
            applyOptions();
        }
    }

    /**
     * Send a message locally
     * @param message The message to be sent.
     */
    public void sendLocalMessage (String message) {
        if (ui.chatfrag != null) {
            ui.chatfrag.addLocalMessage(new APMessage(message));
        }
    }

    /**
     * Send a message locally
     * @param message message to be sent.
     */
    public void sendLocalMessage (APMessage message) {
        if (ui.chatfrag != null) {
            ui.chatfrag.addLocalMessage(message);
        }
    }

    /**
     * Send a message to Archipelago if connected.
     * @param message The message to be sent.
     */
    public void sendArchipelagoMessage (String message) {
        if (!message.isEmpty()) {
            client.sendChatMessage(message);
        }
    }

    /**
     * Verify if the player is allowed for a free launch.
     * @param sector The sector to launch to
     * @return Return True if the player can launch for free.
     */
    public boolean allowFreeLaunch(Sector sector) {
        boolean allow = false;
        switch (worldState.options.getCampaign()) {
            case SERPULO:
                allow = serpuloFreeLaunchTarget(sector);
                break;
            case EREKIR:
                allow = erekirFreeLaunchTarget(sector);
                break;
            case ALL:
                if (serpuloFreeLaunchTarget(sector) || erekirFreeLaunchTarget(sector)) {
                    allow = true;
                }
                break;
            default:
                break;
        }
        return allow;
    }

    /**
     * Verify if the sector is a valid sector for a free launch on Erekir
     * @param sector The sector to verify.
     * @return Return true if the sector is valid.
     */
    public boolean erekirFreeLaunchTarget(Sector sector) {
        boolean allow = false;
        if (settings.getBool(FREE_LAUNCH_EREKIR.value) && sector.planet.name.equals(RandomizerConstant.EREKIR.toLowerCase())) {
            if (sector.id == RandomizerConstant.AEGIS_ID) { //88 -> Aegis
                allow = true;
            }
        }
        return allow;
    }

    /**
     * Verify if the sector is a valid sector for a free launch on Serpulo
     * @param sector The sector to verify.
     * @return Return true if the sector is valid.
     */
    public boolean serpuloFreeLaunchTarget(Sector sector) {
        boolean allow = false;
        if (settings.getBool(FREE_LAUNCH_SERPULO.value) && sector.planet.name.equals(RandomizerConstant.SERPULO.toLowerCase())) {
            if (sector.id == RandomizerConstant.FROZEN_FOREST_ID) { //86 -> frozen forest
                allow = true;
            }
        }
        return allow;
    }

    /**
     * Reset local data related to the randomizer.
     */
    public void reset() {
        worldState.resetWorldState();
    }


    public Randomizer(){
        this.hasConnectedPreviously = false;
        if (settings != null && settings.getBool(HAS_CONNECTED.value)) {
            this.hasConnectedPreviously = true;
        }
        this.worldState = new WorldState();
        this.client = new APClient();
        if (hasConnectedPreviously) {
            initialize();
        }
    }


    /**
     * Check a location that was pending.
     * @param id The Id of the location
     * @return Return if the operation was a success.
     */
    private boolean checkPendingLocation (Long id) {
        return client.checkLocation(id);
    }

    /**
     * Apply options.
     */
    private void applyOptions() {
        MindustryOptions options = worldState.options;
        if (options.getOptionsFilled()) {
            worldState.items.clear();

            if (options.getFasterProduction()) {
                MindustryOptions.applyFasterProduction(options.getCampaign());
            }
            if (options.getFasterUnitProduction()) {
                MindustryOptions.applyFasterUnitProduction(options.getCampaign());
            }
            if (options.getFasterConveyor()) {
                MindustryOptions.applyFasterConveyor(options.getCampaign());
            }
            if (options.getLogisticDistribution() == LogisticsDistribution.STARTER) {
                MindustryOptions.applyStarterLogistics(options.getCampaign());
            }
            if (options.getResearchDiscount()) {
                startResearchDiscountEventListener();
            }
            switch (options.getCampaign()) {
                case SERPULO:
                    worldState.initializeSerpuloItems();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockSerpuloTutorialItems();
                        worldState.complexLocations.serpuloFrozenForest.quietUnlock();
                    }
                    break;
                case EREKIR:
                    worldState.initializeErekirItems();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockErekirTutorialItems();
                        worldState.complexLocations.erekirAegis.quietUnlock();
                    }
                    break;
                case ALL:
                    worldState.initializeAllItems();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockSerpuloTutorialItems();
                        worldState.complexLocations.serpuloFrozenForest.quietUnlock();
                        MindustryOptions.unlockErekirTutorialItems();
                        worldState.complexLocations.erekirAegis.quietUnlock();
                    }
                    break;
                default:
                    throw new RuntimeException(RandomizerConstant.INVALID_CAMPAIGN_TYPE);
            }
        } else {
            RandomizerMessageHandler.printErrorWithReason(RandomizerConstant.OPTION_NOT_FILLED);
        }
    }

    /**
     * Process an event that the player received that is not a research.
     * @param eventId Event Id received from the client.
     */
    public void processEvent(long eventId) {
        if (eventId >= MINDUSTRY_BASE_ID + 701 && eventId < MINDUSTRY_BASE_ID + 849) { //Fillers (excluding "Nothing"(id=700))
            processFillerEvent(eventId);
        }
        else if (eventId >= MINDUSTRY_BASE_ID + 850 && eventId < MINDUSTRY_BASE_ID + 999) {
            processTrapEvent(eventId);
        }
    }

    /**
     * Opens a dialog informing the player that they need to restart the game to apply options.
     */
    public void updateForceExit() {
        APApplyOptionsDialog dialog = new APApplyOptionsDialog();
        dialog.show();
    }

    /**
     * Validate that the sector has been unlocked by the player.
     * @param sector The sector to validate
     */
    public boolean isSectorUnlocked(SectorPreset sector) {
        boolean unlocked = false;

        if (worldState.unlockedSector.contains(sector.name)) {
            unlocked = true;
        }

        return unlocked;
    }

    /**
     * Process a filler item event
     * @param eventId The id of the event
     */
    private void processFillerEvent(long eventId) {
        if (eventId == FillerTrapIdsConstant.CONSTRUCTION_SPEED_BUFF) {
            worldState.constructionSpeedBuffPercentage += 10;
            worldState.constructionSpeedBuffCached = 1f + (worldState.constructionSpeedBuffPercentage / 100f);
        } else if (eventId == FillerTrapIdsConstant.RESEARCH_DISCOUNT_BUFF) {
            int buffValue = worldState.getResearchDiscountBuffPercentage();
            buffValue += 5;
            worldState.setResearchDiscountBuffPercentage(buffValue);
        }
    }

    /**
     * Process a trap item event
     * @param eventId The id of the event
     */
    private void processTrapEvent(long eventId) {
        if (eventId == FillerTrapIdsConstant.FACTORY_MALFUNCTION) {
            launchFactoryMalfunctionEvent();
        } else if (eventId == FillerTrapIdsConstant.LAUNCH_WAVE) {
            launchWaveEvent();
        }
    }

    private List<Block> getProtectedBlockList() {
        List<Block> protectedBlock = new ArrayList<>();
        protectedBlock.add(Blocks.coreShard);
        protectedBlock.add(Blocks.coreFoundation);
        protectedBlock.add(Blocks.coreNucleus);
        protectedBlock.add(Blocks.coreBastion);
        protectedBlock.add(Blocks.coreCitadel);
        protectedBlock.add(Blocks.coreAcropolis);
        protectedBlock.add(Blocks.worldProcessor);
        return protectedBlock;
    }

    /**
     * Start a wave if the player is playing a sector with waves rules
     */
    private void launchWaveEvent() {
        if (Vars.state.map != Vars.emptyMap && Vars.state.rules.waves) {
            Vars.logic.runWave();
        }
    }

    /**
     * Destroy a player's building if they are playing a map.
     */
    private void launchFactoryMalfunctionEvent() {
        if (Vars.state.map != Vars.emptyMap) {
            int playerBuildingAmount = state.rules.defaultTeam.data().buildings.size;
            int amountOfBuildingToDestroy = playerBuildingAmount / 95;
            if (amountOfBuildingToDestroy == 0) {
                amountOfBuildingToDestroy = 1;
            }
            List<Block> protectedBlocks = getProtectedBlockList();
            int min = 0;
            int max = playerBuildingAmount - 1;
            for (int i = 0; i < amountOfBuildingToDestroy; i++) {
                int index = rand.nextInt((max - min) + 1) + min;
                Building candidateBuilding = Vars.state.rules.defaultTeam.data().buildings.get(index);
                int retry = 0;
                while (protectedBlocks.contains(candidateBuilding.block) && retry < 15) {
                    index = rand.nextInt((max - min) + 1) + min;
                    candidateBuilding = Vars.state.rules.defaultTeam.data().buildings.get(index);
                    retry++;
                }
                Vars.state.rules.defaultTeam.data().buildings.get(index).kill();
            }
        }
    }

    private void startResearchDiscountEventListener() {
        Events.run(EventType.Trigger.update, () -> {
            int researchBuffValue = worldState.getResearchDiscountBuffPercentage();
            if (researchBuffValue != worldState.lastResearchDiscountBuffPercentage) {
                applyDiscount();
                worldState.lastResearchDiscountBuffPercentage = researchBuffValue;

                if (ui.research.isShown()) {
                    ui.research.view.rebuildAll();
                }
            }
        });
    }

    private void applyDiscount() {
        float discountFactor = worldState.getBuffMultiplier(worldState.getResearchDiscountBuffPercentage());

        Vars.content.each(content -> {
            if (content instanceof UnlockableContent u && u.techNode != null) {
                ItemStack[] base = u.techNode.originalRequirements;

                for (int i = 0; i < u.techNode.requirements.length; i++) {
                    int discountedAmount = Math.round(base[i].amount * (1f - discountFactor));
                    u.techNode.requirements[i].amount = Math.max(base[i].amount > 0 ? 1 : 0, discountedAmount);
                }
            }
        });
    }

    private void processProgressiveItem(long id) {
        for (ProgressiveItem item : worldState.progressiveItems) {
            if (item.id.equals(id) && !item.allReceived) {
                item.amountItemReceived++;
                if (item.amountItemReceived == item.itemAmount) {
                    item.allReceived = true;
                }
            }
        }
    }
}
