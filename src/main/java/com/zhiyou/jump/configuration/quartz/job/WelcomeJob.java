package com.zhiyou.jump.configuration.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhiyou.jump.manager.system.service.ITestService;

@Component
public class WelcomeJob implements Job {

	@Autowired
	private ITestService testService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		testService.startTestJob();
	}

}
