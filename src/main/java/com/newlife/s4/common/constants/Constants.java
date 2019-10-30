package com.newlife.s4.common.constants;

/**
 * @author: newlife
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 * @date: 2017/10/24 10:15
 */
public class Constants {

//    public static final String SUCCESS_CODE = "100";
//    public static final String SUCCESS_MSG = "请求成功";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    public static final String SESSION_USER_PERMISSION = "userPermission";
    
    
    /**
     * 上传文件
     */
    public static final String UPLOAD_WEB_FILE="file";
    public static final String APIPATH="api";
    
    /**
     * 平台管理员角色ID
     */
    public static final String ROOT_ROLE_ID="1";
    /**
     * 店铺管理员角色ID
     */
    public static final String STORE_ADMIN_ROLE_ID="4";
    /**
     * 售后角色ID
     */
    public static final String AFTER_SALES_ROLE_ID="8";

    /**
     * 财务
     */
    public static final String Financial = "9";

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_MEMBER_ID = "member_id:";

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String USER_ID = "user_id:";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "auths";

    /**
     * 存放在redis (string)中key前缀
     */
    public static final String REDIS_TOKEN_PREFIX = "member_id:";
    /**
     * 充电基础设施运营商:Redis，token前缀
     */
    public static final String OPERATOR_TOKEN_PREFIX = "operator_token:";
    /**
     * token加密分割符号: member##token
     */
    public static final String ENCRYPT_SYMBOL = "##";

    public static final String LOGIN_EXPIRE = "login_expire";

    public static final String MEMBER_CUSTOMERS = "member_customers";
}
