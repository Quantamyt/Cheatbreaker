package net.minecraft.client.gui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.mainmenu.menus.VanillaMenu;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.warning.CompetitiveGameWarningGui;
import com.cheatbreaker.client.util.sessionserver.SessionServer;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiIngameMenu extends GuiScreen {
    private int selectedButton;
    private int eventButton;
    private GuiButton modsButton;
    private final ResourceLocation logo = new ResourceLocation("client/logo_white.png");
    private final ResourceLocation outerLogo = new ResourceLocation("client/logo_255_outer.png");
    private final ResourceLocation innerLogo = new ResourceLocation("client/logo_108_inner.png");
    private final CosineFade logoRotationTime = new CosineFade(4000L);
    private long currentTime;
    private boolean modsButtonHeldDown = false;
    private final CosineFade loginServicesFadeTime = new CosineFade(1500L);

    @Override
    public void initGui() {
        this.selectedButton = 0;
        this.buttonList.clear();
        int n = -16;
        boolean bl = true;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + n, I18n.format("menu.returnToMenu")));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = I18n.format("menu.disconnect");
        }
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + n, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + n, 98, 20, I18n.format("menu.options")));
        GuiButton guiButton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + n, 98, 20, I18n.format("menu.shareToLan"));
        guiButton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + n, 98, 20, I18n.format("gui.achievements")));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + n, 98, 20, I18n.format("gui.stats")));
        if (!guiButton.enabled) {
            this.modsButton = new GuiButton(10, this.width / 2 + 2, this.height / 4 + 96 + n, 98, 20, "Mods");
            this.buttonList.add(this.modsButton);
            this.buttonList.add(new GuiButton(16, this.width / 2 - 100, this.height / 4 + 72 + n, 200, 20, "Server List"));
        } else {
            this.buttonList.add(guiButton);
            this.buttonList.add(new GuiButton(16, this.width / 2 - 100, this.height / 4 + 72 + n, 98, 20, "Server List"));
            this.modsButton = new GuiButton(10, this.width / 2 + 2, this.height / 4 + 72 + n, 98, 20, "Mods");
            this.buttonList.add(this.modsButton);
        }
    }

    private void drawLogo(double d, double d2) {
        try {
            if (!this.logoRotationTime.isTimeNotAtZero()) {
                this.logoRotationTime.startAnimation();
                this.logoRotationTime.loopAnimation();
            }
            float f = 18;
            double d3 = d / (double)2 - (double)f;
            double d4 = this.buttonList.size() > 2 ? (double)((float) this.buttonList.get(1).field_146129_i - f - (float)32) : (double)-100;
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef((float)d3, (float)d4, 1.0f);
            GL11.glTranslatef(f, f, f);
            GL11.glRotatef((float)180 * this.logoRotationTime.getFadeAmount(), 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-f, -f, -f);
            RenderUtil.renderEIcon(this.outerLogo, f, 0.0f, 0.0f);
            GL11.glPopMatrix();
            RenderUtil.renderEIcon(this.innerLogo, f, (float)d3, (float)d4);
        } catch (Exception exception) {

        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        this.modsButtonHeldDown = false;
        switch (guiButton.id) {
            case 16:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                if (CheatBreaker.getInstance().getCbNetHandler().isCompetitveGamemode()) {
                    this.mc.displayGuiScreen(new CompetitiveGameWarningGui(this));
                    break;
                }
                guiButton.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new VanillaMenu());
            default:
                break;
            case 4:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
                break;
            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
                break;
            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 10:
                if (CheatBreaker.getInstance().getGlobalSettings().streamerMode.getBooleanValue() && CheatBreaker.getInstance().getGlobalSettings().holdDownModsGameMenuButton.getBooleanValue()) {
                    this.modsButtonHeldDown = true;
                    this.currentTime = System.currentTimeMillis();
                } else{
                    this.mc.displayGuiScreen(new HudLayoutEditorGui());
                }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.eventButton;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int n3 = 600;
        int n4 = 356;
        double d = (double)Math.min(this.width, this.height) / ((double)n3 * (double)9);
        int n5 = (int)((double)n3 * d);
        int n6 = (int)((double)n4 * d);
        if (CheatBreaker.getInstance().getGlobalSettings().getCurrentMenuSetting().getIntegerValue() == 1) {
            this.drawLogo(this.width, this.height);
        } else {
            RenderUtil.renderIcon(this.logo, this.width / 2 - n5 / 2, n6 * 2, n5, n6);
        }


        boolean bl = false;
        for (SessionServer sessionServer : CheatBreaker.getInstance().sessionServers) {
            if (sessionServer.getStatus() != SessionServer.StatusColor.DOWN) continue;
            bl = true;
        }
        if (this.modsButtonHeldDown && Mouse.isButtonDown(0) && CheatBreaker.getInstance().getGlobalSettings().streamerMode.getBooleanValue() && CheatBreaker.getInstance().getGlobalSettings().holdDownModsGameMenuButton.getBooleanValue()) {
            int countdown = this.currentTime == 0L ? (int) CheatBreaker.getInstance().getGlobalSettings().holdDuration.getFloatValue() : (int)((CheatBreaker.getInstance().getGlobalSettings().holdDuration.getFloatValue() * 1000.0f + 999L - (System.currentTimeMillis() - this.currentTime)) / 1000L);
            this.modsButton.displayString = "Mods (" + countdown + ")";
            if (countdown <= 0) {
                this.mc.displayGuiScreen(new HudLayoutEditorGui());
            }
        } else {
            this.modsButton.displayString = "Mods";
            this.currentTime = -0L;
            this.modsButtonHeldDown = false;
        }


        if (bl) {
            if (!this.loginServicesFadeTime.isTimeNotAtZero()) {
                this.loginServicesFadeTime.startAnimation();
            }
            this.loginServicesFadeTime.loopAnimation();
            drawRect(this.width / 2 - 100, this.height / 4 + 128, this.width / 2 + 100, this.height / 4 + 142, 0x6F000000);
            drawRect(this.width / 2 - 100, this.height / 4 + 128, this.width / 2 + 100, this.height / 4 + 142, new Color(1.0f, 0.2f * 0.75f, 10.6f * 0.014150944f, 1.4142857f * 0.45959595f * this.loginServicesFadeTime.getFadeAmount()).getRGB());
            CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString("Some login services might be offline".toUpperCase(), this.width / 2, this.height / 4 + 130, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
