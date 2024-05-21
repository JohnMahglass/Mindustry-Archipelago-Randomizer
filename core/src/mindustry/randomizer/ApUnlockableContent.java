package mindustry.randomizer;

import arc.Core;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;

/**
 * ApUnlockableContent
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-20
 */
public abstract class ApUnlockableContent extends UnlockableContent {

    /**
     * The Archipelago location Id for this Mindustry location.
     */
    public int locationId;

    /**
     * The original name of the location for the tech tree.
     */
    public String originalLocationName;

    /**
     * Unlocks this content and send the check to archipelago
     */
    public void apUnlock(){
        if(!unlocked()){
            unlocked = true;
            //replace the code underneath this comment to send the location id to Archipelago
            Core.settings.put(name + "-unlocked", true);
        }
    }

    /**
     * Constructor for ApUnlockableContent.
     * @param name Name of the content.
     * @param originalLocationName Name of the original location.
     * @param locationId The Archipelago Mindustry location Id.
     */
    public ApUnlockableContent(String name, String originalLocationName, int locationId) {
        super(name);
        this.originalLocationName = originalLocationName;
        this.locationId = locationId;
    }


    @Override
    public ContentType getContentType() {
        //Content type is irrelevent but this is to prevent an error.
        return ContentType.block;
    }
}
