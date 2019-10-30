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
import com.newlife.s4.sys.service.PermissionService;
import com.newlife.s4.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: newlife
 * @description: 权限相关Controller
 * @date: 2017/10/24 16:04
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	  /**
     * 查询菜单列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取权限列表", notes = "获取权限列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequiresPermissions("menu:list")
    @GetMapping("/listMenus")
    public JSONObject listMenus(HttpServletRequest request) {
    	JSONObject j = CommonUtil.request2Json(request);
    	JSONObject json = permissionService.listMenus(CommonUtil.request2Json(request));
    	return json;
    	
    }
    
    /**
     * 新增菜单
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "新增菜单", notes = "新增菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("menu:add")
    @PostMapping("/addMenu")
    public JSONObject addMenu(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Menu_Name");
        return permissionService.addMenu(requestJson);
    }

    /**
     * 修改菜单
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "修改菜单", notes = "修改菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("menu:update")
    @PostMapping("/updateMenu")
    public JSONObject updateMenu(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Menu_Id,Menu_Name");
        return permissionService.updateMenu(requestJson);
    }
    
    /**
     * 删除菜单
     *
     * @param requestJson
     * @return
     */
    @ApiOperation(value = "删除菜单", notes = "删除菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("menu:delete")
    @PostMapping("/deleteMenu")
    public JSONObject deleteMenu(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "Menu_ID_Tree_Path");
        return permissionService.deleteMenu(requestJson);
    }

    @RequiresPermissions("menu:list")
    @GetMapping("/listMenuOperations")
    public JSONObject listMenuOperations() {
        return permissionService.listMenuOperations();
    }
}
