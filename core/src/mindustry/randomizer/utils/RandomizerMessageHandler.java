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

    public static void printErrorWithReason(String reason){
        randomizer.sendLocalMessage(ERROR + " " + reason);
    }

    public static void printUnknownError(){
        randomizer.sendLocalMessage(ERROR + " Unknown error.");
    }

    public static void printClientError(String reason){
        randomizer.sendLocalMessage(CLIENT_ERROR + " " + reason);
    }

    public static void printSuccessfulConnection(String address){
        randomizer.sendLocalMessage(CONNECTED + " to " + address);
    }

    public static void printFailedConnection(String reason){
        randomizer.sendLocalMessage(CONNETION_FAILED + ": " + reason);
    }

    public static void printDeathLinkBounce(String source){
        randomizer.sendLocalMessage("Death link triggered by " + source);
    }

    public static void printGoalCompleted(ApChatColors color, String campaignPlanet){
        campaignPlanet = ChatColor.applyColor(color, campaignPlanet);
        randomizer.sendLocalMessage(campaignPlanet + " goal has been " + ChatColor.applyColor(GREEN, "completed") + "!");
    }

    public static void printAllGoalCompleted(){
        randomizer.sendLocalMessage(ChatColor.applyColor(GOLD, "All goals has been met!"));
    }
}
