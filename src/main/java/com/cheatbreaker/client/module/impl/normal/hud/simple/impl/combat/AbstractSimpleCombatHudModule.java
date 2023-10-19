package com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat;

import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;

/**
 * Defines the all combat related options and methods for Combat Simple HUD Mods.
 * @see AbstractSimpleHudModule
 */
public abstract class AbstractSimpleCombatHudModule extends AbstractSimpleHudModule {
    public Setting customWaitingMessageBackground;
    public Setting customWaitingMessageNoBackground;
    protected Setting hideWhenNotAttacking;
    public static long lastAttackTime;

    public AbstractSimpleCombatHudModule(String name, String previewString) {
        super(name, previewString);
        this.addEvent(TickEvent.class, this::onTickEvent);
    }

    public void getExtraSettings() {
        this.hideWhenNotAttacking = new Setting(this, "Hide When Not Attacking").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }

    public void getExtraFormatSettings() {
        this.customWaitingMessageBackground = new Setting(this, "Waiting (Background)")
                .setValue(getWaitingString())
                .setCondition(this.background::getBooleanValue)
                .setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.customWaitingMessageNoBackground = new Setting(this, "Waiting (No Background)")
                .setValue(this.isBracketsEnabledByDefault() && !getWaitingString().isEmpty() ? "[" + getWaitingString() + "]" : getWaitingString())
                .setCondition(() -> !this.background.getBooleanValue())
                .setCustomizationLevel(CustomizationLevel.ADVANCED);
    }

    public String getWaitingString() {
        return "";
    }

    public boolean getWaitingRequirement() {
        return lastAttackTime == 0L;
    }

    public String getWaitingMessage() {
        if (getWaitingRequirement()) {
            if ((Boolean) this.background.getValue()) {
                if (!(Boolean) customWaitingMessageBackground.getValue().toString().isEmpty()) {
                    return customWaitingMessageBackground.getValue().toString().replaceAll("%LABEL%", getLabelString()).replaceAll("%VALUE%", getValueString());
                }
            } else {
                if (!(Boolean) customWaitingMessageNoBackground.getValue().toString().isEmpty()) {
                    return customWaitingMessageNoBackground.getValue().toString().replaceAll("%LABEL%", getLabelString()).replaceAll("%VALUE%", getValueString());
                }
            }
        }
        return null;
    }

    public void onTickEvent(TickEvent event) {
        if (System.currentTimeMillis() - lastAttackTime > 2000L) {
            lastAttackTime = 0L;
            clear();
        }
    }

    public void clear() {
    }
}