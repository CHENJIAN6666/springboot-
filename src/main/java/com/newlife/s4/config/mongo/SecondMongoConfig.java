package com.newlife.s4.config.mongo;

import com.mongodb.Mongo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2019-03-14 19:31
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.second")
public class SecondMongoConfig extends AbstractMongoConfig{
    @Bean("secondMongoTemplate")
    @Override
    public  MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
