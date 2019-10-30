package com.newlife.s4.webservice.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Sms17IntConfig {
	
	@Value("${webservice.sms.int17.account}")
	private String account;
	
	@Value("${webservice.sms.int17.password}")
	private String password;

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}
}
