package com.newlife.s4.webservice.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class SmsConfig {
	
	@Value("${webservice.sms.canSend}")
	private Boolean canSend;
	
	@Value("${webservice.sms.smsPrefix}")
	private String smsPrefix;
	
	@Value("${webservice.sms.smsSuffix}")
	private String smsSuffix;

	 /**
	  * 全局配置，是否可以发送短信
	  * @return
	  */
	public boolean getCanSend() {
		
		if(canSend == null) {
			canSend = false;
		}
		
		return canSend.booleanValue();
	}

	/**
	 * 全局配置，短信前缀
	 * @return
	 */
	public String getSmsPrefix() {
		return smsPrefix;
	}
	
	/**
	 * 全局配置，短信追尾后缀
	 * @return
	 */
	public String getSmsSuffix() {
		return smsSuffix;
	}
}
