package com.zhiyou.jump.manager.app.service;

import java.util.List;

import com.zhiyou.jump.manager.dto.app.ConfigDto;

public interface IConfigService {

	List<ConfigDto> list();

	int save(ConfigDto config);

	ConfigDto get(Integer id);

	int update(ConfigDto config);

	int remove(Integer id);

	int batchremove(Integer[] ids);

	ConfigDto getKeywords(String keywords);

}
