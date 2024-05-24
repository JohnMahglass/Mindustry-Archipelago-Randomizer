package mindustry.randomizer;

import mindustry.world.Block;

/**
 * Node acting as a location for AP
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-20
 */
public class ApLocation extends Block {


    private void initialiseApAttributes(String originalNodeName, int locationId){
        setLocationId(locationId);
        setOriginalNodeName(originalNodeName);
    }

    private void initialiseApAttributes(String originalNodeName, int locationId, int itemId){
        setLocationId(locationId);
        setOriginalNodeName(originalNodeName);
        setItemId(itemId);
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param originalNodeName The name the node used to have.
     * @param locationId The id of the node.
     */
    public ApLocation(String name, String originalNodeName, int locationId) {
        super(name);
        initialiseApAttributes(originalNodeName, locationId);
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
        initialiseApAttributes(originalNodeName, locationId, itemId);
    }
}
