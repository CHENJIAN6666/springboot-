package com.newlife.s4.common.constants;

public enum CarRentStateEnum {
    DELIVER("D","交车"),
    EXCEPTION("E","异常"),
    RETURN_CAR("R","还车");

    private String state;
    private String desc;

    CarRentStateEnum(String state, String desc) {
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
