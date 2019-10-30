package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 流程实例
 * @date: 2018/08/30
 */
public interface FlowExecutionDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addFlowExecution(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountFlowExecution(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listFlowExecution(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateFlowExecution(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteFlowExecution(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFlowExecution(JSONObject jsonObject);
}
