package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.ConnectionResultEvent;
import dev.koifysh.archipelago.network.ConnectionResult;
import mindustry.Vars;

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
            //Inform player that they are connected
        } else {
            //Inform player that they are not connected
            if (event.getResult() == ConnectionResult.InvalidSlot) {

            } else if (event.getResult() == ConnectionResult.InvalidPassword) {

            } else if (event.getResult() == ConnectionResult.SlotAlreadyTaken) {

            } else if (event.getResult() == ConnectionResult.IncompatibleVersion) {

            } else {
                //Unknown error
            }
        }
    }
}
