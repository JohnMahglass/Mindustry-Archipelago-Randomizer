package mindustry.randomizer;

import arc.Core;
import mindustry.ctype.ContentType;
import mindustry.world.Block;

import static mindustry.Vars.randomizer;

/**
 * Node acting as a location for AP
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-20
 */
public class ApLocation extends Block {

    /**
     * id of the Archipelago location.
     */
    public Integer locationId;

    /**
     * id of item within the node.
     */
    public Integer itemId;

    /**
     * Original name of the node.
     */
    public String originalNodeName;

    /**
     * Check the location. If the location has no id, add the location to the pending check list.
     */
    @Override
    public void unlock(){
        if(!unlocked()){
            unlocked = true;
            Core.settings.put(name + "-unlocked", true);
            if (itemId != null) {
                randomizer.locationChecked(locationId, itemId);
            } else {
                randomizer.worldState.checkPending.put(locationId, name); //THIS NEEDS TO BE
                // SAVED LOCALLY
            }

        }
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param originalNodeName The name the node used to have.
     * @param locationId The id of the node.
     */
    public ApLocation(String name, String originalNodeName, int locationId) {
        super(name);
        this.locationId = locationId;
        this.originalNodeName = originalNodeName;
        setRandomized(true);
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param originalNodeName The name the node used to have.
     * @param locationId The id of the node.
     * @param itemId The id of the item contained within the node.
     */
    public ApLocation(String name, String originalNodeName, int locationId, int itemId) {
        super(name);
        this.locationId = locationId;
        this.itemId = itemId;
        this.originalNodeName = originalNodeName;
        setRandomized(true);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.block; //Content type irrevelent, this is to prevent an error being raised
    }
}
