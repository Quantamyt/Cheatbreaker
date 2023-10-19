package net.minecraft.client.renderer;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.Iterator;

public abstract class InventoryEffectRenderer extends GuiContainer {
    private boolean field_147045_u;


    public InventoryEffectRenderer(Container p_i1089_1_) {
        super(p_i1089_1_);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui() {
        super.initGui();
        boolean bl2 = CheatBreaker.getInstance().getModuleManager().potionEffectsMod.isEnabled() && (Boolean) CheatBreaker.getInstance().getModuleManager().potionEffectsMod.potionInfoInInventory.getValue();
        boolean bl = !CheatBreaker.getInstance().getModuleManager().potionEffectsMod.isEnabled() && (Boolean) CheatBreaker.getInstance().getGlobalSettings().showPotionInfo.getValue();
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty() && (bl2 || bl)) {
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().shiftPotionInfo.getValue()) {
                this.field_147003_i = 160 + (this.width - this.field_146999_f - 200) / 2;
            }
            this.field_147045_u = true;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

        if (this.field_147045_u) {
            this.func_147044_g();
        }
    }

    private void func_147044_g() {
        int var1 = this.field_147003_i - 124;
        int var2 = this.field_147009_r;
        boolean var3 = true;
        Collection<PotionEffect> var4 = this.mc.thePlayer.getActivePotionEffects();

        if (!var4.isEmpty()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int var5 = 33;

            if (var4.size() > 5) {
                var5 = 132 / (var4.size() - 1);
            }

            for (Iterator<PotionEffect> var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5) {
                PotionEffect var7 = var6.next();
                Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.getTextureManager().bindTexture(field_147001_a);
                drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

                if (var8.hasStatusIcon()) {
                    int var9 = var8.getStatusIconIndex();
                    drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String var11 = I18n.format(var8.getName());

                if (var7.getAmplifier() == 1) {
                    var11 = var11 + " " + I18n.format("enchantment.level.2");
                } else if (var7.getAmplifier() == 2) {
                    var11 = var11 + " " + I18n.format("enchantment.level.3");
                } else if (var7.getAmplifier() == 3) {
                    var11 = var11 + " " + I18n.format("enchantment.level.4");
                }

                this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(var7);
                this.fontRendererObj.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
            }
        }
    }
}
