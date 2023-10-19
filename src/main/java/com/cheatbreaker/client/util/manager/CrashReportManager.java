package com.cheatbreaker.client.util.manager;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.optifine.http.HttpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CrashReportManager {
    public static void handleCrash(StackTraceElement[] arrstackTraceElement) {
        try {
            String uploadUrl = CheatBreaker.getInstance().getGlobalSettings().webapiCrashReportUpload;
            String postData = CrashReportManager.getPOSTData(arrstackTraceElement);
            byte[] postDataBytes = postData.getBytes(StandardCharsets.US_ASCII);

            String string3 = HttpUtils.post(uploadUrl, new HashMap<>(), postDataBytes);
            System.out.println("[Crash Handler] Sent Post");

            if (string3 != null) {
                StringSelection stringSelection = new StringSelection(string3);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                new Thread(() -> JOptionPane.showMessageDialog(null, "Your client has crashed. \n\nPlease use the following code (also copied to your clipboard) when submitting a bug report: " + string3, "Something went wrong (Closing in 5 seconds)", 2)).start();
            }
            Thread.sleep(6000L);
        } catch (Exception exception) {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }


    private static String getPOSTData(StackTraceElement[] arrstackTraceElement) {
        BufferedReader bufferedReader;
        StringBuilder builder = new StringBuilder();
        builder.append("CheatBreaker Client Version: ").append(CheatBreaker.getInstance().getGitCommitId()).append("/").append(CheatBreaker.getInstance().getGitBranch()).append("\n\n");
        builder.append("\n");
        builder.append("\n\n");
        builder.append("= [ StackTrace ] =\n");
        for (StackTraceElement stackTraceElement : arrstackTraceElement) {
            builder.append("\n").append(stackTraceElement);
        }
        builder.append("\n\n");
        GameSettings object = Minecraft.getMinecraft().gameSettings;
        if (Minecraft.getMinecraft().getSession() != null) {
            builder.append("= [ User Info ] =\n");
            builder.append("Username: ").append(Minecraft.getMinecraft().getSession().getUsername()).append("\n");
            builder.append("UUID: ").append(Minecraft.getMinecraft().getSession().getPlayerID()).append("\n\n");
        }
        builder.append("= [ Options.txt ] =\n");
        try {
            String string;
            bufferedReader = new BufferedReader(new FileReader(object.optionsFile));
            while ((string = bufferedReader.readLine()) != null) {
                if (string.startsWith("stream") || string.startsWith("key_") || string.startsWith("soundCategory"))
                    continue;
                builder.append(string).append("\n");
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        builder.append("\n= [ OptionsOF.txt ] =\n");
        try {
            String string;
            bufferedReader = new BufferedReader(new FileReader(object.optionsFileOF));
            while ((string = bufferedReader.readLine()) != null) {
                if (string.startsWith("ofAnimated")) continue;
                builder.append(string + "\n");
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return builder.toString();
    }

    private static String makeSummary(CrashReport crashReport) {
        return "Unknown";
    }


    public static void extendCrashReport(CrashReportCategory crashReportCategory) {
        StringBuilder string = new StringBuilder("[");
        for (Object object : CheatBreaker.getInstance().getGlobalSettings().settingsList) {
            if (((Setting) object).getSettingName().equals("label")) continue;
            string.append(((Setting) object).getSettingName()).append("=").append(((Setting) object).getValue()).append(", ");
        }

        {
            crashReportCategory.addCrashSection("Global Settings", string + "]");
            for (Object object : CheatBreaker.getInstance().getModuleManager().playerMods) {
                String string2 = "[";
                for (Setting setting : ((AbstractModule) object).getSettingsList()) {
                    if (setting.getSettingName().equals("label")) continue;
                    string2 = string2 + setting.getSettingName() + "=" + setting.getValue() + (((AbstractModule) object).getSettingsList().indexOf(setting) == ((AbstractModule) object).getSettingsList().size() - 1 ? "" : ", ");
                }
                crashReportCategory.addCrashSection(((AbstractModule) object).getName() + " options", string2 + "]");
            }
        }

        crashReportCategory.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
        crashReportCategory.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
        crashReportCategory.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
        crashReportCategory.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
    }
}
