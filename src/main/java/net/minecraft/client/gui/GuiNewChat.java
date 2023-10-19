package net.minecraft.client.gui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.hud.chat.ModuleChat;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.chat.ChatLineWrapper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiNewChat extends Gui {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    public final List<String> sentMessages = new ArrayList<>();
    public final List<ChatLine> chatLines = new ArrayList<>();
    public final List<ChatLine> drawnChatLines = new ArrayList<>();
    public final List<ChatLine> brandedLines = new ArrayList<>();
    public int scrollPos;
    public boolean isScrolled;

    private boolean smoothChat = false;
    public int newLines;
    public float percentComplete;
    public long prevMillis = System.currentTimeMillis();
    public float animationPercent;
    public int lineBeingDrawn;


    public GuiNewChat(Minecraft p_i1022_1_) {
        this.mc = p_i1022_1_;
    }

    public void func_146230_a(int p_146230_1_) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int scaledWidth = scaledResolution.getScaledWidth();
            int scaledHeight = scaledResolution.getScaledHeight();
            GuiScreen currentScreen = this.mc.currentScreen;
            if ((currentScreen instanceof HudLayoutEditorGui && ((HudLayoutEditorGui)currentScreen).helpButton.isMouseInside(Mouse.getX() * scaledWidth / this.mc.displayWidth, scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1)) || (CheatBreaker.getInstance().getModuleManager().chatMod.hiddenFromHud && !(currentScreen instanceof GuiChat))) {
                return;
            }

            int var2 = this.func_146232_i();
            boolean var3 = false;
            int var4 = 0;
            int var5 = this.drawnChatLines.size();
            float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (var5 > 0) {
                if (this.getChatOpen()) {
                    var3 = true;
                }

                float var7 = this.func_146244_h();
                int var8 = MathHelper.ceiling_float_int((float)this.func_146228_f() / var7);
                GL11.glPushMatrix();
                GL11.glTranslatef(2.0F, 20.0f, 0.0F);
                GL11.glScalef(var7, var7, 1.0F);
                int var9;
                int var11;
                int var14;

                for (var9 = 0; var9 + this.scrollPos < this.drawnChatLines.size() && var9 < var2; ++var9) {
                    ChatLine var10 = this.drawnChatLines.get(var9 + this.scrollPos);

                    if (var10 != null) {
                        var11 = p_146230_1_ - var10.getUpdatedCounter();

                        if (var11 < 200 || var3) {
                            double var12 = (double)var11 / 200.0D;
                            var12 = 1.0D - var12;
                            var12 *= 10.0D;

                            if (var12 < 0.0D) {
                                var12 = 0.0D;
                            }

                            if (var12 > 1.0D) {
                                var12 = 1.0D;
                            }

                            var12 *= var12;
                            var14 = (int)(255.0D * var12);

                            if (var3) {
                                var14 = 255;
                            }

                            var14 = (int)((float)var14 * var6);
                            ++var4;

                            if (var14 > 3) {
                                byte var15 = 0;
                                int var16 = -var9 * 9;
                                drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
                                String var17 = var10.func_151461_a().getFormattedText();

                                ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
                                if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                                    var17 = var17.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                                }

                                this.mc.fontRenderer.drawStringWithShadow(var17, var15, var16 - 8, 16777215 + (var14 << 24));
                                GL11.glDisable(GL11.GL_ALPHA_TEST);
                            }
                        }
                    }
                }

                if (var3) {
                    var9 = this.mc.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
                    int var18 = var5 * var9 + var5;
                    var11 = var4 * var9 + var4;
                    int var19 = this.scrollPos * var11 / var5;
                    int var13 = var11 * var11 / var18;

                    if (var18 != var11) {
                        var14 = var19 > 0 ? 170 : 96;
                        int var20 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -var19, 2, -var19 - var13, var20 + (var14 << 24));
                        drawRect(2, -var19, 1, -var19 - var13, 13421772 + (var14 << 24));
                    }
                }

                GL11.glPopMatrix();
            }
        }
    }

    public void drawCustomChat(int p_146230_1_, boolean smoothChat) {
        this.smoothChat = smoothChat;
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;

        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int scaledWidth = scaledResolution.getScaledWidth();
            int scaledHeight = scaledResolution.getScaledHeight();
            GuiScreen currentScreen = this.mc.currentScreen;
            boolean showInHudEditor = !CheatBreaker.getInstance().getModuleManager().chatMod.isRenderHud() || !(currentScreen instanceof HudLayoutEditorGui);
            boolean showWhenHidden = CheatBreaker.getInstance().getModuleManager().chatMod.hiddenFromHud && !(currentScreen instanceof GuiChat) && showInHudEditor;
            if ((currentScreen instanceof HudLayoutEditorGui && ((HudLayoutEditorGui)currentScreen).helpButton.isMouseInside(Mouse.getX() * scaledWidth / this.mc.displayWidth, scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1)) || showWhenHidden) {
                return;
            }

            int var2 = this.func_146232_i();
            boolean var3 = false;
            int var4 = 0;
            int var5 = this.drawnChatLines.size();
            float var6 = this.mc.gameSettings.chatOpacity * chatMod.masterOpacity.getFloatValue() / 100.0F * 0.9F + 0.1F;

            long current = System.currentTimeMillis();
            long diff = current - this.prevMillis;
            this.prevMillis = current;
            this.updatePercentage(diff);
            float t = this.percentComplete;
            this.animationPercent = clamp(1.0f - (t -= 1.0f) * t * t * t, 0.0f, 1.0f);

            if (var5 > 0) {
                if (this.getChatOpen()) {
                    var3 = true;
                }

                float var7 = this.func_146244_h();
                int var8 = MathHelper.ceiling_float_int((float)this.func_146228_f() / var7);
                GL11.glPushMatrix();
                float y = chatMod.isRenderHud() ? chatMod.height : 20.0F - chatMod.chatHeightFix.getFloatValue();
                if (this.smoothChat && !this.isScrolled) {
                    y += (9.0f - 9.0f * this.animationPercent) * this.func_146244_h();
                }
                GL11.glTranslatef(chatMod.isRenderHud() ? 0.0F : 2.0F, y, 0.0F);
                GL11.glScalef(var7, var7, 1.0F);
                int var9;
                int var11;
                int var14;

                for (var9 = 0; var9 + this.scrollPos < this.drawnChatLines.size() && var9 < var2; ++var9) {
                    ChatLine var10 = this.drawnChatLines.get(this.getLineBeingDrawn(var9 + this.scrollPos));

                    if (var10 != null) {
                        var11 = p_146230_1_ - var10.getUpdatedCounter();
                        if (var11 < 200 || var3) {
                            double var12 = (double)var11 / 200.0D;
                            var12 = 1.0D - var12;
                            var12 *= 10.0D;

                            if (var12 < 0.0D) {
                                var12 = 0.0D;
                            }

                            if (var12 > 1.0D) {
                                var12 = 1.0D;
                            }

                            var12 *= var12;
                            var14 = (int)(255.0D * var12);

                            if (var3) {
                                var14 = 255;
                            }

                            var14 = (int)((float)var14 * var6);
                            ++var4;

                            if (var14 > 3) {
                                byte var15 = 0;
                                int var16 = -var9 * 9;

                                String var17 = var10.func_151461_a().getFormattedText();
                                if (this.mc.currentScreen instanceof GuiChat && chatMod.background.getValue().equals("While Typing") || chatMod.background.getValue().equals("ON")) {
                                    int bga = (int) ((chatMod.backgroundColor.getColorValue() >> 24 & 255) * ((float)var14 / 255.0F));
                                    int bgr = chatMod.backgroundColor.getColorValue() >> 16 & 255;
                                    int bgg = chatMod.backgroundColor.getColorValue() >> 8 & 255;
                                    int bgb = chatMod.backgroundColor.getColorValue() & 255;
                                    int width = chatMod.backgroundWidthType.getValue().equals("Full") ? var8 + 4 : this.mc.fontRenderer.getStringWidth(var17);
                                    drawRect(var15, var16 - 9, var15 + width, var16, modifyBackgroundOpacity(new Color(bgr, bgg, bgb, bga).getRGB()));
                                }
                             
                                if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                                    if (!nickHider.customNameString.getStringValue().equals(Minecraft.getMinecraft().getSession().getUsername())) {
                                        var17 = var17.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                                    } else {
                                        var17 = var17.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), "You");
                                    }
                                }
                              
                                boolean strip = chatMod.stripFormattingColors.getBooleanValue();
                                GL11.glEnable(3042);

                                boolean nickHiderName = CheatBreaker.getInstance().getModuleManager().nickHiderMod.isEnabled() && CheatBreaker.getInstance().getModuleManager().nickHiderMod.hideRealName.getBooleanValue();
                                String nickHiderNameString = CheatBreaker.getInstance().getModuleManager().nickHiderMod.customNameString.getStringValue();

                                if (var17.contains(Minecraft.getMinecraft().getSession().getUsername()) || var17.contains(nickHiderNameString)) {
                                    if (!chatMod.highlightColor.getStringValue().equalsIgnoreCase("None") && chatMod.highlightOwnName.getBooleanValue()) {
                                        var17 = var17.replaceAll((nickHiderName ? nickHiderNameString : Minecraft.getMinecraft().getSession().getUsername()), chatMod.getHighlightColor() + (nickHiderName ? nickHiderNameString : Minecraft.getMinecraft().getSession().getUsername()) + EnumChatFormatting.RESET);
                                    }
                                }

                                for (int n7 = 0; n7 + this.scrollPos < this.drawnChatLines.size() && n7 < this.func_146232_i(); ++n7) {
                                    ChatLine chatLine = this.drawnChatLines.get(n7 + this.scrollPos);
                                    if (!var3 && !(10.0 - (double)(p_146230_1_ - chatLine.getUpdatedCounter()) / 20.0 > 1.0)) continue;
                                    if (chatLine instanceof ChatLineWrapper) {
                                        ChatLineWrapper chatLineWrapper = (ChatLineWrapper)chatLine;
                                        if (chatLineWrapper.isBranded()) {
                                            this.brandedLines.add(chatLine);
                                        }
                                    }
                                }

                                this.mc.fontRenderer.drawString(strip ? chatMod.stripColor(var17) : var17, var15 + (this.brandedLines.contains(var10) ? 14.5F : 0), var16 - 8, modifyTextOpacity(strip ? chatMod.textColor.getColorValue() : 16777215 + (var14 << 24)), chatMod.textShadow.getBooleanValue());
                                GL11.glDisable(GL11.GL_ALPHA_TEST);
                            }
                        }
                    }
                }

                if (var3) {
                    var9 = this.mc.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
                    int var18 = var5 * var9 + var5;
                    var11 = var4 * var9 + var4;
                    int var19 = this.scrollPos * var11 / var5;
                    int var13 = var11 * var11 / var18;

                    if (var18 != var11) {
                        var14 = var19 > 0 ? 170 : 96;
                        int var20 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -var19, 2, -var19 - var13, var20 + (var14 << 24));
                        drawRect(2, -var19, 1, -var19 - var13, 13421772 + (var14 << 24));
                    }
                }

                int n6 = 0;
                for (int n7 = 0; n7 + this.scrollPos < this.drawnChatLines.size() && n7 < this.func_146232_i(); ++n7) {
                    ChatLine chatLine = this.drawnChatLines.get(n7 + this.scrollPos);
                    if (!var3 && !(10.0 - (double)(p_146230_1_ - chatLine.getUpdatedCounter()) / 20.0 > 1.0)) continue;
                    n6 -= Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
                    if (!(chatLine instanceof ChatLineWrapper)) continue;
                    ChatLineWrapper chatLineWrapper = (ChatLineWrapper)chatLine;
                    if (!chatLineWrapper.isBranded()) continue;

                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderUtil.renderIcon(new ResourceLocation("client/logo_white.png"), (getChatOpen() ? 4.305F : 1.3F), (float)n6 + 1.5f, 12.0f, 6.0f);
                }

                GL11.glPopMatrix();
            }
        }
    }

    private int modifyTextOpacity(int original) {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        if (this.smoothChat && this.lineBeingDrawn <= this.newLines) {
            int opacity = original >> 24 & 0xFF;
            opacity = (int)((float)opacity * this.animationPercent * chatMod.textOpacity.getFloatValue() / 100.0F);
            return original & 0xFFFFFF | opacity << 24;
        }
        int a = (int)((original >> 24 & 255) * chatMod.textOpacity.getFloatValue() / 100.0F);
        return new Color(original >> 16 & 255, original >> 8 & 255, original & 255, a).getRGB();
    }

    private int modifyBackgroundOpacity(int original) {
        if (this.smoothChat && this.lineBeingDrawn <= this.newLines) {
            int opacity = original >> 24 & 0xFF;
            opacity = (int)((float)opacity * this.animationPercent);
            return original & 0xFFFFFF | opacity << 24;
        }
        return original;
    }

    private int getLineBeingDrawn(int line) {
        this.lineBeingDrawn = line;
        return line;
    }

    private void updatePercentage(long diff) {
        if (this.percentComplete < 1.0f) {
            this.percentComplete += 0.001f * CheatBreaker.getInstance().getModuleManager().chatMod.smoothChatSpeed.getFloatValue() / 2.0f * (float)diff;
        }
        this.percentComplete = clamp(this.percentComplete, 0.0f, 1.0f);
    }

    public static float clamp(float number, float min, float max) {
        return number < min ? min : Math.min(number, max);
    }

    public void func_146231_a() {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }

    public void func_146227_a(IChatComponent p_146227_1_) {
        this.func_146234_a(p_146227_1_, 0);
    }

    public void func_146234_av(IChatComponent p_146234_1_) {
        this.percentComplete = 0.0f;
        this.func_146237_a(p_146234_1_, 0, this.mc.ingameGUI.getUpdateCounter(), false);
    }

    public void func_146234_a(IChatComponent p_146234_1_, int p_146234_2_) {
        this.percentComplete = 0.0f;
        this.func_146237_a(p_146234_1_, p_146234_2_, this.mc.ingameGUI.getUpdateCounter(), false);
        logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
    }

    private String func_146235_b(String p_146235_1_) {
        return Minecraft.getMinecraft().gameSettings.chatColours ? p_146235_1_ : EnumChatFormatting.getTextWithoutFormattingCodes(p_146235_1_);
    }

    private void func_146237_a(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
        if (p_146237_2_ != 0) {
            this.func_146242_c(p_146237_2_);
        }

        int var5 = MathHelper.floor_float((float)this.func_146228_f() / this.func_146244_h());
        int var6 = 0;
        ChatComponentText var7 = new ChatComponentText("");
        List<ChatComponentText> var8 = Lists.newArrayList();
        ArrayList var9 = Lists.newArrayList(p_146237_1_);

        if (p_146237_1_ instanceof ChatComponentText && ((ChatComponentText)p_146237_1_).isBranded()) {
            var7.setBranded(true);
        }

        for (int var10 = 0; var10 < var9.size(); ++var10) {
            IChatComponent var11 = (IChatComponent)var9.get(var10);
            String var12 = this.func_146235_b(var11.getChatStyle().getFormattingCode() + var11.getUnformattedTextForChat());
            int var13 = this.mc.fontRenderer.getStringWidth(var12);
            ChatComponentText var14 = new ChatComponentText(var12);
            var14.setChatStyle(var11.getChatStyle().createShallowCopy());
            boolean var15 = false;

            if (var6 + var13 > var5) {
                String var16 = this.mc.fontRenderer.trimStringToWidth(var12, var5 - var6, false);
                String var17 = var16.length() < var12.length() ? var12.substring(var16.length()) : null;

                if (var17 != null && var17.length() > 0) {
                    int var18 = var16.lastIndexOf(" ");

                    if (var18 >= 0 && this.mc.fontRenderer.getStringWidth(var12.substring(0, var18)) > 0) {
                        var16 = var12.substring(0, var18);
                        var17 = var12.substring(var18);
                    }

                    ChatComponentText var19 = new ChatComponentText(var17);
                    var19.setChatStyle(var11.getChatStyle().createShallowCopy());
                    var9.add(var10 + 1, var19);
                }

                var13 = this.mc.fontRenderer.getStringWidth(var16);
                var14 = new ChatComponentText(var16);
                var14.setChatStyle(var11.getChatStyle().createShallowCopy());
                var15 = true;
            }

            if (var6 + var13 <= var5) {
                var6 += var13;
                var7.appendSibling(var14);
            } else {
                var15 = true;
            }

            if (var15) {
                var8.add(var7);
                var6 = 0;
                var7 = new ChatComponentText("");
            }
        }

        var8.add(var7);
        boolean var20 = this.getChatOpen();
        IChatComponent var22;

        for (ChatComponentText chatComponentText5 : var8) {
            ChatLine chatLine = new ChatLine(p_146237_3_, chatComponentText5, p_146237_2_);
            ChatLineWrapper of = ChatLineWrapper.of(chatLine);
            if (chatComponentText5.isBranded()) {
                of.setBranded(true);
                this.drawnChatLines.add(0, of);
            }

            if (!chatComponentText5.isBranded()) {
                this.drawnChatLines.add(0, new ChatLine(p_146237_3_, chatComponentText5, p_146237_2_));
            }

            if (var20 && this.scrollPos > 0) {
                this.isScrolled = true;
                this.func_146229_b(1);
            }
        }

        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        while (this.drawnChatLines.size() > 100 && !chatMod.unlimitedChat.getBooleanValue()) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }

        if (!p_146237_4_) {
            this.chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));

            while (this.chatLines.size() > 100 && !chatMod.unlimitedChat.getBooleanValue()) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }

        setNewLines(var8);
    }

    public void func_146245_b() {
        this.drawnChatLines.clear();
        this.resetScroll();

        for (int var1 = this.chatLines.size() - 1; var1 >= 0; --var1) {
            ChatLine var2 = this.chatLines.get(var1);
            this.func_146237_a(var2.func_151461_a(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
        }
    }

    private List<ChatComponentText> setNewLines(List<ChatComponentText> original) {
        this.newLines = original.size() - 1;
        return original;
    }

    public List func_146238_c() {
        return this.sentMessages;
    }

    public void func_146239_a(String p_146239_1_) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(p_146239_1_)) {
            this.sentMessages.add(p_146239_1_);
        }
    }

    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public void func_146229_b(int p_146229_1_) {
        this.scrollPos += p_146229_1_;
        int var2 = this.drawnChatLines.size();

        if (this.scrollPos > var2 - this.func_146232_i()) {
            this.scrollPos = var2 - this.func_146232_i();
        }

        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    public IChatComponent func_146236_a(int mouseX, int mouseY) {
        if (!this.getChatOpen()) {
            return null;
        } else {
            ScaledResolution var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int var4 = var3.getScaleFactor();
            float var5 = this.func_146244_h();
            int var6 = mouseX / var4 - 2;
            int var7 = mouseY / var4 - 28;
            ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
            if (chatMod.isRenderHud() && chatMod.isEnabled()) {
                float[] scaledPoints = chatMod.getScaledPoints(var3, true);
                var6 = (int)(mouseX / var4 / chatMod.masterScale() - scaledPoints[0]);
                var7 = (int)(mouseY / var4 / chatMod.masterScale() - this.mc.displayHeight / var4 / chatMod.masterScale() + chatMod.height + scaledPoints[1]);
            } else if (!chatMod.isRenderHud() && chatMod.isEnabled()) {
                var7 = (int)(var7 - chatMod.chatHeightFix.getFloatValue());
            }
            var6 = MathHelper.floor_float((float)var6 / var5);
            var7 = MathHelper.floor_float((float)var7 / var5);

            if (var6 >= 0 && var7 >= 0) {
                int var8 = Math.min(this.func_146232_i(), this.drawnChatLines.size());

                if (var6 <= MathHelper.floor_float((float)this.func_146228_f() / this.func_146244_h()) && var7 < this.mc.fontRenderer.FONT_HEIGHT * var8 + var8) {
                    int var9 = var7 / this.mc.fontRenderer.FONT_HEIGHT + this.scrollPos;

                    if (var9 >= 0 && var9 < this.drawnChatLines.size()) {
                        ChatLine var10 = this.drawnChatLines.get(var9);
                        int var11 = 0;

                        for (Object o : var10.func_151461_a()) {
                            IChatComponent var13 = (IChatComponent) o;

                            if (var13 instanceof ChatComponentText) {
                                var11 += this.mc.fontRenderer.getStringWidth(this.func_146235_b(((ChatComponentText) var13).getChatComponentText_TextValue()));

                                if (var11 > var6) {
                                    return var13;
                                }
                            }
                        }
                    }

                    return null;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public void func_146242_c(int p_146242_1_) {
        Iterator var2 = this.drawnChatLines.iterator();
        ChatLine var3;

        while (var2.hasNext()) {
            var3 = (ChatLine)var2.next();

            if (var3.getChatLineID() == p_146242_1_) {
                var2.remove();
            }
        }

        var2 = this.chatLines.iterator();

        while (var2.hasNext()) {
            var3 = (ChatLine)var2.next();

            if (var3.getChatLineID() == p_146242_1_) {
                var2.remove();
                break;
            }
        }
    }

    public int func_146228_f() {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? func_146233_a(this.mc.gameSettings.chatWidth) : chatMod.backgroundWidth.getIntegerValue();
    }

    public int func_146246_g() {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? func_146243_b(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused) : this.getChatOpen() ? chatMod.focusedBackgroundHeight.getIntegerValue() : chatMod.unfocusedBackgroundHeight.getIntegerValue();
    }

    public float func_146244_h() {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? this.mc.gameSettings.chatScale : chatMod.textSize.getFloatValue() / 100.0F;
    }

    public static int func_146233_a(float p_146233_0_) {
        short var1 = 320;
        byte var2 = 40;
        return MathHelper.floor_float(p_146233_0_ * (float)(var1 - var2) + (float)var2);
    }

    public static int func_146243_b(float p_146243_0_) {
        short var1 = 180;
        byte var2 = 20;
        return MathHelper.floor_float(p_146243_0_ * (float)(var1 - var2) + (float)var2);
    }

    public int func_146232_i() {
        return this.func_146246_g() / 9;
    }
}
