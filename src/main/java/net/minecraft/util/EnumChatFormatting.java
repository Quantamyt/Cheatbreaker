package net.minecraft.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r');

    /**
     * Maps a formatting code (e.g., 'f') to its corresponding enum value (e.g., WHITE).
     */
    private static final Map formattingCodeMapping = new HashMap();

    /**
     * Maps a name (e.g., 'underline') to its corresponding enum value (e.g., UNDERLINE).
     */
    private static final Map nameMapping = new HashMap();

    /**
     * Matches formatting codes that indicate that the client should treat the following text as bold, recolored,
     * obfuscated, etc.
     */
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + '\u00a7' + "[0-9A-FK-OR]");
    private static final Pattern formattingCodePatternReversed = Pattern.compile("[0-9A-FK-OR]" + '\u00a7' + "(?i)");


    /** The formatting code that produces this format. */
    private final char formattingCode;
    private final boolean fancyStyling;

    /**
     * The control string (section sign + formatting code) that can be inserted into com.cheatbreaker.client-side text to display
     * subsequent text in this format.
     */
    private final String controlString;

    /**
     * CheatBreaker - Matches formatting codes that indicate that the client should treat the following text as recolored.
     */
    private static final Pattern CBformattingCodePattern = Pattern.compile("(?i)" + '§' + "[0-9A-FR]");


    EnumChatFormatting(char p_i1336_3_) {
        this(p_i1336_3_, false);
    }

    EnumChatFormatting(char p_i1337_3_, boolean p_i1337_4_) {
        this.formattingCode = p_i1337_3_;
        this.fancyStyling = p_i1337_4_;
        this.controlString = "\u00a7" + p_i1337_3_;
    }

    /**
     * Gets the formatting code that produces this format.
     */
    public char getFormattingCode() {
        return this.formattingCode;
    }

    /**
     * False if this is just changing the color or resetting; true otherwise.
     */
    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    /**
     * Checks if typo is a color.
     */
    public boolean isColor() {
        return !this.fancyStyling && this != RESET;
    }

    /**
     * Gets the friendly name of this value.
     */
    public String getFriendlyName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.controlString;
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    public static String getTextWithoutFormattingCodes(String p_110646_0_) {
        return p_110646_0_ == null ? null : formattingCodePattern.matcher(p_110646_0_).replaceAll("");
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    public static String getTextWithoutFormattingCodesReversed(String p_110646_0_) {
        return p_110646_0_ == null ? null : formattingCodePattern.matcher(p_110646_0_).replaceAll("");
    }


    /**
     * Gets a value by its friendly name; null if the given name does not map to a defined value.
     */
    public static EnumChatFormatting getValueByName(String p_96300_0_) {
        return p_96300_0_ == null ? null : (EnumChatFormatting)nameMapping.get(p_96300_0_.toLowerCase());
    }

    /**
     * Gets all the valid values. Args: @param par0: Whether or not to include color values. @param par1: Whether or not
     * to include fancy-styling values (anything that isn't a color value or the "reset" value).
     */
    public static Collection getValidValues(boolean p_96296_0_, boolean p_96296_1_) {
        ArrayList var2 = new ArrayList();
        EnumChatFormatting[] var3 = values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            EnumChatFormatting var6 = var3[var5];

            if ((!var6.isColor() || p_96296_0_) && (!var6.isFancyStyling() || p_96296_1_)) {
                var2.add(var6.getFriendlyName());
            }
        }

        return var2;
    }

    static {
        EnumChatFormatting[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            EnumChatFormatting var3 = var0[var2];
            formattingCodeMapping.put(Character.valueOf(var3.getFormattingCode()), var3);
            nameMapping.put(var3.getFriendlyName(), var3);
        }
    }

    public static String formatColor(String input) {
        Matcher matcher = CBformattingCodePattern.matcher(input);
        String group = "";
        while (matcher.find()) {
            group = matcher.group();
        }
        return group;
    }
}
