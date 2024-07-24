package mindustry.randomizer.client;

import arc.Core;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import mindustry.Vars;
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
        UnlockableContent content = randomizer.itemIdToUnlockableContent(event.getItemID());
        if (content != null && !Core.settings.getBool(content.name + "-unlocked")) { //Making
            // sure the content is not already unlocked.
            randomizer.unlock(event.getItemID());
            randomizer.worldState.saveStates();
            if (Vars.ui.chatfrag != null) {
                if (event.getPlayerID() == randomizer.getPlayerSlot()) { //The player's
                    // own item
                    randomizer.sendLocalMessage(randomizer.getPlayerName() + " found their " +
                            event.getItemName() + "(" + event.getLocationName() + ")");
                } else { //Item is being sent by another player
                    randomizer.sendLocalMessage(event.getPlayerName() + " found " + randomizer.getPlayerName() +
                            " " + event.getItemName() + "(" + event.getLocationName() + ")");
                }
            }
        }

    }

}
