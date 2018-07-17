package com.zhiyou.jump.manager.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyou.jump.configuration.util.service.BService;
import com.zhiyou.jump.manager.app.dao.ConfigDao;
import com.zhiyou.jump.manager.app.service.IConfigService;
import com.zhiyou.jump.manager.dto.app.ConfigDto;

@Service
public class ConfigServiceImpl extends BService implements IConfigService{

	@Autowired
	private ConfigDao configDao;
	
	@Override
	public List<ConfigDto> list() {
		return configDao.list();
	}

	@Transactional
	@Override
	public int save(ConfigDto config) {
		config.setCreateTime(new Date());
		config.setModifiedTime(new Date());
		return configDao.save(config);
	}

	@Override
	public ConfigDto get(Integer id) {
		return configDao.get(id);
	}

	@Transactional
	@Override
	public int update(ConfigDto config) {
		config.setModifiedTime(new Date());
		return configDao.update(config);
	}

	@Transactional
	@Override
	public int remove(Integer id) {
		return configDao.remove(id);
	}

	@Transactional
	@Override
	public int batchremove(Integer[] ids) {
		return configDao.batchRemove(ids);
	}

	@Override
	public ConfigDto getKeywords(String keywords) {
		Map<String, Object> params = new HashMap<>();
		params.put("keywords", keywords);
		return configDao.getKeywords(params);
	}

}
