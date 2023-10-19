package com.cheatbreaker.client.module.impl.packmanager;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.InitializationEvent;
import com.cheatbreaker.client.module.impl.packmanager.utils.PackUtils;
import com.cheatbreaker.client.module.impl.packmanager.utils.ThreadUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ResourcePackManager {
    private final Minecraft mc = Minecraft.getMinecraft();


    public ResourcePackManager() {
        CheatBreaker.getInstance().getEventBus().addEvent(InitializationEvent.class, this::onInit);
    }

    public void onInit(InitializationEvent event) {
        HashMap<String, ResourcePackRepository.Entry> activeEntries = new HashMap<String, ResourcePackRepository.Entry>();
        for (String packName : mc.gameSettings.resourcePacks) {
            activeEntries.put(packName, null);
        }
        for (ResourcePackRepository.Entry entry : mc.getResourcePackRepository().getRepositoryEntries()) {
            activeEntries.put(entry.getResourcePackName(), entry);
        }
        for (ResourcePackRepository.Entry entry : PackUtils.getActiveEntries()) {
            activeEntries.put(entry.getResourcePackName(), entry);
        }
        activeEntries.values().removeIf(Objects::isNull);
        mc.getResourcePackRepository().setRepositories(new ArrayList<>(activeEntries.values()));
        this.mc.refreshResources();
        Runtime.getRuntime().addShutdownHook(new Thread(ThreadUtils::shutdown));
    }

}
