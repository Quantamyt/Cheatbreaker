package com.cheatbreaker.client.ui.overlay;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.websocket.shared.WSPacketConsoleMessage;
import com.cheatbreaker.client.ui.element.DraggableElement;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.element.type.InputFieldElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.MessageUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleElement extends DraggableElement {

    private final List<String> sentCommands = new ArrayList<>();
    private int commandIndex = 0;

    private final InputFieldElement textInputBar;
    private final ScrollableElement scrollBar;
    private final FlatButtonElement sendButton;
    private final FlatButtonElement closeButton;

    public ConsoleElement() {
        this.textInputBar = new InputFieldElement(CheatBreaker.getInstance().playRegular14px, "", 0x2FFFFFFF, 0x6FFFFFFF);
        this.textInputBar.setMaxStringLength(256);
        this.scrollBar = new ScrollableElement(this);
        this.sendButton = new FlatButtonElement("SEND");
        this.closeButton = new FlatButtonElement("X");
    }

    @Override
    public void setElementSize(float x, float y, float width, float height) {
        super.setElementSize(x, y, width, height);
        this.textInputBar.setElementSize(x + 2.0f, y + height - 15.0f, width - 40.0f, 13.0f);
        this.scrollBar.setElementSize(x + width - 6.0f, y + 12.0f + 3.0f, 4.0f, height - 32.0f);
        this.sendButton.setElementSize(x + width - 37.0f, y + height - 15.0f, 35.0f, 13.0f);
        this.closeButton.setElementSize(x + width - 12.0f, y + 2.0f, 10.0f, 10.0f);
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        this.onDrag(f, f2);
        Gui.drawBoxWithOutLine(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0.5f, -16777216, -15395563);
        GL11.glPushMatrix();
        Gui.drawRect(this.xPosition, this.yPosition - 0.5f, this.xPosition + this.width, this.yPosition, -1357572843);
        Gui.drawRect(this.xPosition, this.yPosition + this.height, this.xPosition + this.width, this.yPosition + this.height + 0.5f, -1357572843);

        CheatBreaker.getInstance().playRegular14px.drawString("Console", this.xPosition + (float)4, this.yPosition + (float)3, -1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawRect(this.xPosition + 2.0f, this.yPosition + (float)12 + (float)3, this.xPosition + this.width - 2.0f, this.yPosition + this.height - (float)17, -1356783327);
        this.scrollBar.onScroll(f, f2, bl);
        try {
            if (CheatBreaker.getInstance().isConsoleAccess()) {
                GL11.glPushMatrix();
                GL11.glEnable(3089);
                OverlayGui overlayGui = OverlayGui.getInstance();
                RenderUtil.startScissorBox((int)(this.xPosition + 2.0f), (int)(this.yPosition + (float)12 + (float)3), (int)(this.xPosition + this.width - 2.0f), (int)(this.yPosition + this.height - (float)17), (float)((int)((float)overlayGui.getScaledResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int)overlayGui.getScaledHeight());
                List<String> list = CheatBreaker.getInstance().getConsoleLines();
                int n = 0;
                for (int i = list.size() - 1; i >= 0; --i) {
                    String string = list.get(i);
                    String[] arrstring = CheatBreaker.getInstance().playRegular14px.formatText(string, this.width - 10.0F).split("\n");
                    n += arrstring.length * 10;
                    int n2 = 0;
                    for (String string2 : arrstring) {
                        CheatBreaker.getInstance().playRegular14px.drawString(string2, this.xPosition + (float)6, this.yPosition + this.height - (float)19 - (float)n + (float)(n2 * 10), -1);
                        ++n2;
                    }
                }
                this.scrollBar.setScrollAmount(n + 4);
                GL11.glDisable(3089);
                GL11.glPopMatrix();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.scrollBar.drawElementHover(f, f2, bl);
        GL11.glPopMatrix();
        this.textInputBar.drawElementHover(f, f2, bl);
        this.sendButton.drawElementHover(f, f2, bl);
        this.closeButton.drawElementHover(f, f2, bl);
    }

    @Override
    public void handleElementUpdate() {
        this.textInputBar.handleElementUpdate();
        this.sendButton.handleElementUpdate();
        this.scrollBar.handleElementUpdate();
        this.closeButton.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.textInputBar.handleElementClose();
        this.sendButton.handleElementClose();
        this.scrollBar.handleElementClose();
        this.closeButton.handleElementClose();
    }

    @Override
    public void keyTyped(char c, int n) {
        if (this.textInputBar.isFocused() && !this.textInputBar.getText().equals("") && n == 28) {
            this.handleElementKeyTyped();
        }

        try {
            if (this.textInputBar.isFocused() && n == 200 && this.sentCommands.size() != 0) {
                if (this.commandIndex != 0) this.commandIndex--;
                this.textInputBar.setText(this.sentCommands.get(this.commandIndex));
            } else if (this.textInputBar.isFocused() && n == 208 && this.sentCommands.size() != 0) {
                if (this.commandIndex != this.sentCommands.size() - 1) this.commandIndex++;
                this.textInputBar.setText(this.sentCommands.get(this.commandIndex));
            }
        } catch (IndexOutOfBoundsException ignored) {
            /* Set it to the first one... */
            this.commandIndex = 0;
        }


        this.textInputBar.keyTyped(c, n);
        this.sendButton.keyTyped(c, n);
        this.scrollBar.keyTyped(c, n);
        this.closeButton.keyTyped(c, n);
    }

    @Override
    public boolean onMouseClick(float f, float f2, int n) {
        if (!this.textInputBar.isMouseInside(f, f2) && this.textInputBar.isFocused()) {
            this.textInputBar.setFocused(false);
        }
        return false;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        this.textInputBar.handleElementMouseClicked(f, f2, n, bl);
        this.scrollBar.handleElementMouseClicked(f, f2, n, bl);
        if (!bl) {
            return false;
        }

        if (!this.textInputBar.getText().equals("") && this.sendButton.isMouseInside(f, f2)) {
            this.handleElementKeyTyped();
        }
        this.sendButton.handleElementMouseClicked(f, f2, n, bl);
        if (this.isMouseInside(f, f2) && f2 < this.yPosition + (float)12) {
            this.setPosition(f, f2);
        }
        if (this.closeButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().removeElement(this);
            return true;
        }
        return false;
    }

    @Override
    public void handleElementMouse() {
        this.scrollBar.handleElementMouse();
    }

    private void handleElementKeyTyped() {
        String consoleText = this.textInputBar.getText();
        if (consoleText.equals("ban")) {
            MessageUtils.sendBan("Test", 1);
        } else if (consoleText.equals("clear") || consoleText.equals("cls")) {
            CheatBreaker.getInstance().getConsoleLines().clear();
            CheatBreaker.getInstance().getGlobalSettings().SHOW_MODIFIERS = false;
        } else if (consoleText.equalsIgnoreCase("wsReconnect") && CheatBreaker.getInstance().getWSNetHandler().isClosed()) {
            try {
                CheatBreaker.getInstance().connectToAssetServer();
            } catch (URISyntaxException e) {
                CheatBreaker.getInstance().getConsoleLines().add("Invalid URL: " + e.getInput());
                e.printStackTrace();
            }
        } else {
            CheatBreaker.getInstance().getConsoleLines().add(EnumChatFormatting.GRAY + "> " + consoleText);
            CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketConsoleMessage(consoleText));
            this.sentCommands.add(consoleText);
            this.commandIndex = this.sentCommands.size();
        }
        this.textInputBar.setText("");
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
    }

    @Override
    public boolean onMouseMoved(float f, float f2, int n, boolean bl) {
        this.textInputBar.onMouseMoved(f, f2, n, bl);
        this.sendButton.onMouseMoved(f, f2, n, bl);
        this.scrollBar.onMouseMoved(f, f2, n, bl);
        this.closeButton.onMouseMoved(f, f2, n, bl);
        return false;
    }
}