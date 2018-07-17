package com.zhiyou.jump.configuration.quartz.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhiyou.jump.configuration.redis.RedisCacheUtils;
import com.zhiyou.jump.configuration.util.utils.DateUtils;
import com.zhiyou.jump.manager.app.service.IUserInfoService;

/**
 * 重置排行榜(周一)
 * @author WB
 *
 */
@Component
public class ResetRankJob implements Job{
	private static Logger logger = LoggerFactory.getLogger(ResetRankJob.class);
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Calendar calendar = Calendar.getInstance();
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if(Calendar.MONDAY == weekDay){
			RedisCacheUtils.deleteRangeRank();
			userInfoService.resetScore();
			logger.error("周一重置排行榜  时间: " + DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
	}
}
