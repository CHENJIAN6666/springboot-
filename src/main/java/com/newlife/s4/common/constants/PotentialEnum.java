package com.newlife.s4.common.constants;

/**
 * 来电到店
 * @author Administrator
 *
 */
public enum PotentialEnum {

	V_1(1, "微信关注"), V_2(2, "客户来电"), V_3(3, "客户到店"), V_4(4, "上门开拓"), V_5(5,
			"邀约到店"), V_6(6, "销售登记询价"), V_7(7, "销售登记预约"), V_8(8, "微信询价"), V_9(9,
			"微信预约");

	private Integer visitID;
	private String name;

	PotentialEnum(Integer visitID, String name) {
		this.visitID = visitID;
		this.name = name;
	}

	public Integer getVisitID() {
		return visitID;
	}

	public void setVisitID(Integer visitID) {
		this.visitID = visitID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
