package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 充电站点类型 1：公共; 50:个人 100：公交（专用） 101：环卫（专用） 102：物流（专用） 103：出租车（专用） 255：其他
 * 
 * @author hxl
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StationTypeEnum implements IBaseEnum {
	/**
	 * 公共
	 */
	Public(1, "公共"),
	/**
	 * 个人
	 */
	Private(50, "个人"),
	/**
	 * 公交(专用)
	 */
	Bus(100, "公交(专用)"),
	/**
	 * 环卫(专用)
	 */
	HuanWei(101, "环卫(专用)"),
	/**
	 * 物流(专用)
	 */
	WuLiu(102, "物流(专用)"),
	/**
	 * 出租车(专用)
	 */
	Taxi(103, "出租车(专用)"),
	/**
	 * 其他
	 */
	Other(255, "其他");
	private Integer id;

	private String name;

	StationTypeEnum() {

	}

	StationTypeEnum(Integer id, String name) {
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
			for (StationTypeEnum o : StationTypeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (StationTypeEnum o : StationTypeEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}

}
