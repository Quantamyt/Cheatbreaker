package com.cheatbreaker.client.util.manager;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.FileUploadThread;
import net.minecraft.src.IFileUploadListener;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DebugInfoHandler {
    public static void ReportThatBug(String description) {
        try {
            String link = CheatBreaker.getInstance().getGlobalSettings().webapiDebugUpload;
            String info = DebugInfoHandler.getWebAPIInfo(description);
            byte[] intoToBytes = info.getBytes(StandardCharsets.US_ASCII);
            IFileUploadListener webUploadType = (string, arrby, throwable) -> DebugInfoHandler.sendMessage();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("OF-Version", Config.getVersion());
            FileUploadThread sendWebAPIPostThread = new FileUploadThread(link, hashMap, intoToBytes, webUploadType);
            sendWebAPIPostThread.setPriority(10);
            sendWebAPIPostThread.start();
            Thread.sleep(1000L);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /*
     * Could not resolve type clashes
     */
    private static String getWebAPIInfo(String string) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemDiv = maxMemory / 1024L / 1024L;
        long totalMemDiv = totalMemory / 1024L / 1024L;
        long freeMemDiv = freeMemory / 1024L / 1024L;
        StringBuilder builder = new StringBuilder();
        builder.append("OptiFine Version: ").append(Config.getVersion()).append("\n\n");
        builder.append("CheatBreaker client Version: ").append(CheatBreaker.getInstance().getGitCommitId() + "/" + CheatBreaker.getInstance().getGitBranch()).append("\n\n");
        builder.append("= [ Description ] =\n");
        builder.append(string + "\n\n");
        builder.append("= [ User Info ] =\n");
        if (Minecraft.getMinecraft().getSession() != null) {
            try {
                builder.append("User: " + Minecraft.getMinecraft().getSession().getUsername() + "\n");
                builder.append("UUID: " + Minecraft.getMinecraft().getSession().getPlayerID() + "\n\n");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        builder.append("= [ System Info ] =\n");
        builder.append("OpenGlVersion: " + Config.openGlVersion + "\n");
        builder.append("OpenGlRenderer: " + Config.openGlRenderer + "\n");
        builder.append("OpenGlVendor: " + Config.openGlVendor + "\n");
        builder.append("CpuCount: " + Config.getAvailableProcessors() + "\n");
        builder.append("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n");
        builder.append("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n");
        builder.append("Java VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n");
        builder.append("Memory: " + freeMemory + " bytes (" + freeMemDiv + " MB) / " + totalMemory + " bytes (" + totalMemDiv + " MB) up to " + maxMemory + " bytes (" + maxMemDiv + " MB)" + "\n");
        builder.append("JVM Flags: " + jvmFlags() + "\n\n");
        builder.append("= [ Module Settings ] =").append("\n");
        for (AbstractModule module : CheatBreaker.getInstance().getModuleManager().playerMods) {
            StringBuilder moduleSetting = new StringBuilder();
            for (Setting setting : module.getSettingsList()) {
                if (setting.getSettingName().equals("label")) continue;
                moduleSetting.append(setting.getSettingName()).append("=").append(setting.getValue()).append(module.getSettingsList().indexOf(setting) == module.getSettingsList().size() - 1 ? "" : ", ");
            }
            builder.append(module.getName() + ": [" + moduleSetting + "]\n");
        }
        builder.append("\n= [ Global Settings ] =").append("\n");
        StringBuilder globalSetting = new StringBuilder();
        List<Setting> settingsList = CheatBreaker.getInstance().getGlobalSettings().settingsList;
        for (Setting theSetting : settingsList) {
            if (theSetting.getSettingName().equals("label")) continue;
            globalSetting.append(theSetting.getSettingName()).append("=").append(theSetting.getValue()).append(settingsList.indexOf(theSetting) ==
                    settingsList.size() - 1 ? "" : ", ");
        }
        builder.append("Global: [" + globalSetting + "]\n\n");
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        builder.append("= [ Options.txt ] =\n");
        BufferedReader readFile;
        String varString;
        try {
            readFile = new BufferedReader(new FileReader(gameSettings.optionsFile));
            while ((varString = readFile.readLine()) != null) {
                if (varString.startsWith("stream") || varString.startsWith("key_") || varString.startsWith("soundCategory")) continue;
                builder.append(varString + "\n");
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        builder.append("\n= [ OptionsOF.txt ] =\n");
        try {
            readFile = new BufferedReader(new FileReader(gameSettings.optionsFileOF));
            while ((varString = readFile.readLine()) != null) {
                if (varString.startsWith("ofAnimated")) continue;
                builder.append(varString + "\n");
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return builder.toString();
    }

    private static void sendMessage() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Uploaded debug info."));
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().guiBlur.getValue()) {
            Minecraft.getMinecraft().entityRenderer.stopUseShader();
        }
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    private static String jvmFlags() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> list = runtimeMXBean.getInputArguments();
        int n = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : list) {
            if (!string.startsWith("-X")) continue;
            if (n++ > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string);
        }
        return String.format("%d total; %s", n, stringBuilder);
    }
}
