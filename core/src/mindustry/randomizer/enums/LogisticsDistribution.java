package mindustry.randomizer.enums;

public enum LogisticsDistribution {

    RANDOMIZED(0),
    EARLY(1),
    LOCAL_EARLY(2),
    STARTER(3);

    public static LogisticsDistribution toLogisticDistribution(int option){
        LogisticsDistribution distribution;

        if (option == 0) {
            distribution = RANDOMIZED;
        } else if (option == 1) {
            distribution = EARLY;
        } else if (option == 2) {
            distribution = LOCAL_EARLY;
        } else if (option == 3) {
            distribution = STARTER;
        } else {
           distribution = RANDOMIZED;
        }

        return distribution;
    }

    public final int value;

    private LogisticsDistribution(int value) {
        this.value = value;
    }
}
