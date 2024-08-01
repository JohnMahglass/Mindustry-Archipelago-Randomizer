package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ConnectionResultEvent;
import dev.koifysh.archipelago.network.ConnectionResult;
import mindustry.randomizer.enums.ConnectionStatus;

import static mindustry.Vars.randomizer;

/**
 * ConnectResult
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-09
 */
public class ConnectResult {

    private APClient client;

    public ConnectResult(APClient client) {
        this.client = client;
    }

    @ArchipelagoEventListener
    public void onConnectResult(ConnectionResultEvent event) {
        if (event.getResult() == ConnectionResult.Success) {
            client.connectionStatus = ConnectionStatus.Success;
            client.onCloseTriggered = false;
            client.slotData = event.getSlotData(SlotData.class);
            if (!randomizer.hasConnectedPreviously) { //First time the player is connecting
                // to the game
                randomizer.worldState.options.fillOptions(client.slotData);
                randomizer.initialize();
            }
            while (randomizer.worldState.hasCheckPending()) { // The player has item
                // waiting to be sent to Archipelago
                randomizer.sendPendingLocations();
            }
            randomizer.sendLocalMessage("Connected to '" + client.getAddress() + "'");
        } else {
            if (event.getResult() == ConnectionResult.InvalidSlot) {
                client.connectionStatus = ConnectionStatus.InvalidSlot;
                randomizer.sendLocalMessage("Connection failed: Invalid Slot Name.");
            } else if (event.getResult() == ConnectionResult.InvalidPassword) {
                client.connectionStatus = ConnectionStatus.InvalidPassword;
                randomizer.sendLocalMessage("Connection failed: Invalid Password.");
            } else if (event.getResult() == ConnectionResult.SlotAlreadyTaken) {
                client.connectionStatus = ConnectionStatus.SlotAlreadyTaken;
                randomizer.sendLocalMessage("Connection failed: Slot already taken.");
            } else if (event.getResult() == ConnectionResult.IncompatibleVersion) {
                client.connectionStatus = ConnectionStatus.IncompatibleVersion;
                randomizer.sendLocalMessage("Connection failed: Incompatible version");
            } else {
                client.connectionStatus = ConnectionStatus.NotConnected;
            }
            randomizer.sendLocalMessage("Not connected. To connect, go to Settings -> " +
                    "Archipelago");
        }
    }
}
