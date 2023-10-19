package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

public class InputFieldElement extends AbstractElement {
    private final CBFontRenderer font;
    private String text = "";
    private int maxStringLength = 32;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    private boolean canLoseFocus = true;
    private boolean isFocused;
    private boolean isEnabled = true;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor = 0xE0E0E0;
    private int disabledColor = 0x707070;
    private boolean visible = true;
    private final int color1;
    private final int color2;
    private final String label;

    public InputFieldElement(CBFontRenderer font, String label, int color1, int color2) {
        this.font = font;
        this.label = label;
        this.color1 = color1;
        this.color2 = color2;
    }

    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    public void setText(String string) {
        this.text = string.length() > this.maxStringLength ? string.substring(0, this.maxStringLength) : string;
        this.setCursorPositionEnd();
    }

    public String getText() {
        return this.text;
    }

    public String getSelectedText() {
        int n = Math.min(this.cursorPosition, this.selectionEnd);
        int n2 = Math.max(this.cursorPosition, this.selectionEnd);
        return this.text.substring(n, n2);
    }

    public void writeText(String string) {
        int n;
        String string2 = "";
        String string3 = ChatAllowedCharacters.filterAllowedCharacters(string);
        int n2 = Math.min(this.cursorPosition, this.selectionEnd);
        int n3 = Math.max(this.cursorPosition, this.selectionEnd);
        int n4 = this.maxStringLength - this.text.length() - (n2 - this.selectionEnd);
        boolean bl = false;
        if (this.text.length() > 0) {
            string2 = string2 + this.text.substring(0, n2);
        }
        if (n4 < string3.length()) {
            string2 = string2 + string3.substring(0, n4);
            n = n4;
        } else {
            string2 = string2 + string3;
            n = string3.length();
        }
        if (this.text.length() > 0 && n3 < this.text.length()) {
            string2 = string2 + this.text.substring(n3);
        }
        this.text = string2;
        this.moveCursorBy(n2 - this.selectionEnd + n);
    }

