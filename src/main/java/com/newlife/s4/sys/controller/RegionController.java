package com.newlife.s4.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.sys.service.RegionService;
import com.newlife.s4.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * @author: newlife
 * @description: 地区
 * @date: 2018/06/02
 */
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    /**
     * 列表
     * @param request
     * @return
     */
    @RequiresPermissions("region:list")
    @GetMapping("/listRegion")
    public JSONObject listRegion(HttpServletRequest request) {
        return regionService.listRegion(CommonUtil.request2Json(request));
    }

//    /**
//     * 新增
//     * @param requestJson
//     * @return
//     */
//    @RequiresPermissions("region:add")
//    @PostMapping("/addRegion")
//    public JSONObject addRegion(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "regionID,regionName,parentRegionID,shortName,regionLevel,cityCode,zipCode,mergerName,lng,lat,pinyin");
//        return regionService.addRegion(requestJson);
//    }
//
//    /**
//     * 修改
//     * @param requestJson
//     * @return
//     */
//    @RequiresPermissions("region:update")
//    @PostMapping("/updateRegion")
//    public JSONObject updateRegion(@RequestBody JSONObject requestJson) {
//         CommonUtil.hasAllRequired(requestJson, "regionID,regionName,parentRegionID,shortName,regionLevel,cityCode,zipCode,mergerName,lng,lat,pinyin");
//        return regionService.updateRegion(requestJson);
//    }
//
//    /**
//     * 删除
//     * @param requestJson
//     * @return
//     */
//    @RequiresPermissions("region:delete")
//    @PostMapping("/deleteRegion")
//    public JSONObject deleteRegion(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "regionID");
//        return regionService.deleteRegion(requestJson);
//    }
    
//    /**
//     * 获取单个对象
//     * @param requestJson
//     * @return
//     */
//    @RequiresPermissions("region:list")
//    @PostMapping("/getRegion")
//    public JSONObject getRegion(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson,"regionID");
//        return regionService.getRegion(requestJson);
//    }
}
