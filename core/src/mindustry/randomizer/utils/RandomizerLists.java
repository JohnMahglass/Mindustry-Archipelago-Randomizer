package mindustry.randomizer.utils;

import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.type.Weapon;
import java.util.ArrayList;


/**
 * Contains useful static list that can be used for randomization.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-30
 */
public abstract class RandomizerLists { // Should probably find a better name for the class

    /**
     * Get every possible core units weapons even if they are not balanced.
     * @return Return an array of weapon.
     */
    public static ArrayList<Weapon> getPossibleCoreUnitsWeapons(){
        // For some reason (I have no idea) weapon are "combined?" after the game is opened for a
        // second time when the player equips a different weapon during runtime.
        // This is why a restart of the game is required.
        ArrayList<Weapon> coreUnitweapons = new ArrayList<>();

        coreUnitweapons.add(new Weapon("large-weapon"){{
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

        coreUnitweapons.add(new Weapon("flamethrower"){{
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




        return coreUnitweapons;
    }
}
