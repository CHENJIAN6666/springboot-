package com.newlife.s4.config.aspect;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 设置jsonObject 中值为"null"和空字符串的，改成null
 * 因为空字符串插入数据库有可能会报错
 */
@Aspect
@Component
public class SetParamNull {


    @Before("execution(public * com.newlife.s4..*Dao.update*(..)) || execution(public * com.newlife.s4..*Dao.add*(..))")
    public void doBefore(JoinPoint joinPoint){//用于获取类方法
//        System.out.println("JSONObject值为空的设置成null");
        for (Object obj : joinPoint.getArgs())
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                for (Map.Entry<String,Object> entry : jsonObject.entrySet()) {
                    Object value = entry.getValue();
                    if(value instanceof String){
                        if(StringUtils.isEmpty((String)value)){
                            entry.setValue(null);
                        }
//                        String key = entry.getKey();
                        if(StringUtils.equals("null",(String)value)){
                            entry.setValue(null);
                        }
                    }
                }
            }
    }
}
