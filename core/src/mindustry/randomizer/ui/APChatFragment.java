package mindustry.randomizer.ui;

import arc.Core;
import arc.Events;
import arc.Input;
import arc.func.Boolp;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.math.Mathf;
import arc.scene.Group;
import arc.scene.ui.Label;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.input.Binding;
import mindustry.ui.Fonts;

import static arc.Core.input;
import static arc.Core.scene;
import static mindustry.Vars.maxTextLength;
import static mindustry.Vars.mobile;
import static mindustry.Vars.player;
import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;

/**
 * APChatFragment replacing ChatFragment. This chat can be used without multiplayer.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-05
 */
public class APChatFragment extends Table {

    private static final int messagesShown = 10;
    private Seq<String> messages = new Seq<>();
    private float fadetime;
    private boolean shown = false;
    private TextField chatfield;
    private Label fieldlabel = new Label(">");
    private ChatMode mode = ChatMode.normal;
    private Font font;
    private GlyphLayout layout = new GlyphLayout();
    private float offsetx = Scl.scl(4), offsety = Scl.scl(4), fontoffsetx = Scl.scl(2), chatspace = Scl.scl(50);
    private Color shadowColor = new Color(0, 0, 0, 0.5f);
    private float textspacing = Scl.scl(10);
    private Seq<String> history = new Seq<>();
    private int historyPos = 0;
    private int scrollPos = 0;

    public APChatFragment(){
        super();

        setFillParent(true);
        font = Fonts.def;

        visible(() -> {
            return ui.hudfrag.shown;
        });

        update(() -> {

            if(input.keyTap(Binding.chat) && (scene.getKeyboardFocus() == chatfield || scene.getKeyboardFocus() == null || ui.minimapfrag.shown()) && !ui.consolefrag.shown()){
                toggle();
            }

            if(shown){
                if(input.keyTap(Binding.chat_history_prev) && historyPos < history.size - 1){
                    if(historyPos == 0) history.set(0, chatfield.getText());
                    historyPos++;
                    updateChat();
                }
                if(input.keyTap(Binding.chat_history_next) && historyPos > 0){
                    historyPos--;
                    updateChat();
                }
                scrollPos = (int) Mathf.clamp(scrollPos + input.axis(Binding.chat_scroll), 0, Math.max(0, messages.size - messagesShown));
            }
        });

        history.insert(0, "");
        setup();
    }

    public void build(Group parent){
        scene.add(this);
    }

    public void clearMessages(){
        messages.clear();
        history.clear();
        history.insert(0, "");
    }

    private void setup(){
        fieldlabel.setStyle(new Label.LabelStyle(fieldlabel.getStyle()));
        fieldlabel.getStyle().font = font;
        fieldlabel.setStyle(fieldlabel.getStyle());

        chatfield = new TextField("", new TextField.TextFieldStyle(scene.getStyle(TextField.TextFieldStyle.class)));
        chatfield.setMaxLength(Vars.maxTextLength);
        chatfield.getStyle().background = null;
        chatfield.getStyle().fontColor = Color.white;
        chatfield.setStyle(chatfield.getStyle());

        chatfield.typed(this::handleType);

        bottom().left().marginBottom(offsety).marginLeft(offsetx * 2).add(fieldlabel).padBottom(6f);

        add(chatfield).padBottom(offsety).padLeft(offsetx).growX().padRight(offsetx).height(28);

        if(Vars.mobile){
            marginBottom(105f);
            marginRight(240f);
        }
    }

    //no mobile support.
    private void handleType(char c){
        int cursor = chatfield.getCursorPosition();
        if(c == ':'){
            int index = chatfield.getText().lastIndexOf(':', cursor - 2);
            if(index >= 0 && index < cursor){
                String text = chatfield.getText().substring(index + 1, cursor - 1);
                String uni = Fonts.getUnicodeStr(text);
                if(uni != null && uni.length() > 0){
                    chatfield.setText(chatfield.getText().substring(0, index) + uni + chatfield.getText().substring(cursor));
                    chatfield.setCursorPosition(index + uni.length());
                }
            }
        }
    }

    protected void rect(float x, float y, float w, float h){
        //prevents texture bindings; the string lookup is irrelevant as it is only called <10 times per frame, and maps are very fast anyway
        Draw.rect("whiteui", x + w/2f, y + h/2f, w, h);
    }

