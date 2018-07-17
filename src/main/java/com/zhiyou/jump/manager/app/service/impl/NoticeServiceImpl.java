package com.zhiyou.jump.manager.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyou.jump.configuration.util.service.BService;
import com.zhiyou.jump.manager.app.dao.NoticeDao;
import com.zhiyou.jump.manager.app.service.INoticeService;
import com.zhiyou.jump.manager.dto.app.NoticeDto;

@Service
public class NoticeServiceImpl extends BService implements INoticeService{

	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public List<NoticeDto> list(Map<String, Object> params) {
		return noticeDao.list(params);
	}

	@Override
	public int count(Map<String, Object> params) {
		return noticeDao.count(params);
	}

	@Transactional
	@Override
	public int save(NoticeDto notice) {
		notice.setStatus(NoticeDto.PUBLISHED_NO);
		notice.setCreateTime(new Date());
		notice.setModifiedTime(new Date());
		return noticeDao.save(notice);
	}

	@Override
	public NoticeDto get(Integer id) {
		return noticeDao.get(id);
	}

	@Transactional
	@Override
	public int update(NoticeDto notice) {
		notice.setModifiedTime(new Date());
		return noticeDao.update(notice);
	}

	@Transactional
	@Override
	public int remove(Integer id) {
		return noticeDao.remove(id);
	}

	@Transactional
	@Override
	public int batchremove(Integer[] ids) {
		return noticeDao.batchRemove(ids);
	}

	@Transactional
	@Override
	public void changeStatus(Integer id, Integer status) {
		NoticeDto noticeDto = noticeDao.get(id);
		noticeDto.setStatus(status);
		noticeDto.setModifiedTime(new Date());
		noticeDao.update(noticeDto);
	}

	@Override
	public NoticeDto getPublishNotice() {
		return noticeDao.getPublishNotice(NoticeDto.PUBLISHED_YES);
	}

}
