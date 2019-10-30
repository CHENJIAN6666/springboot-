package com.newlife.s4.crm.controller;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.crm.service.DictdataService;
import com.newlife.s4.util.CommonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * @author: newlife
 * @description: 
 * @date: 2018/3/25
 */
@RestController
@RequestMapping("/dictdata")
public class DictdataController {

    @Autowired
    private DictdataService dictdataService;

    /**
     * 列表
     * @param request
     * @return
     */
//    @RequiresPermissions("dictdata:list")
    @RequiresPermissions(value = {"dictdata/Visiting_Type/Visiting_Type:list",
            "dictdata/Information_Channel/Information_Channel:list",
    "dictdata/Purchase_Use/Purchase_Use:list",
    "dictdata/Consideration/Consideration:list"},logical = Logical.OR)
    @GetMapping("/listDictdata")
    public JSONObject listDictdata(HttpServletRequest request) {
        return dictdataService.listDictdata(CommonUtil.request2Json(request)); 
    }

    /**
     * 新增
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("dictdata:add")
    @RequiresPermissions(value = {"dictdata/Visiting_Type/Visiting_Type:add",
            "dictdata/Information_Channel/Information_Channel:add",
    "dictdata/Purchase_Use/Purchase_Use:add",
    "dictdata/Consideration/Consideratio:addn"},logical = Logical.OR)
    @PostMapping("/addDictdata")
    public JSONObject addDictdata(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictTypeCode,dictDataCode,dictDataText,orderNumber");
        return dictdataService.addDictdata(requestJson);
    }

    /**
     * 修改
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("dictdata:update")
    @RequiresPermissions(value = {"dictdata/Visiting_Type/Visiting_Type:update",
            "dictdata/Information_Channel/Information_Channel:update",
    "dictdata/Purchase_Use/Purchase_Use:update",
    "dictdata/Consideration/Consideration:update"},logical = Logical.OR)
    @PostMapping("/updateDictdata")
    public JSONObject updateDictdata(@RequestBody JSONObject requestJson) {
         CommonUtil.hasAllRequired(requestJson, "dictDataID,dictDataCode,dictDataText,orderNumber");
        return dictdataService.updateDictdata(requestJson);
    }
    
    /**
     * 删除
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("dictdata:delete")
    @RequiresPermissions(value = {"dictdata/Visiting_Type/Visiting_Type:delete",
            "dictdata/Information_Channel/Information_Channel:delete",
    "dictdata/Purchase_Use/Purchase_Use:delete",
    "dictdata/Consideration/Consideration:delete"},logical = Logical.OR)
    @PostMapping("/deleteDictdata")
    public JSONObject deleteDictdata(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictDataID");
        return dictdataService.deleteDictdata(requestJson);
    }
    
    /**
     * 获取单个对象
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("dictdata:list")
    @RequiresPermissions(value = {"dictdata/Visiting_Type/Visiting_Type:list",
            "dictdata/Information_Channel/Information_Channel:list",
    "dictdata/Purchase_Use/Purchase_Use:list",
    "dictdata/Consideration/Consideration:list"},logical = Logical.OR)
    @PostMapping("/getDictdata")
    public JSONObject getDictdata(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson,"dictDataID");
        return dictdataService.getDictdata(requestJson);
    }
}
