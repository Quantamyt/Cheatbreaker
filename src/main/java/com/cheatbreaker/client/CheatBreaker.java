package com.cheatbreaker.client;
import com.cheatbreaker.client.audio.AudioManager;
import com.cheatbreaker.client.config.ConfigManager;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.config.Profile;
import com.cheatbreaker.client.cosmetic.CosmeticManager;
import com.cheatbreaker.client.cosmetic.EmoteManager;
import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.*;
import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.network.messages.Message;
import com.cheatbreaker.client.network.plugin.CBNetHandler;
import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientKeySync;
import com.cheatbreaker.client.audio.music.DashManager;
import com.cheatbreaker.client.ui.element.profile.ProfilesListElement;
import com.cheatbreaker.client.ui.mainmenu.menus.ChangelogMenu;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import com.cheatbreaker.client.util.data.DataUtil;
import com.cheatbreaker.client.util.discord.DiscordRPCHandler;
import com.cheatbreaker.client.util.friend.Friend;
import com.cheatbreaker.client.util.friend.FriendsManager;
import com.cheatbreaker.client.util.data.JSONReader;
import com.cheatbreaker.client.util.sessionserver.SessionServer;
import com.cheatbreaker.client.util.thread.ServerStatusThread;
import com.cheatbreaker.client.util.render.title.TitleManager;
import com.cheatbreaker.client.util.render.worldborder.WorldBorderManager;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter @Setter
public class CheatBreaker {
    @Getter private static CheatBreaker instance;
    private final Minecraft mc = Minecraft.getMinecraft();

    private final Map<String, ResourceLocation> headIconCache = new HashMap<>();

    private final List<String> consoleLines = new ArrayList<>();
    private final List<ResourceLocation> presetLocations = new ArrayList<>();
    public List<SessionServer> sessionServers = new ArrayList<>();

    public List<Session> sessions = new ArrayList<>();
    public List<Profile> profiles = new ArrayList<>();

    private final String cheatBreakerPluginMessageChannel = "CB-Client";
    private final String lunarPluginMessageChannel = "Lunar-Client";
    private final String latestLunarPluginMessageChannel = "lunarclient:pm";
    private final String pluginBinaryChannel = "CB-Binary";
    private final String loggerPrefix = "[CB] ";
    private String gitCommitIdAbbrev = "?";
    private String gitBranch = "?";
    private String gitCommitId = "?";
    private String gitBuildVersion = "?";
    private String currentServer;

    private final long startTime;

    private boolean consoleAccess;
    private boolean acceptingFriendRequests = true;
    private final boolean confidentialBuild = false;
    private final boolean aprilFools = false;

    public static byte[] processlistbytes = new byte[]{86, 79, 84, 69, 32, 84, 82, 85, 77, 80, 32, 50, 48, 50, 48, 33};
    public static AudioFormat universalAudioFormat = new AudioFormat(8000.0f, 16, 1, true, true);

    public Profile activeProfile;

    private final GlobalSettings globalSettings;
    private final CBNetHandler cbNetHandler;
    public ConfigManager configManager;
    private final EventBus eventBus;
    private WSNetHandler wsNetHandler;
    private final ModuleManager moduleManager;
    private final CosmeticManager cosmeticManager;
    private final EmoteManager emoteManager;
    private final AudioManager audioManager;
    private final FriendsManager friendsManager;
    private final DashManager dashManager;
    private final TitleManager titleManager;
    private final WorldBorderManager worldBorderManager;
    private final ProfileHandler profileHandler;

    private IPCClient rpcClient;
    private Friend.Status playerStatus = Friend.Status.ONLINE;

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

    private final ResourceLocation playBold = new ResourceLocation("client/font/Play-Bold.ttf");
    private final ResourceLocation robotoRegular = new ResourceLocation("client/font/Roboto-Regular.ttf");
    private final ResourceLocation robotoBold = new ResourceLocation("client/font/Roboto-Bold.ttf");
    private final ResourceLocation playRegular = new ResourceLocation("client/font/Play-Regular.ttf");
    private final ResourceLocation ubuntuMedium = new ResourceLocation("client/font/Ubuntu-M.ttf");
    public static ResourceLocation icontexture;

    public final Logger logger;

