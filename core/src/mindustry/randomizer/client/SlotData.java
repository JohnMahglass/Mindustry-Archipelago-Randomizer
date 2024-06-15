package mindustry.randomizer.client;


/**
 * SlotData
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class SlotData {
    public boolean tutorialSkip;
    public int campaignChoice;
    public int sectorBehavior;
    public int ressourceBehavior;
    public boolean disableInvasion;
    public boolean earlyRessources;
    public boolean deathlink = false;

    /**
     * Getter for tutorialSkip
     *
     * @return return tutorialSkip
     */
    public boolean isTutorialSkip() {
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
    public boolean isDisableInvasion() {
        return disableInvasion;
    }

    /**
     * Getter for earlyRessources
     *
     * @return return earlyRessources
     */
    public boolean isEarlyRessources() {
        return earlyRessources;
    }

    /**
     * Getter for deathlink
     *
     * @return return deathlink
     */
    public boolean isDeathlink() {
        return deathlink;
    }

}
