package com.zhiyou.jump.manager.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhiyou.jump.configuration.controller.BaseController;
import com.zhiyou.jump.configuration.shiro.ShiroUtils;
import com.zhiyou.jump.configuration.util.secret.MD5;
import com.zhiyou.jump.configuration.util.utils.Result;
import com.zhiyou.jump.configuration.util.utils.Tree;
import com.zhiyou.jump.manager.dto.system.MenuDto;
import com.zhiyou.jump.manager.dto.system.UserDto;
import com.zhiyou.jump.manager.system.service.IMenuService;
import com.zhiyou.jump.manager.system.service.IUserService;

@Controller
public class LoginController extends BaseController {

	@Value("${zhiyou.logoutUri}")
	private String logoutUri;
	
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public Result ajaxLogin(String username, String password) {
		password = MD5.encodeUTF8(password);
		UserDto userDto = userService.getUserDto(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		if (userDto != null) {
			if (userDto.getStatus() == 0) {
				return Result.error("账号已被锁定,请联系管理员");
			}
			subject.login(token);
			return Result.ok();
		} else {
			return Result.error("用户或密码错误");
		}
	}

	@GetMapping("/index")
	public String index(Model model) {
		List<Tree<MenuDto>> menus = menuService.getUserListMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("username", getUsername());
		return "index";
	}

	@GetMapping("/logout")
	public String logout() {
		ShiroUtils.logout();
		return logoutUri;
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

}
