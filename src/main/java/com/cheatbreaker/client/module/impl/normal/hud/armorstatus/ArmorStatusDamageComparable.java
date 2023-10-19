package com.cheatbreaker.client.module.impl.normal.hud.armorstatus;

import java.util.List;

/**
 * @ModuleUtility - ArmorStatusDamageComparable
 *
 * This utility is used to get information on an item for use in the Armor Status module.
 */
public class ArmorStatusDamageComparable implements Comparable<ArmorStatusDamageComparable> {
    public int percent;
    public String colorCode;

    public ArmorStatusDamageComparable(int percent, String colorCode) {
        this.percent = percent;
        this.colorCode = colorCode;
    }

    public int compare(ArmorStatusDamageComparable damageComparable) {
        return Integer.compare(this.percent, damageComparable.percent);
    }

    /**
     * Returns the item damage color.
     */
    public static String getDamageColor(List<ArmorStatusDamageComparable> durability, int n) {
        for (ArmorStatusDamageComparable armorStatusDamageComparable : durability) {
            if (n > armorStatusDamageComparable.percent) continue;
            return armorStatusDamageComparable.colorCode;
        }
        return "f";
    }

    public int compareTo(ArmorStatusDamageComparable object) {
        return this.compare(object);
    }
}
