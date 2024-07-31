package mindustry.randomizer.client;

import dev.koifysh.archipelago.Print.APPrintJsonType;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import static mindustry.Vars.randomizer;

/**
 * Print Json message from Archipelago
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-29
 */
public class PrintJsonListener {
    @ArchipelagoEventListener
    public void onPrintJson(PrintJSONEvent event) {
        randomizer.sendLocalMessage(event.apPrint.getPlainText());
    }
}
