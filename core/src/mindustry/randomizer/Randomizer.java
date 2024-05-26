package mindustry.randomizer;

import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

import mindustry.ctype.UnlockableContent;

/**
 * Randomizer for Archipelago multiworld randomizer.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-12
 */
public class Randomizer {

    /**
     * Represent the state the APworld is in.
     */
    WorldState worldState;

    /**
     * Unlock a UnlockableContent.
     */
    public void unlock(int id){
        UnlockableContent content = itemIdToUnlockableContent(id);
        worldState.unlockedItems.put(id, content);
        content.randomizerUnlock();
    }

    /**
     * Checks whether the plays have received this item.
     * @param id The id of the item to be checked.
     * @return Return True if the player has this item.
     */
    public boolean hasItem(int id){
        boolean itemReceived = false;
        if (worldState.unlockedItems.get(id) != null) {
            itemReceived = true;
        }
        return itemReceived;
    }

    /**
     * Forward the check to Archipelago, if the item is a Mindustry item, unlock it.
     * @param locationId The id of the location
     * @param itemId The itemId that is contained in the location
     */
    public void locationChecked(int locationId, int itemId){
        if (isMindustryItem(itemId)) { //Item id might not be sent to AP, this needs to be verified
            unlock(itemId); //This if statement might not be needed at all
        }
        //send the check to archipelago
    }

    /**
     * Return true if AP item attributes are initialized.
     * within the randomizer.
     * @return If the content is an AP item.
     */
    public boolean isMindustryItem(Integer itemId){
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
    public boolean isSector(int id){
        return (id >= MINDUSTRY_BASE_ID + 166 && id <= MINDUSTRY_BASE_ID + 182);
    }

    /**
     * Return UnlockableContent matching the itemId.
     * @param itemId The itemId of the item.
     * @return The UnlockableContent matching the itemId, or null if no match.
     */
    public UnlockableContent itemIdToUnlockableContent(Integer itemId) {
        UnlockableContent content = null;
        if (isMindustryItem(itemId)) {
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
    }

    /**
     * Place item into their location
     * @param locationId The location's id.
     * @param itemId The item's id.
     */
    private void placeItemsIntoLocations(int locationId, int itemId) {
        //Method not implemented
    }

}
