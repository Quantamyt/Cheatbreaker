package com.cheatbreaker.client.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ByteBufWrapper {
    private final ByteBuf buf;

    public ByteBufWrapper(ByteBuf byteBuf) {
        this.buf = byteBuf;
    }

    public void writeVarInt(int n) {
        while ((n & 0xFFFFFF80) != 0) {
            this.buf.writeByte(n & 0x7F | 0x80);
            n >>>= 7;
        }
        this.buf.writeByte(n);
    }

    public int readVarInt() {
        byte by;
        int n = 0;
        int n2 = 0;
        do {
            by = this.buf.readByte();
            n |= (by & 0x7F) << n2++ * 7;
            if (n2 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((by & 0x80) == 128);
        return n;
    }

    public void writeOptional(Object object, Consumer<Object> consumer) {
        this.buf.writeBoolean(object != null);
        if (object != null) {
            consumer.accept(object);
        }
    }

    public Object readOptional(Supplier supplier) {
        boolean bl = this.buf.readBoolean();
        if (bl) {
            return supplier.get();
        }
        return null;
    }

    public void writeString(String string) {
        byte[] arrby = string.getBytes(Charsets.UTF_8);
        this.writeVarInt(arrby.length);
        this.buf.writeBytes(arrby);
    }

    public String readString() {
        int n = this.readVarInt();
        byte[] arrby = new byte[n];
        this.buf.readBytes(arrby);
        return new String(arrby, Charsets.UTF_8);
    }

    public void writeUUID(UUID uUID) {
        this.buf.writeLong(uUID.getMostSignificantBits());
        this.buf.writeLong(uUID.getLeastSignificantBits());
    }

    public UUID readUUID() {
        long l = this.buf.readLong();
        long l2 = this.buf.readLong();
        return new UUID(l, l2);
    }

    public ByteBuf buf() {
        return this.buf;
    }
}
