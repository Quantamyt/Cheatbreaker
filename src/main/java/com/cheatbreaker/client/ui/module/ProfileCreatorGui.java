package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.Profile;
import com.cheatbreaker.client.ui.element.profile.ProfileElement;
import com.cheatbreaker.client.ui.element.profile.ProfilesListElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.src.Config;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProfileCreatorGui extends GuiScreen {
    private final GuiScreen selectedButton;
    private GuiTextField textField = null;
    private final float scale;
    private final int highlightColor;
    private final ProfilesListElement profileList;
    private String errorString = "";
    private boolean showGui = false;
    private Profile profile;

    public ProfileCreatorGui(Profile profile, GuiScreen guiScreen, ProfilesListElement list, int highlightColor, float scale) {
        this(guiScreen, list, highlightColor, scale);
        this.profile = profile;
    }

    public ProfileCreatorGui(GuiScreen guiScreen, ProfilesListElement list, int highlightColor, float scale) {
        this.selectedButton = guiScreen;
        this.scale = scale;
        this.profileList = list;
        this.highlightColor = highlightColor;
        this.showGui = true;
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        if (!this.showGui) {
            this.mc.displayGuiScreen(this.selectedButton);
            ((HudLayoutEditorGui) this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui) this.selectedButton).profilesElement;
        } else {
            this.showGui = false;
            this.textField = new GuiTextField(299, this.mc.fontRendererObj, this.width / 2 - 70, this.height / 2 - 6, 140, 10);
            if (this.profile != null) {
                this.textField.setText(this.profile.getName());
            }
            this.textField.setFocused(true);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.selectedButton.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
        ProfileCreatorGui.drawRect(this.width / 2 - 73, this.height / 2 - 19, this.width / 2 + 73, this.height / 2 + 8, -11250604);
        ProfileCreatorGui.drawRect(this.width / 2 - 72, this.height / 2 - 18, this.width / 2 + 72, this.height / 2 + 7, -3881788);
        GL11.glPushMatrix();
        GL11.glScalef(this.scale, this.scale, this.scale);
        int n3 = (int) ((float) this.width / this.scale);
        int n4 = (int) ((float) this.height / this.scale);
        CheatBreaker.getInstance().ubuntuMedium16px.drawString("Profile Name: ", (float) (n3 / 2) - (float) 70 / this.scale, (float) (n4 / 2) - (float) 17 / this.scale, 0x6F000000);
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.errorString, (float) (n3 / 2) - (float) 72 / this.scale, (float) (n4 / 2) + (float) 8 / this.scale, -1358954496);
        GL11.glPopMatrix();
        this.textField.drawTextBox();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
    }

    @Override
    public void keyTyped(char c, int n) {
        switch (n) {
            case 1:
                this.mc.displayGuiScreen(this.selectedButton);
                ((HudLayoutEditorGui) this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui) this.selectedButton).profilesElement;
                break;
            case 28:
                if (this.textField.getText().length() < 3) {
                    this.errorString = EnumChatFormatting.RED + "Name must be at least 3 characters long.";
                    break;
                }
                if (this.textField.getText().equalsIgnoreCase("default")) {
                    this.errorString = EnumChatFormatting.RED + "That name is already in use.";
                    break;
                }
                if (!this.textField.getText().matches("([a-zA-Z0-9-_ \\]\\[]+)")) {
                    this.errorString = EnumChatFormatting.RED + "Illegal characters in name.";
                    break;
                }
                if (this.profile != null && !this.profile.isNotEditable()) {
                    File file = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-") + File.separator + "profiles" + File.separator + this.profile.getName() + ".cfg");
                    File file2 = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-") + File.separator + "profiles" + File.separator + this.textField.getText() + ".cfg");
                    if (!file.exists()) break;
                    try {
                        Files.copy(file.toPath(), file2.toPath());
                        Files.delete(file.toPath());
                        this.profile.setName(this.textField.getText());
                        this.mc.displayGuiScreen(this.selectedButton);
                        ((HudLayoutEditorGui) this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui) this.selectedButton).profilesElement;
                    } catch (Exception exception) {
                        this.errorString = EnumChatFormatting.RED + "Could not save profile.";
                        exception.printStackTrace();
                    }
                    break;
                }
                Profile profile = null;
                for (Profile profile2 : CheatBreaker.getInstance().getConfigManager().moduleProfiles) {
                    if (!profile2.getName().toLowerCase().equalsIgnoreCase(this.textField.getText())) continue;
                    profile = profile2;
                    break;
                }
                if (profile == null) {
                    CheatBreaker.getInstance().configManager.updateProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
                    Profile profile3 = new Profile(this.textField.getText(), false);
                    CheatBreaker.getInstance().getConfigManager().moduleProfiles.add(profile3);
                    CheatBreaker.getInstance().getConfigManager().activeProfile = profile3;
                    this.profileList.profileList.add(new ProfileElement(this.profileList, this.highlightColor, profile3, this.scale));
                    CheatBreaker.getInstance().configManager.updateProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
                    this.mc.displayGuiScreen(this.selectedButton);
                    ((HudLayoutEditorGui) this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui) this.selectedButton).profilesElement;
                    break;
                }
                this.errorString = EnumChatFormatting.RED + "That name is already in use.";
                break;
            default:
                this.textField.textboxKeyTyped(c, n);
        }
    }
}
