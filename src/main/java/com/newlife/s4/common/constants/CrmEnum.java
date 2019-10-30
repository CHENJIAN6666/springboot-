package com.newlife.s4.common.constants;

public enum CrmEnum {

    /*
     * 基本资料模块的提示信息
     * */
    M_100("M100", "M100"),


    
    
    
    ;

    private String code;
    private String msg;

    CrmEnum() {
    }

    CrmEnum(String code, String msg) {
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
