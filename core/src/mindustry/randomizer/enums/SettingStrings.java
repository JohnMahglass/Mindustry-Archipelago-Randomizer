package mindustry.randomizer.enums;

public enum SettingStrings {

    HAS_CONNECTED("APhasConnected"),
    FREE_LAUNCH_SERPULO("APfreeLaunchSerpulo"),
    FREE_LAUNCH_EREKIR("APfreeLaunchErekir"),
    FASTER_PRODUCTION("APfasterProduction"),
    FASTER_CONVEYOR("APfasterConveyor"),
    DISABLE_INVASIONS("APdisableInvasions"),
    DEATH_LINK("APdeathLink"),
    DEATH_LINK_MODE("APdeathLinkMode"),
    FORCE_DISABLE_DEATH_LINK("APforceDisableDeathLink"),
    TUTORIAL_SKIP("APtutorialSkip"),
    CAMPAIGN_CHOICE("APcampaignChoice"),
    AP_GOAL("APgoal"),
    CLIENT_ADDRESS("APaddress"),
    CLIENT_NAME("APslotName"),
    CLIENT_PASSWORD("APpassword"),
    SERPULO_VICTORY("APserpuloVictory"),
    EREKIR_VICTORY("APerekirVictory"),
    AP_SEED("APseed"),
    AP_CHAT_DISABLED("APchatDisable"),
    AP_CHAT_SELF_ITEM_ONLY("APchatSelfItemOnly"),
    AP_MAKE_EARLY_ROADBLOCKS_LOCAL("APmakeEarlyRoadblocksLocal"),
    AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO("APdeathLinkRussianRouletteAmmo"),
    AP_DEATH_LINK_RUSSIAN_ROULETTE_CHAMBERS("APdeathLinkRussianRouletteChambers"),
    AP_DEATH_LINK_PROTECT_CAPTURED_SECTOR("APdeathLinkProtectCapturedSector"),
    AMOUNT_OF_RESOURCES_REQUIRED("APamountOfResourcesRequired"),
    RANDOMIZE_CORE_UNITS_WEAPON("APrandomizeCoreUnitsWeapon"),
    LOGISTIC_DISTRIBUTION("APlogisticDistribution"),
    EREKIR_RANDOMIZED_WEAPON_EVOKE("APrandomizedEvokeWeapon"),
    EREKIR_RANDOMIZED_WEAPON_INCITE("APrandomizedInciteWeapon"),
    EREKIR_RANDOMIZED_WEAPON_EMANATE("APrandomizedEmanateWeapon"),
    EREKIR_RANDOMIZED_WEAPON_INIT("APrandomizedWeaponInit"),
    PROGRESSIVE_DRILLS("APprogressiveDrills"),
    PROGRESSIVE_GENERATORS("APprogressiveGenerators");


    public final String value;

    private SettingStrings(String value) {
        this.value = value;
    }
}
