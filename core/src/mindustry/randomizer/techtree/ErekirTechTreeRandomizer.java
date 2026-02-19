package mindustry.randomizer.techtree;

import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.SectorPresets;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Objectives;
import mindustry.randomizer.enums.ArchipelagoGoal;
import mindustry.type.unit.ErekirUnitType;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;

import static mindustry.Vars.content;
import static mindustry.content.Blocks.afflict;
import static mindustry.content.Blocks.armoredDuct;
import static mindustry.content.Blocks.atmosphericConcentrator;
import static mindustry.content.Blocks.basicAssemblerModule;
import static mindustry.content.Blocks.beamNode;
import static mindustry.content.Blocks.beamTower;
import static mindustry.content.Blocks.berylliumWall;
import static mindustry.content.Blocks.berylliumWallLarge;
import static mindustry.content.Blocks.blastDoor;
import static mindustry.content.Blocks.breach;
import static mindustry.content.Blocks.buildTower;
import static mindustry.content.Blocks.canvas;
import static mindustry.content.Blocks.carbideCrucible;
import static mindustry.content.Blocks.carbideWall;
import static mindustry.content.Blocks.carbideWallLarge;
import static mindustry.content.Blocks.chemicalCombustionChamber;
import static mindustry.content.Blocks.cliffCrusher;
import static mindustry.content.Blocks.constructor;
import static mindustry.content.Blocks.coreAcropolis;
import static mindustry.content.Blocks.coreBastion;
import static mindustry.content.Blocks.coreCitadel;
import static mindustry.content.Blocks.cyanogenSynthesizer;
import static mindustry.content.Blocks.deconstructor;
import static mindustry.content.Blocks.diffuse;
import static mindustry.content.Blocks.disperse;
import static mindustry.content.Blocks.duct;
import static mindustry.content.Blocks.ductBridge;
import static mindustry.content.Blocks.ductRouter;
import static mindustry.content.Blocks.ductUnloader;
import static mindustry.content.Blocks.electricHeater;
import static mindustry.content.Blocks.electrolyzer;
import static mindustry.content.Blocks.eruptionDrill;
import static mindustry.content.Blocks.fluxReactor;
import static mindustry.content.Blocks.heatRedirector;
import static mindustry.content.Blocks.heatRouter;
import static mindustry.content.Blocks.impactDrill;
import static mindustry.content.Blocks.largeConstructor;
import static mindustry.content.Blocks.largePayloadMassDriver;
import static mindustry.content.Blocks.largePlasmaBore;
import static mindustry.content.Blocks.lustre;
import static mindustry.content.Blocks.malign;
import static mindustry.content.Blocks.mechAssembler;
import static mindustry.content.Blocks.mechFabricator;
import static mindustry.content.Blocks.mechRefabricator;
import static mindustry.content.Blocks.neoplasiaReactor;
import static mindustry.content.Blocks.overflowDuct;
import static mindustry.content.Blocks.oxidationChamber;
import static mindustry.content.Blocks.payloadLoader;
import static mindustry.content.Blocks.payloadMassDriver;
import static mindustry.content.Blocks.payloadUnloader;
import static mindustry.content.Blocks.phaseHeater;
import static mindustry.content.Blocks.phaseSynthesizer;
import static mindustry.content.Blocks.plasmaBore;
import static mindustry.content.Blocks.primeRefabricator;
import static mindustry.content.Blocks.pyrolysisGenerator;
import static mindustry.content.Blocks.radar;
import static mindustry.content.Blocks.regenProjector;
import static mindustry.content.Blocks.reinforcedBridgeConduit;
import static mindustry.content.Blocks.reinforcedConduit;
import static mindustry.content.Blocks.reinforcedContainer;
import static mindustry.content.Blocks.reinforcedLiquidContainer;
import static mindustry.content.Blocks.reinforcedLiquidJunction;
import static mindustry.content.Blocks.reinforcedLiquidRouter;
import static mindustry.content.Blocks.reinforcedLiquidTank;
import static mindustry.content.Blocks.reinforcedMessage;
import static mindustry.content.Blocks.reinforcedPayloadConveyor;
import static mindustry.content.Blocks.reinforcedPayloadRouter;
import static mindustry.content.Blocks.reinforcedPump;
import static mindustry.content.Blocks.reinforcedSurgeWall;
import static mindustry.content.Blocks.reinforcedSurgeWallLarge;
import static mindustry.content.Blocks.reinforcedVault;
import static mindustry.content.Blocks.scathe;
import static mindustry.content.Blocks.shieldedWall;
import static mindustry.content.Blocks.shipAssembler;
import static mindustry.content.Blocks.shipFabricator;
import static mindustry.content.Blocks.shipRefabricator;
import static mindustry.content.Blocks.shockwaveTower;
import static mindustry.content.Blocks.siliconArcFurnace;
import static mindustry.content.Blocks.slagHeater;
import static mindustry.content.Blocks.slagIncinerator;
import static mindustry.content.Blocks.smallDeconstructor;
import static mindustry.content.Blocks.smite;
import static mindustry.content.Blocks.sublimate;
import static mindustry.content.Blocks.surgeConveyor;
import static mindustry.content.Blocks.surgeCrucible;
import static mindustry.content.Blocks.surgeRouter;
import static mindustry.content.Blocks.tankAssembler;
import static mindustry.content.Blocks.tankFabricator;
import static mindustry.content.Blocks.tankRefabricator;
import static mindustry.content.Blocks.titan;
import static mindustry.content.Blocks.tungstenWall;
import static mindustry.content.Blocks.tungstenWallLarge;
import static mindustry.content.Blocks.turbineCondenser;
import static mindustry.content.Blocks.underflowDuct;
import static mindustry.content.Blocks.unitCargoLoader;
import static mindustry.content.Blocks.unitCargoUnloadPoint;
import static mindustry.content.Blocks.unitRepairTower;
import static mindustry.content.Blocks.ventCondenser;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.apNode;
import static mindustry.content.TechTree.apNodeCapture;
import static mindustry.content.TechTree.apNodeProduce;
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.TechTree.nodeRoot;

