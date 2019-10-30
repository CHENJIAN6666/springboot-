package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 发布号作者表
 * @date: 2018/3/25
 */
public interface ArticleService2 {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addArticle(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listArticle(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateArticle(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteArticle(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getArticle(JSONObject jsonObject);
}
