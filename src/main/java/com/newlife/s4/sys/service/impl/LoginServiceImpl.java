package com.newlife.s4.sys.service.impl;

import com.newlife.s4.common.constants.Constants;

import com.newlife.s4.sys.service.VerificationService;


import com.newlife.s4.util.TokenEncryUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.sys.dao.LoginDao;
import com.newlife.s4.sys.dao.OrganizationsDao;
import com.newlife.s4.sys.dao.UserDao;
import com.newlife.s4.sys.service.LoginService;
import com.newlife.s4.sys.service.PermissionService;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.MD5Util;


import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: newlife
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */
@Service
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserDao userDao;   
    @Autowired
    private OrganizationsDao organizationsDao;
    @Autowired
    private VerificationService verificationService;

    private Pattern mobileReg = Pattern.compile("^\\d{11}");
    /**
     * 登录表单提交
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject returnObj=new JSONObject();
        JSONObject returnData = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Util.MD5(password));
        try {
            currentUser.login(token);
            
            //returnData.put("token", JWTUtil.sign(username, password));
            Serializable sessionID = currentUser.getSession().getId();
            JSONObject user = (JSONObject) currentUser.getSession().getAttribute(Constants.SESSION_USER_INFO);
            Long userID = user.getLong("userId");
            returnData.put("token", TokenEncryUtil.encrySeesion(userID, (String) sessionID));
            
            returnData.put("result", "success");
            returnObj=CommonUtil.successJson(returnData);
        } catch (AuthenticationException e) {
//            returnData.put("result", "fail");
            returnObj=CommonUtil.errorJson(ErrorEnum.E_10010);
        }
        return returnObj;
    }

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public JSONObject getUser(String username, String password) {
        return loginDao.getUser(username, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        String username = Sessions.getCurrentUserName();

        JSONObject userPermission = permissionService.getUserPermission(username);
        Sessions.setSessionUserPermission(userPermission);
        userPermission.put("userOrgId", Sessions.getOrgID());
        JSONObject json = Sessions.getCurrentUser();
        userPermission.put("avatar",  json.get("avatar"));
        JSONObject returnData = new JSONObject();
        returnData.put("userPermission", userPermission);
       

        return CommonUtil.successJson(returnData);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }




}
