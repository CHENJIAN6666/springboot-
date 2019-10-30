package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 系统短信模板
 * @date: 2018/3/25
 */
public interface SmsTemplateService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addSmsTemplate(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listSmsTemplate(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateSmsTemplate(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteSmsTemplate(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getSmsTemplate(JSONObject jsonObject);
    
    
    /**
     * 获取短信模板列表
     * @param jsonObject
     * @return
     */
    JSONObject getSmsTemplateList(JSONObject jsonObject);
    
    /**
     * 删除短信模板（只能删除自己创建的）
     * @param jsonObject
     * @return
     */
    JSONObject delSmsTemplate4m(JSONObject jsonObject);

    /**
     * 群发短信
     * @param jsonObject
     * @return
     */
	JSONObject sendSmsTemplate4m(JSONObject jsonObject);
}
