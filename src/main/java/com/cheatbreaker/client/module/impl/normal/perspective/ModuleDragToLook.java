package com.cheatbreaker.client.module.impl.normal.perspective;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import net.minecraft.client.Minecraft;

/**
 * @Module - ModuleDragToLook
 * @see AbstractModule
 *
 * This module is the first of its kind, now known as Freelook, or Perspective.
 */
public class ModuleDragToLook {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final CheatBreaker cb = CheatBreaker.getInstance();
    public boolean enabled = false;
    public float rotationYaw;
    public float prevRotationYaw;
    public float rotationPitch;
    public float prevRotationPitch;
    private int thirdPersonView;

    public ModuleDragToLook() {
        this.cb.getEventBus().addEvent(TickEvent.class, tickEvent -> {
            if (this.cb.getModuleManager().perspectiveMod.toggleDragToLook.getBooleanValue()) {
                if (this.enabled && this.cb.getGlobalSettings().keyBindDragToLook.isPressed()) {
                    this.tick();
                }
            } else {
                if (this.enabled && !this.cb.getGlobalSettings().keyBindDragToLook.isKeyDown()) {
                    this.tick();
                }
            }

        });
    }

    public void toggle() {
        if (this.cb.getModuleManager().perspectiveMod.isEnabled()) {
            this.rotationYaw = this.mc.thePlayer.rotationYaw;
            this.rotationPitch = this.mc.thePlayer.rotationPitch;
            this.prevRotationYaw = this.mc.thePlayer.prevRotationYaw;
            this.prevRotationPitch = this.mc.thePlayer.prevRotationPitch;
            this.thirdPersonView = this.mc.gameSettings.thirdPersonView;
            switch (this.cb.getModuleManager().perspectiveMod.lookView.getStringValue()) {
                case "Third":
                    this.mc.gameSettings.thirdPersonView = 1;
                    break;
                case "Reverse":
                    this.mc.gameSettings.thirdPersonView = 2;
                    break;
                default:
                    this.mc.gameSettings.thirdPersonView = 0;
            }
            this.enabled = true;
        }
    }

    public void tick() {
        this.mc.gameSettings.thirdPersonView = this.thirdPersonView;
        this.enabled = false;
    }

    public void setAngles(float yaw, float pitch) {
        float rotationPitch = this.rotationPitch;
        float rotationYaw = this.rotationYaw;
        this.rotationYaw = (float) ((double) this.rotationYaw + (this.cb.getModuleManager().perspectiveMod.invertYaw.getBooleanValue() ? (double) -yaw : (double) yaw) * 0.15);
        this.rotationPitch = (float) ((double) this.rotationPitch - (this.cb.getModuleManager().perspectiveMod.invertPitch.getBooleanValue() ? (double) -pitch : (double) pitch) * 0.15);
        if (this.cb.getModuleManager().perspectiveMod.pitchLock.getBooleanValue()) {
            if (this.rotationPitch < (float) -90) {
                this.rotationPitch = -90;
            }
            if (this.rotationPitch > (float) 90) {
                this.rotationPitch = 90;
            }
        }
        this.prevRotationPitch += this.rotationPitch - rotationPitch;
        this.prevRotationYaw += this.rotationYaw - rotationYaw;
    }
}
