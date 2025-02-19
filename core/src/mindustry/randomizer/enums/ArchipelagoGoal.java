package mindustry.randomizer.enums;

public enum ArchipelagoGoal {
    RESOURCES(0),
    CONQUEST(1);

    public static ArchipelagoGoal toArchipelagoGoal(int option){
        ArchipelagoGoal goal;

        if (option == 0) {
            goal = RESOURCES;
        } else if (option == 1) {
            goal = CONQUEST;
        } else {
            goal = RESOURCES;
        }

        return goal;
    }
    public final int value;

    private ArchipelagoGoal(int value) {
        this.value = value;
    }
}
