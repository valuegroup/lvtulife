package com.lvtulife.base.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lvtulife.base.filter.FastjsonFilter;

/**
 * Java对象和JSON字符串相互转化工具类 使用fastjson.jar
 * 
 * @author lvtulife
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties, HttpServletResponse response) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
			logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			String json;
			// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
			// hh24:mi:ss
			// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			if (json.getBytes().length > 50000) {
				logger.info("转换后的JSON字符串：长度过长内容已省略");
			} else {
				logger.info("转换后的JSON字符串：" + json);
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String writeJson(Object object, String[] includesProperties, String[] excludesProperties) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
			logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			String json;
			// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
			// hh24:mi:ss
			// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			logger.info("转换后的JSON字符串：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 兼容IE6
	 * 
	 * @param object
	 * @param includesProperties
	 * @param excludesProperties
	 * @param filter
	 * @return
	 */
	private String toJsonForIE6(Object object, String[] includesProperties, String[] excludesProperties, FastjsonFilter filter) {
		// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
		String json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
		return json;
	}

	private static void writeJsonByFilter(Object object, String callback, HttpServletResponse response) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			String json;
			// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
			// hh24:mi:ss
			// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(callback + "(" + json + ");");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeJsonp(Object object, String callback, HttpServletResponse response) {
		writeJsonByFilter(object, callback, response);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeJson(Object object, HttpServletResponse response) {
		writeJsonByFilter(object, null, null, response);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 */
	public static void writeJsonByIncludesProperties(Object object, String[] includesProperties, HttpServletResponse response) {
		writeJsonByFilter(object, includesProperties, null, response);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByExcludesProperties(Object object, String[] excludesProperties, HttpServletResponse response) {
		writeJsonByFilter(object, null, excludesProperties, response);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static String writeJson(Object object) {
		return writeJson(object, null, null);
	}

	/**
	 * 对象转json
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
		return JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * json字符串转化为List<T>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * json字符串转化为T
	 * 
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> clazz) {
		try {
			T t = JSON.parseObject(str, clazz);
			return t;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * json字符串遍历转化为List<T>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJsonListFor(String json, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		try {
			JSONArray jsonArr = JSON.parseArray(json);
			for (int i = 0; i < jsonArr.size(); i++) {
				T t = jsonArr.getObject(i, clazz);
				list.add(t);
			}
			if (list.size() == 0)
				return null;
			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
