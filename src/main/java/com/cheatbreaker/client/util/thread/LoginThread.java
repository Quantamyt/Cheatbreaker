package com.cheatbreaker.client.util.thread;


import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class LoginThread extends Thread {

    private String password;
    private String username;

    private Minecraft mc;
    public boolean loggedIn;
    public boolean playSound = false;

    public LoginThread() {
        this.mc = Minecraft.getMinecraft();
        loggedIn = false;
    }

    public LoginThread(String username, String password) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = username;
        this.password = password;
        loggedIn = false;
    }

    public LoginThread(String alt) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = alt.split(":")[0];
        this.password = alt.split(":")[1];
        loggedIn = false;
    }


    public Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            loggedIn = true;
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (com.mojang.authlib.exceptions.AuthenticationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        if (this.password == null || this.password.equals("")) {
            this.mc.setSession(new Session(this.username, "", "", "mojang"));
            loggedIn = true;
            return;
        }
        Session auth = createSession(this.username, this.password);
        if (auth == null) {
            loggedIn = false;
        } else {
            this.mc.setSession(auth);
            loggedIn = true;
        }
    }
}