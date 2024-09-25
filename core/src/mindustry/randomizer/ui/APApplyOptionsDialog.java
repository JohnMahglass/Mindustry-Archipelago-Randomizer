package mindustry.randomizer.ui;

import arc.Core;
import arc.graphics.Color;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.BaseDialog;

/**
 * APApplyOptionsDialog
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-01
 */
public class APApplyOptionsDialog extends BaseDialog {
    public APApplyOptionsDialog(String title) {
        super(title);
        setup();
    }

    private void setup() {
        cont.margin(12f);

        cont.table(t -> {
            t.background(Tex.button).margin(0);
            t.row();
            t.add("Restart to apply randomizer option").color(Pal.accent).growX().padLeft(30f);
            t.row();
            t.add("" +
                "To properly apply the randomizer options it is required" + "\n" +
                "to reload the game when you successfully connect to a" + "\n" +
                "game for the first time. Press 'Exit' to close the program." +
                "").pad(10f);
            t.row();

            t.button("Exit", Icon.exit, () -> {
                Core.app.exit();
            }).size(170f, 50);
        }).size(610f, 170).pad(10f);
    }
}
