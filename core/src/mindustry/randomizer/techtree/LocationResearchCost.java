package mindustry.randomizer.techtree;

import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.type.ItemStack;

/**
 * Research cost for an AP node.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-21
 */
public abstract class LocationResearchCost {

    public static ItemStack[] reqCopper(int copperAmount){
        ItemStack[] totalResearchCost = new ItemStack[1];
        totalResearchCost[0] = new ItemStack(Items.copper, copperAmount);
        return totalResearchCost;
    }

    /**
     * Require 1 item as research cost
     * @param item The Item required
     * @param amount The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req1Item (Item item, int amount){
        ItemStack[] totalResearchCost = new ItemStack[1];

        totalResearchCost[0] = new ItemStack(item, amount);

        return totalResearchCost;
    }

    /**
     * Require 2 items as research cost
     * @param item1 The item required
     * @param amount1 The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req2Item (Item item1, int amount1, Item item2, int amount2){
        ItemStack[] totalResearchCost = new ItemStack[2];

        totalResearchCost[0] = new ItemStack(item1, amount1);
        totalResearchCost[1] = new ItemStack(item2, amount2);

        return totalResearchCost;
    }

    /**
     * Require 3 items as research cost
     * @param item1 The item required
     * @param amount1 The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req3Item (Item item1, int amount1, Item item2, int amount2,
                                        Item item3, int amount3){
        ItemStack[] totalResearchCost = new ItemStack[3];

        totalResearchCost[0] = new ItemStack(item1, amount1);
        totalResearchCost[1] = new ItemStack(item2, amount2);
        totalResearchCost[2] = new ItemStack(item3, amount3);

        return totalResearchCost;
    }

    /**
     * Require 4 items as research cost
     * @param item1 The item required
     * @param amount1 The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req4Item (Item item1, int amount1, Item item2, int amount2,
                                        Item item3, int amount3, Item item4, int amount4){
        ItemStack[] totalResearchCost = new ItemStack[4];

        totalResearchCost[0] = new ItemStack(item1, amount1);
        totalResearchCost[1] = new ItemStack(item2, amount2);
        totalResearchCost[2] = new ItemStack(item3, amount3);
        totalResearchCost[3] = new ItemStack(item4, amount4);

        return totalResearchCost;
    }

    /**
     * Require 5 items as research cost
     * @param item1 The item required
     * @param amount1 The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req5Item (Item item1, int amount1, Item item2, int amount2,
                                        Item item3, int amount3, Item item4, int amount4,
                                        Item item5, int amount5){
        ItemStack[] totalResearchCost = new ItemStack[5];

        totalResearchCost[0] = new ItemStack(item1, amount1);
        totalResearchCost[1] = new ItemStack(item2, amount2);
        totalResearchCost[2] = new ItemStack(item3, amount3);
        totalResearchCost[3] = new ItemStack(item4, amount4);
        totalResearchCost[4] = new ItemStack(item5, amount5);

        return totalResearchCost;
    }

    /**
     * Require 6 items as research cost
     * @param item1 The item required
     * @param amount1 The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    public static ItemStack[] req6Item (Item item1, int amount1, Item item2, int amount2,
                                        Item item3, int amount3, Item item4, int amount4,
                                        Item item5, int amount5, Item item6, int amount6){
        ItemStack[] totalResearchCost = new ItemStack[6];

        totalResearchCost[0] = new ItemStack(item1, amount1);
        totalResearchCost[1] = new ItemStack(item2, amount2);
        totalResearchCost[2] = new ItemStack(item3, amount3);
        totalResearchCost[3] = new ItemStack(item4, amount4);
        totalResearchCost[4] = new ItemStack(item5, amount5);
        totalResearchCost[5] = new ItemStack(item6, amount6);

        return totalResearchCost;
    }

}
