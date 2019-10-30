package com.newlife.s4.sys.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 用户/角色/权限
 * @date: 2017-11-14 15:08:45
 */
public interface UserDao {
    /**
     * 查询用户数量
     *
     * @param jsonObject
     * @return
     */
    int countUser(JSONObject jsonObject);

    /**
     * 查询用户列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listUser(JSONObject jsonObject);
    
    /**
     * 查询用户个人信息
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
    int removeUser(JSONObject jsonObject);


    List<JSONObject> getUserNameByIDs(List<Long> userIDs);
    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     *
     * @return
     */
    List<JSONObject> getAllRoles(JSONObject jsonObject);

    /**
     * 校验用户名是否已存在
     *
     * @param jsonObject
     * @return
     */
    int queryExistUsername(JSONObject jsonObject);

    /**
     * 新增用户
     *
     * @param jsonObject
     * @return
     */
    int addUser(JSONObject jsonObject);

    /**
     * 修改用户
     *
     * @param jsonObject
     * @return
     */
    int updateUser(JSONObject jsonObject);

    /**
     * 角色列表
     *
     * @return
     */
    List<JSONObject> listRole(JSONObject jsonObject);

    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    List<JSONObject> listAllPermission(JSONObject jsonObject);

    /**
     * 新增角色
     *
     * @param jsonObject
     * @return
     */
    int insertRole(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     * @return
     */
    int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<JSONObject> permissions);

    /**
     * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
     *
     * @param roleId
     * @param permissions
     * @return
     */
    int removeOldPermission(@Param("roleId") String roleId);
    
    /**
     * 刪除用戶角色
     * @param userId
     * @param roleIdList
     * @return
     */
    int removeOldUserRole(@Param("userId") String userId, @Param("roleIdList") List<Integer> roleIdList);

    /**
     * 修改角色名称
     *
     * @param jsonObject
     * @return
     */
    int updateRoleName(JSONObject jsonObject);

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     *
     * @param jsonObject
     * @return
     */
    JSONObject getRoleAllInfo(JSONObject jsonObject);


    /**
     * 根据角色代码获取角色
     * @param jsonObject
     * @return
     */
    JSONObject getRoleByRoleCode(JSONObject jsonObject);

    /**
     * 删除角色
     *
     * @param jsonObject
     * @return
     */
    int removeRole(JSONObject jsonObject);

    /**
     * 删除本角色全部权限
     *
     * @param jsonObject
     * @return
     */
    int removeRoleAllPermission(JSONObject jsonObject);
    
    /**
     * 添加用户角色
     * @param jsonObject
     * @return
     */
    int insertUserRole(@Param("userId") String userId, @Param("roleId") List<Integer> roles);
    
    /**
     * 查询用户角色
     * @param jsonObject
     * @return
     */
    JSONObject getUserRoleAllInfo(JSONObject jsonObject);
    
    /**
     * 查询meunId和operationId
     * @param menuOperationIds
     * @return
     */
    List<JSONObject> getMenuOperationId(@Param("menuOperationIds") List<Integer> menuOperationIds );
    
    /**
     * 获取用户信息
     * @param jsonObject
     * @return
     */
    JSONObject getUserInfo4m(JSONObject jsonObject);
    

    
    /**
     * 获取组织下的所有用户
     * @param jsonObject
     * @return
     */
    List<JSONObject> getAllSalesConsultantByOrg(JSONObject jsonObject);

    /**
     * 员工端 获取用户
     * @param s
     * @return
     */
	JSONObject getUser4m(JSONObject s);

    /**
     * 根据用户获取所有角色
     * @param jsonObject
     * @return
     */
	List<JSONObject> getRolesByUser(JSONObject jsonObject);

    /**
     * 修改头像
     * @param jsonObject
     * @return
     */
	int updateAvatar(JSONObject jsonObject);

    /**
     * 修改密码
     * @param jsonObject
     * @return
     */
    int updatePwd(JSONObject jsonObject);

    /**
     * by角色 查询 用户
     * @param jsonObject
     * @return
     */
    List<JSONObject> listUserByRoleNOrgID(JSONObject jsonObject);

    /**
     * 获取用户个人二维码所需信息
     * @param jsonObject
     * @return
     */
    JSONObject getQrInfo(JSONObject jsonObject);
}
