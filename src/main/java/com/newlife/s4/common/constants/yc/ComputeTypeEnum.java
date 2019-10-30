package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 计费方式：
 * 
 * @author hxl 
 *  计费方式:0自动充满;1:按金额充;2:按电量;3:按时间
 * 
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ComputeTypeEnum implements IBaseEnum {
	/**
	 * 自动充满
	 */
	Auto(0, "自动充满"),
	/**
	 * 按金额充
	 */
	ByMoney(1, "按金额充"),
	/**
	 * 按电量
	 */
	ByElec(2, "按电量"),
	/**
	 * 按时间
	 */
	ByTime(3, "按时间");
	
	private Integer id;

	private String name;

	ComputeTypeEnum() {

	}

	ComputeTypeEnum(Integer id, String name) {
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
			for (ComputeTypeEnum o : ComputeTypeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

	public static String getOrderStateDesc(Integer id) {
		if (!StringTools.isNullOrEmpty(id)) {
			for (ComputeTypeEnum o : ComputeTypeEnum.values()) {
				if (o.getId().equals(id)) {
					return o.getName();
				}
			}
		}
		return null;
	}

}
