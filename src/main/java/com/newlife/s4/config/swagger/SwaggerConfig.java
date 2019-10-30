package com.newlife.s4.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

@ConditionalOnWebApplication
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${swagger.host}")
	private String host;
	
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.newlife.s4.mobile")) //这里改成你报controller报名即可
                .paths(PathSelectors.any())
                .build()
                .host(host);
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("坚信新生活.新能源汽车 RESTful APIs")
                .description("坚信新生活.新能源汽车 RESTFul API 文档")
                .version("1.0")
                .build();
    }
}