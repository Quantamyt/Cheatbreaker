package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.client.PacketClientVoice;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceChannelSwitch;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;
import com.cheatbreaker.client.network.plugin.server.*;
import com.cheatbreaker.client.network.plugin.shared.CBPacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.CBPacketRemoveWaypoint;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * This class defines what a plugin packet is for the CheatBreaker client.
 */
public abstract class CBPacket {
    private static final BiMap<Class<?>, Integer> REGISTRY = HashBiMap.create();

    @Getter @Setter private Object attachment;

    /**
     * Writes outgoing data.
     * Example: I wrote a string using buf.writeString(); !
     */
    public abstract void write(ByteBufWrapper out) throws IOException;

    /**
     * Reads incoming data.
     */
    public abstract void read(ByteBufWrapper in) throws IOException;

    /**
     * This gets ran when a packet is received.
     * Example: When I receive the packet, a chat message is printed.
     */
    public abstract void process(ICBNetHandler handler);

    /**
     * Handles incoming traffic read by the packet.
     */
    public static CBPacket handle(ICBNetHandler iCBNetHandler, byte[] arrby) {
        return CBPacket.handle(iCBNetHandler, arrby, null);
    }

    /**
     * Handles the packet accordingly.
     * - Updated 2/25/2022 by Noxiuam.
     */
    public static CBPacket handle(ICBNetHandler netHandler, byte[] data, Object attachment) {
        ByteBufWrapper wrappedBuffer = new ByteBufWrapper(Unpooled.wrappedBuffer(data));
        int packetId = wrappedBuffer.readVarInt();
        Class<?> packetClass = REGISTRY.inverse().get(packetId);
        if (packetClass != null) {
            try {
                CBPacket packet = (CBPacket) packetClass.newInstance();
                if (attachment != null) {
                    packet.setAttachment(attachment);
                }
                packet.read(wrappedBuffer);
                return packet;
            }
            catch (IOException | IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Writes to the server and returns the array.
     */
    public static byte[] getPacketData(CBPacket packet) {
        ByteBufWrapper wrappedBuffer = new ByteBufWrapper(Unpooled.buffer());
        wrappedBuffer.writeVarInt(REGISTRY.get(packet.getClass()));
        try {
            packet.write(wrappedBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrappedBuffer.getBuf().array();
    }

    /**
     * Checks if the list already contains a packet, if not, it adds it.
     */
    private static void addPacket(int id, Class<?> clazz) {
        if (REGISTRY.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class (" + clazz.getSimpleName() + "), already used by " + REGISTRY.get(clazz));
        }

        if (REGISTRY.containsValue(id)) {
            throw new IllegalArgumentException("Duplicate packet ID (" + id + "), already used by" + REGISTRY.inverse().get(id).getSimpleName());
        }

        REGISTRY.put(clazz, id);
    }

    /**
     * Writes the length of incoming traffic and the traffic itself short using the ByteBufWrapper.
     */
    protected void writeBlob(ByteBufWrapper b, byte[] bytes) {
        b.getBuf().writeShort(bytes.length);
        b.getBuf().writeBytes(bytes);
    }

    /**
     * Reads incoming data and returns it as bytes.
     */
    protected byte[] readBlob(ByteBufWrapper buf) {
        final short index = buf.getBuf().readShort();

        if (index >= 0) {
            byte[] data = new byte[index];
            buf.getBuf().readBytes(data);
            return data;
        }

        System.out.println("Key was smaller than nothing!  Weird key!");
        return null;
    }

    /*
     * Registers all the plugin packets for use on the client.
     */
    static {
        CBPacket.addPacket(0, PacketClientVoice.class);
        CBPacket.addPacket(1, PacketVoiceMute.class);
        CBPacket.addPacket(2, PacketVoiceChannelSwitch.class);
        CBPacket.addPacket(3, CBPacketCooldown.class);
        CBPacket.addPacket(4, CBPacketAddHologram.class);
        CBPacket.addPacket(5, CBPacketUpdateHologram.class);
        CBPacket.addPacket(6, CBPacketRemoveHologram.class);
        CBPacket.addPacket(7, CBPacketOverrideNametags.class);
        CBPacket.addPacket(8, CBPacketUpdateNametags.class);
        CBPacket.addPacket(9, CBPacketNotification.class);
        CBPacket.addPacket(10, CBPacketServerRule.class);
        CBPacket.addPacket(11, CBPacketServerUpdate.class);
        CBPacket.addPacket(12, CBPacketStaffModState.class);
        CBPacket.addPacket(13, CBPacketTeammates.class);
        CBPacket.addPacket(14, CBPacketTitle.class);
        CBPacket.addPacket(15, CBPacketUpdateWorld.class);
        CBPacket.addPacket(16, CBPacketVoice.class);
        CBPacket.addPacket(17, CBPacketVoiceChannel.class);
        CBPacket.addPacket(18, CBPacketDeleteVoiceChannel.class);
        CBPacket.addPacket(19, CBPacketVoiceChannelUpdate.class);
        CBPacket.addPacket(20, CBPacketWorldBorder.class);
        CBPacket.addPacket(21, CBPacketWorldBorderRemove.class);
        CBPacket.addPacket(22, CBPacketWorldBorderUpdate.class);
        CBPacket.addPacket(23, CBPacketAddWaypoint.class);
        CBPacket.addPacket(24, CBPacketRemoveWaypoint.class);
    }
}
