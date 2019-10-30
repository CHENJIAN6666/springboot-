package com.newlife.s4.common.constants;

public enum BaseEnum {

    /*
     * 基本资料模块的提示信息
     * */
    B_100("B100", "B100");


    private String code;
    private String msg;

    BaseEnum() {
    }

    BaseEnum(String code, String msg) {
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
