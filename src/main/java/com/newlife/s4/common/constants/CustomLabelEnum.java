package com.newlife.s4.common.constants;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum CustomLabelEnum {

	// 潜客标签
	L1(1, "报价", "4EA3D8"), L2(2, "邀约", "5BC4E8"), L3(3, "预约试驾", "67CCBF"), L4(
			4, "试驾", "5D8BCF"), L5(5, "下订", "87BB4C"), L6(6, "邀约到店", "F0A154"), L7(
			7, "邀约未到店", "F3B54F"), L8(8, "首次到店", "9FA3ED"), L9(9, "多次到店",
			"698CF6"), L10(10, "试驾未到店", "F3B54F"), L11(11, "成交", "6ECC5A"), L12(
			12, "取消订单", "EB7E51"), L13(13, "当日回访", "D45DA7"), L14(14, "申请战败",
			"EB807A"), L15(15, "已战败", "7D7D7D"), L16(16, "三日回访", "C14FBD"), L17(
			17, "七日回访", "943A90"), L18( 18, "申请退订", "EB807A")
			, L19( 19, "已退订", "7D7D7D")

	;

	private Integer labelID;
	private String labelName;
	private String labelColor;

	CustomLabelEnum() {
	}

	CustomLabelEnum(Integer labelID, String labelName, String labelColor) {
		this.labelID = labelID;
		this.labelName = labelName;
		this.labelColor = labelColor;

	}

	public Integer getLabelID() {
		return labelID;
	}

	public void setLabelID(Integer labelID) {
		this.labelID = labelID;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	/**
	 * 将 潜客记录 的 添加 label 名称 label 颜色 List<JSONObject>
	 * 
	 * @param potentialCustomerList
	 */
	public static void setCustomerLabel(List<JSONObject> potentialCustomerList) {
		for (JSONObject customer : potentialCustomerList) {
			setCustomerLabel(customer);
		}
	}

	/**
	 * 将 潜客记录 的 添加 label 名称 label 颜色 JSONObject
	 * 
	 * @param
	 */
	public static void setCustomerLabel(JSONObject customer) {
		JSONArray lc = customer.getJSONArray("labelList");
		if (null != lc && lc.size() > 0) {
			for (int i = 0; i < lc.size(); i++) {
				for (CustomLabelEnum s : CustomLabelEnum.values()) {
					if (s.getLabelID().equals(
							lc.getJSONObject(i).getInteger("labelID"))) {
						lc.getJSONObject(i).put("labelName", s.getLabelName());
						lc.getJSONObject(i)
								.put("labelColor", s.getLabelColor());
					}
				}
			}
		}
	}

}
