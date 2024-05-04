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

    /** Seed for randomization, this could be generated when the archipelago world is made? */
    public long SEED = 1234567891;

    /**
     * Contain all UnlockableContent that can be researched through the planet's tech tree
     */
    public Seq<UnlockableContent> planetTechUnlockableContent;

    /**
     * Contain all UnlockableContent that are required to clear the tutorial of the planet
     */
    public Seq<UnlockableContent> starterTechUnlockableContent;

    /**
     * Contain UnlockableContent classified as tier 1 for randomization
     */
    public Seq<UnlockableContent> tier1TechUnlockableContent;
    /**
     * Contain UnlockableContent classified as tier 2 for randomization
     */
    public Seq<UnlockableContent> tier2TechUnlockableContent;
    /**
     * Contain UnlockableContent classified as tier 3 for randomization
     */
    public Seq<UnlockableContent> tier3TechUnlockableContent;

    /**
     * Randomize the UnlockableContent for randomizePlanetTechUnlockableContent
     */
    public void randomizePlanetTechUnlockableContent() {
        Random random = new Random(SEED);

    }

    /**
     * Load the tech tree into the planet
     */
    public abstract void load();

    public abstract void loadTechUnlockableContent();

    public abstract void loadStarterTechUnlockableContent();

    public TechTreeRandomizer() {
        planetTechUnlockableContent = new Seq<>();
        starterTechUnlockableContent = new Seq<>();
        tier1TechUnlockableContent = new Seq<>();
        tier2TechUnlockableContent = new Seq<>();
        tier3TechUnlockableContent = new Seq<>();
        loadStarterTechUnlockableContent();
        loadTechUnlockableContent();
        randomizePlanetTechUnlockableContent();
    }

}
