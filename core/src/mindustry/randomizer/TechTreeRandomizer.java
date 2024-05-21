package mindustry.randomizer;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;

import java.util.Random;

/**
 * Interface for a planet's randomized tech tree.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public interface TechTreeRandomizer {

    /**
     * Load the tech tree into the planet
     */
    public static void load(){
        //Method should be overwritten
    }

}
