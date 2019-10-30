package com.newlife.s4.member.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.config.exception.CommonJsonException;
import com.newlife.s4.config.shiro.RedisDb;
import com.newlife.s4.member.manager.TokenManager;
import com.newlife.s4.member.model.TokenModel;
import com.newlife.s4.util.TokenEncryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 描述:基于redis的 token 管理
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 10:09
 */
@Component
public class RedisTokenManager implements TokenManager {

    private final long EXPIRE_MILLISECONDS = 7 * 24 * 60 * 60 ;//过期时间为2个小时

    @Override
    public TokenModel createToken(long userId) {
        return null;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        Long memberId = model.getMemberID();
        if (StringUtils.isNotEmpty(RedisDb.getString(Constants.REDIS_TOKEN_PREFIX + memberId))) {
            RedisDb.pexpire(Constants.REDIS_TOKEN_PREFIX + memberId, EXPIRE_MILLISECONDS);
            return true;
        }
        return false;
    }

    /**
     * 加密的形式 member||token
     *
     * @param authentication 加密后的字符串
     * @return
     */
    @Override
    public TokenModel getToken(String authentication) {
        if (StringUtils.isEmpty(authentication))
            return new TokenModel();
        TokenModel member = null;
        try {
            authentication = TokenEncryUtil.decryp(authentication,0);
            String tokenModel = RedisDb.getString(Constants.REDIS_TOKEN_PREFIX + authentication);
            member = JSON.parseObject(tokenModel,TokenModel.class);
            if (member == null)
                throw new CommonJsonException(ErrorEnum.E_20011);//加密的字符串不正确
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            throw new CommonJsonException(ErrorEnum.E_400);//加密的字符串不正确
        }
        return member;
    }

    @Override
    public void deleteToken(long memberId) {
        RedisDb.delString(Constants.CURRENT_MEMBER_ID + memberId);
    }

}
