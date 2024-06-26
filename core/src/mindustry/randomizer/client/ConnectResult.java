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
            client.slotData = event.getSlotData(SlotData.class);
            client.connectionStatus = ConnectionStatus.Success;
            if (Vars.randomizer.worldState.checkPending.size() > 0) {
                for (Long locationId : Vars.randomizer.worldState.checkPending) {
                    Vars.randomizer.checkPendingLocation(locationId);
                }
            }
            //Inform player that they are connected
        } else {
            //Inform player that they are not connected
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
        }
    }
}
