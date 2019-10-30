package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.common.constants.FlowDefine;
import com.newlife.s4.common.constants.PreDeliverFlow;
import com.newlife.s4.sys.dao.FlowExecutionDao;
import com.newlife.s4.sys.dao.FlowTaskDao;
import com.newlife.s4.sys.service.FlowExecutionService;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 流程实例
 * @date: 2018/08/30
 */
@Service
public class FlowExecutionServiceImpl implements FlowExecutionService {

    @Autowired
    private FlowExecutionDao flowExecutionDao;

    @Autowired
    private FlowTaskDao flowTaskDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addFlowExecution(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        flowExecutionDao.addFlowExecution(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listFlowExecution(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = flowExecutionDao.listCountFlowExecution(jsonObject);
        List<JSONObject> list = flowExecutionDao.listFlowExecution(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateFlowExecution(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        flowExecutionDao.updateFlowExecution(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteFlowExecution(JSONObject jsonObject) {
        flowExecutionDao.deleteFlowExecution(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getFlowExecution(JSONObject jsonObject) {
    	JSONObject model=flowExecutionDao.getFlowExecution(jsonObject);
        return CommonUtil.successJson(model);
    }


    /**
     *
     * @param businessID  业务ID
     * @param businessType 业务类型 1 交车申请  2退款 3追加装潢
     * @param flowDefine  流程
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FlowDefine startFlow(int businessType, int businessID,List<? extends FlowDefine> flowDefine) {
        JSONObject flow = new JSONObject();
        flow.put("businessID",businessID);
        flow.put("flowStartDate",DateUtils.getNowDateText(DateUtils.YMDHMS_BREAK)); //开始时间
        flow.put("flowState",0);  // 流程状态0 开始 1(留用 )  2完成，结案

        FlowDefine fd = flowDefine.get(1);
        flow.put("businessType",businessType);
        flow.put("nextAuditStep",fd.getStep());
        flow.put("nextAuditRole",fd.getRoleID());
        flow.put("currentAuditStep",fd.getStep()-1); //taskId

        flowExecutionDao.addFlowExecution(flow);

        //新建task
        JSONObject task = newTask(1,0,flow.getIntValue("flowExecutionID"));
        flowTaskDao.addFlowTask(task);

        JSONObject updateFlow = new JSONObject();  //更新Last_Task_ID
        updateFlow.put("flowExecutionID",flow.getString("flowExecutionID"));
        updateFlow.put("lastTaskID",task.getString("flowTaskID")); //taskId

        flowExecutionDao.updateFlowExecution(updateFlow);
        return  flowDefine.get(0);


    }

    private JSONObject newTask(int auditStep,int auditState,int flowExecutionID){
        JSONObject task = new JSONObject();
        task.put("auditStep",auditStep);
        task.put("auditState",auditState);
        task.put("auditDate",DateUtils.getNowDateText(DateUtils.YMDHMS_BREAK));
        task.put("auditUserID",Sessions.getCurrentUserID());
        task.put("flowExecutionID",flowExecutionID);
        return task;
    }

    /**
     *
     * @param businessID    业务ID
     * @param businessType  业务类型 1 交车申请  2退款 3追加装潢
     * @param auditState  0 同意 1 驳回
     * @param flowDefine  流程
     * @return
     */
    @Override
    public FlowDefine nextFlow(int businessType ,int businessID,int auditState,List<? extends FlowDefine> flowDefine) {

        JSONObject queryFlowExecution = new JSONObject();
        queryFlowExecution.put("businessType",businessType);
        queryFlowExecution.put("businessID",businessID);
        //queryFlowExecution.put("nextAuditRole",Sessions.getCurrentUserPermission());
        CommonUtil.fillPageParam(queryFlowExecution);
        List<JSONObject> flowExecutions = flowExecutionDao.listFlowExecution(queryFlowExecution);

        if(flowExecutions.size() == 0){
            return null;
        }

        JSONObject flow = flowExecutions.get(0);



        int nextAuditStep = flow.getIntValue("nextAuditStep");
        FlowDefine fd = flowDefine.get(nextAuditStep-1);
        String busiType = flow.getString("businessType");

        JSONObject task = newTask(nextAuditStep,auditState,flow.getIntValue("flowExecutionID"));
        flowTaskDao.addFlowTask(task);

        JSONObject updateFlow = new JSONObject();  //更新Last_Task_ID
        updateFlow.put("flowExecutionID",flow.getString("flowExecutionID"));
        updateFlow.put("lastTaskID",task.getString("flowTaskID")); //taskId
        updateFlow.put("currentAuditStep",nextAuditStep); //taskId

        //不是流程的最后一步
        if(!fd.isEndStep()) {
            FlowDefine nextFd =flowDefine.get(nextAuditStep);
            updateFlow.put("nextAuditStep",nextFd.getStep());
            updateFlow.put("nextAuditRole",nextFd.getRoleID());
        } else{
            updateFlow.put("lastTaskID",null); //taskId
            updateFlow.put("nextAuditStep",null);
            updateFlow.put("nextAuditRole",null);
            updateFlow.put("flowEndDate",DateUtils.getNowDateText("yyyy-MM-dd HH:mm:ss"));
        }
        flowExecutionDao.updateFlowExecution(updateFlow);

        return fd;
    }

    @Override
    public Boolean isFinish(JSONObject flow,List<? extends FlowDefine> flowDefine) {
        int nextAuditStep = flow.getIntValue("nextAuditStep");
        FlowDefine fd =flowDefine.get(nextAuditStep);
        return fd.isEndStep();
    }
}
