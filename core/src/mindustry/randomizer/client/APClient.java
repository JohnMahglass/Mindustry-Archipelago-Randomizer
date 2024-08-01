package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.flags.ItemsHandling;
import dev.koifysh.archipelago.parts.DataPackage;
import dev.koifysh.archipelago.parts.NetworkPlayer;
import dev.koifysh.archipelago.parts.NetworkSlot;
import mindustry.Vars;
import mindustry.randomizer.enums.ConnectionStatus;

import java.net.URISyntaxException;

import static mindustry.Vars.randomizer;
import static arc.Core.settings;


/**
 * APClient for connecting to Archipelago server.
 *
 * @author John Mahglass
 */
public class APClient extends Client {

    public ConnectionStatus connectionStatus;

    protected SlotData slotData;

    private DataPackage dataPackage;

    private String slotName;

    private String address;

    /**
     * Temporary to fix the onClose twice bug.
     */
    public boolean onCloseTriggered;


    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        if (slotName != null) {
            this.slotName = slotName;
            setName(slotName);
            settings.put("APslotName", slotName);
        }
    }

    @Override
    public void setPassword(String password){
        if (password != null) {
            super.setPassword(password);
            settings.put("APpassword", password);
        }
    }

    /**
     * Send a chat message to Archipelago
     * @param message The message to be sent
     */
    public void sendChatMessage(String message) {
        if (Vars.ui.chatfrag != null) {
            if (isConnected() && connectionStatus.equals(ConnectionStatus.Success)) {
                sendChat(message);
            } else {
                randomizer.sendLocalMessage("ERROR: You are not connected, message cannot be sent.");
            }
        }
    }


    @Override
    public void onError(Exception ex) {
        randomizer.sendLocalMessage("Client error: " + ex.getMessage());
    }

    @Override
    public void onClose(String Reason, int attemptingReconnect) { //onClose is triggering twice?
        if (!onCloseTriggered) { //Temporary
            randomizer.sendLocalMessage("Disconnected / Connection lost. Offline checks will be " +
                    "saved and sent when connecting to the game again.");
        }
        onCloseTriggered = true;
    }

    /**
     * Attempt to connect to Archipelago using the information provided by the user.
     */
    public void connectRandomizer() {
        if (!isConnected()) {
            try {
                if (address != null && slotName != null) {
                    connect(address);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Disconnect from the AP server.
     */
    @Override
    public void disconnect(){
        if (isConnected()) {
            super.disconnect();
            connectionStatus = ConnectionStatus.NotConnected;
        }
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
            settings.put("APaddress", address);
        }

    }

    public String getAddress() {
        return this.address;
    }

    /**
     * Return location name from dataPackage.
     * @param id Id of the location.
     * @param gameName Name of the game of the location.
     * @return Return the name of the location.
     */
    public String getLocationName(Long id, String gameName){
        return dataPackage.getGame(gameName).getLocation(id);
    }

    /**
     * Return item name from dataPackage.
     * @param id Id of the item.
     * @param gameName Name of the game of the item.
     * @return Return the name of the item.
     */
    public String getItemName(Long id, String gameName){
        return dataPackage.getGame(gameName).getItem(id);
    }

    /**
     * Get player name from their slot id.
     * @param slot The slot id of the player.
     * @return Return the name of the player.
     */
    public String getPlayerName(int slot) {
        String playerName = null;
        for (NetworkPlayer player : getRoomInfo().networkPlayers) {
            if (player.slot == slot) {
                playerName = player.name;
            }
        }
        if (playerName == null) {
            playerName = "NAME ERROR";
        }
        return  playerName;
    }

    /**
     * Return the game of the slot user.
     * @param slot The slot user.
     * @return The name of the game.
     */
    public String getPlayerGame(int slot){
        String playerGame = "";

        NetworkSlot ns = getSlotInfo().get(slot);
        if (playerGame != null) {
            playerGame = ns.game;
        } else {
            playerGame = "GAME NAME ERROR";
        }

        return playerGame;
    }


    public APClient () {
        super();
        this.loadInfo();
        this.setGame("Mindustry");
        this.setItemsHandlingFlags(ItemsHandling.SEND_ITEMS + ItemsHandling.SEND_OWN_ITEMS);
        this.setName(getSlotName());
        this.connectionStatus = ConnectionStatus.NotConnected;
        this.dataPackage = getDataPackage();

        this.getEventManager().registerListener(new ConnectResult(this));
        this.getEventManager().registerListener(new LocationChecked());
        this.getEventManager().registerListener(new ReceiveItem());
        this.getEventManager().registerListener(new PrintJsonListener());

        this.onCloseTriggered = false; //Temporary
    }


    /**
     * Load locally saved connection information.
     */
    private void loadInfo() {
        if (settings != null) {
            if (settings.getString("APaddress") != null) {
                setAddress(settings.getString("APaddress"));
            } else {
                setAddress("");
            }
            if (settings.getString("APslotName") != null) {
                setSlotName(settings.getString("APslotName"));
            } else {
                setSlotName("");
            }
            if (settings.getString("APpassword") != null) {
                setPassword(settings.getString("APpassword"));
            } else {
                setPassword("");
            }
        }
    }

}
