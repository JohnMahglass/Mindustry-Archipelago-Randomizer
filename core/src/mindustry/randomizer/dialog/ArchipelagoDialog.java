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

    public ArchipelagoDialog() {
        super("Archipelago");
        this.client = randomizer.randomizerClient;
        this.newAddress = null;
        this.newSlotName = null;
        addCloseButton();
        setup();
    }

    private void setup() {
        cont.row();
        cont.labelWrap("Address: " + ((client.getAddress() != null) ? client.getAddress() :
                "Address not set"));

        cont.row();
        cont.labelWrap("Player name: " + (client.getMyName()));

        cont.row();
        cont.labelWrap("Connection status: " + ((client.isConnected()) ? "Connected":
                "Not Connected"));



        cont.row();
        cont.labelWrap("New Address: ").padBottom(50f);
        cont.field("", text -> {
            newAddress = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.labelWrap("New SlotName: ").padBottom(50f);
        cont.field("", text -> {
            newSlotName = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.button("Apply", () -> {
            if (newAddress != null) {
                client.setAddress(newAddress);
                Core.settings.put("APaddress", newAddress);
            }
            if (newSlotName != null) {
                client.setSlotName(newSlotName);
                Core.settings.put("APslotName", newSlotName);
            }
            hide();
        }).size(140f, 60f).pad(4f);;







        /*
        cont.table(Tex.button, t -> {
            t.defaults().size(280f, 60f).left();

            t.labelWrap("Address: " + ((client.getAddress() != null) ? client.getAddress() :
                    "Address not set"));

            t.labelWrap("Player name: " + (client.getMyName()));

            t.labelWrap("Connection status: " + ((client.isConnected()) ? "Connected":
            "Not Connected"));




            t.labelWrap("New Address: ");
            t.field("", text -> {
                newAddress = text;
            });


            t.button("Apply", () -> {
                if (newAddress != null) {
                    client.setAddress(newAddress);
                    Core.settings.put("APaddress", newAddress);
                }
                hide();
            });


            t.labelWrap("New SlotName: ");
            t.field("", text -> {
                newSlotName = text;
            });


            t.button("Apply", () -> {
                if (newSlotName != null) {
                    client.setSlotName(newSlotName);
                    Core.settings.put("APslotName", newSlotName);
                }
                hide();
            });


        });
        */

    }
}
