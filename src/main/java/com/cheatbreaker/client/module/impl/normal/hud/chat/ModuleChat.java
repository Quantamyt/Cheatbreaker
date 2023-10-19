package com.cheatbreaker.client.module.impl.normal.hud.chat;

import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.render.PreviewDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Module - ModuleChat
 * @see AbstractModule
 * <p>
 * This mod allows you to drag the chat around like the Scoreboard module, and customize it to your liking.
 */
public class ModuleChat extends AbstractModule {
    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-689A-E]");
    private final Map<String, EnumChatFormatting> chatColors = new HashMap<>();
    public List<Long> dingCooldown = new ArrayList<>(); // used to not ding on messages sent by the actual player

    private int activeChatLines = 0;

    public Setting background;
    public Setting inputFieldBackground;
    public Setting backgroundWidthType;
    public Setting backgroundWidth;
    public Setting focusedBackgroundHeight;
    public Setting unfocusedBackgroundHeight;
    public Setting textShadow;
    public Setting textOpacity;
    public Setting textSize;
    public Setting lineSpacing;
    public Setting unlimitedChat;
    public Setting clearChatOnRelog;
    public Setting masterOpacity;
    public Setting smoothChat;
    public Setting smoothChatSpeed;
    public Setting backgroundColor;
    public Setting chatHeightFix;
    public Setting highlightOwnName;
    public Setting highlightColor;
    public Setting playDing;
    public Setting stripFormattingColors;
    public Setting textColor;

    public ModuleChat() {
        super("Chat");
        this.setDefaultAnchor(GuiAnchor.LEFT_BOTTOM);
        this.setDefaultTranslations(0.0f, -26.0f);
        this.notRenderHUD = false;
        this.masterOpacity = new Setting(this, "Master Opacity").setValue(100.0F).setMinMax(0.05F, 100.0F).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.unlimitedChat = new Setting(this, "Unlimited Chat").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.clearChatOnRelog = new Setting(this, "Clear Chat on Relog").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);

        new Setting(this, "label").setValue("Background Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.background = new Setting(this, "Show Background", "Draw a background behind chat.").setValue("ON").acceptedStringValues("ON", "While Typing", "OFF").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.inputFieldBackground = new Setting(this, "Show Input Field Background", "Draw a background behind the chat text input field.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.backgroundWidthType = new Setting(this, "Background Width").setValue("Full").acceptedStringValues("Full", "Compact").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> !this.background.getValue().equals("OFF"));
        this.backgroundWidth = new Setting(this, "Width").setValue(GuiNewChat.calculateChatboxHeight(this.mc.gameSettings.chatWidth)).setMinMax(40, 320).setUnit("px").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this::isRenderHud);
        this.focusedBackgroundHeight = new Setting(this, "Focused Height").setValue(GuiNewChat.calculateChatboxHeight(this.mc.gameSettings.chatHeightFocused)).setMinMax(20, 180).setUnit("px").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this::isRenderHud);
        this.unfocusedBackgroundHeight = new Setting(this, "Unfocused Height").setValue(GuiNewChat.calculateChatboxHeight(this.mc.gameSettings.chatHeightUnfocused)).setMinMax(20, 180).setUnit("px").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this::isRenderHud);
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x80000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> !this.background.getValue().equals("OFF") || (Boolean) this.inputFieldBackground.getValue());

        new Setting(this, "label").setValue("Text Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textShadow = new Setting(this, "Text Shadow").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.stripFormattingColors = new Setting(this, "Strip Formatting Colors").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.stripFormattingColors.getBooleanValue());
        this.textOpacity = new Setting(this, "Text Opacity").setValue(100.0F).setMinMax(0.0F, 100.0F).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textSize = new Setting(this, "Text Scale").setValue(100.0F).setMinMax(0.0F, 100.0F).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this::isRenderHud);