/**
 * Class for the initialisation and randomization of Erekir' tech tree
 *
 * @author John Mahglass
 * @version 1.0.0 2024-04-30
 */
public abstract class ErekirTechTreeRandomizer extends TechTreeRandomizer {

    static IntSet balanced = new IntSet();

    static void rebalanceBullet(BulletType bullet){
        if(balanced.add(bullet.id)){
            bullet.damage *= 0.75f;
        }
    }

    //TODO remove this
    public static void rebalance(){
        for(var unit : content.units().select(u -> u instanceof ErekirUnitType)){
            for(var weapon : unit.weapons){
                rebalanceBullet(weapon.bullet);
            }
        }

        for(var block : content.blocks()){
            if(block instanceof Turret turret && Structs.contains(block.requirements, i -> !Items.serpuloItems.contains(i.item))){
                if(turret instanceof ItemTurret item){
                    for(var bullet : item.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousLiquidTurret cont){
                    for(var bullet : cont.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousTurret cont){
                    rebalanceBullet(cont.shootType);
                }
            }
        }
    }

    public static void load(){
        rebalance();

        //TODO might be unnecessary with no asteroids
        Seq<Objectives.Objective> erekirSector = Seq.with(new Objectives.OnPlanet(Planets.erekir));

        /*
        var costMultipliers = new ObjectFloatMap<Item>();
        for(var item : content.items()) costMultipliers.put(item, 0.9f);



        //these are hard to make
        costMultipliers.put(Items.oxide, 0.5f);
        costMultipliers.put(Items.surgeAlloy, 0.7f);
        costMultipliers.put(Items.carbide, 0.3f);
        costMultipliers.put(Items.phaseFabric, 0.2f);
         */

        Planets.erekir.techTree = nodeRoot("erekir", coreBastion, true, () -> {

            node(duct, erekirSector, () -> {
                apNode(createApLocation("AP-E-01-02", ductRouter, 301L), () -> {
                    apNode(createApLocation("AP-E-01-03", ductBridge, 302L), () -> {
                        apNode(createApLocation("AP-E-01-04", armoredDuct,303L), () -> {
                            apNode(createApLocation("AP-E-01-05", surgeConveyor, 304L), () -> {
                                apNode(createApLocation("AP-E-01-06", surgeRouter, 305L));
                            });
                        });

                        apNode(createApLocation("AP-E-01-07", unitCargoLoader, 306L), () -> {
                            apNode(createApLocation("AP-E-01-08", unitCargoUnloadPoint, 307L), () -> {
                            });
                        });
                    });

                    apNode(createApLocation("AP-E-01-09", overflowDuct, 308L),
                            Seq.with(new Objectives.OnSector(aegis)), () -> {
                                apNode(createApLocation("AP-E-01-10", underflowDuct, 309L));
                                apNode(createApLocation("AP-E-01-11", reinforcedContainer, 310L), () -> {
                                    apNode(createApLocation("AP-E-01-12", ductUnloader, 311L), () -> {
                                    });
                                    apNode(createApLocation("AP-E-01-13", reinforcedVault, 312L), () -> {
                                    });
                                });
                            });

                    apNode(createApLocation("AP-E-01-14", reinforcedMessage, 313L),
                            Seq.with(new Objectives.OnSector(aegis)), () -> {
                                apNode(createApLocation("AP-E-01-15", canvas, 314L));
                            });
                });

                apNode(createApLocation("AP-E-01-16", reinforcedPayloadConveyor, 315L),
                        Seq.with(new Objectives.OnSector(atlas)), () -> {
                            //TODO should only be unlocked in unit sector
                            apNode(createApLocation("AP-E-01-17", payloadMassDriver, 316L),
                            Seq.with(new Objectives.OnSector(split)), () -> {
                            //TODO further limitations
                            apNode(createApLocation("AP-E-01-18", payloadLoader, 317L), () -> {
                            apNode(createApLocation("AP-E-01-19", payloadUnloader, 318L), () -> {
                                    apNode(createApLocation("AP-E-01-20", largePayloadMassDriver, 319L), () -> {
                                    });
                            });
                            });
                            apNode(createApLocation("AP-E-01-21", constructor, 320L),
                                Seq.with(new Objectives.OnSector(split)), () -> {
                                apNode(createApLocation("AP-E-01-22", smallDeconstructor, 321L),
                                    Seq.with(new Objectives.OnSector(peaks)), () -> {
                                    apNode(createApLocation("AP-E-01-23", largeConstructor, 322L),
                                        Seq.with(new Objectives.OnSector(siege)), () -> {
                                        });
                                    apNode(createApLocation("AP-E-01-24", deconstructor, 323L),
                                        Seq.with(new Objectives.OnSector(siege)), () -> {
                                        });
                                    });
                                });
                    });
                        apNode(createApLocation("AP-E-01-25", reinforcedPayloadRouter, 324L), () -> {
                        });
                });
            });

            //TODO move into turbine condenser?
            node(plasmaBore, () -> {
                apNode(createApLocation("AP-E-02-02", impactDrill, 326L),
                    Seq.with(new Objectives.OnSector(aegis)), () -> {
                        apNode(createApLocation("AP-E-02-03", largePlasmaBore, 327L),
                            Seq.with(new Objectives.OnSector(caldera)), () -> {
                            apNode(createApLocation("AP-E-02-04", eruptionDrill, 328L),
                                Seq.with(new Objectives.OnSector(stronghold)), () -> {
                            });
                        });
                });
            });

            node(turbineCondenser, () -> {
                node(beamNode, () -> {
                    apNode(createApLocation("AP-E-03-03", ventCondenser, 331L),
                            Seq.with(new Objectives.OnSector(aegis)), () -> {
                            apNode(createApLocation("AP-E-03-04", chemicalCombustionChamber, 332L),
                                Seq.with(new Objectives.OnSector(basin)), () -> {
                                apNode(createApLocation("AP-E-03-05", pyrolysisGenerator, 333L),
                                    Seq.with(new Objectives.OnSector(crevice)), () -> {
                                    apNode(createApLocation("AP-E-03-06", fluxReactor, 334L),
                                        Seq.with(new Objectives.OnSector(crossroads)), () -> {
                                        apNode(createApLocation("AP-E-03-07", neoplasiaReactor, 335L),
                                            Seq.with(new Objectives.OnSector(karst)), () -> {
                                        });
                                    });
                                });
                            });
                    });

                    apNode(createApLocation("AP-E-03-08", beamTower, 336L),
                        Seq.with(new Objectives.OnSector(peaks)), () -> {
                    });


                    apNode(createApLocation("AP-E-03-09", regenProjector, 337L),
                            Seq.with(new Objectives.OnSector(peaks)), () -> {
                            //TODO more tiers of build tower or "support" structures like overdrive projectors
                            apNode(createApLocation("AP-E-03-10", buildTower, 338L),
                                Seq.with(new Objectives.OnSector(stronghold)), () -> {
                                apNode(createApLocation("AP-E-03-11", shockwaveTower, 339L),
                                    Seq.with(new Objectives.OnSector(siege)), () -> {
                                });
                            });
                    });
                });

                apNode(createApLocation("AP-E-03-12", reinforcedConduit, 340L),
                        Seq.with(new Objectives.OnSector(aegis)), () -> {
                            //TODO maybe should be even later
                            apNode(createApLocation("AP-E-03-13", reinforcedPump, 341L),
                                Seq.with(new Objectives.OnSector(basin)), () -> {
                                //TODO T2 pump, consume cyanogen or similar
                            });
                            apNode(createApLocation("AP-E-03-14", reinforcedLiquidJunction, 342L), () -> {
                                apNode(createApLocation("AP-E-03-15", reinforcedBridgeConduit, 343L), () -> {
                                });
                                apNode(createApLocation("AP-E-03-16", reinforcedLiquidRouter, 344L), () -> {
                                    apNode(createApLocation("AP-E-03-17", reinforcedLiquidContainer, 345L), () -> {
                                        apNode(createApLocation("AP-E-03-18", reinforcedLiquidTank, 346L),
                                            Seq.with(new Objectives.SectorComplete(intersect)), () -> {
                                        });
                                    });
                                });
                            });
                });

                node(cliffCrusher, () -> {
                    node(siliconArcFurnace, () -> {
                        apNode(createApLocation("AP-E-03-21", electrolyzer, 349L),
                            Seq.with(new Objectives.OnSector(atlas)), () -> {
                            apNode(createApLocation("AP-E-03-22", oxidationChamber, 350L),
                                Seq.with(new Objectives.OnSector(marsh)), () -> {
                                apNode(createApLocation("AP-E-03-23", surgeCrucible, 351L),
                                    Seq.with(new Objectives.OnSector(ravine)), () -> {
                                });
                                apNode(createApLocation("AP-E-03-24", heatRedirector, 352L),
                                    Seq.with(new Objectives.OnSector(ravine)), () -> {
                                    apNode(createApLocation("AP-E-03-25", electricHeater, 353L),
                                        Seq.with(new Objectives.OnSector(ravine)), () -> {
                                        apNode(createApLocation("AP-E-03-26", slagHeater, 354L),
                                            Seq.with(new Objectives.OnSector(caldera)), () -> {
                                        });

                                        apNode(createApLocation("AP-E-03-27", atmosphericConcentrator, 355L),
                                            Seq.with(new Objectives.OnSector(caldera)), () -> {
                                            apNode(createApLocation("AP-E-03-28", cyanogenSynthesizer, 356L),
                                                Seq.with(new Objectives.OnSector(siege)), () -> {
                                            });
                                        });

                                            apNode(createApLocation("AP-E-03-29", carbideCrucible, 357L),
                                                Seq.with(new Objectives.OnSector(crevice)), () -> {
                                                apNode(createApLocation("AP-E-03-30", phaseSynthesizer, 358L),
                                                        Seq.with(new Objectives.OnSector(karst)), () -> {
                                                        apNode(createApLocation("AP-E-03-31", phaseHeater, 359L), () -> {
                                                        });
                                                 });
                                            });

                                        apNode(createApLocation("AP-E-03-32", heatRouter, 360L),
                                                () -> {
                                        });
                                    });
                                });
                            });

                                    apNode(createApLocation("AP-E-03-33", slagIncinerator, 361L),
                                    Seq.with(new Objectives.OnSector(basin)), () -> {

                                //TODO these are unused.
                                //node(slagCentrifuge, () -> {});
                                //node(heatReactor, () -> {});
                            });
                        });
                    });
                });
            });


            node(breach, Seq.with(new Objectives.Research(siliconArcFurnace), new Objectives.Research(tankFabricator)), () -> {
                node(berylliumWall, () -> {
                    apNode(createApLocation("AP-E-04-03", berylliumWallLarge, 364L), () -> {
                    });

                    apNode(createApLocation("AP-E-04-04", tungstenWall, 365L), () -> {
                        apNode(createApLocation("AP-E-04-05", tungstenWallLarge, 366L), () -> {
                            apNode(createApLocation("AP-E-04-06", blastDoor, 367L), () -> {
                            });
                        });

                        apNode(createApLocation("AP-E-04-07", reinforcedSurgeWall, 368L), () -> {
                            apNode(createApLocation("AP-E-04-08", reinforcedSurgeWallLarge, 369L), () -> {
                                apNode(createApLocation("AP-E-04-09", shieldedWall, 370L), () -> {
                                });
                            });
                        });

                        apNode(createApLocation("AP-E-04-10", carbideWall, 371L), () -> {
                            apNode(createApLocation("AP-E-04-11", carbideWallLarge, 372L), () -> {
                            });
                        });
                    });
                });

                apNode(createApLocation("AP-E-04-12", diffuse, 373L),
                        Seq.with(new Objectives.OnSector(lake)), () -> {
                            apNode(createApLocation("AP-E-04-13", sublimate, 374L),
                                Seq.with(new Objectives.OnSector(marsh)), () -> {
                                    apNode(createApLocation("AP-E-04-14", afflict, 375L),
                                        Seq.with(new Objectives.OnSector(ravine)), () -> {
                                        apNode(createApLocation("AP-E-04-15", titan, 376L),
                                            Seq.with(new Objectives.OnSector(stronghold)), () -> {
                                            apNode(createApLocation("AP-E-04-16", lustre, 377L),
                                                Seq.with(new Objectives.OnSector(crevice)), () -> {
                                                apNode(createApLocation("AP-E-04-17", smite, 378L),
                                                    Seq.with(new Objectives.OnSector(karst)), () -> {
                                                });
                                            });
                                        });
                                    });
                                });

                            apNode(createApLocation("AP-E-04-18", disperse, 379L),
                                Seq.with(new Objectives.OnSector(stronghold)), () -> {
                                apNode(createApLocation("AP-E-04-19", scathe, 380L),
                                    Seq.with(new Objectives.OnSector(siege)), () -> {
                                    apNode(createApLocation("AP-E-04-20", malign, 381L),
                                        Seq.with(new Objectives.SectorComplete(karst)), () -> {
                                    });
                                });
                            });
                });


                apNode(createApLocation("AP-E-04-21", radar, 382L),
                    Seq.with(new Objectives.OnSector(SectorPresets.aegis)), () -> {
                });
            });

            apNode(createApLocation("AP-E-05-01", coreCitadel, 383L),
                Seq.with(new Objectives.SectorComplete(peaks)), () -> {
                apNode(createApLocation("AP-E-05-02", coreAcropolis, 384L),
                    Seq.with(new Objectives.SectorComplete(siege)), () -> {
                });
            });

            if (Vars.randomizer.worldState.options.getGoal() == ArchipelagoGoal.RESOURCES) {
                apNode(createApLocation("Victory Erekir", null, 999L,
                        LocationResearchCost.reqResourcesVictoryErekir()));
            } else { //Conquest
                apNode(createApLocation("Victory Erekir", null, 999L),
                        Seq.with(new Objectives.SectorComplete(onset),
                                new Objectives.SectorComplete(aegis),
                                new Objectives.SectorComplete(lake),
                                new Objectives.SectorComplete(intersect),
                                new Objectives.SectorComplete(atlas),
                                new Objectives.SectorComplete(split),
                                new Objectives.SectorComplete(basin),
                                new Objectives.SectorComplete(marsh),
                                new Objectives.SectorComplete(peaks),
                                new Objectives.SectorComplete(ravine),
                                new Objectives.SectorComplete(caldera),
                                new Objectives.SectorComplete(stronghold),
                                new Objectives.SectorComplete(crevice),
                                new Objectives.SectorComplete(siege),
                                new Objectives.SectorComplete(crossroads),
                                new Objectives.SectorComplete(karst),
                                new Objectives.SectorComplete(origin)), () -> {
                        });
            }




                node(tankFabricator, Seq.with(new Objectives.Research(siliconArcFurnace), new Objectives.Research(plasmaBore), new Objectives.Research(turbineCondenser)), () -> {
                    node(UnitTypes.stell);
                apNode(createApLocation("AP-E-06-03", unitRepairTower, 387L),
                    Seq.with(new Objectives.OnSector(ravine)), () -> {
                });

                apNode(createApLocation("AP-E-06-04", shipFabricator, 388L),
                    Seq.with(new Objectives.OnSector(lake)), () -> {
                    apNode(createApLocation("AP-E-06-05", UnitTypes.elude, 389L));
                        apNode(createApLocation("AP-E-06-06", mechFabricator, 390L),
                            Seq.with(new Objectives.OnSector(intersect)), () -> {
                            apNode(createApLocation("AP-E-06-07", UnitTypes.merui, 391L));
                                apNode(createApLocation("AP-E-06-08", tankRefabricator, 392L),
                                    Seq.with(new Objectives.OnSector(atlas)), () -> {
                                    apNode(createApLocation("AP-E-06-09", UnitTypes.locus, 393L));
                                    apNode(createApLocation("AP-E-06-10", mechRefabricator, 394L),
                                        Seq.with(new Objectives.OnSector(basin)), () -> {
                                        apNode(createApLocation("AP-E-06-11", UnitTypes.cleroi, 395L));
                                        apNode(createApLocation("AP-E-06-12", shipRefabricator, 396L),
                                            Seq.with(new Objectives.OnSector(peaks)), () -> {
                                            apNode(createApLocation("AP-E-06-13", UnitTypes.avert, 397L));
                                    //TODO
                                    apNode(createApLocation("AP-E-06-14", primeRefabricator, 398L),
                                        Seq.with(new Objectives.OnSector(stronghold)), () -> {
                                        apNode(createApLocation("AP-E-06-15", UnitTypes.precept,
                                    399L));
                                        apNode(createApLocation("AP-E-06-16", UnitTypes.anthicus,
                                    400L));
                                        apNode(createApLocation("AP-E-06-17", UnitTypes.obviate,
                                    401L));
                                    });

                                        apNode(createApLocation("AP-E-06-18", tankAssembler, 402L),
                                            Seq.with(new Objectives.OnSector(siege)), () -> {
                                            apNode(createApLocation("AP-E-06-19", UnitTypes.vanquish,
                                            403L), () -> {
                                            apNode(createApLocation("AP-E-06-20", UnitTypes.conquer,
                                            404L),
                                                Seq.with(new Objectives.OnSector(karst)), () -> {
                                            });
                                        });

                                            apNode(createApLocation("AP-E-06-21", shipAssembler, 405L),
                                                Seq.with(new Objectives.OnSector(crossroads)), () -> {
                                                apNode(createApLocation("AP-E-06-22", UnitTypes.quell,
                                            406L), () -> {
                                                    apNode(createApLocation("AP-E-06-23", UnitTypes.disrupt, 407L),
                                                        Seq.with(new Objectives.OnSector(karst)), () -> {
                                                    });
                                                });
                                            });
                                            apNode(createApLocation("AP-E-06-24", mechAssembler, 408L),
                                                Seq.with(new Objectives.OnSector(crossroads)), () -> {
                                                apNode(createApLocation("AP-E-06-25", UnitTypes.tecta, 409L), () -> {
                                                    apNode(createApLocation("AP-E-06-26", UnitTypes.collaris, 410L),
                                                        Seq.with(new Objectives.OnSector(karst)), () -> {
                                                    });
                                                });
                                            });
                                            apNode(createApLocation("AP-E-06-27", basicAssemblerModule, 411L),
                                                Seq.with(new Objectives.SectorComplete(karst)), () -> {
                                            });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            if (Vars.randomizer.worldState.options.getSectorsAsLocations()) {
                node(onset, () -> {
                    apNodeCapture(Vars.randomizer.worldState.complexLocations.erekirAegis, Seq.with(new Objectives.SectorComplete(onset)), () -> {
                        apNodeCapture(createApLocation("Lake", lake, 452L), Seq.with(new Objectives.SectorComplete(aegis)), () -> {

                        });

                        apNodeCapture(createApLocation("Intersect", intersect, 453L), Seq.with(new Objectives.SectorComplete(aegis), new Objectives.SectorComplete(lake)), () -> {
                            apNodeCapture(createApLocation("Atlas", atlas, 454L), Seq.with(new Objectives.SectorComplete(intersect)), () -> {
                                apNodeCapture(createApLocation("Split", split, 455L), Seq.with(new Objectives.SectorComplete(atlas)), () -> {

                                });

                                apNodeCapture(createApLocation("Basin", basin, 456L), Seq.with(new Objectives.SectorComplete(atlas)), () -> {
                                    apNodeCapture(createApLocation("Marsh", marsh, 457L), Seq.with(new Objectives.SectorComplete(basin)), () -> {
                                        apNodeCapture(createApLocation("Ravine", ravine, 458L), Seq.with(new Objectives.SectorComplete(marsh)), () -> {
                                            apNodeCapture(createApLocation("Caldera", caldera, 459L), Seq.with(new Objectives.SectorComplete(peaks)), () -> {
                                                apNodeCapture(createApLocation("Stronghold", stronghold, 460L), Seq.with(new Objectives.SectorComplete(caldera)), () -> {
                                                    apNodeCapture(createApLocation("Crevice", crevice, 461L), Seq.with(new Objectives.SectorComplete(stronghold)), () -> {
                                                        apNodeCapture(createApLocation("Siege", siege, 462L), Seq.with(new Objectives.SectorComplete(crevice)), () -> {
                                                            apNodeCapture(createApLocation("Crossroads", crossroads, 463L), Seq.with(new Objectives.SectorComplete(siege)), () -> {
                                                                apNodeCapture(createApLocation("Karst", karst, 464L), Seq.with(new Objectives.SectorComplete(crossroads)), () -> {
                                                                    apNodeCapture(createApLocation("Origin", origin, 465L), Seq.with(new Objectives.SectorComplete(karst)), () -> {

                                                                    });
                                                                });
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });

                                        apNodeCapture(createApLocation("Peaks", peaks, 466L), Seq.with(new Objectives.SectorComplete(marsh), new Objectives.SectorComplete(split)), () -> {
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            } else {
                node(onset, () -> {
                    node(aegis, Seq.with(new Objectives.SectorComplete(onset)), () -> {
                        node(lake, Seq.with(new Objectives.SectorComplete(aegis)), () -> {

                        });

                        node(intersect, Seq.with(new Objectives.SectorComplete(aegis), new Objectives.SectorComplete(lake)), () -> {
                            node(atlas, Seq.with(new Objectives.SectorComplete(intersect)), () -> {
                                node(split, Seq.with(new Objectives.SectorComplete(atlas)), () -> {

                                });

                                node(basin, Seq.with(new Objectives.SectorComplete(atlas)), () -> {
                                    node(marsh, Seq.with(new Objectives.SectorComplete(basin)), () -> {
                                        node(ravine, Seq.with(new Objectives.SectorComplete(marsh)), () -> {
                                            node(caldera, Seq.with(new Objectives.SectorComplete(peaks)), () -> {
                                                node(stronghold, Seq.with(new Objectives.SectorComplete(caldera)), () -> {
                                                    node(crevice, Seq.with(new Objectives.SectorComplete(stronghold)), () -> {
                                                        node(siege, Seq.with(new Objectives.SectorComplete(crevice)), () -> {
                                                            node(crossroads, Seq.with(new Objectives.SectorComplete(siege)), () -> {
                                                                node(karst, Seq.with(new Objectives.SectorComplete(crossroads)), () -> {
                                                                    node(origin, Seq.with(new Objectives.SectorComplete(karst)), () -> {

                                                                    });
                                                                });
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });

                                        node(peaks, Seq.with(new Objectives.SectorComplete(marsh), new Objectives.SectorComplete(split)), () -> {

                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            }

            if (Vars.randomizer.worldState.options.getResourcesAsLocations()) {
                apNodeProduce(createApLocation("Beryllium Erekir", Items.beryllium, 500L), Items.beryllium, () -> {
                    apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirSand, Items.sand, () -> {
                        apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirSilicon, Items.silicon, () -> {
                            apNodeProduce(createApLocation("Oxide Erekir", Items.oxide, 503L), Items.oxide, () -> {
                            });
                        });
                    });

                    apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirWater, Liquids.water, () -> {
                        apNodeProduce(createApLocation("Ozone Erekir", Liquids.ozone, 505L), Liquids.ozone, () -> {
                            apNodeProduce(createApLocation("Hydrogen Erekir", Liquids.hydrogen, 506L), Liquids.hydrogen, () -> {
                                apNodeProduce(createApLocation("Nitrogen Erekir", Liquids.nitrogen, 507L), Liquids.nitrogen, () -> {
                                });

                                apNodeProduce(createApLocation("Cyanogen Erekir", Liquids.cyanogen, 508L), Liquids.cyanogen, () -> {
                                    apNodeProduce(createApLocation("Neoplasm Erekir", Liquids.neoplasm, 509L), Liquids.neoplasm, () -> {
                                    });
                                });
                            });
                        });
                    });

                    apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirGraphite, Items.graphite, () -> {
                        apNodeProduce(createApLocation("Tungsten Erekir", Items.tungsten, 511L), Items.tungsten, () -> {
                            apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirSlag, Liquids.slag, () -> {
                            });

                            apNodeProduce(createApLocation("Arkycite Erekir", Liquids.arkycite, 513L), Liquids.arkycite, () -> {
                            });

                            apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirThorium, Items.thorium, () -> {
                                apNodeProduce(createApLocation("Carbide Erekir", Items.carbide, 515L), Items.carbide, () -> {
                                    //nodeProduce(Liquids.gallium, () -> {});
                                });
                            });

                            apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirSurgeAlloy, Items.surgeAlloy, () -> {
                                apNodeProduce(Vars.randomizer.worldState.complexLocations.erekirPhaseFabric, Items.phaseFabric, () -> {
                                });
                            });
                        });
                    });
                });
            } else {
                nodeProduce(Items.beryllium, () -> {
                    nodeProduce(Items.sand, () -> {
                        nodeProduce(Items.silicon, () -> {
                            nodeProduce(Items.oxide, () -> {
                                //nodeProduce(Items.fissileMatter, () -> {});
                            });
                        });
                    });

                    nodeProduce(Liquids.water, () -> {
                        nodeProduce(Liquids.ozone, () -> {
                            nodeProduce(Liquids.hydrogen, () -> {
                                nodeProduce(Liquids.nitrogen, () -> {

                                });

                                nodeProduce(Liquids.cyanogen, () -> {
                                    nodeProduce(Liquids.neoplasm, () -> {

                                    });
                                });
                            });
                        });
                    });

                    nodeProduce(Items.graphite, () -> {
                        nodeProduce(Items.tungsten, () -> {
                            nodeProduce(Liquids.slag, () -> {

                            });

                            nodeProduce(Liquids.arkycite, () -> {

                            });

                            nodeProduce(Items.thorium, () -> {
                                nodeProduce(Items.carbide, () -> {

                                    //nodeProduce(Liquids.gallium, () -> {});
                                });

                                nodeProduce(Items.surgeAlloy, () -> {
                                    nodeProduce(Items.phaseFabric, () -> {

                                    });
                                });
                            });
                        });
                    });
                });
            }
        });
    }
}
