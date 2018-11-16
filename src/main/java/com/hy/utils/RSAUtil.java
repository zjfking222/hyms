package com.hy.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/14 16:46
 * @Description:从网上搜到的RSA加密解密工具
 */
public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * @Author 钱敏杰
     * @Description base64解密
     * @Date 2018/11/15 9:05
     * @Param [key]
     * @return byte[]
     **/
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * @Author 钱敏杰
     * @Description base64加密
     * @Date 2018/11/15 9:06
     * @Param [bytes]
     * @return java.lang.String
     **/
    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @Author 钱敏杰
     * @Description 用私钥对信息生成数字签名
     * @Date 2018/11/15 9:06
     * @Param [data, privateKey]
     * @return java.lang.String
     **/
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * @Author 钱敏杰
     * @Description 校验数字签名
     * @Date 2018/11/15 9:07
     * @Param [data, publicKey, sign]
     * @return boolean
     **/
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * @Author 钱敏杰
     * @Description 用私钥解密数据
     * @Date 2018/11/15 9:07
     * @Param [data, key]
     * @return byte[]
     **/
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * @Author 钱敏杰
     * @Description 用私钥解密base64数据
     * @Date 2018/11/15 9:08
     * @Param [data, key]
     * @return byte[]
     **/
    public static byte[] decryptByPrivateKey(String data, String key)throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }

    /**
     * @Author 钱敏杰
     * @Description 用公钥解密
     * @Date 2018/11/15 9:09
     * @Param [data, key]
     * @return byte[]
     **/
    public static byte[] decryptByPublicKey(byte[] data, String key)throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * @Author 钱敏杰
     * @Description 用公钥加密
     * @Date 2018/11/15 9:09
     * @Param [data, key]
     * @return byte[]
     **/
    public static byte[] encryptByPublicKey(String data, String key)throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * @Author 钱敏杰
     * @Description 用私钥加密
     * @Date 2018/11/15 9:09
     * @Param [data, key]
     * @return byte[]
     **/
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * @Author 钱敏杰
     * @Description 取得私钥
     * @Date 2018/11/15 9:10
     * @Param [keyMap]
     * @return java.lang.String
     **/
    public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * @Author 钱敏杰
     * @Description 取得公钥
     * @Date 2018/11/15 9:10
     * @Param [keyMap]
     * @return java.lang.String
     **/
    public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * @Author 钱敏杰
     * @Description 生成密钥
     * @Date 2018/11/15 9:10
     * @Param []
     * @return java.util.Map<java.lang.String,java.security.Key>
     **/
    public static Map<String, Key> initKey(int size) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(size);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }

    /*public static void main(String[] args)throws Exception{
        Map<String, Key> keyMap = initKey(512);
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);

        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] encodedData = encryptByPublicKey(inputStr, publicKey);
        byte[] decodedData = decryptByPrivateKey(encodedData, privateKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
    }*/
}