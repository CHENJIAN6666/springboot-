package com.newlife.s4.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.service.DictService;
import com.newlife.s4.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: newlife
 * @description: 字典相关Controller
 * @date: 2017/10/24 16:04
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 字典列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字典列表", notes = "字典列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:list")
    @GetMapping("/listDictTypes")
    public JSONObject listDictTypes(HttpServletRequest request) {
    	JSONObject json = dictService.listDictType(CommonUtil.request2Json(request));
    	return json;
    	
    }

    /**
     * 新增字典分类
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "新增字典分类", notes = "新增字典分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:add")
    @PostMapping("/addDictType")
    public JSONObject addDictType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictTypeText");
        return dictService.addDictType(requestJson);
    }

    /**
     * 修改字典分类
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "修改字典分类", notes = "修改字典分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:update")
    @PostMapping("/updateDictType")
    public JSONObject updateDictType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictTypeId,dictTypeText");
        return dictService.updateDictType(requestJson);
    }
    
    /**
     * 删除字典分类
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "删除字典分类", notes = "删除字典分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:delete")
    @PostMapping("/deleteDictType")
    public JSONObject deleteDictType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictTypeIdTreePath");
        return dictService.deleteDictType(requestJson);
    }
    
    /**
     * 字典项目列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字典项目列表", notes = "字典项目列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresPermissions("dict:list")
    @GetMapping("/listDictDatas")
    public JSONObject listDictDatas(HttpServletRequest request) {
    	JSONObject json = dictService.listDictDatas(CommonUtil.request2Json(request));
    	return json;
    	
    }
    
    /**
     * 新增字典数据
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "新增字典数据", notes = "新增字典数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:add")
    @PostMapping("/addDictData")
    public JSONObject addDictData(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictDataText");
        return dictService.addDictData(requestJson);
    }

    
    /**
     * 修改字典数据
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "修改字典数据", notes = "修改字典数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:update")
    @PostMapping("/updateDictData")
    public JSONObject updateDictData(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictDataId,dictDataText");
        return dictService.updateDictData(requestJson);
    }
    
    /**
     * 删除字典数据
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "删除字典数据", notes = "删除字典数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:delete")
    @PostMapping("/deleteDictData")
    public JSONObject deleteDictData(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "dictDataId");
        return dictService.deleteDictData(requestJson);
    }
    
    /**
     * 根据DictTypeCode和DictDataCode查找字典项目
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字典项目", notes = "字典项目", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:list")
    @GetMapping("/getDictData")
    public JSONObject getDictData(HttpServletRequest request) {
    	JSONObject json = dictService.getDictData(CommonUtil.request2Json(request));
    	return json;
    	
    }
    
    /**
     * 查找字典项目
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字典项目", notes = "字典项目", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("dict:list")
    @GetMapping("/queryDictData")
    public JSONObject queryDictData(HttpServletRequest request) {
    	JSONObject json = dictService.queryDictData(CommonUtil.request2Json(request));
    	return json;
    	
    }
}
