package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.SmsSendDao;
import com.newlife.s4.sys.service.SmsSendService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 短信发送明细
 * @date: 2018/3/25
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private SmsSendDao smsSendDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addSmsSend(JSONObject jsonObject) {
//		jsonObject.put("createUser", Sessions.getCurrentUserID());
//		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
//		jsonObject.put("status", 0);
//		jsonObject.put("createTime", "now()");
//		jsonObject.put("modifyTime", "now()");
		
        smsSendDao.addSmsSend(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listSmsSend(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = smsSendDao.listCountSmsSend(jsonObject);
        List<JSONObject> list = smsSendDao.listSmsSend(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateSmsSend(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        smsSendDao.updateSmsSend(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteSmsSend(JSONObject jsonObject) {
        smsSendDao.deleteSmsSend(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getSmsSend(JSONObject jsonObject) {
    	JSONObject model=smsSendDao.getSmsSend(jsonObject);
        return CommonUtil.successJson(model);
    }
}
