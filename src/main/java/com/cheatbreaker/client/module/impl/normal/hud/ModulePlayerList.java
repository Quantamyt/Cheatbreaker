package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Allows the user to move and customize the player list/tab.
 * @see AbstractModule
 */
public class ModulePlayerList extends AbstractModule {
    public Setting background;
    public Setting rowBackground;
    public Setting highlightSelfRow;
    public Setting highlightCBRows;
    public Setting backgroundColor;
    public Setting pingRowColor;
    public Setting rowColor;
    public Setting highlightedSelfRowColor;
    public Setting highlightedCBRowColor;

    public Setting nameTextShadow;
    public Setting showLogo;
    public Setting highlightSelfNameInTab;
    public Setting highlightCBPlayersNamesInTab;
    public Setting highlightedSelfNameColor;
    public Setting highlightedCBNameColor;

    public Setting ping;
    public Setting pingAsNumber;
    public Setting pingTextShadow;
    public Setting dynamicPingNumberColor;
    public Setting staticPingColor;
    public Setting lowPingColor;
    public Setting mediumPingColor;
    public Setting highPingColor;
    public Setting extremelyHighPingColor;
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");

    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-689A-E]");

    public ModulePlayerList() {
        super("Player List");
        this.setDefaultAnchor(GuiAnchor.MIDDLE_TOP);
        this.setDefaultTranslations(0.0F, 12.0F);
        this.notRenderHUD = false;

        new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.background = new Setting(this, "Show Background").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.rowBackground = new Setting(this, "Show Row Background").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightSelfRow = new Setting(this, "Highlight Own Row").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightCBRows = new Setting(this, "Highlight CheatBreaker Players' Row").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.pingRowColor = new Setting(this, "Use Ping Color for Row Background").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.rowBackground.getValue());
        this.rowColor = new Setting(this, "Row Background Color").setValue(0x20FFFFFF).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.rowBackground.getValue());
        this.highlightedSelfRowColor = new Setting(this, "Self Row Color").setValue(0x40AA0000).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.rowBackground.getValue() && (Boolean) this.highlightSelfRow.getValue());
        this.highlightedCBRowColor = new Setting(this, "CheatBreaker Row Color").setValue(0x40DA4253).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.rowBackground.getValue() && (Boolean) this.highlightCBRows.getValue());
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x80000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("Name Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.nameTextShadow = new Setting(this, "Name Text Shadow").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.showLogo = new Setting(this, "Show CheatBreaker Logo").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightSelfNameInTab = new Setting(this, "Highlight Own Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightCBPlayersNamesInTab = new Setting(this, "Highlight CheatBreaker Players' Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightedSelfNameColor = new Setting(this, "Self Text Color").setValue(0xFFCC2222).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.highlightSelfNameInTab.getValue());
        this.highlightedCBNameColor = new Setting(this, "CheatBreaker Text Color").setValue(0xFFFB6576).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.highlightCBPlayersNamesInTab.getValue());

        new Setting(this, "label").setValue("Ping Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.ping = new Setting(this, "Show Ping").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.pingAsNumber = new Setting(this, "Show Ping as Number").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.ping.getValue());
        this.pingTextShadow = new Setting(this, "Ping Text Shadow").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue());
        this.dynamicPingNumberColor = new Setting(this, "Dynamic Ping Number Color").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue());
        this.staticPingColor = new Setting(this, "Static Ping Color").setValue(0xFFFFFF55).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue() && !(Boolean) this.dynamicPingNumberColor.getValue());
        this.lowPingColor = new Setting(this, "Low Ping Color").setValue(0xFF55FF55).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue() && (Boolean) this.dynamicPingNumberColor.getValue());
        this.mediumPingColor = new Setting(this, "Medium Ping Color").setValue(0xFFFFFF55).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue() && (Boolean) this.dynamicPingNumberColor.getValue());
        this.highPingColor = new Setting(this, "High Ping Color").setValue(0xFFFF5555).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue() && (Boolean) this.dynamicPingNumberColor.getValue());
        this.extremelyHighPingColor = new Setting(this, "Extremely High Ping Color").setValue(0xFFAA0000).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.ping.getValue() && (Boolean) this.pingAsNumber.getValue() && (Boolean) this.dynamicPingNumberColor.getValue());
        setDescription("Customize the player list tab.");
        addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    public String valueString() {
        return null;
    }

    private void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        GL11.glPushMatrix();
        scaleAndTranslate(event.getScaledResolution());
        render(0, 0);
        GL11.glPopMatrix();
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            ScoreObjective var48 = this.mc.theWorld.getScoreboard().func_96539_a(0);
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1 || var48 != null)) {
                render(0, 0);
            }
            GL11.glPopMatrix();
        }
    }

    public void render(int x, int y) {
        ProfileHandler profileHandler = CheatBreaker.getInstance().getProfileHandler();

        GL11.glEnable(GL11.GL_BLEND);
        int var41;
        String playerName;
        int var16;
        int var17;
        FontRenderer fontRenderer = this.mc.fontRenderer;
        ScoreObjective var48 = this.mc.theWorld.getScoreboard().func_96539_a(0);
        this.mc.mcProfiler.startSection("playerList");
        NetHandlerPlayClient var25 = this.mc.thePlayer.sendQueue;
        List var49 = var25.playerInfoList;
        var41 = var25.currentServerMaxPlayers;
        var16 = var41;

        for (var17 = 1; var16 > 20; var16 = (var41 + var17 - 1) / var17) {
            ++var17;
        }

        int var27 = 300 / var17;
        if (var27 > 150) {
            var27 = 150;
        }

        byte var29 = (byte) y;
        if ((Boolean) background.getValue()) {
            Gui.drawRect(x, var29, x + (float) (var27 * var17 + 1), (float) (var29 + 9 * var16) + 1, this.backgroundColor.getColorValue());
        }
        this.setDimensions((x + var27 * var17 + 1.01F), (var29 + 9 * var16) + 1);


        int var21;
        for (var21 = 0; var21 < var41; ++var21) {
            int var46 = x + var21 % var17 * var27 + 1;
            int var23 = var29 + var21 / var17 * 9 + 1;
            int pintColor = this.rowColor.getColorValue();
            if (var21 < var49.size()) {
                GuiPlayerInfo var51 = (GuiPlayerInfo) var49.get(var21);
                if (var51.responseTime < -1) {
                    pintColor = this.lowPingColor.getColorValue();
                } else if (var51.responseTime < 150) {
                    pintColor = this.lowPingColor.getColorValue();
                } else if (var51.responseTime < 300) {
                    pintColor = this.mediumPingColor.getColorValue();
                } else if (var51.responseTime < 600) {
                    pintColor = this.highPingColor.getColorValue();
                } else if (var51.responseTime < 1000) {
                    pintColor = this.extremelyHighPingColor.getColorValue();
                } else {
                    pintColor = this.rowColor.getColorValue();
                }

            }
            int alphaT = Math.min(this.rowColor.getColorValue() >> 24 & 255, pintColor >> 24 & 255);
            int redT = pintColor >> 16 & 255;
            int greenT = pintColor >> 8 & 255;
            int blueT = pintColor & 255;
            if ((Boolean) this.rowBackground.getValue() && !(var21 < var49.size())) {
                Gui.drawRect((float) var46, (float) var23, (float) (var46 + var27 - 1), (float) (var23 + 8), (Boolean) this.pingRowColor.getValue() ? new Color(redT, greenT, blueT, alphaT).getRGB() : this.rowColor.getColorValue());
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
            if (var21 < var49.size()) {
                GuiPlayerInfo var51 = (GuiPlayerInfo) var49.get(var21);
                ScorePlayerTeam var31 = this.mc.theWorld.getScoreboard().getPlayersTeam(var51.name);
                playerName = ScorePlayerTeam.formatPlayerName(var31, var51.name);

                ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
                if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                    if (!nickHider.customNameString.getStringValue().equals(Minecraft.getMinecraft().getSession().getUsername())) {
                        playerName = playerName.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                    } else {
                        playerName = playerName.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), "You");
                    }
                }

                if ((Boolean) this.rowBackground.getValue()) {
                    if ((playerName.contains(this.mc.getSession().getUsername()) || playerName.contains(CheatBreaker.getInstance().getModuleManager().nickHiderMod.customNameString.getStringValue())) && (Boolean) this.highlightSelfRow.getValue()) {
                        Gui.drawRect((float) var46, (float) var23, (float) (var46 + var27 - 1), (float) (var23 + 8), this.highlightedSelfRowColor.getColorValue());
                    } else if (profileHandler.validate(playerName, true) && (Boolean) this.highlightCBRows.getValue()) {
                        Gui.drawRect((float) var46, (float) var23, (float) (var46 + var27 - 1), (float) (var23 + 8), this.highlightedCBRowColor.getColorValue());
                    } else {
                        Gui.drawRect((float) var46, (float) var23, (float) (var46 + var27 - 1), (float) (var23 + 8), (Boolean) this.pingRowColor.getValue() ? new Color(redT, greenT, blueT, alphaT).getRGB() : this.rowColor.getColorValue());
                    }
                }

                boolean showIcon = (Boolean) this.showLogo.getValue() && profileHandler.validate(playerName, true);
                int toAdd = (showIcon ? playerName.contains(" ") ? 15 : 14 : 0);
                if (showIcon) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderUtil.renderIcon(new ResourceLocation("client/logo_white.png"), (float) var46 + 1.0F, (float) var23 + 1.0F, 12.0f, 6.0f);
                }

                if ((playerName.contains(this.mc.getSession().getUsername()) || playerName.contains(CheatBreaker.getInstance().getModuleManager().nickHiderMod.customNameString.getStringValue())) && (Boolean) this.highlightSelfNameInTab.getValue()) {
                    fontRenderer.drawString(stripColor(playerName), toAdd + (float) var46, (float) var23, this.highlightedSelfNameColor.getColorValue(), (Boolean) this.nameTextShadow.getValue());
                } else if (profileHandler.validate(playerName, true) && (Boolean) this.highlightCBPlayersNamesInTab.getValue()) {
                    fontRenderer.drawString(stripColor(playerName), toAdd + (float) var46, (float) var23, this.highlightedCBNameColor.getColorValue(), (Boolean) this.nameTextShadow.getValue());
                } else {
                    fontRenderer.drawString(playerName, toAdd + (float) var46, (float) var23, 16777215, (Boolean) this.nameTextShadow.getValue());
                }

                if (var48 != null) {
                    int var33 = var46 + fontRenderer.getStringWidth(playerName) + 5;
                    int var34 = var46 + var27 - 12 - 5;
                    if (var34 - var33 > 5) {
                        Score var54 = var48.getScoreboard().func_96529_a(var51.name, var48);
                        String var36 = EnumChatFormatting.YELLOW + "" + var54.getScorePoints();
                        fontRenderer.drawStringWithShadow(var36, (float) (var34 - fontRenderer.getStringWidth(var36)), (float) var23, 16777215);
                    }
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                byte var55;
                int pingColor;
                if (var51.responseTime < -1) {
                    var55 = 5;
                    pingColor = this.lowPingColor.getColorValue();
                } else if (var51.responseTime < 150) {
                    var55 = 0;
                    pingColor = this.lowPingColor.getColorValue();
                } else if (var51.responseTime < 300) {
                    var55 = 1;
                    pingColor = this.mediumPingColor.getColorValue();
                } else if (var51.responseTime < 600) {
                    var55 = 2;
                    pingColor = this.highPingColor.getColorValue();
                } else if (var51.responseTime < 1000) {
                    var55 = 3;
                    pingColor = this.extremelyHighPingColor.getColorValue();
                } else {
                    var55 = 4;
                    pingColor = -1;
                }
                if (!(Boolean) this.dynamicPingNumberColor.getValue()) {
                    pingColor = this.staticPingColor.getColorValue();
                }
                if (var51.responseTime != -1 && var51.responseTime != 0 && (Boolean) this.ping.getValue()) {
                    this.mc.getTextureManager().bindTexture(icons);
                    if (!(Boolean) this.pingAsNumber.getValue()) {
                        RenderUtil.drawTexturedModalRect(var46 + var27 - 12, var23, 0, 176 + var55 * 8, 10, 8);
                    } else {
                        fontRenderer.drawString(var51.responseTime + "", (float) var46 + var27 - fontRenderer.getStringWidth(var51.responseTime + ""), (float) var23, pingColor, (Boolean) this.pingTextShadow.getValue());
                    }
                }
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
    }

    private String stripColor(String input) {
        return input == null ? null : this.STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
