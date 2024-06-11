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
        Vars.randomizer.unlock(event.getItemID());
    }

}
