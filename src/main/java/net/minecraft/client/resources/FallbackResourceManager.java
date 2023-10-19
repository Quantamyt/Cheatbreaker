package net.minecraft.client.resources;

import com.cheatbreaker.client.network.agent.AgentResources;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FallbackResourceManager implements IResourceManager {
    protected final List<IResourcePack> resourcePacks = new ArrayList<>();
    private final IMetadataSerializer frmMetadataSerializer;


    public FallbackResourceManager(IMetadataSerializer p_i1289_1_) {
        this.frmMetadataSerializer = p_i1289_1_;
    }

    public void addResourcePack(IResourcePack p_110538_1_) {
        this.resourcePacks.add(p_110538_1_);
    }

    public Set getResourceDomains() {
        return null;
    }

    @Override @SneakyThrows
    public IResource getResource(ResourceLocation resourceLocation) {
        Object object;
        IResourcePack iResourcePack = null;
        ResourceLocation resourceLocation2 = FallbackResourceManager.getLocationMcmeta(resourceLocation);
        for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
            IResourcePack pack = this.resourcePacks.get(i);
            if (iResourcePack == null && pack.resourceExists(resourceLocation2)) {
                iResourcePack = pack;
            }
            if (!pack.resourceExists(resourceLocation)) continue;
            InputStream inputStream = null;
            if (iResourcePack != null) {
                inputStream = iResourcePack.getInputStream(resourceLocation2);
            }
            return new SimpleResource(resourceLocation, pack.getInputStream(resourceLocation), inputStream, this.frmMetadataSerializer);
        }
        String string = "assets/minecraft/" + resourceLocation.getResourcePath();
        if (AgentResources.existsBytes(string)) {
            object = AgentResources.getBytesNative(string);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            ByteArrayInputStream byteArrayInputStream2 = null;
            if (AgentResources.existsBytes(string + ".mcmeta")) {
                byteArrayInputStream2 = new ByteArrayInputStream(AgentResources.getBytesNative(string + ".mcmeta"));
            }
            return new SimpleResource(resourceLocation, byteArrayInputStream, byteArrayInputStream2, this.frmMetadataSerializer);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }

    public List<SimpleResource> getAllResources(ResourceLocation p_135056_1_) throws IOException {
        ArrayList<SimpleResource> var2 = Lists.newArrayList();
        ResourceLocation var3 = getLocationMcmeta(p_135056_1_);

        for (IResourcePack pack : this.resourcePacks) {
            if (pack.resourceExists(p_135056_1_)) {
                InputStream var6 = pack.resourceExists(var3) ? pack.getInputStream(var3) : null;
                var2.add(new SimpleResource(p_135056_1_, pack.getInputStream(p_135056_1_), var6, this.frmMetadataSerializer));
            }
        }

        if (var2.isEmpty()) {
            throw new FileNotFoundException(p_135056_1_.toString());
        } else {
            return var2;
        }
    }

    static ResourceLocation getLocationMcmeta(ResourceLocation p_110537_0_) {
        return new ResourceLocation(p_110537_0_.getResourceDomain(), p_110537_0_.getResourcePath() + ".mcmeta");
    }
}
