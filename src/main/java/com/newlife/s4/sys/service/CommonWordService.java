package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 常用语
 * @date: 2018/3/25
 */
public interface CommonWordService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addCommonWord(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listCommonWord(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateCommonWord(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteCommonWord(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getCommonWord(JSONObject jsonObject);

    /**
     * 获取常用语模板列表
     * @param jsonObject
     * @return
     */
    JSONObject getLanguageTemplate4m(JSONObject jsonObject);
}
