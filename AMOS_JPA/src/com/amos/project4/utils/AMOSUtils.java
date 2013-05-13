package com.amos.project4.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AMOSUtils {

	public static String makeMD5(String word) throws NoSuchAlgorithmException {
        /* Berechnung */
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(word.getBytes());
        byte[] result = md5.digest();

        /* Ausgabe */
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<result.length; i++) {
            hexString.append(Integer.toHexString(0xFF & result[i]));
        }
        return hexString.toString();
    }

}
