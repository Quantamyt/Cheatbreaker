package com.cheatbreaker.client.ui.element.type.custom;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.impl.normal.hud.armorstatus.ModuleArmorStatus;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class XRayOptionsElement extends AbstractModulesGuiElement {
    private final String label;
    private final List<Integer> blocksList;

    public XRayOptionsElement(List<Integer> list, String label, float scale) {
        super(scale);
        this.height = 220;
        this.blocksList = list;
        this.label = label;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.label.toUpperCase(), this.x + 10, (float)(this.y + 2), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        int n3 = 0;
        int n4 = 0;
        for (Block block : Block.blockRegistry) {
            Item item = Item.getItemFromBlock(block);
            if (item == null) continue;
            if (n3 >= 15) {
                n3 = 0;
                ++n4;
            }
            int n5 = this.x + 12 + n3 * 20;
            int n6 = this.y + 14 + n4 * 20;
            boolean hovering = (float) mouseX > (float) (n5 - 2) * this.scale && (float) mouseX < (float) (n5 + 18) * this.scale && (float) mouseY > (float) (n6 - 2 + this.yOffset) * this.scale && (float) mouseY < (float) (n6 + 18 + this.yOffset) * this.scale;
            if (blocksList.contains(Item.getIdFromItem(item))) {
                Gui.drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x7F00FF00);
            } else if (hovering) {
                Gui.drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x4F0000FF);
            }
            ModuleArmorStatus.renderItem.renderItemAndEffectIntoGUI(new ItemStack(item), n5, n6);
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
            for (Block block : Block.blockRegistry) {
                Item item = Item.getItemFromBlock(block);
                if (item == null) continue;
                if (n5 >= 15) {
                    n5 = 0;
                    ++n6;
                }
                int n7 = this.x + 12 + n5 * 20;
                int n8 = this.y + 14 + n6 * 20;
                boolean bl = (float) mouseX > (float) (n7 - 2) * this.scale && (float) mouseX < (float) (n7 + 18) * this.scale && (float) mouseY > (float) (n8 - 2 + this.yOffset) * this.scale && (float) mouseY < (float) (n8 + 18 + this.yOffset) * this.scale;
                if (bl && button == 0) {
                    int n9 = Item.getIdFromItem(item);
                    if (blocksList.contains(n9)) {
                        blocksList.removeIf(n2 -> n2 == n9);
                    } else {
                        blocksList.add(n9);
                    }
                    if (CheatBreaker.getInstance().getModuleManager().xray.isEnabled()) {
                        Minecraft.getMinecraft().renderGlobal.loadRenderers();
                    }
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                }
                ++n5;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
