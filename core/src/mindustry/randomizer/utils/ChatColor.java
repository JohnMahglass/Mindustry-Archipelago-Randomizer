package mindustry.randomizer.utils;

import mindustry.randomizer.enums.ApChatColors;

/**
 * ChatColor
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-24
 */
public abstract class ChatColor {

    /**
     * Apply color to a text.
     * @param color The color to be applied
     * @param text The test to be colored
     * @return The colored text
     */
    public static String applyColor(ApChatColors color, String text) {
        return color.value + text + ApChatColors.WHITE.value;
    }
}
