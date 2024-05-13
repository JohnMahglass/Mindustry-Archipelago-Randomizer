package mindustry.randomizer;

/**
 * Randomizer
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-12
 */
public class Randomizer {

    public String uid;

    public CampainType campaign;

    public boolean tutorialSkip;

    public boolean numberedSectorAreLocation;

    public void Randomizer(){
        initialiseDefaultOptions();
    }

    private void initialiseDefaultOptions() {
        this.uid = "default";
        this.campaign = CampainType.SERPULO;
        tutorialSkip = false;
        numberedSectorAreLocation = false;
    }

}
