package com.newlife.s4.common.constants;

public enum FollowBusinessTypeEnum {

    /*
     * 跟进业务类型：0试乘试驾、1下订、2邀约预报、3预约试驾、4报价
     * */
	B_4("4", "报价"),
    B_0("0", "试驾"),
    B_1("1", "下订"),
    B_2("2", "邀约"),
    B_3("3", "预约试驾"),
    B_5("5", "战败");


    private String code;
    private String text;

    FollowBusinessTypeEnum() {
    }

    FollowBusinessTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getFollowBusinessTypeCode() {
        return code;
    }

    public void setFollowBusinessTypeCode(String code) {
        this.code = code;
    }

    public String getFollowBusinessTypeText() {
        return text;
    }

    public void setFollowBusinessTypeText(String text) {
        this.text = text;
    }
}
