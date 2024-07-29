package mindustry.randomizer;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.SectorPresets;
import mindustry.content.UnitTypes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.enums.ProgressiveItemType;
import mindustry.randomizer.techtree.ApLocation;

import static arc.Core.settings;


import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

/**
 * Contain and update information about what has been done within this World.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-26
 */
public class WorldState {

    /**
     * Name of the file containing locations that are pending.
     */
    public String checkPendingFile = "RandomizerCheckPending.txt";

    /**
     * List of all progressive items.
     */
    public ArrayList<ProgressiveItem> progressiveItems;

    /**
     * List of all locations used in the randomisation.
     */
    public Map<Integer, String> locations;

    /**
     * List of locations that have been checked and successfully sent.
     */
    public ArrayList<Long> locationsChecked;

    /**
     * List of checked locations that have not been successfully sent.
     */
    public ArrayList<Long> checkPending;

    /**
     * List of all locations.
     */
    public ArrayList<ApLocation> apLocations;

    /**
     * All UnlockableContent with their matching id
     */
    public Map<Long, UnlockableContent> items;

    /**
     * Randomized items that have been received.
     */
    public Long[] unlockedItems;

    /**
     * Contains the options of the generated game.
     */
    public MindustryOptions options;


    /**
     * True if there is a check waiting to be sent to the server.
     * @return True if a check is pending.
     */
    public boolean hasCheckPending(){
        if (checkPending.isEmpty() || checkPending == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Save every state locally.
     */
    public void saveStates(){
        saveState(checkPendingFile, checkPending);
    }

    protected void resetWorldState() {
        wipeStates();
        settings.remove("APhasConnected");
        settings.remove("APdeathLink");
        settings.remove("APtutorialSkip");
        settings.remove("APdisableInvasions");
        settings.remove("APsectorBehavior");
        settings.remove("APressourceBehavior");
        settings.remove("APcampaignChoice");
        settings.remove("APaddress");
        settings.remove("APslotName");
        settings.remove("APpassword");
        settings.remove("APfreeLaunchSerpulo");
        settings.remove("APfreeLaunchErekir");
    }

    /**
     * Wipe every states.
     */
    private void wipeStates() {
        wipeState(checkPendingFile, checkPending);
        progressiveItems.clear();
        locationsChecked.clear();
    }


    public WorldState() {
        this.options = new MindustryOptions();
        this.items = new HashMap<>();
        this.unlockedItems = null;
        this.locations = new HashMap<>();
        this.locationsChecked = new ArrayList<>();
        this.checkPending = new ArrayList<>();
        this.progressiveItems = new ArrayList<>();
        this.apLocations = new ArrayList<>();
        loadStates();
    }


    /**
     * Save current state to the save file.
     */
    private void saveState(String fileName,ArrayList<?> state){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            if (state != null) {
                oos.writeObject(state);
            }

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load every saved state
     */
    private void loadStates(){
        checkPending = loadState(checkPendingFile);
    }

    /**
     * Load the saved state.
     */
    private <T> ArrayList<T> loadState(String fileName) {
        ArrayList<T> loadedState = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                loadedState = (ArrayList<T>) obj;
            }

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            loadedState = new ArrayList<>();
        }
        return loadedState;
    }

    /**
     * Wipe local state.
     * @param fileName The file to be wiped
     * @param state The array to be wiped.
     */
    private void wipeState(String fileName, ArrayList state) {
        state.clear();
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(state);

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Add a check to an array. Note that the state should be saved after using this method by
     * calling saveState().
     * @param stateArray
     * @param newCheck
     */
    protected void addCheck(ArrayList<Long> stateArray, Long newCheck){
        boolean checkExist = false;
        for (Long check : stateArray) {
            if (check.equals(newCheck)) {
               checkExist = true;
            }
        }
        if (!checkExist) {
            stateArray.add(newCheck);
        } else {
            //log error
        }
    }

    protected boolean isProgressive(Long itemId) {
        boolean progressive = false;
        for (ProgressiveItem item : progressiveItems) {
            if (item.id.equals(itemId)) {
                progressive = true;
            }
        }
        return progressive;
    }

    /**
     * Initialize items for all campaigns.
     */
    protected void initializeAllItems() {
        //Method not implemented
    }

    /**
     * Initialize item from Erekir campaign.
     */
    protected void initializeErekirItems() {
        //Method not implemented
    }

    /**
     * Initialize item from Serpulo campaign.
     */
    protected void initializeSerpuloItems() {
        items.put(MINDUSTRY_BASE_ID + 0, Blocks.conveyor);
        items.put(MINDUSTRY_BASE_ID + 1, Blocks.junction);
        items.put(MINDUSTRY_BASE_ID + 2, Blocks.router);
        items.put(MINDUSTRY_BASE_ID + 3, Blocks.launchPad);
        items.put(MINDUSTRY_BASE_ID + 4, Blocks.distributor);
        items.put(MINDUSTRY_BASE_ID + 5, Blocks.sorter);
        items.put(MINDUSTRY_BASE_ID + 6, Blocks.invertedSorter);
        items.put(MINDUSTRY_BASE_ID + 7, Blocks.overflowGate);
        items.put(MINDUSTRY_BASE_ID + 8, Blocks.underflowGate);
        items.put(MINDUSTRY_BASE_ID + 9, Blocks.container);
        items.put(MINDUSTRY_BASE_ID + 10, Blocks.unloader);
        items.put(MINDUSTRY_BASE_ID + 11, Blocks.vault);
        items.put(MINDUSTRY_BASE_ID + 12, Blocks.itemBridge);
        items.put(MINDUSTRY_BASE_ID + 13, Blocks.titaniumConveyor);
        items.put(MINDUSTRY_BASE_ID + 14, Blocks.phaseConveyor);
        items.put(MINDUSTRY_BASE_ID + 15, Blocks.massDriver);
        items.put(MINDUSTRY_BASE_ID + 16, Blocks.payloadConveyor);
        items.put(MINDUSTRY_BASE_ID + 17, Blocks.payloadRouter);
        items.put(MINDUSTRY_BASE_ID + 18, Blocks.armoredConveyor);
        items.put(MINDUSTRY_BASE_ID + 19, Blocks.plastaniumConveyor);
        items.put(MINDUSTRY_BASE_ID + 20, Blocks.coreFoundation);
        items.put(MINDUSTRY_BASE_ID + 21, Blocks.coreNucleus);
        items.put(MINDUSTRY_BASE_ID + 22, Blocks.mechanicalDrill);
        items.put(MINDUSTRY_BASE_ID + 23, Blocks.mechanicalPump);
        items.put(MINDUSTRY_BASE_ID + 24, Blocks.conduit);
        items.put(MINDUSTRY_BASE_ID + 25, Blocks.liquidJunction);
        items.put(MINDUSTRY_BASE_ID + 26, Blocks.liquidRouter);
        items.put(MINDUSTRY_BASE_ID + 27, Blocks.liquidContainer);
        items.put(MINDUSTRY_BASE_ID + 28, Blocks.liquidTank);
        items.put(MINDUSTRY_BASE_ID + 29, Blocks.bridgeConduit);
        items.put(MINDUSTRY_BASE_ID + 30, Blocks.pulseConduit);
        items.put(MINDUSTRY_BASE_ID + 31, Blocks.phaseConduit);
        items.put(MINDUSTRY_BASE_ID + 32, Blocks.platedConduit);
        items.put(MINDUSTRY_BASE_ID + 33, Blocks.rotaryPump);
        items.put(MINDUSTRY_BASE_ID + 34, Blocks.impulsePump);
        items.put(MINDUSTRY_BASE_ID + 35, Blocks.graphitePress);
        items.put(MINDUSTRY_BASE_ID + 36, Blocks.pneumaticDrill);
        items.put(MINDUSTRY_BASE_ID + 37, Blocks.cultivator);
        items.put(MINDUSTRY_BASE_ID + 38, Blocks.laserDrill);
        items.put(MINDUSTRY_BASE_ID + 39, Blocks.blastDrill);
        items.put(MINDUSTRY_BASE_ID + 40, Blocks.waterExtractor);
        items.put(MINDUSTRY_BASE_ID + 41, Blocks.oilExtractor);
        items.put(MINDUSTRY_BASE_ID + 42, Blocks.pyratiteMixer);
        items.put(MINDUSTRY_BASE_ID + 43, Blocks.blastMixer);
        items.put(MINDUSTRY_BASE_ID + 44, Blocks.siliconSmelter);
        items.put(MINDUSTRY_BASE_ID + 45, Blocks.sporePress);
        items.put(MINDUSTRY_BASE_ID + 46, Blocks.coalCentrifuge);
        items.put(MINDUSTRY_BASE_ID + 47, Blocks.multiPress);
        items.put(MINDUSTRY_BASE_ID + 48, Blocks.siliconCrucible);
        items.put(MINDUSTRY_BASE_ID + 49, Blocks.plastaniumCompressor);
        items.put(MINDUSTRY_BASE_ID + 50, Blocks.phaseWeaver);
        items.put(MINDUSTRY_BASE_ID + 51, Blocks.kiln);
        items.put(MINDUSTRY_BASE_ID + 52, Blocks.pulverizer);
        items.put(MINDUSTRY_BASE_ID + 53, Blocks.incinerator);
        items.put(MINDUSTRY_BASE_ID + 54, Blocks.melter);
        items.put(MINDUSTRY_BASE_ID + 55, Blocks.surgeSmelter);
        items.put(MINDUSTRY_BASE_ID + 56, Blocks.separator);
        items.put(MINDUSTRY_BASE_ID + 57, Blocks.disassembler);
        items.put(MINDUSTRY_BASE_ID + 58, Blocks.cryofluidMixer);
        items.put(MINDUSTRY_BASE_ID + 59, Blocks.microProcessor);
        items.put(MINDUSTRY_BASE_ID + 60, Blocks.switchBlock);
        items.put(MINDUSTRY_BASE_ID + 61, Blocks.message);
        items.put(MINDUSTRY_BASE_ID + 62, Blocks.logicDisplay);
        items.put(MINDUSTRY_BASE_ID + 63, Blocks.largeLogicDisplay);
        items.put(MINDUSTRY_BASE_ID + 64, Blocks.memoryCell);
        items.put(MINDUSTRY_BASE_ID + 65, Blocks.memoryBank);
        items.put(MINDUSTRY_BASE_ID + 66, Blocks.logicProcessor);
        items.put(MINDUSTRY_BASE_ID + 67, Blocks.hyperProcessor);
        items.put(MINDUSTRY_BASE_ID + 68, Blocks.illuminator);
        items.put(MINDUSTRY_BASE_ID + 69, Blocks.combustionGenerator);
        items.put(MINDUSTRY_BASE_ID + 70, Blocks.powerNode);
        items.put(MINDUSTRY_BASE_ID + 71, Blocks.powerNodeLarge);
        items.put(MINDUSTRY_BASE_ID + 72, Blocks.diode);
        items.put(MINDUSTRY_BASE_ID + 73, Blocks.surgeTower);
        items.put(MINDUSTRY_BASE_ID + 74, Blocks.battery);
        items.put(MINDUSTRY_BASE_ID + 75, Blocks.batteryLarge);
        items.put(MINDUSTRY_BASE_ID + 76, Blocks.mender);
        items.put(MINDUSTRY_BASE_ID + 77, Blocks.mendProjector);
        items.put(MINDUSTRY_BASE_ID + 78, Blocks.forceProjector);
        items.put(MINDUSTRY_BASE_ID + 79, Blocks.overdriveProjector);
        items.put(MINDUSTRY_BASE_ID + 80, Blocks.overdriveDome);
        items.put(MINDUSTRY_BASE_ID + 81, Blocks.repairPoint);
        items.put(MINDUSTRY_BASE_ID + 82, Blocks.repairTurret);
        items.put(MINDUSTRY_BASE_ID + 83, Blocks.steamGenerator);
        items.put(MINDUSTRY_BASE_ID + 84, Blocks.thermalGenerator);
        items.put(MINDUSTRY_BASE_ID + 85, Blocks.differentialGenerator);
        items.put(MINDUSTRY_BASE_ID + 86, Blocks.thoriumReactor);
        items.put(MINDUSTRY_BASE_ID + 87, Blocks.impactReactor);
        items.put(MINDUSTRY_BASE_ID + 88, Blocks.rtgGenerator);
        items.put(MINDUSTRY_BASE_ID + 89, Blocks.solarPanel);
        items.put(MINDUSTRY_BASE_ID + 90, Blocks.largeSolarPanel);
        items.put(MINDUSTRY_BASE_ID + 91, Blocks.duo);
        items.put(MINDUSTRY_BASE_ID + 92, Blocks.copperWall);
        items.put(MINDUSTRY_BASE_ID + 93, Blocks.copperWallLarge);
        items.put(MINDUSTRY_BASE_ID + 94, Blocks.titaniumWall);
        items.put(MINDUSTRY_BASE_ID + 95, Blocks.titaniumWallLarge);
        items.put(MINDUSTRY_BASE_ID + 96, Blocks.door);
        items.put(MINDUSTRY_BASE_ID + 97, Blocks.doorLarge);
        items.put(MINDUSTRY_BASE_ID + 98, Blocks.plastaniumWall);
        items.put(MINDUSTRY_BASE_ID + 99, Blocks.plastaniumWallLarge);
        items.put(MINDUSTRY_BASE_ID + 100, Blocks.thoriumWall);
        items.put(MINDUSTRY_BASE_ID + 101, Blocks.thoriumWallLarge);
        items.put(MINDUSTRY_BASE_ID + 102, Blocks.surgeWall);
        items.put(MINDUSTRY_BASE_ID + 103, Blocks.surgeWallLarge);
        items.put(MINDUSTRY_BASE_ID + 104, Blocks.phaseWall);
        items.put(MINDUSTRY_BASE_ID + 105, Blocks.phaseWallLarge);
        items.put(MINDUSTRY_BASE_ID + 106, Blocks.scatter);
        items.put(MINDUSTRY_BASE_ID + 107, Blocks.hail);
        items.put(MINDUSTRY_BASE_ID + 108, Blocks.salvo);
        items.put(MINDUSTRY_BASE_ID + 109, Blocks.swarmer);
        items.put(MINDUSTRY_BASE_ID + 110, Blocks.cyclone);
        items.put(MINDUSTRY_BASE_ID + 111, Blocks.spectre);
        items.put(MINDUSTRY_BASE_ID + 112, Blocks.ripple);
        items.put(MINDUSTRY_BASE_ID + 113, Blocks.fuse);
        items.put(MINDUSTRY_BASE_ID + 114, Blocks.scorch);
        items.put(MINDUSTRY_BASE_ID + 115, Blocks.arc);
        items.put(MINDUSTRY_BASE_ID + 116, Blocks.wave);
        items.put(MINDUSTRY_BASE_ID + 117, Blocks.parallax);
        items.put(MINDUSTRY_BASE_ID + 118, Blocks.segment);
        items.put(MINDUSTRY_BASE_ID + 119, Blocks.tsunami);
        items.put(MINDUSTRY_BASE_ID + 120, Blocks.lancer);
        items.put(MINDUSTRY_BASE_ID + 121, Blocks.meltdown);
        items.put(MINDUSTRY_BASE_ID + 122, Blocks.foreshadow);
        items.put(MINDUSTRY_BASE_ID + 123, Blocks.shockMine);
        items.put(MINDUSTRY_BASE_ID + 124, Blocks.groundFactory);
        items.put(MINDUSTRY_BASE_ID + 125, UnitTypes.dagger);
        items.put(MINDUSTRY_BASE_ID + 126, null); //Progressive Ground Unit Type A

        ProgressiveItem item1 = new ProgressiveItem(ProgressiveItemType.S_GROUND_UNIT_A,
                MINDUSTRY_BASE_ID + 126, 4);
        item1.items.add(UnitTypes.mace);
        item1.items.add(UnitTypes.fortress);
        item1.items.add(UnitTypes.scepter);
        item1.items.add(UnitTypes.reign);
        progressiveItems.add(item1);


        items.put(MINDUSTRY_BASE_ID + 127, null); //Progressive Ground Unit Type B

        ProgressiveItem item2 = new ProgressiveItem(ProgressiveItemType.S_GROUND_UNIT_B,
                MINDUSTRY_BASE_ID + 127, 5);
        item2.items.add(UnitTypes.nova);
        item2.items.add(UnitTypes.pulsar);
        item2.items.add(UnitTypes.quasar);
        item2.items.add(UnitTypes.vela);
        item2.items.add(UnitTypes.corvus);
        progressiveItems.add(item2);

        items.put(MINDUSTRY_BASE_ID + 128, null); //Progressive Ground Unit Type C

        ProgressiveItem item3 = new ProgressiveItem(ProgressiveItemType.S_GROUND_UNIT_C,
                MINDUSTRY_BASE_ID + 128, 5);
        item3.items.add(UnitTypes.crawler);
        item3.items.add(UnitTypes.atrax);
        item3.items.add(UnitTypes.spiroct);
        item3.items.add(UnitTypes.arkyid);
        item3.items.add(UnitTypes.toxopid);
        progressiveItems.add(item3);

        items.put(MINDUSTRY_BASE_ID + 129, Blocks.airFactory);
        items.put(MINDUSTRY_BASE_ID + 130, UnitTypes.flare);

        items.put(MINDUSTRY_BASE_ID + 131, null); //Progressive Air Unit Type A

        ProgressiveItem item4 = new ProgressiveItem(ProgressiveItemType.S_AIR_UNIT_A,
                MINDUSTRY_BASE_ID + 131, 4);
        item4.items.add(UnitTypes.horizon);
        item4.items.add(UnitTypes.zenith);
        item4.items.add(UnitTypes.antumbra);
        item4.items.add(UnitTypes.eclipse);
        progressiveItems.add(item4);

        items.put(MINDUSTRY_BASE_ID + 132, null); //Progressive Air Unit Type B

        ProgressiveItem item5 = new ProgressiveItem(ProgressiveItemType.S_AIR_UNIT_B,
                MINDUSTRY_BASE_ID + 132, 5);
        item5.items.add(UnitTypes.mono);
        item5.items.add(UnitTypes.poly);
        item5.items.add(UnitTypes.mega);
        item5.items.add(UnitTypes.quad);
        item5.items.add(UnitTypes.oct);
        progressiveItems.add(item5);

        items.put(MINDUSTRY_BASE_ID + 133, Blocks.navalFactory);
        items.put(MINDUSTRY_BASE_ID + 134, UnitTypes.risso);

        items.put(MINDUSTRY_BASE_ID + 135, null); //Progressive Naval Unit Type A

        ProgressiveItem item6 = new ProgressiveItem(ProgressiveItemType.S_NAVAL_UNIT_A,
                MINDUSTRY_BASE_ID + 135, 4);
        item6.items.add(UnitTypes.minke);
        item6.items.add(UnitTypes.bryde);
        item6.items.add(UnitTypes.sei);
        item6.items.add(UnitTypes.omura);
        progressiveItems.add(item6);

        items.put(MINDUSTRY_BASE_ID + 136, null); //Progressive Naval Unit Type B

        ProgressiveItem item7 = new ProgressiveItem(ProgressiveItemType.S_NAVAL_UNIT_B,
                MINDUSTRY_BASE_ID + 136, 5);
        item7.items.add(UnitTypes.retusa);
        item7.items.add(UnitTypes.oxynoe);
        item7.items.add(UnitTypes.cyerce);
        item7.items.add(UnitTypes.aegires);
        item7.items.add(UnitTypes.navanax);
        progressiveItems.add(item7);

        items.put(MINDUSTRY_BASE_ID + 137, null); //Progressive Reconstructor

        ProgressiveItem item8 = new ProgressiveItem(ProgressiveItemType.S_RECONSTRUCTOR,
                MINDUSTRY_BASE_ID + 137, 4);
        item8.items.add(Blocks.additiveReconstructor);
        item8.items.add(Blocks.multiplicativeReconstructor);
        item8.items.add(Blocks.exponentialReconstructor);
        item8.items.add(Blocks.tetrativeReconstructor);
        progressiveItems.add(item8);

        items.put(MINDUSTRY_BASE_ID + 138, SectorPresets.frozenForest);
        items.put(MINDUSTRY_BASE_ID + 139, SectorPresets.craters);
        items.put(MINDUSTRY_BASE_ID + 140, SectorPresets.ruinousShores);
        items.put(MINDUSTRY_BASE_ID + 141, SectorPresets.windsweptIslands);
        items.put(MINDUSTRY_BASE_ID + 142, SectorPresets.tarFields);
        items.put(MINDUSTRY_BASE_ID + 143, SectorPresets.impact0078);
        items.put(MINDUSTRY_BASE_ID + 144, SectorPresets.desolateRift);
        items.put(MINDUSTRY_BASE_ID + 145, SectorPresets.planetaryTerminal);
        items.put(MINDUSTRY_BASE_ID + 146, SectorPresets.extractionOutpost);
        items.put(MINDUSTRY_BASE_ID + 147, SectorPresets.saltFlats);
        items.put(MINDUSTRY_BASE_ID + 148, SectorPresets.coastline);
        items.put(MINDUSTRY_BASE_ID + 149, SectorPresets.navalFortress);
        items.put(MINDUSTRY_BASE_ID + 150, SectorPresets.overgrowth);
        items.put(MINDUSTRY_BASE_ID + 151, SectorPresets.biomassFacility);
        items.put(MINDUSTRY_BASE_ID + 152, SectorPresets.stainedMountains);
        items.put(MINDUSTRY_BASE_ID + 153, SectorPresets.fungalPass);
        items.put(MINDUSTRY_BASE_ID + 154, SectorPresets.nuclearComplex);
        items.put(MINDUSTRY_BASE_ID + 155, Items.lead);
        items.put(MINDUSTRY_BASE_ID + 156, Items.titanium);
        items.put(MINDUSTRY_BASE_ID + 157, Liquids.cryofluid);
        items.put(MINDUSTRY_BASE_ID + 158, Items.metaglass);
        items.put(MINDUSTRY_BASE_ID + 159, Items.scrap);
        items.put(MINDUSTRY_BASE_ID + 160, Liquids.slag);
        items.put(MINDUSTRY_BASE_ID + 161, Items.coal);
        items.put(MINDUSTRY_BASE_ID + 162, Items.graphite);
        items.put(MINDUSTRY_BASE_ID + 163, Items.silicon);
        items.put(MINDUSTRY_BASE_ID + 164, Items.pyratite);
        items.put(MINDUSTRY_BASE_ID + 165, Items.blastCompound);
        items.put(MINDUSTRY_BASE_ID + 166, Items.sporePod);
        items.put(MINDUSTRY_BASE_ID + 167, Liquids.oil);
        items.put(MINDUSTRY_BASE_ID + 168, Items.plastanium);
        items.put(MINDUSTRY_BASE_ID + 169, Items.thorium);
        items.put(MINDUSTRY_BASE_ID + 170, Items.surgeAlloy);
        items.put(MINDUSTRY_BASE_ID + 171, Items.phaseFabric);
    }

}
