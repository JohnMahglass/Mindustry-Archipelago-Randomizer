package mindustry.randomizer.dialog;

import arc.Core;
import mindustry.gen.Icon;
import mindustry.randomizer.client.APClient;
import mindustry.randomizer.enums.ConnectionStatus;
import mindustry.ui.dialogs.BaseDialog;

import java.util.Timer;
import java.util.TimerTask;

import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;

/**
 * Dialog for Archipelago settings.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-06-10
 */
public class ArchipelagoDialog extends BaseDialog {
    private APClient client;
    private String newAddress;
    private String newSlotName;
    private String newPassword;
    private boolean settingChanged;
    private ConnectionStatus displayedStatus;

    public ArchipelagoDialog() {
        super("Archipelago");
        this.client = randomizer.randomizerClient;
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
        cont.labelWrap("This options menu is still in development, values might not be accurate");

        cont.row();
        cont.labelWrap("Address: " + ((Core.settings.getString("APaddress") != null) ?
                Core.settings.getString("APaddress") : "Address not set"));

        cont.row();
        cont.labelWrap("Player name: " + ((Core.settings.getString("APslotName") != null ?
                Core.settings.getString("APslotName") : "Name not set")));

        cont.row();
        cont.labelWrap("Password: " + ((Core.settings.getString("APpassword") != null ?
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
                Core.settings.put("APaddress", newAddress);
                settingChanged = true;
            }
            if (newSlotName != null) {
                client.disconnect();
                client.setSlotName(newSlotName);
                Core.settings.put("APslotName", newSlotName);
                settingChanged = true;
            }
            if (newPassword != null) {
                client.disconnect();
                client.setPassword(newPassword);
                Core.settings.put("APpassword", newPassword);
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
            if(client.isConnected()) {
                client.disconnect();
            }
            client.connectRandomizer();
            timer.schedule(task, 2000);
            reload();
        }).size(140f, 60f).pad(4f);
        cont.button("Disconnect", () -> {
            client.disconnect();
            client.connectionStatus = ConnectionStatus.NotConnected;
            reload();
        }).size(140f, 60f).pad(4f);

        cont.row();
        cont.button("Clear data", Icon.trash, () -> {
            ui.showConfirm("@confirm", "Wipe local data related to Archipelago. It is not " +
                            "recommended you use this setting unless you have finished playing a " +
                            "game.",
                    () -> {
                randomizer.worldState.wipeStates();
            });
        }).size(150f, 60f).pad(4f);

    }

    private void verifyConnectionStatus() {
        if (client.connectionStatus != displayedStatus) {
            reload();
        }
    }

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

    private String obfuscatePassword() {
        String obfuscatedString = "";
        if (Core.settings.getString("APpassword") != null &&
                !(Core.settings.getString("APpassword").isEmpty())) {
            int charAmount = Core.settings.getString("APpassword").length();
            for (int i = 0; i < charAmount; i++) {
                obfuscatedString += "*";
            }
        } else {
            obfuscatedString = "Password not Set";
        }

        return obfuscatedString;
    }

    /**
     * Reload dialog
     */
    private void reload() {
        cont.clearChildren();
        setup();
    }
}
