package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 平台意见反馈类型
 * @date: 2018/05/28
 */
public interface FeedbackClassifyService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addFeedbackClassify(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listFeedbackClassify(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateFeedbackClassify(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteFeedbackClassify(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFeedbackClassify(JSONObject jsonObject);

    JSONObject getFeedbackClassifyTree();
}
