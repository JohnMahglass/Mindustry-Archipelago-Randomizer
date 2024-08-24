package mindustry.randomizer.ui;

import arc.Core;
import arc.KeyBinds.Section;
import arc.graphics.Color;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.Label;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Stack;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Icon;
import mindustry.randomizer.client.APClient;
import mindustry.randomizer.enums.ConnectionStatus;
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

    /**
     * True if the player has modified the force death link option box.
     */
    private boolean forceDeathLinkChanged;

    /**
     * Set to true when a setting has been changed.
     */
    private boolean settingChanged;

    /**
     * Status to be displayed in the Dialog.
     */
    private ConnectionStatus displayedStatus;

    public ArchipelagoDialog() {
        super("Archipelago");
        this.client = randomizer.client;
        this.newAddress = null;
        this.newSlotName = null;
        this.settingChanged = false;
        this.newForceDisableDeathLink = false;
        this.forceDeathLinkChanged = false;
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

        cont.row();
        cont.labelWrap("Address: " + ((Core.settings.getString(CLIENT_ADDRESS.value) != null) ?
                Core.settings.getString(CLIENT_ADDRESS.value) : "Address not set"));

        cont.row();
        cont.labelWrap("Player name: " + ((Core.settings.getString(CLIENT_NAME.value) != null ?
                Core.settings.getString(CLIENT_NAME.value) : "Name not set")));

        cont.row();
        cont.labelWrap("Password: " + ((Core.settings.getString(CLIENT_PASSWORD.value) != null ?
                obfuscatePassword() : "Password not set")));

        cont.row();
        cont.labelWrap("Connection status: " + getConnectionStatus());

        cont.row();
        cont.labelWrap("New Address: ").padBottom(55f);
        cont.field("", text -> {
            newAddress = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.labelWrap("New SlotName: ").padBottom(55f);
        cont.field("", text -> {
            newSlotName = text;
        }).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.labelWrap("New Password: ").padBottom(55f);
        cont.add(passwordTextField).size(320f, 54f).maxTextLength(100);

        cont.row();
        cont.check("Force disable death link", settings.getBool(FORCE_DISABLE_DEATH_LINK.value),  bool -> {
            newForceDisableDeathLink = bool;
            forceDeathLinkChanged = true;
        }).padBottom(55f).size(320f, 54f);


        cont.row();
        cont.button("Apply changes", () -> {
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
            if (settingChanged) {
                reload();
                settingChanged = false;
            }
        }).size(140f, 60f).pad(4f);
        cont.button("Refresh status", Icon.refreshSmall, this::reload).size(140f, 60f).pad(4f);

        cont.row();
        cont.button("Connect", () -> {
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
        }).size(140f, 60f).pad(4f);
        cont.button("Disconnect", () -> {
            client.disconnect();
            reload();
        }).size(140f, 60f).pad(4f);

        cont.row();
        cont.button("Clear data", Icon.trash, () -> {
            ui.showConfirm("@confirm", "Wipe campaign/research/saves and Archipelago related data" +
                            " and force exit the program. It is not recommended you use this " +
                            "setting" + " unless you have finished playing a " + "game.",
                    () -> {
                client.disconnect();
                randomizer.reset(); //Reset data related to Archipelago
                clearAllResearch(); //Reset all research
                control.saves.deleteAll(); //Delete all saves
                clearAllCampaign(); //Reset the campaign
                Core.app.exit(); //Force exit to reload game data

            });
        }).size(150f, 60f).pad(4f);
        cont.button("Clear login info", Icon.eraser, () -> {
            client.disconnect();
            passwordTextField.clearText();
            client.setPassword("");

            newAddress = "";
            client.setAddress("");

            newSlotName = "";
            client.setSlotName("");

            reload();
        }).size(140f, 60f).pad(4f);
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
                status = "Connected";
                displayedStatus = ConnectionStatus.Success;
                break;
            case InvalidSlot:
                status = "Invalid slot";
                displayedStatus = ConnectionStatus.InvalidSlot;
                break;
            case InvalidPassword:
                status = "Invalid password";
                displayedStatus = ConnectionStatus.InvalidPassword;
                break;
            case SlotAlreadyTaken:
                status = "Slot already taken";
                displayedStatus = ConnectionStatus.SlotAlreadyTaken;
                break;
            case IncompatibleVersion:
                status = "Incompatible version";
                displayedStatus = ConnectionStatus.IncompatibleVersion;
                break;
            default:
                status = "Not connected";
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
            obfuscatedString = "Password not Set";
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
