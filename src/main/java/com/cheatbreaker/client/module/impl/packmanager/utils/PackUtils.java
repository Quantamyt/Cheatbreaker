package com.cheatbreaker.client.module.impl.packmanager.utils;

import com.cheatbreaker.client.CheatBreaker;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();

    /**
     * Returns true if the file is a zip or a directory with a pack.mcmeta in a pack folder.
     * DOES NOT CHECK IN THE MAIN PACK FOLDER DIRECTORY
     */
    @SneakyThrows
    public static boolean isResourcePack(File file) {
        if(file.isFile() && file.getName().endsWith(".zip")) {
            try {
                ZipFile sourceZipFile = new ZipFile(file);
                ZipEntry entry =  sourceZipFile.getEntry("pack.mcmeta");
                if(entry == null) {
                    System.out.println("Not found in: " + file.getName());
                    return false;

                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return file.isDirectory() && new File(file, "pack.mcmeta").isFile();
    }

    /**
     * Returns true if the file is a zip or a directory with a pack.mcmeta in a pack folder.
     */
    public static boolean isResourcePackDirectory(File file) {
        return file.isDirectory() && !new File(file, "pack.mcmeta").isFile();
    }

    public static Optional<ResourcePackRepository.Entry> createEntry(File resourcePack) {
        Optional<ResourcePackRepository.Entry> entry = Optional.empty();
        try {
            Constructor<ResourcePackRepository.Entry> constructor = ResourcePackRepository.Entry.class.getDeclaredConstructor(ResourcePackRepository.class, File.class);
            constructor.setAccessible(true);
            entry = Optional.of(constructor.newInstance(MC.getResourcePackRepository(), resourcePack));
            entry.get().updateResourcePack();
        } catch (NoSuchMethodException exception) {
            CheatBreaker.getInstance().getLogger().error("Failed to find constructor", exception);
            entry = Optional.empty();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException exception) {
            CheatBreaker.getInstance().getLogger().error("Failed to create entry", exception);
        }

        return entry;
    }

    public static List<ResourcePackRepository.Entry> getActiveEntries() {
        ArrayList<ResourcePackRepository.Entry> activeEntries = new ArrayList<>();
        ResourcePackRepository packRepo = MC.getResourcePackRepository();
        for (String packName : PackUtils.MC.gameSettings.resourcePacks) {
            for (ResourcePackRepository.Entry entry : packRepo.getRepositoryEntriesAll()) {
                if (!entry.getResourcePackName().equals(packName)) continue;
            }
            for (File packFolder : Objects.requireNonNull(packRepo.getDirResourcepacks().listFiles(PackUtils::isResourcePackDirectory))) {
                activeEntries.addAll(PackUtils.getActiveEntries(packFolder, packName));
            }
        }
        return activeEntries;
    }

    private static List<ResourcePackRepository.Entry> getActiveEntries(File directory, String packName) {
        ArrayList<ResourcePackRepository.Entry> activeEntries = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (PackUtils.isResourcePack(file)) {
                if (!file.getName().equals(packName)) continue;
                PackUtils.createEntry(file).ifPresent(activeEntries::add);
                continue;
            }
            if (!PackUtils.isResourcePackDirectory(file)) continue;
            activeEntries.addAll(PackUtils.getActiveEntries(file, packName));
        }
        return activeEntries;
    }
}
