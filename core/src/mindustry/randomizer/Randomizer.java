package mindustry.randomizer;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.SectorPresets;
import mindustry.content.UnitTypes;
import mindustry.ctype.UnlockableContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Randomizer for Archipelago multiworld randomizer.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-12
 */
public class Randomizer {

    /**
     * Contains the options of the generated game.
     */
    public MindustryOptions options;

    /**
     * All UnlockableContent with their matching Id
     */
    public Map<Integer, UnlockableContent> items;

    /**
     * UnlockableContent that are already unlocked with their matching Id
     */
    public Map<Integer, UnlockableContent> unlockedItems;

    /**
     * Unlock a UnlockableContent.
     */
    public void unlock(int id){
        UnlockableContent content = getUnlockableContent(id);
        unlockedItems.put(id, content);
        content.randomizerUnlock();
    }

    /**
     * Checks whether the plays has received this item.
     * @param id The Id of the item to be checked.
     * @return Return True if the player has this item.
     */
    public boolean hasItem(int id){
        boolean itemReceived = false;
        if (unlockedItems.get(id) != null) {
            itemReceived = true;
        }
        return itemReceived;
    }

    /**
     * Forward the check to Archipelago, if the item is a Mindustry item, unlock it.
     * @param locationId The Id of the location
     * @param itemId The itemId that is contained in the location
     */
    public void locationChecked(int locationId, int itemId){
        if (itemId >= Shared.MINDUSTRY_BASE_ID && itemId <= Shared.MINDUSTRY_BASE_ID + 1000) {
            unlock(itemId);
        }
        //send the check to archipelago
    }

    /**
     * Check if the item is a sector
     * @param id The Id of the item
     * @return Return True if the item is a sector
     */
    public boolean isSector(int id){
        return (id >= Shared.MINDUSTRY_BASE_ID + 166 && id <= Shared.MINDUSTRY_BASE_ID + 182);
    }

    /**
     * Constructor for Randomizer
     */
    public Randomizer(){
        this.options = new MindustryOptions();
        this.items = new HashMap<Integer, UnlockableContent>();
        this.unlockedItems  = new HashMap<Integer, UnlockableContent>();
    }

    /**
     * Add an item to the randomizer's item list.
     * @param id The id of the item WITHOUT the base id.
     * @param content The content representing the item.
     */
    private void addItem(int id, UnlockableContent content) {
        items.put(id, content);
    }

    /**
     * Return UnlockableContent matching the itemId.
     * @param itemId The itemId of the item.
     * @return The UnlockableContent matching the itemId, or null if no match.
     */
    private UnlockableContent itemIdToUnlockableContent(int itemId) {
        UnlockableContent content = null;
        if (items.get(itemId) != null) {
            content = items.get(itemId);
        }
        return content;
    }

    /**
     * Initialize the randomizer's list of item depending on the selected campaign
     */
    public void initialize() {
        switch (options.getCampaignChoice()) {
            case SERPULO:
                initializeSerpuloItems();
                //placeItemsIntoLocations();
                break;
            case EREKIR:
                initializeErekirItems();
                break;
            case ALL:
                initializeAllItems();
                break;
            default:
                throw new RuntimeException("Invalid CampaignType");
        }

    }

    /**
     * Place item into their location
     * @param locationId The location's id.
     * @param itemId The item's id.
     */
    private void placeItemsIntoLocations(int locationId, int itemId) {
        //Method not implemented
    }

    private UnlockableContent getUnlockableContent(int id) {
        return items.get(id);
    }

    /**
     * Initialize item from all campaigns.
     */
    private void initializeAllItems() {
    }

    /**
     * Initialize item from Erekir campaign.
     */
    private void initializeErekirItems() {
    }

