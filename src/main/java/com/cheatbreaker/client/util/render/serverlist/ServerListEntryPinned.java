package com.cheatbreaker.client.util.render.serverlist;

import com.cheatbreaker.client.ui.mainmenu.LunarNetworkLogoElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerListEntryPinned implements GuiListExtended.IGuiListEntry {
    private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/resource_packs.png");
    private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_pack.png");
    private static final Logger logger = LogManager.getLogger();
    private static final ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private final GuiMultiplayer guiMultiplayer;
    private final Minecraft mc;
    private final ServerData server;
    private long field_148298_f;
    private String lastIconB64;
    private DynamicTexture icon;
    private final ResourceLocation serverIcon;
    private final ResourceLocation starIcon = new ResourceLocation("client/icons/star-64.png");
    private final ResourceLocation cbIcon = new ResourceLocation("client/icons/cb.png");
    private LunarNetworkLogoElement lunarNetworkLogo;
    
    public ServerListEntryPinned(GuiMultiplayer p_i45048_1_, ServerData serverIn) {
        this.guiMultiplayer = p_i45048_1_;
        this.server = serverIn;
        this.mc = Minecraft.getMinecraft();
        this.serverIcon = new ResourceLocation("servers/" + serverIn.serverIP + "/icon");
        this.icon = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
        if (serverIn.serverIP.toLowerCase().endsWith("warzone.rip")) {
            this.lunarNetworkLogo = new LunarNetworkLogoElement();
        }
    }

    private void prepareServerIcon() {
        if (this.server.getBase64EncodedIconData() == null) {
            this.mc.getTextureManager().getTexture(this.serverIcon);
            this.icon = null;
        } else {
            BufferedImage bufferedImage;
            block8: {
                ByteBuf byteBuf = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), Charsets.UTF_8);
                ByteBuf byteBuf2 = Base64.decode(byteBuf);
                try {
                    bufferedImage = ImageIO.read(new ByteBufInputStream(byteBuf2));
                    Validate.validState(bufferedImage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(bufferedImage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break block8;
                } catch (Exception exception) {
                    logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", (Throwable)exception);
                    this.server.setBase64EncodedIconData((String)null);
                }
                finally {
                    byteBuf.release();
                    byteBuf2.release();
                }
                return;
            }
            if (this.icon == null) {
                this.icon = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
                this.mc.getTextureManager().loadTexture(this.serverIcon, this.icon);
            }
            bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.icon.getTextureData(), 0, bufferedImage.getWidth());
            this.icon.updateDynamicTexture();
        }
    }
    
    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {

        if (!this.server.field_78841_f) {
            this.server.field_78841_f = true;
            this.server.pingToServer = -2L;
            this.server.serverMOTD = "";
            this.server.populationInfo = "";
            executor.submit(() -> {
                try {
                    ServerListEntryPinned.this.guiMultiplayer.getOldServerPinger().ping(ServerListEntryPinned.this.server);
                } catch (UnknownHostException var2) {
                    ServerListEntryPinned.this.server.pingToServer = -1L;
                    ServerListEntryPinned.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
                } catch (Exception var3) {
                    ServerListEntryPinned.this.server.pingToServer = -1L;
                    ServerListEntryPinned.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
                }
            });
        }

        GL11.glColor4f(1.0f, 0.40909088f * 2.2f, 0.0f, 1.0f);
        RenderUtil.renderIcon(this.starIcon, (float)5, (float)(x - 17), (float)(y + (this.server.isCheatBreakerServer() ? 4 : 12)));
        GL11.glColor4f(11.5f * 0.073913045f, 0.2857143f * 2.975f, 3.0f * 0.28333333f, 1.0f);

        if (this.server.isCheatBreakerServer()) {
            float f = 16;
            float f2 = 8;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = x - 20;
            float f6 = y + 20;
            GL11.glEnable(3042);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.cbIcon);
            GL11.glBegin(7);
            GL11.glTexCoord2d(f3 / (float)5, f4 / (float)5);
            GL11.glVertex2d(f5, f6);
            GL11.glTexCoord2d(f3 / (float)5, (f4 + (float)5) / (float)5);
            GL11.glVertex2d(f5, f6 + f2);
            GL11.glTexCoord2d((f3 + (float)5) / (float)5, (f4 + (float)5) / (float)5);
            GL11.glVertex2d(f5 + f, f6 + f2);
            GL11.glTexCoord2d((f3 + (float)5) / (float)5, f4 / (float)5);
            GL11.glVertex2d(f5 + f, f6);
            GL11.glEnd();
            GL11.glDisable(3042);
        }

        boolean flag = this.server.version > 47;
        boolean flag1 = this.server.version < 47;
        boolean flag2 = flag || flag1;
        this.mc.fontRendererObj.drawString(this.server.serverName, x + 32 + 3, y + 1, 16777215);
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, listWidth - 32 - 2);

        for (int i = 0; i < Math.min(list.size(), 2); ++i) {
            this.mc.fontRendererObj.drawString(list.get(i), x + 32 + 3, y + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
        }

        String s2 = flag2 ? EnumChatFormatting.DARK_RED + this.server.gameVersion : this.server.populationInfo;
        int j = this.mc.fontRendererObj.getStringWidth(s2);
        this.mc.fontRendererObj.drawString(s2, x + listWidth - j - 15 - 2, y + 1, 8421504);
        int k = 0;
        String s = null;
        int l;
        String s1;

        if (flag2) {
            l = 5;
            s1 = flag ? "Client out of date!" : "Server out of date!";
            s = this.server.playerList;
        } else if (this.server.field_78841_f && this.server.pingToServer != -2L) {
            if (this.server.pingToServer < 0L) {
                l = 5;
            } else if (this.server.pingToServer < 150L) {
                l = 0;
            } else if (this.server.pingToServer < 300L) {
                l = 1;
            } else if (this.server.pingToServer < 600L) {
                l = 2;
            } else if (this.server.pingToServer < 1000L) {
                l = 3;
            } else {
                l = 4;
            }

            if (this.server.pingToServer < 0L) {
                s1 = "(no connection)";
            } else {
                s1 = this.server.pingToServer + "ms";
                s = this.server.playerList;
            }
        } else {
            k = 1;
            l = (int) (Minecraft.getSystemTime() / 100L + (long) (slotIndex * 2) & 7L);

            if (l > 4) {
                l = 8 - l;
            }

            s1 = "Pinging...";
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (float) (k * 10), (float) (176 + l * 8), 10, 8, 256.0F, 256.0F);

        if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.lastIconB64)) {
            this.lastIconB64 = this.server.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.guiMultiplayer.getServerList().saveServerList();
        }

        if (this.lunarNetworkLogo != null) {
            this.lunarNetworkLogo.setElementSize(x, y, 32.0f, 29.5f);
            this.lunarNetworkLogo.drawElementHover(0.0f, 0.0f, true);
        } else {
            if (this.icon != null) {
                this.drawTextureAt(x, y, this.serverIcon);
            } else {
                this.drawTextureAt(x, y, UNKNOWN_SERVER);
            }
        }

        int i1 = mouseX - x;
        int j1 = mouseY - y;

        if (i1 >= listWidth - 15 && i1 <= listWidth - 5 && j1 >= 0 && j1 <= 8) {
            this.guiMultiplayer.setHoveringText(s1);
        } else if (i1 >= listWidth - j - 15 - 2 && i1 <= listWidth - 15 - 2 && j1 >= 0 && j1 <= 8) {
            this.guiMultiplayer.setHoveringText(s);
        }

        if (this.mc.gameSettings.touchscreen || isSelected) {
            this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int k1 = mouseX - x;

            if (this.func_178013_b()) {
                if (k1 < 32 && k1 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                } else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }
        }
    }

    protected void drawTextureAt(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
        this.mc.getTextureManager().bindTexture(p_178012_3_);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
    }

    private boolean func_178013_b() {
        return true;
    }

    @Override
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        this.guiMultiplayer.selectServer(slotIndex);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
            this.guiMultiplayer.connectToSelected();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        if (p_148278_5_ <= 32) {
            if (p_148278_5_ < 32) {
                this.guiMultiplayer.connectToSelected();
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }

    public ServerData getServer() {
        return this.server;
    }

    static ServerData setServer(ServerListEntryPinned cBServerListEntryNormal) {
        return cBServerListEntryNormal.server;
    }

    static GuiMultiplayer getGuiMultiplayer(ServerListEntryPinned cBServerListEntryNormal) {
        return cBServerListEntryNormal.guiMultiplayer;
    }
}
