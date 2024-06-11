package mindustry.randomizer.client;

import arc.Core;
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

    private String address;

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


    public APClient () {
        super();
        this.loadAdressSlotName();
        this.setGame("Mindustry");
        this.setName(getSlotName());
        this.dataPackage = getDataPackage();

        this.getEventManager().registerListener(new ConnectResult(this));
        this.getEventManager().registerListener(new ReceiveItem());
    }

    private void loadAdressSlotName() {
        if (Core.settings.getString("APaddress") != null) {
            setAddress(Core.settings.getString("APaddress"));
        } else {
            setAddress("");
        }
        if (Core.settings.getString("APslotName") != null) {
            setSlotName(Core.settings.getString("APslotName"));
        } else {
            setSlotName("");
        }
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

    public boolean isAuthenticated() {
        return false; //TEMPORARY
    }

    public void connectRandomizer() {
        try {
            if (address != null) {
                connect(address);
            }
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
