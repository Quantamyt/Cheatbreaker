package net.minecraft.client.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.CapeUtils;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.io.File;
import java.util.UUID;

public abstract class AbstractClientPlayer extends EntityPlayer implements SkinAvailableCallback {
    public static final ResourceLocation locationStevePng = new ResourceLocation("textures/entity/steve.png");
    private ResourceLocation locationSkin;
    private ResourceLocation locationCape;
    private ResourceLocation locationOfCape = null;
    private String nameClear = null;

    public AbstractClientPlayer(World p_i45074_1_, GameProfile p_i45074_2_) {
        super(p_i45074_1_, p_i45074_2_);
        String var3 = this.getCommandSenderName();

        if (!var3.isEmpty()) {
            SkinManager var4 = Minecraft.getMinecraft().func_152342_ad();
            var4.func_152790_a(p_i45074_2_, this, true);
        }

        this.nameClear = p_i45074_2_.getName();

        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }

        Cosmetic activeCape = CheatBreaker.getInstance().getCosmeticManager().getActiveCape(UUID.fromString(CheatBreaker.getInstance().getProfileHandler().recompileUUID(this.getUniqueID().toString())));
        Cosmetic activeWings = CheatBreaker.getInstance().getCosmeticManager().getActiveWings(UUID.fromString(CheatBreaker.getInstance().getProfileHandler().recompileUUID(this.getUniqueID().toString())));
        CheatBreaker.getInstance().getWSNetHandler().handlePlayer(this);

        if (activeCape != null && (Boolean) CheatBreaker.getInstance().getGlobalSettings().showCheatBreakerCapes.getValue()) {
            this.setLocationOfCape(activeCape.getLocation());
            this.setCBCape(activeCape);
        } else if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().showOptifineCapes.getValue()) {
            CapeUtils.downloadCape(this, this.getGameProfile().getId());
        }

        if (activeWings != null) {
            this.setCBWings(activeWings);
        }
    }

    public boolean func_152122_n() {
        return Config.isShowCapes() && (this.locationOfCape != null || this.locationCape != null);
    }

    public boolean func_152123_o() {
        return this.locationSkin != null;
    }

    public ResourceLocation getLocationSkin() {
        ModuleNickHider nickHiderMod = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
        if (nickHiderMod.isEnabled() && nickHiderMod.hideOtherSkins.getBooleanValue()) {
            if (!nickHiderMod.hideSkin.getBooleanValue() && Minecraft.getMinecraft().getSession().getUsername().equals(this.getNameClear())) {
                return this.locationSkin == null ? locationStevePng : this.locationSkin;
            } else {
                return locationStevePng;
            }
        } else if (nickHiderMod.isEnabled() && nickHiderMod.hideSkin.getBooleanValue()
                && Minecraft.getMinecraft().getSession().getUsername().equals(this.getNameClear())) {
            return locationStevePng;
        }

        return this.locationSkin == null ? locationStevePng : this.locationSkin;
    }

    public ResourceLocation getLocationCape() {
        return !Config.isShowCapes() ? null : (this.locationOfCape != null ? this.locationOfCape : this.locationCape);
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation par0ResourceLocation, String par1Str) {
        TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
        Object var3 = var2.getTexture(par0ResourceLocation);

        if (var3 == null) {
            var3 = new ThreadDownloadImageData((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] {StringUtils.stripControlCodes(par1Str)}), locationStevePng, new ImageBufferDownload());
            var2.loadTexture(par0ResourceLocation, (ITextureObject)var3);
        }

        return (ThreadDownloadImageData)var3;
    }

    public static ResourceLocation getLocationSkin(String par0Str) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
    }

    public void func_152121_a(Type p_152121_1_, ResourceLocation p_152121_2_) {
        switch (AbstractClientPlayer.SwitchType.field_152630_a[p_152121_1_.ordinal()]) {
            case 1:
                this.locationSkin = p_152121_2_;
                break;

            case 2:
                this.locationCape = p_152121_2_;
        }
    }

    public String getNameClear() {
        return this.nameClear;
    }

    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }

    public void setLocationOfCape(ResourceLocation locationOfCape) {
        this.locationOfCape = locationOfCape;
    }

    static final class SwitchType {
        static final int[] field_152630_a = new int[Type.values().length];

        static {
            try {
                field_152630_a[Type.SKIN.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
                ;
            }

            try {
                field_152630_a[Type.CAPE.ordinal()] = 2;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }
}
