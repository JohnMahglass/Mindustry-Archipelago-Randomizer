package mindustry.randomizer.ui;

import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.Print.APPrintJsonType;
import dev.koifysh.archipelago.Print.APPrintPart;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import mindustry.Vars;
import mindustry.randomizer.WorldState;
import mindustry.randomizer.enums.ItemsClassification;

import java.util.ArrayList;

/**
 * APMessage
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-21
 */
public class APMessage {

    ArrayList<String> message;

    String plainText;

    boolean isItemMessage;

    ItemsClassification classification;

    /**
     * Create a message from an AP event.
     * @param event The event triggering the message.
     */
    public APMessage(PrintJSONEvent event) {
        this.message = new ArrayList<>();
        this.plainText = event.apPrint.getPlainText();
        if (event != null && event.apPrint.parts.length != 0) {
            for (APPrintPart part : event.apPrint.parts) {
                this.message.add(part.text);
            }
        }
        isItemMessage = false;
        switch (event.type) {
            case ItemSend:
                isItemMessage = true;
                classification = getItemClassification(event.item.itemID);
                break;
            default:
                break;
        }
    }

    /**
     * Create a message from a string.
     * @param message the string to be converted to an APMessage
     */
    public APMessage(String message) {
        this.message = new ArrayList<>();
        this.plainText = message;
        String[] parts = message.split(" ");
        for (int i = 0; i < parts.length; i++) {
            this.message.add(parts[i]);
        }
        isItemMessage = false;
    }

    /**
     * Get item classification from id.
     * @param id The id of the item.
     * @return Return the classification of the item.
     */
    private ItemsClassification getItemClassification(long id) {
        boolean isProgression = false;
        if (Vars.randomizer.worldState.progressionItems.contains(id)) {
            isProgression = true;
        }
        return isProgression ? ItemsClassification.PROGRESSION : ItemsClassification.USEFUL;
    }
}
