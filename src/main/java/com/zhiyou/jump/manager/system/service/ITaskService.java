package com.zhiyou.jump.manager.system.service;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.zhiyou.jump.manager.dto.system.TaskDto;

public interface ITaskService {

	List<TaskDto> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	TaskDto get(Integer id);

	int save(TaskDto taskScheduleJob);

	void update(TaskDto taskScheduleJob);

	int remove(Integer id);

	int batchRemove(Integer[] ids);

	void changeStatus(Integer id, String cmd);

	void initSchedule() throws SchedulerException;
}
	