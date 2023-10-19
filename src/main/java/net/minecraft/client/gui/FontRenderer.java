package net.minecraft.client.gui;

import com.cheatbreaker.client.util.font.*;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import me.tellinq.testing.font.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.FontUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class FontRenderer implements IResourceManagerReloadListener {
    private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];

    /** Array of width of all the characters in default.png */
    private final float[] charWidth = new float[256];

    /** the height in pixels of default text */
    public int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();

    /**
     * Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory.
     */
    private final byte[] glyphWidth = new byte[65536];

    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    public int[] colorCode = new int[32];
    private ResourceLocation locationFontTexture;

    /** The RenderEngine used to load and setup glyph textures. */
    private final TextureManager renderEngine;

    /** Current X coordinate at which to draw the next character. */
    private float posX;

    /** Current Y coordinate at which to draw the next character. */
    private float posY;

    /**
     * If true, strings should be rendered with Unicode fonts instead of the default.png font
     */
    private boolean unicodeFlag;

    /**
     * If true, the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    private boolean bidiFlag;

    /** Used to specify new red value for the current color. */
    private float red;

    /** Used to specify new blue value for the current color. */
    private float blue;

    /** Used to specify new green value for the current color. */
    private float green;

    /** Used to speify new alpha value for the current color. */
    private float alpha;

    /** Text color of the currently rendering string. */
    private int textColor;

    /** Set if the "k" style (random) is active in currently rendering string */
    private boolean randomStyle;

    /** Set if the "l" style (bold) is active in currently rendering string */
    private boolean boldStyle;

    /** Set if the "o" style (italic) is active in currently rendering string */
    private boolean italicStyle;

    /**
     * Set if the "n" style (underlined) is active in currently rendering string
     */
    private boolean underlineStyle;

    /**
     * Set if the "m" style (strikethrough) is active in currently rendering string
     */
    private boolean strikethroughStyle;

    public GameSettings gameSettings;
    public ResourceLocation locationFontTextureBase;
    public boolean enabled = true;
    public float offsetBold = 1.0F;
    private ResourceLocation resourceLocation;
    private int var3;
    private int IllIlIlIllllIlIIllllIIlll;
    private boolean[] IllIIlIIlllllIllIIIlllIII;
    private final TestClassTwo lIlIlIllIIIIIIIIllllIIllI = new TestClassTwo();
    private List<TestClassOne> IlllIIlllIIIIllIIllllIlIl;
    private long IllllIllllIlIIIlIIIllllll;


    public FontRenderer(GameSettings var1, ResourceLocation var2, TextureManager var3, boolean var4) {
        this.gameSettings = var1;
        this.locationFontTextureBase = var2;
        this.locationFontTexture = var2;
        this.renderEngine = var3;
        this.unicodeFlag = var4;
        this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
        for (int var5 = 0; var5 < 32; ++var5) {
            Minecraft.getMinecraft().cbLoadingScreen.addPhase();
            int var6 = (var5 >> 3 & 1) * 85;
            int var7 = (var5 >> 2 & 1) * 170 + var6;
            int var8 = (var5 >> 1 & 1) * 170 + var6;
            int var9 = (var5 >> 0 & 1) * 170 + var6;
            if (var5 == 6) {
                var7 += 85;
            }
            if (var1.anaglyph) {
                int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                int var11 = (var7 * 30 + var8 * 70) / 100;
                int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }
            this.colorCode[var5] = (var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | var9 & 0xFF;
        }
        this.readGlyphSizes();
    }

    @Override
    public void onResourceManagerReload(IResourceManager var1) {
        this.lIlIlIllIIIIIIIIllllIIllI.clear();
        this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
        for (int var2 = 0; var2 < unicodePageLocations.length; ++var2) {
            FontRenderer.unicodePageLocations[var2] = null;
        }
        this.readFontTexture();
        this.readGlyphSizes();
    }

    private void readFontTexture() {
        BufferedImage var1;
        try {
            var1 = ImageIO.read(this.getResourceInputStream(this.locationFontTexture));
        } catch (IOException var22) {
            throw new RuntimeException(var22);
        }
        Properties var2 = FontUtils.readFontProperties(this.locationFontTexture);
        int var3 = var1.getWidth();
        int var4 = var1.getHeight();
        int var5 = var3 / 16;
        int var6 = var4 / 16;
        float var7 = (float)var3 / 128.0f;
        float var8 = Config.limit(var7, 1.0f, 2.0f);
        this.offsetBold = 1.0f / var8;
        float var9 = FontUtils.readFloat(var2, "offsetBold", -1.0f);
        if (var9 >= 0.0f) {
            this.offsetBold = var9;
        }
        int[] var10 = new int[var3 * var4];
        var1.getRGB(0, 0, var3, var4, var10, 0, var3);
        for (int var11 = 0; var11 < 256; ++var11) {
            int var15;
            int var12 = var11 % 16;
            int var13 = var11 / 16;
            boolean var14 = false;
            for (var15 = var5 - 1; var15 >= 0; --var15) {
                int var16 = var12 * var5 + var15;
                boolean var17 = true;
                for (int var18 = 0; var18 < var6 && var17; ++var18) {
                    int var19 = (var13 * var6 + var18) * var3;
                    int var20 = var10[var16 + var19];
                    int var21 = var20 >> 24 & 0xFF;
                    if (var21 <= 16) continue;
                    var17 = false;
                }
                if (!var17) break;
            }
            if (var11 == 65) {
                // empty if block
            }
            if (var11 == 32) {
                var15 = var5 <= 8 ? (int)(2.0f * var7) : (int)(1.5f * var7);
            }
            this.charWidth[var11] = (float)(var15 + 1) / var7 + 1.0f;
        }
        FontUtils.readCustomCharWidths(var2, this.charWidth);
    }

    private void readGlyphSizes() {
        try {
            InputStream var1 = this.getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
            var1.read(this.glyphWidth);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    private float renderCharAtPos(int var1, char var2, boolean var3) {
        if (var2 == ' ') {
            this.IllIIIIIIIlIlIllllIIllIII();
            return !this.unicodeFlag ? this.charWidth[var2] : 4.0f;
        }
        int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(var2);
        return i != -1 && !this.unicodeFlag ? this.renderDefaultChar(var1, var3) : this.renderUnicodeChar(var2, var3);
    }

    private float renderDefaultChar(int var1, boolean var2) {
        if (this.resourceLocation == null && !this.IllIIlIIlllllIllIIIlllIII[this.var3]) {
            this.resourceLocation = this.locationFontTexture;
            if (!Tessellator.instance.isDrawing) {
                int var3 = GL11.glGenLists(1);
                this.IlllIIlllIIIIllIIllllIlIl.add(new TestClassOne(var3, this.resourceLocation));
                GL11.glNewList(var3, 4864);
                Tessellator.instance.startDrawingQuads();
            }
        }
        if (this.resourceLocation == this.locationFontTexture) {
            float var7 = var1 % 16 * 8;
            float var4 = var1 / 16 * 8;
            float var5 = var2 ? 1.0f : 0.0f;
            float var6 = 7.99f;
            this.lIIIIllIIlIlIllIIIlIllIlI();
            Tessellator.instance.setTextureUV(var7 / 128.0f, var4 / 128.0f);
            Tessellator.instance.addVertex(this.posX + var5, this.posY, 0.0);
            Tessellator.instance.setTextureUV(var7 / 128.0f, (var4 + 7.99f) / 128.0f);
            Tessellator.instance.addVertex(this.posX - var5, this.posY + 7.99f, 0.0);
            Tessellator.instance.setTextureUV((var7 + var6 - 1.0f) / 128.0f, (var4 + 7.99f) / 128.0f);
            Tessellator.instance.addVertex(this.posX + var6 - 1.0f - var5, this.posY + 7.99f, 0.0);
            Tessellator.instance.setTextureUV((var7 + var6 - 1.0f) / 128.0f, var4 / 128.0f);
            Tessellator.instance.addVertex(this.posX + var6 - 1.0f + var5, this.posY, 0.0);
            this.IllIIIIIIIlIlIllllIIllIII();
        }
        return this.charWidth[var1];
    }

    private ResourceLocation getUnicodePageLocation(int var1) {
        if (unicodePageLocations[var1] == null) {
            FontRenderer.unicodePageLocations[var1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", var1));
            FontRenderer.unicodePageLocations[var1] = FontUtils.getHdFontLocation(unicodePageLocations[var1]);
        }
        return unicodePageLocations[var1];
    }

    private float renderUnicodeChar(char var1, boolean var2) {
        if (this.glyphWidth[var1] == 0) {
            this.IllIIIIIIIlIlIllllIIllIII();
            return 0.0f;
        }
        ResourceLocation var3 = this.getUnicodePageLocation(var1 / 256);
        int var4 = this.glyphWidth[var1] >>> 4;
        int var5 = this.glyphWidth[var1] & 0xF;
        float var6 = var4 &= 0xF;
        float var7 = var5 + 1;
        if (this.resourceLocation == null && !this.IllIIlIIlllllIllIIIlllIII[this.var3]) {
            this.resourceLocation = var3;
            if (!Tessellator.instance.isDrawing) {
                int var8 = GL11.glGenLists(1);
                this.IlllIIlllIIIIllIIllllIlIl.add(new TestClassOne(var8, this.resourceLocation));
                GL11.glNewList(var8, 4864);
                Tessellator.instance.startDrawingQuads();
            }
        }
        if (this.resourceLocation == var3) {
            float var12 = (float)(var1 % 16 * 16) + var6;
            float var9 = (var1 & 0xFF) / 16 * 16;
            float var10 = var7 - var6 - 0.02f;
            float var11 = var2 ? 1.0f : 0.0f;
            this.lIIIIllIIlIlIllIIIlIllIlI();
            Tessellator.instance.setTextureUV(var12 / 256.0f, var9 / 256.0f);
            Tessellator.instance.addVertex(this.posX + var11, this.posY, 0.0);
            Tessellator.instance.setTextureUV(var12 / 256.0f, (var9 + 15.98f) / 256.0f);
            Tessellator.instance.addVertex(this.posX - var11, this.posY + 7.99f, 0.0);
            Tessellator.instance.setTextureUV((var12 + var10) / 256.0f, (var9 + 15.98f) / 256.0f);
            Tessellator.instance.addVertex(this.posX + var10 / 2.0f - var11, this.posY + 7.99f, 0.0);
            Tessellator.instance.setTextureUV((var12 + var10) / 256.0f, var9 / 256.0f);
            Tessellator.instance.addVertex(this.posX + var10 / 2.0f + var11, this.posY, 0.0);
            this.IllIIIIIIIlIlIllllIIllIII();
        }
        return (var7 - var6) / 2.0f + 1.0f;
    }

    public int drawStringWithShadow(String var1, float var2, float var3, int var4) {
        return this.drawString(var1, var2, var3, var4, true);
    }

    public int drawCenteredStringWithShadow(String var1, int var2, int var3, int var4) {
        int var5 = this.getStringWidth(var1);
        return this.drawString(var1, var2 - var5 / 2, var3, var4, true);
    }

    public int drawCenteredStringWithShadow(String var1, float var2, float var3, int var4) {
        int var5 = this.getStringWidth(var1);
        return this.drawString(var1, var2 - (float)(var5 / 2), var3, var4, false);
    }

    public int drawString(String var1, int var2, int var3, int var4) {
        return !this.enabled ? 0 : this.drawString(var1, var2, var3, var4, false);
    }

    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
//        GL11.glEnable(GL11.GL_BLEND);

        this.enableAlpha();
        this.resetStyles();
        int i;
        if (dropShadow) {
            i = this.renderString(text, x + 1.0f, y + 1.0f, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        } else {
            i = this.renderString(text, x, y, color, false);
        }
        return i;
    }

    public void drawChromaString(String text, float xIn, float y) {
        float x = xIn;
        for (char textChar : text.toCharArray()) {
            long l = System.currentTimeMillis() - (long)(x * 50.0f - y * 50.0f);
            int i = Color.HSBtoRGB((float)(l % 10000L) / 10000.0f, 1.0f, 1.0f);
            String tmp = String.valueOf(textChar);
            this.drawString(tmp, x, y, i, true);
            x += (float)((int)((float)this.getCharWidth(textChar) * 1.0f));
        }
    }

    private String func_147647_b(String var1) {
        try {
            Bidi var2 = new Bidi(new ArabicShaping(8).shape(var1), 127);
            var2.setReorderingMode(0);
            return var2.writeReordered(2);
        } catch (ArabicShapingException var3) {
            return var1;
        }
    }

    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    private void renderStringAtPos(String p_78255_1_, boolean p_78255_2_, int var3) {
        TestClassSix var4 = this.lIlIlIllIIIIIIIIllllIIllI.lIIIIlIIllIIlIIlIIIlIIllI(p_78255_1_, var3, p_78255_2_);

        if (var4 != null) {
            var4.lIIIIlIIllIIlIIlIIIlIIllI(this.IllllIllllIlIIIlIIIllllll);
            GL11.glPushMatrix();
            GL11.glTranslatef(this.posX, this.posY, 0.0f);
            var4.lIIIIlIIllIIlIIlIIIlIIllI();
            GL11.glPopMatrix();
            this.posX += var4.IlllIIIlIlllIllIlIIlllIlI();
        } else {
            float var5 = this.posX;
            float var6 = this.posY;
            this.posX = 0.0f;
            this.posY = 0.0f;
            this.IlllIIlllIIIIllIIllllIlIl = new ArrayList();
            this.IllIIlIIlllllIllIIIlllIII = new boolean[p_78255_1_.length()];
            this.IllIlIlIllllIlIIllllIIlll = p_78255_1_.length();
            boolean var7 = false;
            while (this.IllIlIlIllllIlIIllllIIlll >= 0) {
                if (this.IllIlIlIllllIlIIllllIIlll == 0) {
                    this.IllIlIlIllllIlIIllllIIlll = -1;
                }
                this.posX = 0.0f;
                this.resourceLocation = null;
                this.textColor = -1;
                this.resetStyles();
                this.var3 = 0;
                while (this.var3 < p_78255_1_.length()) {
                    int var10;
                    int var9;
                    char var8 = p_78255_1_.charAt(this.var3);
                    if (var8 == '§' && this.var3 + 1 < p_78255_1_.length()) {
                        var9 = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(p_78255_1_.charAt(this.var3 + 1)));
                        if (var9 < 16) {
                            this.randomStyle = false;
                            this.boldStyle = false;
                            this.strikethroughStyle = false;
                            this.underlineStyle = false;
                            this.italicStyle = false;
                            if (var9 < 0 || var9 > 15) {
                                var9 = 15;
                            }
                            if (p_78255_2_) {
                                var9 += 16;
                            }
                            var10 = this.colorCode[var9];
                            if (Config.isCustomColors()) {
                                var10 = CustomColorizer.getTextColor(var9, var10);
                            }
                            this.textColor = var10;
                        } else if (var9 == 16) {
                            this.randomStyle = true;
                            var7 = true;
                        } else if (var9 == 17) {
                            this.boldStyle = true;
                        } else if (var9 == 18) {
                            this.strikethroughStyle = true;
                        } else if (var9 == 19) {
                            this.underlineStyle = true;
                        } else if (var9 == 20) {
                            this.italicStyle = true;
                        } else if (var9 == 21) {
                            this.randomStyle = false;
                            this.boldStyle = false;
                            this.strikethroughStyle = false;
                            this.underlineStyle = false;
                            this.italicStyle = false;
                            this.textColor = -1;
                        }
                        this.IllIIIIIIIlIlIllllIIllIII();
                        ++this.var3;
                        this.IllIIIIIIIlIlIllllIIllIII();
                    } else {
                        boolean var12;
                        var9 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(var8);
                        if (this.randomStyle && var9 != -1) {
                            while ((int)this.charWidth[var9] != (int)this.charWidth[var10 = this.fontRandom.nextInt(this.charWidth.length)]) {
                            }
                            var9 = var10;
                        }
                        float var11 = var9 != -1 && !this.unicodeFlag ? this.offsetBold : 0.5f;
                        boolean bl = var12 = (var8 == '\u0000' || var9 == -1 || this.unicodeFlag) && p_78255_2_;
                        if (var12) {
                            this.posX -= var11;
                            this.posY -= var11;
                        }
                        float var13 = this.renderCharAtPos(var9, var8, this.italicStyle);
                        if (var12) {
                            this.posX += var11;
                            this.posY += var11;
                        }
                        if (this.boldStyle) {
                            this.posX += var11;
                            if (var12) {
                                this.posX -= var11;
                                this.posY -= var11;
                            }
                            this.renderCharAtPos(var9, var8, this.italicStyle);
                            this.posX -= var11;
                            if (var12) {
                                this.posX += var11;
                                this.posY += var11;
                            }
                            var13 += var11;
                        }
                        if (this.IllIlIlIllllIlIIllllIIlll == -1 && (this.strikethroughStyle || this.underlineStyle)) {
                            if (!Tessellator.instance.isDrawing) {
                                int var14 = GL11.glGenLists(1);
                                this.IlllIIlllIIIIllIIllllIlIl.add(new TestClassOne(var14, null));
                                GL11.glNewList(var14, 4864);
                                Tessellator.instance.startDrawingQuads();
                            }
                            this.lIIIIllIIlIlIllIIIlIllIlI();
                            if (this.strikethroughStyle) {
                                Tessellator.instance.addVertex(this.posX, this.posY + (float)(this.FONT_HEIGHT / 2), 0.0);
                                Tessellator.instance.addVertex(this.posX + var13, this.posY + (float)(this.FONT_HEIGHT / 2), 0.0);
                                Tessellator.instance.addVertex(this.posX + var13, this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0f, 0.0);
                                Tessellator.instance.addVertex(this.posX, this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0f, 0.0);
                            }
                            if (this.underlineStyle) {
                                Tessellator.instance.addVertex(this.posX - 1.0f, this.posY + (float)this.FONT_HEIGHT, 0.0);
                                Tessellator.instance.addVertex(this.posX + var13, this.posY + (float)this.FONT_HEIGHT, 0.0);
                                Tessellator.instance.addVertex(this.posX + var13, this.posY + (float)this.FONT_HEIGHT - 1.0f, 0.0);
                                Tessellator.instance.addVertex(this.posX - 1.0f, this.posY + (float)this.FONT_HEIGHT - 1.0f, 0.0);
                            }
                        }
                        this.posX += var13;
                    }
                    ++this.var3;
                }
                if (!Tessellator.instance.isDrawing) continue;
                Tessellator.instance.draw();
                GL11.glEndList();
            }
            var4 = new TestClassSix(this.IlllIIlllIIIIllIIllllIlIl, this.posX, this.IllllIllllIlIIIlIIIllllll, var7);
            this.lIlIlIllIIIIIIIIllllIIllI.put(new TestClassThree(p_78255_1_, var3, p_78255_2_), var4);
            GL11.glPushMatrix();
            GL11.glTranslatef(var5, var6, 0.0f);
            var4.lIIIIlIIllIIlIIlIIIlIIllI();
            GL11.glPopMatrix();
            this.IlllIIlllIIIIllIIllllIlIl = null;
            this.posX += var5;
        }
    }

    private void IllIIIIIIIlIlIllllIIllIII() {
        if (!this.IllIIlIIlllllIllIIIlllIII[this.var3]) {
            this.IllIIlIIlllllIllIIIlllIII[this.var3] = true;
            --this.IllIlIlIllllIlIIllllIIlll;
        }
    }

    private void lIIIIllIIlIlIllIIIlIllIlI() {
        if (this.textColor == -1) {
            Tessellator.instance.setColorRGBA_F(this.red, this.blue, this.green, this.alpha);
        } else {
            Tessellator.instance.setColorRGBA_F((float)(this.textColor >> 16) / 255.0f, (float)(this.textColor >> 8 & 0xFF) / 255.0f, (float)(this.textColor & 0xFF) / 255.0f, this.alpha);
        }
    }

    private int renderStringAligned(String text, int x, int y, int p_78274_4_, int color, boolean dropShadow) {
        if (this.bidiFlag) {
            int i = this.getStringWidth(this.func_147647_b(text));
            x = x + p_78274_4_ - i;
        }
        return this.renderString(text, x, y, color, dropShadow);
    }

    private int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        }
        if (this.bidiFlag) {
            text = this.func_147647_b(text);
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (dropShadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        this.red = (float)(color >> 16 & 0xFF) / 255.0f;
        this.blue = (float)(color >> 8 & 0xFF) / 255.0f;
        this.green = (float)(color & 0xFF) / 255.0f;
        this.alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        this.posX = x;
        this.posY = y;
        this.renderStringAtPos(text, dropShadow, color);
        return (int)this.posX;
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        float f = 0.0f;
        boolean flag = false;
        for (int i = 0; i < text.length(); ++i) {
            char var5 = text.charAt(i);
            float var6 = this.getCharWidthFloat(var5);
            if (var6 < 0.0f && i < text.length() - 1) {
                if ((var5 = text.charAt(++i)) != 'l' && var5 != 'L') {
                    if (var5 == 'r' || var5 == 'R') {
                        flag = false;
                    }
                } else {
                    flag = true;
                }
                var6 = 0.0f;
            }
            f += var6;
            if (!flag || !(var6 > 0.0f)) continue;
            f += this.unicodeFlag ? 1.0f : this.offsetBold;
        }
        return (int)f;
    }

    public int getCharWidth(char var1) {
        return Math.round(this.getCharWidthFloat(var1));
    }

    private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
        if (p_getCharWidthFloat_1_ == 167) {
            return -1.0F;
        } else if (p_getCharWidthFloat_1_ == 32) {
            return this.charWidth[32];
        } else {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_getCharWidthFloat_1_);

            if (p_getCharWidthFloat_1_ > 0 && i != -1 && !this.unicodeFlag) {
                return this.charWidth[i];
            } else if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
                int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
                int k = this.glyphWidth[p_getCharWidthFloat_1_] & 15;
                j = j & 15;
                ++k;
                return (float) ((k - j) / 2 + 1);
            } else {
                return 0.0F;
            }
        }
    }

    public String trimStringToWidth(String var1, int var2) {
        return this.trimStringToWidth(var1, var2, false);
    }

    public String trimStringToWidth(String var1, int var2, boolean var3) {
        StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        int var6 = var3 ? var1.length() - 1 : 0;
        int var7 = var3 ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < var1.length() && var5 < (float)var2; var10 += var7) {
            char var11 = var1.charAt(var10);
            float var12 = this.getCharWidthFloat(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0f) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9) {
                    var5 += 1.0f;
                }
            }
            if (var5 > (float)var2) break;
            if (var3) {
                var4.insert(0, var11);
                continue;
            }
            var4.append(var11);
        }
        return var4.toString();
    }

    private String trimStringNewline(String var1) {
        while (var1 != null && var1.endsWith("\n")) {
            var1 = var1.substring(0, var1.length() - 1);
        }
        return var1;
    }

    public void drawSplitString(String var1, int var2, int var3, int var4, int var5) {
        this.resetStyles();
        this.textColor = var5;
        var1 = this.trimStringNewline(var1);
        this.renderSplitString(var1, var2, var3, var4, false);
    }

    private void renderSplitString(String var1, int var2, int var3, int var4, boolean var5) {
        List var6 = this.listFormattedStringToWidth(var1, var4);
        for (Object var8 : var6) {
            this.renderStringAligned((String) var8, var2, var3, var4, this.textColor, var5);
            var3 += this.FONT_HEIGHT;
        }
    }

    public int splitStringWidth(String var1, int var2) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(var1, var2).size();
    }

    public void setUnicodeFlag(boolean var1) {
        this.unicodeFlag = var1;
    }

    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }

    public void setBidiFlag(boolean var1) {
        this.bidiFlag = var1;
    }

    public List listFormattedStringToWidth(String var1, int var2) {
        return Arrays.asList(this.wrapFormattedStringToWidth(var1, var2).split("\n"));
    }

    String wrapFormattedStringToWidth(String var1, int var2) {
        int var3 = this.sizeStringToWidth(var1, var2);
        if (var1.length() <= var3) {
            return var1;
        }
        String var4 = var1.substring(0, var3);
        char var5 = var1.charAt(var3);
        boolean var6 = var5 == ' ' || var5 == '\n';
        String var7 = FontRenderer.getFormatFromString(var4) + var1.substring(var3 + (var6 ? 1 : 0));
        return var4 + "\n" + this.wrapFormattedStringToWidth(var7, var2);
    }

    private int sizeStringToWidth(String var1, int var2) {
        int var5;
        int var3 = var1.length();
        float var4 = 0.0f;
        int var6 = -1;
        boolean var7 = false;
        for (var5 = 0; var5 < var3; ++var5) {
            char var8 = var1.charAt(var5);
            switch (var8) {
                case '\n': {
                    --var5;
                    break;
                }
                case ' ': {
                    var6 = var5;
                }
                default: {
                    var4 += this.getCharWidthFloat(var8);
                    if (!var7) break;
                    var4 += 1.0f;
                    break;
                }
                case '§': {
                    char var9;
                    if (var5 >= var3 - 1) break;
                    if ((var9 = var1.charAt(++var5)) != 'l' && var9 != 'L') {
                        if (var9 != 'r' && var9 != 'R' && !FontRenderer.isFormatColor(var9)) break;
                        var7 = false;
                        break;
                    }
                    var7 = true;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > (float)var2) break;
        }
        return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
    }

    /**
     * Checks if the char code is a hexadecimal character, used to set colour.
     */
    private static boolean isFormatColor(char var0) {
        return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'f' || var0 >= 'A' && var0 <= 'F';
    }

    /**
     * Checks if the char code is O-K...lLrRk-o... used to set special formatting.
     */
    private static boolean isFormatSpecial(char var0) {
        return var0 >= 'k' && var0 <= 'o' || var0 >= 'K' && var0 <= 'O' || var0 == 'r' || var0 == 'R';
    }

    /**
     * Digests a string for nonprinting formatting characters then returns a string containing only that formatting.
     */
    private static String getFormatFromString(String var0) {
        String var1 = "";
        int var2 = -1;
        int var3 = var0.length();
        while ((var2 = var0.indexOf(167, var2 + 1)) != -1) {
            if (var2 >= var3 - 1) continue;
            char var4 = var0.charAt(var2 + 1);
            if (FontRenderer.isFormatColor(var4)) {
                var1 = "§" + var4;
                continue;
            }
            if (!FontRenderer.isFormatSpecial(var4)) continue;
            var1 = var1 + "§" + var4;
        }
        return var1;
    }

    public boolean getBidiFlag() {
        return this.bidiFlag;
    }

    protected void enableAlpha() {
        GL11.glEnable(3008);
    }

    protected InputStream getResourceInputStream(ResourceLocation var1) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(var1).getInputStream();
    }

    public void IIIIllIlIIIllIlllIlllllIl() {
        int var1 = 10;
        long var2 = this.IllllIllllIlIIIlIIIllllll - (long)(20 * var1);
        ITestClassFive var4 = this.lIlIlIllIIIIIIIIllllIIllI.IlllIIIlIlllIllIlIIlllIlI().lIIIIlIIllIIlIIlIIIlIIllI();
        while (true) {
            if (!var4.hasNext()) {
                if (this.IllllIllllIlIIIlIIIllllll % 50L == 0L) {
                    this.lIlIlIllIIIIIIIIllllIIllI.lIIIIllIIlIlIllIIIlIllIlI();
                }
                ++this.IllllIllllIlIIIlIIIllllll;
                return;
            }
            TestClassSix var5 = (TestClassSix)var4.next();
            if (!var5.IIIIllIIllIIIIllIllIIIlIl() && var5.IIIIllIlIIIllIlllIlllllIl() >= var2) continue;
            var5.lIIIIIIIIIlIllIIllIlIIlIl();
            var4.remove();
        }
    }
}
