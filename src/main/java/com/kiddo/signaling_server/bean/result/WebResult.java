package com.kiddo.signaling_server.bean.result;


import java.util.HashMap;
import java.util.Map;

public class WebResult {

    private boolean success = true;
    private String errorCode = "0";
    private String errorMsg;
    private String error_msg;
    private Object data;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public WebResult setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public WebResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public WebResult setData(String key, Object value) {
        Map<String, Object> dataTmp = null;
        if (data == null) {
            dataTmp = new HashMap();
        } else if (data instanceof Map) {
            dataTmp = (HashMap) data;
        }
        dataTmp.put(key, value);
        data = dataTmp;
        return this;
    }

    public WebResult setData(Object dataObj) {
        data = dataObj;
        return this;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public WebResult() {
    }
}