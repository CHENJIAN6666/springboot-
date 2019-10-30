package com.newlife.s4;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: newlife
 * @description: SpringBoot启动类
 * @date: 2017/10/24 11:55
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages= {"com.newlife.s4.*.dao"})
@EnableScheduling
@EnableAsync
public class MyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MyApplication.class);
    }

    //文件上传绝对路径
    @Value("${jetty.path}")
    String jettyPath;
    @Bean
    public JettyEmbeddedServletContainerFactory jettyFactory() {
        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
        if (StringUtils.isNotEmpty(jettyPath))
            factory.setDocumentRoot(new File(jettyPath));
        return factory;
    }
}
