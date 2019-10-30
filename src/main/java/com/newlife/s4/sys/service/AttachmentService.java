package com.newlife.s4.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: newlife
 * @description: 附件
 * @date: 2018/08/23
 */
public interface AttachmentService {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    JSONObject addAttachment(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    JSONObject listAttachment(JSONObject jsonObject);


    JSONObject listAttachmentByCarSalesOrderNumber(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    JSONObject updateAttachment(JSONObject jsonObject);
    
    /**
     * 删除
     * @param jsonObject
     * @return
     */
    JSONObject deleteAttachment(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getAttachment(JSONObject jsonObject);
}
