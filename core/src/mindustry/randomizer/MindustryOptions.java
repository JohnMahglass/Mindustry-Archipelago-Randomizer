package mindustry.randomizer;

import arc.struct.Seq;
import dev.koifysh.archipelago.helper.DeathLink;
import mindustry.content.Blocks;
import mindustry.content.SectorPresets;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.randomizer.client.SlotData;
import mindustry.randomizer.enums.ArchipelagoGoal;
import mindustry.randomizer.enums.CampaignType;
import mindustry.randomizer.enums.DeathLinkMode;
import mindustry.randomizer.enums.LogisticsDistribution;
import mindustry.randomizer.utils.RandomizableCoreUnits;
import mindustry.type.Weapon;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.production.Separator;
import mindustry.world.blocks.production.WallCrafter;

import java.util.ArrayList;
import java.util.Random;

import static arc.Core.settings;
import static mindustry.Vars.randomizer;
import static mindustry.randomizer.enums.SettingStrings.*;

/**
 * Class containing the options for the generated Mindustry game.
 *
 * @author John Mahglass
 */
public class MindustryOptions {

    /**
     * If the options has been filled and are not default.
     */
    private boolean optionsFilled;

    /**
     * If the tutorial is to be skipped. Also unlock tutorial-related tech.
     */
    private boolean tutorialSkip;

    /**
     * The selected campaign.
     */
    private int campaign;

    /**
     * The selected goal.
     */
    private int goal;

    /**
     * If death link is activated
     */
    private boolean deathLink;

    /**
     * The death link mode selected by the player. If death link is not activated, does nothing.
     */
    private int deathLinkMode;

    /**
     * The amount of chambers to be used for the 'Core russian roulette' death link mode.
     */
    private int coreRussianRouletteChambers;

    /**
     * Disable death link even if it was chosen as an option for generation.
     */
    private boolean forceDisableDeathLink;

    /**
     * Disable invasions.
     */
    private boolean disableInvasions;

    /**
     * Increase the rate at which resource are harvested and increase production output.
     */
    private boolean fasterProduction;


    /**
     * Increase the speed at which the conveyor transport ressources.
     */
    private boolean fasterConveyor;

    /**
     * Randomize core units weapon.
     */
    private boolean randomizeCoreUnitsWeapon;

    /**
     * How should the logistic be handled by the logic.
     */
    private int logisticDistribution;

    /**
     * The amount of resources required to unlock the victory nodes.
     */
    private int amountOfResourcesRequired;

    /**
     * Make some research early local items.
     */
    private boolean makeEarlyRoadblocksLocal;

    /**
     * Make drills into progressive items
     */
    private boolean progressiveDrills;

    /**
     * Make generators into progressive items
     */
    private boolean progressiveGenerators;

    /**
     * Contains the list of every available ability for the core units randomization.
     */
    private ArrayList<Ability[]> coreUnitAbilities;

    private ArrayList<Ability[]> getCoreUnitAbilities() {
        return (ArrayList<Ability[]>)this.coreUnitAbilities.clone();
    }

    public LogisticsDistribution getLogisticDistribution(){
        return LogisticsDistribution.toLogisticDistribution(this.logisticDistribution);
    }

    /**
     * Return the value of LogisticDistribution. Used for storing information in settings.
     */
    private int getLogisticDistributionValue(){
        return this.logisticDistribution;
    }

    public boolean getTutorialSkip() {
        return this.tutorialSkip;
    }

    public boolean getDisableInvasions() {
        return this.disableInvasions;
    }

    public boolean getFasterProduction() {
        return this.fasterProduction;
    }

    public boolean getFasterConveyor() {
        return this.fasterConveyor;
    }

    /**
     * Verify if death link is enable but does not verify if it has been disabled on client side.
     * This should only be used to enable DeathLink tags in the client upon connection.
     * @return If the death link option was chosen for game generation.
     */
    public boolean getTrueDeathLink(){
        return this.deathLink;
    }

