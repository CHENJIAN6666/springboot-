package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.FeedbackDao;
import com.newlife.s4.sys.service.FeedbackService;
import com.newlife.s4.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newlife.s4.common.Sessions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: newlife
 * @description: 平台意见反馈
 * @date: 2018/05/28
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addFeedback(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
		
        feedbackDao.addFeedback(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listFeedback(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = feedbackDao.listCountFeedback(jsonObject);
        List<JSONObject> list = feedbackDao.listFeedback(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateFeedback(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        feedbackDao.updateFeedback(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteFeedback(JSONObject jsonObject) {
        jsonObject.put("modifyUser",Sessions.getCurrentUserID());
        feedbackDao.deleteFeedback(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getFeedback(JSONObject jsonObject) {
    	JSONObject model=feedbackDao.getFeedback(jsonObject);
        return CommonUtil.successJson(model);
    }

    @Override
    public JSONObject answerFeedback(JSONObject jsonObject) {
        jsonObject.put("replyPerson",Sessions.getCurrentUserID());
        feedbackDao.answerFeedback(jsonObject);
        return CommonUtil.successJson();
    }

	@Override
	public JSONObject addFeedback4m(JSONObject params) {
		JSONObject jo1 = new JSONObject ();
		jo1.put("feedbackMarker", "1"); //微信端
		jo1.put("feedbackPerson", params.get("memberID"));
		jo1.put("feedbackContent", params.get("remark"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jo1.put("feedbackTime", sdf.format(new Date()));
		jo1.put("status", 0);
		jo1.put("createTime", "now()");
		jo1.put("modifyTime", "now()");
		feedbackDao.addFeedback(jo1);
		return  CommonUtil.successJson();
	}

    @Override
    public JSONObject addFeedbackEmployee4m(JSONObject jsonObject) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        jsonObject.put("feedbackPerson",Sessions.getCurrentUserID());
        jsonObject.put("feedbackMarker",2);
        jsonObject.put("feedbackTime",time);
        jsonObject.put("createTime","now()");
        jsonObject.put("createUser",Sessions.getCurrentUserID());
        feedbackDao.addFeedback(jsonObject);

        return CommonUtil.successData();
    }
}
