package com.cheatbreaker.client;

import com.cheatbreaker.client.audio.AudioManager;
import com.cheatbreaker.client.audio.music.DashManager;
import com.cheatbreaker.client.config.ConfigManager;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.cosmetic.CosmeticManager;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.InitializationEvent;
import com.cheatbreaker.client.event.impl.network.ConnectEvent;
import com.cheatbreaker.client.event.impl.network.DisconnectEvent;
import com.cheatbreaker.client.event.impl.network.PluginMessageEvent;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.module.command.ModuleCommandManager;
import com.cheatbreaker.client.network.plugin.CBNetHandler;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientKeySync;
import com.cheatbreaker.client.ui.UIManager;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import com.cheatbreaker.client.util.DataUtil;
import com.cheatbreaker.client.util.discord.DiscordRPCHandler;
import com.cheatbreaker.client.util.friend.FriendsManager;
import com.cheatbreaker.client.util.friend.data.Friend;
import com.cheatbreaker.client.util.json.JSONReader;
import com.cheatbreaker.client.util.manager.BranchManager;
import com.cheatbreaker.client.util.render.title.TitleManager;
import com.cheatbreaker.client.util.render.worldborder.WorldBorderManager;
import com.cheatbreaker.client.util.sessionserver.SessionServer;
import com.cheatbreaker.client.util.thread.ServerStatusThread;
import com.google.common.base.Stopwatch;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
public class CheatBreaker {
    @Getter private static CheatBreaker instance;
    private final Minecraft mc = Minecraft.getMinecraft();

    public static byte[] processListBytes = new byte[]{ 86, 79, 84, 69, 32, 84, 82, 85, 77, 80, 32, 50, 48, 50, 48, 33 };
    public static byte[] changelogAuthorizationBytes = new byte[]{ 104, 116, 116, 112, 115, 58, 47, 47, 99, 104, 97, 110, 103, 101, 108, 111, 103, 46, 110, 111, 120, 105, 117, 97, 109, 46, 103, 113 };

    public static AudioFormat universalAudioFormat = new AudioFormat(8000.0f, 16, 1, true, true);

    @Setter private Friend.Status playerStatus = Friend.Status.ONLINE;

    public GuiScreen lastScreen = null;

    private IPCClient rpcClient;

    private final Map<String, ResourceLocation> playerAvatarCache = new HashMap<>();

    public List<Session> playerSessions = new ArrayList<>();
    public List<SessionServer> sessionServers = new ArrayList<>();
    private final List<String> consoleLines = new ArrayList<>();

    private String currentServer;
    private String gitCommitIdAbbrev = "?";
    private String gitBranch = "?";
    private String gitCommitId = "?";
    private String gitBuildVersion = "?";
    private final String cheatBreakerPluginMessageChannel = "CB-Client";
    private final String lunarPluginMessageChannel = "Lunar-Client";
    private final String latestLunarPluginMessageChannel = "lunarclient:pm";
    private final String pluginBinaryChannel = "CB-Binary";
    public String loggerPrefix = "[CB] ";

    public final Logger logger = LogManager.getLogger("CheatBreaker");

    private final long startTime;

    @Setter private boolean consoleAccess;
    @Setter private boolean acceptingFriendRequests = true;
    @Getter private final boolean confidentialBuild = true;
    public boolean hideNameTags = false;

    @Setter private WSNetHandler wsNetHandler;
    private final ModuleManager moduleManager;
    public ModuleCommandManager moduleCommandManager;
    private final FriendsManager friendsManager;
    private final DashManager dashManager;
    private final TitleManager titleManager;
    private final WorldBorderManager worldBorderManager;
    private final ProfileHandler profileHandler;
    private final UIManager uiManager;
    private final CosmeticManager cosmeticManager;
    private final AudioManager audioManager;
    private final GlobalSettings globalSettings;
    private final CBNetHandler cBNetHandler;
    private final EventBus eventBus;
    private final DiscordRPCHandler discordRPCHandler;
    public ConfigManager configManager;
    public BranchManager branchManager;

    public CBFontRenderer playBold22px;
    public CBFontRenderer playRegular22px;
    public CBFontRenderer ubuntuMedium16px;
    public CBFontRenderer ubuntuMedium14px;
    public CBFontRenderer playBold18px;
    public CBFontRenderer robotoRegular24px;
    public CBFontRenderer playRegular18px;
    public CBFontRenderer playRegular14px;
    public CBFontRenderer playRegular16px;
    public CBFontRenderer robotoRegular13px;
    public CBFontRenderer robotoBold14px;
    public CBFontRenderer playRegular12px;

