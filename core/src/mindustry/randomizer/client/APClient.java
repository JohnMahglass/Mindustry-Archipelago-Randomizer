package mindustry.randomizer.client;

import arc.Core;
import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.ItemFlags;
import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.parts.DataPackage;
import dev.koifysh.archipelago.parts.NetworkItem;
import mindustry.Vars;
import mindustry.randomizer.Randomizer;

import java.net.URISyntaxException;


/**
 * APClient for connecting to Archipelago server.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-07
 */
public class APClient extends Client {

    private Randomizer randomizer;
    public DataPackage dataPackage;

    public SlotData slotData;

    private String slotName;

    private String address;

    private String password;

    /**
     * Getter for password
     *
     * @return Return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Assign variable password
     *
     * @param password Value of password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }



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


    public APClient (Randomizer randomizer) {
        super();
        this.randomizer = randomizer;
        this.loadInfo();
        this.setGame("Mindustry");
        this.setItemsHandlingFlags(ItemFlags.SEND_ITEMS + ItemFlags.SEND_OWN_ITEMS);
        this.setName(getSlotName());
        this.dataPackage = getDataPackage();

        this.getEventManager().registerListener(new ConnectResult(this));
        this.getEventManager().registerListener(new LocationChecked());
        this.getEventManager().registerListener(new ReceiveItem());
    }

    private void loadInfo() {
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
        if (Core.settings.getString("APpassword") != null) {
            setPassword(Core.settings.getString("APpassword"));
        } else {
            setPassword("");
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
    public void connectRandomizerPassword() {
        //Same as connectRandomizer but with a password
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }
}
