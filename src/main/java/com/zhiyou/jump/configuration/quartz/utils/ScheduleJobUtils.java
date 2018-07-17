package com.zhiyou.jump.configuration.quartz.utils;

import com.zhiyou.jump.manager.dto.system.TaskDto;

public class ScheduleJobUtils {
	
	public static ScheduleJob entityToData(TaskDto scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		return scheduleJob;
	}
	
}