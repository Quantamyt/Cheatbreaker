package com.cheatbreaker.client.ui.overlay;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.music.elements.RadioElement;
import com.cheatbreaker.client.audio.music.util.DashUtil;
import com.cheatbreaker.client.ui.AbstractGui;
import com.cheatbreaker.client.ui.element.type.ElementListElement;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.overlay.friend.*;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.friend.Friend;
import com.cheatbreaker.client.util.friend.FriendRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OverlayGui extends AbstractGui {
    private static OverlayGui instance;
    private final FriendsListElement friendsListElement;
    private final FriendRequestListElement friendRequestsElement;
    private final FlatButtonElement friendsButton;
    private final FlatButtonElement requestsButton;
    private ElementListElement selectedFriendElement;
    private final RadioElement radioButton;
    private MessagesElement messages;
    private long openTime;
    private final Queue<CBAlert> alertQueue = new LinkedList();
    private final List<CBAlert> alertList = new ArrayList();
    private GuiScreen context;
    private long lastTyped;

    public OverlayGui() {
        ArrayList<FriendElement> friendElements = new ArrayList<>();
        CheatBreaker.getInstance().getFriendsManager().getFriends().forEach((string, friend) -> friendElements.add(new FriendElement(friend)));
        AbstractElement[] arrabstractElement = new AbstractElement[5];
        this.friendsListElement = new FriendsListElement(friendElements);
        arrabstractElement[0] = this.friendsListElement;
        this.friendRequestsElement = new FriendRequestListElement(new ArrayList());
        arrabstractElement[1] = this.friendRequestsElement;
        this.requestsButton = new FlatButtonElement("REQUESTS");
        arrabstractElement[2] = this.requestsButton;
        this.friendsButton = new FlatButtonElement("FRIENDS");
        arrabstractElement[3] = this.friendsButton;
        this.radioButton = new RadioElement();
        arrabstractElement[4] = this.radioButton;
        this.setElements(arrabstractElement);
        this.addElements(this.friendsListElement, this.friendRequestsElement, this.requestsButton, this.friendsButton);
        this.selectedFriendElement = this.friendsListElement;
    }

    public void setMessages(Friend friend) {
        try {
            MessagesElement messagesElement = null;
            for (AbstractElement abstractElement : this.selectedButton) {
                if (!(abstractElement instanceof MessagesElement)) continue;
                messagesElement = (MessagesElement)abstractElement;
            }
            if (messagesElement == null) {
                this.messages = new MessagesElement(friend);
                this.selectedButton.add(this.messages);
                this.messages.setElementSize(170.0f, 30.0f, 245.0f, 150);
            } else {
                this.selectedButton.add(this.selectedButton.remove(this.selectedButton.indexOf(messagesElement)));
                messagesElement.handleElementUpdate(friend);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.openTime = System.currentTimeMillis();
        if (this.mc.lastScreen != this) {
            this.blurGui();
        }
        friendsListElement.getElements().clear();
        for(Friend friend : CheatBreaker.getInstance().getFriendsManager().getFriends().values()) {
            friendsListElement.getElements().add(new FriendElement(friend));
        }
        this.friendsButton.setElementSize(0.0f, 28.0f, 96.976746f * 0.71666664f, 20);
        this.requestsButton.setElementSize(55.315384f * 1.2745098f, 28.0f, 0.5588235f * 124.36842f, 20);
        float f = (float)28 + this.friendsButton.getHeight() + 1.0f;
        this.friendsListElement.setElementSize(0.0f, f, 140.0f, this.getScaledHeight() - f);
        this.friendRequestsElement.setElementSize(0.0f, f, 140.0f, this.getScaledHeight() - f);
        float f2 = 190;
        this.radioButton.setElementSize(this.getScaledWidth() - f2 - 20.0f, 20.0f, f2, 28);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.handleElementMouse();
    }

    @Override
    public void drawMenu(float f, float f2) {
        GL11.glClear(256);
        this.renderBlur(this.getScaledWidth(), this.getScaledHeight());
        OverlayGui.drawRect(0.0f, 0.0f, 140, this.getScaledHeight(), -14671840);
        OverlayGui.drawRect(140, 0.0f, 141, this.getScaledHeight(), -15395563);
        OverlayGui.drawRect(0.0f, 0.0f, 140, 28, -15395563);
        OverlayGui.drawRect(6, 6, 22, 22, Friend.getStatusColor(CheatBreaker.getInstance().getPlayerStatus()));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation resourceLocation = CheatBreaker.getInstance().getHeadIcon(this.mc.getSession().getUsername(), this.mc.getSession().getPlayerID());
        RenderUtil.renderIcon(resourceLocation, 7.0F, 7.0F, 7.0F);
        CheatBreaker.getInstance().playRegular16px.drawString(this.mc.getSession().getUsername(), 28, 6.0f, -1);
        CheatBreaker.getInstance().playRegular12px.drawString(CheatBreaker.getInstance().getStatusString(), 28, 15.0f, -5460820);
        boolean bl = f > 6.0F && f < 134.0F && f2 > 6.0F && f2 < 22.0F;
        if (this.mouseClicked(this.friendsButton, f, f2) && bl && CheatBreaker.getInstance().getWSNetHandler().isOpen()) {
            OverlayGui.drawRect(22, 0.0f, 140, 28, -15395563);
            OverlayGui.drawRect(24, 6, 40, 22, Friend.getStatusColor(Friend.Status.ONLINE));
            OverlayGui.drawRect(42, 6, 58, 22, Friend.getStatusColor(Friend.Status.AWAY));
            OverlayGui.drawRect(60, 6, 76, 22, Friend.getStatusColor(Friend.Status.BUSY));
            OverlayGui.drawRect(78, 6, 94, 22, Friend.getStatusColor(Friend.Status.OFFLINE));
            GL11.glColor4f(1.2553191f * 0.11949153f, 0.30555555f * 0.4909091f, 0.885f * 0.16949153f, 1.0f);
            RenderUtil.renderIcon(resourceLocation, 7.0F, 25.0F, 7.0F);
            RenderUtil.renderIcon(resourceLocation, 7.0F, 43.0f, 7.0F);
            RenderUtil.renderIcon(resourceLocation, 7.0F, 61.0f, 7.0F);
            RenderUtil.renderIcon(resourceLocation, 7.0F, 79.0f, 7.0F);
        }
        this.selectedFriendElement.drawElementHover(f, f2, this.mouseClicked(this.requestsButton, f, f2));
        OverlayGui.drawRect(51.369568f * 1.3529412f, 28, 0.74025977f * 95.23684f, 28.0f + this.friendsButton.getHeight(), -14869219);
        OverlayGui.drawRect(0.0f, 28.0f + this.friendsButton.getHeight(), 140, 28.0f + this.friendsButton.getHeight() + 1.0f, -15395563);
        this.drawElementHover(f, f2, this.friendsListElement, this.friendRequestsElement);
    }

    @Override
    public void keyTyped(char c, int n) {
        if (n == 15 && Keyboard.isKeyDown(42) && System.currentTimeMillis() - this.openTime > 200L || n == 1) {
            this.lastTyped = System.currentTimeMillis();
            this.mc.displayGuiScreen(this.context);
        }
        this.handleElementKeyTyped(c, n);
        if (n == 59 && CheatBreaker.getInstance().isConsoleAccess()) {
            boolean bl = true;
            for (AbstractElement abstractElement : this.selectedButton) {
                if (!(abstractElement instanceof ConsoleElement)) continue;
                bl = false;
            }
            if (bl) {
                AbstractElement[] arrabstractElement = new AbstractElement[1];
                ConsoleElement consoleElement = new ConsoleElement();
                arrabstractElement[0] = consoleElement;
                this.addElement(arrabstractElement);
                consoleElement.setElementSize(60.0f, 30.0f, 300.0f, 145);
            }
        }
    }

    @Override
    protected void mouseClicked(float mouseX, float mouseY, int n) {
        this.selectedFriendElement.handleElementMouseClicked(mouseX, mouseY, n, this.mouseClicked(this.requestsButton, mouseX, mouseY));
        this.swapElement(mouseX, mouseY, n, this.friendsListElement, this.friendRequestsElement);
        boolean bl2 = this.mouseClicked(this.friendsButton, mouseX, mouseY);
        if (bl2 && this.friendsButton.isMouseInside(mouseX, mouseY) && this.selectedFriendElement != this.friendsListElement) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.selectedFriendElement = this.friendsListElement;
        } else if (bl2 && this.requestsButton.isMouseInside(mouseX, mouseY) && this.selectedFriendElement != this.friendRequestsElement) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.selectedFriendElement = this.friendRequestsElement;
        }
        boolean bl = mouseX > 6.0f && mouseX < 134.0f && mouseY > 6.0f && mouseY < 22.0f;
        if (bl2 && bl && CheatBreaker.getInstance().getWSNetHandler().isOpen()) {
            boolean bl5 = mouseX > 24.0f && mouseX < 40.0f;
            boolean bl6 = mouseX > 42.0f && mouseX < 58.0f;
            boolean bl7 = mouseX > 60.0f && mouseX < 76.0f;
            boolean bl4 = mouseX > 78.0f && mouseX < 94.0f;
            if (bl5) {
                CheatBreaker.getInstance().setPlayerStatus(Friend.Status.ONLINE);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            } else if (bl6) {
                CheatBreaker.getInstance().setPlayerStatus(Friend.Status.AWAY);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            } else if (bl7) {
                CheatBreaker.getInstance().setPlayerStatus(Friend.Status.BUSY);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            } else if (bl4) {
                CheatBreaker.getInstance().setPlayerStatus(Friend.Status.OFFLINE);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
            CheatBreaker.getInstance().getWSNetHandler().updateClientStatus();
        }
    }

    @Override
    public void mouseMovedOrUp(float f, float f2, int n) {
        this.onMouseMoved(f, f2, n);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.context = null;
        this.closeElements();
        this.mc.entityRenderer.stopUseShader();
    }

    @Override
    public void updateScreen() {
        if (this.context != null) {
            this.context.updateScreen();
        }
        this.friendsButton.setText("FRIENDS (" + this.friendsListElement.getElements().size() + ")");
        this.requestsButton.setText("REQUESTS (" + this.friendRequestsElement.getElements().stream().filter(friend -> !friend.getFriendRequest().isFriend()).count() + ")");
        this.updateElements();
    }

    public void pollNotifications() {
        this.alertList.removeIf(CBAlert::shouldDisplay);
        if (this.alertQueue.isEmpty()) {
            return;
        }
        boolean bl = true;
        for (CBAlert cBAlert2 : this.alertList) {
            if (cBAlert2.isFading()) continue;
            bl = false;
        }
        if (bl) {
            CBAlert cBAlert3 = this.alertQueue.poll();
            cBAlert3.setMaxHeight(this.getScaledHeight() - (float)CBAlert.getHeight());
            this.alertList.forEach(cBAlert -> cBAlert.setMaxHeight(cBAlert.getMaxHeight() - (float)CBAlert.getHeight()));
            this.alertList.add(cBAlert3);
        }
    }

    public void renderGameOverlay() {
        this.alertList.forEach(CBAlert::render);
        if (this.mc != null && this.mc.currentScreen == null && (Boolean) CheatBreaker.getInstance().getGlobalSettings().pinRadio.getValue() && DashUtil.isActive()) {
            this.radioButton.drawElementHover(0.0f, 0.0f, false);
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int n, int n2) {
        if (this.context != null) {
            this.context.setWorldAndResolution(mc, n, n2);
        }
        float f = this.getScaledHeight();
        super.setWorldAndResolution(mc, n, n2);
        this.alertList.forEach(cBAlert -> this.setDimensions(cBAlert, this.getScaledHeight() + f));
        this.alertQueue.forEach(cBAlert -> this.setDimensions(cBAlert, this.getScaledHeight() + f));
    }

    private void setDimensions(CBAlert cBAlert, float f) {
        cBAlert.setHeight(this.getScaledWidth() - (float)CBAlert.getWidth());
        cBAlert.setCurrentHeight(cBAlert.getLastHeight() - f);
        cBAlert.setHeight(cBAlert.getMaxHeight() - f);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.context != null) {
            this.context.drawScreen(-1, -1, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void displayMessage(String string) {
        this.displayMessage("", string);
    }

    public void displayMessage(String title, String message) {
        int width = CBAlert.getWidth();
        message = CheatBreaker.getInstance().playRegular14px.formatText(message, width - 10);
        CBAlert cBAlert = new CBAlert(title, message.split("\n"), this.getScaledHeight());
        cBAlert.showTitleBar(title.equals(""));
        this.alertQueue.add(cBAlert);
        System.out.println(message);
    }

    public void handleFriend(Friend friend, boolean bl) {
        if (bl) {
            this.friendsListElement.getElements().add(new FriendElement(friend));
        } else {
            this.friendsListElement.getElements().removeIf(friendElement -> friendElement.getFriend() == friend);
        }
        this.friendsListElement.updateSize();
    }

    public void handleFriendRequest(FriendRequest friendRequest, boolean bl) {
        if (bl) {
            this.friendRequestsElement.getElements().add(new FriendRequestElement(friendRequest));
        } else {
            this.friendRequestsElement.getElements().removeIf(friendRequestElement -> friendRequestElement.getFriendRequest() == friendRequest);
        }
        this.friendRequestsElement.setElementSize();
    }

    public static OverlayGui createInstance(GuiScreen guiScreen) {
        if (guiScreen != instance) {
            OverlayGui.getInstance().context = guiScreen;
        }
        return OverlayGui.getInstance();
    }

    public static OverlayGui getInstance() {
        return instance;
    }

    public static void setInstance(OverlayGui overlayGui) {
        instance = overlayGui;
    }

    public FriendsListElement getFriendsListElement() {
        return this.friendsListElement;
    }

    public FriendRequestListElement getFriendRequestsElement() {
        return this.friendRequestsElement;
    }

    public long getLastTyped() {
        return this.lastTyped;
    }
}
