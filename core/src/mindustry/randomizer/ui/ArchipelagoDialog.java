package mindustry.randomizer.ui;

import arc.Core;
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

    /**
     * New adress when the user is updating the adress.
     */
    private String newAddress;

    /**
     * New slot name when the user is updating the slot name.
     */
    private String newSlotName;

    /**
     * New password when the user is updating the password.
     */
    private String newPassword;

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
        this.newPassword = null;
        this.settingChanged = false;
        addCloseButton();
        setup();
    }

    private void setup() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                verifyConnectionStatus();
            }
        };

        cont.row();
        cont.labelWrap("Address: " + ((Core.settings.getString(CLIENT_ADRESS.value) != null) ?
                Core.settings.getString(CLIENT_ADRESS.value) : "Address not set"));

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
        cont.field("", text -> {
            newPassword = text;
        }).size(320f, 54f).maxTextLength(100);

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
            if (newPassword != null) {
                client.disconnect();
                client.setPassword(newPassword);
                settingChanged = true;
            }
            if (settingChanged) {
                client.connectionStatus = ConnectionStatus.NotConnected;
                reload();
                settingChanged = false;
            }
        }).size(140f, 60f).pad(4f);

        cont.row();
        cont.button("Connect", () -> {
            client.disconnect();
            client.connectRandomizer();
            timer.schedule(task, 1500);
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
        cont.button("Refresh status", Icon.refreshSmall, this::reload).size(140f, 60f).pad(4f);;
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
