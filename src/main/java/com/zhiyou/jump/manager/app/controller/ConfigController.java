package com.zhiyou.jump.manager.app.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhiyou.jump.configuration.controller.BaseController;
import com.zhiyou.jump.configuration.util.utils.Result;
import com.zhiyou.jump.manager.app.service.IConfigService;
import com.zhiyou.jump.manager.dto.app.ConfigDto;

@RequestMapping("/app/config")
@Controller
public class ConfigController extends BaseController {
	private String prefix = "app/config";

	@Autowired
	private IConfigService configService;

	@RequiresPermissions("app:config:config")
	@GetMapping()
	public String config(Model model) {
		return prefix + "/config";
	}

	@RequiresPermissions("app:config:config")
	@GetMapping("/list")
	@ResponseBody
	public List<ConfigDto> list() {
		// 查询列表数据
		List<ConfigDto> configList = configService.list();
		return configList;
	}

	@RequiresPermissions("app:config:add")
	@GetMapping("/add")
	public String add(Model model) {
		return prefix + "/add";
	}

	@RequiresPermissions("app:config:add")
	@PostMapping("/save")
	@ResponseBody
	public Result save(ConfigDto config) {
		if (configService.save(config) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:config:edit")
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		ConfigDto config = configService.get(id);
		model.addAttribute("config", config);
		return prefix + "/edit";
	}

	@RequiresPermissions("app:config:edit")
	@PostMapping("/update")
	@ResponseBody
	public Result update(ConfigDto config) {
		if (configService.update(config) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:config:remove")
	@PostMapping("/remove")
	@ResponseBody
	public Result remove(Integer id) {
		if (configService.remove(id) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:config:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result batchRemove(@RequestParam("ids[]") Integer[] ids) {
		int r = configService.batchremove(ids);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	

}
