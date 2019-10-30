package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 设备接口：类型
 * 
 * @author hxl 1：家用插座(模式2) 2：交流接口插座（模式3，连接方式B) 3.交流接口插头(带枪线，模式3，连接方式C)
 *         4.直流接口枪头（带枪线，模式4） 5无线充电座 6：其他
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConnectorTypeEnum implements IBaseEnum {
	/**
	 * 家用插座(模式2)
	 */
	Home(1, "家用插座(模式2)"),
	/**
	 * 交流接口插座（模式3，连接方式B)
	 */
	AC(2, "交流接口插座（模式3，连接方式B)"),
	/**
	 * 交流接口插头(带枪线，模式3，连接方式C)
	 */
	ACAndLine(3, "交流接口插头(带枪线，模式3，连接方式C)"),
	/**
	 * 直流接口枪头（带枪线，模式4）
	 */
	DCAndLine(4, "直流接口枪头（带枪线，模式4）"),
	/**
	 * 无线充电座
	 */
	Wireless(5, "无线充电座"),
	/**
	 * 其它
	 */
	Other(6, "其他");
	private Integer id;

	private String name;

	ConnectorTypeEnum() {

	}

	ConnectorTypeEnum(Integer id, String name) {
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
			for (ConnectorTypeEnum o : ConnectorTypeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

}
