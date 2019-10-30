package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 平台意见反馈类型
 * @date: 2018/05/28
 */
public interface FeedbackClassifyDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addFeedbackClassify(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountFeedbackClassify(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listFeedbackClassify(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateFeedbackClassify(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteFeedbackClassify(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFeedbackClassify(JSONObject jsonObject);
}
