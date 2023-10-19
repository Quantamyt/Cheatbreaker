package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.module.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleActionData {
    protected List<AbstractModule> moduleList;
    List<GuiAnchor> guiAnchorList;
    List<Float> xTranslationList;
    List<Float> yTranslationList;
    List<Object> valueList;
    final HudLayoutEditorGui hudEditorGui;

    ModuleActionData(HudLayoutEditorGui hudEditorGui, List<ModulePosition> positions) {
        this.hudEditorGui = hudEditorGui;
        ArrayList<AbstractModule> moduleList = new ArrayList<AbstractModule>();
        ArrayList<GuiAnchor> guiAnchorList = new ArrayList<GuiAnchor>();
        ArrayList<Float> xTranslationList = new ArrayList<Float>();
        ArrayList<Float> yTranslationList = new ArrayList<Float>();
        ArrayList<Object> valueList = new ArrayList<Object>();
        for (ModulePosition position : positions) {
            if (position.module.getGuiAnchor() == null) continue;
            moduleList.add(position.module);
            guiAnchorList.add(position.module.getGuiAnchor());
            xTranslationList.add(position.module.getXTranslation());
            yTranslationList.add(position.module.getYTranslation());
            valueList.add(position.module.masterScale());
        }
        this.moduleList = moduleList;
        this.guiAnchorList = guiAnchorList;
        this.xTranslationList = xTranslationList;
        this.yTranslationList = yTranslationList;
        this.valueList = valueList;
    }
}
