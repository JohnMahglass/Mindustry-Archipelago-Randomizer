package mindustry.randomizer;

import arc.Core;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
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


    public ApLocation parent;

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
    public String originalNodeName;

    /**
     * Check the location. If the location has no id, add the location to the pending check list.
     */
    @Override
    public void unlock(){
        if(!unlocked()){
            unlocked = true;
            Core.settings.put(name + "-unlocked", true);
            if (locationId != null) {
                randomizer.locationChecked(locationId);
            } else {
                //Error needs to be logged
            }

        }
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param originalNodeName The name the node used to have.
     * @param locationId The id of the node.
     */
    public ApLocation(String name, String originalNodeName, Long locationId) {
        super(name);
        this.locationId = locationId;
        this.originalNodeName = originalNodeName;
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param originalNodeName The name the node used to have.
     * @param locationId The id of the node.
     * @param itemId The id of the item contained within the node.
     */
    public ApLocation(String name, String originalNodeName, Long locationId, Long itemId) {
        super(name);
        this.locationId = locationId;
        this.itemId = itemId;
        this.originalNodeName = originalNodeName;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.block; //Content type irrevelent, this is to prevent an error being raised
    }

    public void setParent(ApLocation parent){
        this.parent = parent;
    }

    public ApLocation getParent(){
        return this.parent;
    }

    public boolean hasParent(){
        return (parent != null);
    }

    public void addItemToRequirement(Item item, int amount){
        int requirementAmount = researchCost.length + 1;
        ItemStack[] newResearchCost = new ItemStack[requirementAmount];
        if (researchCost.length > 0) {
            newResearchCost = researchCost.clone();
        }
        newResearchCost[requirementAmount] = new ItemStack(item, amount);
        this.researchCost = newResearchCost.clone();
    }

    private void initialiseParentRequirement(ApLocation location) {

    }
}
