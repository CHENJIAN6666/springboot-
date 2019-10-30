package com.newlife.s4.sys.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: newlife
 * @description: 系统短信模板
 * @date: 2018/3/25
 */
public interface SmsTemplateDao {
    /**
     * 新增
     * @param jsonObject
     * @return
     */
    int addSmsTemplate(JSONObject jsonObject);

    /**
     * 统计列表总数
     * @param jsonObject
     * @return
     */
    long listCountSmsTemplate(JSONObject jsonObject);

    /**
     * 列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listSmsTemplate(JSONObject jsonObject);

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    int updateSmsTemplate(JSONObject jsonObject);

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    int deleteSmsTemplate(JSONObject jsonObject);
    
    /**
     * 获取单个对象
     * @param jsonObject
     * @return
     */
    JSONObject getSmsTemplate(JSONObject jsonObject);

    JSONObject getSmsTemplateByCode(JSONObject jsonObject);
    
    /**
     * 获取短信模板列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> getSmsTemplateList4m(JSONObject jsonObject);
    
    /**
     * 删除短信模板（只能删除自己创建的）
     * @param jsonObject
     * @return
     */
    int delSmsTemplate4m(JSONObject jsonObject);
}
