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

    /**
     * Getter for tutorialSkip
     *
     * @return return tutorialSkip
     */
    public boolean getTutorialSkip() {
        return tutorialSkip;
    }

    /**
     * Getter for campaignChoice
     *
     * @return return campaignChoice
     */
    public int getCampaignChoice() {
        return campaignChoice;
    }

    /**
     * Getter for sectorBehavior
     *
     * @return return sectorBehavior
     */
    public int getSectorBehavior() {
        return sectorBehavior;
    }

    /**
     * Getter for ressourceBehavior
     *
     * @return return ressourceBehavior
     */
    public int getRessourceBehavior() {
        return ressourceBehavior;
    }

    /**
     * Getter for disableInvasion
     *
     * @return return disableInvasion
     */
    public boolean getDisableInvasions() {
        return disableInvasions;
    }

    /**
     * Getter for deathlink
     *
     * @return return deathlink
     */
    public boolean getDeathlink() {
        return deathLink;
    }

}
