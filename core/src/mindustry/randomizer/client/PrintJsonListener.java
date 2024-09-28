package mindustry.randomizer.client;

import dev.koifysh.archipelago.Print.APPrintJsonType;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import mindustry.randomizer.ui.APChat.APMessage;

import static mindustry.Vars.randomizer;
import static mindustry.randomizer.enums.SettingStrings.*;
import static arc.Core.settings;

/**
 * Print Json message from Archipelago
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-29
 */
public class PrintJsonListener {
    @ArchipelagoEventListener
    public void onPrintJson(PrintJSONEvent event) {
        if (event.type.equals(APPrintJsonType.ItemSend)){
            filterItemMessage(event);
        } else {
            randomizer.sendLocalMessage(new APMessage(event));
        }

    }

    /**
     * Filter item message event if the player selected the 'self item only' option.
     * @param event The Archipelago event received from the client.
     */
    private void filterItemMessage(PrintJSONEvent event) {
        if (!settings.getBool(AP_CHAT_DISABLED.value)) {
            int receivingPlayer = event.apPrint.receiving;
            int sendingPlayer = event.item.playerID;
            int mindustryPlayer = randomizer.client.getSlot();

            if (settings.getBool(AP_CHAT_SELF_ITEM_ONLY.value)) {
                if (sendingPlayer == mindustryPlayer) {
                    randomizer.sendLocalMessage(new APMessage(event));
                } else {
                    if (receivingPlayer == mindustryPlayer) {
                        randomizer.sendLocalMessage(new APMessage(event));
                    }
                }
            } else {
                randomizer.sendLocalMessage(new APMessage(event));
            }
        }

    }
}
