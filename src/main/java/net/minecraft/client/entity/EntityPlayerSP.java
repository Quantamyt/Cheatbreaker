package net.minecraft.client.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.module.command.ModuleCommand;
import com.cheatbreaker.client.module.impl.normal.hud.simple.module.SimpleModuleToggleSprint;
import com.cheatbreaker.client.util.ClientCredits;
import net.minecraft.MinecraftMovementInputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.src.Config;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import org.lwjgl.opengl.Display;

import java.util.UUID;

public class EntityPlayerSP extends AbstractClientPlayer {
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter statWriter;
    private double lastReportedPosX;
    private double lastReportedPosY;
    private double lastReportedPosZ;
    private float lastReportedYaw;
    private float lastReportedPitch;
    private boolean serverSneakState;
    private boolean serverSprintState;
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String clientBrand;
    public MovementInput movementInput;
    protected Minecraft mc;

    /**
     * Used to tell if the player pressed forward twice. If this is at 0 and it's pressed (And they are allowed to
     * sprint, aka enough food on the ground etc) it sets this to 7. If it's pressed and it's greater than 0 enable
     * sprinting.
     */
    protected int sprintToggleTimer;

    /**
     * Ticks left before sprinting is disabled.
     */
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;

    /**
     * The amount of time an entity has been in a Portal
     */
    public float timeInPortal;

    /**
     * The amount of time an entity has been in a Portal the previous tick
     */
    public float prevTimeInPortal;

    private final MinecraftMovementInputHelper minecraftMovementInputHelper;

    public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
        super(worldIn, netHandler.getGameProfile());
        this.minecraftMovementInputHelper = new MinecraftMovementInputHelper(Minecraft.getMinecraft().gameSettings);
        this.sendQueue = netHandler;
        this.statWriter = statFile;
        this.mc = mcIn;
        this.dimension = 0;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    public void heal(float healAmount) {
    }

