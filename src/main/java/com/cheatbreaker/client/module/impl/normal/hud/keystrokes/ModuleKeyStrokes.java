package com.cheatbreaker.client.module.impl.normal.hud.keystrokes;

import com.cheatbreaker.client.event.impl.mouse.ClickEvent;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * @Module - ModuleKeyStrokes
 * @see AbstractModule
 *
 * This module shows what movement keys and mouse buttons are being pressed.
 */
public class ModuleKeyStrokes extends AbstractModule {
    private final Setting keyOptionsLabel;
    private final Setting movementKeys;
    private final Setting clicks;
    private final Setting spacebar;
    private final Setting sneak;

    private final Setting clickOptionsLabel;
    public Setting leftClicks;
    public Setting rightClicks;
    private final Setting backgroundHeight;
    public Setting clicksColor;
    public Setting clicksColorPressed;

    private final Setting spacebarOptionsLabel;
    public Setting spacebarLineWidth;
    public Setting spacebarLineHeight;
    public Setting spacebarLine;
    public Setting centerSpacebarLine;

    private final Setting textOptionsLabel;
    public Setting namesArrowSetting;
    private final Setting textShadow;
    public Setting legacyPosition;

    private final Setting backgroundOptionsLabel;
    public Setting background;
    public Setting border;
    private final Setting splitBoxSize;
    private final Setting boxSizeHeight;
    public Setting borderThickness;
    private final Setting spacebarHeight;
    public Setting boxSize;
    private final Setting gap;

    private final Setting fadeOptionsLabel;
    public Setting textFadeTime;
    public Setting backgroundFadeTime;
    public Setting borderFadeTime;

    private final Setting colorOptionsLabel;
    private final Setting textColor;
    private final Setting textColorPressed;
    private final Setting backgroundColor;
    private final Setting backgroundColorPressed;
    private final Setting borderColor;
    private final Setting borderColorPressed;

    private Key forwardElement;
    private Key leftElement;
    private Key rightElement;
    private Key backElement;
    private Key attackButtonElement;
    private Key useButtonElement;
    private Key spaceButtonElement;
    private Key sneakButtonElement;

    public final List<Long> leftCPS = new ArrayList();
    public final List<Long> rightCPS = new ArrayList();

