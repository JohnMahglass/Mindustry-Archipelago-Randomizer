package mindustry.randomizer;

import mindustry.world.Block;

/**
 * ApLocation
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-20
 */
public class ApLocation extends Block {

    private void initialiseApAttributes(String originalNodeName, int locationId){
        setLocationId(locationId);
        setOriginalNodeName(originalNodeName);
    }
    public ApLocation(String name, String originalNodeName, int locationId) {
        super(name);
        initialiseApAttributes(originalNodeName, locationId);
    }
}
