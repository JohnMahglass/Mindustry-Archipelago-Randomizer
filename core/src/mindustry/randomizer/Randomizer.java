package mindustry.randomizer;

import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;
import static arc.Core.settings;

import dev.koifysh.archipelago.ClientStatus;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.SectorPresets;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.client.APClient;
import mindustry.type.Sector;


/**
 * Randomizer for Archipelago multiworld randomizer.
 *
 * @author John Mahglass
 * @version 0.0.1 2024-05-12
 */
public class Randomizer {

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
            if (worldState.isProgressive(id)) {
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
        if (locationId - MINDUSTRY_BASE_ID == -1) { //VICTORY CONDITION MET, should use location
            // name instead to find the victory condition
            //Send victory event to AP
            client.setGameState(ClientStatus.CLIENT_GOAL);
            return;
        }
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
     * Return true if AP item attributes are initialized.
     * within the randomizer.
     * @return If the content is an AP item.
     */
    public boolean isMindustryAPItem(Long itemId){
        boolean isMindustryItem = false;
        if (itemId != null) {
            if (itemId >= MINDUSTRY_BASE_ID && itemId <= MINDUSTRY_BASE_ID + 171) {
                isMindustryItem = true;
            }
        }
        return isMindustryItem;
    }

    /**
     * Check if the item is a sector
     * @param id The id of the item
     * @return Return True if the item is a sector
     */
    public boolean isSector(Long id){
        return (id >= MINDUSTRY_BASE_ID + 138 && id <= MINDUSTRY_BASE_ID + 154);
    }

    /**
     * Return UnlockableContent matching the itemId.
     * @param itemId The itemId of the item.
     * @return The UnlockableContent matching the itemId, or null if no match.
     */
    public UnlockableContent itemIdToUnlockableContent(Long itemId) {
        UnlockableContent content = null;
        if (isMindustryAPItem(itemId)) {
            if (worldState.isProgressive(itemId)) {
                boolean found = false;
                for (ProgressiveItem item : worldState.progressiveItems) {
                    if (item.id.equals(itemId) && !item.allReceived && !found) {
                        content = item.items.get(item.amountItemReceived);
                        found = true;
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
        switch (worldState.options.getCampaignChoice()) {
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
        if (settings.getBool("APfreeLaunchErekir") && sector.planet.name.equals("erekir")) {
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
        if (settings.getBool("APfreeLaunchSerpulo")) {
            if (sector.id == 86 && sector.planet.name.equals("serpulo")) { //86 -> frozen forest
                allow = true;
            }
        }
        return allow;
    }

    /**
     * Checks whether the plays have received this item.
     * @param id The id of the item to be checked.
     * @return Return True if the player has this item.
     */
    public boolean hasItem(Long id){
        boolean itemReceived = false;

        for (int i = 0; i < worldState.unlockedItems.length; i++) {
            if (id == worldState.unlockedItems[i]) {
                itemReceived = true;
            }
        }
        return itemReceived;
    }

    /**
     * Reset local data related to the randomizer.
     */
    public void reset() {
        worldState.resetWorldState();
    }


    public Randomizer(){
        this.hasConnectedPreviously = false;
        if (settings != null && settings.getBool("APhasConnected")) {
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
     * Unlock Serpulo's tutorial research and unlock Frozen Forest.
     */
    private static void unlockSerpuloTutorialItems() {
        Blocks.conveyor.quietUnlock();
        Blocks.duo.quietUnlock();
        Blocks.scatter.quietUnlock();
        Blocks.mechanicalDrill.quietUnlock();
        Blocks.copperWall.quietUnlock();
        SectorPresets.groundZero.quietUnlock();
        SectorPresets.frozenForest.quietUnlock();
        SectorPresets.frozenForest.alwaysUnlocked = true;
    }

    /**
     * Apply options.
     */
    private void applyOptions() {
        if (worldState.options.getOptionsFilled()) {
            worldState.items.clear();
            switch (worldState.options.getCampaignChoice()) {
                case 0: //Serpulo
                    worldState.initializeSerpuloItems();
                    if (worldState.options.getTutorialSkip()) {
                        unlockSerpuloTutorialItems();
                    }
                    break;
                case 1: //Erekir
                    worldState.initializeErekirItems();
                    if (worldState.options.getTutorialSkip()) {
                        //Unlock Erekir tutorial items
                    }
                    break;
                case 2: //All
                    worldState.initializeAllItems();
                    if (worldState.options.getTutorialSkip()) {
                        unlockSerpuloTutorialItems();
                        //Unlock Erekir tutorial items
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

}
