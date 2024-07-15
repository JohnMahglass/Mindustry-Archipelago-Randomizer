package mindustry.randomizer;

import mindustry.randomizer.client.SlotData;
import mindustry.randomizer.enums.CampaignType;

/**
 * Class containing the options for the generated Mindustry game.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class MindustryOptions {

    private boolean optionsFilled;

    /**
     * If the tutorial is to be skipped. Also unlock tutorial-related tech.
     */
    private boolean tutorialSkip;

    /**
     * The selected campaign.
     */
    private int campaignChoice;

    private int sectorBehavior;

    private int ressourceBehavior;

    private boolean deathLink;

    /**
     * Disable invasion.
     */
    private boolean disableInvasions;

    /**
     * If the player choose to skip the tutorial.
     * @return
     */
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
        return this.optionsFilled;
    }

    public CampaignType getCampaignChoice() {
        CampaignType type;
            switch (campaignChoice) {
                case 0:
                    type = CampaignType.SERPULO;
                    break;
                case 1:
                    type = CampaignType.EREKIR;
                    break;
                case 2:
                    type = CampaignType.ALL;
                    break;
                default:
                    type = CampaignType.SERPULO;
                    break;
            }
        return type;
    }

    public void fillOptions (SlotData slotData) {
        if (slotData != null) {
            this.deathLink = slotData.getDeathlink();
            this.tutorialSkip = slotData.getTutorialSkip();
            this.disableInvasions = slotData.getDisableInvasions();
            this.sectorBehavior = slotData.getSectorBehavior();
            this.ressourceBehavior = slotData.getRessourceBehavior();
            this.campaignChoice = slotData.getCampaignChoice();

            this.optionsFilled = true;
        }
    }

    /**
     * Constructor for MindustryOptions
     */
    public MindustryOptions() { //This will need to be filled with slot_data
        this.optionsFilled = false;
        this.tutorialSkip = false;
        this.campaignChoice = 0;
        this.disableInvasions = false;
        this.ressourceBehavior = 0;
        this.sectorBehavior = 0;
        this.deathLink = false;
    }
}
