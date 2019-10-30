package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @date: 2017/10/30 13:10
 */
public interface PermissionService {
    /**
     * 查询某用户的 角色  菜单列表   权限列表
     *
     * @param username
     * @return
     */
    JSONObject getUserPermission(String username);

    
    /**
     * 查询所有 菜单列表   
     *
     * @param jsonObject
     * @return
     */
	JSONObject listMenus(JSONObject jsonObject);
	
	/**
     * 新增菜单 
     *
     * @param jsonObject
     * @return
     */
    JSONObject addMenu(JSONObject jsonObject);

    /**
     * 修改菜单
     * @param requestJson
     * @return
     */

	JSONObject updateMenu(JSONObject requestJson);


	/**
	 * 删除菜单 
	 * @param requestJson
	 * @return
	 */
	JSONObject deleteMenu(JSONObject requestJson);

	JSONObject listMenuOperations();
}
