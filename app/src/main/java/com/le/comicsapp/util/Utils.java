package com.le.comicsapp.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static String TAG = "Utils";

    public static String md5HashOf(@NonNull String message) {
        try {
            // MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(message.getBytes());
            byte digestBytes[] = digest.digest();
            // Make it a hex string
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digestBytes.length; i++) {
                String h = Integer.toHexString(0xFF & digestBytes[i]);
                if (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return "";

    }
}
