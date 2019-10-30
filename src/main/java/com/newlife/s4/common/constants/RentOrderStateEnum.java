package com.newlife.s4.common.constants;

public enum RentOrderStateEnum {
    CREATE("C","创建"),
    PAY("P","支付租金"),
    APPLY_REFUND("A","申请退款"),
    GG("G","取消订单"),
    EXCEPTION("E","异常"),
    DELIVER("D","交车"),
    REFUND("R","已退款"),
    SUCCESS("S","完成");

    private String state;
    private String desc;

    RentOrderStateEnum(String state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
