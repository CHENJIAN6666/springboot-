package com.newlife.s4.common.constants;

/**
 * 描述: 微信模版枚举
 *
 * @author withqianqian@163.com
 * @create 2018-06-01 14:37
 */
public enum WechatTemplateEnum {

    BOOK_DRIVE(1),//试驾预约已确认通知
    QUOTE_REMINDER(2),//报价提醒
    FINISH_DRIVE(3),//试驾服务完成通知
    ORDER_SUCCESS(4);//下订成功通知


    public int type;
    WechatTemplateEnum(int type){
        this.type = type;
    }
}
