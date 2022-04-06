package com.kiddo.signaling_server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 返回动态随机的turn服务器密码
 *
 * @author lex
 */
public class TurnPassWordUtil {

    private static final Logger logger = LoggerFactory.getLogger(TurnPassWordUtil.class);

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    private static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public static String getPassWord(String turnUsername) {
        try {
            return Base64Util.encodeByCommons(hmacSHA1Encrypt(turnUsername, "north"));
        } catch (Exception e) {
            logger.error("turn服务器密码加密失败: []", e);
        }
        return null;
    }
}
