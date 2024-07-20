package mindustry.randomizer.techtree;

import arc.Core;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.Shared;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import static mindustry.Vars.randomizer;

/**
 * Node acting as a location for AP
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-20
 */
public class ApLocation extends Block {

    /**
     * id of the Archipelago location.
     */
    public Long locationId;

    /**
     * id of item within the node.
     */
    public Long itemId;

    /**
     * Original name of the node.
     */
    public UnlockableContent content;


    /**
     * Unlock the node and its content and send a check to Archipelago
     */
    @Override
    public void unlock(){
        if(!unlocked && !alwaysUnlocked){
            unlocked = true;
            Core.settings.put(name + "-unlocked", true);
            Core.settings.put(content.name + "-unlocked", true);

            if (locationId != null) {
                randomizer.checkLocation(locationId, name);
            } else {
                //Error needs to be logged
            }
        }
    }


    public ApLocation(String name, UnlockableContent content, Long locationId) {
        super(name);
        this.locationId = Shared.MINDUSTRY_BASE_ID + locationId;
        this.content = content;
        if (content != null) {
            this.researchCost = content.researchRequirements();
        }
    }

    public ApLocation(String name, UnlockableContent content, Long locationId, Long itemId) {
        super(name);
        this.locationId = Shared.MINDUSTRY_BASE_ID + locationId;
        this.itemId = itemId;
        this.content = content;
        if (content != null) {
            this.researchCost = content.researchRequirements();
        }
    }

    @Override
    public ContentType getContentType() {
        return ContentType.block; //Content type irrevelent, this is to prevent an error being raised
    }

}
