package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.plugin.obj.ServerRule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @CBPacket CBPacketServerRule
 * @see CBPacket
 *
 * This packet handles incoming Server Rules set by the server.
 */
@Getter @NoArgsConstructor
public class CBPacketServerRule extends CBPacket {

    private ServerRule rule;

    private String stringValue = "";

    private int intValue;

    private float floatValue;

    private boolean booleanValue;

    public CBPacketServerRule(ServerRule rule, float value) {
        this(rule);
        this.floatValue = value;
    }

    public CBPacketServerRule(ServerRule rule, boolean value) {
        this(rule);
        this.booleanValue = value;
    }

    public CBPacketServerRule(ServerRule rule, int value) {
        this(rule);
        this.intValue = value;
    }

    public CBPacketServerRule(ServerRule rule, String value) {
        this(rule);
        this.stringValue = value;
    }

    private CBPacketServerRule(ServerRule rule) {
        this.rule = rule;
    }

    @Override
    public void write(ByteBufWrapper out) throws IOException {
        out.writeString(this.rule.getRuleName());
        out.getBuf().writeBoolean(this.booleanValue);
        out.getBuf().writeInt(this.intValue);
        out.getBuf().writeFloat(this.floatValue);
        out.writeString(this.stringValue);
    }

    @Override
    protected byte[] readBlob(ByteBufWrapper buf) {
        return super.readBlob(buf);
    }

    @Override
    public void read(ByteBufWrapper in) throws IOException {
        this.rule = ServerRule.getRuleByName(in.readString());
        this.booleanValue = in.getBuf().readBoolean();
        this.intValue = in.getBuf().readInt();
        this.floatValue = in.getBuf().readFloat();
        this.stringValue = in.readString();
    }

    @Override
    public void process(ICBNetHandler handler) {
        ((ICBNetHandlerClient) handler).handleServerRule(this);
    }

}
