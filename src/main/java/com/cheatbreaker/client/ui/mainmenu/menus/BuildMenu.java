package com.cheatbreaker.client.ui.mainmenu.menus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.util.render.wordwrap.WordWrap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class BuildMenu extends MainMenuBase {
    private final GradientTextButton backButton = new GradientTextButton("BACK");

    @Override
    public void initGui() {
        super.initGui();
        this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() / 2.0f + 55.0f, 60.0f, 12.0f);
    }

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);
        Gui.drawRect(this.getScaledWidth() / 2.0f - 80.0f, this.getScaledHeight() / 2.0f - 40.0f, this.getScaledWidth() / 2.0f + 80.0f, this.getScaledHeight() / 2.0f + 50.0f, 0x2F000000);
        this.backButton.drawElementHover(x, y, true);
        String buildInfo = "";
        switch (CheatBreaker.getInstance().getGitBuildVersion()) {
            case "Dev":
                buildInfo = "Development builds are not intended for bug testing purposes; however, expect bugs to appear.";
                break;
            case "Beta":
                buildInfo = "Beta builds are used to test for potential issues. Expect bugs to occur over time.";
                break;
            case "Preview":
                buildInfo = "Preview builds are intended to become the next stable build. Bugs should be less prominent.";
                break;
            case "Special Edition":
                buildInfo = "Special Edition builds are used for very special events. Treat them with care.";
                break;
        }
        String buildType = CheatBreaker.getInstance().getGitBuildVersion().isEmpty() ? "Production" : CheatBreaker.getInstance().getGitBuildVersion();
        String wrapped = WordWrap.from(buildInfo).maxWidth(40).insertHyphens(false).wrap();
        String[] lines = wrapped.split("\n");
        int index = 0;
        for(String line : lines) {
            CheatBreaker.getInstance().playRegular14px.drawCenteredString(line, this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 14.0f + (index * CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f), 0xFFDDDDDD);
            index++;
        }
        CheatBreaker.getInstance().playRegular16px.drawCenteredString("Commit: " + CheatBreaker.getInstance().getGitCommitIdAbbrev() + "/" + CheatBreaker.getInstance().getGitBranch(), this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 14.0f - (index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f)), 0xFFEEEEEE);
        CheatBreaker.getInstance().playRegular16px.drawCenteredString("Minecraft Version: " + Config.MC_VERSION, this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f - 6.0f - (index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f)), 0xFFEEEEEE);
        CheatBreaker.getInstance().playRegular16px.drawCenteredString("Build Version: " + buildType, this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 4.0f - (index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f)), 0xFFEEEEEE);
    }

    public void mouseClicked(float var1, float var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        if (this.backButton.isMouseInside(var1, var2)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new MainMenu());
        }
    }
}
