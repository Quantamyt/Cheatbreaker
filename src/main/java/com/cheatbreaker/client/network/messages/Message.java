package com.cheatbreaker.client.network.messages;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.Microphone;
import com.cheatbreaker.client.network.agent.AgentBooleanReference;
import com.cheatbreaker.client.network.agent.AgentByteArrayReference;
import com.cheatbreaker.client.network.agent.AgentResources;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.client.PacketClientVoice;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This was used by CheatBreaker in their network package.
 * <p>
 * It was excluded from the obfuscator for reason, probably to prevent
 * any problems with any custom networking methods staying functional
 * after deployment of new updates. Copyright CheatBreaker 2018.
 */
public class Message {
    private final String action;
    public static byte[] b;
    public static byte[] a;

    public Message(String string) {
        this.action = string;
    }

    public String getAction() {
        return this.action;
    }

    public static native void a(String[] var0);

    public static native void b(String var0);

    public static native void c(String var0, String var1, String var2);

    public static void d() {
        String string = "";
        String string2 = "";
        String string3 = "";
        String string4 = "";
        String string5 = "";
        String string6 = "";
        String string7 = "";
        for (Method accessibleObject : Minecraft.class.getMethods()) {
            if (accessibleObject.getReturnType() == Minecraft.class) {
                string = accessibleObject.getName() + ":()L" + Minecraft.class.getCanonicalName().replaceAll("\\.", "/") + ";";
                continue;
            }
            if (accessibleObject.getReturnType() != NetHandlerPlayClient.class) continue;
            string2 = accessibleObject.getName() + ":()L" + NetHandlerPlayClient.class.getCanonicalName().replaceAll("\\.", "/") + ";";
        }
        for (Method accessibleObject : NetHandlerPlayClient.class.getMethods()) {
            if (accessibleObject.getParameterTypes().length != 1 || accessibleObject.getParameterTypes()[0] != Packet.class)
                continue;
            string3 = accessibleObject.getName() + ":(L" + Packet.class.getCanonicalName().replaceAll("\\.", "/") + ";)V";
        }
        for (Field accessibleObject : Entity.class.getFields()) {
            if (accessibleObject.getType() != Float.TYPE) continue;
            string4 = accessibleObject.getName() + ":F";
            break;
        }
        for (Method accessibleObject : Main.class.getMethods()) {
            if (accessibleObject.getParameterTypes().length != 1 || accessibleObject.getParameterTypes()[0] != String[].class)
                continue;
            string5 = accessibleObject.getName() + ":([Ljava/lang/String;)V";
        }
        for (Method accessibleObject : AgentResources.class.getMethods()) {
            if (accessibleObject.isAnnotationPresent(AgentByteArrayReference.class)) {
                string6 = accessibleObject.getName() + ":(Ljava/lang/String;)[B";
            }
            if (!accessibleObject.isAnnotationPresent(AgentBooleanReference.class)) continue;
            string7 = accessibleObject.getName() + ":(Ljava/lang/String;)Z";
        }
        Message.a(new String[]{Message.r(C17PacketCustomPayload.class.getName()),
                Message.r(S3FPacketCustomPayload.class.getName()), Message.r(Minecraft.class.getName()),
                Message.r(NetHandlerPlayClient.class.getName()), Message.r(Entity.class.getName()),
                Message.r(AgentResources.class.getName()), Message.r(Main.class.getName()), string, string2, string3, string4,
                string5, string6, string7});
    }

    private static String r(String string) {
        return string.replaceAll("\\.", "/");
    }

    public static native void e(boolean var0);

    public static native void f(String var0, byte[] var1);

    public static void j(byte[] arrby) {
        CheatBreaker.getInstance().getCBNetHandler().sendPacket(new PacketClientVoice(arrby));
    }

    public static void r(byte[] arrby) {
        b = arrby;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null && mc.getNetHandler().getNetworkManager().getChannel().isOpen() && CheatBreaker.getInstance().getCBNetHandler().isRegisteredBinary()) {
//            mc.getNetHandler().getNetworkManager().getChannel().writeAndFlush(new C17PacketCustomPayload(CheatBreaker.getInstance().getPluginBinaryChannel(), arrby));
        }
    }

    public static void g(String[] micDescription, String[] micName) {
        for (int i = 0; i < micDescription.length; ++i) {
            CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Added mic option: " + micName[i]);
            CheatBreaker.getInstance().getAudioManager().getMicrophones().add(new Microphone(micDescription[i], micName[i]));
        }
    }

    /**
     * Sends a prefix in chat for debugging purposes.
     */
    public static void z(boolean incoming, CBPacket packet) {
//        ChatComponentText chatDebugPrefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET);
        ChatComponentText debugMessage = new ChatComponentText(EnumChatFormatting.GRAY + (incoming ? "Received: " : "Sent: ") + EnumChatFormatting.WHITE + packet.getClass().getSimpleName());
        debugMessage.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(new Gson().toJson(packet))));
//        chatDebugPrefix.appendSibling(debugMessage);
        debugMessage.setBranded(true);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(debugMessage);
    }

    public static native void h(String var0);

    public static native byte[] i();

    public static native void k();

    public static native void l(float var0);

    public static native void m(float var0);

    public static void n() {
        Minecraft.getMinecraft().gameSettings.saveOptions();
        CheatBreaker.getInstance().configManager.updateProfile();
        CheatBreaker.getInstance().getWsNetHandler().close();
        System.exit(0);
    }

    public static void o(String string) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.ingameGUI != null && mc.ingameGUI.getChatGUI() != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
        }
    }

    public static native void s(int var0, boolean var1);
}
