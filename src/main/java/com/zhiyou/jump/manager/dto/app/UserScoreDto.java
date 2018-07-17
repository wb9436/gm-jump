package com.zhiyou.jump.manager.dto.app;

public class UserScoreDto implements java.io.Serializable {
	private static final long serialVersionUID = -3778161926883915336L;
	
	private Integer userId;//
	private String openid;//
	private String nickName;// 昵称
	private String avatarUrl;// 头像
	private Integer gender;// 性别:0=未知;1=男;2=女
	private Integer score;// 得分

	public UserScoreDto() {}
	public UserScoreDto(UserInfoDto userInfo) {
		this.userId = userInfo.getUserId();
		this.openid = userInfo.getOpenid();
		this.nickName = userInfo.getNickName();
		this.avatarUrl = userInfo.getAvatarUrl();
		this.gender = userInfo.getGender();
		this.score = userInfo.getScore();
	}

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

}
