package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: newlife
 * @description: 附件
 * @date: 2018/08/23
 */
public interface AttachmentDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addAttachment(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountAttachment(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listAttachment(JSONObject jsonObject);

    List<JSONObject> listAttachmentByCarSalesOrderNumber(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateAttachment(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteAttachment(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getAttachment(JSONObject jsonObject);
}
