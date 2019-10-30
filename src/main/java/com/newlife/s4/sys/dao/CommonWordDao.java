package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 常用语
 * @date: 2018/3/25
 */
public interface CommonWordDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addCommonWord(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountCommonWord(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listCommonWord(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateCommonWord(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteCommonWord(JSONObject jsonObject);
    
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
    List<JSONObject> getLanguageTemplate4m(JSONObject jsonObject);
}
