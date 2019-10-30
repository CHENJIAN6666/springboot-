package com.newlife.s4.util;


import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述: 订单编号生成工具
 *
 * @author withqianqian@163.com
 * @create 2019-09-06 15:47
 */
public class OrderNumGenerator {
    public enum TYPE {
        //充值
        CZ,
        //消费
        XF;
    }

    //修改 SimpleDateFormat 获取方式，防止出现时间出现不同问题
    private static ReentrantLock lock = new ReentrantLock();
    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormat = new ThreadLocal<>();


    /**
     * 时间格式化 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    private static String format(Date date) {
        try {
            lock.lock();
            // UTC时间格式
            if (threadLocalDateFormat.get() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                threadLocalDateFormat.set(dateFormat);
            }
        } finally {
            lock.unlock();
        }
        return threadLocalDateFormat.get().format(date);
    }

    public static String numGenerator(TYPE t) {
        String orderNum = "";
        //充值
        if (t.equals(TYPE.CZ)) {
            orderNum = "CZ" + format(new Date());
            //消费
        } else {
            orderNum = "XF" + format(new Date());
        }
        return orderNum + Double.toString(Math.random()).substring(2, 8);
    }


}
