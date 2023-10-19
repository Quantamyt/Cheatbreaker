package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.network.ConnectEvent;
import com.cheatbreaker.client.event.impl.network.DisconnectEvent;
import com.cheatbreaker.client.event.impl.network.PluginMessageEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.MiniMapRules;
import com.cheatbreaker.client.module.impl.normal.hud.ModuleScoreboard;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleZansMiniMap;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNameTags;
import com.cheatbreaker.client.network.messages.Message;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;
import com.cheatbreaker.client.network.plugin.server.*;
import com.cheatbreaker.client.network.plugin.shared.CBPacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.CBPacketRemoveWaypoint;
import com.cheatbreaker.client.util.manager.BranchManager;
import com.cheatbreaker.client.util.render.hologram.Hologram;
import com.cheatbreaker.client.util.render.teammates.CBTeammate;
import com.cheatbreaker.client.util.render.title.CBTitle;
import com.cheatbreaker.client.util.render.title.data.TitleType;
import com.cheatbreaker.client.util.voicechat.data.VoiceChannel;
import com.cheatbreaker.client.util.voicechat.data.VoiceUser;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @see ICBNetHandlerClient
 *
 * This class contians methods for handling of Plugin Message packets.
 */
@Getter
public class CBNetHandler implements ICBNetHandlerClient {
    final CheatBreaker cheatBreaker = CheatBreaker.getInstance();

    private VoiceChannel currentVoiceChannel;

    private final Map<UUID, List<String>> customNameTags = new HashMap<>();

    private final List<UUID> playersInVoiceChannel = new ArrayList<>();
    private final List<UUID> mutedPlayers = new ArrayList<>();
    private List<VoiceChannel> voiceChannels; // Not meant to be initalized here, it will be later on down in the class.

    private boolean isUsingLegacyLunarChannel = false;
    private boolean registeredClient = false;
    private boolean registeredBinary = false;
    private boolean voiceChatEnabled = false;
    private boolean competitiveGameMode;
    private boolean serverHandlesWaypoints;

    private String worldUID = "";

    public CBNetHandler() {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Network Manager");
    }

    public void onConnect(final ConnectEvent ignored) {
        this.registeredBinary = false;
    }

