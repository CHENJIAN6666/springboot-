package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.AttachmentDao;
import com.newlife.s4.sys.service.AttachmentService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 附件
 * @date: 2018/08/23
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addAttachment(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        attachmentDao.addAttachment(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listAttachment(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = attachmentDao.listCountAttachment(jsonObject);
        List<JSONObject> list = attachmentDao.listAttachment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }


    @Override
    public JSONObject listAttachmentByCarSalesOrderNumber(JSONObject jsonObject) {
        List<JSONObject> list = attachmentDao.listAttachmentByCarSalesOrderNumber(jsonObject);
        return CommonUtil.successJson(list);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateAttachment(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        attachmentDao.updateAttachment(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteAttachment(JSONObject jsonObject) {
        attachmentDao.deleteAttachment(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getAttachment(JSONObject jsonObject) {
    	JSONObject model=attachmentDao.getAttachment(jsonObject);
        return CommonUtil.successJson(model);
    }
}
