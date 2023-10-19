package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @CBPacket CBPacketVoiceChannelUpdate
 * @see CBPacket
 *
 * This packet updates a voice channel on the client.
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class CBPacketVoiceChannelUpdate extends CBPacket {

    public int status;
    private UUID channelUuid;
    private UUID uuid;
    private String name;

    @Override
    public void write(ByteBufWrapper out) {
        out.writeVarInt(this.status);
        out.writeUUID(this.channelUuid);
        out.writeUUID(this.uuid);
        out.writeString(this.name);
    }

    @Override
    public void read(ByteBufWrapper in) {
        this.status = in.readVarInt();
        this.channelUuid = in.readUUID();
        this.uuid = in.readUUID();
        this.name = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleVoiceChannelUpdate(this);
    }

}
