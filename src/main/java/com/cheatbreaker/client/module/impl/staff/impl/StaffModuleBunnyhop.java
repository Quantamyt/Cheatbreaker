package com.cheatbreaker.client.module.impl.staff.impl;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.staff.StaffMod;

/**
 * @Module - StaffModuleBunnyhop
 * @see StaffMod
 *
 * This staff module replicates the CSGO bunny hop.
 */
public class StaffModuleBunnyhop extends StaffMod {
    public static Setting trimp;
    public static Setting trimpMultiplier;
    public static Setting hardCap;
    public static Setting softCap;
    public static Setting softCapDegen;
    public static Setting sharking;
    public static Setting sharkingSurfaceTension;
    public static Setting sharkingWaterFriction;
    public static Setting accelerate;
    public static Setting airAccelerate;
    public static Setting uncappedBHop;
    public static Setting increasedFallDistance;
    public static Setting maxAirAccelPerTick;

    public StaffModuleBunnyhop() {
        super("bunnyhop");
        this.setStaffModule(true);
        trimp = new Setting(this, "Trimp").setValue(true);
        trimpMultiplier = new Setting(this, "Trimp Multiplier").setValue(1.4F).setMinMax(1.0f, 4.0F);
        hardCap = new Setting(this, "Hard cap").setValue(2.0f).setMinMax(1.0f, 4.0F);
        softCap = new Setting(this, "Soft cap").setValue(1.4F).setMinMax(1.0f, 4.0F);
        softCapDegen = new Setting(this, "Soft cap degen").setValue(0.65F).setMinMax(0.1f, 4.0F);
        sharking = new Setting(this, "Sharking").setValue(true);
        sharkingSurfaceTension = new Setting(this, "Sharking surface tension").setValue(0.2D).setMinMax(0.0, 1.0);
        sharkingWaterFriction = new Setting(this, "Sharking water friction").setValue(0.1D).setMinMax(0.0, 1.0);
        accelerate = new Setting(this, "Accelerate").setValue(20.0).setMinMax(5.0, 100.0);
        airAccelerate = new Setting(this, "Air accelerate").setValue(28.0).setMinMax(5.0, 150.0);
        maxAirAccelPerTick = new Setting(this, "Max air accel per tick").setValue(0.095D).setMinMax(0.0, 1.0);
        uncappedBHop = new Setting(this, "Uncapped BunnyHop").setValue(true);
        increasedFallDistance = new Setting(this, "Increased FallDistance").setValue(0.0).setMinMax(0.0, 10.0);
    }

    public static boolean canUse() {
        return CheatBreaker.getInstance().getModuleManager().staffBunnyhop.isStaffModuleEnabled() && CheatBreaker.getInstance().getModuleManager().staffBunnyhop.isEnabled();
    }
}
