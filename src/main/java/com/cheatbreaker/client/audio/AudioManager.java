package com.cheatbreaker.client.audio;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.messages.Message;
import lombok.Getter;
import net.minecraft.client.Minecraft;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AudioManager {

    private final List<Microphone> microphones = new ArrayList<>();

    public void playSound(String name) {
        this.playSoundVol(name, 1.0f);
    }

    public void playSoundVol(String name, float volume) {
        if (!(Boolean) CheatBreaker.getInstance().getGlobalSettings().muteCBSounds.getValue())
            Minecraft.getMinecraft().getSoundHandler().field_147694_f.playSound(name, volume);
    }

    /**
     * Registers the current system enabled Microphones for Voice Chat.
     */
    private void registerSystemMicrophones() {
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info micInfo : mixerInfos) {
            Line.Info[] micLine = AudioSystem.getMixer(micInfo).getTargetLineInfo();

            if (micLine.length >= 1 && micLine[0].getLineClass().equals(TargetDataLine.class)) {
                if (micInfo == null) continue;
                Message.g(new String[]{micInfo.getDescription()}, new String[]{micInfo.getName()});
            }
        }
    }

    /**
     * Returns all registered Microphones in the form of a String Array.
     */
    public String[] getMicrophoneArray() {
        String[] microphones = new String[this.microphones.size()];
        int index = 0;
        for (Microphone microphone : this.microphones) {
            microphones[index] = microphone.getName().replace("Primary Sound Capture Driver", "Default");
            ++index;
        }
        return microphones;
    }

    /**
     * Returns a given Microphone based on its name.
     */
    public Microphone getMicrophone(String name) {
        for (Microphone mic : this.microphones) {
            if (mic.getName().equalsIgnoreCase(name)) {
                return mic;
            }
        }
        return null;
    }

}
