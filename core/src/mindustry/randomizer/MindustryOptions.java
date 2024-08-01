package mindustry.randomizer;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.SectorPresets;
import mindustry.randomizer.client.SlotData;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.production.Separator;

import static arc.Core.settings;
import static mindustry.Vars.randomizer;

/**
 * Class containing the options for the generated Mindustry game.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class MindustryOptions {

    /**
     * If the options has been filled and are not default.
     */
    private boolean optionsFilled;

    /**
     * If the tutorial is to be skipped. Also unlock tutorial-related tech.
     */
    private boolean tutorialSkip;

    /**
     * The selected campaign.
     */
    private int campaignChoice;

    /**
     * If death link is activated
     */
    private boolean deathLink;

    /**
     * Disable invasion.
     */
    private boolean disableInvasions;

    /**
     * Increase the rate at which resource are harvested and increase production output.
     */
    private boolean fasterProduction;

    public boolean getTutorialSkip() {
        return this.tutorialSkip;
    }

    public boolean getDisableInvasion() {
        return this.disableInvasions;
    }

    public boolean getFasterProduction() {
        return this.fasterProduction;
    }

    public boolean getDeathLink() {
        return this.deathLink;
    }

    public int getCampaignChoice() {
        return this.campaignChoice;
    }

    public boolean getOptionsFilled() {
        boolean filled;
        if (this.optionsFilled || randomizer.hasConnectedPreviously) {
            filled = true;
        } else {
            filled = false;
        }
        return filled;
    }


    /**
     * Fill the options with the options received from AP
     * @param slotData slot data containing the player's options.
     */
    public void fillOptions (SlotData slotData) {
        if (slotData != null) {
            this.deathLink = slotData.getDeathlink();
            this.tutorialSkip = slotData.getTutorialSkip();
            this.disableInvasions = slotData.getDisableInvasions();
            this.fasterProduction = slotData.getFasterProduction();
            this.campaignChoice = slotData.getCampaignChoice();

            this.optionsFilled = true;
            saveOptions();
            settings.put("APhasConnected", true);
            if (tutorialSkip) {
                if (campaignChoice == 0) {
                    settings.put("APfreeLaunchSerpulo", true);
                } else if (campaignChoice == 1) {
                    settings.put("APfreeLaunchErekir", true);
                } else if (campaignChoice == 2) {
                    settings.put("APfreeLaunchSerpulo", true);
                    settings.put("APfreeLaunchErekir", true);
                }
            }
            if (fasterProduction) {
                settings.put("APfasterProduction", true);
            }
            if (disableInvasions) {
                settings.put("APdisableInvasion", true);
            }
            if (deathLink) {
                settings.put("APdeathLink", true);
            }
        }
    }


    /**
     * Constructor for MindustryOptions, if the user has never connected to a game, load default
     * options in the meantime.
     */
    public MindustryOptions() {
        //Do not use randomizer.hasConnectedPreviously since Vars.randomizer is null
        if (settings != null && settings.getBool("APhasConnected")) {
            loadOptions();
        } else {
            this.optionsFilled = false;
            this.tutorialSkip = false;
            this.campaignChoice = -1;
            this.disableInvasions = false;
            this.fasterProduction = false;
            this.deathLink = false;
        }
    }

    /**
     * Unlock Serpulo's tutorial research and unlock Frozen Forest.
     */
    protected static void unlockSerpuloTutorialItems() {
        Blocks.conveyor.quietUnlock();
        Blocks.duo.quietUnlock();
        Blocks.scatter.quietUnlock();
        Blocks.mechanicalDrill.quietUnlock();
        Blocks.copperWall.quietUnlock();
        SectorPresets.groundZero.quietUnlock();
        SectorPresets.frozenForest.quietUnlock();
        SectorPresets.frozenForest.alwaysUnlocked = true;
    }

    /**
     * Unlock Erekir's tutorial research and unlock Aegis.
     */
    protected static void unlockErekirTutorialItems() {
        //Method not implemented
    }

    protected static void applyFasterProduction(int campaign){
        if (campaign == 0) { //Serpulo
            applySerpuloFasterProduction();
        } else if (campaign == 1) { //Erekir
            applyErekirFasterProduction();
        } else if (campaign == 2) { //All
            applySerpuloFasterProduction();
            applyErekirFasterProduction();
        }
    }

    private static void applyErekirFasterProduction() {
    }

    private static void applySerpuloFasterProduction() {
        doubleOutputItem(((GenericCrafter) Blocks.graphitePress));
        doubleOutputItem(((GenericCrafter) Blocks.multiPress));
        doubleOutputItem(((GenericCrafter) Blocks.siliconSmelter));
        doubleOutputItem(((GenericCrafter) Blocks.siliconCrucible));
        doubleOutputItem(((GenericCrafter) Blocks.kiln));
        doubleOutputItem(((GenericCrafter) Blocks.plastaniumCompressor));
        doubleOutputItem(((GenericCrafter) Blocks.phaseWeaver));
        doubleOutputItem(((GenericCrafter) Blocks.surgeSmelter));
        doubleOutputLiquid(((GenericCrafter) Blocks.cryofluidMixer));
        doubleOutputItem(((GenericCrafter) Blocks.pyratiteMixer));
        doubleOutputItem(((GenericCrafter) Blocks.blastMixer));
        doubleOutputLiquid(((GenericCrafter) Blocks.melter));
        doubleSeparatorOutput(((Separator) Blocks.separator));
        doubleSeparatorOutput(((Separator) Blocks.disassembler));
        doubleOutputLiquid(((GenericCrafter) Blocks.sporePress));
        doubleOutputItem(((GenericCrafter) Blocks.pulverizer));
        doubleOutputItem(((GenericCrafter) Blocks.coalCentrifuge));
        doublePumpAmount(((Pump) Blocks.mechanicalPump));
        doublePumpAmount(((Pump) Blocks.rotaryPump));
        doublePumpAmount(((Pump) Blocks.impulsePump));
        halfDrillTime(((Drill) Blocks.mechanicalDrill));
        halfDrillTime(((Drill) Blocks.pneumaticDrill));
        halfDrillTime(((Drill) Blocks.laserDrill));
        halfDrillTime(((Drill) Blocks.blastDrill));
        doublePumpAmount(((Pump) Blocks.waterExtractor));
        doublePumpAmount(((Pump) Blocks.oilExtractor));
        doubleOutputItem(((GenericCrafter) Blocks.cultivator));
    }

    private static void halfDrillTime(Drill drill) {
        drill.drillTime = drill.drillTime / 2;
    }

    private static void doubleOutputItem(GenericCrafter crafter){
        crafter.outputItem.amount = crafter.outputItem.amount * 2;
    }

    private static void doubleOutputLiquid(GenericCrafter crafter){
        crafter.outputLiquid.amount = crafter.outputLiquid.amount * 2;
    }

    private static void doubleSeparatorOutput(Separator separator){
        for (int i = 0; i < separator.results.length; i++) {
            separator.results[i].amount = separator.results[i]. amount * 2;
        }
    }

    private static void doublePumpAmount(Pump pump){
        pump.pumpAmount = pump.pumpAmount * 2;
    }


    /**
     * Save options locally
     */
    private void saveOptions() {
        settings.put("APdeathLink", getDeathLink());
        settings.put("APtutorialSkip", getTutorialSkip());
        settings.put("APdisableInvasions", getDisableInvasion());
        settings.put("APfasterProduction", getFasterProduction());
        settings.put("APcampaignChoice", getCampaignChoice());
    }

    /**
     * Load options locally
     */
    private void loadOptions() {
        this.deathLink = settings.getBool("APdeathLink");
        this.tutorialSkip = settings.getBool("APtutorialSkip");
        this.disableInvasions = settings.getBool("APdisableInvasions");
        this.fasterProduction = settings.getBool("APfasterProduction");
        this.campaignChoice = settings.getInt("APcampaignChoice");
        this.optionsFilled = true;
    }

}
