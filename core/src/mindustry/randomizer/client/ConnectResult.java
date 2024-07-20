package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ConnectionResultEvent;
import dev.koifysh.archipelago.network.ConnectionResult;
import mindustry.Vars;
import mindustry.randomizer.enums.ConnectionStatus;

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
            client.slotData = event.getSlotData(SlotData.class);
            if (!Vars.randomizer.hasConnectedPreviously) {
                Vars.randomizer.worldState.options.fillOptions(client.slotData);
                Vars.randomizer.initialize();
            }
            if (Vars.randomizer.worldState.checkPending.size() > 0) {
                Vars.randomizer.sendPendingLocations();
            }
            Vars.randomizer.sendLocalMessage("Connected to '" + client.getAddress() + "'");
        } else {
            if (event.getResult() == ConnectionResult.InvalidSlot) {
                client.connectionStatus = ConnectionStatus.InvalidSlot;
            } else if (event.getResult() == ConnectionResult.InvalidPassword) {
                client.connectionStatus = ConnectionStatus.InvalidPassword;
            } else if (event.getResult() == ConnectionResult.SlotAlreadyTaken) {
                client.connectionStatus = ConnectionStatus.SlotAlreadyTaken;
            } else if (event.getResult() == ConnectionResult.IncompatibleVersion) {
                client.connectionStatus = ConnectionStatus.IncompatibleVersion;
            } else {
                client.connectionStatus = ConnectionStatus.NotConnected;
            }
            Vars.randomizer.sendLocalMessage("Not connected. To connect, go to Settings -> " +
                    "Archipelago");
        }
    }
}
