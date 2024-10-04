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
import mindustry.randomizer.utils.RandomizerMessageHandler;

import static mindustry.randomizer.enums.SettingStrings.*;

import static arc.Core.settings;


import static mindustry.randomizer.Shared.MINDUSTRY_BASE_ID;

/**
 * Contain and update information about what has been done within this World.
 *
 * @author John Mahglass
 */
public class WorldState {

    /**
     * Client seed.
     */
    private int seed;

    /**
     * Name of the file containing locations that are pending.
     */
    public String checkPendingFile = "RandomizerCheckPending.txt";

    /**
     * List of all progressive items.
     */
    public ArrayList<ProgressiveItem> progressiveItems;
    /**
     * List of all filler items.
     */
    public Map<Long, String> fillerItems;

    /**
     * List of all locations used in the randomisation.
     */
    public Map<Long, String> locations;

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
    public ArrayList<Long> unlockedItems;

    /**
     * Contains the options of the generated game.
     */
    public MindustryOptions options;

    /**
     * True if the player is currently dying from a death link bounce. This prevents the player from
     * sending another death link signal when they die from one themself.
     */
    public boolean deathLinkDying;

    /**
     * True if there is a check waiting to be sent to the server.
     * @return True if a check is pending.
     */
    public boolean hasCheckPending(){
        return !checkPending.isEmpty();
    }

    /**
     * Save every state locally.
     */
    public void saveStates(){
        saveState(checkPendingFile, checkPending);
    }

    public int getSeed(){
        return this.seed;
    }

    /**
     * Check if every condition for victory has been met.
     * @return True if the player has completed all their goal.
     */
    public boolean isVictoryConditionMet(){
        boolean victory = false;
        int campaign = options.getCampaign();
        switch (campaign) {
            case 0: //Serpulo
                if (settings.getBool(SERPULO_VICTORY.value)) {
                    victory = true;
                }
                break;
            case 1: //Erekir
                if (settings.getBool(EREKIR_VICTORY.value)) {
                    victory = true;
                }
                break;
            case 2: //All
                if (settings.getBool(SERPULO_VICTORY.value) && settings.getBool(EREKIR_VICTORY.value)) {
                    victory = true;
                }
                break;
        }
        return victory;
    }

    /**
     * Reset all local data related to the world state.
     */
    protected void resetWorldState() {
        wipeStates();
        settings.remove(HAS_CONNECTED.value);
        settings.remove(DEATH_LINK.value);
        settings.remove(FORCE_DISABLE_DEATH_LINK.value);
        settings.remove(TUTORIAL_SKIP.value);
        settings.remove(DISABLE_INVASIONS.value);
        settings.remove(FASTER_PRODUCTION.value);
        settings.remove(CAMPAIGN_CHOICE.value);
        settings.remove(CLIENT_ADDRESS.value);
        settings.remove(CLIENT_NAME.value);
        settings.remove(CLIENT_PASSWORD.value);
        settings.remove(FREE_LAUNCH_SERPULO.value);
        settings.remove(FREE_LAUNCH_EREKIR.value);
        settings.remove(SERPULO_VICTORY.value);
        settings.remove(EREKIR_VICTORY.value);
        settings.remove(AP_SEED.value);
        settings.remove(RANDOMIZE_CORE_UNITS_WEAPON.value);
        settings.remove(LOGISTIC_DISTRIBUTION.value);
        settings.remove(EREKIR_RANDOMIZED_WEAPON_EVOKE.value);
        settings.remove(EREKIR_RANDOMIZED_WEAPON_INCITE.value);
        settings.remove(EREKIR_RANDOMIZED_WEAPON_EMANATE.value);
        settings.remove(EREKIR_RANDOMIZED_WEAPON_INIT.value);
        settings.remove(AP_CHAT_SELF_ITEM_ONLY.value);
        settings.remove(AP_CHAT_DISABLED.value);
        settings.remove(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value);
        settings.remove(AP_DEATH_LINK_PROTECT_CAPTURED_SECTOR.value);
        settings.remove(DEATH_LINK_MODE.value);
        settings.remove(AP_MAKE_EARLY_ROADBLOCKS_LOCAL.value);
        settings.remove(AMOUNT_OF_RESOURCES_REQUIRED.value);
    }

