package com.newlife.s4.webservice.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.util.HttpUtil;
import com.newlife.s4.util.RandomUtil;
import com.newlife.s4.util.StringTools;

@Service
@Scope("prototype")
public class Sms17IntUtil implements ISmsUtil {

	// 日志服务对象
	private static Logger logger = LoggerFactory.getLogger(Sms17IntUtil.class);
	
	private static final String SMS_API_URL = "http://www.17int.cn/xxsmsweb/smsapi/send.json";
	
	@Autowired
	private Sms17IntConfig sms17IntConfig;
	
	@Autowired
	private SmsConfig smsCommonConfig;

	/**
	 * 普通短信发送
	 * 
	 * @param mobile
	 *            手机号码
	 * @param param
	 *            替换短信内容模板变量
	 * @param content
	 *            短信内容模板，变量用 ${key}
	 * @param requestId
	 *            请求ID，不能为空，建议通过StringUtil.randomGUID()获取。
	 * @param extno
	 *            扩展子号，短信发送号码末尾3位
	 * 
	 * @return 返回 batchId，接口返回的批次流水号，供短信查询发送状态使用
	 */
	@Override
	public String sendSms(String mobile, Map<String, String> param, String contentFormat, String requestId,
			String extno) throws SmsException {

		if (StringTools.isNullOrEmpty(requestId)) {
			throw new SmsException("请求ID不能为空！");
		}

		boolean isSendSms = smsCommonConfig.getCanSend();

		String batchId = null;
		Map<String, Object> response = null;

		if (param != null) {
			for (Entry<String, String> keyPair : param.entrySet()) {
				String key = keyPair.getKey();
				String value = keyPair.getValue();

				if (!StringTools.isNullOrEmpty(key)) {
					if (value == null) {
						value = "";
					}
					contentFormat = contentFormat.replaceAll("\\$\\{" + key.trim() + "\\}", value);
				}
			}
		}

		// 最后清空所有${key}未赋值变量
		contentFormat = contentFormat.replaceAll("\\$\\{[\\s\\S]+\\}", "");

		// 短信内容前缀配置
		String smsPrefix = smsCommonConfig.getSmsPrefix();

		if (!StringTools.isNullOrEmpty(smsPrefix)) {
			contentFormat = smsPrefix + contentFormat;
		}

		// 短信内容后缀配置
		String smsSuffix = smsCommonConfig.getSmsSuffix();

		if (!StringTools.isNullOrEmpty(smsSuffix)) {
			contentFormat = contentFormat + smsSuffix;
		}

		logger.debug("requestId: " + requestId + "，接收号码：" + mobile + "，短信发送内容：" + contentFormat);

		// 当 false 表示不发送短信，但是模拟发送成功的状态
		if (!isSendSms) {
			logger.debug("请注意：此次 common.conf.xml 配置 isSendSms=false，只模拟短信发送，实际并不发送短信。");
			batchId = RandomUtil.genarateId("SMS_");
			return batchId;
		}

		Map<String, Object> smsParam = new HashMap<String, Object>();
		smsParam.put("account", sms17IntConfig.getAccount());
		smsParam.put("password", sms17IntConfig.getPassword());
		smsParam.put("requestId", requestId);
		smsParam.put("mobile", mobile);
		smsParam.put("content", contentFormat);

		if (extno != null) {
			smsParam.put("extno", extno);
		}

		String requestUrl = String.format(SMS_API_URL);
		String paramJson = JSONObject.toJSONString(smsParam);

		try {

			response = HttpUtil.httpRequestJsonObject(requestUrl, "POST", paramJson, Map.class);

			logger.debug("短信发送结果：" + JSONObject.toJSONString(response));

			if (response.containsKey("status")) {
				if (response.get("status").equals("10")) {
					logger.debug("短信发送成功 requestId：" + response.get("requestId"));
				} else {
					if (response.get("errorCode") != null) {
						switch (response.get("errorCode").toString().toLowerCase()) {

						case "rejected":
							logger.debug("短信发送失败 请求被拒绝！ requestId：" + response.get("requestId"));
							break;

						case "notauth":
							logger.debug("短信发送失败 用户密码不正确！ requestId：" + response.get("requestId"));
							break;

						case "badreq":
							logger.debug("短信发送失败 请求参数不正确！ requestId：" + response.get("requestId"));
							break;

						case "charge":
							logger.debug("短信发送失败 余额不足！ requestId：" + response.get("requestId"));
							break;

						default:
							logger.debug("短信发送失败 未知错误(代码：" + response.get("errorCode").toString() + ")！ requestId："
									+ response.get("requestId"));
							break;

						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("发送短信时发生异常", ex);
			throw new SmsException("服务器繁忙，请稍候再试！");
		}

		return batchId;
	}
}
