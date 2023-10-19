package com.cheatbreaker.client.module.impl.packmanager.gui.components.list;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.packmanager.gui.screen.GuiResourcePacks;
import com.cheatbreaker.client.module.impl.packmanager.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.ResourcePackRepository;

import java.util.Collections;
import java.util.List;

public abstract class GuiResourcePacksList extends GuiScrollingList {
    protected final GuiResourcePacks parentScreen;
    protected final Minecraft mc;
    public final List<ResourcePackRepository.Entry> entries;

    public GuiResourcePacksList(GuiResourcePacks parentScreen, int x, int y, int width, int height, int slotHeight, String title, List<ResourcePackRepository.Entry> entries) {
        super(parentScreen.mc, x, y, width, height, slotHeight, title);
        this.parentScreen = parentScreen;
        this.mc = parentScreen.mc;
        this.entries = entries;
    }

    @Override
    protected void drawBackground() {
        Gui.drawRect(this.left, this.top, this.right, this.bottom, CheatBreaker.getInstance().getGlobalSettings().packListBackgroundColor.getColorValue());
    }

    @Override
    protected void drawSlot(int index, int right, int top, int buffer, int mouseX, int mouseY, boolean hovered) {
        if (0 <= index && index < this.getSize()) {
            ResourcePackRepository.Entry entry = this.entries.get(index);
            boolean showPackIcons = (Boolean) CheatBreaker.getInstance().getGlobalSettings().packIcons.getValue();
            if (showPackIcons) {
                entry.bindTexturePackIcon(this.mc.getTextureManager());
                Gui.drawModalRectWithCustomSizedTexture(this.left + 2, top + 1, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
            }
            this.mc.fontRendererObj.drawString(GuiUtils.trimString(entry.getResourcePackName(),
                    this.listWidth - 46), (float) this.left + (showPackIcons ? 36.0f : 2.0f), (float) top + 2.0f, -1, true);
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().packDescriptions.getValue()) {
                List<String> lines = this.mc.fontRendererObj.listFormattedStringToWidth(entry.getTexturePackDescription(), this.listWidth - 46);
                for (int i = 0; i < lines.size(); ++i) {
                    String text = lines.get(i);
                    if (i == 1 && lines.size() > 2) {
                        text = GuiUtils.trimString(text, this.listWidth - 46);
                    }
                    this.mc.fontRendererObj.drawString(text, (float) this.left + (showPackIcons ? 36.0f : 2.0f), (float) top + 13.0f + 10.0f * (float) i, -5592406, true);
                    if (i == 1) break;
                }
            }
        }
    }

    @Override
    protected abstract void onClick(int var1, boolean var2);

    @Override
    protected int getSize() {
        return this.entries.size();
    }

    public List<ResourcePackRepository.Entry> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }
}
