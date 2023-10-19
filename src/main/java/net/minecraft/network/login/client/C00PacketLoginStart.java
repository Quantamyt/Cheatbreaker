package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart extends Packet {
    private GameProfile field_149305_a;


    public C00PacketLoginStart() {}

    public C00PacketLoginStart(GameProfile p_i45270_1_) {
        this.field_149305_a = p_i45270_1_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_149305_a = new GameProfile(null, p_148837_1_.readStringFromBuffer(16));
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149305_a.getName());
    }

    public void processPacket(INetHandlerLoginServer p_148833_1_) {
        p_148833_1_.processLoginStart(this);
    }

    public GameProfile func_149304_c() {
        return this.field_149305_a;
    }

    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerLoginServer)p_148833_1_);
    }
}
