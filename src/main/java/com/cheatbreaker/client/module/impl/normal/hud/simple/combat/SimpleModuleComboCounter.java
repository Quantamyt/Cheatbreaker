package com.cheatbreaker.client.module.impl.normal.hud.simple.combat;

import com.cheatbreaker.client.module.AbstractModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @Module - ModuleComboCounter
 * @see AbstractModule
 *
 * This module counts the amount of times you've comboed a player.
 */
public class SimpleModuleComboCounter extends AbstractSimpleCombatHudModule {
    public static List<Long> hits = new ArrayList<>();
    public static long lastComboTime;
    public static int lastAttacked = -1;
    public static int lastCombo;

    public SimpleModuleComboCounter() {
        super("Combo Counter", "[6 Combo]");
        this.setDescription("Displays how many times you hit someone in a row within 2 seconds.");
        this.setCreators("Erouax");
    }

    @Override
    public String getValueString() {
        if (lastAttackTime != 0L || !(Boolean) this.hideWhenNotAttacking.getValue()) {
            return hits.size() + "";
        }
        return null;
    }

    @Override
    public String getLabelString() {
        return "Combo";
    }

    public String getWaitingString() {
        return "No %LABEL%";
    }

    public String getPreviewString() {
        return "6";
    }

    public void clear() {
        hits.clear();
    }
}

