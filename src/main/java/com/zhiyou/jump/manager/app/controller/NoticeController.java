package com.zhiyou.jump.manager.app.controller;

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
import com.zhiyou.jump.manager.app.service.INoticeService;
import com.zhiyou.jump.manager.dto.app.NoticeDto;

@RequestMapping("/app/notice")
@Controller
public class NoticeController extends BaseController {
	private String prefix = "app/notice";

	@Autowired
	private INoticeService noticeService;

	@RequiresPermissions("app:notice:notice")
	@GetMapping()
	public String notice(Model model) {
		return prefix + "/notice";
	}

	@RequiresPermissions("app:notice:notice")
	@GetMapping("/list")
	@ResponseBody
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<NoticeDto> noticeList = noticeService.list(query);
		int total = noticeService.count(query);
		PageUtils pageUtil = new PageUtils(noticeList, total);
		return pageUtil;
	}

	@RequiresPermissions("app:notice:add")
	@GetMapping("/add")
	public String add(Model model) {
		return prefix + "/add";
	}

	@RequiresPermissions("app:notice:add")
	@PostMapping("/save")
	@ResponseBody
	public Result save(NoticeDto notice) {
		if (noticeService.save(notice) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:notice:edit")
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		NoticeDto noticeDto = noticeService.get(id);
		model.addAttribute("notice", noticeDto);
		return prefix + "/edit";
	}

	@RequiresPermissions("app:notice:edit")
	@PostMapping("/update")
	@ResponseBody
	public Result update(NoticeDto notice) {
		if (noticeService.update(notice) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:notice:remove")
	@PostMapping("/remove")
	@ResponseBody
	public Result remove(Integer id) {
		if (noticeService.remove(id) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:notice:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result batchRemove(@RequestParam("ids[]") Integer[] ids) {
		int r = noticeService.batchremove(ids);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequiresPermissions("app:notice:change")
	@PostMapping(value = "/changeNoticeStatus")
	@ResponseBody
	public Result changeNoticeStatus(Integer id, Integer status) {
		String label = "下架";
		if (NoticeDto.PUBLISHED_YES == status.intValue()) {
			label = "发布";
		} 
		try {
			noticeService.changeStatus(id, status);
			return Result.ok("公告" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok("公告" + label + "失败");
	}

	

}
