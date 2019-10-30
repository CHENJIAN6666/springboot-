package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.OfflineNewsDao;
import com.newlife.s4.sys.service.OfflineNewsService;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
@Service
public class OfflineNewsServiceImpl implements OfflineNewsService {

    @Autowired
    private OfflineNewsDao offlineNewsDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addOfflineNews(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        offlineNewsDao.addOfflineNews(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listOfflineNews(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = offlineNewsDao.listCountOfflineNews(jsonObject);
        List<JSONObject> list = offlineNewsDao.listOfflineNews(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateOfflineNews(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        offlineNewsDao.updateOfflineNews(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteOfflineNews(JSONObject jsonObject) {
        offlineNewsDao.deleteOfflineNews(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getOfflineNews(JSONObject jsonObject) {
    	JSONObject model=offlineNewsDao.getOfflineNews(jsonObject);
        return CommonUtil.successJson(model);
    }

    /**
     *  获取未读消息条数
     *
     * @param memberID
     * @return
     */
    @Override
    public JSONObject unReadNewsAmount(Long memberID) {
        int amount = offlineNewsDao.unReadNewsAmount(memberID);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",amount);
        return CommonUtil.successJson(jsonObject);
    }

    @Override
    public JSONObject unreadMsg(JSONObject jsonObject) {
        JSONObject where = new JSONObject();
        JSONObject result = new JSONObject();
        where.put("userID",Sessions.getCurrentUserID());
        where.put("status",0);
        where.put("fromType",1);
        CommonUtil.fillPageParam(where);
        List<JSONObject > list = offlineNewsDao.listOfflineNews(where);
        if(null != list && list.size()>0){
            result.put("status",0);
        }else{
            result.put("status",1);
        }
        return CommonUtil.successData(result);
    }

    @Override
    public JSONObject readMsg(JSONObject jsonObject) {
        if(!StringTools.isNullOrEmpty(jsonObject.get("offlineNewID"))){
            jsonObject.put("status",1);
            offlineNewsDao.updateOfflineNews(jsonObject);
            return CommonUtil.successData();
        }
        return CommonUtil.successData("1","标识失败");
    }

    @Override
    public int getAmountByUser(JSONObject params) {
        return offlineNewsDao.getAmountByUser(params);
    }
}
