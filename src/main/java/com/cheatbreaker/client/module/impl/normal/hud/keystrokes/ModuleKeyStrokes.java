package com.cheatbreaker.client.module.impl.normal.hud.keystrokes;

import com.cheatbreaker.client.event.impl.ClickEvent;
import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays when the user's movement keys, mouse or spacebar is pressed.
 * @see AbstractModule
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
    private Setting backgroundHeight;
    public Setting clicksColor;
    public Setting clicksColorPressed;

    private final Setting spacebarOptionsLabel;
    public Setting spacebarLineWidth;
    public Setting spacebarLineHeight;
    public Setting spacebarLine;
    public Setting centerSpacebarLine;

    private final Setting sneakOptionsLabel;
    public Setting sneakKeybindName;
    public Setting sneakHeight;

    private final Setting textOptionsLabel;
    public Setting namesArrowSetting;
    private final Setting textShadow;
    private final Setting literalKeys;
    public Setting legacyPosition;

    private final Setting backgroundOptionsLabel;
    public Setting background;
    public Setting border;
    private Setting splitBoxSize;
    private Setting boxSizeHeight;
    public Setting borderThickness;
    private Setting spacebarHeight;
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

        this.clickOptionsLabel = new Setting(this, "label").setValue("Click Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.clicks::getBooleanValue);
        this.leftClicks = new Setting(this, "Left CPS").setValue("OFF").acceptedStringValues("OFF", "Replace Clicks", "With Clicks", "Separate").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.clicks::getBooleanValue);
        this.rightClicks = new Setting(this, "Right CPS").setValue("OFF").acceptedStringValues("OFF", "Replace Clicks", "With Clicks", "Separate").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.clicks::getBooleanValue);
        this.backgroundHeight = new Setting(this, "CPS Background Height").setValue(13.0F).setMinMax(10.0F, 24.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.clicks.getBooleanValue() && this.leftClicks.getValue().equals("Separate") || this.rightClicks.getValue().equals("Separate"));
        this.clicksColor = new Setting(this, "CPS Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.clicks.getBooleanValue() && this.leftClicks.getValue().equals("With Clicks") || this.rightClicks.getValue().equals("With Clicks"));
        this.clicksColorPressed = new Setting(this, "CPS Color (Pressed)").setValue(-16777216).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.clicks.getBooleanValue() && this.leftClicks.getValue().equals("With Clicks") || this.rightClicks.getValue().equals("With Clicks"));

        this.spacebarOptionsLabel = new Setting(this, "label").setValue("Spacebar Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.spacebar::getBooleanValue);
        this.spacebarLine = new Setting(this, "Show Spacebar Line").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.spacebar::getBooleanValue);
        this.centerSpacebarLine = new Setting(this, "Center Spacebar Line").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.spacebar.getBooleanValue() && this.spacebarLine.getBooleanValue());
        this.spacebarLineWidth = new Setting(this, "Spacebar Line Width").setValue(4.0F).setMinMax(1.0F, 8.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> this.spacebar.getBooleanValue() && this.spacebarLine.getBooleanValue());
        this.spacebarLineHeight = new Setting(this, "Spacebar Line Height").setValue(1.0F).setMinMax(0.5F, 2.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> this.spacebar.getBooleanValue() && this.spacebarLine.getBooleanValue());
        this.spacebarHeight = new Setting(this, "Spacebar Height").setValue(50.0F).setMinMax(0.0F, 100.0F).setUnit("%").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(this.spacebar::getBooleanValue);

        this.sneakOptionsLabel = new Setting(this, "label").setValue("Sneak Options").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.sneak::getBooleanValue);
        this.sneakKeybindName = new Setting(this, "Show Sneak Keybind Name").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.sneak::getBooleanValue);
        this.sneakHeight = new Setting(this, "Sneak Box Height").setValue(72.0F).setMinMax(45.0F, 100.0F).setUnit("%").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(this.sneak::getBooleanValue);

        this.textOptionsLabel = new Setting(this, "label").setValue("Text Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textShadow = new Setting(this, "Text Shadow").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.literalKeys = new Setting(this, "Literal Keys").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.namesArrowSetting = new Setting(this, "Replace names with arrows").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.movementKeys::getBooleanValue);

        this.legacyPosition = new Setting(this, "Legacy Position").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.movementKeys.getBooleanValue() || this.clicks.getBooleanValue());

        this.backgroundOptionsLabel = new Setting(this, "label").setValue("Background Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.background = new Setting(this, "Show Background").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.border = new Setting(this, "Show Border").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.splitBoxSize = new Setting(this, "Split Box Size").setValue(false).onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.boxSize = new Setting(this, "Box size").setValue(18.0F).setMinMax(10.0F, 32.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.boxSizeHeight = new Setting(this, "Box size height").setValue(18.0F).setMinMax(10.0F, 32.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.splitBoxSize::getBooleanValue);
        this.gap = new Setting(this, "Gap").setValue(1.0F).setMinMax(0.0F, 4.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.borderThickness = new Setting(this, "Border Thickness").setValue(1.0F).setMinMax(0.0F, 3.0F).setUnit("px").onChange(value -> updateKeyElements()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.border::getBooleanValue);

        this.fadeOptionsLabel = new Setting(this, "label").setValue("Fade Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textFadeTime = new Setting(this, "Text Fade Time").setValue(0.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.backgroundFadeTime = new Setting(this, "Background Fade Time").setValue(75.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.background::getBooleanValue);
        this.borderFadeTime = new Setting(this, "Border Fade Time").setValue(100.0F).setMinMax(0.0F, 200.0F).setUnit("ms").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.border::getBooleanValue);

        this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textColorPressed = new Setting(this, "Text Color (Pressed)").setValue(-16777216).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x6F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.background::getBooleanValue);
        this.backgroundColorPressed = new Setting(this, "Background Color (Pressed)").setValue(0x6FFFFFFF).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.background::getBooleanValue);
        this.borderColor = new Setting(this, "Border Color").setValue(0x9F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.border::getBooleanValue);
        this.borderColorPressed = new Setting(this, "Border Color (Pressed)").setValue(0x9FFFFFFF).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.border::getBooleanValue);
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
        float gap = this.gap.getFloatValue();
        float borderThickness = this.borderThickness.getFloatValue();
        float bgHeight = this.backgroundHeight.getFloatValue();
        if (!this.border.getBooleanValue()) {
            borderThickness = 0.0F;
        }

        if (this.movementKeys.getBooleanValue()) {
            this.forwardElement.render(this.leftElement.getWidth() + gap + (borderThickness * 2.0F), 0.0F, this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            this.leftElement.render(0.0F, this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            this.backElement.render(this.leftElement.getWidth() + gap + (borderThickness * 2.0F), this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            this.rightElement.render(this.leftElement.getWidth() + this.backElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), this.forwardElement.getHeight() + gap + (borderThickness * 2.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            width = this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + (2.0F) * gap + (borderThickness * 2.0F);
            height += this.forwardElement.getHeight() + 2.0F * gap + this.backElement.getHeight();
        }

        if (this.clicks.getBooleanValue()) {
            this.attackButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            this.useButtonElement.render(this.attackButtonElement.getWidth() + gap + (borderThickness * 2.0F), height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += this.useButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if (this.spacebar.getBooleanValue()) {
            this.spaceButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            width = this.spaceButtonElement.getWidth();
            height += this.spaceButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if (this.sneak.getBooleanValue()) {
            this.sneakButtonElement.render(0.0F, height + (borderThickness * 4.0F), this.textColor.getColorValue(), this.textColorPressed.getColorValue(), this.backgroundColor.getColorValue(), this.backgroundColorPressed.getColorValue(), this.borderColor.getColorValue(), this.borderColorPressed.getColorValue(), this.textShadow.getBooleanValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += this.sneakButtonElement.getHeight() + gap + (borderThickness * 2.0F);
        }

        if (this.leftClicks.getValue().equals("Separate")) {
            String leftClicks = this.leftCPS.size() + " CPS";
            if (this.background.getBooleanValue()) {
                Gui.drawRect(0.0F, height + (borderThickness * 4.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), height + (borderThickness * 4.0F) + bgHeight, this.backgroundColor.getColorValue());
            }
            if (this.border.getBooleanValue()) {
                Gui.drawOutline(-borderThickness, height + (borderThickness * 3.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 5.0F), height + (borderThickness * 5.0F) + bgHeight, borderThickness, this.borderColor.getColorValue());
            }
            GL11.glEnable(GL11.GL_BLEND);
            this.mc.fontRenderer.drawString(leftClicks, this.width / 2.0F - (float) (this.mc.fontRenderer.getStringWidth(leftClicks) / 2) - 0.1F, height + (borderThickness * 4.0F) + (bgHeight / 2.0F - 3.49F), clicksColor.getColorValue(), this.textShadow.getBooleanValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += bgHeight + gap + (borderThickness * 2.0F);
        }

        if (this.rightClicks.getValue().equals("Separate")) {
            String leftClicks = this.rightCPS.size() + " RCPS";
            if (this.background.getBooleanValue()) {
                Gui.drawRect(0.0F, height + (borderThickness * 4.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 4.0F), height + (borderThickness * 4.0F) + bgHeight, this.backgroundColor.getColorValue());
            }
            if (this.border.getBooleanValue()) {
                Gui.drawOutline(-borderThickness, height + (borderThickness * 3.0F), this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * gap + (borderThickness * 5.0F), height + (borderThickness * 5.0F) + bgHeight, borderThickness, this.borderColor.getColorValue());
            }
            GL11.glEnable(GL11.GL_BLEND);
            this.mc.fontRenderer.drawString(leftClicks, this.width / 2.0F - (float) (this.mc.fontRenderer.getStringWidth(leftClicks) / 2) - 0.1F, height + (borderThickness * 4.0F) + (bgHeight / 2.0F - 3.49F), clicksColor.getColorValue(), this.textShadow.getBooleanValue());
            width = this.attackButtonElement.getWidth() + this.useButtonElement.getWidth() + gap + (borderThickness * 2.0F);
            height += bgHeight + gap + (borderThickness * 2.0F);
        }
        GL11.glDisable(GL11.GL_BLEND);
        
        this.setDimensions(width, height - gap + (borderThickness * 2.0F));
        GL11.glPopMatrix();
    }

    public void updateKeyElements() {
        int forwardKeyCode = this.mc.gameSettings.keyBindForward.getKeyCode();
        int leftKeyCode = this.mc.gameSettings.keyBindLeft.getKeyCode();
        int backKeyCode = this.mc.gameSettings.keyBindBack.getKeyCode();
        int rightKeyCode = this.mc.gameSettings.keyBindRight.getKeyCode();
        int jumpKeyCode = this.mc.gameSettings.keyBindJump.getKeyCode();
        int attackKeyCode = this.mc.gameSettings.keyBindAttack.getKeyCode();
        int useItemKeyCode = this.mc.gameSettings.keyBindUseItem.getKeyCode();
        int sneakKeyBind = this.mc.gameSettings.keyBindSneak.getKeyCode();

        boolean arrowKeys = this.namesArrowSetting.getBooleanValue();
        float borderThickness = this.borderThickness.getFloatValue();
        if (!this.border.getBooleanValue()) {
            borderThickness = 0.0F;
        }

        float boxSize = this.boxSize.getFloatValue();
        float boxSize2 = this.splitBoxSize.getBooleanValue() ? this.boxSizeHeight.getFloatValue() : this.boxSize.getFloatValue();

        String w = this.getKeyOrMouseName(forwardKeyCode);
        String a = this.getKeyOrMouseName(leftKeyCode);
        String s = this.getKeyOrMouseName(backKeyCode);
        String d = this.getKeyOrMouseName(rightKeyCode);
        String jump = this.getKeyOrMouseName(jumpKeyCode);
        String leftClick = this.getKeyOrMouseName(attackKeyCode);
        String rightClick = this.getKeyOrMouseName(useItemKeyCode);
        String sneak = this.sneakKeybindName.getBooleanValue() ? this.getKeyOrMouseName(sneakKeyBind) : "SNEAK";

        float upKeyWidth = (float) this.mc.fontRenderer.getStringWidth(w) * this.scale.getFloatValue();
        float leftKeyWidth = (float) this.mc.fontRenderer.getStringWidth(a) * this.scale.getFloatValue();
        float downKeyWidth = (float) this.mc.fontRenderer.getStringWidth(s) * this.scale.getFloatValue();
        float rightKeyWidth = (float) this.mc.fontRenderer.getStringWidth(d) * this.scale.getFloatValue();
        float jumpKeyWidth = (float) this.mc.fontRenderer.getStringWidth(jump) * this.scale.getFloatValue();
        float leftClickKeyWidth = (float) this.mc.fontRenderer.getStringWidth(leftClick) * this.scale.getFloatValue();
        float rightClickKeyWidth = (float) this.mc.fontRenderer.getStringWidth(rightClick) * this.scale.getFloatValue();
        float sneakKeyWidth = (float) this.mc.fontRenderer.getStringWidth(sneak) * this.scale.getFloatValue();

        this.forwardElement = new Key(arrowKeys ? "▲" : (upKeyWidth > boxSize ? w.substring(0, 1) : w), forwardKeyCode, boxSize, boxSize2);
        this.leftElement = new Key(arrowKeys ? "◀" : (leftKeyWidth > boxSize ? a.substring(0, 1) : a), leftKeyCode, boxSize, boxSize2);
        this.backElement = new Key(arrowKeys ? "▼" : (downKeyWidth > boxSize ? s.substring(0, 1) : s), backKeyCode, boxSize, boxSize2);
        this.rightElement = new Key(arrowKeys ? "▶" : (rightKeyWidth > boxSize ? d.substring(0, 1) : d), rightKeyCode, boxSize, boxSize2);

        float mouseBoxWidth = (this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + this.gap.getFloatValue() + (borderThickness * 2.0F)) / 2.0f;
        float fullBoxWidth = this.leftElement.getWidth() + this.backElement.getWidth() + this.rightElement.getWidth() + 2.0F * this.gap.getFloatValue() + (borderThickness * 4.0F);

        this.attackButtonElement = new Key(leftClickKeyWidth > mouseBoxWidth ? leftClick.substring(0, 1) : leftClick, attackKeyCode, mouseBoxWidth, boxSize2);
        this.useButtonElement = new Key(rightClickKeyWidth > mouseBoxWidth ? rightClick.substring(0, 1) : rightClick, useItemKeyCode, mouseBoxWidth, boxSize2);
        this.spaceButtonElement = new Key(jumpKeyWidth > fullBoxWidth ? jump.substring(0, 1) : jump, jumpKeyCode, fullBoxWidth, boxSize2 * this.spacebarHeight.getFloatValue() / 100.0f);
        this.sneakButtonElement = new Key(sneakKeyWidth > fullBoxWidth ? sneak.substring(0, 1) : sneak, sneakKeyBind, fullBoxWidth, boxSize2 * this.sneakHeight.getFloatValue() / 100.0f);
    }

    private void onClick(ClickEvent event) {
        if (event.getMouseButton() == 0) leftCPS.add(System.currentTimeMillis());
        if (event.getMouseButton() == 1) rightCPS.add(System.currentTimeMillis());
    }

    protected String getKeyOrMouseName(int keyCode) {
        if (keyCode < 0) {
            String openglName = Mouse.getButtonName(keyCode + 100);
            if (openglName != null) {
                if (openglName.equalsIgnoreCase("button0")) {
                    return "LMB";
                }
                if (openglName.equalsIgnoreCase("button1")) {
                    return "RMB";
                }
            }
            return openglName;
        }
        if (this.literalKeys.getBooleanValue()) {
            switch (keyCode) {
                case 41: {
                    return "~";
                }
                case 12:
                case 74: {
                    return "-";
                }
                case 40: {
                    return "'";
                }
                case 26: {
                    return "[";
                }
                case 27: {
                    return "]";
                }
                case 43: {
                    return "\\";
                }
                case 53:
                case 181: {
                    return "/";
                }
                case 51: {
                    return ",";
                }
                case 52: {
                    return ".";
                }
                case 39: {
                    return ";";
                }
                case 13: {
                    return "=";
                }
                case 200: {
                    return "▲";
                }
                case 208: {
                    return "▼";
                }
                case 203: {
                    return "◀";
                }
                case 205: {
                    return "▶";
                }
                case 55: {
                    return "*";
                }
                case 78: {
                    return "+";
                }
            }
        }
        return Keyboard.getKeyName(keyCode);
    }
}
