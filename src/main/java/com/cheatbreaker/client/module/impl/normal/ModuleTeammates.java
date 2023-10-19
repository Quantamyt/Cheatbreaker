package com.cheatbreaker.client.module.impl.normal;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.DisconnectEvent;
import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.util.render.teammates.CBTeammate;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
//TODO: Finish mapping class
@Getter
public class ModuleTeammates {
    public FloatBuffer modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16);
    public FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private final List<CBTeammate> teammates;
    private final int[] colors = new int[]{-15007996, -43234, -3603713, -16580641, -8912129, -16601345, -2786, -64828, -15629042, -10744187};
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getMinecraft();

    public ModuleTeammates() {
        this.teammates = new ArrayList<>();
    }

    public double getDistance(double x, double y, double z) {
        double x2 = x - this.mc.thePlayer.posX;
        double y2 = y - this.mc.thePlayer.posY;
        double z2 = z - this.mc.thePlayer.posZ;
        return Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);
    }

    private void onDraw(GuiDrawEvent event) {
        if (this.teammates.isEmpty()) {
            return;
        }
        IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(2978, intBuffer);
        float f = this.mc.timer.renderPartialTicks;
        float f2 = (float)(this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * (double)f);
        float f3 = (float)(this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)f);
        float f4 = (float)(this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * (double)f);
        double d = (double)(this.mc.thePlayer.rotationPitch + 90.0f) * Math.PI / 180.0;
        double d2 = (double)(this.mc.thePlayer.rotationYaw + 90.0f) * Math.PI / 180.0;
        Vec3 vec3 = new Vec3(Math.sin(d) * Math.cos(d2), Math.cos(d), Math.sin(d) * Math.sin(d2));
        if (this.mc.gameSettings.thirdPersonView == 2) {
            vec3 = new Vec3(vec3.xCoord * (double)-1, vec3.yCoord * (double)-1, vec3.zCoord * (double)-1);
        }
        for (CBTeammate cBTeammate : this.teammates) {
            EntityPlayer entityPlayer = this.mc.theWorld.isPlayerTeamMate(cBTeammate.getUuid());
            if (entityPlayer == null) {
                double d3;
                if (System.currentTimeMillis() - cBTeammate.getLastUpdate() > cBTeammate.getLastMs()) continue;
                double d4 = cBTeammate.getPosition().zCoord - (double)f2;
                double d5 = cBTeammate.getPosition().yCoord - (double)f3;
                double d6 = cBTeammate.getPosition().zCoord - (double)f4;
                double d7 = this.getDistance(cBTeammate.getPosition().xCoord, cBTeammate.getPosition().yCoord, cBTeammate.getPosition().zCoord);
                if (d7 > (d3 = this.mc.gameSettings.getOptionFloatValue(GameSettings.Options.RENDER_DISTANCE) * 16.0f)) {
                    d4 = d4 / d7 * d3;
                    d5 = d5 / d7 * d3;
                    d6 = d6 / d7 * d3;
                }
                this.lIIIIlIIllIIlIIlIIIlIIllI(event.getScaledResolution(), cBTeammate, (float)d4, (float)d5, (float)d6, intBuffer, vec3, (int)d7);
                continue;
            }
            if (entityPlayer == this.mc.thePlayer) continue;
            float f5 = (float)(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)f - (double)f2);
            float f6 = (float)(entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)f - (double)f3) + entityPlayer.height + 1.0f;
            float f7 = (float)(entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)f - (double)f4);
            double d8 = this.getDistance(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            this.lIIIIlIIllIIlIIlIIIlIIllI(event.getScaledResolution(), cBTeammate, f5, f6, f7, intBuffer, vec3, (int)d8);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution, CBTeammate teammate, float f, float f2, float f3, IntBuffer intBuffer, Vec3 vec3, int n) {
        Vec3 vec32 = new Vec3(f, f2, f3);
        double d = vec32.lengthVector();
        if (vec3.dotProduct(vec32 = vec32.normalize()) <= 0.02) {
            double d3 = Math.sin(1.5533430342749535);
            double d4 = Math.cos(1.5533430342749535);
            Vec3 vec33 = vec3.crossProduct(vec32);
            double d5 = vec33.xCoord;
            double d6 = vec33.yCoord;
            double d7 = vec33.zCoord;
            double d8 = d4 + d5 * d5 * (1.0 - d4);
            double d9 = d5 * d6 * (1.0 - d4) - d7 * d3;
            double d10 = d5 * d7 * (1.0 - d4) + d6 * d3;
            double d11 = d6 * d5 * (1.0 - d4) + d7 * d3;
            double d12 = d4 + d6 * d6 * (1.0 - d4);
            double d13 = d6 * d7 * (1.0 - d4) - d5 * d3;
            double d14 = d7 * d5 * (1.0 - d4) - d6 * d3;
            double d15 = d7 * d6 * (1.0 - d4) + d5 * d3;
            double d16 = d4 + d7 * d7 * (1.0 - d4);
            f = (float)(d * (d8 * vec3.xCoord + d9 * vec3.yCoord + d10 * vec3.zCoord));
            f2 = (float)(d * (d11 * vec3.xCoord + d12 * vec3.yCoord + d13 * vec3.zCoord));
            f3 = (float)(d * (d14 * vec3.xCoord + d15 * vec3.yCoord + d16 * vec3.zCoord));
        }
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
        GLU.gluProject(f, f2, f3, this.modelViewMatrixBuffer, this.projectionMatrixBuffer, intBuffer, floatBuffer);
        float f4 = floatBuffer.get(0) / (float)scaledResolution.getScaleFactor();
        float f5 = floatBuffer.get(1) / (float)scaledResolution.getScaleFactor();
        TeammateArrowLocation arrowLocation = null;
        int n2 = 8;
        int n3 = 10;
        int n4 = -4 - n3;
        float f6 = (float)scaledResolution.getScaledHeight() - f5;
        if (f6 < 0.0f) {
            arrowLocation = TeammateArrowLocation.RIGHT;
            f5 = scaledResolution.getScaledHeight() - 6;
        } else if (f6 > (float)(scaledResolution.getScaledHeight() - n3)) {
            arrowLocation = TeammateArrowLocation.BOTTOM;
            f5 = 6;
        }
        if (f4 - (float)n2 < 0.0f) {
            arrowLocation = TeammateArrowLocation.TOP;
            f4 = 6;
        } else if (f4 > (float)(scaledResolution.getScaledWidth() - n2)) {
            arrowLocation = TeammateArrowLocation.LEFT;
            f4 = scaledResolution.getScaledWidth() - 6;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(f4, (float)scaledResolution.getScaledHeight() - f5, 0.0f);
        if (arrowLocation != null) {
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().showOffScreenMarker.getValue()) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(teammate, arrowLocation, 0.0f, 0.0f);
            }
        } else {
            this.lIIIIlIIllIIlIIlIIIlIIllI(teammate, n2, (float)n4, (float)n3);
            if (n > 40 && (Boolean) CheatBreaker.getInstance().getGlobalSettings().showDistance.getValue()) {
                this.mc.fontRenderer.drawCenteredStringWithShadow("(" + n + "m)", 0, 10, -1);
            }
        }
        GL11.glPopMatrix();
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(CBTeammate cBTeammate, TeammateArrowLocation arrowLocation, float f, float f2) {
        Tessellator tes = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        if (cBTeammate.isLeader()) {
            GL11.glColor4f(0.0f, 0.0f, 1.0f, 0.66f);
        } else {
            Color color = cBTeammate.getColor();
            GL11.glColor4f((float)color.getRed() / (float)255, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.66f);
        }
        float f3 = 8;
        float f4 = 10;
        GL11.glPushMatrix();
        GL11.glTranslatef(f, f2, 0.0f);
        switch (arrowLocation) {
            case TOP:
                tes.startDrawingQuads();
                tes.addVertex(f3 / 2.0f, f4 / 2.0f, 0.0);
                tes.addVertex(-f3 / 2.0f, 0.0, 0.0);
                tes.addVertex(f3 / 2.0f, -f4 / 2.0f, 0.0);
                tes.addVertex(-f3 / 2.0f, 0.0, 0.0);
                tes.draw();
                break;
            case LEFT:
                tes.startDrawingQuads();
                tes.addVertex(-f3 / 2.0f, f4 / 2.0f, 0.0);
                tes.addVertex(f3 / 2.0f, 0.0, 0.0);
                tes.addVertex(-f3 / 2.0f, -f4 / 2.0f, 0.0);
                tes.addVertex(f3 / 2.0f, 0.0, 0.0);
                tes.draw();
                break;
            case BOTTOM:
                tes.startDrawingQuads();
                tes.addVertex(-f3 / 2.0f, -f4 / 2.0f, 0.0);
                tes.addVertex(0.0, f4 / 2.0f, 0.0);
                tes.addVertex(f3 / 2.0f, -f4 / 2.0f, 0.0);
                tes.addVertex(0.0, f4 / 2.0f, 0.0);
                tes.draw();
                break;
            case RIGHT:
                tes.startDrawingQuads();
                tes.addVertex(-f3 / 2.0f, f4 / 2.0f, 0.0);
                tes.addVertex(0.0, -f4 / 2.0f, 0.0);
                tes.addVertex(f3 / 2.0f, f4 / 2.0f, 0.0);
                tes.addVertex(0.0, -f4 / 2.0f, 0.0);
                tes.draw();
        }
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(CBTeammate teammate, float f, float f2, float f3) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        if (teammate.isLeader()) {
            GL11.glColor4f(0.0f, 0.0f, 1.0f, 0.66f);
        } else {
            Color color = teammate.getColor();
            GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, 0.66f);
        }
        GL11.glPushMatrix();
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(f * 2.0f, 0.0f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
        tessellator.startDrawingQuads();
        tessellator.addVertex(-f, f2, 0.0);
        tessellator.addVertex(-f, f2 + f3 / 2.0f, 0.0);
        tessellator.addVertex(f, f2 + f3 / 2.0f, 0.0);
        tessellator.addVertex(f, f2, 0.0);
        tessellator.draw();
        GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
        GL11.glTranslatef(f * 2.0f + 1.0f, f3 / 2.0f + 1.0f, 0.0f);
        tessellator.startDrawingQuads();
        tessellator.addVertex(-f / 2.0f + 1.0f, f2, 0.0);
        tessellator.addVertex(-f / 2.0f + 1.0f, f2 + f3 / 2.0f, 0.0);
        tessellator.addVertex(f, f2 + f3 / 2.0f, 0.0);
        tessellator.addVertex(f, f2, 0.0);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    private void onDisconnect(DisconnectEvent event) {
        this.teammates.clear();
    }

    public CBTeammate createTeammate(String teammateKey) {
        for (CBTeammate teammate : this.teammates) {
            if (!teammate.getUuid().equals(teammateKey)) continue;
            return teammate;
        }
        return null;
    }

    public void setEnabled(boolean bl) {
        if (bl && !this.enabled) {
            this.enabled = true;
            CheatBreaker.getInstance().getEventBus().addEvent(GuiDrawEvent.class, this::onDraw);
            CheatBreaker.getInstance().getEventBus().addEvent(DisconnectEvent.class, this::onDisconnect);
        } else if (!bl && this.enabled) {
            this.enabled = false;
            CheatBreaker.getInstance().getEventBus().removeEvent(GuiDrawEvent.class, this::onDraw);
            CheatBreaker.getInstance().getEventBus().removeEvent(DisconnectEvent.class, this::onDisconnect);
        }
    }

    public enum TeammateArrowLocation {
        TOP,
        LEFT,
        BOTTOM,
        RIGHT
    }

}
