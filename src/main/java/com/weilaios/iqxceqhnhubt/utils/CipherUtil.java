package com.weilaios.iqxceqhnhubt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class CipherUtil {

    protected static Logger log = LoggerFactory.getLogger(CipherUtil.class);

    private static char[] HEXCHARS_L = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static char[] HEXCHARS_C = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public CipherUtil() {
    }

    public static String encryptMD5(String str, boolean lower) {
        try {
            byte[] bytes = str.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            bytes = md5.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;

            for (int i = 0; i < bytes.length; ++i) {
                byte b = bytes[i];
                if (lower) {
                    chars[k++] = HEXCHARS_L[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_L[b & 15];
                } else {
                    chars[k++] = HEXCHARS_C[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_C[b & 15];
                }
            }

            return new String(chars);
        } catch (NoSuchAlgorithmException var9) {
            log.error("CipherUtil error message:{}", var9);
            return null;
        }
    }

    public static String encryptSHA(String str, boolean lower) {
        try {
            byte[] bytes = str.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(bytes);
            bytes = sha.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;

            for (int i = 0; i < bytes.length; ++i) {
                byte b = bytes[i];
                if (lower) {
                    chars[k++] = HEXCHARS_L[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_L[b & 15];
                } else {
                    chars[k++] = HEXCHARS_C[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_C[b & 15];
                }
            }

            return new String(chars);
        } catch (NoSuchAlgorithmException var9) {
            log.error("CipherUtil error message:{}", var9);
            return null;
        }
    }

    public static String encryptSHA1(String str, boolean lower) {
        try {
            byte[] bytes = str.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(bytes);
            bytes = sha.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;

            for (int i = 0; i < bytes.length; ++i) {
                byte b = bytes[i];
                if (lower) {
                    chars[k++] = HEXCHARS_L[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_L[b & 15];
                } else {
                    chars[k++] = HEXCHARS_C[b >>> 4 & 15];
                    chars[k++] = HEXCHARS_C[b & 15];
                }
            }

            return new String(chars);
        } catch (NoSuchAlgorithmException var9) {
            log.error("CipherUtil error message:{}", var9);

            return null;
        }
    }

    public static String encryptHMACSHA256(byte[] data, char[] key) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(key);

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA1AndRC2_40");
            SecretKey deskey = keyFactory.generateSecret(pbeKeySpec);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(deskey);
            byte[] digest = mac.doFinal(data);
            return new String(Base64.getEncoder().encode(digest));
        } catch (NoSuchAlgorithmException var7) {
            log.error("CipherUtil error message:{}", var7);
            return null;
        } catch (InvalidKeySpecException var8) {
            log.error("CipherUtil error message:{}", var8);

            return null;
        } catch (InvalidKeyException var9) {
            log.error("CipherUtil error message:{}", var9);
            return null;
        }
    }
}
