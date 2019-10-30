package com.newlife.s4.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.crm.dao.DictdataDao;
import com.newlife.s4.crm.service.DictdataService;
import com.newlife.s4.util.CommonUtil;

/**
 * @author: newlife
 * @description:
 * @date: 2018/3/25
 */
@Service
public class DictdataServiceImpl implements DictdataService {

	@Autowired
	private DictdataDao dictdataDao;

	/**
	 * 新增
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addDictdata(JSONObject jsonObject) {
		jsonObject.put("createUser", Sessions.getCurrentUserID());
		jsonObject.put("modifyUser", Sessions.getCurrentUserID());
		jsonObject.put("orgID", Sessions.getOrgID());
		jsonObject.put("status", 0);
		dictdataDao.addDictdata(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 列表
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject listDictdata(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		jsonObject.put("orgID", Sessions.getOrgID());
		long count = dictdataDao.listCountDictdata(jsonObject);
		List<JSONObject> list = dictdataDao.listDictdata(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 修改
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDictdata(JSONObject jsonObject) {
		dictdataDao.updateDictdata(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 删除
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteDictdata(JSONObject jsonObject) {
		dictdataDao.deleteDictdata(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 获取单个对象
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject getDictdata(JSONObject jsonObject) {
		JSONObject model = dictdataDao.getDictdata(jsonObject);
		return CommonUtil.successJson(model);
	}
	
	
	@Override
	public void setListDict(List<JSONObject> list, JSONArray params) {
		
		if(null==params||params.size()<=0) return;
		if(null==list||list.size()<=0) return;
		
		//加载 dict 字典数据到map
		Map<String,String> dictMap=new HashMap();
		
		String listID="",dictCode="",pListIDName="listID",pDictCodeName="dictCode";
		
		Long orgID=Sessions.getOrgID();
		List<JSONObject> lj=null;
		
		//读取params的 字典
		for(int i=0;i<params.size();i++)
		{
			dictCode=params.getJSONObject(i).getString(pDictCodeName);
			listID=params.getJSONObject(i).getString(pListIDName);
			
			//dictcode=""时 取listID
			if(dictCode==null||dictCode.equals(""))
			{
				
				//首字母大写
				dictCode=listID.substring(0, 1).toUpperCase() + listID.substring(1);
//				dictCode = dictCode.replace("_","");
				params.getJSONObject(i).put(pDictCodeName, dictCode);
			}
			
			lj=dictdataDao.listDictdataByDictTypeCode(
					JSONObject.parseObject("{dictTypeCode:'"+dictCode+"',orgID:"+orgID+"}"));
			
			if(null!=lj&&lj.size()>0) {
				for(JSONObject j:lj)
				dictMap.put(dictCode+j.getString("dictDataCode"), j.getString("dictDataText"));
			}
			
		}
		
		//设值
		for(JSONObject js: list)
		{
			for(int i=0;i<params.size();i++)
			{
				dictCode=params.getJSONObject(i).getString(pDictCodeName);
				listID=params.getJSONObject(i).getString(pListIDName);
				js.put(listID+"Name", dictMap.get(dictCode+js.getString(listID)));
			}
			
			
		}
		
		
	}
	
	
	
	@Override
	public void setObjectDict(JSONObject jsonObject, JSONArray params) {
		
		if(null==params||params.size()<=0) return;
		if(null==jsonObject) return;
		
		String listID="",dictCode="",pListIDName="listID",pDictCodeName="dictCode";
		
		Long orgID=Sessions.getOrgID();
		List<JSONObject> lj=null;

		//读取params的 字典
		for(int i=0;i<params.size();i++)
		{
			dictCode=params.getJSONObject(i).getString(pDictCodeName);
			listID=params.getJSONObject(i).getString(pListIDName);
			
			if(dictCode==null||dictCode.equals(""))
				dictCode=listID.substring(0, 1).toUpperCase() + listID.substring(1);  ;
			
			if(jsonObject.get(listID) != null && !jsonObject.get(listID).equals("")){
				lj=dictdataDao.listDictdataByDictTypeCode(
							JSONObject.parseObject("{dictTypeCode:'"+dictCode+"',dictDataCode:'"
													 	+jsonObject.get(listID)+"',orgID:"+orgID+"}"));
				
				if(null!=lj && lj.size()>0)
					jsonObject.put(listID+"Name", lj.get(0).get("dictDataText"));
			}

		}
		
	}
	
	@Override
	public void setObjectDictByString(JSONObject jsonObject, String str) {
		
		String[] ss=str.split(",");
		JSONArray ja=new JSONArray();
		for(String vs:ss){
			ja.add(JSONObject.parse("{'listID':'"+vs+"'}"));
		}
		setObjectDict(jsonObject,ja);
		
	}

	@Override
	public void setListDictByString(List<JSONObject> jsonObject, String str) {
		String[] ss=str.split(",");
		JSONArray ja=new JSONArray();
		for(String vs:ss){
			ja.add(JSONObject.parse("{'listID':'"+vs+"'}"));
		}
		setListDict(jsonObject,ja);
	}

	/**
	 * 获取字典表列表
	 *
	 * @param paramObject
	 * @return
	 */
	@Override
	public JSONObject getByDictdata(JSONObject paramObject) {
		paramObject.put("orgID", Sessions.getStoreOrgId());
		List<JSONObject> list = dictdataDao.listDictdataByDictTypeCode(paramObject);
		return CommonUtil.successData(list);
	}


	/**
	 *  获取 字典表设置 ，传入参数代码 返回 第一个项目的值
	 *  例如  获取 租车客户ID  getDictDataByName("")
	 */
	@Override
	public String getDictDataByName(String dictTypeName){
		JSONObject queryDict = new JSONObject();
		queryDict.put("dictTypeCode",dictTypeName);
		queryDict.put("orgID","86");
		List<JSONObject> ls = dictdataDao.listDictdataByDictTypeCode(queryDict);

		if(ls.size() == 0 ){
			return null;
		}

		return ls.get(0).getString("dictDataText");


	}

	@Override
	public List<JSONObject> getDictDataListByName(String dictTypeName){
		JSONObject queryDict = new JSONObject();
		queryDict.put("dictTypeCode",dictTypeName);
		queryDict.put("orgID","86");
		List<JSONObject> ls = dictdataDao.listDictdataByDictTypeCode(queryDict);
		return ls;
	}

}
