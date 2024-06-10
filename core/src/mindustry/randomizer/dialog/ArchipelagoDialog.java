package mindustry.randomizer.dialog;

import arc.Core;
import arc.files.Fi;
import arc.scene.ui.TextButton;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.randomizer.client.APClient;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.dataDirectory;
import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;

/**
 * ArchipelagoDialog
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class ArchipelagoDialog extends BaseDialog {

    APClient client;

    public ArchipelagoDialog() {
        super("Archipelago");
        client = randomizer.randomizerClient;
        addCloseButton();
        setup();
    }

    private void setup() {
        cont.table(Tex.button, t -> {
            t.defaults().size(280f, 60f).left();

            t.labelWrap("Address: " + ((client.getAddress() != null) ? client.getAddress() :
                    "Address not set"));

            t.labelWrap("Player name: " + (client.getMyName()));

            t.labelWrap("Connection status: " + ((client.isConnected()) ? "Connected":
            "Not Connected"));
        });
    }
}
