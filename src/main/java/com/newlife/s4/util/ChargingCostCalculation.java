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
 * 描述: 充电计算费用工具
 *
 * @author withqianqian@163.com
 * @create 2019-09-09 17:07
 */
public class ChargingCostCalculation {
    //修改 SimpleDateFormat 获取方式，防止出现时间异常问题
    private static ReentrantLock lock = new ReentrantLock();
    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormat = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> threadLocalDateFullFormat = new ThreadLocal<>();
    //时间临界点
    private static final String WEE_HOURS0 = "23:59:59";
    private static final String WEE_HOURS1 = "00:00:00";


    /**
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat() {
        try {
            lock.lock();
            // UTC时间格式
            if (threadLocalDateFormat.get() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                threadLocalDateFormat.set(dateFormat);
            }
        } finally {
            lock.unlock();
        }
        return threadLocalDateFormat.get();
    }
    private static SimpleDateFormat getSimpleDateFullFormat() {
        try {
            lock.lock();
            // UTC时间格式
            if (threadLocalDateFullFormat.get() == null) {
                SimpleDateFormat dateFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFullFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                threadLocalDateFullFormat.set(dateFullFormat);
            }
        } finally {
            lock.unlock();
        }
        return threadLocalDateFullFormat.get();
    }

    /**
     * 判断时间是否跨日
     *
     * @param start   开始时间
     * @param useTime 用时
     * @throws ParseException
     */
    private static String[][] crossDay(String start, Long useTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        if(start.indexOf("-")>-1){
            Date full = getSimpleDateFullFormat().parse(start);
            start = simpleDateFormat.format(full);
        }
        long s = simpleDateFormat.parse(start).getTime();
        long e = s + useTime * 1000;
        //格式化时间
        String fte = simpleDateFormat.format(e);
        return crossDay(start, fte);
    }

    /**
     * 判断时间是否跨日
     *
     * @param start   开始时间
     * @param endTime 结束时间
     * @return
     * @throws ParseException
     */
    private static String[][] crossDay(String start, String endTime) throws ParseException {
        String[][] arr;
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        if(start.indexOf("-")>-1){
            Date full = getSimpleDateFullFormat().parse(start);
            start = simpleDateFormat.format(full);
        }
        long s = simpleDateFormat.parse(start).getTime();
        long t = simpleDateFormat.parse(endTime).getTime();
        if (t <= s) {
            //拆分2个时间段 23:59-00:00 00:00-00:30
            arr = new String[][]{{start, WEE_HOURS0}, {WEE_HOURS1, endTime}};
        } else {
            arr = new String[][]{{start, endTime}};
        }
        return arr;
    }



    /**
     * 获取平均单价
     *
     * @param request  充电结束后上报的数据
     * @param peakList 电价梯度
     * @param plant    充电站电价
     * @return
     * @throws ParseException
     */
    public static BigDecimal getAverage(JSONObject request, List<JSONObject> peakList, JSONObject plant) throws ParseException {

        BigDecimal totalPrice = new BigDecimal(0);
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        Long useTime = request.getLong("useTime");
        //剩余时间
        String[][] arr = crossDay(request.getString("startTime"), useTime);
        for (String[] ss : arr) {
            for (JSONObject jsonObject : peakList) {
                if(!jsonObject.getInteger("type").equals(plant.getInteger("chargingPileType")))
                    continue;
                long s = Math.max(simpleDateFormat.parse(ss[0]).getTime(),
                        simpleDateFormat.parse(jsonObject.getString("startTime")+":00").getTime());
                String eTime = jsonObject.getString("endTime")+":00";
                if (WEE_HOURS1.equals(eTime))
                    eTime = WEE_HOURS0;

                long e = Math.min(simpleDateFormat.parse(ss[1]).getTime(), simpleDateFormat.parse(eTime).getTime());
                if (s <= e) {
                    BigDecimal total = jsonObject
                            .getBigDecimal("singlePrice")
                            .add(jsonObject.getBigDecimal("servicePrice"), new MathContext(3, RoundingMode.HALF_UP));
                    //梯度用时
                    long t = (e - s) / 1000;
                    //比值
                    BigDecimal subsection = new BigDecimal(t).divide(new BigDecimal(request.getLong("useTime")), 5, BigDecimal.ROUND_HALF_UP);
                    BigDecimal multiply = subsection.multiply(total);

                    totalPrice = totalPrice.add(multiply);
                    useTime = useTime - t;
                }
            }
        }
        //使用类型 0:交流 1:直流
        if(useTime>0){
            BigDecimal total;
            if (plant.getIntValue("chargingPileType") == 0) {
                total = plant
                        .getBigDecimal("exchangePrice")
                        .add(plant.getBigDecimal("exchangeServicePrice"), new MathContext(3, RoundingMode.HALF_UP));
            } else {
                total = plant
                        .getBigDecimal("directCurrentPrice")
                        .add(plant.getBigDecimal("directCurrentServicePrice"), new MathContext(3, RoundingMode.HALF_UP));
            }

            BigDecimal subsection = new BigDecimal(useTime).divide(new BigDecimal(request.getLong("useTime")), 5, BigDecimal.ROUND_HALF_UP);
            BigDecimal multiply = subsection.multiply(total);
            totalPrice = totalPrice.add(multiply);
        }
        return totalPrice;

    }

//    public static void main(String[] args) throws ParseException {
//        List<JSONObject> peakList = new ArrayList<>();
//        JSONObject j1 = new JSONObject();
//        j1.put("startTime", "23:30:00");
//        j1.put("endTime", "00:00:00");
//        j1.put("singlePrice", new BigDecimal(1.0));
//        j1.put("servicePrice", new BigDecimal(0.3));
//        peakList.add(j1);
//
//        JSONObject j2 = new JSONObject();
//        j2.put("startTime", "00:30:00");
//        j2.put("endTime", "01:00:00");
//        j2.put("singlePrice", new BigDecimal(0.8));
//        j2.put("servicePrice", new BigDecimal(0.3));
//        peakList.add(j2);
//
//        JSONObject j3 = new JSONObject();
//        j3.put("startTime", "04:30:00");
//        j3.put("endTime", "05:00:00");
//        j3.put("singlePrice", new BigDecimal(0.9));
//        j3.put("servicePrice", new BigDecimal(0.3));
//        peakList.add(j3);
//
//
//        JSONObject request = new JSONObject();
//        request.put("startTime", "23:30:00");
//        request.put("useTime", 5400L);
//
//        JSONObject plant = new JSONObject();
//        plant.put("chargingPileType", 1);
//        plant.put("exchangePrice", new BigDecimal(1.6));
//        plant.put("exchangeServicePrice",  new BigDecimal(0.1));
//
//        System.out.println(getAverage(request, peakList, plant));
//    }

}
