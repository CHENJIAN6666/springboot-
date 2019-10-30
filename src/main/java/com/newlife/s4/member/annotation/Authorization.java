package com.newlife.s4.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述:在Controller的方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回20011错误
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 9:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
