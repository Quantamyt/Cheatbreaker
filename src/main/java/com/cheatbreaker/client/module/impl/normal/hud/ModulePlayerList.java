package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.profile.ClientProfile;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.render.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import org.apache.commons.lang3.ObjectUtils;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Module - ModulePlayerList
 * @see AbstractModule
 *
 * This module allows you to edit the default Minecraft player list.
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
        this.setDescription("Customize the player list tab.");
        this.setAliases("Tab");
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
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
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        render(0, scoreboard, scoreobjective1, 0, 0);
        GL11.glPopMatrix();
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            ScoreObjective var48 = this.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
            GL11.glPushMatrix();
            this.scaleAndTranslate(event.getScaledResolution());
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaledWidth();
            Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
            ScoreObjective scoreobjective1;
            scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
            if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || var48 != null)) {
                render(0, scoreboard, scoreobjective1, 0, 0);
            }
            GL11.glPopMatrix();
        }
    }

    public void render(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn, int x, int y) {
        NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;

        for (NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.fontRendererObj.getStringWidth(this.mc.ingameGUI.overlayPlayerList.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);

            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, k);
            }
        }

        list = list.subList(0, Math.min(list.size(), 80));
        int l3 = list.size();
        int i4 = l3;
        int j4;

        for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
            ++j4;
        }

        boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int l;

        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                l = 90;
            } else {
                l = j;
            }
        } else {
            l = 0;
        }

        int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
        int j1 = 0/*width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2*/;
        int k1 = 10;
        int l1 = i1 * j4 + (j4 - 1) * 5;
        List<String> list1 = null;
        List<String> list2 = null;

        if (this.mc.ingameGUI.overlayPlayerList.header != null) {
            list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.mc.ingameGUI.overlayPlayerList.header.getFormattedText(), width - 50);

            for (String s : list1) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
            }
        }

        if (this.mc.ingameGUI.overlayPlayerList.footer != null) {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.mc.ingameGUI.overlayPlayerList.footer.getFormattedText(), width - 50);

            for (String s2 : list2) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s2));
            }
        }

        if (list1 != null) {
            Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for (String s3 : list1) {
                int i2 = this.mc.fontRendererObj.getStringWidth(s3);
                this.mc.fontRendererObj.drawStringWithShadow(s3, (float) (width / 2 - i2 / 2), (float) k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }

            ++k1;
        }

        Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, Integer.MIN_VALUE);

        for (int k4 = 0; k4 < l3; ++k4) {
            int l4 = k4 / i4;
            int i5 = k4 % i4;
            int j2 = 0/*j1 + l4 * i1 + l4 * 5*/;
            int k2 = k1 + i5 * 9;
            Gui.drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            if (k4 < list.size()) {
                NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
                String s1 = this.mc.ingameGUI.overlayPlayerList.getPlayerName(networkplayerinfo1);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();

                if (flag) {
                    EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    int l2 = 8 + (flag1 ? 8 : 0);
                    int i3 = 8 * (flag1 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, (float) l2, 8, i3, 8, 8, 64.0F, 64.0F);

                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        int j3 = 8 + (flag1 ? 8 : 0);
                        int k3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, (float) j3, 8, k3, 8, 8, 64.0F, 64.0F);
                    }

                    j2 += 9;
                }

                if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    s1 = EnumChatFormatting.ITALIC + s1;
                    this.mc.fontRendererObj.drawStringWithShadow(s1, (float) j2, (float) k2, -1862270977);
                } else {
                    this.mc.fontRendererObj.drawStringWithShadow(s1, (float) j2, (float) k2, -1);
                }

                if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    int k5 = j2 + i + 1;
                    int l5 = k5 + l;

                    if (l5 - k5 > 5) {
                        this.mc.ingameGUI.overlayPlayerList.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                    }
                }

                this.mc.ingameGUI.overlayPlayerList.drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
            }
        }

        if (list2 != null) {
            k1 = k1 + i4 * 9 + 1;
            Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for (String s4 : list2) {
                int j5 = this.mc.fontRendererObj.getStringWidth(s4);
                this.mc.fontRendererObj.drawStringWithShadow(s4, (float) (width / 2 - j5 / 2), (float) k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
        this.setDimensions(10, 10);
    }

    private boolean isInPlayerList(String username) {
        for (ClientProfile profile : CheatBreaker.getInstance().getProfileHandler().getWsOnlineUsers().values()) {
            if (profile.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private String stripColor(String input) {
        return input == null ? null : this.STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
