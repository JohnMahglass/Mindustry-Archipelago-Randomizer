package mindustry.randomizer.client;

import io.github.archipelagomw.Print.APPrintJsonType;
import io.github.archipelagomw.events.ArchipelagoEventListener;
import io.github.archipelagomw.events.PrintJSONEvent;
import mindustry.randomizer.ui.APChat.APMessage;

import static mindustry.Vars.randomizer;

/**
 * Print Json message from Archipelago
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-29
 */
public class PrintJsonListener {
    @ArchipelagoEventListener
    public void onPrintJson(PrintJSONEvent event) {
        APPrintJsonType type = event.type;
        if (type.equals(APPrintJsonType.ItemSend)){
            filterItemMessage(event);
        } else {
            if (randomizer.worldState.options.getDisableChat()) {
                if (type.equals(APPrintJsonType.Hint) || type.equals(APPrintJsonType.Tutorial)) {
                    randomizer.sendLocalMessage(new APMessage(event));
                }
            } else {
                randomizer.sendLocalMessage(new APMessage(event));
            }
        }

    }

    /**
     * Filter item message event if the player selected the 'self item only' option.
     * @param event The Archipelago event received from the client.
     */
    private void filterItemMessage(PrintJSONEvent event) {
        if (!randomizer.worldState.options.getDisableChat()) {
            int receivingPlayer = event.apPrint.receiving;
            int sendingPlayer = event.item.playerID;
            int mindustryPlayer = randomizer.client.getSlot();

            if (randomizer.worldState.options.getAllowOnlySelfItem()) {
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
