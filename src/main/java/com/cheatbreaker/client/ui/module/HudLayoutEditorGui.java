package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.ScreenLocation;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.element.module.ModuleListElement;
import com.cheatbreaker.client.ui.element.module.ModulePreviewContainer;
import com.cheatbreaker.client.ui.element.module.ModulesGuiButtonElement;
import com.cheatbreaker.client.ui.element.profile.ProfilesListElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import com.cheatbreaker.client.util.manager.DebugInfoHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HudLayoutEditorGui extends GuiScreen {
    public static HudLayoutEditorGui instance;
    private final ResourceLocation settingsIcon = new ResourceLocation("client/icons/cog-64.png");
    private final ResourceLocation deleteIcon = new ResourceLocation("client/icons/delete-64.png");
    private final List<ModulePosition> positions = new ArrayList<>();
    private final List<AbstractScrollableElement> guiElements = new ArrayList<>();
    private final List<ModulesGuiButtonElement> buttons = new ArrayList<>();
    private List<AbstractModule> modules;
    private ModulesGuiButtonElement showGuidesButton;
    public ModulesGuiButtonElement helpButton;
    private ModulesGuiButtonElement bugReportButton;
    @Deprecated
    protected AbstractScrollableElement lastMouseEvent;
    @Deprecated
    protected AbstractScrollableElement field_146298_h;
    public AbstractScrollableElement settingsElement;
    public AbstractScrollableElement profilesElement;
    public AbstractScrollableElement modulesElement;
    protected AbstractScrollableElement staffModulesElement;
    protected AbstractScrollableElement allElements = null;
    public AbstractScrollableElement currentScrollableElement = null;
    private static AbstractModule draggingModule;
    private boolean IlIlIIIlllllIIIlIlIlIllII = false;
    private float mouseX2;
    private float mouseY2;
    private List<ModuleActionData> undoList;
    private List<ModuleActionData> redo;
    private int mouseX;
    private int mouseY;
    private boolean showModSizeOutline = false;
    private ModuleDataHolder dataHolder;
    private boolean isBugReportOpen = false;
    public static boolean allMenusClosed = false;
    private int arrowKeyMoves;
    private GuiTextField bugReportTextField;
    private float animationPhase = 0;
    private final boolean notExperimentalBuild = !CheatBreaker.getInstance().getGitBuildVersion().equals("Production") && !CheatBreaker.getInstance().getGitBuildVersion().equals("Preview");

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.entityRenderer.stopUseShader();
    }

    @Override
    public void initGui() {
        this.blurGui();
        Keyboard.enableRepeatEvents(true);
        this.modules = new ArrayList<>();
        this.modules.addAll(CheatBreaker.getInstance().getModuleManager().playerMods);
        this.undoList = new ArrayList<>();
        this.redo = new ArrayList<>();
        this.mouseX = -1;
        this.mouseY = -1;
        this.arrowKeyMoves = 0;
        instance = this;
        draggingModule = null;
        this.allElements = null;
        this.currentScrollableElement = null;
        this.dataHolder = null;
        allMenusClosed = false;
        float scale = 1.0f / CheatBreaker.getScaleFactor();
        int scaledWidth = (int)((float)this.width / scale);
        int scaledHeight = (int)((float)this.height / scale);
        this.guiElements.clear();
        this.buttons.clear();
        List<AbstractModule> moduleList = CheatBreaker.getInstance().getModuleManager().playerMods;
        List<StaffMod> staffModList = CheatBreaker.getInstance().getModuleManager().staffMods;
        this.modulesElement = new ModulePreviewContainer(scale, scaledWidth / 2 - 565, scaledHeight / 2 + 14, 370, scaledHeight / 2 - 35);
        this.guiElements.add(this.modulesElement);

        this.staffModulesElement = new ModuleListElement(staffModList, scale, scaledWidth / 2 + 195, scaledHeight / 2 + 14, 370, scaledHeight / 2 - 35);
        this.guiElements.add(this.staffModulesElement);
        this.settingsElement = new ModuleListElement(moduleList, scale, scaledWidth / 2 + 195, scaledHeight / 2 + 14, 370, scaledHeight / 2 - 35);
        this.guiElements.add(this.settingsElement);
        this.profilesElement = new ProfilesListElement(scale, scaledWidth / 2 - 565, scaledHeight / 2 + 14, 370, scaledHeight / 2 - 35);
        this.guiElements.add(this.profilesElement);
        this.showGuidesButton = new ModulesGuiButtonElement(null, "eye-64.png", 4, scaledHeight - 32, 28, 28, -12418828, scale);
        this.helpButton = new ModulesGuiButtonElement(null, "?", 36, scaledHeight - 32, 28, 28, -12418828, scale);
        this.bugReportButton = new ModulesGuiButtonElement(null, "Bug report", 68, scaledHeight - 32, 140, 28, -12418828, scale);
        this.bugReportTextField = new GuiTextField(this.mc.fontRenderer, 68, scaledHeight - 58, 140, 20);
        if (CheatBreaker.getInstance().getModuleManager().isUsingStaffModules()) {
            this.buttons.add(new ModulesGuiButtonElement(this.staffModulesElement, "Staff Mods", scaledWidth / 2 - 50, scaledHeight / 2 - 44, 100, 20, -9442858, scale));
        }
        this.buttons.add(new ModulesGuiButtonElement(this.modulesElement, "Mods", scaledWidth / 2 - 50, scaledHeight / 2 - 19, 100, 28, -13916106, scale));
        this.buttons.add(new ModulesGuiButtonElement(this.settingsElement, "cog-64.png", scaledWidth / 2 + 54, scaledHeight / 2 - 19, 28, 28, -12418828, scale));
        this.buttons.add(new ModulesGuiButtonElement(this.profilesElement, "profiles-64.png", scaledWidth / 2 - 82, scaledHeight / 2 - 19, 28, 28, -12418828, scale));
        allMenusClosed = false;
        this.allElements = null;
        this.animationPhase = 5;
    }

    @Override
    public void updateScreen() {
        float f = 1.0f / CheatBreaker.getScaleFactor();
        int n = (int)((float)this.width / f);
        int n2 = (int)((float)this.height / f);
        this.setDirection(n);
        if (this.isBugReportOpen) {
            this.bugReportTextField.updateCursorCounter();
        }
        if (!this.positions.isEmpty()) {
            boolean leftKeyPressed = Keyboard.isKeyDown(203);
            boolean rightKeyPressed = Keyboard.isKeyDown(205);
            boolean downKeyPressed = Keyboard.isKeyDown(200);
            boolean upKeyPressed = Keyboard.isKeyDown(208);
            if (leftKeyPressed || rightKeyPressed || downKeyPressed || upKeyPressed) {
                ++this.arrowKeyMoves;
                if (this.arrowKeyMoves > 10) {
                    for (ModulePosition position : this.positions) {
                        AbstractModule module = position.module;
                        if (module == null) continue;
                        if (leftKeyPressed) {
                            module.setTranslations((int)module.getXTranslation() - 1, (float)((int)module.getYTranslation()));
                            continue;
                        }
                        if (rightKeyPressed) {
                            module.setTranslations((int)module.getXTranslation() + 1, (float)((int)module.getYTranslation()));
                            continue;
                        }
                        if (downKeyPressed) {
                            module.setTranslations((int)module.getXTranslation(), (float)((int)module.getYTranslation() - 1));
                            continue;
                        }
                        if (upKeyPressed) {
                            module.setTranslations((int)module.getXTranslation(), (float)((int)module.getYTranslation() + 1));
                        }

                    }
                }
            }
        }
        float f2 = this.animationPhase > 30 ? 2.0f + this.animationPhase / 2.0f : (float)4;
        this.animationPhase = this.animationPhase + f2 >= (float)255 ? 255 : (int)(this.animationPhase + f2);
    }

    private float lIIIIlIIllIIlIIlIIIlIIllI(Rectangle rectangle, Rectangle rectangle2) {
        float f = Math.max(Math.abs(rectangle.x - rectangle2.x) - rectangle2.width / 2, 0);
        float f2 = Math.max(Math.abs(rectangle.y - rectangle2.y) - rectangle2.height / 2, 0);
        return f * f + f2 * f2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float f2;
        float f3;
        Rectangle object;
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderBlur();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        float scale = 1.0f / CheatBreaker.getScaleFactor();
        if (draggingModule != null) {
            if (CheatBreaker.getInstance().getGlobalSettings().showDebugOutput.getBooleanValue()) {
                this.debugOutputHud(scaledResolution);
            }
            if (!Mouse.isButtonDown(1)) {
                RenderUtil.drawRoundedRect(2.0, 2.0, 2.5D, this.height - 2, 0.0, -15599126);
                RenderUtil.drawRoundedRect((float)this.width - 2.5F, 2.0, this.width - 2, this.height - 2, 0.0, -15599126);
                RenderUtil.drawRoundedRect(2.0, 2, this.width - 2,  2.5, 0.0, -15599126);
                RenderUtil.drawRoundedRect(2.0, (float)this.height - 2.5F, this.width - 2, this.height - 2, 0.0, -15599126);
            }
            // Temporarily Try-Catching this until me or someone else figures out how to fix this *without* the need to reset profiles - Tellinq
            try {
                this.modules.sort((module, module1) -> {
                    if (module == draggingModule || module1 == draggingModule || module.getGuiAnchor() == null || module1.getGuiAnchor() == null) {
                        return 0;
                    }
                    float[] arrf = module.getScaledPoints(scaledResolution, true);
                    float[] arrf2 = module1.getScaledPoints(scaledResolution, true);
                    float[] arrf3 = draggingModule.getScaledPoints(scaledResolution, true);
                    Rectangle rectangle = new Rectangle((int)(arrf[0] * module.masterScale()), (int)(arrf[1] * module.masterScale()), (int)(module.height * module.masterScale()), (int)(module.width * module.masterScale()));
                    Rectangle rectangle2 = new Rectangle((int)(arrf2[0] * module1.masterScale()), (int)(arrf2[1] * module1.masterScale()), (int)(module1.height * module1.masterScale()), (int)(module1.width * module1.masterScale()));
                    Rectangle rectangle3 = new Rectangle((int)(arrf3[0] * HudLayoutEditorGui.draggingModule.masterScale()), (int)(arrf3[1] * HudLayoutEditorGui.draggingModule.masterScale()), (int)(HudLayoutEditorGui.draggingModule.width * HudLayoutEditorGui.draggingModule.masterScale()), (int)(HudLayoutEditorGui.draggingModule.height * HudLayoutEditorGui.draggingModule.masterScale()));
                    try {
                        if (this.lIIIIlIIllIIlIIlIIIlIIllI(rectangle, rectangle3) > this.lIIIIlIIllIIlIIlIIIlIIllI(rectangle2, rectangle3)) {
                            return -1;
                        }
                        return 1;
                    } catch (Exception exception) {
                        return 0;
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ModulePosition modulePosition = this.getSelectedModules(draggingModule);
            if (modulePosition != null) {
                this.positions.remove(modulePosition);
                this.positions.add(modulePosition);
            }
            for (ModulePosition position : this.positions) {
                this.dragModule(position, mouseX, mouseY, scaledResolution);
                if (!(Boolean) CheatBreaker.getInstance().getGlobalSettings().snapModules.getValue() || !this.IlIlIIIlllllIIIlIlIlIllII || Mouse.isButtonDown(1) || position.module != draggingModule) continue;
                for (AbstractModule module : this.modules) {
                    float[] positionScaledPoints = position.module.getScaledPoints(scaledResolution, true);
                    float centerVerticalSnap = this.height / 2 - (positionScaledPoints[1] + position.module.height / 2) * position.module.masterScale();
                    float centerHorizontalSnap = this.width / 2 - (positionScaledPoints[0] + position.module.width / 2) * position.module.masterScale();
                    float snappingStrength = (float)CheatBreaker.getInstance().getGlobalSettings().snappingStrength.getValue();
                    if (centerHorizontalSnap >= (float)(-snappingStrength) && centerHorizontalSnap <= (float)snappingStrength) {
                        this.snapHorizontalPosition(centerHorizontalSnap);
                    }
                    if (centerVerticalSnap >= (float)(-snappingStrength) && centerVerticalSnap <= (float)snappingStrength) {
                        this.snapVerticalPosition(centerVerticalSnap);
                    }
                    if (this.getSelectedModules(module) != null || module.getGuiAnchor() == null || !module.isRenderHud() || !module.isEnabled() || module.getName().contains("Zans") && CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().hide) continue;
                    float[] moduleScaledPoints = module.getScaledPoints(scaledResolution, true);
                    boolean bl = true;
                    boolean bl2 = true;
                    float f6 = moduleScaledPoints[0] * module.masterScale() - positionScaledPoints[0] * position.module.masterScale();
                    float f7 = (moduleScaledPoints[0] + module.width) * module.masterScale() - (positionScaledPoints[0] + position.module.width) * position.module.masterScale();
                    float f8 = (moduleScaledPoints[0] + module.width) * module.masterScale() - positionScaledPoints[0] * position.module.masterScale();
                    float f9 = moduleScaledPoints[0] * module.masterScale() - (positionScaledPoints[0] + position.module.width) * position.module.masterScale();
                    float f10 = moduleScaledPoints[1] * module.masterScale() - positionScaledPoints[1] * position.module.masterScale();
                    f3 = (moduleScaledPoints[1] + module.height) * module.masterScale() - (positionScaledPoints[1] + position.module.height) * position.module.masterScale();
                    f2 = (moduleScaledPoints[1] + module.height) * module.masterScale() - positionScaledPoints[1] * position.module.masterScale();
                    float f11 = moduleScaledPoints[1] * module.masterScale() - (positionScaledPoints[1] + position.module.height) * position.module.masterScale();

                    if (f6 >= (float)(-snappingStrength) && f6 <= (float)snappingStrength) {
                        bl = false;
                        this.snapHorizontalPosition(f6);
                    }
                    if (f7 >= (float)(-snappingStrength) && f7 <= (float)snappingStrength && bl) {
                        bl = false;
                        this.snapHorizontalPosition(f7);
                    }
                    if (f9 >= (float)(-snappingStrength) && f9 <= (float)snappingStrength && bl) {
                        bl = false;
                        this.snapHorizontalPosition(f9);
                    }
                    if (f8 >= (float)(-snappingStrength) && f8 <= (float)snappingStrength && bl) {
                        this.snapHorizontalPosition(f8);
                    }
                    if (f10 >= (float)(-snappingStrength) && f10 <= (float)snappingStrength) {
                        bl2 = false;
                        this.snapVerticalPosition(f10);
                    }
                    if (f3 >= (float)(-snappingStrength) && f3 <= (float)snappingStrength && bl2) {
                        bl2 = false;
                        this.snapVerticalPosition(f3);
                    }
                    if (f11 >= (float)(-snappingStrength) && f11 <= (float)snappingStrength && bl2) {
                        bl2 = false;
                        this.snapVerticalPosition(f11);
                    }
                    if (!(f2 >= (float)(-snappingStrength)) || !(f2 <= (float)snappingStrength) || !bl2) continue;
                    this.snapVerticalPosition(f2);
                }
            }
        } else if (this.dataHolder != null) {
            float f12 = 1.0f;
            int n4;
            switch (this.dataHolder.screenLocation) {
                case RIGHT_BOTTOM:
                    n4 = mouseY - this.dataHolder.mouseY + (mouseX - this.dataHolder.mouseX);
                    f12 = this.dataHolder.scale - (float)n4 / (float)115;
                    break;
                case LEFT_TOP:
                    n4 = mouseY - this.dataHolder.mouseY + (mouseX - this.dataHolder.mouseX);
                    f12 = this.dataHolder.scale + (float)n4 / (float)115;
                    break;
                case RIGHT_TOP:
                    n4 = mouseX - this.dataHolder.mouseX - (mouseY - this.dataHolder.mouseY);
                    f12 = this.dataHolder.scale - (float)n4 / (float)115;
                    break;
                case LEFT_BOTTOM:
                    n4 = mouseX - this.dataHolder.mouseX - (mouseY - this.dataHolder.mouseY);
                    f12 = this.dataHolder.scale + (float)n4 / (float)115;
            }
            if (f12 >= 1.0421053f * 0.47979796f && f12 <= 1.8962264f * 0.7910448f) {
                this.dataHolder.module.scale.setValue((float) ((double) Math.round((double) f12 * (double) 100) / (double) 100));
            }
        }
        this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution);
        boolean bl = true;
        for (AbstractModule module : this.modules) {
            boolean bl3 = this.lIIIIlIIllIIlIIlIIIlIIllI(scale, module, scaledResolution, mouseX, mouseY, bl);
            if (bl3) continue;
            bl = false;
        }
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        int n5 = (int)((float)this.width / scale);
        int n6 = (int)((float)this.height / scale);
        this.showGuidesButton.handleDrawElement(mouseX, mouseY, partialTicks);
        this.helpButton.handleDrawElement(mouseX, mouseY, partialTicks);
        if (notExperimentalBuild) this.bugReportButton.handleDrawElement(mouseX, mouseY, partialTicks);
        if (this.isBugReportOpen) {
            this.mc.fontRenderer.drawString("Bug Description (Press ENTER to send)", 39, n6 - 70, -1);
            this.bugReportTextField.setMaxStringLength(180);
            this.bugReportTextField.drawTextBox();
        }
        float f13 = (this.animationPhase * 8) / (float)255;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, f13);
        int n7 = 0xFFFFFF;
        if (f13 / (float)4 > 0.0f && f13 / (float)4 < 1.0f) {
            n7 = new Color(1.0f, 1.0f, 1.0f, f13 / (float)4).getRGB();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, f13);
        if (f13 > 1.0f) {
            GL11.glTranslatef(-((this.animationPhase * 2) - (float)32) / (float)12 - 1.0f, 0.0f, 0.0f);
        }
        RenderUtil.renderIcon(new ResourceLocation("client/logo_white.png"), (float)(n5 / 2 - 14), (float)(n6 / 2 - 47 - (CheatBreaker.getInstance().getModuleManager().isUsingStaffModules() ? 22 : 0)), (float)28, 15);
        if (f13 > 1.0f) {
            CheatBreaker.getInstance().playBold18px.drawString("| CHEAT", n5 / 2.0f + 18.0f, (float)(n6 / 2 - 45 - (CheatBreaker.getInstance().getModuleManager().isUsingStaffModules() ? 22 : 0)), n7);
            CheatBreaker.getInstance().playRegular18px.drawString("BREAKER", n5 / 2.0f + 53.0f, (float)(n6 / 2 - 45 - (CheatBreaker.getInstance().getModuleManager().isUsingStaffModules() ? 22 : 0)), n7);
        }
        GL11.glPopMatrix();
        for (ModulesGuiButtonElement modulesGuiButtonElement : this.buttons) {
            modulesGuiButtonElement.handleDrawElement(mouseX, mouseY, partialTicks);
        }
        if (draggingModule == null) {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtil.startScissorBox(n5 / 2 - 185, n6 / 2 + 15, n5 / 2 + 185, n6 - 20, (float)scaledResolution.getScaleFactor() * scale, n6);
            for (AbstractScrollableElement abstractScrollableElement : this.guiElements) {
                if (abstractScrollableElement != this.allElements && abstractScrollableElement != this.currentScrollableElement) continue;
                abstractScrollableElement.handleDrawElement(mouseX, mouseY, partialTicks);
            }
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        if (this.mouseX != -1) {
            if (Mouse.isButtonDown(0)) {
                if (this.mouseX != mouseX && this.mouseY != mouseY) {
                    HudLayoutEditorGui.drawRectWithOutline(this.mouseX, this.mouseY, mouseX, mouseY, 0.49f, -1358888961, 0x1F00FFFF);
                }
            } else {
                this.positions.clear();
                for (AbstractModule module : this.modules) {
                    int n8;
                    int n9;
                    int n10;
                    int n11;
                    Rectangle rectangle;
                    if (module.getGuiAnchor() == null || !module.isEnabled() || module.getName().contains("Zans") && CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().hide) continue;
                    float[] arrf = module.getScaledPoints(scaledResolution, true);
                    float f14 = scale / module.masterScale();
                    object = new Rectangle((int)(arrf[0] * module.masterScale() - 2.0f), (int)(arrf[1] * module.masterScale() - 2.0f), (int)(module.width * module.masterScale() + (float)4), (int)(module.height * module.masterScale() + (float)4));
                    if (!object.intersects(rectangle = new Rectangle(n11 = Math.min(this.mouseX, mouseX), n10 = Math.min(this.mouseY, mouseY), n9 = Math.max(this.mouseX, mouseX) - n11, n8 = Math.max(this.mouseY, mouseY) - n10))) continue;
                    f3 = (float) mouseX - module.getXTranslation();
                    f2 = (float) mouseY - module.getYTranslation();
                    this.positions.add(new ModulePosition(module, f3, f2));
                }
                this.mouseX = -1;
                this.mouseY = -1;
            }
        }
        if (this.helpButton.isMouseInside(mouseX, mouseY) && (this.allElements == null || !this.allElements.isMouseInside(mouseX, mouseY))) {
            this.drawShortcutHints(scale);
        }
    }

    private void drawShortcutHints(float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(4, (float)this.height - 245.0f * scale, 0.0f);
        GL11.glScalef(scale, scale, scale);
        Gui.drawRect(0.0f, 0.0f, 240, 200, -1895825408);
        CheatBreaker.getInstance().ubuntuMedium16px.drawString("Shortcuts & Movement", 4, 2.0f, -1);
        Gui.drawRect(4, 12, 234, 2.5815217f * 4.8421054f, 0x4FFFFFFF);
        int n = 16;
        String ctrlOrCmd = Minecraft.isRunningOnMac ? "CMD" : "CTRL";
        int ctrlOrCmdPos = Minecraft.isRunningOnMac ? 2 : 0;
        this.drawShortcutString("Mouse1", 6, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| " + EnumChatFormatting.AQUA + "HOLD" + EnumChatFormatting.RESET + " Add mods to selected region", 80, (float)n, -1);
        this.drawShortcutString("Mouse1", 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("| " + EnumChatFormatting.AQUA + "HOLD" + EnumChatFormatting.RESET + " Select & drag mods", 80, (float)n, -1);
        this.drawShortcutString("Mouse2", 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("| " + EnumChatFormatting.AQUA + "CLICK" + EnumChatFormatting.RESET + " Reset mod to closest position", 80, (float)n, -1);
        this.drawShortcutString("Mouse2", 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("| " + EnumChatFormatting.AQUA + "HOLD" + EnumChatFormatting.RESET + " Don't lock mods while dragging", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("Mouse1", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Toggle (multiple) mod selection", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("A", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Select all mods", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("X", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Remove selected mod(s) from HUD", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("R", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Reset selected mod(s) scale", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("Z", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Undo mod movements", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("Y", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Redo mod movements", 80, (float)n, -1);
        this.drawShortcutString("SHIFT", 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 32, (float)n, -1);
        this.drawShortcutString("Arrows", 38, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Smaller mod movements", 80, (float)n, -1);
        this.drawShortcutString(ctrlOrCmd, 6, n += 12);
        CheatBreaker.getInstance().playRegular14px.drawString("+", 30 - ctrlOrCmdPos, (float)n, -1);
        this.drawShortcutString("Arrows", 36 - ctrlOrCmdPos, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Larger mod movements", 80, (float)n, -1);
        n = 172;
        this.drawShortcutString("Up", 31, n);
        this.drawShortcutString("Left", 6, n += 12);
        this.drawShortcutString("Down", 26, n);
        this.drawShortcutString("Right", 51, n);
        CheatBreaker.getInstance().playRegular14px.drawString("| Move selected mod(s) with precision", 80, (float)n, -1);
        GL11.glPopMatrix();
    }

    private void drawShortcutString(String string, int x, int y) {
        CBFontRenderer font = CheatBreaker.getInstance().playRegular14px;
        float width = font.getStringWidth(string);
        RenderUtil.drawRoundedRect(x, y, (float)x + width + 4.0f, y + 10, 2, -1073741825);
        font.drawString(string, x + 2, (float)y, -16777216);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        if (this.allElements != null && this.allElements.isMouseInside(mouseX, mouseY)) {
            this.allElements.handleMouseClick(mouseX, mouseY, mouseButton);
            //System.out.println("mouseX=" + mouseX + " mouseY=" + mouseY + " mouseButton=" + mouseButton);
        } else {
            AbstractModule module1 = this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution, mouseX, mouseY);
            if (!(draggingModule != null && this.IlIlIIIlllllIIIlIlIlIllII || module1 == null)) {
                float[] scaledPoints = module1.getScaledPoints(scaledResolution, true);
                boolean isModSizeSmall = module1.width < 22 || module1.height < 8;
                boolean hoveringSettingsIcon = !isModSizeSmall && module1.isEnabled() && !module1.getSettingsList().isEmpty() &&
                        (float)mouseX >= scaledPoints[0] * module1.masterScale() &&
                        (float)mouseX <= (scaledPoints[0] + 10.0F) * module1.masterScale() &&
                        (float)mouseY >= (scaledPoints[1] + module1.height - 10.0F) * module1.masterScale() &&
                        (float)mouseY <= (scaledPoints[1] + module1.height + 2.0f) * module1.masterScale();
                boolean hoveringDeleteIcon = !isModSizeSmall && module1.isEnabled() &&
                        (float)mouseX > (scaledPoints[0] + module1.width - 10.0F) * module1.masterScale() &&
                        (float)mouseX < (scaledPoints[0] + module1.width + 2.0f) * module1.masterScale() &&
                        (float)mouseY > (scaledPoints[1] + module1.height - 10.0F) * module1.masterScale() &&
                        (float)mouseY < (scaledPoints[1] + module1.height + 2.0f) * module1.masterScale();
                boolean hoveringSettingsShiftedIcon = isModSizeSmall && module1.isEnabled() && !module1.getSettingsList().isEmpty() &&
                        (float)mouseX >= (scaledPoints[0] + module1.width / 2 - 10.0f) * module1.masterScale() &&
                        (float)mouseX <= (scaledPoints[0] + module1.width / 2 - 2.0F) * module1.masterScale() &&
                        (float)mouseY >= (scaledPoints[1] + module1.height + 2.0F) * module1.masterScale() &&
                        (float)mouseY <= (scaledPoints[1] + module1.height + 12.0f) * module1.masterScale();
                boolean hoveringDeleteShiftedIcon = isModSizeSmall && module1.isEnabled() &&
                        (float)mouseX > (scaledPoints[0] + module1.width / 2 + 0.0F) * module1.masterScale() &&
                        (float)mouseX < (scaledPoints[0] + module1.width / 2 + 8.0f) * module1.masterScale() &&
                        (float)mouseY > (scaledPoints[1] + module1.height + 2.0F) * module1.masterScale() &&
                        (float)mouseY < (scaledPoints[1] + module1.height + 12.0f) * module1.masterScale();
                if (hoveringSettingsIcon || hoveringSettingsShiftedIcon) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    ((ModuleListElement)this.settingsElement).resetColor = false;
                    ((ModuleListElement)this.settingsElement).module = module1;
                    this.currentScrollableElement = this.settingsElement;
                } else if (hoveringDeleteIcon || hoveringDeleteShiftedIcon) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    module1.setState(false);
                }
                if (!isModSizeSmall) {
                    return;
                }

            }
            for (AbstractModule module : this.modules) {
                GuiAnchor anchor;
                ScreenLocation screenLocation;
                if (module.getGuiAnchor() == null || !module.isEnabled() || module == CheatBreaker.getInstance().getModuleManager().miniMapMod) continue;
                float[] arrf = module.getScaledPoints(scaledResolution, true);
                boolean bl4 = (float)mouseX > arrf[0] * module.masterScale() && (float)mouseX < (arrf[0] + module.width) * module.masterScale() && (float)mouseY > arrf[1] * module.masterScale() && (float)mouseY < (arrf[1] + module.height) * module.masterScale();
                boolean bl5 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.LEFT_BOTTOM || !bl4 && (float)mouseX >= (arrf[0] + module.width - (float)5) * module.masterScale() && (float)mouseX <= (arrf[0] + module.width + (float)5) * module.masterScale() && (float)mouseY >= (arrf[1] - (float)5) * module.masterScale() && (float)mouseY <= (arrf[1] + (float)5) * module.masterScale();
                boolean bl6 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.RIGHT_TOP || !bl4 && (float)mouseX >= (arrf[0] - (float)5) * module.masterScale() && (float)mouseX <= (arrf[0] + (float)5) * module.masterScale() && (float)mouseY >= (arrf[1] + module.height - (float)5) * module.masterScale() && (float)mouseY <= (arrf[1] + module.height + (float)5) * module.masterScale();
                boolean bl7 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.RIGHT_BOTTOM || !bl4 && (float)mouseX >= (arrf[0] - (float)5) * module.masterScale() && (float)mouseX <= (arrf[0] + (float)5) * module.masterScale() && (float)mouseY >= (arrf[1] - (float)5) * module.masterScale() && (float)mouseY <= (arrf[1] + (float)5) * module.masterScale();
                boolean bl = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.LEFT_TOP || !bl4 && (float)mouseX >= (arrf[0] + module.width - (float)5) * module.masterScale() && (float)mouseX <= (arrf[0] + module.width + (float)5) * module.masterScale() && (float)mouseY >= (arrf[1] + module.height - (float)5) * module.masterScale() && (float)mouseY <= (arrf[1] + module.height + (float)5) * module.masterScale();
                if (this.mouseX != -1 || !bl5 && !bl6 && !bl7 && !bl) continue;
                if (bl5) {
                    screenLocation = ScreenLocation.LEFT_BOTTOM;
                    anchor = GuiAnchor.LEFT_BOTTOM;
                } else if (bl6) {
                    screenLocation = ScreenLocation.RIGHT_TOP;
                    anchor = GuiAnchor.RIGHT_TOP;
                } else if (bl7) {
                    screenLocation = ScreenLocation.RIGHT_BOTTOM;
                    anchor = GuiAnchor.RIGHT_BOTTOM;
                } else {
                    screenLocation = ScreenLocation.LEFT_TOP;
                    anchor = GuiAnchor.LEFT_TOP;
                }

                if (this.lIIIIIIIIIlIllIIllIlIIlIl(scaledResolution, mouseX, mouseY)) continue;
                if (mouseButton == 0) {
                    this.undoList.add(new ModuleActionData(this, this.positions));
                    this.dataHolder = new ModuleDataHolder(this, module, screenLocation, mouseX, mouseY);
                    this.lIIIIlIIllIIlIIlIIIlIIllI(module, anchor, mouseX, mouseY, scaledResolution);
                } else if (mouseButton == 1) {
                    GuiAnchor cBGuiAnchor2 = module.getGuiAnchor();
                    this.lIIIIlIIllIIlIIlIIIlIIllI(module, anchor, mouseX, mouseY, scaledResolution);
                    module.scale.setValue(1.0f);
                    this.lIIIIlIIllIIlIIlIIIlIIllI(module, cBGuiAnchor2, mouseX, mouseY, scaledResolution);
                }
                return;
            }
            if (draggingModule == null) {
                if (this.showGuidesButton.isMouseInside(mouseX, mouseY)) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    this.showModSizeOutline = !this.showModSizeOutline;
                } else if (this.bugReportButton.isMouseInside(mouseX, mouseY) && notExperimentalBuild) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
                    this.isBugReportOpen = !this.isBugReportOpen;
                    if (this.isBugReportOpen) {
                        this.bugReportTextField.setFocused(true);
                    }
                }
                this.handleMainButtonPress(mouseX, mouseY, mouseButton);
                this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution, mouseX, mouseY, mouseButton);
            }
            for (AbstractModulesGuiElement abstractModulesGuiElement : this.buttons) {
                if (!abstractModulesGuiElement.isMouseInside(mouseX, mouseY)) continue;
                return;
            }
            boolean bl = this.lIIIIIIIIIlIllIIllIlIIlIl(scaledResolution, mouseX, mouseY);
            if (bl) {
                return;
            }
            if (!this.positions.isEmpty()) {
                this.positions.clear();
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
        if (!this.positions.isEmpty()) {
            this.arrowKeyMoves = 0;
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int n = Mouse.getEventDWheel();
        if (this.allElements != null) {
            this.allElements.onScroll(n);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule module, GuiAnchor anchor, int n, int n2, ScaledResolution resolution) {
        if (anchor != module.getGuiAnchor()) {
            float[] arrf = module.getScaledPoints(resolution, true);
            module.setAnchor(anchor);
            float[] arrf2 = module.getScaledPoints(resolution, false);
            module.setTranslations(arrf[0] * module.masterScale() - arrf2[0] * module.masterScale(), arrf[1] * module.masterScale() - arrf2[1] * module.masterScale());
        }
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        if (this.dataHolder != null && partialTicks == 0) {
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.dataHolder.module, this.dataHolder.anchor, mouseX, mouseY, scaledResolution);
            this.dataHolder = null;
        }
        if (draggingModule != null && partialTicks == 0) {
            if (this.IlIlIIIlllllIIIlIlIlIllII) {
                for (ModulePosition position : this.positions) {
                    GuiAnchor anchor = AnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
                    if (anchor == GuiAnchor.MIDDLE_MIDDLE || anchor == position.module.getGuiAnchor() || !this.IlIlIIIlllllIIIlIlIlIllII) continue;
                    this.lIIIIlIIllIIlIIlIIIlIIllI(position.module, anchor, mouseX, mouseY, scaledResolution);
                    position.x = (float)mouseX - position.module.getXTranslation();
                    position.y = (float)mouseY - position.module.getYTranslation();
                }
                if (this.getSelectedModules(draggingModule) == null) {
                    Object object = draggingModule.getScaledPoints(scaledResolution, true);
                    float x = (float)mouseX - draggingModule.getXTranslation();
                    float y = (float)mouseY - draggingModule.getYTranslation();
                    this.positions.add(new ModulePosition(draggingModule, x, y));
                }

                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
            draggingModule = null;
        }
    }

    @Override
    public void keyTyped(char c, int n) {
        if (n == 1) {
            CheatBreaker.getInstance().configManager.write();
        }
        super.keyTyped(c, n);
        if (this.isBugReportOpen) {
            if (n == 28) {
                DebugInfoHandler.ReportThatBug(this.bugReportTextField.getText());
            }
            this.bugReportTextField.keyTyped(c, n);
        }
        if (n == 30 && HudLayoutEditorGui.isCtrlKeyDown()) {
            for (AbstractModule module : this.modules) {
                if (module.isEnabled() && module.getGuiAnchor() != null && this.getSelectedModules(module) == null) {
                    float f4 = (float)mouseX - module.getXTranslation() * module.masterScale();
                    float f5 = (float)mouseY - module.getYTranslation() * module.masterScale();
                    this.positions.add(new ModulePosition(module, f4, f5));
                }
            }
        }
        if (n == 45 && HudLayoutEditorGui.isCtrlKeyDown()) {
            for (AbstractModule module : this.modules) {
                if (this.getSelectedModules(module) != null) {
                    module.setState(false);
                }
            }
        }
        if (n == 19 && HudLayoutEditorGui.isCtrlKeyDown()) {
            for (AbstractModule module : this.modules) {
                if (this.getSelectedModules(module) != null) {
                    module.scale.setValue(1.0F);
                }
            }
        }

        if (n == 44 && HudLayoutEditorGui.isCtrlKeyDown()) {
            if (!this.undoList.isEmpty()) {
                int n2 = this.undoList.size() - 1;
                ModuleActionData moduleActionData = this.undoList.get(this.undoList.size() - 1);
                for (int i = 0; i < moduleActionData.moduleList.size(); ++i) {
                    AbstractModule abstractModule = moduleActionData.moduleList.get(i);
                    float f = moduleActionData.xTranslationList.get(i);
                    float f2 = moduleActionData.yTranslationList.get(i);
                    GuiAnchor cBGuiAnchor = moduleActionData.guiAnchorList.get(i);
                    Float f3 = (Float)moduleActionData.valueList.get(i);
                    abstractModule.setAnchor(cBGuiAnchor);
                    abstractModule.setTranslations(f, f2);
                    abstractModule.scale.setValue(f3);
                }
                if (this.redo.size() > 50) {
                    this.redo.remove(0);
                }
                this.redo.add(moduleActionData);
                this.undoList.remove(n2);
            }
        } else if (n == 21 && HudLayoutEditorGui.isCtrlKeyDown()) {
            if (!this.redo.isEmpty()) {
                int n3 = this.redo.size() - 1;
                ModuleActionData moduleActionData = this.redo.get(this.redo.size() - 1);
                for (int i = 0; i < moduleActionData.moduleList.size(); ++i) {
                    AbstractModule abstractModule = moduleActionData.moduleList.get(i);
                    float f = moduleActionData.xTranslationList.get(i);
                    float f4 = moduleActionData.yTranslationList.get(i);
                    GuiAnchor cBGuiAnchor = moduleActionData.guiAnchorList.get(i);
                    Float f5 = (Float)moduleActionData.valueList.get(i);
                    abstractModule.setAnchor(cBGuiAnchor);
                    abstractModule.setTranslations(f, f4);
                    abstractModule.scale.setValue(f5);
                }
                if (this.redo.size() > 50) {
                    this.redo.remove(0);
                }
                this.undoList.add(moduleActionData);
                this.redo.remove(n3);
            }
        } else {
            this.arrowKeyMoves = 0;
            for (ModulePosition cBModulePosition : this.positions) {
                AbstractModule abstractModule = cBModulePosition.module;
                if (abstractModule == null) continue;
                float amount = 1.0f;
                if (HudLayoutEditorGui.isCtrlKeyDown() && HudLayoutEditorGui.isShiftKeyDown()) {
                    amount = 1.0f / CheatBreaker.getScaleFactor() / 4;
                } else if (HudLayoutEditorGui.isCtrlKeyDown()) {
                    amount = 4.0f;
                } else if (HudLayoutEditorGui.isShiftKeyDown()) {
                    amount = 1.0f / CheatBreaker.getScaleFactor() / 2;
                }
                switch (n) {
                    case 203:
                        abstractModule.setTranslations(abstractModule.getXTranslation() - amount, abstractModule.getYTranslation());
                        break;
                    case 205:
                        abstractModule.setTranslations(abstractModule.getXTranslation() + amount, abstractModule.getYTranslation());
                        break;
                    case 200:
                        abstractModule.setTranslations(abstractModule.getXTranslation(), abstractModule.getYTranslation() - amount);
                        break;
                    case 208:
                        abstractModule.setTranslations(abstractModule.getXTranslation(), abstractModule.getYTranslation() + amount);
                }
            }
        }
    }

    private void snapHorizontalPosition(float currentPos) {
        for (ModulePosition position : this.positions) {
            position.module.setTranslations(position.module.getXTranslation() + currentPos, position.module.getYTranslation());
        }
    }

    private void snapVerticalPosition(float currentPos) {
        for (ModulePosition position : this.positions) {
            position.module.setTranslations(position.module.getXTranslation(), position.module.getYTranslation() + currentPos);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution, int mouseX, int mouseY, int n3) {
        for (AbstractModule module : this.modules) {
            float[] scaledPoints;
            if (module.getGuiAnchor() == null || !module.isEnabled() || module.getName().contains("Zans") && CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().hide) continue;
//            float f = module.width;
//            float f2 = module.height;
//            float f3 = 18;
//            if (f < f3) {
//                module.width = f3;
//            }
//            if (f2 < (float)18) {
//                module.height = 18;
//            }
            if (!((float)mouseX > (scaledPoints = module.getScaledPoints(scaledResolution, true))[0] * module.masterScale() &&
                    (float)mouseX < (scaledPoints[0] + module.width) * module.masterScale() &&
                    (float)mouseY > scaledPoints[1] * module.masterScale() &&
                    (float)mouseY < (scaledPoints[1] + module.height) * module.masterScale())) continue;
            //Mark
            boolean bl3 = !module.getSettingsList().isEmpty() &&
                    (float)mouseX >= scaledPoints[0] * module.masterScale() &&
                    (float)mouseX <= (scaledPoints[0] + (float)10) * module.masterScale() &&
                    (float)mouseY >= (scaledPoints[1] + module.height - (float)10) * module.masterScale() &&
                    (float)mouseY <= (scaledPoints[1] + module.height + 2.0f) * module.masterScale();
            boolean bl = (float)mouseX > (scaledPoints[0] + module.width - (float)10) * module.masterScale() &&
                    (float)mouseX < (scaledPoints[0] + module.width + 2.0f) * module.masterScale() &&
                    (float)mouseY > (scaledPoints[1] + module.height - (float)10) * module.masterScale() &&
                    (float)mouseY < (scaledPoints[1] + module.height + 2.0f) * module.masterScale();
            boolean bl57 = !module.getSettingsList().isEmpty() &&
                    (float)mouseX >= (scaledPoints[0] + module.width / 2 - 10.0f) * module.masterScale() &&
                    (float)mouseX <= (scaledPoints[0] + module.width / 2 - 2.0F) * module.masterScale() &&
                    (float)mouseY >= (scaledPoints[1] + module.height + 2.0F) * module.masterScale() &&
                    (float)mouseY <= (scaledPoints[1] + module.height + 12.0f) * module.masterScale();
            boolean bl4 =
                    (float)mouseX > (scaledPoints[0] + module.width / 2 + 0.0f) * module.masterScale() &&
                            (float)mouseX < (scaledPoints[0] + module.width / 2 + 8.0f) * module.masterScale() &&
                            (float)mouseY > (scaledPoints[1] + module.height + 2.0F) * module.masterScale() &&
                            (float)mouseY < (scaledPoints[1] + module.height + 12.0f) * module.masterScale();
            boolean isModSizeSmall = module.width < 22 || module.height < 8;
            boolean d = isModSizeSmall ? !bl57 && !bl4 : !bl3 && !bl;
            if (n3 == 0 && d && module != CheatBreaker.getInstance().getModuleManager().miniMapMod) {
                boolean bl5 = true;
                if (this.getSelectedModules(module) != null) {
                    this.removeModuleFromPositions(module);
                    bl5 = false;
                }
                float f4 = (float)mouseX - module.getXTranslation() * module.masterScale();
                float f5 = (float)mouseY - module.getYTranslation() * module.masterScale();
                this.mouseX2 = mouseX;
                this.mouseY2 = mouseY;
                this.IlIlIIIlllllIIIlIlIlIllII = false;
                draggingModule = module;
                if (this.getSelectedModules(module) == null) {
                    if (!HudLayoutEditorGui.isCtrlKeyDown() && bl5) {
                        this.positions.clear();
                    }
                    if (bl5 || !HudLayoutEditorGui.isCtrlKeyDown()) {
                        this.positions.add(new ModulePosition(module, f4, f5));
                    }
                }
                this.setSelectedModulesPosition(scaledResolution, mouseX, mouseY);
            }
            if (!(n3 != 0 || this.allElements != null && this.allElements.isMouseInside(mouseX, mouseY))) {
                if (isModSizeSmall ? bl57 : bl3) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    ((ModuleListElement)this.settingsElement).resetColor = false;
                    ((ModuleListElement)this.settingsElement).module = module;
                    this.currentScrollableElement = this.settingsElement;
                } else if (isModSizeSmall ? bl4 : bl) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    module.setState(false);
                }
            } else if (n3 == 1) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                this.undoList.add(new ModuleActionData(this, this.positions));
                float[] snapPositions = AnchorHelper.getSnapPositions(module.getGuiAnchor());
                if ((module == CheatBreaker.getInstance().getModuleManager().chatMod || module == CheatBreaker.getInstance().getModuleManager().playerListMod) && module.getGuiAnchor() == module.getDefaultGuiAnchor()) {
                    module.setTranslations(module.defaultXTranslation, module.defaultYTranslation);
                } else {
                    module.setTranslations(snapPositions[0], snapPositions[1]);
                }

            }
            if (module == CheatBreaker.getInstance().getModuleManager().miniMapMod) continue;
            break;
        }
    }

    private void handleMainButtonPress(int n, int n2, int n3) {
        for (ModulesGuiButtonElement button : this.buttons) {
            if (n3 != 0 || !button.isMouseInside(n, n2) || allMenusClosed) continue;
            if (button.scrollableElement != null && this.allElements != button.scrollableElement && this.currentScrollableElement == null) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                this.currentScrollableElement = button.scrollableElement;
                continue;
            }
            if (button.scrollableElement == null || this.currentScrollableElement != null) continue;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            allMenusClosed = true;
        }
    }

    private AbstractModule lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution, int n, int n2) {
        for (AbstractModule module : this.modules) {
            if (module.getGuiAnchor() == null) continue;
            float[] arrf = module.getScaledPoints(scaledResolution, true);
            boolean bl2 = !module.getSettingsList().isEmpty() &&
                    (float)n >= arrf[0] * module.masterScale() &&
                    (float)n <= (arrf[0] + (float)10) * module.masterScale() &&
                    (float)n2 >= (arrf[1] + module.height - (float)10) * module.masterScale() &&
                    (float)n2 <= (arrf[1] + module.height + 2.0f) * module.masterScale();
            boolean bl =
                    (float)n > (arrf[0] + module.width - (float)10) * module.masterScale() &&
                    (float)n < (arrf[0] + module.width + 2.0f) * module.masterScale() &&
                    (float)n2 > (arrf[1] + module.height - (float)10) * module.masterScale() &&
                    (float)n2 < (arrf[1] + module.height + 2.0f) * module.masterScale();
            boolean bl3 = !module.getSettingsList().isEmpty() &&
                    (float)n >= (arrf[0] + module.width / 2 - 10.0f) * module.masterScale() &&
                    (float)n <= (arrf[0] + module.width / 2 - 2.0F) * module.masterScale() &&
                    (float)n2 >= (arrf[1] + module.height + 2.0F) * module.masterScale() &&
                    (float)n2 <= (arrf[1] + module.height + 12.0f) * module.masterScale();
            boolean bl4 =
                    (float)n > (arrf[0] + module.width / 2 + 0.0f) * module.masterScale() &&
                            (float)n < (arrf[0] + module.width / 2 + 8.0f) * module.masterScale() &&
                            (float)n2 > (arrf[1] + module.height + 2.0F) * module.masterScale() &&
                            (float)n2 < (arrf[1] + module.height + 12.0f) * module.masterScale();
            if (!bl && !bl2 && !bl3 && !bl4) continue;
            return module;
        }
        return null;
    }

    private boolean lIIIIIIIIIlIllIIllIlIIlIl(ScaledResolution scaledResolution, int n, int n2) {
        boolean bl = false;
        for (AbstractModule abstractModule : this.modules) {
            if (abstractModule.getGuiAnchor() == null) continue;
            float[] arrf = abstractModule.getScaledPoints(scaledResolution, true);
            boolean bl2 = (float)n > arrf[0] * abstractModule.masterScale() && (float)n < (arrf[0] + abstractModule.width) * abstractModule.masterScale() && (float)n2 > arrf[1] * abstractModule.masterScale() && (float)n2 < (arrf[1] + abstractModule.height) * abstractModule.masterScale();
            bl = bl || bl2;
        }
        return bl;
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI(float scale, AbstractModule module, ScaledResolution scaledResolution, int mouseX, int mouseY, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6 = 0;
        float[] object;
        if (module.getGuiAnchor() == null || !module.isEnabled() || module == CheatBreaker.getInstance().getModuleManager().miniMapMod || !module.notRenderHUD && !module.isRenderHud()) {
            return true;
        }
        boolean bl3 = false;
//        float f2 = 18.0F;
//        if (module.width < f2) {
//            module.width = (int)f2;
//        }
//        if (module.height < 18.0F) {
//            module.height = 18;
//        }
        GL11.glPushMatrix();
        float[] arrf = module.getScaledPoints(scaledResolution, true);
        module.scaleAndTranslate(scaledResolution);
        boolean bl2 = this.mouseX != -1;
        if (bl2) {
            Rectangle rec = new Rectangle((int)(arrf[0] * module.masterScale() - 2.0f), (int)(arrf[1] * module.masterScale() - 2.0f), (int)(module.width * module.masterScale() + (float)4), (int)(module.height * module.masterScale() + (float)4));
            n6 = Math.min(this.mouseX, mouseX);
            n5 = Math.min(this.mouseY, mouseY);
            n4 = Math.max(this.mouseX, mouseX) - n6;
            n3 = Math.max(this.mouseY, mouseY) - n5;
            Rectangle rectangle = new Rectangle(n6, n5, n4, n3);
            bl2 = rec.intersects(rectangle);
        }
        object = module.getScaledPoints(scaledResolution, true);
        boolean isModSizeSmall = module.width < 22 || module.height < 8;
        boolean isHoveringOverMod =
                (float) mouseX > object[0] * module.masterScale() &&
                (float) mouseX < (object[0] + module.width) * module.masterScale() &&
                (float) mouseY > object[1] * module.masterScale() &&
                (float) mouseY < (object[1] + module.height) * module.masterScale();
        boolean hasHoveredOverMod =
                (float) mouseX > object[0] * module.masterScale() &&
                        (float) mouseX < (object[0] + module.width) * module.masterScale() &&
                        (float) mouseY > object[1] * module.masterScale() &&
                        (float) mouseY < (object[1] + module.height + 10.0F) * module.masterScale();
        if (!this.showModSizeOutline && !(this.mc.currentScreen instanceof ProfileCreatorGui)) {
            if (this.getSelectedModules(module) != null || bl2) {
                Gui.drawRectWithOutline(0.0f, 0.0f, module.width, module.height, 0.4f, 0x9F00FFFF, isHoveringOverMod ? 0x2aFFFFFF : 0x1AFFFFFF);
            } else {
                Gui.drawRectWithOutline(0.0f, 0.0f, module.width, module.height, 0.4f, isHoveringOverMod ? 0x80FFFFFF : 0x6FFFFFFF, isHoveringOverMod ? 0x3aFFFFFF : 0x1AFFFFFF);
            }
        }
        if (!this.showModSizeOutline && (isHoveringOverMod || isModSizeSmall && hasHoveredOverMod)) {
            n5 = !module.getSettingsList().isEmpty() &&
                    (float)mouseX >= (object[0] + 2.0f) * module.masterScale() &&
                    (float)mouseX <= (object[0] + (float)10) * module.masterScale() &&
                    (float)mouseY >= (object[1] + module.height - (float)8) * module.masterScale() &&
                    (float)mouseY <= (object[1] + module.height - 2.0f) * module.masterScale() ? 1 : 0;
            n4 = (float)mouseX > (object[0] + module.width - (float)10) * module.masterScale() &&
                    (float)mouseX < (object[0] + module.width - 2.0f) * module.masterScale() &&
                    (float)mouseY > (object[1] + module.height - (float)8) * module.masterScale() &&
                    (float)mouseY < (object[1] + module.height - 2.0f) * module.masterScale() ? 1 : 0;
            int n53 = !module.getSettingsList().isEmpty() &&
                    (float)mouseX >= (object[0] + module.width / 2 - 10.0f) * module.masterScale() &&
                    (float)mouseX <= (object[0] + module.width / 2 - 2.0F) * module.masterScale() &&
                    (float)mouseY >= (object[1] + module.height + 2.0F) * module.masterScale() &&
                    (float)mouseY <= (object[1] + module.height + 10.0f) * module.masterScale() ? 1 : 0;
            int n43 = (float)mouseX > (object[0] + module.width / 2 + 0.0F) * module.masterScale() &&
                    (float)mouseX < (object[0] + module.width / 2 + 8.0f) * module.masterScale() &&
                    (float)mouseY > (object[1] + module.height + 2.0F) * module.masterScale() &&
                    (float)mouseY < (object[1] + module.height + 10.0f) * module.masterScale() ? 1 : 0;
            float cogXPos = isModSizeSmall ? module.width / 2 - 7.0F : 2.0f;
            float deleteXPos = isModSizeSmall ? module.width / 2 + 1 : module.width - 8.0F;
            float yPos = isModSizeSmall ? module.height + 2.0F : module.height - 7.5f;
            if (!module.getSettingsList().isEmpty()) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, (isModSizeSmall ? n53 != 0 : n5 != 0) ? 1.0f : 0.6f);
                RenderUtil.renderIcon(this.settingsIcon, 3.0F, cogXPos, yPos);
            }
            GL11.glColor4f(0.8f, 0.2f, 0.2f, (isModSizeSmall ? n43 != 0 : n4 != 0) ? 1.0f : 0.6f);
            RenderUtil.renderIcon(this.deleteIcon, 3.0F, deleteXPos, yPos);
        }
        GL11.glPushMatrix();
        float f3 = scale / module.masterScale();
        GL11.glScalef(f3, f3, f3);
        if (bl) {
            n4 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.LEFT_BOTTOM || n6 == 0 && (float)mouseX >= (object[0] + module.width - (float)5) * module.masterScale() && (float)mouseX <= (object[0] + module.width + (float)5) * module.masterScale() && (float)mouseY >= (object[1] - (float)5) * module.masterScale() && (float)mouseY <= (object[1] + (float)5) * module.masterScale() ? 1 : 0;
            n3 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.RIGHT_TOP || n6 == 0 && (float)mouseX >= (object[0] - (float)5) * module.masterScale() && (float)mouseX <= (object[0] + (float)5) * module.masterScale() && (float)mouseY >= (object[1] + module.height - (float)5) * module.masterScale() && (float)mouseY <= (object[1] + module.height + (float)5) * module.masterScale() ? 1 : 0;
            boolean bl5 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.RIGHT_BOTTOM || n6 == 0 && (float)mouseX >= (object[0] - (float)5) * module.masterScale() && (float)mouseX <= (object[0] + (float)5) * module.masterScale() && (float)mouseY >= (object[1] - (float)5) * module.masterScale() && (float)mouseY <= (object[1] + (float)5) * module.masterScale();
            boolean bl6 = this.dataHolder != null && this.dataHolder.module == module && this.dataHolder.screenLocation == ScreenLocation.LEFT_TOP || n6 == 0 && (float)mouseX >= (object[0] + module.width - (float)5) * module.masterScale() && (float)mouseX <= (object[0] + module.width + (float)5) * module.masterScale() && (float)mouseY >= (object[1] + module.height - (float)5) * module.masterScale() && (float)mouseY <= (object[1] + module.height + (float)5) * module.masterScale();
            GL11.glPushMatrix();
            float f4 = 4;
            if (this.mouseX == -1 && bl5) {
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                Gui.drawRect(-f4 / 2.0f, -f4 / 2.0f, f4 / 2.0f, f4 / 2.0f, 0xFF00FF00);
            } else if (this.mouseX == -1 && n4 != 0) {
                GL11.glTranslatef(module.width / f3, 0.0f, 0.0f);
                Gui.drawRect(-f4 / 2.0f, -f4 / 2.0f, f4 / 2.0f, f4 / 2.0f, 0xFF00FF00);
            } else if (this.mouseX == -1 && bl6) {
                GL11.glTranslatef(module.width / f3, module.height / f3, 0.0f);
                Gui.drawRect(-f4 / 2.0f, -f4 / 2.0f, f4 / 2.0f, f4 / 2.0f, 0xFF00FF00);
            } else if (this.mouseX == -1 && n3 != 0) {
                GL11.glTranslatef(0.0f, module.height / f3, 0.0f);
                Gui.drawRect(-f4 / 2.0f, -f4 / 2.0f, f4 / 2.0f, f4 / 2.0f, 0xFF00FF00);
            }
            GL11.glPopMatrix();
            bl3 = this.mouseX == -1 && (bl5 || n4 != 0 || n3 != 0 || bl6);
        }
        n4 = arrf[1] - (float)CheatBreaker.getInstance().ubuntuMedium16px.getHeight() - (float)6 < 0.0f ? 1 : 0;
        float f5 = n4 != 0 ? module.height * module.masterScale() / scale : (float)(-CheatBreaker.getInstance().ubuntuMedium16px.getHeight() - 4);
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().showModName.getValue()) {
            String name = module.getName() + (module.hiddenFromHud ? EnumChatFormatting.GRAY + " (Hidden)" : "");
            switch (module.getPosition()) {
                case LEFT:
                    float f6 = 0.0f;
                    CheatBreaker.getInstance().ubuntuMedium16px.drawStringWithShadow(name, f6, f5, -1);
                    break;
                case CENTER:
                    float f7 = module.width * module.masterScale() / scale / 2.0f;
                    CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredStringWithShadow(name, f7, f5, -1);
                    break;
                case RIGHT:
                    float f8 = module.width * module.masterScale() / scale - (float)CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(name);
                    CheatBreaker.getInstance().ubuntuMedium16px.drawStringWithShadow(name, f8, f5, -1);
            }
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        return !bl3;
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution) {
        if (!Mouse.isButtonDown(1) && draggingModule != null) {
            for (ModulePosition position : this.positions) {
                if (position.module != draggingModule || !(Boolean) CheatBreaker.getInstance().getGlobalSettings().snapModules.getValue()) continue;
                Object var5_5 = null;
                for (AbstractModule abstractModule : this.modules) {
                    float[] positionScaledPoints = position.module.getScaledPoints(scaledResolution, true);
                    float centerHorizontalSnap = this.width / 2 - (positionScaledPoints[0] + position.module.width / 2) * position.module.masterScale();
                    float centerVerticalSnap = this.height / 2  - (positionScaledPoints[1] + position.module.height / 2) * position.module.masterScale();
                    float snappingStrength = (float)CheatBreaker.getInstance().getGlobalSettings().snappingStrength.getValue();
                    if (centerVerticalSnap >= (float)(-snappingStrength) && centerVerticalSnap <= (float)snappingStrength) {
                        RenderUtil.drawRoundedRect(0.0, this.height / 2 - 0.25f, this.width, this.height / 2 + 0.25f, 0.0, -3596854);
                    }
                    if (centerHorizontalSnap >= (float)(-snappingStrength) && centerHorizontalSnap <= (float)snappingStrength) {
                        RenderUtil.drawRoundedRect(this.width / 2 - 0.25f, 0.0, this.width / 2 + 0.25f, this.height, 0.0, -3596854);
                    }
                    if (this.getSelectedModules(abstractModule) != null || abstractModule.getGuiAnchor() == null || !abstractModule.isEnabled() || abstractModule == CheatBreaker.getInstance().getModuleManager().miniMapMod || !abstractModule.notRenderHUD && !abstractModule.isRenderHud() || var5_5 != null && var5_5 != abstractModule) continue;
                    float[] moduleScaledPoints = abstractModule.getScaledPoints(scaledResolution, true);
                    boolean highlightNeighborMods = false;
                    float f2 = moduleScaledPoints[0] * abstractModule.masterScale() - positionScaledPoints[0] * position.module.masterScale();
                    float f3 = (moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale() - (positionScaledPoints[0] + position.module.width) * position.module.masterScale();
                    float f4 = (moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale() - positionScaledPoints[0] * position.module.masterScale();
                    float f5 = moduleScaledPoints[0] * abstractModule.masterScale() - (positionScaledPoints[0] + position.module.width) * position.module.masterScale();
                    float f6 = moduleScaledPoints[1] * abstractModule.masterScale() - positionScaledPoints[1] * position.module.masterScale();
                    float f7 = (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale() - (positionScaledPoints[1] + position.module.height) * position.module.masterScale();
                    float f8 = (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale() - positionScaledPoints[1] * position.module.masterScale();
                    float f9 = moduleScaledPoints[1] * abstractModule.masterScale() - (positionScaledPoints[1] + position.module.height) * position.module.masterScale();
                    if (f2 >= (float)(-snappingStrength) && f2 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(moduleScaledPoints[0] * abstractModule.masterScale() - 0.6666667f * 0.75f, 0.0, moduleScaledPoints[0] * abstractModule.masterScale(), this.height, 0.0, -3596854);
                    }
                    if (f3 >= (float)(-snappingStrength) && f3 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect((moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale(), 0.0, (moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale() + 1.7272727f * 0.28947368f, this.height, 0.0, -3596854);
                    }
                    if (f5 >= (float)(-snappingStrength) && f5 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(moduleScaledPoints[0] * abstractModule.masterScale(), 0.0, moduleScaledPoints[0] * abstractModule.masterScale() + 0.29775283f * 1.6792452f, this.height, 0.0, 0xFFC91DCA);
                    }
                    if (f4 >= (float)(-snappingStrength) && f4 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect((moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale(), 0.0, (moduleScaledPoints[0] + abstractModule.width) * abstractModule.masterScale() + 1.5238096f * 0.328125f, this.height, 0.0, -3596854);
                    }
                    if (f6 >= (float)(-snappingStrength) && f6 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(0.0, moduleScaledPoints[1] * abstractModule.masterScale(), this.width, moduleScaledPoints[1] * abstractModule.masterScale() + 0.3888889f * 1.2857143f, 0.0, -3596854);
                    }
                    if (f7 >= (float)(-snappingStrength) && f7 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(0.0, (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale(), this.width, (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale() + 0.51724136f * 0.9666667f, 0.0, -3596854);
                    }
                    if (f9 >= (float)(-snappingStrength) && f9 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(0.0, moduleScaledPoints[1] * abstractModule.masterScale(), this.width, moduleScaledPoints[1] * abstractModule.masterScale() + 0.16666667f * 3.0f, 0.0, -3596854);
                    }
                    if (f8 >= (float)(-snappingStrength) && f8 <= (float)snappingStrength) {
                        highlightNeighborMods = true;
                        RenderUtil.drawRoundedRect(0.0, (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale() - 0.5810811f * 0.8604651f, this.width, (moduleScaledPoints[1] + abstractModule.height) * abstractModule.masterScale(), 0.0, -3596854);
                    }
                    if (!highlightNeighborMods) continue;
                    GL11.glPushMatrix();
                    abstractModule.scaleAndTranslate(scaledResolution);
                    Gui.drawRectWithOutline(0.0f, 0.0f, abstractModule.width, abstractModule.height, 0.01923077f * 26.0f, 0, 449387978);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    private float lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule abstractModule, float f, float[] arrf, int n) {
        float f2 = f;
        float f3 = 2.0f;
        if (f2 + arrf[0] * abstractModule.masterScale() < f3) {
            f2 = -arrf[0] * abstractModule.masterScale() + f3;
        } else if (f2 + arrf[0] * abstractModule.masterScale() + (float)n > (float)this.width - f3) {
            f2 = (float)this.width - arrf[0] * abstractModule.masterScale() - (float)n - f3;
        }
        return f2;
    }

    private float lIIIIIIIIIlIllIIllIlIIlIl(AbstractModule abstractModule, float f, float[] arrf, int n) {
        float f2 = f;
        float f3 = 2.0f;
        if (f2 + arrf[1] * abstractModule.masterScale() < f3) {
            f2 = -arrf[1] * abstractModule.masterScale() + f3;
        } else if (f2 + arrf[1] * abstractModule.masterScale() + (float)n > (float)this.height - f3) {
            f2 = (float)this.height - arrf[1] * abstractModule.masterScale() - (float)n - f3;
        }
        return f2;
    }

    private void dragModule(ModulePosition modulePosition, int n, int n2, ScaledResolution scaledResolution) {
        if (modulePosition.module.getGuiAnchor() == null || !modulePosition.module.isEnabled() || modulePosition.module == CheatBreaker.getInstance().getModuleManager().miniMapMod || !modulePosition.module.notRenderHUD && !modulePosition.module.isRenderHud()) {
            return;
        }
        float f = (float)n - modulePosition.x;
        float f2 = (float)n2 - modulePosition.y;
        if (!(this.IlIlIIIlllllIIIlIlIlIllII || modulePosition.module != draggingModule || (float)n == this.mouseX2 && (float)n2 == this.mouseY2)) {
            if (this.undoList.size() > 50) {
                this.undoList.remove(0);
            }
            this.undoList.add(new ModuleActionData(this, this.positions));
            CheatBreaker.getInstance().createNewProfile();
            this.IlIlIIIlllllIIIlIlIlIllII = true;
        }
        float[] arrf = modulePosition.module.getScaledPoints(scaledResolution, false);
        if (!Mouse.isButtonDown(1) && this.IlIlIIIlllllIIIlIlIlIllII && modulePosition.module == draggingModule) {
            float f3 = f;
            float f4 = f2;
            f = this.lIIIIlIIllIIlIIlIIIlIIllI(modulePosition.module, f, arrf, (int)(modulePosition.module.width * modulePosition.module.masterScale()));
            f2 = this.lIIIIIIIIIlIllIIllIlIIlIl(modulePosition.module, f2, arrf, (int)(modulePosition.module.height * modulePosition.module.masterScale()));
            float f5 = f3 - f;
            float f6 = f4 - f2;
            for (ModulePosition positions : this.positions) {
                if (positions == modulePosition) continue;
                arrf = positions.module.getScaledPoints(scaledResolution, false);
                float f7 = this.lIIIIlIIllIIlIIlIIIlIIllI(positions.module, positions.module.getXTranslation() - f5, arrf, (int)(positions.module.width * positions.module.masterScale()));
                float f8 = this.lIIIIIIIIIlIllIIllIlIIlIl(positions.module, positions.module.getYTranslation() - f6, arrf, (int)(positions.module.height * positions.module.masterScale()));
                positions.module.setTranslations(f7, f8);
            }
        }
        if (this.IlIlIIIlllllIIIlIlIlIllII) {
            modulePosition.module.setTranslations(f, f2);
        }
    }

    private void setDirection(int n) {
        if (allMenusClosed) {
            if (this.allElements != null) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(this.allElements, true, n);
            }
        } else if (this.currentScrollableElement != null) {
            if (this.allElements != null) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(this.allElements, true, n);
            }
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.currentScrollableElement, false, n);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(AbstractScrollableElement abstractScrollableElement, boolean bl, int n) {
        if (bl) {
            abstractScrollableElement.x = abstractScrollableElement.x2;
            allMenusClosed = false;
            this.allElements = null;
        } else {
            abstractScrollableElement.x = n / 2 - 185;
            this.currentScrollableElement = null;
            this.allElements = abstractScrollableElement;
        }
    }

    public static float getFPSTransitionSpeed(float f) {
        float f2 = f / (float)(Minecraft.debugFPS + 1);
        return Math.max(f2, 1.0f);
    }

    private ModulePosition getSelectedModules(AbstractModule module) {
        for (ModulePosition position : this.positions) {
            if (module != position.module) continue;
            return position;
        }
        return null;
    }

    private void setSelectedModulesPosition(ScaledResolution scaledResolution, int n, int n2) {
        for (ModulePosition position : this.positions) {
            if (position.module == null || position.module.getGuiAnchor() == null) continue;
            position.x = (float)n - position.module.getXTranslation();
            position.y = (float)n2 - position.module.getYTranslation();
        }
    }

    private void removeModuleFromPositions(AbstractModule abstractModule) {
        this.positions.removeIf(cBModulePosition -> cBModulePosition.module == abstractModule);
    }

    private void debugOutputHud(ScaledResolution res) {
        for (AbstractModule module : this.modules) {
            if (this.getSelectedModules(module) != null) {
                this.mc.fontRenderer.drawString(module.getGuiAnchor() + ", " + module.getXTranslation() + ", " + module.getYTranslation(), module.getScaledPoints(res, true)[0] * module.masterScale(), module.getScaledPoints(res, true)[1] * module.masterScale() + module.height * module.masterScale() + 1.0f, -1, true);
            }
        }
    }
}
