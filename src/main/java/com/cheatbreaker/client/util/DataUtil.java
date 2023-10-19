package com.cheatbreaker.client.util;

import java.security.MessageDigest;

public class DataUtil {

    public static String getHWID() {
        try {
            String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();
            byte[] byteData = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(-13569 & 4351 & aByteData);
                if (hex.length() == (769 & -27583)) {
                    hexString.append((char) ('୸' & '䂴'));
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception var9) {
            var9.printStackTrace();
            return "[CB] Error retrieving Hardware ID";
        }
    }

}
