package com.newlife.s4.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.service.OrganizationsService;
import com.newlife.s4.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: newlife
 * @description: 组织相关Controller
 * @date: 2017/10/24 16:04
 */
@Api(tags = "组织结构管理")
@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

    @Autowired
    private OrganizationsService OrganizationsService;

    /**
     * 查询组织列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取组织列表", notes = "获取组织列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresPermissions("organizations:list")
    @GetMapping("/listOrganizations")
    public JSONObject listOrganizations(HttpServletRequest request) {
    	JSONObject json = OrganizationsService.listOrganizations(CommonUtil.request2Json(request));
    	return json;
    	
    }

    /**
     * 新增组织
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "新增组织", notes = "新增组织", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("organizations:add")
    @PostMapping("/addOrganizations")
    public JSONObject addOrganizations(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Org_Name");
        return OrganizationsService.addOrganizations(requestJson);
    }

    /**
     * 修改组织
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "修改组织", notes = "修改组织", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("organizations:update")
    @PostMapping("/updateOrganizations")
    public JSONObject updateOrganizations(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Org_Id,Org_Name");
        return OrganizationsService.updateOrganizations(requestJson);
    }
    
    /**
     * 删除组织
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "删除组织", notes = "删除组织", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("organizations:delete")
    @PostMapping("/deleteOrganizations")
    public JSONObject deleteOrganizations(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Org_ID_Tree_Path");
        return OrganizationsService.deleteOrganizations(requestJson);
    }
    
    

    /**
     * 查询组织列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取组织", notes = "获取组织", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresPermissions("organizations:list")
    @GetMapping("/getOrganizations")
    public JSONObject getOrganizations(HttpServletRequest request) {
    	return OrganizationsService.getOrganizations(CommonUtil.request2Json(request));
    	
    }

    /**
     * 创建二维码
     * @param requestJson
     * @return
     */
    @RequiresPermissions("organizations:update")
    @PostMapping("/createQRCode")
    public JSONObject createQRCode(@RequestBody JSONObject requestJson,HttpServletRequest request) {
        CommonUtil.hasAllRequired(requestJson, "Org_Id,Org_Logo");
        return OrganizationsService.createQRCode(requestJson,request);
    }

    /**
     * 创建二维码
     * @param requestJson
     * @return
     */
    @RequiresPermissions("organizations:list")
    @GetMapping("/downloadQRCode")
    public void downloadQRCode( HttpServletRequest request, HttpServletResponse response) {
         JSONObject requestJson = CommonUtil.request2Json(request);
        CommonUtil.hasAllRequired(requestJson, "Org_Id,Org_Logo");
        OrganizationsService.downloadQRCode(requestJson,request,response);
    }

    /**
     * 查询组织名称列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取组织", notes = "获取组织", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresPermissions("organizations:list")
    @GetMapping("/getOrganizationsList")
    public JSONObject getOrganizationsList(HttpServletRequest request) {
        return OrganizationsService.getOrganizationsList(CommonUtil.request2Json(request));

    }
}
