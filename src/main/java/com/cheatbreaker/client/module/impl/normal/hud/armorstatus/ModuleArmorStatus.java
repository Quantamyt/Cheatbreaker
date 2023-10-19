package com.cheatbreaker.client.module.impl.normal.hud.armorstatus;

import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the user's current armor and holding item durability.
 * @see AbstractModule
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
    private final Setting damageDisplay;
    public static Setting damageDisplayType;
    public static Setting damageThreshold;
    public static RenderItem renderItem = new RenderItem();
    public static final List<ArmorStatusDamageComparable> damageColors = new ArrayList<>();
    private static final List<ArmorStatusItem> items = new ArrayList<>();
    private static ScaledResolution scaledRes;

    public ModuleArmorStatus() {
        super("Armor Status");
        this.setDefaultAnchor(GuiAnchor.RIGHT_BOTTOM);
        this.setDefaultState(false);
        this.generalOptionsLabel = new Setting(this, "label").setValue("General Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        listMode = new Setting(this, "List Mode").setValue("vertical").acceptedStringValues("vertical", "horizontal").setCustomizationLevel(CustomizationLevel.SIMPLE);
        itemName = new Setting(this, "Item Name").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        itemCount = new Setting(this, "Item Count").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) equippedItem.getValue());
        equippedItem = new Setting(this, "Equipped Item").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        showWhileTying = new Setting(this, "Show While Typing").setValue(false).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.damageOptionsLabel = new Setting(this, "label").setValue("Damage Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        damageOverlay = new Setting(this, "Damage Overlay").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        damageAmount = new Setting(this, "Show Damage Amount").setValue("ON").acceptedStringValues("ON", "If Damaged").setCustomizationLevel(CustomizationLevel.SIMPLE);
        itemDamage = new Setting(this, "Show Item Damage").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) equippedItem.getValue());
        armorDamage = new Setting(this, "Show Armor Damage").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        maxDamage = new Setting(this, "Show Max Damage").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) itemDamage.getValue() || (Boolean) armorDamage.getValue());
        this.damageDisplay = new Setting(this, "label").setValue("Damage Display").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
        damageDisplayType = new Setting(this, "Damage Display Type").setValue("value").acceptedStringValues("value", "percent", "none").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
        damageThreshold = new Setting(this, "Damage Threshold Type").setValue("percent").acceptedStringValues("percent", "value").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> ((Boolean) equippedItem.getValue() && (Boolean) itemDamage.getValue()) || (Boolean) armorDamage.getValue());
        damageColors.add(new ArmorStatusDamageComparable(10, "4"));
        damageColors.add(new ArmorStatusDamageComparable(25, "c"));
        damageColors.add(new ArmorStatusDamageComparable(40, "6"));
        damageColors.add(new ArmorStatusDamageComparable(60, "e"));
        damageColors.add(new ArmorStatusDamageComparable(80, "7"));
        damageColors.add(new ArmorStatusDamageComparable(100, "f"));
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/diamond_chestplate.png"), 34, 34);
        this.setDescription("Displays your current armor and holding item durability.");
        this.setCreators("bspkrs", "jadedcat");
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    private void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        ArrayList<ArmorStatusItem> arrayList = new ArrayList<ArmorStatusItem>();
        for (int i = 3; i >= 0; --i) {
            ItemStack itemStack = this.mc.thePlayer.inventory.armorInventory[i];
            if (itemStack == null) continue;
            arrayList.add(new ArmorStatusItem(itemStack, 16, 16, 2, i > -1));
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new ArmorStatusItem(new ItemStack(Item.getItemById(310)), 16, 16, 2, true));
            arrayList.add(new ArmorStatusItem(new ItemStack(Item.getItemById(311)), 16, 16, 2, true));
            arrayList.add(new ArmorStatusItem(new ItemStack(Item.getItemById(312)), 16, 16, 2, true));
            arrayList.add(new ArmorStatusItem(new ItemStack(Item.getItemById(313)), 16, 16, 2, true));
        }
        if ((Boolean) equippedItem.getValue() && this.mc.thePlayer.getCurrentEquippedItem() != null) {
            arrayList.add(new ArmorStatusItem(this.mc.thePlayer.getCurrentEquippedItem(), 16, 16, 2, false));
        } else if ((Boolean) equippedItem.getValue()) {
            arrayList.add(new ArmorStatusItem(new ItemStack(Item.getItemById(276)), 16, 16, 2, false));
        }
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        scaledRes = event.getScaledResolution();
        this.scaleAndTranslate(scaledRes);
        this.render(this.mc, arrayList);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        if (!(this.mc.currentScreen instanceof HudLayoutEditorGui || this.mc.currentScreen instanceof ModulePlaceGui || this.mc.currentScreen instanceof GuiChat && !(Boolean) showWhileTying.getValue())) {
            this.updateItems(this.mc);
            if (!items.isEmpty()) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                scaledRes = event.getScaledResolution();
                this.scaleAndTranslate(scaledRes);
                this.render(this.mc, items);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }

    private void updateItems(Minecraft mc) {
        items.clear();
        for (int i = 3; i >= -1; --i) {
            ItemStack itemStack = null;
            if (i == -1 && (Boolean) equippedItem.getValue()) {
                itemStack = mc.thePlayer.getCurrentEquippedItem();
            } else if (i != -1) {
                itemStack = mc.thePlayer.inventory.armorInventory[i];
            }
            if (itemStack == null) continue;
            items.add(new ArmorStatusItem(itemStack, 16, 16, 2, i > -1));
        }
    }

    private void render(Minecraft mc, List<ArmorStatusItem> list) {
        if (list.size() > 0) {
            int n = (Boolean) itemName.getValue() ? 18 : 16;
            if (((String) listMode.getValue()).equalsIgnoreCase("vertical")) {
                int n3 = 0;
                int n4 = 0;
                boolean bl = AnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == Position.RIGHT;
                for (ArmorStatusItem armorStatusItem : list) {
                    armorStatusItem.renderTo(bl ? this.width : 0.0f, n3);
                    n3 += n;
                    if (armorStatusItem.width() <= n4) continue;
                    n4 = armorStatusItem.width();
                }
                this.height = n3;
                this.width = n4;
            } else if (((String) listMode.getValue()).equalsIgnoreCase("horizontal")) {
                boolean bl = false;
                int width = 0;
                int height = 0;
                boolean bl2 = AnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == Position.RIGHT;
                for (ArmorStatusItem armorStatusItem : list) {
                    if (bl2) {
                        width += armorStatusItem.width();
                    }
                    armorStatusItem.renderTo(width, 0.0f);
                    if (!bl2) {
                        width += armorStatusItem.width();
                    }
                    if (armorStatusItem.height() <= height) continue;
                    height += armorStatusItem.height();
                }
                this.height = height;
                this.width = width;
            }
        }
    }
}