    /**
     * Verifiy if death link is enable and also check if force disable has been enable.
     * @return Return true if death link is activated.
     */
    public boolean getDeathLink() {
        return this.deathLink && !this.forceDisableDeathLink;
    }

    public DeathLinkMode getDeathLinkMode() {
        return DeathLinkMode.toDeathLinkMode(this.deathLinkMode);
    }

    /**
     * Return the value of DeathLinkMode. Used for storing informations in settings.
     */
    private int getDeathLinkModeValue(){
        return this.deathLinkMode;
    }

    public ArchipelagoGoal getGoal(){
        return ArchipelagoGoal.toArchipelagoGoal(this.goal);
    }

    /**
     * Return the value of Goal. Used for storing informations in settings.
     */
    private int getGoalValue(){
        return this.goal;
    }

    public int getCoreRussianRouletteChambers(){
        return this.coreRussianRouletteChambers;
    }

    public boolean getRandomizeCoreUnitsWeapon(){
        return this.randomizeCoreUnitsWeapon;
    }

    public boolean getForceDisableDeathLink() {
        return this.forceDisableDeathLink;
    }

    public int getAmountOfResourcesRequired() {
        return this.amountOfResourcesRequired;
    }
    public boolean getProgressiveDrills(){
        return this.progressiveDrills;
    }
    public boolean getProgressiveGenerators(){
        return this.progressiveGenerators;
    }

    /**
     * Set forceDisableDeathLink and save the variable in settings.
     * @param state The new state of forceDisableDeathLink.
     */
    public void setForceDisableDeathLink(boolean state) {
        this.forceDisableDeathLink = state;
        settings.put(FORCE_DISABLE_DEATH_LINK.value, state);
        if (forceDisableDeathLink != state) { // Setting has been changed.
            if (forceDisableDeathLink) {
                DeathLink.setDeathLinkEnabled(false);
            } else {
                DeathLink.setDeathLinkEnabled(true);
            }
        }

    }

    public boolean getMakeEarlyRoadblocksLocal() {
        return this.makeEarlyRoadblocksLocal;
    }

    public CampaignType getCampaign() {
        return CampaignType.toCampaignType(this.campaign);
    }

    /**
     * Return the value of Campaign. Used for storing informations in settings.
     */
    private int getCampaignValue(){
        return this.campaign;
    }

    public boolean getOptionsFilled() {
        boolean filled;
        if (this.optionsFilled || randomizer.hasConnectedPreviously) {
            filled = true;
        } else {
            filled = false;
        }
        return filled;
    }

    /**
     * Return the local option 'disable chat'
     * @return the value of disable chat
     */
    public boolean getDisableChat() {
        return settings.getBool(AP_CHAT_DISABLED.value);
    }

    /**
     * Return the local value of "Allow self item only"
     * @return the value of allow self item only
     */
    public boolean getAllowOnlySelfItem() {
        return settings.getBool(AP_CHAT_SELF_ITEM_ONLY.value);
    }

    /**
     * Fill the options with the options received from AP
     * @param slotData slot data containing the player's options.
     */
    public void fillOptions (SlotData slotData) {
        if (slotData != null) {
            this.deathLink = slotData.getDeathlink();
            this.deathLinkMode = slotData.getDeathLinkMode();
            this.coreRussianRouletteChambers = slotData.getCoreRussianRouletteChambers();
            this.tutorialSkip = slotData.getTutorialSkip();
            this.disableInvasions = slotData.getDisableInvasions();
            this.fasterProduction = slotData.getFasterProduction();
            this.fasterConveyor = slotData.getFasterConveyor();
            this.campaign = slotData.getCampaignChoice();
            this.goal = slotData.getGoal();
            this.randomizeCoreUnitsWeapon = slotData.getRandomizeCoreUnitsWeapon();
            this.logisticDistribution = slotData.getLogisticDistribution();
            this.makeEarlyRoadblocksLocal = slotData.getMakeEarlyRoadblocksLocal();
            this.amountOfResourcesRequired = slotData.getAmountOfResourcesRequired();
            this.progressiveDrills = slotData.getProgressiveDrills();
            this.progressiveGenerators = slotData.getProgressiveGenerators();

            this.optionsFilled = true;
            saveOptions();
        }
    }