    @Override
    public void draw(){
        float opacity = Core.settings.getInt("chatopacity") / 100f;
        float textWidth = Math.min(Core.graphics.getWidth()/1.5f, Scl.scl(700f));

        Draw.color(shadowColor);

        if(shown){
            rect(offsetx, chatfield.y + scene.marginBottom, chatfield.getWidth() + 15f, chatfield.getHeight() - 1);
        }

        super.draw();

        float spacing = chatspace;

        chatfield.visible = shown;
        fieldlabel.visible = shown;

        Draw.color(shadowColor);
        Draw.alpha(shadowColor.a * opacity);

        float theight = offsety + spacing + getMarginBottom() + scene.marginBottom;
        for(int i = scrollPos; i < messages.size && i < messagesShown + scrollPos && (i < fadetime || shown); i++){

            layout.setText(font, messages.get(i), Color.white, textWidth, Align.bottomLeft, true);
            theight += layout.height + textspacing;
            if(i - scrollPos == 0) theight -= textspacing + 1;

            font.getCache().clear();
            font.getCache().setColor(Color.white);
            font.getCache().addText(messages.get(i), fontoffsetx + offsetx, offsety + theight, textWidth, Align.bottomLeft, true);

            if(!shown && fadetime - i < 1f && fadetime - i >= 0f){
                font.getCache().setAlphas((fadetime - i) * opacity);
                Draw.color(0, 0, 0, shadowColor.a * (fadetime - i) * opacity);
            }else{
                font.getCache().setAlphas(opacity);
            }

            rect(offsetx, theight - layout.height - 2, textWidth + Scl.scl(4f), layout.height + textspacing);
            Draw.color(shadowColor);
            Draw.alpha(opacity * shadowColor.a);

            font.getCache().draw();
        }

        Draw.color();

        if(fadetime > 0 && !shown){
            fadetime -= Time.delta / 180f;
        }
    }

    private void sendMessage(){
        String message = chatfield.getText().trim();
        clearChatInput();

        //avoid sending prefix-empty messages
        if(message.isEmpty() || (message.startsWith(mode.prefix) && message.substring(mode.prefix.length()).isEmpty())) return;

        history.insert(1, message);

        Events.fire(new EventType.ClientChatEvent(message));

        addMessage(message);
    }

    public void toggle(){

        if(!shown){
            scene.setKeyboardFocus(chatfield);
            shown = true;
            if(mobile){
                Input.TextInput input = new Input.TextInput();
                input.maxLength = maxTextLength;
                input.accepted = text -> {
                    chatfield.setText(text);
                    sendMessage();
                    hide();
                    Core.input.setOnscreenKeyboardVisible(false);
                };
                input.canceled = this::hide;
                Core.input.getTextInput(input);
            }else{
                chatfield.fireClick();
            }
        }else{
            //sending chat has a delay; workaround for issue #1943
            Time.runTask(2f, () ->{
                scene.setKeyboardFocus(null);
                shown = false;
                scrollPos = 0;
                sendMessage();
            });
        }
    }

    public void hide(){
        scene.setKeyboardFocus(null);
        shown = false;
        clearChatInput();
    }

    public void updateChat(){
        chatfield.setText(mode.normalizedPrefix() + history.get(historyPos));
        updateCursor();
    }

    public void clearChatInput(){
        historyPos = 0;
        history.set(0, "");
        chatfield.setText(mode.normalizedPrefix());
        updateCursor();
    }

    public void updateCursor(){
        chatfield.setCursorPosition(chatfield.getText().length());
    }

    public boolean shown(){
        return shown;
    }

    /**
     * verify if the message is a command before processing it.
     * @param message The message to be verified.
     */
    public void addMessage(String message){
        boolean isCommand = isCommand(message);
        if (isCommand) {
            executeCommand(message);
        } else {
            randomizer.sendArchipelagoMessage(message);
        }
    }

