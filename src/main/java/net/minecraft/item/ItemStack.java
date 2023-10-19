package net.minecraft.item;

import com.cheatbreaker.client.CheatBreaker;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.*;

public final class ItemStack {
    public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");

    /** Size of the stack. */
    public int stackSize;

    /**
     * Number of animation frames to go when receiving an item (by walking into it, for example).
     */
    public int animationsToGo;
    private Item item;

    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound stackTagCompound;

    /** Damage dealt to the item or number of use. Raise when using items. */
    private int itemDamage;

    /** Item frame this stack is on, or null if not on an item frame. */
    private EntityItemFrame itemFrame;

    public ItemStack(Block block) {
        this(block, 1);
    }

    public ItemStack(Block block, int amount) {
        this(block, amount, 0);
    }

    public ItemStack(Block block, int amount, int meta) {
        this(Item.getItemFromBlock(block), amount, meta);
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int amount) {
        this(item, amount, 0);
    }

    public ItemStack(Item item, int amount, int n2) {
        this.item = item;
        this.stackSize = amount;
        this.itemDamage = n2;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }

    public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
        ItemStack itemStack = new ItemStack();
        itemStack.readFromNBT(nbt);
        return itemStack.getItem() != null ? itemStack : null;
    }

    private ItemStack() {}

    /**
     * Remove the argument from the stack size. Return a new stack object with argument size.
     */
    public ItemStack splitStack(int amount) {
        ItemStack itemStack = new ItemStack(this.item, amount, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= amount;
        return itemStack;
    }

    /**
     * Returns the object corresponding to the stack.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns the icon index of the current stack.
     */
    public IIcon getIconIndex() {
        return this.getItem().getIconIndex(this);
    }

    public int getItemSpriteNumber() {
        return this.getItem().getSpriteNumber();
    }

    public boolean tryPlaceItemIntoWorld(EntityPlayer player, World world, int n, int n2, int n3, int n4, float f, float f2, float f3) {
        boolean bl = this.getItem().onItemUse(this, player, world, n, n2, n3, n4, f, f2, f3);
        if (bl) {
            player.addStat(StatList.objectUseStats[Item.getIdFromItem(this.item)], 1);
        }
        return bl;
    }

    public float func_150997_a(Block block) {
        return this.getItem().func_150893_a(this, block);
    }

    /**
     * Called whenever this item stack is equipped and right clicked. Returns the new item stack to put in the position
     * where this item is. Args: world, player
     */
    public ItemStack useItemRightClick(World world, EntityPlayer player) {
        return this.getItem().onItemRightClick(this, world, player);
    }

    public ItemStack onFoodEaten(World world, EntityPlayer player) {
        return this.getItem().onEaten(this, world, player);
    }

    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setShort("id", (short)Item.getIdFromItem(this.item));
        nbt.setByte("Count", (byte)this.stackSize);
        nbt.setShort("Damage", (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            nbt.setTag("tag", this.stackTagCompound);
        }
        return nbt;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound nBTTagCompound) {
        this.item = Item.getItemById(nBTTagCompound.getShort("id"));
        this.stackSize = nBTTagCompound.getByte("Count");
        this.itemDamage = nBTTagCompound.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
        if (nBTTagCompound.func_150297_b("tag", 10)) {
            this.stackTagCompound = nBTTagCompound.getCompoundTag("tag");
        }
    }

    /**
     * Returns maximum size of the stack.
     */
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }

    /**
     * Returns true if the ItemStack can hold 2 or more units of the item.
     */
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isItemDamaged() || !this.isItemDamaged());
    }

    /**
     * true if this itemStack is damageable
     */
    public boolean isItemStackDamageable() {
        return this.item != null && (this.item.getMaxDamage() > 0 && (!this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable")));
    }

    public boolean getHasSubtypes() {
        return this.item.getHasSubtypes();
    }

    /**
     * returns true when a damageable item is damaged
     */
    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }

    /**
     * gets the damage of an itemstack, for displaying purposes
     */
    public int getItemDamageForDisplay() {
        return this.itemDamage;
    }

    /**
     * gets the damage of an itemstack
     */
    public int getItemDamage() {
        return this.itemDamage;
    }

    /**
     * Sets the item damage of the ItemStack.
     */
    public void setItemDamage(int meta) {
        this.itemDamage = meta;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }

    /**
     * Returns the max damage an item in the stack can take.
     */
    public int getMaxDamage() {
        return this.item.getMaxDamage();
    }

    /**
     * Attempts to damage the ItemStack with par1 amount of damage, If the ItemStack has the Unbreaking enchantment
     * there is a chance for each point of damage to be negated. Returns true if it takes more damage than
     * getMaxDamage(). Returns false otherwise or if the ItemStack can't be damaged or if all points of damage are
     * negated.
     */
    public boolean attemptDamageItem(int amount, Random rand) {
        if (!this.isItemStackDamageable()) {
            return false;
        }
        if (amount > 0) {
            int n2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int n3 = 0;
            for (int i = 0; n2 > 0 && i < amount; ++i) {
                if (!EnchantmentDurability.negateDamage(this, n2, rand)) continue;
                ++n3;
            }
            if ((amount -= n3) <= 0) {
                return false;
            }
        }
        this.itemDamage += amount;
        return this.itemDamage > this.getMaxDamage();
    }

    /**
     * Damages the item in the ItemStack
     */
    public void damageItem(int amount, EntityLivingBase entity) {
        if ((!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(amount, entity.getRNG())) {
            entity.renderBrokenItemStack(this);
            --this.stackSize;
            if (entity instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer)entity;
                entityPlayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(this.item)], 1);
                if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                    entityPlayer.destroyCurrentEquippedItem();
                }
            }
            if (this.stackSize < 0) {
                this.stackSize = 0;
            }
            this.itemDamage = 0;
        }
    }

    /**
     * Calls the corresponding fct in di
     */
    public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
        boolean bl = this.item.hitEntity(this, entityIn, playerIn);
        if (bl) {
            playerIn.addStat(StatList.objectUseStats[Item.getIdFromItem(this.item)], 1);
        }
    }

    public void func_150999_a(World world, Block block, int n, int n2, int n3, EntityPlayer entityPlayer) {
        boolean bl = this.item.onBlockDestroyed(this, world, block, n, n2, n3, entityPlayer);
        if (bl) {
            entityPlayer.addStat(StatList.objectUseStats[Item.getIdFromItem(this.item)], 1);
        }
    }

    public boolean func_150998_b(Block block) {
        return this.item.func_150897_b(block);
    }

    public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
        return this.item.itemInteractionForEntity(this, playerIn, entityIn);
    }

    /**
     * Returns a new stack with the same properties.
     */
    public ItemStack copy() {
        ItemStack itemStack = new ItemStack(this.item, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return itemStack;
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
        return stackA == null && stackB == null || (stackA != null && stackB != null && ((stackA.stackTagCompound != null || stackB.stackTagCompound == null) && (stackA.stackTagCompound == null || stackA.stackTagCompound.equals(stackB.stackTagCompound))));
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        return stackA == null && stackB == null || (stackA != null && stackB != null && stackA.isItemStackEqual(stackB));
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private boolean isItemStackEqual(ItemStack stackA) {
        return this.stackSize == stackA.stackSize && (this.item == stackA.item && (this.itemDamage == stackA.itemDamage && ((this.stackTagCompound != null || stackA.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(stackA.stackTagCompound)))));
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
     * equal
     */
    public boolean isItemEqual(ItemStack other) {
        return this.item == other.item && this.itemDamage == other.itemDamage;
    }

    public String getUnlocalizedName() {
        return this.item.getUnlocalizedName(this);
    }

    /**
     * Creates a copy of a ItemStack, a null parameters will return a null.
     */
    public static ItemStack copyItemStack(ItemStack stack) {
        return stack == null ? null : stack.copy();
    }

    public String toString() {
        return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
     * maps.
     */
    public void updateAnimation(World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        this.item.onUpdate(this, world, entity, inventorySlot, isCurrentItem);
    }

    public void onCrafting(World world, EntityPlayer player, int amount) {
        player.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
        this.item.onCreated(this, world, player);
    }

    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }

    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }

    /**
     * Called when the player releases the use item button. Args: world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(World world, EntityPlayer entityPlayer, int n) {
        this.getItem().onPlayerStoppedUsing(this, world, entityPlayer, n);
    }

    /**
     * Returns true if the ItemStack has an NBTTagCompound. Currently used to store enchantments.
     */
    public boolean hasTagCompound() {
        return this.stackTagCompound != null;
    }

    /**
     * Returns the NBTTagCompound of the ItemStack.
     */
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }

    public NBTTagList getEnchantmentTagList() {
        return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
    }

    /**
     * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
     */
    public void setTagCompound(NBTTagCompound nbt) {
        this.stackTagCompound = nbt;
    }

    /**
     * returns the display name of the itemstack
     */
    public String getDisplayName() {
        NBTTagCompound nBTTagCompound;
        String string = this.getItem().getItemStackDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10) && (nBTTagCompound = this.stackTagCompound.getCompoundTag("display")).func_150297_b("Name", 8)) {
            string = nBTTagCompound.getString("Name");
        }
        return string;
    }

    public ItemStack setStackDisplayName(String displayName) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        if (!this.stackTagCompound.func_150297_b("display", 10)) {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
        return this;
    }

    public void func_135074_t() {
        if (this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10)) {
            NBTTagCompound tag = this.stackTagCompound.getCompoundTag("display");
            tag.removeTag("Name");
            if (tag.hasNoTags()) {
                this.stackTagCompound.removeTag("display");
                if (this.stackTagCompound.hasNoTags()) {
                    this.setTagCompound(null);
                }
            }
        }
    }

    /**
     * Returns true if the itemstack has a display name
     */
    public boolean hasDisplayName() {
        return this.stackTagCompound != null && (this.stackTagCompound.func_150297_b("display", 10) && this.stackTagCompound.getCompoundTag("display").func_150297_b("Name", 8));
    }

    /**
     * Return a list of strings containing information about the item
     */
    public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
        int n;
        String displayName = this.getDisplayName();
        ArrayList<String> list = new ArrayList<>();
        if (this.isCorrectFlag(ItemFlag.DISPLAY_NAME)) {

            if (this.hasDisplayName()) {
                displayName = EnumChatFormatting.ITALIC + displayName + EnumChatFormatting.RESET;
            }
            if (advanced) {
                String s = "";
                if (displayName.length() > 0) {
                    displayName = displayName + " (";
                    s = ")";
                }
                n = Item.getIdFromItem(this.item);
                displayName = this.getHasSubtypes() ? displayName + String.format("#%04d/%d%s", n, this.itemDamage, s) : displayName + String.format("#%04d%s", n, s);
            } else if (!this.hasDisplayName() && this.item == Items.filled_map) {
                displayName = displayName + " #" + this.itemDamage;
            }
            list.add(displayName);
        }
        if (this.isCorrectFlag(ItemFlag.ITEM_STACK_INFORMATION)) {
            this.item.addInformation(this, playerIn, list, advanced);
        }
        if (this.hasTagCompound()) {
            int n2;
            NBTTagList nbtTagList = this.getEnchantmentTagList();
            if (this.isCorrectFlag(ItemFlag.ENCHANTMENTS) && nbtTagList != null) {
                for (n = 0; n < nbtTagList.tagCount(); ++n) {
                    short s = nbtTagList.getCompoundTagAt(n).getShort("id");
                    n2 = nbtTagList.getCompoundTagAt(n).getShort("lvl");
                    if (Enchantment.enchantmentsList[s] == null) continue;
                    list.add(Enchantment.enchantmentsList[s].getTranslatedName(n2));
                }
            }
            if (this.stackTagCompound.func_150297_b("display", 10)) {
                NBTTagCompound var15 = this.stackTagCompound.getCompoundTag("display");
                if (this.isCorrectFlag(ItemFlag.COLOR) && var15.func_150297_b("color", 3)) {
                    if (advanced) {
                        list.add("Color: #" + Integer.toHexString(var15.getInteger("color")).toUpperCase());
                    } else {
                        list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }

                NBTTagList nbttaglist1 = var15.getTagList("Lore", 8);
                if (this.isCorrectFlag(ItemFlag.LORE) && var15.func_150299_b("Lore") == 9 && nbttaglist1.tagCount() > 0) {
                    for (n2 = 0; n2 < nbttaglist1.tagCount(); ++n2) {
                        list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + nbttaglist1.getStringTagAt(n2));
                    }
                }
            }
        }
        Multimap<String, AttributeModifier> var14 = this.getAttributeModifiers();

        if (this.isCorrectFlag(ItemFlag.ATTRIBUTE_MODIFIERS) && !var14.isEmpty()
                && CheatBreaker.getInstance().getGlobalSettings().SHOW_MODIFIERS) {
            list.add("");

            for (Map.Entry<String, AttributeModifier> entry : var14.entries()) {
                AttributeModifier attributeModifier = entry.getValue();
                double d = attributeModifier.getAmount();
                if (attributeModifier.getID() == Item.field_111210_e) {
                    d += (double) EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
                }
                double d2 = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? d : d * (double) 100;
                if (d > 0.0) {
                    list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributeModifier.getOperation(), DECIMALFORMAT.format(d2), StatCollector.translateToLocal("attribute.name." + (String) ((Map.Entry) entry).getKey())));
                    continue;
                }
                if (!(d < 0.0)) continue;
                list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributeModifier.getOperation(), DECIMALFORMAT.format(d2 *= (double) -1), StatCollector.translateToLocal("attribute.name." + (String) ((Map.Entry) entry).getKey())));
            }
        }
        if (this.isCorrectFlag(ItemFlag.UNBREAKABLE) && this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable")) {
            list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }
        if (this.isCorrectFlag(ItemFlag.DURABILITY) && advanced && this.isItemDamaged()) {
            list.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
        }
        return list;
    }

    private boolean isCorrectFlag(ItemFlag flag) {
        if (!this.hasTagCompound()) {
            return true;
        }
        if (!this.getTagCompound().hasKey("hidden")) {
            return true;
        }
        NBTTagCompound nBTTagCompound = this.getTagCompound().getCompoundTag("hidden");
        return !nBTTagCompound.hasKey(flag.name()) || nBTTagCompound.getByte(flag.name()) != 1;
    }

    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }

    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }

    /**
     * True if it is a tool and has no enchantments to begin with
     */
    public boolean isItemEnchantable() {
        return this.getItem().isItemTool(this) && !this.isItemEnchanted();
    }

    /**
     * Adds an enchantment with a desired level on the ItemStack.
     */
    public void addEnchantment(Enchantment enchantment, int n) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.func_150297_b("ench", 9)) {
            this.stackTagCompound.setTag("ench", new NBTTagList());
        }
        NBTTagList nBTTagList = this.stackTagCompound.getTagList("ench", 10);
        NBTTagCompound nBTTagCompound = new NBTTagCompound();
        nBTTagCompound.setShort("id", (short)enchantment.effectId);
        nBTTagCompound.setShort("lvl", (short)((byte)n));
        nBTTagList.appendTag(nBTTagCompound);
    }

    /**
     * True if the item has enchantment data
     */
    public boolean isItemEnchanted() {
        return this.stackTagCompound != null && this.stackTagCompound.func_150297_b("ench", 9);
    }

    public void setTagInfo(String string, NBTBase nBTBase) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(string, nBTBase);
    }

    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }

    /**
     * Return whether this stack is on an item frame.
     */
    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }

    /**
     * Set the item frame this stack is on.
     */
    public void setItemFrame(EntityItemFrame frame) {
        this.itemFrame = frame;
    }

    /**
     * Return the item frame this stack is on. Returns null if not on an item frame.
     */
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }

    /**
     * Get this stack's repair cost, or 0 if no repair cost is defined.
     */
    public int getRepairCost() {
        return this.hasTagCompound() && this.stackTagCompound.func_150297_b("RepairCost", 3) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }

    /**
     * Set this stack's repair cost.
     */
    public void setRepairCost(int cost) {
        if (!this.hasTagCompound()) {
            this.stackTagCompound = new NBTTagCompound();
        }
        this.stackTagCompound.setInteger("RepairCost", cost);
    }

    /**
     * Gets the attribute modifiers for this ItemStack.\nWill check for an NBT tag list containing modifiers for the
     * stack.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers() {
        Multimap<String, AttributeModifier> multimap;
        if (this.hasTagCompound() && this.stackTagCompound.func_150297_b("AttributeModifiers", 9)) {
            multimap = HashMultimap.create();
            NBTTagList nBTTagList = this.stackTagCompound.getTagList("AttributeModifiers", 10);
            for (int i = 0; i < nBTTagList.tagCount(); ++i) {
                NBTTagCompound nBTTagCompound = nBTTagList.getCompoundTagAt(i);
                AttributeModifier modifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nBTTagCompound);
                if (modifier.getID().getLeastSignificantBits() == 0L || modifier.getID().getMostSignificantBits() == 0L) continue;
                multimap.put(nBTTagCompound.getString("AttributeName"), modifier);
            }
        } else {
            multimap = this.getItem().getItemAttributeModifiers();
        }
        return multimap;
    }

    public void func_150996_a(Item item) {
        this.item = item;
    }

    public IChatComponent func_151000_E() {
        IChatComponent iChatComponent = new ChatComponentText("[").appendText(this.getDisplayName()).appendText("]");
        if (this.item != null) {
            NBTTagCompound nBTTagCompound = new NBTTagCompound();
            this.writeToNBT(nBTTagCompound);
            iChatComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nBTTagCompound.toString())));
            iChatComponent.getChatStyle().setColor(this.getRarity().rarityColor);
        }
        return iChatComponent;
    }
}
