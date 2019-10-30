package com.newlife.s4.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期处理工具类
 * 
 * @author Administrator
 *
 */
public class DateUtils {

	public static final String YMD = "yyyyMMdd";
	public static final String YMD_YEAR = "yyyy";
	public static final String YMD_BREAK = "yyyy-MM-dd";
	public static final String YMDHMS = "yyyyMMddHHmmss";
	public static final String YMDHMS_BREAK = "yyyy-MM-dd HH:mm:ss";
	public static final String YMDHMS_BREAK_HALF = "yyyy-MM-dd HH:mm";
	public static final String YMDHMS_BREAK_HALF_CN = "yyyy年MM月dd日 HH:mm";

	/**
	 * 计算时间差
	 */
	public static final int CAL_MINUTES = 1000 * 60;
	public static final int CAL_HOURS = 1000 * 60 * 60;
	public static final int CAL_DAYS = 1000 * 60 * 60 * 24;

	/**
	 * 获取当前时间格式化后的值
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getNowDateText(String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 获取日期格式化后的值
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateText(Date date, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 字符串时间转换成Date格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 获取时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static Long getTime(Date date) {
		return date.getTime();
	}

	/**
	 * 计算时间差
	 * 
	 * @param startDate
	 * @param endDate
	 * @param calType
	 *            计算类型,按分钟、小时、天数计算
	 * @return
	 */
	public static int calDiffs(Date startDate, Date endDate, int calType) {
		Long start = DateUtils.getTime(startDate);
		Long end = DateUtils.getTime(endDate);
		int diff = (int) ((end - start) / calType);
		return diff;
	}

	/**
	 * 计算时间差值以某种约定形式显示
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String timeDiffText(Date startDate, Date endDate) {
		int calDiffs = DateUtils.calDiffs(startDate, endDate, DateUtils.CAL_MINUTES);
		if (calDiffs == 0) {
			return "刚刚";
		}
		if (calDiffs < 60) {
			return calDiffs + "分钟前";
		}
		calDiffs = DateUtils.calDiffs(startDate, endDate, DateUtils.CAL_HOURS);
		if (calDiffs < 24) {
			return calDiffs + "小时前";
		}
		if (calDiffs < 48) {
			return "昨天";
		}
		return DateUtils.getDateText(startDate, DateUtils.YMDHMS_BREAK_HALF_CN);
	}

	/**
	 * 显示某种约定后的时间值,类似微信朋友圈发布说说显示的时间那种
	 * 
	 * @param date
	 * @return
	 */
	public static String showTimeText(Date date) {
		return DateUtils.timeDiffText(date, new Date());
	}

	public static void main(String[] args) throws ParseException {
//		Date start = DateUtils.getDate("2018-05-02 08:54", DateUtils.YMDHMS_BREAK_HALF);
//		calDiffsNowByStr
//		System.out.println(DateUtils.calDiffsByStr("2018-07-04","2018-07-08"));
		Integer minus= DateUtils.calDiffsByStr("2018-08-24");
//		System.out.println(getDateText(minus("2018-07-04",30),YMD_BREAK));
        System.out.println(minus);
	}

	/**
	 * 获取当前时间 amount 天之后的时间
	 * @param amount
	 * @return
	 */
	public static Date add(int amount){
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	public static Date add(Date date,int amount){
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}


	/**
	 * 获取当前时间 amount 月之后的时间
	 * @param amount
	 * @return
	 */
	public static String addMonth(String startDate,int amount){
		return addMonth(startDate,amount,YMDHMS_BREAK);
	}

	public static String addMonth(String startDate,int amount,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS_BREAK);
		Calendar cal = new GregorianCalendar();
		cal.setTime(sdf.parse(startDate, new ParsePosition(0)));
		cal.add(Calendar.MONTH, amount);
		return getDateText(cal.getTime(),pattern);
	}

	public static Date addMonth(Date startDate,int amount){
		Calendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}

	/**
	 * 某日期离今天的天数
	 * @param endDate
	 * @return
	 */
	public static int calDiffsByStr( String endDate)  {
		try {
			Date d1 = new Date();
			Date d2 = DateUtils.getDate(endDate,YMD_BREAK);
			Long start = DateUtils.getTime(d1);
			Long end = DateUtils.getTime(d2);
			int diff = (int) ((end - start) / (24*60*60*1000));
			return diff;
		}catch (ParseException pe){

			pe.printStackTrace();
		}
		return 0;
	}
	public static int calDiffsByStr(String startDate, String endDate)  {
		try {
			Date d1 = DateUtils.getDate(startDate,YMD_BREAK);
			Date d2 = DateUtils.getDate(endDate,YMD_BREAK);
			Long start = DateUtils.getTime(d1);
			Long end = DateUtils.getTime(d2);
			int diff = (int) ((end - start) / (24*60*60*1000));
			return diff;
		}catch (ParseException pe){

			pe.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取 某时间 amount 天之后的时间
	 * @param amount
	 * @return
	 */
	public static Date add(String date1,int amount){
		try {
			Calendar cal = new GregorianCalendar();
			cal.setTime(getDate(date1, YMD_BREAK));
			cal.add(Calendar.DAY_OF_MONTH, amount);
			return cal.getTime();
		}catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取 某时间 amount 天之后的时间
	 * @param amount
	 * @return
	 */
	public static Date minus(String date1,int amount){
		try {
			Calendar cal = new GregorianCalendar();
			cal.setTime(getDate(date1, YMD_BREAK));
			cal.add(Calendar.DAY_OF_MONTH, 0-amount);
			return cal.getTime();
		}catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 当前日期加减分钟，负数为减
	 * @param start
	 * @param minute
	 * @return
	 */
	public static Date getDateAddMinute(Date start, int minute) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);
		ca.add(Calendar.MINUTE, minute);
		return ca.getTime();
	}
	/**
	 * 在日期 start上 增加 天 days ，返回增加天之后的日期
	 *
	 * @param start
	 * @param days
	 * @return
	 */
	public static Date getDateAddDays(Date start, int days) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);
		ca.add(Calendar.DATE, days);
		return ca.getTime();
	}

	public static boolean isValidDate(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess=false;
		}
		return convertSuccess;
	}

	/**
	 * 获取当前时间格式 YMD_BREAK  yyyy-mm-dd
	 *
	 * @return
	 */
	public static String getNowDateStandardText() {
		return getNowDateText(YMD_BREAK);
	}


	/**
	 * 计算  相差月份
	 *
	 */
	public static Integer diffMonth(String str1,String str2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		try {
			bef.setTime(sdf.parse(str1));
			aft.setTime(sdf.parse(str2));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result)+1;

	}
	public static String getDateStrFromStr(String date, String pattern1,String pattern2)  {
		SimpleDateFormat sdf1 =  new SimpleDateFormat(pattern1);;
		SimpleDateFormat sdf2 =  new SimpleDateFormat(pattern2);;
		Date s = null;
		try {
			s = sdf1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf2.format(s);

	}

	public static String addMonthWithPatten(String startDate,int amount,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar cal = new GregorianCalendar();
		cal.setTime(sdf.parse(startDate, new ParsePosition(0)));
		cal.add(Calendar.MONTH, amount);
		return getDateText(cal.getTime(),pattern);
	}
}
