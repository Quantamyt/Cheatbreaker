package com.cheatbreaker.client.module.impl.normal.hud.armorstatus;

import java.util.List;

public class ArmorStatusDamageComparable implements Comparable {
    public int percent;
    public String colorCode;

    public ArmorStatusDamageComparable(int percent, String colorCode) {
        this.percent = percent;
        this.colorCode = colorCode;
    }

    public String toString() {
        return this.percent + ", " + this.colorCode;
    }

    public int compare(ArmorStatusDamageComparable damageComparable) {
        return Integer.compare(this.percent, damageComparable.percent);
    }

    public static String getDamageColor(List<ArmorStatusDamageComparable> durability, int n) {
        for (ArmorStatusDamageComparable armorStatusDamageComparable : durability) {
            if (n > armorStatusDamageComparable.percent) continue;
            return armorStatusDamageComparable.colorCode;
        }
        return "f";
    }

    public int compareTo(Object object) {
        return this.compare((ArmorStatusDamageComparable)object);
    }
}
