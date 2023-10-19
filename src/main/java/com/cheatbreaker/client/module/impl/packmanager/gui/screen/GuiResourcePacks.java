package com.cheatbreaker.client.module.impl.packmanager.gui.screen;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.packmanager.gui.components.ResourcePacksFolder;
import com.cheatbreaker.client.module.impl.packmanager.gui.components.ResourcePacksWatcher;
import com.cheatbreaker.client.module.impl.packmanager.gui.components.list.GuiResourcePacksAvailable;
import com.cheatbreaker.client.module.impl.packmanager.gui.components.list.GuiResourcePacksSelected;
import com.cheatbreaker.client.module.impl.packmanager.utils.PackUtils;
import com.cheatbreaker.client.module.impl.packmanager.utils.ThreadUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class GuiResourcePacks extends GuiScreen {
    private static final Comparator<ResourcePackRepository.Entry> ENTRY_COMPARATOR = (entry1, entry2) -> {
        return StringUtils.stripControlCodes(entry1.getResourcePackName()).trim().compareToIgnoreCase(
                StringUtils.stripControlCodes(entry2.getResourcePackName()).trim());
    };
    private static final Comparator<ResourcePacksFolder> FOLDER_COMPARATOR = (folder1, folder2) -> {
        return StringUtils.stripControlCodes(folder1.getName()).trim().compareToIgnoreCase(StringUtils.stripControlCodes(folder2.getName()).trim());
    };
    private final GuiScreen previousScreen;
    private final ResourcePackRepository packsRepo;
    private final List<ResourcePackRepository.Entry> initialSelectedEntries;
    private List<ResourcePackRepository.Entry> availableEntries;
    private final List<ResourcePackRepository.Entry> selectedEntries;
    private final List<ResourcePackRepository.Entry> searchedEntries;
    private final List<ResourcePacksFolder> packFolders;
    private ResourcePacksWatcher packsWatcher;
    private ResourcePacksFolder activePackFolder;
    private GuiResourcePacksAvailable availablePacks;
    private GuiResourcePacksSelected selectedPacks;
    private GuiButton buttonFolder;
    private GuiButton buttonDone;
    private GuiTextField searchBar;
    private boolean load;
    private GuiButton clearGlassButton;
    private GuiButton redStringButton;
    private GuiButton backgroundButton;

    public GuiResourcePacks(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
        this.packsRepo = previousScreen.mc.getResourcePackRepository();
        this.availableEntries = new ArrayList<ResourcePackRepository.Entry>();
        this.searchedEntries = new ArrayList<ResourcePackRepository.Entry>();
        this.selectedEntries = new ArrayList(Lists.reverse(this.packsRepo.getRepositoryEntries()));
        this.initialSelectedEntries = Collections.unmodifiableList(new ArrayList<ResourcePackRepository.Entry>(this.selectedEntries));
        this.packFolders = new ArrayList<ResourcePacksFolder>();
        ThreadUtils.execute(() -> {
            this.packsRepo.updateRepositoryEntriesAll();
            this.refreshPackFolders();
            this.packsWatcher = new ResourcePacksWatcher(this.packsRepo.getDirResourcepacks().toPath());
            this.load = true;
        });
    }

    public void initGui() {
        int listWidth = (this.width - 30) / 2;
        int listHeight = this.height - 80 - 46;
        boolean wide = (Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue();
        int wideWidth = wide ? this.width - listWidth - 12 : this.width / 2 + 4;
        int wideWidth2 = wide ? listWidth + 2 : 200;
        int wideWidth3 = wide ? listWidth + 2 : 200;
        this.setAvailablePacks(this.activePackFolder);
        this.selectedPacks = new GuiResourcePacksSelected(this, wideWidth, 32, wideWidth2, listHeight + (wide && this.width / 2 > 338 ? 20 : 0), 36, this.selectedEntries);
        this.buttonFolder = new GuiButton(0, this.width / 2 - 154, this.height - 48, 150, 20, I18n.format("resourcePack.openFolder"));
        this.buttonList.add(this.buttonFolder);
        this.buttonDone = new GuiButton(2, wideWidth, this.height - 48, 200, 20, I18n.format("gui.done"));
        this.buttonList.add(this.buttonDone);
        int width = this.selectedPacks.left + this.selectedPacks.listWidth / 2 - this.fontRendererObj.getStringWidth(this.selectedPacks.title) / 2 - (this.availablePacks.left + this.availablePacks.listWidth / 2 + this.fontRendererObj.getStringWidth(this.availablePacks.title) / 2) - 50;
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue()) {
            this.searchBar = new GuiTextField(299, this.fontRendererObj, 10, this.height - 70, (this.width - 30) / 2, 18);
        } else {
            this.searchBar = new GuiTextField(299, this.fontRendererObj, this.width / 2 - 204, this.height - 70, 200, 18);
        }
//        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().packSearchBar.getValue()) {
        this.searchBar.setVisible(this.activePackFolder == null && (Boolean) CheatBreaker.getInstance().getGlobalSettings().packSearchBar.getValue());
//        } else {
//            this.searchBar.setVisible(false);
//        }
        int d = !wide ? -1 : 1;
        this.clearGlassButton = new GuiOptionButton(100, this.width / 2 + 4, this.height - 71, 114, 20, "Clear Glass: " + CheatBreaker.getInstance().getModuleManager().packTweaksMod.clearGlass.getValue());
        this.buttonList.add(this.clearGlassButton);
        this.redStringButton = new GuiOptionButton(101, this.width / 2 + 118, this.height - 71, 86, 20, "Red String: " + ((Boolean) CheatBreaker.getInstance().getModuleManager().packTweaksMod.coloredString.getValue() ? "ON" : "OFF"));
        this.buttonList.add(this.redStringButton);
        this.backgroundButton = new GuiOptionButton(102, this.width / 2 + (wide && this.width / 2 > 338 ? 204 : 4), this.height - (wide && this.width / 2 > 338 ? 71 : 91), (wide && this.width / 2 > 338 ? 138 : 200), 20, "Background: " + ((Boolean) CheatBreaker.getInstance().getGlobalSettings().transparentBackground.getValue() ? "TRANSPARENT" : "NORMAL"));
        this.buttonList.add(this.backgroundButton);
        this.searchBar.setFocused(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.load) {
            this.load = false;
        }
        if (this.mc.theWorld != null && (Boolean) CheatBreaker.getInstance().getGlobalSettings().transparentBackground.getValue()) {
            this.drawDefaultBackground();
        } else {
            this.drawBackground(0);
        }
//        if (this.mc.theWorld == null) {
//            super.func_146278_c(0);
//        } else {
//            Gui.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
//        }
        this.availablePacks.draw(mouseX, mouseY);
        this.selectedPacks.draw(mouseX, mouseY);
//        System.out.println(zLevel);
        zLevel = 0.0f;
//        if (this.availablePacks.isEmpty()) {
//            String string = this.searchBar.getText().isEmpty() ? "Discovering resource packs..." : "No resource packs found.";
//            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + string, this.width / 2 - 100, 60, 0xFFFFFF);
//        }
//        Gui.drawRect(0, 0, this.width, 40, -16777216);
//        Gui.drawRect(0, this.height - 40, this.width, this.height, -16777216);
//        super.drawGradientRect(0, 40, this.width, 45, -16777216, 0);
//        super.drawGradientRect(0, this.height - 45, this.width, this.height - 40, 0, -16777216);
        if (this.availablePacks.entries.isEmpty()) {
            String string = !this.searchBar.getText().isEmpty() && this.searchedEntries.isEmpty() ? "No resource packs found." : "";
            if (this.load) {
                string = "Discovering resource packs...";
            }
            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + string, this.width / 2 - 100, 60, 0xFFFFFF);
        }
        if (this.selectedPacks.entries.isEmpty()) {
            String string = "Select resource packs.";
            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + string, this.width / 2 + 100, 60, 0xFFFFFF);
        }
        super.drawCenteredString(this.mc.fontRendererObj, this.selectedPacks.title, this.selectedPacks.left + this.selectedPacks.listWidth / 2, this.selectedPacks.top - 14, -1);
        super.drawCenteredString(this.mc.fontRendererObj, this.availablePacks.title, this.availablePacks.left + this.availablePacks.listWidth / 2, this.availablePacks.top - 14, -1);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo"), this.width / 2 - 77, this.height - 26, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.searchBar.drawTextBox();
        if (this.searchBar.getVisible() && !this.searchBar.isFocused() && this.searchBar.getText().isEmpty()) {
            super.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Search...", (Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue() ? 14 : this.width / 2 - 200, this.height - 65, 0xFFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.searchBar.getVisible()) {
            this.searchBar.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == this.buttonFolder.id) {
            try {
                Desktop.getDesktop().open(this.packsRepo.getDirResourcepacks());
            } catch (IOException exception) {
                CheatBreaker.getInstance().getLogger().error("Failed to open file", exception);
            }
        } else if (button.id == this.buttonDone.id) {
            if (!this.initialSelectedEntries.equals(this.selectedPacks.getEntries())) {
                ArrayList<ResourcePackRepository.Entry> activePacks = new ArrayList<ResourcePackRepository.Entry>(Lists.reverse(this.selectedPacks.getEntries()));
                this.packsRepo.setRepositories(activePacks);
                this.mc.gameSettings.resourcePacks.clear();
                for (ResourcePackRepository.Entry entry : activePacks) {
                    this.mc.gameSettings.resourcePacks.add(entry.getResourcePackName());
                }
                this.mc.gameSettings.saveOptions();
                this.mc.refreshResources();
            }
            this.mc.displayGuiScreen(this.previousScreen);
        } else if (button.id == this.clearGlassButton.id) {
            Setting clearGlass = CheatBreaker.getInstance().getModuleManager().packTweaksMod.clearGlass;
            for (int i = 0; i < clearGlass.getAcceptedStringValues().length; ++i) {
                if (!clearGlass.getAcceptedStringValues()[i].toLowerCase().equalsIgnoreCase(clearGlass.getStringValue()))
                    continue;
                if (i + 1 >= clearGlass.getAcceptedStringValues().length) {
                    clearGlass.setValue(clearGlass.getAcceptedStringValues()[0]);
                    break;
                }
                clearGlass.setValue(clearGlass.getAcceptedStringValues()[i + 1]);
                clearGlass.setValue(clearGlass.getAcceptedStringValues()[i + 1]);
                break;
            }
            this.clearGlassButton.displayString = "Clear Glass: " + clearGlass.getValue();
            this.mc.renderGlobal.loadRenderers();
        } else if (button.id == this.redStringButton.id) {
            boolean redString = CheatBreaker.getInstance().getModuleManager().packTweaksMod.coloredString.getBooleanValue();
            this.redStringButton.displayString = "Red String: " + (!redString ? "ON" : "OFF");
            CheatBreaker.getInstance().getModuleManager().packTweaksMod.coloredString.setValue(!redString);
            this.mc.renderGlobal.loadRenderers();
        } else if (button.id == this.backgroundButton.id) {
            boolean transparentBackground = CheatBreaker.getInstance().getGlobalSettings().transparentBackground.getBooleanValue();
            this.backgroundButton.displayString = "Background: " + (!transparentBackground ? "TRANSPARENT" : "NORMAL");
            CheatBreaker.getInstance().getGlobalSettings().transparentBackground.setValue(!transparentBackground);
            this.mc.renderGlobal.loadRenderers();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.availablePacks.handleMouseInput();
        this.selectedPacks.handleMouseInput();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (this.searchBar.textboxKeyTyped(typedChar, keyCode)) {
            this.searchForPacks(this.searchBar.getText().toLowerCase());
        }
    }

    public void updateScreen() {
        if (this.packsWatcher != null && this.packsWatcher.hasEvent()) {
            ThreadUtils.execute(this::refreshPackFolders);
            this.setAvailablePacks(null);
        }
        this.searchBar.updateCursorCounter();
    }

    public void addAvailableEntry(ResourcePackRepository.Entry entry) {
        if (!this.availableEntries.contains(entry) && this.activePackFolder == null) {
            this.availableEntries.add(entry);
            this.sortAvailableEntries(this.availableEntries);
        }
    }

    public boolean addSelectedEntry(ResourcePackRepository.Entry entry) {
        if (!this.selectedEntries.contains(entry)) {
            this.selectedEntries.add(0, entry);
            return true;
        }
        return false;
    }

    public void setAvailablePacks(ResourcePacksFolder packFolder) {
        this.activePackFolder = packFolder;
        int searchBarHeight = (Boolean) CheatBreaker.getInstance().getGlobalSettings().packSearchBar.getValue() ? 26 : 4;

        if (packFolder == null) {
            if (this.searchBar != null) {
                this.searchBar.setVisible((Boolean) CheatBreaker.getInstance().getGlobalSettings().packSearchBar.getValue());
            }
            this.availableEntries = new ArrayList<>(this.packsRepo.getRepositoryEntriesAll());
            this.availableEntries.removeAll(this.selectedEntries);

            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue()) {
                this.availablePacks = new GuiResourcePacksAvailable(this, 10, 32, (this.width - 30) / 2, this.height - 80 - searchBarHeight, 36, this.availableEntries, this.packFolders, true);
            } else {
                this.availablePacks = new GuiResourcePacksAvailable(this, this.width / 2 - 204, 32, 200, this.height - 80 - searchBarHeight, 36, this.availableEntries, this.packFolders, true);
            }

        } else {
//            this.searchBar.setVisible(false);
            this.availableEntries = packFolder.getEntries();
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue()) {
                this.availablePacks = new GuiResourcePacksAvailable(this, 10, 32, (this.width - 30) / 2, this.height - 80 - searchBarHeight, 36, this.availableEntries, packFolder.getPackFolders(), true);
            } else {
                this.availablePacks = new GuiResourcePacksAvailable(this, this.width / 2 - 204, 32, 200, this.height - 80 - searchBarHeight, 36, this.availableEntries, packFolder.getPackFolders(), true);
            }

        }
        this.sortAvailableEntries(this.availableEntries);
    }

    public List<ResourcePacksFolder> getPackFolders() {
        return Collections.unmodifiableList(this.packFolders);
    }

    private void refreshPackFolders() {
        this.packFolders.clear();
        for (File packSubfolder : Objects.requireNonNull(this.packsRepo.getDirResourcepacks().listFiles(PackUtils::isResourcePackDirectory))) {
            this.packFolders.add(new ResourcePacksFolder(packSubfolder, this.selectedEntries));
        }
        this.sortPackFolders((String) CheatBreaker.getInstance().getGlobalSettings().packSortMethod.getValue());
    }

    public void sortAvailableEntries(List<ResourcePackRepository.Entry> list) {
        String sortMethod = (String) CheatBreaker.getInstance().getGlobalSettings().packSortMethod.getValue();
        if (sortMethod.equals("A-Z")) {
            list.sort(ENTRY_COMPARATOR);
        } else if (sortMethod.equals("Z-A")) {
            list.sort(ENTRY_COMPARATOR.reversed());
        }
    }

    private void sortPackFolders(String sortMethod) {
        if (sortMethod.equals("A-Z")) {
            this.packFolders.sort(FOLDER_COMPARATOR);
        } else if (sortMethod.equals("Z-A")) {
            this.packFolders.sort(FOLDER_COMPARATOR.reversed());
        }
    }

    private void searchForPacks(String search) {
        if (search.isEmpty()) {
            this.setAvailablePacks(null);
        } else {
            this.searchedEntries.clear();
            for (ResourcePackRepository.Entry entry : this.availableEntries) {
                if (!StringUtils.stripControlCodes(entry.getResourcePackName()).toLowerCase().contains(search) && !StringUtils.stripControlCodes(entry.getTexturePackDescription()).toLowerCase().contains(search))
                    continue;
                this.addSearchedPack(entry);
            }
            for (ResourcePacksFolder packFolder : this.packFolders) {
                this.searchForPacks(packFolder, search);
            }
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().widePackMenu.getValue()) {
                this.availablePacks = new GuiResourcePacksAvailable(this, 10, 32, (this.width - 30) / 2, this.height - 80 - 26, 36, this.searchedEntries, null, false);
            } else {
                this.availablePacks = new GuiResourcePacksAvailable(this, this.width / 2 - 204, 32, 200, this.height - 80 - 26, 36, this.searchedEntries, null, false);
            }
            this.sortAvailableEntries(this.searchedEntries);
        }
    }

    private void searchForPacks(ResourcePacksFolder packFolder, String search) {
        for (ResourcePackRepository.Entry entry : packFolder.getEntries()) {
            if (!StringUtils.stripControlCodes(entry.getResourcePackName().toLowerCase()).contains(search) && !StringUtils.stripControlCodes(entry.getTexturePackDescription()).toLowerCase().contains(search))
                continue;
            this.addSearchedPack(entry);
        }
        for (ResourcePacksFolder packSubfolder : packFolder.getPackFolders()) {
            this.searchForPacks(packSubfolder, search);
        }
    }

    public void addSearchedPack(ResourcePackRepository.Entry entry) {
        boolean duplicate = false;
        for (ResourcePackRepository.Entry searchedEntry : this.searchedEntries) {
            if (!searchedEntry.getResourcePackName().equals(entry.getResourcePackName())) continue;
            duplicate = true;
            break;
        }
        if (!duplicate) {
            this.searchedEntries.add(entry);
            this.sortAvailableEntries(this.searchedEntries);
        }
    }

    public boolean isSearching() {
        return !this.searchBar.getText().isEmpty();
    }
}