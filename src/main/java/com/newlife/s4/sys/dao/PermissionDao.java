package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * @author: newlife
 * @date: 2017/10/30 13:28
 */
public interface PermissionDao {
    /**
     * 查询用户的角色 菜单 权限
     *
     * @param username
     * @return
     */
    JSONObject getUserPermission(String username);

    /**
     * 查询所有的菜单
     *
     * @return
     */
    Set<String> getAllMenu();

    /**
     * 查询所有的权限
     *
     * @return
     */
    Set<String> getAllPermission();
    

    /**
     * 新增菜单
     *
     * @param jsonObject
     * @return
     */
    int addMenu(JSONObject jsonObject);
    
    /**
     * 菜单列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listMenus(JSONObject jsonObject);
    

    /**
     * 查询菜单树形ID
     * @param orgId
     * @return
     */
    String getMenuIdTreePath(JSONObject jsonObject);
    
    /**
     * 更新菜单树形ID
     * @return
     */
    int updateMenuIdTreePath(JSONObject jsonObject);
    
    /**
     * 查询菜单层次
     * @param orgId
     * @return
     */
    int getMenuLevel(JSONObject jsonObject);
    

    /**
     * 更新层数
     * @param jsonObject
     * @return
     */
    int updateMenuLevel(JSONObject jsonObject);
    
    /**
     * 更新菜单
     *
     * @param jsonObject
     * @return
     */
    int updateMenu(JSONObject jsonObject);

    
    /**
     * 删除菜单
     * @param jsonObject
     */
	void deleteMenu(JSONObject jsonObject);

	void addMenuOperations(JSONObject jsonObject);

	void delMenuOperations(JSONObject jsonObject);

	List<JSONObject> listMenuOperations();
}
