package mindustry.randomizer;

/**
 * Class containing the options for the generated Mindustry game.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class MindustryOptions {

    /**
     * If the tutorial is to be skipped. Also unlock tutorial-related tech.
     */
    private boolean tutorialSkip;

    /**
     * The selected campaign.
     */
    private CampaignType campaignChoice;

    /**
     * Add sector clear as a location.
     */
    private boolean numberedSectorAreLocation;

    /**
     * Allow the use of the pause button (space)
     */
    private boolean allowPause;

    /**
     * Allow invasion.
     */
    private boolean allowInvasion;

    /**
     * If the player choose to skip the tutorial.
     * @return
     */
    public boolean getTutorialSkip() {
        return this.tutorialSkip;
    }

    public boolean getAllowInvasion() {
        return this.allowInvasion;
    }

    public CampaignType getCampaignChoice() {
        return this.campaignChoice;
    }

    /**
     * Constructor for MindustryOptions
     */
    public MindustryOptions() { //This will need to be filled with slot_data
        this.tutorialSkip = false;
        this.campaignChoice = CampaignType.SERPULO;
        this.allowInvasion = true;
    }
}
