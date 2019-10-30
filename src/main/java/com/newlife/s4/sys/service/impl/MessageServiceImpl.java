package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.MessageDao;
import com.newlife.s4.sys.service.MessageService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;
import java.util.List;

/**
 * @author: newlife
 * @description: 
 * @date: 2019/03/05
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addMessage(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        messageDao.addMessage(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listMessage(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = messageDao.listCountMessage(jsonObject);
        List<JSONObject> list = messageDao.listMessage(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateMessage(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        messageDao.updateMessage(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteMessage(JSONObject jsonObject) {
        messageDao.deleteMessage(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getMessage(JSONObject jsonObject) {
    	JSONObject model=messageDao.getMessage(jsonObject);
        return CommonUtil.successJson(model);
    }


    /**
     * 推送
     *
     * @param roleID
     * @param content
     */
    @Override
    public JSONObject pushMessage(String roleID,String title, String content) {
        JSONObject saveModel = new JSONObject();
        saveModel.put("messageTitle",title);
        saveModel.put("messageContent",content);
        saveModel.put("messageState",0);
        saveModel.put("receiveRoleID",roleID);

        saveModel.put("status", 0);
        saveModel.put("createTime", "now()");
        saveModel.put("modifyTime", "now()");
        saveModel.put("createUser", Sessions.getCurrentUserID());
        saveModel.put("modifyUser", Sessions.getCurrentUserID());

        messageDao.addMessage(saveModel);
        return CommonUtil.successData();
    }


    /**
     * 标识已读
     *

     */
    @Override
    public JSONObject markRead(JSONObject jsonObject) {
        JSONObject saveRead = new JSONObject();
        saveRead.put("sysMessageID",jsonObject.get("sysMessageID"));
        saveRead.put("messageState",1);

        saveRead.put("status", 0);
        saveRead.put("createUser", Sessions.getCurrentUserID());
        saveRead.put("modifyUser", Sessions.getCurrentUserID());
        messageDao.updateMessage(saveRead);
        return CommonUtil.successData();
    }

    /**
     * 获取 我的信息列表
     *
     * @param jsonObject
     */
    @Override
    public JSONObject getMyMessageList(JSONObject jsonObject) {
        JSONObject queryMyMessage = new JSONObject();
        queryMyMessage.put("userID",Sessions.getCurrentUserID());
        List<JSONObject> list = messageDao.getMyMessageList(queryMyMessage);
        return CommonUtil.successData(list);
    }
}
