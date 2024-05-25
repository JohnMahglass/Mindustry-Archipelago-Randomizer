package mindustry.randomizer;

import mindustry.Vars;
import mindustry.content.SectorPresets;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

/**
 * APSectorObjective
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-25
 */
public class APSectorObjective implements Objectives.Objective{

    UnlockableContent sector;

    @Override
    public boolean complete() {
        //Method not implemented
        return false;
    }

    @Override
    public String display() {
        return "Capture " + sector.localizedName;
    }

    public APSectorObjective(int itemId) {
        if (Vars.randomizer.isSector(itemId)) {
            sector = Vars.randomizer.items.get(itemId);
        }
        else {
            sector = null; //Probably should change this
        }
    }
}
