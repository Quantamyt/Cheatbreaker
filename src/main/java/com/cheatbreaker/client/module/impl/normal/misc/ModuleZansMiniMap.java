package com.cheatbreaker.client.module.impl.normal.misc;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.MiniMapRules;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import net.minecraft.util.ResourceLocation;

/**
 * @Module - ModuleZansMiniMap
 * @see AbstractModule
 *
 * This module is the most popular minimap mod for forge, ported into CheatBreaker!
 */
public class ModuleZansMiniMap extends AbstractModule {
    //    @Getter private final VoxelMap voxelMap;
    public static MiniMapRules rule = MiniMapRules.NEUTRAL;

    public ModuleZansMiniMap() {
        super("Zans Minimap");
        this.setDefaultState(false);
        this.notRenderHUD = false;
        this.setDefaultAnchor(GuiAnchor.RIGHT_TOP);
//        this.voxelMap = new VoxelMap(true, true);
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/zans.png"), 42, 42);
        this.setDescription("Adds a minimap on the top right and allows you to manage waypoints.");
        this.setCreators("Zan");
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    @Override
    public void addAllEvents() {
        super.addAllEvents();
        if (rule == MiniMapRules.FORCED_OFF) {
            CheatBreaker.getInstance().getModuleManager().notificationsMod.send("Error", "&4Minimap &fis not allowed on this server. Some functions may not work.", 4000L);
        }
    }

    private void onGuiDraw(GuiDrawEvent event) {
//        float f = 1.0f / CheatBreaker.getScaleFactor();
//        switch (this.voxelMap.getMapOptions().mapCorner) {
//            case 0:
//                if (this.getGuiAnchor() == GuiAnchor.LEFT_TOP) break;
//                this.setAnchor(GuiAnchor.LEFT_TOP);
//                break;
//            case 1:
//                if (this.getGuiAnchor() == GuiAnchor.RIGHT_TOP) break;
//                this.setAnchor(GuiAnchor.RIGHT_TOP);
//                break;
//            case 2:
//                if (this.getGuiAnchor() == GuiAnchor.RIGHT_BOTTOM) break;
//                this.setAnchor(GuiAnchor.RIGHT_BOTTOM);
//                break;
//            case 3:
//                if (this.getGuiAnchor() == GuiAnchor.LEFT_BOTTOM) break;
//                this.setAnchor(GuiAnchor.LEFT_BOTTOM);
//        }
//        switch (this.voxelMap.getMapOptions().sizeModifier) {
//            case -1:
//                this.setTranslations((int)((float)-5 * f), (float)((int)((float)5 * f)));
//                this.setDimensions((int)((float)100 * f), (int)((float)100 * f));
//                break;
//            case 0:
//                this.setTranslations((int)((float)-5 * f), (float)((int)((float)5 * f)));
//                this.setDimensions((int)((float)135 * f), (int)((float)135 * f));
//                break;
//            case 1:
//                this.setTranslations((int)((float)-5 * f), (float)((int)((float)5 * f)));
//                this.setDimensions((int)((float)175 * f), (int)((float)175 * f));
//        }
//        this.voxelMap.onTickInGame(Minecraft.getMinecraft());
    }
}
