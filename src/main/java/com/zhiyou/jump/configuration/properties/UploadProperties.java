package com.zhiyou.jump.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zhiyou.upload")
public class UploadProperties {

	/**
	 * 上传地址
	 */
	private String url;
	
	/**
	 * 回显地址
	 */
	private String visit;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVisit() {
		return visit;
	}
	public void setVisit(String visit) {
		this.visit = visit;
	}
}
