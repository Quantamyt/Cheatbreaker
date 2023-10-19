package com.cheatbreaker.client.module.impl.normal.misc;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ModuleAutoText extends AbstractModule {

    @Setter @Getter private String activeKeybindName = "";
    public List<Long> messageCount = new ArrayList<>();

    public final Setting command1;
    public final Setting command2;
    public final Setting command3;
    public final Setting command4;
    public final Setting command5;
    public final Setting command6;
    public final Setting command7;
    public final Setting command8;
    public final Setting command9;
    public final Setting command10;
    public final Setting command11;
    public final Setting command12;
    public final Setting command13;
    public final Setting command14;
    public final Setting command15;

    public List<Setting> hotkeys = new ArrayList<>();
//    public int amount = 0;

    public ModuleAutoText() {
        super("Auto Text");
        this.setDefaultState(false);
        this.setPreviewLabel("Auto Text", 1.0F);

        this.command1 = new Setting(this,"Hotkey 1").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command2 = new Setting(this,"Hotkey 2").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command3 = new Setting(this,"Hotkey 3").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command4 = new Setting(this,"Hotkey 4").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command5 = new Setting(this,"Hotkey 5").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command6 = new Setting(this,"Hotkey 6").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command7 = new Setting(this,"Hotkey 7").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command8 = new Setting(this,"Hotkey 8").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command9 = new Setting(this,"Hotkey 9").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command10 = new Setting(this,"Hotkey 10").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command11 = new Setting(this,"Hotkey 11").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command12 = new Setting(this,"Hotkey 12").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command13 = new Setting(this,"Hotkey 13").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command14 = new Setting(this,"Hotkey 14").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.command15 = new Setting(this,"Hotkey 15").setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.hotkeys.addAll(this.getSettingsList());

//        addOne();
    }

    /*
     * If we figure out how to update the options menu when this is pressed, we will add this in.
     * For now, there's 15 preset ones.
     * :( - Nox
     */
//    public void addOne() {
//        Setting setting = new Setting(this,"Command " + (amount + 1) + " String").setValue("/Command").setKeyCode(0);
//        this.hotkeys.put(setting, amount);
//        amount++;
//    }
}
