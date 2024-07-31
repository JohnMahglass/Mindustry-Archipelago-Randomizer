package mindustry.randomizer.client;

import dev.koifysh.archipelago.Print.APPrintJsonType;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import dev.koifysh.archipelago.parts.NetworkPlayer;
import dev.koifysh.archipelago.parts.NetworkSlot;
import mindustry.Vars;
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
        if (event.type.equals(APPrintJsonType.Chat)) {
            randomizer.sendLocalMessage(getPlayerName(event.apPrint.slot) + ": " + event.apPrint.message);
        } else if (event.type.equals(APPrintJsonType.ItemSend)) {
             if (event.item.playerID == randomizer.getPlayerSlot()) { //The player send an item
                 if (event.item.playerID == event.apPrint.receiving) {// Player is sending an item to themself
                     randomizer.sendLocalMessage(getPlayerName(event.item.playerID) + " found their " +
                             randomizer.getClient().getItemName(event.apPrint.item.itemID, getPlayerGame(event.player)) +
                             "(" + randomizer.getClient().getLocationName(event.item.locationID, getPlayerGame(event.player)) +
                             ")");
                 } else { //Player is sending an item to someone else.
                     randomizer.sendLocalMessage(getPlayerName(event.item.playerID) + " sent  "
                             + getPlayerName(event.apPrint.receiving) + " " + randomizer.getClient().getItemName(event.apPrint.item.itemID, getPlayerGame(event.apPrint.receiving)) +
                             "(" + randomizer.getClient().getLocationName(event.item.locationID,
                             getPlayerGame(event.apPrint.receiving)) + ")");
                 }
             } else {
                 if (event.item.playerID == event.apPrint.receiving) {
                     //Item is being sent by another player to themself
                     randomizer.sendLocalMessage(getPlayerName(event.item.playerID) + " found their " + randomizer.getClient().getItemName(event.apPrint.item.itemID, getPlayerGame(event.player)) +
                             "(" + randomizer.getClient().getLocationName(event.item.locationID, getPlayerGame(event.player)) +
                             ")");
                 } else {//Item is being sent by another player to another player
                     randomizer.sendLocalMessage(getPlayerName(event.item.playerID) + " sent " + getPlayerName(event.apPrint.receiving) +
                             " " + randomizer.getClient().getItemName(event.apPrint.item.itemID,
                             getPlayerGame(event.apPrint.receiving)) + "(" + randomizer.getClient().getLocationName(event.item.locationID, getPlayerGame(event.apPrint.receiving)) + ")");
                 }

             }
        } else if (event.type.equals(APPrintJsonType.Join) && event.player != randomizer.getClient().getSlot()) {
            randomizer.sendLocalMessage(getPlayerName(event.apPrint.slot) +
                    " (Team #" + event.apPrint.team + ") playing "+ getPlayerGame(event.apPrint.slot) + " has joined.");
        } else if (event.type.equals(APPrintJsonType.Tutorial)) {
            randomizer.sendLocalMessage("Now that you are connected, you can use !help to " +
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

    private String getPlayerGame(int slot){
        String playerGame = "";

        NetworkSlot ns = Vars.randomizer.getClient().getSlotInfo().get(slot);
        if (playerGame != null) {
            playerGame = ns.game;
        } else {
            playerGame = "GAME NAME ERROR";
        }

        return playerGame;
    }
}
