package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
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
        switch (event.type) {
            case Chat:
                randomizer.sendLocalMessage(randomizer.client.getPlayerName(event.apPrint.slot) + ": " + event.apPrint.message);
                break;
            case ItemSend:
                randomizer.sendLocalMessageItemSendEvent(event);
                break;
            case Join:
                randomizer.sendLocalMessage(randomizer.client.getPlayerName(event.apPrint.slot) +
                            " (Team #" + event.apPrint.team + ") playing " +
                            randomizer.client.getPlayerGame(event.apPrint.slot) + " has joined.");
                break;
            case Part:
                randomizer.sendLocalMessage(randomizer.client.getPlayerName(event.apPrint.slot) +
                        " has left the game " + randomizer.client.getPlayerGame(event.apPrint.slot) +
                        " (Team #" + event.apPrint.team + ")");
                break;
            case Tutorial:
                randomizer.sendLocalMessage("Now that you are connected, you can use !help to " +
                        "list commands to run via the server. If your client supports it, you may" +
                        " have additional local commands you can list with /help. (Local commands " +
                        "are comming soon!)");
                break;
            default:
                //Unsupported Event type
                break;
        }
    }

}
