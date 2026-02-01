package mindustry.randomizer.ui;

import arc.Core;
import arc.graphics.Color;
import arc.math.Rand;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Icon;
import mindustry.randomizer.client.APClient;
import mindustry.randomizer.constant.RandomizerConstant;
import mindustry.randomizer.enums.ConnectionStatus;
import mindustry.randomizer.utils.ChatColor;
import mindustry.ui.dialogs.BaseDialog;

import java.util.Timer;
import java.util.TimerTask;

import static arc.Core.settings;
import static mindustry.Vars.content;
import static mindustry.Vars.control;
import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;
import static mindustry.Vars.universe;
import static mindustry.randomizer.enums.SettingStrings.*;
import static mindustry.randomizer.enums.ApChatColors.*;

/**
 * Dialog for Archipelago settings.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class ArchipelagoDialog extends BaseDialog {
    private APClient client;

    private TextField passwordTextField;

    /**
     * New address when the user is updating the address.
     */
    private String newAddress;

    /**
     * New slot name when the user is updating the slot name.
     */
    private String newSlotName;

    /**
     * New state for disable death link when the user is updating local option.
     */
    private boolean newForceDisableDeathLink;

    private boolean newDeathLinkProtectSector;

    private boolean newDisableChat;

    private boolean newAllowOnlySelfItemMessage;

    /**
     * True if the player has modified the force death link option box.
     */
    private boolean forceDeathLinkChanged;

    private boolean deathLinkProtectedSectorChanged;

    private boolean disableChatChanged;

    private boolean onlySelfItemChanged;

    /**
     * Set to true when a setting has been changed.
     */
    private boolean settingChanged;

    /**
     * Status to be displayed in the Dialog.
     */
    private ConnectionStatus displayedStatus;

    public ArchipelagoDialog() {
        super(RandomizerConstant.ARCHIPELAGO);
        this.client = randomizer.client;
        this.newAddress = null;
        this.newSlotName = null;
        this.settingChanged = false;
        this.newForceDisableDeathLink = false;
        this.forceDeathLinkChanged = false;
        this.newDeathLinkProtectSector = false;
        this.deathLinkProtectedSectorChanged = false;
        this.newDisableChat = false;
        this.newAllowOnlySelfItemMessage = false;
        this.disableChatChanged = false;
        this.onlySelfItemChanged = false;
        setPasswordTextField();
        addCloseButton();
        setup();
    }

    /**
     * Set the textfield for the password to display obfuscated character instead of the password.
     */
    private void setPasswordTextField() {
        this.passwordTextField = new TextField();
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
    }

    private void setup() {
        Timer timerD = new Timer(true);

        Table archipelagoTable = new Table();
        ScrollPane pane = new ScrollPane(archipelagoTable);
        pane.setScrollingDisabled(true, false);

        archipelagoTable.defaults().padLeft(15f).size(280f, 60f).left();

        archipelagoTable.defaults().size(280f, 60f).left();
        archipelagoTable.row();
        archipelagoTable.table(info -> info.add(ChatColor.applyColor(LIGHTGRAY, RandomizerConstant.CONNECTION_OPTIONS)).width(archipelagoTable.getWidth())).get().left();
        archipelagoTable.row();
        archipelagoTable.image().width(archipelagoTable.getWidth()).color(Color.gray).fillX().height(3).pad(6).colspan(4).padTop(0).padBottom(1).row();
        archipelagoTable.add(RandomizerConstant.ADDRESS_LABEL + ((Core.settings.getString(CLIENT_ADDRESS.value) != null) ?
                Core.settings.getString(CLIENT_ADDRESS.value) : RandomizerConstant.ADDRESS_NOT_SET));

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.PLAYER_NAME_LABEL + ((Core.settings.getString(CLIENT_NAME.value) != null ?
                Core.settings.getString(CLIENT_NAME.value) : RandomizerConstant.PLAYER_NAME_NOT_SET)));

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.PASSWORD_LABEL + ((Core.settings.getString(CLIENT_PASSWORD.value) != null ?
                obfuscatePassword() : RandomizerConstant.PASSWORD_NOT_SET)));

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.CONNECTION_STATUS_LABEL + getConnectionStatus());

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.NEW_ADDRESS_LABEL);
        archipelagoTable.field("", text -> {
            newAddress = text;
        }).maxTextLength(100);

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.NEW_SLOT_NAME_LABEL);
        archipelagoTable.field("", text -> {
            newSlotName = text;
        }).maxTextLength(100);

        archipelagoTable.row();
        archipelagoTable.add(RandomizerConstant.NEW_PASSWORD_LABEL);
        archipelagoTable.add(passwordTextField).maxTextLength(100);

        archipelagoTable.row();
        archipelagoTable.table(info -> info.add(ChatColor.applyColor(LIGHTGRAY, RandomizerConstant.DEATH_LINK_OPTION)).width(archipelagoTable.getWidth())).get().left();
        archipelagoTable.row();
        archipelagoTable.image().width(archipelagoTable.getWidth()).color(Color.gray).fillX().height(3).pad(1).colspan(4).padTop(0).padBottom(1).row();
        archipelagoTable.check(RandomizerConstant.FORCE_DISABLE_DEATH_LINK, settings.getBool(FORCE_DISABLE_DEATH_LINK.value),  bool -> {
            newForceDisableDeathLink = bool;
            forceDeathLinkChanged = true;
        }).tooltip(RandomizerConstant.FORCE_DISABLE_DEATH_LINK_TOOLTIP).grow().padBottom(1f).size(320f, 60f).get().align(Align.left);
        archipelagoTable.row();
        archipelagoTable.check(RandomizerConstant.PROTECT_CAPTURED_SECTOR, settings.getBool(AP_DEATH_LINK_PROTECT_CAPTURED_SECTOR.value),
                bool -> {
                     newDeathLinkProtectSector = bool;
                     deathLinkProtectedSectorChanged = true;
                }).tooltip(RandomizerConstant.PROTECT_CAPTURED_SECTOR_TOOLTIP).padBottom(1f).get().left();

        archipelagoTable.row();
        archipelagoTable.table(info -> info.add(ChatColor.applyColor(LIGHTGRAY, RandomizerConstant.CHAT_OPTIONS)).width(archipelagoTable.getWidth())).padBottom(1f).get().left();
        archipelagoTable.row();
        archipelagoTable.image().width(archipelagoTable.getWidth()).color(Color.gray).fillX().height(3).pad(6).colspan(4).padTop(0).padBottom(1).row();


        archipelagoTable.check(RandomizerConstant.DISABLE_CHAT, settings.getBool(AP_CHAT_DISABLED.value),
                bool -> {
            newDisableChat = bool;
            disableChatChanged = true;
        }).tooltip(RandomizerConstant.DISABLE_CHAT_TOOLTIP).padBottom(1f).get().left();
        archipelagoTable.row();
        archipelagoTable.check(RandomizerConstant.SELF_ITEM_ONLY,
                settings.getBool(AP_CHAT_SELF_ITEM_ONLY.value),  bool -> {
            newAllowOnlySelfItemMessage = bool;
            onlySelfItemChanged = true;
        }).tooltip(RandomizerConstant.SELF_ITEM_ONLY_TOOLTIP).padBottom(1f).get().left();
        archipelagoTable.row();


        cont.add(pane);
        cont.row();

        Table resetButtonTable = new Table();

        resetButtonTable.button(RandomizerConstant.RESET_AP_DATA, Icon.trash, () -> {
            ui.showConfirm("@confirm", RandomizerConstant.RESET_AP_DATA_WARNING,
                    () -> {
                        client.disconnect();
                        randomizer.reset(); //Reset data related to Archipelago
                        clearAllResearch(); //Reset all research
                        control.saves.deleteAll(); //Delete all saves
                        clearAllCampaign(); //Reset the campaign
                        Core.app.exit(); //Force exit to reload game data

                    });
        }).tooltip(RandomizerConstant.RESET_AP_DATA_WARNING).size(300f, 60f).pad(4f);

        cont.add(resetButtonTable);
        cont.row();

        Table buttonTable = new Table();

        buttonTable.button(RandomizerConstant.APPLY_CHANGES, () -> {
            if (newAddress != null) {
                client.disconnect();
                client.setAddress(newAddress);
                settingChanged = true;
            }
            if (newSlotName != null) {
                client.disconnect();
                client.setSlotName(newSlotName);
                settingChanged = true;
            }
            if (!passwordTextField.getText().isEmpty() && !(passwordTextField.getText().equals(settings.getString(CLIENT_PASSWORD.value)))) {
                client.disconnect();
                client.setPassword(passwordTextField.getText());
                settingChanged = true;
                passwordTextField.clearText();
            }
            if (forceDeathLinkChanged) {
                randomizer.worldState.options.setForceDisableDeathLink(newForceDisableDeathLink);
                settingChanged = true;
                forceDeathLinkChanged = false;
            }
            if (deathLinkProtectedSectorChanged) {
                settings.put(AP_DEATH_LINK_PROTECT_CAPTURED_SECTOR.value, newDeathLinkProtectSector);
                settingChanged = true;
                deathLinkProtectedSectorChanged = false;
            }
            if (onlySelfItemChanged){
                settings.put(AP_CHAT_SELF_ITEM_ONLY.value, newAllowOnlySelfItemMessage);
                settingChanged = true;
                onlySelfItemChanged = false;
            }
            if (disableChatChanged) {
                settings.put(AP_CHAT_DISABLED.value, newDisableChat);
                settingChanged = true;
                disableChatChanged = false;
            }
            if (settingChanged) {
                reload();
                settingChanged = false;
            }
        }).size(150f, 60f).pad(4f);

        buttonTable.button(RandomizerConstant.MANUALLY_VALIDATE_VICTORY, () -> {
            if (randomizer.worldState.isVictoryConditionMet()) {
                randomizer.sendGoalSignal();
            } else {
                randomizer.sendLocalMessage(RandomizerConstant.GOAL_NOT_MET);
            }
        }).size(150f, 60f).pad(4f);

        buttonTable.row();
        buttonTable.button(RandomizerConstant.CONNECT, () -> {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.connectRandomizer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    verifyConnectionStatus();
                    this.cancel();
                }
            };

            timerD.schedule(task, 1500);
            reload();
        }).size(150f, 60f).pad(4f);
        buttonTable.button(RandomizerConstant.DISCONNECT, () -> {
            client.disconnect();
            reload();
        }).size(150f, 60f).pad(4f);


        cont.add(buttonTable);
    }

    /**
     * Update ConnectionStatus
     */
    private void verifyConnectionStatus() {
        if (client.connectionStatus != displayedStatus) {
            reload();
        }
    }

    /**
     * Return in text format the status of the connection.
     * @return The status of the connection.
     */
    private String getConnectionStatus() {
        String status;
        switch (client.connectionStatus) {
            case Success:
                status = RandomizerConstant.CONNECTED;
                displayedStatus = ConnectionStatus.Success;
                break;
            case InvalidSlot:
                status = RandomizerConstant.INVALID_SLOT_NAME;
                displayedStatus = ConnectionStatus.InvalidSlot;
                break;
            case InvalidPassword:
                status = RandomizerConstant.INVALID_PASSWORD;
                displayedStatus = ConnectionStatus.InvalidPassword;
                break;
            case SlotAlreadyTaken:
                status = RandomizerConstant.SLOT_ALREADY_TAKEN;
                displayedStatus = ConnectionStatus.SlotAlreadyTaken;
                break;
            case IncompatibleVersion:
                status = RandomizerConstant.INCOMPATIBLE_VERSION;
                displayedStatus = ConnectionStatus.IncompatibleVersion;
                break;
            default:
                status = RandomizerConstant.NOT_CONNECTED;
                displayedStatus = ConnectionStatus.NotConnected;
                break;
        }
        return status;
    }

    /**
     * Obfuscate the password from view.
     * @return The obfuscated string.
     */
    private String obfuscatePassword() {
        String obfuscatedString = "";
        if (Core.settings.getString(CLIENT_PASSWORD.value) != null &&
                !(Core.settings.getString(CLIENT_PASSWORD.value).isEmpty())) {
            int charAmount = Core.settings.getString(CLIENT_PASSWORD.value).length();
            for (int i = 0; i < charAmount; i++) {
                obfuscatedString += "*";
            }
        } else {
            obfuscatedString = RandomizerConstant.PASSWORD_NOT_SET;
        }

        return obfuscatedString;
    }

    /**
     * Force the menu to reload. Should only be called before opening the menu.
     */
    public void forceReload() {
        reload();
    }

    /**
     * Reload dialog
     */
    private void reload() {
        cont.clearChildren();
        setup();
    }

    /**
     * Clear all research.
     */
    private void clearAllResearch() {
        universe.clearLoadoutInfo();
        for(TechTree.TechNode node : TechTree.all){
            node.reset();
        }
        content.each(c -> {
            if(c instanceof UnlockableContent u){
                u.clearUnlock();
            }
        });
        settings.remove("unlocks");
    }

    /**
     * Clear the campaign.
     */
    private void clearAllCampaign() {
        for(var planet : content.planets()){
            for(var sec : planet.sectors){
                sec.clearInfo();
                if(sec.save != null){
                    sec.save.delete();
                    sec.save = null;
                }
            }
        }

        for(var slot : control.saves.getSaveSlots().copy()){
            if(slot.isSector()){
                slot.delete();
            }
        }
    }


}
