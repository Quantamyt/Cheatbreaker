package com.cheatbreaker.client.ui.element.profile;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.config.Profile;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.element.module.ModulesGuiButtonElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.module.ProfileCreatorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfilesListElement extends AbstractScrollableElement {
    private final int highlightColor;
    public final List<ProfileElement> profileList = new ArrayList<>();
    private final ResourceLocation plusIcon = new ResourceLocation("client/icons/plus-64.png");
    private final ModulesGuiButtonElement openProfilesFolderButton;

    public ProfilesListElement(float scale, int x, int y, int width, int height) {
        super(scale, x, y, width, height);
        this.highlightColor = -12418828;
        this.openProfilesFolderButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().playBold18px, null, "Open Profiles Folder", this.x + width - 120, this.y + height + 4, 110, 28, -12418828, scale);
        this.loadProfiles();
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        int hovering;
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height + 2, 8, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor4);

        this.onScroll(mouseX, mouseY);
        this.scrollHeight = 15;
        for (hovering = 0; hovering < this.profileList.size(); ++hovering) {
            ProfileElement object = this.profileList.get(hovering);
            object.setDimensions(this.x + 4, this.y + 4 + hovering * 18, this.width - 12, 18);
            object.yOffset = this.scrollAmount;
            object.handleDrawElement(mouseX, mouseY, partialTicks);
            this.scrollHeight += object.getHeight();
        }
        hovering = (float) mouseX > (float)(this.x + this.width - 92) * this.scale && (float) mouseX < (float)(this.x + this.width - 6) * this.scale && (float)mouseY > (float)(this.y + this.scrollHeight - 10 + this.scrollAmount) * this.scale && (float)mouseY < (float)(this.y + this.scrollHeight + 3 + this.scrollAmount) * this.scale ? 1 : 0;
        GL11.glColor4f(hovering != 0 ? 0.0f : 0.25f, hovering != 0 ? 0.8f : 0.25f, hovering != 0 ? 0.0f : 0.25f, 0.65f);
        RenderUtil.renderIcon(this.plusIcon, 3.5f, (float)(this.x + this.width - 15), (float)(this.y + this.scrollHeight) - 6.5f);
        String addNewProfile = (hovering != 0 ? "(COPIES CURRENT PROFILE) " : "") + "ADD NEW PROFILE";
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(addNewProfile, this.x + this.width - 17 - CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(addNewProfile), (float)(this.y + this.scrollHeight) - 7.5f, hovering != 0 ? 0x7F007F00 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor6 : CBTheme.lightTextColor6);
        this.scrollHeight += 10;
        this.openProfilesFolderButton.yOffset = this.scrollAmount;
        this.openProfilesFolderButton.setDimensions(this.x + this.width - 130, this.y + this.scrollHeight, 125, 20);
        this.openProfilesFolderButton.handleDrawElement(mouseX, mouseY, partialTicks);
        this.onGuiDraw(mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.openProfilesFolderButton.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            try {
                Desktop.getDesktop().open(CheatBreaker.getInstance().getConfigManager().profileDir);
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
            }
            return;
        }

        for (ProfileElement profileElement : this.profileList) {
            if (!profileElement.isMouseInside(mouseX, mouseY)) continue;
            profileElement.handleMouseClick(mouseX, mouseY, button);
            return;
        }
        boolean bl = (float) mouseX > (float)(this.x + this.width - 92) * this.scale && (float) mouseX < (float)(this.x + this.width - 6) * this.scale && (float) mouseY > (float)(this.y + this.scrollHeight - 20 + this.scrollAmount) * this.scale && (float) mouseY < (float)(this.y + this.scrollHeight - 7 + this.scrollAmount) * this.scale;
        if (bl) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            Minecraft.getMinecraft().displayGuiScreen(new ProfileCreatorGui(HudLayoutEditorGui.instance, this, this.highlightColor, this.scale));
        }
        double d = this.height - 10;
        double d2 = this.scrollHeight;
        double d3 = d / d2 * (double)100;
        double d4 = d / (double)100 * d3;
        double d5 = (double)this.scrollAmount / 100.0 * d3;
        boolean bl43 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (double)mouseY > ((double)(this.y + 11) - d5) * (double)this.scale && (double)mouseY < ((double)(this.y + 8) + d4 - d5) * (double)this.scale;
        boolean bl33 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (float)mouseY > (float)(this.y + 11) * this.scale && (double)mouseY < ((double)(this.y + 6) + d - (double)3) * (double)this.scale;
        if (button == 0 && bl33 || bl43) {
            this.hovering = true;
        }
    }

    @Override
    public boolean hasSettings(AbstractModule module) {
        return true;
    }

    @Override
    public void handleModuleMouseClick(AbstractModule abstractModule) {
    }

    public void loadProfiles() {
        new Thread(() -> {
            this.profileList.clear();
            File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch() + File.separator + "profiles");
            if (file.exists()) {
                for (File file2 : file.listFiles()) {
                    if (!file2.getName().endsWith(".cfg")) continue;
                    Profile profile = null;
                    for (Profile profile2 : CheatBreaker.getInstance().profiles) {
                        if (!file2.getName().equals(profile2.getName() + ".cfg")) continue;
                        profile = profile2;
                    }
                    if (profile != null) continue;
                    CheatBreaker.getInstance().profiles.add(new Profile(file2.getName().replace(".cfg", ""), false));
                }
            }
            for (Profile profile : CheatBreaker.getInstance().profiles) {
                this.profileList.add(new ProfileElement(this, this.highlightColor, profile, this.scale));
            }
            this.profileList.sort((profileElement, profileElement2) -> {
                if (profileElement.profile.getName().equalsIgnoreCase("default")) {
                    return 0;
                }
                if (profileElement.profile.index < profileElement2.profile.index) {
                    return -1;
                }
                return 1;
            });
        }).start();
    }
}
