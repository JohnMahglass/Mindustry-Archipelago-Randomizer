package mindustry.randomizer;

import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

import dev.koifysh.archipelago.ClientStatus;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.client.APClient;


/**
 * Randomizer for Archipelago multiworld randomizer.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-12
 */
public class Randomizer {

    public APClient randomizerClient;

    /**
     * Represent the state the APWorld is in.
     */
    public WorldState worldState;

    /**
     * Unlock a UnlockableContent.
     */
    public void unlock(Long id){
        UnlockableContent content = itemIdToUnlockableContent(id);
        content.unlock();
        //DEBUG
        //sendLocalMessage(content.localizedName + " Researched");
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
     * Forward the check to Archipelago.
     * @param locationId The id of the location
     */
    public void checkLocation(Long locationId, String locationName){
        if (locationId - MINDUSTRY_BASE_ID == -1) { //VICTORY CONDITION MET
            //Send victory event to AP
            randomizerClient.setGameState(ClientStatus.CLIENT_GOAL);
            return;
        }
        boolean success = false;
        if (randomizerClient.isConnected()) {
            //Try to send check to archipelago
            success = randomizerClient.checkLocation(locationId);
        }
        if (!randomizerClient.isConnected() || !success) {
            //Check could not be send, added to check pending list.
            worldState.addCheck(worldState.checkPending, locationId);
            sendLocalMessage("ERROR: You are not connected, pending checks will be sent when " +
                    "reconnected");
        }
        //Add location to checked list and save world state.
        worldState.addCheck(worldState.locationsChecked, locationId);
        worldState.saveStates();
    }

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

    private boolean checkPendingLocation (Long id) {
        return randomizerClient.checkLocation(id);
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
        return (id >= MINDUSTRY_BASE_ID + 166 && id <= MINDUSTRY_BASE_ID + 182);
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
                for (ProgressiveItem item : worldState.progressiveItems) {
                    if (item.id.equals(itemId) && !item.allReceived) {
                        content = item.items.get(item.amountItemReceived);
                        item.amountItemReceived++;
                        if (item.amountItemReceived == item.itemAmount) {
                            item.allReceived = true;
                        }
                    }
                }
            } else {
                content = worldState.items.get(itemId);
            }
        }
        return content;
    }

    /**
     * Initialize the randomizer's list of item depending on the selected campaign
     */
    public void initialize() {
        worldState.initialize();
        switch (worldState.options.getCampaignChoice()) {
            case SERPULO:
                worldState.initializeSerpuloItems();
                break;
            case EREKIR:
                worldState.initializeErekirItems();
                break;
            case ALL:
                worldState.initializeAllItems();
                break;
            default:
                throw new RuntimeException("Invalid CampaignType");
        }
    }

    /**
     * Constructor for Randomizer
     */
    public Randomizer(){
        this.worldState = new WorldState();
        initialize();
        this.randomizerClient = new APClient();
        randomizerClient.connectRandomizer();
    }

    public void sendLocalMessage (String message) {
        Vars.ui.chatfrag.addLocalMessage(message);
    }


}
