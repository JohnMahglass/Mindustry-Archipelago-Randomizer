package mindustry.randomizer;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.UnitTypes;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.world.Block;

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
import static mindustry.content.SectorPresets.biomassFacility;
import static mindustry.content.SectorPresets.coastline;
import static mindustry.content.SectorPresets.craters;
import static mindustry.content.SectorPresets.desolateRift;
import static mindustry.content.SectorPresets.extractionOutpost;
import static mindustry.content.SectorPresets.frozenForest;
import static mindustry.content.SectorPresets.fungalPass;
import static mindustry.content.SectorPresets.groundZero;
import static mindustry.content.SectorPresets.impact0078;
import static mindustry.content.SectorPresets.navalFortress;
import static mindustry.content.SectorPresets.nuclearComplex;
import static mindustry.content.SectorPresets.overgrowth;
import static mindustry.content.SectorPresets.planetaryTerminal;
import static mindustry.content.SectorPresets.ruinousShores;
import static mindustry.content.SectorPresets.saltFlats;
import static mindustry.content.SectorPresets.stainedMountains;
import static mindustry.content.SectorPresets.tarFields;
import static mindustry.content.SectorPresets.windsweptIslands;
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.TechTree.nodeRoot;
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
        Seq<UnlockableContent> tier1UnlockableContent = tier1TechUnlockableContent.copy();
        Seq<UnlockableContent> tier2UnlockableContent = tier2TechUnlockableContent.copy();
        Seq<UnlockableContent> tier3UnlockableContent = tier3TechUnlockableContent.copy();
        Planets.serpulo.techTree = nodeRoot("serpulo", coreShard, () -> {

            node(conveyor, () -> {

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier1UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(extractionOutpost)), () -> {
                            //no longer necessary to beat the campaign
                            //node(interplanetaryAccelerator, Seq.with(new SectorComplete(planetaryTerminal)), () -> {

                            //});
                        });

                        node(tier2UnlockableContent.remove(0));
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0));
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0));
                            });
                        });
                        node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
                            node(tier2UnlockableContent.remove(0));
                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(stainedMountains)), () -> {

                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });

                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });

                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });
                    });
                });
            });

            node(tier1UnlockableContent.remove(0), () -> {
                node(tier2UnlockableContent.remove(0), () -> {

                });
            });

            node(mechanicalDrill, () -> {

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier2UnlockableContent.remove(0));
                                });

                                node(tier2UnlockableContent.remove(0));

                                node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                    node(tier2UnlockableContent.remove(0), () -> {

                                    });

                                    node(tier2UnlockableContent.remove(0), () -> {

                                    });

                                    node(tier2UnlockableContent.remove(0), () -> {
                                        node(tier2UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(frozenForest)), () -> {
                        node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {

                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {

                            });

                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(saltFlats)), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {

                                });
                            });
                        });
                    });

                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {

                        });
                    });

                    node(tier2UnlockableContent.remove(0), () -> {

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });

                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(tarFields)), () -> {

                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });

                                        node(tier3UnlockableContent.remove(0), () -> {
                                            node(tier3UnlockableContent.remove(0), () -> {

                                            });
                                        });

                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        //logic disabled until further notice
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });

                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });

                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {

                        });
                    });
                });


                node(tier1UnlockableContent.remove(0), Seq.with(new Objectives.Research(Items.coal)), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {

                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
                                    node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
                                        node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {

                                        });
                                    });
                                });

                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.Research(Liquids.cryofluid)), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });

                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {

                            });
                        });
                    });
                });
            });

            node(duo, () -> {
                node(copperWall, () -> {
                    node(tier1UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0));

                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0));
                            });
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0));
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0));
                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0));
                                    });
                                });
                            });
                        });
                    });
                });

                node(scatter, () -> {
                    node(tier1UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {

                                    });
                                });
                            });

                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });
                        });
                    });
                });

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });

                            node(tier3UnlockableContent.remove(0), () -> {

                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });

                            node(tier3UnlockableContent.remove(0), () -> {

                            });
                        });
                    });
                });
            });

            node(tier1UnlockableContent.remove(0), () -> {

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {

                                });
                            });
                        });
                    });

                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });
                    });

                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });
                    });
                });

                node(tier1UnlockableContent.remove(0), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {

                                    });
                                });
                            });
                        });

                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier3UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });
                            });
                        });
                    });

                    node(tier1UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(ruinousShores)), () -> {
                        node(tier2UnlockableContent.remove(0), () -> {
                            node(tier2UnlockableContent.remove(0), () -> {
                                node(tier2UnlockableContent.remove(0), () -> {
                                    node(tier3UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {

                                        });
                                    });
                                });
                            });

                            node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                node(tier2UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(coastline)), () -> {
                                    node(tier2UnlockableContent.remove(0), () -> {
                                        node(tier3UnlockableContent.remove(0), () -> {
                                            node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(navalFortress)), () -> {

                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(tier1UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
                    node(tier2UnlockableContent.remove(0), () -> {
                        node(tier3UnlockableContent.remove(0), Seq.with(new Objectives.SectorComplete(overgrowth)), () -> {
                            node(tier3UnlockableContent.remove(0), () -> {

                            });
                        });
                    });
                });
            });

            node(groundZero, () -> {
                node(frozenForest, Seq.with(
                        new Objectives.SectorComplete(groundZero),
                        new Objectives.Research(junction),
                        new Objectives.Research(router)
                ), () -> {
                    node(craters, Seq.with(
                            new Objectives.SectorComplete(frozenForest),
                            new Objectives.Research(mender),
                            new Objectives.Research(combustionGenerator)
                    ), () -> {
                        node(ruinousShores, Seq.with(
                                new Objectives.SectorComplete(craters),
                                new Objectives.Research(graphitePress),
                                new Objectives.Research(kiln),
                                new Objectives.Research(mechanicalPump)
                        ), () -> {
                            node(windsweptIslands, Seq.with(
                                    new Objectives.SectorComplete(ruinousShores),
                                    new Objectives.Research(pneumaticDrill),
                                    new Objectives.Research(hail),
                                    new Objectives.Research(siliconSmelter),
                                    new Objectives.Research(steamGenerator)
                            ), () -> {
                                node(tarFields, Seq.with(
                                        new Objectives.SectorComplete(windsweptIslands),
                                        new Objectives.Research(coalCentrifuge),
                                        new Objectives.Research(conduit),
                                        new Objectives.Research(wave)
                                ), () -> {
                                    node(impact0078, Seq.with(
                                            new Objectives.SectorComplete(tarFields),
                                            new Objectives.Research(Items.thorium),
                                            new Objectives.Research(lancer),
                                            new Objectives.Research(salvo),
                                            new Objectives.Research(coreFoundation)
                                    ), () -> {
                                        node(desolateRift, Seq.with(
                                                new Objectives.SectorComplete(impact0078),
                                                new Objectives.Research(thermalGenerator),
                                                new Objectives.Research(thoriumReactor),
                                                new Objectives.Research(coreNucleus)
                                        ), () -> {
                                            node(planetaryTerminal, Seq.with(
                                                    new Objectives.SectorComplete(desolateRift),
                                                    new Objectives.SectorComplete(nuclearComplex),
                                                    new Objectives.SectorComplete(overgrowth),
                                                    new Objectives.SectorComplete(extractionOutpost),
                                                    new Objectives.SectorComplete(saltFlats),
                                                    new Objectives.Research(risso),
                                                    new Objectives.Research(minke),
                                                    new Objectives.Research(bryde),
                                                    new Objectives.Research(spectre),
                                                    new Objectives.Research(launchPad),
                                                    new Objectives.Research(massDriver),
                                                    new Objectives.Research(impactReactor),
                                                    new Objectives.Research(additiveReconstructor),
                                                    new Objectives.Research(exponentialReconstructor)
                                            ), () -> {

                                            });
                                        });
                                    });
                                });

                                node(extractionOutpost, Seq.with(
                                        new Objectives.SectorComplete(stainedMountains),
                                        new Objectives.SectorComplete(windsweptIslands),
                                        new Objectives.Research(groundFactory),
                                        new Objectives.Research(nova),
                                        new Objectives.Research(airFactory),
                                        new Objectives.Research(mono)
                                ), () -> {

                                });

                                node(saltFlats, Seq.with(
                                        new Objectives.SectorComplete(windsweptIslands),
                                        new Objectives.Research(groundFactory),
                                        new Objectives.Research(additiveReconstructor),
                                        new Objectives.Research(airFactory),
                                        new Objectives.Research(door)
                                ), () -> {
                                    node(coastline, Seq.with(
                                            new Objectives.SectorComplete(windsweptIslands),
                                            new Objectives.SectorComplete(saltFlats),
                                            new Objectives.Research(navalFactory),
                                            new Objectives.Research(payloadConveyor)
                                    ), () -> {
                                        node(navalFortress, Seq.with(
                                                new Objectives.SectorComplete(coastline),
                                                new Objectives.SectorComplete(extractionOutpost),
                                                new Objectives.Research(oxynoe),
                                                new Objectives.Research(minke),
                                                new Objectives.Research(cyclone),
                                                new Objectives.Research(ripple)
                                        ), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        node(overgrowth, Seq.with(
                                new Objectives.SectorComplete(craters),
                                new Objectives.SectorComplete(fungalPass),
                                new Objectives.Research(cultivator),
                                new Objectives.Research(sporePress),
                                new Objectives.Research(additiveReconstructor),
                                new Objectives.Research(UnitTypes.mace),
                                new Objectives.Research(UnitTypes.flare)
                        ), () -> {

                        });
                    });

                    node(biomassFacility, Seq.with(
                            new Objectives.SectorComplete(frozenForest),
                            new Objectives.Research(powerNode),
                            new Objectives.Research(steamGenerator),
                            new Objectives.Research(scatter),
                            new Objectives.Research(graphitePress)
                    ), () -> {
                        node(stainedMountains, Seq.with(
                                new Objectives.SectorComplete(biomassFacility),
                                new Objectives.Research(pneumaticDrill),
                                new Objectives.Research(siliconSmelter)
                        ), () -> {
                            node(fungalPass, Seq.with(
                                    new Objectives.SectorComplete(stainedMountains),
                                    new Objectives.Research(groundFactory),
                                    new Objectives.Research(door)
                            ), () -> {
                                node(nuclearComplex, Seq.with(
                                        new Objectives.SectorComplete(fungalPass),
                                        new Objectives.Research(thermalGenerator),
                                        new Objectives.Research(laserDrill),
                                        new Objectives.Research(Items.plastanium),
                                        new Objectives.Research(swarmer)
                                ), () -> {

                                });
                            });
                        });
                    });
                });
            });

            nodeProduce(Items.copper, () -> {
                nodeProduce(Liquids.water, () -> {

                });

                nodeProduce(Items.lead, () -> {
                    nodeProduce(Items.titanium, () -> {
                        nodeProduce(Liquids.cryofluid, () -> {

                        });

                        nodeProduce(Items.thorium, () -> {
                            nodeProduce(Items.surgeAlloy, () -> {

                            });

                            nodeProduce(Items.phaseFabric, () -> {

                            });
                        });
                    });

                    nodeProduce(Items.metaglass, () -> {

                    });
                });

                nodeProduce(Items.sand, () -> {
                    nodeProduce(Items.scrap, () -> {
                        nodeProduce(Liquids.slag, () -> {

                        });
                    });

                    nodeProduce(Items.coal, () -> {
                        nodeProduce(Items.graphite, () -> {
                            nodeProduce(Items.silicon, () -> {

                            });
                        });

                        nodeProduce(Items.pyratite, () -> {
                            nodeProduce(Items.blastCompound, () -> {

                            });
                        });

                        nodeProduce(Items.sporePod, () -> {

                        });

                        nodeProduce(Liquids.oil, () -> {
                            nodeProduce(Items.plastanium, () -> {

                            });
                        });
                    });
                });
            });
        });
    }

    /** Load tech into tiered Seq. NOTE: This needs to be rebalanced / or think of something else
     * to help with the randomization. */
    @Override
    public void loadTechUnlockableContent() {
        //14 tech, if you are adding/removing something update this number 139 / 167
        tier1TechUnlockableContent.add(junction);
        tier1TechUnlockableContent.add(router);
        tier1TechUnlockableContent.add(distributor);
        tier1TechUnlockableContent.add(sorter);
        tier1TechUnlockableContent.add(itemBridge);
        tier1TechUnlockableContent.add(graphitePress);
        tier1TechUnlockableContent.add(combustionGenerator);
        tier1TechUnlockableContent.add(powerNode);
        tier1TechUnlockableContent.add(mender);
        tier1TechUnlockableContent.add(copperWallLarge);
        tier1TechUnlockableContent.add(scatter);
        tier1TechUnlockableContent.add(hail);
        tier1TechUnlockableContent.add(salvo);
        tier1TechUnlockableContent.add(scorch);

        //88 tech, if you are adding/removing something update this number
        tier2TechUnlockableContent.add(invertedSorter);
        tier2TechUnlockableContent.add(overflowGate);
        tier2TechUnlockableContent.add(underflowGate);
        tier2TechUnlockableContent.add(container);
        tier2TechUnlockableContent.add(unloader);
        tier2TechUnlockableContent.add(vault);
        tier2TechUnlockableContent.add(titaniumConveyor);
        tier2TechUnlockableContent.add(payloadConveyor);
        tier2TechUnlockableContent.add(payloadRouter);
        tier2TechUnlockableContent.add(armoredConveyor);
        tier2TechUnlockableContent.add(coreFoundation);
        tier2TechUnlockableContent.add(coreNucleus);
        tier2TechUnlockableContent.add(mechanicalPump);
        tier2TechUnlockableContent.add(conduit);
        tier2TechUnlockableContent.add(liquidJunction);
        tier2TechUnlockableContent.add(liquidRouter);
        tier2TechUnlockableContent.add(liquidContainer);
        tier2TechUnlockableContent.add(liquidTank);
        tier2TechUnlockableContent.add(bridgeConduit);
        tier2TechUnlockableContent.add(pulseConduit);
        tier2TechUnlockableContent.add(rotaryPump);
        tier2TechUnlockableContent.add(pneumaticDrill);
        tier2TechUnlockableContent.add(cultivator);
        tier2TechUnlockableContent.add(laserDrill);
        tier2TechUnlockableContent.add(blastDrill);
        tier2TechUnlockableContent.add(waterExtractor);
        tier2TechUnlockableContent.add(oilExtractor);
        tier2TechUnlockableContent.add(pyratiteMixer);
        tier2TechUnlockableContent.add(blastMixer);
        tier2TechUnlockableContent.add(siliconSmelter);
        tier2TechUnlockableContent.add(sporePress);
        tier2TechUnlockableContent.add(coalCentrifuge);
        tier2TechUnlockableContent.add(multiPress);
        tier2TechUnlockableContent.add(kiln);
        tier2TechUnlockableContent.add(pulverizer);
        tier2TechUnlockableContent.add(incinerator);
        tier2TechUnlockableContent.add(melter);
        tier2TechUnlockableContent.add(microProcessor);
        tier2TechUnlockableContent.add(switchBlock);
        tier2TechUnlockableContent.add(message);
        tier2TechUnlockableContent.add(logicDisplay);
        tier2TechUnlockableContent.add(largeLogicDisplay);
        tier2TechUnlockableContent.add(memoryCell);
        tier2TechUnlockableContent.add(memoryBank);
        tier2TechUnlockableContent.add(logicProcessor);
        tier2TechUnlockableContent.add(illuminator);
        tier2TechUnlockableContent.add(powerNodeLarge);
        tier2TechUnlockableContent.add(diode);
        tier2TechUnlockableContent.add(battery);
        tier2TechUnlockableContent.add(mendProjector);
        tier2TechUnlockableContent.add(forceProjector);
        tier2TechUnlockableContent.add(steamGenerator);
        tier2TechUnlockableContent.add(solarPanel);
        tier2TechUnlockableContent.add(largeSolarPanel);
        tier2TechUnlockableContent.add(titaniumWall);
        tier2TechUnlockableContent.add(titaniumWallLarge);
        tier2TechUnlockableContent.add(door);
        tier2TechUnlockableContent.add(doorLarge);
        tier2TechUnlockableContent.add(plastaniumWall);
        tier2TechUnlockableContent.add(plastaniumWallLarge);
        tier2TechUnlockableContent.add(thoriumWall);
        tier2TechUnlockableContent.add(thoriumWallLarge);
        tier2TechUnlockableContent.add(swarmer);
        tier2TechUnlockableContent.add(cyclone);
        tier2TechUnlockableContent.add(ripple);
        tier2TechUnlockableContent.add(arc);
        tier2TechUnlockableContent.add(wave);
        tier2TechUnlockableContent.add(lancer);
        tier2TechUnlockableContent.add(shockMine);
        tier2TechUnlockableContent.add(groundFactory);
        tier2TechUnlockableContent.add(dagger);
        tier2TechUnlockableContent.add(mace);
        tier2TechUnlockableContent.add(fortress);
        tier2TechUnlockableContent.add(nova);
        tier2TechUnlockableContent.add(pulsar);
        tier2TechUnlockableContent.add(crawler);
        tier2TechUnlockableContent.add(airFactory);
        tier2TechUnlockableContent.add(flare);
        tier2TechUnlockableContent.add(horizon);
        tier2TechUnlockableContent.add(mono);
        tier2TechUnlockableContent.add(poly);
        tier2TechUnlockableContent.add(zenith);
        tier2TechUnlockableContent.add(navalFactory);
        tier2TechUnlockableContent.add(risso);
        tier2TechUnlockableContent.add(minke);
        tier2TechUnlockableContent.add(retusa);
        tier2TechUnlockableContent.add(additiveReconstructor);
        tier2TechUnlockableContent.add(multiplicativeReconstructor);

        //60 tech, if you are adding/removing something update this number
        tier3TechUnlockableContent.add(launchPad);
        tier3TechUnlockableContent.add(phaseConveyor);
        tier3TechUnlockableContent.add(massDriver);
        tier3TechUnlockableContent.add(phaseConduit);
        tier3TechUnlockableContent.add(plastaniumConveyor);
        tier3TechUnlockableContent.add(platedConduit);
        tier3TechUnlockableContent.add(impulsePump);
        tier3TechUnlockableContent.add(siliconCrucible);
        tier3TechUnlockableContent.add(plastaniumCompressor);
        tier3TechUnlockableContent.add(phaseWeaver);
        tier3TechUnlockableContent.add(surgeSmelter);
        tier3TechUnlockableContent.add(separator);
        tier3TechUnlockableContent.add(disassembler);
        tier3TechUnlockableContent.add(cryofluidMixer);
        tier3TechUnlockableContent.add(hyperProcessor);
        tier3TechUnlockableContent.add(surgeTower);
        tier3TechUnlockableContent.add(batteryLarge);
        tier3TechUnlockableContent.add(overdriveProjector);
        tier3TechUnlockableContent.add(overdriveDome);
        tier3TechUnlockableContent.add(repairPoint);
        tier3TechUnlockableContent.add(repairTurret);
        tier3TechUnlockableContent.add(thermalGenerator);
        tier3TechUnlockableContent.add(differentialGenerator);
        tier3TechUnlockableContent.add(thoriumReactor);
        tier3TechUnlockableContent.add(impactReactor);
        tier3TechUnlockableContent.add(rtgGenerator);
        tier3TechUnlockableContent.add(surgeWall);
        tier3TechUnlockableContent.add(surgeWallLarge);
        tier3TechUnlockableContent.add(phaseWall);
        tier3TechUnlockableContent.add(phaseWallLarge);
        tier3TechUnlockableContent.add(spectre);
        tier3TechUnlockableContent.add(fuse);
        tier3TechUnlockableContent.add(parallax);
        tier3TechUnlockableContent.add(segment);
        tier3TechUnlockableContent.add(tsunami);
        tier3TechUnlockableContent.add(meltdown);
        tier3TechUnlockableContent.add(foreshadow);
        tier3TechUnlockableContent.add(scepter);
        tier3TechUnlockableContent.add(reign);
        tier3TechUnlockableContent.add(quasar);
        tier3TechUnlockableContent.add(vela);
        tier3TechUnlockableContent.add(corvus);
        tier3TechUnlockableContent.add(atrax);
        tier3TechUnlockableContent.add(spiroct);
        tier3TechUnlockableContent.add(arkyid);
        tier3TechUnlockableContent.add(toxopid);
        tier3TechUnlockableContent.add(antumbra);
        tier3TechUnlockableContent.add(eclipse);
        tier3TechUnlockableContent.add(mega);
        tier3TechUnlockableContent.add(quad);
        tier3TechUnlockableContent.add(oct);
        tier3TechUnlockableContent.add(bryde);
        tier3TechUnlockableContent.add(sei);
        tier3TechUnlockableContent.add(omura);
        tier3TechUnlockableContent.add(oxynoe);
        tier3TechUnlockableContent.add(cyerce);
        tier3TechUnlockableContent.add(aegires);
        tier3TechUnlockableContent.add(navanax);
        tier3TechUnlockableContent.add(exponentialReconstructor);
        tier3TechUnlockableContent.add(tetrativeReconstructor);


        /*
        Seq<Block> contentList = Vars.content.blocks();
        for (UnlockableContent content : contentList){
            if (content.fromPlanet == PlanetName.SERPULO || content.fromPlanet == PlanetName.ALL) {
                planetTechUnlockableContent.add(content);
            }
        }
        for (UnlockableContent content : starterTechUnlockableContent){
            planetTechUnlockableContent.remove(content);
        }
         */
    }

    @Override
    public void loadStarterTechUnlockableContent() {
        starterTechUnlockableContent.add(conveyor);
        starterTechUnlockableContent.add(mechanicalDrill);
        starterTechUnlockableContent.add(duo);
        starterTechUnlockableContent.add(copperWall);
        starterTechUnlockableContent.add(scatter);
    }

    public SerpuloTechTreeRandomizer() {
        super();
    }
}
