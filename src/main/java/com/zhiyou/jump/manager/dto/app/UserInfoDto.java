package com.zhiyou.jump.manager.dto.app;

import java.util.Date;

import com.zhiyou.jump.configuration.util.dto.DTO;

public class UserInfoDto extends DTO{
	private static final long serialVersionUID = 530162328856706185L;

	private Integer userId;//
	private String openid;//
	private String nickName;//昵称
	private String avatarUrl;//头像
	private Integer gender;//性别:0=未知;1=男;2=女
	private Integer score;//得分
	private Date createTime;//创建时间
	private Date modifiedTime;//更新时间
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	private Integer rankNum;

	public Integer getRankNum() {
		return rankNum;
	}
	public void setRankNum(Integer rankNum) {
		this.rankNum = rankNum;
	}
	@Override
	public int hashCode() {
		if (userId == null) {
			return Integer.MIN_VALUE;
		}
		return userId.intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj ==  null) {
			return false;
		}
		UserInfoDto that = (UserInfoDto)obj;
		if (this.getUserId() == that.getUserId()) {
			return true;
		}
		return false;
	}
		
	
	
}
