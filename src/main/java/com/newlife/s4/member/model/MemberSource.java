package com.newlife.s4.member.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 描述:平台会员来源
 *
 * @author withqianqian@163.com
 * @create 2018-06-14 15:59
 */
public enum MemberSource {

    //关注微信公众
    ATTENTION(0),
    //到店扫码
    TO_SHOP(1),
    //关注销售二维码
    SHARE_QR(2),
    //文章二维码
    ARTICLE_QR(3),
    //活动二维码
    ACTIVE_QR(4),
    //店铺二维码
    SHOP_QR(5),
    //售后顾问二维码
    AFTER_CONSULTANT_QR(6),
    //平台会员二维码
    MEMBER_QR(7),;
    public int type;

    MemberSource(int type) {
        this.type = type;
    }

    /**
     * 根据类型名称获取枚举
     *
     * @param typeName
     * @return
     */
    public int getType(String typeName) {
        int t = 0;
        switch (typeName) {
            case "Article":
                t = ARTICLE_QR.type;
                break;
            case "Active":
                t = ACTIVE_QR.type;
                break;
            case "Shop":
                t = SHOP_QR.type;
                break;
            case "After_consultant":
                t = AFTER_CONSULTANT_QR.type;
                break;
            case "Member":
                t = MEMBER_QR.type;
                break;
            case "LookCar":
                t = SHOP_QR.type;
                break;
            default:
                break;
        }
        return t;
    }


}
