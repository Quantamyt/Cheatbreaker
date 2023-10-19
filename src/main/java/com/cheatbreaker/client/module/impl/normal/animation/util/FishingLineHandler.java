package com.cheatbreaker.client.module.impl.normal.animation.util;

import com.cheatbreaker.client.module.impl.normal.animation.ModuleOneSevenVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

/**
 * @ModuleUtility - FishingLineHandler
 * @see ModuleOneSevenVisuals
 *
 * This class handles fishing rod visuals.
 */
public class FishingLineHandler {
    public static final FishingLineHandler INSTANCE = new FishingLineHandler();

    public Vec3 getOffset() {
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110.0;
        return new Vec3(-decimalFov + decimalFov / 2.5 - decimalFov / 8.0 + 0.16, 0.0, 0.4);
    }
}