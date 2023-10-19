package com.cheatbreaker.client.ui.element;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.Minecraft;

public abstract class AbstractModulesGuiElement {
    public float scale;
    public int yOffset = 0;
    public int x;
    public int y;
    public int width;
    public int height;
    public Setting setting;

    public AbstractModulesGuiElement(float scaleFactor) {
        this.scale = scaleFactor;
    }

    public void setDimensions(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void handleDrawElement(int mouseX, int mouseY, float partialTicks);

    public abstract void handleMouseClick(int mouseX, int mouseY, int button);

    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        return false;
    }

    public void handleElementUpdate() {
    }

    public void handleElementClose() {
    }

    public void keyTyped(char c, int n) {

    }

    public boolean isMouseInside(int mouseX, int mouseY) {
        return (float)mouseX > (float)this.x * this.scale && (float)mouseX < (float)(this.x + this.width) * this.scale && (float)mouseY > (float)(this.y + this.yOffset) * this.scale && (float)mouseY < (float)(this.y + this.height + this.yOffset) * this.scale && Minecraft.getMinecraft().currentScreen instanceof HudLayoutEditorGui;
    }

    public void onScroll(int n) {
    }

    public int getHeight() {
        return this.height;
    }

    public void drawDescription(Setting setting, int mouseX, int mouseY) {
//        if ((float)mouseY > (float)(this.y + this.yOffset) * this.scale && (float)mouseY < (float)(this.y + this.height + this.yOffset) * this.scale && !setting.getSettingDescription().equals("")) {
//            String wrapped = WordWrap.from(setting.getSettingDescription()).maxWidth(60).insertHyphens(false).wrap();
//            String[] lines = wrapped.split("\n");
//            int index = 0;
//            int lineAmount = -1;
//            int longestStringWidth = 0;
//            for(String line : lines) {
//                longestStringWidth = longestStringWidth < CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(line) ? CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(line) : longestStringWidth;
//                lineAmount++;
//            }
//            float f = (mouseX * 2 * 0.5f + 5) / this.scale;
//            float f2 = (-this.yOffset / CheatBreaker.getScaleFactor() + mouseY * 2 * 0.5f + 7) / this.scale;
//            RenderUtil.drawRoundedRect(f, f2, f + 5.0f + longestStringWidth, f2 + 12.0f + 8 * lineAmount, 8.0, -1087492562);
//            for(String line : lines) {
//                CheatBreaker.getInstance().ubuntuMedium16px.drawStringWithShadow(line, f + 3.0f, f2 + 8 * index + 1.0f, -1);
//                index++;
//            }
//        }
    }

    public boolean shouldHide(Setting setting) {
        if (setting.getCustomizationLevel() != null) {
            if (CheatBreaker.getInstance().getGlobalSettings().customizationLevel.getValue().equals("Simple")) {
                if (!setting.getCustomizationLevel().equals(CustomizationLevel.SIMPLE)) {
                    return true;
                }
            }
            if (CheatBreaker.getInstance().getGlobalSettings().customizationLevel.getValue().equals("Medium")) {
                if (!setting.getCustomizationLevel().equals(CustomizationLevel.SIMPLE) && !setting.getCustomizationLevel().equals(CustomizationLevel.MEDIUM)) {
                    return true;
                }
            }
        }

        if (setting.getCondition() == null) {
            return false;
        }
        return !((Boolean) setting.getCondition().getAsBoolean());
    }
}
