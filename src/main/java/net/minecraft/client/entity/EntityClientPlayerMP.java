package net.minecraft.client.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleBunnyhop;
import com.cheatbreaker.client.ui.overlay.CBAlert;
import com.cheatbreaker.client.util.chat.ChatHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.network.play.client.*;
import net.minecraft.src.Config;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityClientPlayerMP extends EntityPlayerSP {
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter field_146108_bO;
    private double oldPosX;

    /**
     * Old Minimum Y of the bounding box
     */
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;

    /**
     * Check if was on ground last update
     */
    private boolean wasOnGround;

    /**
     * should the player stop sneaking?
     */
    private boolean shouldStopSneaking;
    private boolean wasSneaking;

    /**
     * Counter used to ensure that the server sends a move packet (Packet11, 12 or 13) to the com.cheatbreaker.client at least once a
     * second.
     */
    private int ticksSinceMovePacket;

    /**
     * has the com.cheatbreaker.client player's health been set?
     */
    private boolean hasSetHealth;
    private String field_142022_ce;

    List IlllIIIlIlllIllIlIIlllIlI = new ArrayList();
    private boolean lIllIlllIIllIllllIllIIlll;


    public EntityClientPlayerMP(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient p_i45064_4_, StatFileWriter p_i45064_5_) {
        super(p_i45064_1_, p_i45064_2_, p_i45064_3_, 0);
        this.sendQueue = p_i45064_4_;
        this.field_146108_bO = p_i45064_5_;
    }

    @Override
    public void moveEntityWithHeading(float f, float f2) {
        if (StaffModuleBunnyhop.canUse()) {
            double d = this.posX;
            double d2 = this.posY;
            double d3 = this.posZ;
            if (this.capabilities.isFlying && this.ridingEntity == null) {
                super.moveEntityWithHeading(f, f2);
            } else {
                this.IIIIllIlIIIllIlllIlllllIl(f, f2);
            }
            this.addMovementStat(this.posX - d, this.posY - d2, this.posZ - d3);
        } else {
            super.moveEntityWithHeading(f, f2);
        }
    }

    public void lIIIIllIIlIlIllIIIlIllIlI() {
        this.isJumping = false;
        if (!this.IlllIIIlIlllIllIlIIlllIlI.isEmpty()) {
            this.IlllIIIlIlllIllIlIIlllIlI.clear();
        }
    }

    @Override
    public void onLivingUpdate() {
        if (StaffModuleBunnyhop.canUse()) {
            this.lIIIIllIIlIlIllIIIlIllIlI();
        }
        super.onLivingUpdate();
    }

    @Override
    public void moveFlying(float f, float f2, float f3) {
        if (StaffModuleBunnyhop.canUse()) {
            if (this.capabilities.isFlying && this.ridingEntity == null || this.isInWater()) {
                super.moveFlying(f, f2, f3);
                return;
            }
            float[] arrf = this.a_(f, f2);
            float[] arrf2 = new float[]{arrf[0] * (f3 *= 1.5438596f * 1.3926138f), arrf[1] * f3};
            this.IlllIIIlIlllIllIlIIlllIlI.add(arrf2);
        } else {
            super.moveFlying(f, f2, f3);
        }
    }

    @Override
    public void jump() {
        if (StaffModuleBunnyhop.canUse()) {
            if (this.isSprinting()) {
                float f = this.rotationYaw * (0.016018774f * 1.0895523f);
                this.motionX += (double) (MathHelper.sin(f) * (0.12380953f * 1.6153846f));
                this.motionZ -= (double) (MathHelper.cos(f) * (0.23611112f * 0.84705883f));
            }
            this.lIllIlIlIIlIllIllllIllIIl();
            this.lIllIlllIIllIllllIllIIlll = true;
        }
        super.jump();
    }

    public double n_() {
        return MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
    }

    public float o_() {
        float f = 1.0f;
        if (this.onGround) {
            Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            f = 1.0f - block.slipperiness;
        }
        return f;
    }

    public float p_() {
        float f = 0.79505265f * 1.1445783f;
        if (this.onGround) {
            f = 19.4f * 0.028144334f;
            Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (block != null) {
                f = block.slipperiness * (2.142857f * 0.4246667f);
            }
        }
        return f;
    }

    public float llIlIIIlIIIIlIlllIlIIIIll() {
        float f = this.p_();
        float f2 = 0.5409836f * 0.30088037f / (f * f * f);
        return this.getAIMoveSpeed() * f2;
    }

    public float[] a_(float f, float f2) {
        float f3 = f * f + f2 * f2;
        float[] arrf = new float[]{0.0f, 0.0f};
        if (f3 >= 1.1111112f * 8.9999994E-5f) {
            if ((f3 = MathHelper.sqrt_float(f3)) < 1.0f) {
                f3 = 1.0f;
            }
            f3 = 1.0f / f3;
            float f4 = MathHelper.sin(this.rotationYaw * (2.1470587f * 1.4632076f) / (float) 180);
            float f5 = MathHelper.cos(this.rotationYaw * (9.74977f * 0.32222223f) / (float) 180);
            arrf[0] = (f *= f3) * f5 - (f2 *= f3) * f4;
            arrf[1] = f2 * f5 + f * f4;
        }
        return arrf;
    }

    public float IIIlIIllllIIllllllIlIIIll() {
        float f = this.getAIMoveSpeed();
        return !this.isSneaking() ? f * 2.15f : f * (2.1f * 0.5285715f);
    }

    public float lllIIIIIlIllIlIIIllllllII() {
        float f = this.getAIMoveSpeed();
        return f * (13.333333f * 0.16125001f);
    }

    private void lIIlIIllIIIIIlIllIIIIllII(int n) {
        int n2;
        int n3;
        int n4 = MathHelper.floor_double(this.posX);
        Block block = this.worldObj.getBlock(n4, n3 = MathHelper.floor_double(this.posY - 3.133333387805356 * 0.06382978707551956 - (double) this.yOffset), n2 = MathHelper.floor_double(this.posZ));
        if (block != null && block.getMaterial() != Material.air) {
            for (int i = 0; i < n; ++i) {
                this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + this.worldObj.getBlockMetadata(n4, n3, n2), this.posX + ((double) new Random().nextFloat() - 0.41011236009987867 * 1.2191780805587769) * (double) this.width, this.boundingBox.minY + (double) 0.52f * 0.1923076993614967, this.posZ + ((double) new Random().nextFloat() - 0.7090908885002136 * 0.7051282256038316) * (double) this.width, -this.motionX * (double) 4, 1.4220778824487261 * 1.0547945499420166, -this.motionZ * (double) 4);
            }
        }
    }

    private void lIlIlIIllIlIIIIIlllIllIII() {
        this.motionY = !(!this.worldObj.isClient || this.worldObj.blockExists((int) this.posX, 0, (int) this.posZ) && this.worldObj.getChunkFromBlockCoords((int) ((int) this.posX), (int) ((int) this.posZ)).isChunkLoaded) ? (this.posY > 0.0 ? -0.17307691553817026 * 0.5777778029441833 : 0.0) : (this.motionY -= 0.12285714050336764 * 0.6511628031730652);
        this.motionY *= 0.367499996200204 * 2.6666667461395264;
    }

    private void IllIIIIIIIlIlIllllIIllIII(float f) {
        this.motionX *= (double) f;
        this.motionZ *= (double) f;
    }

    private void lllIIlIlIllIIlIllIIIIIlII() {
        if (this.isOnLadder()) {
            boolean bl;
            float f = 1.0819672f * 0.13863637f;
            if (this.motionX < (double) (-f)) {
                this.motionX = -f;
            }
            if (this.motionX > (double) f) {
                this.motionX = f;
            }
            if (this.motionZ < (double) (-f)) {
                this.motionZ = -f;
            }
            if (this.motionZ > (double) f) {
                this.motionZ = f;
            }
            this.fallDistance = 0.0f;
            if (this.motionY < -0.15937499940628186 * 0.9411764740943909) {
                this.motionY = -0.024999999999999998 * 6.0;
            }
            if ((bl = this.isSneaking()) && this.motionY < 0.0) {
                this.motionY = 0.0;
            }
        }
    }

    private void IlIIIlIIllIIIIllllIlIlIlI() {
        if (this.isCollidedHorizontally && this.isOnLadder()) {
            this.motionY = 0.07111111111111111 * 2.8125;
        }
    }

    private void lIIIIlllIlIlllIIIlllllIlI() {
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d = this.posX - this.prevPosX;
        double d2 = this.posZ - this.prevPosZ;
        float f = MathHelper.sqrt_double(d * d + d2 * d2) * (float) 4;
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.limbSwingAmount += (f - this.limbSwingAmount) * (0.45714286f * 0.875f);
        this.limbSwing += this.limbSwingAmount;
    }

    private void IllIIIIIIIlIlIllllIIllIII(float f, float f2) {
        double d = this.posY;
        this.moveFlying(f, f2, 0.040727273f * 0.98214287f);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 2.9090908893868948 * (double) 0.275f;
        this.motionY *= 1.7407407760620117 * 0.4595744656081004;
        this.motionZ *= 0.5797101259231567 * 1.38000006580353;
        this.motionY -= 7.833333492279053 * 0.0025531914375550406;
        if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 3.21052622795105 * 0.18688525844088072 - this.posY + d, this.motionZ)) {
            this.motionY = 1.7297297716140747 * 0.17343750269210426;
        }
    }

    public void IlllIIIlIlllIllIlIIlllIlI(float f, float f2) {
        if (this.isInWater() && !this.capabilities.isFlying || this.handleLavaMovement() && !this.capabilities.isFlying) {
            super.moveEntityWithHeading(f, f2);
        } else {
            float f3 = this.p_();
            this.moveFlying(f, f2, this.llIlIIIlIIIIlIlllIlIIIIll());
            this.lllIIlIlIllIIlIllIIIIIlII();
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.IlIIIlIIllIIIIllllIlIlIlI();
            this.lIlIlIIllIlIIIIIlllIllIII();
            this.IllIIIIIIIlIlIllllIIllIII(f3);
            this.lIIIIlllIlIlllIIIlllllIlI();
        }
    }

    /*
     * Unable to fully structure code
     */
    public void IIIIllIlIIIllIlllIlllllIl(float var1, float var2) {
        if (this.handleLavaMovement() && !this.capabilities.isFlying) {
            super.moveEntityWithHeading(var1, var2);
        } else {
            if (this.isInWater() && !this.capabilities.isFlying) {
                if (!(Boolean) StaffModuleBunnyhop.sharking.getValue()) {
                    super.moveEntityWithHeading(var1, var2);
                    return;
                }

                this.lIIIIllIIlIlIllIIIlIllIlI(var1, var2);
            } else {
                float var3 = var1 == 0.0F && var2 == 0.0F ? 0.0F : this.IIIlIIllllIIllllllIlIIIll();
                float[] var4 = this.a_(var1, var2);
                boolean var5 = this.onGround && !this.isJumping;
                float var6 = this.p_();
                double var7;
                if (var5) {
                    this.IllIIIIIIIlIlIllllIIllIII(var6);
                    var7 = (Double) StaffModuleBunnyhop.accelerate.getValue();
                    if (var3 != 0.0F) {
                        var7 *= (double) (this.llIlIIIlIIIIlIlllIlIIIIll() * 1.2989583F * 1.6551725F / var3);
                        this.lIIIIlIIllIIlIIlIIIlIIllI(var3, (double) var4[0], (double) var4[1], var7);
                    }

                    if (!this.IlllIIIlIlllIllIlIIlllIlI.isEmpty()) {
                        float var12 = var3 / this.lllIIIIIlIllIlIIIllllllII();

                        float[] var11;
                        for (Iterator var13 = this.IlllIIIlIlllIllIlIIlllIlI.iterator(); var13.hasNext();
                             this.motionX += (double) (var11[1] * var12)) {
                            var11 = (float[]) var13.next();
                            this.motionY += (double) (var11[0] * var12);
                        }
                    }
                } else {
                    var7 = (Double) StaffModuleBunnyhop.airAccelerate.getValue();
                    this.lIIIIIIIIIlIllIIllIlIIlIl(var3, (double) var4[0], (double) var4[1], var7);
                    if ((Boolean) StaffModuleBunnyhop.sharking.getValue() && (Double) StaffModuleBunnyhop.sharkingSurfaceTension.getValue() > 0.0 && this.isJumping && this.motionY < 0.0) {
                        AxisAlignedBB var9 = this.boundingBox.getOffsetBoundingBox(this.motionX, this.motionY, this.motionZ);
                        boolean var10 = this.worldObj.isAnyLiquid(var9);
                        if (var10) {
                            this.motionY *= (Double) StaffModuleBunnyhop.sharkingSurfaceTension.getValue();
                        }
                    }
                }

                this.lllIIlIlIllIIlIllIIIIIlII();
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.IlIIIlIIllIIIIllllIlIlIlI();
                this.lIlIlIIllIlIIIIIlllIllIII();
            }

            this.lIIIIlllIlIlllIIIlllllIlI();
        }
    }

    private void lIllIlIlIIlIllIllllIllIIl() {
        this.IIIlIIllllIIllllllIlIIIll(this.lllIIIIIlIllIlIIIllllllII());
        boolean bl = this.IIIlllIllIIllIllIlIIIllII();
        if (!bl) {
            this.lllIIIIIlIllIlIIIllllllII(this.lllIIIIIlIllIlIIIllllllII());
        }
    }

    private boolean IIIlllIllIIllIllIlIIIllII() {
        float f;
        double d;
        if ((Boolean) StaffModuleBunnyhop.trimp.getValue() && this.isSneaking() && (d = this.n_()) > (double) (f = this.lllIIIIIlIllIlIIIllllllII())) {
            double d2 = d / (double) f * (0.4166666567325592 * 1.2000000286102301);
            if (d2 > 1.0) {
                d2 = 1.0;
            }
            this.motionY += d2 * d * (double) (Float) StaffModuleBunnyhop.trimpMultiplier.getValue();
            if ((Float) StaffModuleBunnyhop.trimpMultiplier.getValue() > 0.0f) {
                float f2 = 1.0f / (Float) StaffModuleBunnyhop.trimpMultiplier.getValue();
                this.motionX *= (double) f2;
                this.motionZ *= (double) f2;
            }
            this.lIIlIIllIIIIIlIllIIIIllII(30);
            return true;
        }
        return false;
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(double d) {
        this.motionX *= d;
        this.motionY *= d;
        this.motionZ *= d;
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(float f, float f2, double d, double d2, double d3) {
        float f3 = f - f2;
        if (f3 > 0.0f) {
            float f4 = (float) (d3 * (double) f * ((double) 6.071429f * 0.008235293963169974));
            if (f4 > f3) {
                f4 = f3;
            }
            this.motionX += (double) f4 * d;
            this.motionZ += (double) f4 * d2;
        }
    }

    private void lIIIIllIIlIlIllIIIlIllIlI(float f, float f2) {
        double d = this.posY;
        float f3 = f != 0.0f || f2 != 0.0f ? this.lllIIIIIlIllIlIIIllllllII() : 0.0f;
        float[] arrf = this.a_(f, f2);
        boolean bl = this.isJumping && this.isOffsetPositionInLiquid(0.0, 1.0, 0.0);
        double d2 = this.n_();
        if (!bl || d2 < 0.0566526315393012 * 1.3768116235733032) {
            this.IllIIIIIIIlIlIllllIIllIII(f, f2);
        } else {
            if (d2 > 0.03947368264198303 * 2.2800000905990636) {
                this.lIIIIIIIIIlIllIIllIlIIlIl((double) StaffModuleBunnyhop.sharkingWaterFriction.getValue());
            }
            if (d2 > (double) 0.1724138f * 0.5683999898362162) {
                this.lIIIIIIIIIlIllIIllIlIIlIl(f3, arrf[0], arrf[1], (double) StaffModuleBunnyhop.accelerate.getValue());
            } else {
                this.lIIIIlIIllIIlIIlIIIlIIllI(12.2f * 0.008032787f, (double) arrf[0], (double) arrf[1], (double) StaffModuleBunnyhop.accelerate.getValue());
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionY = 0.0;
        }
        if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 2.188235327364251 * 0.27419355511665344 - this.posY + d, this.motionZ)) {
            this.motionY = (double) 1.6f * 0.18750000465661282;
        }
        if (!this.IlllIIIlIlllIllIlIIlllIlI.isEmpty()) {
            float f4 = f3 / this.lllIIIIIlIllIlIIIllllllII();
            for (Object arrf2 : this.IlllIIIlIlllIllIlIIlllIlI) {
                this.motionX += (double) ((Float[]) arrf2)[0] * f4;
                this.motionZ += (double) ((Float[]) arrf2)[1] * f4;
            }
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(float f, double d, double d2, double d3) {
        double d4 = this.motionX * d + this.motionZ * d2;
        double d5 = (double) f - d4;
        if (d5 <= 0.0) {
            return;
        }
        double d6 = d3 * (double) f / (double) this.p_() * (1.121212124824524 * 0.04459459511542773);
        if (d6 > d5) {
            d6 = d5;
        }
        this.motionX += d6 * d;
        this.motionZ += d6 * d2;
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl(float f, double d, double d2, double d3) {
        double d4;
        double d5;
        float f2 = f;
        float f3 = (float) ((double) StaffModuleBunnyhop.maxAirAccelPerTick.getValue());
        if (f2 > f3) {
            f2 = f3;
        }
        if ((d5 = (double) f2 - (d4 = this.motionX * d + this.motionZ * d2)) <= 0.0) {
            return;
        }
        double d6 = d3 * (double) f * (0.4054054021835327 * 0.12333333615130851);
        if (d6 > d5) {
            d6 = d5;
        }
        this.motionX += d6 * d;
        this.motionZ += d6 * d2;
    }

    private void lllIlIIIllIIlIIlIlIllIIlI() {
        float f;
        float f2;
        float f3;
        double d = this.n_();
        if (d <= 0.0) {
            return;
        }
        float f4 = 0.0f;
        float f5 = 1.7560976f * 0.0028472221f;
        double d2 = Math.max(d, (double) f5);
        double d3 = d - (double) (f4 = (float) ((double) f4 + d2 * (double) (f3 = (f2 = 1.0f) * (f = this.o_())) * (0.0026666667064030964 * 18.75)));
        if (d3 < 0.0) {
            d3 = 0.0;
        }
        if (d3 != d) {
            this.motionX *= (d3 /= d);
            this.motionZ *= d3;
        }
    }

    private void IIIlIIllllIIllllllIlIIIll(float f) {
        float f2;
        float f3;
        float f4 = (Float) StaffModuleBunnyhop.softCap.getValue();
        float f5 = (Float) StaffModuleBunnyhop.softCapDegen.getValue();
        if ((Boolean) StaffModuleBunnyhop.uncappedBHop.getValue()) {
            f4 = 1.0f;
            f5 = 1.0f;
        }
        if ((f3 = (float) this.n_()) > (f2 = f * f4)) {
            if (f5 != 1.0f) {
                float f6 = (f3 - f2) * f5 + f2;
                float f7 = f6 / f3;
                this.motionX *= (double) f7;
                this.motionZ *= (double) f7;
            }
            this.lIIlIIllIIIIIlIllIIIIllII(10);
        }
    }

    private void lllIIIIIlIllIlIIIllllllII(float f) {
        float f2;
        if ((Boolean) StaffModuleBunnyhop.uncappedBHop.getValue()) {
            return;
        }
        float f3 = (Float) StaffModuleBunnyhop.hardCap.getValue();
        float f4 = (float) this.n_();
        if (f4 > (f2 = f * f3) && f2 != 0.0f) {
            float f5 = f2 / f4;
            this.motionX *= (double) f5;
            this.motionZ *= (double) f5;
            this.lIIlIIllIIIIIlIllIIIIllII(30);
        }
    }

    private void IIlIIlIlIlIlllIIlIIlIIlII() {
        this.lIllIlllIIllIllllIllIIlll = false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(float p_70691_1_) {
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity p_70078_1_) {
        super.mountEntity(p_70078_1_);

        if (p_70078_1_ instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart) p_70078_1_));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
            super.onUpdate();

            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            } else {
                this.sendMotionUpdates();
            }
        }
    }

    /**
     * Send updated motion and position information to the server
     */
    public void sendMotionUpdates() {
        boolean var1 = this.isSprinting();

        if (var1 != this.wasSneaking) {
            if (var1) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 4));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 5));
            }

            this.wasSneaking = var1;
        }

        boolean var2 = this.isSneaking();

        if (var2 != this.shouldStopSneaking) {
            if (var2) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 1));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 2));
            }

            this.shouldStopSneaking = var2;
        }

        double var3 = this.posX - this.oldPosX;
        double var5 = this.boundingBox.minY - this.oldMinY;
        double var7 = this.posZ - this.oldPosZ;
        double var9 = this.rotationYaw - this.oldRotationYaw;
        double var11 = this.rotationPitch - this.oldRotationPitch;
        boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.ticksSinceMovePacket >= 20;
        boolean var14 = var9 != 0.0D || var11 != 0.0D;

        if (this.ridingEntity != null) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
            var13 = false;
        } else if (var13 && var14) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
        } else if (var13) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
        } else if (var14) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
        } else {
            this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
        }

        ++this.ticksSinceMovePacket;
        this.wasOnGround = this.onGround;

        if (var13) {
            this.oldPosX = this.posX;
            this.oldMinY = this.boundingBox.minY;
            this.oldPosY = this.posY;
            this.oldPosZ = this.posZ;
            this.ticksSinceMovePacket = 0;
        }

        if (var14) {
            this.oldRotationYaw = this.rotationYaw;
            this.oldRotationPitch = this.rotationPitch;
        }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean p_71040_1_) {
        int var2 = p_71040_1_ ? 3 : 4;
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, 0, 0, 0, 0));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem p_71012_1_) {
    }

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String p_71165_1_) {
        /*if (p_71165_1_.equals("/cb changes")) {
            for (AbstractModule mod : CheatBreaker.getInstance().getModuleManager().playerMods) {
                System.out.println("Mod: " + mod.getName());
                System.out.println(" - Options:");
                for (int i = 0; i < mod.getSettingsList().size(); i++) {
                    System.out.println("   - " + mod.getSettingsList().get(i).getSettingName());
                }
            }
        }*/

        /*if (p_71165_1_.equals("/cb changes settings")) {
            for (Setting setting : CheatBreaker.getInstance().getGlobalSettings().settingsList) {
                boolean hasNoDescription = setting.getSettingDescription().length() == 0;
                if (!setting.getSettingName().equals("label")) {
                    System.out.println("- " + setting.getSettingName() + (hasNoDescription ? "" : " (" + EnumChatFormatting.getTextWithoutFormattingCodes(setting.getSettingDescription().replaceAll("\n", ", ")) + ")"));
                }
            }
        }*/

        if (p_71165_1_.equals("/cb chat")) {
            ChatHandler.sendBrandedChatMessage("Nox has been CheatBreaker Bunned!");
            return;
        }

        if (p_71165_1_.equals("/cb debug")) {
            CheatBreaker.getInstance().getGlobalSettings().isDebug = !CheatBreaker.getInstance().getGlobalSettings().isDebug;
            if (CheatBreaker.getInstance().getGlobalSettings().isDebug) {
                Display.setTitle("Minecraft " + Config.MC_VERSION + " - " +
                        CheatBreaker.getInstance().getGitBuildVersion() +
                        " Build (" + CheatBreaker.getInstance().getGitCommitIdAbbrev() +
                        "/" + CheatBreaker.getInstance().getGitBranch() + ")");
            } else {
                Display.setTitle("Minecraft " + Config.MC_VERSION);
            }

            if (Minecraft.getMinecraft().getSession().getUsername().equalsIgnoreCase("Noxiuam") && !CheatBreaker.getInstance().getGlobalSettings().isDebug) {
                CheatBreaker.getInstance().getCosmeticManager().getWings().add(new Cosmetic(Minecraft.getMinecraft().getSession().getPlayerID(), "Black_Wings", Cosmetic.CosmeticType.WINGS, 0.13F, true, "client/wings/black.png"));
                CheatBreaker.getInstance().getCosmeticManager().getCapes().add(new Cosmetic(Minecraft.getMinecraft().getSession().getPlayerID(), "CheatBreaker", Cosmetic.CosmeticType.CAPE, 0.16F, true, "client/capes/cb2.png"));
            }

            ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
            ChatComponentText status = new ChatComponentText(EnumChatFormatting.GRAY + "Debug: " + CheatBreaker.getInstance().getGlobalSettings().isDebug);
            prefix.appendSibling(status);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(prefix);
        }/* else if(p_71165_1_.equals("/cb credits")) {
            if (!ClientCredits.isCreditedUser(this.mc.getSession().getUniqueID())) ClientCredits.sendCredits();
        } else if(p_71165_1_.startsWith("/cb emote")) {
            String[] args = p_71165_1_.split(" ");
            if(args.length != 3) {
                ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " +EnumChatFormatting.RESET);
                ChatComponentText arguments = new ChatComponentText(EnumChatFormatting.RED + "Usage: /cb emote <id>");
                prefix.appendSibling(arguments);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(prefix);
                return;
            }
            if(args[2].equals("list")) {
                ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " +EnumChatFormatting.RESET);
                StringBuilder emotes = new StringBuilder();
                for (int id : CheatBreaker.getInstance().getEmoteManager().getEmotes()) {
                    Emote emote = CheatBreaker.getInstance().getEmoteManager().getEmote(id);
                    if (!emotes.toString().equals("")) {
                        emotes.append(", ");
                    }
                    emotes.append(emote.getName()).append(" (ID: ").append(id).append(")");
                }
                ChatComponentText chatComponentText2 = new ChatComponentText(EnumChatFormatting.GRAY + "Emotes: " + emotes);

                prefix.appendSibling(chatComponentText2);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(prefix);
                return;
            }
            int id = Integer.parseInt(args[2]);
            Emote emote =CheatBreaker.getInstance().getEmoteManager().getEmote(id);
            ChatComponentText chatComponentText = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " +EnumChatFormatting.RESET);
            ChatComponentText chatComponentText2 = new ChatComponentText(EnumChatFormatting.GRAY + "Doing emote: " + emote.getName());
            chatComponentText.appendSibling(chatComponentText2);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(chatComponentText);
            CheatBreaker.getInstance().getEmoteManager().playEmote(Minecraft.getMinecraft().thePlayer, emote);
        } else if(p_71165_1_.equals("/cb everything chroma")) {
            ChatComponentText chatComponentText = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " +EnumChatFormatting.RESET);
            ChatComponentText chatComponentText2 = new ChatComponentText(EnumChatFormatting.GRAY + ":)");
            chatComponentText.appendSibling(chatComponentText2);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(chatComponentText);
            for(AbstractModule module : CheatBreaker.getInstance().getModuleManager().staffMods) {
                module.getSettingsList().forEach(setting -> {
                    setting.rainbow = true;
                    setting.speed = true;
                });
            }
            for(AbstractModule module : CheatBreaker.getInstance().getModuleManager().playerMods) {
                module.getSettingsList().forEach(setting -> {
                    setting.rainbow = true;
                    setting.speed = true;
                });
            }
        } */ else {
            this.sendQueue.addToSendQueue(new C01PacketChatMessage(p_71165_1_));
            CheatBreaker.getInstance().getModuleManager().chatMod.dingCooldown.add(System.currentTimeMillis());
        }
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation(this, 1));
    }

    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.isEntityInvulnerable()) {
            this.setHealth(this.getHealth() - p_70665_2_);
        }
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenNoPacket();
    }

    /**
     * Closes the GUI screen without sending a packet to the server
     */
    public void closeScreenNoPacket() {
        this.inventory.setItemStack(null);
        super.closeScreen();
    }

    /**
     * Updates health locally.
     */
    public void setPlayerSPHealth(float p_71150_1_) {
        if (this.hasSetHealth) {
            super.setPlayerSPHealth(p_71150_1_);
        } else {
            this.setHealth(p_71150_1_);
            this.hasSetHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase p_71064_1_, int p_71064_2_) {
        if (p_71064_1_ != null) {
            if (p_71064_1_.isIndependent) {
                super.addStat(p_71064_1_, p_71064_2_);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }

    protected void func_110318_g() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 6, (int) (this.getHorseJumpPower() * 100.0F)));
    }

    public void func_110322_i() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 7));
    }

    public void func_142020_c(String p_142020_1_) {
        this.field_142022_ce = p_142020_1_;
    }

    public String func_142021_k() {
        return this.field_142022_ce;
    }

    public StatFileWriter func_146107_m() {
        return this.field_146108_bO;
    }
}
