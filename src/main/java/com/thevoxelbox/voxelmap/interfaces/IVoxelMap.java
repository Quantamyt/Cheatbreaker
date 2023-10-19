package com.thevoxelbox.voxelmap.interfaces;

import com.thevoxelbox.voxelmap.MapSettingsManager;
import com.thevoxelbox.voxelmap.RadarSettingsManager;

public interface IVoxelMap {
	IObservableChunkChangeNotifier getNotifier();

	MapSettingsManager getMapOptions();

	RadarSettingsManager getRadarOptions();

	IMap getMap();

	IRadar getRadar();

	IColorManager getColorManager();

	IWaypointManager getWaypointManager();

	IDimensionManager getDimensionManager();

	void setPermissions(boolean paramBoolean1, boolean paramBoolean2);

	void newSubWorldName(String paramString);

	void newSubWorldHash(String paramString);
}
