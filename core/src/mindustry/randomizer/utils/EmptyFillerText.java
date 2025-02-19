package mindustry.randomizer.utils;

import arc.struct.Seq;

/**
 * EmptyFillerStrings
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-01
 */
public class EmptyFillerText {

    /**
     * Text for when a player receive the "A fistful of nothing..." item.
     * @return A random text from a list.
     */
    public static String getRandomText(){
        Seq<String> texts = new Seq<>();
        texts.add("A fistful of nothing... It fills you with determination.");
        texts.add("Unfortunately, you received a fistful of nothing...");
        texts.add("This could have been an item, but it was nothing...");
        texts.add("I hope you were not to excited about this one.");
        return texts.random();
    }
}
