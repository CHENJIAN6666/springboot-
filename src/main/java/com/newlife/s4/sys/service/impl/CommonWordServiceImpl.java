package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.CommonWordDao;
import com.newlife.s4.sys.service.CommonWordService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 常用语
 * @date: 2018/3/25
 */
@Service
public class CommonWordServiceImpl implements CommonWordService {

    @Autowired
    private CommonWordDao commonWordDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addCommonWord(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        commonWordDao.addCommonWord(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listCommonWord(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = commonWordDao.listCountCommonWord(jsonObject);
        List<JSONObject> list = commonWordDao.listCommonWord(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateCommonWord(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        commonWordDao.updateCommonWord(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteCommonWord(JSONObject jsonObject) {
        commonWordDao.deleteCommonWord(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getCommonWord(JSONObject jsonObject) {
    	JSONObject model=commonWordDao.getCommonWord(jsonObject);
        return CommonUtil.successJson(model);
    }

    @Override
    public JSONObject getLanguageTemplate4m(JSONObject jsonObject) {
        jsonObject.put("orgID", Sessions.getStoreOrgId());
        jsonObject.put("isPublic", 1);
        return CommonUtil.successData( commonWordDao.getLanguageTemplate4m(jsonObject));
    }
}
