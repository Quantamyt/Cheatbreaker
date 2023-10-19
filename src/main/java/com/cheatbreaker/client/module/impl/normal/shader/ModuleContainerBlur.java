package com.cheatbreaker.client.module.impl.normal.shader;

import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.google.common.base.Throwables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;

public class ModuleContainerBlur extends AbstractModule {


    public ModuleContainerBlur() {
        super("Container Blur");
        this.setDefaultState(false);

        /*
         * Insert Settings here.
         */

        this.setPreviewLabel("Container Blur", 1.1F);
        this.setDescription("Adds a blur to containers.");
        this.addEvent(TickEvent.class, this::onTick);
    }

    private void onTick(TickEvent event) {
        if (this.mc.currentScreen == null && !this.mc.entityRenderer.isShaderActive()) {
            this.drawShader();
        }
    }

    private void drawShader() {
        this.mc.entityRenderer.loadMotionBlurShader();
        ShaderGroup shaderGroup = Minecraft.getMinecraft().entityRenderer.getShaderGroup();
        try {
            if (this.mc.entityRenderer.isShaderActive() && this.mc.thePlayer != null) {
                for (Shader shader : shaderGroup.getShaders()) {
                    /*
                     * Insert code here.
                     */
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            Throwables.propagate(illegalArgumentException);
        }
    }
}
