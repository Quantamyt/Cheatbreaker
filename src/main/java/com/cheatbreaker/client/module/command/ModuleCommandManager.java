package com.cheatbreaker.client.module.command;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.command.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class ModuleCommandManager {
    public List<ModuleCommand> moduleCommands = new ArrayList<>();

    public IncreaseTimeCommand increaseStaticTimeCommand;
    public DecreaseTimeCommand decreaseStaticTimeCommand;

    public ModuleCommandManager() {
        this.moduleCommands.add(this.increaseStaticTimeCommand = new IncreaseTimeCommand());
        this.moduleCommands.add(this.decreaseStaticTimeCommand = new DecreaseTimeCommand());
    }

    public void sendModCommandCallback(ChatComponentText chatComponent) {
        if (CheatBreaker.getInstance().getGlobalSettings().hideCallbackMessages.getBooleanValue()) return;

        chatComponent.setBranded(true);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
}
