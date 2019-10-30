package com.newlife.s4.common.constants;

/**
 * @author: newlife
 * @date: 2017/10/24 10:16
 */
public enum ErrorEnum {
    /*
     * 系统相关的错误提示信息
     * */
    E_400("400", "请求处理异常，请稍后再试"),
    E_500("500", "请求方式有误,请检查 GET/POST"),
    E_501("501", "请求路径不存在"),
    E_502("502", "权限不足"),
    
    E_10008("10008", "角色删除失败,尚有用户属于此角色"),
    E_10009("10009", "账户已存在"),
    E_10010("10010", "账户或密码不正确"),

    E_11000("11000", "销售单已审核，不能再修改"),

    //=======租车
    E_12000("12000", "库存不足"),
    E_12001("12001", "会员审核没申请"),
    E_12002("12002", "会员审核没成功"),
    E_12003("12003", "会员审核已经申请了"),
    E_12004("12004", "租金短信提醒模板没有设置"),

    E_20011("20011", "登陆已过期,请重新登陆"),
    //=====================
    E_21001("21001", "推广码不能自己扫自己"),
    E_21002("21002", "待激活推广码"),
    E_21003("21003", "该卡片已绑定其他代理人"),
    E_21004("21004", "该卡片已绑定其他代理人"),

    E_90003("90003", "缺少必填参数"),
	
    E_90004("90004", "上传发生系统故障"),
    
    E_100001("100001", "客户因手机格式导入失败。"),

    E_100086("100086", "验证码没有失效"),
    E_100087("100087", "请使用最新的验证码！"),
    E_100088("100088", "请输入正确的手机号码！"),
    //国庆活动
    E_1001_01("100101","自己不能重复抽奖"),
    E_1001_02("100102","请不要重复提交"),
    E_1001_03("100103","当天活动已开奖了"),

    E_333("333", "余额不足"),
    E_30001("30001","该卡券已使用"),
    E_30002("30002","该卡券已过期"),
    ;



    private String errorCode;

    private String errorMsg;

    ErrorEnum() {
    }

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
