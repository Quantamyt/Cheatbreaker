package com.cheatbreaker.client.event.impl.chat;

import com.cheatbreaker.client.event.EventBus;
import lombok.*;

@Getter @AllArgsConstructor
public class ChatReceivedEvent extends EventBus.Event {
    private final String receivedChatMessage;
}
