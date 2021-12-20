package com.kiddo.signaling_server.framework.interceptor;

import com.kiddo.signaling_server.bean.result.WebResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <br/>
 * Author:Logan  Date:2019/6/27 0027 12:12
 */
@ControllerAdvice
public class APIResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static ThreadLocal<WebResult> threadLocal = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body != null && body instanceof WebResult) {
            threadLocal.set((WebResult)body);
        }
        return body;
    }

    public static WebResult getResponseBody() {
    	WebResult objTmp = threadLocal.get();
        //清除掉 以防止线程池的线程id重复使用 下一次的请求进来使用了上一次的线程id
        threadLocal.remove();
        return objTmp;
    }

}