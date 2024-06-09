package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.events.ConnectionResultEvent;
import dev.koifysh.archipelago.parts.DataPackage;
import dev.koifysh.archipelago.parts.NetworkItem;

import java.net.URISyntaxException;

/**
 * APClient for connecting to Archipelago server.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-07
 */
public class APClient extends Client {

    DataPackage dataPackage;

    public APClient () {
        super();
        this.setGame("Mindustry");
        this.setName("Dev");

        this.getEventManager().registerListener(new ConnectResult(this));
        //this.dataPackage = getDataPackage();

    }

    @Override
    public void onPrintJson(APPrint apPrint, String type, int player, NetworkItem item) {

    }

    @Override
    public void onError(Exception ex) {

    }

    @Override
    public void onClose(String Reason, int attemptingReconnect) {

    }
}
