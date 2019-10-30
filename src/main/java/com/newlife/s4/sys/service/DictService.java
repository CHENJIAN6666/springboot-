package com.newlife.s4.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author jxxsh
 *
 */
public interface DictService {

	/**
	 * 新增分类
	 * @param jsonObject
	 * @return
	 */
	JSONObject addDictType(JSONObject jsonObject);
	
	/**
	 * 分类列表
	 * @param jsonObejct
	 * @return
	 */
	JSONObject listDictType(JSONObject jsonObject);
	
	/**
	 * 删除分类
	 * @param jsonObject
	 * @return
	 */
	JSONObject deleteDictType(JSONObject jsonObject);
	
	/**
	 * 更新分类
	 * @param jsonObject
	 * @return
	 */
	JSONObject updateDictType(JSONObject jsonObject);
	
	/**
	 * 新增项目
	 * @param jsonObject
	 * @return
	 */
	JSONObject addDictData(JSONObject jsonObject);
	
	/**
	 * 删除项目
	 * @param jsonObject
	 * @return
	 */
	JSONObject deleteDictData(JSONObject jsonObject);
	
	/**
	 * 修改项目
	 * @param jsonObject
	 * @return
	 */
	JSONObject updateDictData(JSONObject jsonObject);
	
	/**
	 * 项目列表
	 * @param jsonObject
	 * @return
	 */
	JSONObject listDictDatas(JSONObject jsonObject);
	
	
	/**
	 * 查找项目
	 * @param jsonObject
	 * @return
	 */
	JSONObject getDictData(JSONObject jsonObject);

	/**
	 * 查找项目
	 * @param jsonObject
	 * @return
	 */
	JSONObject queryDictData(JSONObject jsonObject);

	 JSONObject buildPermissionMap(String permissionName);

	 Boolean checkRolePermission(JSONObject permissionMap,String permission,Long[] roles);
}
