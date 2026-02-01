package mindustry.randomizer.ui;

import arc.Core;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.randomizer.constant.RandomizerConstant;
import mindustry.ui.dialogs.BaseDialog;

/**
 * APApplyOptionsDialog
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-01
 */
public class APApplyOptionsDialog extends BaseDialog {
    public APApplyOptionsDialog() {
        super("Apply randomizer options");
        setup();
    }

    private void setup() {
        cont.margin(12f);

        cont.table(t -> {
            t.background(Tex.button).margin(0);
            t.row();
            t.labelWrap(RandomizerConstant.TITLE_APPLYING_RANDOMIZER_OPTION_RESTART).color(Pal.accent).growX().padLeft(110f).padTop(20f);
            t.row();
            t.labelWrap(RandomizerConstant.APPLYING_RANDOMIZER_OPTION_RESTART).pad(30f).left();
            t.row();

            t.button(RandomizerConstant.EXIT, Icon.exit, () -> {
                Core.app.exit();
            }).size(170f, 50);
        }).size(610f, 275).padLeft(10f).padRight(10f).padBottom(10f);
    }
}
