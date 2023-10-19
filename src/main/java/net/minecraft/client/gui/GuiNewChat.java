package net.minecraft.client.gui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.chat.ChatReceivedEvent;
import com.cheatbreaker.client.module.impl.normal.hud.chat.ModuleChat;
import com.cheatbreaker.client.module.impl.normal.hypixel.ModuleNickHider;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.chat.ChatLineWrapper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GuiNewChat extends Gui
{
    private static final Logger logger = LogManager.getLogger();
    private final ResourceLocation iconLocation = new ResourceLocation("client/logo_white.png");
    private final Minecraft mc;
    private final List<String> sentMessages = Lists.<String>newArrayList();
    private final List<ChatLine> chatLines = Lists.<ChatLine>newArrayList();
    public final List<ChatLine> drawnChatLines = Lists.<ChatLine>newArrayList();
    public final List<ChatLine> brandedLines = new ArrayList<>();
    public int scrollPos;
    private boolean isScrolled;

    private boolean smoothChat = false;
    public int newLines;
    public float percentComplete;
    public long prevMillis = System.currentTimeMillis();
    public float animationPercent;
    public int lineBeingDrawn;

    public GuiNewChat(Minecraft mcIn)
    {
        this.mc = mcIn;
    }

    public void drawChat(int updateCounter)
    {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            int scaledWidth = scaledResolution.getScaledWidth();
            int scaledHeight = scaledResolution.getScaledHeight();
            GuiScreen currentScreen = this.mc.currentScreen;
            if ((currentScreen instanceof HudLayoutEditorGui && ((HudLayoutEditorGui)currentScreen).helpButton.isMouseInside(Mouse.getX() * scaledWidth / this.mc.displayWidth, scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1)) || (CheatBreaker.getInstance().getModuleManager().chatMod.hiddenFromHud && !(currentScreen instanceof GuiChat))) {
                return;
            }

            int i = this.getLineCount();
            boolean flag = false;
            int j = 0;
            int k = this.drawnChatLines.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (k > 0)
            {
                if (this.getChatOpen())
                {
                    flag = true;
                }

                float f1 = this.getChatScale();
                int l = MathHelper.ceiling_float_int((float)this.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(f1, f1, 1.0F);

                for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1)
                {
                    ChatLine chatline = (ChatLine)this.drawnChatLines.get(i1 + this.scrollPos);

                    if (chatline != null)
                    {
                        int j1 = updateCounter - chatline.getUpdatedCounter();

                        if (j1 < 200 || flag)
                        {
                            double d0 = (double)j1 / 200.0D;
                            d0 = 1.0D - d0;
                            d0 = d0 * 10.0D;
                            d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
                            d0 = d0 * d0;
                            int l1 = (int)(255.0D * d0);

                            if (flag)
                            {
                                l1 = 255;
                            }

                            l1 = (int)((float)l1 * f);
                            ++j;

                            if (l1 > 3)
                            {
                                int i2 = 0;
                                int j2 = -i1 * 9;
                                drawRect(i2, j2 - 9, i2 + l + 4, j2, l1 / 2 << 24);
                                String s = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();

                                ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
                                if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                                    s = s.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                                }

                                this.mc.fontRendererObj.drawStringWithShadow(s, (float)i2, (float)(j2 - 8), 16777215 + (l1 << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag)
                {
                    int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int l2 = k * k2 + k;
                    int i3 = j * k2 + j;
                    int j3 = this.scrollPos * i3 / k;
                    int k1 = i3 * i3 / l2;

                    if (l2 != i3)
                    {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
                        drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

    public void drawCustomChat(int updateCounter, boolean smoothChat) {
        this.smoothChat = smoothChat;
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;

        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            int scaledWidth = scaledResolution.getScaledWidth();
            int scaledHeight = scaledResolution.getScaledHeight();
            GuiScreen currentScreen = this.mc.currentScreen;
            boolean showInHudEditor = !CheatBreaker.getInstance().getModuleManager().chatMod.isRenderHud() || !(currentScreen instanceof HudLayoutEditorGui);
            boolean showWhenHidden = CheatBreaker.getInstance().getModuleManager().chatMod.hiddenFromHud && !(currentScreen instanceof GuiChat) && showInHudEditor;

            if ((currentScreen instanceof HudLayoutEditorGui && ((HudLayoutEditorGui)currentScreen).helpButton.isMouseInside(Mouse.getX() * scaledWidth / this.mc.displayWidth, scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1)) || showWhenHidden) {
                return;
            }

            int var2 = this.getLineCount();
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

                float var7 = this.getChatScale();
                int var8 = MathHelper.ceiling_float_int((float)this.getChatWidth() / var7);
                GL11.glPushMatrix();
                float y = chatMod.isRenderHud() ? chatMod.height : 20.0F - chatMod.chatHeightFix.getFloatValue();
                if (this.smoothChat && !this.isScrolled) {
                    y += (9.0f - 9.0f * this.animationPercent) * this.getChatScale();
                }
                GL11.glTranslatef(chatMod.isRenderHud() ? 0.0F : 2.0F, y, 0.0F);
                GL11.glScalef(var7, var7, 1.0F);
                int var9;
                int var11;
                int var14;

                for (var9 = 0; var9 + this.scrollPos < this.drawnChatLines.size() && var9 < var2; ++var9) {
                    ChatLine var10 = this.drawnChatLines.get(this.getLineBeingDrawn(var9 + this.scrollPos));

                    if (var10 != null) {
                        var11 = updateCounter - var10.getUpdatedCounter();
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
                                String var17 = var10.getChatComponent().getFormattedText();
                                if (this.mc.currentScreen instanceof GuiChat && chatMod.background.getValue().equals("While Typing") || chatMod.background.getValue().equals("ON")) {
                                    int bga = (int) ((chatMod.backgroundColor.getColorValue() >> 24 & 255) * ((float)var14 / 255.0F));
                                    int bgr = chatMod.backgroundColor.getColorValue() >> 16 & 255;
                                    int bgg = chatMod.backgroundColor.getColorValue() >> 8 & 255;
                                    int bgb = chatMod.backgroundColor.getColorValue() & 255;
                                    int width = chatMod.backgroundWidthType.getValue().equals("Full") ? var8 + 4 : this.mc.fontRendererObj.getStringWidth(var17);
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
                                boolean nickHiderName = CheatBreaker.getInstance().getModuleManager().nickHiderMod.isEnabled() && CheatBreaker.getInstance().getModuleManager().nickHiderMod.hideRealName.getBooleanValue();
                                String nickHiderNameString = CheatBreaker.getInstance().getModuleManager().nickHiderMod.customNameString.getStringValue();

                                if (var17.contains(Minecraft.getMinecraft().getSession().getUsername()) || var17.contains(nickHiderNameString)) {
                                    if (!chatMod.highlightColor.getStringValue().equalsIgnoreCase("None")) {
                                        var17 = var17.replaceAll((nickHiderName ? nickHiderNameString : Minecraft.getMinecraft().getSession().getUsername()), chatMod.getHighlightColor() + (nickHiderName ? nickHiderNameString : Minecraft.getMinecraft().getSession().getUsername()) + EnumChatFormatting.RESET);
                                    }
                                }

                                for (int n7 = 0; n7 + this.scrollPos < this.drawnChatLines.size() && n7 < this.getLineCount(); ++n7) {
                                    if (!var3 && !(10.0 - (double)(updateCounter - var10.getUpdatedCounter()) / 20.0 > 1.0)) continue;
                                    if (var10 instanceof ChatLineWrapper) {
                                        ChatLineWrapper chatLineWrapper = (ChatLineWrapper)var10;
                                        if (chatLineWrapper.isBranded()) {
                                            this.brandedLines.add(var10);
                                        }
                                    }
                                }

                                GlStateManager.enableBlend();
                                this.mc.fontRendererObj.drawString(strip ? chatMod.stripColor(var17) : var17, var15 + (this.brandedLines.contains(var10) ? 16F : 0), var16 - 8, modifyTextOpacity(strip ? chatMod.textColor.getColorValue() : 16777215 + (var14 << 24)), chatMod.textShadow.getBooleanValue());
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (var3) {
                    var9 = this.mc.fontRendererObj.FONT_HEIGHT;
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
                for (int n7 = 0; n7 + this.scrollPos < this.drawnChatLines.size() && n7 < this.getLineCount(); ++n7) {
                    ChatLine chatLine = this.drawnChatLines.get(n7 + this.scrollPos);
                    if (!var3 && !(10.0 - (double)(updateCounter - chatLine.getUpdatedCounter()) / 20.0 > 1.0)) continue;
                    n6 -= Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
                    if (!(chatLine instanceof ChatLineWrapper)) continue;
                    ChatLineWrapper chatLineWrapper = (ChatLineWrapper)chatLine;
                    if (!chatLineWrapper.isBranded()) continue;

                    if (chatMod.textShadow.getBooleanValue()) {
                        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                        RenderUtil.renderIcon(this.iconLocation, (getChatOpen() ? 4.105F : 1.5F), (float)n6 + 1.5f, 14.0f, 6.9f);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderUtil.renderIcon(this.iconLocation, (getChatOpen() ? 4.105F : 1.3F), (float)n6 + 0.9f, 14.0f, 6.9f);
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

    public void clearChatMessages()
    {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }

    public void printChatMessage(IChatComponent chatComponent)
    {
        this.printChatMessageWithOptionalDeletion(chatComponent, 0);
    }

    public void dontPrintChatMessageWithOptionalDeletion(IChatComponent p_146234_1_) {
        this.percentComplete = 0.0f;

        this.setChatLine(p_146234_1_, 0, this.mc.ingameGUI.getUpdateCounter(), false);
    }

    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId)
    {
        if (this.hideReceivedChatMessage(chatComponent.getUnformattedText())) {
            return;
        }

        CheatBreaker.getInstance().getEventBus().handleEvent(new ChatReceivedEvent(chatComponent.getUnformattedText()));
        this.percentComplete = 0.0f;
        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        logger.info("[CHAT] " + chatComponent.getUnformattedText());
    }

    private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly)
    {
        if (chatLineId != 0)
        {
            this.deleteChatLine(chatLineId);
        }

        int i = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
        boolean flag = this.getChatOpen();

        for (IChatComponent ichatcomponent : list)
        {
            if (flag && this.scrollPos > 0)
            {
                this.isScrolled = true;
                this.scroll(1);
            }

            ChatLine chatLine = new ChatLine(updateCounter, chatComponent, chatLineId);
            ChatLineWrapper of = ChatLineWrapper.of(chatLine);

            if (chatComponent instanceof ChatComponentText) {
                if (((ChatComponentText)chatComponent).isBranded()) {
                    of.setBranded(true);
                    this.drawnChatLines.add(0, of);
                }

                if (!((ChatComponentText)chatComponent).isBranded()) {
                    this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
                }
            }
        }

        while (this.drawnChatLines.size() > 100) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }

        if (!displayOnly)
        {

            ChatLine chatLine = new ChatLine(updateCounter, chatComponent, chatLineId);
            ChatLineWrapper of = ChatLineWrapper.of(chatLine);

            if (chatComponent instanceof ChatComponentText) {
                if (((ChatComponentText)chatComponent).isBranded()) {
                    of.setBranded(true);
                    this.chatLines.add(0, of);
                }
            } else {
                this.drawnChatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
            }

            while (this.chatLines.size() > 100)
            {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
        setNewLines(list);
    }

    private boolean hideReceivedChatMessage(String string2) {
        if (CheatBreaker.getInstance().getModuleManager().hypixelMod.isEnabled() && Minecraft.getMinecraft().getCurrentServerData() != null && Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {
            if (CheatBreaker.getInstance().getModuleManager().hypixelMod.autoTip.getBooleanValue() && (string2.startsWith("You can't tip the same person") || string2.equals("Still processing your most recent request!") || string2.startsWith("You've already tipped that person") || string2.equals("You cannot tip yourself!") || string2.startsWith("You can only use the /tip command") || string2.equals("You are not allowed to use commands as a spectator!") || string2.equals("Slow down! You can only use /tip every few seconds.") || string2.startsWith("You've already tipped someone in the past hour in "))) {
                return true;
            }
            if (CheatBreaker.getInstance().getModuleManager().hypixelMod.antiGL.getBooleanValue() && Arrays.stream(string2.split(" ")).anyMatch(string -> string.equalsIgnoreCase("gl"))) {
                return true;
            }
            return CheatBreaker.getInstance().getModuleManager().hypixelMod.antiGG.getBooleanValue() && Arrays.stream(string2.split(" ")).anyMatch(string -> string.equalsIgnoreCase("gg"));
        }
        return false;
    }

    public void refreshChat()
    {
        this.drawnChatLines.clear();
        this.resetScroll();

        for (int i = this.chatLines.size() - 1; i >= 0; --i)
        {
            ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }

    private List<IChatComponent> setNewLines(List<IChatComponent> original) {
        this.newLines = original.size() - 1;
        return original;
    }

    public List<String> getSentMessages()
    {
        return this.sentMessages;
    }

    public void addToSentMessages(String message)
    {
        if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(message))
        {
            this.sentMessages.add(message);
        }
    }

    public void resetScroll()
    {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public void scroll(int amount)
    {
        this.scrollPos += amount;
        int i = this.drawnChatLines.size();

        if (this.scrollPos > i - this.getLineCount())
        {
            this.scrollPos = i - this.getLineCount();
        }

        if (this.scrollPos <= 0)
        {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    public IChatComponent getChatComponent(int mouseX, int mouseY)
    {
        if (!this.getChatOpen())
        {
            return null;
        }
        else
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaleFactor();
            float f = this.getChatScale();
            int j = mouseX / i - 2;
            int k = mouseY / i - 28;
            ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
            if (chatMod.isRenderHud() && chatMod.isEnabled()) {
                float[] scaledPoints = chatMod.getScaledPoints(scaledresolution, true);
                j = (int)(mouseX / i / chatMod.masterScale() - scaledPoints[0]);
                k = (int)(mouseY / i / chatMod.masterScale() - this.mc.displayHeight / i / chatMod.masterScale() + chatMod.height + scaledPoints[1]);
            } else if (!chatMod.isRenderHud() && chatMod.isEnabled()) {
                k = (int)(k - chatMod.chatHeightFix.getFloatValue());
            }
            j = MathHelper.floor_float((float)j / f);
            k = MathHelper.floor_float((float)k / f);

            if (j >= 0 && k >= 0)
            {
                int l = Math.min(this.getLineCount(), this.drawnChatLines.size());

                if (j <= MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l)
                {
                    int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;

                    if (i1 >= 0 && i1 < this.drawnChatLines.size())
                    {
                        ChatLine chatline = (ChatLine)this.drawnChatLines.get(i1);
                        int j1 = 0;

                        for (IChatComponent ichatcomponent : chatline.getChatComponent())
                        {
                            if (ichatcomponent instanceof ChatComponentText)
                            {
                                j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));

                                if (j1 > j)
                                {
                                    return ichatcomponent;
                                }
                            }
                        }
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

    public boolean getChatOpen()
    {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public void deleteChatLine(int id)
    {
        Iterator<ChatLine> iterator = this.drawnChatLines.iterator();

        while (iterator.hasNext())
        {
            ChatLine chatline = (ChatLine)iterator.next();

            if (chatline.getChatLineID() == id)
            {
                iterator.remove();
            }
        }

        iterator = this.chatLines.iterator();

        while (iterator.hasNext())
        {
            ChatLine chatline1 = (ChatLine)iterator.next();

            if (chatline1.getChatLineID() == id)
            {
                iterator.remove();
                break;
            }
        }
    }

    public int getChatWidth()
    {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? calculateChatboxWidth(this.mc.gameSettings.chatWidth) : chatMod.backgroundWidth.getIntegerValue();
    }

    public int getChatHeight()
    {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused) : this.getChatOpen() ? chatMod.focusedBackgroundHeight.getIntegerValue() : chatMod.unfocusedBackgroundHeight.getIntegerValue();
    }

    public float getChatScale()
    {
        ModuleChat chatMod = CheatBreaker.getInstance().getModuleManager().chatMod;
        return !chatMod.isRenderHud() || !chatMod.isEnabled() ? this.mc.gameSettings.chatScale : chatMod.textSize.getFloatValue() / 100.0F;
    }

    public static int calculateChatboxWidth(float scale)
    {
        int i = 320;
        int j = 40;
        return MathHelper.floor_float(scale * (float)(i - j) + (float)j);
    }

    public static int calculateChatboxHeight(float scale)
    {
        int i = 180;
        int j = 20;
        return MathHelper.floor_float(scale * (float)(i - j) + (float)j);
    }

    public int getLineCount()
    {
        return this.getChatHeight() / 9;
    }
}
