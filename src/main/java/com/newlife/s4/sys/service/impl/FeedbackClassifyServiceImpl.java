package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.TreeBuilder;
import com.newlife.s4.common.model.Node;
import com.newlife.s4.sys.dao.FeedbackClassifyDao;
import com.newlife.s4.sys.service.FeedbackClassifyService;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newlife.s4.common.Sessions;

import java.util.*;

/**
 * @author: newlife
 * @description: 平台意见反馈类型
 * @date: 2018/05/28
 */
@Service
public class FeedbackClassifyServiceImpl implements FeedbackClassifyService {

    @Autowired
    private FeedbackClassifyDao feedbackClassifyDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addFeedbackClassify(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("status", 0);
		jsonObject.put("createTime", "now()");
		jsonObject.put("modifyTime", "now()");
        feedbackClassifyDao.addFeedbackClassify(jsonObject);
        JSONObject updateJson = new JSONObject();
        updateJson.put("feedbackClassifyID",jsonObject.get("feedbackClassifyID"));
        //填写树形ID
        if(null !=jsonObject.get("parentClassifyID") && !"".equals(jsonObject.get("parentClassifyID"))) {
            JSONObject parent = new JSONObject();
            parent.put("feedbackClassifyID", jsonObject.getInteger("parentClassifyID"));
            parent= feedbackClassifyDao.getFeedbackClassify(parent);

            //set classifyIDTreePath
            StringBuffer sb = new StringBuffer(parent.getString("classifyIDTreePath"));
            sb.append(updateJson.get("feedbackClassifyID"));
            sb.append(".");
            updateJson.put("classifyIDTreePath", sb.toString());

            //set classifyLevel
            Integer orgLevel = Integer.valueOf(parent.getString("classifyLevel"));
            orgLevel += 1;
            updateJson.put("classifyLevel", orgLevel);

            //set classifyNameTreePath
            String namePath = parent.getString("classifyNameTreePath");
            sb.setLength(0);
            sb.append(namePath);
            sb.append(jsonObject.get("classifyName"));
            sb.append(">");
            updateJson.put("classifyNameTreePath", sb.toString());

        }else {
            StringBuffer sb = new StringBuffer();
            sb.append(".");
            sb.append(updateJson.get("feedbackClassifyID"));
            sb.append(".");
            updateJson.put("classifyIDTreePath", sb.toString());
            updateJson.put("classifyLevel", 0);

            sb.setLength(0);
            sb.append(jsonObject.get("classifyName"));
            sb.append(">");
            updateJson.put("classifyNameTreePath", sb.toString());
        }
        feedbackClassifyDao.updateFeedbackClassify(updateJson);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listFeedbackClassify(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = feedbackClassifyDao.listCountFeedbackClassify(jsonObject);
        List<JSONObject> list = feedbackClassifyDao.listFeedbackClassify(jsonObject);

        List<Node> nodeList = new ArrayList<Node>();
        for (JSONObject json : list) {
            Node n =  new Node();
            n.setId(json.getString("id"));
            n.setLabel(json.getString("label"));
            n.setPid(json.getString("pid"));
            JSONObject nodeData = new JSONObject();
            nodeData.put("classifyCode", json.getString("classifyCode"));
            nodeData.put("classifyName", json.getString("classifyName"));
            nodeData.put("orderNumber", json.getString("orderNumber"));
            nodeData.put("classifyIDTreePath", json.getString("classifyIDTreePath"));
            n.setNodeData(nodeData);
            nodeList.add(n);
        }
        List<JSONObject> list2 = new TreeBuilder().buildTree(nodeList);

        return CommonUtil.successPage(jsonObject, list2, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateFeedbackClassify(JSONObject jsonObject) {
		jsonObject.remove("createTime");
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyTime", "now()");
        feedbackClassifyDao.updateFeedbackClassify(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteFeedbackClassify(JSONObject jsonObject) {
        jsonObject.put("modifyUser",Sessions.getCurrentUserID());
        feedbackClassifyDao.deleteFeedbackClassify(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getFeedbackClassify(JSONObject jsonObject) {
    	JSONObject model=feedbackClassifyDao.getFeedbackClassify(jsonObject);
        return CommonUtil.successJson(model);
    }

    @Override
    public JSONObject getFeedbackClassifyTree() {
        JSONObject query = new JSONObject(2);
        query.put("offSet",0);
        query.put("pageRow",100);
        List<JSONObject> list =  feedbackClassifyDao.listFeedbackClassify(query);
        List<JSONObject> tree = new ArrayList<>();

        for (JSONObject item:list){
            Integer level = item.getInteger("classifyLevel");
            if(null == level || level.intValue()==0 ) {
                tree.add(item);
            }
            List<JSONObject> children = new ArrayList<>();
            for (JSONObject child : list) {
                Integer pid = child.getInteger("pid");
                if(null != pid && pid.intValue() == item.getInteger("id").intValue()){
                    children.add(child);
                }
            }
            if(children.size()>0)
                item.put("children",children);
        }
        return CommonUtil.successJson(tree);
    }

}
