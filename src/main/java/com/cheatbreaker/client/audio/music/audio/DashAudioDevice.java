package com.cheatbreaker.client.audio.music.audio;

import com.cheatbreaker.client.CheatBreaker;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDeviceBase;

import javax.sound.sampled.*;

/**
 * Defines the Music Player's Audio Device.
 */
public class DashAudioDevice extends AudioDeviceBase {
    private SourceDataLine sourceDataLine = null;
    private AudioFormat audioFormat = null;
    private byte[] byteArray = new byte[4096];

    protected void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    protected AudioFormat getAudioFormat() {
        if (this.audioFormat == null) {
            Decoder decoder = this.getDecoder();
            this.audioFormat = new AudioFormat(decoder.getOutputFrequency(), 16, decoder.getOutputChannels(), true, false);
        }
        return this.audioFormat;
    }

    protected DataLine.Info getInfo() {
        AudioFormat format = this.getAudioFormat();
        return new DataLine.Info(SourceDataLine.class, format);
    }

    public void open(AudioFormat var1) {
        if (!this.isOpen()) {
            this.setAudioFormat(var1);
            this.openImpl();
            this.setOpen(true);
        }
    }

    @Override
    protected void openImpl() {
    }

    protected void start() throws JavaLayerException {
        Throwable throwable = null;
        try {
            Line line = AudioSystem.getLine(this.getInfo());
            if (line instanceof SourceDataLine) {
                this.sourceDataLine = (SourceDataLine) line;
                this.sourceDataLine.open(this.audioFormat);
                this.sourceDataLine.start();
                this.setFloatControlValue((float) CheatBreaker.getInstance().getGlobalSettings().radioVolume.getValue());
            }
        } catch (RuntimeException | LineUnavailableException | LinkageError var3) {
            throwable = var3;
        }
        if (this.sourceDataLine == null) {
            throw new JavaLayerException("cannot obtain source audio line", throwable);
        }
    }

    @Override
    protected void closeImpl() {
        if (this.sourceDataLine != null) {
            this.sourceDataLine.close();
        }
    }

    @Override
    protected void writeImpl(short[] var1, int var2, int var3) throws JavaLayerException {
        if (this.sourceDataLine == null) {
            this.start();
        }
        byte[] var4 = this.toByteArray(var1, var2, var3);
        this.sourceDataLine.write(var4, 0, var3 * 2);
    }

    protected byte[] getByteArray(int var1) {
        if (this.byteArray.length < var1) {
            this.byteArray = new byte[var1 + 1024];
        }
        return this.byteArray;
    }

    protected byte[] toByteArray(short[] var1, int var2, int var3) {
        byte[] var4 = this.getByteArray(var3 * 2);
        int var6 = 0;
        while (var3-- > 0) {
            short var5 = var1[var2++];
            var4[var6++] = (byte) var5;
            var4[var6++] = (byte) (var5 >>> 8);
        }
        return var4;
    }

    @Override
    protected void flushImpl() {
        if (this.sourceDataLine != null) {
            this.sourceDataLine.drain();
        }
    }

    @Override
    public int getPosition() {
        int var1 = 0;
        if (this.sourceDataLine != null) {
            var1 = (int) (this.sourceDataLine.getMicrosecondPosition() / 1000L);
        }
        return var1;
    }

    public void test() throws JavaLayerException {
        try {
            this.open(new AudioFormat(22050.0f, 16, 1, true, false));
            short[] var1 = new short[2205];
            this.write(var1, 0, var1.length);
            this.flush();
            this.close();
        } catch (RuntimeException var2) {
            throw new JavaLayerException("Device test failed: " + var2);
        }
    }

    public void setFloatControlValue(float value) {
        if (this.sourceDataLine != null) {
            FloatControl control = (FloatControl) this.sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            float var3 = control.getMaximum() - control.getMinimum();
            float volume = var3 * (value / 100.0f) + control.getMinimum();
            control.setValue(volume);
        }
    }
}