    public void deleteWords(int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean bl = n < 0;
                int n2 = bl ? this.cursorPosition + n : this.cursorPosition;
                int n3 = bl ? this.cursorPosition : this.cursorPosition + n;
                String string = "";
                if (n2 >= 0) {
                    string = this.text.substring(0, n2);
                }
                if (n3 < this.text.length()) {
                    string = string + this.text.substring(n3);
                }
                this.text = string;
                if (bl) {
                    this.moveCursorBy(n);
                }
            }
        }
    }

    public int getNthWordFromCursor(int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }

    public int getNthWordFromPos(int n, int n2) {
        return this.func_146197_a(n, this.getCursorPosition(), true);
    }

    public int func_146197_a(int n, int n2, boolean bl) {
        int n3 = n2;
        boolean bl2 = n < 0;
        int n4 = Math.abs(n);
        for (int i = 0; i < n4; ++i) {
            if (bl2) {
                while (bl && n3 > 0 && this.text.charAt(n3 - 1) == ' ') {
                    --n3;
                }
                while (n3 > 0 && this.text.charAt(n3 - 1) != ' ') {
                    --n3;
                }
                continue;
            }
            int n5 = this.text.length();
            if ((n3 = this.text.indexOf(32, n3)) == -1) {
                n3 = n5;
                continue;
            }
            while (bl && n3 < n5 && this.text.charAt(n3) == ' ') {
                ++n3;
            }
        }
        return n3;
    }

    public void moveCursorBy(int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }

    public void setCursorPosition(int n) {
        this.cursorPosition = n;
        int n2 = this.text.length();
        if (this.cursorPosition < 0) {
            this.cursorPosition = 0;
        }
        if (this.cursorPosition > n2) {
            this.cursorPosition = n2;
        }
        this.setSelectionPos(this.cursorPosition);
    }

    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    public boolean textboxKeyTyped(char c, int n) {
        if (!this.isFocused) {
            return false;
        }
        switch (c) {
            case '\u0001':
                this.setCursorPositionEnd();
                this.setSelectionPos(0);
                return true;
            case '\u0003':
                GuiScreen.setClipboardString(this.getSelectedText());
                return true;
            case '\u0016':
                if (this.isEnabled) {
                    this.writeText(GuiScreen.getClipboardString());
                }
                return true;
            case '\u0018':
                GuiScreen.setClipboardString(this.getSelectedText());
                if (this.isEnabled) {
                    this.writeText("");
                }
                return true;
        }
        switch (n) {
            case 14:
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(-1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            case 199:
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(0);
                } else {
                    this.setCursorPositionZero();
                }
                return true;
            case 203:
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                } else {
                    this.moveCursorBy(-1);
                }
                return true;
            case 205:
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                } else {
                    this.moveCursorBy(1);
                }
                return true;
            case 207:
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(this.text.length());
                } else {
                    this.setCursorPositionEnd();
                }
                return true;
            case 211:
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c)) {
            if (this.isEnabled) {
                this.writeText(Character.toString(c));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        boolean bl2;
        if (!bl) {
            this.setFocused(false);
            return true;
        }
        if (n == 1 && this.isMouseInside(f, f2)) {
            this.setText("");
        }
        boolean bl3 = bl2 = f >= this.xPosition && f < this.xPosition + this.width && f2 >= this.yPosition && f2 < this.yPosition + this.height;

        if (this.canLoseFocus) {
            this.setFocused(bl2);
        }
        if (this.isFocused && n == 0) {
            float f3 = f - this.xPosition;
            if (this.enableBackgroundDrawing) {
                f3 -= (float) 4;
            }
            String string = this.font.setWrapWords(this.text.substring(this.lineScrollOffset), this.IIIIIIlIlIlIllllllIlllIlI());
            this.setCursorPosition(this.font.setWrapWords(string, f3).length() + this.lineScrollOffset);
        }
        return false;
    }

    public void drawElement() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color1);
            }
            int n = this.isEnabled ? this.enabledColor : this.disabledColor;
            int n2 = this.cursorPosition - this.lineScrollOffset;
            int n3 = this.selectionEnd - this.lineScrollOffset;
            String string = this.font.setWrapWords(this.text.substring(this.lineScrollOffset), this.IIIIIIlIlIlIllllllIlllIlI());
            boolean bl = n2 >= 0 && n2 <= string.length();
            boolean bl2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && bl;
            float f = this.enableBackgroundDrawing ? this.xPosition + (float) 4 : this.xPosition;
            float f2 = this.enableBackgroundDrawing ? this.yPosition + (this.height - (float) 8) / 2.0f : this.yPosition;
            float f3 = f;
            if (n3 > string.length()) {
                n3 = string.length();
            }
            if (string.length() > 0) {
                String string2 = bl ? string.substring(0, n2) : string;
                f3 = this.font.drawString(string2, f, f2, n);
            } else if (!this.isFocused()) {
                this.font.drawString(this.label, f, f2, n);
            }
            boolean bl3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            float f4 = f3;
            if (!bl) {
                f4 = n2 > 0 ? f + this.width : f;
            } else if (bl3) {
                f4 = f3 - 1.0f;
                f3 -= 1.0f;
            }
            if (string.length() > 0 && bl && n2 < string.length()) {
                this.font.drawString(string.substring(n2), f3, f2, n);
            }
            if (bl2) {
                if (bl3) {
                    Gui.drawRect(f4, f2 - 1.0f, f4 + 1.0f, f2 + 1.0f + (float) this.font.getHeight(), -3092272);
                } else {
                    this.font.drawString("_", f4, f2, n);
                }
            }
            if (n3 != n2) {
                float f5 = f + (float) this.font.getStringWidth(string.substring(0, n3));
                this.drawCursorVertical(f4, f2 - 1.0f + 2.0f, f5 - 1.0f, f2 + 1.0f + (float) this.font.getHeight() + 2.0f);
            }
        }
    }

    private void drawCursorVertical(float f, float f2, float f3, float f4) {
        float f5;
        if (f < f3) {
            f5 = f;
            f = f3;
            f3 = f5;
        }
        if (f2 < f4) {
            f5 = f2;
            f2 = f4;
            f4 = f5;
        }
        if (f3 > this.xPosition + this.width) {
            f3 = this.xPosition + this.width;
        }
        if (f > this.xPosition + this.width) {
            f = this.xPosition + this.width;
        }
//        Tessellator tessellator = Tessellator.getInstance();
        GL11.glColor4f(0.0f, 0.0f, 255, 255);
        GL11.glDisable(3553);
        GL11.glEnable(3058);
        GL11.glLogicOp(5387);
//        tessellator.startDrawingQuads();
//        tessellator.addVertex(f, f4, 0.0);
//        tessellator.addVertex(f3, f4, 0.0);
//        tessellator.addVertex(f3, f2, 0.0);
//        tessellator.addVertex(f, f2, 0.0);
//        tessellator.draw();
        GL11.glDisable(3058);
        GL11.glEnable(3553);
    }

    public void setMaxStringLength(int limit) {
        this.maxStringLength = limit;
        if (this.text.length() > limit) {
            this.text = this.text.substring(0, limit);
        }
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    public void setEnableBackgroundDrawing(boolean bl) {
        this.enableBackgroundDrawing = bl;
    }

    public void setTextColor(int color) {
        this.enabledColor = color;
    }

    public void setDisabledTextColour(int color) {
        this.disabledColor = color;
    }

    public void setFocused(boolean bl) {
        if (bl && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = bl;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setEnabled(boolean bl) {
        this.isEnabled = bl;
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public float IIIIIIlIlIlIllllllIlllIlI() {
        return this.getEnableBackgroundDrawing() ? this.width - (float) 8 : this.width;
    }

    public void setSelectionPos(int n) {
        int n2 = this.text.length();
        if (n > n2) {
            n = n2;
        }
        if (n < 0) {
            n = 0;
        }
        this.selectionEnd = n;
        if (this.font != null) {
            if (this.lineScrollOffset > n2) {
                this.lineScrollOffset = n2;
            }
            float f = this.IIIIIIlIlIlIllllllIlllIlI();
            String string = this.font.setWrapWords(this.text.substring(this.lineScrollOffset), f);
            int n3 = string.length() + this.lineScrollOffset;
            if (n == this.lineScrollOffset) {
                this.lineScrollOffset -= this.font.wrapWords(this.text, f, true).length();
            }
            if (n > n3) {
                this.lineScrollOffset += n - n3;
            } else if (n <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - n;
            }
            if (this.lineScrollOffset < 0) {
                this.lineScrollOffset = 0;
            }
            if (this.lineScrollOffset > n2) {
                this.lineScrollOffset = n2;
            }
        }
    }

    public void setCanLoseFocus(boolean bl) {
        this.canLoseFocus = bl;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    @Override
    public void handleElementUpdate() {
        this.updateCursorCounter();
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        this.drawElement();
    }

    @Override
    public void keyTyped(char c, int n) {
        this.textboxKeyTyped(c, n);
    }
}
