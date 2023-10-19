package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;

import java.util.Arrays;
import java.util.Objects;

/**
 * Displays how many health potions/bowls of soup the user has in their inventory.
 * @see AbstractSimpleHudModule
 */
public class ModulePotionCounter extends AbstractSimpleHudModule {
    private Setting counter;
    private Setting hideWhenEmpty;

    public ModulePotionCounter() {
        super("Potion Counter", "[10 Pots]");
        this.setDescription("Displays the amount of healing potions or bowls of soup in your inventory.");
        this.setCreators("Maxwell");
    }

    public String getValueString() {
        if (this.excludeArrayOptions(this.hideValue, this.getItemFromPlayersInventory(), this.hiddenValue.getIntegerValue())) {
            return null;
        }
        if (this.getItemFromPlayersInventory() == 0 && this.hideWhenEmpty.getBooleanValue()) {
            return null;
        }
        return "" + this.getItemFromPlayersInventory();

    }

    public String getPreviewString() {
        return "10";
    }

    public String getLabelString() {
        return  "" + (this.getItemFromPlayersInventory() == 1 ? this.counter.getValue().toString().replaceAll("s", "") : this.counter.getValue().toString());
    }

    public void getExtraSettings() {
        this.hideWhenEmpty = new Setting(this, "Hide When Empty", "Hide the mod when there are none of the selected counter item in your inventory.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.counter = new Setting(this, "Counter", "Count Healing potions or Soup.").setValue("Pots").acceptedStringValues("Pots", "Soup").setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    private int getItemFromPlayersInventory() {
        final EntityClientPlayerMP player = mc.thePlayer;
        if (this.counter.getValue().equals("Pots")) {
            return (int) Arrays.stream(player.inventory.mainInventory).filter(Objects::nonNull).filter(item -> item.getItem() instanceof ItemPotion).filter(item -> item.getItemDamage() == 16421 | item.getItemDamage() == 16453).count();
        } else {
            return (int) Arrays.stream(player.inventory.mainInventory).filter(Objects::nonNull).filter(item -> item.getItem() instanceof ItemSoup).count();
        }

    }
}
