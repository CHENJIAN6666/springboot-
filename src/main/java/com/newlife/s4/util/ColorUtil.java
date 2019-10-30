package com.newlife.s4.util;

import java.awt.*;

public class ColorUtil {

    public static Color toColorFromString(String colorStr){
        Color color =  new Color(Integer.parseInt(trimPrefix(colorStr), 16)) ;
        return color;
    }



    public static  String trimPrefix(String str){
        if(str.contains("#")){
            return str.substring(str.indexOf("#")+1,str.length());
        }
        return str;
    }

    public static  void main(String[] args){
        String test = "#abcd";
        System.out.println(trimPrefix(test));
    }
}
