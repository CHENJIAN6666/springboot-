package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2019/03/05
 */
public interface MessageDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addMessage(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountMessage(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listMessage(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateMessage(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteMessage(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getMessage(JSONObject jsonObject);

    /**
     * 获取 当前用户 信息
     * @param queryMyMessage
     * @return
     */
    List<JSONObject> getMyMessageList(JSONObject queryMyMessage);
}
