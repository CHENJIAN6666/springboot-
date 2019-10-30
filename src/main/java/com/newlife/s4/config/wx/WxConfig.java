package com.newlife.s4.config.wx;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: withqianqian@163.com
 * @description: 微信公众号主配置
 * @date: 2018/5/8
 */
@Configuration
public class WxConfig {
    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.token}")
    private String token;

    @Value("${wx.aesKey}")
    private String aesKey;

    @Value("${wx.redirectDomain}")
    private String redirectDomain;

    @Value("${wx.apiDomain}")
    private String apiDomain;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.appId);
        configStorage.setSecret(this.appSecret);
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    public String getAppId() {
        return appId;
    }

    public String getRedirectDomain() {
        return redirectDomain;
    }

    public String getApiDomain() {
        return apiDomain;
    }
}
