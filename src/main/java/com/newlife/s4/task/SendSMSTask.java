package com.newlife.s4.task;


import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newlife.s4.sys.service.SmsSendService;
import com.newlife.s4.util.RandomUtil;
import com.newlife.s4.webservice.sms.SmsException;
import com.newlife.s4.webservice.sms.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 描述: //从redis获取待发送的短信
 * 从阻塞队列中获取待发送的短信
 *
 * @author withqianqian@163.com
 * @create 2018-05-30 16:43
 */
@Component("sendSMSTask")
public class SendSMSTask implements PushService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("SMSTask-%d").build();
    private ExecutorService pool = Executors.newSingleThreadExecutor(threadFactory);

    @Autowired
    private Environment environment;

    //待发送消息的队列
    private volatile BlockingQueue<JSONObject> msgQueue = new LinkedBlockingQueue<JSONObject>();

    //待更新的消息容器
//    private volatile ConcurrentHashMap<Long, Object> container = new ConcurrentHashMap<Long, Object>(100);

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsSendService smsSendService;

//    @PostConstruct
    private void startSendSMS() {
        String nwe = environment.getProperty("newlife-non-web-environment");
        if(nwe == null || nwe.equals("false")) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.debug("发短信线程启动 .......");
                    while (Boolean.TRUE) {
                        JSONObject msg = null;
                        Long smsSendID = 0l;
                        try {
                            msg = msgQueue.poll(10, TimeUnit.MINUTES);
                            if (msg == null)
                                continue;
                            smsSendID = msg.getLong("smsSendID");
                            String content = msg.getString("smsContent");
                            String phone = msg.getString("mobile");
                            String requestId = RandomUtil.genarateId("SMS_"); //请求唯一ID
                            if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(phone)) {
                                String smsId = smsService.sendSms(phone, null, content, requestId, "");
                                if (StringUtils.isNotEmpty(smsId)) {
                                    msg.clear();
                                    msg.put("smsSendID", smsSendID);
                                    msg.put("sendState", 1);
                                    msg.put("sendTime", new Date());
                                    smsSendService.updateSmsSend(msg);
                                }
                            }

                        } catch (InterruptedException e) {
//                        e.printStackTrace();
                            Thread.currentThread().interrupt();
                        } catch (SmsException e) {
                            logger.error("发送消息失败 error:{}", e.getMessage());
                            if (msg != null) {
                                msg.put("smsSendID", smsSendID);
                                msg.put("sendState", 2);
                                msg.put("sendTime", new Date());
                                smsSendService.updateSmsSend(msg);
                            }
                        }
                    }
                }
            });
        }else {
            pool = null;
        }
    }

    /**
     * 添加短信
     *
     * @param jsonObject {
     *                   用户ID(发送人)userID:1,
     *                   会员ID(发送人)memberID:22,
     *                   手机号码 mobile:188,
     *                   短信内容 smsContent:"XXXXXXXXXX",
     *                   优先级 sendPriority:1
     *                   }
     */
    public void pushMsg(JSONObject jsonObject) {
        try {
            jsonObject.put("createTime", "now()");
            if (!jsonObject.containsKey("sendPriority")) {
                jsonObject.put("sendPriority", 1);
            }
            jsonObject.put("sendState", 0);
            smsSendService.addSmsSend(jsonObject);
            if (jsonObject.getInteger("sendPriority") == 1)
                msgQueue.put(jsonObject);

        } catch (InterruptedException e) {
            //e.printStackTrace();
            logger.error("添加到队列失败");
        }
    }

    @PreDestroy
    private void shutdonw() {
        pool.shutdownNow();
    }

}
