package com.zhiyou.jump.manager.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhiyou.jump.manager.dto.app.ConfigDto;

@Mapper
public interface ConfigDao {

	ConfigDto get(@Param("id") Integer id);

	List<ConfigDto> list();

	int save(ConfigDto config);

	int update(ConfigDto config);

	int remove(@Param("id") Integer id);

	int batchRemove(Integer[] ids);

	ConfigDto getKeywords(Map<String, Object> params);

}
