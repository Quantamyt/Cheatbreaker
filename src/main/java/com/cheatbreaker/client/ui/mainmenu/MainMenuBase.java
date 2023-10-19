package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.AbstractGui;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.cosmetics.GuiCosmetics;
import com.cheatbreaker.client.ui.mainmenu.element.IconButtonElement;
import com.cheatbreaker.client.ui.mainmenu.element.TextButtonElement;
import com.cheatbreaker.client.ui.mainmenu.element.TextWithShadowButton;
import com.cheatbreaker.client.ui.mainmenu.menus.*;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.Proxy;
import java.net.URI;
import java.util.List;
import java.util.*;

public class MainMenuBase extends AbstractGui { // TODO: Finish mapping this class
    @Getter private final List<CBAccount> accounts = new ArrayList<>();

    private final ResourceLocation logo = new ResourceLocation("client/logo_42.png");
    private DynamicTexture viewportTexture;
    private final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("client/panorama/0.png"), new ResourceLocation("client/panorama/1.png"), new ResourceLocation("client/panorama/2.png"), new ResourceLocation("client/panorama/3.png"), new ResourceLocation("client/panorama/4.png"), new ResourceLocation("client/panorama/5.png")};
    private ResourceLocation backgroundTexture;
    private static int rotationAngle = 4100;
    private float sessionNameWidth = CheatBreaker.getInstance().robotoRegular13px.getStringWidth(Minecraft.getMinecraft().getSession().getUsername());

    private final IconButtonElement exitButton = new IconButtonElement(new ResourceLocation("client/icons/delete-64.png"));
    private final IconButtonElement languageButton = new IconButtonElement(6.0f, new ResourceLocation("client/icons/globe-24.png"));
    private final AccountList accountsButton = new AccountList(this, Minecraft.getMinecraft().getSession().getUsername(),
            CheatBreaker.getInstance().getHeadIcon(Minecraft.getMinecraft().getSession().getUsername(),
                    Minecraft.getMinecraft().getSession().getPlayerID()));

    private final TextButtonElement optionsButton = new TextButtonElement("OPTIONS");
    private final TextButtonElement changelogButton = new TextButtonElement("CHANGELOG");
    private final TextButtonElement cosmeticsButton = new TextButtonElement("COSMETICS");

    private final List<TextButtonElement> horizontalButtonList = ImmutableList.of(this.optionsButton, this.changelogButton , this.cosmeticsButton);

    private final String summaryBuildInfo = CheatBreaker.getInstance().getGitBuildVersion().isEmpty()
            || CheatBreaker.getInstance().getGitBuildVersion().equals("Production")
            || CheatBreaker.getInstance().getGitBuildVersion().toLowerCase().equals(CheatBreaker.getInstance().getGitBranch()) ? "CheatBreaker (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch() + ")" : "CheatBreaker " + CheatBreaker.getInstance().getGitBuildVersion() + " (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch() + ")";
    private final String copyright = "Copyright Mojang AB. Do not distribute!";
    private final String confidentialNoticeString = "Unauthorized disclosure of this build in any manner may result in disciplinary action up to and including termination of an assignment.";
    private String clientToken = "";


    private final TextWithShadowButton commitButton = new TextWithShadowButton(summaryBuildInfo);
    private final TextWithShadowButton creditsButton = new TextWithShadowButton(copyright);
    private final TextWithShadowButton confidentialButton = new TextWithShadowButton(confidentialNoticeString, 50);
    private final ColorFade overlayGradient = new ColorFade(0xF000000, -16777216);
    private final File launcherAccounts = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "launcher_accounts.json");

    public MainMenuBase() {
        this.loadAccounts();
    }

    private void loadAccounts() {
        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<HashMap<String, String>> var2 = new ArrayList<>();
        if (this.launcherAccounts.exists()) {
            try {
                FileReader launcherProfiles = new FileReader(this.launcherAccounts);
                JsonParser parser = new JsonParser();
                JsonElement elements = parser.parse(launcherProfiles);
                Iterator<Map.Entry<String, JsonElement>> iterator = elements.getAsJsonObject().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, JsonElement> entry = iterator.next();
                    for (Map.Entry<String, JsonElement> var4 : entry.getValue().getAsJsonObject().entrySet()) {
                        HashMap<String, String> var5 = new HashMap<>();
                        for (Map.Entry<String, JsonElement> var7 : var4.getValue().getAsJsonObject().entrySet()) {
                            if (var7.getKey().equalsIgnoreCase("minecraftProfile")) {
                                for (Map.Entry<String, JsonElement> o1 : var7.getValue().getAsJsonObject().entrySet()) {
                                    if (o1.getKey().equals("id")) {
                                        var5.put("uuid", o1.getValue().getAsString());
                                    }
                                    if (o1.getKey().equals("name")) {
                                        var5.put("displayName", o1.getValue().getAsString());
                                    }
                                }
                            } else {
                                if (!var7.getKey().equalsIgnoreCase("username") && !var7.getKey().equalsIgnoreCase("name") && !var7.getKey().equalsIgnoreCase("id") && !var7.getKey().equalsIgnoreCase("accessToken")) {
                                    continue;
                                }
                                var5.put(var7.getKey(), var7.getValue().getAsString());
                            }
                        }
                        var2.add(var5);
                    }
                    while (iterator.hasNext()) {
                        Object o2 = iterator.next();
                        Map.Entry var10 = (Map.Entry)o2;
                        if (var10.getKey().equals("mojangClientToken")) {
                            JsonPrimitive clientTokenPrim = (JsonPrimitive) var10.getValue();
                            this.clientToken = clientTokenPrim.getAsString();
                        }
                    }
                }
            } catch (Exception ignored) {}
        }
        this.accounts.clear();
        this.sessionNameWidth = (float)CheatBreaker.getInstance().robotoRegular13px.getStringWidth(Minecraft.getMinecraft().getSession().getUsername());
        for (Map<String, String> var11 : var2) {
            CBAccount var22 = new CBAccount(var11.get("username"), this.clientToken, var11.get("accessToken"), var11.get("displayName"), var11.get("uuid"));
            this.accounts.add(var22);
            float var13 = (float)CheatBreaker.getInstance().robotoRegular13px.getStringWidth(var22.getDisplayName());
            if (var13 > this.sessionNameWidth) {
                this.sessionNameWidth = var13;
            }
            if (mc.getSession() != null && clientToken.equalsIgnoreCase(mc.getSession().getUsername())) {
                this.accountsButton.setUsername(var22.getDisplayName());
                this.accountsButton.setResourceLocation(CheatBreaker.getInstance().getHeadIcon(var22.getDisplayName(), var22.getDisplayName()));
                this.getAccountListElementSize();
            }
        }
    }

    @Override
    protected void keyTyped(char c, int n) {
        if (n == 1 && Minecraft.getMinecraft().currentScreen instanceof MainMenu) {
            return;
        }
        super.keyTyped(c, n);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (this.accountsButton != null) {
            this.accountsButton.handleElementMouse();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++rotationAngle;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);

        float horizontalButtonXPosition = 0;
        for (TextButtonElement button : this.horizontalButtonList) {
            button.setElementSize(124.0f + horizontalButtonXPosition, 6.0f, CheatBreaker.getInstance().robotoBold14px.getStringWidth(button.string) * 1.14F, 20.0f);
            horizontalButtonXPosition += CheatBreaker.getInstance().robotoBold14px.getStringWidth(button.string) + 12.0F;
        }

        this.exitButton.setElementSize(this.getScaledWidth() - 30.0f, 7.0f, 23.0f, 17.0f);
        this.languageButton.setElementSize(this.getScaledWidth() / 2.0f - 13.0f, this.getScaledHeight() - 17.0f, 26.0f, 18.0f);
        this.commitButton.setElementSize(5.0f, this.getScaledHeight() - 14.0f, CheatBreaker.getInstance().playRegular18px.getStringWidth(summaryBuildInfo), 18.0f);
        this.creditsButton.setElementSize(this.getScaledWidth() - (float)CheatBreaker.getInstance().playRegular18px.getStringWidth(copyright) - 5.0f, this.getScaledHeight() - 14.0f, CheatBreaker.getInstance().playRegular18px.getStringWidth(copyright), 18.0F);
        if (CheatBreaker.getInstance().isConfidentialBuild()) {
            this.confidentialButton.setElementSize(this.getScaledWidth() - (float)CheatBreaker.getInstance().playRegular18px.getStringWidth(copyright) - 5.0f, this.getScaledHeight() - 30.0F, CheatBreaker.getInstance().playRegular18px.getStringWidth(copyright), 20.0F);
        }
        this.getAccountListElementSize();
    }

    public void getAccountListElementSize() {
        this.accountsButton.setElementSize(this.getScaledWidth() - 35.0f - this.accountsButton.setWidthFromAccountString(this.sessionNameWidth), 7.0f, this.accountsButton.setWidthFromAccountString(this.sessionNameWidth), 17.0f);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glDisable(3008);
        this.lIIIIIIIIIlIllIIllIlIIlIl(mouseX, mouseY, 1.0f);
        GL11.glEnable(3008);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawMenu(float x, float y) {
        MainMenuBase.drawGradientRect(0.0f, 0.0f, this.getScaledWidth(), this.getScaledHeight(), 0x5FFFFFFF, 0x2FFFFFFF);
        MainMenuBase.drawGradientRect(0.0f, 0.0f, this.getScaledWidth(), 160.0f, -553648128, 0);
        boolean var3 = x < this.optionsButton.getXPosition() && y < 30.0f;
        Color var4 = this.overlayGradient.getColor(var3);
        CheatBreaker.getInstance().robotoRegular24px.drawString("CheatBreaker", 37.0f, 9.0f, var4.getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.logo, 10.0f, 8.0f, 6.0f);
        CheatBreaker.getInstance().robotoRegular24px.drawString("CheatBreaker", 36.0f, 8.0f, -1);
        this.exitButton.drawElementHover(x, y, true);
        this.languageButton.drawElementHover(x, y, true);
        this.accountsButton.drawElementHover(x, y, true);
        for (TextButtonElement button : this.horizontalButtonList) {
            button.drawElementHover(x, y, true);
        }
        this.commitButton.drawElementHover(x, y, true);
        this.creditsButton.drawElementHover(x, y, true);
        if (CheatBreaker.getInstance().isConfidentialBuild()) {
            this.confidentialButton.drawElementHover(x, y, true);
        }
    }

    @Override
    protected void mouseClicked(float var1, float var2, int var3) {
        this.exitButton.handleElementMouseClicked(var1, var2, var3, true);
        this.accountsButton.handleElementMouseClicked(var1, var2, var3, true);
        if (this.exitButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.shutdown();
        } else if (this.optionsButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else if (this.languageButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        } else if (this.cosmeticsButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiCosmetics());
        } else if (this.commitButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new BuildMenu());
        } else if (this.creditsButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiCreditsMenu());
        } else if (this.changelogButton.isMouseInside(var1, var2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new ChangelogMenu());
        } else if (this.confidentialButton.isMouseInside(var1, var2) && CheatBreaker.getInstance().isConfidentialBuild()) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new ConfidentialBuildNoticeMenu());
        } else if (var1 >= 8 && var1 <= 26 && var2 >= 6 && var2 <= 26) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            switchMenu();
        } else {
            boolean var4 = var1 < this.optionsButton.getXPosition() && var2 < 30.0f;
            if (var4 && !(this.mc.currentScreen instanceof MainMenu)) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                this.mc.displayGuiScreen(new MainMenu());
            }
        }
    }

    @Override
    public void mouseMovedOrUp(float var1, float var2, int var3) {
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(int var1, int var2, float var3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
        this.IlllIIIlIlllIllIlIIlllIlI(var1, var2, var3);
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
        float var8 = this.width;
        float var9 = this.height;
        var4.addVertexWithUV(0.0, var9, zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }

    private void IlllIIIlIlllIllIlIIlllIlI(int var1, int var2, float var3) {
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
            GL11.glRotatef(MathHelper.sin(((float) rotationAngle + var3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-((float) rotationAngle + var3) * 0.39240506f * 0.2548387f, 0.0f, 1.0f, 0.0f);
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
                this.mc.getTextureManager().bindTexture(this.titlePanoramaPaths[var10]);
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
            float var5 = this.width;
            float var6 = this.height;
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

    public String getAccessToken(String shit) {
        String token = "";
        String decoded = new String(Base64.getDecoder().decode(shit.split("\\.")[1]), Charsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(decoded).getAsJsonObject();
        if (jsonObject.get("yggt").getAsString() != null) {
            token = jsonObject.get("yggt").getAsString();
        }
        return token;
    }

    /*
     * Exception decompiling
     */
    public void writeStringToBuffer(String var1) {
        try {
            CBAccount var2 = null;

            for (CBAccount account : this.accounts) {
                if (account.getDisplayName().equals(var1)) {
                    var2 = account;
                }
            }

            if (var2 != null) {
                if (var2.getUuid().equalsIgnoreCase(Minecraft.getMinecraft().getSession().getPlayerID())) {
                    return;
                }

                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

                for (Session session : CheatBreaker.getInstance().sessions) {
                    if (session.func_148256_e().getId().toString().replaceAll("-", "").equalsIgnoreCase(var2.getUuid().replaceAll("-", ""))) {
                        Minecraft.getMinecraft().setSession(session);
                        this.accountsButton.setUsername(var2.getDisplayName());
                        this.accountsButton.setResourceLocation(var2.getHeadIcon());
                        this.getAccountListElementSize();
                        return;
                    }
                }

                YggdrasilAuthenticationService var26 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, this.clientToken);
                YggdrasilUserAuthentication var28 = (YggdrasilUserAuthentication)var26.createUserAuthentication(Agent.MINECRAFT);
                HashMap<String, Object> var5 = new HashMap<>();
                var5.put("uuid", var2.getUuid());
                var5.put("displayName", var2.getDisplayName());
                var5.put("username", var2.getUsername());
                var5.put("accessToken", var2.getAccessToken());
                var28.loadFromStorage(var5);

                Session session;
                try {
                    var28.logIn();
                    session = new Session(var28.getSelectedProfile().getName(), var28.getSelectedProfile().getId().toString(), var28.getAuthenticatedToken(), "mojang");
                } catch (AuthenticationException var21) {
                    var21.printStackTrace();
                    return;
                }

                File launcherAccountsFile = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "launcher_accounts.json");
                if (launcherAccountsFile.exists() && !var28.getAuthenticatedToken().equals(var2.getAccessToken())) {
                    try {
                        FileReader fileReader = new FileReader(launcherAccountsFile);
                        JsonParser var9 = new JsonParser();
                        JsonElement var10 = var9.parse(fileReader);
                        Map.Entry<String, JsonElement> var11 = null;
                        Iterator<Map.Entry<String, JsonElement>> var12 = var10.getAsJsonObject().entrySet().iterator();

                        label106:
                        while(true) {
                            Map.Entry<String, JsonElement> var13;
                            do {
                                if (!var12.hasNext()) {
                                    if (var11 != null) {
                                        var2.setAccessToken(var28.getAuthenticatedToken());
                                        var11.getValue().getAsJsonObject().remove("accessToken");
                                        var11.getValue().getAsJsonObject().addProperty("accessToken", var28.getAuthenticatedToken());
                                    }

                                    Gson var29 = (new GsonBuilder()).setPrettyPrinting().create();

                                    try {
                                        DataOutputStream var30 = new DataOutputStream(new FileOutputStream(launcherAccountsFile));
                                        var30.writeBytes(var29.toJson(var10).replace("\n", "\r\n"));
                                        var30.flush();
                                        var30.close();
                                        break label106;
                                    } catch (Exception var20) {
                                        var20.printStackTrace();
                                        return;
                                    }
                                }

                                var13 = var12.next();
                            } while(!var13.getKey().equalsIgnoreCase("authenticationDatabase"));

                            Iterator<Map.Entry<String, JsonElement>> var14 = var13.getValue().getAsJsonObject().entrySet().iterator();

                            label104:
                            while(var14.hasNext()) {
                                Map.Entry<String, JsonElement> var15 = var14.next();
                                Iterator<Map.Entry<String, JsonElement>> var16 = var15.getValue().getAsJsonObject().entrySet().iterator();

                                while(true) {
                                    Map.Entry<String, JsonElement> var17;
                                    do {
                                        if (!var16.hasNext()) {
                                            continue label104;
                                        }

                                        var17 = var16.next();
                                    } while(!var17.getKey().equalsIgnoreCase("profiles"));

                                    for (Map.Entry<String, JsonElement> var19 : var17.getValue().getAsJsonObject().entrySet()) {
                                        if (var19.getKey().replaceAll("-", "").equalsIgnoreCase(session.getPlayerID().replaceAll("-", ""))) {
                                            var11 = var15;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception var22) {
                        var22.printStackTrace();
                        return;
                    }
                }

                System.out.println("Updated accessToken and logged user in.");
                this.accountsButton.setUsername(var2.getDisplayName());
                this.accountsButton.setResourceLocation(var2.getHeadIcon());
                this.getAccountListElementSize();
                CheatBreaker.getInstance().sessions.add(session);
                Minecraft.getMinecraft().setSession(session);
                CheatBreaker.getInstance().getWSNetHandler().close();
            }
        } catch (Exception var23) {
            var23.printStackTrace();
        }

    }

    protected void openLink(URI p_146407_1_) {
        try {
            Class<?> var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, p_146407_1_);
        } catch (Throwable e) {
            CheatBreaker.getInstance().getLogger().error("Couldn't open link", e);
        }
    }

    public static void switchMenu() {
        int next = CheatBreaker.getInstance().getGlobalSettings().getCurrentMenu().ordinal() + 1;
        MenuTypes menu;
        if (next == 3) {
            menu = MenuTypes.OLD;
        } else {
            menu = MenuTypes.values()[next];
        }
        CheatBreaker.getInstance().getGlobalSettings().getCurrentMenuSetting().setValue(menu.ordinal());
        Minecraft.getMinecraft().displayGuiScreen(MenuTypes.getMenu(menu));
    }

    public enum MenuTypes {
        OLD,
        MAIN,
        VANILLA;

        public static GuiScreen getMenu(MenuTypes menuTypes) {
            switch (menuTypes) {
                case OLD:
                    return new CBOldMenu();
                case VANILLA:
                    return new VanillaMenu();
                default:
                    return new MainMenu();
            }
        }
    }
}
