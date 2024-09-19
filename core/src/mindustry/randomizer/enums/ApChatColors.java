package mindustry.randomizer.enums;

public enum ApChatColors {

    RED("[#DB3232]"),
    GREEN("[#66C942]"),
    WHITE("[#FFFFFF]");

    public final String value;

    private ApChatColors(String value) {
        this.value = value;
    }
}
