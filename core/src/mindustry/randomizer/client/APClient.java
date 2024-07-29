package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.ItemFlags;
import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.parts.DataPackage;
import dev.koifysh.archipelago.parts.NetworkItem;
import dev.koifysh.archipelago.parts.NetworkPlayer;
import mindustry.Vars;
import mindustry.randomizer.enums.ConnectionStatus;

import java.net.URISyntaxException;

import static mindustry.Vars.randomizer;
import static arc.Core.settings;


/**
 * APClient for connecting to Archipelago server.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-07
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
    public void onPrintJson(APPrint apPrint, String type, int player, NetworkItem item) {
        switch (type) {
            case "Chat":
                randomizer.sendLocalMessage(getPlayerName(apPrint.slot) + ": " + apPrint.message);
                break;
            case "Tutorial":
                randomizer.sendLocalMessage("Now that you are connected, you can use !help to " +
                        "list commands to run via the server. If your client supports it, you may" +
                        " have additional local commands you can list with /help. (Commands " +
                        "support is coming soon.)"); //Message from Tutorial message is null???
                break;
            default:
                if (apPrint.message != null) {
                    randomizer.sendLocalMessage(apPrint.message);
                }
        }
    }

    @Override
    public void onError(Exception ex) {

    }

    @Override
    public void onClose(String Reason, int attemptingReconnect) {
        randomizer.sendLocalMessage("Connection closed due to : " + Reason);
    }

    /**
     * Attempt to connect to Archipelago using the information provided by the user.
     */
    public void connectRandomizer() {
        try {
            if (address != null && slotName != null) {
                connect(address);
            }
        } catch (URISyntaxException e) { //NEED TO LOG ERROR
            e.printStackTrace();
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
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

    /**
     * Get player name from their slot id.
     * @param slot The slot id of the player.
     * @return Return the name of the player.
     */
    private String getPlayerName(int slot) {
        String playerName = "";
        for (NetworkPlayer player : client.getRoomInfo().networkPlayers) {
            if (player.slot == slot) {
                playerName = player.name;
            }
        }
        return  playerName;
    }
}