    /**
     * Execute a command sent by the player
     * @param message The message containing the command.
     */
    private void executeCommand(String message) {
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
                    addLocalMessage("You are already connected.");
                }
                break;
            case "disconnect":
                if (connectionOpen) {
                    executeDisconnectCommand(commandParts);
                } else {
                    addLocalMessage("You are not connected to any game.");
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
            default:
                addLocalMessage("Unknown command. Use '/help' for command usage.");
                break;
        }
    }

    private void executeOptionsCommand(String[] commandParts) {
        if (commandParts.length > 1) {
            tooManyArgumentMessage();
            return;
        }
        if (randomizer.worldState.options.getOptionsFilled()) {
            addLocalMessage("Options:\n" +
                            "   Selected campaign: " + getCampaignName() + "\n" +
                            "   Tutorial skip: " + getActivationStatus(randomizer.worldState.options.getTutorialSkip()) + "\n" +
                            "   Disable invasions: " + getActivationStatus(randomizer.worldState.options.getDisableInvasions()) + "\n" +
                            "   Faster production: " + getActivationStatus(randomizer.worldState.options.getFasterProduction()) + "\n" +
                            "   Death link: " + getDeathLinkActivationStatus(randomizer.worldState.options.getDeathLink()) + "\n" +
                            "   Force D. DL (DEV): " + getActivationStatus(randomizer.worldState.options.getForceDisableDeathLink()));
        } else {
            addLocalMessage("You must connect to a game once to view .yaml options.");
        }
    }

    /**
     * Return if the death link option was activated and if it was overridden by local client rule.
     * @param deathLink The state of the death link option.
     * @return Return if the death link option has been activated and if it is being overridden.
     */
    private String getDeathLinkActivationStatus(boolean deathLink) {
        //TEMPORARY SINCE OVERRIDE NOT IMPLEMENTED
        return getActivationStatus(deathLink);
    }

    /**
     * Return if the option was activated.
     * @param status The status of the option
     * @return Return the status of the option.
     */
    private String getActivationStatus(boolean status) {
        return status ? "Activated" : "Deactivated";
    }

    /**
     * Return the selected campaign name.
     * @return The campaign name.
     */
    private String getCampaignName() {
        String name;
        int campaign = randomizer.worldState.options.getCampaignChoice();
        if (campaign == 0) { //Serpulo
            name = "Serpulo";
        } else if (campaign == 1) { //Erekir
            name = "Erekir";
        } else if (campaign == 2) { //All
            name = "Serpulo and Erekir";
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
                status = "Connected";
                break;
            case NotConnected:
                status = "Not connected";
                break;
            case InvalidSlot:
                status = "Invalid slot name";
                break;
            case InvalidPassword:
                status = "Invalid password";
                break;
            case SlotAlreadyTaken:
                status = "Slot already taken";
                break;
            case IncompatibleVersion:
                status = "Incompatible version";
                break;
            default:
                status = "Error";
                break;
        }
        addLocalMessage("Connection status: " + status);
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
        addLocalMessage("Too many argument. Use '/help' for command usage.");
    }

    /**
     * List in the player's chat all available commands for the client.
     */
    private void listAvailableCommands() {
        addLocalMessage("""
                Available commands:
                  /help
                        List available commands. (what you are doing right now)
                  /status
                        Display connection status.
                  /options
                        Display selected options for game generation.
                        You need to have connected once to be able
                        to view selected options
                  /connect
                        Connect using the information provided in
                        Settings -> Archipelago
                  /connect [Address] [Slot Name]
                        Connect using the information provided in argument.
                        (Password not available to prevent displaying password)
                  /disconnect
                        Disconnect from AP""");
    }

    /**
     * Return if the player's message is a client command.
     * @param message The player's message.
     * @return True if the message is a command.
     */
    private boolean isCommand(String message) {
        return message.startsWith("/");
    }

    /**
     * Add a message to the chat without sending it to Archipelago
     * @param message The messaged to be added.
     */
    public void addLocalMessage(String message){
        if(message == null) return;
        messages.insert(0, message);

        fadetime += 2f;
        fadetime = Math.min(fadetime, messagesShown) + 1f;

        if(scrollPos > 0) scrollPos++;
    }

    private enum ChatMode{
        normal(""),
        team("/t"),
        admin("/a", player::admin)
        ;

        public String prefix;
        public Boolp valid;
        public static final ChatMode[] all = values();

        ChatMode(String prefix){
            this.prefix = prefix;
            this.valid = () -> true;
        }

        ChatMode(String prefix, Boolp valid){
            this.prefix = prefix;
            this.valid = valid;
        }

        public ChatMode next(){
            return all[(ordinal() + 1) % all.length];
        }

        public String normalizedPrefix(){
            return prefix.isEmpty() ? "" : prefix + " ";
        }

        public boolean isValid(){
            return valid.get();
        }
    }

}
