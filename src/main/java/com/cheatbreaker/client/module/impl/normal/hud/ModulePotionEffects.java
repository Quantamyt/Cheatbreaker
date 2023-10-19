package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.render.PreviewDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.*;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.regex.Pattern;

public class ModulePotionEffects extends AbstractModule {
    private final Setting componentOptionsLabel;
    private final Setting showIcon;
    private final Setting effectName;
    private final Setting effectNameText;
    private final Setting effectAmplifierText;
    private final Setting romanNumerals;
    private final Setting showLevelOne;
    private final Setting maximumRomanNumeral;
    private final Setting duration;

    private final Setting generalOptionsLabel;
    private final Setting showWhileTyping;
    public Setting potionInfoInInventory;
    private final Setting textShadow;
    private final Setting effectSpacing;

    private final Setting sortingOptionsLabel;
    private final Setting sorting;
    private final Setting reverseSorting;

    private final Setting colorOptionsLabel;
    private final Setting nameColor;
    private final Setting durationColor;

    private final Setting blinkOptionsLabel;
    private final Setting effectNameBlink;
    private final Setting effectIconBlink;
    private final Setting durationBlink;
    private final Setting blink;
    private final Setting blinkDuration;
    private final Setting blinkSpeed;

    private final Setting colorNameBasedOnEffect;
    private final Setting colorDurationBasedOnEffect;
    private final Setting texturePackOverrideColors;
    private final Setting colorType;

    private final Setting excludingOptionsLabel;
    private final Setting excludeSetDuration;
    private final Setting excludedDuration;
    private final Setting excludePerm;
    private final Setting excludeSetAmplifier;
    private final Setting excludedAmplifier;
    private final Setting excludeHelpfulHarmfulEffects;
    private final Setting excludeSpecificEffects;

    private final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    private int ticks = 0;
    public static Map<Integer, Integer> potionColorDefault = new ImmutableMap.Builder<Integer, Integer>().put(1, -11141121).put(2, -10851199)
            .put(3, -2506685).put(4, -11910633).put(5, -7134173).put(6, 0xFFF82423).put(7, 0xFF430A09).put(8, -1).put(9, -11199158).put(10, -3318613).put(11, 0xFF53C653).put(12, -1795526)
            .put(13, -13741415).put(14, -8420462).put(15, -14737629).put(16, -14737503).put(17, -10979757)
            .put(18, -12038840).put(19, -11627727).put(20, -13293017).put(21, 0xFFDE170A).put(22, -14331227).put(23, 0xFFF8C123).build();
    public static Map<Integer, Integer> potionColorPotionColors = new ImmutableMap.Builder<Integer, Integer>().put(1, 0xFF7CAFC6).put(2, -10851199)
            .put(3, -2506685).put(4, -11910633).put(5, -7134173).put(6, 0xFFF82423).put(7, 0xFF430A09).put(8, 0xFF786297).put(9, -11199158).put(10, -3318613).put(11, 0xFF99453A).put(12, -1795526)
            .put(13, -13741415).put(14, -8420462).put(15, -14737629).put(16, -14737503).put(17, -10979757)
            .put(18, -12038840).put(19, -11627727).put(20, -13293017).put(21, 0xFFF87D23).put(22, -14331227).put(23, 0xFFF82423).build();
    public static Map<Integer, Integer> potionColorColorCodes = new ImmutableMap.Builder<Integer, Integer>().put(1, -11141121).put(2, 0xFF555555)
            .put(3, 0xFFFFFF55).put(4, 0xFF555555).put(5, 0xFFAA0000).put(6, 0xFFFF5555).put(7, 0xFFAA0000).put(8, -1).put(9, 0xFFAA00AA).put(10, 0xFFFF55FF).put(11, 0xFFAAAAAA).put(12, 0xFFFFAA00)
            .put(13, 0xFF5555FF).put(14, 0xFFAAAAAA).put(15, 0xFF000000).put(16, 0xFF0000AA).put(17, 0xFF00AA00)
            .put(18, 0xFF555555).put(19, 0xFF55FF55).put(20, 0xFF000000).put(21, 0xFF5555).put(22, 0xFFFFFF55).put(23, 0xFFFFFF55).build();
    public static Map<Integer, Integer> potionColorsVibrant = new ImmutableMap.Builder<Integer, Integer>().put(1, -7405569).put(2, -10528630)
            .put(3, -10240).put(4, -9728589).put(5, -5177344).put(6, 0xFFF15251).put(7, 0xFF790907).put(8, -2097153).put(9, -1).put(10, -36483).put(11, -11511440)
            .put(12, -24259).put(13, -7229441).put(14, -5592406).put(15, -7975034).put(16, -14263332)
            .put(17, -11818424).put(18, -9474163).put(19, -14689758).put(20, -5276243).put(21, 0xFFF83023).put(22, -7866).put(23, 0xFFFFFF99).build();

