package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public abstract class AbstractResourcePack implements IResourcePack {
    private static final Logger resourceLog = LogManager.getLogger();
    public final File resourcePackFile;
    public static final int SIZE_CAP = 64;

    public AbstractResourcePack(File p_i1287_1_) {
        this.resourcePackFile = p_i1287_1_;
    }

    private static String locationToName(ResourceLocation p_110592_0_) {
        return String.format("%s/%s/%s", "assets", p_110592_0_.getResourceDomain(), p_110592_0_.getResourcePath());
    }

    protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }

    public InputStream getInputStream(ResourceLocation p_110590_1_) throws IOException {
        return this.getInputStreamByName(locationToName(p_110590_1_));
    }

    public boolean resourceExists(ResourceLocation p_110589_1_) {
        return this.hasResourceName(locationToName(p_110589_1_));
    }

    protected abstract InputStream getInputStreamByName(String p_110591_1_) throws IOException;

    protected abstract boolean hasResourceName(String p_110593_1_);

    protected void logNameNotLowercase(String p_110594_1_) {
        resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", p_110594_1_, this.resourcePackFile);
    }

    public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
        return readMetadata(p_135058_1_, this.getInputStreamByName("pack.mcmeta"), p_135058_2_);
    }

    static IMetadataSection readMetadata(IMetadataSerializer p_110596_0_, InputStream p_110596_1_, String p_110596_2_) {
        JsonObject var3 = null;
        BufferedReader var4 = null;

        try {
            var4 = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
            var3 = (new JsonParser()).parse(var4).getAsJsonObject();
        } catch (RuntimeException var9) {
            throw new JsonParseException(var9);
        }
        finally {
            IOUtils.closeQuietly(var4);
        }

        return p_110596_0_.parseMetadataSection(p_110596_2_, var3);
    }


    public BufferedImage getPackImage(){
        try {
            return scalePackImage(ImageIO.read(this.getInputStreamByName("pack.png")));
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
