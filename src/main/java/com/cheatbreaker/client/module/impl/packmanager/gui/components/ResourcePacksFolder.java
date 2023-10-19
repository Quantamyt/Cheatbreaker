package com.cheatbreaker.client.module.impl.packmanager.gui.components;


import com.cheatbreaker.client.module.impl.packmanager.utils.PackUtils;
import lombok.Getter;
import net.minecraft.client.resources.ResourcePackRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
public class ResourcePacksFolder {
    private final String name;
    private final List<ResourcePackRepository.Entry> entries;
    private final List<ResourcePackRepository.Entry> selectedEntries;
    private final List<ResourcePacksFolder> packFolders;
    private final ResourcePacksFolder previousPackFolder;

    public ResourcePacksFolder(File directory, List<ResourcePackRepository.Entry> selectedEntries) {
        this(directory, selectedEntries, null);
    }

    private ResourcePacksFolder(File directory, List<ResourcePackRepository.Entry> selectedEntries, ResourcePacksFolder previousPackFolder) {
        this.name = directory.getName();
        this.entries = new ArrayList<ResourcePackRepository.Entry>();
        this.selectedEntries = selectedEntries;
        this.packFolders = new ArrayList<ResourcePacksFolder>();
        this.previousPackFolder = previousPackFolder;
        this.packFolders.add(new ResourcePacksFolder(previousPackFolder));
        for (File file : Objects.requireNonNull(directory.listFiles())) {

            if (PackUtils.isResourcePack(file)) {
                for (final ResourcePackRepository.Entry entry : this.selectedEntries) {
                    if (entry.getResourcePackName().equals(file.getName())) {
                        final Optional<ResourcePackRepository.Entry> newEntry = PackUtils.createEntry(file);
                        if (newEntry.isPresent() && newEntry.get().equals(entry)) {
                            break;
                        }
                    }
                }
                PackUtils.createEntry(file).ifPresent(this.entries::add);
            } else if (PackUtils.isResourcePackDirectory(file)) {
                this.packFolders.add(new ResourcePacksFolder(file, selectedEntries, this));
            }
        }
    }

    private ResourcePacksFolder(ResourcePacksFolder previousPackFolder) {
        this.name = "Back to " + (previousPackFolder == null ? "Main Folder" : previousPackFolder.name);
        this.entries = new ArrayList<ResourcePackRepository.Entry>();
        this.selectedEntries = new ArrayList<ResourcePackRepository.Entry>();
        this.packFolders = new ArrayList<ResourcePacksFolder>();
        this.previousPackFolder = previousPackFolder;
    }
}