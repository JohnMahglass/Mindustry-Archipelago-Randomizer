package mindustry.randomizer.enums;

/**
 * Type of campaign for the randomizer.
 */
public enum CampaignType {
    SERPULO(0),
    EREKIR(1),
    ALL(2);

    public static CampaignType toCampaignType(int option){
        CampaignType campaign;

        if (option == 0) {
            campaign = SERPULO;
        } else if (option == 1) {
            campaign = EREKIR;
        } else if (option == 2) {
            campaign = ALL;
        } else {
            campaign = SERPULO;
        }

        return campaign;
    }

    public final int value;

    private CampaignType(int value) {
        this.value = value;
    }
}
