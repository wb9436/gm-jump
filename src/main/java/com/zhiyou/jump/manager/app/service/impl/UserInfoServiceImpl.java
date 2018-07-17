package com.zhiyou.jump.manager.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.jump.configuration.redis.RedisCacheUtils;
import com.zhiyou.jump.configuration.util.service.BService;
import com.zhiyou.jump.configuration.util.utils.PagingDto;
import com.zhiyou.jump.configuration.util.utils.StringUtils;
import com.zhiyou.jump.manager.app.dao.UserInfoDao;
import com.zhiyou.jump.manager.app.service.IUserInfoService;
import com.zhiyou.jump.manager.dto.app.UserInfoDto;
import com.zhiyou.jump.manager.dto.app.UserScoreDto;

@Service
public class UserInfoServiceImpl extends BService implements IUserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Transactional
	@Override
	public void saveScore(Map<String, String> params) {
		try {
			String openid = params.get("openid");
			String nickName = params.get("nickName");
			String avatarUrl = params.get("avatarUrl");
			String genderStr = params.get("gender");
			String scoreStr = params.get("score");
			String isTest = params.get("isTest");
			if ("0".equals(isTest) && !StringUtils.isEmpty(openid) && !"undefined".equals(avatarUrl)) {
				int score = Integer.parseInt(scoreStr);
				int gender = 0;
				try {
					gender = Integer.parseInt(genderStr);
				} catch (Exception e) {
				}
				nickName = StringUtils.filterEmoji(nickName);
				
				UserScoreDto oldScore = null;
				UserInfoDto userInfoDto = userInfoDao.get(openid);
				if (userInfoDto == null) {
					userInfoDto = new UserInfoDto();
					userInfoDto.setOpenid(openid);
					userInfoDto.setNickName(nickName);
					userInfoDto.setAvatarUrl(avatarUrl);
					userInfoDto.setGender(gender);
					userInfoDto.setScore(score);
					userInfoDto.setCreateTime(new Date());
					userInfoDto.setModifiedTime(new Date());
					userInfoDao.save(userInfoDto);
				} else {
					oldScore = new UserScoreDto(userInfoDto);
					userInfoDto.setNickName(nickName);
					userInfoDto.setAvatarUrl(avatarUrl);
					userInfoDto.setGender(gender);
					if (score > userInfoDto.getScore()) {
						userInfoDto.setScore(score);
					}
					userInfoDto.setModifiedTime(new Date());
					userInfoDao.update(userInfoDto);
				}
				// 添加榜单,分数要大于0
				if (userInfoDto.getScore().intValue() > 0) {
					RedisCacheUtils.addRangeRank(oldScore, new UserScoreDto(userInfoDto));
				}
			}
		} catch (Exception e) {
			logger.error("Error data : " + JSONObject.toJSONString(params));
			logger.error("Error : saveScore", e);
		}
	}

	@Override
	public JSONObject rank(Map<String, Object> params) {
		JSONObject result = new JSONObject();
		String curPageNum = (String) params.get("curPageNum");
		String openid = (String) params.get("openid");
		try {
			Long total = RedisCacheUtils.countRangeRankNum();
			PagingDto paging = new PagingDto(curPageNum);
			paging.setTotalRows(total != null && total > 0 ? total.intValue() : 0);
			List<UserScoreDto> list = new ArrayList<>();
			if (paging.getCurPageNum() <= paging.getTotalPageNum()) {
				list = RedisCacheUtils.getRangeRank(paging.getStartRowIndex(),
						paging.getStartRowIndex() + paging.getPageSize() - 1);
			}
			UserInfoDto userInfoDto = userInfoDao.get(openid);
			int rankNum = 0;
			if (userInfoDto != null) {
				rankNum = RedisCacheUtils.getRank(new UserScoreDto(userInfoDto));
			}

			result.put("totalPage", paging.getTotalPageNum());
			result.put("list", list);
			result.put("rankNum", rankNum);
			result.put("code", 200);
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 201);
			result.put("msg", "加载数据失败");
		}
		result.put("curPageNum", curPageNum);
		return result;
	}

	@Transactional
	@Override
	public void resetScore() {
		userInfoDao.resetScore();
	}

	@Override
	public UserInfoDto getUserInfo(int userId) {
		return userInfoDao.getById(userId);
	}

	@Override
	public void doResetRangeRank(String curPageNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("curPageNum", curPageNum);
		PagingDto paging = new PagingDto(50, curPageNum);
		paging.setTotalRows(1000);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		List<UserInfoDto> listRank = userInfoDao.listRank(params);
		if (listRank != null && listRank.size() > 0) {
			for (int index = 0; index < listRank.size(); index++) {
				UserInfoDto userInfoDto = listRank.get(index);
				if (userInfoDto.getScore().intValue() > 0) {
					RedisCacheUtils.addRangeRank(null, new UserScoreDto(userInfoDto));
				}
			}
		}
	}

}
