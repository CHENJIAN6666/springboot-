/**
 * 
 */
package com.newlife.s4.webservice.sms;

/**
 * @author zengxiangxin
 *
 */
public class BaseSmsResponse {

	/**
	 * 状态码：200-验证成功， 405-AppKey为空， 406-AppKey无效， 456-国家代码或手机号码为空， 457-手机号码格式错误，
	 * 466-请求校验的验证码为空，
	 * 467-请求校验验证码频繁（5分钟内同一个appkey的同一个号码最多只能校验三次），468-验证码错误，474-没有打开服务端验证开关；
	 */
	private Integer status;

	/**
	 * 验证结果描述
	 */
	private String msg;

	/**
	 * 验证结果
	 */
	private Boolean success;

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {

		if (status.equals(200)) {
			success = true;
		}

		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
