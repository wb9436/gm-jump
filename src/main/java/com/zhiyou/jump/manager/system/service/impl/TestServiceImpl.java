package com.zhiyou.jump.manager.system.service.impl;

import org.springframework.stereotype.Service;

import com.zhiyou.jump.configuration.util.service.BService;
import com.zhiyou.jump.manager.system.service.ITestService;

@Service
public class TestServiceImpl extends BService implements ITestService{

	@Override
	public void startTestJob() {
		System.err.println("Welcome to manage system, now time :" + System.currentTimeMillis());
	}

}
