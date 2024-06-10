package mindustry.randomizer.client;

import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.Print.APPrint;
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

    public DataPackage dataPackage;

    public SlotData slotData;

    private String slotName;

    /**
     * Getter for slotName
     *
     * @return return slotName
     */
    public String getSlotName() {
        return slotName;
    }

    /**
     * Assign slotName
     *
     * @param slotName Value of slotName
     */
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    private String address;

    public APClient () {
        super();
        this.setGame("Mindustry");
        this.setName("Dev");
        this.dataPackage = getDataPackage();
        this.address = null;

        this.getEventManager().registerListener(new ConnectResult(this));
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

    public void connectRandomizer() {
        try {
            connect(address);
        } catch (URISyntaxException e) { //NEED TO LOG ERROR
            e.printStackTrace();
        }
    }
    public void connectRandomizer(String password) {
        //Same as connectRandomizer but with a password
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }
}
