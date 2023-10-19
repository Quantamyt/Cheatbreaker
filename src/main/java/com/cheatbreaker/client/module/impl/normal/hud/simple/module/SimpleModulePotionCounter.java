package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Module - ModulePotionCounter
 * @see AbstractSimpleHudModule
 *
 * This module shows how many health potions/bowls of soup you have in your inventory.
 */
public class SimpleModulePotionCounter extends AbstractSimpleHudModule {
    private Setting counter;
    private Setting hideWhenEmpty;
    private Setting hideWhenSpecificAmount;
    private Setting exceedingAmount;

    public SimpleModulePotionCounter() {
        super("Potion Counter", "[10 Pots]");
        setDescription("Displays the amount of healing potions or soups in your inventory.");
        setCreators("Maxwell");
    }

    @Override
    public String getValueString() {
        if ((this.getItemFromPlayersInventory() != 0 || !(Boolean) hideWhenEmpty.getValue()) && !((Boolean) hideWhenSpecificAmount.getValue() && this.getItemFromPlayersInventory() > (Integer) exceedingAmount.getValue())) {
            return "" + this.getItemFromPlayersInventory();
        }
        return null;
    }

    public String getPreviewString() {
        return "10";
    }

    @Override
    public String getLabelString() {
        return "" + (this.getItemFromPlayersInventory() == 1 ? this.counter.getValue().toString().replaceAll("s", "") : this.counter.getValue().toString());
    }

    public void getExtraSettings() {
        this.counter = new Setting(this, "Counter", "Count Healing potions or Soup.").setValue("Pots").acceptedStringValues("Pots", "Soup").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hideWhenEmpty = new Setting(this, "Hide When Empty", "Hide the mod when there are none of the selected counter item in your inventory.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.hideWhenSpecificAmount = new Setting(this, "Hide When Above Specific Amount", "Hide the mod when there are more of the selected item than the set exceeded amount.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.exceedingAmount = new Setting(this, "Exceeded Amount", "Determine the maximum amount of the selected item should show before the mod is hidden.").setValue(10).setMinMax(0, 29).setUnit(" items").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.hideWhenSpecificAmount.getValue());
    }

    private int getItemFromPlayersInventory() {
        EntityPlayer player = mc.thePlayer;
        if (this.counter.getValue().equals("Pots")) {
            return (int) Arrays.stream(player.inventory.mainInventory).filter(Objects::nonNull).filter(item -> item.getItem() instanceof ItemPotion).filter(item -> item.getItemDamage() == 16421 | item.getItemDamage() == 16453).count();
        } else {
            return (int) Arrays.stream(player.inventory.mainInventory).filter(Objects::nonNull).filter(item -> item.getItem() instanceof ItemSoup).count();
        }

    }
}
