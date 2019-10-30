package com.newlife.s4.common.constants;

public enum CommonEnum {

    /*
     * 系统相关的通用提示信息
     * */
    C_100("100", "请求成功");


    private String code;
    private String msg;

    CommonEnum() {
    }

    CommonEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
