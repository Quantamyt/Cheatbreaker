package net.minecraft.client.resources;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.util.manager.BranchManager;
import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ForkJoinPool;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;

public abstract class AbstractResourcePack implements IResourcePack {
    private static final Logger resourceLog = LogManager.getLogger();
    public final File resourcePackFile;
    public static final int SIZE_CAP = 64;

    public AbstractResourcePack(File resourcePackFileIn) {
        this.resourcePackFile = resourcePackFileIn;
    }

    private static String locationToName(ResourceLocation location) {
        return String.format("%s/%s/%s", new Object[]{"assets", location.getResourceDomain(), location.getResourcePath()});
    }

    protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }

    public InputStream getInputStream(ResourceLocation location) throws IOException {
        return this.getInputStreamByName(locationToName(location));
    }

    public boolean resourceExists(ResourceLocation location) {
        return this.hasResourceName(locationToName(location));
    }

    protected abstract InputStream getInputStreamByName(String name) throws IOException;

    protected abstract boolean hasResourceName(String name);

    protected void logNameNotLowercase(String name) {
        resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", new Object[]{name, this.resourcePackFile});
    }

    public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        return readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), metadataSectionName);
    }

    static <T extends IMetadataSection> T readMetadata(IMetadataSerializer p_110596_0_, InputStream p_110596_1_, String p_110596_2_) {
        JsonObject jsonobject = null;
        BufferedReader bufferedreader = null;

        try {
            bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
            jsonobject = (new JsonParser()).parse((Reader) bufferedreader).getAsJsonObject();
        } catch (RuntimeException runtimeexception) {
            throw new JsonParseException(runtimeexception);
        } finally {
            IOUtils.closeQuietly((Reader) bufferedreader);
        }

        return (T) p_110596_0_.parseMetadataSection(p_110596_2_, jsonobject);
    }

    public BufferedImage getPackImage() throws IOException {
        try {
            return scalePackImage(TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png")));
        } catch (IOException e) {
            return Config.getDefaultResourcePack().getPackImage();
        }
    }

    public BufferedImage scalePackImage(BufferedImage image) {
        if (image == null) {
            return null;
        }
        if (image.getWidth() > SIZE_CAP || image.getHeight() > SIZE_CAP) {
            resourceLog.info("[Icon Scaler] Scaling resource pack icon from " + image.getWidth() + " to " + SIZE_CAP);
            BufferedImage smallImage = new BufferedImage(SIZE_CAP, SIZE_CAP, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = smallImage.getGraphics();
            graphics.drawImage(image, 0, 0, SIZE_CAP, SIZE_CAP, null);
            graphics.dispose();
            return smallImage;
        } else {
            resourceLog.info("[Icon Scaler] Retaining pack icon scale at " + image.getWidth());
            return image;
        }

    }

    public String getPackName() {
        return this.resourcePackFile.getName();
    }
}
