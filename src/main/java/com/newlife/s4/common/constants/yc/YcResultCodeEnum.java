package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 基础平台返回参数代码
 * 
 * @author hxl 
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum YcResultCodeEnum implements IBaseEnum {
	/**
	 * 系统繁忙,此时请求方稍后重试
	 */
	ERROR(-1, "系统繁忙,此时请求方稍后重试"),
	/**
	 *请求成功
	 */
	Success(0, "请求成功"),
	/**
	 * 签名错误
	 */
	SigError(4001, "签名错误"),
	/**
	 * Token错误
	 */
	TokenError(4002, "Token错误"),
	/**
	 * POST参数不合法,缺少必需参数
	 */
	ParamError(4003, "POST参数不合法,缺少必需参数"),
	/**
	 * 请求的业务参数不合法
	 */
	BussParamError(4004, "请求的业务参数不合法"),
	/**
	 * 系统错误
	 */
	SystemError(500, "系统错误");
	private Integer id;

	private String name;

	YcResultCodeEnum() {

	}

	YcResultCodeEnum(Integer id, String name) {
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
			for (YcResultCodeEnum o : YcResultCodeEnum.values()) {
				if (o.getName().equals(desc)) {
					return o.getId();
				}
			}
		}
		return null;
	}

}
