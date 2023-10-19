package com.cheatbreaker.client.module.impl.packmanager.gui.components;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.packmanager.utils.PackUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ResourcePacksWatcher {
    private WatchService watchService;

    public ResourcePacksWatcher(Path root) {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) throws IOException {
                    File file = path.toFile();
                    if (PackUtils.isResourcePackDirectory(file)) {
                        path.register(ResourcePacksWatcher.this.watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
                        return FileVisitResult.CONTINUE;
                    }
                    if (PackUtils.isResourcePack(file)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException exception) {
            CheatBreaker.getInstance().getLogger().error("Failed to create watch service", exception);
        }
    }

    public boolean hasEvent() {
        WatchKey watchKey;
        boolean eventOccurred = false;
        if (this.watchService != null && (watchKey = this.watchService.poll()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                File file = ((Path) watchKey.watchable()).resolve(event.context().toString()).toFile();
                if (!PackUtils.isResourcePack(file) && !PackUtils.isResourcePackDirectory(file)) continue;
                eventOccurred = true;
                break;
            }
            watchKey.reset();
        }
        return eventOccurred;
    }
}

