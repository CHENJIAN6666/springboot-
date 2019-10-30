package com.newlife.s4.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;


import com.newlife.s4.common.constants.RoleConstants;

import com.newlife.s4.sys.dao.OrganizationsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.config.shiro.RedisDb;
import com.newlife.s4.config.storage.FileStorageConfig;
import com.newlife.s4.config.wx.WxConfig;

import com.newlife.s4.sys.dao.UserDao;
import com.newlife.s4.sys.service.UserService;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.MD5Util;
import com.newlife.s4.util.StringTools;

import static com.alibaba.fastjson.JSON.parseArray;


/**
 * @author: newlife
 * @description: 用户/角色/权限
 * @date: 2017/11/2 10:18
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private WxConfig wxConfig;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private RedisDb redisDb;





	@Autowired
	private FileStorageConfig fileStorageConfig;




	@Autowired
	private OrganizationsDao organizationsDao;


	/** 
	 * 查询用户列表
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject listUser(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		jsonObject.put("orgID", Sessions.getOrgID());
		if(!jsonObject.containsKey("listQueryOrgID") || StringUtils.isEmpty(jsonObject.getString("listQueryOrgID"))){
//			jsonObject.put("listQueryOrgID",Sessions.getStoreOrgId());
			//根据treePath获取改路径下的用户。
			if(Sessions.getOrgIdTreePath().split(".").length <= 5)
				jsonObject.put("orgIdTreePath", Sessions.getOrgIdTreePath());
			else
				jsonObject.put("orgIdTreePath", Sessions.getStoreOrgTreePath());
		}
		int count = userDao.countUser(jsonObject);
		List<JSONObject> list = userDao.listUser(jsonObject);

		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 添加用户
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject addUser(JSONObject jsonObject) {
		int exist = userDao.queryExistUsername(jsonObject);
		if (exist > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10009);
		}
		jsonObject.put("password", MD5Util.MD5(jsonObject.getString("password")));

		//添加默认头像
		if(!jsonObject.containsKey("avatar")||StringUtils.isEmpty(jsonObject.getString("avatar"))){
			jsonObject.put("avatar",fileStorageConfig.getFileStorageAddress()+"imgs/icon_kefu@2x.png");
		}
		userDao.addUser(jsonObject);

		userDao.insertUserRole(jsonObject.getString("userId"), (List<Integer>) jsonObject.get("roleIdList"));
		return CommonUtil.successJson();
	}

	/**
	 * 查询所有的角色 在添加/修改用户的时候要使用此方法
	 *
	 * @return
	 */
	@Override
	public JSONObject getAllRoles() {
		JSONObject json = new JSONObject();
		json.put("orgID", Sessions.getOrgID());
		List<JSONObject> roles = userDao.getAllRoles(json);
		return CommonUtil.successPage(roles);
	}

	/**
	 * 修改用户
	 *
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject updateUser(JSONObject jsonObject) {
		String userId = jsonObject.getString("userId");
		List<Integer> newRoleIdList = (List<Integer>) jsonObject.get("roleIdList");
		JSONObject userRoleInfo = userDao.getUserRoleAllInfo(jsonObject);
		Set<Integer> oldRoleIdList = (Set<Integer>) userRoleInfo.get("roleIdList");
		// 修改用戶
		if(!StringTools.isNullOrEmpty(jsonObject.getString("password"))){
			jsonObject.put("password", MD5Util.MD5(jsonObject.getString("password")));
		}
		userDao.updateUser(jsonObject);
		// 添加新权限
		saveNewUserRole(userId, newRoleIdList, oldRoleIdList);
		// 移除旧的不再拥有的权限
		removeOldUserRole(userId, newRoleIdList, oldRoleIdList);

		;
		return CommonUtil.successJson();
	}

	private void removeOldUserRole(String userId, Collection<Integer> newRoleIdList,
			Collection<Integer> oldRoleIdList) {
		List<Integer> waitRemove = new ArrayList<>();
		for (Integer old : oldRoleIdList) {
			if (!newRoleIdList.contains(old)) {
				waitRemove.add(old);
			}
		}
		if (waitRemove.size() > 0) {
			userDao.removeOldUserRole(userId, waitRemove);
		}
	}

	/**
	 * 角色列表
	 *
	 * @return
	 */
	@Override
	public JSONObject listRole() {
		JSONObject userOrg = Sessions.getCurrentUser();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orgID", userOrg.get("orgID"));
		List<JSONObject> roles = userDao.listRole(jsonObject);
		return CommonUtil.successPage(roles);
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 *
	 * @return
	 */
	@Override
	public JSONObject listAllPermission() {
		JSONObject json = new JSONObject();
		if (!Sessions.hasRootRole()) {
			json.put("orgID", Sessions.getOrgID());
		}
		List<JSONObject> permissions = userDao.listAllPermission(json);
		return CommonUtil.successPage(permissions);
	}

	/**
	 * 添加角色
	 *
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject addRole(JSONObject jsonObject) {
		System.out.println(Sessions.getOrgID() + Sessions.getOrgName());
		jsonObject.put("orgID", Sessions.getOrgID());
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		
		userDao.insertRole(jsonObject);
		//根据permissions去查询menuId和operationId
		List<Integer> menuOperationIds = (List<Integer>)jsonObject.get("permissions");
		List<JSONObject> json = userDao.getMenuOperationId(menuOperationIds);
		
		if(json.size()>0) {
			userDao.insertRolePermission(jsonObject.getString("roleId"), json);
		}
		
		/*JSONArray jsonArray = json.getJSONArray(key)
		
		
		if (jsonArray.size() > 0) {
			List<JSONObject> list = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject j = JSONObject.parseObject(jsonArray.get(i).toString());
				list.add(j);
			}

			userDao.insertRolePermission(jsonObject.getString("roleId"), list);
		}*/

		return CommonUtil.successJson();
	}

	/**
	 * 修改角色
	 *
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject updateRole(JSONObject jsonObject) {
		String roleId = jsonObject.getString("roleId");
		//根据permissions去查询menuId和operationId
		List<Integer> menuOperationIds = (List<Integer>)jsonObject.get("permissions");
		List<JSONObject> json = userDao.getMenuOperationId(menuOperationIds);
		
		
		JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
		// 修改角色名称
		dealRoleName(jsonObject, roleInfo);

		if (json.size() > 0) {
			// 一次清除所有权限
			userDao.removeOldPermission(roleId);
			// 添加新权限
			
			userDao.insertRolePermission(roleId, json);
		}

		/*
		 * // 添加新权限 saveNewPermission(roleId, newPerms, oldPerms); // 移除旧的不再拥有的权限
		 * removeOldPermission(roleId, newPerms, oldPerms);
		 * 
		 * List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
		 * JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject); Set<Integer>
		 * oldPerms = (Set<Integer>) roleInfo.get("permissionIds"); //修改角色名称
		 * dealRoleName(jsonObject, roleInfo); //添加新权限 saveNewPermission(roleId,
		 * newPerms, oldPerms); //移除旧的不再拥有的权限 removeOldPermission(roleId, newPerms,
		 * oldPerms);
		 */
		return CommonUtil.successJson();
	}

	private void dealUserRoel(JSONObject paramJson, JSONObject roleInfo) {
		String roleName = paramJson.getString("roleName");
		if (!roleName.equals(roleInfo.getString("roleName"))) {
			userDao.updateRoleName(paramJson);
		}
	}

	/**
	 * 修改角色名称
	 *
	 * @param paramJson
	 * @param roleInfo
	 */
	private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
		String roleName = paramJson.getString("roleName");
		String roleCode = paramJson.getString("roleCode");
		if (!roleName.equals(roleInfo.getString("roleName"))
				||!roleCode.equals(roleInfo.getString("roleCode"))) {
			userDao.updateRoleName(paramJson);
		}
	}

	/**
	 * 为角色添加新权限
	 *
	 * @param newPerms
	 * @param oldPerms
	 */
	private void saveNewPermission(String roleId, JSONArray newPerms, JSONArray oldPerms) {
		List<JSONObject> waitInsert = new ArrayList<>();

		/*
		 * for (int i = 0; i < newPerms.size(); i++) { JSONObject jsonNew =
		 * newPerms.getJSONObject(i); for(int j = 0;j<oldPerms.size();j++) { JSONObject
		 * jsonOld = oldPerms.getJSONObject(j); System.out.println(jsonOld.toString());
		 * if (jsonOld.getInteger("menuId") != jsonNew.getInteger("menuId") &&
		 * jsonOld.getInteger("operationId") != jsonNew.getInteger("operationId")) {
		 * waitInsert.add(newPerms.getJSONObject(i)); }else { break; } }
		 * 
		 * }
		 */
		if (waitInsert.size() > 0) {
			userDao.insertRolePermission(roleId, waitInsert);
		}
	}

	private void saveNewUserRole(String userId, Collection<Integer> newUserRole, Collection<Integer> oldUserRole) {
		List<Integer> waitInsert = new ArrayList();
		for (Integer newUr : newUserRole) {
			if (!oldUserRole.contains(newUr)) {
				waitInsert.add(newUr);
			}
		}
		if (waitInsert.size() > 0) {
			userDao.insertUserRole(userId, waitInsert);
		}

	}

	/**
	 * 删除角色 旧的 不再拥有的权限
	 *
	 * @param roleId
	 * @param newPerms
	 * @param oldPerms
	 */
	private void removeOldPermission(String roleId, JSONArray newPerms, JSONArray oldPerms) {
		List<JSONObject> waitRemove = new ArrayList<>();

		/*
		 * for (int i = 0; i < oldPerms.size(); i++) { JSONObject jsonOld =
		 * oldPerms.getJSONObject(i); for (int j = 0; j < newPerms.size(); j++) {
		 * JSONObject jsonNew = newPerms.getJSONObject(j);
		 * 
		 * if (jsonOld.getInteger("menuId") != jsonNew.getInteger("menuId") &&
		 * jsonOld.getInteger("operationId") != jsonNew.getInteger("operationId")) {
		 * waitRemove.add(oldPerms.getJSONObject(i)); }else { break; } } }
		 */
		if (waitRemove.size() > 0) {
			for (JSONObject json : waitRemove) {
				userDao.removeOldPermission(roleId);
			}
		}
	}

	/**
	 * 删除角色
	 *
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject deleteRole(JSONObject jsonObject) {
		JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
		List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
		if (users != null && users.size() > 0) {
			return CommonUtil.errorJson(ErrorEnum.E_10008);
		}
		userDao.removeRole(jsonObject);
		// userDao.removeRoleAllPermission(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public void getUserNameByIDs(JSONObject object,String[] fields) {
		if(fields.length == 0) return;
		List<Long> userIDs = new ArrayList<>(fields.length);
		for (String f:fields){
			userIDs.add(object.getLong(f));
		}
		List<JSONObject> userNames= userDao.getUserNameByIDs(userIDs);
		for (String f : fields){
			if(object.getLong(f) > 0L){
				//用户名的字段名
				String fieldName;
				if(StringUtils.endsWith(f,"ID")){
					fieldName = f.substring(0,f.length()-2) +"Name";
				}else{
					fieldName = f+"Name";
				}
				//设置fieldName的值
				for (JSONObject name:userNames){
					if(name.getLongValue("userID")==object.getLongValue(f)){
						object.put(fieldName,name.getString("realName"));
					}
				}
			}
		}
	}

	@Override
	public void getUserNameByIDs(List<JSONObject> list,String[] fields) {
		if(list.size() == 0 || fields.length == 0) return;
		Set<Long>userIDs = new HashSet<>(fields.length);
		list.stream().forEach(object ->{
			for (String f:fields){
				userIDs.add(object.getLong(f));
			}
		});
		List<JSONObject> userNames= userDao.getUserNameByIDs(new ArrayList<>(userIDs));
		list.stream().forEach(object -> {
			for (String f : fields){
				if(object.getLong(f) > 0L){
					//用户名的字段名
					String fieldName;
					if(StringUtils.endsWith(f,"ID")){
						fieldName = f.substring(0,f.length()-2) +"Name";
					}else{
						fieldName = f+"Name";
					}
					//设置fieldName的值
					for (JSONObject name:userNames){
						if(name.getLongValue("userID")==object.getLongValue(f)){
							object.put(fieldName,name.getString("realName"));
						}
					}
				}
			}
		});
	}

	/**
	 * 查询当前用户组织指定角色的用户，返回第一个
	 * @param roleID
	 */
	@Override
	public JSONObject getUserByRoleID(long roleID) {
		JSONObject querySaleManager = new JSONObject();
		querySaleManager.put("roleID",roleID);
		querySaleManager.put("orgIdTreePath",Sessions.getStoreOrgTreePath());
		List<JSONObject> managerList = userDao.listUserByRoleNOrgID(querySaleManager);
		if(managerList.size() == 0){
			return null;
		}
		JSONObject user = managerList.get(0);
		return user;
	}

	@Override
	public JSONObject getUser(JSONObject jsonObject) {
		return CommonUtil.successJson(userDao.getUser(jsonObject));
	}

	@Override
	public JSONObject removeUser(JSONObject jsonObject) {
		userDao.removeUser(jsonObject);
		// userDao.removeRoleAllPermission(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject isExistUser(JSONObject jsonObject) {
		int exist = userDao.queryExistUsername(jsonObject);
		if (exist > 0) {
			return CommonUtil.successJson(1);
		}
		return CommonUtil.successJson(0);
	}

	/**
	 * 获取用户信息
	 *
	 * @param paramObject
	 * @return
	 */
	@Override
	public JSONObject getUserInfo4m(JSONObject paramObject) {
		if(!paramObject.containsKey("userID"))
			paramObject.put("userID",Sessions.getCurrentUserID());
		Object object = userDao.getUserInfo4m(paramObject);
		return CommonUtil.successJson(object);
	}

	/**
	 * 获取用户二维码
	 *
	 * @param paramObject
	 * @return
	 */
	@Override
	public JSONObject getQrCode(JSONObject paramObject) {
		StringBuilder s = new StringBuilder();
		s.append(wxConfig.getApiDomain() + "/wechat/tmpFriend?");
		s.append("userID="+Sessions.getCurrentUserID());
		s.append("&orgID="+Sessions.getStoreOrgId());
		paramObject.put("codeUrl", s.toString());

		paramObject.put("userID",Sessions.getCurrentUserID());
		JSONObject list = userDao.getQrInfo(paramObject);
		paramObject.put("info",list);


		return CommonUtil.successData(paramObject);
	}


	
	private int size(List<JSONObject> list){
		return list==null?0:list.size();
	}

	@Override
	public JSONObject getAllSalesConsultantByOrg(JSONObject jsonObject) {
		jsonObject.put("orgIdTreePath", Sessions.getStoreOrgTreePath());
		return CommonUtil.successData(userDao.getAllSalesConsultantByOrg(jsonObject));
	}







}
