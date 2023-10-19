package com.cheatbreaker.client.module.impl.normal.animation.util;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.impl.normal.animation.ModuleOneSevenVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @ModuleUtility - SneakHandler
 * @see ModuleOneSevenVisuals
 * @see net.minecraft.entity.player.EntityPlayer
 *
 * This class handles player sneaking visuals.
 */
public class SneakHandler {
    private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;
    private static final SneakHandler INSTANCE = new SneakHandler();
    private float eyeHeight;
    private float lastEyeHeight;

    public SneakHandler() {
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
    }

    public static SneakHandler getInstance() {
        return INSTANCE;
    }

    public float getEyeHeight(float partialTicks) {
        if (!ModuleOneSevenVisuals.smoothSneaking.getBooleanValue()) {
            return this.eyeHeight;
        }
        return this.lastEyeHeight + (this.eyeHeight - this.lastEyeHeight) * partialTicks;
    }

    public void onTick(TickEvent event) {
        this.lastEyeHeight = this.eyeHeight;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            this.eyeHeight = 1.62f;
            return;
        }
        if (player.isSneaking()) {
            this.eyeHeight = 1.54f;
        } else if (!ModuleOneSevenVisuals.longSneaking.getBooleanValue()) {
            this.eyeHeight = 1.62f;
        } else if (this.eyeHeight < 1.62f) {
            float delta = 1.62f - this.eyeHeight;
            delta = (float) ((double) delta * 0.4);
            this.eyeHeight = 1.62f - delta;
        }
    }
}
