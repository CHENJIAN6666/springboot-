package com.newlife.s4.common.constants;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum ReturnVisitLabelEnum {

	// 事件标签
    R_4(4, "报价","#44ACE2"),
    R_0(0, "试驾","#5C81D9"),
    R_1(1, "下订","#72C13F"),
    R_2(2, "邀约","#00C6EF"),
    R_3(3, "预约试驾","#00D3C2"),
    R_5(5, "战败","#A2A2A2");

	private Integer labelID;
	private String labelName;
	private String labelColor;

	ReturnVisitLabelEnum() {
	}

	ReturnVisitLabelEnum(Integer labelID, String labelName, String labelColor) {
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
	 * @param potentialCustomerList
	 */
	public static void setCustomerLabel(JSONObject customer) {
		JSONArray lc = customer.getJSONArray("labelList");
		if (null != lc && lc.size() > 0) {
			for (int i = 0; i < lc.size(); i++) {
				for (ReturnVisitLabelEnum s : ReturnVisitLabelEnum.values()) {
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
