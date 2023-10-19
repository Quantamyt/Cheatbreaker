package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.plugin.obj.ServerRule;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketServerRule extends CBPacket {
    private ServerRule rule;
    private int intValue;
    private float floatValue;
    private boolean booleanValue;
    private String stringValue = "";

    public PacketServerRule() {
    }

    public PacketServerRule(ServerRule serverRule, float floatValue) {
        this(serverRule);
        this.floatValue = floatValue;
    }

    public PacketServerRule(ServerRule serverRule, boolean booleanValue) {
        this(serverRule);
        this.booleanValue = booleanValue;
    }

    public PacketServerRule(ServerRule serverRule, int intValue) {
        this(serverRule);
        this.intValue = intValue;
    }

    public PacketServerRule(ServerRule serverRule, String stringValue) {
        this(serverRule);
        this.stringValue = stringValue;
    }

    private PacketServerRule(ServerRule serverRule) {
        this.rule = serverRule;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.rule.getCommandName());
        byteBufWrapper.buf().writeBoolean(this.booleanValue);
        byteBufWrapper.buf().writeInt(this.intValue);
        byteBufWrapper.buf().writeFloat(this.floatValue);
        byteBufWrapper.writeString(this.stringValue);
    }

    @Override
    protected byte[] readBlob(ByteBufWrapper byteBufWrapper) {
        return super.readBlob(byteBufWrapper);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.rule = ServerRule.getRule(byteBufWrapper.readString());
        this.booleanValue = byteBufWrapper.buf().readBoolean();
        this.intValue = byteBufWrapper.buf().readInt();
        this.floatValue = byteBufWrapper.buf().readFloat();
        this.stringValue = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleServerRule(this);
    }

    public ServerRule getRule() {
        return this.rule;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public boolean getBoolean() {
        return this.booleanValue;
    }

    public String getRuleName() {
        return this.stringValue;
    }
}
