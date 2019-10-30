package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 
 * @date: 2019/01/22
 */
public interface MobileImageService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addMobileImage(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listMobileImage(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateMobileImage(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteMobileImage(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getMobileImage(JSONObject jsonObject);


    /**
     *  通过 Code 获取图片
     */
    JSONObject getMobileImageByCode(String code);
}
