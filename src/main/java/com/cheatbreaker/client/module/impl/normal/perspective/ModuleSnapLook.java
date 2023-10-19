package com.cheatbreaker.client.module.impl.normal.perspective;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import net.minecraft.client.Minecraft;

/**
 * @Module - ModuleSnapLook
 * @see AbstractModule
 *
 * This module snaps your perspective to a certain value.
 */
public class ModuleSnapLook {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final CheatBreaker cb = CheatBreaker.getInstance();
    public boolean enabled = false;
    public float rotationYaw;
    public float prevRotationYaw;
    public float rotationPitch;
    public float prevRotationPitch;
    private int thirdPersonView;
    private int mode;

    public ModuleSnapLook() {
        this.cb.getEventBus().addEvent(TickEvent.class, tickEvent -> {
            if ((this.mode == 1 && this.cb.getModuleManager().perspectiveMod.toggleBackCamera.getBooleanValue()) || (this.mode == 2 && this.cb.getModuleManager().perspectiveMod.toggleFrontCamera.getBooleanValue())) {
                if (this.enabled && (this.cb.getGlobalSettings().keyBindBackLook.isPressed() || this.cb.getGlobalSettings().keyBindFrontLook.isPressed())) {
                    this.tick();
                }
            } else {
                if (this.enabled && !this.cb.getGlobalSettings().keyBindBackLook.isKeyDown() && !this.cb.getGlobalSettings().keyBindFrontLook.isKeyDown()) {
                    this.tick();
                }
            }
        });
    }

    public void toggle(int mode) {
        if (this.cb.getModuleManager().perspectiveMod.isEnabled()) {
            this.rotationYaw = this.mc.thePlayer.rotationYaw;
            this.rotationPitch = this.mc.thePlayer.rotationPitch;
            this.prevRotationYaw = this.mc.thePlayer.prevRotationYaw;
            this.prevRotationPitch = this.mc.thePlayer.prevRotationPitch;
            this.thirdPersonView = this.mc.gameSettings.thirdPersonView;
            if (mode == 1) {
                this.mc.gameSettings.thirdPersonView = 1;
            } else {
                this.mc.gameSettings.thirdPersonView = 2;
            }
            this.enabled = true;
            this.mode = mode;
        }
    }

    public void tick() {
        this.mc.gameSettings.thirdPersonView = this.thirdPersonView;
        this.enabled = false;
    }
}