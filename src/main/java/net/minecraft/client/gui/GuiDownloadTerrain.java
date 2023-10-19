package net.minecraft.client.gui;

import com.cheatbreaker.client.ui.mainmenu.menus.VanillaMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {
    private final NetHandlerPlayClient field_146594_a;
    private int field_146593_f;
    private long lastMouseEvent;


    public GuiDownloadTerrain(NetHandlerPlayClient p_i45023_1_) {
        this.field_146594_a = p_i45023_1_;
        this.lastMouseEvent = System.currentTimeMillis();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {}

    protected void actionPerformed(GuiButton guiButton) {
        if (this.mc.currentServerData != null && this.mc.theWorld != null) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.mc.playerServerData));
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen(new VanillaMenu());
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui() {
        this.buttonList.clear();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.field_146593_f;

        if (this.field_146594_a == null) {
            return;
        }
        if (System.currentTimeMillis() - this.lastMouseEvent >= 5000L && this.buttonList.isEmpty()) {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 36, 99, 20, "Reconnect"));
            this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 4 + 120 + 36, 99, 20, "Disconnect"));
        }
        if (this.field_146593_f % 20 == 0) {
            this.field_146594_a.addToSendQueue(new C00PacketKeepAlive());
        }

        if (this.field_146594_a != null) {
            this.field_146594_a.onNetworkTick();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.func_146278_c(0);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }
}