    public ModuleKeyStrokes() {
        super("Key Strokes");
        this.setDefaultAnchor(GuiAnchor.LEFT_TOP);
        this.setDefaultTranslations(0.0F, 5);
        this.setDefaultState(false);
        this.keyOptionsLabel = new Setting(this, "label").setValue("Key Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.movementKeys = new Setting(this, "Show movement keys").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.clicks = new Setting(this, "Show clicks").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.spacebar = new Setting(this, "Show spacebar").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.sneak = new Setting(this, "Show sneak key").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        this.clickOptionsLabel = new Setting(this, "label").setValue("Click Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.clicks.getValue());
        this.leftClicks = new Setting(this, "Left CPS").setValue("OFF").acceptedStringValues("OFF", "Replace Clicks", "With Clicks", "Separate").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.clicks.getValue());
        this.rightClicks = new Setting(this, "Right CPS").setValue("OFF").acceptedStringValues("OFF", "Replace Clicks", "With Clicks", "Separate").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.clicks.getValue());
        this.backgroundHeight = new Setting(this, "CPS Background Height").setValue(13.0F).setMinMax(10.0F, 24.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.clicks.getValue() && this.leftClicks.getValue().equals("Separate") || this.rightClicks.getValue().equals("Separate"));
        this.clicksColor = new Setting(this, "CPS Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.clicks.getValue() && this.leftClicks.getValue().equals("With Clicks") || this.rightClicks.getValue().equals("With Clicks"));
        this.clicksColorPressed = new Setting(this, "CPS Color (Pressed)").setValue(-16777216).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.clicks.getValue() && this.leftClicks.getValue().equals("With Clicks") || this.rightClicks.getValue().equals("With Clicks"));

        this.spacebarOptionsLabel = new Setting(this, "label").setValue("Spacebar Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.spacebar.getValue());
        this.spacebarLine = new Setting(this, "Show Spacebar Line").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.spacebar.getValue());
        this.centerSpacebarLine = new Setting(this, "Center Spacebar Line").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.spacebar.getValue() && (Boolean) this.spacebarLine.getValue());
        this.spacebarLineWidth = new Setting(this, "Spacebar Line Width").setValue(4.0F).setMinMax(1.0F, 8.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.spacebar.getValue() && (Boolean) this.spacebarLine.getValue());
        this.spacebarLineHeight = new Setting(this, "Spacebar Line Height").setValue(1.0F).setMinMax(0.5F, 2.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.spacebar.getValue() && (Boolean) this.spacebarLine.getValue());
        this.spacebarHeight = new Setting(this, "Spacebar Height").setValue(50.0F).setMinMax(0.0F, 100.0F).setUnit("%").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.spacebar.getValue());

        this.textOptionsLabel = new Setting(this, "label").setValue("Text Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textShadow = new Setting(this, "Text Shadow").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.namesArrowSetting = new Setting(this, "Replace names with arrows").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.movementKeys.getValue());

        this.legacyPosition = new Setting(this, "Legacy Position").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.movementKeys.getValue() || (Boolean) this.clicks.getValue());

        this.backgroundOptionsLabel = new Setting(this, "label").setValue("Background Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.background = new Setting(this, "Show Background").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.border = new Setting(this, "Show Border").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.splitBoxSize = new Setting(this, "Split Box Size").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.boxSize = new Setting(this, "Box size").setValue(18.0F).setMinMax(10.0F, 32.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.boxSizeHeight = new Setting(this, "Box size height").setValue(18.0F).setMinMax(10.0F, 32.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.splitBoxSize.getValue());
        this.gap = new Setting(this, "Gap").setValue(1.0F).setMinMax(0.0F, 4.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.borderThickness = new Setting(this, "Border Thickness").setValue(1.0F).setMinMax(0.0F, 3.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.border.getValue());

        this.fadeOptionsLabel = new Setting(this, "label").setValue("Fade Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textFadeTime = new Setting(this, "Text Fade Time").setValue(0.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.backgroundFadeTime = new Setting(this, "Background Fade Time").setValue(75.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.background.getValue());
        this.borderFadeTime = new Setting(this, "Border Fade Time").setValue(100.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.border.getValue());

        this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textColorPressed = new Setting(this, "Text Color (Pressed)").setValue(-16777216).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x6F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.background.getValue());
        this.backgroundColorPressed = new Setting(this, "Background Color (Pressed)").setValue(0x6FFFFFFF).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.background.getValue());
        this.borderColor = new Setting(this, "Border Color").setValue(0x9F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.border.getValue());
        this.borderColorPressed = new Setting(this, "Border Color (Pressed)").setValue(0x9FFFFFFF).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.border.getValue());
        this.updateKeyElements();
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/wasd.png"), 55, 37);
        this.setDescription("Displays when your movement keys, mouse or spacebar is pressed.");
        this.setCreators("Fyu");
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.addEvent(ClickEvent.class, this::onClick);
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        this.leftCPS.removeIf((var0) -> var0 < System.currentTimeMillis() - 1000L);
        this.rightCPS.removeIf((var0) -> var0 < System.currentTimeMillis() - 1000L);
        float width = 0.0f;
        float height = 0.0f;
        float gap = (Float) this.gap.getValue();
        float borderThickness = (Float) this.borderThickness.getValue();
        float bgHeight = (Float) this.backgroundHeight.getValue();
        if (!(Boolean) border.getValue()) {
            borderThickness = 0.0F;
        }

        if ((Boolean) this.movementKeys.getValue()) {
            this.forwardElement.render(this.leftElement.getWidth() + gap + (borderThickness * 2.0F), 0.0F, this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            this.leftElement.render(0.0F, this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            this.backElement.render(this.leftElement.getWidth() + gap + (borderThickness * 2.0F), this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            this.rightElement.render(this.leftElement.getWidth() + this.backElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + (2.0F) * gap + (borderThickness * 2.0F);
            height += this.forwardElement.getHeight() + 2.0F * gap + this.backElement.getHeight();
        }

        if ((Boolean) this.clicks.getValue()) {
            this.attackButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            this.useButtonElement.render(this.attackButtonElement.getWidth() + gap + (borderThickness * 2.0F), height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += this.useButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if ((Boolean) this.spacebar.getValue()) {
            this.spaceButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.spaceButtonElement.getWidth();
            height += this.spaceButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if ((Boolean) this.sneak.getValue()) {
            this.sneakButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += this.sneakButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if (this.leftClicks.getValue().equals("Separate")) {
            String leftClicks = this.leftCPS.size() + " CPS";
            if ((Boolean) this.background.getValue()) {
                Gui.drawRect(0.0F, height + (borderThickness * 4.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), height + (borderThickness * 4.0F) + bgHeight, this.backgroundColor.getColorValue());
            }
            if ((Boolean) this.border.getValue()) {
                Gui.drawOutline(-borderThickness, height + (borderThickness * 3.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 5.0F), height + (borderThickness * 5.0F) + bgHeight, borderThickness, this.borderColor.getColorValue());
            }
            GlStateManager.enableBlend();
            this.mc.fontRendererObj.drawString(leftClicks, this.width / 2.0F - (float) (this.mc.fontRendererObj.getStringWidth(leftClicks) / 2) - 0.1F, height + (borderThickness * 4.0F) + (bgHeight / 2.0F - 3.49F), clicksColor.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += bgHeight + gap + (borderThickness * 2.0F);
        }

        if (this.rightClicks.getValue().equals("Separate")) {
            String leftClicks = this.rightCPS.size() + " RCPS";
            if ((Boolean) this.background.getValue()) {
                Gui.drawRect(0.0F, height + (borderThickness * 4.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), height + (borderThickness * 4.0F) + bgHeight, this.backgroundColor.getColorValue());
            }
            if ((Boolean) this.border.getValue()) {
                Gui.drawOutline(-borderThickness, height + (borderThickness * 3.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 5.0F), height + (borderThickness * 5.0F) + bgHeight, borderThickness, this.borderColor.getColorValue());
            }
            GlStateManager.enableBlend();
            this.mc.fontRendererObj.drawString(leftClicks, this.width / 2.0F - (float) (this.mc.fontRendererObj.getStringWidth(leftClicks) / 2) - 0.1F, height + (borderThickness * 4.0F) + (bgHeight / 2.0F - 3.49F), clicksColor.getColorValue(), (Boolean) this.textShadow.getValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += bgHeight + gap + (borderThickness * 2.0F);
        }
        GlStateManager.disableBlend();

        this.setDimensions(width, height - gap + (borderThickness * 2.0F));
        GL11.glPopMatrix();
    }

    public void updateKeyElements() {
        int forwardKeyCode = this.mc.gameSettings.keyBindForward.getKeyCode();
        int leftKeyCode = this.mc.gameSettings.keyBindLeft.getKeyCode();
        int backKeyCode = this.mc.gameSettings.keyBindBack.getKeyCode();
        int rightKeyCode = this.mc.gameSettings.keyBindRight.getKeyCode();
        float boxSize = (Float) this.boxSize.getValue();
        float boxSize2 = (Boolean) this.splitBoxSize.getValue() ? (Float) this.boxSizeHeight.getValue() : (Float) this.boxSize.getValue();
        float gap = (Float) this.gap.getValue();
        String w = Keyboard.getKeyName(forwardKeyCode);
        String a = Keyboard.getKeyName(leftKeyCode);
        String s = Keyboard.getKeyName(backKeyCode);
        String d = Keyboard.getKeyName(rightKeyCode);
        float upKeyWidth = (float) this.mc.fontRendererObj.getStringWidth(w) * (Float) this.scale.getValue();
        float leftKeyWidth = (float) this.mc.fontRendererObj.getStringWidth(a) * (Float) this.scale.getValue();
        float downKeyWidth = (float) this.mc.fontRendererObj.getStringWidth(s) * (Float) this.scale.getValue();
        float rightKeyWidth = (float) this.mc.fontRendererObj.getStringWidth(d) * (Float) this.scale.getValue();
        int jumpKeyCode = this.mc.gameSettings.keyBindJump.getKeyCode();
        int attackKeyCode = this.mc.gameSettings.keyBindAttack.getKeyCode();
        int useItemKeyCode = this.mc.gameSettings.keyBindUseItem.getKeyCode();
        int sneakKeyBind = this.mc.gameSettings.keyBindSneak.getKeyCode();
        boolean arrowKeys = (Boolean) this.namesArrowSetting.getValue();
        float borderThickness = (Float) this.borderThickness.getValue();
        if (!(Boolean) this.border.getValue()) {
            borderThickness = 0.0F;
        }
        this.forwardElement = new Key(arrowKeys ? "▲" : (upKeyWidth > boxSize ? w.substring(0, 1) : w), forwardKeyCode, boxSize, boxSize2);
        this.leftElement = new Key(arrowKeys ? "◀" : (leftKeyWidth > boxSize ? a.substring(0, 1) : a), leftKeyCode, boxSize, boxSize2);
        this.backElement = new Key(arrowKeys ? "▼" : (downKeyWidth > boxSize ? s.substring(0, 1) : s), backKeyCode, boxSize, boxSize2);
        this.rightElement = new Key(arrowKeys ? "▶" : (rightKeyWidth > boxSize ? d.substring(0, 1) : d), rightKeyCode, boxSize, boxSize2);
        float boxSizeButtonWidth = (this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + gap + (borderThickness * 2.0F)) / 2.0f;
        this.attackButtonElement = new Key(boxSize < 14 ? "L" : "LMB", attackKeyCode, boxSizeButtonWidth, boxSize2);
        this.useButtonElement = new Key(boxSize < 14 ? "R" : "RMB", useItemKeyCode, boxSizeButtonWidth, boxSize2);
        this.spaceButtonElement = new Key(Keyboard.getKeyName(jumpKeyCode), jumpKeyCode, this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0f * gap + (borderThickness * 4.0F), boxSize2 * (Float) this.spacebarHeight.getValue() / 100.0f);
        this.sneakButtonElement = new Key("SNEAK", sneakKeyBind, this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), boxSize2 * 0.72F);
    }

    private void onClick(ClickEvent event) {
        if (event.getMouseButton() == 0) leftCPS.add(System.currentTimeMillis());
        if (event.getMouseButton() == 1) rightCPS.add(System.currentTimeMillis());
    }
}
