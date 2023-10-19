package net.minecraft.client.gui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.google.common.base.Throwables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiScreen extends Gui {
    protected static RenderItem itemRender = new RenderItem();
    public Minecraft mc;
    public int width;
    public int height;
    protected List<GuiButton> buttonList = new ArrayList();
    protected List labelList = new ArrayList();
    public boolean field_146291_p;
    protected FontRenderer fontRendererObj;
    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int field_146298_h;
    protected static ColorFade blurColor = new ColorFade(0, -553648128);
    protected static ColorFade blurColorOffSet = new ColorFade(0, 1243487774);

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        int n3;
        for (n3 = 0; n3 < this.buttonList.size(); ++n3) {
            this.buttonList.get(n3).drawButton(this.mc, mouseX, mouseY);
        }
        for (n3 = 0; n3 < this.labelList.size(); ++n3) {
            ((GuiLabel)this.labelList.get(n3)).func_146159_a(this.mc, mouseX, mouseY);
        }
    }

    protected void keyTyped(char c, int n) {
        if (Keyboard.isKeyDown(42) && n == 15) {
            this.mc.displayGuiScreen(OverlayGui.createInstance(this.mc.currentScreen));
        }
        if (n == 1) {
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().guiBlur.getValue()) {
                this.mc.entityRenderer.stopUseShader();
            }
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    public static String getClipboardString() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception exception) {

        }
        return "";
    }

    public static void setClipboardString(String string) {
        try {
            StringSelection stringSelection = new StringSelection(string);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        } catch (Exception exception) {

        }
    }

    protected void func_146285_a(ItemStack itemStack, int n, int n2) {
        List list = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, itemStack.getRarity().rarityColor + (String)list.get(i));
                continue;
            }
            list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
        }
        this.func_146283_a(list, n, n2);
    }

    protected void func_146279_a(String string, int n, int n2) {
        this.func_146283_a(Arrays.asList(string), n, n2);
    }

    protected void func_146283_a(List list, int n, int n2) {
        if (!list.isEmpty()) {
            int n3;
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int n4 = 0;
            for (Object string : list) {
                n3 = this.fontRendererObj.getStringWidth((String) string);
                if (n3 <= n4) continue;
                n4 = n3;
            }
            int n5 = n + 12;
            n3 = n2 - 12;
            int n6 = 8;
            if (list.size() > 1) {
                n6 += 2 + (list.size() - 1) * 10;
            }
            if (n5 + n4 > this.width) {
                n5 -= 28 + n4;
            }
            if (n3 + n6 + 6 > this.height) {
                n3 = this.height - n6 - 6;
            }
            zLevel = 300;
            GuiScreen.itemRender.zLevel = 300;
            int n7 = -267386864;
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 - 4), (float)(n5 + n4 + 3), (float)(n3 - 3), n7, n7);
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 + n6 + 3), (float)(n5 + n4 + 3), (float)(n3 + n6 + 4), n7, n7);
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 - 3), (float)(n5 + n4 + 3), (float)(n3 + n6 + 3), n7, n7);
            GuiScreen.drawGradientRect((float)(n5 - 4), (float)(n3 - 3), (float)(n5 - 3), (float)(n3 + n6 + 3), n7, n7);
            GuiScreen.drawGradientRect((float)(n5 + n4 + 3), (float)(n3 - 3), (float)(n5 + n4 + 4), (float)(n3 + n6 + 3), n7, n7);
            int n8 = 0x505000FF;
            int n9 = (n8 & 0xFEFEFE) >> 1 | n8 & 0xFF000000;
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 - 3 + 1), (float)(n5 - 3 + 1), (float)(n3 + n6 + 3 - 1), n8, n9);
            GuiScreen.drawGradientRect((float)(n5 + n4 + 2), (float)(n3 - 3 + 1), (float)(n5 + n4 + 3), (float)(n3 + n6 + 3 - 1), n8, n9);
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 - 3), (float)(n5 + n4 + 3), (float)(n3 - 3 + 1), n8, n8);
            GuiScreen.drawGradientRect((float)(n5 - 3), (float)(n3 + n6 + 2), (float)(n5 + n4 + 3), (float)(n3 + n6 + 3), n9, n9);
            for (int i = 0; i < list.size(); ++i) {
                String string = (String)list.get(i);
                this.fontRendererObj.drawStringWithShadow(string, (float)n5, (float)n3, -1);
                if (i == 0) {
                    n3 += 2;
                }
                n3 += 10;
            }
            zLevel = 0.0f;
            GuiScreen.itemRender.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guiButton = this.buttonList.get(i);
                if (!guiButton.mousePressed(this.mc, mouseX, mouseY)) continue;
                this.selectedButton = guiButton;
                guiButton.func_146113_a(this.mc.getSoundHandler());
                this.actionPerformed(guiButton);
            }
        }
    }

    protected void mouseMovedOrUp(int n, int n2, int n3) {
        if (this.selectedButton != null && n3 == 0) {
            this.selectedButton.mouseReleased(n, n2);
            this.selectedButton = null;
        }
    }

    protected void mouseClickMove(int n, int n2, int n3, long l) {
    }

    protected void actionPerformed(GuiButton guiButton) {
    }

    public void setWorldAndResolution(Minecraft minecraft, int n, int n2) {
        this.mc = minecraft;
        this.fontRendererObj = minecraft.fontRenderer;
        this.width = n;
        this.height = n2;
        this.buttonList.clear();
        this.initGui();
        this.blurGui();
    }

    public void initGui() {
    }

    public void blurGui() {
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().guiBlur.getValue() && this.mc.theWorld != null && this.mc.thePlayer != null) {
            this.mc.entityRenderer.loadGuiBlurShader();
        }
        if (this.mc.lastScreen == null) {
            blurColor.startAnimation();
            blurColorOffSet.startAnimation();
        }
    }

    public void handleInput() {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }

    public void handleMouseInput() {
        int n = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int n2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int n3 = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.field_146298_h++ > 0) {
                return;
            }
            this.eventButton = n3;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(n, n2, this.eventButton);
        } else if (n3 != -1) {
            if (this.mc.gameSettings.touchscreen && --this.field_146298_h > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseMovedOrUp(n, n2, n3);
        } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(n, n2, this.eventButton, l);
        }
    }

    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }

        this.mc.func_152348_aa();
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public void renderBlur() {
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().guiBlur.getValue()) {
            try {
                if (this.mc.entityRenderer.isShaderActive()) {
                    ShaderGroup shaderGroup = Minecraft.getMinecraft().entityRenderer.getShaderGroup();
                    for (Shader shader : shaderGroup.getShaders()) {
                        ShaderUniform shaderUniform = shader.getShaderManager().func_147991_a("Progress");
                        if (shaderUniform == null) continue;
                        shaderUniform.func_148090_a(blurColor.getFadeAmount());
                    }
                }
                GL11.glEnable(2929);
            } catch (IllegalArgumentException illegalArgumentException) {
                Throwables.propagate(illegalArgumentException);
            }
        }
    }

    public void drawDefaultBackground() {
        if (this.mc.theWorld != null) {
            this.renderBlur();
            if (CheatBreaker.getInstance().getGlobalSettings().containerBackground.getStringValue().equals("CheatBreaker")) {
                GuiScreen.drawGradientRect(0.0f, 0.0f, (float)this.width, (float)this.height, blurColor.getColor(true).getRGB(), blurColorOffSet.getColor(true).getRGB());
            } else if (CheatBreaker.getInstance().getGlobalSettings().containerBackground.getStringValue().equals("Vanilla")) {
                this.func_146270_b(0);
            }
            if (this.mc.isFullScreen() && CheatBreaker.getInstance().getGlobalSettings().isDebug) {
                String debug = Config.MC_VERSION + " (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch() + ")";
                CheatBreaker.getInstance().playRegular18px.drawStringWithShadow(debug, 5.0, this.height - 14.0f, -1879048193);
            }
        } else {
            this.func_146270_b(0);
        }

    }

    public void renderBlur(float f, float f2) {
        ShaderGroup shaderGroup = Minecraft.getMinecraft().entityRenderer.getShaderGroup();
        try {
            if (this.mc.entityRenderer.isShaderActive()) {
                for (Shader shader : shaderGroup.getShaders()) {
                    ShaderUniform shaderUniform = shader.getShaderManager().func_147991_a("Progress");
                    if (shaderUniform == null) continue;
                    shaderUniform.func_148090_a(0.75555557f * 0.6617647f);
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            Throwables.propagate(illegalArgumentException);
        }
        GuiScreen.drawGradientRect(0.0f, 0.0f, f, f2,
                blurColor.getColor(true).getRGB(),
                blurColorOffSet.getColor(true).getRGB());
    }

    public void func_146270_b(int n) {
        if (this.mc.theWorld != null) {
            GuiScreen.drawGradientRect(0.0f, 0.0f, (float)this.width, (float)this.height, -1072689136, -804253680);
        } else {
            this.func_146278_c(n);
        }
    }

    public void func_146278_c(int n) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        Tessellator tessellator = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(optionsBackground);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0x404040);
        tessellator.addVertexWithUV(0.0, this.height, 0.0, 0.0, (float)this.height / f + (float)n);
        tessellator.addVertexWithUV(this.width, this.height, 0.0, (float)this.width / f, (float)this.height / f + (float)n);
        tessellator.addVertexWithUV(this.width, 0.0, 0.0, (float)this.width / f, n);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, 0.0, n);
        tessellator.draw();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void confirmClicked(boolean bl, int n) {
    }

    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }

    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
}
