package com.zhiyou.jump.manager.dto.app;

import java.util.Date;

import com.zhiyou.jump.configuration.util.dto.DTO;

public class NoticeDto extends DTO {
	private static final long serialVersionUID = 3027631242521650464L;

	public static final int PUBLISHED_YES = 1;
	public static final int PUBLISHED_NO = 0;
	
	private Integer id;//
	private String title;// 标题
	private String content;// 内容
	private Integer status; //发布状态
	private Date createTime;//
	private Date modifiedTime;//
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
