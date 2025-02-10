package mindustry.randomizer.utils;

import mindustry.randomizer.enums.ApChatColors;

import static mindustry.Vars.randomizer;
import static mindustry.randomizer.enums.ApChatColors.*;
/**
 * ErrorMessageHandler
 *
 * @author John Mahglass
 * @version 1.0.0 2024-09-24
 */
public abstract class RandomizerMessageHandler {
    private static final String ERROR = ChatColor.applyColor(RED, "ERROR:");
    private static final String CLIENT_ERROR = ChatColor.applyColor(RED, "CLIENT ERROR:");
    private static final String CONNECTED = ChatColor.applyColor(GREEN, "Connected");
    private static final String CONNETION_FAILED = ChatColor.applyColor(RED, "Connection failed");

    /**
     * Print an error message with the error reason.
     * @param reason The reason there was an error.
     */
    public static void printErrorWithReason(String reason){
        randomizer.sendLocalMessage(ERROR + " " + reason);
    }

    /**
     * Print an error with an unknown reason.
     */
    public static void printUnknownError(){
        randomizer.sendLocalMessage(ERROR + " Unknown error.");
    }

    /**
     * Print an error message originating from the client.
     * @param reason The reason of the error.
     */
    public static void printClientError(String reason){
        randomizer.sendLocalMessage(CLIENT_ERROR + " " + reason);
    }

    /**
     * Print a message notifying the user of the successful connection.
     * @param address The connected adresse.
     */
    public static void printSuccessfulConnection(String address){
        randomizer.sendLocalMessage(CONNECTED + " to " + address);
    }

    /**
     * Print a message notifying the user of the failed connection.
     * @param reason The reason for the error.
     */
    public static void printFailedConnection(String reason){
        randomizer.sendLocalMessage(CONNETION_FAILED + ": " + reason);
    }

    /**
     * Print a message containing death link bounce information.
     * @param source The source of the death link bounce.
     */
    public static void printDeathLinkBounce(String source){
        randomizer.sendLocalMessage("Death link triggered by " + source);
    }

    /**
     * Print a message notifying the player that they have reached their planet goal.
     * @param color The color of the message.
     * @param campaignPlanet The planet the goal was reached.
     */
    public static void printGoalCompleted(ApChatColors color, String campaignPlanet){
        campaignPlanet = ChatColor.applyColor(color, campaignPlanet);
        randomizer.sendLocalMessage(campaignPlanet + " goal has been " + ChatColor.applyColor(GREEN, "completed") + "!");
    }

    /**
     * Print a message notifying the player that they reached all their goal (if they add multiple).
     */
    public static void printAllGoalCompleted(){
        randomizer.sendLocalMessage(ChatColor.applyColor(GOLD, "All goals has been met!"));
    }
}
