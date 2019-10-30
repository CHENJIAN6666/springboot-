package com.newlife.s4.common.model;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: MyBatis的一对多JSON返回对象
 */
public class MobileOne2Many extends JSONObject{

	  private List<JSONObject> brandList;
	  private List<JSONObject> modelList;
	  private List<JSONObject> carModelColor;
	  private List<JSONObject> carInteriorColor;
	  private List<JSONObject> favoritesCarList;
	  private List<JSONObject> browseCarList;
	  private List<JSONObject> timeList;

	  private List<JSONObject> labelList;


	  private List<JSONObject> favoritesShopList;
	  private List<JSONObject> fllowList;

	  private List<JSONObject> userList;
	  private List<JSONObject> orgModelLabel;
	  private List<JSONObject> modelDiscountLabelList;
	  private List<JSONObject> carBrandList;
	  private List<JSONObject> callPurchaseConsultationList;
	  private List<JSONObject> visitFollowList;
}
