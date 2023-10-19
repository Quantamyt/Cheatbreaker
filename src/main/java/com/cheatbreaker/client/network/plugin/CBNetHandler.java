package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.ClickStateEvent;
import com.cheatbreaker.client.event.impl.ConnectEvent;
import com.cheatbreaker.client.event.impl.DisconnectEvent;
import com.cheatbreaker.client.event.impl.PluginMessageEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.MiniMapRules;
import com.cheatbreaker.client.module.impl.normal.ModuleMiniMap;
import com.cheatbreaker.client.module.impl.normal.hud.ModuleScoreboard;
import com.cheatbreaker.client.module.impl.normal.hud.cooldowns.ModuleCooldowns;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNametags;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;
import com.cheatbreaker.client.network.plugin.server.*;
import com.cheatbreaker.client.network.plugin.shared.PacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.PacketRemoveWaypoint;
import com.cheatbreaker.client.util.render.hologram.Hologram;
import com.cheatbreaker.client.util.render.teammates.CBTeammate;
import com.cheatbreaker.client.util.render.title.CBTitle;
import com.cheatbreaker.client.util.render.title.TitleType;
import com.cheatbreaker.client.util.voicechat.VoiceChannel;
import com.cheatbreaker.client.util.voicechat.VoiceUser;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.thevoxelbox.voxelmap.MapSettingsManager;
import com.thevoxelbox.voxelmap.VoxelMap;
import com.thevoxelbox.voxelmap.WaypointManager;
import com.thevoxelbox.voxelmap.util.Waypoint;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CBNetHandler implements ICBNetHandlerClient {
    final CheatBreaker cb = CheatBreaker.getInstance();
    private boolean registeredClient = false;
    private boolean registeredBinary = false;
    private boolean serverHandlesWaypoints;
    private boolean voiceChatEnabled = false;
    private List<VoiceChannel> voiceChannels;
    private VoiceChannel voiceChannel;
    private boolean competitiveGamemode;
    private final List<UUID> voiceusers = new ArrayList<>();
    private final List<UUID> mutedUsers = new ArrayList<>();
    private String world = "";
    private final Map<UUID, List<String>> nametagsMap = new HashMap<>();
    private boolean onALunarServer = false;
    private boolean isLegacyLunar = false;

    public void onClickEvent(ClickStateEvent var1) {
    }

    public void onConnect(final ConnectEvent connectEvent) {
        this.registeredBinary = false;
    }

    public void onPluginMessage(PluginMessageEvent customPayload) {
        try {
            if (customPayload.getChannelName().equals("REGISTER")) {
                String payload = new String(customPayload.getBufferData(), Charsets.UTF_8);
                this.registeredClient = payload.contains(this.cb.getCheatBreakerPluginMessageChannel());
                if (!this.registeredClient) {
                    this.onALunarServer = payload.contains(this.cb.getLatestLunarPluginMessageChannel());
                    if (!this.onALunarServer) {
                        boolean legacy = payload.contains(this.cb.getLunarPluginMessageChannel());
                        this.onALunarServer = legacy;
                        if (legacy) {
                            this.isLegacyLunar = true;
                        }
                    }
                }
                this.registeredBinary = payload.contains(this.cb.getPluginBinaryChannel());
                PacketBuffer wrapper = new PacketBuffer(Unpooled.buffer());
                wrapper.writeBytes(this.cb.getCheatBreakerPluginMessageChannel().getBytes(Charsets.UTF_8));
                if (Minecraft.getMinecraft().getNetHandler() != null && this.registeredClient) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", wrapper));
                }

                PacketBuffer wrapper2 = new PacketBuffer(Unpooled.buffer());
                wrapper2.writeBytes(this.isLegacyLunar ? this.cb.getLunarPluginMessageChannel().getBytes(Charsets.UTF_8) : this.cb.getLatestLunarPluginMessageChannel().getBytes(Charsets.UTF_8));
                if (Minecraft.getMinecraft().getNetHandler() != null) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", wrapper2));
                }
                this.initialize();
            } else if (customPayload.getChannelName().equals(this.cb.getCheatBreakerPluginMessageChannel()) || customPayload.getChannelName().equals(this.cb.getLunarPluginMessageChannel()) || customPayload.getChannelName().equals(this.cb.getLatestLunarPluginMessageChannel())) {
                CBPacket packet = CBPacket.handle(this, customPayload.getBufferData());
                if (packet != null) {
                    packet.process(this);
                }
                if (this.cb.getGlobalSettings().isDebug) {
                    ChatComponentText debugPrefix = new ChatComponentText(EnumChatFormatting.GRAY + "Received: " + EnumChatFormatting.WHITE + packet.getClass().getSimpleName());
                    debugPrefix.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(new Gson().toJson(packet))));
                    debugPrefix.setBranded(true);
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(debugPrefix);
                }
            }
        } catch (AssertionError | Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initialize() {
        this.cb.getModuleManager().miniMapMod.getVoxelMap().getWaypointManager().getWaypoints().removeIf(var0 -> var0.isAutomated);
        this.voiceChatEnabled = false;
        this.serverHandlesWaypoints = false;
        this.voiceChannels = null;
        this.voiceChannel = null;
        this.competitiveGamemode = false;
        this.world = "";
        this.mutedUsers.clear();
        for (AbstractModule var2 : this.cb.getModuleManager().staffMods) {
            ((StaffMod)var2).disableStaffModule();
        }
        this.cb.getWorldBorderManager().clearBorders();
        this.resetWorldStuff();
        ModuleMiniMap.rule = MiniMapRules.FORCED_OFF;
        ModuleScoreboard.minimapRule = MiniMapRules.NEUTRAL;
        this.cb.getModuleManager().teammatesMod.getTeammates().clear();
        Hologram.getHolograms().clear();
        VoxelMap var3 = this.cb.getModuleManager().miniMapMod.getVoxelMap();
        if (Minecraft.getMinecraft().thePlayer != null && var3.getWaypointManager() != null) {
            var3.getWaypointManager().getWaypoints().removeIf(var0 -> var0.packetWaypoint);
            ((WaypointManager)var3.getWaypointManager()).getOld2dWayPts().removeIf(var0 -> var0.packetWaypoint);
            if (((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer() != null) {
                ((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer().wayPts.removeIf(var0 -> var0.packetWaypoint);
                MapSettingsManager.instance.saveAll();
                var3.getWaypointManager().check2dWaypoints();
            }
        }
    }

    public void resetWorldStuff() {
        this.cb.getTitleManager().getTitles().clear();
        StaffModuleNametags.setMap(null);
    }

    public void sendPacket(CBPacket var1) {
        if (var1 != null && this.cb.getGlobalSettings().isDebug) {
//            ChatComponentText var2 = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
            ChatComponentText var3 = new ChatComponentText(EnumChatFormatting.GRAY + "Sent: " + EnumChatFormatting.WHITE + var1.getClass().getSimpleName());
            var3.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(new Gson().toJson(var1))));
//            var2.appendSibling(var3);
            var3.setBranded(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(var3);
        }
        C17PacketCustomPayload var4 = new C17PacketCustomPayload(this.cb.getLunarPluginMessageChannel(), CBPacket.getPacketData(var1));
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(var4);
    }

    public void onDisconnect(DisconnectEvent var1) {
        this.registeredClient = false;
        this.registeredBinary = false;
    }

    @Override
    public void handleAddWaypoint(PacketAddWaypoint var1) {
        int xPos = var1.getX();
        int yPos = var1.getY();
        int zPos = var1.getZ();
        VoxelMap voxelMap = this.cb.getModuleManager().miniMapMod.getVoxelMap();
        if (!voxelMap.getWaypointManager().getWaypoints().stream().anyMatch(lWay ->
                lWay.name.equals(var1.getName()) && lWay.sWorld.equals(var1.getWorld()))) {
            Color color = new Color(var1.getColor());
            float red = (float)color.getRed() / 255.0f;
            float green = (float)color.getGreen() / 255.0f;
            float blue = (float)color.getBlue() / 255.0f;
            TreeSet<Integer> dimensions = new TreeSet<Integer>();
            dimensions.add(-1);
            dimensions.add(0);
            dimensions.add(1);
            System.out.println("Received waypoint (" + var1.getName() + ")[x" + xPos + ",y" +
                    yPos + ",z" + zPos + "][r" + red + ",g" + green + ",b" + blue + "]");
            Waypoint waypoint = new Waypoint(var1.getName(), xPos, zPos, yPos, true, red, green, blue, "", voxelMap.getWaypointManager().getCurrentSubworldDescriptor(), dimensions, true, true);
            waypoint.forced = var1.isForced();
            waypoint.sWorld = var1.getWorld();
            waypoint.packetWaypoint = true;
            voxelMap.getWaypointManager().addWaypoint(waypoint);
        }
    }

    @Override
    public void handleRemoveWaypoint(PacketRemoveWaypoint var1) {
        try {
            String var2 = var1.getName();
            VoxelMap var3 = this.cb.getModuleManager().miniMapMod.getVoxelMap();
            var3.getWaypointManager().getWaypoints().removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
            ((WaypointManager)var3.getWaypointManager()).getOld2dWayPts().removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
            ((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer().wayPts.removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
            MapSettingsManager.instance.saveAll();
            var3.getWaypointManager().check2dWaypoints();
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    @Override
    public void handleCooldown(PacketCooldown var1) {
        ModuleCooldowns.addCooldown(var1.getMessage(), var1.getDurationMs(), var1.getIconId());
    }

    @Override
    public void handleNotification(PacketNotification var1) {
        this.cb.getModuleManager().notificationsMod.send(
                var1.getLevel(), var1.getMessage(), var1.getDurationMs());
    }

    @Override
    public void handleStaffModState(PacketStaffModState var1) {
        for (AbstractModule var3 : this.cb.getModuleManager().staffMods) {
            if (!var3.getName().equals(var1.getId().replaceAll("_", "").toLowerCase())) continue;
            var3.setStaffModuleEnabled(var1.getState());
        }
    }

    @Override
    public void handleUpdateNametags(PacketUpdateNametags var1) {
        if (var1.getPlayersMap() != null) {
            StaffModuleNametags.setMap(new HashMap());
            for (Map.Entry<UUID, List<String>> var3 : var1.getPlayersMap().entrySet()) {
                StaffModuleNametags.getMap().put(var3.getKey().toString(), var3.getValue());
            }
        } else {
            StaffModuleNametags.setMap(null);
        }
    }

    @Override
    public void handleTeammates(PacketTeammates packet) {
        Map<UUID, Map<String, Double>> players = packet.getPlayers();
        UUID leader = packet.getLeader();
        long lastMs = packet.getLastMs();
        if (!(!(Boolean) this.cb.getGlobalSettings().enableTeamView.getValue()
                || players == null || players.isEmpty() || players.size() == 1 && players.containsKey(Minecraft.getMinecraft().thePlayer.getUniqueID()))) {
            int var6 = 0;
            for (Map.Entry<UUID, Map<String, Double>> teammates : players.entrySet()) {
                CBTeammate teammate = this.cb.getModuleManager().teammatesMod.createTeammate(teammates.getKey().toString());
                if (teammate == null) {
                    teammate = new CBTeammate(teammates.getKey().toString(), leader != null && leader.equals(teammates.getKey()));
                    this.cb.getModuleManager().teammatesMod.getTeammates().add(teammate);
                    Random var10 = new Random();
                    if (var6 < this.cb.getModuleManager().teammatesMod.getColors().length) {
                        teammate.setColor(new Color(this.cb.getModuleManager().teammatesMod.getColors()[var6]));
                    } else {
                        float var11 = var10.nextFloat();
                        float var12 = var10.nextFloat();
                        float var13 = var10.nextFloat() / 2.0f;
                        teammate.setColor(new Color(var11, var12, var13));
                    }
                }
                try {
                    double xPos = (Double)((Map)teammates.getValue()).get("x");
                    double yPos = (Double)((Map)teammates.getValue()).get("y") + 2.0;
                    double zPos = (Double)((Map)teammates.getValue()).get("z");
                    teammate.updateTeammate(xPos, yPos, zPos, lastMs);
                } catch (Exception var16) {
                    var16.printStackTrace();
                }
                ++var6;
            }
            this.cb.getModuleManager().teammatesMod.getTeammates().removeIf(var1x ->
                    !players.containsKey(UUID.fromString(var1x.getUuid())));
        } else {
            this.cb.getModuleManager().teammatesMod.getTeammates().clear();
        }
    }

    @Override
    public void handleOverrideNametags(PacketOverrideNametags var1) {
        if (var1.getTags() == null) {
            this.nametagsMap.remove(var1.getUUID());
        } else {
            Collections.reverse(var1.getTags());
            this.nametagsMap.put(var1.getUUID(), var1.getTags());
        }
    }

    @Override
    public void handleAddHologram(PacketAddHologram var1) {
        Hologram var2 = new Hologram(var1.getUUID(), var1.getX(), var1.getY(), var1.getZ());
        Hologram.getHolograms().add(var2);
        var2.setLines(var1.getLines().toArray(new String[0]));
    }

    @Override
    public void handleUpdateHologram(PacketUpdateHologram var1) {
        Hologram.getHolograms().stream().filter(var1x ->
                var1x.getUUID().equals(var1.getUUID())).
                forEach(var1x -> var1x.setLines(var1.getLines().toArray(new String[0])));
    }

    @Override
    public void handleRemoveHologram(PacketRemoveHologram var1) {
        Hologram.getHolograms().removeIf(var1x -> var1x.getUUID().equals(var1.getUUID()));
    }

    @Override
    public void handleTitle(PacketTitle var1) {
        TitleType var2 = TitleType.SUBTITLE;
        if (var1.getType().equalsIgnoreCase("subtitle")) {
            var2 = TitleType.TITLE;
        }
        this.cb.getTitleManager().getTitles().add(
                new CBTitle(var1.getMessage(), var2, var1.getScale(),
                        var1.getDisplayTimeMs(), var1.getFadeInTimeMs(), var1.getFadeOutTimeMs()));
    }

    @Override
    public void handleServerRule(PacketServerRule var1) {
        switch (var1.getRule().ordinal()) {
            case 0:
                this.logger("Voice is: " + (var1.getBoolean() ? "enabled" : "disabled"));
                this.voiceChatEnabled = var1.getBoolean();
                break;
            case 1:
                String value;
                switch (value = var1.getRuleName()) {
                    case "NEUTRAL":
                        ModuleMiniMap.rule = MiniMapRules.NEUTRAL;
                        return;
                    case "FORCED_OFF":
                        ModuleMiniMap.rule = MiniMapRules.FORCED_OFF;
                        return;
                }
                return;
            case 2:
                this.serverHandlesWaypoints = var1.getBoolean();
                break;
            case 3:
                this.competitiveGamemode = var1.getBoolean();
        }
    }

    @Override
    public void handleVoice(PacketVoice var1) {
        this.cb.getModuleManager().voiceChat.updateUser(var1.getUUID());
    }

    @Override
    public void handleVoiceChannel(PacketVoiceChannel var1) {
        this.logger("Voice Channel Received: " + var1.getName());
        this.logger("Channel has " + var1.getPlayers().size() + " members");
        if (!this.doesVoiceChannelExist(var1.getUUID())) {
            if (this.voiceChannels == null) {
                this.voiceChannels = new ArrayList<VoiceChannel>();
            }
            VoiceChannel var2 = new VoiceChannel(var1.getUUID(), var1.getName());
            this.voiceChannels.add(var2);
            ArrayList<VoiceUser> var3 = new ArrayList<VoiceUser>();
            for (Map.Entry<UUID, String> var5 : var1.getPlayers().entrySet()) {
                this.logger("Added member [" + var5.getValue() + "]");
                VoiceUser var6 = var2.createUser(var5.getKey(), var5.getValue());
                if (var6 == null) continue;
                var3.add(var6);
            }
            this.addMutedUsers(var3);
            for (Map.Entry<UUID, String> var5 : var1.getListening().entrySet()) {
                this.logger("Added listener [" + var5.getValue() + "]");
                var2.addListener(var5.getKey(), var5.getValue());
            }
        }
    }

    @Override
    public void handleVoiceChannelUpdate(PacketVoiceChannelUpdate var1) {
        this.logger("Channel Update: " + var1.getName() + " (" + var1.getStatus() + ")");
        if (this.voiceChannels != null) {
            VoiceChannel var2 = this.getVoiceChannel(var1.getChannelUUID());
            if (var2 == null) {
                this.logger(var1.getChannelUUID().toString());
            } else {
                switch (var1.getStatus()) {
                    case 0:
                        VoiceUser var3 = var2.createUser(var1.getUUID(), var1.getName());
                        if (var3 == null) break;
                        this.addMutedUsers(ImmutableList.of(var3));
                        break;
                    case 1:
                        var2.removeUser(var1.getUUID());
                        break;
                    case 2:
                        if (!var1.getUUID().toString().equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                            if (this.voiceChannel == var2) {
//                                ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + var1.getName() + EnumChatFormatting.AQUA + " joined " + var2.getName() + " channel. Press '" + Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindOpenVoiceMenu.getKeyCode()) + "'!" + EnumChatFormatting.RESET);
//                                Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(var4);
                            }
                        } else {
                            this.voiceChannel = var2;
                            for (VoiceChannel var5 : this.voiceChannels) {
                                var5.removeListeners(var1.getUUID());
                            }
//                            ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + "Joined " + var2.getName() + " channel. Press '" + Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindVoicePushToTalk.getKeyCode()) + "' to talk!" + EnumChatFormatting.RESET);
//                            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(var4);
                        }
                        var2.addListener(var1.getUUID(), var1.getName());
                        break;
                    case 3:
                        if (this.voiceChannel == var2 && !var1.getUUID().toString().equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
//                            ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + var1.getName() + EnumChatFormatting.AQUA + " left " + var2.getName() + " channel. Press '" + Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindOpenVoiceMenu.getKeyCode()) + "'!" + EnumChatFormatting.RESET);
//                            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(var4);
                        }
                        var2.removeListeners(var1.getUUID());
                }
            }
        }
    }

    private void addMutedUsers(List<VoiceUser> var1) {
        for (VoiceUser var3 : var1) {
            if (var3 == null || !this.voiceusers.contains(var3.getPlayerUUID())
                    || this.mutedUsers.contains(var3.getPlayerUUID())) continue;
            this.mutedUsers.add(var3.getPlayerUUID());
            this.sendPacket(new PacketVoiceMute(var3.getPlayerUUID()));
        }
    }

    @Override
    public void handleDeleteVoiceChannel(PacketDeleteVoiceChannel var1) {
        this.logger("Deleted channel: " + var1.getChannelId().toString());
        if (this.voiceChannels != null) {
            this.voiceChannels.removeIf(var1x -> var1x.getUuid().equals(var1.getChannelId()));
        }
        if (this.voiceChannel != null && this.voiceChannel.getUuid().equals(var1.getChannelId())) {
            this.voiceChannel = null;
        }
    }

    @Override
    public void handleUpdateWorld(PacketUpdateWorld var1) {
        this.logger("World Update: " + var1.getWorld());
        this.world = var1.getWorld();
    }

    @Override
    public void handleServerUpdate(PacketServerUpdate var1) {
        this.logger("Retrieved " + var1.getServer());
        this.cb.updateServerInfo(var1.getServer());
//        this.cb.updateTheRPC(Minecraft.getMinecraft().currentServerData.serverIP, var1.getServer(), false);
    }

    @Override
    public void handleWorldBorder(PacketWorldBorder packet) {
        this.cb.getWorldBorderManager().createBorder(packet.getId(), packet.getWorld(), packet.getColor(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.canShrinkExpand(), packet.doesCancelExit());
    }

    @Override
    public void handleWorldBorderUpdate(PacketWorldBorderUpdate packet) {
        this.cb.getWorldBorderManager().updateBorder(packet.getId(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.getDurationTicks());
    }

    @Override
    public void handleWorldBorderRemove(PacketWorldBorderRemove packet) {
        this.cb.getWorldBorderManager().removeBorder(packet.getId());
    }

    private boolean doesVoiceChannelExist(UUID var1) {
        return this.getVoiceChannel(var1) != null;
    }

    private VoiceChannel getVoiceChannel(UUID var1) {
        VoiceChannel var3;
        if (this.voiceChannels == null) {
            return null;
        }
        Iterator<VoiceChannel> var2 = this.voiceChannels.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while (!(var3 = var2.next()).getUuid().equals(var1));
        return var3;
    }

    public VoiceUser getVoiceUser(UUID var1) {
        if (this.voiceChannels != null && this.voiceChannel != null) {
            VoiceUser var3;
            Iterator<VoiceUser> var2 = this.voiceChannel.getUsers().iterator();
            do {
                if (var2.hasNext()) continue;
                return null;
            } while (!(var3 = var2.next()).getPlayerUUID().equals(var1));
            return var3;
        }
        return null;
    }

    private void logger(String var1) {
        System.out.println("\u001b[31m[CheatBreaker]\u001b[0m " + var1);
    }

    public boolean hasRegisteredBinary() {
        return this.registeredBinary;
    }

    public boolean doesServerHandleWaypoints() {
        return this.serverHandlesWaypoints;
    }

    public boolean isVoiceChatEnabled() {
        return this.voiceChatEnabled;
    }

    public List<VoiceChannel> getVoiceChannels() {
        return this.voiceChannels;
    }

    public VoiceChannel getCurrentVoiceChannel() {
        return this.voiceChannel;
    }

    public boolean isCompetitveGamemode() {
        return this.competitiveGamemode;
    }

    public List<UUID> getVoiceUsers() {
        return this.voiceusers;
    }

    public String getWorldUID() {
        return this.world;
    }

    public Map<UUID, List<String>> getNametagsMap() {
        return this.nametagsMap;
    }
}
