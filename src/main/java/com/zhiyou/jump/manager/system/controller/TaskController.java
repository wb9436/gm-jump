package com.zhiyou.jump.manager.system.controller;

import java.util.List;
import java.util.Map;

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
import com.zhiyou.jump.configuration.util.utils.PageUtils;
import com.zhiyou.jump.configuration.util.utils.Query;
import com.zhiyou.jump.configuration.util.utils.Result;
import com.zhiyou.jump.manager.dto.system.TaskDto;
import com.zhiyou.jump.manager.system.service.ITaskService;

@RequestMapping("/sys/task")
@Controller
public class TaskController extends BaseController {
	public String prefix = "system/task";

	@Autowired
	private ITaskService taskService;

	@RequiresPermissions("sys:task:task")
	@GetMapping()
	public String taskScheduleJob() {
		return prefix + "/task";
	}

	@RequiresPermissions("sys:task:task")
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<TaskDto> taskScheduleJobList = taskService.list(query);
		int total = taskService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@RequiresPermissions("sys:task:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	@RequiresPermissions("sys:task:edit")
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		TaskDto job = taskService.get(id);
		model.addAttribute("job", job);
		return prefix + "/edit";
	}

	@RequestMapping("/info/{id}")
	public Result info(@PathVariable("id") Integer id) {
		TaskDto taskScheduleJob = taskService.get(id);
		return Result.ok().put("taskScheduleJob", taskScheduleJob);
	}

	@RequiresPermissions("sys:task:add")
	@ResponseBody
	@PostMapping("/save")
	public Result save(TaskDto taskScheduleJob) {
		if (taskService.save(taskScheduleJob) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("sys:task:edit")
	@ResponseBody
	@PostMapping("/update")
	public Result update(TaskDto taskScheduleJob) {
		taskService.update(taskScheduleJob);
		return Result.ok();
	}

	@RequiresPermissions("sys:task:remove")
	@PostMapping("/remove")
	@ResponseBody
	public Result remove(Integer id) {
		if (taskService.remove(id) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("sys:task:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result remove(@RequestParam("ids[]") Integer[] ids) {
		taskService.batchRemove(ids);
		return Result.ok();
	}

	@RequiresPermissions("sys:task:change")
	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public Result changeJobStatus(Integer id, String cmd) {
		String label = "停止";
		if ("start".equals(cmd)) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			taskService.changeStatus(id, cmd);
			return Result.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok("任务" + label + "失败");
	}

}
