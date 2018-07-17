package com.zhiyou.jump.manager.app.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.jump.manager.dto.app.NoticeDto;

public interface INoticeService {

	List<NoticeDto> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	int save(NoticeDto notice);

	NoticeDto get(Integer id);

	int update(NoticeDto notice);

	int remove(Integer id);

	int batchremove(Integer[] ids);

	void changeStatus(Integer id, Integer status);

	NoticeDto getPublishNotice();

}
