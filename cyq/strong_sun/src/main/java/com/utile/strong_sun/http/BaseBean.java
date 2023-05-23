package com.utile.strong_sun.http;

public class BaseBean<T> {
    private static final int SUCCESS_STATUS = 0;

    private String msg;
    private Integer code;
    private T data;

    public boolean isSuccess() {
        return code != null && code == SUCCESS_STATUS;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}