package mindustry.randomizer.utils;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.abilities.MoveLightningAbility;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.ShieldArcAbility;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.entities.abilities.SuppressionFieldAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.EmpBulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.bullet.RailBulletType;
import mindustry.entities.bullet.SapBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.type.weapons.RepairBeamWeapon;

import java.util.ArrayList;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.content.UnitTypes.anthicus;
import static mindustry.content.UnitTypes.elude;
import static mindustry.content.UnitTypes.locus;


/**
 * Contains useful static list that can be used for randomization.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-30
 */
public abstract class RandomizableCoreUnits {

    /**
     * List of possible ability for a core unit.
     * @return Return a list of abilities.
     */
    public static ArrayList<Ability[]> getPossibleCoreUnitsAbility(){
        ArrayList<Ability[]> coreUnitAbility = new ArrayList<>();


        Ability[] scepterAbilities = new Ability[]{ //0
                new ShieldRegenFieldAbility(25f, 250f, 60f * 1, 60f)
        };
        coreUnitAbility.add(scepterAbilities);


        Ability[] quasarAbilities = new Ability[]{ //1 ,shield health upgraded
                new ForceFieldAbility(60f, 0.5f, 1000f, 60f * 6)
        };
        coreUnitAbility.add(quasarAbilities);


        Ability[] polyAbilities = new Ability[]{ //2, healing boosted
                new RepairFieldAbility(45f, 50f * 8, 50f)
        };
        coreUnitAbility.add(polyAbilities);


        Ability[] omuraAbilities = new Ability[]{ //3, Made spawn time longer
                new UnitSpawnAbility(elude, 75f * 15f, 0f, 0f)
        };
        coreUnitAbility.add(omuraAbilities);


        Ability[] oxynoeAbilities = new Ability[]{//4
                new StatusFieldAbility(StatusEffects.overclock, 60f * 6, 60f * 6f, 60f)
        };
        coreUnitAbility.add(oxynoeAbilities);


        Ability[] tectaShield = new Ability[]{ //5, Boosted shield hp and size
                new ShieldArcAbility(){{
                    region = "tecta-shield";
                    radius = 45f;
                    angle = 82f;
                    regen = 0.8f;
                    cooldown = 60f * 8f;
                    max = 3000f;
                    y = -20f;
                    width = 8f;
                    whenShooting = false;
                }}
        };
        coreUnitAbility.add(tectaShield);


        Ability[] eludeAbilities = new Ability[]{ //6
                new MoveEffectAbility(0f, -7f, Pal.sapBulletBack, Fx.missileTrailShort, 4f)
        };
        coreUnitAbility.add(eludeAbilities);


        Ability[] aegiresAbilities = new Ability[]{ //7, Reduced area to prevent cheesing
                new EnergyFieldAbility(40f, 65f, 90f){{
                    statusDuration = 60f * 6f;
                    maxTargets = 25;
                    healPercent = 1.5f;
                    sameTypeHealMult = 0.5f;
                }}};
        coreUnitAbility.add(aegiresAbilities);


        Ability[] suppressionFieldAbility = new Ability[]{ //8 Quell, Not too noticable, remove? or
                // add info for all weapons/ability
                new SuppressionFieldAbility(){{
                    orbRadius = 5.3f;
                    y = 1f;
                }}};
        coreUnitAbility.add(suppressionFieldAbility);

        Ability[] moveLightningAbility = new Ability[]{ //9
                new MoveLightningAbility(40f, 12, 0.25f, 0f, 0.8f, 1.2f, Color.gold)
        };
        coreUnitAbility.add(moveLightningAbility);

        Ability[] moveLightningAbility2 = new Ability[]{ //10
                new MoveLightningAbility(150f, 20, 0.06f, 0f, 0.5f, 0.8f, Color.red)
        };
        coreUnitAbility.add(moveLightningAbility2);

        Ability[] spawnT2Tank = new Ability[]{ //11
                new UnitSpawnAbility(locus, 80f * 25f, 0f, 0f)
        };
        coreUnitAbility.add(spawnT2Tank);

        Ability[] spawnT3Mech = new Ability[]{ //12
                new UnitSpawnAbility(anthicus, 90f * 40f, 0f, 0f)
        };
        coreUnitAbility.add(spawnT3Mech);

        return coreUnitAbility;
    }

    /**
     * Get every possible core units weapons even if they are not balanced.
     * @return Return an array of weapon.
     */
    public static ArrayList<Seq<Weapon>> getPossibleCoreUnitsWeapons(){
        // For some reason (I have no idea) weapon are "combined?" after the game is opened for a
        // second time when the player equips a different weapon during runtime.
        // This is why a restart of the game is required.
        ArrayList<Seq<Weapon>> coreUnitweapons = new ArrayList<>();

        Seq<Weapon> daggerWeapons = new Seq<>(); //0
        daggerWeapons.add(new Weapon("large-weapon"){{
            reload = 13f;
            x = 4f;
            y = 2f;
            top = false;
            ejectEffect = Fx.casing1;
            bullet = new BasicBulletType(2.5f, 9){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
            }};
        }});
        coreUnitweapons.add(daggerWeapons);

        Seq<Weapon> maceWeapons = new Seq<>(); //1
        maceWeapons.add(new Weapon("flamethrower"){{
            top = false;
            shootSound = Sounds.flame;
            shootY = 2f;
            reload = 11f;
            recoil = 1f;
            ejectEffect = Fx.none;
            bullet = new BulletType(4.2f, 37f){{
                ammoMultiplier = 3f;
                hitSize = 7f;
                lifetime = 13f;
                pierce = true;
                pierceBuilding = true;
                pierceCap = 2;
                statusDuration = 60f * 4;
                shootEffect = Fx.shootSmallFlame;
                hitEffect = Fx.hitFlameSmall;
                despawnEffect = Fx.none;
                status = StatusEffects.burning;
                keepVelocity = false;
                hittable = false;
            }};
        }});
        coreUnitweapons.add(maceWeapons);

        Seq<Weapon> fortressWeapons = new Seq<>(); //2
        fortressWeapons.add(new Weapon("artillery"){{
            top = false;
            y = 1f;
            x = 7f;
            reload = 60f;
            recoil = 4f;
            shake = 2f;
            ejectEffect = Fx.casing2;
            shootSound = Sounds.artillery;
            bullet = new ArtilleryBulletType(2f, 20, "shell"){{
                hitEffect = Fx.blastExplosion;
                knockback = 0.8f;
                lifetime = 120f;
                width = height = 14f;
                collides = true;
                collidesTiles = true;
                splashDamageRadius = 35f;
                splashDamage = 80f;
                backColor = Pal.bulletYellowBack;
                frontColor = Pal.bulletYellow;
            }};
        }});
        coreUnitweapons.add(fortressWeapons);

        Seq<Weapon> scepterWeapons1 = new Seq<>(); //3
        scepterWeapons1.add(new Weapon("scepter-weapon"){{
            top = false;
            y = 1f;
            x = 8f;
            shootY = 8f;
            reload = 45f;
            recoil = 5f;
            shake = 2f;
            ejectEffect = Fx.casing3;
            shootSound = Sounds.bang;
            inaccuracy = 3f;

            shoot.shots = 3;
            shoot.shotDelay = 4f;

            bullet = new BasicBulletType(8f, 80){{
                width = 11f;
                height = 20f;
                lifetime = 27f;
                shootEffect = Fx.shootBig;
                lightning = 2;
                lightningLength = 6;
                lightningColor = Pal.surge;
                //standard bullet damage is far too much for lightning
                lightningDamage = 20;
            }};
        }});
        coreUnitweapons.add(scepterWeapons1);

        Seq<Weapon> scepterWeapons2 = new Seq<>();//4
        scepterWeapons2.add(new Weapon("mount-weapon"){{
            reload = 13f;
            x = 4.5f;
            y = 3f;
            rotate = false;
            ejectEffect = Fx.casing1;
            bullet = new BasicBulletType(3f, 10){{
                width = 7f;
                height = 9f;
                lifetime = 50f;
            }};
        }});
        scepterWeapons2.add(new Weapon("mount-weapon"){{
            reload = 16f;
            x = 4.5f;
            y = -4f;
            rotate = false;
            ejectEffect = Fx.casing1;
            bullet = new BasicBulletType(3f, 10){{
                width = 7f;
                height = 9f;
                lifetime = 50f;
            }};
        }});
        coreUnitweapons.add(scepterWeapons2);

        Seq<Weapon> reignWeapons = new Seq<>(); //5
        reignWeapons.add(new Weapon("reign-weapon"){{
            top = false;
            y = 1f;
            x = 10f;
            shootY = 11f;
            reload = 9f;
            recoil = 5f;
            shake = 2f;
            ejectEffect = Fx.casing4;
            shootSound = Sounds.bang;

            bullet = new BasicBulletType(13f, 80){{
                pierce = true;
                pierceCap = 10;
                width = 14f;
                height = 33f;
                lifetime = 15f;
                shootEffect = Fx.shootBig;
                fragVelocityMin = 0.4f;

                hitEffect = Fx.blastExplosion;
                splashDamage = 18f;
                splashDamageRadius = 13f;

                fragBullets = 3;
                fragLifeMin = 0f;
                fragRandomSpread = 30f;

                fragBullet = new BasicBulletType(9f, 20){{
                    width = 10f;
                    height = 10f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 3;

                    lifetime = 20f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 15f;
                    splashDamageRadius = 10f;
                }};
            }};
        }});
        coreUnitweapons.add(reignWeapons);