    public CheatBreaker() throws IOException {
        logger = LogManager.getLogger("CheatBreaker Setup");
        this.startTime = System.currentTimeMillis();
        this.logger.info(this.loggerPrefix + "Starting CheatBreaker setup");
        instance = this;

        this.mc.cbLoadingScreen.updatePhase("Settings");
        this.globalSettings = new GlobalSettings();
        this.logger.info(this.loggerPrefix + "Created settings");

        this.mc.cbLoadingScreen.updatePhase("EventBus");
        this.eventBus = new EventBus();
        this.logger.info(this.loggerPrefix + "Created EventBus");

        this.mc.cbLoadingScreen.updatePhase("Mods");
        this.moduleManager = new ModuleManager(this.eventBus);
        this.logger.info(this.loggerPrefix + "Created Mod Manager");

        this.mc.cbLoadingScreen.updatePhase("Network Manager");
        this.cbNetHandler = new CBNetHandler();
        this.logger.info(this.loggerPrefix + "Created Network Manager");

        this.mc.cbLoadingScreen.updatePhase("Cosmetics");
        this.cosmeticManager = new CosmeticManager();
        this.emoteManager = new EmoteManager();
        this.logger.info(this.loggerPrefix + "Created Cosmetic Managers");

        this.mc.cbLoadingScreen.updatePhase("Radio");
        this.dashManager = new DashManager();
        this.audioManager = new AudioManager();
        this.logger.info(this.loggerPrefix + "Created Dash Manager");

        this.mc.cbLoadingScreen.updatePhase("Friends");
        this.friendsManager = new FriendsManager();
        this.logger.info(this.loggerPrefix + "Created Friends Manager");

        this.mc.cbLoadingScreen.updatePhase("Titles");
        this.titleManager = new TitleManager();
        this.logger.info(this.loggerPrefix + "Created Title Manager");

        try {
            ChangelogMenu.changelogObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(JSONReader.readAsBrowser(new URL("https://noxiuam.gq/cheatbreaker/changelog"), true), StandardCharsets.UTF_8))).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            CheatBreaker.getInstance().getLogger().error(CheatBreaker.getInstance().getLoggerPrefix() + "Failed to load changelog.");
        }

        this.mc.cbLoadingScreen.updatePhase("World Border");
        this.worldBorderManager = new WorldBorderManager();
        this.logger.info(this.loggerPrefix + "Created Friend Manager");

        this.profileHandler = new ProfileHandler();
        this.logger.info(this.loggerPrefix + "Created Profile Handler");


        this.mc.cbLoadingScreen.updatePhase("Network Events");
        EventBus eventBus = this.eventBus;
        CBNetHandler netHandler = this.cbNetHandler;
        eventBus.addEvent(DisconnectEvent.class, netHandler::onDisconnect);
        eventBus.addEvent(ConnectEvent.class, netHandler::onConnect);
        eventBus.addEvent(PluginMessageEvent.class, netHandler::onPluginMessage);
        eventBus.addEvent(ClickStateEvent.class, netHandler::onClickEvent);

