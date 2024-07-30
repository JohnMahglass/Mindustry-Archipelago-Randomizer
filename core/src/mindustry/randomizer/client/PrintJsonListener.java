package mindustry.randomizer.client;

import dev.koifysh.archipelago.Print.APPrintJsonType;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import dev.koifysh.archipelago.parts.NetworkPlayer;
import mindustry.Vars;

/**
 * Print Json message from Archipelago
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-29
 */
public class PrintJsonListener {
    @ArchipelagoEventListener
    public void onPrintJson(PrintJSONEvent event) {
        // Don't print chat messages originating from ourselves.
        if (event.type.equals(APPrintJsonType.Chat) && event.player != Vars.randomizer.getClient().getSlot()) {
            Vars.randomizer.sendLocalMessage(getPlayerName(event.apPrint.slot) + ": " + event.apPrint.message);
        } else if (event.type.equals(APPrintJsonType.Join) && event.player != Vars.randomizer.getClient().getSlot()) {
            Vars.randomizer.sendLocalMessage(getPlayerName(event.apPrint.slot) +
                    " (Team #" + event.apPrint.team + ") has joined." + event.apPrint);
        } else if (event.type.equals(APPrintJsonType.Tutorial)) {
            Vars.randomizer.sendLocalMessage("Now that you are connected, you can use !help to " +
                    "list commands to run via the server. If your client supports it, you may" +
                    " have additional local commands you can list with /help. (Local commands " +
                    "are comming soon!)");
        }



    }

    /**
     * Get player name from their slot id.
     * @param slot The slot id of the player.
     * @return Return the name of the player.
     */
    private String getPlayerName(int slot) {
        String playerName = "";
        for (NetworkPlayer player : Vars.randomizer.getClient().getRoomInfo().networkPlayers) {
            if (player.slot == slot) {
                playerName = player.name;
            }
        }
        return  playerName;
    }
}
