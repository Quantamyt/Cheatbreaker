package com.cheatbreaker.client.util.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataTransformer {

    public static Map<Character, String> hexBinValues = new HashMap<>();

    public static String getASCIIFromHex(String hexIn) {
        return convertBinaryToASCII(getBinaryFromHexData(hexIn));
    }

    public static String getBinaryFromHexData(String hexIn) {
        char character;
        StringBuilder translatedHex = new StringBuilder();
        hexIn = hexIn.toUpperCase();

        for (int i = 0; i < hexIn.length(); i++) {
            character = hexIn.charAt(i);
            if (hexBinValues.containsKey(character)) {
                translatedHex.append(hexBinValues.get(character));
            }
        }

        return translatedHex.toString();
    }

    public static String convertBinaryToASCII(String binaryIn) {
        return Arrays.stream(binaryIn.split("(?<=\\G.{8})"))
                .parallel()
                .map(eightBits -> (char) Integer.parseInt(eightBits, 2))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    static {
        hexBinValues.put('0', "0000");
        hexBinValues.put('1', "0001");
        hexBinValues.put('2', "0010");
        hexBinValues.put('3', "0011");
        hexBinValues.put('4', "0100");
        hexBinValues.put('5', "0101");
        hexBinValues.put('6', "0110");
        hexBinValues.put('7', "0111");
        hexBinValues.put('8', "1000");
        hexBinValues.put('9', "1001");
        hexBinValues.put('A', "1010");
        hexBinValues.put('B', "1011");
        hexBinValues.put('C', "1100");
        hexBinValues.put('D', "1101");
        hexBinValues.put('E', "1110");
        hexBinValues.put('F', "1111");
    }
}
