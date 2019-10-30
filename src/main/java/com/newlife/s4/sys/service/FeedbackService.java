package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 平台意见反馈
 * @date: 2018/05/28
 */
public interface FeedbackService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addFeedback(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listFeedback(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateFeedback(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteFeedback(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFeedback(JSONObject jsonObject);

    JSONObject answerFeedback(JSONObject jsonObject);

    /**
     * 微信端反馈
     * @param params
     * @return
     */
	JSONObject addFeedback4m(JSONObject params);

    /**
     * 员工端反馈
     * @param jsonObject
     * @return
     */
	JSONObject addFeedbackEmployee4m(JSONObject jsonObject);
}
