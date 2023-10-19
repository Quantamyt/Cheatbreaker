package com.cheatbreaker.client.util.friend;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.Minecraft;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FriendsManager {
    private final Map<String, Friend> friendsMap = new HashMap<String, Friend>();
    private final Map<String, FriendRequest> friendRequestsMap = new HashMap<String, FriendRequest>();
    private final Map<String, List<String>> readMessages = new HashMap<String, List<String>>();
    private final Map<String, List<String>> messages = new HashMap<String, List<String>>();
    private final Map<String, List<String>> unreadMessages = new HashMap<String, List<String>>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void addUnreadMessage(String var1, String var2) {
        Friend var3 = this.getFriend(var1);
        if (var3 != null) {
            if (!this.messages.containsKey(var1)) {
                this.messages.put(var1, new ArrayList<>());
            }
            String var4 = EnumChatFormatting.GRAY + LocalDateTime.now().format(this.timeFormatter);
            String var5 = EnumChatFormatting.GREEN + var3.getName() + EnumChatFormatting.RESET;
            String var6 = var4 + " " + var5 + ": " + var2;
            this.messages.get(var1).add(var6);
        }
    }

    public void addMessage(String var1, String var2) {
        Friend var3 = this.getFriend(var1);
        if (var3 != null) {
            if (!this.readMessages.containsKey(var1)) {
                this.readMessages.put(var1,  new ArrayList<>());
            }
            this.readMessages.get(var1).add(var2);
        }
    }

    public void addOutgoingMessage(String playerId, String message) {
        Friend friend = this.getFriend(playerId);
        if (friend != null) {
            if (!this.unreadMessages.containsKey(playerId)) {
                this.unreadMessages.put(playerId, new ArrayList<>());
            }
            this.unreadMessages.get(playerId).add(friend.getName() + ": " + message);
            String time = EnumChatFormatting.GRAY +
                    LocalDateTime.now().format(this.timeFormatter);
            String name = EnumChatFormatting.AQUA +
                    Minecraft.getMinecraft().getSession().getUsername() + EnumChatFormatting.RESET;
            String var6 = time + " " + name + ": " + message;
            this.addMessage(playerId, var6);
        }
    }

    public void readMessages(String var1) {
        if (this.messages.containsKey(var1)) {
            List<String> var2 = this.messages.get(var1);
            if (!this.readMessages.containsKey(var1)) {
                this.readMessages.put(var1, new ArrayList<>());
            }
            this.readMessages.get(var1).addAll(var2);
            this.messages.remove(var1);
        }
    }

    public Friend getFriend(String var1) {
        Iterator<Friend> var2 = this.friendsMap.values().iterator();
        if (!var2.hasNext()) {
            return null;
        }
        Friend var3 = var2.next();
        while (!var3.getPlayerId().equals(var1)) {
            if (!var2.hasNext()) {
                return null;
            }
            var3 = var2.next();
        }
        return var3;
    }

    public Map<String, Friend> getFriends() {
        return this.friendsMap;
    }

    public Map<String, FriendRequest> getFriendRequests() {
        return this.friendRequestsMap;
    }

    public Map<String, List<String>> getReadMessages() {
        return this.readMessages;
    }

    public Map<String, List<String>> getMessage() {
        return this.messages;
    }

    public Map<String, List<String>> getUnreadMessages() {
        return this.unreadMessages;
    }
}