//        this.lineSpacing = new Setting(this, "Line Spacing").setValue(0.0F).setMinMax(0.0F, 1.0F);

        new Setting(this, "label").setValue("Animation Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.smoothChat = new Setting(this, "Smooth Chat").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.smoothChatSpeed = new Setting(this, "Smooth Chat Speed").setValue(8.0F).setMinMax(0.25F, 10.0F).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.smoothChat.getValue());
//        new Setting(this, "label").setValue("Filter Options");

//        new Setting(this, "label").setValue("Keybind Options");

        new Setting(this, "label").setValue("Highlighting Options");
        this.highlightOwnName = new Setting(this, "Highlight Own Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightColor = new Setting(this, "Highlight Colour").setValue("None")
                .acceptedStringValues("None", "Purple", "Dark Red", "Gold", "Cyan", "Blue", "Green", "Aqua").setCondition(() -> this.highlightOwnName.getBooleanValue());
        this.playDing = new Setting(this, "Play Ding").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.highlightOwnName::getBooleanValue);

        new Setting(this, "label").setValue("Position Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !this.isRenderHud());
        this.chatHeightFix = new Setting(this, "Chat Height Position").setValue(0.0F).setMinMax(-14.0F, 32.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !this.isRenderHud());

        this.setDescription("Move and customize the Minecraft chat to your liking.");
        this.setCreators("LlamaLad7 (Smooth Chat)"/*, "GeckoThePecko (Filter Options)"*/);

        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.addEvent(TickEvent.class, this::onTick);
        this.setColors();
    }

    private void onPreviewDraw(PreviewDrawEvent event) {
        if (this.isRenderHud() && this.activeChatLines == 0 && this.mc.currentScreen instanceof HudLayoutEditorGui) {
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            GuiNewChat chat = new GuiNewChat(this.mc);
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("§7§m-----------------------------------------------------"));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText(""));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("Welcome to §cCheatBreaker§f!"));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText(""));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("- §cWebsite: §fwww.cheatbreaker.com"));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("- §cDiscord: §fdiscord.cheatbreaker.com"));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("- §cTwitter: §fwww.twitter.com/CheatBreaker"));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText(""));
            chat.dontPrintChatMessageWithOptionalDeletion(new ChatComponentText("§7§m-----------------------------------------------------"));

            chat.drawCustomChat(this.mc.ingameGUI.getUpdateCounter(), false);
            this.setDimensions((float) (int) this.backgroundWidth.getValue() + 4.0F, Math.min(getChatHeight(), chat.drawnChatLines.size() * 9) * (float) this.textSize.getValue() / 100.0F);
            GL11.glPopMatrix();
        }
    }

    private void onTick(TickEvent event) {
        this.dingCooldown.removeIf(n -> n < System.currentTimeMillis() - 250L);
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        int var2 = this.mc.ingameGUI.getChatGUI().getLineCount();
        int var9;
        int var3 = 0;

        for (var9 = 0; var9 + this.mc.ingameGUI.getChatGUI().scrollPos < this.mc.ingameGUI.getChatGUI().drawnChatLines.size() && var9 < var2; ++var9) {
            ChatLine var10 = this.mc.ingameGUI.getChatGUI().drawnChatLines.get(var9);
            if (var10 != null) {
                if (this.mc.ingameGUI.getUpdateCounter() - var10.getUpdatedCounter() < 200) {
                    ++var3;
                }
            }
        }

        this.activeChatLines = var3;
        if (this.activeChatLines != 0 || !(this.mc.currentScreen instanceof HudLayoutEditorGui)) {
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            int height = this.mc.currentScreen instanceof GuiChat ? this.mc.ingameGUI.getChatGUI().drawnChatLines.size() * 9 : -(-this.activeChatLines * 9);
            this.mc.ingameGUI.getChatGUI().drawCustomChat(this.mc.ingameGUI.getUpdateCounter(), this.smoothChat.getBooleanValue());
            this.setDimensions((float) (int) this.backgroundWidth.getValue() + 4.0F * (float) this.textSize.getValue() / 100.0F, Math.min(getChatHeight(), height) * (float) this.textSize.getValue() / 100.0F);
            GL11.glPopMatrix();
        }
    }

    public int getChatHeight() {
        return this.mc.currentScreen instanceof GuiChat ? (int) this.focusedBackgroundHeight.getValue() : (int) this.unfocusedBackgroundHeight.getValue();
    }

    private void setColors() {
        this.chatColors.put("Purple", EnumChatFormatting.DARK_PURPLE);
        this.chatColors.put("Red", EnumChatFormatting.DARK_RED);
        this.chatColors.put("Gold", EnumChatFormatting.GOLD);
        this.chatColors.put("Cyan", EnumChatFormatting.DARK_AQUA);
        this.chatColors.put("Blue", EnumChatFormatting.DARK_BLUE);
        this.chatColors.put("Green", EnumChatFormatting.DARK_GREEN);
        this.chatColors.put("Aqua", EnumChatFormatting.AQUA);
    }

    public EnumChatFormatting getHighlightColor() {
        return this.chatColors.get(this.highlightColor.getStringValue());
    }

    // Public so that GuiNewChat can access it.
    public String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
