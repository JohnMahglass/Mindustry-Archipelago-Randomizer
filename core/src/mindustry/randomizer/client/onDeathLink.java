package mindustry.randomizer.client;

import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.DeathLinkEvent;
import mindustry.Vars;

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
            if (event.cause != null && !event.cause.isBlank()) {
                Vars.randomizer.sendLocalMessage(event.cause);
            } else {
                Vars.randomizer.sendLocalMessage("Death link triggered by " + event.source);
            }
            //kill the player
        }
    }
}
