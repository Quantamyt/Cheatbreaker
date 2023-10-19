package com.cheatbreaker.client.ui.module;

import com.cheatbreaker.client.module.AbstractModule;
import net.minecraft.client.gui.ScaledResolution;

public class AnchorHelper {//TODO: Map this class
    public static GuiAnchor getAnchor(float f, float f2, ScaledResolution scaledResolution) {
        int n = scaledResolution.getScaledWidth();
        int n2 = scaledResolution.getScaledHeight();
        if (f < (float)(n / 3) && f2 < (float)(n2 / 3)) {
            return GuiAnchor.LEFT_TOP;
        }
        if (f > (float)(n / 3 * 2) && f2 < (float)(n2 / 3)) {
            return GuiAnchor.RIGHT_TOP;
        }
        if (f2 < (float)(n2 / 3)) {
            return GuiAnchor.MIDDLE_TOP;
        }
        if (f < (float)(n / 3) && f2 < (float)(n2 / 3 * 2)) {
            return GuiAnchor.LEFT_MIDDLE;
        }
        if (f > (float)(n / 3 * 2) && f2 < (float)(n2 / 3 * 2)) {
            return GuiAnchor.RIGHT_MIDDLE;
        }
        if (f2 < (float)(n2 / 3 * 2)) {
            return GuiAnchor.MIDDLE_MIDDLE;
        }
        if (f < (float)(n / 3)) {
            return GuiAnchor.LEFT_BOTTOM;
        }
        if (f < (float)(n / 3 * 2)) {
            if (f > (float)(n / 3 + n / 6)) {
                return GuiAnchor.MIDDLE_BOTTOM_RIGHT;
            }
            return GuiAnchor.MIDDLE_BOTTOM_LEFT;
        }
        return GuiAnchor.RIGHT_BOTTOM;
    }

    public static float[] getPositions(float f, float f2, ScaledResolution scaledResolution) {
        float f3 = scaledResolution.getScaledWidth();
        float f4 = scaledResolution.getScaledHeight();
        GuiAnchor cBGuiAnchor = AnchorHelper.getAnchor(f, f2, scaledResolution);
        float f5 = 0.0f;
        float f6 = 0.0f;
        switch (cBGuiAnchor) {
            case LEFT_TOP:
                f5 = 0.0f;
                f6 = 0.0f;
                break;
            case RIGHT_TOP:
                f5 = f3 / 3.0F * 2.0f;
                f6 = 0.0f;
                break;
            case MIDDLE_TOP:
                f5 = f3 / 3.0F;
                f6 = 0.0f;
                break;
            case LEFT_MIDDLE:
                f5 = 0.0f;
                f6 = f4 / 3.0F;
                break;
            case RIGHT_MIDDLE:
                f5 = f3 / 3.0F * 2.0f;
                f6 = f4 / 3.0F;
                break;
            case MIDDLE_MIDDLE:
                f5 = f3 / 3.0F;
                f6 = f4 / 3.0F;
                break;
            case LEFT_BOTTOM:
                f5 = 0.0f;
                f6 = f4 / 3.0F * 2.0f;
                break;
            case MIDDLE_BOTTOM_RIGHT:
                f5 = f3 / 3.0F + f3 / (float)6;
                f6 = f4 / 3.0F * 2.0f;
                break;
            case MIDDLE_BOTTOM_LEFT:
                f5 = f3 / 3.0F;
                f6 = f4 / 3.0F * 2.0f;
                break;
            case RIGHT_BOTTOM:
                f5 = f3 / 3.0F * 2.0f;
                f6 = f4 / 3.0F * 2.0f;
        }
        return new float[]{f5, f6};
    }

    public static float[] getPositions(GuiAnchor anchor, ScaledResolution scaledResolution, float f, float f2, float f3) {
        float f4 = 0.0f;
        float f5 = 0.0f;
        f *= f3;
        f2 *= f3;
        switch (anchor) {
            case LEFT_TOP:
                f4 = 2.0f;
                f5 = 2.0f;
                break;
            case LEFT_MIDDLE:
                f4 = 2.0f;
                f5 = (float) (scaledResolution.getScaledHeight() / 2) - f2 / 2.0f;
                break;
            case LEFT_BOTTOM:
                f5 = (float) scaledResolution.getScaledHeight() - f2 - 2.0f;
                f4 = 2.0f;
                break;
            case MIDDLE_TOP:
                f4 = (float) (scaledResolution.getScaledWidth() / 2) - f / 2.0f;
                f5 = 2.0f;
                break;
            case MIDDLE_MIDDLE:
                f4 = (float) (scaledResolution.getScaledWidth() / 2) - f / 2.0f;
                f5 = (float) (scaledResolution.getScaledHeight() / 2) - f2 / 2.0f;
                break;
            case MIDDLE_BOTTOM_LEFT:
                f4 = (float) (scaledResolution.getScaledWidth() / 2) - f;
                f5 = (float) scaledResolution.getScaledHeight() - f2 - 2.0f;
                break;
            case MIDDLE_BOTTOM_RIGHT:
                f4 = scaledResolution.getScaledWidth() / 2;
                f5 = (float) scaledResolution.getScaledHeight() - f2 - 2.0f;
                break;
            case RIGHT_TOP:
                f4 = (float) scaledResolution.getScaledWidth() - f - 2.0f;
                f5 = 2.0f;
                break;
            case RIGHT_MIDDLE:
                f4 = (float) scaledResolution.getScaledWidth() - f;
                f5 = (float) (scaledResolution.getScaledHeight() / 2) - f2 / 2.0f;
                break;
            case RIGHT_BOTTOM:
                f4 = (float) scaledResolution.getScaledWidth() - f;
                f5 = (float) scaledResolution.getScaledHeight() - f2;
        }
        return new float[]{f4, f5};
    }

