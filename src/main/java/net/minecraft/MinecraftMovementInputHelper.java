package net.minecraft;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.hud.simple.module.SimpleModuleToggleSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

import java.text.DecimalFormat;

public class MinecraftMovementInputHelper extends MovementInputFromOptions {
    public static boolean isToggleSprintAllowed;
    public static boolean lIIIIIIIIIlIllIIllIlIIlIl;
    public static boolean isSprinting = true;
    public static boolean superSusBoolean = false;
    public static boolean aSusBoolean = false;
    private static long someTiming;
    private static long llIIlllIIIIlllIllIlIlllIl;
    private static boolean someToggleSneakBoolean;
    private static boolean IIIlllIIIllIllIlIIIIIIlII;
    private static boolean isPlayerRiding;
    private static boolean IIIlIIllllIIllllllIlIIIll;
    private static boolean shouldBeSprinting;
    public static String toggleSprintString = "";

    public MinecraftMovementInputHelper(GameSettings gameSettings) {
        super(gameSettings);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Minecraft minecraft, MovementInputFromOptions movementInputFromOptions, EntityPlayerSP entityPlayerSP) {
        movementInputFromOptions.moveStrafe = 0.0f;
        movementInputFromOptions.moveForward = 0.0f;
        GameSettings gameSettings = minecraft.gameSettings;
        if (gameSettings.keyBindForward.isKeyDown()) {
            movementInputFromOptions.moveForward += 1.0f;
        }
        if (gameSettings.keyBindBack.isKeyDown()) {
            movementInputFromOptions.moveForward -= 1.0f;
        }
        if (gameSettings.keyBindLeft.isKeyDown()) {
            movementInputFromOptions.moveStrafe += 1.0f;
        }
        if (gameSettings.keyBindRight.isKeyDown()) {
            movementInputFromOptions.moveStrafe -= 1.0f;
        }
        if (entityPlayerSP.isRiding() && !IIIlIIllllIIllllllIlIIIll) {
            IIIlIIllllIIllllllIlIIIll = true;
            shouldBeSprinting = isSprinting;
        } else if (IIIlIIllllIIllllllIlIIIll && !entityPlayerSP.isRiding()) {
            IIIlIIllllIIllllllIlIIIll = false;
            if (shouldBeSprinting && !isSprinting) {
                isSprinting = true;
                llIIlllIIIIlllIllIlIlllIl = System.currentTimeMillis();
                IIIlllIIIllIllIlIIIIIIlII = true;
                superSusBoolean = false;
            }
        }
        movementInputFromOptions.jump = gameSettings.keyBindJump.isKeyDown();
        if ((Boolean) SimpleModuleToggleSprint.toggleSneak.getValue() && CheatBreaker.getInstance().getModuleManager().toggleSprintMod.isEnabled()) {
            if (gameSettings.keyBindSneak.isKeyDown() && !someToggleSneakBoolean) {
                if (entityPlayerSP.isRiding() || entityPlayerSP.capabilities.isFlying) {
                    movementInputFromOptions.sneak = true;
                    isPlayerRiding = entityPlayerSP.isRiding();
                } else {
                    movementInputFromOptions.sneak = !movementInputFromOptions.sneak;
                }
                someTiming = System.currentTimeMillis();
                someToggleSneakBoolean = true;
            }
            if (!gameSettings.keyBindSneak.isKeyDown() && someToggleSneakBoolean) {
                if (entityPlayerSP.capabilities.isFlying || isPlayerRiding) {
                    movementInputFromOptions.sneak = false;
                } else if (System.currentTimeMillis() - someTiming > 300L) {
                    movementInputFromOptions.sneak = false;
                }
                someToggleSneakBoolean = false;
            }
        } else {
            movementInputFromOptions.sneak = gameSettings.keyBindSneak.isKeyDown();
        }
        if (movementInputFromOptions.sneak) {
            movementInputFromOptions.moveStrafe = (float)((double)movementInputFromOptions.moveStrafe * ((double)1.7f * 0.17647058328542756));
            movementInputFromOptions.moveForward = (float)((double)movementInputFromOptions.moveForward * (0.19999999999999998 * 1.5));
        }
        boolean bl = (float)entityPlayerSP.getFoodStats().getFoodLevel() > (float)6 || entityPlayerSP.capabilities.isFlying;
        boolean bl2 = !movementInputFromOptions.sneak && !entityPlayerSP.capabilities.isFlying && bl;
        isToggleSprintAllowed = !((Boolean) SimpleModuleToggleSprint.toggleSprint.getValue());
        lIIIIIIIIIlIllIIllIlIIlIl = (Boolean) SimpleModuleToggleSprint.doubleTap.getValue();
        if ((bl2 || isToggleSprintAllowed) && gameSettings.keyBindSprint.isKeyDown() && !IIIlllIIIllIllIlIIIIIIlII && !entityPlayerSP.capabilities.isFlying && !isToggleSprintAllowed) {
            isSprinting = !isSprinting;
            llIIlllIIIIlllIllIlIlllIl = System.currentTimeMillis();
            IIIlllIIIllIllIlIIIIIIlII = true;
            superSusBoolean = false;
        }
        if ((bl2 || isToggleSprintAllowed) && !gameSettings.keyBindSprint.isKeyDown() && IIIlllIIIllIllIlIIIIIIlII) {
            if (System.currentTimeMillis() - llIIlllIIIIlllIllIlIlllIl > 300L) {
                superSusBoolean = true;
            }
            IIIlllIIIllIllIlIIIIIIlII = false;
        }
        MinecraftMovementInputHelper.lIIIIlIIllIIlIIlIIIlIIllI(movementInputFromOptions, entityPlayerSP, gameSettings);
    }

