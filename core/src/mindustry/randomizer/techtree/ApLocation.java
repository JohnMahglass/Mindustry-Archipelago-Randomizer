package mindustry.randomizer.techtree;

import arc.Core;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Sounds;
import mindustry.randomizer.Shared;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;

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
    public UnlockableContent content;


    /**
     * Check the location. If the location has no id, add the location to the pending check list.
     */
    @Override
    public void unlock(){
        if(!unlocked()){
            unlocked = true;
            Sounds.unlock.play();
            Core.settings.put(name + "-unlocked", true);
            if (locationId != null) {
                randomizer.checkLocation(locationId);
                //DEBUG
                ui.chatfrag.addMessage(name + " checked");
            } else {
                //Error needs to be logged
            }
        }
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param content The content of the original node
     * @param locationId The id of the node.
     */
    public ApLocation(String name, UnlockableContent content, Long locationId) {
        super(name);
        this.locationId = Shared.MINDUSTRY_BASE_ID + locationId;
        this.content = content;
        if (content != null) {
            this.researchCost = content.researchRequirements();
        }
    }

    /**
     * Constructor of ApLocation
     * @param name The name of the node.
     * @param content The original content of the node.
     * @param locationId The id of the node.
     * @param itemId The id of the item contained within the node.
     */
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
