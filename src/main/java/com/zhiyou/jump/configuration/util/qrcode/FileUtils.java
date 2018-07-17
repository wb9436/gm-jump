package com.zhiyou.jump.configuration.util.qrcode;

import java.io.File;

import com.zhiyou.jump.configuration.util.utils.StringUtils;

public class FileUtils {

	/**
	 * @param pathName 判断路径是否存在，如果不存在则创建
	 */
	public static void mkdirs(String pathName) {
		if (StringUtils.isEmpty(pathName)) {
			return;
		}
		File file = new File(pathName);
		if (!file.getParentFile().exists()) { // 判断父目录是否存在,如果父目录不存在，则创建目录
			file.getParentFile().mkdir();
		}
	}
	

}
