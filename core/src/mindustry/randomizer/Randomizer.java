package mindustry.randomizer;

import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

import dev.koifysh.archipelago.network.server.RoomInfoPacket;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.client.APClient;

import java.net.URISyntaxException;

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
    public void locationChecked(Long locationId){
        if (locationId == -1) { //VICTORY CONDITION MET
            //Send victory event to AP
            return;
        }
        boolean success = false;
        if (randomizerClient.isConnected()) {
            //Try to send check to archipelago
            success = true;
        }
        if (!randomizerClient.isConnected() || !success) {
            worldState.addCheck(worldState.checkPending, locationId);
        }
        worldState.addCheck(worldState.locationsChecked, locationId);
        worldState.saveStates();
    }

    /**
     * Return true if AP item attributes are initialized.
     * within the randomizer.
     * @return If the content is an AP item.
     */
    public boolean isMindustryAPItem(Long itemId){
        boolean isMindustryItem = false;
        if (itemId != null) {
            if (itemId >= MINDUSTRY_BASE_ID && itemId <= MINDUSTRY_BASE_ID + 199) {
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
            if (worldState.items.get(itemId) != null) {
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
                worldState.initializeSerpuloLocations();
                //placeItemsIntoLocations();
                break;
            case EREKIR:
                worldState.initializeErekirItems();
                worldState.initializeErekirLocations();
                break;
            case ALL:
                worldState.initializeAllItems();
                worldState.initializeAllLocations();
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

    private void showItemReceived(String message) {
        //Use unlock popup for item received message
    }

    /**
     * Place item into their location.
     * @param locationId The location's id.
     * @param itemId The item's id.
     */
    private void placeItemsIntoLocations(Long locationId, Long itemId) {
        //Method not implemented
    }

}
