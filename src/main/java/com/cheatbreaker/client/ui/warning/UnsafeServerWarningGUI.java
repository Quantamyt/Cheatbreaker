package com.cheatbreaker.client.ui.warning;


import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.render.wordwrap.WordWrap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class UnsafeServerWarningGUI extends GuiScreen {
    private final ServerData selectedButton;
    private final GuiScreen eventButton;
    private final long currentTime;
    private final String reason;
    private final boolean blocked;

    public UnsafeServerWarningGUI(GuiScreen guiScreen, ServerData serverData, String reason, boolean block) {
        this.eventButton = guiScreen;
        this.selectedButton = serverData;
        this.currentTime = System.currentTimeMillis();
        this.reason = reason;
        this.blocked = block;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
        float f2 = 0.52F;
        int n3 = this.currentTime == 0L ? 10 : (int)((10999L - (System.currentTimeMillis() - this.currentTime)) / 1000L);
        String leaveString = this.blocked ? "Leave" : "Get me out of here!";
        String continueString = "I understand the risk, continue anyways " + (n3 > 0 ? "(" + n3 + ")" : "");
        String title = this.blocked ? "THIS SERVER IS " + EnumChatFormatting.RED + "BLOCKED" : "WARNING!";
        float f3 = this.mc.fontRenderer.getStringWidth(leaveString);
        boolean bl = mouseX > this.width / 2 + 20 && mouseX < this.width / 2 + 140 && mouseY > this.height / 2 + 40 && mouseY < this.height / 2 + 50;
        boolean bl2 = (this.blocked ? mouseX > this.width / 2 - 20 : mouseX > this.width / 2 - 130) && (this.blocked ? (float) mouseX < (float)(this.width / 2 - 20) + f3 / f2 : (float) mouseX < (float)(this.width / 2 - 130) + f3 / f2) && mouseY > this.height / 2 + 30 && mouseY < this.height / 2 + 42;
        RenderUtil.drawRoundedRect(this.width / 2 - 140, this.height / 2 - 100, this.width / 2 + 140, this.height / 2 + 50, 8, 0x4FFF0000);
        GL11.glPushMatrix();
        GL11.glScalef(f2, f2, f2);
        if (!this.blocked) {
            this.mc.fontRenderer.drawStringWithShadow(continueString, (float)((int)((float)(this.width / 2 + 20) / f2)), (float)((int)((float)(this.height / 2 + 40) / f2)), bl ? -1 : (n3 <= 0 ? -1907998 : -5066062));
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        f2 = 1.0f;
        GL11.glScalef(f2, f2, f2);
        String wrapped = WordWrap.from(this.reason).maxWidth(50).insertHyphens(false).wrap();
        String[] lines = wrapped.split("\n");
        int index = 0;
        for(String line : lines) {
//            CheatBreaker.getInstance().playRegular14px.drawCenteredString(line, this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 14.0f + (index * CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f), 0xFFDDDDDD);
            this.mc.fontRenderer.drawCenteredStringWithShadow(line, (int)((float)(this.width / 2) / f2), (int)((float)(this.height / 2 - 70 + (index * 20)) / f2), -1);
            index++;
        }
        if (!this.blocked) {
            this.mc.fontRenderer.drawCenteredStringWithShadow("Join at " + EnumChatFormatting.BOLD +  "your own" + EnumChatFormatting.RESET + " risk!", (int)((float)(this.width / 2) / f2), (int)((float)(this.height / 2 - 70 + (index * 20)) / f2), -1);
        }
//        this.mc.fontRenderer.drawCenteredStringWithShadow(!this.blockedwarn ? "This is a test message." : "You cannot join this server", (int)((float)(this.width / 2) / f2), (int)((float)(this.height / 2 - 70) / f2), -1);
//        this.mc.fontRenderer.drawCenteredStringWithShadow(!this.blockedwarn ? "This is a test message 2." : "", (int)((float)(this.width / 2) / f2), ((int)((float)(this.height / 2 - 50) / f2)), -1);
//        this.mc.fontRenderer.drawStringWithShadow(" ", (float)((int)((float)(this.width / 2 - 130) / f2)), (float)((int)((float)(this.height / 2 - 40) / f2)), -1);
//        this.mc.fontRenderer.drawCenteredStringWithShadow(!this.blockedwarn ? "Join at " + EnumChatFormatting.BOLD +  "your own" + EnumChatFormatting.RESET + " risk!" : "", (int)((float)(this.width / 2) / f2), (int)((float)(this.height / 2 - 30) / f2), -1);
//        this.mc.fontRenderer.drawStringWithShadow(" ", (float)((int)((float)(this.width / 2 - 130) / f2)), (float)((int)((float)(this.height / 2 - 20) / f2)), -1);
//        this.mc.fontRenderer.drawStringWithShadow(" ", (float)((int)((float)(this.width / 2 - 130) / f2)), (float)((int)((float)(this.height / 2 - 10) / f2)), -1);
//        this.mc.fontRenderer.drawStringWithShadow(" ", (float)((int)((float)(this.width / 2 - 130) / f2)), (float)((int)((float)(this.height / 2) / f2)), -1);
        GL11.glPopMatrix();
        this.mc.fontRenderer.drawCenteredStringWithShadow(title, this.width / 2, this.height / 2 - 90, -1);
        if (this.blocked) {
            this.mc.fontRenderer.drawCenteredStringWithShadow(leaveString, this.width / 2, this.height / 2 + 30, bl2 ? -1 : -1907998);
        } else {
            this.mc.fontRenderer.drawStringWithShadow(leaveString, (float)(this.width / 2 - 130), (float)(this.height / 2 + 30), bl2 ? -1 : -1907998);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        float f = 0.5F;
        String leaveString = this.blocked ? "Leave" : "Get me out of here!";
        float f2 = this.mc.fontRenderer.getStringWidth(leaveString);
        boolean bl2 = mouseX > this.width / 2 + 20 && mouseX < this.width / 2 + 140 && mouseY > this.height / 2 + 40 && mouseY < this.height / 2 + 50;
        boolean bl = (this.blocked ? mouseX > this.width / 2 - 20 : mouseX > this.width / 2 - 130) &&
                (this.blocked ? (float) mouseX < (float)(this.width / 2 - 20) + f2 / f : (float) mouseX < (float)(this.width / 2 - 130) + f2 / f)
                && mouseY > this.height / 2 + 30 && mouseY < this.height / 2 + 42;
        if (bl) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(this.eventButton);
        }
        if (bl2 && !this.blocked) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            int n4 = this.currentTime == 0L ? 10 : (int)((10999L - (System.currentTimeMillis() - this.currentTime)) / 1000L);
            if (n4 <= 0) {
                this.mc.displayGuiScreen(new GuiConnecting(this.eventButton, this.mc, this.selectedButton));
            }
        }
    }
}
