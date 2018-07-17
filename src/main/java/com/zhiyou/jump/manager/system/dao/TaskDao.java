package com.zhiyou.jump.manager.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhiyou.jump.manager.dto.system.TaskDto;

@Mapper
public interface TaskDao {

	TaskDto get(@Param("id")Integer id);
	
	List<TaskDto> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskDto task);
	
	int update(TaskDto task);
	
	int remove(@Param("id")Integer id);
	
	int batchRemove(Integer[] ids);
}
