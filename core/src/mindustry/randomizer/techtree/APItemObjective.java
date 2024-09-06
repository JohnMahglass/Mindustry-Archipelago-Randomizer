package mindustry.randomizer.techtree;

import arc.scene.ui.layout.Table;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

import static mindustry.Vars.randomizer;

/**
 * Create an item objective as a requirement for an AP node.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-24
 */
public class APItemObjective implements Objectives.Objective {

    /**
     * The id of the item required
     */
    Long itemId;

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
        return randomizer.worldState.hasItem(itemId);
    }

    @Override
    public String display() {
        if (sector != null) {
            return "Capture " + sector.localizedName;
        }
        else {
            if (randomizer.worldState.isMindustryAPItem(itemId)) {
                return "Unlock " + randomizer.itemIdToUnlockableContent(itemId).localizedName;
            }
            else {
                return "AP Item Objective display Error";
            }
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
    public APItemObjective(Long itemId) {
        this.itemId = itemId;
        if (randomizer.isSector(itemId)) {
            sector = randomizer.worldState.items.get(itemId);
        }
    }
}
