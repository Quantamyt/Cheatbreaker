package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MainMenuAccountButton extends GuiButton {
    private final boolean lIIIIlIIllIIlIIlIIIlIIllI = true;
    private final String displayName;
    private final ResourceLocation headIcon;
    private final Map<String, Object> lIIIIIllllIIIIlIlIIIIlIlI;
    private final String clientToken;

    public MainMenuAccountButton(int var1, Map var2, int var3, int var4, int var5, int var6) {
        super(var1, var3, var4, var5, var6, (String)var2.get("displayName"));
        this.displayName = (String)var2.get("displayName");
        this.clientToken = (String)var2.get("clientToken");
        this.lIIIIIllllIIIIlIlIIIIlIlI = var2;
        this.headIcon = CheatBreaker.getInstance().getHeadIcon(this.displayName, (String)var2.get("uuid"));
    }

    @Override
    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.field_146125_m) {
            FontRenderer var4 = var1.fontRenderer;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = var2 >= this.field_146128_h && var3 >= this.field_146129_i && var2 < this.field_146128_h + this.field_146120_f && var3 < this.field_146129_i + this.field_146121_g;
            int var5 = this.getHoverState(this.field_146123_n);
            if (this.lIIIIlIIllIIlIIlIIIlIIllI) {
                Gui.drawRect(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i + this.field_146121_g, this.field_146123_n ? -15395563 : -14540254);
            }
            this.mouseDragged(var1, var2, var3);
            int var6 = -1;
            if (!this.enabled) {
                var6 = -986896;
            } else if (this.field_146123_n) {
                var6 = -3092272;
            }
            if (this.displayName.length() > 9) {
                float var10002 = this.field_146128_h + this.field_146120_f / 2 + 12;
                float var10003 = this.field_146129_i + this.field_146121_g / 2 - (this.lIIIIlIIllIIlIIlIIIlIIllI ? 4 : 3);
                CheatBreaker.getInstance().playRegular14px.drawCenteredString(this.displayName, var10002, var10003, var6);
            } else {
                float var10002 = this.field_146128_h + this.field_146120_f / 2 + 12;
                float var10003 = this.field_146129_i + this.field_146121_g / 2 - (this.lIIIIlIIllIIlIIlIIIlIIllI ? 5 : 4);
                CheatBreaker.getInstance().playRegular16px.drawCenteredString(this.displayName, var10002, var10003, var6);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderUtil.renderIcon(this.headIcon, 7.0f, (float)(this.field_146128_h + 10), (float)(this.field_146129_i + 5));
        }
    }

    /*
     * Exception decompiling
     */
    public boolean lIIIIlIIllIIlIIlIIIlIIllI() {
        if (((String) this.lIIIIIllllIIIIlIlIIIIlIlI.get("uuid")).equalsIgnoreCase(Minecraft.getMinecraft().getSession().getPlayerID())) {
            return false;
        } else {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
            Iterator var1 = CheatBreaker.getInstance().sessions.iterator();

            while (var1.hasNext()) {
                Session session = (Session) var1.next();
                if (session.func_148256_e().getId().toString().replaceAll("-", "").equalsIgnoreCase(((String) this.lIIIIIllllIIIIlIlIIIIlIlI.get("uuid")).replaceAll("-", ""))) {
                    Minecraft.getMinecraft().setSession(session);
                    return true;
                }
            }

            YggdrasilAuthenticationService var21 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, this.clientToken);
            YggdrasilUserAuthentication var22 = (YggdrasilUserAuthentication) var21.createUserAuthentication(Agent.MINECRAFT);
            var22.loadFromStorage(this.lIIIIIllllIIIIlIlIIIIlIlI);

            Session var3;
            try {
                var22.logIn();
                var3 = new Session(var22.getSelectedProfile().getName(), var22.getSelectedProfile().getId().toString(), var22.getAuthenticatedToken(), "mojang");
            } catch (AuthenticationException var18) {
                var18.printStackTrace();
                return false;
            }

            File var4 = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "launcher_accounts.json");
            if (var4.exists() && !var22.getAuthenticatedToken().equals(this.lIIIIIllllIIIIlIlIIIIlIlI.get("accessToken"))) {
                try {
                    FileReader var5 = new FileReader(var4);
                    JsonParser var6 = new JsonParser();
                    JsonElement var7 = var6.parse(var5);
                    Entry var8 = null;
                    Iterator var9 = var7.getAsJsonObject().entrySet().iterator();

                    label84:
                    while (true) {
                        Entry var10;
                        do {
                            if (!var9.hasNext()) {
                                if (var8 != null) {
                                    this.lIIIIIllllIIIIlIlIIIIlIlI.put("accessToken", var22.getAuthenticatedToken());
                                    ((JsonElement) var8.getValue()).getAsJsonObject().remove("accessToken");
                                    ((JsonElement) var8.getValue()).getAsJsonObject().addProperty("accessToken", var22.getAuthenticatedToken());
                                    System.out.println("Updated accessToken and logged user in.");
                                }

                                Gson var23 = (new GsonBuilder()).setPrettyPrinting().create();

                                try {
                                    DataOutputStream var24 = new DataOutputStream(new FileOutputStream(var4));
                                    var24.writeBytes(var23.toJson(var7).replace("\n", "\r\n"));
                                    var24.flush();
                                    var24.close();
                                    break label84;
                                } catch (Exception var17) {
                                    var17.printStackTrace();
                                    return false;
                                }
                            }

                            var10 = (Entry) var9.next();
                        } while (!((String) var10.getKey()).equalsIgnoreCase("authenticationDatabase"));

                        Iterator var11 = ((JsonElement) var10.getValue()).getAsJsonObject().entrySet().iterator();

                        label82:
                        while (var11.hasNext()) {
                            Entry var12 = (Entry) var11.next();
                            Iterator var13 = ((JsonElement) var12.getValue()).getAsJsonObject().entrySet().iterator();

                            while (true) {
                                Entry var14;
                                do {
                                    if (!var13.hasNext()) {
                                        continue label82;
                                    }

                                    var14 = (Entry) var13.next();
                                } while (!((String) var14.getKey()).equalsIgnoreCase("profiles"));

                                Iterator var15 = ((JsonElement) var14.getValue()).getAsJsonObject().entrySet().iterator();

                                while (var15.hasNext()) {
                                    Entry var16 = (Entry) var15.next();
                                    if (((String) var16.getKey()).replaceAll("-", "").equalsIgnoreCase(var3.getPlayerID().replaceAll("-", ""))) {
                                        var8 = var12;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception var19) {
                    var19.printStackTrace();
                    return false;
                }
            }

            CheatBreaker.getInstance().sessions.add(var3);
            Minecraft.getMinecraft().setSession(var3);
            return true;
        }
    }
}
