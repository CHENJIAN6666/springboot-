//package com.newlife.s4.config.mongo;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author hxl
// *
// */
//@Configuration
//@EnableMongoRepositories(basePackages= {"com.newlife.s4.yc.mongo"})
//public class MongoConfig {
//	// 注入配置实体
//	@Autowired
//	private MongoSettingsProperties mongoSettingsProperties;
//
//	@Bean
//	@ConfigurationProperties(prefix = "spring.data.mongodb")
//	MongoSettingsProperties mongoSettingsProperties() {
//		return new MongoSettingsProperties();
//	}
//
//	// 覆盖默认的MongoDbFactory
//	@Bean
//	MongoDbFactory mongoDbFactory() {
//		// 客户端配置（连接数、副本集群验证）
//		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//
//		if (mongoSettingsProperties.getReplicaSet() != null) {
//			builder.requiredReplicaSetName(mongoSettingsProperties.getReplicaSet());
//		}
//		MongoClientOptions mongoClientOptions = builder.build();
//
//		// MongoDB地址列表
//		List<ServerAddress> serverAddresses = new ArrayList<>();
//		Integer port = mongoSettingsProperties.getPort();
//
//		ServerAddress serverAddress = new ServerAddress(mongoSettingsProperties.getHost(), port);
//		serverAddresses.add(serverAddress);
//
//		System.out.println("serverAddresses:" + serverAddresses.toString());
//
//		// 连接认证
//		List<MongoCredential> mongoCredentialList = new ArrayList<>();
//		if (mongoSettingsProperties.getUsername() != null) {
//			mongoCredentialList.add(MongoCredential.createScramSha1Credential(mongoSettingsProperties.getUsername(),
//					mongoSettingsProperties.getAuthenticationDatabase() != null
//							? mongoSettingsProperties.getAuthenticationDatabase()
//							: mongoSettingsProperties.getDatabase(),
//					mongoSettingsProperties.getPassword().toCharArray()));
//		}
//		System.out.println("mongoCredentialList:" + mongoCredentialList.toString());
//		// 创建客户端和Factory
//		MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredentialList, mongoClientOptions);
//		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, mongoSettingsProperties.getDatabase());
//		return mongoDbFactory;
//	}
//}