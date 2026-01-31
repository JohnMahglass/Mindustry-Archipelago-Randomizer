package mindustry.randomizer.constant;

import mindustry.randomizer.enums.ApChatColors;
import mindustry.randomizer.utils.ChatColor;

public class RandomizerConstant {
    private RandomizerConstant() {};

    // Planet
    public static final String SERPULO = "Serpulo";
    public static final String EREKIR = "Erekir";

    // Sector
    public static final int AEGIS_ID = 88;
    public static final int FROZEN_FOREST_ID = 86;

    // TechTree
    public static final String UNLOCK_PARENT_OBJECTIVE = "Unlock parent node";

    // Items
    public static final String FISTFUL_OF_NOTHING = "A fistful of nothing...";

    // Archipelago client
    public static final String SENDING_PENDING_CHECK = "Reconnected, sending pending check...";
    public static final String PENDING_CHECK_SENT = "All pending check has been sent!";
    public static final String PENDING_CHECK_REMAINING = "Pending check remaining. You can try to send the checks again by reconnecting to Archipelago.";
    public static final String ALL_GOAL_MET = "All goals has been met!";
    public static final String GOAL_NOT_MET = "Victory condition has not been met.";
    public static final String CONNECT = "Connect";
    public static final String CONNECTED = "Connected";
    public static final String NOT_CONNECTED = "Not connected";
    public static final String DISCONNECT = "Disconnect";
    public static final String DISCONNECTED = "Disconnected / Connection lost";
    public static final String INVALID_SLOT_NAME = "Invalid slot name";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String SLOT_ALREADY_TAKEN = "Slot already taken";
    public static final String INCOMPATIBLE_VERSION = "Incompatible version";
    public static final String ALREADY_CONNECTED = "You are already connected.";
    public static final String DISCONNECT_WHEN_NOT_CONNECTED = "You are not connected to any game.";
    public static final String CANT_SEND_MSG_NOT_CONNECTED = "You are not connected, message cannot be sent.";
    public static final String DISCONNECTED_INFO = " Offline checks will be saved and sent when connecting to the game again.";
    public static final String NAME_OR_SLOT_EMPTY = "Address or Slot name empty.";
    public static final String CONNETION_FAILED = "Connection failed. Please verify your login information in Settings -> Archipelago";

    // AP Chat
    public static final String TITLE_APPLYING_RANDOMIZER_OPTION_RESTART = "Restart to apply randomizer option";
    public static final String APPLYING_RANDOMIZER_OPTION_RESTART = "To properly apply the randomizer options it is required\nto reload the game when you successfully connect to\na game for the first time.\n\nPress 'Exit' to close the program.";

    // Chat commands
    public static final String CONNECT_COMMAND = "connect";
    public static final String DISCONNECT_COMMAND = "disconnect";
    public static final String STATUS_COMMAND = "status";
    public static final String OPTIONS_COMMAND = "options";
    public static final String HELP_COMMAND = "help";
    public static final String CLEAR_COMMAND = "clear";
    public static final String DEV_COMMAND = "dev";
    public static final String UNKNOWN_COMMAND = "Unknown command. Use '/help' for command usage";

    // Death Link
    public static final String DEATH_LINK_TRIGGERED_BY = "Death link triggered by ";
    public static final String RECEIVING_DEATH_LINK_ERROR = "Error Receiving DeathLink, possible malformed bounce packet";
    public static final String INVALID_DEATH_LINK_MODE = "Invalid death link mode";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_DEFLECTED = "But your battle-hardened core " + ChatColor.applyColor(ApChatColors.GOLD, "deflected") +  "the bullet!";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_TRIGGERED = "=== Archipelago Death Link Gun™ triggered ===";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_LIVE_SHOT = "BANG! The Archipelago Death Link Gun™ has obliterated your core!";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_RELOADING = "No more ammo in the Archipelago Death Link Gun™. Reloading... ";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_AMMO_AMOUNT_ERROR = "Invalid ammo amount inside the Archipelago Death Link Gun™. Reloading...";
    public static final String DEATH_LINK_RUSSIAN_ROULETTE_END = "===      Archipelago Death Link Gun™ end      ===";

    // Archipelago Dialog
    public static final String EXIT = "Exit";
    public static final String ARCHIPELAGO = "Archipelago";
    public static final String CONNECTION_OPTIONS = "Connection options";
    public static final String ADDRESS_LABEL = "Address: ";
    public static final String ADDRESS_NOT_SET = "Address not set";
    public static final String PLAYER_NAME_LABEL = "Player name: ";
    public static final String PLAYER_NAME_NOT_SET = "Name not set";
    public static final String PASSWORD_LABEL = "Password: ";
    public static final String PASSWORD_NOT_SET = "Password not set";
    public static final String CONNECTION_STATUS_LABEL = "Connection status: ";
    public static final String NEW_ADDRESS_LABEL = "New Address: ";
    public static final String NEW_SLOT_NAME_LABEL = "New SlotName: ";
    public static final String NEW_PASSWORD_LABEL = "New Password: ";
    public static final String DEATH_LINK_OPTION = "Death link options";
    public static final String FORCE_DISABLE_DEATH_LINK = "Force disable death link";
    public static final String FORCE_DISABLE_DEATH_LINK_TOOLTIP = "Disable death link even if it was choosen for the game generation.";
    public static final String PROTECT_CAPTURED_SECTOR = "Protect captured sector";
    public static final String PROTECT_CAPTURED_SECTOR_TOOLTIP = "Death link signal will be ignored when playing in a captured sector";
    public static final String CHAT_OPTIONS = "Chat options";
    public static final String DISABLE_CHAT = "Disable chat";
    public static final String DISABLE_CHAT_TOOLTIP = "Will not display any chat message from Archipelago";
    public static final String SELF_ITEM_ONLY = "Self item only";
    public static final String SELF_ITEM_ONLY_TOOLTIP = "Only display item messages related to self in chat";
    public static final String RESET_AP_DATA = "Reset AP data";
    public static final String RESET_AP_DATA_WARNING = "Wipe campaign/research/saves and Archipelago related data and force exit the program.\n\nTo be used when you want to reset data and play a new multiworld.";
    public static final String APPLY_CHANGES = "Apply changes";
    public static final String MANUALLY_VALIDATE_VICTORY = "Manually validate victory";

    // Error
    public static final String NULL_CONTENT_UNLOCK_ERROR = "Content that was null was called for unlock.";
    public static final String NOT_CONNECTED_PENDING_CHECKS = "You are not connected, pending checks will be sent when reconnecting to the game.";
    public static final String NOT_CONNECTED_GOAL_COMPLETED = "Goal completed, but you are not connected. Goal has not been sent to Archipelago. Once you are reconnected, you can force check the victory condition by going into Settings -> Archipelago and using the 'Manually verify victory condition' button.";
    public static final String INVALID_CAMPAIGN_TYPE = "Invalid campaign type";
    public static final String OPTION_NOT_FILLED = "Options was not filled, cannot apply options";
    public static final String INVALID_ITEM_TO_BE_ADDED_TO_UNLOCK_LIST = "Invalid item was called for unlock (Invalid ID)";
    public static final String UNKNOWN_ERROR = "Unknown error";
    public static final String NULL_LOCATION_ID = "Location ID is null";


} 