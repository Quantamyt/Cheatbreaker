package com.cheatbreaker.client.module.impl.normal.perspective;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.TickEvent;
import net.minecraft.client.Minecraft;

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
            } else{
                if (this.enabled && !this.cb.getGlobalSettings().keyBindDragToLook.getIsKeyPressed()) {
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

    public void setAngles(float x, float y) {
        float pitch = this.rotationPitch;
        float yaw = this.rotationYaw;
        this.rotationYaw = (float)((double)this.rotationYaw + (this.cb.getModuleManager().perspectiveMod.invertYaw.getBooleanValue() ? (double)-x : (double)x) * 0.15);
        this.rotationPitch = (float)((double)this.rotationPitch - (this.cb.getModuleManager().perspectiveMod.invertPitch.getBooleanValue() ? (double)-y : (double)y) * 0.15);
        if (this.cb.getModuleManager().perspectiveMod.pitchLock.getBooleanValue()) {
            if (this.rotationPitch < (float)-90) {
                this.rotationPitch = -90;
            }
            if (this.rotationPitch > (float)90) {
                this.rotationPitch = 90;
            }
        }
        this.prevRotationPitch += this.rotationPitch - pitch;
        this.prevRotationYaw += this.rotationYaw - yaw;
    }
}
