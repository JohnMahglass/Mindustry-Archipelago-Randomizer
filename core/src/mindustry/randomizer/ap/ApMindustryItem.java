package mindustry.randomizer.ap;

import mindustry.ctype.UnlockableContent;

/**
 * Contains information about an item from Mindustry used in the randomisation.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-02
 */
public class ApMindustryItem {

    int id;
    UnlockableContent content;
    String name;
    boolean isUpgradable;
    boolean isSector;
    boolean isMaterial;

    public ApMindustryItem(int id, UnlockableContent content, String name){
        this.id = id;
        this.content = content;
        this.name = name;
        isMaterial = false;
        isSector = false;
        isUpgradable = false;
    }

    public ApMindustryItem(int id, UnlockableContent content, String name, boolean material,
                           boolean sector, boolean upgradable){
        this.id = id;
        this.content = content;
        this.name = name;
        isMaterial = material;
        isSector = sector;
        isUpgradable = upgradable;
    }

}
