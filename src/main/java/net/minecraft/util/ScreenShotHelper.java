package net.minecraft.util;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.FrameBuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreenShotHelper {
    private static final Logger logger = LogManager.getLogger();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;

    public static IChatComponent lIIIIlIIllIIlIIlIIIlIIllI(File file, int n, int n2, FrameBuffer frameBuffer) {
        return ScreenShotHelper.lIIIIlIIllIIlIIlIIIlIIllI(file, null, n, n2, frameBuffer);
    }

    public static void saveScreenshot(File file, int n, int n2, FrameBuffer frameBuffer) {
        try {
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().shutterSound.getValue()) {
                CheatBreaker.getInstance().getAudioManager().playSound("shutter");
            }
            File file2 = new File(file, "screenshots");
            if (!file2.exists()) {
                file2.mkdir();
            }
            if (OpenGlHelper.isFramebufferEnabled()) {
                n = frameBuffer.framebufferTextureWidth;
                n2 = frameBuffer.framebufferTextureHeight;
            }
            int n3 = n * n2;
            if (pixelBuffer == null || pixelBuffer.capacity() < n3) {
                pixelBuffer = BufferUtils.createIntBuffer(n3);
                pixelValues = new int[n3];
            }
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            pixelBuffer.clear();
            if (OpenGlHelper.isFramebufferEnabled()) {
                GL11.glBindTexture(3553, frameBuffer.framebufferTexture);
                GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
            } else {
                GL11.glReadPixels(0, 0, n, n2, 32993, 33639, pixelBuffer);
            }
            pixelBuffer.get(pixelValues);
            int n4 = n;
            int n5 = n2;
            new Thread(() -> {
                TextureUtil.func_147953_a(pixelValues, n4, n5);
                BufferedImage bufferedImage = null;
                if (OpenGlHelper.isFramebufferEnabled()) {
                    int n33;
                    bufferedImage = new BufferedImage(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight, 1);
                    for (int i = n33 = frameBuffer.framebufferTextureHeight - frameBuffer.framebufferHeight; i < frameBuffer.framebufferTextureHeight; ++i) {
                        for (int j = 0; j < frameBuffer.framebufferWidth; ++j) {
                            bufferedImage.setRGB(j, i - n33, pixelValues[i * frameBuffer.framebufferTextureWidth + j]);
                        }
                    }
                } else {
                    bufferedImage = new BufferedImage(n4, n5, 1);
                    bufferedImage.setRGB(0, 0, n4, n5, pixelValues, 0, n4);
                }
                File file22 = ScreenShotHelper.getTimestampedPNGFileForDirectory(file2);
                try {
                    ImageIO.write(bufferedImage, "png", file22);
                    ChatComponentText open = new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + ((Boolean) CheatBreaker.getInstance().getGlobalSettings().compactOptions.getValue() ?  " [OPN]" : " [Open]"));
                    open.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file22.getCanonicalPath()));
                    open.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(file22.getName())));
                    ChatComponentText copy = new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + ((Boolean) CheatBreaker.getInstance().getGlobalSettings().compactOptions.getValue() ? " [CPY]" : " [Copy]"));
                    copy.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.COPY_SCREENSHOT, file22.getName()));
                    copy.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Copy the screenshot")));
                    ChatComponentText upload = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + ((Boolean) CheatBreaker.getInstance().getGlobalSettings().compactOptions.getValue() ?  " [UPL]" : " [Upload]"));
                    upload.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.UPLOAD_SCREENSHOT, file22.getName()));
                    upload.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Upload to " + EnumChatFormatting.GREEN + "imgur.com & open")));
                    ChatComponentText savedMessage = new ChatComponentText("Saved" + ((Boolean) CheatBreaker.getInstance().getGlobalSettings().copyAutomatically.getValue() ? " and copied " : " ")  + "screenshot");
                    savedMessage.getChatStyle().setUnderlined(true);
                    if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().openOption.getValue()) savedMessage.appendSibling(open);
                    if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().copyOption.getValue()) savedMessage.appendSibling(copy);
                    if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().uploadOption.getValue()) savedMessage.appendSibling(upload);
                    if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().copyAutomatically.getValue()) IllIlIIIllllIIllIIlllIIlI(file22.getName());
                        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().sendScreenshotMessage.getValue()) {
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(savedMessage);
                    }

                } catch (IOException iOException) {
                    logger.warn("Couldn't save screenshot");
                }
            }).start();
        } catch (Exception exception) {
            logger.warn("Couldn't save screenshot", exception);
        }
    }

    public static void IllIlIIIllllIIllIIlllIIlI(String var1) {
        File var2 = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "screenshots" + File.separator + var1);
        List listOfFiles = new ArrayList();
        listOfFiles.add(var2);

        CheatBreaker.getInstance().getModuleManager().notificationsMod.send("Info", "&9Copied &fscreenshot.", 2000L);

        IllIlIIIllllIIIllIIlllIIll ft = new IllIlIIIllllIIIllIIlllIIll(listOfFiles);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ft, (clipboard, contents) -> System.out.println("Lost ownership"));
    }

    public static class IllIlIIIllllIIIllIIlllIIll implements Transferable {

        private final List n;

        public IllIlIIIllllIIIllIIlllIIll(List n) {
            this.n = n;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.javaFileListFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            return n;
        }
    }

    public static IChatComponent lIIIIlIIllIIlIIlIIIlIIllI(File file, String string, int n, int n2, FrameBuffer frameBuffer) {
        try {
            File file2 = new File(file, "screenshots");
            if (!file2.exists()) {
                file2.mkdir();
            }
            if (OpenGlHelper.isFramebufferEnabled()) {
                n = frameBuffer.framebufferTextureWidth;
                n2 = frameBuffer.framebufferTextureHeight;
            }
            int n3 = n * n2;
            if (pixelBuffer == null || pixelBuffer.capacity() < n3) {
                pixelBuffer = BufferUtils.createIntBuffer(n3);
                pixelValues = new int[n3];
            }
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            pixelBuffer.clear();
            if (OpenGlHelper.isFramebufferEnabled()) {
                GL11.glBindTexture(3553, frameBuffer.framebufferTexture);
                GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
            } else {
                GL11.glReadPixels(0, 0, n, n2, 32993, 33639, pixelBuffer);
            }
            pixelBuffer.get(pixelValues);
            TextureUtil.func_147953_a(pixelValues, n, n2);
            BufferedImage bufferedImage = null;
            if (OpenGlHelper.isFramebufferEnabled()) {
                int n4;
                bufferedImage = new BufferedImage(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight, 1);
                for (int i = n4 = frameBuffer.framebufferTextureHeight - frameBuffer.framebufferHeight; i < frameBuffer.framebufferTextureHeight; ++i) {
                    for (int j = 0; j < frameBuffer.framebufferWidth; ++j) {
                        bufferedImage.setRGB(j, i - n4, pixelValues[i * frameBuffer.framebufferTextureWidth + j]);
                    }
                }
            } else {
                bufferedImage = new BufferedImage(n, n2, 1);
                bufferedImage.setRGB(0, 0, n, n2, pixelValues, 0, n);
            }
            File file3 = string == null ? ScreenShotHelper.getTimestampedPNGFileForDirectory(file2) : new File(file2, string);
            ImageIO.write(bufferedImage, "png", file3);
            ChatComponentText chatComponentText = new ChatComponentText(file3.getName());
            chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file3.getAbsolutePath()));
            chatComponentText.getChatStyle().setUnderlined(true);
            return new ChatComponentTranslation("screenshot.success", chatComponentText);
        } catch (Exception exception) {
            logger.warn("Couldn't save screenshot", exception);
            return new ChatComponentTranslation("screenshot.failure", exception.getMessage());
        }
    }

    private static File getTimestampedPNGFileForDirectory(File file) {
        String string = dateFormat.format(new Date());
        int n = 1;
        File file2;
        while ((file2 = new File(file, string + (n == 1 ? "" : "_" + n) + ".png")).exists()) {
            ++n;
        }
        return file2;
    }
}
