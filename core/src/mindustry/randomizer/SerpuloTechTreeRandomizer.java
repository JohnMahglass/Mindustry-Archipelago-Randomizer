package mindustry.randomizer;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.UnitTypes;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Category;
import mindustry.type.ItemStack;

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
import static mindustry.type.ItemStack.with;

/**
 * Class for the initialisation and randomization of Serpulo's tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public abstract class SerpuloTechTreeRandomizer implements TechTreeRandomizer {


    public static void load() {

        /*
        Vars.randomizer.unlock(Items.sporePod);
        Vars.randomizer.unlock(Items.pyratite);
        Vars.randomizer.unlock(Items.graphite);
        Vars.randomizer.unlock(Items.plastanium);
        Vars.randomizer.unlock(Items.silicon);
        Vars.randomizer.unlock(Items.blastCompound);
        Vars.randomizer.unlock(Items.phaseFabric);
        Vars.randomizer.unlock(Items.surgeAlloy);
        Vars.randomizer.unlock(Items.thorium);
        Vars.randomizer.unlock(Items.scrap);
        Vars.randomizer.unlock(Items.metaglass);
        Vars.randomizer.unlock(Items.titanium);
        Vars.randomizer.unlock(Items.coal);

         */

        Planets.serpulo.techTree = nodeRoot("serpulo", coreShard, () -> {

            node(createApLocation("AP-W-00-01", "Victory", -1,
                    LocationResearchCost.reqCopper(9999)));

            node(createApLocation("AP-S-01-01", "Conveyor", 0,
                            LocationResearchCost.req1Item(Items.copper, 5)),
                    () -> {

                node(createApLocation("AP-S-01-02", "Junction", 1,
                        LocationResearchCost.req1Item(Items.copper, 100)), () -> {
                    node(createApLocation("AP-S-01-03", "Router", 2,
                            LocationResearchCost.req1Item(Items.copper, 100)), () -> {
                        node(createApLocation("AP-S-01-04", "Launch Pad", null,
                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(extractionOutpost)), () -> {
                            //no longer necessary to beat the campaign
                            //node(interplanetaryAccelerator, Seq.with(new SectorComplete(planetaryTerminal)), () -> {

                            //});
                        });

                        node(createApLocation("AP-S-01-05", "Distributor", 1,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                        node(createApLocation("AP-S-01-06", "Sorter", 1,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)), () -> {
                            node(createApLocation("AP-S-01-07", "Inverted Sorter", 1,
                                    LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                            node(createApLocation("AP-S-01-08", "Overflow Gate", 1,
                                    LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)), () -> {
                                node(createApLocation("AP-S-01-09", "Underflow Gate", 1,
                                        LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)));
                            });
                        });
                        node(createApLocation("AP-S-01-10", "Container", null,
                                LocationResearchCost.req2Item(Items.copper, 100, Items.lead, 100)), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
                            node(createApLocation("AP-S-01-11", "Unloader", 1,
                                    LocationResearchCost.req2Item(Items.titanium, 700,
                                            Items.silicon, 900)));
                            node(createApLocation("AP-S-01-12", "Vault", null,
                                    LocationResearchCost.reqCopper(1)),
                                    Seq.with(new Objectives.SectorComplete(stainedMountains)), () -> {

                            });
                        });

                        node(createApLocation("AP-S-01-13", "Bridge Conveyor", 1,
                                LocationResearchCost.req2Item(Items.copper, 200, Items.lead, 200)),
                                () -> {
                            node(createApLocation("AP-S-01-14", "Titanium Conveyor", null,
                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                                node(createApLocation("AP-S-01-15", "Phase Conveyor", 1,
                                        LocationResearchCost.req4Item(Items.lead, 300, Items.graphite, 300, Items.silicon, 200, Items.phaseFabric, 100)), () -> {
                                    node(createApLocation("AP-S-01-16", "Mass Driver", 1,
                                            LocationResearchCost.req4Item(Items.lead, 4300,
                                                    Items.titanium, 4300, Items.thorium, 1500, Items.silicon, 2400)), () -> {

                                    });
                                });

                                node(createApLocation("AP-S-01-17", "Payload Conveyor", 1,
                                        LocationResearchCost.req2Item(Items.copper, 300,
                                                Items.graphite, 300)),
                                        () -> {
                                    node(createApLocation("AP-S-01-18", "Payload Router", 1,
                                            LocationResearchCost.req2Item(Items.copper, 300,
                                                    Items.graphite, 400)),
                                            () -> {

                                    });
                                });

                                node(createApLocation("AP-S-01-19", "Armored Conveyor", 1,
                                        LocationResearchCost.req3Item(Items.metaglass, 80,
                                                Items.thorium, 80, Items.plastanium, 80)),
                                        () -> {
                                    node(createApLocation("AP-S-01-20", "Plastanium Conveyor", 1,
                                            LocationResearchCost.req3Item(Items.graphite, 80,
                                                    Items.silicon, 80, Items.plastanium, 80)), () -> {

                                    });
                                });
                            });
                        });
                    });
                });
            });

            node(createApLocation("AP-S-02-01", "Core: Foundation", 1,
                    LocationResearchCost.req3Item(Items.copper, 10000,
                            Items.lead, 10000, Items.silicon, 6400)), () -> {
                node(createApLocation("AP-S-02-02", "Core: Nucleus", 1,
                        LocationResearchCost.req4Item(Items.copper, 47000,
                                Items.lead, 47000, Items.thorium, 21000, Items.silicon, 28000)),
                        () -> {

                });
            });

            node(createApLocation("AP-S-03-01", "Mechanical Drill", 1,
                    LocationResearchCost.req1Item(Items.copper, 10)), () -> {

                node(createApLocation("AP-S-03-02", "Mechanical Pump", 1,
                        LocationResearchCost.req2Item(Items.copper, 400, Items.metaglass, 300)),
                        () -> {
                    node(createApLocation("AP-S-03-03", "Conduit", 1,
                            LocationResearchCost.req1Item(Items.metaglass, 80)), () -> {
                        node(createApLocation("AP-S-03-04", "Liquid Junction", 1,
                                LocationResearchCost.req2Item(Items.metaglass, 200,
                                        Items.graphite, 100)),
                                () -> {
                            node(createApLocation("AP-S-03-05", "Liquid Router", 1,
                                    LocationResearchCost.req2Item(Items.metaglass, 100,
                                            Items.graphite, 100)), () -> {
                                node(createApLocation("AP-S-03-06", "Liquid Container", 1,
                                        LocationResearchCost.req2Item(Items.metaglass, 400,
                                                Items.titanium, 300)), () -> {
                                    node(createApLocation("AP-S-03-07", "Liquid Tank", 1,
                                            LocationResearchCost.req2Item(Items.metaglass, 1200,
                                                    Items.titanium, 900)));
                                });

                                node(createApLocation("AP-S-03-08", "Bridge Conduit", 1,
                                        LocationResearchCost.req2Item(Items.metaglass, 200,
                                                Items.graphite, 100)));

                                node(createApLocation("AP-S-03-09", "Pulse Conduit", null,
                                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                    node(createApLocation("AP-S-03-10", "Phase Conduit", 1,
                                            LocationResearchCost.req4Item(Items.metaglass, 600,
                                                    Items.titanium, 300, Items.silicon, 200,
                                                    Items.phaseFabric, 100)), () -> {

                                    });

                                    node(createApLocation("AP-S-03-11", "Plated Conduit", 1,
                                            LocationResearchCost.req3Item(Items.metaglass, 80,
                                                    Items.thorium, 100, Items.plastanium, 80)), () -> {

                                    });

                                    node(createApLocation("AP-S-03-12", "Rotary Pump", 1,
                                            LocationResearchCost.req4Item(Items.copper, 2200,
                                                    Items.metaglass, 1500, Items.titanium, 1000,
                                                    Items.silicon, 600)),
                                            () -> {
                                        node(createApLocation("AP-S-03-13", "Impulse Pump", 1,
                                                LocationResearchCost.req5Item(Items.copper, 2600,
                                                        Items.metaglass, 3000, Items.titanium,
                                                        1200, Items.thorium, 1000,
                                                        Items.silicon, 900)), () -> {

                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-03-14", "Graphite Press", 1,
                        LocationResearchCost.req2Item(Items.copper, 2400, Items.lead, 900)), () -> {
                    node(createApLocation("AP-S-03-15", "Pneumatic Drill", null,
                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(frozenForest)), () -> {
                        node(createApLocation("AP-S-03-16", "Cultivator", null,
                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {

                        });

                        node(createApLocation("AP-S-03-17", "Lazer Drill", 1,
                                LocationResearchCost.req4Item(Items.copper, 1000, Items.graphite,
                                        900, Items.titanium, 600, Items.silicon,
                                        900)), () -> {
                            node(createApLocation("AP-S-03-18", "Blast Drill", null,
                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {

                            });

                            node(createApLocation("AP-S-03-19", "Water Extractor", null,
                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(saltFlats)), () -> {
                                node(createApLocation("AP-S-03-20", "Oil Extractor", 1,
                                        LocationResearchCost.req5Item(Items.copper, 5200,
                                                Items.lead, 3900, Items.graphite, 6200,
                                                Items.thorium, 3900, Items.silicon, 2400)),
                                        () -> {

                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-03-21", "Pyratite Mixer", 1,
                            LocationResearchCost.req2Item(Items.copper, 1500, Items.lead, 700)),
                            () -> {
                        node(createApLocation("AP-S-03-22", "Blast Mixer", 1,
                                LocationResearchCost.req2Item(Items.lead, 900, Items.titanium,
                                        600)),
                                () -> {

                        });
                    });

                    node(createApLocation("AP-S-03-23", "Silicon Smelter", 1,
                            LocationResearchCost.req2Item(Items.copper, 900, Items.lead, 700)),
                            () -> {

                        node(createApLocation("AP-S-03-24", "Spore Press", 1,
                                LocationResearchCost.req2Item(Items.lead, 1000, Items.silicon,
                                        900)),
                                () -> {
                            node(createApLocation("AP-S-03-25", "Coal Centrifuge", 1,
                                    LocationResearchCost.req3Item(Items.lead, 900, Items.graphite
                                            , 1200, Items.titanium, 600)),
                                    () -> {
                                node(createApLocation("AP-S-03-26", "Multi-Press", 1,
                                        LocationResearchCost.req4Item(Items.lead, 3300,
                                                Items.graphite, 1500, Items.titanium, 3300,
                                                Items.silicon, 700)),
                                        () -> {
                                    node(createApLocation("AP-S-03-27", "Silicon Crucible", 1,
                                            LocationResearchCost.req4Item(Items.metaglass, 2600,
                                                    Items.titanium, 4100, Items.silicon, 1900,
                                                    Items.plastanium, 1000)), () -> {

                                    });
                                });
                            });

                            node(createApLocation("AP-S-03-28", "Plastanium Compressor", 1,
                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                node(createApLocation("AP-S-03-29", "Phase Weaver", 1,
                                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(tarFields)), () -> {

                                });
                            });
                        });

                        node(createApLocation("AP-S-03-30", "Kiln", 1,
                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                            node(createApLocation("AP-S-03-31", "Pulverizer", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-03-32", "Incinerator", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-03-33", "Melter", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-03-34", "Surge Smelter", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });

                                        node(createApLocation("AP-S-03-35", "Separator", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {
                                            node(createApLocation("AP-S-03-36", "Disassembler",
                                                    1,
                                                    LocationResearchCost.reqCopper(1)), () -> {

                                            });
                                        });

                                        node(createApLocation("AP-S-03-37", "Cryofluid Mixer", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        //logic disabled until further notice
                        node(createApLocation("AP-S-03-38", "Micro Processor", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-03-39", "Switch Block", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-03-40", "Message", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-03-41", "Logic Display", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-03-42", "Large Logic Display",
                                                1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });

                                    node(createApLocation("AP-S-03-43", "Memory Cell", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-03-44", "Memory Bank", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });
                                });

                                node(createApLocation("AP-S-03-45", "Logic Processor", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-03-46", "Hyper Processor", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {

                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-47", "Illuminator", 1,
                                LocationResearchCost.reqCopper(1)), () -> {

                        });
                    });
                });


                node(createApLocation("AP-S-03-48", "Combustion Generator", 1,
                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.Research(Items.coal)), () -> {
                    node(createApLocation("AP-S-03-49", "Power Node", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-03-50", "Large Power Node", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-03-51", "Battery Diode", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-03-52", "Surge Tower", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });
                        });

                        node(createApLocation("AP-S-03-53", "Battery", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-03-54", "Large Battery", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {

                            });
                        });

                        node(createApLocation("AP-S-03-55", "Mender", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-03-56", "Mend Projector", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-03-57", "Force Projector", 1,
                                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
                                    node(createApLocation("AP-S-03-58", "Overdrive Projector", 1,
                                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
                                        node(createApLocation("AP-S-03-59", "Overdrive Dome", 1,
                                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(impact0078)), () -> {

                                        });
                                    });
                                });

                                node(createApLocation("AP-S-03-60", "Repair Point", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-03-61", "Repair Turret", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {

                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-62", "Steam Generator", 1,
                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                            node(createApLocation("AP-S-03-63", "Thermal Generator", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-03-64", "Differential Generator", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-03-65", "Thorium Reactor", 1,
                                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.Research(Liquids.cryofluid)), () -> {
                                        node(createApLocation("AP-S-03-66", "Impact Reactor", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });

                                        node(createApLocation("AP-S-03-67", "Rtg Generator", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-03-68", "Solar Panel", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-03-69", "Large Solar Panel", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {

                            });
                        });
                    });
                });
            });

            node(createApLocation("AP-S-04-01", "Duo", 1,
                    LocationResearchCost.reqCopper(1)), () -> {
                node(createApLocation("AP-S-04-02", "Copper Wall", 1,
                        LocationResearchCost.reqCopper(1)), () -> {
                    node(createApLocation("AP-S-04-03", "Large Copper Wall", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-04-04", "Titanium Wall", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-04-05", "Large Titanium Wall", 1,
                                    LocationResearchCost.reqCopper(1)));

                            node(createApLocation("AP-S-04-06", "Door", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-07", "Large Door", 1,
                                        LocationResearchCost.reqCopper(1)));
                            });
                            node(createApLocation("AP-S-04-08", "Plastanium Wall", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-09", "Large Plastanium Wall", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });
                            node(createApLocation("AP-S-04-10", "Thorium Wall", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-11", "Large Thorium Wall", 1,
                                        LocationResearchCost.reqCopper(1)));
                                node(createApLocation("AP-S-04-12", "Surge Wall", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-04-13", "Large Surge Wall", 1,
                                            LocationResearchCost.reqCopper(1)));
                                    node(createApLocation("AP-S-04-14", "Phase Wall", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-04-15", "Large Phase Wall",
                                                1,
                                                LocationResearchCost.reqCopper(1)));
                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-04-16", "Scatter", 1,
                        LocationResearchCost.reqCopper(1)), () -> {
                    node(createApLocation("AP-S-04-17", "Hail", 1,
                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(craters)), () -> {
                        node(createApLocation("AP-S-04-18", "Salvo", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-04-19", "Swarmer", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-20", "Cyclone", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-04-21", "Spectre", 1,
                                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {

                                    });
                                });
                            });

                            node(createApLocation("AP-S-04-22", "Ripple", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-23", "Fuse", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-04-24", "Scorch", 1,
                        LocationResearchCost.reqCopper(1)), () -> {
                    node(createApLocation("AP-S-04-25", "Arc", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-04-26", "Wave", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-04-27", "Parallax", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-28", "Segment", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });

                            node(createApLocation("AP-S-04-29", "Tsunami", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {

                            });
                        });

                        node(createApLocation("AP-S-04-30", "Lancer", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-04-31", "Meltdown", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-04-32", "Foreshadow", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });

                            node(createApLocation("AP-S-04-33", "Shock Mine", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {

                            });
                        });
                    });
                });
            });

            node(createApLocation("AP-S-05-01", "Ground Factory", 1,
                    LocationResearchCost.reqCopper(1)), () -> {

                node(createApLocation("AP-S-05-02", "Dagger", 1,
                        LocationResearchCost.reqCopper(1)), () -> {
                    node(createApLocation("AP-S-05-03", "Mace", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-05-04", "Fortress", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-05", "Scepter", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-06", "Reign", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {

                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-07", "Nova", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-05-08", "Pulsar", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-09", "Quasar", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-10", "Vela", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-05-11", "Corvus", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {

                                    });
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-12", "Crawler", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-05-13", "Atrax", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-14", "Spiroct", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-15", "Arkyid", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-05-16", "Toxopid", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {

                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-05-17", "Air Factory", 1,
                        LocationResearchCost.reqCopper(1)), () -> {
                    node(createApLocation("AP-S-05-18", "Flare", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-05-19", "Horizon", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-20", "Zenith", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-21", "Antumbra", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-05-22", "Eclipse", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {

                                    });
                                });
                            });
                        });

                        node(createApLocation("AP-S-05-23", "Mono", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-24", "Poly", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-25", "Mega", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-05-26", "Quad", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-05-27", "Oct", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });
                                });
                            });
                        });
                    });

                    node(createApLocation("AP-S-05-28", "Naval Factory", 1,
                            LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(ruinousShores)), () -> {
                        node(createApLocation("AP-S-05-29", "Risso", 1,
                                LocationResearchCost.reqCopper(1)), () -> {
                            node(createApLocation("AP-S-05-30", "Minke", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {
                                node(createApLocation("AP-S-05-31", "Bryde", 1,
                                        LocationResearchCost.reqCopper(1)), () -> {
                                    node(createApLocation("AP-S-05-32", "Sei", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-05-33", "Omura", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {

                                        });
                                    });
                                });
                            });

                            node(createApLocation("AP-S-05-34", "Retusa", 1,
                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
                                node(createApLocation("AP-S-05-35", "Oxynoe", 1,
                                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(coastline)), () -> {
                                    node(createApLocation("AP-S-05-36", "Cyerce", 1,
                                            LocationResearchCost.reqCopper(1)), () -> {
                                        node(createApLocation("AP-S-05-37", "Aegires", 1,
                                                LocationResearchCost.reqCopper(1)), () -> {
                                            node(createApLocation("AP-S-05-38", "Navanax", 1,
                                                    LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(navalFortress)), () -> {

                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                node(createApLocation("AP-S-05-39", "Additive Reconstructor", 1,
                        LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
                    node(createApLocation("AP-S-05-40", "Multiplicative Reconstructor", 1,
                            LocationResearchCost.reqCopper(1)), () -> {
                        node(createApLocation("AP-S-05-41", "Exponential Reconstructor", 1,
                                LocationResearchCost.reqCopper(1)), Seq.with(new Objectives.SectorComplete(overgrowth)), () -> {
                            node(createApLocation("AP-S-05-42", "Tetrative Reconstructor", 1,
                                    LocationResearchCost.reqCopper(1)), () -> {

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

    /**
     * Create a location in the form of an UnlockableContent WITH NO research cost.
     * @param name Name of the node.
     * @param originalLocationName Name of the item originally contained in the node.
     * @param locationId Id of the location.
     * @return Return a location in the form a UnlockableContent.
     */
    private static UnlockableContent createApLocation(String name, String originalLocationName,
                                               int locationId){
        return new ApLocation(name, originalLocationName, locationId);
    }

    /**
     * Create a location in the form of an UnlockableContent with its research cost.
     * @param name Name of the node.
     * @param originalLocationName Name of the item originally contained in the node.
     * @param locationId Id of the location.
     * @param locationResearchCost List of Item required to check this location.
     * @return Return a location in the form a UnlockableContent.
     */
    private static UnlockableContent createApLocation(String name, String originalLocationName,
                                                      int locationId,
                                                      ItemStack[] locationResearchCost){
        ApLocation apContent = new ApLocation(name, originalLocationName, locationId);
        apContent.researchCost = locationResearchCost;
        return apContent;
    }

}
