package com.cheatbreaker.client.ui.element.profile;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.config.Profile;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.module.ProfileCreatorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.Collections;

public class ProfileElement extends AbstractModulesGuiElement {
    private final int highlightColor;
    public final Profile profile;
    private final AbstractScrollableElement scrollableElement;
    private int animationSpeed = 0;
    private final ResourceLocation deleteIcon = new ResourceLocation("client/icons/delete-64.png");
    private final ResourceLocation themeIcon = new ResourceLocation("client/icons/checkmark-64.png");
    private final ResourceLocation arrowIcon = new ResourceLocation("client/icons/right.png");
    private final ResourceLocation pencilIcon = new ResourceLocation("client/icons/pencil-64.png");

    public ProfileElement(AbstractScrollableElement abstractScrollableElement, int highlightColor, Profile profile, float scale) {
        super(scale);
        this.scrollableElement = abstractScrollableElement;
        this.highlightColor = highlightColor;
        this.profile = profile;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean bl;
        boolean bl2;
        float animationSpeed;
        boolean bl3 = mouseX > this.x + 12 && this.isMouseInside(mouseX, mouseY);
        int n3 = 75;

        Gui.drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);

        if (bl3) {
            if (this.animationSpeed < n3) {
                animationSpeed = HudLayoutEditorGui.getFPSTransitionSpeed(790);
                this.animationSpeed = (int) ((float) this.animationSpeed + animationSpeed);
                if (this.animationSpeed > n3) {
                    this.animationSpeed = n3;
                }
            }
        } else if (this.animationSpeed > 0) {
            animationSpeed = HudLayoutEditorGui.getFPSTransitionSpeed(790);
            this.animationSpeed = (float) this.animationSpeed - animationSpeed < 0.0f ? 0 : (int) ((float) this.animationSpeed - animationSpeed);
        }

        if (this.animationSpeed > 0) {
            animationSpeed = (float) this.animationSpeed / (float) n3 * 100.0F;
            Gui.drawRect(this.x + 12, (int) ((float) this.y + ((float) this.height - (float) this.height * animationSpeed / 100.0F)), this.x + this.width - (this.profile.isNotEditable() ? 0 : 30), this.y + this.height, this.highlightColor);
        }

