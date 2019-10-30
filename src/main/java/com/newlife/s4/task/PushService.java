package com.newlife.s4.task;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述:推送接口
 *
 * @author withqianqian@163.com
 * @create 2018-06-01 9:37
 */
public interface PushService {
     void pushMsg(JSONObject jsonObject);
}
