package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.CheatBreaker;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @Thread WSPacketClientSyncThread
 * @see WSPacketClientSync
 *
 * This thread sends the com.cheatbreaker.client.network.websocket.client.WSPacketClientSync packet every so often.
 */
public class WSPacketClientSyncThread implements Runnable {
    private static final int lIIIIlIIllIIlIIlIIIlIIllI = "Lo6a$DMR".length() * "aAO20DQ6iIlP".length();
    private static final int lIIIIIIIIIlIllIIllIlIIlIl = lIIIIlIIllIIlIIlIIIlIIllI * "yh9bV53gfZv4tBa49MF2G".length() - 16;
    private static final int IlllIIIlIlllIllIlIIlllIlI = "u2CXyEg4Fy32".length() - 2;
    private static final double IIIIllIlIIIllIlllIlllllIl = 8000;
    private static final double IIIIllIIllIIIIllIllIIIlIl = 3;
    private static final double IlIlIIIlllIIIlIlllIlIllIl = 6;
    private static final double IIIllIllIlIlllllllIlIlIII = 20;
    private static final double IllIIIIIIIlIlIllllIIllIII = 1.0;
    private Stopwatch lIIIIllIIlIlIllIIIlIllIlI;

    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    boolean bl = this.lIIIIllIIlIlIllIIIlIllIlI == null || this.lIIIIllIIlIlIllIIIlIllIlI.elapsed(TimeUnit.MINUTES) >= 10L;
                    boolean bl2 = false;
                    if (this.lIIIIlIIllIIlIIlIIIlIIllI() && bl) {
                        bl2 = this.lIIIIlIIllIIlIIlIIIlIIllI(0, IIIIllIlIIIllIlllIlllllIl);
                    }
                    if (this.isFancyStyling() && bl) {
                        bl2 = this.lIIIIlIIllIIlIIlIIIlIIllI(1, IIIIllIIllIIIIllIllIIIlIl);
                    }
                    if (this.onValidSurface() && bl) {
                        bl2 = this.lIIIIlIIllIIlIIlIIIlIIllI(2, IlIlIIIlllIIIlIlllIlIllIl);
                    }
                    if (this.IIIIllIlIIIllIlllIlllllIl() && bl) {
                        bl2 = this.lIIIIlIIllIIlIIlIIIlIIllI(3, IIIllIllIlIlllllllIlIlIII);
                    }
                    if (this.IIIIllIIllIIIIllIllIIIlIl() && bl) {
                        bl2 = this.lIIIIlIIllIIlIIlIIIlIIllI(4, IllIIIIIIIlIlIllllIIllIII);
                    }
                    if (bl2) {
                        if (this.lIIIIllIIlIlIllIIIlIllIlI == null) {
                            this.lIIIIllIIlIlIllIIIlIllIlI = Stopwatch.createStarted();
                        } else {
                            this.lIIIIllIIlIlIllIIIlIllIlI.reset();
                            this.lIIIIllIIlIlIllIIIlIllIlI.start();
                        }
                    }
                    Thread.sleep(TimeUnit.SECONDS.toMillis(30L));
                }
            } catch (Exception exception) {
                continue;
            }
        }
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI(int n, double d) {
        if (CheatBreaker.getInstance() != null && CheatBreaker.getInstance().getWsNetHandler() != null) {
            CheatBreaker.getInstance().getWsNetHandler().sendPacket(new WSPacketClientSync(n, d));
            return true;
        }
        return false;
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI() {
        return IIIIllIlIIIllIlllIlllllIl != (double) lIIIIIIIIIlIllIIllIlIIlIl * (double) 4;
    }

    private boolean isFancyStyling() {
        return IIIIllIIllIIIIllIllIIIlIl != (double) (lIIIIlIIllIIlIIlIIIlIIllI >> 5);
    }

    private boolean onValidSurface() {
        return IlIlIIIlllIIIlIlllIlIllIl != (double) (lIIIIlIIllIIlIIlIIIlIIllI >> 4);
    }

    private boolean IIIIllIlIIIllIlllIlllllIl() {
        return IIIllIllIlIlllllllIlIlIII != (double) (IlllIIIlIlllIllIlIIlllIlI * 2);
    }

    private boolean IIIIllIIllIIIIllIllIIIlIl() {
        return IllIIIIIIIlIlIllllIIllIII != (double) (IlllIIIlIlllIllIlIIlllIlI / 10);
    }
}
