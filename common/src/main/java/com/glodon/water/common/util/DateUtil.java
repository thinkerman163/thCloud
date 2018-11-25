package com.glodon.water.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {


	public static Calendar setStartDay(Calendar cal) {
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal;
	}
	
	public static Calendar setEndDay(Calendar cal) {
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal;
	}
	/* HOUR_OF_DAY 指示一天中的小时 */
    public static String timeBeforHour(Calendar calendar){
    	//Calendar calendar = Calendar.getInstance();
   
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
   SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    
       return df.format(calendar.getTime());
    }
	public static Date string2Date(String dateString, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {			
			System.out.println(e.getMessage());
		}
		return date;
	}
 
	public static String date2String(Date date, String pattern) {
		if (date != null) {
			DateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	public static Date getNextDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, 3);  
        date = calendar.getTime();  
        return date;  
    } 
	public static int maxMonth(String dateString, String pattern) {
		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.string2Date(dateString, pattern);
		cal.setTime(date);
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return max;
	}

	public static int weekOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取向前或向后几天/月/年等
	 * 
	 * @param dateString
	 * @param field
	 *            天-Calendar.DATE、月-Calendar.MONTH、年-Calendar.YEAR等，
	 * @param amount
	 *            数量，向前则为负数
	 * @param pattern
	 * @return
	 */
	public static String beOrAfDate2String(String dateString, int field, int amount, String pattern) {
		Date date = DateUtil.string2Date(dateString, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		String s = DateUtil.date2String(cal.getTime(), pattern);
		return s;
	}

	public static Date beOrAfDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static String string2String(String dateString, String fromPattern, String toPattern) {
		DateFormat df = null;
		Date date = null;
		String dateStr = null;
		try {
			df = new SimpleDateFormat(fromPattern);
			date = df.parse(dateString);
			df = new SimpleDateFormat(toPattern);
			dateStr = df.format(date);
		} catch (ParseException e) {			
			System.out.println(e.getMessage());
		}
		return dateStr;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 得到指定月的天数
	 */
	public static int getMonthLastDay(Date date) {
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * int型分钟数据格式化成 天 小时 分
	 * 
	 * @param mi
	 * @return
	 */
	public static String formatMinute(int mi) {
		StringBuffer result = new StringBuffer();
		int hour = mi / 60;

		if (hour > 0) {
			// if (hour > 23) {
			// int day = hour / 24;
			// hour = hour % 24;
			// result.append(day).append("天");
			// }
			result.append(hour).append("小时");
		}
		int lmi = mi % 60;
		result.append(lmi).append("分");
		return result.toString();
	}

	/**
	 * 两个日期的分钟时间差
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getTimeSpan(Date start, Date end) {
		long span = end.getTime() - start.getTime();
		long rs = span / 60000;
		return (int) rs;
	}

	/**
	 * 两个日期相差多少天数
	 * 
	 * @param start
	 * @param end
	 * @param format
	 * @return
	 */
	public static int getDaySpan(String start, String end, String format) {
		Calendar s = Calendar.getInstance();
		s.setTime(DateUtil.string2Date(start, format));
		Calendar e = Calendar.getInstance();
		e.setTime(DateUtil.string2Date(end, format));
		return (int) ((e.getTimeInMillis() - s.getTimeInMillis()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 两个日期相差多少天数
	 * 
	 * @param start
	 * @param end
	 * @param format
	 * @return
	 */
	public static int getMonthSpan(String start, String end, String format) {
		Calendar s = Calendar.getInstance();
		s.setTime(DateUtil.string2Date(start, format));
		Calendar e = Calendar.getInstance();
		e.setTime(DateUtil.string2Date(end, format));
		int n = 0;

		int month = e.get(Calendar.MONTH) - s.get(Calendar.MONTH);
		if (month == 0) {
			if (e.get(Calendar.YEAR) - s.get(Calendar.YEAR) == 0) {
				n = 0;
			}
			if (e.get(Calendar.YEAR) - s.get(Calendar.YEAR) > 0) {
				n = (e.get(Calendar.YEAR) - s.get(Calendar.YEAR)) * 12;
			}

		} else if (month > 0) {
			if (e.get(Calendar.YEAR) - s.get(Calendar.YEAR) == 0) {
				n = month;
			}
			if (e.get(Calendar.YEAR) - s.get(Calendar.YEAR) > 0) {
				n = (e.get(Calendar.YEAR) - s.get(Calendar.YEAR)) * 12 + month;
			}
		} else if (month < 0) {
			n = (e.get(Calendar.YEAR) - s.get(Calendar.YEAR)) * 12 + month;
		}

		return n;
	}

	/**
	 * 获取上周，上月，上年本日期
	 * 
	 * @param date
	 * @param type
	 *            0：周； 1：月 ；2 ：年
	 * @return
	 */
	public static Date getBeforeDate(Date date, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type == 0) {
			cal.add(Calendar.DAY_OF_YEAR, -7);
		} else if (type == 1) {
			cal.add(Calendar.MONTH, -1);
		} else {
			cal.add(Calendar.YEAR, -1);
		}
		date = cal.getTime();
		return date;
	}

	/**
	 * 获取本周 周一、周日日期
	 * 
	 * @param date
	 * @param type
	 *            0：周一 1：周日
	 * @return
	 */
	public static Date getWeekDate(Date date, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1; // 获取周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
		Date result = null;
		if (type == 0) {
			calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
			result = calendar.getTime();
		} else {
			calendar.add(Calendar.DAY_OF_WEEK, 8 - current); // 开始+6，获取周结束日期
			result = calendar.getTime();
		}
		return result;
	}

	/**
	 * 获取本月第一天，最后一天
	 * 
	 * @param date
	 * @param type
	 *            0:第一天 1：最后一天
	 * @return
	 */
	public static Date getMonthDate(Date date, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (type == 0) {
			calendar.set(Calendar.DATE, 1);
		} else {
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
		}
		return calendar.getTime();
	}

	/**
	 * 获取本年第一天，最后一天
	 * 
	 * @param date
	 * @param type
	 *            0:第一天 1：最后一天
	 * @return
	 */
	public static Date getYearDate(Date date, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (type == 0) {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
		} else {
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DATE, 31);
		}
		return calendar.getTime();
	}

	public static Date plusOrMinusDate(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);
		return calendar.getTime();
	}

	public static Date plusOrMinusMonth(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}

	public static Date plusOrMinusYear(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, i);
		return calendar.getTime();
	}

	public static Date plusOrMinusSecond(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, i);
		return calendar.getTime();
	}
	
	/**
	 * 取本周7天的第一天
	 */
	public static String getNowWeekBegin(Date date) {
		Calendar cd = Calendar.getInstance();
		if (date != null)
			cd.setTime(date);
		cd.set(Calendar.DAY_OF_WEEK, 2);
		return DateUtil.date2String(cd.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 取本周7天最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getNowWeekEnd(Date date) {
		Calendar cd = Calendar.getInstance();
		if (date != null)
			cd.setTime(date);
		cd.set(Calendar.DAY_OF_WEEK, 1);
		return DateUtil.date2String(beOrAfDate(cd.getTime(), Calendar.DAY_OF_YEAR, 7), "yyyy-MM-dd");
	}

	/**
	 * 日期比较，如果未指定日期，则返回-1
	 * 
	 * @param first
	 * @param last
	 * @return
	 */

	public static int compareDate(Date first, Date last) {
		if (first == null || last == null)
			return -1;
		Calendar fcal = Calendar.getInstance();
		Calendar lcal = Calendar.getInstance();
		fcal.setTime(first);
		lcal.setTime(last);
		return fcal.compareTo(lcal);
	}

	/**
	 * 获取指定日期指定秒数之前的日期
	 * 
	 * @param date
	 *            当前日期
	 * @param second
	 *            需要减去的秒数
	 * @return 返回减去秒数后的日期
	 */
	public static Date getBeforeDateBySecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - second);
		return calendar.getTime();
	}

	/**
	 * 判断日期是否为公元2000年后
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isAfterYear2000(Date date) {
		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int era = calendar.get(Calendar.ERA);
		if (era == java.util.GregorianCalendar.BC)
			return false;
		int year = calendar.get(Calendar.YEAR);
		if (year > 2000)
			return true;
		return false;
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return
	 */
	public static Calendar getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal;
	}
	
	/**
	 * 获取上一天日期
	 * 
	 * @return
	 */
	public static Date getLastday(Date date) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.DATE, -1);  //设置为前一天		
		String s_PeroidDay = DateUtil.date2String(calendar.getTime(),"yyyy-MM-dd");
		Date peroidDay_time = DateUtil.string2Date(s_PeroidDay + " 00:00:00","yyyy-MM-dd HH:mm:ss");
		return peroidDay_time;   //得到前一天的时间;
	}
	

	/**
	 * 判断是否相同日期
	 * 
	 * @param first
	 * @param standard
	 *            标准
	 * @return 0:同一天 -1:小于标准日期 1：大于标准日期
	 */
	public static int isSameDate(Date first, Date standard) {
		long span = first.getTime() - standard.getTime();
		if (span < 0) {
			return -1;
		}
		if (span < 24 * 60 * 60000) {
			return 0;
		} else {
			return 1;
		}
	}

	public static boolean isSameDay(Date first, Date standard) {
		return DateUtils.isSameDay(first, standard);
	}

	/**
	 * 取得两个日期之间的所有日期
	 * 
	 * @param startD
	 *            开始日期
	 * @param endD
	 *            结束日期
	 * @param format
	 *            返回日期的格式
	 * @return List
	 */
	public static List<String> getAllDaysBetween2Date2(String startD, String endD, String format) {
		List<String> list = new ArrayList<String>();
		Date dateTemp = string2Date(startD, "yyyy-MM-dd");
		Date dateTemp2 = string2Date(endD, "yyyy-MM-dd");
		while (isSameDate(dateTemp, dateTemp2) < 1) {
			list.add(date2String(dateTemp, format));
			dateTemp = DateUtil.string2Date(DateUtil.beOrAfDate2String(DateUtil.date2String(dateTemp, "yyyy-MM-dd"), java.util.Calendar.DAY_OF_MONTH, 1, "yyyy-MM-dd"), "yyyy-MM-dd");
		}
		return list;
	}

	/**
	 * 格林威治时间转换为当前时间
	 * @param date
	 * @return
	 */
	public static Date convernGMTToCST(Date date){
		int offset = Calendar.getInstance().getTimeZone().getRawOffset();
		long mTime = date.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(mTime + offset));
		return c.getTime();
	}
	public static int daysBetween(Date smdate, Date bdate) throws ParseException
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        final Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        final long time2 = cal.getTimeInMillis();
        final long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
	
	// 求时间的差值
	public static long checkDate(Date newPeroidTime, Date oldPeroid_time) {
			try {
				long newtime = newPeroidTime.getTime();
				long oldtime = oldPeroid_time.getTime();
				long diff = newtime - oldtime;
				return diff;				
			} catch (Exception e) {				
				System.out.println("时间格式不对:" + newPeroidTime + "-"
						+ oldPeroid_time);	
				return -1;
			}			

		}
	
	 
    public static Long endDateHandle(Date date){
        Calendar cal = Calendar.getInstance();
        if(date!=null){
            cal.setTime(date);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            Long ll=cal.getTime().getTime()-1;
            return ll;
        }
        return null;
    }
	
	public static void main(String[] args) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse("3088-05-01");
	    System.out.println(sdf.format(parse));
    }
	
}