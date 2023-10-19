package net.minecraft.client.multiplayer;

import com.cheatbreaker.client.CheatBreaker;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final List<ServerData> servers = Lists.<ServerData>newArrayList();

    public ServerList(Minecraft mcIn) {
        this.mc = mcIn;
        this.loadServerList();
    }

    public void loadServerList() {
        try {
            this.servers.clear();
            NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));

            if (nbttagcompound == null) {
                return;
            }

            NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);

            for (String[] server : CheatBreaker.getInstance().getGlobalSettings().getPinnedServers()) {
                this.servers.add(new ServerData(true, server[0], server[1], false));
            }

            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
            }
        } catch (Exception exception) {
            logger.error((String) "Couldn\'t load server list", (Throwable) exception);
        }
    }

    public void saveServerList() {
        try {
            NBTTagList nbttaglist = new NBTTagList();

            for (ServerData serverdata : this.servers) {
                if (serverdata.dontSave) continue;
                nbttaglist.appendTag(serverdata.getNBTCompound());
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
        } catch (Exception exception) {
            logger.error((String) "Couldn\'t save server list", (Throwable) exception);
        }
    }

    public ServerData getServerData(int index) {
        return (ServerData) this.servers.get(index);
    }

    public void removeServerData(int index) {
        this.servers.remove(index);
    }

    public void addServerData(ServerData server) {
        this.servers.add(server);
    }

    public int countServers() {
        return this.servers.size();
    }

    public void swapServers(int p_78857_1_, int p_78857_2_) {
        ServerData serverdata = this.getServerData(p_78857_1_);
        ServerData serverdata2 = this.getServerData(p_78857_2_);

        if (!serverdata.dontSave && !serverdata2.dontSave) {
            this.servers.set(p_78857_1_, serverdata2);
            this.servers.set(p_78857_2_, serverdata);
            this.saveServerList();
        }

    }

    public void func_147413_a(int index, ServerData server) {
        this.servers.set(index - CheatBreaker.getInstance().getGlobalSettings().getPinnedServers().size(), server);
    }

    public static void func_147414_b(ServerData p_147414_0_) {
        ServerList serverlist = new ServerList(Minecraft.getMinecraft());
        serverlist.loadServerList();

        for (int i = 0; i < serverlist.countServers(); ++i) {
            ServerData serverdata = serverlist.getServerData(i);

            if (serverdata.serverName.equals(p_147414_0_.serverName) && serverdata.serverIP.equals(p_147414_0_.serverIP)) {
                serverlist.func_147413_a(i, p_147414_0_);
                break;
            }
        }

        serverlist.saveServerList();
    }
}
