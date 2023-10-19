package com.cheatbreaker.client.ui.overlay.friend;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientRequestsStatus;
import com.cheatbreaker.client.network.websocket.shared.WSPacketFriendRequest;
import com.cheatbreaker.client.ui.element.type.ElementListElement;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.element.type.InputFieldElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.overlay.CBAlert;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestListElement extends ElementListElement<FriendRequestElement> {
    private final InputFieldElement filterTextField;
    private final InputFieldElement usernameTextField;
    private final FlatButtonElement addButton;
    private final FlatButtonElement toggleIncomingRequestsButton;
    private final ScrollableElement scrollbar;
    private final List<FriendRequestElement> requestElements = new ArrayList<>();

    public FriendRequestListElement(List list) {
        super(list);
        this.filterTextField = new InputFieldElement(CheatBreaker.getInstance().playRegular14px, "Filter", 0x2FFFFFFF, 0x6FFFFFFF);
        this.usernameTextField = new InputFieldElement(CheatBreaker.getInstance().playRegular14px, "Username", 0x2FFFFFFF, 0x6FFFFFFF);
        this.addButton = new FlatButtonElement("ADD");
        this.toggleIncomingRequestsButton = new FlatButtonElement("");
        this.scrollbar = new ScrollableElement(this);
    }

    public void setElementSize() {
        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    @Override
    public void setElementSize(float f, float y, float width, float height) {
        super.setElementSize(f, y, width, height);
        this.scrollbar.setElementSize(f + width - (float) 4, y, (float) 4, height);
        int n = 22;
        int n2 = 0;
        for (FriendRequestElement friendRequestElement : this.elements) {
            if (!this.handleElementMouseClicked(friendRequestElement)) continue;
            friendRequestElement.setElementSize(f, y + (float) 14 + (float) (n2 * 22), width, 22);
            ++n2;
        }
        float f5 = 14 + this.elements.size() * 22 + 30;
        if (f5 < height) {
            f5 = height;
        }
        this.filterTextField.setElementSize(0.0f, y, width, 13);
        this.usernameTextField.setElementSize(0.0f, y + f5 - (float) 13, width - (float) 35, 13);
        this.addButton.setElementSize(width - (float) 35, y + f5 - (float) 13, (float) 35, 13);
        this.toggleIncomingRequestsButton.setElementSize(0.0f, y + f5 - (float) 26, width, 13);
        this.scrollbar.setScrollAmount(f5);
    }

    private boolean handleElementMouseClicked(FriendRequestElement friendRequestElement) {
        return this.filterTextField.getText().equals("") || EnumChatFormatting.getTextWithoutFormattingCodes(friendRequestElement.getFriendRequest().getUsername()).toLowerCase().startsWith(this.filterTextField.getText().toLowerCase());
    }

    /*
     * Iterators could be improved
     */
    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        if (!this.requestElements.isEmpty()) {
            this.elements.removeAll(this.requestElements);
            OverlayGui.getInstance().getFriendRequestsElement().setElementSize();
            this.requestElements.clear();
        }
        if (!CheatBreaker.getInstance().getWsNetHandler().isOpen()) {
            CheatBreaker.getInstance().playBold18px.drawCenteredString("Connection lost", this.xPosition + this.width / 2.0f, this.yPosition + (float) 10, -1);
            CheatBreaker.getInstance().playRegular14px.drawCenteredString("Please try again later.", this.xPosition + this.width / 2.0f, this.yPosition + (float) 22, -1);
        } else {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            OverlayGui overlayGui = OverlayGui.getInstance();
            this.scrollbar.drawScrollable(f, f2, bl);
            RenderUtil.startScissorBox((int) this.xPosition, (int) this.yPosition, (int) (this.xPosition + this.width), (int) (this.yPosition + this.height),
                    (float) ((int) ((float) overlayGui.getScaledResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
            GL11.glDisable(3089);
            GL11.glPopMatrix();
            ImmutableList<FriendRequestElement> friendList = ImmutableList.copyOf(this.elements);
            for (FriendRequestElement friendRequestElement : friendList) {
                if (!this.handleElementMouseClicked(friendRequestElement)) continue;
                friendRequestElement.drawElementHover(f, f2 - this.scrollbar.getHeight(), bl);
            }
            if (friendList.isEmpty()) {
                CheatBreaker.getInstance().playBold18px.drawCenteredString("No friend requests", this.xPosition + this.width / 2.0f, this.yPosition + (float) 30, -1);
            }
            this.filterTextField.drawElementHover(f, f2 - this.scrollbar.getHeight(), true);
            this.usernameTextField.drawElementHover(f, f2, true);
            this.addButton.drawElementHover(f, f2, true);
            this.toggleIncomingRequestsButton.drawButton((CheatBreaker.getInstance().isAcceptingFriendRequests() ? "Disable" : "Enable") + " incoming friend requests", f, f2, true);

            this.scrollbar.handleElementDraw(f, f2, bl);
        }
    }

    @Override
    public void handleElementMouse() {
        this.scrollbar.handleElementMouse();
    }

    @Override
    public void handleElementUpdate() {
        this.filterTextField.handleElementUpdate();
        this.usernameTextField.handleElementUpdate();
        this.toggleIncomingRequestsButton.handleElementUpdate();
        this.addButton.handleElementUpdate();
        this.scrollbar.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.filterTextField.handleElementClose();
        this.scrollbar.handleElementClose();
    }

    @Override
    public void keyTyped(char c, int n) {
        super.keyTyped(c, n);
        this.filterTextField.keyTyped(c, n);
        this.usernameTextField.keyTyped(c, n);
        this.toggleIncomingRequestsButton.keyTyped(c, n);
        this.addButton.keyTyped(c, n);
        this.scrollbar.keyTyped(c, n);
        if (this.usernameTextField.isFocused() && n == 28) {
            this.sendRequest();
        }
        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        this.filterTextField.handleElementMouseClicked(f, f2 - this.scrollbar.getPosition(), n, bl);
        this.usernameTextField.handleElementMouseClicked(f, f2 - this.scrollbar.getPosition(), n, bl);
        if (this.filterTextField.isFocused() && n == 1 && this.filterTextField.getText().equals("")) {
            this.setElementSize();
        }
        if (!bl) {
            return false;
        }
        this.addButton.handleElementMouseClicked(f, f2 - this.scrollbar.getPosition(), n, bl);
        this.toggleIncomingRequestsButton.handleElementMouseClicked(f, f2 - this.scrollbar.getPosition(), n, bl);
        this.scrollbar.handleElementMouseClicked(f, f2, n, bl);
        if (this.addButton.isMouseInside(f, f2 - this.scrollbar.getPosition())) {
            this.sendRequest();
        }
        if (this.toggleIncomingRequestsButton.isMouseInside(f, f2 - this.scrollbar.getPosition())) {
            CheatBreaker.getInstance().getWsNetHandler().sendPacket(new WSPacketClientRequestsStatus(!CheatBreaker.getInstance().isAcceptingFriendRequests()));
            CheatBreaker.getInstance().setAcceptingFriendRequests(!CheatBreaker.getInstance().isAcceptingFriendRequests());
            return false;
        }
        boolean bl2 = false;
        for (FriendRequestElement friendRequestElement : this.elements) {
            if (!this.handleElementMouseClicked(friendRequestElement)) continue;
            if (bl2) break;
            bl2 = friendRequestElement.handleElementMouseClicked(f, f2 - this.scrollbar.getPosition(), n, bl);
        }
        return bl2;
    }

    private void sendRequest() {
        if (!this.usernameTextField.getText().isEmpty()) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            String string = this.usernameTextField.getText();
            if (string.matches("([a-zA-Z0-9_]+)") && string.length() <= 16) {
                CheatBreaker.getInstance().getWsNetHandler().sendPacket(new WSPacketFriendRequest("", this.usernameTextField.getText()));
                this.usernameTextField.setText("");
            } else {
                CBAlert.displayMessage(EnumChatFormatting.RED + "Error!", "Incorrect username.");
            }
        }
    }

    @Override
    public boolean onMouseMoved(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        this.filterTextField.onMouseMoved(f, f2 - this.scrollbar.getHeight(), n, bl);
        this.usernameTextField.onMouseMoved(f, f2 - this.scrollbar.getHeight(), n, bl);
        this.addButton.onMouseMoved(f, f2 - this.scrollbar.getHeight(), n, bl);
        this.scrollbar.onMouseMoved(f, f2 - this.scrollbar.getHeight(), n, bl);
        boolean bl2 = false;
        for (FriendRequestElement friendRequestElement : this.elements) {
            if (!this.handleElementMouseClicked(friendRequestElement)) continue;
            if (bl2) break;
            bl2 = friendRequestElement.onMouseMoved(f, f2 - this.scrollbar.getHeight(), n, bl);
        }
        return bl2;
    }

    public FlatButtonElement getAddButton() {
        return this.addButton;
    }

    public FlatButtonElement getToggleRequestsButton() {
        return this.toggleIncomingRequestsButton;
    }

    public List<FriendRequestElement> getElements() {
        return this.requestElements;
    }
}
