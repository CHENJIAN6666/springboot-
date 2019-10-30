package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
public interface OfflineNewsService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addOfflineNews(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listOfflineNews(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateOfflineNews(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteOfflineNews(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getOfflineNews(JSONObject jsonObject);

    JSONObject unReadNewsAmount(Long memberID);

    /**
     * 员工对话判断是有未读消息
     * @param jsonObject
     * @return
     */
    JSONObject unreadMsg(JSONObject jsonObject);

    /**
     * 标识消息为已读
     * @param jsonObject
     * @return
     */
    JSONObject readMsg(JSONObject jsonObject);

    int getAmountByUser(JSONObject params);
}
