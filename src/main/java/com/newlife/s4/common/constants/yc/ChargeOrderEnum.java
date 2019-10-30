package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 云充订单：状态
 * 
 * @author hxl 订单状态:0:生成订单;1:已插枪;2:充电配置;3:充电中;4:已拔枪;5:已结束6:未知
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChargeOrderEnum implements IBaseEnum {

	/**
	 * 已插枪
	 */
	Connectioned(0, "已插枪"),
	/**
	 * 充电配置
	 */
	Config(1, "充电配置"),
	/**
	 * 充电中
	 */
	Charging(2, "充电中"),
	/**
	 * 已拔枪
	 */
	unConnection(3, "已拔枪"),
	/**
	 * 已结束
	 */
	End(4, "已结束");

	private Integer id;

	private String name;

	ChargeOrderEnum() {

	}

	ChargeOrderEnum(Integer id, String name) {
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
			for (ChargeOrderEnum o : ChargeOrderEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

}
