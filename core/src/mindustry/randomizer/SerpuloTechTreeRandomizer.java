package mindustry.randomizer;

import static mindustry.content.Blocks.*;
import static mindustry.content.Blocks.additiveReconstructor;
import static mindustry.content.Blocks.airFactory;
import static mindustry.content.Blocks.coalCentrifuge;
import static mindustry.content.Blocks.combustionGenerator;
import static mindustry.content.Blocks.conduit;
import static mindustry.content.Blocks.coreFoundation;
import static mindustry.content.Blocks.coreNucleus;
import static mindustry.content.Blocks.cultivator;
import static mindustry.content.Blocks.cyclone;
import static mindustry.content.Blocks.door;
import static mindustry.content.Blocks.exponentialReconstructor;
import static mindustry.content.Blocks.graphitePress;
import static mindustry.content.Blocks.groundFactory;
import static mindustry.content.Blocks.hail;
import static mindustry.content.Blocks.impactReactor;
import static mindustry.content.Blocks.junction;
import static mindustry.content.Blocks.kiln;
import static mindustry.content.Blocks.lancer;
import static mindustry.content.Blocks.laserDrill;
import static mindustry.content.Blocks.launchPad;
import static mindustry.content.Blocks.massDriver;
import static mindustry.content.Blocks.mechanicalPump;
import static mindustry.content.Blocks.mender;
import static mindustry.content.Blocks.navalFactory;
import static mindustry.content.Blocks.payloadConveyor;
import static mindustry.content.Blocks.pneumaticDrill;
import static mindustry.content.Blocks.powerNode;
import static mindustry.content.Blocks.ripple;
import static mindustry.content.Blocks.router;
import static mindustry.content.Blocks.salvo;
import static mindustry.content.Blocks.scatter;
import static mindustry.content.Blocks.siliconSmelter;
import static mindustry.content.Blocks.spectre;
import static mindustry.content.Blocks.sporePress;
import static mindustry.content.Blocks.steamGenerator;
import static mindustry.content.Blocks.swarmer;
import static mindustry.content.Blocks.thermalGenerator;
import static mindustry.content.Blocks.thoriumReactor;
import static mindustry.content.Blocks.wave;
import static mindustry.content.UnitTypes.aegires;
import static mindustry.content.UnitTypes.antumbra;
import static mindustry.content.UnitTypes.arkyid;
import static mindustry.content.UnitTypes.atrax;
import static mindustry.content.UnitTypes.bryde;
import static mindustry.content.UnitTypes.corvus;
import static mindustry.content.UnitTypes.crawler;
import static mindustry.content.UnitTypes.cyerce;
import static mindustry.content.UnitTypes.dagger;
import static mindustry.content.UnitTypes.eclipse;
import static mindustry.content.UnitTypes.flare;
import static mindustry.content.UnitTypes.fortress;
import static mindustry.content.UnitTypes.horizon;
import static mindustry.content.UnitTypes.mace;
import static mindustry.content.UnitTypes.mega;
import static mindustry.content.UnitTypes.minke;
import static mindustry.content.UnitTypes.mono;
import static mindustry.content.UnitTypes.navanax;
import static mindustry.content.UnitTypes.nova;
import static mindustry.content.UnitTypes.oct;
import static mindustry.content.UnitTypes.omura;
import static mindustry.content.UnitTypes.oxynoe;
import static mindustry.content.UnitTypes.poly;
import static mindustry.content.UnitTypes.pulsar;
import static mindustry.content.UnitTypes.quad;
import static mindustry.content.UnitTypes.quasar;
import static mindustry.content.UnitTypes.reign;
import static mindustry.content.UnitTypes.retusa;
import static mindustry.content.UnitTypes.risso;
import static mindustry.content.UnitTypes.scepter;
import static mindustry.content.UnitTypes.sei;
import static mindustry.content.UnitTypes.spiroct;
import static mindustry.content.UnitTypes.toxopid;
import static mindustry.content.UnitTypes.vela;
import static mindustry.content.UnitTypes.zenith;