//        eventBus.addEvent(GuiDrawEvent.class, this.titleManager::onGuiDrawEvent);
//        eventBus.addEvent(TickEvent.class, this.titleManager::onTickEvent);
        this.logger.info(this.loggerPrefix + "Registered network events");

        SimpleReloadableResourceManager.updatepack();
    }

    private void addMics() {
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getTargetLineInfo();
            if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                if (info == null) continue;
                Message.g(new String[]{info.getDescription()}, new String[]{info.getName()});
            }
        }
    }

    public void initialize() {
        this.mc.cbLoadingScreen.updatePhase("Fonts");
        this.loadFonts();
        this.logger.info(this.loggerPrefix + "Loaded all fonts");

        this.mc.cbLoadingScreen.updatePhase("Properties");
        this.loadVersionData();
        this.logger.info(this.loggerPrefix + "Loaded client properties");

        this.mc.cbLoadingScreen.updatePhase("Profiles");
        this.createDefaultConfigPresets();
        this.logger.info(this.loggerPrefix + "Created default configuration presets");
        this.loadProfiles();
        this.logger.info(this.loggerPrefix + "Loaded " + this.profiles.size() + " custom profiles");

        this.mc.cbLoadingScreen.updatePhase("Session");
        this.addSessionServers();
        this.logger.info(this.loggerPrefix + "Loaded mojang session status entries");

        this.mc.cbLoadingScreen.updatePhase("Configs");
        this.configManager = new ConfigManager();
        this.configManager.read();
        this.logger.info(this.loggerPrefix + "Loaded configuration");

        this.mc.cbLoadingScreen.updatePhase("Overlay");
        OverlayGui.setInstance(new OverlayGui());
        this.logger.info(this.loggerPrefix + "Loaded Overlay Gui");

        this.mc.cbLoadingScreen.updatePhase("Player Assets");
        try {
            this.logger.info(this.loggerPrefix + "Connecting to player assets server");
            this.connectToAssetServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.mc.cbLoadingScreen.updatePhase("Session Server");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ServerStatusThread sessionPinger = new ServerStatusThread();
        scheduler.scheduleAtFixedRate(sessionPinger, 0L, this.globalSettings.sessionCheckInteral, TimeUnit.SECONDS);
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
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }
                    Thread.sleep(30000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win") && this.getGlobalSettings().showRPC.getBooleanValue()) {
                this.mc.cbLoadingScreen.addPhase();
                this.connectDiscordIPC();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.mc.cbLoadingScreen.addPhase();
        eventBus.handleEvent(new InitializationEvent());
    }

    public void updateServerInfo(String server) {
        if (this.getGlobalSettings().showRPC.getBooleanValue()) {
            if (this.getGlobalSettings().showServer.getBooleanValue()) {
                this.updateRPC(DiscordRPCHandler.getServerName(this.currentServer)[3] != null ? DiscordRPCHandler.getServerName(this.currentServer)[3] : server, DiscordRPCHandler.getServerName(this.currentServer));
            } else {
                this.updateRPC(server, new String[]{"Minecraft " + Config.MC_VERSION, "cb", "Minecraft " + Config.MC_VERSION});
            }
        }

    }

    /**
     * Creates a new IPC Client for Discord RPC usage.
     */
    public void connectDiscordIPC() {
        try {
            (this.rpcClient = new IPCClient(925912458353340447L)).setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    CheatBreaker.getInstance().rpcClient = client;
                    CheatBreaker.getInstance().updateRPC(Minecraft.getMinecraft().getSession().getUsername(), DiscordRPCHandler.getServerName(CheatBreaker.getInstance().currentServer));
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
            this.logger.error(this.loggerPrefix + "RPC was unable to be found");
        }
    }

    public void updateWSServer(String server, String ip, int port) {
        try {
            this.currentServer = server;
            if (this.getGlobalSettings().showAccount.getBooleanValue()) {
                this.updateServerInfo(this.mc.getSession().getUsername());
            } else {
                this.updateServerInfo(null);
            }

//            boolean equals = server.equals(ip + ":" + port);
//            this.updateTheRPC(server, null, equals);

        } catch (Exception | UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        boolean equals = server.equals(ip + ":" + port);
//        this.updateTheRPC(server, null, equals);
        if (!equals) {
            this.wsNetHandler.sendUpdateServer(server);
            this.currentServer = (server.isEmpty() ? "In-Menus" : server);
        } else {
            this.wsNetHandler.sendUpdateServer("server");
            this.currentServer = "server";
        }
//        if (!server.equals(ip + ":" + port)) {
//            this.wsNetHandler.sendUpdateServer(server);
//        } else {
//            this.wsNetHandler.sendUpdateServer("server");
//        }
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
     * Connects to the Player Assets Server.
     */
    public void connectToAssetServer() throws URISyntaxException {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", this.mc.getSession().getUsername());
        data.put("playerId", this.mc.getSession().getPlayerID());
        data.put("HWID", DataUtil.getHWID());
        data.put("version", Config.MC_VERSION);
        data.put("gitCommit", this.getGitCommitId());
        data.put("branch", this.getGitBranch());
        data.put("buildType", this.getGitBuildVersion());

        if (this.currentServer != null)
            data.put("server", this.currentServer);

        this.wsNetHandler = new WSNetHandler(new URI("ws://server.noxiuam.gq/connect"), data);
        this.wsNetHandler.connect();
    }

    /**
     * Loads the current version data.
     */
    private void loadVersionData() {
        try {
            ResourceLocation propertiesFile = new ResourceLocation("client/properties/app.properties");
            Properties property = new Properties();
            InputStream fileInputStream = this.mc.getResourceManager().getResource(propertiesFile).getInputStream();
            if (fileInputStream == null) {
                return;
            }
            property.load(fileInputStream);
            fileInputStream.close();
            gitCommitIdAbbrev = property.getProperty("git.commit.id.abbrev");
            gitCommitId = property.getProperty("git.commit.id");
            gitBranch = property.getProperty("git.branch");
            gitBuildVersion = property.getProperty("git.build.version");
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    /**
     * Registers the session servers for periodic checking.
     */
    private void addSessionServers() {
        this.sessionServers.add(new SessionServer("Session", "session.minecraft.net"));
        this.sessionServers.add(new SessionServer("Login", "authserver.mojang.com"));
        this.sessionServers.add(new SessionServer("Account", "account.mojang.com"));
        this.sessionServers.add(new SessionServer("API", "api.mojang.com"));
    }

    private void createDefaultConfigPresets() {
        File mainDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + this.gitBranch);
        File profiles = new File(mainDir + File.separator + "profiles");
        if (!mainDir.exists()) {
            mainDir.mkdirs();
            File masterDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-master");
            File first189Dir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "_"));
            File legacyDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client");
            File firstDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "client");
            File dupDir = null;
            List<File> directories = ImmutableList.of(firstDir, legacyDir, first189Dir, masterDir);
            for (File directory : directories) {
                if (directory.exists()) {
                    dupDir = directory;
                }
            }

            if (dupDir != null) {
                try {
                    FileUtils.copyDirectory(dupDir, mainDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        this.presetLocations.add(new ResourceLocation("client/presets/Preset 1.cfg"));
        if (profiles.exists() || profiles.mkdirs()) {
            for (ResourceLocation presets : this.presetLocations) {
                String presetsName = presets.getResourcePath().replaceAll("([a-zA-Z0-9/]+)/", "");
                File presetsFile = new File(profiles, presetsName);
                if (presetsFile.exists()) continue;
                try {
                    InputStream presetInputStream = this.mc.getResourceManager().getResource(presets).getInputStream();
                    Files.copy(presetInputStream, presetsFile.toPath());
                    presetInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns the scale factor for module scaling.
     */
    public static float getScaleFactor() {
        ScaledResolution var4 = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        if (getInstance().getGlobalSettings().scaleModsDownSmallerResoltion.getBooleanValue()) {
            switch (Minecraft.getMinecraft().gameSettings.guiScale) {
                case 0:
                    return 2.0f;
                case 1:
                    return 0.5f;
                case 2:
                    return 1.0f;
                case 3:
                    return 1.5f;
            }
        } else {
            return var4.getScaleFactor() / 2.0F;
        }
        return 1.0f;
    }

    private void loadProfiles() {
        this.profiles.add(new Profile("default", true));
        File profilesDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch() + File.separator + "profiles");
        if (profilesDir.exists()) {
            for (File profile : Objects.requireNonNull(profilesDir.listFiles())) {
                this.mc.cbLoadingScreen.addPhase();
                if (!profile.getName().endsWith(".cfg")) continue;
                this.profiles.add(new Profile(profile.getName().replace(".cfg", ""), false));
            }
        }
    }

    /**
     * Loads the CheatBreaker fonts for rendering.
     */
    private void loadFonts() {
        this.playBold22px = new CBFontRenderer(playBold, 22.0f);
        this.playRegular22px = new CBFontRenderer(this.playRegular, 22.0f);
        this.playRegular18px = new CBFontRenderer(this.playRegular, 18.0f);
        this.playRegular14px = new CBFontRenderer(this.playRegular, 14.0f);
        this.playRegular12px = new CBFontRenderer(this.playRegular, 12.0f);
        this.playRegular16px = new CBFontRenderer(this.playRegular, 16.0f);
        this.playBold18px = new CBFontRenderer(playBold, 18.0f);
        this.ubuntuMedium16px = new CBFontRenderer(ubuntuMedium, 16.0f);
        this.ubuntuMedium14px = new CBFontRenderer(ubuntuMedium, 14.0f);
        this.robotoRegular13px = new CBFontRenderer(robotoRegular, 13.0f);
        this.robotoBold14px = new CBFontRenderer(robotoBold, 14.0f);
        this.robotoRegular24px = new CBFontRenderer(robotoRegular, 24.0f);
    }

    private String getNewProfileName(String name) {
        File clientDir = new File(this.mc.mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch());
        File profilesDir = new File(clientDir + File.separator + "profiles");
        if ((profilesDir.exists() || profilesDir.mkdirs()) && new File(profilesDir + File.separator + name + ".cfg").exists()) {
            return this.getNewProfileName(name + "1");
        }
        return name;
    }

    public void createNewProfile() {
        if (this.activeProfile == this.profiles.get(0))
        {
            Profile defaultProfile;
            CheatBreaker.getInstance().activeProfile = defaultProfile = new Profile(this.getNewProfileName("Profile 1"), false);
            CheatBreaker.getInstance().profiles.add(defaultProfile);
            CheatBreaker.getInstance().configManager.write();
            if (this.mc.currentScreen instanceof HudLayoutEditorGui)

            {
                ProfilesListElement profilesList = (ProfilesListElement) ((HudLayoutEditorGui) this.mc.currentScreen).profilesElement;
                profilesList.loadProfiles();
            }
        }
    }

    /**
     * Returns the ResourceLocation of the player avatar for the account switcher.
     */
    public ResourceLocation getHeadIcon(String name, String uuid) {
        ResourceLocation headIcon = this.headIconCache.getOrDefault(name, new ResourceLocation("client/heads/" + name + ".png"));

        if (!this.headIconCache.containsKey(name)) {
            ThreadDownloadImageData var4 = new ThreadDownloadImageData(null, "https://minotar.net/helm/" + name
                    + "/32.png", new ResourceLocation("client/defaults/steve.png"), null);
            this.mc.renderEngine.loadTexture(headIcon, var4);
            this.headIconCache.put(name, headIcon);
        }

        return headIcon;
    }

    public WSNetHandler getWSNetHandler() {
        return this.wsNetHandler;
    }
}