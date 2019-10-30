package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 微信推文
 * @date: 2018/10/09
 */
public interface WxPushService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addWxPush(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listWxPush(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateWxPush(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteWxPush(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getWxPush(JSONObject jsonObject);

    List<JSONObject> getWxPushOrder();
}
