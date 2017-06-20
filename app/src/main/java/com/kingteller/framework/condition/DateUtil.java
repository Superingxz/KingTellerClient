package com.kingteller.framework.condition;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM = "yyyy-MM";
	public static final String yyyy_MM_ddHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHH24mmss = "yyyyMMddHHmmss";
	public static final String yyyyMMdd = "yyyyMMdd";

	/**
	 * 将日期转换为指定格式的字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				if (format == null || format.length() == 0) {
					SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
					result = sdf.format(date);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					result = sdf.format(date);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 将日期字符串按照指定格式转换为Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String str, String format) {
		if (str != null) {
			str.trim();
			// 如果字符串和格式要求字符串 长度不一致
			if (str.length() != format.length()) {
				if (format.equals(yyyy_MM_ddHHMMSS)) {
					if (str.length() < format.length()) {
						if (str.length() == 10) {
							str += " 00:00:00";// 日期为 yyyy-MM-dd
						} else if (str.length() == 7) {
							str += "-01 00:00:00";// 日期为 yyyy-MM
						} else {
							str += " 00:00:00";
						}
					} else {
						str = str.substring(0, 19);
					}
				}
			}
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(str);
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 将日期字符串按照指定格式转换为 长整数 time
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long stringToTime(String str, String format) {
		return stringToDate(str, format).getTime();
	}

	/**
	 * 将日期字符串按照指定格式转换为 长整数 time
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long stringToTime(String str) {
		return stringToDate(str, yyyy_MM_ddHHMMSS).getTime();
	}

	/**
	 * 把日期格式化成当天的最后一个时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWithEndTime(Date date) {
		if (date == null)
			return null;
		String dateStr = dateToString(date, yyyy_MM_dd);
		dateStr = dateStr + " 23:59:59";
		return stringToDate(dateStr, yyyy_MM_ddHHMMSS);
	}

	/**
	 * 把日期格式化成当天的初始一个时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWithStartTime(Date date) {
		if (date == null)
			return null;
		String dateStr = dateToString(date, yyyy_MM_dd);
		dateStr = dateStr + " 00:00:00";

		return stringToDate(dateStr, yyyy_MM_ddHHMMSS);
	}

	/**
	 * 把日期样式的字符串 转成 另一种日期样式 如 20110408 转成 2011/04/08
	 * formatDate("20110408","yyyyMMdd","yyyy/MM/dd")
	 * 
	 * @param dateStr
	 * @param srcFormat
	 * @param destFormat
	 * @return
	 */
	public static String formatDate(String dateStr, String srcFormat,
			String destFormat) {
		return dateToString(stringToDate(dateStr, srcFormat), destFormat);
	}

	/**
	 * 取得当前正负N天对应的日期
	 * 
	 * @author tony
	 * @param dateStr
	 *            日期字符串
	 * @param before
	 *            int 正负N天
	 * @return
	 */
	public static Date getDateBeforeCurDate(int before) {
		Date date = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.DATE, rightNow.get(Calendar.DATE) + before);
		return rightNow.getTime();
	}

	/**
	 * 取得指定日期 正负N天对应的日期
	 * 
	 * @author tony
	 * @param Date
	 *            date 指定日期
	 * @param before
	 *            int 正负N天
	 * @return
	 */
	public static Date getDateBeforeSpecialDate(Date date, int before) {
		Calendar specialCal = Calendar.getInstance();
		specialCal.setTime(date);
		specialCal.set(Calendar.DATE, specialCal.get(Calendar.DATE) + before);
		return specialCal.getTime();
	}

	/**
	 * @see 根据日期对象获得相对应的长整型。精确到毫秒 返回0为出错
	 */
	public static long getTime(String dateStr) {
		long time = -1;
		String format = "yyyy-MM-dd HH:mm:ss";
		Date date = stringToDate(dateStr, format);
		if (date != null) {
			time = date.getTime();
		}
		return time;
	}

	/**
	 * 判断endDate是否在beginDate之后
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static final boolean before(Date beginDate, Date endDate) {
		if (endDate.after(beginDate)) {
			return true;
		}
		return false;

	}

	/**
	 * 检查一个日期是否在指定的日期范围内 (接口参数为Date类型)
	 * 
	 * @param date
	 *            The date to be test
	 * @param from
	 *            From range
	 * @param to
	 *            To range
	 * @return 如果测试日期在指定的日期范围内,则返回true,否则返回false
	 */
	public static final boolean isBetween(Date testDate, Date from, Date to) {
		return from.before(testDate) && to.after(testDate);
	}

	/**
	 * 获取两个时间之间的间隔，不同的type返回不同的间隔单位，
	 * d-表示相隔多少天，h-表示相隔多少个小时，"m"-表示相隔多少分钟,s-表示相隔多少秒
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static long getTimeBetween(Date beginTime, Date endTime, String type) {
		long begin = beginTime.getTime();// 开始时间毫秒数
		long end = endTime.getTime();// 结束时间毫秒数
		long dis = end - begin;
		long ret = 0;
		// 根据不同的传入类型计算间隔
		if ("d".equals(type) || type == null) {// 日
			ret = Math.round(dis / (24 * 60 * 60 * 1000));
		} else if ("h".equals(type)) {
			ret = Math.round(dis / (60 * 60 * 1000));
		} else if ("m".equals(type)) {
			ret = Math.round(dis / (60 * 1000));
		} else if ("s".equals(type)) {
			ret = Math.round(dis / 1000);
		}
		return ret;
	}

	/**
	 * 获得当月天数
	 * 
	 * @return ChenRu add 2012-7-10
	 */
	public static int getMonthHasDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 获得当天号数
	 * 
	 * @param date
	 *            ChenRu add 2012-7-10
	 */
	public static int getMonthCurDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当年
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获得当月
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前月的第一天 yyyy-mm-01
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthFirstDate(Date date) {
		int year = getYear(date);
		int month = getMonth(date);
		String m;
		if (month < 10) {
			m = "0" + month;
		} else {
			m = String.valueOf(month);
		}
		String returnDateStr = year + "-" + m + "-01";
		return returnDateStr;
	}

	/**
	 * 获得当前时间的上个月 yyyy-mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getPreMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if (month == 0) {
			year = year - 1;
			month = 12;
		}
		String m;
		if (month != 12 && month != 11 && month != 10) {
			m = "0" + month;
		} else {
			m = String.valueOf(month);
		}
		String ym = year + "-" + m;
		return ym;
	}

	/**
	 * 获得当前时间的上个月季度 flag 为 false 返回yyyy-mm-01; flag 为 true 返回yyyy-mm
	 * 
	 * @param date
	 * @param flag
	 * @return
	 */
	public static String getYjd(Date date, Boolean flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String m;
		// 默认显示上个季度
		if (month == 1 || month == 2 || month == 3) {
			m = "12";
			year--;
		} else if (month == 4 || month == 5 || month == 6) {
			m = "03";
		} else if (month == 7 || month == 8 || month == 9) {
			m = "06";
		} else {
			m = "09";
		}
		String jd = year + "-" + m;
		if (!flag) {
			jd += "-01";
		}
		return jd;
	}

	/**
	 * 获得当前绩效考核的季度 从季度最后一个月开始到下季度前两个月，显示为本季度 flag 为 false 返回yyyy-mm-01 ; flag 为
	 * true 返回yyyy-mm
	 * 
	 * @param date
	 * @param flag
	 * @return
	 */
	public static String getKPIQuarter(Date date, Boolean flag) {
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;
		if (month < 3) {
			year = year - 1;
		}
		if (month >= 3 && month <= 5) {
			month = 3;
		} else if (month >= 6 && month <= 8) {
			month = 6;
		} else if (month >= 9 && month <= 11) {
			month = 9;
		} else if (month == 12 || month <= 2) {
			month = 12;
		}
		if (!flag) {
			return year + "-" + month + "-01";
		} else {
			return year + "-" + month;
		}
	}

	/**
	 * 求距 date 有count天的日期
	 * 
	 * @param count
	 *            天数
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getDayByCount(int count, Date date) {
		Date d = new Date();
		Long a = (d.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
		Calendar c = Calendar.getInstance();
		if (a != 0) {
			int cnt = count - Integer.parseInt(String.valueOf(a));
			c.add(Calendar.DAY_OF_MONTH, +cnt);
		}
		return c.getTime();
	}

	/**
	 * 判断是星期几(从星期一开始)
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			day = 7;
		} else {
			day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return day;
	}

	/**
	 * 获取下一秒钟的时间对象
	 */
	public static Date getNextSecondTime() {
		Date date = new Date();
		date.setTime(date.getTime() + (1 * 1000));
		return date;
	}

	public static void main(String[] args) {
		/**
		 * DateFieldHandler.boolDate() query
		 * CodeStr=6.28000000000000024868995751603506505489349365234375
		 * DateFieldHandler.boolDate() query CodeStr=41088
		 * DateFieldHandler.boolDate() query CodeStr=41087
		 * DateFieldHandler.boolDate() query CodeStr=41059
		 * DateFieldHandler.boolDate() query CodeStr=41058
		 * 
		 */

		// String CodeStr= "2010-9-15调入";
		// Date testDate =DateUtil.stringToDate(CodeStr, DateUtil.yyyy_MM_dd);
		// System.out.println("DateUtil.main() "+testDate.toLocaleString());
		System.out.println("["
				+ DateUtil.dateToString(new Date(), DateUtil.yyyy_MM_ddHHMMSS)
				+ "]" + "获取年份、月份  " + DateUtil.getYear(new Date()) + "  "
				+ DateUtil.getMonth(new Date()));
	}

}