/**
 * Class for the initialisation and randomization of Serpulo's tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public class SerpuloTechTreeRandomizer extends TechTreeRandomizer {

    @Override
    public void load() {

    }

    @Override
    public void loadUnlockableContent() {
        planetUnlockableContent.add(conveyor);
        planetUnlockableContent.add(mechanicalDrill);
        planetUnlockableContent.add(duo);
        planetUnlockableContent.add(copperWall);
        planetUnlockableContent.add(scatter);

        planetUnlockableContent.add(junction);
        planetUnlockableContent.add(router);
        planetUnlockableContent.add(distributor);
        planetUnlockableContent.add(sorter);
        planetUnlockableContent.add(itemBridge);
        planetUnlockableContent.add(graphitePress);
        planetUnlockableContent.add(combustionGenerator);
        planetUnlockableContent.add(powerNode);
        planetUnlockableContent.add(mender);
        planetUnlockableContent.add(copperWallLarge);
        planetUnlockableContent.add(scatter);
        planetUnlockableContent.add(hail);
        planetUnlockableContent.add(salvo);
        planetUnlockableContent.add(scorch);
        planetUnlockableContent.add(invertedSorter);
        planetUnlockableContent.add(overflowGate);
        planetUnlockableContent.add(underflowGate);
        planetUnlockableContent.add(container);
        planetUnlockableContent.add(unloader);
        planetUnlockableContent.add(vault);
        planetUnlockableContent.add(titaniumConveyor);
        planetUnlockableContent.add(payloadConveyor);
        planetUnlockableContent.add(payloadRouter);
        planetUnlockableContent.add(armoredConveyor);
        planetUnlockableContent.add(coreFoundation);
        planetUnlockableContent.add(coreNucleus);
        planetUnlockableContent.add(mechanicalPump);
        planetUnlockableContent.add(conduit);
        planetUnlockableContent.add(liquidJunction);
        planetUnlockableContent.add(liquidRouter);
        planetUnlockableContent.add(liquidContainer);
        planetUnlockableContent.add(liquidTank);
        planetUnlockableContent.add(bridgeConduit);
        planetUnlockableContent.add(pulseConduit);
        planetUnlockableContent.add(rotaryPump);
        planetUnlockableContent.add(pneumaticDrill);
        planetUnlockableContent.add(cultivator);
        planetUnlockableContent.add(laserDrill);
        planetUnlockableContent.add(blastDrill);
        planetUnlockableContent.add(waterExtractor);
        planetUnlockableContent.add(oilExtractor);
        planetUnlockableContent.add(pyratiteMixer);
        planetUnlockableContent.add(blastMixer);
        planetUnlockableContent.add(siliconSmelter);
        planetUnlockableContent.add(sporePress);
        planetUnlockableContent.add(coalCentrifuge);
        planetUnlockableContent.add(multiPress);
        planetUnlockableContent.add(kiln);
        planetUnlockableContent.add(pulverizer);
        planetUnlockableContent.add(incinerator);
        planetUnlockableContent.add(melter);
        planetUnlockableContent.add(microProcessor);
        planetUnlockableContent.add(switchBlock);
        planetUnlockableContent.add(message);
        planetUnlockableContent.add(logicDisplay);
        planetUnlockableContent.add(largeLogicDisplay);
        planetUnlockableContent.add(memoryCell);
        planetUnlockableContent.add(memoryBank);
        planetUnlockableContent.add(logicProcessor);
        planetUnlockableContent.add(illuminator);
        planetUnlockableContent.add(powerNodeLarge);
        planetUnlockableContent.add(diode);
        planetUnlockableContent.add(battery);
        planetUnlockableContent.add(mendProjector);
        planetUnlockableContent.add(forceProjector);
        planetUnlockableContent.add(steamGenerator);
        planetUnlockableContent.add(solarPanel);
        planetUnlockableContent.add(largeSolarPanel);
        planetUnlockableContent.add(titaniumWall);
        planetUnlockableContent.add(titaniumWallLarge);
        planetUnlockableContent.add(door);
        planetUnlockableContent.add(doorLarge);
        planetUnlockableContent.add(plastaniumWall);
        planetUnlockableContent.add(plastaniumWallLarge);
        planetUnlockableContent.add(thoriumWall);
        planetUnlockableContent.add(thoriumWallLarge);
        planetUnlockableContent.add(swarmer);
        planetUnlockableContent.add(cyclone);
        planetUnlockableContent.add(ripple);
        planetUnlockableContent.add(arc);
        planetUnlockableContent.add(wave);
        planetUnlockableContent.add(lancer);
        planetUnlockableContent.add(shockMine);
        planetUnlockableContent.add(groundFactory);
        planetUnlockableContent.add(dagger);
        planetUnlockableContent.add(mace);
        planetUnlockableContent.add(fortress);
        planetUnlockableContent.add(nova);
        planetUnlockableContent.add(pulsar);
        planetUnlockableContent.add(crawler);
        planetUnlockableContent.add(airFactory);
        planetUnlockableContent.add(flare);
        planetUnlockableContent.add(horizon);
        planetUnlockableContent.add(mono);
        planetUnlockableContent.add(poly);
        planetUnlockableContent.add(zenith);
        planetUnlockableContent.add(navalFactory);
        planetUnlockableContent.add(risso);
        planetUnlockableContent.add(minke);
        planetUnlockableContent.add(retusa);
        planetUnlockableContent.add(additiveReconstructor);
        planetUnlockableContent.add(multiplicativeReconstructor);
        planetUnlockableContent.add(launchPad);
        planetUnlockableContent.add(phaseConveyor);
        planetUnlockableContent.add(massDriver);
        planetUnlockableContent.add(phaseConduit);
        planetUnlockableContent.add(plastaniumConveyor);
        planetUnlockableContent.add(platedConduit);
        planetUnlockableContent.add(impulsePump);
        planetUnlockableContent.add(siliconCrucible);
        planetUnlockableContent.add(plastaniumCompressor);
        planetUnlockableContent.add(phaseWeaver);
        planetUnlockableContent.add(surgeSmelter);
        planetUnlockableContent.add(separator);
        planetUnlockableContent.add(disassembler);
        planetUnlockableContent.add(cryofluidMixer);
        planetUnlockableContent.add(hyperProcessor);
        planetUnlockableContent.add(surgeTower);
        planetUnlockableContent.add(batteryLarge);
        planetUnlockableContent.add(overdriveProjector);
        planetUnlockableContent.add(overdriveDome);
        planetUnlockableContent.add(repairPoint);
        planetUnlockableContent.add(repairTurret);
        planetUnlockableContent.add(thermalGenerator);
        planetUnlockableContent.add(differentialGenerator);
        planetUnlockableContent.add(thoriumReactor);
        planetUnlockableContent.add(impactReactor);
        planetUnlockableContent.add(rtgGenerator);
        planetUnlockableContent.add(surgeWall);
        planetUnlockableContent.add(surgeWallLarge);
        planetUnlockableContent.add(phaseWall);
        planetUnlockableContent.add(phaseWallLarge);
        planetUnlockableContent.add(spectre);
        planetUnlockableContent.add(fuse);
        planetUnlockableContent.add(parallax);
        planetUnlockableContent.add(segment);
        planetUnlockableContent.add(tsunami);
        planetUnlockableContent.add(meltdown);
        planetUnlockableContent.add(foreshadow);
        planetUnlockableContent.add(scepter);
        planetUnlockableContent.add(reign);
        planetUnlockableContent.add(quasar);
        planetUnlockableContent.add(vela);
        planetUnlockableContent.add(corvus);
        planetUnlockableContent.add(atrax);
        planetUnlockableContent.add(spiroct);
        planetUnlockableContent.add(arkyid);
        planetUnlockableContent.add(toxopid);
        planetUnlockableContent.add(antumbra);
        planetUnlockableContent.add(eclipse);
        planetUnlockableContent.add(mega);
        planetUnlockableContent.add(quad);
        planetUnlockableContent.add(oct);
        planetUnlockableContent.add(bryde);
        planetUnlockableContent.add(sei);
        planetUnlockableContent.add(omura);
        planetUnlockableContent.add(oxynoe);
        planetUnlockableContent.add(cyerce);
        planetUnlockableContent.add(aegires);
        planetUnlockableContent.add(navanax);
        planetUnlockableContent.add(exponentialReconstructor);
        planetUnlockableContent.add(tetrativeReconstructor);
    }

    @Override
    public void loadStarterUnlockableContent() {
        starterUnlockableContent.add(conveyor);
        starterUnlockableContent.add(mechanicalDrill);
        starterUnlockableContent.add(duo);
        starterUnlockableContent.add(copperWall);
        starterUnlockableContent.add(scatter);
    }

    public SerpuloTechTreeRandomizer() {
        super();
    }
}
