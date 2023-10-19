package com.thevoxelbox.voxelmap.interfaces;

import com.thevoxelbox.voxelmap.util.Dimension;
import java.util.ArrayList;

public interface IDimensionManager {
	ArrayList<Dimension> getDimensions();

	Dimension getDimensionByID(int paramInt);

	void enteredDimension(int paramInt);

	void populateDimensions();
}
