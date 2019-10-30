package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 设备类型
 * 
 * @author hxl
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EquipmentTypeEnum implements IBaseEnum {
	/**
	 * 直流设备
	 */
	DC(1, "直流设备"),
	/**
	 * 交流设备
	 */
	AC(2, "交流设备"),
	/**
	 * 交直流一体
	 */
	All(3, "交直流一体"),
	/**
	 * 无线设备
	 */
	Wireless(4, "无线设备"),
	/**
	 * 其它
	 */
	Other(5, "其它");
	private Integer id;

	private String name;

	EquipmentTypeEnum() {

	}

	EquipmentTypeEnum(Integer id, String name) {
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
			for (EquipmentTypeEnum o : EquipmentTypeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (EquipmentTypeEnum o : EquipmentTypeEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}

}
