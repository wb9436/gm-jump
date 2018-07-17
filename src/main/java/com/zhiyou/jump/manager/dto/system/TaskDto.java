package com.zhiyou.jump.manager.dto.system;

import java.util.Date;

import com.zhiyou.jump.configuration.util.dto.DTO;

public class TaskDto extends DTO {
	private static final long serialVersionUID = 463687519445558786L;

	private Integer id;
	private String cronExpression;// cron表达式
	private String description;// 任务描述
	private String beanClass;// 任务执行时调用哪个类的方法 包名+类名
	private String jobStatus;// 任务状态
	private String jobGroup;// 任务分组
	private String jobName;// 任务名
	private Date createTime;// 创建时间
	private Date modifiedTime;// 更新时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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
