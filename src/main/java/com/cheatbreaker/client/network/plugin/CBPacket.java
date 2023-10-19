package com.cheatbreaker.client.network.plugin;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.client.PacketClientVoice;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceChannelSwitch;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;
import com.cheatbreaker.client.network.plugin.server.*;
import com.cheatbreaker.client.network.plugin.shared.PacketAddWaypoint;
import com.cheatbreaker.client.network.plugin.shared.PacketRemoveWaypoint;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.Unpooled;


/*
 * This class works exactly the same way
 * as the Websocket packet system! :)
 */
public abstract class CBPacket {
    private static final BiMap REGISTRY = HashBiMap.create();
    private Object attachment;

    /*
     * Writes outgoing data.
     * Example: I wrote a string using buf.writeString(); !
     */
    public abstract void write(ByteBufWrapper buf);
    
    /*
     * Reads incoming traffic.
     */
    public abstract void read(ByteBufWrapper buf);

    /*
     * This gets ran when a packet is received.
     * Example: When I receive the packet, a chat message is printed.
     */
    public abstract void process(ICBNetHandler netHandler);

    /*
     * Handles incoming traffic read by the packet.
     */
    public static CBPacket handle(ICBNetHandler iCBNetHandler, byte[] arrby) {
        return CBPacket.handle(iCBNetHandler, arrby, null);
    }

    /*
     * Writes to the server and returns the array.
     */
    public static CBPacket handle(ICBNetHandler iCBNetHandler, byte[] arrby, Object object) {
        ByteBufWrapper byteBufWrapper = new ByteBufWrapper(Unpooled.wrappedBuffer(arrby));
        int n = byteBufWrapper.readVarInt();
        Class class_ = (Class)REGISTRY.inverse().get(n);
        if (class_ != null) {
            try {
                CBPacket cBPacket = (CBPacket)class_.newInstance();
                if (object != null) {
                    cBPacket.setAttachment(object);
                }
                cBPacket.read(byteBufWrapper);
                return cBPacket;
            } catch (IllegalAccessException | InstantiationException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /*
     * Writes to the server and returns the array.
     */
    public static byte[] getPacketData(CBPacket cBPacket) {
        ByteBufWrapper byteBufWrapper = new ByteBufWrapper(Unpooled.buffer());
        byteBufWrapper.writeVarInt((Integer)REGISTRY.get(cBPacket.getClass()));
        cBPacket.write(byteBufWrapper);
        return byteBufWrapper.buf().array();
    }
    
    /*
     * Checks if the list already contains a packet, if not, it adds it.
     */
    private static void addPacket(int n, Class clazz) {
        if (REGISTRY.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class (" + clazz.getSimpleName() + "), already used by " + REGISTRY.get(clazz));
        }
        if (REGISTRY.containsValue(n)) {
            throw new IllegalArgumentException("Duplicate packet ID (" + n + "), already used by" + ((Class)REGISTRY.inverse().get(n)).getSimpleName());
        }
        REGISTRY.put(clazz, n);
    }

    /*
     * Writes the length of incoming traffic and the traffic itself short using the ByteBufWrapper.
     */
    protected void writeBlob(ByteBufWrapper byteBufWrapper, byte[] arrby) {
        byteBufWrapper.buf().writeShort(arrby.length);
        byteBufWrapper.buf().writeBytes(arrby);
    }

    /*
     * Reads incoming data and returns it as bytes.
     */
    protected byte[] readBlob(ByteBufWrapper byteBufWrapper) {
        // packetId
        final short s = byteBufWrapper.buf().readShort();
        if (s >= 0) {
            byte[] arrby = new byte[s];
            byteBufWrapper.buf().readBytes(arrby);
            return arrby;
        }
        System.out.println("Key was smaller than nothing!  Weird key!");
        return null;
    }

    /*
     * Sets the Attachment.
     */
    public void setAttachment(Object object) {
        this.attachment = object;
    }

    /*
     * Gets the attachment.
     */
    public Object getAttachment() {
        return this.attachment;
    }

    /*
     * Registers all the plugin packets for use on the client.
     */
    static {
        CBPacket.addPacket(0, PacketClientVoice.class);
        CBPacket.addPacket(1, PacketVoiceMute.class);
        CBPacket.addPacket(2, PacketVoiceChannelSwitch.class);
        CBPacket.addPacket(3, PacketCooldown.class);
        CBPacket.addPacket(4, PacketAddHologram.class);
        CBPacket.addPacket(5, PacketUpdateHologram.class);
        CBPacket.addPacket(6, PacketRemoveHologram.class);
        CBPacket.addPacket(7, PacketOverrideNametags.class);
        CBPacket.addPacket(8, PacketUpdateNametags.class);
        CBPacket.addPacket(9, PacketNotification.class);
        CBPacket.addPacket(10, PacketServerRule.class);
        CBPacket.addPacket(11, PacketServerUpdate.class);
        CBPacket.addPacket(12, PacketStaffModState.class);
        CBPacket.addPacket(13, PacketTeammates.class);
        CBPacket.addPacket(14, PacketTitle.class);
        CBPacket.addPacket(15, PacketUpdateWorld.class);
        CBPacket.addPacket(16, PacketVoice.class);
        CBPacket.addPacket(17, PacketVoiceChannel.class);
        CBPacket.addPacket(18, PacketDeleteVoiceChannel.class);
        CBPacket.addPacket(19, PacketVoiceChannelUpdate.class);
        CBPacket.addPacket(20, PacketWorldBorder.class);
        CBPacket.addPacket(21, PacketWorldBorderRemove.class);
        CBPacket.addPacket(22, PacketWorldBorderUpdate.class);
        CBPacket.addPacket(23, PacketAddWaypoint.class);
        CBPacket.addPacket(24, PacketRemoveWaypoint.class);
    }
}
