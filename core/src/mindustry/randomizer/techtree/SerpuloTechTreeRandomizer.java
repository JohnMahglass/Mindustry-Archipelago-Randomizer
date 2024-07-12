package mindustry.randomizer.techtree;

import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.randomizer.Shared;
import mindustry.type.ItemStack;

import mindustry.game.Objectives.*;

import java.nio.file.Path;
import java.nio.file.Paths;

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
import static mindustry.content.TechTree.apNode;
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
public abstract class SerpuloTechTreeRandomizer extends TechTreeRandomizer {

    public static void load() {
        Planets.serpulo.techTree = nodeRoot("serpulo", coreShard, () -> {
            node(conveyor , () -> {

                apNode(createApLocation("AP-S-01-02", junction, 1L), () -> {
                    apNode(createApLocation("AP-S-01-03", router, 2L), () -> {
                        apNode(createApLocation("AP-S-01-04", launchPad, 3L),
                                Seq.with(new SectorComplete(extractionOutpost)), () -> {
                        });

                        apNode(createApLocation("AP-S-01-05", distributor, 4L));
                        apNode(createApLocation("AP-S-01-06", sorter, 5L), () -> {
                            apNode(createApLocation("AP-S-01-07", invertedSorter, 6L));
                            apNode(createApLocation("AP-S-01-08", overflowGate, 7L), () -> {
                                apNode(createApLocation("AP-S-01-09", underflowGate, 8L));
                            });
                        });
                        apNode(createApLocation("AP-S-01-10", container, 9L),
                                Seq.with(new SectorComplete(biomassFacility)), () -> {
                                    apNode(createApLocation("AP-S-01-11", unloader, 10L),
                                    Seq.with(new SectorComplete(biomassFacility)), () -> {
                                    });
                                    apNode(createApLocation("AP-S-01-12", vault, 11L),
                                    Seq.with(new SectorComplete(biomassFacility),
                                            new SectorComplete(stainedMountains)), () -> {
                            });
                        });

                        apNode(createApLocation("AP-S-01-13", itemBridge, 12L), () -> {
                            apNode(createApLocation("AP-S-01-14", titaniumConveyor, 13L),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                        apNode(createApLocation("AP-S-01-15", phaseConveyor, 14L),
                                       Seq.with(new SectorComplete(craters)), () -> {
                                                    apNode(createApLocation("AP-S-01-16", massDriver, 15L),
                                            Seq.with(new SectorComplete(craters)), () -> {
                                    });
                                });

                                        apNode(createApLocation("AP-S-01-17", payloadConveyor, 16L),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                                    apNode(createApLocation("AP-S-01-18", payloadRouter, 17L),
                                            Seq.with(new SectorComplete(craters)), () -> {
                                    });
                                });

                                        apNode(createApLocation("AP-S-01-19", armoredConveyor, 18L),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                                    apNode(createApLocation("AP-S-01-20", plastaniumConveyor, 19L),
                                            Seq.with(new SectorComplete(craters)),() -> {
                                    });
                                });
                            });
                        });
                    });
                });
            });

            apNode(createApLocation("AP-S-02-01", coreFoundation, 20L), () -> {
                apNode(createApLocation("AP-S-02-02", coreNucleus, 21L), () -> {
                });
            });

