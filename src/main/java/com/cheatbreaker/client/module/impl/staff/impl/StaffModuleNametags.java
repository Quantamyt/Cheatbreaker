package com.cheatbreaker.client.module.impl.staff.impl;

import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Module - StaffModuleNameTags
 * @see StaffMod
 *
 * This staff module renders an upscaled nametag with health information above players.
 */
public class StaffModuleNameTags extends StaffMod {
    public FloatBuffer modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16);
    public FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private static Map<UUID, List<String>> nameTagList = null;

    public StaffModuleNameTags() {
        super("Nametags");
        this.setStaffModule(true);
        this.addEvent(GuiDrawEvent.class, this::onGuiDrawEvent);
    }

    private void onGuiDrawEvent(GuiDrawEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(2978, intBuffer);
        float f = mc.timer.renderPartialTicks;
        float f2 = (float) (mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double) f);
        float f3 = (float) (mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double) f);
        float f4 = (float) (mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double) f);
        double d = (double) (mc.thePlayer.rotationPitch + 90.0f) * Math.PI / 180.0;
        double d2 = (double) (mc.thePlayer.rotationYaw + 90.0f) * Math.PI / 180.0;
        boolean bl = nameTagList != null;
        Vec3 vec3 = new Vec3(Math.sin(d) * Math.cos(d2), Math.cos(d), Math.sin(d) * Math.sin(d2));
        if (mc.gameSettings.thirdPersonView == 2) {
            vec3 = new Vec3(vec3.xCoord * -1.0, vec3.yCoord * -1.0, vec3.zCoord * -1.0);
        }
        for (int i = 0; i < mc.theWorld.getLoadedEntityList().size(); ++i) {
            Entity entity = mc.theWorld.getLoadedEntityList().get(i);
            if (entity == null || entity == mc.thePlayer || !(entity instanceof AbstractClientPlayer)) continue;
            AbstractClientPlayer player = (AbstractClientPlayer) entity;
            Scoreboard scoreboard = player.getWorldScoreboard();
            ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);
            float f5 = 0.0f;
            if (player.getDistanceSqToEntity(mc.thePlayer) < (double) 100 && scoreObjective != null) {
                f5 = (float) ((double) f5 + (double) ((float) mc.fontRendererObj.FONT_HEIGHT * 0.0375f));
            }
            float f6 = (float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) f - (double) f2);
            float f7 = (float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) f - (double) f3) + player.height + (player.isSneaking() ? 0.3F : 0.55F) + f5;
            float f8 = (float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) f - (double) f4);
            Vec3 vec32 = new Vec3(f6, f7, f8);
            double d3 = vec32.lengthVector();
            if (vec3.dotProduct(vec32 = vec32.normalize()) <= 0.02) {
                double d4 = 1.5533430342749535;
                double d5 = Math.sin(1.5533430342749535);
                double d6 = Math.cos(1.5533430342749535);
                Vec3 cross = vec3.crossProduct(vec32);
                double d7 = cross.xCoord;
                double d8 = cross.yCoord;
                double d9 = cross.zCoord;
                double d10 = d6 + d7 * d7 * (1.0 - d6);
                double d11 = d7 * d8 * (1.0 - d6) - d9 * d5;
                double d12 = d7 * d9 * (1.0 - d6) + d8 * d5;
                double d13 = d8 * d7 * (1.0 - d6) + d9 * d5;
                double d14 = d6 + d8 * d8 * (1.0 - d6);
                double d15 = d8 * d9 * (1.0 - d6) - d7 * d5;
                double d16 = d9 * d7 * (1.0 - d6) - d8 * d5;
                double d17 = d9 * d8 * (1.0 - d6) + d7 * d5;
                double d18 = d6 + d9 * d9 * (1.0 - d6);
                f6 = (float) (d3 * (d10 * vec3.xCoord + d11 * vec3.yCoord + d12 * vec3.zCoord));
                f7 = (float) (d3 * (d13 * vec3.xCoord + d14 * vec3.yCoord + d15 * vec3.zCoord));
                f8 = (float) (d3 * (d16 * vec3.xCoord + d17 * vec3.yCoord + d18 * vec3.zCoord));
            }
            FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
            GLU.gluProject(f6, f7, f8, this.modelViewMatrixBuffer, this.projectionMatrixBuffer, intBuffer, floatBuffer);
            float f9 = floatBuffer.get(0) / (float) event.getScaledResolution().getScaleFactor();
            float f10 = floatBuffer.get(1) / (float) event.getScaledResolution().getScaleFactor();
            GL11.glPushMatrix();
            GL11.glTranslatef(f9, (float) event.getScaledResolution().getScaledHeight() - f10, 0.0f);
            float f11 = player.getHealth();
            String string = player.getName() + ((double) f11 != 1.0 ? " | " + this.setHealthColor(f11) + f11 : "");
            if (scoreObjective != null) {
                Score score = scoreboard.getValueFromObjective(player.getName(), scoreObjective);
                string = string + EnumChatFormatting.WHITE + " | " + this.setHealthColor(score.getScorePoints()) + score.getScorePoints();
            }
            float f12 = mc.fontRendererObj.getStringWidth(EnumChatFormatting.getTextWithoutFormattingCodes(string));
            mc.fontRendererObj.drawStringWithShadow(string, (float) ((int) (-f12) / 2), (float) (-mc.fontRendererObj.FONT_HEIGHT), -1);
            if (bl && nameTagList.containsKey(player.getGameProfile().getId().toString())) {
                int n = 1;
                for (Object o : (List) nameTagList.get(player.getGameProfile().getId().toString())) {
                    String string2 = (String) o;
                    mc.fontRendererObj.drawCenteredStringWithShadow(string2, (int) (-f12) / 2, -mc.fontRendererObj.FONT_HEIGHT * ++n, -1);
                }
            }
            GL11.glPopMatrix();
        }
    }

    private EnumChatFormatting setHealthColor(float f) {
        return f > 15.0f ? EnumChatFormatting.DARK_GREEN : (f > 10.0f ? EnumChatFormatting.YELLOW : (f > 5.0f ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED));
    }

    public static Map<UUID, List<String>> getNameTagList() {
        return nameTagList;
    }

    public static void setNameTagList(Map<UUID, List<String>> nameTagList) {
        StaffModuleNameTags.nameTagList = nameTagList;
    }
}
