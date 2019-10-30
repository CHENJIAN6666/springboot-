package com.newlife.s4.common.converter;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 枚举基类
 * @author hxl
 */
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface IBaseEnum extends Serializable {
    static <E extends Enum<E> & IBaseEnum> E valueOf (Integer enumCode, Class<E> clazz) {
        E[] enums = clazz.getEnumConstants ();
        for (E e : enums) {

            if (e.getId ().equals (enumCode)) {
                return e;
            }

        }
        return null;
    }

    Integer getId ();
    
    String getName ();
}