    private final ResourceLocation fontPlayRegular = new ResourceLocation("client/font/Play-Regular.ttf");
    private final ResourceLocation fontPlayBold = new ResourceLocation("client/font/Play-Bold.ttf");
    private final ResourceLocation fontRobotoRegular = new ResourceLocation("client/font/Roboto-Regular.ttf");
    private final ResourceLocation fontRobotoBold = new ResourceLocation("client/font/Roboto-Bold.ttf");
    private final ResourceLocation fontUbuntuMedium = new ResourceLocation("client/font/Ubuntu-M.ttf");
    public static ResourceLocation packIconTexture;

    /**
     * Initial CheatBreaker Client Setup
     */
    public CheatBreaker() throws IOException {
        this.startTime = System.currentTimeMillis();
        instance = this;

        this.logger.info(this.loggerPrefix + "Starting CheatBreaker setup");

        Stopwatch setupStopwatch = Stopwatch.createStarted();
        this.mc.cbLoadingScreen.updatePhase("CheatBreaker Setup");
        this.configManager = new ConfigManager();
        this.configManager.createDefaultConfigurationPresets();

        this.logger.info(setupStopwatch.elapsed(TimeUnit.SECONDS) + "s : Finished setup");

        Stopwatch settingStopwatch = Stopwatch.createStarted();
        this.mc.cbLoadingScreen.updatePhase("Settings");
        this.globalSettings = new GlobalSettings();
        this.branchManager = new BranchManager();

        this.logger.info(settingStopwatch.elapsed(TimeUnit.SECONDS) + "s : Finished loading settings");

        Stopwatch managerStopwatch = Stopwatch.createStarted();
        this.mc.cbLoadingScreen.updatePhase("EventBus");
        this.eventBus = new EventBus();

        this.mc.cbLoadingScreen.updatePhase("Mods");
        this.moduleManager = new ModuleManager(this.eventBus);
        this.moduleCommandManager = new ModuleCommandManager();

        this.mc.cbLoadingScreen.updatePhase("Network Manager");
        this.cBNetHandler = new CBNetHandler();

        this.discordRPCHandler = new DiscordRPCHandler();

        this.mc.cbLoadingScreen.updatePhase("Emotes");
        this.cosmeticManager = new CosmeticManager();

        this.logger.info(managerStopwatch.elapsed(TimeUnit.SECONDS) + "s : Finished loading emotes, network, mods, and events");

        Stopwatch miscStopwatch = Stopwatch.createStarted();
        this.mc.cbLoadingScreen.updatePhase("Radio");
        this.audioManager = new AudioManager();
        this.dashManager = new DashManager();

        this.mc.cbLoadingScreen.updatePhase("Friends");
        this.friendsManager = new FriendsManager();

        this.mc.cbLoadingScreen.updatePhase("Titles");
        this.titleManager = new TitleManager();

        this.logger.info(miscStopwatch.elapsed(TimeUnit.SECONDS) + "s : Finished loading radio, friends, and titles");

        //
/*        try {
            ChangelogMenu.changelogObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(JSONReader.readAsBrowser(new URL("https://noxiuam.gq/cheatbreaker/changelog"), true), StandardCharsets.UTF_8))).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            CheatBreaker.getInstance().getLogger().error(CheatBreaker.getInstance().getLoggerPrefix() + "Failed to load changelog.");
        }*/

        this.mc.cbLoadingScreen.updatePhase("World Border");
        this.worldBorderManager = new WorldBorderManager();
        this.profileHandler = new ProfileHandler();
        this.uiManager = new UIManager();

        this.mc.cbLoadingScreen.updatePhase("Network Events");
        this.eventBus.addEvent(DisconnectEvent.class, this.cBNetHandler::onDisconnect);
        this.eventBus.addEvent(ConnectEvent.class, this.cBNetHandler::onConnect);
        this.eventBus.addEvent(PluginMessageEvent.class, this.cBNetHandler::onPluginMessage);
        this.logger.info(this.loggerPrefix + "Registered network events");

        this.eventBus.addEvent(GuiDrawEvent.class, this.titleManager::onGuiDrawEvent);
        this.eventBus.addEvent(TickEvent.class, this.titleManager::onTickEvent);

        SimpleReloadableResourceManager.updatepack();
    }

