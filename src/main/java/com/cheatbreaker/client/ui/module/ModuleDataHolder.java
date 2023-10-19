package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.ScreenLocation;

public class ModuleDataHolder {
    public AbstractModule module;
    public float xTranslation;
    public float yTranslation;
    public float scale;
    public float width;
    public float height;
    public int mouseY;
    public int mouseX;
    public ScreenLocation screenLocation;
    public GuiAnchor anchor;
    public HudLayoutEditorGui layoutEditorGui;

    public ModuleDataHolder(HudLayoutEditorGui layoutEditorGui, AbstractModule module, ScreenLocation screenLocation, int mouseX, int mouseY) {
        this.layoutEditorGui = layoutEditorGui;
        this.module = module;
        this.xTranslation = module.getXTranslation();
        this.yTranslation = module.getYTranslation();
        this.width = module.width * module.masterScale();
        this.height = module.height * module.masterScale();
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.screenLocation = screenLocation;
        this.scale = (Float) module.scale.getValue();
        this.anchor = module.getGuiAnchor();
    }
}
