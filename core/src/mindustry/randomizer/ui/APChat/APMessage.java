package mindustry.randomizer.ui.APChat;

import dev.koifysh.archipelago.Print.APPrintPart;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import dev.koifysh.archipelago.flags.NetworkItem;
import mindustry.randomizer.enums.ItemsClassification;

import java.util.ArrayList;

/**
 * Archipelago message format for the chat box. Can be created from a PrintJSONEvent or a String.
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
        if (event.apPrint.parts.length != 0) {
            for (APPrintPart part : event.apPrint.parts) {
                this.message.add(part.text);
            }
        }
        isItemMessage = false;
        switch (event.type) {
            case ItemSend:
                isItemMessage = true;
                classification = getItemClassification(event);
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
     * Get item classification from event.
     * @param event The event containing the item.
     * @return Return the classification of the item.
     */
    private ItemsClassification getItemClassification(PrintJSONEvent event) {
        ItemsClassification classification = ItemsClassification.USEFUL;
        if (event.item.flags == NetworkItem.ADVANCEMENT) {
            classification = ItemsClassification.PROGRESSION;
        } else if (event.item.flags == NetworkItem.USEFUL) {
            classification = ItemsClassification.USEFUL;
        } else if (event.item.flags == NetworkItem.TRAP) {
            classification = ItemsClassification.TRAP;
        }
        return classification;
    }
}
