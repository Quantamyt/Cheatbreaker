package com.thevoxelbox.voxelmap.interfaces;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

public interface IColorManager {
	int COLOR_FAILED_LOAD = -65025;

	BufferedImage getColorPicker();

	BufferedImage getBlockImage(int paramInt1, int paramInt2);

	boolean checkForChanges();

	int colorAdder(int paramInt1, int paramInt2);

	int colorMultiplier(int paramInt1, int paramInt2);

	int getBlockColorWithDefaultTint(int paramInt1, int paramInt2, int paramInt3);

	int getBlockColor(int paramInt1, int paramInt2, int paramInt3);

	void setSkyColor(int paramInt);

	int getMapImageInt();

	Set<Integer> getBiomeTintsAvailable();

	boolean isOptifineInstalled();

	HashMap<String, Integer[]> getBlockTintTables();

	int getAirColor();
}