            node(mechanicalDrill , () -> {

                apNode(createApLocation("AP-S-03-02", mechanicalPump, 23L), () -> {
                    apNode(createApLocation("AP-S-03-03", conduit, 24L), () -> {
                        apNode(createApLocation("AP-S-03-04", liquidJunction, 25L), () -> {
                            apNode(createApLocation("AP-S-03-05", liquidRouter, 26L), () -> {
                                apNode(createApLocation("AP-S-03-06", liquidContainer, 27L), () -> {
                                    apNode(createApLocation("AP-S-03-07", liquidTank, 28L));
                                });

                                apNode(createApLocation("AP-S-03-08", bridgeConduit, 29L));
                                apNode(createApLocation("AP-S-03-09", pulseConduit, 30L),
                                        Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                            apNode(createApLocation("AP-S-03-10", phaseConduit, 31L),
                                            Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                    });

                                            apNode(createApLocation("AP-S-03-11", platedConduit, 32L),
                                            Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                    });

                                            apNode(createApLocation("AP-S-03-12", rotaryPump, 33L),
                                            Seq.with(new SectorComplete(windsweptIslands)),() -> {
                                                        apNode(createApLocation("AP-S-03-13", impulsePump, 34L),
                                                Seq.with(new SectorComplete(windsweptIslands)),() -> {
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                apNode(createApLocation("AP-S-03-14", graphitePress, 35L), () -> {
                    apNode(createApLocation("AP-S-03-15", pneumaticDrill, 36L),
                            Seq.with(new SectorComplete(frozenForest)), () -> {
                                apNode(createApLocation("AP-S-03-16", cultivator, 37L),
                                Seq.with(new SectorComplete(biomassFacility),
                                        new SectorComplete(frozenForest)),
                                () -> {
                        });

                                apNode(createApLocation("AP-S-03-17", laserDrill, 38L),
                                Seq.with(new SectorComplete(frozenForest)),() -> {
                                            apNode(createApLocation("AP-S-03-18", blastDrill, 39L),
                                    Seq.with(new SectorComplete(nuclearComplex),
                                            new SectorComplete(frozenForest)), () -> {
                            });

                                            apNode(createApLocation("AP-S-03-19", waterExtractor, 40L),
                                    Seq.with(new SectorComplete(saltFlats),
                                            new SectorComplete(frozenForest)), () -> {
                                                        apNode(createApLocation("AP-S-03-20", oilExtractor, 41L),
                                        Seq.with(new SectorComplete(saltFlats),
                                                new SectorComplete(frozenForest)),() -> {
                                });
                            });
                        });
                    });

                    apNode(createApLocation("AP-S-03-21", pyratiteMixer, 42L), () -> {
                        apNode(createApLocation("AP-S-03-22", blastMixer, 43L), () -> {
                        });
                    });

                    apNode(createApLocation("AP-S-03-23", siliconSmelter, 44L), () -> {

                        apNode(createApLocation("AP-S-03-24", sporePress, 45L), () -> {
                            apNode(createApLocation("AP-S-03-25", coalCentrifuge, 46L), () -> {
                                apNode(createApLocation("AP-S-03-26", multiPress, 47L), () -> {
                                    apNode(createApLocation("AP-S-03-27", siliconCrucible, 48L), () -> {
                                    });
                                });
                            });

                            apNode(createApLocation("AP-S-03-28", plastaniumCompressor, 49L),
                                    Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                        apNode(createApLocation("AP-S-03-29", phaseWeaver, 50L),
                                        Seq.with(new SectorComplete(tarFields),
                                                new SectorComplete(windsweptIslands)), () -> {
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-03-30", kiln, 51L),
                                Seq.with(new SectorComplete(craters)), () -> {
                                    apNode(createApLocation("AP-S-03-31", pulverizer, 52L),
                                    Seq.with(new SectorComplete(craters)),() -> {
                                                apNode(createApLocation("AP-S-03-32", incinerator, 53L),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                                            apNode(createApLocation("AP-S-03-33", melter, 54L),
                                            Seq.with(new SectorComplete(craters)),() -> {
                                                                        apNode(createApLocation("AP-S-03-34", surgeSmelter, 55L),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                        });

                                                                        apNode(createApLocation("AP-S-03-35", separator, 56L),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                                                                    apNode(createApLocation("AP-S-03-36", disassembler, 57L),
                                                    Seq.with(new SectorComplete(craters)),() -> {
                                            });
                                        });

                                                                        apNode(createApLocation("AP-S-03-37", cryofluidMixer, 58L),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                        });
                                    });
                                });
                            });
                        });

                        //logic disabled until further notice
                        apNode(createApLocation("AP-S-03-38", microProcessor, 59L), () -> {
                            apNode(createApLocation("AP-S-03-39", switchBlock, 60L), () -> {
                                apNode(createApLocation("AP-S-03-40", message, 61L), () -> {
                                    apNode(createApLocation("AP-S-03-41", logicDisplay, 62L), () -> {
                                        apNode(createApLocation("AP-S-03-42", largeLogicDisplay, 63L), () -> {
                                        });
                                    });

                                    apNode(createApLocation("AP-S-03-43", memoryCell, 64L), () -> {
                                        apNode(createApLocation("AP-S-03-44", memoryBank, 65L), () -> {
                                        });
                                    });
                                });

                                apNode(createApLocation("AP-S-03-45", logicProcessor, 66L), () -> {
                                    apNode(createApLocation("AP-S-03-46", hyperProcessor, 67L), () -> {
                                    });
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-03-47", illuminator, 68L), () -> {
                        });
                    });
                });


                apNode(createApLocation("AP-S-03-48", combustionGenerator, 69L), () -> {
                    apNode(createApLocation("AP-S-03-49", powerNode, 70L), () -> {
                        apNode(createApLocation("AP-S-03-50", powerNodeLarge, 71L),() -> {
                            apNode(createApLocation("AP-S-03-51", diode, 72L),() -> {
                                apNode(createApLocation("AP-S-03-52", surgeTower, 73L),() -> {
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-03-53", battery, 74L),() -> {
                            apNode(createApLocation("AP-S-03-54", batteryLarge, 75L),() -> {
                            });
                        });

                        apNode(createApLocation("AP-S-03-55", mender, 76L),() -> {
                            apNode(createApLocation("AP-S-03-56", mendProjector, 77L),() -> {
                                apNode(createApLocation("AP-S-03-57", forceProjector, 78L),
                                        Seq.with(new SectorComplete(impact0078)), () -> {
                                            apNode(createApLocation("AP-S-03-58", overdriveProjector, 79L),
                                            Seq.with(new SectorComplete(impact0078)), () -> {
                                                        apNode(createApLocation("AP-S-03-59", overdriveDome, 80L),
                                                Seq.with(new SectorComplete(impact0078)), () -> {
                                        });
                                    });
                                });

                                apNode(createApLocation("AP-S-03-60", repairPoint, 81L),() -> {
                                    apNode(createApLocation("AP-S-03-61", repairTurret, 82L),() -> {
                                    });
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-03-62", steamGenerator, 83L),
                                Seq.with(new SectorComplete(craters)), () -> {
                                    apNode(createApLocation("AP-S-03-63", thermalGenerator, 84L),
                                    Seq.with(new SectorComplete(craters)),() -> {
                                                apNode(createApLocation("AP-S-03-64", differentialGenerator, 85L),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                                            apNode(createApLocation("AP-S-03-65", thoriumReactor, 86L),
                                            Seq.with(new Research(Liquids.cryofluid),
                                                    new SectorComplete(craters)), () -> {
                                                                        apNode(createApLocation("AP-S-03-66", impactReactor, 87L),
                                                Seq.with(new SectorComplete(craters),
                                                        new Research(Liquids.cryofluid)),() -> {
                                        });

                                                                        apNode(createApLocation("AP-S-03-67", rtgGenerator, 88L),
                                                Seq.with(new SectorComplete(craters),
                                                        new Research(Liquids.cryofluid)),() -> {
                                        });
                                    });
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-03-68", solarPanel, 89L), () -> {
                            apNode(createApLocation("AP-S-03-69", largeSolarPanel, 90L),() -> {
                            });
                        });
                    });
                });
            });

            node(duo , () -> {
                node(copperWall , () -> {
                    apNode(createApLocation("AP-S-04-03", copperWallLarge, 93L), () -> {
                        apNode(createApLocation("AP-S-04-04", titaniumWall, 94L), () -> {
                            apNode(createApLocation("AP-S-04-05", titaniumWallLarge, 95L));

                            apNode(createApLocation("AP-S-04-06", door, 96L), () -> {
                                apNode(createApLocation("AP-S-04-07", doorLarge, 97L));
                            });
                            apNode(createApLocation("AP-S-04-08", plastaniumWall, 98L), () -> {
                                apNode(createApLocation("AP-S-04-09", plastaniumWallLarge, 99L), () -> {
                                });
                            });
                            apNode(createApLocation("AP-S-04-10", thoriumWall, 100L), () -> {
                                apNode(createApLocation("AP-S-04-11", thoriumWallLarge, 101L));
                                apNode(createApLocation("AP-S-04-12", surgeWall, 102L), () -> {
                                    apNode(createApLocation("AP-S-04-13", surgeWallLarge, 103L));
                                    apNode(createApLocation("AP-S-04-14", phaseWall, 104L), () -> {
                                        apNode(createApLocation("AP-S-04-15", phaseWallLarge, 105L));
                                    });
                                });
                            });
                        });
                    });
                });

                node(scatter , () -> {
                    apNode(createApLocation("AP-S-04-17", hail, 107L),
                            Seq.with(new SectorComplete(craters)), () -> {
                                apNode(createApLocation("AP-S-04-18", salvo, 108L),
                                Seq.with(new SectorComplete(craters)), () -> {
                                            apNode(createApLocation("AP-S-04-19", swarmer, 109L),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                                        apNode(createApLocation("AP-S-04-20", cyclone, 110L),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                                                    apNode(createApLocation("AP-S-04-21", spectre, 111L),
                                            Seq.with(new SectorComplete(nuclearComplex),
                                                    new SectorComplete(craters)), () -> {
                                    });
                                });
                            });

                                            apNode(createApLocation("AP-S-04-22", ripple, 112L),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                                        apNode(createApLocation("AP-S-04-23", fuse, 113L),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                });
                            });
                        });
                    });
                });

                apNode(createApLocation("AP-S-04-24", scorch, 114L), () -> {
                    apNode(createApLocation("AP-S-04-25", arc, 115L), () -> {
                        apNode(createApLocation("AP-S-04-26", wave, 116L), () -> {
                            apNode(createApLocation("AP-S-04-27", parallax, 117L), () -> {
                                apNode(createApLocation("AP-S-04-28", segment, 118L), () -> {
                                });
                            });

                            apNode(createApLocation("AP-S-04-29", tsunami, 119L), () -> {
                            });
                        });

                        apNode(createApLocation("AP-S-04-30", lancer, 120L), () -> {
                            apNode(createApLocation("AP-S-04-31", meltdown, 121L), () -> {
                                apNode(createApLocation("AP-S-04-32", foreshadow, 122L),
                                        () -> {
                                });
                            });

                            apNode(createApLocation("AP-S-04-33", shockMine, 123L), () -> {
                            });
                        });
                    });
                });
            });

            apNode(createApLocation("Victory", null, -1L,
                    LocationResearchCost.reqCopper(2000)));

            apNode(createApLocation("AP-S-05-01", groundFactory, 124L), () -> {

                apNode(createApLocation("AP-S-05-02", dagger, 125L), () -> {
                    apNode(createApLocation("AP-S-05-03", mace, 126L), () -> {
                        apNode(createApLocation("AP-S-05-04", fortress, 127L), () -> {
                            apNode(createApLocation("AP-S-05-05", scepter, 128L), () -> {
                                apNode(createApLocation("AP-S-05-06", reign, 129L), () -> {
                                });
                            });
                        });
                    });

                    apNode(createApLocation("AP-S-05-07", nova, 130L), () -> {
                        apNode(createApLocation("AP-S-05-08", pulsar, 131L), () -> {
                            apNode(createApLocation("AP-S-05-09", quasar, 132L), () -> {
                                apNode(createApLocation("AP-S-05-10", vela, 133L),
                                        () -> {
                                            apNode(createApLocation("AP-S-05-11", corvus, 134L), () -> {
                                    });
                                });
                            });
                        });
                    });

                    apNode(createApLocation("AP-S-05-12", crawler, 135L), () -> {
                        apNode(createApLocation("AP-S-05-13", atrax, 136L), () -> {
                            apNode(createApLocation("AP-S-05-14", spiroct, 137L), () -> {
                                apNode(createApLocation("AP-S-05-15", arkyid, 138L), () -> {
                                    apNode(createApLocation("AP-S-05-16", toxopid, 139L), () -> {
                                    });
                                });
                            });
                        });
                    });
                });

                apNode(createApLocation("AP-S-05-17", airFactory, 140L), () -> {
                    apNode(createApLocation("AP-S-05-18", flare, 141L), () -> {
                        apNode(createApLocation("AP-S-05-19", horizon, 142L), () -> {
                            apNode(createApLocation("AP-S-05-20", zenith, 143L), () -> {
                                apNode(createApLocation("AP-S-05-21", antumbra, 144L), () -> {
                                    apNode(createApLocation("AP-S-05-22", eclipse, 145L), () -> {
                                    });
                                });
                            });
                        });

                        apNode(createApLocation("AP-S-05-23", mono, 146L), () -> {
                            apNode(createApLocation("AP-S-05-24", poly, 147L), () -> {
                                apNode(createApLocation("AP-S-05-25", mega, 148L), () -> {
                                    apNode(createApLocation("AP-S-05-26", quad, 149L), () -> {
                                        apNode(createApLocation("AP-S-05-27", oct, 150L), () -> {
                                        });
                                    });
                                });
                            });
                        });
                    });

                    apNode(createApLocation("AP-S-05-28", navalFactory, 151L),
                            Seq.with(new SectorComplete(ruinousShores)), () -> {
                                apNode(createApLocation("AP-S-05-29", risso, 152L),
                                Seq.with(new SectorComplete(ruinousShores)),() -> {
                                            apNode(createApLocation("AP-S-05-30", minke, 153L),
                                    Seq.with(new SectorComplete(ruinousShores)),() -> {
                                                        apNode(createApLocation("AP-S-05-31", bryde, 154L),
                                        Seq.with(new SectorComplete(ruinousShores)),() -> {
                                                                    apNode(createApLocation("AP-S-05-32", sei, 155L),
                                            Seq.with(new SectorComplete(ruinousShores)),() -> {
                                                                                apNode(createApLocation("AP-S-05-33", omura, 156L),
                                                Seq.with(new SectorComplete(ruinousShores)),() -> {
                                        });
                                    });
                                });
                            });

                                            apNode(createApLocation("AP-S-05-34", retusa, 157L),
                                    Seq.with(new SectorComplete(windsweptIslands),
                                            new SectorComplete(ruinousShores)), () -> {
                                                        apNode(createApLocation("AP-S-05-35", oxynoe, 158L),
                                        Seq.with(new Objectives.SectorComplete(coastline),
                                                new SectorComplete(windsweptIslands),
                                                new SectorComplete(ruinousShores)), () -> {
                                                                    apNode(createApLocation("AP-S-05-36", cyerce, 159L),
                                            Seq.with(new Objectives.SectorComplete(coastline),
                                                    new SectorComplete(windsweptIslands),
                                                    new SectorComplete(ruinousShores)),() -> {
                                                                                apNode(createApLocation("AP-S-05-37", aegires, 160L),
                                                Seq.with(new Objectives.SectorComplete(coastline),
                                                        new SectorComplete(windsweptIslands),
                                                        new SectorComplete(ruinousShores)),() -> {
                                                                                            apNode(createApLocation("AP-S-05-38", navanax, 161L),
                                                    Seq.with(new SectorComplete(navalFortress),
                                                            new SectorComplete(coastline),
                                                            new SectorComplete(windsweptIslands),
                                                            new SectorComplete(ruinousShores)), () -> {
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                apNode(createApLocation("AP-S-05-39", additiveReconstructor, 162L),
                        Seq.with(new SectorComplete(biomassFacility)), () -> {
                            apNode(createApLocation("AP-S-05-40", multiplicativeReconstructor, 163L),
                            Seq.with(new SectorComplete(biomassFacility)),() -> {
                                        apNode(createApLocation("AP-S-06-41", exponentialReconstructor, 164L),
                                Seq.with(new SectorComplete(overgrowth),
                                        new SectorComplete(biomassFacility)), () -> {
                                                    apNode(createApLocation("AP-S-05-42", tetrativeReconstructor, 165L),
                                    Seq.with(new SectorComplete(overgrowth),
                                            new SectorComplete(biomassFacility)),() -> {
                            });
                        });
                    });
                });
            });

            node(groundZero, () -> {
                node(frozenForest, Seq.with(
                        new Objectives.SectorComplete(groundZero)
                ), () -> {
                    node(craters, Seq.with(
                            new Objectives.SectorComplete(frozenForest)
                    ), () -> {
                        node(ruinousShores, Seq.with(
                                new Objectives.SectorComplete(craters)
                        ), () -> {
                            node(windsweptIslands, Seq.with(
                                    new Objectives.SectorComplete(ruinousShores)
                            ), () -> {
                                node(tarFields, Seq.with(
                                        new Objectives.SectorComplete(windsweptIslands)
                                ), () -> {
                                    node(impact0078, Seq.with(
                                            new Objectives.SectorComplete(tarFields)
                                    ), () -> {
                                        node(desolateRift, Seq.with(
                                                new Objectives.SectorComplete(impact0078)
                                        ), () -> {
                                            node(planetaryTerminal, Seq.with(
                                                    new Objectives.SectorComplete(desolateRift),
                                                    new Objectives.SectorComplete(nuclearComplex),
                                                    new Objectives.SectorComplete(overgrowth),
                                                    new Objectives.SectorComplete(extractionOutpost),
                                                    new Objectives.SectorComplete(saltFlats)
                                            ), () -> {

                                            });
                                        });
                                    });
                                });

                                node(extractionOutpost, Seq.with(
                                        new Objectives.SectorComplete(stainedMountains),
                                        new Objectives.SectorComplete(windsweptIslands)
                                ), () -> {

                                });

                                node(saltFlats, Seq.with(
                                        new Objectives.SectorComplete(windsweptIslands)
                                ), () -> {
                                    node(coastline, Seq.with(
                                            new Objectives.SectorComplete(windsweptIslands),
                                            new Objectives.SectorComplete(saltFlats)
                                    ), () -> {
                                        node(navalFortress, Seq.with(
                                                new Objectives.SectorComplete(coastline),
                                                new Objectives.SectorComplete(extractionOutpost)
                                        ), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        node(overgrowth, Seq.with(
                                new Objectives.SectorComplete(craters),
                                new Objectives.SectorComplete(fungalPass)
                        ), () -> {

                        });
                    });

                    node(biomassFacility, Seq.with(
                            new Objectives.SectorComplete(frozenForest)
                    ), () -> {
                        node(stainedMountains, Seq.with(
                                new Objectives.SectorComplete(biomassFacility)
                        ), () -> {
                            node(fungalPass, Seq.with(
                                    new Objectives.SectorComplete(stainedMountains),
                                    new Objectives.Research(groundFactory)
                            ), () -> {
                                node(nuclearComplex, Seq.with(
                                        new Objectives.SectorComplete(fungalPass)
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


}
