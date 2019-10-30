package com.newlife.s4.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.dao.ArticleDao2;
import com.newlife.s4.sys.service.ArticleService2;
import com.newlife.s4.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: newlife
 * @description: 发布号作者表
 * @date: 2018/3/25
 */
@Service
public class ArticleServiceImpl2 implements ArticleService2 {

    @Autowired
    private ArticleDao2 articleDao;

    /**
     * 新增
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addArticle(JSONObject jsonObject) {
        articleDao.addArticle(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listArticle(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        long count = articleDao.listCountArticle(jsonObject);
        List<JSONObject> list = articleDao.listArticle(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateArticle(JSONObject jsonObject) {
        articleDao.updateArticle(jsonObject);
        return CommonUtil.successJson();
    }
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteArticle(JSONObject jsonObject) {
        articleDao.deleteArticle(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getArticle(JSONObject jsonObject) {
    	JSONObject model=articleDao.getArticle(jsonObject);
        return CommonUtil.successJson(model);
    }
}
