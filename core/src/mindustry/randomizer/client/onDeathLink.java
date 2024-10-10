package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.DeathLinkEvent;
import mindustry.Vars;
import mindustry.randomizer.enums.ApChatColors;
import mindustry.randomizer.utils.ChatColor;
import mindustry.randomizer.utils.RandomizerMessageHandler;

import java.util.Random;

import static mindustry.Vars.player;
import static mindustry.Vars.randomizer;
import static arc.Core.settings;
import static mindustry.Vars.state;
import static mindustry.randomizer.enums.SettingStrings.*;

/**
 * Called when receiving a death link event from the server.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-02
 */
public class onDeathLink {
    @ArchipelagoEventListener
    public static void onBounce(DeathLinkEvent event) {
        if (randomizer.worldState.options.getDeathLink()) {
            if (event.cause != null && !event.cause.isEmpty()) {
                randomizer.sendLocalMessage(event.cause);
            } else {
                RandomizerMessageHandler.printDeathLinkBounce(event.source);
            }
            processDeathLinkBounce(randomizer.worldState.options.getDeathLinkMode());
        }
    }

    private static void processDeathLinkBounce(int deathLinkMode) {
        switch (deathLinkMode) {
            case 0:
                if (state.isCampaign() && !Vars.player.unit().dead) {
                    randomizer.worldState.deathLinkDying = true;
                    Vars.player.unit().kill();
                }
                break;
            case 1:
                if (state.isCampaign()) {
                    destroyPlayerCores();
                }
                break;
            case 2:
                if (state.isCampaign()) {
                    fireDeathLinkGun();
                }
                break;
            default:
                RandomizerMessageHandler.printErrorWithReason("Invalid death link mode.");
                break;
        }
    }

    /**
     * Detroy every core owned by the player.
     */
    private static void destroyPlayerCores() {
        if (!state.getSector().isCaptured()) {
            randomizer.worldState.deathLinkDying = true;
            for(var core : player.team().cores().copy()){
                core.kill();
            }
        } else if (!settings.getBool(AP_DEATH_LINK_PROTECT_CAPTURED_SECTOR.value)) {
            randomizer.worldState.deathLinkDying = true;
            for(var core : player.team().cores().copy()){
                core.kill();
            }
        } else {
            randomizer.sendLocalMessage("But your battle-hardened core " + ChatColor.applyColor(ApChatColors.GOLD, "deflected") +
                    " the bullet!");
        }

    }

    /**
     * Fire the Archipelago Death Link Gun™. Has a chance to fire a live ammo.
     */
    private static void fireDeathLinkGun() {
        int chamberSize = randomizer.worldState.options.getCoreRussianRouletteChambers();
        randomizer.sendLocalMessage(ChatColor.applyColor(ApChatColors.RED, "=== Archipelago Death" +
                " Link Gun™ triggered ==="));

        int ammoInChamber = settings.getInt(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value);
        if (ammoInChamber >= 1 && ammoInChamber <= chamberSize) { //Valid amount of ammo
            Random random = new Random();
            int fireAttempt = random.nextInt(ammoInChamber);
            if (fireAttempt == 0) { //Live shot
                randomizer.sendLocalMessage("BANG! The Archipelago Death Link Gun™ has " +
                        "obliterated your core!");
                settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, chamberSize);
                destroyPlayerCores();
            } else { //Blank shot
                randomizer.sendLocalMessage("The Archipelago Death Link Gun™ fired a blank shot. " +
                        "1/" + (settings.getInt(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value) - 1) +
                        " ammo remaining.");
                ammoInChamber -= 1; //Remove ammo from chamber
                settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, ammoInChamber);
            }
            if (ammoInChamber == 0) { //Gun never fired a live shot.
                RandomizerMessageHandler.printErrorWithReason("No more ammo in the Archipelago " +
                        "Death Link Gun™. Reloading... ");
                settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, chamberSize);
            }
        } else { //Invalid amount of ammo.
            RandomizerMessageHandler.printErrorWithReason("Invalid ammo amount inside the " +
                    "Archipelago Death Link Gun™. Reloading...");
            settings.put(AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value, chamberSize);
        }

        randomizer.sendLocalMessage(ChatColor.applyColor(ApChatColors.RED, "===      Archipelago " +
                "Death Link Gun™ end      ==="));
    }

}
