package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.FlowDefine;

import java.util.List;

/**
 * @author: newlife
 * @description: 流程实例
 * @date: 2018/08/30
 */
public interface FlowExecutionService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addFlowExecution(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listFlowExecution(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateFlowExecution(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteFlowExecution(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getFlowExecution(JSONObject jsonObject);

    FlowDefine startFlow(int businessType,int businessID,List<? extends FlowDefine> flowDefine);

    FlowDefine nextFlow(int businessType,int businessID, int auditState,List<? extends FlowDefine> flowDefine);

    Boolean isFinish(JSONObject flow,List<? extends FlowDefine> flowDefine);
}
