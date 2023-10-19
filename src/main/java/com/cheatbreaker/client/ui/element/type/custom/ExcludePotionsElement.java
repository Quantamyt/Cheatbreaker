package com.cheatbreaker.client.ui.element.type.custom;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ExcludePotionsElement extends AbstractModulesGuiElement {
    private final List<Potion> list2 = new ArrayList<>();

    public ExcludePotionsElement(Setting setting, float scale) {
        super(scale);
        this.height = 50;
        this.setting = setting;
        list();
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 2), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        int n3 = 0;
        int n4 = 0;
        for (Potion item : list2) {
//            Item item = Item.getItemFromBlock(block);
            if (item == null) continue;
            if (n3 >= 15) {
                n3 = 0;
                ++n4;
            }
            int n5 = this.x + 12 + n3 * 20;
            int n6 = this.y + 14 + n4 * 20;
            boolean hovering = (float) mouseX > (float) (n5 - 2) * this.scale && (float) mouseX < (float) (n5 + 18) * this.scale && (float) mouseY > (float) (n6 - 2 + this.yOffset) * this.scale && (float) mouseY < (float) (n6 + 18 + this.yOffset) * this.scale;
            if (this.setting.getArrayListValue().contains(item.id)) {
                Gui.drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x7F00FF00);
            } else if (hovering) {
                Gui.drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x4F0000FF);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
            int n7 = item.getStatusIconIndex();
            RenderUtil.drawTexturedModalRect(n5 - 1, (float) n6 - 1, (float) (n7 % 8 * 18), (float) (198 + n7 / 8 * 18), 18, 18);
            ++n3;
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        GL11.glDisable(3042);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        try {
            int n5 = 0;
            int n6 = 0;
            for (Integer item : (Integer[]) setting.getAcceptedValues()) {
//                Item item = Item.getItemFromBlock(block);
                if (item == null) continue;
                if (n5 >= 15) {
                    n5 = 0;
                    ++n6;
                }
                int n7 = this.x + 12 + n5 * 20;
                int n8 = this.y + 14 + n6 * 20;
                boolean bl = (float) mouseX > (float) (n7 - 2) * this.scale && (float) mouseX < (float) (n7 + 18) * this.scale && (float) mouseY > (float) (n8 - 2 + this.yOffset) * this.scale && (float) mouseY < (float) (n8 + 18 + this.yOffset) * this.scale;
                if (bl && button == 0) {
                    int n9 = item;
                    if (((List<Integer>) this.setting.getValue()).contains(n9)) {
                        ((List<Integer>) this.setting.getValue()).removeIf(n2 -> n2 == n9);
                    } else {
                        this.setting.getArrayListValue().add(n9);
                    }
                    setting.setValue(this.setting.getArrayListValue());
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                }
                ++n5;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void list() {
        list2.add(Potion.moveSpeed);
        list2.add(Potion.moveSlowdown);
        list2.add(Potion.digSpeed);
        list2.add(Potion.digSlowdown);
        list2.add(Potion.damageBoost);
        list2.add(Potion.jump);
        list2.add(Potion.confusion);
        list2.add(Potion.regeneration);
        list2.add(Potion.resistance);
        list2.add(Potion.fireResistance);
        list2.add(Potion.waterBreathing);
        list2.add(Potion.invisibility);
        list2.add(Potion.blindness);
        list2.add(Potion.nightVision);
        list2.add(Potion.hunger);
        list2.add(Potion.weakness);
        list2.add(Potion.poison);
        list2.add(Potion.wither);
        list2.add(Potion.absorption);
    }
}
