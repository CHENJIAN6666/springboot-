package com.newlife.s4.crm.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
public interface DictdataDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addDictdata(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountDictdata(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listDictdata(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateDictdata(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteDictdata(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getDictdata(JSONObject jsonObject);
    
    /**
     * by dictTypeCode 和 orgID 查询 dict
     * @param jsonObject
     * @return
     */
    List<JSONObject> listDictdataByDictTypeCode(JSONObject jsonObject);

}
