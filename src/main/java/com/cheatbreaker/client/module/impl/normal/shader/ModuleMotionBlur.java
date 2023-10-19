package com.cheatbreaker.client.module.impl.normal.shader;

import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.google.common.base.Throwables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;

public class ModuleMotionBlur extends AbstractModule {
    private final Setting amount;
    private final Setting color;
    public Setting oldBlur;

    public ModuleMotionBlur() {
        super("Motion Blur");
        this.setDefaultState(false);
        this.oldBlur = new Setting(this, "Old Blur", "Revert to the previously used motion blur shader.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.amount = new Setting(this, "Amount", "Change the amount of blur intensity.").setValue(10.0F).setMinMax(0.01F, 100.0F).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.color = new Setting(this, "Color", "Change the color of the motion blur.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.oldBlur.getValue());
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/swordblur.png"), 40, 40);
        this.setDescription("Adds a shader to blur your surroundings while moving.");
        this.setCreators("Fyu (Original)", "Moonsworth LLC (Newer shader)");
        this.addEvent(TickEvent.class, this::onTick);
    }

    private void onTick(TickEvent tickEvent) {
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
                    ShaderUniform uniform = shader.getShaderManager().func_147991_a("Phosphor");
                    if (uniform == null) continue;
                    float amount = 1.0f - this.amount.getFloatValue() / 100.0F * 0.9F;
                    if (this.oldBlur.getBooleanValue()) {
                        amount = 0.7F + this.amount.getFloatValue() / 1000.0F * 3.0F - 0.01F;
                    }
                    int color = this.oldBlur.getBooleanValue() ? this.color.getColorValue() : -1;
                    float red = (float)(color >> 16 & 0xFF) / 255.0F;
                    float green = (float)(color >> 8 & 0xFF) / 255.0F;
                    float blue = (float)(color & 0xFF) / 255.0F;
                    uniform.func_148095_a(amount * red, amount * green, amount * blue);
                }
            }
        } catch (IllegalArgumentException e) {
            Throwables.propagate(e);
        }
    }
}
