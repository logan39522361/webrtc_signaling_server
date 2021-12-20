package com.kiddo.signaling_server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test01 {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");

        map.remove("1");

        System.out.println(JSONObject.toJSONString(map));
    }
}
