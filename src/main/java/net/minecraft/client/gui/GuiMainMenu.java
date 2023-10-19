package net.minecraft.client.gui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.cosmetic.EmoteGUI;
import com.cheatbreaker.client.ui.element.type.ImageButtonElement;
import com.cheatbreaker.client.ui.mainmenu.buttons.MainMenuAccountButton;
import com.cheatbreaker.client.ui.mainmenu.buttons.MainMenuButton;
import com.cheatbreaker.client.ui.mainmenu.buttons.MainMenuCosmeticsMenu;
import com.cheatbreaker.client.ui.mainmenu.menus.VanillaMenu;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
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

import java.io.*;
import java.net.URI;
import java.util.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private float updateCounter;
    private String splashText = "missingno";
    private GuiButton buttonResetDemo;
    private static int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final Object threadLock = new Object();
    private String openGLWarning1;
    private String openGLWarning2 = field_96138_a;
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts;
    private static final ResourceLocation minecraftTitleTextures;
    private static final ResourceLocation[] titlePanoramaPaths;
    public static final String field_96138_a;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private final List mainButtons = new ArrayList();
    private MainMenuAccountButton accountButton;
    private boolean lIIIlllIlIlllIIIIIIIIIlII = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GuiMainMenu() {
        BufferedReader bufferedReader = null;
        try {
            String s;
            ArrayList<String> list = new ArrayList<String>();
            bufferedReader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            while ((s = bufferedReader.readLine()) != null) {
                if ((s = s.trim()).isEmpty()) continue;
                list.add(s);
            }
            if (!list.isEmpty()) {
                do {
                    this.splashText = list.get(RANDOM.nextInt(list.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        } catch (IOException ignored) {}
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {}
            }
        }
        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.func_153193_b()) {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    @Override
    public void updateScreen() {
        ++panoramaTimer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char c, int n) {
        this.mc.func_152348_aa();
        if (n == 1) {
            this.mc.displayGuiScreen(new VanillaMenu());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     */
    @Override
    public void initGui() {
        String string;
        Object object3;
        Object object2;
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.buttonList.add(new MainMenuButton(0, 45, 0, 50, 25, "OPTIONS", false));
        this.buttonList.add(new MainMenuButton(5, 95, 0, 50, 25, "LANGUAGE", false));
        this.buttonList.add(new MainMenuButton(66, 145, 0, 65, 25, "COSMETICS", false));
//        this.buttonList.add(new MainMenuButton(67, 195, 0, 65, 25, "EMOTES", false));
        this.buttonList.add(new ImageButtonElement(4, new ResourceLocation("client/icons/exit-64.png"), this.width - 65, 0, 65, 25, "EXIT", false));
        File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "launcher_accounts.json");
        ArrayList<HashMap<String, String>> accountList = new ArrayList<>();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                object2 = new JsonParser();
                object3 = ((JsonParser)object2).parse(fileReader);
                string = "";
                for (Map.Entry<String, JsonElement> entry : ((JsonElement)object3).getAsJsonObject().entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("clientToken")) {
                        string = entry.getValue().getAsString();
                    }
                    if (!entry.getKey().equalsIgnoreCase("authenticationDatabase")) continue;
                    for (Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("clientToken", string);
                        for (Map.Entry<String, JsonElement> entry3 : entry2.getValue().getAsJsonObject().entrySet()) {
                            if (entry3.getKey().equalsIgnoreCase("profiles")) {
                                for (Map.Entry<String, JsonElement> entry4 : entry3.getValue().getAsJsonObject().entrySet()) {
                                    hashMap.put("uuid", entry4.getKey());
                                    for (Map.Entry<String, JsonElement> entry5 : entry4.getValue().getAsJsonObject().entrySet()) {
                                        hashMap.put("displayName", entry5.getValue().getAsString());
                                    }
                                }
                                continue;
                            }
                            if (!entry3.getKey().equalsIgnoreCase("username") &&
                                    !entry3.getKey().equalsIgnoreCase("displayName") && !entry3.getKey().equalsIgnoreCase("uuid") && !entry3.getKey().equalsIgnoreCase("accessToken")) continue;
                            hashMap.put(entry3.getKey(), entry3.getValue().getAsString());
                        }
                        accountList.add(hashMap);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.accountButton = null;
        this.mainButtons.clear();
        int n = 0;
        for (Map<String, String> accountMap : accountList) {
            String accountName = accountMap.get("displayName");
            MainMenuAccountButton mainMenuAccountButton = new MainMenuAccountButton(50, accountMap, this.width - 200, n * 25, 130, 25);
            this.mainButtons.add(mainMenuAccountButton);
            if (this.mc.getSession() != null && accountName.equalsIgnoreCase(this.mc.getSession().getUsername())) {
                this.accountButton = mainMenuAccountButton;
                if (n != 0) {
                    MainMenuAccountButton entry = (MainMenuAccountButton)this.mainButtons.get(0);
                    entry.field_146129_i = n * 25;
                    this.accountButton.field_146129_i = 0;
                }
            }
            ++n;
        }
        if (this.accountButton == null && !this.mainButtons.isEmpty()) {
            this.accountButton = (MainMenuAccountButton)this.mainButtons.get(0);
        }
        object2 = this.threadLock;
        object3 = this.threadLock;
        synchronized (object3) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int n2 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - n2) / 2;
            this.field_92021_u = this.buttonList.get(0).field_146129_i - 24;
            this.field_92020_v = this.field_92022_t + n2;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }
    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    protected void addSingleplayerMultiplayerButtons(int n, int n2) {
        this.buttonList.add(new MainMenuButton(1, this.width / 2 - 65, n + 24, 130, 24, I18n.format("menu.singleplayer")));
        this.buttonList.add(new MainMenuButton(2, this.width / 2 - 65, n + 52, 130, 24, I18n.format("menu.multiplayer")));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        ISaveFormat iSaveFormat;
        WorldInfo worldInfo;
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (guiButton.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (guiButton.id == 66) {
            this.mc.displayGuiScreen(new MainMenuCosmeticsMenu());
        }
        if (guiButton.id == 67) {
            this.mc.displayGuiScreen(new EmoteGUI(8));
        }
        if (guiButton.id == 67) {
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (guiButton.id == 14) {
            this.IlIlIIIlllIIIlIlllIlIllIl();
        }
        if (guiButton.id == 4) {
            this.mc.shutdown();
        }
        if (guiButton.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (guiButton.id == 12 && (worldInfo = (iSaveFormat = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo guiYesNo = GuiSelectWorld.func_152129_a(this, worldInfo.getWorldName(), 12);
            this.mc.displayGuiScreen(guiYesNo);
        }
    }

    private void IlIlIIIlllIIIlIlllIlIllIl() {
        RealmsBridge realmsBridge = new RealmsBridge();
        realmsBridge.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean bl, int n) {
        if (bl && n == 12) {
            ISaveFormat iSaveFormat = this.mc.getSaveLoader();
            iSaveFormat.flushCache();
            iSaveFormat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (n == 13) {
            if (bl) {
                try {
                    Class<?> class_ = Class.forName("java.awt.Desktop");
                    Object object = class_.getMethod("getDesktop", new Class[0]).invoke(null);
                    class_.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable) {
                    logger.error("Couldn't open link", throwable);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2, float f) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(120, 1.0f, 0.026190476f * 1.9090909f, 10);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(90, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int n3 = 8;
        for (int i = 0; i < n3 * n3; ++i) {
            GL11.glPushMatrix();
            float f2 = ((float)(i % n3) / (float)n3 - 0.2840909f * 1.76f) / (float)64;
            float f3 = ((float)(i / n3) / (float)n3 - 0.6666667f * 0.75f) / (float)64;
            float f4 = 0.0f;
            GL11.glTranslatef(f2, f3, f4);
            GL11.glRotatef(MathHelper.sin(((float) panoramaTimer + f) / (float)400) * (float)25 + (float)20, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-((float) panoramaTimer + f) * (0.10666667f * 0.9375f), 0.0f, 1.0f, 0.0f);
            for (int j = 0; j < 6; ++j) {
                GL11.glPushMatrix();
                if (j == 1) {
                    GL11.glRotatef(90, 0.0f, 1.0f, 0.0f);
                }
                if (j == 2) {
                    GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
                }
                if (j == 3) {
                    GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
                }
                if (j == 4) {
                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                }
                if (j == 5) {
                    GL11.glRotatef(-90, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[j]);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_I(0xFFFFFF, 255 / (i + 1));
                float f5 = 0.0f;
                tessellator.addVertexWithUV(-1, -1, 1.0, 0.0f + f5, 0.0f + f5);
                tessellator.addVertexWithUV(1.0, -1, 1.0, 1.0f - f5, 0.0f + f5);
                tessellator.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - f5, 1.0f - f5);
                tessellator.addVertexWithUV(-1, 1.0, 1.0, 0.0f + f5, 1.0f - f5);
                tessellator.draw();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        tessellator.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(float f) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        GL11.glDisable(3008);
        int n = 3;
        for (int i = 0; i < n; ++i) {
            tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (float)(i + 1));
            int n2 = this.width;
            int n3 = this.height;
            float f2 = (float)(i - n / 2) / (float)256;
            tessellator.addVertexWithUV(n2, n3, zLevel, 0.0f + f2, 1.0);
            tessellator.addVertexWithUV(n2, 0.0, zLevel, 1.0f + f2, 1.0);
            tessellator.addVertexWithUV(0.0, 0.0, zLevel, 1.0f + f2, 0.0);
            tessellator.addVertexWithUV(0.0, n3, zLevel, 0.0f + f2, 0.0);
        }
        tessellator.draw();
        GL11.glEnable(3008);
        GL11.glColorMask(true, true, true, true);
    }

    private void IlllIIIlIlllIllIlIIlllIlI(int n, int n2, float f) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
        this.lIIIIIIIIIlIllIIllIlIIlIl(n, n2, f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(f);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float f2 = this.width > this.height ? (float)120 / (float)this.width : (float)120 / (float)this.height;
        float f3 = (float)this.height * f2 / (float)256;
        float f4 = (float)this.width * f2 / (float)256;
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int n3 = this.width;
        int n4 = this.height;
        tessellator.addVertexWithUV(0.0, n4, zLevel, 0.021052632f * 23.75f - f3, 2.0666666f * 0.24193549f + f4);
        tessellator.addVertexWithUV(n3, n4, zLevel, 24.5f * 0.020408163f - f3, 0.12962963f * 3.857143f - f4);
        tessellator.addVertexWithUV(n3, 0.0, zLevel, 1.1315789f * 0.44186047f + f3, 11.5f * 0.04347826f - f4);
        tessellator.addVertexWithUV(0.0, 0.0, zLevel, 0.050847456f * 9.833334f + f3, 0.45918366f * 1.0888889f + f4);
        tessellator.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glDisable(3008);
        this.IlllIIIlIlllIllIlIIlllIlI(mouseX, mouseY, partialTicks);
        GL11.glEnable(3008);
        int n3 = 274;
        int n4 = this.width / 2 - n3 / 2;
        int n5 = 30;
        drawGradientRect(0.0f, 0.0f, (float)this.width, (float)this.height, 0x5FFFFFFF, 0x2FFFFFFF);
        drawRect(0.0f, 0.0f, this.width, 25, -819846622);
        String string = "CheatBreaker " + CheatBreaker.getInstance().getGitBuildVersion() + " (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch() + ")";
        this.drawString(this.fontRendererObj, string, 2, this.height - 10, -1);
        String string2 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, string2, this.width - this.fontRendererObj.getStringWidth(string2) - 2, this.height - 10, -1);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 0x55200000);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, this.buttonList.get(0).field_146129_i - 12, -1);
        }
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f2 = 30;
        float f3 = 15;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 10;
        float f7 = 5;
        GL11.glEnable(3042);
        this.mc.renderEngine.bindTexture(new ResourceLocation("client/icons/cb.png"));
        GL11.glBegin(7);
        GL11.glTexCoord2d(f4 / (float)5, f5 / (float)5);
        GL11.glVertex2d(f6, f7);
        GL11.glTexCoord2d(f4 / (float)5, (f5 + (float)5) / (float)5);
        GL11.glVertex2d(f6, f7 + f3);
        GL11.glTexCoord2d((f4 + (float)5) / (float)5, (f5 + (float)5) / (float)5);
        GL11.glVertex2d(f6 + f2, f7 + f3);
        GL11.glTexCoord2d((f4 + (float)5) / (float)5, f5 / (float)5);
        GL11.glVertex2d(f6 + f2, f7);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    protected void lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2) {
        int n3;
        for (n3 = 0; n3 < this.buttonList.size(); ++n3) {
            this.buttonList.get(n3).drawButton(this.mc, n, n2);
        }
        for (n3 = 0; n3 < this.labelList.size(); ++n3) {
            ((GuiLabel)this.labelList.get(n3)).func_146159_a(this.mc, n, n2);
        }
        if (this.accountButton != null) {
            this.lIIIlllIlIlllIIIIIIIIIlII = !this.lIIIlllIlIlllIIIIIIIIIlII ? n > this.accountButton.field_146128_h && n < this.accountButton.field_146128_h + this.accountButton.getWidth() && n2 > this.accountButton.field_146129_i && n2 < this.accountButton.field_146129_i + this.accountButton.getHeight() : n > this.accountButton.field_146128_h && n < this.accountButton.field_146128_h + this.accountButton.getWidth() && n2 > this.accountButton.field_146129_i && n2 < this.accountButton.field_146129_i + 25 * this.mainButtons.size();
            this.accountButton.drawButton(this.mc, n, n2);
            if (this.lIIIlllIlIlllIIIIIIIIIlII) {
                int n4 = this.mainButtons.size();
                int n5 = this.accountButton.getHeight();
                for (int i = 0; i < n4; ++i) {
                    MainMenuAccountButton mainMenuAccountButton = (MainMenuAccountButton)this.mainButtons.get(i);
                    if (mainMenuAccountButton != this.accountButton) {
                        mainMenuAccountButton.drawButton(this.mc, n, n2);
                    }
                    n5 += mainMenuAccountButton.getHeight();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object object = this.threadLock;
        if (mouseX <= 45 && mouseY <= 25) {
            this.mc.displayGuiScreen(new VanillaMenu());
        }
        if (this.lIIIlllIlIlllIIIIIIIIIlII) {
            int n4 = this.mainButtons.size();
            int n5 = this.accountButton.getHeight();
            for (int i = 0; i < n4; ++i) {
                MainMenuAccountButton mainMenuAccountButton = (MainMenuAccountButton)this.mainButtons.get(i);
                if (mainMenuAccountButton != this.accountButton && mouseX < mainMenuAccountButton.field_146128_h + mainMenuAccountButton.getWidth() && mouseY > mainMenuAccountButton.field_146129_i && mouseY < mainMenuAccountButton.field_146129_i + mainMenuAccountButton.getHeight()) {
                    if (!mainMenuAccountButton.lIIIIlIIllIIlIIlIIIlIIllI() || mainMenuAccountButton == this.accountButton) break;
                    this.accountButton.field_146129_i = mainMenuAccountButton.field_146129_i;
                    mainMenuAccountButton.field_146129_i = 0;
                    this.accountButton = mainMenuAccountButton;
                    break;
                }
                n5 += mainMenuAccountButton.getHeight();
            }
        }
        Object object2 = this.threadLock;
        synchronized (object2) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiConfirmOpenLink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiConfirmOpenLink.func_146358_g();
                this.mc.displayGuiScreen(guiConfirmOpenLink);
            }
        }
    }

    static {
        splashTexts = new ResourceLocation("texts/splashes.txt");
        minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
        titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
        field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }
}
