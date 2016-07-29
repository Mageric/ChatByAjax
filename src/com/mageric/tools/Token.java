package com.mageric.tools;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Token {
    public static String generateToken(int length) {
        SecureRandom random = null;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException nsa) {
            try {
                random = SecureRandom.getInstance("IBMSecureRandom");
            } catch (NoSuchAlgorithmException nsa1) {
                System.out.println(nsa1);
            }
        }

        char[] chars = new char[length];
        int clength=chars.length;
        for(int i=0;i<clength;i++){
            int v = random.nextInt(10 + 26 + 26);
            char c;
            if (v < 10) c = (char)('0' + v);
            else if (v < 36) c = (char)('a' - 10 + v);
            else c = (char)('A' - 36 + v);
            chars[i] = c;
        }
        return new String(chars);
    }
}
