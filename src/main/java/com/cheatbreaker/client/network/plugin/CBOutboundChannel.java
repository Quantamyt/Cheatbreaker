package com.cheatbreaker.client.network.plugin;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.SneakyThrows;

import javax.crypto.SecretKey;

public class CBOutboundChannel extends ChannelOutboundHandlerAdapter {
    private long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private long lIIIIIIIIIlIllIIllIlIIlIl = 0L;
    private long IlllIIIlIlllIllIlIIlllIlI;
    private final byte[] IIIIllIlIIIllIlllIlllllIl = "ZB9hEJy5l+u8QARAlX9T0w".getBytes();

    public CBOutboundChannel(SecretKey secretKey) {
        for (byte by : secretKey.getEncoded()) {
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)(by & 0xFF)) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
    }

    @Override@SneakyThrows
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
        ByteBuf byteBuf = (ByteBuf)object;
        while (byteBuf.readableBytes() > 0) {
            int n = byteBuf.readByte() & 0xFF;
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)n) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
        byteBuf.readerIndex(0);
        for (byte by : this.IIIIllIlIIIllIlllIlllllIl) {
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)(by & 0xFF)) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
        this.IlllIIIlIlllIllIlIIlllIlI = this.lIIIIIIIIIlIllIIllIlIIlIl << 16 | this.lIIIIlIIllIIlIIlIIIlIIllI;
        super.write(channelHandlerContext, object, channelPromise);
    }

    public long lIIIIlIIllIIlIIlIIIlIIllI() {
        return this.IlllIIIlIlllIllIlIIlllIlI;
    }
}
