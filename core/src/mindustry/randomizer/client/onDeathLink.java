package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.DeathLinkEvent;
import mindustry.Vars;
import mindustry.randomizer.utils.RandomizerMessageHandler;

/**
 * Called when receiving a death link event from the server.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-02
 */
public class onDeathLink {
    @ArchipelagoEventListener
    public static void onBounce(DeathLinkEvent event) {
        if (Vars.randomizer.worldState.options.getDeathLink()) {
            if (event.cause != null && !event.cause.isEmpty()) {
                Vars.randomizer.sendLocalMessage(event.cause);
            } else {
                RandomizerMessageHandler.printDeathLinkBounce(event.source);
            }
            if (!Vars.player.unit().dead) {
                Vars.randomizer.worldState.deathLinkDying = true;
                Vars.player.unit().kill();
            }
        }
    }
}
