package com.finance.geex.statisticslibrary.util;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created on 2019/8/28 10:48.
 * RSA加密工具类
 *
 * @author Geex302
 */
public class RSAUtil {

    public static final String RSA = "RSA";
    /**加密方式，标准jdk的*/
    public final static String RSA_CHIPER = "RSA/ECB/PKCS1Padding";

    public final static int KEY_SIZE = 1024;
    /**加密的数据最大的字节数，即117个字节*/
    public final static int ENCRYPT_KEYSIZE = 117;
    /**解密的数据最大的字节数，即128个字节*/
    public final static int DECRYPT_KEYSIZE = 128;

    public final static String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrA1llUZ9yhtd0Wv/VUi/ExMSv" +
            "c+YUUxTcU8CUNs9Py21CLJKDSA1BG4sPygbwsYw5NAgrUvjEkq3uYLAqoaY9zy4D" +
            "CdMprfRlDren/l1rNFb+kV0EsL5+ZLukIjtayz7ukKIybRbfTQS29OUD1fw5uwpv" +
            "mC8RSKCcnbqvRnxVEQIDAQAB" +
            "-----END PUBLIC KEY-----";


    // ======================================================================================
    // 公私钥算法
    // ======================================================================================

    /**
     * 公钥算法
     *
     * @param srcData   源字节
     * @param publicKey 公钥
     * @param mode      加密 OR 解密
     * @return
     */
    public static byte[] rsaByPublicKey(byte[] srcData, PublicKey publicKey, int mode){
        try {
            Cipher cipher = Cipher.getInstance(RSA_CHIPER);
            cipher.init(mode, publicKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? ENCRYPT_KEYSIZE : DECRYPT_KEYSIZE;
            byte[] encryptedData = null;
            for (int i = 0; i < srcData.length; i += blockSize) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(srcData, i, i + blockSize));
                encryptedData = addAll(encryptedData, doFinal);
            }
            return encryptedData;

        } catch (NoSuchAlgorithmException e) {
//            log.error("公钥算法-不存在的解密算法:", e);
//            throw new NoSuchAlgorithmException("公钥算法-不存在的解密算法:");
        } catch (NoSuchPaddingException e) {
//            log.error("公钥算法-无效的补位算法:", e);
//            throw new NoSuchPaddingException("公钥算法-无效的补位算法");
        } catch (IllegalBlockSizeException e) {
//            log.error("公钥算法-无效的块大小:", e);
//            throw new IllegalBlockSizeException("公钥算法-无效的块大小");
        } catch (BadPaddingException e) {
//            log.error("公钥算法-补位算法异常:", e);
//            throw new BadPaddingException("公钥算法-补位算法异常");
        } catch (InvalidKeyException e) {
//            log.error("公钥算法-无效的私钥:", e);
//            throw new InvalidKeyException("公钥算法-无效的私钥:");
        }
        return null;
    }

    /**
     * 分段密文
     *
     * @param array
     * @param startIndexInclusive
     * @param endIndexExclusive
     * @return
     */
    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;

        if (newSize <= 0) {
            return new byte[0];
        }

        byte[] subarray = new byte[newSize];

        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);

        return subarray;
    }

    /**
     * 组合密文
     *
     * @param array1
     * @param array2
     * @return
     */
    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }

    /**
     * 根据公钥字符串生成PublicKey
     * @param publicKeyTxt 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKeyTxt) {
        PublicKey publicKey = null;
        if (TextUtils.isEmpty(publicKeyTxt)) {
            return null;
        }
        try{
            publicKeyTxt = publicKeyTxt.replaceAll("-----BEGIN PUBLIC KEY-----", "");
            publicKeyTxt = publicKeyTxt.replaceAll("-----END PUBLIC KEY-----", "");
            byte[] decode = Base64.decode(publicKeyTxt,Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return publicKey;

    }



}
