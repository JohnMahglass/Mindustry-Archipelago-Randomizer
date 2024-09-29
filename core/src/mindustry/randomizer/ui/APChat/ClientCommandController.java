package mindustry.randomizer.ui.APChat;

import arc.Core;
import mindustry.randomizer.enums.SettingStrings;
import mindustry.randomizer.utils.ChatColor;

import static mindustry.Vars.randomizer;
import static mindustry.randomizer.enums.ApChatColors.*;

/**
 * Manage chat client commands.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-08-25
 */
public class ClientCommandController {

    private APChatFragment chat;

    public ClientCommandController(APChatFragment chat){
        this.chat = chat;
    }

    /**
     * Return if the player's message is a client command.
     * @param message The player's message.
     * @return True if the message is a command.
     */
    protected static boolean isCommand(String message) {
        return message.startsWith("/");
    }

    /**
     * Execute a command sent by the player
     * @param message The message containing the command.
     */
    protected void executeCommand(String message) {
        String command = message;
        command = command.substring(1); //Remove the '/'
        String[] commandParts = command.split(" ");
        command = commandParts[0];
        command = command.toLowerCase();
        boolean connectionOpen = randomizer.client.isConnected();
        switch (command) {
            case "connect":
                if (!connectionOpen) {
                    executeConnectCommand(commandParts);
                } else {
                    chat.addLocalMessage(new APMessage("You are already connected."));
                }
                break;
            case "disconnect":
                if (connectionOpen) {
                    executeDisconnectCommand(commandParts);
                } else {
                    chat.addLocalMessage(new APMessage("You are not connected to any game."));
                }
                break;
            case "status":
                executeStatusCommand(commandParts);
                break;
            case "options":
                executeOptionsCommand(commandParts);
                break;
            case "help":
                listAvailableCommands();
                break;
            case "clear":
                executeClearCommand(commandParts);
                break;
            case "dev":
                executeDevCommand(commandParts);
                break;
            default:
                chat.addLocalMessage(new APMessage("Unknown command. Use '/help' for command " +
                        "usage."));
                break;
        }
    }

    private void executeDevCommand(String[] commandParts) {
        if (commandParts.length > 1) {
            tooManyArgumentMessage();
            return;
        }
        randomizer.sendLocalMessage("-----EXECUTE DEV COMMAND-----");
        randomizer.debug = true;
        randomizer.sendLocalMessage("---Debug mode activated---");
        randomizer.client.setSlotName("Dev");
        randomizer.client.setAddress("localhost:38281");
        randomizer.client.connectRandomizer();
    }

    private void executeClearCommand(String[] commandParts) {
        if (commandParts.length > 1) {
            tooManyArgumentMessage();
            return;
        }
        chat.clearMessages();
    }

    private void executeOptionsCommand(String[] commandParts) {
        if (commandParts.length > 1) {
            tooManyArgumentMessage();
            return;
        }
        if (randomizer.worldState.options.getOptionsFilled()) {
            chat.addLocalMessage(new APMessage("Options:\n" +
                    "   Selected campaign: " + getCampaignName() + "\n" +
                    "   Tutorial skip: " + getActivationStatus(randomizer.worldState.options.getTutorialSkip()) + "\n" +
                    "   Disable invasions: " + getActivationStatus(randomizer.worldState.options.getDisableInvasions()) + "\n" +
                    "   Faster production: " + getActivationStatus(randomizer.worldState.options.getFasterProduction()) + "\n" +
                    "   Death link: " + getActivationStatus(randomizer.worldState.options.getDeathLink()) + "\n" +
                    "   Death link mode: " + getDeathLinkModeText() + "\n" +
                    "   Seed: " + randomizer.worldState.getSeed() + "\n" +
                    "   Randomize core units weapon: " + getActivationStatus(randomizer.worldState.options.getRandomizeCoreUnitsWeapon()) + "\n" +
                    "   Logistic Distribution: " + getLogisticDistributionValue(randomizer.worldState.options.getLogisticDistribution()) + "\n" +
                    "   Make early roadblocks local: " + getActivationStatus(randomizer.worldState.options.getMakeEarlyRoadblocksLocal())));
        } else {
            chat.addLocalMessage(new APMessage("You must connect to a game once to view .yaml " +
                    "options."));
        }
    }

    private String getDeathLinkModeText() {
        int mode = randomizer.worldState.options.getDeathLinkMode();
        String modeText;
        switch (mode) {
            case 0:
                modeText = "Unit";
                break;
            case 1:
                modeText = "Core";
                break;
            case 2:
                int ammo = Core.settings.getInt(SettingStrings.AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value);
                modeText = "Core russian roulette. 1/" + ammo + " ammo left.";
                break;
            default:
                modeText = "Invalid mode";
                break;
        }
        return modeText;
    }

