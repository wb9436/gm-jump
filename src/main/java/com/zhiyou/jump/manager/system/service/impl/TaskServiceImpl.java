package com.zhiyou.jump.manager.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyou.jump.configuration.quartz.config.QuartzManager;
import com.zhiyou.jump.configuration.quartz.utils.ScheduleJob;
import com.zhiyou.jump.configuration.quartz.utils.ScheduleJobUtils;
import com.zhiyou.jump.configuration.util.service.BService;
import com.zhiyou.jump.manager.dto.system.TaskDto;
import com.zhiyou.jump.manager.system.dao.TaskDao;
import com.zhiyou.jump.manager.system.service.ITaskService;

@Service
public class TaskServiceImpl extends BService implements ITaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Autowired(required = true)
	private QuartzManager quartzManager;

	@Override
	public List<TaskDto> list(Map<String, Object> params) {
		return taskDao.list(params);
	}

	@Override
	public int count(Map<String, Object> params) {
		return taskDao.count(params);
	}

	@Override
	public TaskDto get(Integer id) {
		return taskDao.get(id);
	}

	@Transactional
	@Override
	public int save(TaskDto taskScheduleJob) {
		taskScheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		taskScheduleJob.setCreateTime(new Date());
		taskScheduleJob.setModifiedTime(new Date());
		return taskDao.save(taskScheduleJob);
	}

	@Transactional
	@Override
	public void update(TaskDto taskScheduleJob) {
		taskScheduleJob.setModifiedTime(new Date());
		taskDao.update(taskScheduleJob);
	}

	@Transactional
	@Override
	public int remove(Integer id) {
		try {
			TaskDto scheduleJob = get(id);
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			return taskDao.remove(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional
	@Override
	public int batchRemove(Integer[] ids) {
		for (Integer id : ids) {
			try {
				TaskDto scheduleJob = get(id);
				quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			} catch (SchedulerException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return taskDao.batchRemove(ids);
	}

	@Transactional
	@Override
	public void changeStatus(Integer id, String cmd){
		try {
			TaskDto scheduleJob = get(id);
			if (scheduleJob == null) {
				return;
			}
			if ("stop".equals(cmd)) {
				quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
				scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
			} else {
				scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
				quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
			}
			update(scheduleJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initSchedule() throws SchedulerException {
		List<TaskDto> jobList = taskDao.list(new HashMap<String, Object>(16));
		for (TaskDto scheduleJob : jobList) {
			if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
				ScheduleJob job = ScheduleJobUtils.entityToData(scheduleJob);
				quartzManager.addJob(job);
			}
		}
	}

}
