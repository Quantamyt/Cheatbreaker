package com.cheatbreaker.client.ui.element.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.PreviewType;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.normal.hud.cooldowns.CooldownRenderer;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.module.ModulePlaceGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class ModulePreviewElement extends AbstractModulesGuiElement {
    private final AbstractModule module;
    private final ModulesGuiButtonElement optionsButton;
    private final ModulesGuiButtonElement mainEnableDisableButton;
    private final ModulesGuiButtonElement secondaryEnableDisableButton;

    private final AbstractScrollableElement scrollableElement;

    public static ModulePreviewElement instance;

    private final boolean compact = CheatBreaker.getInstance().getGlobalSettings().compactMode.getValue().equals("Compact");

    public ModulePreviewElement(AbstractScrollableElement abstractScrollableElement, AbstractModule module, float scale) {
        super(scale);
        instance = this;
        this.module = module;
        this.scrollableElement = abstractScrollableElement;
        boolean validModule = module != CheatBreaker.getInstance().getModuleManager().miniMapMod && module != CheatBreaker.getInstance().getModuleManager().notificationsMod;

        String mainEnableDisableButtonString = module.getGuiAnchor() == null ? (module.isRenderHud() ? "Disable" : "Enable") : (module.isRenderHud() ? "Hide from HUD" : "Add to HUD");
        String secondaryEnableDisableButtonString = module.isEnabled() ? "Disable" : "Enable";
        String optionsString = (this.compact ? "cog-64.png" : "Options");

        CBFontRenderer boldFont = CheatBreaker.getInstance().playBold18px;
        CBFontRenderer regularFont = CheatBreaker.getInstance().playRegular14px;

        this.secondaryEnableDisableButton = new ModulesGuiButtonElement(regularFont, null, secondaryEnableDisableButtonString, this.x + this.width / 2 + 2, this.y + this.height - 38, this.x + this.width - 4, this.y + this.height - 24, module.isEnabled() ? -5756117 : -13916106, scale);
        this.optionsButton = new ModulesGuiButtonElement(boldFont, null, optionsString, this.x + 4, this.y + this.height - 20, this.x + this.width - 4, this.y + this.height - 6, -12418828, scale);
        this.mainEnableDisableButton = new ModulesGuiButtonElement(regularFont, null, mainEnableDisableButtonString, this.x + 4, this.y + this.height - 38, this.x + this.width / 2 - 2, this.y + this.height - 24, module.isRenderHud() ? -5756117 : -13916106, scale);
        this.mainEnableDisableButton.allowAddToHud(validModule);
    }

    @Override
    public void handleDrawElement(int mouseX, int n2, float partialTicks) {
        float f2;
        String previewString;

        if (this.module.isEnabled()) {
            Gui.drawRect(this.x, this.y, this.x + this.width, this.compact ? this.y + 16 : this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkEnabledBackgroundColor : CBTheme.lightEnabledBackgroundColor);
        } else {
            Gui.drawRect(this.x, this.y, this.x + this.width, this.compact ? this.y + 16 : this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkInactiveBackgroundColor : CBTheme.lightInactiveBackgroundColor);
        }

        CBFontRenderer cBFontRenderer = CheatBreaker.getInstance().playBold18px;
        GL11.glPushMatrix();
        int n3 = 0;
        int n4 = 0;
        if (!this.compact) {
            if (this.module == CheatBreaker.getInstance().getModuleManager().armourStatus) {
                n3 = -10;
                previewString = "329/329";
                f2 = Minecraft.getMinecraft().fontRenderer.getStringWidth(previewString);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(previewString, (float) ((int) ((float) (this.x + 1 + this.width / 2) - f2 / 2.0f)), (float) (this.y + this.height / 2 - 18), -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().potionEffectsMod) {
                n4 = -30;
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Speed II", (float) (this.x + 8 + this.width / 2 - 20), (float) (this.y + this.height / 2 - 36), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("0:42", (float) (this.x + 8 + this.width / 2 - 20), (float) (this.y + this.height / 2 - 26), -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().scoreboardMod) {
                Gui.drawRect(this.x + 20, this.y + this.height / 2 - 44, this.x + this.width - 20, this.y + this.height / 2 - 6, 0x6F000000);
                Minecraft.getMinecraft().fontRenderer.drawCenteredStringWithShadow("Score", this.x + this.width / 2, this.y + this.height / 2 - 40, -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Steve", (float) (this.x + 24), (float) (this.y + this.height / 2 - 28), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Alex", (float) (this.x + 24), (float) (this.y + this.height / 2 - 18), -1);
                Minecraft.getMinecraft().fontRenderer.drawCenteredStringWithShadow(EnumChatFormatting.RED + "0", this.x + this.width - 26, this.y + this.height / 2 - 18, -1);
                Minecraft.getMinecraft().fontRenderer.drawCenteredStringWithShadow(EnumChatFormatting.RED + "1", this.x + this.width - 26, this.y + this.height / 2 - 28, -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().bossBarMod) {
                n3 = 3;
                previewString = "Wither";
                f2 = Minecraft.getMinecraft().fontRenderer.getStringWidth(previewString);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(previewString, (float) ((int) ((float) (this.x + 1 + this.width / 2) - f2 / 2.0f)), (float) (this.y + this.height / 2 - 36), -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().playerListMod) {
                Gui.drawRect(this.x + 20, this.y + this.height / 2 - 44, this.x + this.width - 20, this.y + this.height / 2 - 7, 0x6F000000);
                Gui.drawRect(this.x + 21, this.y + this.height / 2 - 43, this.x + this.width - 21, this.y + this.height / 2 - 35, 0x20FFFFFF);
                Gui.drawRect(this.x + 21, this.y + this.height / 2 - 34, this.x + this.width - 21, this.y + this.height / 2 - 26, 0x20FFFFFF);
                Gui.drawRect(this.x + 21, this.y + this.height / 2 - 25, this.x + this.width - 21, this.y + this.height / 2 - 17, 0x20FFFFFF);
                Gui.drawRect(this.x + 21, this.y + this.height / 2 - 16, this.x + this.width - 21, this.y + this.height / 2 - 8, 0x20FFFFFF);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Minecraft.getMinecraft().getSession().getUsername(), (float) (this.x + 21), (float) (this.y + this.height / 2 - 43), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Steve", (float) (this.x + 21), (float) (this.y + this.height / 2 - 34), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Alex", (float) (this.x + 21), (float) (this.y + this.height / 2 - 25), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Notch", (float) (this.x + 21), (float) (this.y + this.height / 2 - 16), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.GREEN + "15", (float) (this.x + this.width - 21 - Minecraft.getMinecraft().fontRenderer.getStringWidth("15")), (float) (this.y + this.height / 2 - 43), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.GREEN + "75", (float) (this.x + this.width - 21 - Minecraft.getMinecraft().fontRenderer.getStringWidth("75")), (float) (this.y + this.height / 2 - 34), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.YELLOW + "155", (float) (this.x + this.width - 21 - Minecraft.getMinecraft().fontRenderer.getStringWidth("155")), (float) (this.y + this.height / 2 - 25), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.YELLOW + "250", (float) (this.x + this.width - 21 - Minecraft.getMinecraft().fontRenderer.getStringWidth("250")), (float) (this.y + this.height / 2 - 16), -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().chatMod) {
                Gui.drawRect(this.x + 10, this.y + this.height / 2 - 35, this.x + this.width - 10, this.y + this.height / 2 - 17, 0x6F000000);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.GREEN + "Steve" + EnumChatFormatting.WHITE + ": Hey.", (float) (this.x + 10), (float) (this.y + this.height / 2 - 34), -1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(EnumChatFormatting.GREEN + "Alex" + EnumChatFormatting.WHITE + ": Hi!", (float) (this.x + 10), (float) (this.y + this.height / 2 - 25), -1);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().nametagMod) {
                int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(Minecraft.getMinecraft().getSession().getUsername());
                String account = width > 94 ? "Steve" : Minecraft.getMinecraft().getSession().getUsername();
                width = Minecraft.getMinecraft().fontRenderer.getStringWidth(account);
                Gui.drawRect((float) (this.x + this.width / 2 - width / 2 - 8), (float) (this.y + this.height / 2 - 33), (float) (this.x + this.width / 2 + width / 2 + 8), (float) (this.y + this.height / 2 - 24), 1862270976);
                Minecraft.getMinecraft().fontRenderer.drawString(account, this.x + this.width / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(account) / 2 + 7, this.y + this.height / 2 - 32, -1, false);
                RenderUtil.renderIcon(new ResourceLocation("client/logo_white.png"), (float) (this.x + this.width / 2 - width / 2 - 7), (float) (this.y + this.height / 2 - 32), 13.0f, 7.0f);
            } else if (this.module == CheatBreaker.getInstance().getModuleManager().crosshairMod) {
                GL11.glScalef(2.0F, 2.0F, 0);
                float size = 6.0F;
                float gap = 4.0F;
                float thickness = 2.0F;
                float var11 = (this.x + this.width / 2) / 2.0F;
                float var12 = (this.y + this.height / 2 - 27) / 2.0F;

                Gui.drawBoxWithOutLine(var11 - gap - size, var12 - thickness / 2.0F, var11 - gap, var12 + thickness / 2.0F, 0.5F, -1358954496, -1);
                Gui.drawBoxWithOutLine(var11 + gap, var12 - thickness / 2.0F, var11 + gap + size, var12 + thickness / 2.0F, 0.5F, -1358954496, -1);
                Gui.drawBoxWithOutLine(var11 - thickness / 2.0F, var12 - gap - size, var11 + thickness / 2.0F, var12 - gap, 0.5F, -1358954496, -1);
                Gui.drawBoxWithOutLine(var11 - thickness / 2.0F, var12 + gap, var11 + thickness / 2.0F, var12 + gap + size, 0.5F, -1358954496, -1);
                GL11.glScalef(1.0F, 1.0F, 0);
            }

            if (this.module == CheatBreaker.getInstance().getModuleManager().cooldownsMod) {
                CooldownRenderer cooldown = new CooldownRenderer("EnderPearl", 368, 9000L);
                cooldown.render(CheatBreaker.getInstance().getModuleManager().cooldownsMod.theme, this.x + this.width / 2 - 18, this.y + this.height / 2 - 26 - 18, -1);
            } else if ((this.module.getPreviewType() == null || this.module.getPreviewType() == PreviewType.LABEL) && this.module != CheatBreaker.getInstance().getModuleManager().scoreboardMod && this.module != CheatBreaker.getInstance().getModuleManager().playerListMod && this.module != CheatBreaker.getInstance().getModuleManager().chatMod && this.module != CheatBreaker.getInstance().getModuleManager().nametagMod) {
                previewString = "";
                if (this.module.getPreviewType() == null) {
                    f2 = 2.0f;
                    for (String string : this.module.getName().split(" ")) {
                        String string2 = string.substring(0, 1);
                        previewString = previewString + (Objects.equals(previewString, "") ? string2 : string2.toLowerCase());
                    }
                } else {
                    f2 = this.module.getPreviewLabelSize();
                    previewString = this.module.getPreviewLabel();
                }

                GL11.glScalef(f2, f2, f2);
                float f3 = (float) Minecraft.getMinecraft().fontRenderer.getStringWidth(previewString) * f2;
                if (this.module.getPreviewType() == null && !this.compact) {
                    Minecraft.getMinecraft().fontRenderer.drawString(previewString, (int) (((float) (this.x + 1 + this.width / 2) - f3 / 2.0f) / f2), (int) ((float) (this.y + this.height / 2 - 32) / f2), -13750738);
                } else {
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(previewString, (float) ((int) (((float) (this.x + 1 + this.width / 2) - f3 / 2.0f) / f2)), (float) ((int) ((float) (this.y + this.height / 2 - 32) / f2)), -1);
                }
            } else if (this.module.getPreviewType() == PreviewType.ICON) {
                float f4 = this.module.getPreviewIconWidth();
                f2 = this.module.getPreviewIconHeight();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderUtil.renderIcon(this.module.getPreviewIcon(), (float) (this.x + this.width / 2) - f4 / 2.0f + (float) n4, (float) (this.y + n3 + this.height / 2 - 26) - f2 / 2.0f, f4, f2);
            }
        }
        GL11.glPopMatrix();

        String name = this.module.getName();
        int max = 14;
        if (this.compact && name.replaceAll(" ", "").length() >= max)
            name = name.substring(0, max).trim() + "..";

        if (this.compact) {
            cBFontRenderer.drawString(name, (this.x + 4F), this.y + 4, 0x5F000000);
            cBFontRenderer.drawString(name, (this.x + 3F), this.y + 3, -1);
        } else {
            cBFontRenderer.drawCenteredString(name, (float) (this.x + this.width / 2) - 0.5F, this.y + this.height / 2 - 4 + 1, 0x5F000000);
            cBFontRenderer.drawCenteredString(name, (float) (this.x + this.width / 2) - 1.5F, this.y + this.height / 2 - 4, -1);
        }

        if (!this.compact) {
            this.secondaryEnableDisableButton.optionString = this.module.isEnabled() ? "Disable" : "Enable";
            this.secondaryEnableDisableButton.yOffset = this.yOffset;
            this.secondaryEnableDisableButton.setSmallIcon(false);
            if (!this.module.notRenderHUD) {
                this.secondaryEnableDisableButton.setDimensions(this.x + this.width / 2 + 8, this.y + this.height - 38, this.width / 2 - 12, this.y + this.height - 24 - (this.y + this.height - 38));
                this.secondaryEnableDisableButton.handleDrawElement(mouseX, n2, partialTicks);
            }
        }

        this.mainEnableDisableButton.optionString = this.compact ? this.module.isEnabled() ? "delete-64.png" : "plus-64.png" : this.module.getGuiAnchor() == null ? (this.module.isRenderHud() && this.module.isEnabled() ? "Disable" : "Enable") : (this.module.isRenderHud() && this.module.isEnabled() ? "Hide from HUD" : this.module.isWasRenderHud() && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? "Return to HUD" : "Add to HUD");
        this.mainEnableDisableButton.color = this.module.isRenderHud() && this.module.isEnabled() ? -5756117 : -13916106;
        this.mainEnableDisableButton.setDimensions(this.x + (this.compact ? 87 : 4), this.y + (this.compact ? 3 : this.height - 38), this.compact ? 10 : this.module.notRenderHUD ? this.width - 8 : this.width / 2 + 2, this.compact ? 10 : this.y + this.height - 24 - (this.y + this.height - 38));
        this.mainEnableDisableButton.yOffset = this.yOffset;
        this.mainEnableDisableButton.setSmallIcon(this.compact);
        this.mainEnableDisableButton.handleDrawElement(mouseX, n2, partialTicks);

        this.optionsButton.setDimensions(this.x + (this.compact ? 102 : 4), this.y + (this.compact ? 3 : this.height - 20), this.compact ? 10 : this.width - 8, this.compact ? 10 : 16);
        this.optionsButton.setSmallIcon(this.compact);
        this.optionsButton.yOffset = this.yOffset;
        this.optionsButton.handleDrawElement(mouseX, n2, partialTicks);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.optionsButton.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).resetColor = false;
            ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).scrollableElement = this.scrollableElement;
            ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).module = this.module;
            HudLayoutEditorGui.instance.currentScrollableElement = HudLayoutEditorGui.instance.settingsElement;
        } else if (!this.module.notRenderHUD && this.secondaryEnableDisableButton.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.module.setWasRenderHud(true);
            this.module.setState(!this.module.isEnabled());
            this.secondaryEnableDisableButton.optionString = this.module.isEnabled() ? "Disable" : "Enable";
            this.secondaryEnableDisableButton.color = this.module.isEnabled() ? -5756117 : -13916106;
            if (this.module.isEnabled()) {
                this.applyDefaultColor();
                this.module.setState(true);
            }
        } else if (this.mainEnableDisableButton.moduleAllowedToAddToHud && this.mainEnableDisableButton.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            if (!this.module.isEnabled()) {
                this.module.setRenderHud(true);
                this.applyDefaultColor();
                if (this.module.getGuiAnchor() != null && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || !this.module.isWasRenderHud())) {
                    Minecraft.getMinecraft().displayGuiScreen(new ModulePlaceGui(HudLayoutEditorGui.instance, this.module));
                } else {
                    this.module.setState(true);
                }
            } else {
                this.module.setRenderHud(!this.module.isRenderHud());
                if (this.module.isRenderHud()) {
                    this.applyDefaultColor();
                    if (this.module.getGuiAnchor() != null && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || !this.module.isWasRenderHud())) {
                        Minecraft.getMinecraft().displayGuiScreen(new ModulePlaceGui(HudLayoutEditorGui.instance, this.module));
                    } else {
                        this.module.setState(true);
                    }
                } else if (this.module.notRenderHUD && this.module.isEnabled()) {
                    this.module.setState(false);
                }
            }
            this.module.setWasRenderHud(true);
            this.mainEnableDisableButton.optionString = this.module.getGuiAnchor() == null ? (this.module.isRenderHud() && this.module.isEnabled() ? "Disable" : "Enable") : (this.module.isRenderHud() && this.module.isEnabled() ? "Hide from HUD" : this.module.isWasRenderHud() && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? "Return to HUD" : "Add to HUD");
            this.mainEnableDisableButton.color = this.module.isRenderHud() && this.module.isEnabled() ? -5756117 : -13916106;
        }

        if (this.compact && this.isMouseInside(mouseX, mouseY) && !this.mainEnableDisableButton.isMouseInside(mouseX, mouseY) && !this.secondaryEnableDisableButton.isMouseInside(mouseX, mouseY) && !this.optionsButton.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.module.setWasRenderHud(true);
            this.module.setState(!this.module.isEnabled());

            if (this.module.isEnabled()) {
                this.applyDefaultColor();
                this.module.setState(true);
            }
        }
    }

    public void sendBackToModuleList(AbstractModule module) {
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).resetColor = false;
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).scrollableElement = this.scrollableElement;
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).module = module;
        (HudLayoutEditorGui.instance.settingsElement).scrollAmount = CheatBreaker.getInstance().getModuleManager().lastSettingScrollPos;
        HudLayoutEditorGui.instance.currentScrollableElement = HudLayoutEditorGui.instance.settingsElement;
    }

    private void applyDefaultColor() {
        if (this.module == CheatBreaker.getInstance().getModuleManager().directionHUDMod
                || this.module == CheatBreaker.getInstance().getModuleManager().playerListMod
                || this.module == CheatBreaker.getInstance().getModuleManager().enchantmentGlintMod
                || this.module == CheatBreaker.getInstance().getModuleManager().packTweaksMod
                || this.module == CheatBreaker.getInstance().getModuleManager().blockOverlayMod
                || this.module == CheatBreaker.getInstance().getModuleManager().hitColorMod
                || this.module == CheatBreaker.getInstance().getModuleManager().crosshairMod
                || this.module == CheatBreaker.getInstance().getModuleManager().scoreboardMod
                || !(Boolean) CheatBreaker.getInstance().getGlobalSettings().resetColors.getValue()) {
            return;
        }
        for (Setting setting : this.module.getSettingsList()) {
            if (setting.getType() != SettingType.INTEGER || !setting.getSettingName().toLowerCase().contains("color") || setting.getSettingName().toLowerCase().contains("background") || setting.getSettingName().toLowerCase().contains("border") || setting.getSettingName().toLowerCase().contains("pressed") || setting.getSettingName().toLowerCase().contains("amount") || setting.getSettingName().toLowerCase().contains("line"))
                continue;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            setting.setValue(CheatBreaker.getInstance().getGlobalSettings().defaultColor.getColorValue());
        }
    }
}
