package mindustry.randomizer.client;


import com.google.gson.annotations.SerializedName;

/**
 * SlotData
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class SlotData {
    @SerializedName("tutorial_skip")
    private boolean tutorialSkip = false;

    @SerializedName("campaign_choice")
    private int campaignChoice = 0;

    @SerializedName("disable_invasions")
    private boolean disableInvasions = false;

    @SerializedName("faster_production")
    private boolean fasterProduction = false;

    @SerializedName("death_link")
    private boolean deathLink = false;

    @SerializedName("death_link_mode")
    private int deathLinkMode = 0;

    @SerializedName("core_russian_roulette_chambers")
    private int coreRussianRouletteChambers = 0;

    @SerializedName("randomize_core_units_weapon")
    private boolean randomizeCoreUnitsWeapon = false;

    @SerializedName("logistic_distribution")
    private int logisticDistribution = 0;

    @SerializedName("make_early_roadblocks_local")
    private boolean makeEarlyRoadblocksLocal = false;

    @SerializedName("amount_of_resources_required")
    private int amountOfResourcesRequired = 2000;


    public boolean getTutorialSkip() {
        return tutorialSkip;
    }

    public int getCampaignChoice() {
        return campaignChoice;
    }

    public boolean getFasterProduction() {
        return fasterProduction;
    }

    public boolean getDisableInvasions() {
        return disableInvasions;
    }

    public boolean getDeathlink() {
        return deathLink;
    }

    public int getDeathLinkMode() {
        return deathLinkMode;
    }

    public int getCoreRussianRouletteChambers() {
        return coreRussianRouletteChambers;
    }

    public boolean getRandomizeCoreUnitsWeapon() {
        return randomizeCoreUnitsWeapon;
    }

    public int getLogisticDistribution(){
        return logisticDistribution;
    }

    public boolean getMakeEarlyRoadblocksLocal() {
        return this.makeEarlyRoadblocksLocal;
    }

    public int getAmountOfResourcesRequired() {
        return this.amountOfResourcesRequired;
    }

}
