package com.newlife.s4.webservice.sms;

import java.util.Map;

public interface ISmsUtil {

	/**
	 * 根据短信模板发送短信记录
	 * 
	 * @param mobile
	 *            收件人手机号码
	 * @param param
	 *            短信模板内容参数填充
	 * @param contentFormat
	 *            短信模板内容
	 * @param requestId
	 *            请求ID(StringUtil.ramdomGUID)
	 * @param extno
	 *            扩展号(非必选)
	 * @return
	 * @throws SwallowException
	 */
	String sendSms(String mobile, Map<String, String> param, String contentFormat, String requestId, String extno)
			throws SmsException;
}
