package com.cheatbreaker.client.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;

import javax.crypto.SecretKey;

/**
 * @see CBPingInboundHandler
 *
 * This is the CheatBreaker protocol adapter class.
 */
@Getter
public class CBChannelInboundHandlerAdap extends ChannelInboundHandlerAdapter {
    private final byte[] byteKey = "cf2O02b1QJSZOcVHphHucA".getBytes();

    private long someLong2 = 1L;
    private long someLong = 0L;
    private long unsignedInt;
    private long signedInt;

    public CBChannelInboundHandlerAdap(SecretKey secretKey) {
        for (byte by : secretKey.getEncoded()) {
            this.someLong2 = (this.someLong2 + (long) (by & 0xFF)) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) {
        ByteBuf byteBuf = (ByteBuf) object;

        while (byteBuf.readableBytes() > 0) {
            int n = byteBuf.readByte() & 0xFF;
            this.someLong2 = (this.someLong2 + (long) n) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }

        byteBuf.readerIndex(0);

        for (byte by : this.byteKey) {
            this.someLong2 = (this.someLong2 + (long) (by & 0xFF)) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }
        this.unsignedInt = this.signedInt;
        this.signedInt = this.someLong << 16 | this.someLong2;

        try {
            super.channelRead(channelHandlerContext, object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
