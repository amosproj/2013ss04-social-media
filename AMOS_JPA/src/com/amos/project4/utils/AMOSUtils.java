/*
 *
 * This file is part of the Datev and Social Media project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
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
        	hexString.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            //hexString.append(Integer.toHexString(0xFF & result[i]));
        }
        return hexString.toString();
    }

}
