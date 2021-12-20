package com.kiddo.signaling_server.utils;


import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * <br/>
 * Author:Logan  Date:2019/5/24 0024 15:09
 */
public class Base64Util {

    private static BASE64Encoder base64Encoder = new BASE64Encoder();
    private static BASE64Decoder base64Decoder = new BASE64Decoder();

    /**
     * 使用base64加密,可能!!!产生换行符!!!
     */
    public static String encodeByBase64(byte[] bytes) {
        return base64Encoder.encode(bytes);
    }

    /**
     * 使用base64解密，可能产生换行符
     */
    public static byte[] decodeByBase64(String source) throws IOException {
        return base64Decoder.decodeBuffer(source);
    }


    /**
     * 换用Apache的 commons-codec.jar， Base64.encodeBase64String(byte[]）
     * 得到的编码字符串是!!!不带换行符!!!的
     */
    public static String encodeByCommons(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static void main(String[] args) {
        System.out.println(encodeByBase64("Base64.encodeBase64String(byte[]）得到的编码字符串是不带换行符的".getBytes()));
        System.out.println("---");
        System.out.println(encodeByCommons("Base64.encodeBase64String(byte[]）得到的编码字符串是不带换行符的".getBytes()));
    }
}
