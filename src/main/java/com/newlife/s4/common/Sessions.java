package com.newlife.s4.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;

public class Sessions {


	public static final void setSessionUser(JSONObject user) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
	}
	public static final void setSessionUserPermission(JSONObject userPermission) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
	}

	/**
	 * 获取当前用户用户对象
	 * @return
	 */
	public static final JSONObject getCurrentUser() {
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject user = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		return user;
	}
	/**
	 * 获取当前用户用户权限
	 * @return
	 */
	public static final JSONObject getCurrentUserPermission() {
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userPermission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
		return userPermission;
	}
	/**
	 * 获取当前用户UserName
	 * @return
	 */
	public static final String getCurrentUserName() {
		JSONObject userInfo = getCurrentUser();
		String username = userInfo==null ? "" : userInfo.getString("username");
		return username;
	}

	/**
	 * 获取当前用户UserName
	 * @return
	 */
	public static final String getCurrentUserRealName() {
		JSONObject userInfo = getCurrentUser();
		String username = userInfo==null ? "" : userInfo.getString("realName");
		return username;
	}

	/**
	 * 获取当前用户mobile
	 * @return
	 */
	public static final String getCurrentMobile() {
		JSONObject userInfo = getCurrentUser();
		String mobile = userInfo==null ? "" : userInfo.getString("mobile");
		return mobile;
	}

	/**
	 * 获取当前用户UserID
	 * @return
	 */
	public static final Long getCurrentUserID() {
		JSONObject userInfo = getCurrentUser();
		Long userId = userInfo==null ? 0L :userInfo.getLong("userId");
		return userId;
	}

	/**
	 * 获取当前用户OrgID
	 * @return
	 */
	public static final Long getOrgID() {
		JSONObject userInfo = getCurrentUser();
		Long orgId = userInfo==null ? 0L :userInfo.getLong("orgID");
		return orgId;
	}

	/**
	 * 获取当前用户OrgName
	 * @return
	 */
	public static final String getOrgName() {
		JSONObject userInfo = getCurrentUser();
		String orgName = userInfo==null ? "" :userInfo.getString("orgName");
		return orgName;
	}


	/**
	 * 获取当前用户OrgIdTreePath
	 * @return
	 */
	public static final String getOrgIdTreePath() {
		JSONObject userInfo = getCurrentUser();
		String orgIdTreePath = userInfo==null ? "" :userInfo.getString("orgIdTreePath");
		return orgIdTreePath;
	}


	/**
	 *
	 * 1 : 平台 组织
	 * 2 : 集团
	 * 3 : 公司
	 * 4 : 店
	 * 5 : 部门
	 * 6 : 组 ....
	 *
	 * 获取当前用户 当前组织的店的组织ID 例如 .89.90.91.92.93  将会返回 92
	 * @return
	 */
	public static final String getStoreOrgId() {

		if(!StringUtils.isEmpty(getOrgIdTreePath())&&getOrgIdTreePath().split("\\.").length>=5)
			return getOrgIdTreePath().split("\\.")[4];
		//没有返回一个当前组织
		return getOrgID()+"";
	}

	/**
	 * 获取当前用户 所在集团ID
	 * @return
	 */
	public static final String getGroupId() {

		if(!StringUtils.isEmpty(getOrgIdTreePath())&&getOrgIdTreePath().split("\\.").length>=5)
			return getOrgIdTreePath().split("\\.")[2];
		//没有返回一个当前组织
		return getOrgID()+"";
	}


	/**
	 * 获取当前用户 所在集团ID
	 * @return
	 */
	public static final String getGroupTreePath() {

		if(!StringUtils.isEmpty(getOrgIdTreePath())&&getOrgIdTreePath().split("\\.").length>=3)
		{
			String[] arr = getOrgIdTreePath().split("\\.");
			return  "."+arr[1]+"."+arr[2]+".";
		}else{
			//没有返回一个当前组织
			return "."+getOrgID()+"";
		}
	}
	/**
	 * 获取店的treePath
	 * @return
	 */
	public static final String getStoreOrgTreePath() {


		if(!StringUtils.isEmpty(getOrgIdTreePath())&&getOrgIdTreePath().split("\\.").length>=5)
		{
			String[] arr = getOrgIdTreePath().split("\\.");
			return  "."+arr[1]+"."+arr[2]+"."+arr[3]+"."+arr[4];
		}else{
			//没有返回一个当前组织
			return "."+getOrgID()+"";
		}

	}

	/**
	 * 获取如果有店的treePath返回店，否则向上取，最大只获取点的treePath
	 *
	 * @return
	 */
	public static final String getAutoOrgTreePath() {
		if (!StringUtils.isEmpty(getOrgIdTreePath()) && getOrgIdTreePath().split("\\.").length >= 5) {
			String[] arr = getOrgIdTreePath().split("\\.");
			return "." + arr[1] + "." + arr[2] + "." + arr[3] + "." + arr[4];
		} else if (!StringUtils.isEmpty(getOrgIdTreePath()) && getOrgIdTreePath().split("\\.").length >= 4) {
			String[] arr = getOrgIdTreePath().split("\\.");
			return "." + arr[1] + "." + arr[2] + "." + arr[3];
		} else if (!StringUtils.isEmpty(getOrgIdTreePath()) && getOrgIdTreePath().split("\\.").length >= 3) {
			String[] arr = getOrgIdTreePath().split("\\.");
			return "." + arr[1] + "." + arr[2];
		} else {
			// 没有返回一个当前组织
			return "." + getOrgID() + "";
		}
	}

	/**
	 * 当前用户是否平台管理员
	 *
	 * @return
	 */
	public static final Boolean hasRootRole() {
		JSONObject userPermission = getCurrentUserPermission();
		return isRootRole(userPermission);
	}
	/**
	 * 当前用户是否平台管理员
	 * @return
	 */
	public static final Boolean hasRootRole(JSONObject userPermission) {
		return isRootRole(userPermission);
	}
	private static final Boolean isRootRole(JSONObject userPermission) {
		return hasRole(userPermission,Constants.ROOT_ROLE_ID);
	}


	/**
	 * 当前用户是否平台管理员
	 * @return
	 */
	public static final Boolean hasStoreAdminRole() {
		JSONObject userPermission = getCurrentUserPermission();
		return isStoreAdminRole(userPermission);
	}

	/**
	 * 当前用户是否售后人员
	 * @return
	 */
	public static final Boolean hasAfterSalesRole() {
		JSONObject userPermission = getCurrentUserPermission();
		return isFinancial(userPermission);
	}

	/**
	 * 当前用户是否售后人员
	 * @return
	 */
	public static final Boolean hasFinancialRole() {
		JSONObject userPermission = getCurrentUserPermission();
		return isAfterSalesRole(userPermission);
	}

	private static final Boolean isStoreAdminRole(JSONObject userPermission) {
		return hasRole(userPermission,Constants.STORE_ADMIN_ROLE_ID);
	}

	private static final Boolean isAfterSalesRole(JSONObject userPermission) {
		return hasRole(userPermission,Constants.AFTER_SALES_ROLE_ID);
	}

	private static final Boolean isFinancial (JSONObject userPermission) {
		return hasRole(userPermission,Constants.Financial);
	}

	public static final Boolean isRole (long roleID) {
		return hasRole(getCurrentUserPermission(),roleID+"");
	}

	public static final Boolean isRole (String roleCode) {
		return hasRoleByRoleCode(getCurrentUserPermission(),roleCode);
	}
	/**
	 * 当前用户是否为销售经理
	 * @return
	 */
	public static final Boolean hasSalesManagerRole() {
		JSONObject userPermission = getCurrentUserPermission();
		return  hasRole(userPermission,"7");
	}


	private static final Boolean hasRole(JSONObject userPermission,String roleID) {
		Boolean hasRole =
				(userPermission==null)
						? false
						: (userPermission.getJSONArray("roleIdList").indexOf(roleID)!=-1);

		return hasRole;
	}

	/**
	 * 根据roleCode判断用户是否有该角色
	 * @param userPermission
	 * @param roleCode
	 * @return
	 */
	private static final Boolean hasRoleByRoleCode(JSONObject userPermission,String roleCode) {
		Boolean hasRole =
				(userPermission==null)
						? false
						: (userPermission.getJSONArray("roleCodeList").indexOf(roleCode)!=-1);

		return hasRole;
	}


	public static final String getRolesString() {
		JSONObject userPermission = getCurrentUserPermission();
		if(userPermission != null){
			JSONArray ja = userPermission.getJSONArray("roleIdList");
			String s = "";
			for(int i= 0 ; i< ja.size() ; i++)
				s=","+ja.get(i);
			return s.substring(1);

		}
		return "";
	}

	public static final Long[] getRoleIDs() {
		JSONObject userPermission = getCurrentUserPermission();
		if(userPermission != null){
			JSONArray ja = userPermission.getJSONArray("roleIdList");
			Long[] roleIDs = new Long[ja.size()];
			for(int i= 0 ; i< ja.size() ; i++)
				roleIDs[i] = ja.getLong(i);
			return roleIDs;

		}
		return null;
	}

}
