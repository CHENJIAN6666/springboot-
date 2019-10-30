package com.newlife.s4.member.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.config.exception.CommonJsonException;
import com.newlife.s4.member.annotation.Authorization;
import com.newlife.s4.member.manager.TokenManager;
import com.newlife.s4.member.model.TokenModel;
import com.newlife.s4.util.TokenEncryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 描述: 登陆拦截器
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 9:47
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //只校验方法带注解Authorization的请求路径
        if (method.getAnnotation(Authorization.class) != null) {
            //从请求header中得到加密后的token
            String authorization = request.getHeader(Constants.AUTHORIZATION);
            TokenModel tokenModel = manager.getToken(authorization);
            if (tokenModel != null && manager.checkToken(tokenModel)) {
                //redis中已存在登陆用户直接返回
                request.setAttribute(Constants.CURRENT_MEMBER_ID, tokenModel.getMemberID());
//                request.setAttribute(Constants.MEMBER_CUSTOMERS, tokenModel.getCustomers());
                request.setAttribute(Constants.AUTHORIZATION,tokenModel.getToken());
                request.setAttribute("member_name",tokenModel.getMemberName());
                request.setAttribute("mobile",tokenModel.getMobile());


            } else {
                //token不存在redis中 返回20011
                throw new CommonJsonException(ErrorEnum.E_20011);
            }

        }
        return true;

    }

}
