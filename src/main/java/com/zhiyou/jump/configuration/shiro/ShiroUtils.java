package com.zhiyou.jump.configuration.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.zhiyou.jump.manager.dto.system.UserDto;

public class ShiroUtils {

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static UserDto getUser() {
		Object object = getSubjct().getPrincipal();
		return (UserDto) object;
	}

	public static Integer getUserId() {
		return getUser().getUserId();
	}

	public static void logout() {
		getSubjct().logout();
	}

	
}
