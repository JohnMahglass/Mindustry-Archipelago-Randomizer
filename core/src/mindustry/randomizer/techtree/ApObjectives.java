package mindustry.randomizer.techtree;

import mindustry.content.TechTree;
import mindustry.game.Objectives;
import mindustry.randomizer.constant.RandomizerConstant;

/**
 * Objectives related to the Archipelago randomizer
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-18
 */
public abstract class ApObjectives extends Objectives {

    public static class UnlockParent implements Objective {

        TechTree.TechNode parentNode;

        public UnlockParent(TechTree.TechNode parentNode) {
            this.parentNode = parentNode;
        }

        @Override
        public boolean complete() {
            return parentNode.content.unlocked();
        }

        @Override
        public String display() {
            return RandomizerConstant.UNLOCK_PARENT_OBJECTIVE;
        }
    }

}
