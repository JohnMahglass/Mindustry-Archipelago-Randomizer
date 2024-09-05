package mindustry.randomizer.enums;

public enum SettingStrings {

    HAS_CONNECTED("APhasConnected"),
    FREE_LAUNCH_SERPULO("APfreeLaunchSerpulo"),
    FREE_LAUNCH_EREKIR("APfreeLaunchErekir"),
    FASTER_PRODUCTION("APfasterProduction"),
    DISABLE_INVASIONS("APdisableInvasions"),
    DEATH_LINK("APdeathLink"),
    FORCE_DISABLE_DEATH_LINK("APforceDisableDeathLink"),
    TUTORIAL_SKIP("APtutorialSkip"),
    CAMPAIGN_CHOICE("APcampaignChoice"),
    CLIENT_ADDRESS("APaddress"),
    CLIENT_NAME("APslotName"),
    CLIENT_PASSWORD("APpassword"),
    SERPULO_VICTORY("APserpuloVictory"),
    EREKIR_VICTORY("APerekirVictory"),
    AP_SEED("APseed"),

    RANDOMIZE_BLOCK_SIZE("APrandomizeBlockSize"),
    RANDOMIZE_CORE_UNITS_WEAPON("APrandomizeCoreUnitsWeapon"),
    LOGISTIC_DISTRIBUTION("APlogisticDistribution");


    public final String value;

    private SettingStrings(String value) {
        this.value = value;
    }
}
