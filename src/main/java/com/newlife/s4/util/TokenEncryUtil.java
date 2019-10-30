package com.newlife.s4.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.member.model.TokenModel;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2018-05-10 18:15
 */
public class TokenEncryUtil {

    /**
     * 加密后进行64编码
     *
     * @param memberId
     * @param token
     * @param timestamps
     * @return
     */
    public static TokenModel encry(Long memberId, String token, long timestamps, String type) {
        String md5Token = MD5Util.MD5(token + timestamps);
        String unMD5 = memberId + Constants.ENCRYPT_SYMBOL + md5Token + Constants.ENCRYPT_SYMBOL + type;
        TokenModel tokenModel = null;
        String outStr = "";
        try {
            outStr = Base64.getEncoder().encodeToString(unMD5.getBytes("utf-8"));
            tokenModel = new TokenModel(memberId, outStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tokenModel;
    }

    /**
     * 员工端 session 加密 方便获取 员工ID
     *
     * @param userID
     * @param session
     * @return
     */
    public static String encrySeesion(Long userID, String session) {
        String unEncry = userID + Constants.ENCRYPT_SYMBOL + session + Constants.ENCRYPT_SYMBOL + Constants.USER_ID;
        String outStr = "";
        try {
            outStr = Base64.getEncoder().encodeToString(unEncry.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return outStr;
    }

    /**
     * 进行64编码后解密
     */
    public static String decryp(String inStr,int index) throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(inStr))
            return "";
        String decryp64 = new String(Base64.getDecoder().decode(inStr), "utf-8");
        String memberID = decryp64.split(Constants.ENCRYPT_SYMBOL)[index];
        return memberID;

    }


}
