package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 充电设备接口：状态
 * 
 * @author hxl 接口状态：0：离网;1:空闲;2：占用(未充电）；3：占用(充电中); 4:占用（预约锁定）; 255:故障
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConnectorStatusEnum implements IBaseEnum {
	/**
	 * 离网
	 */
	Offline(0, "离网"),
	/**
	 * 空闲
	 */
	Freeing(1, "空闲"),
	/**
	 * 占用(未充电）
	 */
	UsedNoCharge(2, "占用(未充电）"),
	/**
	 * 占用(充电中)
	 */
	Charging(3, "占用(充电中)"),
	/**
	 * 占用（预约锁定）
	 */
	Locking(4, "占用（预约锁定）"),
	/**
	 * 故障
	 */
	Error(255, "故障");
	private Integer id;

	private String name;

	ConnectorStatusEnum() {

	}

	ConnectorStatusEnum(Integer id, String name) {
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
			for (ConnectorStatusEnum o : ConnectorStatusEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

}