        boolean bl4 = (float) mouseX > (float) this.x * this.scale && (float) mouseX < (float) (this.x + 12) * this.scale && (float) mouseY >= (float) (this.y + this.yOffset) * this.scale && (float) mouseY <= (float) (this.y + this.height / 2 + this.yOffset) * this.scale;
        boolean bl5 = (float) mouseX > (float) this.x * this.scale && (float) mouseX < (float) (this.x + 12) * this.scale && (float) mouseY > (float) (this.y + this.height / 2 + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;
        float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.35f);
        float f3 = 2.5f;
        if (!this.profile.isNotEditable()) {
            bl2 = false;
            bl = false;
            ProfilesListElement profilesListElement = (ProfilesListElement) this.scrollableElement;

            if (profilesListElement.profileList.indexOf(this) != 0 && profilesListElement.profileList.indexOf(this) > 1) {
                bl2 = true;
                GL11.glPushMatrix();
                if (bl4) {
                    GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.65f);
                }
                GL11.glTranslatef((float) (this.x + 6) - f3, (float) this.y + 7.0F, 0.0f);
                GL11.glRotatef(-90, 0.0f, 0.0f, 1.0f);
                RenderUtil.renderIcon(this.arrowIcon, f3, (float) -1, 0.0f);
                GL11.glPopMatrix();
                GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.35f);
            }

            if (profilesListElement.profileList.indexOf(this) != profilesListElement.profileList.size() - 1) {
                bl = true;
                GL11.glPushMatrix();
                if (bl5) {
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.65f);
                }
                GL11.glTranslatef((float) (this.x + 6) + f3, (float) this.y + 7.0F, 0.0f);
                GL11.glRotatef(90, 0.0f, 0.0f, 1.0f);
                RenderUtil.renderIcon(this.arrowIcon, f3, 2.0f, 0.0f);
                GL11.glPopMatrix();
            }

            if (!bl2 && !bl) {
                RenderUtil.renderIcon(this.arrowIcon, 2.5f, (float) (this.x + 4), (float) this.y + 6.0f);
            }
        } else {
            RenderUtil.renderIcon(this.arrowIcon, 2.5f, (float) (this.x + 4), (float) this.y + 6.0f);
        }

        if (CheatBreaker.getInstance().getConfigManager().activeProfile == this.profile) {
            CheatBreaker.getInstance().playBold18px.drawString(this.profile.getName().toUpperCase(), (float)this.x + 16.0f, (float)(this.y + 3), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor2 : CBTheme.lightDullTextColor2);
        } else {
            CheatBreaker.getInstance().playRegular16px.drawString(this.profile.getName().toUpperCase(), (float)this.x + 16.0f, (float)this.y + 3.5f, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor2 : CBTheme.lightDullTextColor2);
        }
        if (CheatBreaker.getInstance().getConfigManager().activeProfile == this.profile) {
            CheatBreaker.getInstance().playRegular14px.drawString(" (Active)", (float)this.x + (float)17 + (float)CheatBreaker.getInstance().playBold18px.getStringWidth(this.profile.getName().toUpperCase()), (float)this.y + (float)4, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor4 : CBTheme.lightDullTextColor4);
        }

        if (!this.profile.isNotEditable()) {


            bl2 = (float) mouseX > (float) (this.x + this.width - 30) * this.scale && (float) mouseX < (float) (this.x + this.width - 13) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;
            GL11.glColor4f(bl2 ? 0.0f : 1.1707317f * 0.21354167f, bl2 ? 0.0f : 0.101648346f * 2.4594595f, bl2 ? 0.48876402f * 1.0229886f : 0.5647059f * 0.4427083f, 0.5675676f * 1.145238f);
            RenderUtil.renderIcon(this.pencilIcon, (float) 5, (float) (this.x + this.width - 26), (float) this.y + 5.1916666f * 0.6741573f);

            bl = (float) mouseX > (float) (this.x + this.width - 17) * this.scale && (float) mouseX < (float) (this.x + this.width - 2) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;
            GL11.glColor4f(bl ? 1.4181818f * 0.5641026f : 0.96875f * 0.2580645f, bl ? 0.0f : 0.17553192f * 1.4242424f, bl ? 0.0f : 15.250001f * 0.016393442f, 0.44444445f * 1.4625f);
            RenderUtil.renderIcon(this.deleteIcon, (float) 5, (float) (this.x + this.width - 13), (float) this.y + 0.7653061f * 4.5733333f);
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl = (float) mouseX > (float) (this.x + this.width - 17) * this.scale && (float) mouseX < (float) (this.x + this.width - 2) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;
        boolean bl2 = (float) mouseX > (float) (this.x + this.width - 30) * this.scale && (float) mouseX < (float) (this.x + this.width - 13) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;
        boolean bl3 = (float) mouseX > (float) this.x * this.scale && (float) mouseX < (float) (this.x + 12) * this.scale && (float) mouseY >= (float) (this.y + this.yOffset) * this.scale && (float) mouseY <= (float) (this.y + this.height / 2 + this.yOffset) * this.scale;
        boolean bl4 = (float) mouseX > (float) this.x * this.scale && (float) mouseX < (float) (this.x + 12) * this.scale && (float) mouseY > (float) (this.y + this.height / 2 + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + this.height + this.yOffset) * this.scale;

        ProfilesListElement profilesListElement = (ProfilesListElement) this.scrollableElement;

        if (!this.profile.isNotEditable() && (bl3 || bl4)) {
            if (bl3 && ((ProfilesListElement) this.scrollableElement).profileList.indexOf(this) != 0 && ((ProfilesListElement) this.scrollableElement).profileList.indexOf(this) > 1) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                this.profile.index = profilesListElement.profileList.indexOf(this) - 1;
                profilesListElement.profileList.get(profilesListElement.profileList.indexOf(this) - 1).profile.index = profilesListElement.profileList.indexOf(this);
                Collections.swap(profilesListElement.profileList, profilesListElement.profileList.indexOf(this), profilesListElement.profileList.indexOf(this) - 1);
            }
            if (bl4 && profilesListElement.profileList.indexOf(this) != profilesListElement.profileList.size() - 1) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                this.profile.index = profilesListElement.profileList.indexOf(this) + 1;
                profilesListElement.profileList.get(profilesListElement.profileList.indexOf(this) + 1).profile.index = profilesListElement.profileList.indexOf(this);
                Collections.swap(profilesListElement.profileList, profilesListElement.profileList.indexOf(this), profilesListElement.profileList.indexOf(this) + 1);
            }
        } else if (!this.profile.isNotEditable() && bl) {
            File file;
            File file2;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            if (CheatBreaker.getInstance().getConfigManager().activeProfile == this.profile) {
                CheatBreaker.getInstance().getConfigManager().activeProfile = CheatBreaker.getInstance().getConfigManager().moduleProfiles.get(0);
                CheatBreaker.getInstance().configManager.readProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
                CheatBreaker.getInstance().getModuleManager().keystrokesMod.updateKeyElements();
                Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
            }
            if (!this.profile.isNotEditable() && (file2 = (file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-") + File.separator + "profiles")).exists() || file.mkdirs() ? new File(file + File.separator + this.profile.getName().toLowerCase() + ".cfg") : null).exists() && file2.delete()) {
                CheatBreaker.getInstance().getConfigManager().moduleProfiles.removeIf(profile -> profile == this.profile);
                profilesListElement.profileList.removeIf(profileElement -> profileElement == this);
            }
        } else if (!this.profile.isNotEditable() && bl2) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            Minecraft.getMinecraft().displayGuiScreen(new ProfileCreatorGui(this.profile, HudLayoutEditorGui.instance, (ProfilesListElement) this.scrollableElement, this.highlightColor, this.scale));
        } else if (CheatBreaker.getInstance().getConfigManager().activeProfile != this.profile) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            CheatBreaker.getInstance().configManager.updateProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
            CheatBreaker.getInstance().getConfigManager().activeProfile = this.profile;
            CheatBreaker.getInstance().configManager.readProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
            CheatBreaker.getInstance().getModuleManager().keystrokesMod.updateKeyElements();
            Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
        }
    }
}
