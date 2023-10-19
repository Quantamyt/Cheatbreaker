package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;

/**
 * @Module - ModuleParticles
 * @see AbstractModule
 *
 * This module changes the Vanilla Minecraft particles rendering.
 */
public class ModuleParticles extends AbstractModule {
    private final Setting generalParticlesLabel;
    public Setting sprintingParticles;
    public Setting blockBreakingParticles;
    public Setting lazyParticleLoading;
    public Setting lazyParticleLoadingAmount;

    private final Setting attackParticlesLabel;
    public Setting sharpnessParticles;
    public Setting sharpnessParticlesMultiplier;
    public Setting critParticles;
    public Setting critParticlesMultiplier;
    public Setting damaged;
    public Setting attacking;

    private final Setting potionParticlesLabel;
    public Setting splashParticles;
    public Setting instantEffectSplashOpacity;
    public Setting normalEffectSplashOpacity;
    public Setting activeEffectParticles;
    public Setting activeEffectOpacity;
    public Setting activeEffectAmbientOpacity;


    public ModuleParticles() {
        super("Particles");
        this.generalParticlesLabel = new Setting(this, "label").setValue("General Particles");
        this.sprintingParticles = new Setting(this, "Show Sprinting Particles").setValue(true);
        this.blockBreakingParticles = new Setting(this, "Show Block Breaking Particles").setValue(true);
        this.lazyParticleLoading = new Setting(this, "Lazily Load Particles").setValue(false);
        this.lazyParticleLoadingAmount = new Setting(this, "Lazy Particle Amount").setValue(5.0f).setMinMax(0.1f, 10.0f).setUnit("x");
        this.attackParticlesLabel = new Setting(this, "label").setValue("Attack Particles");
        this.damaged = new Setting(this, "Show When Damaged").setValue("Override").acceptedStringValues("OFF", "Vanilla", "Override");
        this.attacking = new Setting(this, "Show When Attacking").setValue("Override").acceptedStringValues("OFF", "Vanilla", "Override");
        this.sharpnessParticles = new Setting(this, "Sharpness Particles").setValue("Vanilla").acceptedStringValues("Never", "Vanilla", "Always").setCondition(() -> this.damaged.getValue().equals("Override") || this.attacking.getValue().equals("Override"));
        this.sharpnessParticlesMultiplier = new Setting(this, "Sharpness Multiplier").setValue(1.0f).setMinMax(0.1f, 10.0f).setUnit("x").setCondition(() -> !this.sharpnessParticles.getValue().equals("Never") && (this.damaged.getValue().equals("Override") || this.attacking.getValue().equals("Override")));
        this.critParticles = new Setting(this, "Crit Particles").setValue("Vanilla").acceptedStringValues("Never", "Vanilla", "Always").setCondition(() -> this.damaged.getValue().equals("Override") || this.attacking.getValue().equals("Override"));
        this.critParticlesMultiplier = new Setting(this, "Crit Multiplier").setValue(1.0f).setMinMax(0.1f, 10.0f).setUnit("x").setCondition(() -> !this.critParticles.getValue().equals("Never") && (this.damaged.getValue().equals("Override") || this.attacking.getValue().equals("Override")));

        this.potionParticlesLabel = new Setting(this, "label").setValue("Effect Particles");
        this.splashParticles = new Setting(this, "Show Splash Particles").setValue(true);
        this.instantEffectSplashOpacity = new Setting(this, "Instant Splash Opacity").setValue(100.0f).setMinMax(0.01f, 100.0f).setUnit("%").setCondition(() -> (Boolean) this.splashParticles.getValue());
        this.normalEffectSplashOpacity = new Setting(this, "Normal Splash Opacity").setValue(100.0f).setMinMax(0.01f, 100.0f).setUnit("%").setCondition(() -> (Boolean) this.splashParticles.getValue());

        this.activeEffectParticles = new Setting(this, "Show Active Effect Particles").setValue(true);
        this.activeEffectOpacity = new Setting(this, "Active Effect Opacity").setValue(100.0f).setMinMax(0.01f, 100.0f).setUnit("%").setCondition(() -> (Boolean) this.activeEffectParticles.getValue());
        this.activeEffectAmbientOpacity = new Setting(this, "Ambient Effect Opacity").setValue(15.0F).setMinMax(0.01f, 100.0f).setUnit("%").setCondition(() -> (Boolean) this.activeEffectParticles.getValue());

        this.setPreviewLabel("Particles", 1.0F);
        this.setDescription("Change how particles appear.");
        this.setCreators("dewgs (Multipliers)");
    }
}
