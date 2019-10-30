package com.newlife.s4.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2019-09-05 17:06
 */
public class CustomWrapper extends MapWrapper {
    public CustomWrapper(MetaObject metaObject, JSONObject map) {
        super(metaObject, map);
    }


    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if(useCamelCaseMapping&&name.indexOf("_")>-1){
            //CaseFormat是引用的 guava库,里面有转换驼峰的,免得自己重复造轮子,pom添加
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,name);
        }
        return name;
    }
}
