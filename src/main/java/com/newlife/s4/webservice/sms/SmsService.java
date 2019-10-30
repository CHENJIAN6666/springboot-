package com.newlife.s4.webservice.sms;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class SmsService {

	@Autowired
	private Sms17IntUtil sms17IntUtil;
	
	/**
	 * 获取推送实例
	 * 
	 * @param
	 * @return
	 */
	private ISmsUtil createSmsUtil() {

		// 设置默认的短信工具，通过设置这里更换短信服务商
		SmsServiceType type = SmsServiceType.Int17;
		ISmsUtil smsUtil = null;

		switch (type) {
		case Int17:
			smsUtil = sms17IntUtil;
			break;

		default:
			break;
		}

		return smsUtil;
	}

	/**
	 * 根据短信模板发送短信记录
	 * 
	 * @param mobile
	 *            收件人手机号码
	 * @param param
	 *            短信模板内容参数填充，
	 * @param contentFormat
	 *            短信模板内容，变量用 ${key}
	 * @param requestId
	 *            请求ID(StringUtil.ramdomGUID)
	 * @param extno
	 *            扩展号(非必选)
	 * @return
	 * @throws SwallowException
	 */
	public String sendSms(String mobile, Map<String, String> param, String contentFormat, String requestId,
			String extno) throws SmsException {
		ISmsUtil smsUtil = this.createSmsUtil();
		return smsUtil.sendSms(mobile, param, contentFormat, requestId, extno);
	}
}
