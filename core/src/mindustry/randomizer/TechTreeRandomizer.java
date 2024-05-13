package mindustry.randomizer;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;

import java.util.Random;

/**
 * Class for the initialisation and randomization of a planet's tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public abstract class TechTreeRandomizer {

    /**
     * Contain all UnlockableContent that are required to clear the tutorial of the planet
     */
    public Seq<UnlockableContent> starterUnlockableContent;

    /**
     * Contain all UnlockableContent from the planet's tech tree
     */
    public Seq<UnlockableContent> planetUnlockableContent;

    /**
     * Randomize the UnlockableContent for the planet's tech tree.
     */
    public void randomizePlanetUnlockableContent() {
    }

    /**
     * Load the tech tree into the planet
     */
    public abstract void load();

    /*
     * Load all unlockable content into a list
     */
    public abstract void loadUnlockableContent();

    /*
     * Load all unlockable content that are required to skip the tutorial into a list
     */
    public abstract void loadStarterUnlockableContent();

    public TechTreeRandomizer() {
        loadUnlockableContent();
        loadStarterUnlockableContent();
        //randomizePlanetUnlockableContent();
    }

}
