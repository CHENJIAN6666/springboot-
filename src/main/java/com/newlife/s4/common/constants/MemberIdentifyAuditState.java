package com.newlife.s4.common.constants;

public enum  MemberIdentifyAuditState {
    APPLY(0,"审核中"),
    SUCCESS(1,"通过"),
    FAILURE(2,"驳回");
    private Integer state;
    private String desc;

    MemberIdentifyAuditState(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
