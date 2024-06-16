package mindustry.randomizer.techtree;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.UnitTypes;
import mindustry.game.Objectives;
import mindustry.randomizer.Shared;
import mindustry.type.ItemStack;

import mindustry.game.Objectives.*;
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
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.TechTree.nodeRoot;
import static mindustry.content.UnitTypes.bryde;
import static mindustry.content.UnitTypes.minke;
import static mindustry.content.UnitTypes.mono;
import static mindustry.content.UnitTypes.nova;
import static mindustry.content.UnitTypes.oxynoe;
import static mindustry.content.UnitTypes.risso;
/**
 * Class for the initialisation and randomization of Serpulo's tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public abstract class SerpuloTechTreeRandomizer implements TechTreeRandomizer {

    public static void load() {
        Planets.serpulo.techTree = nodeRoot("serpulo", coreShard, () -> {
            node(conveyor , () -> {

                node(createApLocation("AP-S-01-02", "Junction", 1L,
                        LocationResearchCost.req1Item(Items.copper, 100)), () -> {
                    node(createApLocation("AP-S-01-03", "Router", 2L,
                            LocationResearchCost.req1Item(Items.copper, 100)), () -> {
                        node(createApLocation("AP-S-01-04", "Launch Pad", 3L,
                                LocationResearchCost.req4Item(Items.copper, 5400, Items.lead,
                                        7200, Items.titanium, 5200, Items.silicon, 4800)),
                                Seq.with(new SectorComplete(extractionOutpost)),
                                () -> {

                        });

                        node(createApLocation("AP-S-01-05", "Distributor", 4L,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                        node(createApLocation("AP-S-01-06", "Sorter", 5L,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)), () -> {
                            node(createApLocation("AP-S-01-07", "Inverted Sorter", 6L,
                                    LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                            node(createApLocation("AP-S-01-08", "Overflow Gate", 7L,
                                    LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)), () -> {
                                node(createApLocation("AP-S-01-09", "Underflow Gate", 8L,
                                        LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                            });
                        });
                        node(createApLocation("AP-S-01-10", "Container", 9L,
                                LocationResearchCost.req1Item(Items.titanium, 3300)),
                                Seq.with(new SectorComplete(biomassFacility)), () -> {
                            node(createApLocation("AP-S-01-11", "Unloader", 10L,
                                    LocationResearchCost.req2Item(Items.titanium, 700,
                                            Items.silicon, 900)),
                                    Seq.with(new SectorComplete(biomassFacility)), () -> {
                                    });
                            node(createApLocation("AP-S-01-12", "Vault", 11L,
                                    LocationResearchCost.req2Item(Items.titanium, 9200,
                                            Items.thorium, 4300)),
                                    Seq.with(new SectorComplete(biomassFacility),
                                            new SectorComplete(stainedMountains)), () -> {
                            });
                        });

                        node(createApLocation("AP-S-01-13", "Bridge Conveyor", 12L,
                                LocationResearchCost.req2Item(Items.copper, 200, Items.lead, 200)), () -> {
                            node(createApLocation("AP-S-01-14", "Titanium Conveyor", 13L,
                                    LocationResearchCost.req3Item(Items.copper, 80, Items.lead,
                                            80, Items.titanium, 80)),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                node(createApLocation("AP-S-01-15", "Phase Conveyor", 14L,
                                        LocationResearchCost.req4Item(Items.lead, 300, Items.graphite, 300,
                                                Items.silicon, 200, Items.phaseFabric, 100)),
                                       Seq.with(new SectorComplete(craters)), () -> {
                                    node(createApLocation("AP-S-01-16", "Mass Driver", 15L,
                                            LocationResearchCost.req4Item(Items.lead, 4300, Items.titanium, 4300,
                                                    Items.thorium, 1500, Items.silicon, 2400)),
                                            Seq.with(new SectorComplete(craters)), () -> {
                                    });
                                });

                                node(createApLocation("AP-S-01-17", "Payload Conveyor", 16L,
                                        LocationResearchCost.req2Item(Items.copper, 300,
                                                Items.graphite, 300)),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                    node(createApLocation("AP-S-01-18", "Payload Router", 17L,
                                            LocationResearchCost.req2Item(Items.copper, 300,
                                                    Items.graphite, 400)),
                                            Seq.with(new SectorComplete(craters)), () -> {
                                    });
                                });

                                node(createApLocation("AP-S-01-19", "Armored Conveyor", 18L,
                                        LocationResearchCost.req3Item(Items.metaglass, 80, Items.thorium,
                                                80, Items.plastanium, 80)),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                    node(createApLocation("AP-S-01-20", "Plastanium Conveyor", 19L,
                                            LocationResearchCost.req3Item(Items.graphite, 80, Items.silicon,
                                                    80, Items.plastanium, 80)),
                                            Seq.with(new SectorComplete(craters)),() -> {
                                    });
                                });
                            });
                        });
                    });
                });
            });

            node(createApLocation("AP-S-02-01", "Core: Foundation", 20L,
                    LocationResearchCost.req3Item(Items.copper, 10000, Items.lead, 10000,
                            Items.silicon, 6400)), () -> {
                node(createApLocation("AP-S-02-02", "Core: Nucleus", 21L,
                        LocationResearchCost.req4Item(Items.copper, 47000, Items.lead, 47000,
                                Items.thorium, 21000, Items.silicon, 28000)), () -> {
                });
            });

            node(mechanicalDrill , () -> {

                node(createApLocation("AP-S-03-02", "Mechanical Pump", 23L,
                        LocationResearchCost.req2Item(Items.copper, 400, Items.metaglass, 300)), () -> {
                    node(createApLocation("AP-S-03-03", "Conduit", 24L,
                            LocationResearchCost.req1Item(Items.metaglass, 80)), () -> {
                        node(createApLocation("AP-S-03-04", "Liquid Junction", 25L,
                                LocationResearchCost.req2Item(Items.metaglass, 200, Items.graphite,
                                        100)), () -> {
                            node(createApLocation("AP-S-03-05", "Liquid Router", 26L,
                                    LocationResearchCost.req2Item(Items.metaglass, 100,
                                            Items.graphite, 100)), () -> {
                                node(createApLocation("AP-S-03-06", "Liquid Container", 27L,
                                        LocationResearchCost.req2Item(Items.metaglass, 400,
                                                Items.titanium, 300)), () -> {
                                    node(createApLocation("AP-S-03-07", "Liquid Tank", 28L,
                                            LocationResearchCost.req2Item(Items.metaglass, 1200,
                                                    Items.titanium, 900)));
                                });

                                node(createApLocation("AP-S-03-08", "Bridge Conduit", 29L,
                                        LocationResearchCost.req2Item(Items.metaglass, 200,
                                                Items.graphite, 100)));
                                node(createApLocation("AP-S-03-09", "Pulse Conduit", 30L,
                                        LocationResearchCost.req2Item(Items.metaglass, 80,
                                                Items.titanium, 100)),
                                        Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                    node(createApLocation("AP-S-03-10", "Phase Conduit", 31L,
                                            LocationResearchCost.req4Item(Items.metaglass, 600,
                                                    Items.titanium, 300, Items.silicon, 200,
                                                    Items.phaseFabric, 100)),
                                            Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                    });

                                    node(createApLocation("AP-S-03-11", "Plated Conduit", 32L,
                                            LocationResearchCost.req3Item(Items.metaglass, 80,
                                                    Items.thorium, 100, Items.plastanium, 80)),
                                            Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                    });

                                    node(createApLocation("AP-S-03-12", "Rotary Pump", 33L,
                                            LocationResearchCost.req4Item(Items.copper, 2200,
                                                    Items.metaglass, 1500, Items.titanium, 1000,
                                                    Items.silicon, 600)),
                                            Seq.with(new SectorComplete(windsweptIslands)),() -> {
                                        node(createApLocation("AP-S-03-13", "Impulse Pump", 34L,
                                                LocationResearchCost.req5Item(Items.copper, 2600,
                                                        Items.metaglass, 3000, Items.titanium, 1200,
                                                        Items.thorium, 1000, Items.silicon, 900)),
                                                Seq.with(new SectorComplete(windsweptIslands)),() -> {
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-03-14", "Graphite Press", 35L,
                        LocationResearchCost.req2Item(Items.copper, 2400, Items.lead, 900)), () -> {
                    node(createApLocation("AP-S-03-15", "Pneumatic Drill", 36L,
                            LocationResearchCost.req2Item(Items.copper, 500, Items.graphite, 300)),
                            Seq.with(new SectorComplete(frozenForest)), () -> {
                        node(createApLocation("AP-S-03-16", "Cultivator", 37L,
                                LocationResearchCost.req3Item(Items.copper, 700, Items.lead, 700,
                                 Items.silicon, 300)),
                                Seq.with(new SectorComplete(biomassFacility),
                                        new SectorComplete(frozenForest)),
                                () -> {
                        });

                        node(createApLocation("AP-S-03-17", "Lazer Drill", 38L,
                                LocationResearchCost.req4Item(Items.copper, 1000, Items.graphite,
                                        900, Items.titanium, 600, Items.silicon, 900)),
                                Seq.with(new SectorComplete(frozenForest)),() -> {
                            node(createApLocation("AP-S-03-18", "Blast Drill", 39L,
                                    LocationResearchCost.req4Item(Items.copper, 2100,
                                            Items.titanium, 1500, Items.thorium, 2400, Items.silicon,
                                            1900)),
                                    Seq.with(new SectorComplete(nuclearComplex),
                                            new SectorComplete(frozenForest)), () -> {
                            });

                            node(createApLocation("AP-S-03-19", "Water Extractor", 40L,
                                    LocationResearchCost.req4Item(Items.copper, 900, Items.lead,
                                            900, Items.metaglass, 900, Items.graphite, 900)),
                                    Seq.with(new SectorComplete(saltFlats),
                                            new SectorComplete(frozenForest)), () -> {
                                node(createApLocation("AP-S-03-20", "Oil Extractor", 41L,
                                        LocationResearchCost.req5Item(Items.copper, 5200,
                                                Items.lead, 3900, Items.graphite, 6200, Items.thorium,
                                                3900, Items.silicon, 2400)),
                                        Seq.with(new SectorComplete(saltFlats),
                                                new SectorComplete(frozenForest)),() -> {
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-03-21", "Pyratite Mixer", 42L,
                            LocationResearchCost.req2Item(Items.copper, 1500, Items.lead, 700)), () -> {
                        node(createApLocation("AP-S-03-22", "Blast Mixer", 43L,
                                LocationResearchCost.req2Item(Items.lead, 900, Items.titanium, 600)), () -> {
                        });
                    });

                    node(createApLocation("AP-S-03-23", "Silicon Smelter", 44L,
                            LocationResearchCost.req2Item(Items.copper, 900, Items.lead, 700)), () -> {

                        node(createApLocation("AP-S-03-24", "Spore Press", 45L,
                                LocationResearchCost.req2Item(Items.lead, 1000, Items.silicon,
                                        900)), () -> {
                            node(createApLocation("AP-S-03-25", "Coal Centrifuge", 46L,
                                    LocationResearchCost.req3Item(Items.lead, 900, Items.graphite
                                            , 1200, Items.titanium, 600)), () -> {
                                node(createApLocation("AP-S-03-26", "Multi-Press", 47L,
                                        LocationResearchCost.req4Item(Items.lead, 3300,
                                                Items.graphite, 1500, Items.titanium, 3300,
                                                Items.silicon, 700)), () -> {
                                    node(createApLocation("AP-S-03-27", "Silicon Crucible", 48L,
                                            LocationResearchCost.req4Item(Items.metaglass, 2600,
                                                    Items.titanium, 4100, Items.silicon, 1900,
                                                    Items.plastanium, 1000)), () -> {
                                    });
                                });
                            });

                            node(createApLocation("AP-S-03-28", "Plastanium Compressor", 49L,
                                    LocationResearchCost.req4Item(Items.lead, 3900,
                                            Items.graphite, 1900, Items.titanium, 2600,
                                            Items.silicon, 2600)),
                                    Seq.with(new SectorComplete(windsweptIslands)), () -> {
                                node(createApLocation("AP-S-03-29", "Phase Weaver", 50L,
                                        LocationResearchCost.req3Item(Items.lead, 4100,
                                                Items.thorium, 2400, Items.silicon, 4500)),
                                        Seq.with(new SectorComplete(tarFields),
                                                new SectorComplete(windsweptIslands)), () -> {
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-30", "Kiln", 51L,
                                LocationResearchCost.req3Item(Items.copper, 1900, Items.lead, 900,
                                        Items.graphite, 900)),
                                Seq.with(new SectorComplete(craters)), () -> {
                            node(createApLocation("AP-S-03-31", "Pulverizer", 52L,
                                    LocationResearchCost.req2Item(Items.copper, 900, Items.lead,
                                            700)),
                                    Seq.with(new SectorComplete(craters)),() -> {
                                node(createApLocation("AP-S-03-32", "Incinerator", 53L,
                                        LocationResearchCost.req2Item(Items.lead, 400,
                                                Items.graphite, 100)),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                    node(createApLocation("AP-S-03-33", "Melter", 54L,
                                            LocationResearchCost.req3Item(Items.copper, 900,
                                                    Items.lead, 1000, Items.graphite, 1400)),
                                            Seq.with(new SectorComplete(craters)),() -> {
                                        node(createApLocation("AP-S-03-34", "Surge Smelter", 55L,
                                                LocationResearchCost.req3Item(Items.lead, 2600,
                                                        Items.thorium, 2200, Items.silicon, 2600)),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                        });

                                        node(createApLocation("AP-S-03-35", "Separator", 56L,
                                                LocationResearchCost.req2Item(Items.copper, 900,
                                                        Items.titanium, 700)),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                            node(createApLocation("AP-S-03-36", "Disassembler", 57L,
                                                    LocationResearchCost.req4Item(Items.titanium,
                                                     3300, Items.thorium, 2600, Items.silicon,
                                                            5200, Items.plastanium, 1200)),
                                                    Seq.with(new SectorComplete(craters)),() -> {
                                            });
                                        });

                                        node(createApLocation("AP-S-03-37", "Cryofluid Mixer", 58L,
                                                LocationResearchCost.req3Item(Items.lead, 2100,
                                                        Items.titanium, 1900, Items.silicon, 1200)),
                                                Seq.with(new SectorComplete(craters)),() -> {
                                        });
                                    });
                                });
                            });
                        });

                        //logic disabled until further notice
                        node(createApLocation("AP-S-03-38", "Micro Processor", 59L,
                                LocationResearchCost.req3Item(Items.copper, 3000, Items.lead,
                                        1500, Items.silicon, 1500)), () -> {
                            node(createApLocation("AP-S-03-39", "Switch Block", 60L,
                                    LocationResearchCost.req2Item(Items.copper, 100,
                                            Items.graphite, 100)), () -> {
                                node(createApLocation("AP-S-03-40", "Message", 61L,
                                        LocationResearchCost.req2Item(Items.copper, 100,
                                                Items.graphite, 100)), () -> {
                                    node(createApLocation("AP-S-03-41", "Logic Display", 62L,
                                            LocationResearchCost.req3Item(Items.lead, 3300,
                                                    Items.metaglass, 1500, Items.silicon, 1500)), () -> {
                                        node(createApLocation("AP-S-03-42", "Large Logic Display", 63L,
                                                LocationResearchCost.req4Item(Items.lead, 7200,
                                                        Items.metaglass, 3300, Items.silicon,
                                                        5200, Items.phaseFabric, 2400)), () -> {
                                        });
                                    });

                                    node(createApLocation("AP-S-03-43", "Memory Cell", 64L,
                                            LocationResearchCost.req3Item(Items.copper, 900,
                                                    Items.graphite, 900, Items.silicon, 900)), () -> {
                                        node(createApLocation("AP-S-03-44", "Memory Bank", 65L,
                                                LocationResearchCost.req4Item(Items.copper, 900,
                                                        Items.graphite, 2600, Items.silicon, 2600,
                                                        Items.phaseFabric, 900)), () -> {
                                        });
                                    });
                                });

                                node(createApLocation("AP-S-03-45", "Logic Processor", 66L,
                                        LocationResearchCost.req4Item(Items.lead, 12000,
                                                Items.graphite, 1900, Items.thorium, 1500,
                                                Items.silicon, 2600)), () -> {
                                    node(createApLocation("AP-S-03-46", "Hyper Processor", 67L,
                                            LocationResearchCost.req4Item(Items.lead, 17000,
                                                    Items.thorium, 2400, Items.silicon, 5200,
                                                    Items.surgeAlloy, 1500)), () -> {
                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-47", "Illuminator", 68L,
                                LocationResearchCost.req3Item(Items.lead, 200, Items.graphite,
                                        300, Items.silicon, 200)), () -> {
                        });
                    });
                });


                node(createApLocation("AP-S-03-48", "Combustion Generator", 69L,
                        LocationResearchCost.req2Item(Items.copper, 700, Items.lead, 400)),
                        Seq.with(new Research(Items.coal)), () -> {
                    node(createApLocation("AP-S-03-49", "Power Node", 70L,
                            LocationResearchCost.req2Item(Items.copper, 80, Items.lead, 100)), () -> {
                        node(createApLocation("AP-S-03-50", "Large Power Node", 71L,
                                LocationResearchCost.req3Item(Items.lead, 300, Items.titanium,
                                        100, Items.silicon, 100)),() -> {
                            node(createApLocation("AP-S-03-51", "Battery Diode", 72L,
                                    LocationResearchCost.req3Item(Items.metaglass, 300,
                                            Items.silicon, 300, Items.plastanium, 100)),() -> {
                                node(createApLocation("AP-S-03-52", "Surge Tower", 73L,
                                        LocationResearchCost.req4Item(Items.lead, 300,
                                                Items.titanium, 200, Items.silicon, 400,
                                                Items.surgeAlloy, 400)),() -> {
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-53", "Battery", 74L,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 600)),() -> {
                            node(createApLocation("AP-S-03-54", "Large Battery", 75L,
                                    LocationResearchCost.req3Item(Items.lead, 1500,
                                            Items.titanium, 600, Items.silicon, 900)),() -> {
                            });
                        });

                        node(createApLocation("AP-S-03-55", "Mender", 76L,
                                LocationResearchCost.req2Item(Items.copper, 700, Items.lead, 900)),() -> {
                            node(createApLocation("AP-S-03-56", "Mend Projector", 77L,
                                    LocationResearchCost.req4Item(Items.copper, 1500, Items.lead,
                                     3300, Items.titanium, 700, Items.silicon, 1200)),() -> {
                                node(createApLocation("AP-S-03-57", "Force Projector", 78L,
                                        LocationResearchCost.req3Item(Items.lead, 3300,
                                                Items.titanium, 2400, Items.silicon, 4300)),
                                        Seq.with(new SectorComplete(impact0078)), () -> {
                                    node(createApLocation("AP-S-03-58", "Overdrive Projector", 79L,
                                            LocationResearchCost.req4Item(Items.lead, 3300,
                                                    Items.titanium, 2400, Items.silicon, 2400,
                                                    Items.plastanium, 900)),
                                            Seq.with(new SectorComplete(impact0078)), () -> {
                                        node(createApLocation("AP-S-03-59", "Overdrive Dome", 80L,
                                                LocationResearchCost.req5Item(Items.lead, 7200,
                                                        Items.titanium, 4500, Items.silicon, 4500,
                                                        Items.plastanium, 2600, Items.surgeAlloy, 4100)),
                                                Seq.with(new SectorComplete(impact0078)), () -> {
                                        });
                                    });
                                });

                                node(createApLocation("AP-S-03-60", "Repair Point", 81L,
                                        LocationResearchCost.req3Item(Items.copper, 900,
                                                Items.lead, 900, Items.silicon, 600)),() -> {
                                    node(createApLocation("AP-S-03-61", "Repair Turret", 82L,
                                            LocationResearchCost.req3Item(Items.thorium, 2600,
                                                    Items.silicon, 3000, Items.plastanium, 1900)),() -> {
                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-62", "Steam Generator", 83L,
                                LocationResearchCost.req4Item(Items.copper, 1000, Items.lead,
                                        1200, Items.graphite, 700, Items.silicon, 900)),
                                Seq.with(new SectorComplete(craters)), () -> {
                            node(createApLocation("AP-S-03-63", "Thermal Generator", 84L,
                                    LocationResearchCost.req5Item(Items.copper, 1200, Items.lead,
                                     1500, Items.metaglass, 1200, Items.graphite, 1000,
                                            Items.silicon, 1000)),
                                    Seq.with(new SectorComplete(craters)),() -> {
                                node(createApLocation("AP-S-03-64", "Differential Generator", 85L,
                                        LocationResearchCost.req5Item(Items.copper, 2200, Items.lead,
                                                3300, Items.metaglass, 1500, Items.titanium, 1500,
                                                Items.silicon, 2100)),
                                        Seq.with(new SectorComplete(craters)),() -> {
                                    node(createApLocation("AP-S-03-65", "Thorium Reactor", 86L,
                                            LocationResearchCost.req5Item(Items.lead, 2200,
                                                    Items.metaglass, 3300, Items.graphite, 1500, Items.thorium, 1500,
                                                    Items.silicon, 2100)),
                                            Seq.with(new Research(Liquids.cryofluid),
                                                    new SectorComplete(craters)), () -> {
                                        node(createApLocation("AP-S-03-66", "Impact Reactor", 87L,
                                                LocationResearchCost.req6Item(Items.lead, 19000,
                                                        Items.metaglass, 9200, Items.graphite, 15000,
                                                        Items.thorium, 3300, Items.silicon, 11000,
                                                        Items.surgeAlloy, 9200)),
                                                Seq.with(new SectorComplete(craters),
                                                        new Research(Liquids.cryofluid)),() -> {
                                        });

                                        node(createApLocation("AP-S-03-67", "Rtg Generator", 88L,
                                                LocationResearchCost.req5Item(Items.lead, 3300,
                                                        Items.thorium, 1500, Items.silicon, 2400,
                                                        Items.plastanium, 2400, Items.phaseFabric,
                                                        700)),
                                                Seq.with(new SectorComplete(craters),
                                                        new Research(Liquids.cryofluid)),() -> {
                                        });
                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-68", "Solar Panel", 89L,
                                LocationResearchCost.req2Item(Items.lead, 300, Items.silicon, 400)), () -> {
                            node(createApLocation("AP-S-03-69", "Large Solar Panel", 90L,
                                    LocationResearchCost.req3Item(Items.lead, 2600, Items.silicon,
                                            3700, Items.phaseFabric, 400)),() -> {
                            });
                        });
                    });
                });
            });

            node(duo , () -> {
                node(copperWall , () -> {
                    node(createApLocation("AP-S-04-03", "Large Copper Wall", 93L,
                            LocationResearchCost.req1Item(Items.copper, 700)), () -> {
                        node(createApLocation("AP-S-04-04", "Titanium Wall", 94L,
                                LocationResearchCost.req1Item(Items.titanium, 200)), () -> {
                            node(createApLocation("AP-S-04-05", "Large Titanium Wall", 95L,
                                    LocationResearchCost.req1Item(Items.titanium, 700)));

                            node(createApLocation("AP-S-04-06", "Door", 96L,
                                    LocationResearchCost.req2Item(Items.titanium, 200,
                                            Items.silicon, 100)), () -> {
                                node(createApLocation("AP-S-04-07", "Large Door", 97L,
                                        LocationResearchCost.req2Item(Items.titanium, 700,
                                                Items.silicon, 400)));
                            });
                            node(createApLocation("AP-S-04-08", "Plastanium Wall", 98L,
                                    LocationResearchCost.req2Item(Items.metaglass, 100,
                                            Items.plastanium, 100)), () -> {
                                node(createApLocation("AP-S-04-09", "Large Plastanium Wall", 99L,
                                        LocationResearchCost.req2Item(Items.metaglass, 200,
                                                Items.plastanium, 600)), () -> {
                                });
                            });
                            node(createApLocation("AP-S-04-10", "Thorium Wall", 100L,
                                    LocationResearchCost.req1Item(Items.thorium, 200)), () -> {
                                node(createApLocation("AP-S-04-11", "Large Thorium Wall", 101L,
                                        LocationResearchCost.req1Item(Items.thorium, 700)));
                                node(createApLocation("AP-S-04-12", "Surge Wall", 102L,
                                        LocationResearchCost.req1Item(Items.surgeAlloy, 200)), () -> {
                                    node(createApLocation("AP-S-04-13", "Large Surge Wall", 103L,
                                            LocationResearchCost.req1Item(Items.surgeAlloy, 700)));
                                    node(createApLocation("AP-S-04-14", "Phase Wall", 104L,
                                            LocationResearchCost.req1Item(Items.phaseFabric, 200)), () -> {
                                        node(createApLocation("AP-S-04-15", "Large Phase Wall", 105L,
                                                LocationResearchCost.req1Item(Items.phaseFabric, 700)));
                                    });
                                });
                            });
                        });
                    });
                });

                node(scatter , () -> {
                    node(createApLocation("AP-S-04-17", "Hail", 107L,
                            LocationResearchCost.req2Item(Items.copper, 1200, Items.graphite, 500)),
                            Seq.with(new SectorComplete(craters)), () -> {
                        node(createApLocation("AP-S-04-18", "Salvo", 108L,
                                LocationResearchCost.req3Item(Items.copper, 3300, Items.graphite,
                                        2600, Items.titanium, 1500)),
                                Seq.with(new SectorComplete(craters)), () -> {
                            node(createApLocation("AP-S-04-19", "Swarmer", 109L,
                                    LocationResearchCost.req4Item(Items.graphite, 1000,
                                            Items.titanium, 1000, Items.silicon, 900,
                                            Items.plastanium, 1400)),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                node(createApLocation("AP-S-04-20", "Cyclone", 110L,
                                        LocationResearchCost.req3Item(Items.copper, 7200,
                                                Items.titanium, 4300, Items.plastanium, 2600)),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                    node(createApLocation("AP-S-04-21", "Spectre", 111L,
                                            LocationResearchCost.req5Item(Items.copper, 38000,
                                                    Items.graphite, 11000, Items.thorium, 9200,
                                                    Items.plastanium, 6200, Items.surgeAlloy, 9200)),
                                            Seq.with(new SectorComplete(nuclearComplex),
                                                    new SectorComplete(craters)), () -> {
                                    });
                                });
                            });

                            node(createApLocation("AP-S-04-22", "Ripple", 112L,
                                    LocationResearchCost.req3Item(Items.copper, 5200,
                                            Items.graphite, 4600, Items.titanium, 1900)),
                                    Seq.with(new SectorComplete(craters)), () -> {
                                node(createApLocation("AP-S-04-23", "Fuse", 113L,
                                        LocationResearchCost.req3Item(Items.copper, 8200,
                                                Items.graphite, 8200, Items.thorium, 3300)),
                                        Seq.with(new SectorComplete(craters)), () -> {
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-04-24", "Scorch", 114L,
                        LocationResearchCost.req2Item(Items.copper, 700, Items.graphite, 600)), () -> {
                    node(createApLocation("AP-S-04-25", "Arc", 115L,
                            LocationResearchCost.req2Item(Items.copper, 1500, Items.lead, 1500)), () -> {
                        node(createApLocation("AP-S-04-26", "Wave", 116L,
                                LocationResearchCost.req3Item(Items.copper, 700, Items.lead, 2400
                                        , Items.metaglass, 1400)), () -> {
                            node(createApLocation("AP-S-04-27", "Parallax", 117L,
                                    LocationResearchCost.req3Item(Items.graphite, 900,
                                            Items.titanium, 3000, Items.silicon, 4100)), () -> {
                                node(createApLocation("AP-S-04-28", "Segment", 118L,
                                        LocationResearchCost.req4Item(Items.titanium, 1200,
                                                Items.thorium, 2600, Items.silicon, 4500,
                                                Items.phaseFabric, 1200)), () -> {
                                });
                            });

                            node(createApLocation("AP-S-04-29", "Tsunami", 119L,
                                    LocationResearchCost.req4Item(Items.lead, 15000,
                                            Items.metaglass, 3300, Items.titanium, 9200,
                                            Items.thorium, 3300)), () -> {
                            });
                        });

                        node(createApLocation("AP-S-04-30", "Lancer", 120L,
                                LocationResearchCost.req4Item(Items.copper, 1900, Items.lead,
                                        2200, Items.titanium, 900, Items.silicon, 1900)), () -> {
                            node(createApLocation("AP-S-04-31", "Meltdown", 121L,
                                    LocationResearchCost.req5Item(Items.copper, 52000, Items.lead
                                            , 13000, Items.graphite, 11000, Items.silicon, 12000,
                                            Items.surgeAlloy, 12000)), () -> {
                                node(createApLocation("AP-S-04-32", "Foreshadow", 122L,
                                        LocationResearchCost.req5Item(Items.copper, 42000,
                                                Items.metaglass, 24000, Items.silicon, 24000,
                                                Items.plastanium, 7200, Items.surgeAlloy, 11000)),
                                        () -> {
                                });
                            });

                            node(createApLocation("AP-S-04-33", "Shock Mine", 123L,
                                    LocationResearchCost.req2Item(Items.lead, 700, Items.silicon,
                                     300)), () -> {
                            });
                        });
                    });
                });
            });

            node(createApLocation("AP-W-00-01", "Victory", -1L,
                    LocationResearchCost.reqCopper(500)));

            node(createApLocation("AP-S-05-01", "Ground Factory", 124L,
                    LocationResearchCost.req3Item(Items.copper, 1500, Items.lead, 4100,
                            Items.silicon, 2600)), () -> {

                node(createApLocation("AP-S-05-02", "Dagger", 125L,
                        LocationResearchCost.req2Item(Items.silicon, 600, Items.lead, 600)), () -> {
                    node(createApLocation("AP-S-05-03", "Mace", 126L,
                            LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite,
                                    2800)), () -> {
                        node(createApLocation("AP-S-05-04", "Fortress", 127L,
                                LocationResearchCost.req3Item(Items.silicon, 10000,
                                        Items.titanium, 6100, Items.metaglass, 2800)), () -> {
                            node(createApLocation("AP-S-05-05", "Scepter", 128L,
                                    LocationResearchCost.req3Item(Items.silicon, 83000,
                                            Items.titanium, 72000, Items. plastanium, 62000)), () -> {
                                node(createApLocation("AP-S-05-06", "Reign", 129L,
                                        LocationResearchCost.req4Item(Items.silicon, 99000,
                                                Items.plastanium, 56000, Items.surgeAlloy, 46000,
                                                Items.phaseFabric, 31000)), () -> {
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-07", "Nova", 130L,
                            LocationResearchCost.req3Item(Items.silicon, 2100, Items.lead, 1300,
                                    Items.titanium, 1300)), () -> {
                        node(createApLocation("AP-S-05-08", "Pulsar", 131L,
                                LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite
                                        , 2800)), () -> {
                            node(createApLocation("AP-S-05-09", "Quasar", 132L,
                                    LocationResearchCost.req3Item(Items.silicon, 10000,
                                            Items.titanium, 6100, Items.metaglass, 2800)), () -> {
                                node(createApLocation("AP-S-05-10", "Vela", 133L,
                                        LocationResearchCost.req3Item(Items.silicon, 83000,
                                                Items.titanium, 72000, Items.plastanium, 62000)),
                                        () -> {
                                    node(createApLocation("AP-S-05-11", "Corvus", 134L,
                                            LocationResearchCost.req4Item(Items.silicon, 99000,
                                                    Items.plastanium, 56000, Items.surgeAlloy,
                                                    46000, Items.phaseFabric, 31000)), () -> {
                                    });
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-12", "Crawler", 135L,
                            LocationResearchCost.req2Item(Items.silicon, 400, Items.coal, 600)), () -> {
                        node(createApLocation("AP-S-05-13", "Atrax", 136L,
                                LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite
                                        , 2800)), () -> {
                            node(createApLocation("AP-S-05-14", "Spiroct", 137L,
                                    LocationResearchCost.req3Item(Items.silicon, 10000,
                                            Items.titanium, 6100, Items.metaglass, 2800)), () -> {
                                node(createApLocation("AP-S-05-15", "Arkyid", 138L,
                                        LocationResearchCost.req3Item(Items.silicon, 83000,
                                                Items.titanium, 72000, Items.plastanium, 62000)), () -> {
                                    node(createApLocation("AP-S-05-16", "Toxopid", 139L,
                                            LocationResearchCost.req4Item(Items.silicon, 99000,
                                                    Items.plastanium, 56000, Items.surgeAlloy,
                                                    46000, Items.phaseFabric, 31000)), () -> {
                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-05-17", "Air Factory", 140L,
                        LocationResearchCost.req2Item(Items.copper, 1900, Items.lead, 2200)), () -> {
                    node(createApLocation("AP-S-05-18", "Flare", 141L,
                            LocationResearchCost.req1Item(Items.silicon, 900)), () -> {
                        node(createApLocation("AP-S-05-19", "Horizon", 142L,
                                LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite
                                        , 2800)), () -> {
                            node(createApLocation("AP-S-05-20", "Zenith", 143L,
                                    LocationResearchCost.req3Item(Items.silicon, 10000,
                                            Items.titanium, 6100, Items.metaglass, 2800)), () -> {
                                node(createApLocation("AP-S-05-21", "Antumbra", 144L,
                                        LocationResearchCost.req3Item(Items.silicon, 83000,
                                                Items.titanium, 72000, Items.plastanium, 62000)), () -> {
                                    node(createApLocation("AP-S-05-22", "Eclipse", 145L,
                                            LocationResearchCost.req4Item(Items.silicon, 99000,
                                                    Items.plastanium, 56000, Items.surgeAlloy,
                                                    46000, Items.phaseFabric, 31000)), () -> {
                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-05-23", "Mono", 146L,
                                LocationResearchCost.req2Item(Items.silicon, 2100, Items.lead,
                                        900)), () -> {
                            node(createApLocation("AP-S-05-24", "Poly", 147L,
                                    LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite,
                                            2800)), () -> {
                                node(createApLocation("AP-S-05-25", "Mega", 148L,
                                        LocationResearchCost.req3Item(Items.silicon, 10000,
                                                Items.titanium, 6100, Items.metaglass, 2800)), () -> {
                                    node(createApLocation("AP-S-05-26", "Quad", 149L,
                                            LocationResearchCost.req3Item(Items.silicon, 83000,
                                                    Items.titanium, 72000, Items.plastanium, 62000)), () -> {
                                        node(createApLocation("AP-S-05-27", "Oct", 150L,
                                                LocationResearchCost.req4Item(Items.silicon, 99000,
                                                        Items.plastanium, 56000, Items.surgeAlloy,
                                                        46000, Items.phaseFabric, 31000)), () -> {
                                        });
                                    });
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-28", "Naval Factory", 151L,
                            LocationResearchCost.req3Item(Items.copper, 5200, Items.lead, 4500,
                                    Items.metaglass, 4100)),
                            Seq.with(new SectorComplete(ruinousShores)), () -> {
                        node(createApLocation("AP-S-05-29", "Risso", 152L,
                                LocationResearchCost.req2Item(Items.silicon, 1300,
                                        Items.metaglass, 2400)),
                                Seq.with(new SectorComplete(ruinousShores)),() -> {
                            node(createApLocation("AP-S-05-30", "Minke", 153L,
                                    LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite,
                                            2800)),
                                    Seq.with(new SectorComplete(ruinousShores)),() -> {
                                node(createApLocation("AP-S-05-31", "Bryde", 154L,
                                        LocationResearchCost.req3Item(Items.silicon, 10000,
                                                Items.titanium, 6100, Items.metaglass, 2800)),
                                        Seq.with(new SectorComplete(ruinousShores)),() -> {
                                    node(createApLocation("AP-S-05-32", "Sei", 155L,
                                            LocationResearchCost.req3Item(Items.silicon, 83000,
                                                    Items.titanium, 72000, Items.plastanium, 62000)),
                                            Seq.with(new SectorComplete(ruinousShores)),() -> {
                                        node(createApLocation("AP-S-05-33", "Omura", 156L,
                                                LocationResearchCost.req4Item(Items.silicon, 99000,
                                                        Items.plastanium, 56000, Items.surgeAlloy,
                                                        46000, Items.phaseFabric, 31000)),
                                                Seq.with(new SectorComplete(ruinousShores)),() -> {
                                        });
                                    });
                                });
                            });

                            node(createApLocation("AP-S-05-34", "Retusa", 157L,
                                    LocationResearchCost.req3Item(Items.silicon, 900,
                                            Items.metaglass, 1700, Items.titanium, 1300)),
                                    Seq.with(new SectorComplete(windsweptIslands),
                                            new SectorComplete(ruinousShores)), () -> {
                                node(createApLocation("AP-S-05-35", "Oxynoe", 158L,
                                        LocationResearchCost.req2Item(Items.silicon, 2800, Items.graphite, 2800)),
                                        Seq.with(new Objectives.SectorComplete(coastline),
                                                new SectorComplete(windsweptIslands),
                                                new SectorComplete(ruinousShores)), () -> {
                                    node(createApLocation("AP-S-05-36", "Cyerce", 159L,
                                            LocationResearchCost.req3Item(Items.silicon, 10000,
                                                    Items.titanium, 6100, Items.metaglass, 2800)),
                                            Seq.with(new Objectives.SectorComplete(coastline),
                                                    new SectorComplete(windsweptIslands),
                                                    new SectorComplete(ruinousShores)),() -> {
                                        node(createApLocation("AP-S-05-37", "Aegires", 160L,
                                                LocationResearchCost.req3Item(Items.silicon, 83000,
                                                        Items.titanium, 72000, Items.plastanium, 62000)),
                                                Seq.with(new Objectives.SectorComplete(coastline),
                                                        new SectorComplete(windsweptIslands),
                                                        new SectorComplete(ruinousShores)),() -> {
                                            node(createApLocation("AP-S-05-38", "Navanax", 161L,
                                                    LocationResearchCost.req4Item(Items.silicon, 99000,
                                                            Items.plastanium, 56000, Items.surgeAlloy,
                                                            46000, Items.phaseFabric, 31000)),
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

                node(createApLocation("AP-S-05-39", "Additive Reconstructor", 162L,
                        LocationResearchCost.req3Item(Items.copper, 7200, Items.lead, 4100,
                                Items.silicon, 3000)),
                        Seq.with(new SectorComplete(biomassFacility)), () -> {
                    node(createApLocation("AP-S-05-40", "Multiplicative Reconstructor", 163L,
                            LocationResearchCost.req4Item(Items.lead, 26000, Items.titanium,
                                    13000, Items.thorium, 26000, Items.silicon, 17000)),
                            Seq.with(new SectorComplete(biomassFacility)),() -> {
                        node(createApLocation("AP-S-06-41", "Exponential Reconstructor", 164L,
                                LocationResearchCost.req6Item(Items.lead, 92000, Items.titanium,
                                        92000, Items.thorium, 31000, Items.silicon, 42000,
                                        Items.plastanium, 17000, Items.phaseFabric, 24000)),
                                Seq.with(new SectorComplete(overgrowth),
                                        new SectorComplete(biomassFacility)), () -> {
                            node(createApLocation("AP-S-05-42", "Tetrative Reconstructor", 165L,
                                    LocationResearchCost.req6Item(Items.lead, 199000, Items.thorium,
                                            42000, Items.silicon, 144000, Items.plastanium, 24000,
                                            Items.phaseFabric, 24000, Items.surgeAlloy, 33000)),
                                    Seq.with(new SectorComplete(overgrowth),
                                            new SectorComplete(biomassFacility)),() -> {
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

    private long toBaseId(Long id) {
        return Shared.MINDUSTRY_BASE_ID + id;
    }

    /**
     * Create a location in the form of an UnlockableContent WITH NO research cost.
     * @param name Name of the node.
     * @param originalLocationName Name of the item originally contained in the node.
     * @param locationId Id of the location.
     * @return Return a location in the form a UnlockableContent.
     */
    private static ApLocation createApLocation(String name, String originalLocationName,
                                               Long locationId){
        return new ApLocation(name, originalLocationName, locationId);
    }

    /**
     * Create a location in the form of an UnlockableContent with its research cost.
     * @param name Name of the node.
     * @param originalLocationName Name of the item originally contained in the node.
     * @param locationId id of the location.
     * @param locationResearchCost List of Item required to check this location.
     * @return Return a location in the form a UnlockableContent.
     */
    private static ApLocation createApLocation(String name, String originalLocationName,
                                               Long locationId,
                                                      ItemStack[] locationResearchCost){
        ApLocation apContent = new ApLocation(name, originalLocationName, locationId);
        apContent.researchCost = locationResearchCost;
        return apContent;
    }

    /**
     * Create a location in the form of an UnlockableContent with its research cost.
     * @param name Name of the node.
     * @param originalLocationName Name of the item originally contained in the node.
     * @param locationId id of the location.
     * @param locationResearchCost List of Item required to check this location.
     * @param parent ApLocation parent to this one
     * @return Return a location in the form a UnlockableContent.
     */
    private static ApLocation createApLocation(String name, String originalLocationName,
                                               Long locationId,
                                                      ItemStack[] locationResearchCost,
                                                      ApLocation parent){
        ApLocation apContent = new ApLocation(name, originalLocationName, locationId);
        apContent.researchCost = locationResearchCost;
        return apContent;
    }

}
