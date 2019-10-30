package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: newlife
 * @date: 2017/10/24 16:06
 */
public interface OrganizationsService {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject addOrganizations(JSONObject jsonObject);

    /**
     * 组织列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listOrganizations(JSONObject jsonObject);

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateOrganizations(JSONObject jsonObject);
    
    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteOrganizations(JSONObject jsonObject);
    
    
    /**
     * 获取单个组织
     *
     * @param jsonObject
     * @return
     */
    JSONObject getOrganizations(JSONObject jsonObject);

    /**
     * 创建二维码
     * @param jsonObject
     * @return
     */
    JSONObject createQRCode(JSONObject jsonObject,HttpServletRequest request);

    void downloadQRCode(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response);
    /**
     * 查询组织名称列表
     * @param jsonObject
     * @return
     */
    JSONObject getOrganizationsList(JSONObject jsonObject);
}
