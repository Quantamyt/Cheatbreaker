package com.cheatbreaker.client.module.impl.packmanager.gui.components.list;

import com.cheatbreaker.client.module.impl.packmanager.gui.components.ResourcePacksFolder;
import com.cheatbreaker.client.module.impl.packmanager.gui.screen.GuiResourcePacks;
import com.cheatbreaker.client.module.impl.packmanager.utils.PackUtils;
import com.cheatbreaker.client.util.GlStateManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GuiResourcePacksSelected extends GuiResourcePacksList {
    private static final ResourceLocation RESOURCE_PACK_TEXTURES = new ResourceLocation("textures/gui/resource_packs.png");
    private final GuiResourcePacks parentScreen;
    private Action action;

    public GuiResourcePacksSelected(GuiResourcePacks parentScreen, int x, int y, int width, int height, int slotHeight, List<ResourcePackRepository.Entry> entries) {
        super(parentScreen, x, y, width, height, slotHeight, I18n.format("resourcePack.selected.title"), entries);
        this.parentScreen = parentScreen;
        this.action = Action.MOVE_BACK;
    }

    @Override
    protected void drawSlot(int index, int right, int top, int buffer, int mouseX, int mouseY, boolean hovered) {
        if (hovered) {
            Gui.drawRect(this.left, top - 1, right + 1, top + buffer + 3, -2134851392);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        super.drawSlot(index, right, top, buffer, mouseX, mouseY, hovered);
        if (hovered) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(RESOURCE_PACK_TEXTURES);
            if (right - 40 <= mouseX && mouseX < right) {
                if (top <= mouseY && mouseY < top + buffer / 2 - 2) {
                    if (index == 0) {
                        if (this.action == Action.MOVE_DOWN) {
                            this.action = Action.MOVE_BACK;
                        }
                    } else {
                        this.action = Action.MOVE_UP;
                        Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    if (index != super.getSize() - 1) {
                        Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                } else {
                    if (index != 0) {
                        Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                    if (index == super.getSize() - 1) {
                        if (this.action == Action.MOVE_UP) {
                            this.action = Action.MOVE_BACK;
                        }
                    } else {
                        this.action = Action.MOVE_DOWN;
                        Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
            } else {
                this.action = Action.MOVE_BACK;
                if (index != 0) {
                    Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
                if (index != super.getSize() - 1) {
                    Gui.drawModalRectWithCustomSizedTexture(right - 40, top, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
        }
    }

    @Override
    protected void onClick(int index, boolean doubleClick) {
        if (0 <= index && index < super.getSize()) {
            switch (this.action) {
                case MOVE_UP:
                    if (index - 1 < 0) break;
                    Collections.swap(this.entries, index, index - 1);
                    break;
                case MOVE_DOWN:
                    if (index + 1 >= super.getSize()) break;
                    Collections.swap(this.entries, index, index + 1);
                    break;
                default:
                    ResourcePackRepository.Entry entry = this.entries.get(index);
                    this.refreshEntry(entry);
                    this.entries.remove(entry);
            }
        }
    }

    private void refreshEntry(ResourcePackRepository.Entry entry) {
        for (File file : Objects.requireNonNull(this.mc.getResourcePackRepository().getDirResourcepacks().listFiles())) {
            if (PackUtils.isResourcePack(file)) {
                Optional<ResourcePackRepository.Entry> newEntry;
                if (!file.getName().equals(entry.getResourcePackName()) || !(newEntry = PackUtils.createEntry(file)).isPresent() || !newEntry.get().equals(entry))
                    continue;
                if (this.parentScreen.isSearching()) {
                    ((GuiResourcePacksList) this).parentScreen.addSearchedPack(entry);
                    continue;
                }
                ((GuiResourcePacksList) this).parentScreen.addAvailableEntry(entry);
                continue;
            }
            if (!PackUtils.isResourcePackDirectory(file)) continue;
            this.refreshEntry(file, entry);
        }
    }

    private void refreshEntry(File directory, ResourcePackRepository.Entry entry) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (PackUtils.isResourcePack(file)) {
                Optional<ResourcePackRepository.Entry> newEntry;
                if (!file.getName().equals(entry.getResourcePackName()) || !(newEntry = PackUtils.createEntry(file)).isPresent() || !newEntry.get().equals(entry))
                    continue;
                for (ResourcePacksFolder packFolder : this.parentScreen.getPackFolders()) {
                    this.refreshEntry(packFolder, directory, entry);
                }
                continue;
            }
            if (!PackUtils.isResourcePackDirectory(file)) continue;
            this.refreshEntry(file, entry);
        }
    }

    private void refreshEntry(ResourcePacksFolder packFolder, File directory, ResourcePackRepository.Entry entry) {
        if (packFolder.getName().equals(directory.getName())) {
            if (this.parentScreen.isSearching()) {
                if (packFolder.getEntries().contains(entry)) {
                    ((GuiResourcePacksList) this).parentScreen.addSearchedPack(entry);
                }
            } else {
                if (!packFolder.getEntries().contains(entry)) {
                    packFolder.getEntries().add(entry);
                }
                this.parentScreen.sortAvailableEntries(packFolder.getEntries());
            }
        } else {
            for (ResourcePacksFolder packSubfolder : packFolder.getPackFolders()) {
                this.refreshEntry(packSubfolder, directory, entry);
            }
        }
    }

    enum Action {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_BACK

    }
}
