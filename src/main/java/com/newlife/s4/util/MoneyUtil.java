package com.newlife.s4.util;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;

public class MoneyUtil {
    /** 大写数字 */
    private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆","柒", "捌", "玖" };
    /** 整数部分的单位 */
    private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
    /** 小数部分的单位 */
    private static final String[] DUNIT = { "角", "分", "厘" };

    /**
     * 得到大写金额。
     */
    public static String toChinese(final double number){
        return toChinese(String.valueOf(number));
    }
    public static String toChinese(final String strNumber) {
        String str = strNumber.replaceAll(",", "");// 去掉","
        String integerStr;// 整数部分数字
        String decimalStr;// 小数部分数字

        // 初始化：分离整数部分和小数部分
        if (str.indexOf(".") > 0) {
            integerStr = str.substring(0, str.indexOf("."));
            decimalStr = str.substring(str.indexOf(".") + 1);
        } else if (str.indexOf(".") == 0) {
            integerStr = "";
            decimalStr = str.substring(1);
        } else {
            integerStr = str;
            decimalStr = "";
        }
        // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
        if (StringUtils.isNotBlank(integerStr)) {
            integerStr = Long.toString(Long.parseLong(integerStr));
            if (integerStr.equals("0")) {
                integerStr = "";
            }
        }
        // overflow超出处理能力，直接返回
        if (integerStr.length() > IUNIT.length) {
            System.out.println(str + ":超出处理能力");
            return str;
        }

        int[] integers = toArray(integerStr);// 整数部分数字
//        boolean isMust5 = isMust5(integerStr);// 设置万单位
        int[] decimals = toArray(decimalStr);// 小数部分数字
//        return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
        return toChinese(integers,decimals);
    }

    private static int[] toArray(final String s) {
        if(StringUtils.isBlank(s)){
            return new int[0];
        }
        String[] ss= s.split("");
        return Arrays.asList(ss).stream().mapToInt(Integer::parseInt).toArray();
    }

    private static String toChinese(int[] is,int[] ds){
        StringBuilder sb = new StringBuilder();
        if(is.length > 0){
            for (int i =0; i < is.length; i++){
                int b = is[is.length-1-i];
                sb.insert(0,IUNIT[i]);
                sb.insert(0,NUMBERS[b]);
            }
        }
        if(ds.length > 0){
            for (int i=0;i < ds.length || i >= 3; i++){
                int d = ds[i];
                sb.append(NUMBERS[d]);
                sb.append(DUNIT[i]);
            }
        }
        String result = "";
        String sbStr = sb.toString();
        for (int i = 0;i < sbStr.length()-1;i += 2){
            String tmp = null;
            if(sbStr.substring(i,i+1).equals("零")){
                tmp = sbStr.substring(i,i+2);
                if(tmp.equals("零元")) {
                    tmp = "元";
                }else {
                    continue;
                }
            }else {
                tmp = sbStr.substring(i,i+2);
            }

            result += tmp;
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(MoneyUtil.toChinese("123456.45"));
//        System.out.println(MoneyUtil.toChinese("123456"));
//        System.out.println(MoneyUtil.toChinese("123456."));
//        System.out.println(MoneyUtil.toChinese("123456.00"));
//        System.out.println(MoneyUtil.toChinese("123456.0000"));
//        System.out.println(MoneyUtil.toChinese("110000"));
//        System.out.println(MoneyUtil.toChinese("110000.11"));
    }

}
