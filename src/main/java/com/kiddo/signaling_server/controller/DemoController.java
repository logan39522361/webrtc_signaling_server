package com.kiddo.signaling_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.kiddo.signaling_server.bean.result.WebResult;
import com.kiddo.signaling_server.utils.TurnPassWordUtil;
import com.kiddo.signaling_server.websocket.WebSocketDemo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/demohttp/")
public class DemoController {

    @ResponseBody
    @RequestMapping(value = "/getUserList.json")
    public WebResult getUserList() {
        WebResult webResult = new WebResult();

        List list = new LinkedList();

        Map<String, Session> sessionMap = WebSocketDemo.getSessionMap();
        for (Map.Entry<String, Session> sessionEntry : sessionMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", sessionEntry.getKey());
            list.add(jsonObject);
        }

        webResult.setData(list);
        return webResult;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoomList.json")
    public WebResult getRoomList() {
        WebResult webResult = new WebResult();
        return webResult;
    }

}