package mindustry.randomizer.ui.APChat;

import arc.Core;
import mindustry.randomizer.enums.ArchipelagoGoal;
import mindustry.randomizer.enums.CampaignType;
import mindustry.randomizer.enums.DeathLinkMode;
import mindustry.randomizer.enums.LogisticsDistribution;
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

    /**
     * Execute commands meant for development.
     */
    private void executeDevCommand(String[] commandParts) {
        if (commandParts.length > 2) {
            tooManyArgumentMessage();
        } else {
            if (commandParts.length == 2 && commandParts[1].equals("c")) {
                randomizer.client.setSlotName("Dev");
                randomizer.client.setAddress("localhost:38281");
                randomizer.client.connectRandomizer();
            }
            randomizer.sendLocalMessage("-----EXECUTE DEV COMMAND-----");
            randomizer.debug = true;
            randomizer.sendLocalMessage("---Debug mode activated---");
        }

    }

    /**
     * Remove all messages from the chat.
     */
    private void executeClearCommand(String[] commandParts) {
        if (commandParts.length > 1) {
            tooManyArgumentMessage();
            return;
        }
        chat.clearMessages();
    }

    /**
     * Display the players options in chat.
     */
    private void executeOptionsCommand(String[] commandParts) {
        if (commandParts.length > 2) {
            tooManyArgumentMessage();
            return;
        }
        else if (commandParts.length == 2 && commandParts[1].equals("f")) {
            if (randomizer.worldState.options.getOptionsFilled()) {
                chat.addLocalMessage(new APMessage("Options:\n" +
                        getCampaignOptionText()+
                        getGoalOptionText() +
                        getAmountofResourcesRequiredOptionText() +
                        getTutorialSkipOptionText() +
                        getDisableInvasionsOptionText() +
                        getFasterProductionOptionText() +
                        getFasterConveyorOptionText() +
                        getDeathLinkOptionText() +
                        getDeathLinkModeOptionText() +
                        getCoreRussianRouletteSizeOptionText() +
                        getSeedText() +
                        getRandomizeCoreUnitsWeaponOptionText() +
                        getLogisiticDistributionOptionText() +
                        getLocalEarlyRoadblocksOptionText() +
                        getProgressiveDrillsOptionText() +
                        getProgressiveGeneratorsOptionText()
                        ));
            } else {
                chat.addLocalMessage(new APMessage("You must connect to a game once to view .yaml " +
                        "options."));
            }
        } else if(commandParts.length == 1){
            if (randomizer.worldState.options.getOptionsFilled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Options:\n");
                sb.append(getCampaignOptionText());
                sb.append(getGoalOptionText());
                if (randomizer.worldState.options.getGoal() == ArchipelagoGoal.RESOURCES) {
                    sb.append(getAmountofResourcesRequiredOptionText());
                }
                sb.append(getTutorialSkipOptionText());
                if(randomizer.worldState.options.getCampaign() == CampaignType.SERPULO ||
                        randomizer.worldState.options.getCampaign() == CampaignType.ALL) { // Serpulo included in chosen campaign
                    sb.append(getDisableInvasionsOptionText());
                }
                sb.append(getFasterProductionOptionText());
                sb.append(getFasterConveyorOptionText());
                sb.append(getDeathLinkOptionText());
                if(randomizer.worldState.options.getTrueDeathLink()){
                    sb.append(getDeathLinkModeOptionText());
                    if (randomizer.worldState.options.getDeathLinkMode() == DeathLinkMode.CORE_RUSSIAN_ROULETTE) {
                        sb.append(getCoreRussianRouletteSizeOptionText());
                    }
                }
                sb.append(getRandomizeCoreUnitsWeaponOptionText());
                sb.append(getLogisiticDistributionOptionText());
                sb.append(getLocalEarlyRoadblocksOptionText());
                sb.append(getProgressiveDrillsOptionText());
                sb.append(getProgressiveGeneratorsOptionText());

                chat.addLocalMessage(new APMessage(sb.toString()));
            } else {
                chat.addLocalMessage(new APMessage("You must connect to a game once to view .yaml " +
                        "options."));
            }
        }

    }

    private String getProgressiveGeneratorsOptionText() {
        return "   Progressive Generators: " + getActivationStatus(randomizer.worldState.options.getProgressiveGenerators()) + "\n";
    }

    private String getProgressiveDrillsOptionText() {
        return "   Progressive Drills: " + getActivationStatus(randomizer.worldState.options.getProgressiveDrills()) + "\n";
    }

    private String getLocalEarlyRoadblocksOptionText() {
        return "   Make early roadblocks local: " + getActivationStatus(randomizer.worldState.options.getMakeEarlyRoadblocksLocal()) + "\n";
    }

    private String getLogisiticDistributionOptionText() {
        return "   Logistic Distribution: " + getLogisticDistributionValue(randomizer.worldState.options.getLogisticDistribution()) + "\n";
    }

    private String getRandomizeCoreUnitsWeaponOptionText() {
        return "   Randomize core units weapon: " + getActivationStatus(randomizer.worldState.options.getRandomizeCoreUnitsWeapon()) + "\n";
    }

    private String getSeedText() {
        return "   Seed: " + randomizer.worldState.getSeed() + "\n";
    }

    private String getDeathLinkModeOptionText() {
        return "   Death link mode: " + getDeathLinkModeText() + "\n";
    }

    private String getDeathLinkOptionText() {
        return "   Death link: " + getActivationStatus(randomizer.worldState.options.getDeathLink()) + "\n";
    }

    private String getFasterProductionOptionText() {
        return "   Faster production: " + getActivationStatus(randomizer.worldState.options.getFasterProduction()) + "\n";
    }

    private String getFasterConveyorOptionText() {
        return "   Faster conveyor: " + getActivationStatus(randomizer.worldState.options.getFasterConveyor()) + "\n";
    }

    private String getDisableInvasionsOptionText() {
        return "   Disable invasions: " + getActivationStatus(randomizer.worldState.options.getDisableInvasions()) + "\n";
    }

    private String getTutorialSkipOptionText() {
        return "   Tutorial skip: " + getActivationStatus(randomizer.worldState.options.getTutorialSkip()) + "\n";
    }

    private String getCoreRussianRouletteSizeOptionText(){
        return  "   Core russian roulette chambers size: " + randomizer.worldState.options.getCoreRussianRouletteChambers() + "\n";
    }

    private String getDeathLinkModeText() {
        String modeText;
        switch (randomizer.worldState.options.getDeathLinkMode()) {
            case UNIT:
                modeText = "Unit";
                break;
            case CORE:
                modeText = "Core";
                break;
            case CORE_RUSSIAN_ROULETTE:
                int ammo =
                        Core.settings.getInt(SettingStrings.AP_DEATH_LINK_RUSSIAN_ROULETTE_AMMO.value); //TODO make a method to fetch this value in MindustryOption
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
    private String getLogisticDistributionValue(LogisticsDistribution logisticOption) {
        String text = "Error";
        switch (logisticOption){
            case RANDOMIZED:
                text = "Randomized logistics";
                break;
            case EARLY:
                text = "Early logistics";
                break;
            case LOCAL_EARLY:
                text = "Local early logistics";
                break;
            case STARTER:
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
    private String getCampaignOptionText() {
        String name;
        CampaignType campaign = randomizer.worldState.options.getCampaign();
        if (campaign == CampaignType.SERPULO) {
            name = ChatColor.applyColor(SERPULO, "Serpulo");
        } else if (campaign == CampaignType.EREKIR) {
            name = ChatColor.applyColor(EREKIR, "Erekir");
        } else if (campaign == CampaignType.ALL) {
            name = ChatColor.applyColor(SERPULO, "Serpulo") + " and " + ChatColor.applyColor(EREKIR, "Erekir");
        } else {
            name = "Campaign name error";
        }
        return "   Selected campaign: " + name + "\n";
    }

    /**
     * Return the goal name
     * @return The goal name
     */
    private String getGoalOptionText(){
        String name;
        ArchipelagoGoal goal = randomizer.worldState.options.getGoal();
        if (goal == ArchipelagoGoal.RESOURCES) {
            name = ChatColor.applyColor(GOLD, "Recources");
        } else if (goal == ArchipelagoGoal.CONQUEST) {
            name = ChatColor.applyColor(GOLD, "Conquest");
        } else {
            name = "Goal name error";
        }
        return "   Goal: " + name + "\n";
    }

    private String getAmountofResourcesRequiredOptionText() {
        return "   Amount of resources required: " + randomizer.worldState.options.getAmountOfResourcesRequired() + "\n";
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
                        Display relevant options for game generation.
                        You need to have connected once to be able
                        to view selected options.
                  [#FFDF29]/options[#FFFFFF] [f]
                        Display every selected options for game generation.
                        You need to have connected once to be able
                        to view selected options.
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
