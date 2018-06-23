package com.sky.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	

	public static String formatDate(Date date, String pattern) {
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String conversionDateString(String date, String patternFrom, String patternTo) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(patternFrom);
			Date tmp = sdf.parse(date);
			result = formatDate(tmp, patternTo);
			return result;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static Date parseString(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
     * 获取与指定日期相对天数的日期
     * 
     * @param date
     *            日期
     * @param days
     *            相对天数，允许为负数
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

	/**
	 * 增加分钟
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	/**
	 * 增加秒钟
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/**
	 * 增加豪秒钟
	 * @param date
	 * @param miliSeconds
	 * @return
	 */
	public static Date addMiliSeconds(Date date, int miliSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, miliSeconds);
		return calendar.getTime();
	}

}
