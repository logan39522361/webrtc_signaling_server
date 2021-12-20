package com.kiddo.signaling_server.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 唯一码 工具类
 * <br/>
 * Author:Logan  Date:2019/6/4 0004 19:31
 */
public class UniqueCodeUtil {

    private static final String DATE_PATTERN = "yyyyMMddHHmmssSS";
    private static String uniquePrefix;//唯一的前缀
    private static long tmpCount = 0L;
    private static long limit = 10000L;
    //private static String currentMillsMulti = getDateInMillsString();
    private static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    static {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            //uniquePrefix = getRandomLetter() + hostAddress.replace(".", getRandomLetter()) + getRandomLetter();
            uniquePrefix = hostAddress + "_" + getProcessID() + "_";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取唯一code 每毫秒1w并发
     *
     * @return
     * @throws InterruptedException
     */
    public static synchronized String getUniqueCode() {
        while (tmpCount >= limit) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
            tmpCount = 0;
        }

        String currentMillsMulti = getDateInMillsString();

        String uniqueCode = uniquePrefix + currentMillsMulti + "_" + tmpCount;

        tmpCount++;
        return uniqueCode;
    }

    /**
     * 获取随机字母
     *
     * @return
     */
    private static String getRandomLetter() {
        Random random = new Random();
        int lengthTmp = alphabet.length;
        return alphabet[random.nextInt(lengthTmp)];
    }

    /**
     * 获取进程id
     *
     * @return
     */
    private static final int getProcessID() {
        //out put like this '18648@xx-PC'
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
    }

    /**
     * 获取当前毫秒时间格式制的，放大limit倍数
     *
     * @return
     */
    private static String getDateInMillsString() {
        String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        /*BigDecimal bigDecimal = new BigDecimal(date);
        return bigDecimal.toString();*/
        return date;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getDateInMillsString());
        System.out.println(getProcessID());

        for (int i = 0; i < 10; i++) {
            System.out.println(getUniqueCode());
        }
    }
}
