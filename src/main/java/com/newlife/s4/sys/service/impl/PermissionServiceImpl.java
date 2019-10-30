package com.newlife.s4.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.common.TreeBuilder;
import com.newlife.s4.common.model.Node;
import com.newlife.s4.sys.dao.PermissionDao;
import com.newlife.s4.sys.service.PermissionService;
import com.newlife.s4.util.CommonUtil;
import springfox.documentation.spring.web.json.Json;


/**
 * @author: newlife
 * @description:
 * @date: 2017/10/30 13:15
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     *
     * @param username
     * @return
     */
    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = getUserPermissionFromDB(username);
        return userPermission;
    }

    /**
     * 从数据库查询用户权限信息
     *
     * @param username
     * @return
     */
    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = permissionDao.getUserPermission(username);
        //管理员角色ID为1
        //int adminRoleId = 1;
        //如果是管理员
        //String roleIdKey = "roleId";
        //if (adminRoleId == userPermission.getIntValue(roleIdKey)) {

        if (Sessions.hasRootRole(userPermission)) {
            //查询所有菜单  所有权限
            Set<String> menuList = permissionDao.getAllMenu();
            Set<String> permissionList = permissionDao.getAllPermission();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;
    }

	@Override
	public JSONObject listMenus(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = 1;
		if (!Sessions.hasRootRole()) {
			jsonObject.put("orgID", Sessions.getOrgID());
		}
		List<JSONObject> list = permissionDao.listMenus(jsonObject);
		List<Node> nodeList = new ArrayList<Node>();
		for (JSONObject json : list) {
			Node n =  new Node();
			n.setId(json.getString("id"));
			n.setLabel(json.getString("label"));
			n.setPid(json.getString("pid"));
			JSONObject nodeData = new JSONObject();
			nodeData.put("menuCode", json.getString("menuCode"));
			nodeData.put("menuFullName", json.getString("menuFullName"));
			nodeData.put("menuIdTreePath", json.getString("menuIdTreePath"));
			nodeData.put("operations", json.getJSONArray("operations"));
			n.setNodeData(nodeData);
			nodeList.add(n);
		}
		 List<JSONObject> list2 = new TreeBuilder().buildTree(nodeList);
		return CommonUtil.successPage(jsonObject, list2, count);
	}

	/**
	 * 新增菜单
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addMenu(JSONObject jsonObject) {
		permissionDao.addMenu(jsonObject);
		//更新树形ID
		if(null !=jsonObject.get("Parent_Menu_ID") && !"".equals(jsonObject.get("Parent_Menu_ID"))) {

			Integer parentOrgId = jsonObject.getInteger("Parent_Menu_ID");
			JSONObject json2 = new JSONObject();
			json2.put("Parent_Menu_ID", parentOrgId);
			String treePath = permissionDao.getMenuIdTreePath(json2);
			StringBuffer sb = new StringBuffer(treePath);
			sb.append(jsonObject.get("Menu_Id"));
			sb.append(".");
			json2.put("Menu_ID_Tree_Path", sb.toString());
			json2.put("Menu_Id", jsonObject.get("Menu_Id"));
			permissionDao.updateMenuIdTreePath(json2);
			json2.put("Menu_Id", parentOrgId);
			Integer orgLevel = permissionDao.getMenuLevel(json2);
			orgLevel +=1;
			json2.put("Menu_Id", jsonObject.get("Menu_Id"));
			json2.put("Menu_Level", orgLevel);
			permissionDao.updateMenuLevel(json2);
		}else {
			
			JSONObject json = new JSONObject();
			StringBuffer sb = new StringBuffer();
			sb.insert(0, ".");
			sb.append(jsonObject.get("Menu_Id"));
			sb.append(".");
			json.put("Menu_ID_Tree_Path", sb.toString());
			json.put("Menu_Id", jsonObject.get("Menu_Id"));
			permissionDao.updateMenuIdTreePath(json);
			json.put("Menu_Level", 0);
			permissionDao.updateMenuLevel(json);
		}
		addOperations(jsonObject);
//		permissionDao.addMenuOperations(jsonObject);
		return CommonUtil.successJson();
	}

	private void addOperations(JSONObject jsonObject){
		JSONArray opList = jsonObject.getJSONArray("menuOpList");
		if( null != opList && opList.size() > 0){
			permissionDao.addMenuOperations(jsonObject);
		}
	}
	/**
	 * 更新菜单
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateMenu(JSONObject jsonObject) {
		permissionDao.updateMenu(jsonObject);
		permissionDao.delMenuOperations(jsonObject);
//		permissionDao.addMenuOperations(jsonObject);
		addOperations(jsonObject);
		return CommonUtil.successJson();
	}
	
	/**
	 * 更新文章
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteMenu(JSONObject jsonObject) {
		String temp = jsonObject.get("Menu_ID_Tree_Path").toString()+"%";
		jsonObject.put("Menu_ID_Tree_Path", temp);
		permissionDao.deleteMenu(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject listMenuOperations(){
		return CommonUtil.successJson(permissionDao.listMenuOperations());
	}
}
