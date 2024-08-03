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
    CLIENT_PASSWORD("APpassword");


    public final String value;

    private SettingStrings(String value) {
        this.value = value;
    }
}
