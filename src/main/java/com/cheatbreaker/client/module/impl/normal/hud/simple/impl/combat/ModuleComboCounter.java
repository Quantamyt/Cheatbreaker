package com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat;

import com.cheatbreaker.client.module.AbstractModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays how many times the user hit another entity in a row within 2 seconds.
 * @see AbstractModule
 */
public class ModuleComboCounter extends AbstractSimpleCombatHudModule {
    public static List<Long> hits = new ArrayList<>();
    public static long lastComboTime;
    public static int lastAttacked = -1;
    public static int lastCombo;

    public ModuleComboCounter() {
        super("Combo Counter", "[6 Combo]");
        this.setDescription("Displays how many times you hit someone in a row within 2 seconds.");
        this.setCreators("Erouax");
    }

    public String getValueString() {
        if (this.excludeArrayOptions(this.hideValue, hits.size(), this.hiddenValue.getIntegerValue())) {
            return null;
        }
        if (lastAttackTime != 0L || !(Boolean) this.hideWhenNotAttacking.getValue()) {
            return hits.size() + "";
        }
        return null;
    }

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

