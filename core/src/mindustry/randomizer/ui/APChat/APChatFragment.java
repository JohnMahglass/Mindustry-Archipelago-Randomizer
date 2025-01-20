package mindustry.randomizer.ui.APChat;

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
import mindustry.randomizer.enums.ApChatColors;
import mindustry.randomizer.enums.ItemsClassification;
import mindustry.randomizer.utils.ChatColor;
import mindustry.ui.Fonts;

import static arc.Core.input;
import static arc.Core.scene;
import static mindustry.Vars.maxTextLength;
import static mindustry.Vars.mobile;
import static mindustry.Vars.player;
import static mindustry.Vars.randomizer;
import static mindustry.Vars.ui;
import static mindustry.randomizer.enums.ApChatColors.*;

/**
 * APChatFragment replacing ChatFragment. This chat can be used without multiplayer.
 *
 * @author John Mahglass
 * @version 1.0.0 2024-07-05
 */
public class APChatFragment extends Table {

    ClientCommandController commandController;
    private static final int messagesShown = 10;
    private Seq<APMessage> messages = new Seq<>();
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

        commandController = new ClientCommandController(this);
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
            if (messages.get(i).isItemMessage) {
                APMessage chatMessage = messages.get(i);
                theight = drawItemMessage(opacity, textWidth, theight, i, chatMessage);
            } else {
                theight = drawBlankMessage(opacity, textWidth, theight, i);
            }
        }

        Draw.color();

        if(fadetime > 0 && !shown){
            fadetime -= Time.delta / 180f;
        }
    }

    /**
     * Draw a non-colored message.
     * @param opacity
     * @param textWidth
     * @param theight
     * @param i
     * @return
     */
    private float drawBlankMessage(float opacity, float textWidth, float theight, int i) {
        layout.setText(font, messages.get(i).plainText, Color.white, textWidth,
                Align.bottomLeft, true);
        theight += layout.height + textspacing;
        if(i - scrollPos == 0) theight -= textspacing + 1;

        font.getCache().clear();
        font.getCache().setColor(Color.white);
        font.getCache().addText(messages.get(i).plainText, fontoffsetx + offsetx,
                offsety + theight, textWidth, Align.bottomLeft, true);

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
        return theight;
    }

    /**
     * Draw a colored message for an item event.
     * @param opacity
     * @param textWidth
     * @param theight
     * @param i
     * @param chatMessage
     * @return
     */
    private float drawItemMessage(float opacity, float textWidth, float theight, int i,
                             APMessage chatMessage) {
        String finalText;

        String finderPlayer = getPlayerNameText(chatMessage);
        boolean isMindustryPlayer = randomizer.client.getSlotName().equals(finderPlayer);
        if (isMindustryPlayer) {
            finderPlayer = ChatColor.applyColor(GOLD, finderPlayer);
        } else {
            finderPlayer = ChatColor.applyColor(TEAL, finderPlayer);
        }
        ApChatColors itemColor = WHITE; //Should only be white if it's a filler item.
        if (chatMessage.classification.equals(ItemsClassification.PROGRESSION)) {
            itemColor = PURPLE;
        } else if (chatMessage.classification.equals(ItemsClassification.USEFUL)) {
            itemColor = BLUE;
        } else if (chatMessage.classification.equals(ItemsClassification.TRAP)) {
            itemColor = ORANGE;
        }
        String foundText = getFoundText(chatMessage);
        String itemText;
        String endText;
        itemText = chatMessage.message.get(2);
        itemText = ChatColor.applyColor(itemColor, itemText);
        if (chatMessage.message.size() == 6) { //Player found their own item
            endText = getEndText(chatMessage);
            finalText = finderPlayer + foundText + itemText + endText;
        } else { //Item is being sent from one player to another
            String receivingPlayer = chatMessage.message.get(4);
            isMindustryPlayer = randomizer.client.getSlotName().equals(chatMessage.message.get(4));
            if (isMindustryPlayer) {
                receivingPlayer = ChatColor.applyColor(GOLD, receivingPlayer);
            } else {
                receivingPlayer = ChatColor.applyColor(TEAL, receivingPlayer);
            }
            endText = getEndText(chatMessage);
            finalText =
                    finderPlayer + chatMessage.message.get(1) + itemText + chatMessage.message.get(3) + receivingPlayer + endText;
        }

        font.data.markupEnabled = true;
        layout.setText(font, finalText, 0, finalText.length(),
                Color.white, textWidth,
                Align.bottomLeft, true, null);
        theight += layout.height + textspacing;
        if(i - scrollPos == 0) theight -= textspacing + 1;

        font.getCache().clear();
        font.getCache().setColor(Color.white);
        font.getCache().addText(finalText, fontoffsetx + offsetx,
                offsety + theight,0 , finalText.length(), textWidth,
                Align.bottomLeft, true);

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
        return theight;
    }

    private String getFoundText(APMessage chatMessage) {
        return chatMessage.message.get(1);
    }

    /**
     * Return the 3 last part of a message. This part contains the location the item was found.
     * @param chatMessage The chat message, separated into parts.
     * @return Return the end of the text.
     */
    private String getEndText(APMessage chatMessage) {
        int size = chatMessage.message.size();
        return (chatMessage.message.get(size - 3) + chatMessage.message.get(size - 2) + chatMessage.message.get(size - 1));
    }

    private String getColoredText(APMessage chatMessage) {
        return (chatMessage.message.get(2));
    }

    private String getPlayerNameText(APMessage chatMessage) {
        return (chatMessage.message.get(0));
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
        boolean isCommand = ClientCommandController.isCommand(message);
        if (isCommand) {
            commandController.executeCommand(message);
        } else {
            randomizer.sendArchipelagoMessage(message);
        }
    }

    /**
     * Add a message to the chat without sending it to Archipelago
     * @param message The messaged to be added.
     */
    public void addLocalMessage(APMessage message){
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