    /**
     * Initialize item from Serpulo campaign.
     */
    private void initializeSerpuloItems() {
        addItem(Shared.MINDUSTRY_BASE_ID + 0, Blocks.coreFoundation);
        addItem(Shared.MINDUSTRY_BASE_ID + 1, Blocks.coreNucleus);
        addItem(Shared.MINDUSTRY_BASE_ID + 2, Blocks.conveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 3, Blocks.junction);
        addItem(Shared.MINDUSTRY_BASE_ID + 4, Blocks.router);
        addItem(Shared.MINDUSTRY_BASE_ID + 5, Blocks.launchPad);
        addItem(Shared.MINDUSTRY_BASE_ID + 6, Blocks.distributor);
        addItem(Shared.MINDUSTRY_BASE_ID + 7, Blocks.sorter);
        addItem(Shared.MINDUSTRY_BASE_ID + 8, Blocks.invertedSorter);
        addItem(Shared.MINDUSTRY_BASE_ID + 9, Blocks.overflowGate);
        addItem(Shared.MINDUSTRY_BASE_ID + 10, Blocks.underflowGate);
        addItem(Shared.MINDUSTRY_BASE_ID + 11, Blocks.container);
        addItem(Shared.MINDUSTRY_BASE_ID + 12, Blocks.unloader);
        addItem(Shared.MINDUSTRY_BASE_ID + 13, Blocks.vault);
        addItem(Shared.MINDUSTRY_BASE_ID + 14, Blocks.itemBridge);
        addItem(Shared.MINDUSTRY_BASE_ID + 15, Blocks.titaniumConveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 16, Blocks.phaseConveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 17, Blocks.massDriver);
        addItem(Shared.MINDUSTRY_BASE_ID + 18, Blocks.payloadConveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 19, Blocks.payloadRouter);
        addItem(Shared.MINDUSTRY_BASE_ID + 20, Blocks.armoredConveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 21, Blocks.plastaniumConveyor);
        addItem(Shared.MINDUSTRY_BASE_ID + 22, Blocks.mechanicalDrill);
        addItem(Shared.MINDUSTRY_BASE_ID + 23, Blocks.mechanicalPump);
        addItem(Shared.MINDUSTRY_BASE_ID + 24, Blocks.conduit);
        addItem(Shared.MINDUSTRY_BASE_ID + 25, Blocks.liquidJunction);
        addItem(Shared.MINDUSTRY_BASE_ID + 26, Blocks.liquidRouter);
        addItem(Shared.MINDUSTRY_BASE_ID + 27, Blocks.liquidContainer);
        addItem(Shared.MINDUSTRY_BASE_ID + 28, Blocks.liquidTank);
        addItem(Shared.MINDUSTRY_BASE_ID + 29, Blocks.bridgeConduit);
        addItem(Shared.MINDUSTRY_BASE_ID + 30, Blocks.pulseConduit);
        addItem(Shared.MINDUSTRY_BASE_ID + 31, Blocks.phaseConduit);
        addItem(Shared.MINDUSTRY_BASE_ID + 32, Blocks.platedConduit);
        addItem(Shared.MINDUSTRY_BASE_ID + 33, Blocks.rotaryPump);
        addItem(Shared.MINDUSTRY_BASE_ID + 34, Blocks.impulsePump);
        addItem(Shared.MINDUSTRY_BASE_ID + 35, Blocks.graphitePress);
        addItem(Shared.MINDUSTRY_BASE_ID + 36, Blocks.pneumaticDrill);
        addItem(Shared.MINDUSTRY_BASE_ID + 37, Blocks.cultivator);
        addItem(Shared.MINDUSTRY_BASE_ID + 38, Blocks.laserDrill);
        addItem(Shared.MINDUSTRY_BASE_ID + 39, Blocks.blastDrill);
        addItem(Shared.MINDUSTRY_BASE_ID + 40, Blocks.waterExtractor);
        addItem(Shared.MINDUSTRY_BASE_ID + 41, Blocks.oilExtractor);
        addItem(Shared.MINDUSTRY_BASE_ID + 42, Blocks.pyratiteMixer);
        addItem(Shared.MINDUSTRY_BASE_ID + 43, Blocks.blastMixer);
        addItem(Shared.MINDUSTRY_BASE_ID + 44, Blocks.siliconSmelter);
        addItem(Shared.MINDUSTRY_BASE_ID + 45, Blocks.sporePress);
        addItem(Shared.MINDUSTRY_BASE_ID + 46, Blocks.coalCentrifuge);
        addItem(Shared.MINDUSTRY_BASE_ID + 47, Blocks.multiPress);
        addItem(Shared.MINDUSTRY_BASE_ID + 48, Blocks.siliconCrucible);
        addItem(Shared.MINDUSTRY_BASE_ID + 49, Blocks.plastaniumCompressor);
        addItem(Shared.MINDUSTRY_BASE_ID + 50, Blocks.phaseWeaver);
        addItem(Shared.MINDUSTRY_BASE_ID + 51, Blocks.kiln);
        addItem(Shared.MINDUSTRY_BASE_ID + 52, Blocks.pulverizer);
        addItem(Shared.MINDUSTRY_BASE_ID + 53, Blocks.incinerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 54, Blocks.melter);
        addItem(Shared.MINDUSTRY_BASE_ID + 55, Blocks.surgeSmelter);
        addItem(Shared.MINDUSTRY_BASE_ID + 56, Blocks.separator);
        addItem(Shared.MINDUSTRY_BASE_ID + 57, Blocks.disassembler);
        addItem(Shared.MINDUSTRY_BASE_ID + 58, Blocks.cryofluidMixer);
        addItem(Shared.MINDUSTRY_BASE_ID + 59, Blocks.microProcessor);
        addItem(Shared.MINDUSTRY_BASE_ID + 60, Blocks.switchBlock);
        addItem(Shared.MINDUSTRY_BASE_ID + 61, Blocks.message);
        addItem(Shared.MINDUSTRY_BASE_ID + 62, Blocks.logicDisplay);
        addItem(Shared.MINDUSTRY_BASE_ID + 63, Blocks.largeLogicDisplay);
        addItem(Shared.MINDUSTRY_BASE_ID + 64, Blocks.memoryCell);
        addItem(Shared.MINDUSTRY_BASE_ID + 65, Blocks.memoryBank);
        addItem(Shared.MINDUSTRY_BASE_ID + 66, Blocks.logicProcessor);
        addItem(Shared.MINDUSTRY_BASE_ID + 67, Blocks.hyperProcessor);
        addItem(Shared.MINDUSTRY_BASE_ID + 68, Blocks.illuminator);
        addItem(Shared.MINDUSTRY_BASE_ID + 69, Blocks.combustionGenerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 70, Blocks.powerNode);
        addItem(Shared.MINDUSTRY_BASE_ID + 71, Blocks.powerNodeLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 72, Blocks.diode);
        addItem(Shared.MINDUSTRY_BASE_ID + 73, Blocks.surgeTower);
        addItem(Shared.MINDUSTRY_BASE_ID + 74, Blocks.battery);
        addItem(Shared.MINDUSTRY_BASE_ID + 75, Blocks.batteryLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 76, Blocks.mender);
        addItem(Shared.MINDUSTRY_BASE_ID + 77, Blocks.mendProjector);
        addItem(Shared.MINDUSTRY_BASE_ID + 78, Blocks.forceProjector);
        addItem(Shared.MINDUSTRY_BASE_ID + 79, Blocks.overdriveProjector);
        addItem(Shared.MINDUSTRY_BASE_ID + 80, Blocks.overdriveDome);
        addItem(Shared.MINDUSTRY_BASE_ID + 81, Blocks.repairPoint);
        addItem(Shared.MINDUSTRY_BASE_ID + 82, Blocks.repairTurret);
        addItem(Shared.MINDUSTRY_BASE_ID + 83, Blocks.steamGenerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 84, Blocks.thermalGenerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 85, Blocks.differentialGenerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 86, Blocks.thoriumReactor);
        addItem(Shared.MINDUSTRY_BASE_ID + 87, Blocks.impactReactor);
        addItem(Shared.MINDUSTRY_BASE_ID + 88, Blocks.rtgGenerator);
        addItem(Shared.MINDUSTRY_BASE_ID + 89, Blocks.solarPanel);
        addItem(Shared.MINDUSTRY_BASE_ID + 90, Blocks.largeSolarPanel);
        addItem(Shared.MINDUSTRY_BASE_ID + 91, Blocks.duo);
        addItem(Shared.MINDUSTRY_BASE_ID + 92, Blocks.copperWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 93, Blocks.copperWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 94, Blocks.titaniumWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 95, Blocks.titaniumWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 96, Blocks.door);
        addItem(Shared.MINDUSTRY_BASE_ID + 97, Blocks.doorLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 98, Blocks.plastaniumWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 99, Blocks.plastaniumWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 100, Blocks.thoriumWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 101, Blocks.thoriumWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 102, Blocks.surgeWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 103, Blocks.surgeWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 104, Blocks.phaseWall);
        addItem(Shared.MINDUSTRY_BASE_ID + 105, Blocks.phaseWallLarge);
        addItem(Shared.MINDUSTRY_BASE_ID + 106, Blocks.scatter);
        addItem(Shared.MINDUSTRY_BASE_ID + 107, Blocks.hail);
        addItem(Shared.MINDUSTRY_BASE_ID + 108, Blocks.salvo);
        addItem(Shared.MINDUSTRY_BASE_ID + 109, Blocks.swarmer);
        addItem(Shared.MINDUSTRY_BASE_ID + 110, Blocks.cyclone);
        addItem(Shared.MINDUSTRY_BASE_ID + 111, Blocks.spectre);
        addItem(Shared.MINDUSTRY_BASE_ID + 112, Blocks.ripple);
        addItem(Shared.MINDUSTRY_BASE_ID + 113, Blocks.fuse);
        addItem(Shared.MINDUSTRY_BASE_ID + 114, Blocks.scorch);
        addItem(Shared.MINDUSTRY_BASE_ID + 115, Blocks.arc);
        addItem(Shared.MINDUSTRY_BASE_ID + 116, Blocks.wave);
        addItem(Shared.MINDUSTRY_BASE_ID + 117, Blocks.parallax);
        addItem(Shared.MINDUSTRY_BASE_ID + 118, Blocks.segment);
        addItem(Shared.MINDUSTRY_BASE_ID + 119, Blocks.tsunami);
        addItem(Shared.MINDUSTRY_BASE_ID + 120, Blocks.lancer);
        addItem(Shared.MINDUSTRY_BASE_ID + 121, Blocks.meltdown);
        addItem(Shared.MINDUSTRY_BASE_ID + 122, Blocks.foreshadow);
        addItem(Shared.MINDUSTRY_BASE_ID + 123, Blocks.shockMine);
        addItem(Shared.MINDUSTRY_BASE_ID + 124, Blocks.groundFactory);
        addItem(Shared.MINDUSTRY_BASE_ID + 125, UnitTypes.dagger);
        addItem(Shared.MINDUSTRY_BASE_ID + 126, UnitTypes.mace);
        addItem(Shared.MINDUSTRY_BASE_ID + 127, UnitTypes.fortress);
        addItem(Shared.MINDUSTRY_BASE_ID + 128, UnitTypes.scepter);
        addItem(Shared.MINDUSTRY_BASE_ID + 129, UnitTypes.reign);
        addItem(Shared.MINDUSTRY_BASE_ID + 130, UnitTypes.nova);
        addItem(Shared.MINDUSTRY_BASE_ID + 131, UnitTypes.pulsar);
        addItem(Shared.MINDUSTRY_BASE_ID + 132, UnitTypes.quasar);
        addItem(Shared.MINDUSTRY_BASE_ID + 133, UnitTypes.vela);
        addItem(Shared.MINDUSTRY_BASE_ID + 134, UnitTypes.corvus);
        addItem(Shared.MINDUSTRY_BASE_ID + 135, UnitTypes.crawler);
        addItem(Shared.MINDUSTRY_BASE_ID + 136, UnitTypes.atrax);
        addItem(Shared.MINDUSTRY_BASE_ID + 137, UnitTypes.spiroct);
        addItem(Shared.MINDUSTRY_BASE_ID + 138, UnitTypes.arkyid);
        addItem(Shared.MINDUSTRY_BASE_ID + 139, UnitTypes.toxopid);
        addItem(Shared.MINDUSTRY_BASE_ID + 140, Blocks.airFactory);
        addItem(Shared.MINDUSTRY_BASE_ID + 141, UnitTypes.flare);
        addItem(Shared.MINDUSTRY_BASE_ID + 142, UnitTypes.horizon);
        addItem(Shared.MINDUSTRY_BASE_ID + 143, UnitTypes.zenith);
        addItem(Shared.MINDUSTRY_BASE_ID + 144, UnitTypes.antumbra);
        addItem(Shared.MINDUSTRY_BASE_ID + 145, UnitTypes.eclipse);
        addItem(Shared.MINDUSTRY_BASE_ID + 146, UnitTypes.mono);
        addItem(Shared.MINDUSTRY_BASE_ID + 147, UnitTypes.poly);
        addItem(Shared.MINDUSTRY_BASE_ID + 148, UnitTypes.mega);
        addItem(Shared.MINDUSTRY_BASE_ID + 149, UnitTypes.quad);
        addItem(Shared.MINDUSTRY_BASE_ID + 150, UnitTypes.oct);
        addItem(Shared.MINDUSTRY_BASE_ID + 151, Blocks.navalFactory);
        addItem(Shared.MINDUSTRY_BASE_ID + 152, UnitTypes.risso);
        addItem(Shared.MINDUSTRY_BASE_ID + 153, UnitTypes.minke);
        addItem(Shared.MINDUSTRY_BASE_ID + 154, UnitTypes.bryde);
        addItem(Shared.MINDUSTRY_BASE_ID + 155, UnitTypes.sei);
        addItem(Shared.MINDUSTRY_BASE_ID + 156, UnitTypes.omura);
        addItem(Shared.MINDUSTRY_BASE_ID + 157, UnitTypes.retusa);
        addItem(Shared.MINDUSTRY_BASE_ID + 158, UnitTypes.oxynoe);
        addItem(Shared.MINDUSTRY_BASE_ID + 159, UnitTypes.cyerce);
        addItem(Shared.MINDUSTRY_BASE_ID + 160, UnitTypes.aegires);
        addItem(Shared.MINDUSTRY_BASE_ID + 161, UnitTypes.navanax);
        addItem(Shared.MINDUSTRY_BASE_ID + 162, Blocks.additiveReconstructor);
        addItem(Shared.MINDUSTRY_BASE_ID + 163, Blocks.multiplicativeReconstructor);
        addItem(Shared.MINDUSTRY_BASE_ID + 164, Blocks.exponentialReconstructor);
        addItem(Shared.MINDUSTRY_BASE_ID + 165, Blocks.tetrativeReconstructor);
        addItem(Shared.MINDUSTRY_BASE_ID + 166, SectorPresets.frozenForest);
        addItem(Shared.MINDUSTRY_BASE_ID + 167, SectorPresets.craters);
        addItem(Shared.MINDUSTRY_BASE_ID + 168, SectorPresets.ruinousShores);
        addItem(Shared.MINDUSTRY_BASE_ID + 169, SectorPresets.windsweptIslands);
        addItem(Shared.MINDUSTRY_BASE_ID + 170, SectorPresets.tarFields);
        addItem(Shared.MINDUSTRY_BASE_ID + 171, SectorPresets.impact0078);
        addItem(Shared.MINDUSTRY_BASE_ID + 172, SectorPresets.desolateRift);
        addItem(Shared.MINDUSTRY_BASE_ID + 173, SectorPresets.planetaryTerminal);
        addItem(Shared.MINDUSTRY_BASE_ID + 174, SectorPresets.extractionOutpost);
        addItem(Shared.MINDUSTRY_BASE_ID + 175, SectorPresets.saltFlats);
        addItem(Shared.MINDUSTRY_BASE_ID + 176, SectorPresets.coastline);
        addItem(Shared.MINDUSTRY_BASE_ID + 177, SectorPresets.navalFortress);
        addItem(Shared.MINDUSTRY_BASE_ID + 178, SectorPresets.overgrowth);
        addItem(Shared.MINDUSTRY_BASE_ID + 179, SectorPresets.biomassFacility);
        addItem(Shared.MINDUSTRY_BASE_ID + 180, SectorPresets.stainedMountains);
        addItem(Shared.MINDUSTRY_BASE_ID + 181, SectorPresets.fungalPass);
        addItem(Shared.MINDUSTRY_BASE_ID + 182, SectorPresets.nuclearComplex);
        addItem(Shared.MINDUSTRY_BASE_ID + 183, Items.lead);
        addItem(Shared.MINDUSTRY_BASE_ID + 184, Items.titanium);
        addItem(Shared.MINDUSTRY_BASE_ID + 185, Liquids.cryofluid);
        addItem(Shared.MINDUSTRY_BASE_ID + 186, Items.thorium);
        addItem(Shared.MINDUSTRY_BASE_ID + 187, Items.surgeAlloy);
        addItem(Shared.MINDUSTRY_BASE_ID + 188, Items.phaseFabric);
        addItem(Shared.MINDUSTRY_BASE_ID + 189, Items.metaglass);
        addItem(Shared.MINDUSTRY_BASE_ID + 190, Items.scrap);
        addItem(Shared.MINDUSTRY_BASE_ID + 191, Liquids.slag);
        addItem(Shared.MINDUSTRY_BASE_ID + 192, Items.coal);
        addItem(Shared.MINDUSTRY_BASE_ID + 193, Items.graphite);
        addItem(Shared.MINDUSTRY_BASE_ID + 194, Items.silicon);
        addItem(Shared.MINDUSTRY_BASE_ID + 195, Items.pyratite);
        addItem(Shared.MINDUSTRY_BASE_ID + 196, Items.blastCompound);
        addItem(Shared.MINDUSTRY_BASE_ID + 197, Items.sporePod);
        addItem(Shared.MINDUSTRY_BASE_ID + 198, Liquids.oil);
        addItem(Shared.MINDUSTRY_BASE_ID + 199, Items.plastanium);
    }

}
