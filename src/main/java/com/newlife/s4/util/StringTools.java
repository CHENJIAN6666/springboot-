package com.newlife.s4.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: newlife
 * @date: 2017/10/24 10:16
 */
public class StringTools {

    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }
    
    public static boolean isNumeric(String str){
    	if(isNullOrEmpty(str))
    	return false;
    	
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
