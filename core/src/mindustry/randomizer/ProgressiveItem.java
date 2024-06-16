package mindustry.randomizer;

import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.enums.ProgressiveItemType;

import java.util.ArrayList;

/**
 * ProgressiveItem
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-16
 */
public class ProgressiveItem {

    public ProgressiveItemType type;
    public Long id;
    public int itemAmount;
    public ArrayList<UnlockableContent> items;
    public int amountItemReceived;
    public boolean allReceived;


    public ProgressiveItem(ProgressiveItemType type, Long id, int itemAmount) {
        this.type = type;
        this.itemAmount = itemAmount;
        this.id = id;
        this.items = new ArrayList<>();
        this.amountItemReceived = 0;
        this.allReceived = false;
    }
}
