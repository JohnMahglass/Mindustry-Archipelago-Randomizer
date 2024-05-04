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

    public final int SHUFFLE_CYCLE = 100;

    /** Seed for randomization, this could be generated when the archipelago world is made? */
    public long seed;

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
        randomizeSequence(tier1TechUnlockableContent, seed);
        randomizeSequence(tier2TechUnlockableContent, seed);
        randomizeSequence(tier3TechUnlockableContent, seed);
    }

    private void randomizeSequence(Seq sequence, long seed){
        Random random = new Random(seed);
        /**
         * Need to shuffle the content using the SEED so that the same randomization can be
        rebuild each time
         */
        int movingElement = 0;
        int movedElement = 0;
        for (int i = 0; i <= SHUFFLE_CYCLE; i++) {
            movingElement = 0;
            movedElement = 0;
            while (movingElement == movedElement) {
                movingElement = random.nextInt(sequence.size) - 1;
                movedElement = random.nextInt(sequence.size) - 1;
                if (movingElement < 0) {
                    movingElement = 0;
                }
                if (movedElement < 0) {
                    movedElement = 0;
                }
            }
            sequence.swap(movingElement, movedElement);
        }
    }

    /**
     * Load the tech tree into the planet
     */
    public abstract void load();

    public abstract void loadTechUnlockableContent();

    public abstract void loadStarterTechUnlockableContent();

    public TechTreeRandomizer(long seed) {
        this.seed = seed;
        starterTechUnlockableContent = new Seq<>();
        tier1TechUnlockableContent = new Seq<>();
        tier2TechUnlockableContent = new Seq<>();
        tier3TechUnlockableContent = new Seq<>();
        loadStarterTechUnlockableContent();
        loadTechUnlockableContent();
        randomizePlanetTechUnlockableContent();
    }

}
