package mindustry.randomizer.client;

import arc.Core;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import mindustry.ctype.UnlockableContent;

import static mindustry.Vars.randomizer;

/**
 * ReceiveItem
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-11
 */
public class ReceiveItem {

    @ArchipelagoEventListener
    public static void onReceiveItem(ReceiveItemEvent event) {
        if (event.getItemID() == 777000288) {
            int test = 1;
        }
        UnlockableContent content = randomizer.itemIdToUnlockableContent(event.getItemID());
        if (content != null && !Core.settings.getBool(content.name + "-unlocked")) { //Making
            // sure the content is not already unlocked.
            randomizer.unlock(event.getItemID());
            randomizer.worldState.saveStates();
        }

    }

}
