package com.newlife.s4.util;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDate;

/**
 * 描述: 订金工具类
 *
 * @author withqianqian@163.com
 * @create 2018-09-07 18:21
 */
public class FeeUtil {
    /**
     * 9.10-9.20号，线上下订1元，线下抵500；
     * 9.21-9.25号，线上下订50元，线下抵500；
     * 9.26-9.30号，线上下订100元，线下抵500；
     * 10月1号之后，线上订金恢复为500元。
     *
     * @param fee
     * @return
     */
    public static Integer getFeeByActivity( Integer fee) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if (month == 9 || (month == 10 && day < 8)) {
            fee = 1 ;
        }else {
            fee = 500;
        }
        return fee;
    }
}
