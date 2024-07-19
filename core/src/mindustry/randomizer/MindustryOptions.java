package mindustry.randomizer;

import arc.Core;
import mindustry.Vars;
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
        boolean filled;
        if (this.optionsFilled || Vars.randomizer.hasConnectedPreviously) {
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
        /*
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
         */
        return this.campaignChoice;
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
            saveOptions();
            Core.settings.put("APhasConnected", true);
            if (tutorialSkip) {
                if (campaignChoice == 0) {
                    Core.settings.put("APfreeLaunchSerpulo", true);
                } else if (campaignChoice == 1) {
                    Core.settings.put("APfreeLaunchErekir", true);
                } else if (campaignChoice == 2) {
                    Core.settings.put("APfreeLaunchSerpulo", true);
                    Core.settings.put("APfreeLaunchErekir", true);
                }

            }
        }
    }

    private void saveOptions() {
        Core.settings.put("APdeathLink", getDeathLink());
        Core.settings.put("APtutorialSkip", getTutorialSkip());
        Core.settings.put("APdisableInvasions", getDisableInvasion());
        Core.settings.put("APsectorBehavior", getSectorBehavior());
        Core.settings.put("APressourceBehavior", getRessourceBehavior());
        Core.settings.put("APcampaignChoice", getCampaignChoice());
    }

    public void loadOptions() {
        this.deathLink = Core.settings.getBool("APdeathLink");
        this.tutorialSkip = Core.settings.getBool("APtutorialSkip");
        this.disableInvasions = Core.settings.getBool("APdisableInvasions");
        this.sectorBehavior = Core.settings.getInt("APsectorBehavior");
        this.ressourceBehavior = Core.settings.getInt("APressourceBehavior");
        this.campaignChoice = Core.settings.getInt("APcampaignChoice");
        this.optionsFilled = true;
    }

    /**
     * Constructor for MindustryOptions
     */
    public MindustryOptions() {
        //Do not use randomizer.hasConnectedPreviously since Vars.randomizer is null
        if (Core.settings.getBool("APhasConnected")) {
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
}
