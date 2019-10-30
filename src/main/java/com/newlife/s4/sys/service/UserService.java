package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.util.CommonUtil;

import java.util.List;

/**
 * @author: newlife
 * @description: 用户/角色/权限
 * @date: 2017/11/2 10:18
 */
public interface UserService {
    /**
     * 用户列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listUser(JSONObject jsonObject);

    /**
     * 用户个人信息
     *
     * @param jsonObject
     * @return
     */
    JSONObject getUser(JSONObject jsonObject);

    /**
     * 删除用户
     * @param jsonObject
     * @return
     */
    JSONObject removeUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     *
     * @return
     */
    JSONObject getAllRoles();

    /**
     * 添加用户
     *
     * @param jsonObject
     * @return
     */
    JSONObject addUser(JSONObject jsonObject);
    
    /**
     * 判断用户是否存在
     * @param jsonObject
     * @return
     */
    JSONObject isExistUser(JSONObject jsonObject);

    /**
     * 修改用户
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateUser(JSONObject jsonObject);

    /**
     * 角色列表
     *
     * @return
     */
    JSONObject listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    JSONObject listAllPermission();

    /**
     * 添加角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject addRole(JSONObject jsonObject);

    /**
     * 修改角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateRole(JSONObject jsonObject);

    /**
     * 删除角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteRole(JSONObject jsonObject);


    /**
     *
     * @param jsonObject
     * @return
     */
    void getUserNameByIDs(JSONObject object,String[] fields);

    void getUserNameByIDs(List<JSONObject> list,String[] fields);

    JSONObject getUserByRoleID(long roleID);

    /**
     * 获取用户信息
     *
     * @param paramObject
     * @return
     */
    JSONObject getUserInfo4m(JSONObject paramObject);

    /**
     * 获取用户二维码
     *
     * @param paramObject
     * @return
     */
    JSONObject getQrCode(JSONObject paramObject);
    

    /**
     * 获取组织下的所有用户
     * @param jsonObject
     * @return
     */
    JSONObject getAllSalesConsultantByOrg(JSONObject jsonObject);










}