    /**
     * Create a seed using the Archipelago Room Info seed. The seed is converted to an int so
     * that it can easily be stored inside Settings
     * @param roomInfoSeed The seed from the Room Info.
     */
    public void createSeed(String roomInfoSeed){
        seed = Integer.parseInt(roomInfoSeed.substring(0, 7));
        settings.put(AP_SEED.value, seed);
    }

    /**
     * Add item to unlocked items list. If the item is progressive, update the count.
     * @param itemId The id of the unlocked item.
     */
    public void addUnlockedItem(Long itemId) {
        if (isMindustryAPItem(itemId)) {
            unlockedItems.add(itemId);
        } else {
            RandomizerMessageHandler.printErrorWithReason("Invalid item was called for unlock " +
                    "(Invalid ID)");
        }
    }

    /**
     * Checks whether the plays have received the item at least once.
     * @param id The id of the item to be checked.
     * @return Return True if the player has this item at least once.
     */
    public boolean hasItem(Long id){
        boolean itemReceived = false;
        for (Long unlockedItem : unlockedItems) {
            if (id.equals(unlockedItem)) {
                itemReceived = true;
            }
        }
        return itemReceived;
    }

    /**
     * Return true if AP item attributes are initialized.
     * within the randomizer.
     * @return If the content is an AP item.
     */
    public boolean isMindustryAPItem(Long itemId){
        boolean isMindustryItem = false;
        if (itemId != null) {
            if (itemId >= MINDUSTRY_BASE_ID && itemId <= MINDUSTRY_BASE_ID + 999) {
                isMindustryItem = true;
            }
        }
        return isMindustryItem;
    }


    public WorldState() {
        this.options = new MindustryOptions();
        this.items = new HashMap<>();
        this.fillerItems = new HashMap<>();
        this.unlockedItems = new ArrayList<>();
        this.locations = new HashMap<>();
        this.locationsChecked = new ArrayList<>();
        this.checkPending = new ArrayList<>();
        this.progressiveItems = new ArrayList<>();
        this.apLocations = new ArrayList<>();
        this.deathLinkDying = false;
        initialize();
    }

    /**
     * Wipe every state.
     */
    private void wipeStates() {
        wipeState(checkPendingFile, checkPending);
        progressiveItems.clear();
        locationsChecked.clear();
    }

