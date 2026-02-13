package mindustry.randomizer.client;

import io.github.archipelagomw.events.ArchipelagoEventListener;
import io.github.archipelagomw.events.ReceiveItemEvent;
import mindustry.ctype.UnlockableContent;

import static mindustry.Vars.randomizer;
import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

/**
 * ReceiveItem
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-11
 */
public class ReceiveItem {

    @ArchipelagoEventListener
    public static void onReceiveItem(ReceiveItemEvent event) {
        UnlockableContent content = randomizer.itemIdToUnlockableContent(event.getItemID());

        if (content != null) { //Making sure the content is not already unlocked.
            randomizer.unlock(event.getItemID(), content);
            randomizer.worldState.saveStates();
        }
        if (event.getItemID() >= MINDUSTRY_BASE_ID + 700 && event.getItemID() < MINDUSTRY_BASE_ID + 1000) { // Filler or Trap event
            randomizer.processEvent(event.getItemID());
        }
        randomizer.worldState.addUnlockedItem(event.getItemID());
    }

}
