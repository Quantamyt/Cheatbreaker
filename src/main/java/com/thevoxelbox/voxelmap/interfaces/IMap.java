package com.thevoxelbox.voxelmap.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;

public interface IMap {
	String getCurrentWorldName();

	void forceFullRender(boolean paramBoolean);

	void drawMinimap(Minecraft paramMinecraft);

	void chunkCalc(Chunk paramChunk);

	float getPercentX();

	float getPercentY();

	void onTickInGame(Minecraft paramMinecraft);

	void setPermissions(boolean paramBoolean1, boolean paramBoolean2);
}