    private void initialize() {
        if (settings.getInt(AP_SEED.value) != 0){ // 0 = no value saved.
            seed = settings.getInt(AP_SEED.value);
        } else {
            seed = 0;
        }
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
     * Load the saved state. If file cannot be found, initialize an empty list.
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
     * @param stateArray The array to store the check
     * @param newCheck The id of the check
     */
    protected void addCheck(ArrayList<Long> stateArray, Long newCheck){
        boolean checkExist = false;
        for (Long check : stateArray) {
            if (check.equals(newCheck)) {
               checkExist = true;
               int i = 2;
            }
        }
        if (!checkExist) {
            stateArray.add(newCheck);
        } else {
            //log error
        }
    }

    /**
     * Return if the item is a progressive item.
     * @param itemId The item's id.
     * @return Return true if the item is progressive.
     */
    protected boolean isProgressive(Long itemId) {
        boolean found = false;
        for (ProgressiveItem item : progressiveItems) {
            if (item.id.equals(itemId)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Initialize a list of possible filler item for Serpulo.
     */
    protected void initializeSerpuloFillers(){
        fillerItems.put(MINDUSTRY_BASE_ID + 700, "A fistful of nothing...");
    }

    /**
     * Initialize a list of possible filler item for Erekir.
     */
    protected void initializeErekirFillers(){
        fillerItems.put(MINDUSTRY_BASE_ID + 700, "A fistful of nothing...");
    }

    /**
     * Initialize a list of possible filler item for all campaigns.
     */
    protected void initializeAllFillers(){
        fillerItems.put(MINDUSTRY_BASE_ID + 700, "A fistful of nothing...");
    }

    /**
     * Initialize items for all campaigns.
     */
    protected void initializeAllItems() {
        initializeSerpuloItems();
        initializeErekirItems();
    }

    /**
     * Initialize item from Erekir campaign.
     */
    protected void initializeErekirItems() {
        items.put(MINDUSTRY_BASE_ID + 200, Blocks.duct);
        items.put(MINDUSTRY_BASE_ID + 201, Blocks.ductRouter);
        items.put(MINDUSTRY_BASE_ID + 202, Blocks.ductBridge);
        items.put(MINDUSTRY_BASE_ID + 203, Blocks.armoredDuct);
        items.put(MINDUSTRY_BASE_ID + 204, Blocks.surgeConveyor);
        items.put(MINDUSTRY_BASE_ID + 205, Blocks.surgeRouter);
        items.put(MINDUSTRY_BASE_ID + 206, Blocks.unitCargoLoader);
        items.put(MINDUSTRY_BASE_ID + 207, Blocks.unitCargoUnloadPoint);
        items.put(MINDUSTRY_BASE_ID + 208, Blocks.overflowDuct);
        items.put(MINDUSTRY_BASE_ID + 209, Blocks.underflowDuct);
        items.put(MINDUSTRY_BASE_ID + 210, Blocks.ductUnloader);
        items.put(MINDUSTRY_BASE_ID + 211, Blocks.reinforcedContainer);
        items.put(MINDUSTRY_BASE_ID + 212, Blocks.reinforcedVault);
        items.put(MINDUSTRY_BASE_ID + 213, Blocks.reinforcedMessage);
        items.put(MINDUSTRY_BASE_ID + 214, Blocks.canvas);
        items.put(MINDUSTRY_BASE_ID + 215, Blocks.reinforcedPayloadConveyor);
        items.put(MINDUSTRY_BASE_ID + 216, Blocks.payloadMassDriver);
        items.put(MINDUSTRY_BASE_ID + 217, Blocks.payloadLoader);
        items.put(MINDUSTRY_BASE_ID + 218, Blocks.payloadUnloader);
        items.put(MINDUSTRY_BASE_ID + 219, Blocks.largePayloadMassDriver);
        items.put(MINDUSTRY_BASE_ID + 220, Blocks.constructor);
        items.put(MINDUSTRY_BASE_ID + 221, Blocks.smallDeconstructor);
        items.put(MINDUSTRY_BASE_ID + 222, Blocks.largeConstructor);
        items.put(MINDUSTRY_BASE_ID + 223, Blocks.deconstructor);
        items.put(MINDUSTRY_BASE_ID + 224, Blocks.reinforcedPayloadRouter);
        items.put(MINDUSTRY_BASE_ID + 225, Blocks.plasmaBore);
        items.put(MINDUSTRY_BASE_ID + 226, Blocks.impactDrill);
        items.put(MINDUSTRY_BASE_ID + 227, Blocks.largePlasmaBore);
        items.put(MINDUSTRY_BASE_ID + 228, Blocks.eruptionDrill);
        items.put(MINDUSTRY_BASE_ID + 229, Blocks.turbineCondenser);
        items.put(MINDUSTRY_BASE_ID + 230, Blocks.beamNode);
        items.put(MINDUSTRY_BASE_ID + 231, Blocks.ventCondenser);
        items.put(MINDUSTRY_BASE_ID + 232, Blocks.chemicalCombustionChamber);
        items.put(MINDUSTRY_BASE_ID + 233, Blocks.pyrolysisGenerator);
        items.put(MINDUSTRY_BASE_ID + 234, Blocks.fluxReactor);
        items.put(MINDUSTRY_BASE_ID + 235, Blocks.neoplasiaReactor);
        items.put(MINDUSTRY_BASE_ID + 236, Blocks.beamTower);
        items.put(MINDUSTRY_BASE_ID + 237, Blocks.regenProjector);
        items.put(MINDUSTRY_BASE_ID + 238, Blocks.buildTower);
        items.put(MINDUSTRY_BASE_ID + 239, Blocks.shockwaveTower);
        items.put(MINDUSTRY_BASE_ID + 240, Blocks.reinforcedConduit);
        items.put(MINDUSTRY_BASE_ID + 241, Blocks.reinforcedPump);
        items.put(MINDUSTRY_BASE_ID + 242, Blocks.reinforcedLiquidJunction);
        items.put(MINDUSTRY_BASE_ID + 243, Blocks.reinforcedBridgeConduit);
        items.put(MINDUSTRY_BASE_ID + 244, Blocks.reinforcedLiquidRouter);
        items.put(MINDUSTRY_BASE_ID + 245, Blocks.reinforcedLiquidContainer);
        items.put(MINDUSTRY_BASE_ID + 246, Blocks.reinforcedLiquidTank);
        items.put(MINDUSTRY_BASE_ID + 247, Blocks.cliffCrusher);
        items.put(MINDUSTRY_BASE_ID + 248, Blocks.siliconArcFurnace);
        items.put(MINDUSTRY_BASE_ID + 249, Blocks.electrolyzer);
        items.put(MINDUSTRY_BASE_ID + 250, Blocks.oxidationChamber);
        items.put(MINDUSTRY_BASE_ID + 251, Blocks.surgeCrucible);
        items.put(MINDUSTRY_BASE_ID + 252, Blocks.heatRedirector);
        items.put(MINDUSTRY_BASE_ID + 253, Blocks.electricHeater);
        items.put(MINDUSTRY_BASE_ID + 254, Blocks.slagHeater);
        items.put(MINDUSTRY_BASE_ID + 255, Blocks.atmosphericConcentrator);
        items.put(MINDUSTRY_BASE_ID + 256, Blocks.cyanogenSynthesizer);
        items.put(MINDUSTRY_BASE_ID + 257, Blocks.carbideCrucible);
        items.put(MINDUSTRY_BASE_ID + 258, Blocks.phaseSynthesizer);
        items.put(MINDUSTRY_BASE_ID + 259, Blocks.phaseHeater);
        items.put(MINDUSTRY_BASE_ID + 260, Blocks.heatRouter);
        items.put(MINDUSTRY_BASE_ID + 261, Blocks.slagIncinerator);
        items.put(MINDUSTRY_BASE_ID + 262, Blocks.breach);
        items.put(MINDUSTRY_BASE_ID + 263, Blocks.berylliumWall);
        items.put(MINDUSTRY_BASE_ID + 264, Blocks.berylliumWallLarge);
        items.put(MINDUSTRY_BASE_ID + 265, Blocks.tungstenWall);
        items.put(MINDUSTRY_BASE_ID + 266, Blocks.tungstenWallLarge);
        items.put(MINDUSTRY_BASE_ID + 267, Blocks.blastDoor);
        items.put(MINDUSTRY_BASE_ID + 268, Blocks.reinforcedSurgeWall);
        items.put(MINDUSTRY_BASE_ID + 269, Blocks.reinforcedSurgeWallLarge);
        items.put(MINDUSTRY_BASE_ID + 270, Blocks.shieldedWall);
        items.put(MINDUSTRY_BASE_ID + 271, Blocks.carbideWall);
        items.put(MINDUSTRY_BASE_ID + 272, Blocks.carbideWallLarge);
        items.put(MINDUSTRY_BASE_ID + 273, Blocks.diffuse);
        items.put(MINDUSTRY_BASE_ID + 274, Blocks.sublimate);
        items.put(MINDUSTRY_BASE_ID + 275, Blocks.afflict);
        items.put(MINDUSTRY_BASE_ID + 276, Blocks.titan);
        items.put(MINDUSTRY_BASE_ID + 277, Blocks.lustre);
        items.put(MINDUSTRY_BASE_ID + 278, Blocks.smite);
        items.put(MINDUSTRY_BASE_ID + 279, Blocks.disperse);
        items.put(MINDUSTRY_BASE_ID + 280, Blocks.scathe);
        items.put(MINDUSTRY_BASE_ID + 281, Blocks.malign);
        items.put(MINDUSTRY_BASE_ID + 282, Blocks.radar);
        items.put(MINDUSTRY_BASE_ID + 283, Blocks.coreCitadel);
        items.put(MINDUSTRY_BASE_ID + 284, Blocks.coreAcropolis);
        items.put(MINDUSTRY_BASE_ID + 285, Blocks.tankFabricator);
        items.put(MINDUSTRY_BASE_ID + 286, UnitTypes.stell);
        items.put(MINDUSTRY_BASE_ID + 287, Blocks.unitRepairTower);
        items.put(MINDUSTRY_BASE_ID + 288, Blocks.shipFabricator);
        items.put(MINDUSTRY_BASE_ID + 290, Blocks.mechFabricator);
        items.put(MINDUSTRY_BASE_ID + 292, Blocks.tankRefabricator);
        items.put(MINDUSTRY_BASE_ID + 294, Blocks.mechRefabricator);
        items.put(MINDUSTRY_BASE_ID + 296, Blocks.shipRefabricator);
        items.put(MINDUSTRY_BASE_ID + 298, Blocks.primeRefabricator);
        items.put(MINDUSTRY_BASE_ID + 302, Blocks.tankAssembler);
        items.put(MINDUSTRY_BASE_ID + 305, Blocks.shipAssembler);
        items.put(MINDUSTRY_BASE_ID + 308, Blocks.mechAssembler);
        items.put(MINDUSTRY_BASE_ID + 311, Blocks.basicAssemblerModule);

        items.put(MINDUSTRY_BASE_ID + 312, SectorPresets.aegis);
        items.put(MINDUSTRY_BASE_ID + 313, SectorPresets.lake);
        items.put(MINDUSTRY_BASE_ID + 314, SectorPresets.intersect);
        items.put(MINDUSTRY_BASE_ID + 315, SectorPresets.atlas);
        items.put(MINDUSTRY_BASE_ID + 316, SectorPresets.split);
        items.put(MINDUSTRY_BASE_ID + 317, SectorPresets.basin);
        items.put(MINDUSTRY_BASE_ID + 318, SectorPresets.marsh);
        items.put(MINDUSTRY_BASE_ID + 319, SectorPresets.ravine);
        items.put(MINDUSTRY_BASE_ID + 320, SectorPresets.caldera);
        items.put(MINDUSTRY_BASE_ID + 321, SectorPresets.stronghold);
        items.put(MINDUSTRY_BASE_ID + 322, SectorPresets.crevice);
        items.put(MINDUSTRY_BASE_ID + 323, SectorPresets.siege);
        items.put(MINDUSTRY_BASE_ID + 324, SectorPresets.crossroads);
        items.put(MINDUSTRY_BASE_ID + 325, SectorPresets.karst);
        items.put(MINDUSTRY_BASE_ID + 326, SectorPresets.origin);
        items.put(MINDUSTRY_BASE_ID + 327, SectorPresets.peaks);
        items.put(MINDUSTRY_BASE_ID + 328, Items.oxide);
        items.put(MINDUSTRY_BASE_ID + 329, Liquids.ozone);
        items.put(MINDUSTRY_BASE_ID + 330, Liquids.hydrogen);
        items.put(MINDUSTRY_BASE_ID + 331, Liquids.nitrogen);
        items.put(MINDUSTRY_BASE_ID + 332, Liquids.cyanogen);
        items.put(MINDUSTRY_BASE_ID + 333, Liquids.neoplasm);
        items.put(MINDUSTRY_BASE_ID + 334, Items.tungsten);
        items.put(MINDUSTRY_BASE_ID + 335, Liquids.slag);
        items.put(MINDUSTRY_BASE_ID + 336, Liquids.arkycite);
        items.put(MINDUSTRY_BASE_ID + 337, Items.carbide);
        items.put(MINDUSTRY_BASE_ID + 338, Items.thorium);
        items.put(MINDUSTRY_BASE_ID + 339, Items.phaseFabric);
        items.put(MINDUSTRY_BASE_ID + 340, Items.surgeAlloy);

        items.put(MINDUSTRY_BASE_ID + 341, null); //Progressive Tanks
        ProgressiveItem item1 = new ProgressiveItem(ProgressiveItemType.E_TANKS,
                MINDUSTRY_BASE_ID + 341, 4);
        item1.items.add(UnitTypes.locus);
        item1.items.add(UnitTypes.precept);
        item1.items.add(UnitTypes.vanquish);
        item1.items.add(UnitTypes.conquer);
        progressiveItems.add(item1);

        items.put(MINDUSTRY_BASE_ID + 342, null); //Progressive Ships
        ProgressiveItem item3 = new ProgressiveItem(ProgressiveItemType.E_SHIPS,
                MINDUSTRY_BASE_ID + 342, 5);
        item3.items.add(UnitTypes.elude);
        item3.items.add(UnitTypes.avert);
        item3.items.add(UnitTypes.obviate);
        item3.items.add(UnitTypes.quell);
        item3.items.add(UnitTypes.disrupt);
        progressiveItems.add(item3);

        items.put(MINDUSTRY_BASE_ID + 343, null); //Progressive Mechs
        ProgressiveItem item2 = new ProgressiveItem(ProgressiveItemType.E_MECHS,
                MINDUSTRY_BASE_ID + 343, 5);
        item2.items.add(UnitTypes.merui);
        item2.items.add(UnitTypes.cleroi);
        item2.items.add(UnitTypes.anthicus);
        item2.items.add(UnitTypes.tecta);
        item2.items.add(UnitTypes.collaris);
        progressiveItems.add(item2);
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

        items.put(MINDUSTRY_BASE_ID + 126, null); //Progressive Offensive Ground Unit
        ProgressiveItem item1 = new ProgressiveItem(ProgressiveItemType.S_OFFENSIVE_GROUND_UNIT,
                MINDUSTRY_BASE_ID + 126, 5);
        item1.items.add(UnitTypes.dagger);
        item1.items.add(UnitTypes.mace);
        item1.items.add(UnitTypes.fortress);
        item1.items.add(UnitTypes.scepter);
        item1.items.add(UnitTypes.reign);
        progressiveItems.add(item1);


        items.put(MINDUSTRY_BASE_ID + 127, null); //Progressive Support Ground Unit
        ProgressiveItem item2 = new ProgressiveItem(ProgressiveItemType.S_SUPPORT_GROUND_UNIT,
                MINDUSTRY_BASE_ID + 127, 5);
        item2.items.add(UnitTypes.nova);
        item2.items.add(UnitTypes.pulsar);
        item2.items.add(UnitTypes.quasar);
        item2.items.add(UnitTypes.vela);
        item2.items.add(UnitTypes.corvus);
        progressiveItems.add(item2);

        items.put(MINDUSTRY_BASE_ID + 128, null); //Progressive Insectoid Ground Unit
        ProgressiveItem item3 = new ProgressiveItem(ProgressiveItemType.S_INSECTOID_GROUND_UNIT,
                MINDUSTRY_BASE_ID + 128, 5);
        item3.items.add(UnitTypes.crawler);
        item3.items.add(UnitTypes.atrax);
        item3.items.add(UnitTypes.spiroct);
        item3.items.add(UnitTypes.arkyid);
        item3.items.add(UnitTypes.toxopid);
        progressiveItems.add(item3);

        items.put(MINDUSTRY_BASE_ID + 129, Blocks.airFactory);

        items.put(MINDUSTRY_BASE_ID + 131, null); //Progressive Offensive Air Unit
        ProgressiveItem item4 = new ProgressiveItem(ProgressiveItemType.S_OFFENSIVE_AIR_UNIT,
                MINDUSTRY_BASE_ID + 131, 5);
        item4.items.add(UnitTypes.flare);
        item4.items.add(UnitTypes.horizon);
        item4.items.add(UnitTypes.zenith);
        item4.items.add(UnitTypes.antumbra);
        item4.items.add(UnitTypes.eclipse);
        progressiveItems.add(item4);

        items.put(MINDUSTRY_BASE_ID + 132, null); //Progressive Support Air Unit
        ProgressiveItem item5 = new ProgressiveItem(ProgressiveItemType.S_SUPPORT_AIR_UNIT,
                MINDUSTRY_BASE_ID + 132, 5);
        item5.items.add(UnitTypes.mono);
        item5.items.add(UnitTypes.poly);
        item5.items.add(UnitTypes.mega);
        item5.items.add(UnitTypes.quad);
        item5.items.add(UnitTypes.oct);
        progressiveItems.add(item5);

        items.put(MINDUSTRY_BASE_ID + 133, Blocks.navalFactory);

        items.put(MINDUSTRY_BASE_ID + 135, null); //Progressive Offensive Naval Unit
        ProgressiveItem item6 = new ProgressiveItem(ProgressiveItemType.S_OFFENSIVE_NAVAL_UNIT,
                MINDUSTRY_BASE_ID + 135, 5);
        item6.items.add(UnitTypes.risso);
        item6.items.add(UnitTypes.minke);
        item6.items.add(UnitTypes.bryde);
        item6.items.add(UnitTypes.sei);
        item6.items.add(UnitTypes.omura);
        progressiveItems.add(item6);

        items.put(MINDUSTRY_BASE_ID + 136, null); //Progressive Support Naval Unit
        ProgressiveItem item7 = new ProgressiveItem(ProgressiveItemType.S_SUPPORT_NAVAL_UNIT,
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
