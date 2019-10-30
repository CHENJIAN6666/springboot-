package com.newlife.s4.common.constants.yc;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 充电站点：状态
 * 
 * @author hxl
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StationStatusEnum implements IBaseEnum {
	/**
	 * 未知
	 */
	UNKNOWN(0, "未知"),
	/**
	 * 建设中
	 */
	InMaking(1, "建设中"),
	/**
	 * 关闭下线
	 */
	Closed(5, "关闭下线"),
	/**
	 * 维护中
	 */
	Repairing(6, "维护中"),
	/**
	 * 物流(专用)
	 */
	Normal(50, "正常使用");
	private Integer id;

	private String name;

	StationStatusEnum() {

	}

	StationStatusEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String toString() {
		return String.valueOf(this.id);
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Integer getOrderState(String desc) {
		if (!StringTools.isNullOrEmpty(desc)) {
			for (StationStatusEnum o : StationStatusEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (StationStatusEnum o : StationStatusEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}

//	public static List<JSONObject> getOrderList(){
//		List<JSONObject> list = new ArrayList<>();
//		for (StationStatusEnum o : StationStatusEnum.values()) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("label",o.getName());
//			jsonObject.put("value",o.getId());
//			list.add(jsonObject);
//		}
//		return list;
//	}

}
