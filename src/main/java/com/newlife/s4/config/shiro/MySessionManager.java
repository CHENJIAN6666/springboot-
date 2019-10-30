package com.newlife.s4.config.shiro;

import com.newlife.s4.util.TokenEncryUtil;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/12/11.
 * 自定义sessionId获取
 */
public class MySessionManager extends DefaultWebSessionManager {
  
    private static final String AUTHORIZATION = "auths_api";
  
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
  
    public MySessionManager() {
        super();
    }
  
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        String id = null;
        if(!StringUtils.isEmpty(token)){
	        try {
	            id = TokenEncryUtil.decryp(token,1);
	        } catch (UnsupportedEncodingException e) {
	            //e.printStackTrace();
	        }
	        if (!StringUtils.isEmpty(id)) {
	            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
	                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
	            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
	            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
	            return id;
	        } 
        }else {
			//否则按默认规则从cookie取sessionId
        	//这里必须处理，否则后台刷新页面则会退出到登录界面
			return super.getSessionId(request, response);
		}

//        if(StringUtils.isEmpty(id)){
//        	id="bnVsbCMjZDdmNTc2MjctOGI1MC00YzUzLWFjZTEtYWMwYzVlOTk2YzU0IyN1c2VyX2lkOg==";
//        }

        //如果请求头中有 Authorization 则其值为sessionId
//        if (!StringUtils.isEmpty(id)) {
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
//                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//            return id;
//        } else {
//            //否则按默认规则从cookie取sessionId
//            return super.getSessionId(request, response);
//        }
        return id;
    }
}