    /**
     * Return the selected option in text format
     * @param logisticOption The selected option
     * @return The selected option in text format.
     */
    private String getLogisticDistributionValue(int logisticOption) {
        String text = "Error";
        switch (logisticOption){
            case 0: // Randomized logistics
                text = "Randomized logistics";
                break;
            case 1: // Early logistics
                text = "Early logistics";
                break;
            case 2: // Local early logistics
                text = "Local early logistics";
                break;
            case 3: //Starter logistics
                text = "Starter logistics";
                break;
        }
        return text;
    }

    /**
     * Return if the option was activated.
     * @param status The status of the option
     * @return Return the status of the option.
     */
    private String getActivationStatus(boolean status) {
        return status ? ChatColor.applyColor(GREEN, "Activated") : ChatColor.applyColor(RED, "Deactivated");
    }

    /**
     * Return the selected campaign name.
     * @return The campaign name.
     */
    private String getCampaignName() {
        String name;
        int campaign = randomizer.worldState.options.getCampaign();
        if (campaign == 0) { //Serpulo
            name = ChatColor.applyColor(SERPULO, "Serpulo");
        } else if (campaign == 1) { //Erekir
            name = ChatColor.applyColor(EREKIR, "Erekir");
        } else if (campaign == 2) { //All
            name = ChatColor.applyColor(SERPULO, "Serpulo") + " and " + ChatColor.applyColor(EREKIR, "Erekir");
        } else {
            name = "Campaign name error";
        }
        return name;
    }

    /**
     * Execute the status command.
     * @param commandParts The command and its argument split into parts.
     */
    private void executeStatusCommand(String[] commandParts) {
        if (commandParts.length > 1) { //Wrong number of argument for status command.
            tooManyArgumentMessage();
            return;
        }
        String status;
        switch (randomizer.client.connectionStatus) {
            case Success:
                status = ChatColor.applyColor(GREEN, "Connected");
                break;
            case NotConnected:
                status = ChatColor.applyColor(RED, "Not connected");
                break;
            case InvalidSlot:
                status = ChatColor.applyColor(RED, "Invalid slot name");
                break;
            case InvalidPassword:
                status = ChatColor.applyColor(RED, "Invalid password");
                break;
            case SlotAlreadyTaken:
                status = ChatColor.applyColor(RED, "Slot already taken");
                break;
            case IncompatibleVersion:
                status = ChatColor.applyColor(RED, "Incompatible version");
                break;
            default:
                status = ChatColor.applyColor(RED, "Unknown error");
                break;
        }
        chat.addLocalMessage(new APMessage("Connection status: " + status));
    }

    /**
     * Disconnect the player from the server.
     * @param commandParts The command and its arguments split into parts.
     */
    private void executeDisconnectCommand(String[] commandParts) {
        if (commandParts.length > 1) { //Wrong number of argument for disconnect command.
            tooManyArgumentMessage();
            return;
        }
        randomizer.client.disconnect();
    }

    /**
     * Connect the player to the AP server. Change connection settings if there are argument.
     * @param commandParts The commands and its arguments split into parts.
     */
    private void executeConnectCommand(String[] commandParts) {
        if (commandParts.length > 3) { //Wrong number of argument for connect command.
            tooManyArgumentMessage();
            return;
        }
        if (commandParts.length > 1) {
            randomizer.client.setAddress(commandParts[1]);
        }
        if (commandParts.length > 2) {
            randomizer.client.setSlotName(commandParts[2]);
        }
        randomizer.client.connectRandomizer();
    }

    /**
     * Inform the player that they used too many argument for their command.
     */
    private void tooManyArgumentMessage() {
        chat.addLocalMessage(new APMessage("Too many argument. Use '/help' for command usage."));
    }

    /**
     * List in the player's chat all available commands for the client.
     */
    private void listAvailableCommands() {
        chat.addLocalMessage(new APMessage("""
                Available commands:
                  [#FFDF29]/help[#FFFFFF]
                        List available commands. (what you are doing right now)
                  [#FFDF29]/status[#FFFFFF]
                        Display connection status.
                  [#FFDF29]/options[#FFFFFF]
                        Display selected options for game generation.
                        You need to have connected once to be able
                        to view selected options
                  [#FFDF29]/connect[#FFFFFF]
                        Connect using the information provided in
                        Settings -> Archipelago
                  [#FFDF29]/connect[#FFFFFF] [Address] [Slot Name]
                        Connect using the information provided in argument.
                        (Password not available to prevent displaying password)
                  [#FFDF29]/disconnect[#FFFFFF]
                        Disconnect from AP
                  [#FFDF29]/clear[#FFFFFF]
                        Clear chat messages."""));
    }
}
