package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 充电结束原因 充电结束原因: 0:用户手动停止充电; 1:客户运营平台停止充电;2:BMS停止充电; 3:充电机设备故障;4:连接器断开;5~99:自定义
 * 
 * @author hxl
 * 
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeStopTypeEnum implements IBaseEnum {
	/**
	 * 用户手动停止充电
	 */
	User(0, "用户手动停止充电"),
	/**
	 * 客户运营平台停止充电
	 */
	Operator(1, "客户运营平台停止充电"),
	/**
	 * BMS停止充电
	 */
	BMS(2, "BMS停止充电"),
	/**
	 * 充电机设备故障
	 */
	Equipment(3, "充电机设备故障"),
	/**
	 * 连接器断开
	 */
	Connector(4, "连接器断开"),
	/**
	 * 其它
	 */
	Other(5, "其它");

	private Integer id;

	private String name;

	ChargeStopTypeEnum() {

	}

	ChargeStopTypeEnum(Integer id, String name) {
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
			for (ChargeStopTypeEnum o : ChargeStopTypeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (ChargeStopTypeEnum o : ChargeStopTypeEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}
}
