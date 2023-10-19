package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

public class ModuleEnchantmentGlint extends AbstractModule {
    public Setting inventoryGlint;
    public Setting droppedItemGlint;
    public Setting heldItemGlint;
    public Setting armorGlint;
    public Setting droppedItemGlintColor;
    public Setting inventoryGlintColor;
    public Setting heldItemGlintColor;
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
        inventoryGlint = new Setting(this, "Inventory Glint").setValue(true);
        droppedItemGlint = new Setting(this, "Dropped Item Glint").setValue(true);
        heldItemGlint = new Setting(this, "Held Item Glint").setValue(true);
        armorGlint = new Setting(this, "Armor Glint").setValue(true);

        // All Shiny Pots related options
        new Setting(this, "label").setValue("Potion Options").setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        showPotionColor = new Setting(this, "Show Potion Color (Inventory)").setValue(false).setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        onlyRenderPotionGlint = new Setting(this, "Only Render Potion Glint (Inventory)").setValue(false).setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        excludePotionGlint = new Setting(this, "Exclude Potion Glint (Inventory)").setValue(false).setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        shinyPots = new Setting(this, "Shiny Pots", "Show a glint box around potions.").setValue(false).setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        boxBoundary = new Setting(this, "Shiny Pots Boundary").setValue("Entire Slot").acceptedStringValues("Entire Slot", "To Border").setCondition(() -> (Boolean) this.inventoryGlint.getValue() && (Boolean) this.shinyPots.getValue());

        // All Color options
        new Setting(this, "label").setValue("Color Options").setCondition(() -> (Boolean) this.inventoryGlint.getValue() || (Boolean) this.droppedItemGlint.getValue() || (Boolean) this.heldItemGlint.getValue() || (Boolean) this.armorGlint.getValue());
        inventoryGlintColor = new Setting(this, "Inventory Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.inventoryGlint.getValue());
        droppedItemGlintColor = new Setting(this, "Dropped Item Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.droppedItemGlint.getValue());
        heldItemGlintColor = new Setting(this, "Held Item Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.heldItemGlint.getValue());
        armorGlintColor = new Setting(this, "Armor Glint Color").setValue(-8372020).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> (Boolean) this.armorGlint.getValue());

        setPreviewIcon(new ResourceLocation("client/icons/mods/ench_sword.png"), 32, 32);
        setDescription("Customize the enchantment glint and/or enable shiny pots overlay.");
    }
}
