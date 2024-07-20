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

    @SerializedName("sector_behavior")
    private int sectorBehavior = 0;

    @SerializedName("ressource_behavior")
    private int ressourceBehavior = 0;

    @SerializedName("disable_invasions")
    private boolean disableInvasions = false;

    @SerializedName("death_link")
    private boolean deathLink = false;


    public boolean getTutorialSkip() {
        return tutorialSkip;
    }

    public int getCampaignChoice() {
        return campaignChoice;
    }

    public int getSectorBehavior() {
        return sectorBehavior;
    }

    public int getRessourceBehavior() {
        return ressourceBehavior;
    }

    public boolean getDisableInvasions() {
        return disableInvasions;
    }

    public boolean getDeathlink() {
        return deathLink;
    }

}
