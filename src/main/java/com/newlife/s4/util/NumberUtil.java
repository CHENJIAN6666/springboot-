package com.newlife.s4.util;

/**
 * @author: newlife
 * @date: 2017/10/24 10:16
 */
public class NumberUtil {

	//转万
    public static String toTenThousand1(Double n) {
    	if(n==null)
    		return "0.0";
    	else
    		{
    			return String.format("%.1f", n/10000);
    		}
    	
    }

   
}
