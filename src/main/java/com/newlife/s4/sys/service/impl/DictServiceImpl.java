package com.newlife.s4.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.newlife.s4.crm.service.DictdataService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.common.TreeBuilder;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.common.model.Node;
import com.newlife.s4.crm.dao.DictdataDao;
import com.newlife.s4.sys.dao.DictDao;
import com.newlife.s4.sys.service.DictService;
import com.newlife.s4.util.CommonUtil;


/**
 * 
 * @author jxxsh
 *
 */
@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictDao dictDao;

	@Autowired
	private DictdataService dictdataService;

	@Override
	public JSONObject addDictType(JSONObject jsonObject) {
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		Integer username = userInfo.getInteger("userId");
		jsonObject.put("createUser", username);
		jsonObject.put("modifyUser", username);
		jsonObject.put("orgID", Sessions.getOrgID());
		
		dictDao.addDictType(jsonObject);
		
		// 子节点
		if(null != jsonObject.get("parentDictTypeId") && !"".equals(jsonObject.get("parentDictTypeId"))) {
			Integer parentDictTypeId = jsonObject.getInteger("parentDictTypeId");
			//查询父节点TreePath
			JSONObject j = new JSONObject();
			j.put("dictTypeId", parentDictTypeId);
			String parentTreePath = dictDao.getDictTypeTreePath(j);
			StringBuffer sb = new StringBuffer(parentTreePath);
			sb.append(jsonObject.get("dictTypeId"));
			sb.append(".");
			jsonObject.put("dictTypeIdTreePath", sb.toString());
			//更新treePath
			dictDao.updateDictTypeTreePath(jsonObject);
			
			//查询父节点Level
			int level = dictDao.getDictTypeLevel(j);
			level +=1;
			jsonObject.put("dictTypeLevel", level);
			//更新Level
			dictDao.updateDictTypeLevel(jsonObject);
			
		}else {
			// 根节点
			jsonObject.put("parentDictTypeId", null);
			String s = jsonObject.get("dictTypeId").toString();
			StringBuffer sb = new StringBuffer(s);
			sb.insert(0, ".");
			sb.append(".");
			jsonObject.put("dictTypeIdTreePath", sb.toString());
			dictDao.updateDictTypeTreePath(jsonObject);
			
			jsonObject.put("dictTypeLevel", 0);
			dictDao.updateDictTypeLevel(jsonObject);
		}

		
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject listDictType(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = dictDao.countDictType(jsonObject);
		jsonObject.put("orgID", Sessions.getOrgID());
		List<JSONObject> list = dictDao.listDictTypes(jsonObject);
		List<Node> nodeList = new ArrayList<Node>();
		for (JSONObject json : list) {
			Node n =  new Node();
			n.setId(json.getString("dictTypeId"));
			n.setLabel(json.getString("dictTypeText"));
			n.setPid(json.getString("parentDictTypeId"));
			JSONObject nodeData = new JSONObject();
			nodeData.put("dictTypeCode", json.getString("dictTypeCode"));
			nodeData.put("dictTypeText", json.getString("dictTypeText"));
			nodeData.put("parentDictTypeId", json.getString("parentDictTypeId"));
			nodeData.put("dictTypeIdTreePath", json.getString("dictTypeIdTreePath"));
			n.setNodeData(nodeData);
			nodeList.add(n);
		}
		 List<JSONObject> list2 = new TreeBuilder().buildTree(nodeList);
		return CommonUtil.successPage(jsonObject, list2, count);
	}

	@Override
	public JSONObject deleteDictType(JSONObject jsonObject) {
		String temp = jsonObject.get("dictTypeIdTreePath").toString();
		jsonObject.put("dictTypeIdTreePath", temp);
		dictDao.deleteDictType(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject updateDictType(JSONObject jsonObject) {
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		Integer username = userInfo.getInteger("userId");
		jsonObject.put("modifyUser", username);
		dictDao.updateDictType(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject addDictData(JSONObject jsonObject) {
		jsonObject.put("orgID", Sessions.getOrgID());
		dictDao.addDictData(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject deleteDictData(JSONObject jsonObject) {
		dictDao.deleteDictData(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject updateDictData(JSONObject jsonObject) {
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		Integer username = userInfo.getInteger("userId");
		jsonObject.put("modifyUser", username);
		dictDao.updateDictData(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject listDictDatas(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = dictDao.countDictData(jsonObject);
		jsonObject.put("orgID", Sessions.getStoreOrgId());
		List<JSONObject> list = dictDao.listDictDatas(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}
	
	@Override
	public JSONObject getDictData(JSONObject jsonObject) {
		List<JSONObject> list = dictDao.getDictData(jsonObject);
		return CommonUtil.successPage(list);
	}
	
	@Override
	public JSONObject queryDictData(JSONObject jsonObject) { 
		return CommonUtil.successJson(dictDao.queryDictData(jsonObject));
	}


	/**  buildPermissionMap , checkRolePermission  为 后台列表管控所用，可以按角色不同 查看记录
	 *
	 *   使用方法 查看示例 	RentorderServiceImpl.listRentorder
	 *
	 *  字典表 权限值
	 *         permissionName
	 * @return
	 */

	@Override
	public JSONObject buildPermissionMap(String permissionName){
		final List<JSONObject> permissionRoles = dictdataService.getDictDataListByName(permissionName);
		JSONObject m = new JSONObject();
		for (JSONObject pr: permissionRoles){
			m.put(pr.getString("dictDataCode"),pr.getString("dictDataText"));
		}
		return m;
	}

	/**
	 *
	 *    checkRolePermission :
	 *          1.See_Below_OrgData_RoleIDs  为查看所有组织所有记录
	 *          2.See_All_Data_RoleIDs 查看本组织下所有记录
	 *          3.默认 仅查看 自己的客户数据
	 *
	 */
	@Override
	public Boolean checkRolePermission(JSONObject permissionMap,String permission,Long[] roles){
		String text = permissionMap.getString(permission);
		final String[] ss = text.split(",");
		for (int i = 0; i < ss.length; i++) {
			for (int j = 0; j < roles.length; j++) {
				if(ss[i].equals(roles[j].toString())){
					return true;
				}
			}
		}
		return false;
	}


}
