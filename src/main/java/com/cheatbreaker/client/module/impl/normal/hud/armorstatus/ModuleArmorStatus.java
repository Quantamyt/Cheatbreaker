package com.cheatbreaker.client.module.impl.normal.hud.armorstatus;

import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.render.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * @Module - ModuleArmorStatus
 * @see AbstractModule
 *
 * This mod displays what item you are holding, and the armor you're wearing, and its health.
 */
public class ModuleArmorStatus extends AbstractModule {
    private final Setting generalOptionsLabel;
    public static Setting listMode;
    public static Setting itemName;
    public static Setting itemCount;
    public static Setting showWhileTying;
    public static Setting equippedItem;

    private final Setting damageOptionsLabel;
    public static Setting damageOverlay;
    public static Setting damageAmount;
    public static Setting itemDamage;
    public static Setting armorDamage;
    public static Setting maxDamage;

    private final Setting damageDisplayLabel;
    public static Setting damageDisplayType;
    public static Setting damageThreshold;
    public static RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    public static final List<ArmorStatusDamageComparable> damageColors = new ArrayList<>();
    private static final List<ArmorStatusItem> items = new ArrayList<>();
    private static ScaledResolution scaledRes;

    public ModuleArmorStatus() {
        super("Armor Status");
        this.setDefaultAnchor(GuiAnchor.RIGHT_BOTTOM);
        this.setDefaultState(false);

        {
            this.generalOptionsLabel = new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
            listMode = new Setting(this, "List Mode").setValue("Vertical").acceptedStringValues("Vertical", "Horizontal").setCustomizationLevel(CustomizationLevel.SIMPLE);
            itemName = new Setting(this, "Item Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
            itemCount = new Setting(this, "Item Count").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) equippedItem.getValue());
            equippedItem = new Setting(this, "Equipped Item").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
            showWhileTying = new Setting(this, "Show While Typing").setValue(false).setCustomizationLevel(CustomizationLevel.ADVANCED);
        }
        {
            this.damageOptionsLabel = new Setting(this, "label").setValue("Damage Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
            damageOverlay = new Setting(this, "Damage Overlay").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
            damageAmount = new Setting(this, "Show Damage Amount").setValue("ON").acceptedStringValues("ON", "If Damaged").setCustomizationLevel(CustomizationLevel.SIMPLE);
            itemDamage = new Setting(this, "Show Item Damage").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) equippedItem.getValue());
            armorDamage = new Setting(this, "Show Armor Damage").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
            maxDamage = new Setting(this, "Show Max Damage").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) itemDamage.getValue() || (Boolean) armorDamage.getValue());
        }
        {
            this.damageDisplayLabel = new Setting(this, "label").setValue("Damage Display").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
            damageDisplayType = new Setting(this, "Damage Display Type").setValue("Value").acceptedStringValues("Value", "Percent", "None").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
            damageThreshold = new Setting(this, "Damage Threshold Type").setValue("Percent").acceptedStringValues("Percent", "Value").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
        }
        {
            damageColors.add(new ArmorStatusDamageComparable(10, "4"));
            damageColors.add(new ArmorStatusDamageComparable(25, "c"));
            damageColors.add(new ArmorStatusDamageComparable(40, "6"));
            damageColors.add(new ArmorStatusDamageComparable(60, "e"));
            damageColors.add(new ArmorStatusDamageComparable(80, "7"));
            damageColors.add(new ArmorStatusDamageComparable(100, "f"));
        }

        this.setPreviewIcon(new ResourceLocation("client/icons/mods/diamond_chestplate.png"), 34, 34);
        this.setDescription("Displays your current armor and holding item durability.");
        this.setCreators("bspkrs", "jadedcat");
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    /**
     * Draws the preview.
     */
    private void onPreviewDraw(PreviewDrawEvent event) {
        ArrayList<ArmorStatusItem> items = new ArrayList<>();

        if (!this.isRenderHud()) {
            return;
        }

        for (int i = 3; i >= 0; --i) {
            ItemStack itemStack = this.mc.thePlayer.inventory.armorInventory[i];
            if (itemStack == null) continue;
            items.add(new ArmorStatusItem(itemStack, 16, 16, 2, i > -1));
        }

        if (items.isEmpty()) {
            items.add(new ArmorStatusItem(new ItemStack(Item.getItemById(310)), 16, 16, 2, true));
            items.add(new ArmorStatusItem(new ItemStack(Item.getItemById(311)), 16, 16, 2, true));
            items.add(new ArmorStatusItem(new ItemStack(Item.getItemById(312)), 16, 16, 2, true));
            items.add(new ArmorStatusItem(new ItemStack(Item.getItemById(313)), 16, 16, 2, true));
        }

        if ((Boolean) equippedItem.getValue() && this.mc.thePlayer.getCurrentEquippedItem() != null) {
            items.add(new ArmorStatusItem(this.mc.thePlayer.getCurrentEquippedItem(), 16, 16, 2, false));
        } else if ((Boolean) equippedItem.getValue()) {
            items.add(new ArmorStatusItem(new ItemStack(Item.getItemById(276)), 16, 16, 2, false));
        }

        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        scaledRes = event.getScaledResolution();
        this.scaleAndTranslate(scaledRes);
        this.render(items);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    /**
     * Draws the real module.
     */
    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }

        if (!(this.mc.currentScreen instanceof HudLayoutEditorGui || this.mc.currentScreen instanceof ModulePlaceGui || this.mc.currentScreen instanceof GuiChat && !(Boolean) showWhileTying.getValue())) {
            this.updateItems(this.mc);
            if (!items.isEmpty()) {
                GL11.glPushMatrix();
                GlStateManager.enableBlend();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                scaledRes = event.getScaledResolution();
                this.scaleAndTranslate(scaledRes);
                this.render(items);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Updates the items on the module.
     */
    private void updateItems(Minecraft mc) {
        items.clear();

        for (int i = 3; i >= -1; --i) {
            ItemStack item = null;

            if (i == -1 && (Boolean) equippedItem.getValue()) {
                item = mc.thePlayer.getCurrentEquippedItem();
            } else if (i != -1) {
                item = mc.thePlayer.inventory.armorInventory[i];
            }

            if (item == null) continue;
            items.add(new ArmorStatusItem(item, 16, 16, 2, i > -1));
        }
    }

    /**
     * Renders the module.
     */
    private void render(List<ArmorStatusItem> list) {
        if (list.size() > 0) {
            int n = (Boolean) itemName.getValue() ? 18 : 16;
            if (((String) listMode.getValue()).equalsIgnoreCase("vertical")) {
                int n3 = 0;
                int n4 = 0;
                boolean bl = AnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == Position.RIGHT;
                for (ArmorStatusItem armorStatusItem : list) {
                    armorStatusItem.renderTo(bl ? this.width : 0.0f, n3);
                    n3 += n;
                    if (armorStatusItem.getItemWidth() <= n4) continue;
                    n4 = armorStatusItem.getItemWidth();
                }
                this.height = n3;
                this.width = n4;
            } else if (((String) listMode.getValue()).equalsIgnoreCase("horizontal")) {
                int width = 0;
                int height = 0;
                boolean bl2 = AnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == Position.RIGHT;
                for (ArmorStatusItem armorStatusItem : list) {
                    if (bl2) {
                        width += armorStatusItem.getItemWidth();
                    }
                    armorStatusItem.renderTo(width, 0.0f);
                    if (!bl2) {
                        width += armorStatusItem.getItemWidth();
                    }
                    if (armorStatusItem.getItemHeight() <= height) continue;
                    height += armorStatusItem.getItemHeight();
                }
                this.height = height;
                this.width = width;
            }
        }
    }
}
