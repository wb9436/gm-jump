package com.zhiyou.jump.configuration.redis;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyou.jump.manager.dto.app.UserScoreDto;

public class RedisCacheUtils {
	public static final Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);
	
	private static RedisClient redis;
    static {
    	redis = new RedisClient();
    	redis.init();
    }
    
    public static final int DEFAULT_EXPIRE_SECONDS = 86400;
    public static final int MONTH_EXPIRE_SECONDS = 30 * 86400;
    
    public static <T> void putInCache(String key,T o) {
        redis.setObject(key, o);
    }
    
    public static <T> T getFromCache(String key) {
        return redis.getObject(key);
    }
    
    public static void delFromCache(String key) {
        redis.del(key);
    }
    
    public static <T> void putInCache(String key,T o,int seconds) {
        redis.setObject(key, o,seconds);
    }
    
    /**
	 * @param pattern
	 */
	public static Set<String> getKeys(String pattern) {
		return redis.keys(pattern);
	}
	
    /** 服务器选择策略 */
    public static long getServerPolicyID() {
        Long l = redis.incr("ServerSelectPolicyID");
        return l.longValue();
    }
    
    public static final String ScoreRank = "GAME_ALL_GOAL_RANK";
    
    /**
     * 加入榜单
     * @param oldMember
     * @param newMember
     */
	public static void addRangeRank(UserScoreDto oldMember, UserScoreDto newMember) {
		Double zscore = redis.zscore(ScoreRank, oldMember);
		if(zscore != null && zscore.doubleValue() != Double.MIN_VALUE){//存在-更新
			redis.zrem(ScoreRank, oldMember);
		}
		redis.zadd(ScoreRank, new Double(newMember.getScore()), newMember);
	}

	/**
	 * 获取榜单成员数量
	 * @return
	 */
	public static Long countRangeRankNum() {
		return redis.zcard(ScoreRank);
	}
	
	/**
	 * 获取成员排名
	 * @param member
	 * @return
	 */
	public static int getRank(UserScoreDto member) {
		Long rankNum = redis.zrevrank(ScoreRank, member);
		if (rankNum != null) {
			return rankNum.intValue() + 1;
		}
		return 0;
	}
	
	/**
	 * 获取榜单列表
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<UserScoreDto> getRangeRank(int start, int end) {
		return redis.zrevRangeRank(ScoreRank, start, end);
	}
	
	/**
	 * 删除榜单
	 */
	public static void deleteRangeRank(){
		redis.del(ScoreRank);
	}

	/**
	 * 删除成员
	 */
	public static void deleteRangeRankByMember(UserScoreDto member){
		Double zscore = redis.zscore(ScoreRank, member);
		if(zscore != null && zscore.doubleValue() != Double.MIN_VALUE){
			redis.zrem(ScoreRank, member);
		}
	}
	
	/**
	 * 清理榜单记录
	 * 正序清理(从小到大)
	 */
	public static void cleanRangeRank(int start, int end){
		redis.zremRangeByRank(ScoreRank, start, end);
	}
	
}
