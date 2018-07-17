package com.zhiyou.jump.configuration.controller;

import org.springframework.stereotype.Controller;

import com.zhiyou.jump.configuration.shiro.ShiroUtils;
import com.zhiyou.jump.manager.dto.system.UserDto;

@Controller
public class BaseController {
	public UserDto getUser() {
		return ShiroUtils.getUser();
	}

	public Integer getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}