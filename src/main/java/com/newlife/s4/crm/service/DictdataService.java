package com.newlife.s4.crm.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
public interface DictdataService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addDictdata(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listDictdata(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateDictdata(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteDictdata(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getDictdata(JSONObject jsonObject);
    
	/**
	 * 
	 * 按params 设置list的内的值
	 * 
	 * 用法:
	 * List<JSONObject> list = carSalesOrderDao.listCarSalesOrder(jsonObject);
	 * JSONArray params=JSONArray.parseArray("[{listID:purchaseWay,dictCode:purchaseWay}]");
	 * dictService.setListDict(list,params);
	 * 
	 * 封装字典表中的文本到list中 
	 * @param list
	 * @param params
	 */
	void setListDict(List<JSONObject> list, JSONArray params);

	/**
	 * 封装字典表中的文本到对象中  用法 :
	 *       JSONArray params=JSONArray.parseArray("[{listID:'purchaseWay'},{listID:'salesType'}]");
     *			dictdataService.setObjectDict(model,params);
	 * @param list
	 * @param params
	 */
	void setObjectDict(JSONObject jsonObject, JSONArray params);

	/**
	 * by string 封装 String  str= "purchaseWay,salesType"
	 * @param jsonObject
	 * @param str
	 */
	void setObjectDictByString(JSONObject jsonObject, String str);

	void setListDictByString(List<JSONObject> jsonObject, String str);

	/**
	 * 获取字典表列表
	 * @param jsonObject
	 * @return
	 */
	JSONObject getByDictdata(JSONObject jsonObject);


	/**
	 *  获取 字典表设置 ，传入参数代码 返回 第一个项目的值
	 *  例如  获取 租车客户ID  getDictDataByName("Rent_Car_Customer_ID")
	 */

	String getDictDataByName(String dictTypeName);

	List<JSONObject> getDictDataListByName(String dictTypeName);
}
