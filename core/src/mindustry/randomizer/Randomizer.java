package mindustry.randomizer;

import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;
import static arc.Core.settings;

import dev.koifysh.archipelago.ClientStatus;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import dev.koifysh.archipelago.helper.DeathLink;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.client.APClient;
import mindustry.randomizer.ui.APApplyOptionsDialog;
import mindustry.randomizer.ui.APChat.APMessage;
import mindustry.randomizer.utils.EmptyFillerText;
import mindustry.type.Sector;

import static mindustry.randomizer.enums.SettingStrings.*;


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
    public void unlock(Long id){
        UnlockableContent content = itemIdToUnlockableContent(id);
        if (content != null) {
            content.quietUnlock();
            if (worldState.isProgressive(id)) { //Progressive is being looked twice?
                for (ProgressiveItem item : worldState.progressiveItems) {
                    if (item.id.equals(id) && !item.allReceived) {
                        item.amountItemReceived++;
                        if (item.amountItemReceived == item.itemAmount) {
                            item.allReceived = true;
                        }
                    }
                }
            }
        } else {
            //DEBUG
            sendLocalMessage("ERROR: Content that was null was called to unlock");
        }

    }

    /**
     * Forward the check to Archipelago.
     * @param locationId The id of the location
     */
    public void checkLocation(Long locationId){
        verifyVictoryConditions(locationId);
        boolean success = false;
        if (client.isConnected()) {
            //Try to send check to archipelago
            success = client.checkLocation(locationId);
        }
        if (!client.isConnected() || !success) {
            //Check could not be send, added to check pending list.
            worldState.addCheck(worldState.checkPending, locationId);
            sendLocalMessage("ERROR: You are not connected, pending checks will be sent when " +
                    "reconnected");
        }
        //Add location to checked list and save world state.
        worldState.addCheck(worldState.locationsChecked, locationId);
        worldState.saveStates();
    }

    /**
     * Verify if the location is a victory node and check if the player has cleared all their goal.
     */
    private void verifyVictoryConditions(Long locationId) {
        if (locationId - MINDUSTRY_BASE_ID == 998) { //Victory condition for Serpulo met.
           settings.put(SERPULO_VICTORY.value, true);
           sendLocalMessage("Serpulo goal has been completed!");
        }
        if (locationId - MINDUSTRY_BASE_ID == 999) { //Victory condition for Erekir met.
            settings.put(EREKIR_VICTORY.value, true);
            sendLocalMessage("Erekir goal has been completed!");
        }

        if (worldState.isVictoryConditionMet()) {
            client.setGameState(ClientStatus.CLIENT_GOAL);
            sendLocalMessage("All goals have been met!");
        }
    }

    /**
     * Send pending location to Archipelago
     */
    public void sendPendingLocations () {
        boolean succes;
        sendLocalMessage("Reconnected, sending pending check...");
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
            sendLocalMessage("All pending check has been sent!");
        } else { //Not every check has been sent.
            sendLocalMessage("ERROR: Pending check remaining. You can try to send the checks again by " +
                    "reconnecting to Archipelago.");
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

    @Deprecated
    public void sendLocalMessageItemSendEvent(PrintJSONEvent event){
        Long itemId = event.item.itemID;
        String senderName = client.getPlayerName(event.item.playerID);
        String senderGameName = client.getPlayerGame(event.item.playerID);
        String receiverGameName = client.getPlayerGame(event.apPrint.receiving);
        String itemName = client.getItemName(itemId, receiverGameName);
        String locationName = client.getLocationName(event.item.locationID, senderGameName);

        if (event.item.playerID == event.apPrint.receiving) { //Item is being received by the sender
            sendLocalMessage(senderName + " found their " + itemName + "(" + locationName + ")");
        } else { //The item is being received by someone else than the sender
            String receiverName = client.getPlayerName(event.apPrint.receiving);
            sendLocalMessage(senderName + " sent " + receiverName + " their " + itemName + "(" +
                    locationName + ")");
        }
    }

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
        if (Vars.ui.chatfrag != null) {
            Vars.ui.chatfrag.addLocalMessage(new APMessage(message));
        }
    }

    public void sendLocalMessage (APMessage message) {
        if (Vars.ui.chatfrag != null) {
            Vars.ui.chatfrag.addLocalMessage(message);
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
            case 0: //Serpulo
                allow = serpuloFreeLaunchTarget(sector);
                break;
            case 1: //Erekir
                allow = erekirFreeLaunchTarget(sector);
                break;
            case 2: //All
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
        if (settings.getBool(FREE_LAUNCH_EREKIR.value) && sector.planet.name.equals("erekir")) {
            if (sector.id == 88) { //86 -> Aegis
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
        if (settings.getBool(FREE_LAUNCH_SERPULO.value)) {
            if (sector.id == 86 && sector.planet.name.equals("serpulo")) { //86 -> frozen forest
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
            if (options.getRandomizeCoreUnitsWeapon()) {
                MindustryOptions.randomizeCoreUnitsWeapon(options.getCampaign());
            }
            if (options.getLogisticDistribution() == 2) { //Starter logistics
                MindustryOptions.applyStarterLogistics(options.getCampaign());
            }
            switch (options.getCampaign()) {
                case 0: //Serpulo
                    worldState.initializeSerpuloItems();
                    worldState.initializeSerpuloFillers();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockSerpuloTutorialItems();
                    }
                    break;
                case 1: //Erekir
                    worldState.initializeErekirItems();
                    worldState.initializeErekirFillers();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockErekirTutorialItems();
                    }
                    break;
                case 2: //All
                    worldState.initializeAllItems();
                    worldState.initializeAllFillers();
                    if (options.getTutorialSkip()) {
                        MindustryOptions.unlockSerpuloTutorialItems();
                        MindustryOptions.unlockErekirTutorialItems();
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid CampaignType");
            }
        } else {
            //DEBUG
            sendLocalMessage("ERROR: Options was not filled, cannot apply options");
        }
    }

    /**
     * Process an event that the player received that is not a research.
     * @param event Event received from the client.
     */
    public void processEvent(ReceiveItemEvent event) {
        if (event.getItemID() == MINDUSTRY_BASE_ID + 700) { //A fistful of nothing...
            sendLocalMessage(EmptyFillerText.getRandomText());
        }
    }

    public void updateForceExit() {
        //open confirmation dialog to warn the user of the required game restart.
        APApplyOptionsDialog dialog = new APApplyOptionsDialog("Apply randomizer options");
        dialog.show();
    }
}
