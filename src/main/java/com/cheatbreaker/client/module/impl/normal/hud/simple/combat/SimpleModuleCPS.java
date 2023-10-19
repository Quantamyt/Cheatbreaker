package com.cheatbreaker.client.module.impl.normal.hud.simple.combat;

import com.cheatbreaker.client.event.impl.mouse.ClickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.util.RenderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Module - ModuleComboCounter
 * @see AbstractModule
 *
 * This module counts the amount of clicks per second you're getting.
 */
public class SimpleModuleCPS extends AbstractSimpleCombatHudModule {
    private final Deque<Long> leftCPS = new ArrayDeque<>();
    private final Deque<Long> rightCPS = new ArrayDeque<>();
    private Setting counterType;
    private Setting lineColor;
    private String leftClickCounter;
    private String space;

    public SimpleModuleCPS() {
        super("CPS", "[9 CPS]");
        this.setDescription("Displays your clicks per second.");
        this.setCreators("Fyu");
        this.addEvent(ClickEvent.class, this::onClick);
    }

    @Override
    public String getValueString() {
        if (lastAttackTime != 0L || !(Boolean) this.hideWhenNotAttacking.getValue()) {
            leftCPS.removeIf((var0) -> var0 < System.currentTimeMillis() - 1000L);
            rightCPS.removeIf((var0) -> var0 < System.currentTimeMillis() - 1000L);
            leftClickCounter = !counterType.getValue().equals("Right Clicks") ? "" + leftCPS.size() : "";
            String rightClickCounter = !counterType.getValue().equals("Left Clicks") ? Integer.toString(rightCPS.size()) : "";
            space = counterType.getValue().equals("Both") ? "  " : "";
            return counterType.getValue().equals("Higher") ? Math.max(leftCPS.size(), rightCPS.size()) + "" : leftClickCounter + space + rightClickCounter;
        }
        return null;
    }

    @Override
    public String getLabelString() {
        return (counterType.getValue().equals("Right Clicks") || (counterType.getValue().equals("Higher") && leftCPS.size() < rightCPS.size()) ? "R" : "") + "CPS";
    }

    public String getPreviewString() {
        return counterType.getValue().equals("Both") ? "9  2" : "9";
    }

    public void getExtraSettings() {
        this.counterType = new Setting(this, "Counter", "Determines which and when clicks should be shown." +
                "\n" +
                "\n§bLeft Clicks:§r Only show left clicks." +
                "\n§bRight Clicks:§r Only show right clicks." +
                "\n§bBoth:§r Show both left and right clicks." +
                "\n§bHigher:§r Shows either left or right clicks depending which has a higher amount.").setValue("Left Clicks").acceptedStringValues("Left Clicks", "Right Clicks", "Both", "Higher").setCustomizationLevel(CustomizationLevel.SIMPLE);
        super.getExtraSettings();
    }

    public void getExtraColorSettings() {
        this.lineColor = new Setting(this, "Line Color").setValue(0xFF202020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.counterType.getValue().equals("Both"));
    }

    private void onClick(ClickEvent event) {
        if (event.getMouseButton() == 0) leftCPS.add(System.currentTimeMillis());
        if (event.getMouseButton() == 1) rightCPS.add(System.currentTimeMillis());
    }

    public void render(String value) {
        super.render(value);
        drawRectWithShadow((this.background.getBooleanValue() ? this.customString : this.customStringNoBackground).getStringValue(), value);
    }

    public void drawRectWithShadow(String total, String value) {
        float lineXPos = (float) this.mc.fontRendererObj.getStringWidth(
                StringUtils.substringBefore(total, "%VALUE%"))
                + (float) this.mc.fontRendererObj.getStringWidth(leftClickCounter)
                + (this.mc.fontRendererObj.getStringWidth(space) / 2.0F - 0.6F);

        float lineYPos = this.background.getBooleanValue() || this.alwaysCenter.getBooleanValue() ? this.backgroundHeight.getFloatValue() / 2.0F - 4.5F : -1.0F;
        boolean shadow = this.background.getBooleanValue() ? this.textShadowBackground.getBooleanValue() : this.textShadowNoBackground.getBooleanValue();
        if (this.background.getBooleanValue() || this.alwaysCenter.getBooleanValue()) {
            float first = this.width / 2.0F - (float) (this.mc.fontRendererObj.getStringWidth(total.replaceAll("%LABEL%", getLabelString()).replaceAll("%VALUE%", value)) / 2);
            lineXPos += first;
        }

        if (this.counterType.getValue().equals("Both")) {
            RenderUtil.drawRectWithShadow(lineXPos, lineYPos, lineXPos + 1.0F, lineYPos + 9.0F, this.lineColor.getColorValue(), shadow);
        }

    }
}

