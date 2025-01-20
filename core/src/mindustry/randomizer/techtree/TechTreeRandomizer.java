package mindustry.randomizer.techtree;

import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;

import static mindustry.Vars.randomizer;

/**
 * A planet's randomized tech tree.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
abstract class TechTreeRandomizer {

    /**
     * Load the tech tree into the planet
     */
    public static void load(){
        //Method should be overwritten
    }

    /**
     * Create a location in the form of an UnlockableContent WITH NO research cost.
     * @param name Name of the node.
     * @param content Content originally contained in the node.
     * @param locationId Id of the location.
     * @return Return a location in the form a UnlockableContent.
     */
    protected static ApLocation createApLocation(String name, UnlockableContent content,
                                               Long locationId){
        ApLocation location = new ApLocation(name, content, locationId);
        randomizer.worldState.apLocations.add(location);
        randomizer.worldState.locations.put(locationId, name);
        return location;
    }

    /**
     * Create a location in the form of an UnlockableContent with its research cost.
     * @param name Name of the node.
     * @param content content that was originally contained in the node.
     * @param locationId id of the location.
     * @param locationResearchCost List of Item required to check this location.
     * @return Return a location in the form a UnlockableContent.
     */
    protected static ApLocation createApLocation(String name, UnlockableContent content,
                                               Long locationId,
                                               ItemStack[] locationResearchCost){
        ApLocation apContent = new ApLocation(name, content, locationId);

        apContent.researchCost = locationResearchCost;
        randomizer.worldState.locations.put(locationId, name);
        return apContent;
    }

}