    /**
     * Initializes everything required by the client.
     */
    public void initialize() {
        this.mc.cbLoadingScreen.updatePhase("Fonts");
        this.loadFonts();

        this.mc.cbLoadingScreen.updatePhase("Configurations");
        this.configManager.loadProfiles();
        this.configManager.readCurrentProfile();

        this.mc.cbLoadingScreen.updatePhase("Properties");
        this.loadVersionData();

        this.mc.cbLoadingScreen.updatePhase("Session");
        this.registerSessionServers();

        this.mc.cbLoadingScreen.updatePhase("Overlay");
        OverlayGui.setInstance(new OverlayGui());

        try {
            this.mc.cbLoadingScreen.updatePhase("Player Assets");
            this.connectToAssetServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mc.cbLoadingScreen.updatePhase("Session Server");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new ServerStatusThread(), 0L, this.globalSettings.sessionCheckInteral, TimeUnit.SECONDS);
        this.logger.info(this.loggerPrefix + "Scheduled session server status updates");

        this.mc.cbLoadingScreen.updatePhase("Finishing");
        this.getModuleManager().keystrokesMod.updateKeyElements();
        this.mc.cbLoadingScreen.addPhase();
        this.logger.info(this.loggerPrefix + "Finished startup in " + (System.currentTimeMillis() - this.startTime) + "ms!");
        consoleAccess = true;

        new Thread(() -> {
            try {
                while (true) {
                    try {
                        if (this.wsNetHandler != null) {
                            this.wsNetHandler.sendPacket(new WSPacketClientKeySync());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(30000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        if (System.getProperty("os.name").toLowerCase().contains("win") && this.getGlobalSettings().showRPC.getBooleanValue()) {
            this.mc.cbLoadingScreen.addPhase();
            this.connectDiscordIPC();
        }

        this.mc.cbLoadingScreen.addPhase();
        eventBus.handleEvent(new InitializationEvent());
    }

    /**
     * Updates the Discord RPC with the current server the player is on.
     */
    public void updateServerInfo(String server) {
        if (this.getGlobalSettings().showServer.getBooleanValue()) {
            this.updateRPC(this.discordRPCHandler.getRPCInfo(this.currentServer)[3] != null
                    ? this.discordRPCHandler.getRPCInfo(this.currentServer)[3] : server,
                    this.discordRPCHandler.getRPCInfo(this.currentServer));
        } else {
            this.updateRPC(server, new String[]{"Minecraft " + Config.MC_VERSION, "cb", "Minecraft " + Config.MC_VERSION});
        }
    }

    /**
     * Sends the current server you're on to the player asset server.
     */
    public void syncCurrentServerWithAssetServer(String server, String ip, int port) {
        if (this.wsNetHandler == null) return;

        try {
            this.currentServer = server;
            if (this.getGlobalSettings().showAccount.getBooleanValue()) {
                this.updateServerInfo(this.mc.getSession().getUsername());
            } else {
                this.updateServerInfo(null);
            }
        } catch (Exception | UnsatisfiedLinkError ignore) {}

        boolean equals = server.equals(ip + ":" + port);
        if (!equals) {
            this.wsNetHandler.sendUpdateServer(server);
            this.currentServer = (server.isEmpty() ? "In-Menus" : server);
        } else {
            this.wsNetHandler.sendUpdateServer("server");
            this.currentServer = "server";
        }
    }

    /**
     * Gets the player's current friends list status.
     */
    public String getStatusString() {
        switch (this.playerStatus) {
            case AWAY:
                return "Away";
            case BUSY:
                return "Busy";
            case OFFLINE:
                return "Offline";
            default:
                return "Online";
        }
    }

    /**
     * Attempts to form a connection between the player asset server and the client.
     * This server is mainly used for cosmetics and possible anticheat related things.
     */
    public void connectToAssetServer() throws URISyntaxException {
        this.logger.info(this.loggerPrefix + "Attempting to connect to player assets server");
        HashMap<String, String> reqs = new HashMap<>();

        reqs.put("username", this.mc.getSession().getUsername());
        reqs.put("playerId", CheatBreaker.getInstance().getProfileHandler().recompileUUID(this.mc.getSession().getPlayerID()));
        reqs.put("HWID", DataUtil.getHWID());
        reqs.put("version", Config.MC_VERSION);
        reqs.put("gitCommit", this.getGitCommitId());
        reqs.put("branch", this.getGitBranch());
        reqs.put("buildType", this.getGitBuildVersion());

        if (this.currentServer != null) {
            reqs.put("server", this.currentServer);
        }

        this.wsNetHandler = new WSNetHandler(new URI("https://127.0.0.1:6788"), reqs);
        this.wsNetHandler.connect();
    }

    /**
     * Loads the current version data
     */
    private void loadVersionData() {
        try {
            ResourceLocation propertiesFile = new ResourceLocation("client/properties/app.properties");
            Properties property = new Properties();
            InputStream fileInputStream = this.mc.getResourceManager().getResource(propertiesFile).getInputStream();

            if (fileInputStream == null) return;

            property.load(fileInputStream);
            fileInputStream.close();

            this.gitCommitIdAbbrev = property.getProperty("git.commit.id.abbrev");
            this.gitCommitId = property.getProperty("git.commit.id");
            this.gitBranch = property.getProperty("git.branch");
            this.gitBuildVersion = property.getProperty("git.build.version");
            this.branchManager.setCurrentBranch(BranchManager.Branch.getBranchByName(this.gitBranch));
            this.logger.info(this.loggerPrefix + "Loaded client properties");
        } catch (IOException var4) {
            var4.printStackTrace();
            this.branchManager.setCurrentBranch(BranchManager.Branch.DEVELOPMENT);
            this.logger.error(this.loggerPrefix + "An error occurred when loading client properties");
        }
    }

    /**
     * Registers the session servers for periodic checking.
     */
    private void registerSessionServers() {
        this.sessionServers.add(new SessionServer("Session", "session.minecraft.net"));
        this.sessionServers.add(new SessionServer("Login", "authserver.mojang.com"));
        this.sessionServers.add(new SessionServer("Account", "account.mojang.com"));
        this.sessionServers.add(new SessionServer("API", "api.mojang.com"));
        this.logger.info(this.loggerPrefix + "Loaded mojang session status entries");
    }

    /**
     * Returns the scale factor for module scaling.
     */
    public static float getScaleFactor() {
        ScaledResolution var4 = new ScaledResolution(Minecraft.getMinecraft());
        if (getInstance().getGlobalSettings().scaleModsDownSmallerResoltion.getBooleanValue()) {
            switch (Minecraft.getMinecraft().gameSettings.guiScale) {
                case 0: return 2.0f;
                case 1: return 0.5f;
                case 2: return 1.0f;
                case 3: return 1.5f;
            }
            return 1.0f;
        } else {
            return var4.getScaleFactor() / 2.0F;
        }
    }

    /**
     * Loads the CheatBreaker fonts for rendering.
     */
    private void loadFonts() {
        this.playBold22px = new CBFontRenderer(this.fontPlayBold, 22.0f);
        this.playRegular22px = new CBFontRenderer(this.fontPlayRegular, 22.0f);
        this.playRegular18px = new CBFontRenderer(this.fontPlayRegular, 18.0f);
        this.playRegular14px = new CBFontRenderer(this.fontPlayRegular, 14.0f);
        this.playRegular12px = new CBFontRenderer(this.fontPlayRegular, 12.0f);
        this.playRegular16px = new CBFontRenderer(this.fontPlayRegular, 16.0f);
        this.playBold18px = new CBFontRenderer(this.fontPlayBold, 18.0f);
        this.ubuntuMedium16px = new CBFontRenderer(fontUbuntuMedium, 16.0f);
        this.ubuntuMedium14px = new CBFontRenderer(fontUbuntuMedium, 14.0f);
        this.robotoRegular13px = new CBFontRenderer(this.fontRobotoRegular, 13.0f);
        this.robotoBold14px = new CBFontRenderer(this.fontRobotoBold, 14.0f);
        this.robotoRegular24px = new CBFontRenderer(this.fontRobotoRegular, 24.0f);
        this.logger.info(this.loggerPrefix + "Loaded all fonts");
    }

    /**
     * Returns the ResourceLocation of the player avatar for the account switcher.
     */
    public ResourceLocation getHeadIcon(String name) {
        ResourceLocation var3 = this.playerAvatarCache.getOrDefault(name, new ResourceLocation("client/heads/" + name + ".png"));
        if (!this.playerAvatarCache.containsKey(name)) {
            ThreadDownloadImageData var4 = new ThreadDownloadImageData(null, "https://minotar.net/helm/" + name + "/32.png", new ResourceLocation("client/defaults/steve.png"), null);
            this.mc.renderEngine.loadTexture(var3, var4);
            this.playerAvatarCache.put(name, var3);
        }
        return var3;
    }

    /**
     * Creates a new IPC Client for Discord RPC usage.
     */
    public void connectDiscordIPC() {
        try {
            (this.rpcClient = new IPCClient(925912458353340447L)).setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    CheatBreaker.getInstance().updateRPC(Minecraft.getMinecraft().getSession().getUsername(), CheatBreaker.getInstance().discordRPCHandler.getRPCInfo(CheatBreaker.getInstance().getCurrentServer()));
                }
            });
            this.rpcClient.connect();
            this.logger.info(this.loggerPrefix + "Connected to Discord IPC");
        } catch (Exception e) {
            this.logger.error(this.loggerPrefix + "Failed to connect to Discord IPC");
        }
    }

    /**
     * Updates the Discord RPC.
     */
    public void updateRPC(String state, String[] serverName) {
        try {
            if (this.rpcClient != null) {
                RichPresence.Builder var8 = new RichPresence.Builder();
                var8.setState(state).setDetails(serverName[2]).setStartTimestamp(OffsetDateTime.now()).setLargeImage(serverName[1], serverName[0]);
                this.rpcClient.sendRichPresence(var8.build());
            }
        } catch (Exception ignore) {
        }
    }
}
