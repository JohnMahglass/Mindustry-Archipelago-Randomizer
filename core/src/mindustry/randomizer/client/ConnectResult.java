package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ConnectionResultEvent;
import dev.koifysh.archipelago.helper.DeathLink;
import dev.koifysh.archipelago.network.ConnectionResult;
import mindustry.randomizer.enums.ConnectionStatus;
import mindustry.randomizer.utils.RandomizerMessageHandler;

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
            client.onCloseTriggered = false; //To prevent a bug were onClose method is being
            // called twice, this needs to be investigated.
            client.slotData = event.getSlotData(SlotData.class);
            if (!randomizer.hasConnectedPreviously) { //First time the player is connecting
                // to the game
                randomizer.worldState.options.fillOptions(client.slotData);
                randomizer.worldState.createSeed(client.getRoomInfo().seedName);
                randomizer.initialize();
                randomizer.updateForceExit(); //Required to apply all randomizer options.
            }
            while (randomizer.worldState.hasCheckPending()) { // The player has item
                // waiting to be sent to Archipelago
                randomizer.sendPendingLocations();
            }
            RandomizerMessageHandler.printSuccessfulConnection("'" + client.getAddress() + "'");
            if (randomizer.worldState.options.getTrueDeathLink()) {
                DeathLink.setDeathLinkEnabled(true);
            }
        } else {
            if (event.getResult() == ConnectionResult.InvalidSlot) {
                client.connectionStatus = ConnectionStatus.InvalidSlot;
                RandomizerMessageHandler.printFailedConnection("Invalid Slot name.");
            } else if (event.getResult() == ConnectionResult.InvalidPassword) {
                client.connectionStatus = ConnectionStatus.InvalidPassword;
                RandomizerMessageHandler.printFailedConnection("Invalid Password.");
            } else if (event.getResult() == ConnectionResult.SlotAlreadyTaken) {
                client.connectionStatus = ConnectionStatus.SlotAlreadyTaken;
                RandomizerMessageHandler.printFailedConnection("Slot already taken.");
            } else if (event.getResult() == ConnectionResult.IncompatibleVersion) {
                client.connectionStatus = ConnectionStatus.IncompatibleVersion;
                RandomizerMessageHandler.printFailedConnection("Incompatible version");
            } else {
                client.connectionStatus = ConnectionStatus.NotConnected;
                RandomizerMessageHandler.printFailedConnection("Unknown reason.");
            }
        }
    }
}
