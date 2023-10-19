package com.cheatbreaker.client.network.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

import java.io.*;

/**
 * @see ChannelInboundHandlerAdapter
 *
 * This class handles incoming CheatBreaker Ping payloads, and sends a CheatBreaker Pong payload back.
 */
public class CBPingInboundHandler extends ChannelInboundHandlerAdapter {
    private final CBChannelInboundHandlerAdap channelInboundHandlerAdap;
    private long maxTime = System.nanoTime() - 30000000000L;
    private final Minecraft mc = Minecraft.getMinecraft();
    private long version = 0L;

    public CBPingInboundHandler(CBChannelInboundHandlerAdap var1) {
        this.channelInboundHandlerAdap = var1;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        S3FPacketCustomPayload payload;
        if (packet instanceof S3FPacketCustomPayload && (payload = (S3FPacketCustomPayload) packet).getChannelName().equals("CB|PING")) {
            this.maxTime = System.nanoTime();
            long version = (long)new DataInputStream(new ByteArrayInputStream(payload.getBufferData().readByteArray())).readInt() & 0xFFFFFFFFL;
            if (version != this.channelInboundHandlerAdap.getUnsignedInt()) {
                throw new IOException("CheatBreaker Protocol Error -2a\n(Try updating your client)");
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);
            try {
                dataOutputStream.write(payload.getBufferData().readByteArray());
                if ((this.version++ & 7L) == 0L) {
                    dataOutputStream.writeLong(this.version);
                    dataOutputStream.writeUTF(this.mc.currentServerData.serverIP);
                    dataOutputStream.writeUTF(this.mc.getSession().getPlayerID());
                    dataOutputStream.writeUTF(this.mc.entityRenderer.getClass().getName());
                }
            } catch (IOException ignored) {
            }

            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeBytes(arrayOutputStream.toByteArray());
            ctx.channel().eventLoop().execute(() -> ctx.channel().writeAndFlush(new C17PacketCustomPayload("CB|PONG", packetBuffer)));
        }
        if (System.nanoTime() - this.maxTime > 45000000000L) {
            throw new IOException("CheatBreaker Protocol Error -2b\n(Try updating your client)");
        }
        super.channelRead(ctx, packet);
    }
}
