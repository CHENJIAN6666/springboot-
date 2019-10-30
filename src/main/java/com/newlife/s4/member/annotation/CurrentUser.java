package com.newlife.s4.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述:在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的member对象
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 9:40
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
