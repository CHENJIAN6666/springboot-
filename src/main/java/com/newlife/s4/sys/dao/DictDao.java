package com.newlife.s4.sys.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据字典分类
 * @author jxxsh
 *
 */
public interface DictDao {

	/**
	 * 添加分类
	 * @param jsonObejct
	 * @return
	 */
	int addDictType(JSONObject jsonObject);
	
	/**
	 * 分类列表
	 * @param jsonObject
	 * @return
	 */
	List<JSONObject> listDictTypes(JSONObject jsonObject);
	
	/**
	 * 更新分类
	 * @param jsonObject
	 * @return
	 */
	int updateDictType(JSONObject jsonObject);
	
	/**
	 * 删除分类
	 * @param jsonObejct
	 * @return
	 */
	int deleteDictType(JSONObject jsonObject);
	
	/**
	 * 查询分类树形ID
	 * @param jsonObejct
	 * @return
	 */
	String getDictTypeTreePath(JSONObject jsonObject);
	
	/**
	 * 更新分类树形ID
	 * @param jsonObject
	 * @return
	 */
	int updateDictTypeTreePath(JSONObject jsonObject);
	
	/**
	 * 查询分类树形Level
	 * @param jsonObject
	 * @return
	 */
	int getDictTypeLevel(JSONObject jsonObject);
	
	/**
	 * 更新分类树形Level
	 * @param jsonObject
	 * @return
	 */
	int updateDictTypeLevel(JSONObject jsonObject);
	
	
	/**
	 * 统计分类总数
	 * @param jsonObject
	 * @return
	 */
	int countDictType(JSONObject jsonObject);
	
	/**
	 * 新增项目
	 * @param jsonObject
	 * @return
	 */
	int addDictData(JSONObject jsonObject);
	
	/**
	 * 删除项目
	 * @param jsonObject
	 * @return
	 */
	int deleteDictData(JSONObject jsonObject);
	
	/**
	 * 项目列表
	 * @param jsonObject
	 * @return
	 */
	List<JSONObject> listDictDatas(JSONObject jsonObject);
	
	/**
	 * 修改项目
	 * @param jsonObject
	 * @return
	 */
	int updateDictData(JSONObject jsonObject);
	
	/**
	 * 统计项目总数
	 * @param jsonObject
	 * @return
	 */
	int countDictData(JSONObject jsonObject);
	
	/**
	 * 查找项目
	 * @param jsonObject
	 * @return
	 */
	List<JSONObject> getDictData(JSONObject jsonObject);
	
	/**
	 * 
	 */
	JSONObject queryDictData(JSONObject jsonObject);
}