        Seq<Weapon> novaWeapons = new Seq<>(); //6
        novaWeapons.add(new Weapon("heal-weapon"){{
            top = false;
            shootY = 2f;
            reload = 24f;
            x = 4.5f;
            alternate = false;
            ejectEffect = Fx.none;
            recoil = 2f;
            shootSound = Sounds.lasershoot;

            bullet = new LaserBoltBulletType(5.2f, 13){{
                lifetime = 30f;
                healPercent = 5f;
                collidesTeam = true;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
        }});
        coreUnitweapons.add(novaWeapons);

        Seq<Weapon> pulsarWeapons = new Seq<>(); //7
        pulsarWeapons.add(new Weapon("heal-shotgun-weapon"){{
            top = false;
            x = 5f;
            shake = 2.2f;
            y = 0.5f;
            shootY = 2.5f;

            reload = 36f;
            inaccuracy = 35;

            shoot.shots = 3;
            shoot.shotDelay = 0.5f;

            ejectEffect = Fx.none;
            recoil = 2.5f;
            shootSound = Sounds.spark;

            bullet = new LightningBulletType(){{
                lightningColor = hitColor = Pal.heal;
                damage = 14f;
                lightningLength = 7;
                lightningLengthRand = 7;
                shootEffect = Fx.shootHeal;
                //Does not actually do anything; Just here to make stats work
                healPercent = 2f;

                lightningType = new BulletType(0.0001f, 0f){{
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 10f;
                    hittable = false;
                    healPercent = 1.6f;
                    collidesTeam = true;
                }};
            }};
        }});
        coreUnitweapons.add(pulsarWeapons);

        Seq<Weapon> quasarWeapons = new Seq<>(); //8
        quasarWeapons.add(new Weapon("beam-weapon"){{
            top = false;
            shake = 2f;
            shootY = 4f;
            x = 6.5f;
            reload = 55f;
            recoil = 4f;
            shootSound = Sounds.laser;

            bullet = new LaserBulletType(){{
                damage = 45f;
                recoil = 1f;
                sideAngle = 45f;
                sideWidth = 1f;
                sideLength = 70f;
                healPercent = 10f;
                collidesTeam = true;
                length = 135f;
                colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
            }};
        }});
        coreUnitweapons.add(quasarWeapons);

        Seq<Weapon> velaWeapons1 = new Seq<>(); //9
        velaWeapons1.add(new Weapon("vela-weapon"){{
            mirror = false;
            top = false;
            shake = 4f;
            shootY = 14f;
            x = y = 0f;

            shoot.firstShotDelay = Fx.greenLaserChargeSmall.lifetime - 1f;
            parentizeEffects = true;

            reload = 155f;
            recoil = 0f;
            chargeSound = Sounds.lasercharge2;
            shootSound = Sounds.beam;
            continuous = true;
            cooldownTime = 200f;

            bullet = new ContinuousLaserBulletType(){{
                damage = 35f;
                length = 180f;
                hitEffect = Fx.hitMeltHeal;
                drawSize = 420f;
                lifetime = 160f;
                shake = 1f;
                despawnEffect = Fx.smokeCloud;
                smokeEffect = Fx.none;

                chargeEffect = Fx.greenLaserChargeSmall;

                incendChance = 0.1f;
                incendSpread = 5f;
                incendAmount = 1;

                //constant healing
                healPercent = 1f;
                collidesTeam = true;

                colors = new Color[]{Pal.heal.cpy().a(.2f), Pal.heal.cpy().a(.5f), Pal.heal.cpy().mul(1.2f), Color.white};
            }};

            shootStatus = StatusEffects.slow;
            shootStatusDuration = bullet.lifetime + shoot.firstShotDelay;
        }});
        coreUnitweapons.add(velaWeapons1);

        Seq<Weapon> corvusWeapons = new Seq<>();//10
        corvusWeapons.add(new Weapon("corvus-weapon"){{
            shootSound = Sounds.laserblast;
            chargeSound = Sounds.lasercharge;
            soundPitchMin = 1f;
            top = false;
            mirror = false;
            shake = 14f;
            shootY = 5f;
            x = y = 0;
            reload = 350f;
            recoil = 0f;

            cooldownTime = 350f;

            shootStatusDuration = 60f * 2f;
            shootStatus = StatusEffects.unmoving;
            shoot.firstShotDelay = Fx.greenLaserCharge.lifetime;
            parentizeEffects = true;

            bullet = new LaserBulletType(){{
                length = 460f;
                damage = 560f;
                width = 75f;

                lifetime = 65f;

                lightningSpacing = 35f;
                lightningLength = 5;
                lightningDelay = 1.1f;
                lightningLengthRand = 15;
                lightningDamage = 50;
                lightningAngleRand = 40f;
                largeHit = true;
                lightColor = lightningColor = Pal.heal;

                chargeEffect = Fx.greenLaserCharge;

                healPercent = 25f;
                collidesTeam = true;

                sideAngle = 15f;
                sideWidth = 0f;
                sideLength = 0f;
                colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
            }};
        }});
        coreUnitweapons.add(corvusWeapons);

        Seq<Weapon> crawlerWeapons = new Seq<>(); //11
        crawlerWeapons.add(new Weapon(){{
            shootOnDeath = true;
            reload = 24f;
            shootCone = 180f;
            ejectEffect = Fx.none;
            shootSound = Sounds.explosion;
            x = shootY = 0f;
            mirror = false;
            bullet = new BulletType(){{
                collidesTiles = false;
                collides = false;
                hitSound = Sounds.explosion;

                rangeOverride = 30f;
                hitEffect = Fx.pulverize;
                speed = 0f;
                splashDamageRadius = 55f;
                instantDisappear = true;
                splashDamage = 90f;
                killShooter = true;
                hittable = false;
                collidesAir = true;
            }};
        }});
        coreUnitweapons.add(crawlerWeapons);

        Seq<Weapon> atraxWeapons = new Seq<>(); //12
        atraxWeapons.add(new Weapon("atrax-weapon"){{
            top = false;
            shootY = 3f;
            reload = 9f;
            ejectEffect = Fx.none;
            recoil = 1f;
            x = 7f;
            shootSound = Sounds.flame;

            bullet = new LiquidBulletType(Liquids.slag){{
                damage = 13;
                speed = 2.5f;
                drag = 0.009f;
                shootEffect = Fx.shootSmall;
                lifetime = 57f;
                collidesAir = false;
            }};
        }});
        coreUnitweapons.add(atraxWeapons);

        Seq<Weapon> spiroctWeapons1 = new Seq<>(); //13
        spiroctWeapons1.add(new Weapon("spiroct-weapon"){{
            shootY = 4f;
            reload = 14f;
            ejectEffect = Fx.none;
            recoil = 2f;
            rotate = true;
            shootSound = Sounds.sap;

            x = 6.5f;
            y = -1.5f;

            bullet = new SapBulletType(){{
                sapStrength = 0.5f;
                length = 75f;
                damage = 23;
                shootEffect = Fx.shootSmall;
                hitColor = color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.54f;
                lifetime = 35f;
                knockback = -1.24f;
            }};
        }});
        coreUnitweapons.add(spiroctWeapons1);

        Seq<Weapon> spiroctWeapons2 = new Seq<>(); //14
        spiroctWeapons2.add(new Weapon("mount-purple-weapon"){{
            reload = 18f;
            rotate = true;
            x = 4.5f;
            y = 1f;
            shootSound = Sounds.sap;

            bullet = new SapBulletType(){{
                sapStrength = 0.8f;
                length = 40f;
                damage = 18;
                shootEffect = Fx.shootSmall;
                hitColor = color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.4f;
                lifetime = 25f;
                knockback = -0.65f;
            }};
        }});
        coreUnitweapons.add(spiroctWeapons2);

        Seq<Weapon> arkyidWeapons1 = new Seq<>(); //15
        arkyidWeapons1.add(new Weapon("large-purple-mount"){{
            y = -2f;
            x = 8f;
            shootY = 7f;
            reload = 45;
            shake = 3f;
            rotateSpeed = 2f;
            ejectEffect = Fx.casing1;
            shootSound = Sounds.artillery;
            rotate = true;
            shadow = 8f;
            recoil = 3f;

            bullet = new ArtilleryBulletType(2f, 12){{
                hitEffect = Fx.sapExplosion;
                knockback = 0.8f;
                lifetime = 70f;
                width = height = 19f;
                collidesTiles = true;
                ammoMultiplier = 4f;
                splashDamageRadius = 70f;
                splashDamage = 65f;
                backColor = Pal.sapBulletBack;
                frontColor = lightningColor = Pal.sapBullet;
                lightning = 3;
                lightningLength = 10;
                smokeEffect = Fx.shootBigSmoke2;
                shake = 5f;

                status = StatusEffects.sapped;
                statusDuration = 60f * 10;
            }};
        }});
        coreUnitweapons.add(arkyidWeapons1);

        Seq<Weapon> arkyidWeapons2 = new Seq<>(); //16
        arkyidWeapons2.add(new Weapon("spiroct-weapon"){{
            reload = 9f;
            x = 2.5f;
            y = 2.5f;
            rotate = true;
            bullet = new SapBulletType(){{
                sapStrength = 0.85f;
                length = 55f;
                damage = 40;
                shootEffect = Fx.shootSmall;
                hitColor = color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.55f;
                lifetime = 30f;
                knockback = -1f;
            }};
            shootSound = Sounds.sap;
        }});
        arkyidWeapons2.add(new Weapon("spiroct-weapon"){{
            reload = 14f;
            x = 5f;
            y = 0f;
            rotate = true;
            bullet = new SapBulletType(){{
                sapStrength = 0.85f;
                length = 55f;
                damage = 40;
                shootEffect = Fx.shootSmall;
                hitColor = color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.55f;
                lifetime = 30f;
                knockback = -1f;
            }};
            shootSound = Sounds.sap;
        }});
        arkyidWeapons2.add(new Weapon("spiroct-weapon"){{
            reload = 22f;
            x = 3f;
            y = -2f;
            rotate = true;
            bullet = new SapBulletType(){{
                sapStrength = 0.85f;
                length = 55f;
                damage = 40;
                shootEffect = Fx.shootSmall;
                hitColor = color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.55f;
                lifetime = 30f;
                knockback = -1f;
            }};
            shootSound = Sounds.sap;
        }});
        coreUnitweapons.add(arkyidWeapons2);

        Seq<Weapon> toxopidWeapons1 = new Seq<>(); //17
        toxopidWeapons1.add(new Weapon("large-purple-mount"){{
            y = -2f;
            x = 8f;
            shootY = 7f;
            reload = 30;
            shake = 4f;
            rotateSpeed = 2f;
            ejectEffect = Fx.casing1;
            shootSound = Sounds.shootBig;
            rotate = true;
            shadow = 12f;
            recoil = 3f;

            shoot = new ShootSpread(2, 17f);

            bullet = new ShrapnelBulletType(){{
                length = 90f;
                damage = 110f;
                width = 25f;
                serrationLenScl = 7f;
                serrationSpaceOffset = 60f;
                serrationFadeOffset = 0f;
                serrations = 10;
                serrationWidth = 6f;
                fromColor = Pal.sapBullet;
                toColor = Pal.sapBulletBack;
                shootEffect = smokeEffect = Fx.sparkShoot;
            }};
        }});
        coreUnitweapons.add(toxopidWeapons1);

        Seq<Weapon> toxopidWeapons2 = new Seq<>(); //18
        toxopidWeapons2.add(new Weapon("toxopid-cannon"){{
            y = -1f;
            x = 0f;
            shootY = 22f;
            mirror = false;
            reload = 210;
            shake = 10f;
            recoil = 10f;
            rotateSpeed = 1f;
            ejectEffect = Fx.casing3;
            shootSound = Sounds.artillery;
            rotate = true;
            shadow = 30f;

            rotationLimit = 80f;

            bullet = new ArtilleryBulletType(3f, 50){{
                hitEffect = Fx.sapExplosion;
                knockback = 0.8f;
                lifetime = 80f;
                width = height = 25f;
                collidesTiles = collides = true;
                ammoMultiplier = 4f;
                splashDamageRadius = 80f;
                splashDamage = 75f;
                backColor = Pal.sapBulletBack;
                frontColor = lightningColor = Pal.sapBullet;
                lightning = 5;
                lightningLength = 20;
                smokeEffect = Fx.shootBigSmoke2;
                hitShake = 10f;
                lightRadius = 40f;
                lightColor = Pal.sap;
                lightOpacity = 0.6f;

                status = StatusEffects.sapped;
                statusDuration = 60f * 10;

                fragLifeMin = 0.3f;
                fragBullets = 9;

                fragBullet = new ArtilleryBulletType(2.3f, 30){{
                    hitEffect = Fx.sapExplosion;
                    knockback = 0.8f;
                    lifetime = 90f;
                    width = height = 20f;
                    collidesTiles = false;
                    splashDamageRadius = 70f;
                    splashDamage = 40f;
                    backColor = Pal.sapBulletBack;
                    frontColor = lightningColor = Pal.sapBullet;
                    lightning = 2;
                    lightningLength = 5;
                    smokeEffect = Fx.shootBigSmoke2;
                    hitShake = 5f;
                    lightRadius = 30f;
                    lightColor = Pal.sap;
                    lightOpacity = 0.5f;

                    status = StatusEffects.sapped;
                    statusDuration = 60f * 10;
                }};
            }};
        }});
        coreUnitweapons.add(toxopidWeapons2);

        Seq<Weapon> flareWeapons = new Seq<>(); //19
        flareWeapons.add(new Weapon(){{
            y = 0f;
            x = 2f;
            reload = 20f;
            ejectEffect = Fx.casing1;
            bullet = new BasicBulletType(2.5f, 9){{
                width = 7f;
                height = 9f;
                lifetime = 45f;
                shootEffect = Fx.shootSmall;
                smokeEffect = Fx.shootSmallSmoke;
                ammoMultiplier = 2;
            }};
            shootSound = Sounds.pew;
        }});
        coreUnitweapons.add(flareWeapons);

        Seq<Weapon> horizonWeapons = new Seq<>(); //20
        horizonWeapons.add(new Weapon(){{
            minShootVelocity = 0.75f;
            x = 3f;
            shootY = 0f;
            reload = 12f;
            shootCone = 180f;
            ejectEffect = Fx.none;
            inaccuracy = 15f;
            ignoreRotation = true;
            shootSound = Sounds.none;
            bullet = new BombBulletType(27f, 25f){{
                width = 10f;
                height = 14f;
                hitEffect = Fx.flakExplosion;
                shootEffect = Fx.none;
                smokeEffect = Fx.none;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
        }});
        coreUnitweapons.add(horizonWeapons);

        Seq<Weapon> zenithWeapons = new Seq<>(); //21
        zenithWeapons.add(new Weapon("zenith-missiles"){{
            reload = 40f;
            x = 6.5f;
            rotate = true;
            shake = 1f;
            shoot.shots = 2;
            inaccuracy = 5f;
            velocityRnd = 0.2f;
            shootSound = Sounds.missile;

            bullet = new MissileBulletType(3f, 14){{
                width = 8f;
                height = 8f;
                shrinkY = 0f;
                drag = -0.003f;
                homingRange = 60f;
                keepVelocity = false;
                splashDamageRadius = 25f;
                splashDamage = 15f;
                lifetime = 50f;
                trailColor = Pal.unitBack;
                backColor = Pal.unitBack;
                frontColor = Pal.unitFront;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                weaveScale = 6f;
                weaveMag = 1f;
            }};
        }});
        coreUnitweapons.add(zenithWeapons);

        Seq<Weapon> antumbraWeapons1 = new Seq<>(); //22
        antumbraWeapons1.add(new Weapon("large-bullet-mount"){{
                                y = 0f;
                                x = 7f;
                                shootY = 10f;
                                reload = 12;
                                shake = 1f;
                                rotateSpeed = 2f;
                                ejectEffect = Fx.casing1;
                                shootSound = Sounds.shootBig;
                                rotate = true;
                                shadow = 8f;
                                bullet = new BasicBulletType(7f, 55){{
                                    width = 12f;
                                    height = 18f;
                                    lifetime = 25f;
                                    shootEffect = Fx.shootBig;
                                }};
                            }});
        coreUnitweapons.add(antumbraWeapons1);

        Seq<Weapon> antumbraWeapons2 = new Seq<>(); //23
        antumbraWeapons2.add(new Weapon("missiles-mount"){{
            y = 3f;
            x = 5.5f;
            reload = 20f;
            ejectEffect = Fx.casing1;
            rotateSpeed = 8f;
            bullet = new MissileBulletType(2.7f, 18){{
                width = 8f;
                height = 8f;
                shrinkY = 0f;
                drag = -0.01f;
                splashDamageRadius = 20f;
                splashDamage = 37f;
                ammoMultiplier = 4f;
                lifetime = 50f;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
            shootSound = Sounds.missile;
            rotate = true;
            shadow = 6f;
        }});
        antumbraWeapons2.add(new Weapon("missiles-mount"){{
            y = -3.5f;
            x = 5.5f;
            reload = 35;
            rotateSpeed = 8f;
            ejectEffect = Fx.casing1;
            bullet = new MissileBulletType(2.7f, 18){{
                width = 8f;
                height = 8f;
                shrinkY = 0f;
                drag = -0.01f;
                splashDamageRadius = 20f;
                splashDamage = 37f;
                ammoMultiplier = 4f;
                lifetime = 50f;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
            shootSound = Sounds.missile;
            rotate = true;
            shadow = 6f;
        }});
        coreUnitweapons.add(antumbraWeapons2);

        Seq<Weapon> eclipseWeapons1 = new Seq<>(); //24
        eclipseWeapons1.add(new Weapon("large-laser-mount"){{
            shake = 4f;
            shootY = 9f;
            x = 11f;
            y = 0f;
            rotateSpeed = 2f;
            reload = 45f;
            recoil = 4f;
            shootSound = Sounds.laser;
            shadow = 20f;
            rotate = true;

            bullet = new LaserBulletType(){{
                damage = 115f;
                sideAngle = 20f;
                sideWidth = 1.5f;
                sideLength = 80f;
                width = 25f;
                length = 230f;
                shootEffect = Fx.shockwave;
                colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
            }};
        }});
        coreUnitweapons.add(eclipseWeapons1);

        Seq<Weapon> eclipseWeapons2 = new Seq<>(); //25
        eclipseWeapons2.add(new Weapon("large-artillery"){{
            x = 5f;
            y = 5f;
            rotateSpeed = 2f;
            reload = 9f;
            shootSound = Sounds.shoot;
            shadow = 7f;
            rotate = true;
            recoil = 0.5f;
            shootY = 7.25f;
            bullet = new FlakBulletType(4f, 15){{
                shootEffect = Fx.shootBig;
                ammoMultiplier = 4f;
                splashDamage = 65f;
                splashDamageRadius = 25f;
                collidesGround = true;
                lifetime = 47f;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
        }});
        eclipseWeapons2.add(new Weapon("large-artillery"){{
            y = -4f;
            x = 7f;
            reload = 12f;
            ejectEffect = Fx.casing1;
            rotateSpeed = 7f;
            shake = 1f;
            shootSound = Sounds.shoot;
            rotate = true;
            shadow = 12f;
            shootY = 7.25f;
            bullet = new FlakBulletType(4f, 15){{
                shootEffect = Fx.shootBig;
                ammoMultiplier = 4f;
                splashDamage = 65f;
                splashDamageRadius = 25f;
                collidesGround = true;
                lifetime = 47f;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
        }});
        coreUnitweapons.add(eclipseWeapons2);

        Seq<Weapon> polyWeapons = new Seq<>(); //26
        polyWeapons.add(new Weapon("poly-weapon"){{
            top = false;
            y = -2.5f;
            x = 3.75f;
            reload = 30f;
            ejectEffect = Fx.none;
            recoil = 2f;
            shootSound = Sounds.missile;
            velocityRnd = 0.5f;
            inaccuracy = 15f;
            alternate = true;

            bullet = new MissileBulletType(4f, 12){{
                homingPower = 0.08f;
                weaveMag = 4;
                weaveScale = 4;
                lifetime = 50f;
                keepVelocity = false;
                shootEffect = Fx.shootHeal;
                smokeEffect = Fx.hitLaser;
                hitEffect = despawnEffect = Fx.hitLaser;
                frontColor = Color.white;
                hitSound = Sounds.none;

                healPercent = 5.5f;
                collidesTeam = true;
                reflectable = false;
                backColor = Pal.heal;
                trailColor = Pal.heal;
            }};
        }});
        coreUnitweapons.add(polyWeapons);

        Seq<Weapon> megaWeapons = new Seq<>(); //27
        megaWeapons.add(new Weapon("heal-weapon-mount"){{
            shootSound = Sounds.lasershoot;
            reload = 24f;
            x = 4.5f;
            y = -3f;
            rotate = true;
            bullet = new LaserBoltBulletType(5.2f, 10){{
                lifetime = 35f;
                healPercent = 5.5f;
                collidesTeam = true;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
        }});
        megaWeapons.add(new Weapon("heal-weapon-mount"){{
            shootSound = Sounds.lasershoot;
            reload = 15f;
            x = 3f;
            y = 2f;
            rotate = true;
            bullet = new LaserBoltBulletType(5.2f, 8){{
                lifetime = 35f;
                healPercent = 3f;
                collidesTeam = true;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
        }});
        coreUnitweapons.add(megaWeapons);

        Seq<Weapon> quadWeapons = new Seq<>(); //28
        quadWeapons.add(new Weapon(){{
            x = y = 0f;
            mirror = false;
            reload = 55f;
            minShootVelocity = 0.01f;

            soundPitchMin = 1f;
            shootSound = Sounds.plasmadrop;

            bullet = new BasicBulletType(){{
                sprite = "large-bomb";
                width = height = 120/4f;

                maxRange = 30f;
                ignoreRotation = true;

                backColor = Pal.heal;
                frontColor = Color.white;
                mixColorTo = Color.white;

                hitSound = Sounds.plasmaboom;

                shootCone = 180f;
                ejectEffect = Fx.none;
                hitShake = 4f;

                collidesAir = false;

                lifetime = 70f;

                despawnEffect = Fx.greenBomb;
                hitEffect = Fx.massiveExplosion;
                keepVelocity = false;
                spin = 2f;

                shrinkX = shrinkY = 0.7f;

                speed = 0f;
                collides = false;

                healPercent = 15f;
                splashDamage = 220f;
                splashDamageRadius = 80f;
            }};
        }});
        coreUnitweapons.add(quadWeapons);

        Seq<Weapon> rissoWeapons1 = new Seq<>(); //29
        rissoWeapons1.add(new Weapon("mount-weapon"){{
            reload = 13f;
            x = 4f;
            shootY = 4f;
            y = 1f;
            rotate = true;
            ejectEffect = Fx.casing1;
            bullet = new BasicBulletType(2.5f, 9){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
                ammoMultiplier = 2;
            }};
        }});
        coreUnitweapons.add(rissoWeapons1);

        Seq<Weapon> rissoWeapons2 = new Seq<>(); //30
        rissoWeapons2.add(new Weapon("missiles-mount"){{
            mirror = false;
            reload = 25f;
            x = 0f;
            y = -5f;
            rotate = true;
            ejectEffect = Fx.casing1;
            shootSound = Sounds.missile;
            bullet = new MissileBulletType(2.7f, 12, "missile"){{
                keepVelocity = true;
                width = 8f;
                height = 8f;
                shrinkY = 0f;
                drag = -0.003f;
                homingRange = 60f;
                splashDamageRadius = 25f;
                splashDamage = 10f;
                lifetime = 65f;
                trailColor = Color.gray;
                backColor = Pal.bulletYellowBack;
                frontColor = Pal.bulletYellow;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                weaveScale = 8f;
                weaveMag = 2f;
            }};
        }});
        coreUnitweapons.add(rissoWeapons2);

        Seq<Weapon> minkeWeapons1 = new Seq<>(); //31
        minkeWeapons1.add(new Weapon("mount-weapon"){{
            reload = 10f;
            x = 5f;
            y = 0.5f;
            rotate = true;
            rotateSpeed = 5f;
            inaccuracy = 8f;
            ejectEffect = Fx.casing1;
            shootSound = Sounds.shoot;
            bullet = new FlakBulletType(4.2f, 3){{
                lifetime = 60f;
                ammoMultiplier = 4f;
                shootEffect = Fx.shootSmall;
                width = 6f;
                height = 8f;
                hitEffect = Fx.flakExplosion;
                splashDamage = 27f * 1.5f;
                splashDamageRadius = 15f;
            }};
        }});
        coreUnitweapons.add(minkeWeapons1);

        Seq<Weapon> minkeWeapons2 = new Seq<>(); //32
        minkeWeapons2.add(new Weapon("artillery-mount"){{
            reload = 30f;
            x = 5f;
            y = -5f;
            rotate = true;
            inaccuracy = 2f;
            rotateSpeed = 2f;
            shake = 1.5f;
            ejectEffect = Fx.casing2;
            shootSound = Sounds.bang;
            bullet = new ArtilleryBulletType(3f, 20, "shell"){{
                hitEffect = Fx.flakExplosion;
                knockback = 0.8f;
                lifetime = 80f;
                width = height = 11f;
                collidesTiles = false;
                splashDamageRadius = 30f * 0.75f;
                splashDamage = 40f;
            }};
        }});
        coreUnitweapons.add(minkeWeapons2);

        Seq<Weapon> brydeWeapons1 = new Seq<>(); //33
        brydeWeapons1.add(new Weapon("large-artillery"){{
            reload = 65f;
            mirror = false;
            x = 0f;
            y = -3.5f;
            rotateSpeed = 1.7f;
            rotate = true;
            shootY = 7f;
            shake = 5f;
            recoil = 4f;
            shadow = 12f;

            inaccuracy = 3f;
            ejectEffect = Fx.casing3;
            shootSound = Sounds.artillery;

            bullet = new ArtilleryBulletType(3.2f, 15){{
                trailMult = 0.8f;
                hitEffect = Fx.massiveExplosion;
                knockback = 1.5f;
                lifetime = 84f;
                height = 15.5f;
                width = 15f;
                collidesTiles = false;
                splashDamageRadius = 40f;
                splashDamage = 70f;
                backColor = Pal.missileYellowBack;
                frontColor = Pal.missileYellow;
                trailEffect = Fx.artilleryTrail;
                trailSize = 6f;
                hitShake = 4f;

                shootEffect = Fx.shootBig2;

                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
        }});
        coreUnitweapons.add(brydeWeapons1);

        Seq<Weapon> brydeWeapons2 = new Seq<>(); //34
        brydeWeapons2.add(new Weapon("missiles-mount"){{
            reload = 20f;
            x = 6.5f;
            y = -1f;

            shadow = 6f;

            rotateSpeed = 4f;
            rotate = true;
            shoot.shots = 2;
            shoot.shotDelay = 3f;

            inaccuracy = 5f;
            velocityRnd = 0.1f;
            shootSound = Sounds.missile;

            ejectEffect = Fx.none;
            bullet = new MissileBulletType(2.7f, 12){{
                width = 8f;
                height = 8f;
                shrinkY = 0f;
                drag = -0.003f;
                homingRange = 60f;
                keepVelocity = false;
                splashDamageRadius = 25f;
                splashDamage = 10f;
                lifetime = 70f;
                trailColor = Color.gray;
                backColor = Pal.bulletYellowBack;
                frontColor = Pal.bulletYellow;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                weaveScale = 8f;
                weaveMag = 1f;
            }};
        }});
        coreUnitweapons.add(brydeWeapons2);

        Seq<Weapon> seiWeapons1 = new Seq<>(); //35
        seiWeapons1.add(new Weapon("sei-launcher"){{

            x = 0f;
            y = 3f;
            rotate = false;
            rotateSpeed = 4f;
            mirror = false;

            shadow = 20f;

            shootY = 4.5f;
            recoil = 4f;
            reload = 45f;
            velocityRnd = 0.4f;
            inaccuracy = 7f;
            ejectEffect = Fx.none;
            shake = 1f;
            shootSound = Sounds.missile;

            shoot = new ShootAlternate(){{
                shots = 6;
                shotDelay = 1.5f;
                spread = 4f;
                barrels = 3;
            }};

            bullet = new MissileBulletType(4.2f, 42){{
                homingPower = 0.12f;
                width = 8f;
                height = 8f;
                shrinkX = shrinkY = 0f;
                drag = -0.003f;
                homingRange = 80f;
                keepVelocity = false;
                splashDamageRadius = 35f;
                splashDamage = 45f;
                lifetime = 62f;
                trailColor = Pal.bulletYellowBack;
                backColor = Pal.bulletYellowBack;
                frontColor = Pal.bulletYellow;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                weaveScale = 8f;
                weaveMag = 2f;
            }};
        }});
        coreUnitweapons.add(seiWeapons1);

        Seq<Weapon> seiWeapons2 = new Seq<>(); //36
        seiWeapons2.add(new Weapon("large-bullet-mount"){{
            reload = 60f;
            cooldownTime = 90f;
            x = 6f;
            y = -2f;
            rotateSpeed = 4f;
            rotate = false;
            shootY = 7f;
            shake = 2f;
            recoil = 3f;
            shadow = 12f;
            ejectEffect = Fx.casing3;
            shootSound = Sounds.shootBig;

            shoot.shots = 3;
            shoot.shotDelay = 4f;

            inaccuracy = 1f;
            bullet = new BasicBulletType(7f, 57){{
                width = 13f;
                height = 19f;
                shootEffect = Fx.shootBig;
                lifetime = 35f;
            }};
        }});
        coreUnitweapons.add(seiWeapons2);

        Seq<Weapon> omuraWeapons = new Seq<>(); //37
        omuraWeapons.add(new Weapon("omura-cannon"){{
            reload = 110f;
            cooldownTime = 90f;
            mirror = false;
            x = 0f;
            y = -3.5f;
            rotateSpeed = 1.4f;
            rotate = true;
            shootY = 23f;
            shake = 6f;
            recoil = 10.5f;
            shadow = 50f;
            shootSound = Sounds.railgun;

            ejectEffect = Fx.none;

            bullet = new RailBulletType(){{
                shootEffect = Fx.railShoot;
                length = 500;
                pointEffectSpace = 60f;
                pierceEffect = Fx.railHit;
                pointEffect = Fx.railTrail;
                hitEffect = Fx.massiveExplosion;
                smokeEffect = Fx.shootBig2;
                damage = 1250;
                pierceDamageFactor = 0.5f;
            }};
        }});
        coreUnitweapons.add(omuraWeapons);

        Seq<Weapon> retusaWeapons = new Seq<>(); //38
        retusaWeapons.add(new RepairBeamWeapon("repair-beam-weapon-center"){{
            x = 0f;
            y = -5.5f;
            shootY = 6f;
            beamWidth = 0.8f;
            mirror = false;
            repairSpeed = 0.75f;

            bullet = new BulletType(){{
                maxRange = 120f;
            }};
        }}); //need to test
        coreUnitweapons.add(retusaWeapons);

        Seq<Weapon> oxynoeWeapons1 = new Seq<>(); //39
        oxynoeWeapons1.add(new Weapon("plasma-mount-weapon"){{

            reload = 5f;
            x = 4.5f;
            y = 1.5f;
            rotate = false;
            rotateSpeed = 5f;
            inaccuracy = 10f;
            ejectEffect = Fx.casing1;
            shootSound = Sounds.flame;
            shootCone = 30f;

            bullet = new BulletType(3.4f, 23f){{
                healPercent = 1.5f;
                collidesTeam = true;
                ammoMultiplier = 3f;
                hitSize = 7f;
                lifetime = 18f;
                pierce = true;
                collidesAir = false;
                statusDuration = 60f * 4;
                hitEffect = Fx.hitFlamePlasma;
                ejectEffect = Fx.none;
                despawnEffect = Fx.none;
                status = StatusEffects.burning;
                keepVelocity = false;
                hittable = false;
                shootEffect = new Effect(32f, 80f, e -> {
                    color(Color.white, Pal.heal, Color.gray, e.fin());

                    randLenVectors(e.id, 8, e.finpow() * 60f, e.rotation, 10f, (x, y) -> {
                        Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
                        Drawf.light(e.x + x, e.y + y, 16f * e.fout(), Pal.heal, 0.6f);
                    });
                });
            }};
        }}); //oxynoe
        coreUnitweapons.add(oxynoeWeapons1);

        Seq<Weapon> oxynoeWeapons2 = new Seq<>(); //40
        oxynoeWeapons2.add(new PointDefenseWeapon("point-defense-mount"){{
            mirror = false;
            x = 0f;
            y = 1f;
            reload = 9f;
            targetInterval = 10f;
            targetSwitchInterval = 15f;

            bullet = new BulletType(){{
                shootEffect = Fx.sparkShoot;
                hitEffect = Fx.pointHit;
                maxRange = 100f;
                damage = 17f;
            }};
        }});
        coreUnitweapons.add(oxynoeWeapons2);

        Seq<Weapon> cyerceWeapons1 = new Seq<>(); //41
        cyerceWeapons1.add(new RepairBeamWeapon("repair-beam-weapon-center"){{
            x = 4f;
            y = -6f;
            shootY = 6f;
            beamWidth = 0.8f;
            repairSpeed = 0.7f;

            bullet = new BulletType(){{
                maxRange = 130f;
            }};
        }});
        coreUnitweapons.add(cyerceWeapons1);

        Seq<Weapon> cyerceWeapons2 = new Seq<>(); //42
        cyerceWeapons2.add(new Weapon("plasma-missile-mount"){{
            reload = 60f;
            x = 5f;
            y = 4f;

            shadow = 5f;

            rotateSpeed = 4f;
            rotate = false;
            inaccuracy = 1f;
            velocityRnd = 0.1f;
            shootSound = Sounds.missile;

            ejectEffect = Fx.none;
            bullet = new FlakBulletType(2.5f, 25){{
                sprite = "missile-large";
                //for targeting
                collidesGround = collidesAir = true;
                explodeRange = 40f;
                width = height = 12f;
                shrinkY = 0f;
                drag = -0.003f;
                homingRange = 60f;
                keepVelocity = false;
                lightRadius = 60f;
                lightOpacity = 0.7f;
                lightColor = Pal.heal;

                splashDamageRadius = 30f;
                splashDamage = 25f;

                lifetime = 80f;
                backColor = Pal.heal;
                frontColor = Color.white;

                hitEffect = new ExplosionEffect(){{
                    lifetime = 28f;
                    waveStroke = 6f;
                    waveLife = 10f;
                    waveRadBase = 7f;
                    waveColor = Pal.heal;
                    waveRad = 30f;
                    smokes = 6;
                    smokeColor = Color.white;
                    sparkColor = Pal.heal;
                    sparks = 6;
                    sparkRad = 35f;
                    sparkStroke = 1.5f;
                    sparkLen = 4f;
                }};

                weaveScale = 8f;
                weaveMag = 1f;

                trailColor = Pal.heal;
                trailWidth = 4.5f;
                trailLength = 29;

                fragBullets = 7;
                fragVelocityMin = 0.3f;

                fragBullet = new MissileBulletType(3.9f, 11){{
                    homingPower = 0.2f;
                    weaveMag = 4;
                    weaveScale = 4;
                    lifetime = 60f;
                    keepVelocity = false;
                    shootEffect = Fx.shootHeal;
                    smokeEffect = Fx.hitLaser;
                    splashDamage = 13f;
                    splashDamageRadius = 20f;
                    frontColor = Color.white;
                    hitSound = Sounds.none;

                    lightColor = Pal.heal;
                    lightRadius = 40f;
                    lightOpacity = 0.7f;

                    trailColor = Pal.heal;
                    trailWidth = 2.5f;
                    trailLength = 20;
                    trailChance = -1f;

                    healPercent = 2.8f;
                    collidesTeam = true;
                    backColor = Pal.heal;

                    despawnEffect = Fx.none;
                    hitEffect = new ExplosionEffect(){{
                        lifetime = 20f;
                        waveStroke = 2f;
                        waveColor = Pal.heal;
                        waveRad = 12f;
                        smokeSize = 0f;
                        smokeSizeBase = 0f;
                        sparkColor = Pal.heal;
                        sparks = 9;
                        sparkRad = 35f;
                        sparkLen = 4f;
                        sparkStroke = 1.5f;
                    }};
                }};
            }};
        }});
        coreUnitweapons.add(cyerceWeapons2);

        Seq<Weapon> aegiresWeapons = new Seq<>(); //43
        for(float mountY : new float[]{-4f, 3}){
            aegiresWeapons.add(new PointDefenseWeapon("point-defense-mount"){{
                x = 5f;
                y = mountY;
                reload = 4f;
                targetInterval = 8f;
                targetSwitchInterval = 8f;

                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 180f;
                    damage = 30f;
                }};
            }});
        }
        coreUnitweapons.add(aegiresWeapons);

        Seq<Weapon> navanaxWeapons1 = new Seq<>(); //44
        for(float mountY : new float[]{-5f, 6f}){
            for(float sign : Mathf.signs){
                navanaxWeapons1.add(new Weapon("plasma-laser-mount"){{
                    shadow = 20f;
                    controllable = false;
                    autoTarget = true;
                    mirror = false;
                    shake = 3f;
                    shootY = 7f;
                    rotate = true;
                    x = 8f * sign;
                    y = mountY;

                    targetInterval = 20f;
                    targetSwitchInterval = 35f;

                    rotateSpeed = 3.5f;
                    reload = 170f;
                    recoil = 1f;
                    shootSound = Sounds.beam;
                    continuous = true;
                    cooldownTime = reload;

                    bullet = new ContinuousLaserBulletType(){{
                        maxRange = 90f;
                        damage = 27f;
                        length = 95f;
                        hitEffect = Fx.hitMeltHeal;
                        drawSize = 200f;
                        lifetime = 155f;
                        shake = 1f;

                        shootEffect = Fx.shootHeal;
                        smokeEffect = Fx.none;
                        width = 4f;
                        largeHit = false;

                        incendChance = 0.03f;
                        incendSpread = 5f;
                        incendAmount = 1;

                        healPercent = 0.4f;
                        collidesTeam = true;

                        colors = new Color[]{Pal.heal.cpy().a(.2f), Pal.heal.cpy().a(.5f), Pal.heal.cpy().mul(1.2f), Color.white};
                    }};
                }});
            }
        }
        coreUnitweapons.add(navanaxWeapons1);

        Seq<Weapon> navanaxWeapons2 = new Seq<>(); //45
        navanaxWeapons2.add(new Weapon("emp-cannon-mount"){{
            rotate = true;

            x = 10f;
            y = -3f;

            reload = 65f;
            shake = 3f;
            rotateSpeed = 2f;
            shadow = 30f;
            shootY = 7f;
            recoil = 4f;
            cooldownTime = reload - 10f;
            //TODO better sound
            shootSound = Sounds.laser;

            bullet = new EmpBulletType(){{
                float rad = 100f;

                scaleLife = true;
                lightOpacity = 0.7f;
                unitDamageScl = 0.8f;
                healPercent = 20f;
                timeIncrease = 3f;
                timeDuration = 60f * 20f;
                powerDamageScl = 3f;
                damage = 60;
                hitColor = lightColor = Pal.heal;
                lightRadius = 70f;
                shootEffect = Fx.hitEmpSpark;
                smokeEffect = Fx.shootBigSmoke2;
                lifetime = 60f;
                sprite = "circle-bullet";
                backColor = Pal.heal;
                frontColor = Color.white;
                width = height = 12f;
                shrinkY = 0f;
                speed = 5f;
                trailLength = 20;
                trailWidth = 6f;
                trailColor = Pal.heal;
                trailInterval = 3f;
                splashDamage = 70f;
                splashDamageRadius = rad;
                hitShake = 4f;
                trailRotation = true;
                status = StatusEffects.electrified;
                hitSound = Sounds.plasmaboom;

                trailEffect = new Effect(16f, e -> {
                    color(Pal.heal);
                    for(int s : Mathf.signs){
                        Drawf.tri(e.x, e.y, 4f, 30f * e.fslope(), e.rotation + 90f*s);
                    }
                });

                hitEffect = new Effect(50f, 100f, e -> {
                    e.scaled(7f, b -> {
                        color(Pal.heal, b.fout());
                        Fill.circle(e.x, e.y, rad);
                    });

                    color(Pal.heal);
                    stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, rad);

                    int points = 10;
                    float offset = Mathf.randomSeed(e.id, 360f);
                    for(int i = 0; i < points; i++){
                        float angle = i* 360f / points + offset;
                        //for(int s : Mathf.zeroOne){
                        Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 6f, 50f * e.fout(), angle/* + s*180f*/);
                        //}
                    }

                    Fill.circle(e.x, e.y, 12f * e.fout());
                    color();
                    Fill.circle(e.x, e.y, 6f * e.fout());
                    Drawf.light(e.x, e.y, rad * 1.6f, Pal.heal, e.fout());
                });
            }};
        }});
        coreUnitweapons.add(navanaxWeapons2);

        Seq<Weapon> alphaWeapons = new Seq<>(); //46
        alphaWeapons.add(new Weapon("small-basic-weapon"){{
            reload = 17f;
            x = 2.75f;
            y = 1f;
            top = false;
            ejectEffect = Fx.casing1;

            bullet = new BasicBulletType(2.5f, 11){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
                shootEffect = Fx.shootSmall;
                smokeEffect = Fx.shootSmallSmoke;
                buildingDamageMultiplier = 0.01f;
            }};
        }});
        coreUnitweapons.add(alphaWeapons);

        Seq<Weapon> betaWeapons = new Seq<>(); //47
        betaWeapons.add(new Weapon("small-mount-weapon"){{
            top = false;
            reload = 20f;
            x = 3f;
            y = 0.5f;
            rotate = true;
            shoot.shots = 2;
            shoot.shotDelay = 4f;
            ejectEffect = Fx.casing1;

            bullet = new BasicBulletType(3f, 11){{
                width = 7f;
                height = 9f;
                lifetime = 60f;
                shootEffect = Fx.shootSmall;
                smokeEffect = Fx.shootSmallSmoke;
                buildingDamageMultiplier = 0.01f;
            }};
        }});
        coreUnitweapons.add(betaWeapons);

        Seq<Weapon> gammaWeapons = new Seq<>(); //48
        gammaWeapons.add(new Weapon("small-mount-weapon"){{
            top = false;
            reload = 15f;
            x = 1f;
            y = 2f;
            shoot = new ShootSpread(){{
                shots = 2;
                shotDelay = 3f;
                spread = 2f;
            }};

            inaccuracy = 3f;
            ejectEffect = Fx.casing1;

            bullet = new BasicBulletType(3.5f, 11){{
                width = 6.5f;
                height = 11f;
                lifetime = 70f;
                shootEffect = Fx.shootSmall;
                smokeEffect = Fx.shootSmallSmoke;
                buildingDamageMultiplier = 0.01f;
                homingPower = 0.04f;
            }};
        }});
        coreUnitweapons.add(gammaWeapons);

        Seq<Weapon> stellWeapons = new Seq<>(); //49
        stellWeapons.add(new Weapon("stell-weapon"){{
            layerOffset = 0.0001f;
            reload = 50f;
            shootY = 4.5f;
            recoil = 1f;
            rotate = false;
            rotateSpeed = 2.2f;
            mirror = false;
            x = 0f;
            y = -0.75f;
            heatColor = Color.valueOf("f9350f");
            cooldownTime = 30f;

            bullet = new BasicBulletType(4f, 40){{
                sprite = "missile-large";
                smokeEffect = Fx.shootBigSmoke;
                shootEffect = Fx.shootBigColor;
                width = 5f;
                height = 7f;
                lifetime = 40f;
                hitSize = 4f;
                hitColor = backColor = trailColor = Color.valueOf("feb380");
                frontColor = Color.white;
                trailWidth = 1.7f;
                trailLength = 5;
                despawnEffect = hitEffect = Fx.hitBulletColor;
            }};
        }});
        coreUnitweapons.add(stellWeapons);

        Seq<Weapon> locusWeapons = new Seq<>(); //50
        locusWeapons.add(new Weapon("locus-weapon"){{
            shootSound = Sounds.bolt;
            layerOffset = 0.0001f;
            reload = 18f;
            shootY = 10f;
            recoil = 1f;
            rotate = false;
            rotateSpeed = 1.4f;
            mirror = false;
            shootCone = 2f;
            x = 0f;
            y = 0f;
            heatColor = Color.valueOf("f9350f");
            cooldownTime = 30f;

            shoot = new ShootAlternate(3.5f);

            bullet = new RailBulletType(){{
                length = 160f;
                damage = 48f;
                hitColor = Color.valueOf("feb380");
                hitEffect = endEffect = Fx.hitBulletColor;
                pierceDamageFactor = 0.8f;

                smokeEffect = Fx.colorSpark;

                endEffect = new Effect(14f, e -> {
                    color(e.color);
                    Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                });

                shootEffect = new Effect(10, e -> {
                    color(e.color);
                    float w = 1.2f + 7 * e.fout();

                    Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                    color(e.color);

                    for(int i : Mathf.signs){
                        Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                    }

                    Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                });

                lineEffect = new Effect(20f, e -> {
                    if(!(e.data instanceof Vec2 v)) return;

                    color(e.color);
                    stroke(e.fout() * 0.9f + 0.6f);

                    Fx.rand.setSeed(e.id);
                    for(int i = 0; i < 7; i++){
                        Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                        Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                    }

                    e.scaled(14f, b -> {
                        stroke(b.fout() * 1.5f);
                        color(e.color);
                        Lines.line(e.x, e.y, v.x, v.y);
                    });
                });
            }};
        }});
        coreUnitweapons.add(locusWeapons);

        Seq<Weapon> preceptWeapons = new Seq<>(); //51
        preceptWeapons.add(new Weapon("precept-weapon"){{
            shootSound = Sounds.dullExplosion;
            layerOffset = 0.0001f;
            reload = 80f;
            shootY = 16f;
            recoil = 3f;
            rotate = false;
            rotateSpeed = 1.3f;
            mirror = false;
            shootCone = 2f;
            x = 0f;
            y = 2.5f;
            heatColor = Color.valueOf("f9350f");
            cooldownTime = 30f;
            bullet = new BasicBulletType(7f, 120){{
                sprite = "missile-large";
                width = 7.5f;
                height = 13f;
                lifetime = 28f;
                hitSize = 6f;
                pierceCap = 2;
                pierce = true;
                pierceBuilding = true;
                hitColor = backColor = trailColor = Color.valueOf("feb380");
                frontColor = Color.white;
                trailWidth = 2.8f;
                trailLength = 8;
                hitEffect = despawnEffect = Fx.blastExplosion;
                shootEffect = Fx.shootTitan;
                smokeEffect = Fx.shootSmokeTitan;
                splashDamageRadius = 20f;
                splashDamage = 50f;

                trailEffect = Fx.hitSquaresColor;
                trailRotation = true;
                trailInterval = 3f;

                fragBullets = 4;

                fragBullet = new BasicBulletType(5f, 35){{
                    sprite = "missile-large";
                    width = 5f;
                    height = 7f;
                    lifetime = 15f;
                    hitSize = 4f;
                    pierceCap = 3;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = Color.valueOf("feb380");
                    frontColor = Color.white;
                    trailWidth = 1.7f;
                    trailLength = 3;
                    drag = 0.01f;
                    despawnEffect = hitEffect = Fx.hitBulletColor;
                }};
            }};
        }});
        coreUnitweapons.add(preceptWeapons);

        Seq<Weapon> vanquishWeapons1 = new Seq<>(); //52
        vanquishWeapons1.add(new Weapon("vanquish-weapon"){{
            shootSound = Sounds.mediumCannon;
            layerOffset = 0.0001f;
            reload = 70f;
            shootY = 71f / 4f;
            shake = 5f;
            recoil = 4f;
            rotate = false;
            rotateSpeed = 1f;
            mirror = false;
            x = 0f;
            y = 3f;
            shadow = 28f;
            heatColor = Color.valueOf("f9350f");
            cooldownTime = 80f;

            bullet = new BasicBulletType(8f, 190){{
                sprite = "missile-large";
                width = 9.5f;
                height = 13f;
                lifetime = 18f;
                hitSize = 6f;
                shootEffect = Fx.shootTitan;
                smokeEffect = Fx.shootSmokeTitan;
                pierceCap = 2;
                pierce = true;
                pierceBuilding = true;
                hitColor = backColor = trailColor = Color.valueOf("feb380");
                frontColor = Color.white;
                trailWidth = 3.1f;
                trailLength = 8;
                hitEffect = despawnEffect = Fx.blastExplosion;
                splashDamageRadius = 20f;
                splashDamage = 50f;

                fragOnHit = false;
                fragRandomSpread = 0f;
                fragSpread = 10f;
                fragBullets = 5;
                fragVelocityMin = 1f;
                despawnSound = Sounds.dullExplosion;

                fragBullet = new BasicBulletType(8f, 35){{
                    sprite = "missile-large";
                    width = 8f;
                    height = 12f;
                    lifetime = 15f;
                    hitSize = 4f;
                    hitColor = backColor = trailColor = Color.valueOf("feb380");
                    frontColor = Color.white;
                    trailWidth = 2.8f;
                    trailLength = 6;
                    hitEffect = despawnEffect = Fx.blastExplosion;
                    splashDamageRadius = 10f;
                    splashDamage = 20f;
                }};
            }};
        }});
        coreUnitweapons.add(vanquishWeapons1);

        Seq<Weapon> vanquishWeapons2 = new Seq<>(); //53
        int i = 0;
        for(float f : new float[]{3f, -5.5f }){
            int fi = i ++;
            vanquishWeapons2.add(new Weapon("vanquish-point-weapon"){{
                reload = 35f + fi * 5;
                x = 5f;
                y = f;
                shootY = 5.5f;
                recoil = 2f;
                rotate = false;
                rotateSpeed = 2f;

                bullet = new BasicBulletType(4.5f, 25){{
                    width = 6.5f;
                    height = 11f;
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootBigSmoke;
                    hitColor = backColor = trailColor = Color.valueOf("feb380");
                    frontColor = Color.white;
                    trailWidth = 1.5f;
                    trailLength = 4;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }};
            }});
        }
        coreUnitweapons.add(vanquishWeapons2);

        Seq<Weapon> conquerWeapons = new Seq<>(); //54
        conquerWeapons.add(new Weapon("conquer-weapon"){{
            shootSound = Sounds.largeCannon;
            layerOffset = 0.1f;
            reload = 100f;
            shootY = 32.5f;
            shake = 5f;
            recoil = 5f;
            rotate = false;
            rotateSpeed = 0.6f;
            mirror = false;
            x = 0f;
            y = 2f;
            shadow = 50f;
            heatColor = Color.valueOf("f9350f");
            shootWarmupSpeed = 0.06f;
            cooldownTime = 110f;
            heatColor = Color.valueOf("f9350f");
            minWarmup = 0.9f;

            parts.addAll(
                    new RegionPart("-glow"){{
                        color = Color.red;
                        blending = Blending.additive;
                        outline = mirror = false;
                    }},
                    new RegionPart("-sides"){{
                        progress = PartProgress.warmup;
                        mirror = true;
                        under = true;
                        moveX = 0.75f;
                        moveY = 0.75f;
                        moveRot = 82f;
                        x = 37 / 4f;
                        y = 8 / 4f;
                    }},
                    new RegionPart("-sinks"){{
                        progress = PartProgress.warmup;
                        mirror = true;
                        under = true;
                        heatColor = new Color(1f, 0.1f, 0.1f);
                        moveX = 17f / 4f;
                        moveY = -15f / 4f;
                        x = 32 / 4f;
                        y = -34 / 4f;
                    }},
                    new RegionPart("-sinks-heat"){{
                        blending = Blending.additive;
                        progress = PartProgress.warmup;
                        mirror = true;
                        outline = false;
                        colorTo = new Color(1f, 0f, 0f, 0.5f);
                        color = colorTo.cpy().a(0f);
                        moveX = 17f / 4f;
                        moveY = -15f / 4f;
                        x = 32 / 4f;
                        y = -34 / 4f;
                    }}
            );

            for(int i = 1; i <= 3; i++){
                int fi = i;
                parts.add(new RegionPart("-blade"){{
                    progress = PartProgress.warmup.delay((3 - fi) * 0.3f).blend(PartProgress.reload, 0.3f);
                    heatProgress = PartProgress.heat.add(0.3f).min(PartProgress.warmup);
                    heatColor = new Color(1f, 0.1f, 0.1f);
                    mirror = true;
                    under = true;
                    moveRot = -40f * fi;
                    moveX = 3f;
                    layerOffset = -0.002f;

                    x = 11 / 4f;
                }});
            }

            bullet = new BasicBulletType(8f, 360f){{
                sprite = "missile-large";
                width = 12f;
                height = 20f;
                lifetime = 35f;
                hitSize = 6f;

                smokeEffect = Fx.shootSmokeTitan;
                pierceCap = 3;
                pierce = true;
                pierceBuilding = true;
                hitColor = backColor = trailColor = Color.valueOf("feb380");
                frontColor = Color.white;
                trailWidth = 4f;
                trailLength = 9;
                hitEffect = despawnEffect = Fx.massiveExplosion;

                shootEffect = new ExplosionEffect(){{
                    lifetime = 40f;
                    waveStroke = 4f;
                    waveColor = sparkColor = trailColor;
                    waveRad = 15f;
                    smokeSize = 5f;
                    smokes = 8;
                    smokeSizeBase = 0f;
                    smokeColor = trailColor;
                    sparks = 8;
                    sparkRad = 40f;
                    sparkLen = 4f;
                    sparkStroke = 3f;
                }};

                int count = 6;
                for(int j = 0; j < count; j++){
                    int s = j;
                    for(int i : Mathf.signs){
                        float fin = 0.05f + (j + 1) / (float)count;
                        float spd = speed;
                        float life = lifetime / Mathf.lerp(fin, 1f, 0.5f);
                        spawnBullets.add(new BasicBulletType(spd * fin, 60){{
                            drag = 0.002f;
                            width = 12f;
                            height = 11f;
                            lifetime = life + 5f;
                            weaveRandom = false;
                            hitSize = 5f;
                            pierceCap = 2;
                            pierce = true;
                            pierceBuilding = true;
                            hitColor = backColor = trailColor = Color.valueOf("feb380");
                            frontColor = Color.white;
                            trailWidth = 2.5f;
                            trailLength = 7;
                            weaveScale = (3f + s/2f) / 1.2f;
                            weaveMag = i * (4f - fin * 2f);

                            splashDamage = 65f;
                            splashDamageRadius = 30f;
                            despawnEffect = new ExplosionEffect(){{
                                lifetime = 50f;
                                waveStroke = 4f;
                                waveColor = sparkColor = trailColor;
                                waveRad = 30f;
                                smokeSize = 7f;
                                smokes = 6;
                                smokeSizeBase = 0f;
                                smokeColor = trailColor;
                                sparks = 5;
                                sparkRad = 30f;
                                sparkLen = 3f;
                                sparkStroke = 1.5f;
                            }};
                        }});
                    }
                }
            }};
        }});
        coreUnitweapons.add(conquerWeapons);

        Seq<Weapon> meruiWeapons = new Seq<>(); //55
        meruiWeapons.add(new Weapon("merui-weapon"){{
            shootSound = Sounds.missile;
            mirror = false;
            showStatSprite = false;
            x = 0f;
            y = 1f;
            shootY = 4f;
            reload = 60f;
            cooldownTime = 42f;
            heatColor = Pal.turretHeat;

            bullet = new ArtilleryBulletType(3f, 40){{
                shootEffect = new MultiEffect(Fx.shootSmallColor, new Effect(9, e -> {
                    color(Color.white, e.color, e.fin());
                    stroke(0.7f + e.fout());
                    Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                    Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                }));

                collidesTiles = true;
                backColor = hitColor = Pal.techBlue;
                frontColor = Color.white;

                knockback = 0.8f;
                lifetime = 50f;
                width = height = 9f;
                splashDamageRadius = 19f;
                splashDamage = 30f;

                trailLength = 27;
                trailWidth = 2.5f;
                trailEffect = Fx.none;
                trailColor = backColor;

                trailInterp = Interp.slope;

                shrinkX = 0.6f;
                shrinkY = 0.2f;

                hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, new WaveEffect(){{
                    colorFrom = colorTo = Pal.techBlue;
                    sizeTo = splashDamageRadius + 2f;
                    lifetime = 9f;
                    strokeFrom = 2f;
                }});
            }};
        }});
        coreUnitweapons.add(meruiWeapons);

        Seq<Weapon> cleroiWeapons1 = new Seq<>(); //56
        cleroiWeapons1.add(new Weapon("cleroi-weapon"){{
            shootSound = Sounds.blaster;
            x = 14f / 4f;
            y = 3f;
            reload = 30f;
            layerOffset = -0.002f;
            alternate = false;
            heatColor = Color.red;
            cooldownTime = 25f;
            smoothReloadSpeed = 0.15f;
            recoil = 2f;

            bullet = new BasicBulletType(3.5f, 30){{
                backColor = trailColor = hitColor = Pal.techBlue;
                frontColor = Color.white;
                width = 7.5f;
                height = 10f;
                lifetime = 40f;
                trailWidth = 2f;
                trailLength = 4;
                shake = 1f;

                trailEffect = Fx.missileTrail;
                trailParam = 1.8f;
                trailInterval = 6f;

                splashDamageRadius = 30f;
                splashDamage = 43f;

                hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                    colorFrom = colorTo = Pal.techBlue;
                    sizeTo = splashDamageRadius + 3f;
                    lifetime = 9f;
                    strokeFrom = 3f;
                }});

                shootEffect = new MultiEffect(Fx.shootBigColor, new Effect(9, e -> {
                    color(Color.white, e.color, e.fin());
                    stroke(0.7f + e.fout());
                    Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                    Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                }));
                smokeEffect = Fx.shootSmokeSquare;
                ammoMultiplier = 2;
            }};
        }});
        coreUnitweapons.add(cleroiWeapons1);

        Seq<Weapon> cleroiWeapons2 = new Seq<>(); //57
        cleroiWeapons2.add(new PointDefenseWeapon("cleroi-point-defense"){{
            x = 16f / 4f;
            y = -3f;
            reload = 9f;

            targetInterval = 9f;
            targetSwitchInterval = 12f;
            recoil = 0.5f;

            bullet = new BulletType(){{
                shootSound = Sounds.lasershoot;
                shootEffect = Fx.sparkShoot;
                hitEffect = Fx.pointHit;
                maxRange = 100f;
                damage = 38f;
            }};
        }});
        coreUnitweapons.add(cleroiWeapons2);

        Seq<Weapon> tectaWeapons = new Seq<>(); //58
        tectaWeapons.add(new Weapon("tecta-weapon"){{
            shootSound = Sounds.malignShoot;
            mirror = true;
            top = false;

            x = 8f;
            y = 1f;
            shootY = 47 / 4f;
            recoil = 3f;
            reload = 40f;
            shake = 3f;
            cooldownTime = 40f;

            shoot.shots = 3;
            inaccuracy = 3f;
            velocityRnd = 0.33f;
            heatColor = Color.red;

            bullet = new MissileBulletType(4.2f, 60){{
                homingPower = 0.2f;
                weaveMag = 4;
                weaveScale = 4;
                lifetime = 55f;
                shootEffect = Fx.shootBig2;
                smokeEffect = Fx.shootSmokeTitan;
                splashDamage = 70f;
                splashDamageRadius = 30f;
                frontColor = Color.white;
                hitSound = Sounds.none;
                width = height = 10f;

                lightColor = trailColor = backColor = Pal.techBlue;
                lightRadius = 40f;
                lightOpacity = 0.7f;

                trailWidth = 2.8f;
                trailLength = 20;
                trailChance = -1f;
                despawnSound = Sounds.dullExplosion;

                despawnEffect = Fx.none;
                hitEffect = new ExplosionEffect(){{
                    lifetime = 20f;
                    waveStroke = 2f;
                    waveColor = sparkColor = trailColor;
                    waveRad = 12f;
                    smokeSize = 0f;
                    smokeSizeBase = 0f;
                    sparks = 10;
                    sparkRad = 35f;
                    sparkLen = 4f;
                    sparkStroke = 1.5f;
                }};
            }};
        }});
        coreUnitweapons.add(tectaWeapons);

        Seq<Weapon> collarisWeapons = new Seq<>(); //59
        collarisWeapons.add(new Weapon("collaris-weapon"){{
            shootSound = Sounds.pulseBlast;
            mirror = true;
            rotationLimit = 30f;
            rotateSpeed = 0.4f;
            rotate = false;

            x = 8.5f;
            y = -28f / 4f;
            shootY = 64f / 4f;
            recoil = 4f;
            reload = 130f;
            cooldownTime = reload * 1.2f;
            shake = 7f;
            layerOffset = 0.02f;
            shadow = 10f;

            shootStatus = StatusEffects.slow;
            shootStatusDuration = reload + 1f;

            shoot.shots = 1;
            heatColor = Color.red;

            for(int i = 0; i < 5; i++){
                int fi = i;
                parts.add(new RegionPart("-blade"){{
                    under = true;
                    layerOffset = -0.001f;
                    heatColor = Pal.techBlue;
                    heatProgress = PartProgress.heat.add(0.2f).min(PartProgress.warmup);
                    progress = PartProgress.warmup.blend(PartProgress.reload, 0.1f);
                    x = 13.5f / 4f;
                    y = 10f / 4f - fi * 2f;
                    moveY = 1f - fi * 1f;
                    moveX = fi * 0.3f;
                    moveRot = -45f - fi * 17f;

                    moves.add(new PartMove(PartProgress.reload.inv().mul(1.8f).inv().curve(fi / 5f, 0.2f), 0f, 0f, 36f));
                }});
            }

            bullet = new ArtilleryBulletType(5.5f, 260){{
                collidesTiles = collides = true;
                lifetime = 70f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareBig;
                frontColor = Color.white;
                trailEffect = new MultiEffect(Fx.artilleryTrail, Fx.artilleryTrailSmoke);
                hitSound = Sounds.none;
                width = 18f;
                height = 24f;

                lightColor = trailColor = hitColor = backColor = Pal.techBlue;
                lightRadius = 40f;
                lightOpacity = 0.7f;

                trailWidth = 4.5f;
                trailLength = 19;
                trailChance = -1f;

                despawnEffect = Fx.none;
                despawnSound = Sounds.dullExplosion;

                hitEffect = despawnEffect = new ExplosionEffect(){{
                    lifetime = 34f;
                    waveStroke = 4f;
                    waveColor = sparkColor = trailColor;
                    waveRad = 25f;
                    smokeSize = 0f;
                    smokeSizeBase = 0f;
                    sparks = 10;
                    sparkRad = 25f;
                    sparkLen = 8f;
                    sparkStroke = 3f;
                }};

                splashDamage = 85f;
                splashDamageRadius = 20f;

                fragBullets = 15;
                fragVelocityMin = 0.5f;
                fragRandomSpread = 130f;
                fragLifeMin = 0.3f;
                despawnShake = 5f;

                fragBullet = new BasicBulletType(5.5f, 50){{
                    pierceCap = 2;
                    pierceBuilding = true;

                    homingPower = 0.09f;
                    homingRange = 150f;

                    lifetime = 50f;
                    shootEffect = Fx.shootBigColor;
                    smokeEffect = Fx.shootSmokeSquareBig;
                    frontColor = Color.white;
                    hitSound = Sounds.none;
                    width = 12f;
                    height = 20f;

                    lightColor = trailColor = hitColor = backColor = Pal.techBlue;
                    lightRadius = 40f;
                    lightOpacity = 0.7f;

                    trailWidth = 2.2f;
                    trailLength = 7;
                    trailChance = -1f;

                    collidesAir = false;

                    despawnEffect = Fx.none;
                    splashDamage = 46f;
                    splashDamageRadius = 30f;

                    hitEffect = despawnEffect = new MultiEffect(new ExplosionEffect(){{
                        lifetime = 30f;
                        waveStroke = 2f;
                        waveColor = sparkColor = trailColor;
                        waveRad = 5f;
                        smokeSize = 0f;
                        smokeSizeBase = 0f;
                        sparks = 5;
                        sparkRad = 20f;
                        sparkLen = 6f;
                        sparkStroke = 2f;
                    }}, Fx.blastExplosion);
                }};
            }};
        }});
        coreUnitweapons.add(collarisWeapons);

        Seq<Weapon> eludeWeapons = new Seq<>(); //60
        eludeWeapons.add(new Weapon("elude-weapon"){{
            shootSound = Sounds.blaster;
            y = -2f;
            x = 4f;
            top = true;
            mirror = true;
            reload = 40f;
            baseRotation = -35f;
            shootCone = 360f;

            shoot = new ShootSpread(2, 11f);

            bullet = new BasicBulletType(5f, 16){{
                homingPower = 0.19f;
                homingDelay = 4f;
                width = 7f;
                height = 12f;
                lifetime = 30f;
                shootEffect = Fx.sparkShoot;
                smokeEffect = Fx.shootBigSmoke;
                hitColor = backColor = trailColor = Pal.suppress;
                frontColor = Color.white;
                trailWidth = 1.5f;
                trailLength = 5;
                hitEffect = despawnEffect = Fx.hitBulletColor;
            }};
        }});
        coreUnitweapons.add(eludeWeapons);

        Seq<Weapon> avertWeapons = new Seq<>(); //61
        avertWeapons.add(new Weapon("avert-weapon"){{
            shootSound = Sounds.blaster;
            reload = 35f;
            x = 0f;
            y = 5.5f;
            shootY = 5f;
            recoil = 1f;
            top = false;
            layerOffset = -0.01f;
            rotate = false;
            mirror = false;
            shoot = new ShootHelix();

            bullet = new BasicBulletType(5f, 34){{
                width = 7f;
                height = 12f;
                lifetime = 18f;
                shootEffect = Fx.sparkShoot;
                smokeEffect = Fx.shootBigSmoke;
                hitColor = backColor = trailColor = Pal.suppress;
                frontColor = Color.white;
                trailWidth = 1.5f;
                trailLength = 5;
                hitEffect = despawnEffect = Fx.hitBulletColor;
            }};
        }});
        coreUnitweapons.add(avertWeapons);

        Seq<Weapon> obviateWeapons = new Seq<>(); //62
        obviateWeapons.add(new Weapon(){{
            shootSound = Sounds.shockBlast;
            x = 0f;
            y = -2f;
            shootY = 0f;
            reload = 140f;
            mirror = false;
            minWarmup = 0.95f;
            shake = 3f;
            cooldownTime = reload - 10f;

            bullet = new BasicBulletType(){{
                shoot = new ShootHelix(){{
                    mag = 1f;
                    scl = 5f;
                }};

                shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                    colorTo = Pal.sapBulletBack;
                    sizeTo = 26f;
                    lifetime = 14f;
                    strokeFrom = 4f;
                }});
                smokeEffect = Fx.shootSmokeTitan;
                hitColor = Pal.sapBullet;
                despawnSound = Sounds.spark;

                sprite = "large-orb";
                trailEffect = Fx.missileTrail;
                trailInterval = 3f;
                trailParam = 4f;
                speed = 3f;
                damage = 75f;
                lifetime = 60f;
                width = height = 15f;
                backColor = Pal.sapBulletBack;
                frontColor = Pal.sapBullet;
                shrinkX = shrinkY = 0f;
                trailColor = Pal.sapBulletBack;
                trailLength = 12;
                trailWidth = 2.2f;
                despawnEffect = hitEffect = new ExplosionEffect(){{
                    waveColor = Pal.sapBullet;
                    smokeColor = Color.gray;
                    sparkColor = Pal.sap;
                    waveStroke = 4f;
                    waveRad = 40f;
                }};

                intervalBullet = new LightningBulletType(){{
                    damage = 16;
                    collidesAir = false;
                    ammoMultiplier = 1f;
                    lightningColor = Pal.sapBullet;
                    lightningLength = 3;
                    lightningLengthRand = 6;

                    //for visual stats only.
                    buildingDamageMultiplier = 0.25f;

                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.25f;
                    }};
                }};

                bulletInterval = 4f;

                lightningColor = Pal.sapBullet;
                lightningDamage = 17;
                lightning = 8;
                lightningLength = 2;
                lightningLengthRand = 8;
            }};

        }});
        coreUnitweapons.add(obviateWeapons);


        return coreUnitweapons;
    }
}
