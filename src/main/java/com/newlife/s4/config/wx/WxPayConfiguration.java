package com.newlife.s4.config.wx;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:微信支付相关配置
 *
 * @author withqianqian@163.com
 * @create 2018-07-11 15:34
 */
@Configuration
public class WxPayConfiguration {

    @Value("${charging.ycNotifyUrl}")
    private String ycNotifyUrl;

    @Value("${charging.appId}")
    private String chargingAppId;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.mchId}")
    private String mchId;

    @Value("${wx.mchKey}")
    private String mchKey;

    @Value("${wx.subAppId}")
    private String subAppId;

    @Value("${wx.subMchId}")
    private String subMchId;

    @Value("${wx.keyPath}")
    private String keyPath;

    @Value("${wx.notifyUrl}")
    private String notifyUrl;

    @Value("${rent.weNotifyUrl}")
    private String weNotifyUrl;

    @Value("${rent.weRentalNotifyUrl}")
    private String weRentalNotifyUrl;

    @Value("${rent.weMergeRentalNotifyUrl}")
    private String weMergeRentalNotifyUrl;

    @Value("${rent.appID}")
    private String weAppId;

    @Bean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = getWxPayConfig();
        payConfig.setNotifyUrl(notifyUrl);
        return payConfig;
    }

    @Bean
    public WxPayService payService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(payConfig());
        return payService;
    }
    //===============微信小程序支付==============
    @Bean("wePayConfig")
    public WxPayConfig wePayConfig() {
        WxPayConfig payConfig = getWxPayConfig();
        //设置微信小程序支付回调接口
        payConfig.setNotifyUrl(weNotifyUrl);
        payConfig.setAppId(weAppId);
        return payConfig;
    }


    //===============微信小程序支付==============
    @Bean("weRentalPayConfig")
    public WxPayConfig weRentalPayConfig() {
        WxPayConfig payConfig = getWxPayConfig();
        //设置微信小程序支付回调接口
        payConfig.setNotifyUrl(weRentalNotifyUrl);
        payConfig.setAppId(weAppId);
        return payConfig;
    }


    @Bean("wePayService")
    public WxPayService wePayService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(wePayConfig());
        return payService;
    }
    //=============end==============

    //===============微信小程序租金合并支付==============
    @Bean("weMergeRentalPayConfig")
    public WxPayConfig weMergeRentalPayConfig() {
        WxPayConfig payConfig = getWxPayConfig();
        //设置微信小程序支付回调接口
        payConfig.setNotifyUrl(weMergeRentalNotifyUrl);
        payConfig.setAppId(weAppId);
        return payConfig;
    }

    @Bean("weMergePayService")
    public WxPayService weMergePayService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(weMergeRentalPayConfig());
        return payService;
    }
    //=============end==============
    /**
     * 获取WxPayConfig
     *
     * @return
     */
    private WxPayConfig getWxPayConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(appId);
        payConfig.setMchId(mchId);
        payConfig.setMchKey(mchKey);
        payConfig.setSubAppId(subAppId);
        payConfig.setSubMchId(subMchId);
        payConfig.setKeyPath(keyPath);
        return payConfig;
    }

    //===============云充小程序支付==============
    @Bean("ycPayConfig")
    public WxPayConfig ycPayConfig() {
        WxPayConfig payConfig = getWxPayConfig();
        //设置微信小程序支付回调接口
        payConfig.setAppId(chargingAppId);
        payConfig.setNotifyUrl(ycNotifyUrl);
        return payConfig;
    }

    @Bean("ycPayService")
    public WxPayService ycPayService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(ycPayConfig());
        return payService;
    }




}
