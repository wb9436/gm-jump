package com.zhiyou.jump.configuration.util.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * @Date 2016-1-20
 */
public class RespUtils {

	public static String encodeURL(String data) {
		try {
			return URLEncoder.encode(data, "utf8");
		} catch (UnsupportedEncodingException e) {
			return data;
		}
	}

	public static String decodeURL(String data) {
		try {
			return URLDecoder.decode(data, "utf8");
		} catch (IOException e) {
			return data;
		}
	}	

	public static void writeDataToClient(HttpServletResponse response, String data) {
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			out = response.getOutputStream();
			byte[] bytes = StringUtils.getUTFBytes(data);
			out.write(bytes);
			out.flush();
		} catch (Exception e) {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void writeToClient(HttpServletResponse response, JSONObject result) {
        PrintWriter pw = null;
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/json; charset=utf-8");
            pw = response.getWriter();
            pw.println(result.toJSONString());
            pw.flush();
        } catch (Exception e) {
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
	
}
