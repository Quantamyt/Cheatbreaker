package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {
    public String serverName;
    public String serverIP;

    /**
     * the string indicating number of players on and capacity of the server that is shown on the server browser (i.e.
     * "5/20" meaning 5 slots used out of 20 slots total)
     */
    public String populationInfo;
    /**
     * CheatBreaker Fields
     */
    public String domain;
    public int port;

    /**
     * (better variable name would be 'hostname') server name as displayed in the server browser's second line (grey
     * text)
     */
    public String serverMOTD;

    /** last server ping that showed up in the server browser */
    public long pingToServer;
    public int version = 5;

    /** Game version for this server. */
    public String gameVersion = "1.7.10";;
    public boolean field_78841_f;
    public String playerList;
    private ServerData.ServerResourceMode resourceMode = ServerData.ServerResourceMode.PROMPT;
    private String field_147411_m;
    private boolean field_152588_l;
    public boolean dontSave = false;
    public boolean pinged = false;
    public boolean lunarServer = false;

    public ServerData(boolean dontSave, String serverName, String serverIP) {
        this(serverName, serverIP);
        this.dontSave = dontSave;
    }

    public ServerData(String p_i1193_1_, String p_i1193_2_) {
        this.serverName = p_i1193_1_;
        this.serverIP = p_i1193_2_;
    }

    public boolean isCheatBreakerServer() {
        return version == -1332;
    }

    public ServerData(String p_i46395_1_, String p_i46395_2_, boolean p_i46395_3_) {
        this(p_i46395_1_, p_i46395_2_);
        this.field_152588_l = p_i46395_3_;
    }

    /**
     * Returns an NBTTagCompound with the server's name, IP and maybe acceptTextures.
     */
    public NBTTagCompound getNBTCompound() {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("name", this.serverName);
        var1.setString("ip", this.serverIP);

        if (this.field_147411_m != null) {
            var1.setString("icon", this.field_147411_m);
        }

        if (this.resourceMode == ServerData.ServerResourceMode.ENABLED) {
            var1.setBoolean("acceptTextures", true);
        } else if (this.resourceMode == ServerData.ServerResourceMode.DISABLED) {
            var1.setBoolean("acceptTextures", false);
        }

        return var1;
    }

    public ServerData.ServerResourceMode func_152586_b() {
        return this.resourceMode;
    }

    public void func_152584_a(ServerData.ServerResourceMode p_152584_1_) {
        this.resourceMode = p_152584_1_;
    }

    /**
     * Takes an NBTTagCompound with 'name' and 'ip' keys, returns a ServerData instance.
     */
    public static ServerData getServerDataFromNBTCompound(NBTTagCompound p_78837_0_) {
        ServerData var1 = new ServerData(p_78837_0_.getString("name"), p_78837_0_.getString("ip"));

        if (p_78837_0_.func_150297_b("icon", 8)) {
            var1.setBase64EncodedIconData(p_78837_0_.getString("icon"));
        }

        if (p_78837_0_.func_150297_b("acceptTextures", 1)) {
            if (p_78837_0_.getBoolean("acceptTextures")) {
                var1.func_152584_a(ServerData.ServerResourceMode.ENABLED);
            } else {
                var1.func_152584_a(ServerData.ServerResourceMode.DISABLED);
            }
        } else {
            var1.func_152584_a(ServerData.ServerResourceMode.PROMPT);
        }

        return var1;
    }

    public String getBase64EncodedIconData() {
        return this.field_147411_m;
    }

    public void setBase64EncodedIconData(String p_147407_1_) {
        this.field_147411_m = p_147407_1_;
    }

    public void func_152583_a(ServerData p_152583_1_) {
        this.serverIP = p_152583_1_.serverIP;
        this.serverName = p_152583_1_.serverName;
        this.func_152584_a(p_152583_1_.func_152586_b());
        this.field_147411_m = p_152583_1_.field_147411_m;
    }

    public boolean func_152585_d() {
        return this.field_152588_l;
    }

    public enum ServerResourceMode {
        ENABLED("ENABLED", 0, "enabled"),
        DISABLED("DISABLED", 1, "disabled"),
        PROMPT("PROMPT", 2, "prompt");
        private final IChatComponent field_152594_d;

        private static final ServerData.ServerResourceMode[] $VALUES = new ServerData.ServerResourceMode[]{ENABLED, DISABLED, PROMPT};


        ServerResourceMode(String p_i1053_1_, int p_i1053_2_, String p_i1053_3_) {
            this.field_152594_d = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_);
        }

        public IChatComponent func_152589_a() {
            return this.field_152594_d;
        }
    }
}
