package com.zhiyou.jump.configuration.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhiyou.jump.configuration.redis.RedisCacheUtils;
import com.zhiyou.jump.configuration.util.utils.DateUtils;

/**
 * 清理500名以后的记录
 * @author WB
 *
 */
@Component
public class CleanRankJob implements Job{
	private static Logger logger = LoggerFactory.getLogger(CleanRankJob.class);

	private static final int TotalRankNum = 500;//榜单取前五百名
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Long totalMembers = RedisCacheUtils.countRangeRankNum();
		if (totalMembers != null && totalMembers.intValue() > (TotalRankNum + 5)) {
			int start = 0;
			int end = totalMembers.intValue() - (TotalRankNum + 1);
			RedisCacheUtils.cleanRangeRank(start, end);
			logger.info("清理了名次 : TIME = " + DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
	}
	
}
