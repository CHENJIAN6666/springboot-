package com.newlife.s4.task;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.config.shiro.RedisDb;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 描述:微信公众推送
 *
 * @author withqianqian@163.com
 * @create 2018-06-01 9:42
 */
@Component("wechatPush")
public class WechatPush implements PushService {


    /**
     *  推送格式
     *
     * @param jsonObject
     *         {
     *            type:1, //对应的微信消息模版
     *            time:now(),
     *            url:"",
     *            memberID:22,
     *            body:{
     *               //微信消息模版 对应的实体格式
     *            }
     *
     *         }
     *
     */
    @Override
    public void pushMsg(JSONObject jsonObject) {
        Calendar calendar = Calendar.getInstance();
        jsonObject.put("createTime", calendar.getTime());
        RedisDb.pushWehcatMsg(jsonObject.toJSONString());
    }

}
