package mindustry.randomizer.enums;

public enum DeathLinkMode {

    UNIT(0),
    CORE(1),
    CORE_RUSSIAN_ROULETTE(2);

    public final int value;

    public static DeathLinkMode toDeathLinkMode(int mode){
        DeathLinkMode deathLinkMode;

        if (mode == 0) {
            deathLinkMode = UNIT;
        } else if (mode == 1){
            deathLinkMode = CORE;
        } else if (mode == 2) {
            deathLinkMode = CORE_RUSSIAN_ROULETTE;
        } else {
            deathLinkMode = UNIT;
        }

        return  deathLinkMode;
    }

    private DeathLinkMode(int value) {
        this.value = value;
    }
}