    public static float[] getPositions(AbstractModule module, float f, float f2, ScaledResolution scaledResolution) {
        float f3 = scaledResolution.getScaledWidth();
        float f4 = scaledResolution.getScaledHeight();
        GuiAnchor cBGuiAnchor = AnchorHelper.getAnchor(f, f2, scaledResolution);
        float f5 = module.width * (Float) module.masterScale();
        float f6 = module.height * (Float) module.masterScale();
        float f7 = 0.0f;
        float f8 = 0.0f;
        switch (cBGuiAnchor) {
            case LEFT_TOP:
                f7 = f5 / 2.0f;
                f8 = f6 / 2.0f;
                break;
            case RIGHT_TOP:
                f7 = f3 / 3.0F - f5 / 2.0f;
                f8 = f6 / 2.0f;
                break;
            case MIDDLE_TOP:
                f7 = f3 / 6.0F;
                f8 = f6 / 2.0f;
                break;
            case LEFT_MIDDLE:
                f7 = f5 / 2.0f;
                f8 = f4 / (float)6;
                break;
            case RIGHT_MIDDLE:
                f7 = f3 / 3.0F - f5 / 2.0f;
                f8 = f4 / 6.0F;
                break;
            case MIDDLE_MIDDLE:
                f7 = f3 / 6.0F;
                f8 = f4 / 6.0F;
                break;
            case LEFT_BOTTOM:
                f7 = f5 / 2.0f;
                f8 = f4 / 3.0F - f6 / 2.0f;
                break;
            case MIDDLE_BOTTOM_RIGHT:
                f7 = f5 / 2.0f;
                f8 = f4 / 3.0F - f6 / 2.0f;
                break;
            case MIDDLE_BOTTOM_LEFT:
                f7 = f3 / 6.0F - f5 / 2.0f;
                f8 = f4 / 3.0F - f6 / 2.0f;
                break;
            case RIGHT_BOTTOM:
                f7 = f3 / 3.0F - f5 / 2.0f;
                f8 = f4 / 3.0F - f6 / 2.0f;
        }
        return new float[]{f7, f8};
    }

    public static float[] getSnapPositions(GuiAnchor anchor) {
        float f = 0.0f;
        float f2 = 0.0f;
        switch (anchor) {
            case RIGHT_MIDDLE:
                f = -2;
                break;
            case LEFT_BOTTOM:
                f = 2.0f;
                f2 = -34;
                break;
            case MIDDLE_MIDDLE:
                f2 = -50;
                f = 0.0f;
        }
        return new float[]{f, f2};
    }

    public static Position getVerticalPositionEnum(GuiAnchor cBGuiAnchor) {
        switch (cBGuiAnchor) {
            case LEFT_TOP:
            case RIGHT_TOP:
            case MIDDLE_TOP:
                return Position.TOP;
            case LEFT_MIDDLE:
            case RIGHT_MIDDLE:
            case MIDDLE_MIDDLE:
                return Position.CENTER;
            case LEFT_BOTTOM:
            case MIDDLE_BOTTOM_RIGHT:
            case MIDDLE_BOTTOM_LEFT:
            case RIGHT_BOTTOM:
                return Position.BOTTOM;
        }
        return null;
    }

    public static Position getHorizontalPositionEnum(GuiAnchor cBGuiAnchor) {
        switch (cBGuiAnchor) {
            case LEFT_TOP:
            case LEFT_MIDDLE:
            case LEFT_BOTTOM:
            case MIDDLE_BOTTOM_RIGHT:
                return Position.LEFT;
            case MIDDLE_TOP:
            case MIDDLE_MIDDLE:
                return Position.CENTER;
            case RIGHT_TOP:
            case RIGHT_MIDDLE:
            case MIDDLE_BOTTOM_LEFT:
            case RIGHT_BOTTOM:
                return Position.RIGHT;
        }
        return null;
    }
}
