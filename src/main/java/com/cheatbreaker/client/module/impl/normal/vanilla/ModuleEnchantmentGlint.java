package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

/**
 * @Module - ModuleEnchantmentGlint
 * @see AbstractModule
 *
 * This module changes the Vanilla Minecraft enchantment glint.
 * @implNote When this module is disabled, the enchantment glint will be disabled as well.
 */
public class ModuleEnchantmentGlint extends AbstractModule {

    public Setting inventoryGlint;
    public Setting droppedItemGlint;
    public Setting heldItemGlint;
    public Setting armorGlint;

    public Setting droppedItemGlintColor;
    public Setting inventoryGlintColor;
    public Setting heldItemGlintColor;
    public Setting glintColor;
    public Setting armorGlintColor;
    public Setting showPotionColor;
    public Setting onlyRenderPotionGlint;
    public Setting excludePotionGlint;
    public Setting shinyPots;
    public Setting boxBoundary;

    public ModuleEnchantmentGlint() {
        super("Enchantment Glint");
        this.setDefaultState(true);

        // Category to toggle specific parts of enchantment glint
        new Setting(this, "label").setValue("Glint Options");
        this.inventoryGlint = new Setting(this, "Inventory Glint").setValue(true).setCondition(() -> Config.MC_VERSION.equals("1.7.10"));
        this.droppedItemGlint = new Setting(this, "Dropped Item Glint").setValue(true).setCondition(() -> Config.MC_VERSION.equals("1.7.10"));
        this.heldItemGlint = new Setting(this, "Held Item Glint").setValue(true).setCondition(() -> Config.MC_VERSION.equals("1.7.10"));
        this.armorGlint = new Setting(this, "Armor Glint").setValue(true);

        // All Shiny Pots related options
        new Setting(this, "label").setValue("Potion Options").setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.showPotionColor = new Setting(this, "Show Potion Color (Inventory)").setValue(false).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.onlyRenderPotionGlint = new Setting(this, "Only Render Potion Glint (Inventory)").setValue(false).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.excludePotionGlint = new Setting(this, "Exclude Potion Glint (Inventory)").setValue(false).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.shinyPots = new Setting(this, "Shiny Pots", "Show a glint box around potions.").setValue(false).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.boxBoundary = new Setting(this, "Shiny Pots Boundary").setValue("Entire Slot").acceptedStringValues("Entire Slot", "To Border").setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue() && (Boolean) this.shinyPots.getValue());

        // All Color options
        new Setting(this, "label").setValue("Color Options").setCondition(() -> !Config.MC_VERSION.equals("1.7.10") || (Boolean) this.inventoryGlint.getValue() || (Boolean) this.droppedItemGlint.getValue() || (Boolean) this.heldItemGlint.getValue() || (Boolean) this.armorGlint.getValue());
        this.inventoryGlintColor = new Setting(this, "Inventory Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.inventoryGlint.getValue());
        this.droppedItemGlintColor = new Setting(this, "Dropped Item Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.droppedItemGlint.getValue());
        this.heldItemGlintColor = new Setting(this, "Held Item Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> Config.MC_VERSION.equals("1.7.10") && (Boolean) this.heldItemGlint.getValue());

        this.glintColor = new Setting(this, "Item Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.armorGlintColor = new Setting(this, "Armor Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.armorGlint.getValue());

        this.setPreviewIcon(new ResourceLocation("client/icons/mods/ench_sword.png"), 32, 32);
        this.setDescription("Customize the enchantment glint and/or enable shiny pots overlay.");
    }
}
