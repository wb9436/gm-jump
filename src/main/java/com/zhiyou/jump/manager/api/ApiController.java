package com.zhiyou.jump.manager.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.jump.configuration.redis.RedisCacheUtils;
import com.zhiyou.jump.configuration.util.utils.RespUtils;
import com.zhiyou.jump.manager.app.service.IConfigService;
import com.zhiyou.jump.manager.app.service.INoticeService;
import com.zhiyou.jump.manager.app.service.IUserInfoService;
import com.zhiyou.jump.manager.dto.app.ConfigDto;
import com.zhiyou.jump.manager.dto.app.NoticeDto;
import com.zhiyou.jump.manager.dto.app.UserInfoDto;
import com.zhiyou.jump.manager.dto.app.UserScoreDto;

@RequestMapping("/api")
@Controller
public class ApiController {

	@Value("${zhiyou-share.qrcodeUrl}")
	private String qrcodeUrl;

	@Value("${zhiyou.upload.url}")
	private String uploadUrl;

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IConfigService configService;
	@Autowired
	private IUserInfoService userInfoService;

	@GetMapping("/notice")
	public void notice(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			NoticeDto notice = noticeService.getPublishNotice();
			if (notice != null) {
				result.put("title", notice.getTitle());
				result.put("content", notice.getContent());
				result.put("code", 200);
				result.put("msg", "成功");
			} else {
				result.put("code", 201);
				result.put("msg", "未发布公告");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 201);
			result.put("msg", "获取公告异常");
		}
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/config")
	public void config(HttpServletRequest request, HttpServletResponse response) {
		String keywords = "isContinue";
		ConfigDto config = configService.getKeywords(keywords);
		JSONObject result = new JSONObject();
		if (config != null) {
			result.put(keywords, config.getValue());
		} else {
			result.put(keywords, 0);
		}
		result.put("code", 200);
		result.put("msg", "成功");
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/qrcode")
	public void qrcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// InputStream logoInputStream =
		// this.getClass().getResourceAsStream("/static/img/gzh/logo.png");
		// InputStream bgImageStream =
		// this.getClass().getResourceAsStream("/static/img/gzh/background.png");
		// File logoFile = new File("logo.png");
		// FileUtils.copyInputStreamToFile(logoInputStream, logoFile);
		//
		// String qrUrl = qrcodeUrl;
		// BufferedImage haibaoImage = QRCodeUtil.createHaibao(qrUrl,
		// bgImageStream, logoFile);
		// response.setHeader("Access-Control-Allow-origin", "*");
		// response.setHeader("Content-Type", "image/png");
		// ImageIO.write(haibaoImage, "png", response.getOutputStream());

		// InputStream miniGmStream =
		// this.getClass().getResourceAsStream("/static/img/gzh/mini-gm.png");
		File destFile = new File(uploadUrl, "/gzh/mini-gm.png");
		InputStream miniGmStream = new FileInputStream(destFile);
		BufferedImage haibaoImage = ImageIO.read(miniGmStream);

		response.setHeader("Access-Control-Allow-origin", "*");
		response.setHeader("Content-Type", "image/png");
		ImageIO.write(haibaoImage, "png", response.getOutputStream());
	}

	public static final String Appid = "wx89000cde7130f838";
	public static final String Secret = "6551463f75f65a0c684139f8506f4128";

	@RequestMapping("/getOpenid")
	public void getOpenid(@RequestParam String code, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Appid + "&secret=" + Secret + "&js_code="
				+ code + "&grant_type=authorization_code";
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url);
			// 发送get请求
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					String result = EntityUtils.toString(entity, "UTF-8");
					JSONObject resultJson = JSONObject.parseObject(result);
					String session_key = resultJson.getString("session_key");
					String openid = resultJson.getString("openid");
					json.put("session_key", session_key);
					json.put("openid", openid);
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RespUtils.writeToClient(response, json);
	}

	@PostMapping("/saveScore")
	public void saveScore(@RequestParam Map<String, String> params, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			userInfoService.saveScore(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("code", 200);
		result.put("msg", "成功");
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/rank")
	public void rank(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		JSONObject result = userInfoService.rank(params);
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/add/{userId}")
	public void testAdd(@PathVariable("userId") int userId, @RequestParam Map<String, Object> params,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();

		String test = (String) params.get("test");
		if ("yes".equals(test)) {
			UserInfoDto userInfoDto = new UserInfoDto();
			userInfoDto.setUserId(userId);
			userInfoDto.setScore(userId * 10);
			userInfoDto.setAvatarUrl(
					"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epnEUlyibN0hScwk3sakWTE7ibZia9zZYkyhCUmD7VUHcTPDWzYLsxJ72XC3OsMrP8f9CGsWL3fhNAZQ/132");
			userInfoDto.setNickName("小明" + userId);
			RedisCacheUtils.addRangeRank(null, new UserScoreDto(userInfoDto));
		}
		result.put("totalMembers", RedisCacheUtils.countRangeRankNum());
		result.put("code", 200);
		result.put("msg", "成功");
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/get/{start}/{end}")
	public void testGet(@PathVariable("start") int start, @PathVariable("end") int end, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		List<UserScoreDto> list = RedisCacheUtils.getRangeRank(start, end);
		result.put("list", list);
		result.put("totalMembers", RedisCacheUtils.countRangeRankNum());
		result.put("code", 200);
		result.put("msg", "成功");
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/delete")
	public void testDelete(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String secret = (String) params.get("secret");
		if ("success".equals(secret)) {
			Object obj = params.get("userId");
			if (obj != null) {
				try {
					String userIdStr = (String) obj;
					UserInfoDto userInfo = userInfoService.getUserInfo(Integer.parseInt(userIdStr));
					RedisCacheUtils.deleteRangeRankByMember(new UserScoreDto(userInfo));
					result.put("userId", userIdStr);
				} catch (Exception e) {
				}
			} else {
				RedisCacheUtils.deleteRangeRank();
			}
			result.put("code", 200);
			result.put("msg", "成功");
		} else {
			result.put("code", 201);
			result.put("msg", "secret error");
		}
		RespUtils.writeToClient(response, result);
	}

	@GetMapping("/reset")
	public void testReset(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String secret = (String) params.get("secret");
		if ("success".equals(secret)) {
			String curPageNum = (String) params.get("curPageNum");
			try {
				userInfoService.doResetRangeRank(curPageNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.put("totalMembers", RedisCacheUtils.countRangeRankNum());
			result.put("code", 200);
			result.put("msg", "成功");
		} else {
			result.put("code", 201);
			result.put("msg", "secret error");
		}
		RespUtils.writeToClient(response, result);
	}
	
}
