package com.newlife.s4.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.newlife.s4.config.exception.CommonJsonException;
import com.newlife.s4.common.constants.CommonEnum;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.common.constants.BaseEnum;
import com.newlife.s4.common.constants.CommonEnum;
import com.newlife.s4.common.constants.CrmEnum;

import javax.servlet.http.HttpServletRequest;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: newlife
 * @description: 本后台接口系统常用的json工具类
 * @date: 2017/10/24 10:12
 */
public class CommonUtil {

    /**
     * 返回一个returnData为空对象的成功消息的json
     *
     * @return
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为100的json
     *
     * @param returnData json里的主要内容
     * @return
     */
    public static JSONObject successJson(Object returnData) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("returnCode", CommonEnum.C_100.getCode());
        resultJson.put("returnMsg", CommonEnum.C_100.getMsg());
        resultJson.put("returnData", returnData);
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     *
     * @param errorEnum 错误码的errorEnum
     * @return
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("returnCode", errorEnum.getErrorCode());
        resultJson.put("returnMsg", errorEnum.getErrorMsg());
        resultJson.put("returnData", new JSONObject());
        return resultJson;
    }
    
    public static JSONObject errorJson(ErrorEnum errorEnum,Object returnData) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("returnCode", errorEnum.getErrorCode());
        resultJson.put("returnMsg", errorEnum.getErrorMsg());
        resultJson.put("returnData", returnData);
        return resultJson;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
     * @param list        查询分页对象list
     * @param totalCount  查询出记录的总条数
     */
    public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, long totalCount) {
        int pageRow = requestJson.getIntValue("pageRow");
        long totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        returnData.put("totalCount", totalCount);
        returnData.put("totalPage", totalPage);
        result.put("returnData", returnData);
        return result;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param list 查询分页对象list
     */
    public static JSONObject successPage(List<JSONObject> list) {
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        result.put("returnData", returnData);
        return result;
    }

    /**
     * 获取总页数
     *
     * @param pageRow   每页行数
     * @param itemCount 结果的总条数
     * @return
     */
    public static long getPageCounts(int pageRow, long itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageRow > 0 ?
                itemCount / pageRow + 1 :
                itemCount / pageRow;
    }

    /**
     * 将request参数值转为json
     *
     * @param request
     * @return
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     *
     * @param request
     * @param requiredColumns
     * @return
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     * @param jsonObject
     * @return
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringTools.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringTools.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringTools.isNullOrEmpty(missCol)) {
                jsonObject.clear();
                jsonObject.put("returnCode", ErrorEnum.E_90003.getErrorCode());
                jsonObject.put("returnMsg", "缺少必填参数:" + missCol.trim());
                jsonObject.put("returnData", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        }
    }
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJson(Class<? extends Enum> enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodValues = enumClass.getMethod("values");
        Object invoke = methodValues.invoke(null);
        int length = java.lang.reflect.Array.getLength(invoke);
        List<Object> values = new ArrayList<Object>();
        for (int i=0; i<length; i++) {
            values.add(java.lang.reflect.Array.get(invoke, i));
        }

        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(enumClass);

        return JSON.toJSONString(values,config);
    }
    
    
    

    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param paramObject    查询条件json
     * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
     */
    public static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    /**
     * 分页查询之前的处理参数
     * 没有传pageRow参数时,默认每页10条.
     *
     * @param paramObject
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }
    
   
    
    /**
     * 移动端  通用 100    正常返回数据
     *
     * @param list 查询分页对象list
     */
    public static JSONObject successData(Object returnData) {
        JSONObject result = successJson();
        result.put("returnCode", CommonEnum.C_100.getCode());
        result.put("returnMsg", CommonEnum.C_100.getMsg());
        result.put("returnData", returnData);
        return result;
    }
    
    /**
     * 移动端  自定义返回信息  (带return data)
     *
     * @param
     */
    public static JSONObject successData(String code,String msg,Object returnData) {
        JSONObject result = successJson();
        result.put("returnCode", code);
        result.put("returnMsg", msg);
        result.put("returnData", returnData);
        return result;
    }
    
    /**
     * 移动端  自定义返回信息 
     *
     * @param
     */
    public static JSONObject successData(String code,String msg) {
    	JSONObject result = successJson();
    	result.put("returnCode", code);
    	result.put("returnMsg", msg);
    	result.put("returnData", null);
    	return result;
    }
    
    
    /**
     * 移动端  自定义返回信息 
     *
     * @param
     */
    public static JSONObject successData() {
    	return successData(new JSONObject());
    }

    /**
     * 扩展分页参数
     *
     * @param jsonObject
     */
    public static void expandPagination(JSONObject jsonObject) {
        if (!jsonObject.containsKey("pageNum") || jsonObject.get("pageNum") == null)
            jsonObject.put("pageNum", 1);
        if (!jsonObject.containsKey("pageRow") || jsonObject.get("pageRow") == null)
            jsonObject.put("pageRow", 10);
        //添加limit & pages
        fillPageParam(jsonObject);
    }

    /**
     * 获取 jsonList 中的第一条数据 没有则返回null
     * @param lj
     */
    public static Object getJSONListFirst(List<JSONObject> lj,String key){
        if(lj.size() > 0) {
            return lj.get(0).get(key);
        }
        return null;
    }


    /**
     * 查询分页结果后的封装工具方法   附加 一个JSON
     *
     * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
     * @param list        查询分页对象list
     * @param totalCount  查询出记录的总条数
     */
    public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, long totalCount,JSONObject json) {
        int pageRow = requestJson.getIntValue("pageRow");
        long totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        returnData.put("json", json);
        returnData.put("totalCount", totalCount);
        returnData.put("totalPage", totalPage);
        result.put("returnData", returnData);
        return result;
    }

}
