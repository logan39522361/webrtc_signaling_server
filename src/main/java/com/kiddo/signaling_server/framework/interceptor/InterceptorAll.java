package com.kiddo.signaling_server.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kiddo.signaling_server.bean.result.WebResult;
import com.kiddo.signaling_server.utils.UniqueCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 权限验证拦截器
 * <br/>
 * Author:Logan  Date:2019/5/24 0024 15:09
 */
@Component
public class InterceptorAll extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());//用于打印到http文件的

    //在请求处理之前进行调用(Controller方法调用之前)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getSession().setAttribute("requestStartTime", System.currentTimeMillis());

        //设置流水号
        String flowId = UniqueCodeUtil.getUniqueCode();

        return true;
    }


    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        handleHTTPLogger(request, response);
    }

    /**
     * 打印http请求日志
     */
    private void handleHTTPLogger(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long costTime = System.currentTimeMillis() - (Long) (request.getSession().getAttribute("requestStartTime"));

        JSONObject headerJSONObject = new JSONObject();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headerJSONObject.put(name, value);
        }

        JSONObject paramJSONObject = new JSONObject();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            paramJSONObject.put(name, value);
        }


        WebResult responseResult = APIResponseBodyAdvice.getResponseBody();
        String result = JSONObject.toJSONString(responseResult);

        //logger.info("uri=[{}] costTime=[{}]\nheader=[{}]\nparams=[{}]\nresponse=[{}]", request.getRequestURI(), costTime, headerJSONObject.toJSONString(), paramJSONObject.toJSONString(), result);
    }

}