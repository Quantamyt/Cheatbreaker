package com.cheatbreaker.client.network.plugin;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.crypto.SecretKey;

/**
 * @see ChannelOutboundHandlerAdapter
 *
 * This class handles the custom outbound channel for pinging.
 */
public class CBOutboundChannelHandler extends ChannelOutboundHandlerAdapter {
    private long someLong2 = 1L;
    private long someLong = 0L;
    @Getter private long unsignedInt;
    private final byte[] key = "ZB9hEJy5l+u8QARAlX9T0w".getBytes();

    public CBOutboundChannelHandler(SecretKey secretKey) {
        for (byte by : secretKey.getEncoded()) {
            this.someLong2 = (this.someLong2 + (long) (by & 0xFF)) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }
    }

    @Override
    @SneakyThrows
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
        ByteBuf byteBuf = (ByteBuf) object;
        while (byteBuf.readableBytes() > 0) {
            int n = byteBuf.readByte() & 0xFF;
            this.someLong2 = (this.someLong2 + (long) n) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }
        byteBuf.readerIndex(0);
        for (byte by : this.key) {
            this.someLong2 = (this.someLong2 + (long) (by & 0xFF)) % 65521L;
            this.someLong = (this.someLong + this.someLong2) % 65521L;
        }
        this.unsignedInt = this.someLong << 16 | this.someLong2;
        super.write(channelHandlerContext, object, channelPromise);
    }
}