    public void onPluginMessage(PluginMessageEvent customPayload) {
        try {
            if (customPayload.getChannelName().equals("REGISTER")) {
                String payload = new String(customPayload.getBufferData(), Charsets.UTF_8);
                this.registeredClient = payload.contains(this.cheatBreaker.getCheatBreakerPluginMessageChannel());
                if (!this.registeredClient) {
                    boolean onALunarServer = payload.contains(this.cheatBreaker.getLatestLunarPluginMessageChannel());
                    if (!onALunarServer) {
                        boolean legacy = payload.contains(this.cheatBreaker.getLunarPluginMessageChannel());
                        if (legacy) {
                            this.isUsingLegacyLunarChannel = true;
                        }
                    }
                }
                this.registeredBinary = payload.contains(this.cheatBreaker.getPluginBinaryChannel());
                PacketBuffer wrapper = new PacketBuffer(Unpooled.buffer());
                wrapper.writeBytes(this.cheatBreaker.getCheatBreakerPluginMessageChannel().getBytes(Charsets.UTF_8));
                if (Minecraft.getMinecraft().getNetHandler() != null && this.registeredClient) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", wrapper));
                }

                PacketBuffer wrapper2 = new PacketBuffer(Unpooled.buffer());
                wrapper2.writeBytes(this.isUsingLegacyLunarChannel ? this.cheatBreaker.getLunarPluginMessageChannel().getBytes(Charsets.UTF_8) : this.cheatBreaker.getLatestLunarPluginMessageChannel().getBytes(Charsets.UTF_8));
                if (Minecraft.getMinecraft().getNetHandler() != null) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", wrapper2));
                }
                this.initialize();
            } else if (customPayload.getChannelName().equals(this.cheatBreaker.getCheatBreakerPluginMessageChannel())
                    || customPayload.getChannelName().equals(this.cheatBreaker.getLunarPluginMessageChannel())
                    || customPayload.getChannelName().equals(this.cheatBreaker.getLatestLunarPluginMessageChannel())) {

                CBPacket packet = CBPacket.handle(this, customPayload.getBufferData());
                if (this.cheatBreaker.getGlobalSettings().isDebug && packet != null) {
                    packet.process(this);
                    Message.z(true, packet);
                }
            }
        } catch (AssertionError | Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initialize() {
//        this.cb.getModuleManager().miniMapMod.getVoxelMap().getWaypointManager().getWaypoints().removeIf(var0 -> var0.isAutomated);
        this.voiceChatEnabled = false;
        this.serverHandlesWaypoints = false;
        this.voiceChannels = null;
        this.currentVoiceChannel = null;
        this.competitiveGameMode = false;
        this.worldUID = "";
        this.mutedPlayers.clear();
        for (StaffMod staffMod : this.cheatBreaker.getModuleManager().staffMods) {
            staffMod.disableStaffModule();
        }
        this.cheatBreaker.getWorldBorderManager().clearBorders();

        this.cheatBreaker.getTitleManager().getTitles().clear();
        StaffModuleNameTags.setNameTagList(null);

        ModuleZansMiniMap.rule = MiniMapRules.FORCED_OFF;
        ModuleScoreboard.minimapRule = MiniMapRules.NEUTRAL;

        this.cheatBreaker.getModuleManager().teammatesMod.getTeammates().clear();
        Hologram.getHolograms().clear();

//        VoxelMap var3 = this.cb.getModuleManager().miniMapMod.getVoxelMap();
//        if (Minecraft.getMinecraft().thePlayer != null && var3.getWaypointManager() != null) {
//            var3.getWaypointManager().getWaypoints().removeIf(var0 -> var0.packetWaypoint);
//            ((WaypointManager)var3.getWaypointManager()).getOld2dWayPts().removeIf(var0 -> var0.packetWaypoint);
//            if (((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer() != null) {
//                ((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer().wayPts.removeIf(var0 -> var0.packetWaypoint);
//                MapSettingsManager.instance.saveAll();
//                var3.getWaypointManager().check2dWaypoints();
//            }
//        }
    }

    public void sendPacket(CBPacket packet) {
        if (packet != null && this.cheatBreaker.getGlobalSettings().isDebug) {
            Message.z(false, packet);
        }

        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeBytes(CBPacket.getPacketData(Objects.requireNonNull(packet)));
        C17PacketCustomPayload payload = new C17PacketCustomPayload(this.cheatBreaker.getLunarPluginMessageChannel(), packetBuffer);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(payload);
    }

    public void onDisconnect(DisconnectEvent ignored) {
        this.registeredClient = false;
        this.registeredBinary = false;
    }

    @Override
    public void handleAddWaypoint(CBPacketAddWaypoint var1) {
        int xPos = var1.getX();
        int yPos = var1.getY();
        int zPos = var1.getZ();
//        VoxelMap voxelMap = this.cb.getModuleManager().miniMapMod.getVoxelMap();
//        if (!voxelMap.getWaypointManager().getWaypoints().stream().anyMatch(lWay ->
//                lWay.name.equals(var1.getName()) && lWay.sWorld.equals(var1.getWorld()))) {
//            Color color = new Color(var1.getColor());
//            float red = (float)color.getRed() / 255.0f;
//            float green = (float)color.getGreen() / 255.0f;
//            float blue = (float)color.getBlue() / 255.0f;
//            TreeSet<Integer> dimensions = new TreeSet<Integer>();
//            dimensions.add(-1);
//            dimensions.add(0);
//            dimensions.add(1);
//            System.out.println("Received waypoint (" + var1.getName() + ")[x" + xPos + ",y" +
//                    yPos + ",z" + zPos + "][r" + red + ",g" + green + ",b" + blue + "]");
//            Waypoint waypoint = new Waypoint(var1.getName(), xPos, zPos, yPos, true, red, green, blue, "", voxelMap.getWaypointManager().getCurrentSubworldDescriptor(), dimensions, true, true);
//            waypoint.forced = var1.isForced();
//            waypoint.sWorld = var1.getWorld();
//            waypoint.packetWaypoint = true;
//            voxelMap.getWaypointManager().addWaypoint(waypoint);
//        }
    }

    @Override
    public void handleRemoveWaypoint(CBPacketRemoveWaypoint var1) {
//        try {
//            String var2 = var1.getName();
//            VoxelMap var3 = this.cb.getModuleManager().miniMapMod.getVoxelMap();
//            var3.getWaypointManager().getWaypoints().removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
//            ((WaypointManager)var3.getWaypointManager()).getOld2dWayPts().removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
//            ((WaypointManager)var3.getWaypointManager()).getEntityWaypointContainer().wayPts.removeIf(var2x -> var2x.sWorld.equals(var1.getWorld()) && var2x.isAutomated && var2x.name.equalsIgnoreCase(var2));
//            MapSettingsManager.instance.saveAll();
//            var3.getWaypointManager().check2dWaypoints();
//        } catch (Exception var4) {
//            var4.printStackTrace();
//        }
    }

    @Override
    public void handleCooldown(CBPacketCooldown var1) {
        CheatBreaker.getInstance().getModuleManager().coolDownsMod.addCooldown(var1.getMessage(), var1.getDurationMs(), var1.getIconId());
    }

    @Override
    public void handleNotification(CBPacketNotification var1) {
        this.cheatBreaker.getModuleManager().notificationsMod.send(
                var1.getLevel(), var1.getMessage(), var1.getDurationMs());
    }

    @Override
    public void handleStaffModState(CBPacketStaffModState var1) {
        for (AbstractModule var3 : this.cheatBreaker.getModuleManager().staffMods) {
            if (!var3.getName().equals(var1.getMod().replaceAll("_", "").toLowerCase())) continue;
            var3.setStaffModuleEnabled(var1.isState());
        }
    }

    @Override
    public void handleNametagsUpdate(CBPacketUpdateNametags var1) {
        if (var1.getPlayersMap() != null) {
            StaffModuleNameTags.setNameTagList(new HashMap<>());
            for (Map.Entry<UUID, List<String>> var3 : var1.getPlayersMap().entrySet()) {
                StaffModuleNameTags.getNameTagList().put(UUID.fromString(var3.getKey().toString()), var3.getValue());
            }
        } else {
            StaffModuleNameTags.setNameTagList(null);
        }
    }

    @Override
    public void handleTeammates(CBPacketTeammates packet) {
        Map<UUID, Map<String, Double>> players = packet.getPlayers();
        UUID leader = packet.getLeader();
        long lastMs = packet.getLastMs();
        if (!(!(Boolean) this.cheatBreaker.getGlobalSettings().enableTeamView.getValue()
                || players == null || players.isEmpty() || players.size() == 1 && players.containsKey(Minecraft.getMinecraft().thePlayer.getUniqueID()))) {
            int var6 = 0;
            for (Map.Entry<UUID, Map<String, Double>> teammates : players.entrySet()) {
                CBTeammate teammate = this.cheatBreaker.getModuleManager().teammatesMod.createTeammate(teammates.getKey().toString());
                if (teammate == null) {
                    teammate = new CBTeammate(teammates.getKey().toString(), leader != null && leader.equals(teammates.getKey()));
                    this.cheatBreaker.getModuleManager().teammatesMod.getTeammates().add(teammate);
                    Random var10 = new Random();
                    if (var6 < this.cheatBreaker.getModuleManager().teammatesMod.getColors().length) {
                        teammate.setColor(new Color(this.cheatBreaker.getModuleManager().teammatesMod.getColors()[var6]));
                    } else {
                        float var11 = var10.nextFloat();
                        float var12 = var10.nextFloat();
                        float var13 = var10.nextFloat() / 2.0f;
                        teammate.setColor(new Color(var11, var12, var13));
                    }
                }
                try {
                    double xPos = (Double) ((Map<?, ?>) teammates.getValue()).get("x");
                    double yPos = (Double) ((Map<?, ?>) teammates.getValue()).get("y") + 2.0;
                    double zPos = (Double) ((Map<?, ?>) teammates.getValue()).get("z");
                    teammate.updateTeammate(xPos, yPos, zPos, lastMs);
                } catch (Exception var16) {
                    var16.printStackTrace();
                }
                ++var6;
            }
            this.cheatBreaker.getModuleManager().teammatesMod.getTeammates().removeIf(var1x ->
                    !players.containsKey(UUID.fromString(var1x.getUuid())));
        } else {
            this.cheatBreaker.getModuleManager().teammatesMod.getTeammates().clear();
        }
    }

    @Override
    public void handleOverrideNametags(CBPacketOverrideNametags packet) {
        if (packet.getTags() == null) {
            this.customNameTags.remove(packet.getPlayerId());
        } else {
            Collections.reverse(packet.getTags());
            if (CheatBreaker.getInstance().getBranchManager().getCurrentBranch().isAboveOrEqual(BranchManager.Branch.DEVELOPMENT))
                System.out.println("playerId=" + packet.getPlayerId() + ", Tags=" + packet.getTags());
            this.customNameTags.put(packet.getPlayerId(), packet.getTags());
        }
    }

    @Override
    public void handleAddHologram(CBPacketAddHologram var1) {
        Hologram var2 = new Hologram(var1.getUuid(), var1.getX(), var1.getY(), var1.getZ());
        Hologram.getHolograms().add(var2);
        var2.setLines(var1.getLines().toArray(new String[0]));
    }

    @Override
    public void handleUpdateHologram(CBPacketUpdateHologram var1) {
        Hologram.getHolograms().stream().filter(var1x ->
                        var1x.getUUID().equals(var1.getUuid())).
                forEach(var1x -> var1x.setLines(var1.getLines().toArray(new String[0])));
    }

    @Override
    public void handleRemoveHologram(CBPacketRemoveHologram var1) {
        Hologram.getHolograms().removeIf(var1x -> var1x.getUUID().equals(var1.getUuid()));
    }

    @Override
    public void handleTitle(CBPacketTitle var1) {
        TitleType var2 = TitleType.SUBTITLE;
        if (var1.getType().equalsIgnoreCase("subtitle")) {
            var2 = TitleType.TITLE;
        }
        this.cheatBreaker.getTitleManager().getTitles().add(
                new CBTitle(var1.getMessage(), var2, var1.getScale(),
                        var1.getDisplayTimeMs(), var1.getFadeInTimeMs(), var1.getFadeOutTimeMs()));
    }

    @Override
    public void handleServerRule(CBPacketServerRule var1) {
        switch (var1.getRule().ordinal()) {
            case 0:
                this.logger("Voice is: " + (var1.isBooleanValue() ? "enabled" : "disabled"));
                this.voiceChatEnabled = var1.isBooleanValue();
                break;
            case 1:
                switch (var1.getStringValue()) {
                    case "NEUTRAL":
                        ModuleZansMiniMap.rule = MiniMapRules.NEUTRAL;
                        return;
                    case "FORCED_OFF":
                        ModuleZansMiniMap.rule = MiniMapRules.FORCED_OFF;
                        return;
                }
                return;
            case 2:
                this.serverHandlesWaypoints = var1.isBooleanValue();
                break;
            case 3:
                this.competitiveGameMode = var1.isBooleanValue();
        }
    }

    @Override
    public void handleVoice(CBPacketVoice var1) {
        this.cheatBreaker.getModuleManager().voiceChat.updateUser(var1.getUuid());
    }

    @Override
    public void handleVoiceChannel(CBPacketVoiceChannel var1) {
        this.logger("Voice Channel Received: " + var1.getName());
        this.logger("Channel has " + var1.getPlayers().size() + " members");
        if (!this.doesVoiceChannelExist(var1.getUuid())) {
            if (this.voiceChannels == null) {
                this.voiceChannels = new ArrayList<>();
            }
            VoiceChannel channels = new VoiceChannel(var1.getUuid(), var1.getName());
            this.voiceChannels.add(channels);
            List<VoiceUser> var3 = new ArrayList<>();
            for (Map.Entry<UUID, String> var5 : var1.getPlayers().entrySet()) {
                this.logger("Added member [" + var5.getValue() + "]");
                VoiceUser user = channels.createUser(var5.getKey(), var5.getValue());
                if (user == null) continue;
                var3.add(user);
            }
            this.addMutedUsers(var3);
            for (Map.Entry<UUID, String> var5 : var1.getListening().entrySet()) {
                this.logger("Added listener [" + var5.getValue() + "]");
                channels.addListener(var5.getKey(), var5.getValue());
            }
        }
    }

    @Override
    public void handleVoiceChannelUpdate(CBPacketVoiceChannelUpdate var1) {
        this.logger("Channel Update: " + var1.getName() + " (" + var1.getStatus() + ")");
        if (this.voiceChannels != null) {
            VoiceChannel var2 = this.getVoiceChannel(var1.getChannelUuid());
            if (var2 == null) {
                this.logger(var1.getChannelUuid().toString());
            } else {
                switch (var1.getStatus()) {
                    case 0:
                        VoiceUser var3 = var2.createUser(var1.getUuid(), var1.getName());
                        if (var3 == null) break;
                        this.addMutedUsers(ImmutableList.of(var3));
                        break;
                    case 1:
                        var2.removeUser(var1.getUuid());
                        break;
                    case 2:
                        if (!var1.getUuid().toString().equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                            if (this.currentVoiceChannel == var2) {
                                ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + var1.getName() + EnumChatFormatting.AQUA + " joined " + var2.getName() + " channel. Press 'Unbound" + /*Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindOpenVoiceMenu.getKeyCode()) + */"'!" + EnumChatFormatting.RESET);
                                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(var4);
                            }
                        } else {
                            this.currentVoiceChannel = var2;
                            for (VoiceChannel var5 : this.voiceChannels) {
                                var5.removeListeners(var1.getUuid());
                            }
                            ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + "Joined " + var2.getName() + " channel. Press 'Unbound" +/* Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindVoicePushToTalk.getKeyCode()) + */"' to talk!" + EnumChatFormatting.RESET);
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(var4);
                        }
                        var2.addListener(var1.getUuid(), var1.getName());
                        break;
                    case 3:
                        if (this.currentVoiceChannel == var2 && !var1.getUuid().toString().equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                            ChatComponentText var4 = new ChatComponentText(EnumChatFormatting.AQUA + var1.getName() + EnumChatFormatting.AQUA + " left " + var2.getName() + " channel. Press 'Unbound" + /*Keyboard.getKeyName(this.cb.getGlobalSettings().keyBindOpenVoiceMenu.getKeyCode()) + */"'!" + EnumChatFormatting.RESET);
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(var4);
                        }
                        var2.removeListeners(var1.getUuid());
                }
            }
        }
    }

    private void addMutedUsers(List<VoiceUser> var1) {
        for (VoiceUser var3 : var1) {
            if (var3 == null || !this.playersInVoiceChannel.contains(var3.getPlayerUUID())
                    || this.mutedPlayers.contains(var3.getPlayerUUID())) continue;
            this.mutedPlayers.add(var3.getPlayerUUID());
            this.sendPacket(new PacketVoiceMute(var3.getPlayerUUID()));
        }
    }

    @Override
    public void handleDeleteVoiceChannel(CBPacketDeleteVoiceChannel var1) {
        this.logger("Deleted channel: " + var1.getUuid().toString());
        if (this.voiceChannels != null) {
            this.voiceChannels.removeIf(var1x -> var1x.getUuid().equals(var1.getUuid()));
        }
        if (this.currentVoiceChannel != null && this.currentVoiceChannel.getUuid().equals(var1.getUuid())) {
            this.currentVoiceChannel = null;
        }
    }

    @Override
    public void handleUpdateWorld(CBPacketUpdateWorld var1) {
        this.logger("World Update: " + var1.getWorld());
        this.worldUID = var1.getWorld();
    }

    @Override
    public void handleServerUpdate(CBPacketServerUpdate var1) {
        this.logger("Retrieved " + var1.getServer());
        this.cheatBreaker.updateServerInfo(var1.getServer());
    }

    @Override
    public void handleWorldBorder(CBPacketWorldBorder packet) {
        this.cheatBreaker.getWorldBorderManager().createBorder(packet.getId(), packet.getWorld(), packet.getColor(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.isCanShrinkExpand(), packet.isCancelsExit());
    }

    @Override
    public void handleWorldBorderUpdate(CBPacketWorldBorderUpdate packet) {
        this.cheatBreaker.getWorldBorderManager().updateBorder(packet.getId(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.getDurationTicks());
    }

    @Override
    public void handleWorldBorderRemove(CBPacketWorldBorderRemove packet) {
        this.cheatBreaker.getWorldBorderManager().removeBorder(packet.getId());
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
        if (this.voiceChannels != null && this.currentVoiceChannel != null) {
            VoiceUser var3;
            Iterator<VoiceUser> var2 = this.currentVoiceChannel.getUsers().iterator();
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
}
