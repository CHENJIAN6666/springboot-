package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.SmsTemplateDao;
import com.newlife.s4.sys.service.SmsTemplateService;
import com.newlife.s4.task.PushService;
import com.newlife.s4.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newlife.s4.common.Sessions;

import java.util.List;

/**
 * @author: newlife
 * @description: 系统短信模板
 * @date: 2018/3/25
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateDao smsTemplateDao;
    
    @Autowired
    @Qualifier("sendSMSTask")
    private PushService pushService;
    

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addSmsTemplate(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        smsTemplateDao.addSmsTemplate(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listSmsTemplate(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = smsTemplateDao.listCountSmsTemplate(jsonObject);
        List<JSONObject> list = smsTemplateDao.listSmsTemplate(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateSmsTemplate(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        smsTemplateDao.updateSmsTemplate(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteSmsTemplate(JSONObject jsonObject) {
        jsonObject.put("modifyUser", Sessions.getCurrentUserID());
        smsTemplateDao.deleteSmsTemplate(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getSmsTemplate(JSONObject jsonObject) {
    	JSONObject model=smsTemplateDao.getSmsTemplate(jsonObject);
        return CommonUtil.successJson(model);
    }

    /**
     * 获取短信模板列表
     */
	@Override
	public JSONObject getSmsTemplateList(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("orgID", Sessions.getStoreOrgId());
		 return CommonUtil.successData( smsTemplateDao.getSmsTemplateList4m(jsonObject));
	}

	/**
	 * 删除短信模板（只能删除自己的）
	 */
	@Override
	public JSONObject delSmsTemplate4m(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		smsTemplateDao.delSmsTemplate4m(jsonObject);
		return  CommonUtil.successData();
	}

	/**
	 * 群发短信
	 */
	@Override
	public JSONObject sendSmsTemplate4m(JSONObject jsonObject) {
		
		if(null != jsonObject.getJSONArray("customerList") && jsonObject.getJSONArray("customerList").size()>0 ){
			JSONArray arr = jsonObject.getJSONArray("customerList");
			
			
			for(int i = 0;i<arr.size();i++){
				JSONObject push = new JSONObject();
				push.put("userID", Sessions.getCurrentUserID());
				push.put("smsContent", jsonObject.get("templateContent"));
				push.put("sendPriority", 1);
				push.put("mobile", arr.getJSONObject(i).get("mobile"));
				pushService.pushMsg(push);
			}
			
			
			
		}
		
		
		return CommonUtil.successData();
	}
}
