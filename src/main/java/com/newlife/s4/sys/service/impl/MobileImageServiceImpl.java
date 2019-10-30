package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.MobileImageDao;
import com.newlife.s4.sys.service.MobileImageService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2019/01/22
 */
@Service
public class MobileImageServiceImpl implements MobileImageService {

    @Autowired
    private MobileImageDao mobileImageDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addMobileImage(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");

        //判断 code 是否存在
        if(!checkIsExist(jsonObject)) {
            mobileImageDao.addMobileImage(jsonObject);
            return CommonUtil.successJson();
        }
        return CommonUtil.successData("01","该编码已经存在");


    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */


    /**
     * true 存在  false 不存在
     * @param jsonObject
     */

    private Boolean checkIsExist(JSONObject jsonObject) {
        JSONObject queryImage = new JSONObject();
        // queryImage.put("Code",requestJso.get)
        queryImage.put("code",jsonObject.get("code"));
        CommonUtil.fillPageParam(queryImage);
        List<JSONObject> lc = mobileImageDao.listMobileImage(queryImage);
        if ( lc.size() > 0 && lc.get(0).getLong("mobileImageID") != jsonObject.getLong("mobileImageID"))
            return true;
        else
            return false;

    }

    @Override
    public JSONObject listMobileImage(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = mobileImageDao.listCountMobileImage(jsonObject);
        List<JSONObject> list = mobileImageDao.listMobileImage(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateMobileImage(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");

        //判断 code 是否存在
        if(!checkIsExist(jsonObject)) {
            mobileImageDao.updateMobileImage(jsonObject);
            return CommonUtil.successJson();
        }
        return CommonUtil.successData("01","该编码已经存在");
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteMobileImage(JSONObject jsonObject) {
        mobileImageDao.deleteMobileImage(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getMobileImage(JSONObject jsonObject) {
    	JSONObject model=mobileImageDao.getMobileImage(jsonObject);
        return CommonUtil.successJson(model);
    }


    @Override
    public JSONObject getMobileImageByCode(String code){
        JSONObject queryImage = new JSONObject();
        queryImage.put("code",code);
        JSONObject model = mobileImageDao.getMobileImage(queryImage);
        return model;
    }

}
