package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.sys.dao.VerificationDao;
import com.newlife.s4.sys.service.SmsSendService;
import com.newlife.s4.sys.service.VerificationService;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.RandomUtil;
import com.newlife.s4.webservice.sms.SmsException;
import com.newlife.s4.webservice.sms.SmsService;
import com.sun.org.apache.regexp.internal.RE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newlife.s4.common.Sessions;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: newlife
 * @description: 系统验证：手机验证、邮箱验证
 * @date: 2018/3/25
 */
@Service
public class VerificationServiceImpl implements VerificationService {
    private final long EXPIRE_TIME = 10 * 60 * 1000;
    private Pattern mobileReg = Pattern.compile("^\\d{11}");

    @Autowired
    private VerificationDao verificationDao;

    @Autowired
    private SmsService smsService;

    /**
     * 新增
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addVerification(JSONObject jsonObject) {
           JSONObject isExpire = verificationDao.getVerification(jsonObject);
           if (isExpire != null && isExpire.getDate("createTime").getTime() < (new Date().getTime() + EXPIRE_TIME)) {
               return CommonUtil.errorJson(ErrorEnum.E_100086);
           }


        String verificationCode = String.valueOf(Math.random()).substring(2, 6 );

        jsonObject.put("verificationCode", verificationCode);
        jsonObject.put("businessState", 0);
        jsonObject.put("createTime", "now()");
        jsonObject.put("verificationContent", jsonObject.getString("mobile"));
        jsonObject.put("businessType", 1);

        verificationDao.addVerification(jsonObject);
        String requestId = RandomUtil.genarateId("SMS_"); //请求唯一ID
        String content = "你的手机验证码是" + verificationCode +"有效时间10分钟";
        try {
           System.out.println("sms return :"+smsService.sendSms(jsonObject.getString("mobile"), null, content, requestId, ""));
        } catch (SmsException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listVerification(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = verificationDao.listCountVerification(jsonObject);
        List<JSONObject> list = verificationDao.listVerification(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateVerification(JSONObject jsonObject) {
        jsonObject.remove("createTime");
        jsonObject.put("modifyUser", Sessions.getCurrentUserID());
        jsonObject.put("modifyTime", "now()");
        verificationDao.updateVerification(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 删除
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteVerification(JSONObject jsonObject) {
        verificationDao.deleteVerification(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     *
     * @param jsonObject
     * @return
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public JSONObject getVerification(JSONObject jsonObject) {
        JSONObject model = verificationDao.getVerification(jsonObject);
        return CommonUtil.successJson(model);
    }

	@Override
	public JSONObject addPhoneVerification4m(JSONObject jsonObject) {
        Matcher matcher = mobileReg.matcher(jsonObject.getString("mobile"));
        if (!matcher.find()) {
            return CommonUtil.errorJson(ErrorEnum.E_100088);
        }
        jsonObject.put("verificationContent", jsonObject.get("mobile"));
        int count = verificationDao.getVerificationCount(jsonObject);
        //1小时内调用次数为20
        if(count>20)
            return CommonUtil.errorJson(ErrorEnum.E_100087);
//        if (isExpire != null && isExpire.getDate("createTime").getTime() + EXPIRE_TIME > (new Date().getTime())) {
//            return CommonUtil.errorJson(ErrorEnum.E_100086);
//        }


        String verificationCode = String.valueOf(Math.random()).substring(2, 6);

        jsonObject.put("verificationCode", verificationCode);
        jsonObject.put("businessState", 0);
        jsonObject.put("createTime", "now()");
        jsonObject.put("verificationContent", jsonObject.getString("mobile"));
        jsonObject.put("businessType", 1);
        jsonObject.put("memberID", jsonObject.get("memberID"));

        verificationDao.addVerification(jsonObject);
        String requestId = RandomUtil.genarateId("SMS_"); //请求唯一ID
        String content = "你的手机验证码是" + verificationCode + "有效时间10分钟";


        JSONObject returnObject = new JSONObject();
        returnObject.put("verificationID", jsonObject.get("verificationID"));

        try {
            smsService.sendSms(jsonObject.getString("mobile"), null, content, requestId, "");
        } catch (SmsException e) {
            e.printStackTrace();
        }
        return returnObject;
	}

	@Override
	public JSONObject getVerification4m(JSONObject paramjson) {
        JSONObject p = verificationDao.getVerificationByMemberIDNMobile(paramjson);
        if(null != p)
        	return p;
		return null;
	}

	@Override
	public JSONObject returnVerifyFail() {
		
		 JSONObject returnMsg =  new JSONObject();
		 returnMsg.put("errCode", "2");
		 returnMsg.put("errMsg", "验证码不正确");
		 return CommonUtil.successJson(returnMsg);
	}

	
	@Override
	public JSONObject returnVerifySuccess() {
		
		 JSONObject returnMsg =  new JSONObject();
		 returnMsg.put("returnMsg", "验证码正确");
		 return CommonUtil.successJson(returnMsg);
	}

}
