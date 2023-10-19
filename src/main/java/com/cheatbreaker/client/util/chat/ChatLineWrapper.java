package com.cheatbreaker.client.util.chat;

import lombok.*;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.IChatComponent;

public class ChatLineWrapper extends ChatLine {
    @Getter @Setter
    private boolean branded;

    public ChatLineWrapper(int i, IChatComponent chatComponent, int i1) {
        super(i, chatComponent, i1);
    }

    public static ChatLineWrapper of(ChatLine chatLine) {
        return new ChatLineWrapper(chatLine.getUpdatedCounter(), chatLine.getChatComponent(), chatLine.getChatLineID());
    }
}
