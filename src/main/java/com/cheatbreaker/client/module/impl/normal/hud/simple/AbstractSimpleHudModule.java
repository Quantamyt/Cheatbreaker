package com.cheatbreaker.client.module.impl.normal.hud.simple;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModExclusionRange;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModSize;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Defines the necessary options and rendering for all Simple Modules.
 * @see AbstractModule
 */
public abstract class AbstractSimpleHudModule extends AbstractModule {

    protected Setting background;
    protected Setting staticBackgroundWidth;
    protected Setting textOverflow;
    protected Setting padding;
    protected Setting backgroundWidth;
    protected Setting backgroundHeight;
    protected Setting border;
    protected Setting borderThickness;
    protected Setting borderColor;
    protected Setting showWhileTyping;
    protected Setting alwaysCenter;
    protected Setting textShadowBackground;
    protected Setting textShadowNoBackground;

    public Setting hideValue;
    public Setting hiddenValue;

    protected Setting textColor;
    protected Setting backgroundColor;
    private Setting icon;
    protected ResourceLocation iconTexture;
    protected Setting customString;
    protected Setting customStringNoBackground;

    public AbstractSimpleHudModule(String name) {
        this(name, name, 1.5F, false, true);
    }

    public AbstractSimpleHudModule(String name, String previewString) {
        this(name, previewString, 1.5F, true, false);
    }

    public AbstractSimpleHudModule(String name, String previewString, float previewScale, boolean showBackground, boolean alwaysCenter) {
        super(name);
        this.setDefaultAnchor(GuiAnchor.RIGHT_TOP);
        this.setDefaultTranslations(0.0F, 0.0F);
        this.setDefaultState(false);

        new Setting(this, "label").setValue("Background Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.background = new Setting(this, "Show Background", "Draw a background.").setValue(showBackground).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.staticBackgroundWidth = new Setting(this, "Static Background Width",
                "§2Enabled:§r Background width is set by a slider.\n" +
                        "§4Disabled:§r Background width is set by the text size.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (boolean) this.background.getValue() || (boolean) this.alwaysCenter.getValue());
        this.textOverflow = new Setting(this, "Text Overflow",
                "What the mod will do if the text overflows from the background" +
                        "\n§bExtend Width:§r Increases the background width." +
                        "\n§bScale Text:§r Scales the text down." +
                        "\n§bDo Nothing:§r Does no action.")
                .setValue("Extend Width").acceptedStringValues("Extend Width", "Scale Text", "Do Nothing").setCondition(() -> (this.background.getBooleanValue() || this.alwaysCenter.getBooleanValue()) && this.staticBackgroundWidth.getBooleanValue()).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.border = new Setting(this, "Show Border", "Draw a border around the background.").setValue(false).setCondition(() -> (boolean) this.background.getValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.padding = new Setting(this, "Background Width Padding", "Change how spaced the background width is from the text.").setValue(6.0F).setMinMax(0.0F, 10.0F).setUnit("px")
                .setCondition(() -> ((boolean) this.background.getValue() || (boolean) this.alwaysCenter.getValue()) && (!(boolean) this.staticBackgroundWidth.getValue() || this.textOverflow.getValue().equals("Extend Background")))
                .setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.backgroundWidth = new Setting(this, "Background Width", "Change the width of the background.").setValue(this.backgroundDimensionValues().getDefWidth()).setMinMax(this.backgroundDimensionValues().getMinWidth(), this.backgroundDimensionValues().getMaxWidth()).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> ((boolean) this.background.getValue() || (boolean) this.alwaysCenter.getValue()) && (boolean) this.staticBackgroundWidth.getValue());
        this.backgroundHeight = new Setting(this, "Background Height", "Change the height of the background.").setValue(this.backgroundDimensionValues().getDefHeight()).setMinMax(this.backgroundDimensionValues().getMinHeight(), this.backgroundDimensionValues().getMaxHeight()).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (boolean) this.background.getValue() || (boolean) this.alwaysCenter.getValue());
        this.borderThickness = new Setting(this, "Border Thickness", "Change the thickness of the border.").setValue(1.0F).setMinMax(0.25F, 3.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (boolean) this.background.getValue() && (boolean) this.border.getValue());

        new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.showWhileTyping = new Setting(this, "Show While Typing", "Show the mod when opening chat.").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.alwaysCenter = new Setting(this, "Always Center", "Force the text to be centered in the mod's placement.").setValue(alwaysCenter).setCondition(() -> !this.background.getBooleanValue()).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.icon = new Setting(this, "Show icon", "Show an icon corresponding to the mod.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.iconTexture != null);
        this.hideValue = new Setting(this, "Hide when value is").setValue("OFF").acceptedStringValues(this.excludeOptions).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.iconTexture == null && !name.equals("Clock"));
        this.hiddenValue = new Setting(this, "Hidden value(s)").setValue(this.exclusionRange().getDefValue()).setMinMax(this.exclusionRange().getMinValue(), this.exclusionRange().getMaxValue()).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !this.hideValue.getValue().equals("OFF") && this.iconTexture == null && !name.equals("Clock"));
        this.getExtraSettings();
        new Setting(this, "label").setValue("Format Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.textShadowBackground = new Setting(this, "Text Shadow (Background)", "Add a text shadow when the background is enabled.").setValue(false).setCondition(() -> this.background.getBooleanValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textShadowNoBackground = new Setting(this, "Text Shadow (No background)", "Add a text shadow when the background is disabled.").setValue(true).setCondition(() -> !this.background.getBooleanValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.getExtraFormatSettings();
        this.customString = new Setting(this, "Format (Background)").setValue(getDefaultFormatString()).setCondition(() -> (boolean) this.background.getValue()).setCustomizationLevel(CustomizationLevel.ADVANCED);
        if (isBracketsEnabledByDefault()) {
            this.customStringNoBackground = new Setting(this, "Format (No Background)").setValue("[" + getDefaultFormatString() + "]").setCondition(() -> !(boolean) this.background.getValue()).setCustomizationLevel(CustomizationLevel.ADVANCED);
        } else {
            this.customStringNoBackground = new Setting(this, "Format (No Background)").setValue(getDefaultFormatString()).setCondition(() -> !(boolean) this.background.getValue()).setCustomizationLevel(CustomizationLevel.ADVANCED);
        }


        new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textColor = new Setting(this, "Text Color", "Sets the color for the text.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> !(name.equals("Ping") && (Boolean) CheatBreaker.getInstance().getModuleManager().pingMod.dynamicColorRange.getValue()) && !(name.equals("Saturation") && (Boolean) CheatBreaker.getInstance().getModuleManager().saturationMod.amountColors.getValue()));
        this.getExtraColorSettings();
        this.backgroundColor = new Setting(this, "Background Color", "Sets the color for the background.").setValue(1862270976).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (boolean) this.background.getValue());
        this.borderColor = new Setting(this, "Border Color", "Sets the color for the border.").setValue(-1627389952).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (boolean) this.background.getValue() && (boolean) this.border.getValue());
        this.setPreviewLabel(previewString, previewScale);
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    public void getExtraSettings() {

    }

