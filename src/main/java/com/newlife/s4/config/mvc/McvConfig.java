package com.newlife.s4.config.mvc;

import com.newlife.s4.member.interceptor.AuthorizationInterceptor;
import com.newlife.s4.member.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 描述:配置类，增加自定义拦截器和解析器 控制用户登陆
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 9:44
 */
@Configuration
public class McvConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver userResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //参数级别拦截
        argumentResolvers.add(userResolver);
    }
}
