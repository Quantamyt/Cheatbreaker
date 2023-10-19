package com.cheatbreaker.client.module.command.impl;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.command.ModuleCommand;
import com.cheatbreaker.client.module.impl.normal.vanilla.ModuleEnvironmentChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DecreaseTimeCommand extends ModuleCommand {

    public DecreaseTimeCommand() {
        super(CheatBreaker.getInstance().getModuleManager().environmentChangerMod, "/cb_decrease_time");
    }

    @Override
    public void handle() {
        long decreaseAmount = Integer.parseInt(((ModuleEnvironmentChanger)this.mod).worldTime.getValue().toString()) - ((ModuleEnvironmentChanger)this.mod).increaseDecreaseAmount.getIntegerValue();

        if (((ModuleEnvironmentChanger)this.mod).timeType.getStringValue().equalsIgnoreCase("Static")) {
            if (Minecraft.getMinecraft().theWorld != null) {
                if (decreaseAmount < -22880) {
                    return;
                }
                ((ModuleEnvironmentChanger)this.mod).worldTime.setValue(Integer.parseInt(((ModuleEnvironmentChanger)this.mod).worldTime.getValue().toString())
                        - ((ModuleEnvironmentChanger)this.mod).increaseDecreaseAmount.getIntegerValue());
                Minecraft.getMinecraft().theWorld.setWorldTime(decreaseAmount);

                String msg = "Decreased time by " + CheatBreaker.getInstance().getModuleManager().environmentChangerMod.increaseDecreaseAmount.getIntegerValue() + ".";
                CheatBreaker.getInstance().getModuleCommandManager().sendModCommandCallback(new ChatComponentText(msg));
            }
        } else {
            String msg = EnumChatFormatting.RED + "This mod command only works with the \"Static\" time type.";
            CheatBreaker.getInstance().getModuleCommandManager().sendModCommandCallback(new ChatComponentText(msg));
        }
    }
}
