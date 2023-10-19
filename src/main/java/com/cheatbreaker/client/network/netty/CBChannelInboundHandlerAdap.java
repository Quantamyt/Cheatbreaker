package com.cheatbreaker.client.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javax.crypto.SecretKey;

public class CBChannelInboundHandlerAdap extends ChannelInboundHandlerAdapter {
    private long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private long lIIIIIIIIIlIllIIllIlIIlIl = 0L;
    private long IlllIIIlIlllIllIlIIlllIlI;
    private long IIIIllIlIIIllIlllIlllllIl;
    private final byte[] IIIIllIIllIIIIllIllIIIlIl = "cf2O02b1QJSZOcVHphHucA".getBytes();

    public CBChannelInboundHandlerAdap(SecretKey secretKey) {
        for (byte by : secretKey.getEncoded()) {
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)(by & 0xFF)) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) {
        ByteBuf byteBuf = (ByteBuf)object;
        while (byteBuf.readableBytes() > 0) {
            int n = byteBuf.readByte() & 0xFF;
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)n) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
        byteBuf.readerIndex(0);
        for (byte by : this.IIIIllIIllIIIIllIllIIIlIl) {
            this.lIIIIlIIllIIlIIlIIIlIIllI = (this.lIIIIlIIllIIlIIlIIIlIIllI + (long)(by & 0xFF)) % 65521L;
            this.lIIIIIIIIIlIllIIllIlIIlIl = (this.lIIIIIIIIIlIllIIllIlIIlIl + this.lIIIIlIIllIIlIIlIIIlIIllI) % 65521L;
        }
        this.IlllIIIlIlllIllIlIIlllIlI = this.IIIIllIlIIIllIlllIlllllIl;
        this.IIIIllIlIIIllIlllIlllllIl = this.lIIIIIIIIIlIllIIllIlIIlIl << 16 | this.lIIIIlIIllIIlIIlIIIlIIllI;
        try {
            super.channelRead(channelHandlerContext, object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public long lIIIIlIIllIIlIIlIIIlIIllI() {
        return this.IlllIIIlIlllIllIlIIlllIlI;
    }
}
