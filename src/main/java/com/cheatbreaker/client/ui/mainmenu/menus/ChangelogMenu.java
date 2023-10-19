package com.cheatbreaker.client.ui.mainmenu.menus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.mainmenu.buttons.ChangeLogButton;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChangelogMenu extends MainMenuBase {
    private final GradientTextButton backButton;
    private final ScrollableElement scrollableElement;
    private final List<ChangeLogButton> changeLogButtonList = new ArrayList<>();
    public static JsonObject changelogObject;

    public ChangelogMenu() {
        this.backButton = new GradientTextButton("BACK");
        this.scrollableElement = new ScrollableElement(null);
        this.loadChangelog();
    }

    @Override
    public void initGui() {
        super.initGui();
        float f = Math.min(240.0f, this.getScaledWidth() - 10.0f);
        float f2 = this.getScaledWidth() / 2.0f - f / 2.0f;
        float f3 = this.getScaledWidth() / 2.0f + f / 2.0f;
        float f4 = 40;
        float f5 = this.getScaledHeight() - 40.0f;
        this.scrollableElement.setElementSize(f3 - 8.0f, f4 + 18.0f, 4.0f, f5 - f4 - 28.0f);
        int n = 0;
        float f6 = 22;
        for (ChangeLogButton button : this.changeLogButtonList) {
            button.setElementSize(f2 + 8.0f, f4 + 20.0f + (float) n * (f6 + 1.0f), f - 16.0f, f6);
            ++n;
        }
        this.scrollableElement.setScrollAmount((float) this.changeLogButtonList.size() * (f6 + 1.0f) + 2.0f);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.scrollableElement.handleElementMouse();
    }

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);
        float f3 = Math.min(240.0f, this.getScaledWidth() - 10.0f);
        float f4 = this.getScaledWidth() / 2.0f - f3 / 2.0f;
        float f5 = this.getScaledWidth() / 2.0f + f3 / 2.0f;
        float f6 = 40;
        float f7 = this.getScaledHeight() - 40.0f;
        Gui.drawRect(f4, f6, f5, f7, 0x2F000000);
        Gui.drawRect(f4 + 8.0f, f6 + 16.0f, f5 - 8.0f, f6 + 0.15789473f * 104.50001f, 0x1AFFFFFF);
        CheatBreaker.getInstance().playRegular16px.drawString("CHANGELOG", f4 + 8.0f, f6 + 5.0f, -1);
        if (this.changeLogButtonList.size() < 1) {
            CheatBreaker.getInstance().playRegular16px.drawCenteredString("Nothing has changed in the client, that's odd!", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 4.0f, -6381922);
        }
        this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() - 35.0f, 60.0f, 12);
        this.backButton.drawElementHover(x, y, true);
        this.scrollableElement.drawScrollable(x, y, true);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtil.startScissorBox((int) f4, (int) f6 + 18, (int) f5, (int) f7 - 8, (float) ((int) ((float) this.getScaledResolution().getScaleFactor() * this.getScaleFactor())), (int) this.getScaledHeight());
        for (ChangeLogButton changeLogButton : this.changeLogButtonList) {
            changeLogButton.drawElementHover(x, y - this.scrollableElement.getPosition(), !this.scrollableElement.isButtonHeld());
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.scrollableElement.drawElementHover(x, y, true);
    }

    @Override
    public void mouseClicked(float f, float f2, int n) {
        super.mouseClicked(f, f2, n);
        if (this.backButton.isMouseInside(f, f2)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new MainMenu());
        } else if (this.scrollableElement.isMouseInside(f, f2)) {
            this.scrollableElement.handleElementMouseClicked(f, f2, n, true);
        } else {
            float f3 = Math.min((float) 240, this.getScaledWidth() - (float) 10);
            float f4 = this.getScaledWidth() / 2.0f - f3 / 2.0f;
            float f5 = this.getScaledWidth() / 2.0f + f3 / 2.0f;
            float f6 = 40;
            float f7 = this.getScaledHeight() - (float) 40;
            if (f > f4 && f < f5 && f2 > f6 && f2 < f7) {
                for (ChangeLogButton button : this.changeLogButtonList) {
                    button.handleElementMouseClicked(f, f2 - this.scrollableElement.getPosition(), n, button.isMouseInside(f, f2 - this.scrollableElement.getPosition()) && !this.scrollableElement.isButtonHeld());
                }
            }
        }
    }

    @SneakyThrows
    private void loadChangelog() {
        String branchToCheck = CheatBreaker.getInstance().getGitBranch();
        if (changelogObject.toString().contains("\"?\"") && CheatBreaker.getInstance().getGitBranch().equalsIgnoreCase("dev")) {
            branchToCheck = "?";
        } else if (changelogObject == null || !changelogObject.toString().contains("\"" + CheatBreaker.getInstance().getGitBranch() + "\"")) return;

        Set<Map.Entry<String, JsonElement>> entries = changelogObject.getAsJsonObject(branchToCheck).entrySet();
        for (Map.Entry<String, JsonElement> changelog : entries) {
            JsonObject changelogObject = changelog.getValue().getAsJsonObject();
            String title = changelog.getKey();
            String author = changelogObject.get("author").getAsString();
            String description = changelogObject.get("description").getAsString();

            this.changeLogButtonList.add(new ChangeLogButton(title, author, description));
        }
    }
}
