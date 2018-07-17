package com.zhiyou.jump.manager.app.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.jump.manager.dto.app.UserInfoDto;

public interface IUserInfoService {

	void saveScore(Map<String, String> params);

	JSONObject rank(Map<String, Object> params);

	void resetScore();

	UserInfoDto getUserInfo(int userId);

	void doResetRangeRank(String curPageNum);
	
}
