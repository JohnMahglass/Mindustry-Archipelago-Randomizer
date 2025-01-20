package mindustry.randomizer.enums;

public enum ApChatColors {

    RED("[#DB3232]"),
    GREEN("[#66C942]"),
    WHITE("[#FFFFFF]"),
    GOLD("[#EDCE32]"),
    TEAL("[#54E8D4]"),
    PURPLE("[#8949C4]"),
    BLUE("[#3A72BA]"),
    ORANGE("[#EB4F34]"),
    LIGHTGRAY("[#6E6E6E]"),
    SERPULO("[#8738E0]"),
    EREKIR("[#E06838]");

    public final String value;

    private ApChatColors(String value) {
        this.value = value;
    }
}