    public void mountEntity(Entity entityIn) {
        super.mountEntity(entityIn);

        if (entityIn instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart) entityIn));
        }
    }

    public void onUpdate() {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
            super.onUpdate();

            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            } else {
                this.onUpdateWalkingPlayer();
            }
        }
    }

    public void onUpdateWalkingPlayer() {
        boolean flag = this.isSprinting();

        if (flag != this.serverSprintState) {
            if (flag) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.serverSprintState = flag;
        }

        boolean flag1 = this.isSneaking();

        if (flag1 != this.serverSneakState) {
            if (flag1) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity()) {
            double d0 = this.posX - this.lastReportedPosX;
            double d1 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            double d2 = this.posZ - this.lastReportedPosZ;
            double d3 = (double) (this.rotationYaw - this.lastReportedYaw);
            double d4 = (double) (this.rotationPitch - this.lastReportedPitch);
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;

            if (this.ridingEntity == null) {
                if (flag2 && flag3) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
                } else if (flag2) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
                } else if (flag3) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                } else {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                }
            } else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            }

            ++this.positionUpdateTicks;

            if (flag2) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = this.getEntityBoundingBox().minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (flag3) {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
        }
    }

    public EntityItem dropOneItem(boolean dropAll) {
        C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    protected void joinEntityItemWithWorld(EntityItem itemIn) {
    }

    public void sendChatMessage(String message) {

        if (message.equals("/cb debug")) {
            CheatBreaker.getInstance().getGlobalSettings().isDebug = !CheatBreaker.getInstance().getGlobalSettings().isDebug;

            Display.setTitle("Minecraft " + Config.MC_VERSION +
                    (CheatBreaker.getInstance().getGlobalSettings().isDebug ? " - "
                            + CheatBreaker.getInstance().getGitBuildVersion()
                            + " Build (" + CheatBreaker.getInstance().getGitCommitIdAbbrev()
                            + "/" + CheatBreaker.getInstance().getGitBranch() + ")" : ""));

            ChatComponentText status = new ChatComponentText(EnumChatFormatting.GRAY + "Debug: " + EnumChatFormatting.RESET + CheatBreaker.getInstance().getGlobalSettings().isDebug);
            status.setBranded(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(status);
        } else if (message.equals("/cb credits") && !ClientCredits.isCreditedUser(UUID.fromString(this.mc.getSession().getPlayerID()))) {
            ClientCredits.sendCredits();
        } else if (message.startsWith("/cb emote")) {
            String[] args = message.split(" ");

            if (args.length != 3) {
                ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
                ChatComponentText arguments = new ChatComponentText(EnumChatFormatting.RED + "Usage: /cb emote <id>");
                prefix.appendSibling(arguments);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix);
                return;
            }

            if (args[2].equals("list")) {
                ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
                StringBuilder emotes = new StringBuilder();

                for (int id : CheatBreaker.getInstance().getCosmeticManager().getEmotes()) {
                    Emote emote = CheatBreaker.getInstance().getCosmeticManager().getEmoteById(id);
                    if (!emotes.toString().equals("")) emotes.append(", ");

                    emotes.append(emote.getName()).append(" (ID: ").append(id).append(")");
                }

                prefix.appendSibling(new ChatComponentText(EnumChatFormatting.GRAY + "Emotes: " + emotes));
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix);
                return;
            }

            int id;
            try {
                id = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sendIncorrectEmoteId(args[2]);
                return;
            }

            Emote emote = CheatBreaker.getInstance().getCosmeticManager().getEmoteById(id);
            if (emote == null) {
                sendIncorrectEmoteId(id + "");
                return;
            }

            ChatComponentText chatComponentText = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
            ChatComponentText chatComponentText2 = new ChatComponentText(EnumChatFormatting.GRAY + "Doing emote: " + emote.getName());
            chatComponentText.appendSibling(chatComponentText2);

            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatComponentText);
            CheatBreaker.getInstance().getCosmeticManager().playEmote(Minecraft.getMinecraft().thePlayer, emote);
        } else {
            if (CheatBreaker.getInstance().getGlobalSettings().enableModuleCommands.getBooleanValue()) {
                for (ModuleCommand modCommand : CheatBreaker.getInstance().getModuleCommandManager().moduleCommands) {
                    if (message.equals(modCommand.getCommand())) {
                        modCommand.handle();
                        return;
                    }
                }
            }

            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
            CheatBreaker.getInstance().getModuleManager().chatMod.dingCooldown.add(System.currentTimeMillis());
        }
    }


    private void sendIncorrectEmoteId(String id) {
        ChatComponentText chatComponentText = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
        ChatComponentText chatComponentText2 = new ChatComponentText(EnumChatFormatting.GRAY + "Invalid Emote ID: " + id);
        chatComponentText.appendSibling(chatComponentText2);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatComponentText);
    }

    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }

    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (!this.isEntityInvulnerable(damageSrc)) {
            this.setHealth(this.getHealth() - damageAmount);
        }
    }

    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack() {
        this.inventory.setItemStack( null);
        super.closeScreen();
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().guiBlur.getValue()) {
            Minecraft.getMinecraft().entityRenderer.stopUseShader();
        }
        this.mc.displayGuiScreen( null);
    }

    public void setPlayerSPHealth(float health) {
        if (this.hasValidHealth) {
            float f = this.getHealth() - health;

            if (f <= 0.0F) {
                this.setHealth(health);

                if (f < 0.0F) {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            } else {
                this.lastDamage = f;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, f);
                this.hurtTime = this.maxHurtTime = 10;
            }
        } else {
            this.setHealth(health);
            this.hasValidHealth = true;
        }
    }

    public void addStat(StatBase stat, int amount) {
        if (stat != null) {
            if (stat.isIndependent) {
                super.addStat(stat, amount);
            }
        }
    }

    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }

    public boolean isUser() {
        return true;
    }

    protected void sendHorseJump() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int) (this.getHorseJumpPower() * 100.0F)));
    }

    public void sendHorseInventory() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public void setClientBrand(String brand) {
        this.clientBrand = brand;
    }

    public String getClientBrand() {
        return this.clientBrand;
    }

    public StatFileWriter getStatFileWriter() {
        return this.statWriter;
    }

    public void addChatComponentMessage(IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    protected boolean pushOutOfBlocks(double x, double y, double z) {
        if (this.noClip) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(x, y, z);
            double d0 = x - (double) blockpos.getX();
            double d1 = z - (double) blockpos.getZ();

            if (!this.isOpenBlockSpace(blockpos)) {
                int i = -1;
                double d2 = 9999.0D;

                if (this.isOpenBlockSpace(blockpos.west()) && d0 < d2) {
                    d2 = d0;
                    i = 0;
                }

                if (this.isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
                    d2 = 1.0D - d0;
                    i = 1;
                }

                if (this.isOpenBlockSpace(blockpos.north()) && d1 < d2) {
                    d2 = d1;
                    i = 4;
                }

                if (this.isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
                    d2 = 1.0D - d1;
                    i = 5;
                }

                float f = 0.1F;

                if (i == 0) {
                    this.motionX = (double) (-f);
                }

                if (i == 1) {
                    this.motionX = (double) f;
                }

                if (i == 4) {
                    this.motionZ = (double) (-f);
                }

                if (i == 5) {
                    this.motionZ = (double) f;
                }
            }

            return false;
        }
    }

    private boolean isOpenBlockSpace(BlockPos pos) {
        return !this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube();
    }

    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = sprinting ? 600 : 0;
    }

    public void setXPStats(float currentXP, int maxXP, int level) {
        this.experience = currentXP;
        this.experienceTotal = maxXP;
        this.experienceLevel = level;
    }

    public void addChatMessage(IChatComponent component) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
        return permLevel <= 0;
    }

    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
    }

    public void playSound(String name, float volume, float pitch) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
    }

    public boolean isServerWorld() {
        return true;
    }

    public boolean isRidingHorse() {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse) this.ridingEntity).isHorseSaddled();
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    public void openEditSign(TileEntitySign signTile) {
        this.mc.displayGuiScreen(new GuiEditSign(signTile));
    }

    public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(cmdBlockLogic));
    }

    public void displayGUIBook(ItemStack bookStack) {
        Item item = bookStack.getItem();

        if (item == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
        }
    }

    public void displayGUIChest(IInventory chestInventory) {
        String s = chestInventory instanceof IInteractionObject ? ((IInteractionObject) chestInventory).getGuiID() : "minecraft:container";

        if ("minecraft:chest".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        } else if ("minecraft:hopper".equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
        } else if ("minecraft:furnace".equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
        } else if ("minecraft:brewing_stand".equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
        } else if ("minecraft:beacon".equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
        } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        } else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
        }
    }

    public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, horseInventory, horse));
    }

    public void displayGui(IInteractionObject guiOwner) {
        String s = guiOwner.getGuiID();

        if ("minecraft:crafting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        } else if ("minecraft:enchanting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
        } else if ("minecraft:anvil".equals(s)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }

    public void displayVillagerTradeGui(IMerchant villager) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.worldObj));
    }

    public void onCriticalHit(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }

    public void onEnchantmentCritical(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

    public boolean isSneaking() {
        boolean flag = this.movementInput != null ? this.movementInput.sneak : false;
        return flag && !this.sleeping;
    }

    public void updateEntityActionState() {
        super.updateEntityActionState();

        if (this.isCurrentViewEntity()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float) ((double) this.renderArmPitch + (double) (this.rotationPitch - this.renderArmPitch) * 0.5D);
            this.renderArmYaw = (float) ((double) this.renderArmYaw + (double) (this.rotationYaw - this.renderArmYaw) * 0.5D);
        }
    }

    protected boolean isCurrentViewEntity() {
        return this.mc.getRenderViewEntity() == this;
    }

    public void onLivingUpdate() {
        if (CheatBreaker.getInstance().getModuleManager().toggleSprintMod.isEnabled()) {
            this.toggleSprintVersionOnLivingUpdate();
        } else {
            if (this.sprintingTicksLeft > 0) {
                --this.sprintingTicksLeft;

                if (this.sprintingTicksLeft == 0) {
                    this.setSprinting(false);
                }
            }

            if (this.sprintToggleTimer > 0) {
                --this.sprintToggleTimer;
            }

            this.prevTimeInPortal = this.timeInPortal;

            if (this.inPortal) {
                if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                    this.mc.displayGuiScreen(null);
                }

                if (this.timeInPortal == 0.0F) {
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
                }

                this.timeInPortal += 0.0125F;

                if (this.timeInPortal >= 1.0F) {
                    this.timeInPortal = 1.0F;
                }

                this.inPortal = false;
            } else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
                this.timeInPortal += 0.006666667F;

                if (this.timeInPortal > 1.0F) {
                    this.timeInPortal = 1.0F;
                }
            } else {
                if (this.timeInPortal > 0.0F) {
                    this.timeInPortal -= 0.05F;
                }

                if (this.timeInPortal < 0.0F) {
                    this.timeInPortal = 0.0F;
                }
            }

            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }

            boolean flag = this.movementInput.jump;
            boolean flag1 = this.movementInput.sneak;
            float f = 0.8F;
            boolean flag2 = this.movementInput.moveForward >= f;
            this.movementInput.updatePlayerMoveState();

            if (this.isUsingItem() && !this.isRiding()) {
                this.movementInput.moveStrafe *= 0.2F;
                this.movementInput.moveForward *= 0.2F;
                this.sprintToggleTimer = 0;
            }

            this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double) this.width * 0.35D);
            boolean flag3 = (float) this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;

            if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.setSprinting(true);
                }
            }

            if (!this.isSprinting() && this.movementInput.moveForward >= f && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.setSprinting(true);
            }

            if (this.isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3)) {
                this.setSprinting(false);
            }

            if (this.capabilities.allowFlying) {
                if (this.mc.playerController.isSpectatorMode()) {
                    if (!this.capabilities.isFlying) {
                        this.capabilities.isFlying = true;
                        this.sendPlayerAbilities();
                    }
                } else if (!flag && this.movementInput.jump) {
                    if (this.flyToggleTimer == 0) {
                        this.flyToggleTimer = 7;
                    } else {
                        this.capabilities.isFlying = !this.capabilities.isFlying;
                        this.sendPlayerAbilities();
                        this.flyToggleTimer = 0;
                    }
                }
            }

            if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
                if (this.movementInput.sneak) {
                    this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
                }

                if (this.movementInput.jump) {
                    this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
                }
            }

            if (this.isRidingHorse()) {
                if (this.horseJumpPowerCounter < 0) {
                    ++this.horseJumpPowerCounter;

                    if (this.horseJumpPowerCounter == 0) {
                        this.horseJumpPower = 0.0F;
                    }
                }

                if (flag && !this.movementInput.jump) {
                    this.horseJumpPowerCounter = -10;
                    this.sendHorseJump();
                } else if (!flag && this.movementInput.jump) {
                    this.horseJumpPowerCounter = 0;
                    this.horseJumpPower = 0.0F;
                } else if (flag) {
                    ++this.horseJumpPowerCounter;

                    if (this.horseJumpPowerCounter < 10) {
                        this.horseJumpPower = (float) this.horseJumpPowerCounter * 0.1F;
                    } else {
                        this.horseJumpPower = 0.8F + 2.0F / (float) (this.horseJumpPowerCounter - 9) * 0.1F;
                    }
                }
            } else {
                this.horseJumpPower = 0.0F;
            }

            super.onLivingUpdate();

            if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
                this.capabilities.isFlying = false;
                this.sendPlayerAbilities();
            }
        }
    }

    public void toggleSprintVersionOnLivingUpdate() {
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;

            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }

        this.prevTimeInPortal = this.timeInPortal;

        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                this.mc.displayGuiScreen((GuiScreen) null);
            }

            if (this.timeInPortal == 0.0F) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
            }

            this.timeInPortal += 0.0125F;

            if (this.timeInPortal >= 1.0F) {
                this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
        } else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
            this.timeInPortal += 0.006666667F;

            if (this.timeInPortal > 1.0F) {
                this.timeInPortal = 1.0F;
            }
        } else {
            if (this.timeInPortal > 0.0F) {
                this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F) {
                this.timeInPortal = 0.0F;
            }
        }

        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }

        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.8F;
        boolean flag2 = this.movementInput.moveForward >= f;
