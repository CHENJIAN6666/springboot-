package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 文章Dao层
 * @date: 2017/10/24 16:06
 */
public interface OrganizationsDao {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    int addOrganizations(JSONObject jsonObject);

    /**
     * 统计文章总数
     *
     * @param jsonObject
     * @return
     */
    int countOrganizations(JSONObject jsonObject);

    /**
     * 文章列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listOrganizations(JSONObject jsonObject);

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    int updateOrganizations(JSONObject jsonObject);
    
    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    int deleteOrganizations(JSONObject jsonObject);
    
    /**
     * 查询组织机构层次
     * @param orgId
     * @return
     */
    int getOrgLevel(JSONObject jsonObject);
    
    /**
     * 更新层数
     * @param jsonObject
     * @return
     */
    int updateOrgLevel(JSONObject jsonObject);
    
    
    /**
     * 查询组织机构树形ID
     * @param orgId
     * @return
     */
    String getOrgIdTreePath(JSONObject jsonObject);
    
    /**
     * 更新组织机构树形ID
     * @return
     */
    int updateOrgIdTreePath(JSONObject jsonObject);
    
    /**
     * 更新组织机构树形名称
     * @return
     */
    int updateOrgNameTreePath(JSONObject jsonObject);


    int updateStoreOrgNameTreePath(JSONObject jsonObject);

    
    /**
     * 查询单个组织机构
     * @param orgId
     * @return
     */
    JSONObject getOrganizations(JSONObject jsonObject);

    
    /**
     * 查询组织机构树形名称
     * @param orgId
     * @return
     */
    String getOrgNameTreePath(JSONObject jsonObject);

    /**
     * 根据集团去查询所有店
     * @param jsonObject
     * @return
     */
    List<JSONObject> getStoreIDByGroupID(JSONObject jsonObject);

    /**
     * 根据集团去查询所有店 (状态正常的)
     * @param jsonObject
     * @return
     */
    List<JSONObject> getStoreByGroupID(JSONObject jsonObject);

    /**
     * 查询组织名称列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> getOrganizationsList(JSONObject jsonObject);

    /**
     * 查询组织名称列表第四层
     * @param jsonObject
     * @return
     */
    List<JSONObject> getOrganizationsList4(JSONObject jsonObject);

    List<JSONObject> getOrganizationsList4s(JSONObject jsonObject);
}
