package com.cheatbreaker.client.module.impl.normal.hypixel;

import com.cheatbreaker.client.event.impl.chat.ChatReceivedEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionModuleHypixel extends AbstractModule {
    public final Setting autoCommandsOptionsLabel;
    public final Setting autoTip;
    public final Setting autoFriend;
    public final Setting autoFriendMode;
    public final Setting autoFriendDelay;

    public final Setting autoChatOptionsLabel;
    public final Setting autoGG;
    public final Setting autoGGMessage;
    public final Setting autoGGDelay;
    public final Setting autoGL;
    public final Setting autoGLMessage;
    public final Setting autoGLDelay;
    public final Setting autoWB;
    public final Setting autoWBFriends;
    public final Setting autoWBGuildMembers;
    public final Setting autoWBMessage;
    public final Setting autoWBDelay;

    public final Setting chatFilterOptionsLabel;
    public final Setting antiGG;
    public final Setting antiGL;

    public final Setting bedWarsOptionsLabel;
    public final Setting bedwarsHardcoreHearts;

    /**
     * Trigger lists and patterns
     */
    private List<String> autoGGTriggerList = new ArrayList<>();
    private final Pattern friendRequestTriggerPattern = Pattern.compile("§m----------------------------------------------------Friend request from (?<name>.+)\\[ACCEPT\\] - \\[DENY\\] - \\[IGNORE\\].*");
    private final Pattern bedLossPattern = Pattern.compile("BED DESTRUCTION > Your Bed .*");
    private final String[] gameOverStrings = new String[]{"You have been eliminated!", "Reward Summary"};


    private final Pattern guildJoinedTriggerPattern = Pattern.compile("Guild > (?<name>.+) joined\\..*");
    private final Pattern friendJoinedTriggerPattern = Pattern.compile("Friend > (?<name>.+) joined\\..*");

    public CollectionModuleHypixel() {
        super("Hypixel Collection");
        this.setDefaultState(false);
        this.autoCommandsOptionsLabel = new Setting(this, "label").setValue("Auto Command Options");
        this.autoTip = new Setting(this, "Auto Tip").setValue(false);

        this.autoFriend = new Setting(this, "Auto Friend").setValue(false);
        this.autoFriendMode = new Setting(this, "Auto Friend Mode").setValue("Accept").acceptedStringValues("Accept", "Deny", "Ignore");
        this.autoFriendDelay = new Setting(this, "Auto Friend Delay").setUnit("s").setValue(0.0F).setMinMax(0.0F, 15.0F).setCondition(this.autoFriend::getBooleanValue);

        this.autoChatOptionsLabel = new Setting(this, "label").setValue("Auto Chat Options");
        this.autoGG = new Setting(this, "Auto GG").setValue(false);
        this.autoGGDelay = new Setting(this, "Send GG Message Delay").setUnit("s").setValue(0.0F).setMinMax(0.0F, 5.0F).setCondition(this.autoGG::getBooleanValue);
        this.autoGGMessage = new Setting(this, "Auto GG String").setValue("gg").setCondition(this.autoGG::getBooleanValue).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.autoGL = new Setting(this, "Auto GL").setValue(false);
        this.autoGLDelay = new Setting(this, "Send GL Message Delay").setUnit("s").setValue(0.0F).setMinMax(0.0F, 15.0F).setCondition(this.autoGL::getBooleanValue);
        this.autoGLMessage = new Setting(this, "Auto GL String").setValue("gl").setCondition(this.autoGL::getBooleanValue).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.autoWB = new Setting(this, "Auto WB").setValue(false);
        this.autoWBFriends = new Setting(this, "Welcome Back Friends").setValue(true).setCondition(this.autoWB::getBooleanValue);
        this.autoWBGuildMembers = new Setting(this, "Welcome Back Guild Members").setValue(true).setCondition(this.autoWB::getBooleanValue);
        this.autoWBDelay = new Setting(this, "Send WB Message Delay").setUnit("s").setValue(2.0F).setMinMax(2.0F, 15.0F).setCondition(this.autoWB::getBooleanValue);
        this.autoWBMessage = new Setting(this, "Auto WB String").setValue("Welcome back %USER%!").setCondition(this.autoWB::getBooleanValue).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.chatFilterOptionsLabel = new Setting(this, "label").setValue("Chat Filter Options");
        this.antiGG = new Setting(this, "Hide GG").setValue(false);
        this.antiGL = new Setting(this, "Hide GL").setValue(false);

        this.bedWarsOptionsLabel = new Setting(this, "label").setValue("Bed Wars Options");
        this.bedwarsHardcoreHearts = new Setting(this, "Hardcore Hearts", "Changes your hearts to show hardcore hearts when you lose your own bed.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        this.setPreviewIcon(new ResourceLocation("client/icons/mods/hypixel.png"), 21, 40);
        this.setDescription("A collection of mods made for the Hypixel Network.");
        this.setCreators("2Pi (Auto GG, Auto Friend, Auto Tip)", "Sk1er (Auto GL, Hardcore Hearts)", "Maximusbarcz (Auto WB)");
        this.addEvent(ChatReceivedEvent.class, this::onReceivedChatEvent);
        this.setupTriggerList();
    }


    /**
     * Sets up 2PI's Auto GG trigger list.
     */
    private void setupTriggerList() {
        new Thread(() -> {
            try {
                String ggTriggerList = IOUtils.toString(new URL("https://gist.githubusercontent.com/minemanpi/72c38b0023f5062a5f3eba02a5132603/raw/triggers.txt"));
                this.autoGGTriggerList = new ArrayList<>(Arrays.asList(ggTriggerList.split("\n")));
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }).start();
    }

    public void onReceivedChatEvent(ChatReceivedEvent event) {
        Matcher matcher;
        String message = event.getReceivedChatMessage();

        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {

            if (message.contains(gameOverStrings[0]) || message.contains(gameOverStrings[1])) {
                Minecraft.getMinecraft().thePlayer.worldObj.getWorldInfo().setHardcore(false);
            }

            if (this.bedwarsHardcoreHearts.getBooleanValue() && this.bedLossPattern.matcher(message).matches()) {
                this.mc.thePlayer.worldObj.getWorldInfo().setHardcore(true);
            }

            if (this.autoGG.getBooleanValue() && this.autoGGTriggerList.stream().anyMatch(message::contains)) {
                this.sendMessage("/achat " + this.autoGGMessage.getStringValue(), this.autoGGDelay.getFloatValue());
            }

            if (this.autoGL.getBooleanValue() && message.startsWith("The game starts in 5 seconds!")) {
                this.sendMessage("/achat " + this.autoGLMessage.getStringValue(), this.autoGLDelay.getFloatValue());
            }

            if (this.autoWB.getBooleanValue() && this.autoWBGuildMembers.getBooleanValue() && (matcher = this.guildJoinedTriggerPattern.matcher(message)).matches()) {
                String name = matcher.group("name");
                this.sendMessage("/gc " + this.autoWBMessage.getStringValue().replaceAll("%USER%", name), this.autoWBDelay.getFloatValue());
            }

            if (this.autoWB.getBooleanValue() && this.autoWBFriends.getBooleanValue() && (matcher = this.friendJoinedTriggerPattern.matcher(message)).matches()) {
                String name = matcher.group("name");
                this.sendMessage("/msg " + name + " " + this.autoWBMessage.getStringValue().replaceAll("%USER%", name), this.autoWBDelay.getFloatValue());
            }

            if (this.autoFriend.getBooleanValue() && (matcher = this.friendRequestTriggerPattern.matcher(message.replace("\n", ""))).matches()) {
                String name = matcher.group("name");
                // Removes any rank prefix.
                if (name.startsWith("[")) name = name.substring(name.indexOf("] ") + 2);
                this.sendMessage("/friend " + this.autoFriendMode.getStringValue().toLowerCase() + " " + name, this.autoFriendDelay.getFloatValue());
            }
        }
    }

    /**
     * Sends the set chat message with a set delay.
     */
    public void sendMessage(String message, float delay) {
        new Thread(() -> {
            try {
                Thread.sleep((long) delay * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mc.thePlayer.sendChatMessage(message);
        }).start();
    }
}