    public void getExtraFormatSettings() {

    }

    public void getExtraColorSettings() {

    }

    /**
     * Returns the default background dimension values.
     */
    public SimpleHudModSize backgroundDimensionValues() {
        return new SimpleHudModSize(10, 13, 24, 40, 56, 80);
    }

    /**
     * Returns the default exclusion range values.
     */
    public SimpleHudModExclusionRange exclusionRange() {
        return new SimpleHudModExclusionRange(0, 0, 30);
    }

    /**
     * Sets the abstract value string.
     * <p>
     * The actual modules will return the intended value(s).
     */
    public abstract String getValueString();

    /**
     * Sets the abstract label string.
     * <p>
     * The actual modules will return the intended label.
     */
    public abstract String getLabelString();

    /**
     * Returns a string in which will display when the mod is in a "waiting" state. If the string is empty or null, the waiting message will not be displayed.
     */
    public String getWaitingMessage() {
        return null;
    }

    /**
     * Returns the default simple mod format.
     */
    public String getDefaultFormatString() {
        return "%VALUE% %LABEL%";
    }

    /**
     * Returns a string in which will display when the mod is in a "preview" state. This string will be displayed when the "real" string is null.
     */
    public String getPreviewString() {
        return "";
    }

    /**
     * Returns true if a mod should include brackets in the No Background mode by default.
     */
    public boolean isBracketsEnabledByDefault() {
        return true;
    }

    /**
     * Returns the actual path for the icon texture. The icon will not display if the path is null.
     */
    public ResourceLocation getIconTexture() {
        return null;
    }

    /**
     * Returns the active color.
     */
    public int getColor() {
        return this.textColor.getColorValue();
    }

