package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 系统验证：手机验证、邮箱验证
 * @date: 2018/3/25
 */
public interface VerificationDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addVerification(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountVerification(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listVerification(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateVerification(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteVerification(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getVerification(JSONObject jsonObject);

    
    /**
     * 获取 最新 验 证码by memberID,mobile
     * @param paramjson
     * @return
     */
	JSONObject getVerificationByMemberIDNMobile(JSONObject paramjson);

    int getVerificationCount(JSONObject jsonObject);
}