//        this.movementInput.updatePlayerMoveState();
        MinecraftMovementInputHelper.lIIIIlIIllIIlIIlIIIlIIllI(this.mc, (MovementInputFromOptions) this.movementInput, this);

        if (this.isUsingItem() && !this.isRiding()) {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
        }

        this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double) this.width * 0.35D);
        this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double) this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double) this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double) this.width * 0.35D);
        boolean flag3 = (float) this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
        boolean bl4 = !CheatBreaker.getInstance().getModuleManager().toggleSprintMod.isEnabled() || !((Boolean) SimpleModuleToggleSprint.toggleSprint.getValue());
        boolean bl5 = (Boolean) SimpleModuleToggleSprint.doubleTap.getValue();
        if (SimpleModuleToggleSprint.buggedSprint) {
            this.setSprinting(false);
            this.minecraftMovementInputHelper.setSprintState(false, false);
            SimpleModuleToggleSprint.buggedSprint = false;
        }

        if (bl4) {
            if ((Boolean) SimpleModuleToggleSprint.doubleTap.getValue() && this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.setSprinting(true);
                    this.minecraftMovementInputHelper.setSprintState(true, false);
                }
                if (!this.isSprinting() && this.movementInput.moveForward >= f && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                    this.setSprinting(true);
                    this.minecraftMovementInputHelper.setSprintState(true, false);
                }
            }
        } else {
            boolean bl6 = MinecraftMovementInputHelper.isSprinting;
            if (!(!flag3 || this.isUsingItem() || this.isPotionActive(Potion.blindness) || MinecraftMovementInputHelper.superSusBoolean || bl5 && this.isSprinting())) {
                this.setSprinting(bl6);
            }
            if (bl5 && !bl6 && this.onGround && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                if (this.sprintToggleTimer == 0) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.setSprinting(true);
                    this.minecraftMovementInputHelper.setSprintState(true, true);
                    this.sprintToggleTimer = 0;
                }
            }
        }


        if (this.isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3)) {
            this.setSprinting(false);
            if (MinecraftMovementInputHelper.superSusBoolean || bl4 || MinecraftMovementInputHelper.aSusBoolean || this.isRiding()) {
                this.minecraftMovementInputHelper.setSprintState(false, false);

            }
        }

        //CB
        if ((Boolean) SimpleModuleToggleSprint.flyBoost.getValue() && this.capabilities.isFlying && this.mc.gameSettings.keyBindSprint.isKeyDown() && this.capabilities.isCreativeMode) {
            this.capabilities.setFlySpeed(0.027272727f * 1.8333334f * (Float) SimpleModuleToggleSprint.flyBoostAmount.getValue());
            if (this.movementInput.sneak) {
                this.motionY -= 0.6526315808296204 * 0.2298387090145425 * (double) (Float) SimpleModuleToggleSprint.flyBoostAmount.getValue();
            }
            if (this.movementInput.jump) {
                this.motionY += 0.01084337374315776 * 13.833333015441895 * (double) (Float) SimpleModuleToggleSprint.flyBoostAmount.getValue();
            }
        } else if (this.capabilities.getFlySpeed() != 0.0129629625f * 3.857143f) {
            this.capabilities.setFlySpeed(4.714286f * 0.010606061f);
        }

        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.motionY -= (double) (this.capabilities.getFlySpeed() * 3.0F);
            }

            if (this.movementInput.jump) {
                this.motionY += (double) (this.capabilities.getFlySpeed() * 3.0F);
            }
        }

        if (this.isRidingHorse()) {
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            } else if (flag) {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter < 10) {
                    this.horseJumpPower = (float) this.horseJumpPowerCounter * 0.1F;
                } else {
                    this.horseJumpPower = 0.8F + 2.0F / (float) (this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        } else {
            this.horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();

        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
}
