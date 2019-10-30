package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.RegionDao;
import com.newlife.s4.sys.service.RegionService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 地区
 * @date: 2018/06/02
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addRegion(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        regionDao.addRegion(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listRegion(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = regionDao.listCountRegion(jsonObject);
        List<JSONObject> list = regionDao.listRegion(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateRegion(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        regionDao.updateRegion(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteRegion(JSONObject jsonObject) {
        regionDao.deleteRegion(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getRegion(JSONObject jsonObject) {
    	JSONObject model=regionDao.getRegion(jsonObject);
        return CommonUtil.successJson(model);
    }
}
