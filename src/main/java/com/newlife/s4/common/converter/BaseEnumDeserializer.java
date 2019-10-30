package com.newlife.s4.common.converter;

import java.io.IOException;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/** 
 * @Date: 2018/9/28 9:58
 * @Author: hxl
 * @Description:JSON转换
 */
public class BaseEnumDeserializer extends JsonDeserializer<IBaseEnum> {

    @Override
    @SuppressWarnings("unchecked")
    public IBaseEnum deserialize (JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec ().readTree (jp);
        String currentName = jp.getCurrentName();
        Object currentValue = jp.getCurrentValue ();
        @SuppressWarnings("rawtypes")
        Class findPropertyType = BeanUtils.findPropertyType (currentName, currentValue.getClass ());
        JsonFormat annotation = (JsonFormat) findPropertyType.getAnnotation (JsonFormat.class);
        IBaseEnum valueOf;
        if (annotation == null || annotation.shape () != JsonFormat.Shape.OBJECT) {

            valueOf = (IBaseEnum) IBaseEnum.valueOf (node.asInt (), findPropertyType);
        } else {

            valueOf = (IBaseEnum) IBaseEnum.valueOf (node.get ("id").asInt (), findPropertyType);
        }
        return valueOf;
    }
}
