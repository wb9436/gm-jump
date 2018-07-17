package com.zhiyou.jump.manager.dto.app;

import java.util.Date;

import com.zhiyou.jump.configuration.util.dto.DTO;

public class ConfigDto extends DTO {
	private static final long serialVersionUID = -436739842975873152L;
	
	private Integer id;//
	private String description;// 描述',
	private String keywords;// 关键字',
	private String value;// 值',
	private Date createTime;// 创建时间',
	private Date modifiedTime;// 修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
