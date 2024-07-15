package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ReceiveItemEvent;
import mindustry.Vars;

/**
 * ReceiveItem
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-11
 */
public class ReceiveItem {

    @ArchipelagoEventListener
    public static void onReceiveItem(ReceiveItemEvent event) {
        String receivingPlayerName = Vars.randomizer.randomizerClient.getSlotName();
        Vars.randomizer.unlock(event.getItemID());
        Vars.randomizer.worldState.saveStates();
        if (!Vars.ui.chatfrag.isBlockAPMessage()) {
            if (event.getPlayerID() == Vars.randomizer.randomizerClient.getSlot()) { //The player's
                // own item
                Vars.randomizer.sendLocalMessage(receivingPlayerName + " found their " +
                        event.getItemName() + "(" + event.getLocationName() + ")");
            } else { //Item is being sent by another player
                Vars.randomizer.sendLocalMessage(event.getPlayerName() + " found " + receivingPlayerName +
                        " " + event.getItemName() + "(" + event.getLocationName() + ")");
            }
        }
    }

}
