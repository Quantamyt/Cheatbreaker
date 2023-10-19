package com.cheatbreaker.client.network.websocket;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.Profile;
import com.cheatbreaker.client.cosmetic.CosmeticManager;
import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.network.messages.Message;
import com.cheatbreaker.client.network.websocket.client.*;
import com.cheatbreaker.client.network.websocket.server.*;
import com.cheatbreaker.client.network.websocket.shared.*;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.overlay.CBAlert;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.overlay.friend.FriendRequestElement;
import com.cheatbreaker.client.ui.overlay.friend.MessagesElement;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.cosmetic.profile.ClientProfile;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import com.cheatbreaker.client.util.friend.Friend;
import com.cheatbreaker.client.util.friend.FriendRequest;
import com.cheatbreaker.client.util.thread.CBAssestConnThread;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.src.Config;
import net.minecraft.util.CryptManager;
import net.minecraft.util.EnumChatFormatting;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class WSNetHandler extends WebSocketClient {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<String> playersCache = new ArrayList<>();

    public WSNetHandler(URI url, Map<String, String> data) {
        super(url, new Draft_6455(), data, 0);
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }
    public void sendPacket(WSPacket var1) {
        if (this.isOpen()) {
            PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
            var2.writeVarIntToBuffer(WSPacket.REGISTRY.get(var1.getClass()));
            try {
                var1.write(var2);
                System.out.println(CheatBreaker.getInstance().getLoggerPrefix() + "[WS NetHandler] (OUT) id: " +
                        WSPacket.REGISTRY.get(var1.getClass()) + " Name: " + var1.getClass().getSimpleName());

                this.send(var2.array());
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    public void handleIncoming(PacketBuffer var1) {
        int var2 = var1.readVarIntFromBuffer();
        Class<? extends WSPacket> var3 = WSPacket.REGISTRY.inverse().get(var2);
        try {
            WSPacket var4 = var3 == null ? null : var3.newInstance();
            if (var4 == null) {
                return;
            }
            System.out.println(CheatBreaker.getInstance().getLoggerPrefix() + "[WS NetHandler] (IN) id: " + var2 + " Name: " + var4.getClass().getSimpleName());
            var4.read(var1);
            var4.process(this);

        } catch (Exception var5) {
            System.out.println("Error from: " + var3);
            var5.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake var1) {
        System.out.println(CheatBreaker.getInstance().getLoggerPrefix() + "Connection established");
        if (Objects.equals(Minecraft.getMinecraft().getSession().getUsername(), Minecraft.getMinecraft().getSession().getPlayerID())) {
            this.close();
        }
    }

    @Override
    public void onMessage(String message) {
        if (this.isOpen()) {
            super.send(message);
        }
    }

    @Override
    public void onMessage(ByteBuffer var1) {
        this.handleIncoming(new PacketBuffer(Unpooled.wrappedBuffer(var1.array())));
    }



    public void handleConsoleOutput(WSPacketConsoleMessage var1) {
        CheatBreaker.getInstance().getConsoleLines().add(var1.getMessage());
    }

    public void handleFriendRemove(WSPacketClientFriendRemove var1) {
        String var2 = var1.getPlayerId();
        Friend var3 = CheatBreaker.getInstance().getFriendsManager().getFriend(var2);
        if (var3 != null) {
            CheatBreaker.getInstance().getFriendsManager().getFriends().remove(var2);
            OverlayGui.getInstance().handleFriend(var3, false);
        }
    }

    public void handleMessage(WSPacketFriendMessage var1) {
        String playerId = var1.getPlayerId();
        String message = var1.getMessage();
        Friend friend = CheatBreaker.getInstance().getFriendsManager().getFriends().get(playerId);
        if (friend != null) {
            CheatBreaker.getInstance().getFriendsManager().addUnreadMessage(friend.getPlayerId(), message);
            if (CheatBreaker.getInstance().getPlayerStatus() != Friend.Status.BUSY) {
                CheatBreaker.getInstance().getAudioManager().playSound("message");
                CBAlert.displayMessage(EnumChatFormatting.GREEN + friend.getName() + EnumChatFormatting.RESET + " says:", message);
            }
            for (AbstractElement element : OverlayGui.getInstance().getElements()) {
                if (!(element instanceof MessagesElement) || ((MessagesElement) element).getFriend() != friend) continue;
                CheatBreaker.getInstance().getFriendsManager().readMessages(friend.getPlayerId());
            }
        }
    }

    public void handleEmote(WSPacketEmote packet) {
        EntityPlayer entityPlayer = Minecraft.getMinecraft().theWorld.func_152378_a(packet.getPlayerId());
        Emote emote = CheatBreaker.getInstance().getEmoteManager().getEmote(packet.getEmoteId());
        if (entityPlayer instanceof AbstractClientPlayer) {
            CheatBreaker.getInstance().getEmoteManager().playEmote((AbstractClientPlayer)entityPlayer, emote);
        }
    }

    public void sendUpdateServer(String server) {
        this.sendPacket(new WSPacketServerUpdate("", server));
    }

    public void handleServerUpdate(WSPacketServerUpdate var1) {
        String var2 = var1.getPlayerId();
        String var3 = var1.getServer();
        Friend var4 = CheatBreaker.getInstance().getFriendsManager().getFriends().get(var2);
        if (var4 != null) {
            var4.setServer(var3);
        }
    }

    public void handleBulkFriends(WSPacketBulkFriendRequest packet) {
        CheatBreaker.getInstance().getFriendsManager().getFriendRequests().clear();
        JsonArray friendList = packet.getBulkArray();
        for (JsonElement element : friendList) {
            JsonObject friendObject = element.getAsJsonObject();
            String uuid = friendObject.get("uuid").getAsString();
            String name = friendObject.get("name").getAsString();
            FriendRequest request = new FriendRequest(name, uuid);
            CheatBreaker.getInstance().getFriendsManager().getFriendRequests().put(uuid, request);
            System.out.println("Added Friend");
            OverlayGui.getInstance().handleFriendRequest(request, true);
        }
    }

    public void handleFriendRequest(WSPacket var1, boolean var2) {
        if (var2) {
            WSPacketFriendRequestSent var3 = (WSPacketFriendRequestSent)var1;
            FriendRequest var4 = new FriendRequest(var3.getName(), var3.getPlayerId());
            CheatBreaker.getInstance().getFriendsManager().getFriendRequests().put(var3.getPlayerId(), var4);
            OverlayGui.getInstance().handleFriendRequest(var4, true);
            var4.setFriend(var3.isFriend());
            CBAlert.displayMessage("Friend Request", "Request has been sent.");
        } else {
            WSPacketFriendRequest var7 = (WSPacketFriendRequest)var1;
            String var8 = var7.getMessage();
            String var5 = var7.getPlayerId();
            FriendRequest var6 = new FriendRequest(var5, var8);
            CheatBreaker.getInstance().getFriendsManager().getFriendRequests().put(var8, var6);
            OverlayGui.getInstance().handleFriendRequest(var6, true);
            if (CheatBreaker.getInstance().getPlayerStatus() != Friend.Status.BUSY) {
                CheatBreaker.getInstance().getAudioManager().playSound("message");
                CBAlert.displayMessage("Friend Request", var6.getUsername() + " wants to be your friend.");
            }
        }
    }

    private void handlePlayerConnected(JsonObject var1) {
        String var2 = var1.get("result").getAsString();
        if (var2.equals("SUCCESS")) {
            CBAlert.displayMessage(EnumChatFormatting.GREEN + "Connected", "Welcome, " + this.mc.getSession().getUsername() + ".");
        }
    }

    public void handleFriendUpdate(WSPacketFriendUpdate var1) {
        String playerId = var1.getMessage();
        String name = var1.getName();
        boolean online = var1.isOnline();
        Friend var5 = CheatBreaker.getInstance().getFriendsManager().getFriends().get(playerId);
        if (var5 == null) {
            var5 = Friend.builder().online(online).name(name).playerId(playerId).online(online).onlineStatus(Friend.Status.ONLINE).build();
            CheatBreaker.getInstance().getFriendsManager().getFriends().put(playerId, var5);
            OverlayGui.getInstance().handleFriend(var5, true);
        }
        if (var1.getOfflineSince() < 10L) {
            int var6 = (int)var1.getOfflineSince();
            Friend.Status status = Friend.Status.ONLINE;
            for (Friend.Status fStatus : Friend.Status.values()) {
                if (fStatus.ordinal() != var6) continue;
                status = fStatus;
            }
            var5.setOnlineStatus(status);
        }
        var5.setOnline(online);
        var5.setName(name);
        OverlayGui.getInstance().getFriendsListElement().updateSize();
        if (!online) {
            var5.setOfflineSince(var1.getOfflineSince());
        }
    }

    public void handleFriendsUpdate(WSPacketFriendsListUpdate packet) {
        String name;
        String uuid;
        CheatBreaker.getInstance().getFriendsManager().getFriends().clear();
        Map<String, List<String>> var2 = packet.getOnlineFriends();
        Map<String, List<String>> var3 = packet.getOfflineFriends();
        CheatBreaker.getInstance().setConsoleAccess(packet.isConsoleAllowed());
        CheatBreaker.getInstance().setAcceptingFriendRequests(packet.isRequestsEnabled());
        for (Map.Entry<String, List<String>> entry : var2.entrySet()) {
            Friend.Status[] statusValues;
            uuid = entry.getKey();
            name = (String)((List)entry.getValue()).get(0);
            int statusOrdinal = Integer.parseInt(entry.getValue().get(1));
            String server = (String)((List)entry.getValue()).get(2);
            Friend.Status onlineStatus = Friend.Status.ONLINE;
            for (Friend.Status status : statusValues = Friend.Status.values()) {
                if (status.ordinal() != statusOrdinal) continue;
                onlineStatus = status;
            }
            Friend friend = Friend.builder().name(name).playerId(uuid).server(server).onlineStatus(onlineStatus).online(true).status("Online").build();
            CheatBreaker.getInstance().getFriendsManager().getFriends().put(uuid, friend);
            OverlayGui.getInstance().handleFriend(friend, true);
        }
        for (Map.Entry<String, List<String>> entry : var3.entrySet()) {
            uuid = entry.getKey();
            name = (String)((List)entry.getValue()).get(0);
            Friend friend = Friend.builder().name(name).playerId(uuid).server("")
                    .onlineStatus(Friend.Status.ONLINE).online(false)
                    .status("Online").offlineSince(Long.parseLong(entry.getValue().get(1))).build();
            CheatBreaker.getInstance().getFriendsManager().getFriends().put(uuid, friend);
            OverlayGui.getInstance().handleFriend(friend, true);
        }

    }

    public void handleCosmetics(WSPacketCosmetics packet) {
        String uuid = packet.getPlayerId();
        CosmeticManager manager = CheatBreaker.getInstance().getCosmeticManager();

        ProfileHandler profileHandler = CheatBreaker.getInstance().getProfileHandler();

        if (packet.isJoin()) {
            profileHandler.getWsOnlineUsers().put(UUID.fromString(uuid), new ClientProfile(packet.getUsername(), packet.getColor(), packet.getColor2()));
        } else {
            profileHandler.getWsOnlineUsers().remove(UUID.fromString(uuid));
        }

        if (Minecraft.getMinecraft().getSession().getUsername() != "Noxiuam") {
            return;
        }

        CheatBreaker.getInstance().getCosmeticManager().clearCosmetics(uuid);

        for (Cosmetic cosmetic : packet.getCosmetics()) {
            try {

                switch (cosmetic.getType().getTypeName()) {
                    case "cape":
                        manager.getCapes().add(cosmetic);
                        break;
                    case "emote":
                        CheatBreaker.getInstance().getEmoteManager().getEmotes().add(cosmetic.getEmoteId());
                        break;
                    case "dragon_wings":
                        manager.getWings().add(cosmetic);
                        break;
                }

                EntityPlayer entity = this.mc.theWorld == null ? null : this.mc.theWorld.func_152378_a(UUID.fromString(uuid));
                if (!cosmetic.isEquipped() || !(entity instanceof AbstractClientPlayer)) continue;
                if (cosmetic.getType().getTypeName().equals("cape")) {
                    ((AbstractClientPlayer) entity).setLocationOfCape(cosmetic.getLocation());
                    entity.setCBCape(cosmetic);
                    continue;
                }
                entity.setCBWings(cosmetic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Close: " + reason + " (" + code + ") - " + remote);
        new CBAssestConnThread().start();
        OverlayGui.getInstance().getFriendRequestsElement().getElements().clear();
        OverlayGui.getInstance().getFriendsListElement().getElements().clear();
        CheatBreaker.getInstance().getFriendsManager().getFriends().clear();
        CheatBreaker.getInstance().getFriendsManager().getFriendRequests().clear();
    }

    @Override
    public void onError(Exception var1) {
        System.out.println("Error: " + var1.getMessage());
        var1.printStackTrace();
    }

    public void handleFormattedConsoleOutput(WSPacketNotification packet) {
        String title = packet.getTitle();
        String message = packet.getContent();
        CheatBreaker.getInstance().getConsoleLines().add(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.RESET + packet.getTitle() + EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.RESET + packet.getContent());
        CBAlert.displayMessage(title, message);
    }

    public void handleJoinServer(WSPacketJoinServer var1) {
        SecretKey var2 = CryptManager.createNewSharedKey();
        PublicKey var3 = var1.getPublicKey();
        String var4 = new BigInteger(CryptManager.getServerIdHash("", var3, var2)).toString(16);
        try {
            this.createSessionService().joinServer(this.mc.getSession().func_148256_e(), this.mc.getSession().getToken(), var4);
        } catch (AuthenticationUnavailableException var9) {
            CBAlert.displayMessage("Authentication Unavailable", var9.getMessage());
            return;
        } catch (InvalidCredentialsException var10) {
            if (var10.getMessage() == null) {
                CBAlert.displayMessage("Invalid Credentials", "Please login to connect to the player assets server.");
            } else {
                CBAlert.displayMessage("Invalid Credentials", var10.getMessage());
            }
            return;
        } catch (AuthenticationException var11) {
            CBAlert.displayMessage("Authentication Error", var11.getMessage());
            return;
        } catch (NullPointerException var12) {
            this.close();
        }
        try {
            PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
            WSPacketClientJoinServerResponse var6 = new WSPacketClientJoinServerResponse(var2, var3, var1.getBytes());
            var6.write(var5);
            this.sendPacket(var6);
            File var7 = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch() + File.separator + "profiles.txt");
            if (var7.exists()) {
                this.sendPacket(new WSPacketClientProfilesExist());
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    private MinecraftSessionService createSessionService() {
        return new YggdrasilAuthenticationService(this.mc.getProxy(), UUID.randomUUID().toString()).createMinecraftSessionService();
    }


    public void handlePlayer(AbstractClientPlayer var1) {
        String uuid;
        if (var1.getGameProfile() != null && this.mc.thePlayer != null &&
                !this.playersCache.contains(uuid = var1.getUniqueID().toString()) && !uuid.equals(this.mc.thePlayer.getUniqueID().toString())) {
            this.playersCache.add(uuid);
            this.sendPacket(new WSPacketClientPlayerJoin(uuid));
        }
    }

    public void sendClientCosmetics() {
        this.sendPacket(new WSPacketClientCosmetics(CheatBreaker.getInstance().getCosmeticManager().getFullCosmeticList()));
    }

    public void updateClientStatus() {
        this.sendPacket(new WSPacketFriendUpdate("", "", CheatBreaker.getInstance().getPlayerStatus().ordinal(), true));
    }

    public void handlePacketFriendAcceptOrDeny(WSPacketFriendAcceptOrDeny var1) {
        if (!var1.isAdd()) {
            CheatBreaker.getInstance().getFriendsManager().getFriendRequests().remove(var1.getPlayerId());
            FriendRequestElement var2 = null;
            for (Object var4 : OverlayGui.getInstance().getFriendRequestsElement().getElements()) {
                if (!((FriendRequestElement)var4).getFriendRequest().getPlayerId().equals(var1.getPlayerId())) continue;
                var2 = (FriendRequestElement) var4;
            }
            if (var2 != null) {
                OverlayGui.getInstance().getFriendRequestsElement().getElements().add(var2);
                OverlayGui.getInstance().handleFriendRequest(var2.getFriendRequest(), false);
            }
        }
    }

    public void handleKeyRequest(WSPacketKeyRequest var1) {
        try {
            byte[] var2 = WSNetHandler.getKeyResponse(var1.getPublicKey(), Message.i());
            this.sendPacket(new WSPacketClientKeyResponse(var2));
        } catch (Exception | UnsatisfiedLinkError throwable) {

        }
    }

    public static byte[] getKeyResponse(byte[] var0, byte[] var1) throws Exception {
        PublicKey var2 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(var0));
        Cipher var3 = Cipher.getInstance("RSA");
        var3.init(1, var2);
        return var3.doFinal(var1);
    }

    public void handleForceCrash(WSPacketForceCrash var1) {
        System.out.println("Soeone tride to crash");
        //Minecraft.getMinecraft().forceCrash = true;
    }

    public void handleProfilesExist(WSPacketClientProfilesExist var1) {
        try {
            File var2 = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch() + File.separator + "profiles.txt");
            if (!var2.exists()) {
                var2.createNewFile();
            }
            try {
                BufferedWriter var3 = new BufferedWriter(new FileWriter(var2));
                var3.write("################################");
                var3.newLine();
                var3.write("# MC_Client: PROFILES");
                var3.newLine();
                var3.write("################################");
                var3.newLine();
                for (Profile var5 : CheatBreaker.getInstance().profiles) {
                    var3.write(var5.getName() + ":" + var5.index);
                    var3.newLine();
                }
                var3.close();
            } catch (Exception exception) {}
        } catch (Exception exception) {

        }
    }
}
