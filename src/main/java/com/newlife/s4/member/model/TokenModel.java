package com.newlife.s4.member.model;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.config.shiro.RedisDb;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 10:07
 */
public class TokenModel {
    //会员id
    private Long memberID;

    //微信登陆产生的token
    private String token;


    private String memberName;

    private String mobile;

    public final static int MEMBER_EXPIRE_TIME = 7 * 24 * 60 * 60 ;

    public TokenModel() {
    }

    public TokenModel(Long memberID) {
        this.memberID = memberID;
    }

    public TokenModel(Long memberID, String token) {
        this.memberID = memberID;
        this.token = token;
    }

    public TokenModel(Long memberID, String token, String memberName, String mobile) {
        this.memberID = memberID;
        this.token = token;
        this.memberName = memberName;
        this.mobile = mobile;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void updateMobileAndName(String mobile, String memberName) {
        this.mobile = mobile;
        this.memberName = memberName;
        RedisDb.setString(Constants.REDIS_TOKEN_PREFIX + this.memberID, JSONObject.toJSONString(this), MEMBER_EXPIRE_TIME);
    }
}
