package mindustry.randomizer.techtree;

import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.type.ItemStack;

import static mindustry.Vars.randomizer;

/**
 * Research cost for an AP node.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-21
 */
public abstract class LocationResearchCost {

    /**
     * Require copper has a research cost. This method is intended as a quick way to put a
     * requirement on a node and probably should not be used seriously.
     * @param copperAmount Amount of copper required.
     * @return Return an ItemStack containing the required amount of copper.
     */
    public static ItemStack[] reqCopper(int copperAmount){
        ItemStack[] totalResearchCost = new ItemStack[1];
        totalResearchCost[0] = new ItemStack(Items.copper, copperAmount);
        return totalResearchCost;
    }

    /**
     * Require beryllium has a research cost. This method is intended as a quick way to put a
     * requirement on a node and probably should not be used seriously.
     * @param berylliumAmount Amount of beryllium required.
     * @return Return an ItemStack containing the required amount of beryllium.
     */
    public static ItemStack[] reqBeryllium(int berylliumAmount){
        ItemStack[] totalResearchCost = new ItemStack[1];
        totalResearchCost[0] = new ItemStack(Items.beryllium, berylliumAmount);
        return totalResearchCost;
    }

    /**
     * Require 1 item as research cost
     * @param item The Item required
     * @param amount The amount of item required
     * @return Return the ItemStack[] of required Item for the research.
     */
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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

    /**
     * Requirement for unlocking the Victory node.
     * @return The requirement for the Victory node
     */
    public static ItemStack[] reqResourcesVictoryErekir() {
        ItemStack[] totalResearchCost = new ItemStack[9];

        //Possible range = 100 - 100 000
        int amountRequired = 2000; //Default
        if (randomizer.worldState.options.getOptionsFilled()) {
            amountRequired = randomizer.worldState.options.getAmountOfResourcesRequired();
        }

        totalResearchCost[0] = new ItemStack(Items.beryllium , amountRequired);
        totalResearchCost[1] = new ItemStack(Items.silicon , amountRequired);
        totalResearchCost[2] = new ItemStack(Items.oxide , amountRequired);
        totalResearchCost[3] = new ItemStack(Items.graphite , amountRequired);
        totalResearchCost[4] = new ItemStack(Items.tungsten , amountRequired);
        totalResearchCost[5] = new ItemStack(Items.thorium , amountRequired);
        totalResearchCost[6] = new ItemStack(Items.carbide , amountRequired);
        totalResearchCost[7] = new ItemStack(Items.surgeAlloy , amountRequired);
        totalResearchCost[8] = new ItemStack(Items.phaseFabric , amountRequired);

        return totalResearchCost;
    }

    /**
     * Requirement for unlocking the Victory node.
     * @return The requirement for the Victory node
     */
    public static ItemStack[] reqResourcesVictorySerpulo() {
        ItemStack[] totalResearchCost = new ItemStack[16];

        //Possible range = 100 - 100 000
        int amountRequired = 2000; //Default
        if (randomizer.worldState.options.getOptionsFilled()) {
            amountRequired = randomizer.worldState.options.getAmountOfResourcesRequired();
        }

        totalResearchCost[0] = new ItemStack(Items.copper, amountRequired);
        totalResearchCost[1] = new ItemStack(Items.lead, amountRequired);
        totalResearchCost[2] = new ItemStack(Items.coal, amountRequired);
        totalResearchCost[3] = new ItemStack(Items.scrap, amountRequired);
        totalResearchCost[4] = new ItemStack(Items.sand, amountRequired);
        totalResearchCost[5] = new ItemStack(Items.graphite, amountRequired);
        totalResearchCost[6] = new ItemStack(Items.titanium, amountRequired);
        totalResearchCost[7] = new ItemStack(Items.silicon, amountRequired);
        totalResearchCost[8] = new ItemStack(Items.metaglass, amountRequired);
        totalResearchCost[9] = new ItemStack(Items.sporePod, amountRequired);
        totalResearchCost[10] = new ItemStack(Items.pyratite, amountRequired);
        totalResearchCost[11] = new ItemStack(Items.blastCompound, amountRequired);
        totalResearchCost[12] = new ItemStack(Items.thorium, amountRequired);
        totalResearchCost[13] = new ItemStack(Items.plastanium, amountRequired);
        totalResearchCost[14] = new ItemStack(Items.phaseFabric, amountRequired);
        totalResearchCost[15] = new ItemStack(Items.surgeAlloy, amountRequired);

        return totalResearchCost;
    }

}
