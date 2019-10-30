package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 由设备平台上传
 * @云充订单状态
 * 充电订单状态:1:启动中;2:充电中;3:停止中;4:已结束;5:未知
 * @author hxl  
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeOrderStatusEnum implements IBaseEnum {
	/**
	 * 启动中
	 */
	Starting(1, "启动中"),
	/**
	 * 充电中
	 */
	Charging(2, "充电中"),
	/**
	 * 停止中
	 */
	Stoping(3, "停止中"),  
	/**
	 * 已结束
	 */
	End(4, "已结束"),  
	/**
	 * 未知
	 */
	Other(5, "未知");
	private Integer id;

	private String name;

	ChargeOrderStatusEnum() {

	}

	ChargeOrderStatusEnum(Integer id, String name) {
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
			for (ChargeOrderStatusEnum o : ChargeOrderStatusEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (ChargeOrderStatusEnum o : ChargeOrderStatusEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}
}
