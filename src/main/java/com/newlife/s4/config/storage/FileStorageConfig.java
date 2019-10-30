package com.newlife.s4.config.storage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class FileStorageConfig {
	
	@Value("${storageService.fileStorageAddress}")
	private String fileStorageAddress;

	/**
	 * 文件存储服务器地址
	 * @return
	 */
	public String getFileStorageAddress() {
		return fileStorageAddress;
	}

	/**
	 * 返回静态文件的相对路径
	 * @param url
	 * @return
	 */
	public String getFileStoragePath(String url){
		if(StringUtils.isNotBlank(url.substring(url.indexOf("/file/")))){
			return url.substring(url.indexOf("/file/"));
		}
		return url;
	}
}
