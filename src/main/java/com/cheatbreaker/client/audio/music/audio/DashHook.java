package com.cheatbreaker.client.audio.music.audio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.cheatbreaker.client.network.agent.AgentResources;
import javazoom.jl.decoder.JavaLayerHook;
import javazoom.jl.decoder.JavaLayerUtils;

public class DashHook implements JavaLayerHook {
    @Override
    public InputStream getResourceAsStream(String string) {
        Class<JavaLayerUtils> class_ = JavaLayerUtils.class;
        InputStream inputStream = class_.getResourceAsStream(string);
        if (inputStream == null) {
            String string2 = "javazoom/jl/decoder/" + string;
            System.out.println("Retrieving: " + string2);
            if (AgentResources.existsBytes(string2)) {
                inputStream = new ByteArrayInputStream(AgentResources.getBytesNative(string2));
            }
        }
        return inputStream;
    }
}
