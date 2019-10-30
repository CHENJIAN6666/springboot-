package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 
 * @date: 2019/03/05
 */
public interface MessageService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addMessage(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listMessage(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateMessage(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteMessage(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getMessage(JSONObject jsonObject);

    /**
     * 推送信息列表
     * @param roleID
     * @param content
     */
    JSONObject pushMessage(String roleID, String title,String content);

    /**
     * 推送

     */
    JSONObject markRead(JSONObject jsonObject);


    /**
     * 获取 我的信息列表
     */
    JSONObject getMyMessageList(JSONObject jsonObject);
}