    public ModulePotionEffects() {
        super("Potion Effects");
        this.setDefaultState(false);
        this.setDefaultAnchor(GuiAnchor.LEFT_MIDDLE);

        this.componentOptionsLabel = new Setting(this, "label").setValue("Component Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.showIcon = new Setting(this, "Icon").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.effectName = new Setting(this, "Effect Name").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.effectNameText = new Setting(this, "Effect Name Text").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.effectName::getBooleanValue);
        this.effectAmplifierText = new Setting(this, "Effect Amplifier Text").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.effectName::getBooleanValue);
        this.romanNumerals = new Setting(this, "Show Roman Numerals").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.effectName.getBooleanValue() && this.effectAmplifierText.getBooleanValue());
        this.showLevelOne = new Setting(this, "Show Level 1").setValue(false).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> this.effectName.getBooleanValue() && this.effectAmplifierText.getBooleanValue());
        this.maximumRomanNumeral = new Setting(this, "Max Roman Numeral").setValue(10).setMinMax(1, 255).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> this.effectName.getBooleanValue() && this.effectAmplifierText.getBooleanValue() && this.romanNumerals.getBooleanValue());
        this.duration = new Setting(this, "Duration").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.generalOptionsLabel = new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.showWhileTyping = new Setting(this, "Show While Typing").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.potionInfoInInventory = new Setting(this, "Show Potion info in inventory").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textShadow = new Setting(this, "Text Shadow").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.effectName.getBooleanValue() || this.duration.getBooleanValue());
        this.effectSpacing = new Setting(this, "Effect Spacing").setValue(4.0F).setMinMax(0.0F, 15.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.sortingOptionsLabel = new Setting(this, "label").setValue("Sorting Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.sorting = new Setting(this, "Sorting Method").setValue("Vanilla").acceptedStringValues("Vanilla", "Alphabetical", "Duration", "Amplifier").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.reverseSorting = new Setting(this, "Reverse Sorting").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.blinkOptionsLabel = new Setting(this, "label").setValue("Blink Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.blink = new Setting(this, "Blink").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.effectNameBlink = new Setting(this, "Make Effect Name Text Blink").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.blink.getBooleanValue() && this.effectName.getBooleanValue());
        this.effectIconBlink = new Setting(this, "Make Effect Icon Blink").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.blink.getBooleanValue() && this.showIcon.getBooleanValue());
        this.durationBlink = new Setting(this, "Make Duration Text Blink").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.blink.getBooleanValue() && this.duration.getBooleanValue());
        this.blinkDuration = new Setting(this, "Blink Duration").setValue(10.0F).setMinMax(2.0F, 60.0F).setUnit("s").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(this.blink::getBooleanValue);
        this.blinkSpeed = new Setting(this, "Blink Speed").setValue(1.0f).setMinMax(0.2f, 3.5f).setUnit("s").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.blink::getBooleanValue);

        this.excludingOptionsLabel = new Setting(this, "label").setValue("Excluding Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.excludePerm = new Setting(this, "Exclude Permanent Effects").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.excludeSetDuration = new Setting(this, "Exclude Set Duration", "Exclude all active effects above a set duration.").setValue("OFF").acceptedStringValues("OFF", "Effects Above", "Effects Below", "Effects At", "Effects Not At").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.excludedDuration = new Setting(this, "Excluded Duration", "The duration effects are excluded").setValue(30.0F).setMinMax(2.0F, 90.0F).setUnit("s").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !this.excludeSetDuration.getValue().equals("OFF"));
        this.excludeSetAmplifier = new Setting(this, "Exclude Set Amplifier", "Exclude all active effects below a set amplifier.").setValue("OFF").acceptedStringValues("OFF", "Effects Above", "Effects Below", "Effects At", "Effects Not At").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.excludedAmplifier = new Setting(this, "Excluded Amplifier", "The duration effects are excluded").setValue(10).setMinMax(0, 20).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !this.excludeSetAmplifier.getValue().equals("OFF"));
        this.excludeHelpfulHarmfulEffects = new Setting(this, "Exclude Helpful/Harmful Effects").setValue("OFF").acceptedStringValues("OFF", "Only Helpful", "Only Harmful").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.excludeSpecificEffects = new Setting(this, "Exclude Specific Effects").setValue(new ArrayList<>()).acceptedValues(new Integer[]{Potion.moveSpeed.id, Potion.moveSlowdown.id, Potion.digSpeed.id, Potion.digSlowdown.id, Potion.damageBoost.id, Potion.jump.id, Potion.confusion.id, Potion.regeneration.id, Potion.resistance.id, Potion.fireResistance.id, Potion.waterBreathing.id, Potion.invisibility.id, Potion.blindness.id, Potion.nightVision.id, Potion.hunger.id, Potion.weakness.id, Potion.poison.id, Potion.wither.id, Potion.absorption.id}).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.effectName.getBooleanValue() || this.duration.getBooleanValue());
        this.texturePackOverrideColors = new Setting(this, "Override Name Color With Pack Format").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.effectName.getBooleanValue() && this.effectNameText.getBooleanValue());
        this.colorNameBasedOnEffect = new Setting(this, "Color Name Based On").setValue("Static").acceptedStringValues("Static", "Effect", "Duration").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.effectName::getBooleanValue);
        this.colorDurationBasedOnEffect = new Setting(this, "Color Duration Based On").setValue("Static").acceptedStringValues("Static", "Effect", "Duration").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(this.duration::getBooleanValue);
        this.colorType = new Setting(this, "Color Type").setValue("Default").acceptedStringValues("Default", "Potion Colors", "Color Codes", "Good/Bad", "Vibrant").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.colorDurationBasedOnEffect.getValue().equals("Effect") || this.colorNameBasedOnEffect.getValue().equals("Effect"));
        this.nameColor = new Setting(this, "Name Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.effectName.getBooleanValue() && this.colorNameBasedOnEffect.getValue().equals("Static"));
        this.durationColor = new Setting(this, "Duration Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.duration.getBooleanValue() && this.colorDurationBasedOnEffect.getValue().equals("Static"));
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/speed_icon.png"), 28, 28);
        this.setDescription("Displays your active potion effects.");
        this.setCreators("bspkrs", "jadedcat");
        this.setAliases("Potion Status", "Status Effects");
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    private void onTick(TickEvent event) {
        ++this.ticks;
    }

    private void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        GL11.glPushMatrix();
        List<PotionEffect> potionEffects = new ArrayList<>();
        if (this.mc.thePlayer != null) {
            potionEffects.addAll(this.mc.thePlayer.getActivePotionEffects());
        }
        if (potionEffects.isEmpty()) {
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            potionEffects.add(new PotionEffect(Potion.moveSpeed.id, 1200, 3));
            potionEffects.add(new PotionEffect(Potion.damageBoost.id, 30, 3));
            info(potionEffects);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        if (this.showWhileTyping.getBooleanValue() || !this.mc.ingameGUI.getChatGUI().getChatOpen()) {
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            List<PotionEffect> potionEffects = new ArrayList<>();
            if (this.mc.thePlayer != null) {
                potionEffects.addAll(this.mc.thePlayer.getActivePotionEffects());
            }
            if (potionEffects.isEmpty()) {
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                return;
            }
            info(potionEffects);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    private void info(List<PotionEffect> effects) {
        GlStateManager.enableBlend();
        Position cBPositionEnum = this.getPosition();
        int height = 0;
        int width = 0;
        float potionSpacing = this.effectSpacing.getFloatValue();
        float spacing = potionSpacing + (this.showIcon.getBooleanValue() || this.effectName.getBooleanValue() && this.duration.getBooleanValue() ? 18.0f : this.mc.fontRendererObj.FONT_HEIGHT - 1);
        float iconPos = this.showIcon.getBooleanValue() ? 20.0F : 0.0F;
        this.sortEffects(effects);

        for (PotionEffect potionEffect : effects) {
            if (!excludePotions(potionEffect) || this.mc.currentScreen instanceof HudLayoutEditorGui || this.mc.currentScreen instanceof ModulePlaceGui || this.mc.currentScreen instanceof ProfileCreatorGui) {
                Potion potion;
                String string;
                boolean showingDuringBlink = this.showEffectDuringBlink(potionEffect.getDuration());
                boolean packOverride = this.texturePackOverrideColors.getBooleanValue();
                int effectWidth = 0;
                int effectY = this.duration.getBooleanValue() ? 0 : this.showIcon.getBooleanValue() ? 5 : 0;
                String effectName = (this.effectNameText.getBooleanValue() ? StatCollector.translateToLocal(potionEffect.getEffectName()) : "") + (this.effectAmplifierText.getBooleanValue() ? this.amplifierNumerals(potionEffect.getAmplifier()) : "");
                if (this.effectName.getBooleanValue() && (showingDuringBlink || !this.effectNameBlink.getBooleanValue())) {
                    effectWidth = this.mc.fontRendererObj.getStringWidth(effectName) + (int) iconPos;
                    int potionColor = potionColor(this.nameColor.getColorValue(), this.colorNameBasedOnEffect.getValue(), potionEffect);
                    if (cBPositionEnum == Position.RIGHT) {
                        this.mc.fontRendererObj.drawString((packOverride ? effectName : stripColor(effectName)) + "§r", this.getWidth() - (float) effectWidth, (float) height + effectY, potionColor, this.textShadow.getBooleanValue());
                    } else if (cBPositionEnum == Position.LEFT) {
                        this.mc.fontRendererObj.drawString((packOverride ? effectName : stripColor(effectName)) + "§r", iconPos, (float) height + effectY, potionColor, this.textShadow.getBooleanValue());
                    } else if (cBPositionEnum == Position.CENTER) {
                        this.mc.fontRendererObj.drawString((packOverride ? effectName : stripColor(effectName)) + "§r", this.getWidth() / 2.0f - (float) (effectWidth / 2) + iconPos, (float) height + effectY, potionColor, this.textShadow.getBooleanValue());
                    }
                    if (effectWidth > width) {
                        width = effectWidth;
                    }
                }
                string = Potion.getDurationString(potionEffect);
                int durationWidth = this.duration.getBooleanValue() ? this.mc.fontRendererObj.getStringWidth(string) + (int) iconPos : 18;
                int durationY = this.effectName.getBooleanValue() && !effectName.isEmpty() ? 10 : this.showIcon.getBooleanValue() ? 5 : 0;
                if (this.duration.getBooleanValue() && (showingDuringBlink || !this.durationBlink.getBooleanValue())) {
                    int potionColor = potionColor(this.durationColor.getColorValue(), this.colorDurationBasedOnEffect.getValue(), potionEffect);
                    if (cBPositionEnum == Position.RIGHT) {
                        this.mc.fontRendererObj.drawString(string + "§r", this.getWidth() - (float) durationWidth, (float) (height + durationY), potionColor, this.textShadow.getBooleanValue());
                    } else if (cBPositionEnum == Position.LEFT) {
                        this.mc.fontRendererObj.drawString(string + "§r", iconPos, (float) (height + durationY), potionColor, this.textShadow.getBooleanValue());
                    } else if (cBPositionEnum == Position.CENTER) {
                        this.mc.fontRendererObj.drawString(string + "§r", this.getWidth() / 2.0f - (float) (durationWidth / 2) + iconPos, (float) (height + durationY), potionColor, this.textShadow.getBooleanValue());
                    }
                }
                if ((potion = Potion.potionTypes[potionEffect.getPotionID()]).hasStatusIcon() && this.showIcon.getBooleanValue() && (showingDuringBlink || !this.effectIconBlink.getBooleanValue())) {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, excludePotions(potionEffect) ? 0.5F : 1.0F);
                    this.mc.getTextureManager().bindTexture(this.inventoryBackground);
                    int index = potion.getStatusIconIndex();
                    if (cBPositionEnum == Position.LEFT || !this.effectName.getBooleanValue() && !this.duration.getBooleanValue()) {
                        RenderUtil.drawTexturedModalRect(0.0f, (float) height, (float) (index % 8 * 18), (float) (198 + index / 8 * 18), 18, 18);
                    } else if (cBPositionEnum == Position.RIGHT) {
                        RenderUtil.drawTexturedModalRect(this.getWidth() - (float) 20, (float) height, (float) (index % 8 * 18), (float) (198 + index / 8 * 18), 18, 18);
                    } else if (cBPositionEnum == Position.CENTER) {
                        RenderUtil.drawTexturedModalRect(this.getWidth() / 2.0f - (float) ((this.effectName.getBooleanValue() ? effectWidth : durationWidth) / 2), (float) height, (float) (index % 8 * 18), (float) (198 + index / 8 * 18), 18, 18);
                    }
                }
                if (durationWidth > width) {
                    width = durationWidth;
                }
                height += spacing;
            }
        }
        this.setDimensions(width, height - potionSpacing);
        GlStateManager.disableBlend();
    }

    private boolean showEffectDuringBlink(float duration) {
        float blinkSpeed = this.blinkSpeed.getFloatValue() * 10;
        if (this.blink.getBooleanValue() && duration <= this.blinkDuration.getFloatValue() * 20.0F) {
            if (this.ticks > blinkSpeed * 2) {
                this.ticks = 0;
            }
            return this.ticks <= blinkSpeed;
        }
        return true;
    }

    private int potionColor(int staticColor, Object option, PotionEffect effects) {
        int time = effects.getDuration();
        int value;
        switch ((String) option) {
            case "Effect":
                Potion var9 = Potion.potionTypes[effects.getPotionID()];
                switch (this.colorType.getStringValue()) {
                    case "Default":
                        value = potionColorDefault.get(effects.getPotionID());
                        break;
                    case "Potion Colors":
                        value = potionColorPotionColors.get(effects.getPotionID());
                        break;
                    case "Color Codes":
                        value = potionColorColorCodes.get(effects.getPotionID());
                        break;
                    case "Vibrant":
                        value = potionColorsVibrant.get(effects.getPotionID());
                        break;
                    default:
                        if (!var9.isBadEffect()) {
                            value = -15691760;
                        } else {
                            value = -7335920;
                        }
                }
                break;
            case "Duration":
                if (time >= 1200) {
                    value = -16733696;
                } else if (time >= 600) {
                    value = -11141291;
                } else if (time >= 200) {
                    value = -171;
                } else {
                    value = time >= 100 ? -43691 : -5636096;
                }
                break;
            default:
                value = staticColor;
        }
        int opacity = value >> 24 & 0xFF;
        opacity = (int) ((float) opacity * (excludePotions(effects) ? 0.5F : 1.0F));
        return value & 0xFFFFFF | opacity << 24;
    }

    private void sortEffects(List<PotionEffect> effects) {
        switch (this.sorting.getStringValue()) {
            case "Alphabetical":
                effects.sort(Comparator.comparing(effect -> I18n.format(effect.getEffectName())));
                break;
            case "Duration":
                effects.sort(Comparator.comparingInt(PotionEffect::getDuration));
                break;
            case "Amplifier":
                effects.sort(Comparator.comparingInt(PotionEffect::getAmplifier));
                Collections.reverse(effects);
        }
        if (this.reverseSorting.getBooleanValue()) {
            Collections.reverse(effects);
        }
    }

    private boolean excludePotions(PotionEffect effect) {
        if (this.excludeArrayOptions(this.excludeSetDuration, effect.getDuration(), this.excludedDuration.getFloatValue() * 20.0F) && !effect.getIsPotionDurationMax()) {
            return true;
        } else if (this.excludePerm.getBooleanValue() && effect.getIsPotionDurationMax()) {
            return true;
        } else if (this.excludeArrayOptions(this.excludeSetAmplifier, effect.getAmplifier(), this.excludedAmplifier.getIntegerValue() - 1)) {
            return true;
        } else if (this.excludeHelpfulHarmfulEffects.getValue().equals("Only Helpful") && !Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
            return true;
        } else if (this.excludeHelpfulHarmfulEffects.getValue().equals("Only Harmful") && Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
            return true;
        } else {
            return this.excludeSpecificEffects.getArrayListValue().contains(effect.getPotionID());
        }
    }

    private boolean excludeArrayOptions(Setting setting, int realValue, float sliderValue) {
        switch (setting.getStringValue()) {
            case "Effects Above": return realValue > sliderValue;
            case "Effects Below": return realValue < sliderValue;
            case "Effects At": return realValue == sliderValue;
            case "Effects Not At": return realValue != sliderValue;
            default: return false;
        }
    }

    private String amplifierNumerals(int level) {
        String s = this.effectNameText.getBooleanValue() ? " " : "";
        if (level < 0) {
            level = 127 + Math.abs(128 + level);
        }
        int l = level + 1;
        int minLevel = this.showLevelOne.getBooleanValue() ? 0 : 1;
        if (this.romanNumerals.getBooleanValue() && level >= minLevel && level <= this.maximumRomanNumeral.getIntegerValue()) {
            return s + String.join("", Collections.nCopies(l, "I"))
                    .replace("IIIII", "V").replace("IIII", "IV").replace("VV", "X")
                    .replace("VIV", "IX").replace("XXXXX", "L").replace("XXXX", "XL")
                    .replace("LL", "C").replace("LXL", "XC").replace("CCCCC", "D")
                    .replace("CCCC", "CD").replace("DD", "M").replace("DCD", "CM");
        } else {
            return level >= minLevel ? s + l : "";
        }
    }

    private String stripColor(String input) {
        return input == null ? null : Pattern.compile("(?i)" + '§' + "[0-9A-F]").matcher(input).replaceAll("§r");
    }
}
