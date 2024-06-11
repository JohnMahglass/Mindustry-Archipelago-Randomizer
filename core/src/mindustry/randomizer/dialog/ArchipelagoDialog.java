package mindustry.randomizer.dialog;

import arc.Core;
import mindustry.randomizer.client.APClient;
import mindustry.ui.dialogs.BaseDialog;
import static mindustry.Vars.randomizer;

/**
 * Dialog for Archipelago settings.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class ArchipelagoDialog extends BaseDialog {
    APClient client;

    String newAddress;
    String newSlotName;
    boolean settingChanged;

    public ArchipelagoDialog() {
        super("Archipelago");
        this.client = randomizer.randomizerClient;
        this.newAddress = null;
        this.newSlotName = null;
        this.settingChanged = false;
        addCloseButton();
        setup();
    }

    private void setup() {
        cont.row();
        cont.labelWrap("Address: " + ((Core.settings.getString("APaddress") != null) ?
                Core.settings.getString("APaddress") : "Address not set"));

        cont.row();
        cont.labelWrap("Player name: " + ((Core.settings.getString("APslotName") != null ?
                Core.settings.getString("APslotName") : "Name not set")));

        cont.row();
        cont.labelWrap("Connection status: " + ((client.isConnected()) ? "Connected":
                "Not Connected"));



        cont.row();
        cont.labelWrap("New Address: ").padBottom(55f);
        cont.field("", text -> {
            newAddress = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.labelWrap("New SlotName: ").padBottom(55f);
        cont.field("", text -> {
            newSlotName = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.button("Apply", () -> {
            if (newAddress != null) {
                client.setAddress(newAddress);
                Core.settings.put("APaddress", newAddress);
                settingChanged = true;
            }
            if (newSlotName != null) {
                client.setSlotName(newSlotName);
                Core.settings.put("APslotName", newSlotName);
                settingChanged = true;
            }
            if (settingChanged) {
                reload();
                settingChanged = false;
            }
        }).size(140f, 60f).pad(4f);

        cont.row();
        cont.button("Connect", () -> {
           client.connectRandomizer();
            reload();
            hide();
        }).size(140f, 60f).pad(4f);
        cont.button("Disconnect", () -> {
            client.disconnect();
            reload();
            hide();
        }).size(140f, 60f).pad(4f);

    }

    public void reload() {
        cont.clearChildren();
        setup();
    }
}
