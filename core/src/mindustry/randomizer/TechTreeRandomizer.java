package mindustry.randomizer;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.world.Block;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for the initialisation and randomization of a planet's tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public abstract class TechTreeRandomizer {

    /**
     * Contain all blocks that can be researched through the planet's tech tree
     */
    public Seq<UnlockableContent> planetTechBlocks;

    /**
     * Contain all blocks that are required to clear the tutorial of the planet
     */
    public Seq<UnlockableContent> starterTechBlocks;

    /**
     * Randomize the blocks for planetTechBlocks
     */
    public void randomizePlanetTechBlocks() {
        //Need to think of the logic for randomization.
    }

    /**
     * Load the tech tree into the planet
     */
    public abstract void load();

    public abstract void loadTechBlocks();

    public TechTreeRandomizer() {
        planetTechBlocks = new Seq<>();
        starterTechBlocks = new Seq<>();
        loadTechBlocks();
        randomizePlanetTechBlocks();
    }
}
