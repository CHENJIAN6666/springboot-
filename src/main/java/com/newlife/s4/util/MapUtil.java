package com.newlife.s4.util;

import com.alibaba.fastjson.JSONObject;

public class MapUtil {

    public static JSONObject getDuration(double fromLat,double fromLng, double toLat, double toLng){
        StringBuilder sb =  new StringBuilder();
        sb.append("https://apis.map.qq.com/ws/distance/v1/?mode=driving&from=");
        sb.append(fromLat);
        sb.append(",");
        sb.append(fromLng);
        sb.append("&to=");
        sb.append(toLat);
        sb.append(",");
        sb.append(toLng);
        sb.append("&key=FMABZ-RNNWX-B7R4Q-ZBNCH-GHJ2T-SKBTO");
        System.out.println(sb.toString());
        String s =  HttpUtil.httpRequestString(sb.toString(),"GET",null);
        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(s);
        return jsonObject;
    }

}
