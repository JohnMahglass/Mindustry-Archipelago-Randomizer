package mindustry.randomizer;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.SectorPresets;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

/**
 * APItemObjective
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class APItemObjective implements Objectives.Objective {


    int itemId;
    UnlockableContent sector;

    @Override
    public boolean complete() {
        return Vars.randomizer.hasItem(itemId);
    }

    @Override
    public String display() {
        if (sector != null) {
            return Core.bundle.format("requirement.capture", sector.localizedName);
        }
        else {
            return null;
        }

    }

    @Override
    public void build(Table table) {
        Objectives.Objective.super.build(table);
    }

    public APItemObjective(int itemId) {
        this.itemId = itemId;
        if (Vars.randomizer.isSector(itemId)) {
            sector = Vars.randomizer.items.get(itemId);
        }
    }
}
