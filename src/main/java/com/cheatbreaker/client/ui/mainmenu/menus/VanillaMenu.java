package com.cheatbreaker.client.ui.mainmenu.menus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class VanillaMenu extends GuiScreen implements GuiYesNoCallback {
    private static final Logger eventButton = LogManager.getLogger();
    private static final Random lastMouseEvent = new Random();
    private final float field_146298_h;
    private String splashText = "missingno";
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final Object threadLock = new Object();
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String selectedButton = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private double lllIlIIllllIIIIlIllIlIIII;
    private float lIIIIlllIIlIlllllIlIllIII = 0.0f;
    private final ResourceLocation logoOuter = new ResourceLocation("client/logo_outer.png");
    private final ResourceLocation logoInner = new ResourceLocation("client/logo_inner.png");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public VanillaMenu() {
        this.field_146972_A = selectedButton;
        BufferedReader var1 = null;
        try {
            String var3;
            ArrayList<String> var2 = new ArrayList<String>();
            var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            while ((var3 = var1.readLine()) != null) {
                if ((var3 = var3.trim()).isEmpty()) continue;
                var2.add(var3);
            }
            if (!var2.isEmpty()) {
                do {
                    this.splashText = var2.get(lastMouseEvent.nextInt(var2.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        } catch (IOException ignored) {}
        finally {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (IOException ignored) {}
            }
        }
        this.field_146298_h = lastMouseEvent.nextFloat();
        this.field_92025_p = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.func_153193_b()) {
            this.field_92025_p = I18n.format("title.oldgl1");
            this.field_146972_A = I18n.format("title.oldgl2");
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
        this.lllIlIIllllIIIIlIllIlIIII += 0.06283185307179587;
        this.lIIIIlllIIlIlllllIlIllIII = (float)((Math.sin(this.lllIlIIllllIIIIlIllIlIIII) / 2.0 + 0.5) * 180.0);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        } else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        } else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        boolean var2 = true;
        int var3 = this.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.lIIIIIIIIIlIllIIllIlIIlIl(var3, 24);
        } else {
            this.lIIIIlIIllIIlIIlIIIlIIllI(var3, 24);
        }
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 48, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 48, 98, 20, I18n.format("menu.quit")));
        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 48));
        Object var4 = this.threadLock;
        Object var5 = this.threadLock;
        Object object = this.threadLock;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int var6 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - var6) / 2;
            this.field_92021_u = this.buttonList.get(0).field_146129_i - 24;
            this.field_92020_v = this.field_92022_t + var6;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(int var1, int var2) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var1, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, var1 + var2 * 1, I18n.format("menu.multiplayer")));
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(int var1, int var2) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, var1, I18n.format("menu.playdemo")));
        this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, var1 + var2 * 1, I18n.format("menu.resetdemo"));
        this.buttonList.add(this.buttonResetDemo);
        ISaveFormat var3 = this.mc.getSaveLoader();
        WorldInfo var4 = var3.getWorldInfo("Demo_World");
        if (var4 == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        ISaveFormat var2;
        WorldInfo var3;
        if (var1.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (var1.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (var1.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (var1.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (var1.id == 14) {
            this.IlIlIIIlllIIIlIlllIlIllIl();
        }
        if (var1.id == 4) {
            this.mc.shutdown();
        }
        if (var1.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (var1.id == 12 && (var3 = (var2 = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
            this.mc.displayGuiScreen(var4);
        }
    }

    private void IlIlIIIlllIIIlIlllIlIllIl() {
        RealmsBridge var1 = new RealmsBridge();
        var1.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean var1, int var2) {
        if (var1 && var2 == 12) {
            ISaveFormat var6 = this.mc.getSaveLoader();
            var6.flushCache();
            var6.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (var2 == 13) {
            if (var1) {
                try {
                    Class<?> var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null);
                    var3.getMethod("browse", URI.class).invoke(var4, new URI(this.field_104024_v));
                } catch (Throwable var5) {
                    eventButton.error("Couldn't open link", var5);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(int var1, int var2, float var3) {
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int var5 = 8;
        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GL11.glPushMatrix();
            float var7 = ((float)(var6 % var5) / (float)var5 - 0.5f) / 64.0f;
            float var8 = ((float)(var6 / var5) / (float)var5 - 0.5f) / 64.0f;
            float var9 = 0.0f;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(MathHelper.sin(((float)this.panoramaTimer + var3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-((float)this.panoramaTimer + var3) * 0.055882353f * 1.7894737f, 0.0f, 1.0f, 0.0f);
            for (int var10 = 0; var10 < 6; ++var10) {
                GL11.glPushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var10]);
                var4.startDrawingQuads();
                var4.setColorRGBA_I(0xFFFFFF, 255 / (var6 + 1));
                float var11 = 0.0f;
                var4.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var11, 1.0f - var11);
                var4.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var11, 1.0f - var11);
                var4.draw();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        var4.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(float var1) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColorMask(true, true, true, false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        GL11.glDisable(3008);
        int var3 = 3;
        for (int var4 = 0; var4 < var3; ++var4) {
            var2.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (float)(var4 + 1));
            int var5 = this.width;
            int var6 = this.height;
            float var7 = (float)(var4 - var3 / 2) / 256.0f;
            var2.addVertexWithUV(var5, var6, zLevel, 0.0f + var7, 1.0);
            var2.addVertexWithUV(var5, 0.0, zLevel, 1.0f + var7, 1.0);
            var2.addVertexWithUV(0.0, 0.0, zLevel, 1.0f + var7, 0.0);
            var2.addVertexWithUV(0.0, var6, zLevel, 0.0f + var7, 0.0);
        }
        var2.draw();
        GL11.glEnable(3008);
        GL11.glColorMask(true, true, true, true);
    }

    private void IlllIIIlIlllIllIlIIlllIlI(int var1, int var2, float var3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
        this.lIIIIIIIIIlIllIIllIlIIlIl(var1, var2, var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.lIIIIlIIllIIlIIlIIIlIIllI(var3);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = this.width > this.height ? 120.0f / (float)this.width : 120.0f / (float)this.height;
        float var6 = (float)this.height * var5 / 256.0f;
        float var7 = (float)this.width * var5 / 256.0f;
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var8 = this.width;
        int var9 = this.height;
        var4.addVertexWithUV(0.0, var9, zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glDisable(3008);
        this.IlllIIIlIlllIllIlIIlllIlI(mouseX, mouseY, partialTicks);
        GL11.glEnable(3008);
        Tessellator var4 = Tessellator.instance;
        int var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        boolean var7 = true;
        VanillaMenu.drawGradientRect(0.0f, 0.0f, (float)this.width, (float)this.height, -2130706433, 0xFFFFFF);
        VanillaMenu.drawGradientRect(0.0f, 0.0f, (float)this.width, (float)this.height, 0, Integer.MIN_VALUE);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2 - 40, this.height / 4 - 40, 0.0f);
        int var8 = 40;
        GL11.glTranslatef(var8, var8, var8);
        GL11.glRotatef(this.lIIIIlllIIlIlllllIlIllIII, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-var8, -var8, -var8);
        RenderUtil.renderIcon(this.logoOuter, (float)var8, 0.0f, 0.0f);
        GL11.glPopMatrix();
        RenderUtil.renderIcon(this.logoInner, 40.0f, (float)(this.width / 2 - 40), (float)(this.height / 4 - 38));
        var4.setColorOpaque_I(-1);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2 + 90, 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float var9 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * 3.998391f * 0.78571427f * 2.0f) * 0.09589041f * 1.0428572f);
        var9 = var9 * 100.0f / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GL11.glScalef(var9, var9, var9);
        GL11.glPopMatrix();
        String commitText = "CheatBreaker " + CheatBreaker.getInstance().getGitBuildVersion() + " (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch() + ")";
        this.drawString(this.fontRendererObj, commitText, 2, this.height - 10, -1);
        String var11 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, var11, this.width - this.fontRendererObj.getStringWidth(var11) - 2, this.height - 10, -1);
        if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
            VanillaMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 0x55200000);
            this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, this.buttonList.get(0).field_146129_i - 12, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object var4 = this.threadLock;
        Object var5 = this.threadLock;
        Object object = this.threadLock;
        if (mouseX >= 7 && mouseX <= 39 && mouseY >= 5 && mouseY <= 20) {
            MainMenuBase.switchMenu();
        }
        synchronized (object) {
            if (this.field_92025_p.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink var6 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                var6.func_146358_g();
                this.mc.displayGuiScreen(var6);
            }
        }
    }
}
