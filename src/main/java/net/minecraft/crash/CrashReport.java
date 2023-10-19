package net.minecraft.crash;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientCrashReport;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.CrashReporter;
import net.minecraft.src.HttpUtils;
import net.minecraft.src.Reflector;
import net.minecraft.util.ReportedException;
import net.minecraft.world.gen.layer.IntCache;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;

public class CrashReport {
    private static final Logger logger = LogManager.getLogger();

    /** Description of the crash report. */
    private final String description;

    /** The Throwable that is the "cause" for this crash and Crash Report. */
    private final Throwable cause;

    /** Category of crash */
    private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");

    /** Holds the keys and values of all crash report sections. */
    private final List crashReportSections = new ArrayList();

    /** File of crash report. */
    private File crashReportFile;
    private boolean field_85059_f = true;
    private StackTraceElement[] stacktrace = new StackTraceElement[0];
    private boolean reported = false;

    public CrashReport(String par1Str, Throwable par2Throwable) {
        this.description = par1Str;
        this.cause = par2Throwable;
        this.populateEnvironment();
    }

    /**
     * Populates this crash report with initial information about the running server and operating system / java
     * environment
     */
    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable("Minecraft Version", () -> "1.7.10");
        this.theReportCategory.addCrashSectionCallable("Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        this.theReportCategory.addCrashSectionCallable("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        this.theReportCategory.addCrashSectionCallable("Java VM Version", () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        this.theReportCategory.addCrashSectionCallable("Memory", () -> {
            Runtime var1 = Runtime.getRuntime();
            long var2 = var1.maxMemory();
            long var4 = var1.totalMemory();
            long var6 = var1.freeMemory();
            long var8 = var2 / 1024L / 1024L;
            long var10 = var4 / 1024L / 1024L;
            long var12 = var6 / 1024L / 1024L;
            return var6 + " bytes (" + var12 + " MB) / " + var4 + " bytes (" + var10 + " MB) up to " + var2 + " bytes (" + var8 + " MB)";
        });
        this.theReportCategory.addCrashSectionCallable("JVM Flags", () -> {
            RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
            List var2 = var1.getInputArguments();
            int var3 = 0;
            StringBuilder var4 = new StringBuilder();
            Iterator var5 = var2.iterator();

            while (var5.hasNext()) {
                String var6 = (String)var5.next();

                if (var6.startsWith("-X")) {
                    if (var3++ > 0) {
                        var4.append(" ");
                    }

                    var4.append(var6);
                }
            }

            return String.format("%d total; %s", new Object[] {Integer.valueOf(var3), var4.toString()});
        });
        this.theReportCategory.addCrashSectionCallable("AABB Pool Size", () -> {
            byte var1 = 0;
            int var2 = 56 * var1;
            int var3 = var2 / 1024 / 1024;
            byte var4 = 0;
            int var5 = 56 * var4;
            int var6 = var5 / 1024 / 1024;
            return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
        });
        this.theReportCategory.addCrashSectionCallable("IntCache", new Callable() {
            public String call() throws SecurityException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
                return IntCache.getCacheSizes();
            }
        });

        if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
            Object instance = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            Reflector.callString(instance, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] {this, this.theReportCategory});
        }
    }

    /**
     * Returns the description of the Crash Report.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the Throwable object that is the cause for the crash and Crash Report.
     */
    public Throwable getCrashCause() {
        return this.cause;
    }

    /**
     * Gets the various sections of the crash report into the given StringBuilder
     */
    public void getSectionsInStringBuilder(StringBuilder par1StringBuilder) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
            this.stacktrace = (StackTraceElement[])((StackTraceElement[])ArrayUtils.subarray(((CrashReportCategory)this.crashReportSections.get(0)).func_147152_a(), 0, 1));
        }

        if (this.stacktrace != null && this.stacktrace.length > 0) {
            par1StringBuilder.append("-- Head --\n");
            par1StringBuilder.append("Stacktrace:\n");
            StackTraceElement[] var6 = this.stacktrace;
            int var7 = var6.length;

            for (int var4 = 0; var4 < var7; ++var4) {
                StackTraceElement var5 = var6[var4];
                par1StringBuilder.append("\t").append("at ").append(var5.toString());
                par1StringBuilder.append("\n");
            }

            par1StringBuilder.append("\n");
        }

        Iterator var61 = this.crashReportSections.iterator();

        while (var61.hasNext()) {
            CrashReportCategory var71 = (CrashReportCategory)var61.next();
            var71.appendToStringBuilder(par1StringBuilder);
            par1StringBuilder.append("\n\n");
        }

        this.theReportCategory.appendToStringBuilder(par1StringBuilder);
    }

    /**
     * Gets the stack trace of the Throwable that caused this crash report, or if that fails, the cause .toString().
     */
    public String getCauseStackTraceOrString() {
        StringWriter var1 = null;
        PrintWriter var2 = null;
        Object var3 = this.cause;

        if (((Throwable)var3).getMessage() == null) {
            if (var3 instanceof NullPointerException) {
                var3 = new NullPointerException(this.description);
            } else if (var3 instanceof StackOverflowError) {
                var3 = new StackOverflowError(this.description);
            } else if (var3 instanceof OutOfMemoryError) {
                var3 = new OutOfMemoryError(this.description);
            }

            ((Throwable)var3).setStackTrace(this.cause.getStackTrace());
        }

        String var4 = ((Throwable)var3).toString();

        try {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            ((Throwable)var3).printStackTrace(var2);
            var4 = var1.toString();
        }
        finally {
            IOUtils.closeQuietly(var1);
            IOUtils.closeQuietly(var2);
        }

        return var4;
    }

    /**
     * Gets the complete report with headers, stack trace, and different sections as a string.
     */
    public String getCompleteReport() {
        if (!this.reported) {
            this.reported = true;
            CrashReporter.onCrashReport(this, this.theReportCategory);
        }

        StringBuilder var1 = new StringBuilder();
        var1.append("---- Minecraft Crash Report ----\n");
        var1.append("// ");
        var1.append(getWittyComment());
        var1.append("\n\n");
        var1.append("Time: ");
        var1.append((new SimpleDateFormat()).format(new Date()));
        var1.append("\n");
        var1.append("Description: ");
        var1.append(this.description);
        var1.append("\n\n");
        var1.append(this.getCauseStackTraceOrString());
        var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for (int var2 = 0; var2 < 87; ++var2) {
            var1.append("-");
        }

        var1.append("\n\n");
        this.getSectionsInStringBuilder(var1);
        return var1.toString();
    }

    /**
     * Gets the file this crash report is saved into.
     */
    public File getFile() {
        return this.crashReportFile;
    }

    /**
     * Saves this CrashReport to the given file and returns a value indicating whether we were successful at doing so.
     */
    public boolean saveToFile(File p_147149_1_) {
        if (this.crashReportFile != null) {
            return false;
        } else {
            if (p_147149_1_.getParentFile() != null) {
                p_147149_1_.getParentFile().mkdirs();
            }

            try {
                FileWriter var3 = new FileWriter(p_147149_1_);
                var3.write(this.getCompleteReport());
                var3.close();
                this.crashReportFile = p_147149_1_;
                return true;
            } catch (Throwable var31) {
                logger.error("Could not save crash report to " + p_147149_1_, var31);
                return false;
            }
        }
    }

    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }

    /**
     * Creates a CrashReportCategory
     */
    public CrashReportCategory makeCategory(String par1Str) {
        return this.makeCategoryDepth(par1Str, 1);
    }

    /**
     * Creates a CrashReportCategory for the given stack trace depth
     */
    public CrashReportCategory makeCategoryDepth(String par1Str, int par2) {
        CrashReportCategory var3 = new CrashReportCategory(this, par1Str);

        if (this.field_85059_f) {
            int var4 = var3.getPrunedStackTrace(par2);
            StackTraceElement[] var5 = this.cause.getStackTrace();
            StackTraceElement var6 = null;
            StackTraceElement var7 = null;
            int var8 = var5.length - var4;

            if (var8 < 0) {
                System.out.println("Negative index in crash report handler (" + var5.length + "/" + var4 + ")");
            }

            if (var5 != null && 0 <= var8 && var8 < var5.length) {
                var6 = var5[var8];

                if (var5.length + 1 - var4 < var5.length) {
                    var7 = var5[var5.length + 1 - var4];
                }
            }

            this.field_85059_f = var3.firstTwoElementsOfStackTraceMatch(var6, var7);

            if (var4 > 0 && !this.crashReportSections.isEmpty()) {
                CrashReportCategory var9 = (CrashReportCategory)this.crashReportSections.get(this.crashReportSections.size() - 1);
                var9.trimStackTraceEntriesFromBottom(var4);
            } else if (var5 != null && var5.length >= var4 && 0 <= var8 && var8 < var5.length) {
                this.stacktrace = new StackTraceElement[var8];
                System.arraycopy(var5, 0, this.stacktrace, 0, this.stacktrace.length);
            } else {
                this.field_85059_f = false;
            }
        }

        this.crashReportSections.add(var3);
        return var3;
    }

    /**
     * Gets a random witty comment for inclusion in this CrashReport
     */
    private static String getWittyComment() {
        String[] var0 = new String[] {"Who set us up the TNT?", "Everything\'s going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I\'m sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don\'t be sad. I\'ll do better next time, I promise!", "Don\'t be sad, have a hug! <3", "I just don\'t know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn\'t worry myself about that.", "I bet Cylons wouldn\'t have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I\'m Minecraft, and I\'m a crashaholic.", "Ooh. Shiny.", "This doesn\'t make any sense!", "Why is it breaking :(", "Don\'t do that.", "Ouch. That hurt :(", "You\'re mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};

        try {
            return var0[(int)(System.nanoTime() % (long)var0.length)];
        } catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }

    /**
     * Creates a crash report for the exception
     */
    public static CrashReport makeCrashReport(Throwable throwable, String par1Str) {
        try {
            StackTraceElement[] stackTrace;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(throwable.getClass().getSimpleName()).append(": ").append(throwable.getLocalizedMessage());
            for (StackTraceElement element : stackTrace = throwable.getStackTrace()) {
                stringBuilder.append("\n").append("\t").append("at ").append(element);
            }
            UUID uuid = UUID.randomUUID();
            StringSelection reportID = new StringSelection("CB-" + uuid.toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(reportID, null);
            sendCrashReport(uuid.toString(), stringBuilder.toString());
            if (throwable.getClass().getSimpleName().equals("OutOfMemoryError")) {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Your client has ran out of memory." +
                        "\nYou can increase memory allocation in the launcher. " +
                        "\n\nPlease use the following code (also copied to your clipboard) when submitting a bug report: " +
                        "\n\nCB-" + uuid, "Out of Memory", 2)).start();
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(null,
                        "Your client has crashed. " +
                                "\n\nPlease use the following code (also copied to your clipboard) when submitting a bug report: " +
                                "\n\nCB-" + uuid, "Something went wrong", 2)).start();
            }
            Thread.sleep(4000L);
        } catch (Exception stackTrace) {
            stackTrace.printStackTrace();
            // empty catch block
        }

        CrashReport var2;

        if (throwable instanceof ReportedException) {
            var2 = ((ReportedException)throwable).getCrashReport();
        } else {
            var2 = new CrashReport(par1Str, throwable);
        }

        return var2;
    }

    private static void sendCrashReport(String crashId, String stackTrace) throws IOException {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("OptiFine Version: ").append(Config.getVersion()).append("\n\n");
        descriptionBuilder.append("CheatBreaker client Version: ").append(CheatBreaker.getInstance().getGitCommitId() + "/" +
                CheatBreaker.getInstance().getGitBranch()).append("\n\n");
        descriptionBuilder.append("= [ User Info ] =\n");
        if (Minecraft.getMinecraft().getSession() != null) {
            try {
                descriptionBuilder.append("User: " + Minecraft.getMinecraft().getSession().getUsername() + "\n");
                descriptionBuilder.append("UUID: " + Minecraft.getMinecraft().getSession().getPlayerID() + "\n\n");

            } catch (Exception var20) {
                var20.printStackTrace();
            }
        }
        descriptionBuilder.append("= [ Crash Info ] =\n");
        descriptionBuilder.append("Crash ID: " + crashId);
        descriptionBuilder.append("\nStackTrace: " + stackTrace + "\n\n");
        BufferedReader var28;
        String var29;
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemDiv = maxMemory / 1024L / 1024L;
        long totalMemDiv = totalMemory / 1024L / 1024L;
        long freeMemDiv = freeMemory / 1024L / 1024L;
        descriptionBuilder.append("= [ System Info ] =\n");
        descriptionBuilder.append("OpenGlVersion: " + Config.openGlVersion + "\n");
        descriptionBuilder.append("OpenGlRenderer: " + Config.openGlRenderer + "\n");
        descriptionBuilder.append("OpenGlVendor: " + Config.openGlVendor + "\n");
        descriptionBuilder.append("CpuCount: " +  Config.getAvailableProcessors() + "\n");
        descriptionBuilder.append("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n");
        descriptionBuilder.append("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n");
        descriptionBuilder.append("Java VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n");
        descriptionBuilder.append("Memory: " + freeMemory + " bytes (" + freeMemDiv + " MB) / " + totalMemory + " bytes (" + totalMemDiv + " MB) up to " + maxMemory + " bytes (" + maxMemDiv + " MB)\n");
        descriptionBuilder.append("JVM Flags: " + jvmargs() + "\n\n");
        descriptionBuilder.append("= [ Module Settings ] =").append("\n");
        for (AbstractModule var16 : CheatBreaker.getInstance().getModuleManager().playerMods) {
            StringBuilder var17 = new StringBuilder();
            for (Setting var19 : var16.getSettingsList()) {
                if (var19.getSettingName().equals("label")) continue;
                var17.append(var19.getSettingName()).append("=").append(var19.getValue()).append(var16.getSettingsList().indexOf(var19) == var16.getSettingsList().size() - 1 ? "" : ", ");
            }
            descriptionBuilder.append(var16.getName() + ": [" + var17.toString() + "]\n");
        }
        descriptionBuilder.append("\n= [ Global Settings ] =").append("\n");
        String var23 = "";
        List<Setting> var24 =CheatBreaker.getInstance().getGlobalSettings().settingsList;
        for (Setting var27 : var24) {
            if (var27.getSettingName().equals("label")) continue;
            var23 =  var23 + var27.getSettingName() + "=" + var27.getValue() + (var24.indexOf(var27) == var24.size() - 1 ? "" : ", ");
        }
        descriptionBuilder.append("Global: [" + var23 + "]\n\n");
        GameSettings var26 = Minecraft.getMinecraft().gameSettings;
        descriptionBuilder.append("= [ Options.txt ] =\n");
        try {
            var28 = new BufferedReader(new FileReader(var26.optionsFile));
            while ((var29 = var28.readLine()) != null) {
                if (var29.startsWith("stream") || var29.startsWith("key_") || var29.startsWith("soundCategory")) continue;
                descriptionBuilder.append(var29 + "\n");
            }
        } catch (IOException var22) {
            var22.printStackTrace();
        }
        descriptionBuilder.append("\n= [ OptionsOF.txt ] =\n");
        try {
            var28 = new BufferedReader(new FileReader(var26.optionsFileOF));
            while ((var29 = var28.readLine()) != null) {
                if (var29.startsWith("ofAnimated")) continue;
                descriptionBuilder.append(var29 + "\n");
            }
        } catch (IOException var21) {
            var21.printStackTrace();
        }

        byte[] var4 = null;
        try {
            var4 = descriptionBuilder.toString().getBytes("ASCII");
        } catch (Exception exception) {
            // empty catch block
        }

        ImmutableMap<Object, Object> var5 = ImmutableMap.builder()
                .put("branch", CheatBreaker.getInstance().getGitBranch())
                .put("buildType", CheatBreaker.getInstance().getGitBuildVersion())
                .put("crashID", crashId)
                .put("version", Config.MC_VERSION)
                .put("gitCommit", CheatBreaker.getInstance().getGitCommitIdAbbrev())
                .put("osInfo", System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"))
                .put("username", Minecraft.getMinecraft().getSession().getUsername())
                .put("uuid", Minecraft.getMinecraft().getSession().getPlayerID())
                .build();
        HttpUtils.post(CheatBreaker.getInstance().getGlobalSettings().webapiCrashReportUpload, var5, var4);
        if (CheatBreaker.getInstance().getWSNetHandler().isOpen()) {
            WSPacketClientCrashReport crashReport = new WSPacketClientCrashReport(crashId, CheatBreaker.getInstance().getGitCommitId(), System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"), "Memory: " + freeMemory + " bytes (" + freeMemDiv + " MB) / " + totalMemory + " bytes (" + totalMemDiv + " MB) up to " + maxMemory + " bytes (" + maxMemDiv + " MB)", descriptionBuilder.toString());
            CheatBreaker.getInstance().getWSNetHandler().sendPacket(crashReport);
        }
    }

    private static String jvmargs() {
        RuntimeMXBean var0 = ManagementFactory.getRuntimeMXBean();
        List<String> var1 = var0.getInputArguments();
        int var2 = 0;
        StringBuilder var3 = new StringBuilder();
        for (String var5 : var1) {
            if (!var5.startsWith("-X")) continue;
            if (var2++ > 0) {
                var3.append(" ");
            }
            var3.append(var5);
        }
        return String.format("%d total; %s", var2, var3.toString());
    }
}
