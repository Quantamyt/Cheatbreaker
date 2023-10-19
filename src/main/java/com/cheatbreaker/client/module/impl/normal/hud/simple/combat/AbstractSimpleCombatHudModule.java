package com.cheatbreaker.client.module.impl.normal.hud.simple.combat;

import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;

/**
 * @SimpleModule - AbstractSimpleCombatHudModule
 * @see AbstractSimpleHudModule
 *
 * This abstract class defines the simple class simple HUD modules use.
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
        this.customWaitingMessageBackground = new Setting(this, "Waiting (Background)").setValue(getWaitingString()).setCondition(this.background::getBooleanValue).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.customWaitingMessageNoBackground = new Setting(this, "Waiting (No Background)").setValue(showBrackets() && !getWaitingString().isEmpty() ? "[" + getWaitingString() + "]" : getWaitingString()).setCondition(() -> !this.background.getBooleanValue()).setCustomizationLevel(CustomizationLevel.ADVANCED);
    }

    public String getWaitingString() {
        return "";
    }

    public boolean getWaitingRequirement() {
        return lastAttackTime == 0L;
    }

    public String getWaitingMessage() {
        if (getWaitingRequirement()) {
            String message = (this.background.getBooleanValue() ? this.customWaitingMessageBackground : this.customWaitingMessageNoBackground).getStringValue();
            if (!message.isEmpty()) {
                return message.replaceAll("%LABEL%", getLabelString()).replaceAll("%VALUE%", getValueString());
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