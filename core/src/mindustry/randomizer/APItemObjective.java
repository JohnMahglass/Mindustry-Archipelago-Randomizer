package mindustry.randomizer;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

/**
 * Create an objective as a requirement for an AP node.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class APItemObjective implements Objectives.Objective {

    /**
     * The id of the item required
     */
    int itemId;

    /**
     * The sector tied to this objective (if applicable).
     */
    UnlockableContent sector;

    /**
     * Return whether the objective is complete or not.
     * @return Return True if the objective is completed.
     */
    @Override
    public boolean complete() {
        return Vars.randomizer.hasItem(itemId);
    }

    @Override
    public String display() {
        if (sector != null) { //localisation?
            return "Unlock " + sector.localizedName;
        }
        else {
            return "Unlock " + Vars.randomizer.itemIdToUnlockableContent(itemId).name;
        }
    }

    @Override
    public void build(Table table) {
        Objectives.Objective.super.build(table);
    }

    /**
     * Constructor of ApItemObjective
     * @param itemId The id of the item required to clear to objective.
     */
    public APItemObjective(int itemId) {
        this.itemId = itemId;
        if (Vars.randomizer.isSector(itemId)) {
            sector = Vars.randomizer.items.get(itemId);
        }
    }
}
