package com.newlife.s4.webservice.sms;

public class SmsException extends Exception {
	
	/**
	 * 构造异常
	 * @param msg 异常消息
	 * @param ex  内置异常
	 */
	public SmsException(String msg, Exception ex) {
		super(msg,ex);
	}
	/**
	 * 构造异常
	 * @param msg 异常消息
	 */
	public SmsException(String msg){
		super(msg);
	}
}
