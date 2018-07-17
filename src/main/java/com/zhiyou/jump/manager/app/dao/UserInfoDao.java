package com.zhiyou.jump.manager.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhiyou.jump.manager.dto.app.UserInfoDto;

@Mapper
public interface UserInfoDao {

	UserInfoDto get(@Param("openid")String openid);
	
	List<UserInfoDto> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	int save(UserInfoDto userInfoDto);

	int update(UserInfoDto userInfoDto);

	List<UserInfoDto> listRank(Map<String, Object> params);

	int countRank(Map<String, Object> params);

	void resetScore();

	UserInfoDto getById(@Param("userId")int userId);

}
