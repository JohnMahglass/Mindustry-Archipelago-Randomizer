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
 * Randomizer
 *
 * @author John Mahglass
 * @version 1.0.0 2024-05-12
 */
public class Randomizer {

    /**
     * The Archipelago Mindustry base id.
     */
    public final int MINDUSTRY_BASE_ID = 777000000;

    /**
     * The campaign type.
     */
    public CampaignType campaign;

    /**
     * UnlockableContent with their matching Id
     */
    public Map<Integer, UnlockableContent> items;

    /**
     * Unlock a UnlockableContent.
     * @param content The content to unlock.
     */
    public void unlock(UnlockableContent content){
            content.randomizerUnlock();
    }

    /**
     * Method not implemented
     * @param locationId
     * @param origialNodeName
     */
    public void locationChecked(int locationId, String origialNodeName){
        //Method not implemented
    }

    /**
     * Constructor for Randomizer
     */
    public Randomizer(){
        this.campaign = CampaignType.SERPULO;
        this.items  = new HashMap<Integer, UnlockableContent>();
        initializeItems(campaign);
    }

    /**
     * Add an item to the randomizer's item list.
     * @param id The id of the item WITHOUT the base id.
     * @param content The content representing the item.
     */
    private void addItem(int id, UnlockableContent content) {
        items.put(MINDUSTRY_BASE_ID + id, content);
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
     * @param campaign The selected campaign.
     */
    private void initializeItems(CampaignType campaign) {
        switch (campaign) {
            case SERPULO:
                initializeSerpuloItems();
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
        addItem(0, Blocks.coreFoundation);
        addItem(1, Blocks.coreNucleus);
        addItem(2, Blocks.conveyor);
        addItem(3, Blocks.junction);
        addItem(4, Blocks.router);
        addItem(5, Blocks.launchPad);
        addItem(6, Blocks.distributor);
        addItem(7, Blocks.sorter);
        addItem(8, Blocks.invertedSorter);
        addItem(9, Blocks.overflowGate);
        addItem(10, Blocks.underflowGate);
        addItem(11, Blocks.container);
        addItem(12, Blocks.unloader);
        addItem(13, Blocks.vault);
        addItem(14, Blocks.itemBridge);
        addItem(15, Blocks.titaniumConveyor);
        addItem(16, Blocks.phaseConveyor);
        addItem(17, Blocks.massDriver);
        addItem(18, Blocks.payloadConveyor);
        addItem(19, Blocks.payloadRouter);
        addItem(20, Blocks.armoredConveyor);
        addItem(21, Blocks.plastaniumConveyor);
        addItem(22, Blocks.mechanicalDrill);
        addItem(23, Blocks.mechanicalPump);
        addItem(24, Blocks.conduit);
        addItem(25, Blocks.liquidJunction);
        addItem(26, Blocks.liquidRouter);
        addItem(27, Blocks.liquidContainer);
        addItem(28, Blocks.liquidTank);
        addItem(29, Blocks.bridgeConduit);
        addItem(30, Blocks.pulseConduit);
        addItem(31, Blocks.phaseConduit);
        addItem(32, Blocks.platedConduit);
        addItem(33, Blocks.rotaryPump);
        addItem(34, Blocks.impulsePump);
        addItem(35, Blocks.graphitePress);
        addItem(36, Blocks.pneumaticDrill);
        addItem(37, Blocks.cultivator);
        addItem(38, Blocks.laserDrill);
        addItem(39, Blocks.blastDrill);
        addItem(40, Blocks.waterExtractor);
        addItem(41, Blocks.oilExtractor);
        addItem(42, Blocks.pyratiteMixer);
        addItem(43, Blocks.blastMixer);
        addItem(44, Blocks.siliconSmelter);
        addItem(45, Blocks.sporePress);
        addItem(46, Blocks.coalCentrifuge);
        addItem(47, Blocks.multiPress);
        addItem(48, Blocks.siliconCrucible);
        addItem(49, Blocks.plastaniumCompressor);
        addItem(50, Blocks.phaseWeaver);
        addItem(51, Blocks.kiln);
        addItem(52, Blocks.pulverizer);
        addItem(53, Blocks.incinerator);
        addItem(54, Blocks.melter);
        addItem(55, Blocks.surgeSmelter);
        addItem(56, Blocks.separator);
        addItem(57, Blocks.disassembler);
        addItem(58, Blocks.cryofluidMixer);
        addItem(59, Blocks.microProcessor);
        addItem(60, Blocks.switchBlock);
        addItem(61, Blocks.message);
        addItem(62, Blocks.logicDisplay);
        addItem(63, Blocks.largeLogicDisplay);
        addItem(64, Blocks.memoryCell);
        addItem(65, Blocks.memoryBank);
        addItem(66, Blocks.logicProcessor);
        addItem(67, Blocks.hyperProcessor);
        addItem(68, Blocks.illuminator);
        addItem(69, Blocks.combustionGenerator);
        addItem(70, Blocks.powerNode);
        addItem(71, Blocks.powerNodeLarge);
        addItem(72, Blocks.diode);
        addItem(73, Blocks.surgeTower);
        addItem(74, Blocks.battery);
        addItem(75, Blocks.batteryLarge);
        addItem(76, Blocks.mender);
        addItem(77, Blocks.mendProjector);
        addItem(78, Blocks.forceProjector);
        addItem(79, Blocks.overdriveProjector);
        addItem(80, Blocks.overdriveDome);
        addItem(81, Blocks.repairPoint);
        addItem(82, Blocks.repairTurret);
        addItem(83, Blocks.steamGenerator);
        addItem(84, Blocks.thermalGenerator);
        addItem(85, Blocks.differentialGenerator);
        addItem(86, Blocks.thoriumReactor);
        addItem(87, Blocks.impactReactor);
        addItem(88, Blocks.rtgGenerator);
        addItem(89, Blocks.solarPanel);
        addItem(90, Blocks.largeSolarPanel);
        addItem(91, Blocks.duo);
        addItem(92, Blocks.copperWall);
        addItem(93, Blocks.copperWallLarge);
        addItem(94, Blocks.titaniumWall);
        addItem(95, Blocks.titaniumWallLarge);
        addItem(96, Blocks.door);
        addItem(97, Blocks.doorLarge);
        addItem(98, Blocks.plastaniumWall);
        addItem(99, Blocks.plastaniumWallLarge);
        addItem(100, Blocks.thoriumWall);
        addItem(101, Blocks.thoriumWallLarge);
        addItem(102, Blocks.surgeWall);
        addItem(103, Blocks.surgeWallLarge);
        addItem(104, Blocks.phaseWall);
        addItem(105, Blocks.phaseWallLarge);
        addItem(106, Blocks.scatter);
        addItem(107, Blocks.hail);
        addItem(108, Blocks.salvo);
        addItem(109, Blocks.swarmer);
        addItem(110, Blocks.cyclone);
        addItem(111, Blocks.spectre);
        addItem(112, Blocks.ripple);
        addItem(113, Blocks.fuse);
        addItem(114, Blocks.scorch);
        addItem(115, Blocks.arc);
        addItem(116, Blocks.wave);
        addItem(117, Blocks.parallax);
        addItem(118, Blocks.segment);
        addItem(119, Blocks.tsunami);
        addItem(120, Blocks.lancer);
        addItem(121, Blocks.meltdown);
        addItem(122, Blocks.foreshadow);
        addItem(123, Blocks.shockMine);
        addItem(124, Blocks.groundFactory);
        addItem(125, UnitTypes.dagger);
        addItem(126, UnitTypes.mace);
        addItem(127, UnitTypes.fortress);
        addItem(128, UnitTypes.scepter);
        addItem(129, UnitTypes.reign);
        addItem(130, UnitTypes.nova);
        addItem(131, UnitTypes.pulsar);
        addItem(132, UnitTypes.quasar);
        addItem(133, UnitTypes.vela);
        addItem(134, UnitTypes.corvus);
        addItem(135, UnitTypes.crawler);
        addItem(136, UnitTypes.atrax);
        addItem(137, UnitTypes.spiroct);
        addItem(138, UnitTypes.arkyid);
        addItem(139, UnitTypes.toxopid);
        addItem(140, Blocks.airFactory);
        addItem(141, UnitTypes.flare);
        addItem(142, UnitTypes.horizon);
        addItem(143, UnitTypes.zenith);
        addItem(144, UnitTypes.antumbra);
        addItem(145, UnitTypes.eclipse);
        addItem(146, UnitTypes.mono);
        addItem(147, UnitTypes.poly);
        addItem(148, UnitTypes.mega);
        addItem(149, UnitTypes.quad);
        addItem(150, UnitTypes.oct);
        addItem(151, Blocks.navalFactory);
        addItem(152, UnitTypes.risso);
        addItem(153, UnitTypes.minke);
        addItem(154, UnitTypes.bryde);
        addItem(155, UnitTypes.sei);
        addItem(156, UnitTypes.omura);
        addItem(157, UnitTypes.retusa);
        addItem(158, UnitTypes.oxynoe);
        addItem(159, UnitTypes.cyerce);
        addItem(160, UnitTypes.aegires);
        addItem(161, UnitTypes.navanax);
        addItem(162, Blocks.additiveReconstructor);
        addItem(163, Blocks.multiplicativeReconstructor);
        addItem(164, Blocks.exponentialReconstructor);
        addItem(165, Blocks.tetrativeReconstructor);
        addItem(166, Items.lead);
        addItem(167, Items.titanium);
        addItem(168, Items.metaglass);
        addItem(169, Items.scrap);
        addItem(170, Items.coal);
        addItem(171, Liquids.cryofluid);
        addItem(172, Items.thorium);
        addItem(173, Items.surgeAlloy);
        addItem(174, Items.phaseFabric);
        addItem(175, Items.silicon);
        addItem(176, Items.blastCompound);
        addItem(177, Items.plastanium);
        addItem(178, SectorPresets.frozenForest);
        addItem(179, SectorPresets.craters);
        addItem(180, SectorPresets.biomassFacility);
        addItem(181, SectorPresets.ruinousShores);
        addItem(182, SectorPresets.windsweptIslands);
        addItem(183, SectorPresets.tarFields);
        addItem(184, SectorPresets.impact0078);
        addItem(185, SectorPresets.desolateRift);
        addItem(186, SectorPresets.planetaryTerminal);
        addItem(187, SectorPresets.extractionOutpost);
        addItem(188, SectorPresets.saltFlats);
        addItem(189, SectorPresets.coastline);
        addItem(190, SectorPresets.navalFortress);
        addItem(191, SectorPresets.overgrowth);
        addItem(192, SectorPresets.stainedMountains);
        addItem(193, SectorPresets.fungalPass);
        addItem(194, SectorPresets.nuclearComplex);
        addItem(195, Liquids.slag);
        addItem(196, Items.graphite);
        addItem(197, Items.pyratite);
        addItem(198, Items.sporePod);
        addItem(199, Liquids.oil);
    }

}
