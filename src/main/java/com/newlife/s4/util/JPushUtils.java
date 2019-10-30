package com.newlife.s4.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

@Component
//@ConfigurationProperties(prefix="jpush.config")
public class JPushUtils {

    public enum  PushType {
        appointPurchase("1","购车询价"),
        appointDrive("2","试驾预约"),
        deposit("3","客户下订"),
        offlineNews("4","客户对话"),
        clue("5","您有一条新的【扫码线索】"),

        //销售单确认受理
        saleOrderDrivingFollow("3","销售单确认受理"),
        //完善销售单 updateCarSalesOrder
        saleOrderComplete("3","完善销售单"),
        //订单生效审核
        saleOrderEffectiveAudit("3","订单生效审核"),
        //打印合同
        saleOrderPrint("3","打印合同"),
        //结算审核
        saleOrderSettlementAudit("3","结算审核"),
//        //交车申请
//        saleOrderPreDeliver("3","交车申请"),
        //销售经理、总经理 交车审核
        saleOrderDeliverAudit("3","交车申请"),
        //交车
        saleOrderDeliver("3","交车");

        private String type;
        private String desc;

        PushType(String _type,String _desc){
            this.type = _type;
            this.desc = _desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    private Logger logger = LoggerFactory.getLogger(JPushUtils.class);

    @Value("${jpush.config.masterSecret}")
    private String masterSecret;

    @Value("${jpush.config.appKey}")
    private String appKey;

    @Autowired
    private Environment env;

    @Autowired
    private JPushClient jpushClient;

    @Bean
    public JPushClient newJPushClient(){
        return new JPushClient(masterSecret, appKey);
    }

    /**
     * 不是正式环境，alias会添加"dev_"的前缀
     * 推送PushType为3 extraData carSalesOrderID
     * @param type
     * @param extraData
     * @param userID
     * @return
     */
    public boolean send(PushType type,JSONObject extraData,String userID) {
        try {
            Map<String,String> extra = new HashMap<>();
            extra.put("extraNotificationID",type.getType());
//            extra.put("extra",extraData.toJSONString());
            for (Map.Entry<String,Object> entry: extraData.getInnerMap().entrySet()){
                extra.put(entry.getKey(),entry.getValue().toString());
            }
            String title = "客户在手"; //推送标题
            String msgContent = "您有一条新的【"+type.getDesc()+"】任务。";    //推送提示
            //离线对话，推送提示不一样
            if(StringUtils.equals(type.getType(),PushType.offlineNews.getType())){
                title = extraData.getString("memberName");
                String ctx = extraData.getString("content");
                if(ctx.length() > 10){
                    ctx = ctx.substring(0,10) + "...";
                }
                msgContent = ctx;
                extra.remove("content");
            }
            //扫描线索
            if(StringUtils.equals(type.getType(),PushType.clue.getType())){
                String memberName = extra.get("memberName");
                title = type.getDesc();
                msgContent = "微信客户名称：" + memberName;
                extra.remove("memberName");
            }
            String alias = "newLifeID_"+userID;
            //不是正式环境，添加"dev_"的前缀
            if(!StringUtils.equalsIgnoreCase("prod",env.getProperty("spring.profiles.active"))){
                alias = "dev_" + alias;
            }
            PushPayload payload  = buildPushObjectAlert(title,msgContent,extra,alias);
            PushResult result = jpushClient.sendPush(payload);
            return result.isResultOK();
        }catch (Exception e){
            logger.warn("极光推送[用户：{}]失败：{}",userID,e.getMessage());
            return false;
        }
    }

    private PushPayload buildPushObjectAlert(String title,String msgContent,Map<String,String> extra, String alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setNotification(Notification.android(msgContent, title, extra))
//                .setMessage(Message.content(msgContent))
                .build();
    }
}
