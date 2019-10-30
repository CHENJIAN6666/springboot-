package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 平台意见反馈
 * @date: 2018/05/28
 */
public interface FeedbackDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addFeedback(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountFeedback(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listFeedback(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateFeedback(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteFeedback(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFeedback(JSONObject jsonObject);

    int answerFeedback(JSONObject jsonObject);
}
