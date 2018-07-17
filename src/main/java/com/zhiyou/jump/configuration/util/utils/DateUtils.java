package com.zhiyou.jump.configuration.util.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final long DAY_MILLIS = 86400000;
	public static final long HOUR_MILLIS = 3600000;

	/**
	 * 获取时间格式化后字符串: 
	 * yyyy-MM-dd HH:mm:ss 
	 * yyyy-MM-dd 
	 * yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatDateString(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);
		return df.format(date);
	}

}
