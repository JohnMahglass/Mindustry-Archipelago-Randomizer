package mindustry.randomizer;

import mindustry.randomizer.client.SlotData;

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
     * Indicate how should sector behave.
     */
    private int sectorBehavior;

    /**
     * Indicate how should ressource behave.
     */
    private int ressourceBehavior;

    /**
     * If death link is activated
     */
    private boolean deathLink;

    /**
     * Disable invasion.
     */
    private boolean disableInvasions;

    public boolean getTutorialSkip() {
        return this.tutorialSkip;
    }

    public boolean getDisableInvasion() {
        return this.disableInvasions;
    }

    public boolean getDeathLink() {
        return this.deathLink;
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

    public int getRessourceBehavior() {
        return this.ressourceBehavior;
    }

    public int getSectorBehavior() {
        return this.sectorBehavior;
    }

    public int getCampaignChoice() {
        return this.campaignChoice;
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
            this.sectorBehavior = slotData.getSectorBehavior();
            this.ressourceBehavior = slotData.getRessourceBehavior();
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
            this.ressourceBehavior = 0;
            this.sectorBehavior = 0;
            this.deathLink = false;
        }
    }


    /**
     * Save options locally
     */
    private void saveOptions() {
        settings.put("APdeathLink", getDeathLink());
        settings.put("APtutorialSkip", getTutorialSkip());
        settings.put("APdisableInvasions", getDisableInvasion());
        settings.put("APsectorBehavior", getSectorBehavior());
        settings.put("APressourceBehavior", getRessourceBehavior());
        settings.put("APcampaignChoice", getCampaignChoice());
    }

    /**
     * Load options locally
     */
    private void loadOptions() {
        this.deathLink = settings.getBool("APdeathLink");
        this.tutorialSkip = settings.getBool("APtutorialSkip");
        this.disableInvasions = settings.getBool("APdisableInvasions");
        this.sectorBehavior = settings.getInt("APsectorBehavior");
        this.ressourceBehavior = settings.getInt("APressourceBehavior");
        this.campaignChoice = settings.getInt("APcampaignChoice");
        this.optionsFilled = true;
    }

}
