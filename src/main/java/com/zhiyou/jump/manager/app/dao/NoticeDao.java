package com.zhiyou.jump.manager.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhiyou.jump.manager.dto.app.NoticeDto;

@Mapper
public interface NoticeDao {

	NoticeDto get(@Param("id")Integer id);

	List<NoticeDto> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	int save(NoticeDto notice);

	int update(NoticeDto notice);

	int remove(@Param("id")Integer id);

	int batchRemove(Integer[] ids);

	NoticeDto getPublishNotice(@Param("status")int status);
	
}
