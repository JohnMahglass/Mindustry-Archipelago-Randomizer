package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.flags.ItemsHandling;
import dev.koifysh.archipelago.parts.DataPackage;
import mindustry.Vars;
import mindustry.randomizer.enums.ApChatColors;
import mindustry.randomizer.enums.ConnectionStatus;
import mindustry.randomizer.utils.ChatColor;
import mindustry.randomizer.utils.RandomizerMessageHandler;

import java.net.URISyntaxException;

import static mindustry.Vars.randomizer;
import static arc.Core.settings;
import static mindustry.randomizer.enums.SettingStrings.*;


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
     * To prevent the onClose twice bug.
     */
    public boolean onCloseTriggered;


    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        if (slotName != null) {
            this.slotName = slotName;
            setName(slotName);
            settings.put(CLIENT_NAME.value, slotName);
        }
    }

    @Override
    public void setPassword(String password){
        if (password != null) {
            super.setPassword(password);
            settings.put(CLIENT_PASSWORD.value, password);
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
                RandomizerMessageHandler.printErrorWithReason("You are not connected, message cannot be sent.");
            }
        }
    }

    @Override
    public void onError(Exception ex) {
        RandomizerMessageHandler.printClientError(ex.getMessage());
    }

    @Override
    public void onClose(String Reason, int attemptingReconnect) { //onClose is triggering twice?
        if (!onCloseTriggered) { //Temporary
            randomizer.sendLocalMessage(ChatColor.applyColor(ApChatColors.RED, "Disconnected / Connection lost")  +
                    ". Offline checks will be saved and sent when connecting to the game again.");
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
                } else {
                    RandomizerMessageHandler.printErrorWithReason("Address or Slot name empty.");
                }
            } catch (URISyntaxException e) {
                if (randomizer.debug) {
                    e.printStackTrace();
                }
                RandomizerMessageHandler.printClientError("Connection failed. Please verify your " +
                        "login information in Settings -> Archipelago.");
            }
        } else {
            RandomizerMessageHandler.printErrorWithReason("You are already connected.");
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
            settings.put(CLIENT_ADDRESS.value, address);
        }

    }

    public String getAddress() {
        return this.address;
    }


    public APClient () {
        super();
        this.loadInfo();
        this.setGame("Mindustry");
        this.setItemsHandlingFlags(ItemsHandling.SEND_ITEMS + ItemsHandling.SEND_OWN_ITEMS + ItemsHandling.SEND_STARTING_INVENTORY);
        this.setName(getSlotName());
        this.connectionStatus = ConnectionStatus.NotConnected;
        this.dataPackage = getDataPackage();

        this.getEventManager().registerListener(new ConnectResult(this));
        this.getEventManager().registerListener(new LocationChecked());
        this.getEventManager().registerListener(new ReceiveItem());
        this.getEventManager().registerListener(new PrintJsonListener());
        this.getEventManager().registerListener(new onDeathLink());


        this.onCloseTriggered = false; //Temporary
    }


    /**
     * Load locally saved connection information.
     */
    private void loadInfo() {
        if (settings != null) {
            if (settings.getString(CLIENT_ADDRESS.value) != null) {
                setAddress(settings.getString(CLIENT_ADDRESS.value));
            } else {
                setAddress("");
            }

            if (settings.getString(CLIENT_NAME.value) != null) {
                setSlotName(settings.getString(CLIENT_NAME.value));
            } else {
                setSlotName("");
            }

            if (settings.getString(CLIENT_PASSWORD.value) != null) {
                setPassword(settings.getString(CLIENT_PASSWORD.value));
            } else {
                setPassword("");
            }
        }
    }

}