    /**
     * Shows the preview version of the mod when in the HUD Editor. For Simple Mods, this will show when the "real" string is null.
     */
    private void onPreviewDraw(PreviewDrawEvent event) {
        if (this.isRenderHud()) {
            if (this.getValueString() == null) {
                GL11.glPushMatrix();
                this.scaleAndTranslate(event.getScaledResolution());
                render(getPreviewString());
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Shows the actual mod itself.
     */
    private void onGuiDraw(GuiDrawEvent event) {
        String value = this.getValueString();
        if (value != null && (!this.mc.ingameGUI.getChatGUI().getChatOpen() || (Boolean) this.showWhileTyping.getValue())) {
            if (this.isRenderHud() && (!this.hiddenFromHud || (this.mc.currentScreen instanceof HudLayoutEditorGui))) {
                GL11.glPushMatrix();
                this.scaleAndTranslate(event.getScaledResolution());
                if (value.isEmpty()) {
                    value = "";
                }
                render(value);
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Actually calls the necessary methods to render the mod.
     */
    public void render(String value) {
        boolean showIcon = this.icon.getBooleanValue() && iconTexture != null;
        String label = this.getLabelString();
        if (label == null || label.isEmpty()) {
            label = "";
        }

        String counter;
        if (getWaitingMessage() != null && getValueString() != null) {
            counter = getWaitingMessage();
        } else if (!label.equals("")) {
            if (!showIcon && !(Boolean)this.background.getValue()) {
                counter = this.customStringNoBackground.getValue().toString().replaceAll("%LABEL%", label).replaceAll("%VALUE%", value);
            } else {
                counter = this.customString.getValue().toString().replaceAll("%LABEL%", label).replaceAll("%VALUE%", value);
            }
        } else {
            counter = value;
        }



        iconTexture = getIconTexture();
        float width = (float)mc.fontRenderer.getStringWidth(counter);
        float bHeight = (Float) this.backgroundHeight.getValue();
        float extraX = showIcon ? bHeight : 0.0F;
        float bWidth = !(Boolean) staticBackgroundWidth.getValue() ? width + (float)padding.getValue() + extraX : (Float) this.backgroundWidth.getValue();

        if (this.textOverflow.getValue().equals("Extend Width") && (Boolean) this.staticBackgroundWidth.getValue()) {
            bWidth = Math.max((Float) this.backgroundWidth.getValue(), width + (float) padding.getValue() + extraX);
        }

        if ((Boolean) this.background.getValue() || showIcon || (Boolean) this.alwaysCenter.getValue()) {
            this.setDimensions(bWidth, bHeight);
            if ((Boolean)this.background.getValue()) {
                Gui.drawRect(0.0F, 0.0F, bWidth, bHeight, this.backgroundColor.getColorValue());
                if ((Boolean) this.border.getValue()) {
                    float borderThickness = (Float) this.borderThickness.getValue();
                    Gui.drawOutline(-borderThickness, -borderThickness, bWidth + borderThickness, bHeight + borderThickness, borderThickness, this.borderColor.getColorValue());
                }
            }

            if (showIcon) {
                GL11.glColor3f(1.0F,1.0F,1.0F);
                RenderUtil.renderEIcon(iconTexture, bHeight / 2.0f, 0, 0);
            }
            GL11.glEnable(GL11.GL_BLEND);
            float scale = 1.0F;
            if (this.mc.fontRenderer.getStringWidth(counter) > bWidth - this.padding.getFloatValue() - extraX && this.textOverflow.getValue().equals("Scale Text")) {
                scale = (bWidth - this.padding.getFloatValue() - extraX) / this.mc.fontRenderer.getStringWidth(counter);
                GL11.glScalef(scale, scale, 1.0F);
                scale = this.mc.fontRenderer.getStringWidth(counter) / (bWidth - this.padding.getFloatValue() - extraX);
            }
            this.mc.fontRenderer.drawString(counter, this.width / 2.0F * scale - (float) (this.mc.fontRenderer.getStringWidth(counter) / 2) + extraX * scale / 2.0F + 0.6F, bHeight / 2.0F * scale - 3.49F, getColor(), (Boolean) this.background.getValue() ? (Boolean) this.textShadowBackground.getValue() : (Boolean) this.textShadowNoBackground.getValue());
            if (this.mc.fontRenderer.getStringWidth(counter) > bWidth) {
                GL11.glScalef(scale, scale, 1.0F);
            }
        } else {
            GL11.glEnable(GL11.GL_BLEND);
            this.setDimensions((float)this.mc.fontRenderer.drawString(counter, 0.0F, 0.0F, getColor(), (Boolean) this.textShadowNoBackground.getValue()), mc.fontRenderer.FONT_HEIGHT);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }
}
