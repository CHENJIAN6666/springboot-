package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
public interface OfflineNewsDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addOfflineNews(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountOfflineNews(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listOfflineNews(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateOfflineNews(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteOfflineNews(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getOfflineNews(JSONObject jsonObject);

    /**
     * 获取未读消息条数
     *
     * @param memberID
     * @return
     */
    int unReadNewsAmount(Long memberID);

    int getAmountByUser(JSONObject params);

    /**
     * 根据用户id和会员id查询潜客id
     * @param params
     * @return
     */
    Long getPotentialCustomerID(JSONObject params);

    List<JSONObject> unreadMsg(JSONObject where);
}
