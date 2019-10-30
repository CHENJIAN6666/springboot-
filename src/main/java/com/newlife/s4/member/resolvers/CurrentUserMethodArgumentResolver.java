package com.newlife.s4.member.resolvers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.member.annotation.CurrentUser;
import com.newlife.s4.member.model.TokenModel;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;

/**
 * 描述:将含有CurrentUser注解的方法参数注入当前登录用户
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 9:47
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        Long memberId = (Long) nativeWebRequest.getAttribute(Constants.CURRENT_MEMBER_ID, RequestAttributes.SCOPE_REQUEST);
        if (memberId != null) {
            String token = (String) nativeWebRequest.getAttribute(Constants.AUTHORIZATION, RequestAttributes.SCOPE_REQUEST);
            String memberName = (String) nativeWebRequest.getAttribute("member_name", RequestAttributes.SCOPE_REQUEST);
            String mobile = (String) nativeWebRequest.getAttribute("mobile", RequestAttributes.SCOPE_REQUEST);
            return new TokenModel(memberId,token,memberName,mobile);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_MEMBER_ID);
    }

}
