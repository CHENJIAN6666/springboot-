package com.newlife.s4.common.model;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: MyBatis的一对多JSON返回对象
 */
public class FinOne2Many extends JSONObject{

	  List<JSONObject> detailList;
}
