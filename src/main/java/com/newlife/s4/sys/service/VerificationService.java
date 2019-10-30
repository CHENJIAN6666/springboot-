package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 系统验证：手机验证、邮箱验证
 * @date: 2018/3/25
 */
public interface VerificationService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addVerification(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listVerification(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateVerification(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteVerification(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getVerification(JSONObject jsonObject);


    /**
     * 微信端发短信验证
     * @param requestJson
     * @return
     */
    JSONObject addPhoneVerification4m(JSONObject requestJson);

    /**
     * 获取 验证码 by memberID 
     * @param paramjson
     * @return
     */
    JSONObject getVerification4m(JSONObject paramjson);

	/**
	 * 验证失败
	 * @return
	 */
	JSONObject returnVerifyFail();

	
	/**
	 * 验证成功
	 * @return
	 */
	JSONObject returnVerifySuccess();
}