    /**
     * Constructor for MindustryOptions, if the user has never connected to a game, load default
     * options in the meantime.
     */
    public MindustryOptions() {
        //Do not use randomizer.hasConnectedPreviously since Vars.randomizer is null
        if (settings != null && settings.getBool(HAS_CONNECTED.value)) { //Player has connected to the game before
            loadOptions();
        } else { //Player never connected to the game and has not received options information.
            this.optionsFilled = false;
            this.tutorialSkip = false;
            this.campaign = 0;
            this.goal = 0;
            this.disableInvasions = false;
            this.fasterProduction = false;
            this.fasterConveyor = false;
            this.deathLink = false;
            this.deathLinkMode = 0;
            this.coreRussianRouletteChambers = 6;
            this.randomizeCoreUnitsWeapon = false;
            this.logisticDistribution = 0;
            this.makeEarlyRoadblocksLocal = false;
            this.amountOfResourcesRequired = 2000;
            this.coreUnitAbilities = RandomizableCoreUnits.getPossibleCoreUnitsAbility();
            if (settings != null) { //Locally saved settings
                this.forceDisableDeathLink = settings.getBool(FORCE_DISABLE_DEATH_LINK.value);
                settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, 6);
            }
        }
    }

    /**
     * Randomize core units weapon.
     */
    public void randomizeCoreUnitsWeapon(Unit unit) {
        if (getCampaign() == CampaignType.EREKIR || getCampaign() == CampaignType.ALL) {
            ArrayList<Ability[]> possibleCoreUnitAbilities = getCoreUnitAbilities();
            randomizeErekirCoreUnitsAbility(possibleCoreUnitAbilities, unit);
        }
    }

    /**
     * Randomize the ability of every core unit of Erekir
     * @param coreUnitAbilities The list of possible abilities.
     */
    private void randomizeErekirCoreUnitsAbility(ArrayList<Ability[]> coreUnitAbilities,
                                                        Unit unit) {
        if (!settings.getBool(EREKIR_RANDOMIZED_WEAPON_INIT.value)) { //Core unit ability were not assigned
            generateErekirWeapons();
        }
        if (unit.type.equals(UnitTypes.evoke)) {
            unit.abilities = coreUnitAbilities.get(settings.getInt(EREKIR_RANDOMIZED_WEAPON_EVOKE.value));
        } else if (unit.type.equals(UnitTypes.incite)) {
            unit.abilities = coreUnitAbilities.get(settings.getInt(EREKIR_RANDOMIZED_WEAPON_INCITE.value));
        } else if (unit.type.equals(UnitTypes.emanate)) {
            unit.abilities = coreUnitAbilities.get(settings.getInt(EREKIR_RANDOMIZED_WEAPON_EMANATE.value));
        }
    }

    /**
     * Generate and save Erekir weapons randomization.
     */
    private void generateErekirWeapons() {
        Random random = new Random(settings.getInt(AP_SEED.value));
        int evokeIndex = random.nextInt(coreUnitAbilities.size() - 1);
        int inciteIndex = random.nextInt(coreUnitAbilities.size() - 1);
        while (evokeIndex == inciteIndex) { //Making sure that index are not the same
            inciteIndex = random.nextInt(coreUnitAbilities.size() - 1);
        }
        int emanateIndex = random.nextInt(coreUnitAbilities.size() - 1);
        while (emanateIndex == evokeIndex || emanateIndex == inciteIndex) {
            emanateIndex = random.nextInt(coreUnitAbilities.size() - 1);
        }
        settings.put(EREKIR_RANDOMIZED_WEAPON_EVOKE.value, evokeIndex);
        settings.put(EREKIR_RANDOMIZED_WEAPON_INCITE.value, inciteIndex);
        settings.put(EREKIR_RANDOMIZED_WEAPON_EMANATE.value, emanateIndex);

        settings.put(EREKIR_RANDOMIZED_WEAPON_INIT.value, true);
    }

    /**
     * Randomize the weapon of every core unit of Serpulo
     * @param coreUnitWeapons The list of possible weapons.
     */
    private static void randomizeSerpuloCoreUnitsWeapon(ArrayList<Seq<Weapon>> coreUnitWeapons) {
        Random random = new Random(settings.getInt(AP_SEED.value));
        UnitTypes.alpha.weapons.clear();
        UnitTypes.alpha.weapons.add(coreUnitWeapons.remove(random.nextInt(coreUnitWeapons.size() - 1)));

        UnitTypes.beta.weapons.clear();
        UnitTypes.beta.weapons.add(coreUnitWeapons.remove(random.nextInt(coreUnitWeapons.size() - 1)));

        UnitTypes.gamma.weapons.clear();
        UnitTypes.gamma.weapons.add(coreUnitWeapons.remove(random.nextInt(coreUnitWeapons.size() - 1)));
    }

    /**
     * Unlock Serpulo's tutorial research and unlock Frozen Forest.
     */
    protected static void unlockSerpuloTutorialItems() {
        Blocks.conveyor.quietUnlock();
        Blocks.duo.quietUnlock();
        Blocks.scatter.quietUnlock();
        Blocks.mechanicalDrill.quietUnlock();
        Blocks.copperWall.quietUnlock();
        SectorPresets.groundZero.quietUnlock();
        SectorPresets.frozenForest.quietUnlock();
        SectorPresets.frozenForest.alwaysUnlocked = true;
    }

    /**
     * Unlock Erekir's tutorial research and unlock Aegis.
     */
    protected static void unlockErekirTutorialItems() {
        Blocks.duct.quietUnlock();
        Blocks.plasmaBore.quietUnlock();
        Blocks.turbineCondenser.quietUnlock();
        Blocks.beamNode.quietUnlock();
        Blocks.cliffCrusher.quietUnlock();
        Blocks.siliconArcFurnace.quietUnlock();
        Blocks.breach.quietUnlock();
        Blocks.berylliumWall.quietUnlock();
        Blocks.tankFabricator.quietUnlock();
        UnitTypes.stell.unlock();
        SectorPresets.aegis.quietUnlock();
        SectorPresets.aegis.alwaysUnlocked = true;
    }

    /**
     * Unlock Erekir research related to the starter logistics option.
     */
    private static void unlockErekirLogisticItems(){
        Blocks.ductRouter.quietUnlock();
        Blocks.ductBridge.quietUnlock();
        Blocks.reinforcedConduit.quietUnlock();
        Blocks.reinforcedLiquidJunction.quietUnlock();
        Blocks.reinforcedLiquidRouter.quietUnlock();
        Blocks.reinforcedBridgeConduit.quietUnlock();
    }

    /**
     * Unlock Serpulo research related to the starter logistics option.
     */
    private static void unlockSerpuloLogisticItems(){
        Blocks.conduit.quietUnlock();
        Blocks.liquidJunction.quietUnlock();
        Blocks.liquidRouter.quietUnlock();
        Blocks.bridgeConduit.quietUnlock();
        Blocks.junction.quietUnlock();
        Blocks.router.quietUnlock();
        Blocks.itemBridge.quietUnlock();
        Blocks.powerNode.quietUnlock();
    }

    /**
     * Apply the starter logistics option.
     * @param campaign The selected campaign.
     */
    protected static void applyStarterLogistics(CampaignType campaign){
        switch (campaign) {
            case SERPULO:
                unlockSerpuloLogisticItems();
                break;
            case EREKIR:
                unlockErekirLogisticItems();
                break;
            case ALL:
                unlockSerpuloLogisticItems();
                unlockErekirLogisticItems();
                break;
        }
    }

    /**
     * Apply the faster production option to the selected campaign.
     * @param campaign The selected campaign.
     */
    protected static void applyFasterProduction(CampaignType campaign){
        switch (campaign) {
            case SERPULO:
                applySerpuloFasterProduction();
                break;
            case EREKIR:
                applyErekirFasterProduction();
                break;
            case ALL:
                applySerpuloFasterProduction();
                applyErekirFasterProduction();
                break;
        }

    }

    /**
     * Apply the faster production option on Erekir's research.
     */
    private static void applyErekirFasterProduction() {
        doubleOutputItem(((GenericCrafter) Blocks.siliconArcFurnace));
        doubleOutputLiquids(((GenericCrafter) Blocks.electrolyzer));
        doubleOutputLiquid(((GenericCrafter) Blocks.atmosphericConcentrator));
        doubleOutputItem(((GenericCrafter) Blocks.oxidationChamber));
        doubleOutputItem(((GenericCrafter) Blocks.carbideCrucible));
        doubleOutputItem(((GenericCrafter) Blocks.surgeCrucible));
        doubleOutputLiquid(((GenericCrafter) Blocks.cyanogenSynthesizer));
        doubleOutputItem(((GenericCrafter) Blocks.phaseSynthesizer));
        halfWallCrafterDrillTime(((WallCrafter) Blocks.cliffCrusher));
        halfBeamDrillTime(((BeamDrill) Blocks.plasmaBore));
        halfBeamDrillTime(((BeamDrill) Blocks.largePlasmaBore));
        halfDrillTime(((Drill) Blocks.impactDrill));
        halfDrillTime(((Drill) Blocks.eruptionDrill));
        doublePumpAmount(((Pump)Blocks.reinforcedPump));
    }

    /**
     * Apply the faster production option on Serpulo's research.
     */
    private static void applySerpuloFasterProduction() {
        doubleOutputItem(((GenericCrafter) Blocks.graphitePress));
        doubleOutputItem(((GenericCrafter) Blocks.multiPress));
        doubleOutputItem(((GenericCrafter) Blocks.siliconSmelter));
        doubleOutputItem(((GenericCrafter) Blocks.siliconCrucible));
        doubleOutputItem(((GenericCrafter) Blocks.kiln));
        doubleOutputItem(((GenericCrafter) Blocks.plastaniumCompressor));
        doubleOutputItem(((GenericCrafter) Blocks.phaseWeaver));
        doubleOutputItem(((GenericCrafter) Blocks.surgeSmelter));
        doubleOutputLiquid(((GenericCrafter) Blocks.cryofluidMixer));
        doubleOutputItem(((GenericCrafter) Blocks.pyratiteMixer));
        doubleOutputItem(((GenericCrafter) Blocks.blastMixer));
        doubleOutputLiquid(((GenericCrafter) Blocks.melter));
        doubleSeparatorOutput(((Separator) Blocks.separator));
        doubleSeparatorOutput(((Separator) Blocks.disassembler));
        doubleOutputLiquid(((GenericCrafter) Blocks.sporePress));
        doubleOutputItem(((GenericCrafter) Blocks.pulverizer));
        doubleOutputItem(((GenericCrafter) Blocks.coalCentrifuge));
        doublePumpAmount(((Pump) Blocks.mechanicalPump));
        doublePumpAmount(((Pump) Blocks.rotaryPump));
        doublePumpAmount(((Pump) Blocks.impulsePump));
        halfDrillTime(((Drill) Blocks.mechanicalDrill));
        halfDrillTime(((Drill) Blocks.pneumaticDrill));
        halfDrillTime(((Drill) Blocks.laserDrill));
        halfDrillTime(((Drill) Blocks.blastDrill));
        doublePumpAmount(((Pump) Blocks.waterExtractor));
        doublePumpAmount(((Pump) Blocks.oilExtractor));
        doubleOutputItem(((GenericCrafter) Blocks.cultivator));
    }

    /**
     * Double the output of liquid generating Crafter.
     * @param crafter The crafter to double the output.
     */
    private static void doubleOutputLiquids(GenericCrafter crafter) {
        for (int i = 0; i < crafter.outputLiquids.length; i++) {
            crafter.outputLiquids[i].amount = crafter.outputLiquids[i].amount * 2;
        }
    }

    /**
     * Reduce time required by the miner to extract resources by half.
     * @param miner the miner to have the extract time reduced by half
     */
    private static void halfWallCrafterDrillTime(WallCrafter miner) {
        miner.drillTime = miner.drillTime / 2;
    }

    /**
     * Reduce time required by the drill to extract resources by half.
     * @param drill the drill to have the extract time reduced by half
     */
    private static void halfBeamDrillTime(BeamDrill drill) {
        drill.drillTime = drill.drillTime / 2;
    }

    /**
     * Reduce time required by the drill to extract resources by half.
     * @param drill the drill to have the extract time reduced by half
     */
    private static void halfDrillTime(Drill drill) {
        drill.drillTime = drill.drillTime / 2;
    }

    /**
     * Double the output item of a GenericCrafter
     * @param crafter The crafter to have the output doubled.
     */
    private static void doubleOutputItem(GenericCrafter crafter){
        crafter.outputItem.amount = crafter.outputItem.amount * 2;
    }

    /**
     * Double the output liquid of a ThermalGenerator
     * @param generator The generator to have the output doubled.
     */
    private static void doubleGeneratorOutputLiquid(ThermalGenerator generator){
        generator.outputLiquid.amount = generator.outputLiquid.amount * 2;
    }

    /**
     * Double the output liquid of a GenericCrafter
     * @param crafter The crafter to have the output doubled.
     */
    private static void doubleOutputLiquid(GenericCrafter crafter){
        crafter.outputLiquid.amount = crafter.outputLiquid.amount * 2;
    }

    /**
     * Double the output item of a Separator.
     * @param separator The separator to have the output doubled.
     */
    private static void doubleSeparatorOutput(Separator separator){
        for (int i = 0; i < separator.results.length; i++) {
            separator.results[i].amount = separator.results[i]. amount * 2;
        }
    }

    /**
     * Double the amount extracted by the Pump
     * @param pump The pump to have the amount doubled.
     */
    private static void doublePumpAmount(Pump pump){
        pump.pumpAmount = pump.pumpAmount * 2;
    }


    /**
     * Apply the faster conveyor option on Serpulo's research.
     */
    public static void applyFasterConveyor() {
        doubleConveyorSpeed(((Conveyor) Blocks.conveyor));
        doubleConveyorSpeed(((Conveyor) Blocks.titaniumConveyor));
        doubleConveyorSpeed(((Conveyor) Blocks.armoredConveyor));
        doubleStackConveyorSpeed(((StackConveyor) Blocks.plastaniumConveyor));
    }

    /**
     * Double the speed of the conveyor.
     * @param conveyor The conveyor to have the speed doubled.
     */
    private static void doubleConveyorSpeed(Conveyor conveyor) {
        conveyor.speed = conveyor.speed * 2;
        conveyor.displayedSpeed = conveyor.displayedSpeed * 2;
    }

    /**
     * Double the speed of the stack conveyor.
     * @param conveyor The stack conveyor to have the speed doubled.
     */
    private static void doubleStackConveyorSpeed(StackConveyor conveyor) {
        conveyor.speed = conveyor.speed * 2;
        conveyor.baseEfficiency = conveyor.baseEfficiency * 2;
    }

    /**
     * Save options locally
     */
    private void saveOptions() {
        settings.put(DEATH_LINK.value, getDeathLink());
        settings.put(DEATH_LINK_MODE.value, getDeathLinkModeValue());
        settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_CHAMBERS.value, getCoreRussianRouletteChambers());
        settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, getCoreRussianRouletteChambers());
        settings.put(TUTORIAL_SKIP.value, getTutorialSkip());
        settings.put(DISABLE_INVASIONS.value, getDisableInvasions());
        settings.put(FASTER_PRODUCTION.value, getFasterProduction());
        settings.put(CAMPAIGN_CHOICE.value, getCampaignValue());
        settings.put(AP_GOAL.value, getGoalValue());
        settings.put(RANDOMIZE_CORE_UNITS_WEAPON.value, getRandomizeCoreUnitsWeapon());
        settings.put(LOGISTIC_DISTRIBUTION.value, getLogisticDistributionValue());
        settings.put(PROGRESSIVE_DRILLS.value, getProgressiveDrills());
        settings.put(PROGRESSIVE_GENERATORS.value, getProgressiveGenerators());
        settings.put(AP_MAKE_EARLY_ROADBLOCKS_LOCAL.value, getMakeEarlyRoadblocksLocal());
        settings.put(AMOUNT_OF_RESOURCES_REQUIRED.value, getAmountOfResourcesRequired());
        if (getTutorialSkip()) {
            if (getCampaign() == CampaignType.SERPULO) {
                settings.put(FREE_LAUNCH_SERPULO.value, true);
            } else if (getCampaign() == CampaignType.EREKIR) {
                settings.put(FREE_LAUNCH_EREKIR.value, true);
            } else if (getCampaign() == CampaignType.ALL) {
                settings.put(FREE_LAUNCH_SERPULO.value, true);
                settings.put(FREE_LAUNCH_EREKIR.value, true);
            }
        }
        settings.put(HAS_CONNECTED.value, true);
    }

    /**
     * Load options locally
     */
    private void loadOptions() {
        this.deathLink = settings.getBool(DEATH_LINK.value);
        this.deathLinkMode = settings.getInt(DEATH_LINK_MODE.value);
        this.coreRussianRouletteChambers = settings.getInt(AP_DEATH_LINK_RUSSIAN_ROULETTE_CHAMBERS.value);
        this.forceDisableDeathLink = settings.getBool(FORCE_DISABLE_DEATH_LINK.value);
        this.tutorialSkip = settings.getBool(TUTORIAL_SKIP.value);
        this.disableInvasions = settings.getBool(DISABLE_INVASIONS.value);
        this.fasterProduction = settings.getBool(FASTER_PRODUCTION.value);
        this.fasterConveyor = settings.getBool(FASTER_CONVEYOR.value);
        this.campaign = settings.getInt(CAMPAIGN_CHOICE.value);
        this.goal = settings.getInt(AP_GOAL.value);
        this.randomizeCoreUnitsWeapon = settings.getBool(RANDOMIZE_CORE_UNITS_WEAPON.value);
        this.logisticDistribution = settings.getInt(LOGISTIC_DISTRIBUTION.value);
        this.progressiveDrills = settings.getBool(PROGRESSIVE_DRILLS.value);
        this.progressiveGenerators = settings.getBool(PROGRESSIVE_GENERATORS.value);
        this.makeEarlyRoadblocksLocal = settings.getBool(AP_MAKE_EARLY_ROADBLOCKS_LOCAL.value);
        this.amountOfResourcesRequired = settings.getInt(AMOUNT_OF_RESOURCES_REQUIRED.value);

        this.optionsFilled = true;
        if (this.randomizeCoreUnitsWeapon) {
            if (getCampaign() == CampaignType.SERPULO) {
                randomizeSerpuloCoreUnitsWeapon(RandomizableCoreUnits.getPossibleCoreUnitsWeapons());
            } else if (getCampaign() == CampaignType.EREKIR) {
                coreUnitAbilities = RandomizableCoreUnits.getPossibleCoreUnitsAbility();
            } else if (getCampaign() == CampaignType.ALL) {
                randomizeSerpuloCoreUnitsWeapon(RandomizableCoreUnits.getPossibleCoreUnitsWeapons());
                coreUnitAbilities = RandomizableCoreUnits.getPossibleCoreUnitsAbility();
            }
        }
    }
}