    public void setSprintState(boolean bl, boolean bl2) {
        isSprinting = bl;
        aSusBoolean = bl2;
    }

    private static void lIIIIlIIllIIlIIlIIIlIIllI(MovementInputFromOptions movementInputFromOptions, EntityPlayerSP entityPlayerSP, GameSettings gameSettings) {
//        toggleSprintString = "";
        String string = "";
        boolean flying = entityPlayerSP.capabilities.isFlying;
        boolean riding = entityPlayerSP.isRiding();
        boolean sneakHeld = gameSettings.keyBindSneak.isKeyDown();
        boolean sprintHeld = gameSettings.keyBindSprint.isKeyDown();
        if (flying) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            string = (Boolean) SimpleModuleToggleSprint.flyBoost.getValue() && sprintHeld && entityPlayerSP.capabilities.isCreativeMode ? string + ((String)CheatBreaker.getInstance().getModuleManager().toggleSprintMod.flyBoostString.getValue()).replaceAll("%BOOST%", decimalFormat.format(SimpleModuleToggleSprint.flyBoostAmount.getValue())) : string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.flyString.getValue();
        }
        if (riding) {
            string = string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.ridingString.getValue();
        }
        if (movementInputFromOptions.sneak) {
            string = flying ? CheatBreaker.getInstance().getModuleManager().toggleSprintMod.decendString.getValue().toString() :
                    (riding ? CheatBreaker.getInstance().getModuleManager().toggleSprintMod.dismountString.getValue().toString() :
                            (sneakHeld ? string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.sneakHeldString.getValue() :
                                    string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.sneakToggledString.getValue()));
        } else if (isSprinting && !flying && !riding) {
            boolean bl5 = superSusBoolean || isToggleSprintAllowed || aSusBoolean;
            string = sprintHeld ? string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.sprintHeldString.getValue() : (bl5 ? string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.sprintVanillaString.getValue() : string + CheatBreaker.getInstance().getModuleManager().toggleSprintMod.sprintToggledString.getValue());
        }
        toggleSprintString = string;
    }
}
