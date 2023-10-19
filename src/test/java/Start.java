import net.minecraft.client.main.Main;

import java.io.File;
import java.util.Arrays;

public class Start {
    public static void main(final String[] args) {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform()) {
            case LINUX:
                workingDirectory = new File(userHome, ".minecraft/");
                break;
            case WINDOWS:
                String applicationData = System.getenv("APPDATA");
                String folder = applicationData != null ? applicationData : userHome;
                workingDirectory = new File(folder, ".minecraft/");
                break;
            case MACOS:
                workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                break;
            default:
                workingDirectory = new File(userHome, "minecraft/");
        }

        Main.main(concat(new String[] {
                "--username", "Aplosh",
                "--uuid", "a4434163-f43e-4b17-98f2-3b08ee4f5513",
                "--accessToken", "0",
                "--version", "CheatBreaker",
                "--assetIndex", "1.8",
                "--userProperties", "{}",
                "--gameDir", new File(workingDirectory, ".").getAbsolutePath(),
                "--assetsDir", new File(workingDirectory, "assets/").getAbsolutePath()
        }, args));
    }

    public static <T> T[] concat(final T[] first, final T[] second) {
        final T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private static OS getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? OS.WINDOWS :
                (os.contains("mac") ? OS.MACOS :
                        (os.contains("solaris") || os.contains("sunos") ? OS.SOLARIS :
                                (os.contains("linux") || os.contains("unix") ? OS.LINUX : OS.UNKNOWN)));
    }

    public enum OS {
        LINUX,
        MACOS,
        SOLARIS,
        UNKNOWN,
        WINDOWS
    }
}
