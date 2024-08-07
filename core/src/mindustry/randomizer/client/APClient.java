package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.ItemFlags;
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
 * @version 0.0.1 2024-06-07
 */
public class APClient extends Client {

    public ConnectionStatus connectionStatus;

    protected SlotData slotData;

    private DataPackage dataPackage;

    private String slotName;

    private String address;


    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
        setName(slotName);
    }

    /**
     * Send a chat message to Archipelago
     * @param message The message to be sent
     */
    public void sendChatMessage(String message) {
        if (isConnected()) {
            if (Vars.ui.chatfrag != null) {
                sendChat(message);
            }
        } else {
            randomizer.sendLocalMessage("ERROR: You are not connected, message cannot be sent.");
        }
    }


    @Override
    public void onError(Exception ex) {
        randomizer.sendLocalMessage("Client error: " + ex.getMessage());
    }

    @Override
    public void onClose(String Reason, int attemptingReconnect) {
        randomizer.sendLocalMessage("Connection closed.");
    }

    /**
     * Attempt to connect to Archipelago using the information provided by the user.
     */
    public void connectRandomizer() {
        try {
            if (address != null && slotName != null) {
                connect(address);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public String getLocationName(Long id, String gameName){
        return dataPackage.getGame(gameName).getLocation(id);
    }

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
        this.setItemsHandlingFlags(ItemFlags.SEND_ITEMS + ItemFlags.SEND_OWN_ITEMS);
        this.setName(getSlotName());
        this.connectionStatus = ConnectionStatus.NotConnected;
        this.dataPackage = getDataPackage();

        this.getEventManager().registerListener(new ConnectResult(this));
        this.getEventManager().registerListener(new LocationChecked());
        this.getEventManager().registerListener(new ReceiveItem());
        this.getEventManager().registerListener(new PrintJsonListener());
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
