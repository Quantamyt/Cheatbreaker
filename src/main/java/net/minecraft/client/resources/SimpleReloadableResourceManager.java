package net.minecraft.client.resources;

import com.cheatbreaker.client.CheatBreaker;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleReloadableResourceManager implements IReloadableResourceManager
{
    private static final Logger logger = LogManager.getLogger();
    private static final Joiner joinerResourcePacks = Joiner.on(", ");
    private final Map<String, FallbackResourceManager> domainResourceManagers = Maps.<String, FallbackResourceManager>newHashMap();
    private final List<IResourceManagerReloadListener> reloadListeners = Lists.<IResourceManagerReloadListener>newArrayList();
    private final Set<String> setResourceDomains = Sets.<String>newLinkedHashSet();
    private final IMetadataSerializer rmMetadataSerializer;

    public SimpleReloadableResourceManager(IMetadataSerializer rmMetadataSerializerIn)
    {
        this.rmMetadataSerializer = rmMetadataSerializerIn;
    }

    public void reloadResourcePack(IResourcePack resourcePack)
    {
        for (String s : resourcePack.getResourceDomains())
        {
            this.setResourceDomains.add(s);
            FallbackResourceManager fallbackresourcemanager = (FallbackResourceManager)this.domainResourceManagers.get(s);

            if (fallbackresourcemanager == null)
            {
                fallbackresourcemanager = new FallbackResourceManager(this.rmMetadataSerializer);
                this.domainResourceManagers.put(s, fallbackresourcemanager);
            }

            fallbackresourcemanager.addResourcePack(resourcePack);
        }
    }

    public Set<String> getResourceDomains()
    {
        return this.setResourceDomains;
    }

    public IResource getResource(ResourceLocation location) throws IOException
    {
        IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());

        if (iresourcemanager != null)
        {
            return iresourcemanager.getResource(location);
        }
        else
        {
            throw new FileNotFoundException(location.toString());
        }
    }

    public List<IResource> getAllResources(ResourceLocation location) throws IOException
    {
        IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());

        if (iresourcemanager != null)
        {
            return iresourcemanager.getAllResources(location);
        }
        else
        {
            throw new FileNotFoundException(location.toString());
        }
    }

    private void clearResources()
    {
        this.domainResourceManagers.clear();
        this.setResourceDomains.clear();
    }

    public void reloadResources(List<IResourcePack> resourcesPacksList)
    {
        this.clearResources();
        logger.info("Reloading ResourceManager: " + joinerResourcePacks.join(Iterables.transform(resourcesPacksList, new Function<IResourcePack, String>()
        {
            public String apply(IResourcePack p_apply_1_)
            {
                return p_apply_1_.getPackName();
            }
        })));

        for (IResourcePack iresourcepack : resourcesPacksList)
        {
            this.reloadResourcePack(iresourcepack);
        }

        updatepack();
        this.notifyReloadListeners();
    }

    public void registerReloadListener(IResourceManagerReloadListener reloadListener)
    {
        this.reloadListeners.add(reloadListener);
        reloadListener.onResourceManagerReload(this);
    }

    private void notifyReloadListeners()
    {
        for (IResourceManagerReloadListener iresourcemanagerreloadlistener : this.reloadListeners)
        {
            iresourcemanagerreloadlistener.onResourceManagerReload(this);
        }
    }


    /**
     * Last two classes are for the Pack Display Mod.
     */
    public static void updatepack() {
        if (CheatBreaker.getInstance() == null) {
            return;
        }
        DynamicTexture texture = null;
        try {
            texture = new DynamicTexture(getResourcePack().getPackImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CheatBreaker.packIconTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("texturepackicon", texture);
    }

    public static IResourcePack getResourcePack() {
        IResourcePack[] rps = Config.getResourcePacks();

        if (rps.length <= 0) {
            return Config.getDefaultResourcePack();
        } else {
            IResourcePack[] names = new IResourcePack[rps.length];

            for (int nameStr = 0; nameStr < rps.length; ++nameStr) {
                names[nameStr] = rps[nameStr];
            }

            String var3 = Config.arrayToString(names);
            return names[names.length -1];
        }
    }
}
