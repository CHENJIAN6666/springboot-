package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.WxPushDao;
import com.newlife.s4.sys.service.WxPushService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 微信推文
 * @date: 2018/10/09
 */
@Service
public class WxPushServiceImpl implements WxPushService {

    @Autowired
    private WxPushDao wxPushDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addWxPush(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        wxPushDao.addWxPush(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listWxPush(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = wxPushDao.listCountWxPush(jsonObject);
        List<JSONObject> list = wxPushDao.listWxPush(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateWxPush(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        wxPushDao.updateWxPush(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteWxPush(JSONObject jsonObject) {
        wxPushDao.deleteWxPush(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getWxPush(JSONObject jsonObject) {
    	JSONObject model=wxPushDao.getWxPush(jsonObject);
        return CommonUtil.successJson(model);
    }

    @Override
    public List<JSONObject> getWxPushOrder() {
        return wxPushDao.getWxPushOrder();
    }
}
