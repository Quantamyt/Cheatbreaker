package com.cheatbreaker.client.module.impl.normal.hud.armorstatus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.module.AnchorHelper;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.Position;
import com.cheatbreaker.client.ui.util.HudUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @ModuleUtility - ArmorStatusItem
 *
 * This defines the ArmorStatusItem Object, used for the Armor Status module.
 */
public class ArmorStatusItem {
    public final ItemStack item;

    public final int iconW;
    public final int iconH;
    public final int padW;
    @Getter private int itemWidth;
    @Getter private int itemHeight;
    private int displayTextWidth;
    private int textWidth;

    private String displayText = "";
    private String text = "";

    private final boolean isArmor;

    private final Minecraft mc = Minecraft.getMinecraft();

    public ArmorStatusItem(ItemStack itemStack, int iconW, int iconH, int padW, boolean isArmor) {
        this.item = itemStack;
        this.iconW = iconW;
        this.iconH = iconH;
        this.padW = padW;
        this.isArmor = isArmor;
        this.initSize();
    }

    /**
     * Sets the size up.
     */
    private void initSize() {
        this.itemHeight = (Boolean) ModuleArmorStatus.itemName.getValue() ?
                Math.max(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * 2, this.iconH) :
                Math.max(this.mc.fontRendererObj.FONT_HEIGHT, this.iconH);

        if (this.item != null) {
            int n2;
            int n3;
            boolean damageAmount = ModuleArmorStatus.damageAmount.getValue().equals("ON") ? this.item.isItemStackDamageable() : this.item.isItemDamaged();

            if ((this.isArmor && (Boolean) ModuleArmorStatus.armorDamage.getValue()
                    || !this.isArmor
                    && (Boolean) ModuleArmorStatus.itemDamage.getValue())
                    && damageAmount) {
                n3 = this.item.getMaxDamage() + 1;
                n2 = n3 - this.item.getItemDamage();

                if (((String) ModuleArmorStatus.damageDisplayType.getValue()).equalsIgnoreCase("value")) {
                    this.text = "§" + ArmorStatusDamageComparable.getDamageColor(ModuleArmorStatus.damageColors, ((String) ModuleArmorStatus.damageThreshold.getValue()).equalsIgnoreCase("percent") ? n2 * 100 / n3 : n2) + n2 + ((Boolean) ModuleArmorStatus.maxDamage.getValue() ? "/" + n3 : "");
                } else if (((String) ModuleArmorStatus.damageDisplayType.getValue()).equalsIgnoreCase("percent")) {
                    this.text = "§" + ArmorStatusDamageComparable.getDamageColor(ModuleArmorStatus.damageColors, ((String) ModuleArmorStatus.damageThreshold.getValue()).equalsIgnoreCase("percent") ? n2 * 100 / n3 : n2) + n2 * 100 / n3 + "%";
                }
            }
            this.textWidth = this.mc.fontRendererObj.getStringWidth(HudUtil.getColorCode(this.text));
            this.itemWidth = this.padW + this.iconW +
                    this.padW + this.textWidth;

            if ((Boolean) ModuleArmorStatus.itemName.getValue()) {
                this.displayText = this.item.getDisplayName();
                this.itemWidth = this.padW +
                        this.iconW + this.padW +
                        Math.max(this.mc.fontRendererObj.getStringWidth(HudUtil.getColorCode(this.displayText)), this.textWidth);
            }

            this.displayTextWidth = this.mc.fontRendererObj.getStringWidth(HudUtil.getColorCode(this.displayText));
        }
    }

    /**
     * Draws the actual item and health integer.
     */
    public void renderTo(float f, float f2) {
        boolean bl;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        ModuleArmorStatus.renderItem.zLevel = -160;
        GuiAnchor cBGuiAnchor = CheatBreaker.getInstance().getModuleManager().armourStatus.getGuiAnchor();
        bl = AnchorHelper.getHorizontalPositionEnum(cBGuiAnchor) == Position.RIGHT;

        if (bl) {
            ModuleArmorStatus.renderItem.renderItemAndEffectIntoGUI(this.item, (int) (f - (float) (this.iconW + this.padW)), (int) f2);
            HudUtil.renderItemOverlayIntoGUI(this.mc.fontRendererObj, this.item, (int) (f - (float) (this.iconW + this.padW)),
                    (int) f2, (Boolean) ModuleArmorStatus.damageOverlay.getValue(),
                    (Boolean) ModuleArmorStatus.itemCount.getValue());

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            GlStateManager.disableBlend();
            this.mc.fontRendererObj.drawStringWithShadow(this.displayText + "§r", f - (float) (this.padW + this.iconW + this.padW) - (float) this.displayTextWidth, f2, 0xFFFFFF);
            this.mc.fontRendererObj.drawStringWithShadow(this.text + "§r", f - (float) (this.padW + this.iconW + this.padW) - (float) this.textWidth, f2 + (float) ((Boolean) ModuleArmorStatus.itemName.getValue() ? this.itemHeight / 2 : this.itemHeight / 4), 0xFFFFFF);
        } else {
            ModuleArmorStatus.renderItem.renderItemAndEffectIntoGUI(this.item, (int) f, (int) f2);
            HudUtil.renderItemOverlayIntoGUI(this.mc.fontRendererObj, this.item, (int) f, (int) f2, ModuleArmorStatus.damageOverlay.getBooleanValue(), (Boolean) ModuleArmorStatus.itemCount.getValue());
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            GlStateManager.disableBlend();
            this.mc.fontRendererObj.drawStringWithShadow(this.displayText + "§r", f + (float) this.iconW + (float) this.padW, f2, 0xFFFFFF);
            this.mc.fontRendererObj.drawStringWithShadow(this.text + "§r", f + (float) this.iconW + (float) this.padW, f2 + (float) ((Boolean) ModuleArmorStatus.itemName.getValue() ? this.itemHeight / 2 : this.itemHeight / 4), 0xFFFFFF);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
