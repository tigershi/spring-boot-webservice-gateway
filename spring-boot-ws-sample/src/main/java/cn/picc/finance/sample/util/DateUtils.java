package cn.picc.finance.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期操作类
 * 
 * @author panhong
 * @Date 2015年7月8日 下午5:33:43
 */
public class DateUtils {

	private static final Log logger = LogFactory.getLog(DateUtils.class);

	private static final String LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SHORT_FORMAT = "yyyy-MM-dd";

	/**
	 * 禁止外部实例化
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:34:17
	 */
	private DateUtils() {
	}

	/**
	 * 根据给定日期格式获取当前日期
	 * @param format
	 * @return
	 */
	public static String formatString(String format) {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(now);
	}

	/**
	 * 给定时间字符串转换为 yyyy-MM-dd HH:mm:ss Date类型
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:40:36
	 * @param dateStr
	 * @return
	 */
	public static Date convertToLongDate(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat(LONG_FORMAT);
		Date longDate = null;
		try {
			longDate = df.parse(dateStr);
		} catch (ParseException e) {
			logger.error("时间转换错误", e);
		}
		return longDate;
	}

	/**
	 * 给定时间字符串转换为 yyyy-MM-dd Date类型
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:39:46
	 * @param dateStr
	 * @return
	 */
	public static Date convertToShortDate(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat(SHORT_FORMAT);
		Date shortDate = null;
		try {
			shortDate = df.parse(dateStr);
		} catch (ParseException e) {
			logger.error("时间转换错误", e);
		}
		return shortDate;
	}

	/**
	 * 给定时间格式和时间字符串转换为Date类型
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:38:31
	 * @param formatStr
	 * @param dateStr
	 * @return
	 */
	public static Date convertToShortDate(String formatStr, String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date formatDate = null;
		try {
			formatDate = df.parse(dateStr);
		} catch (ParseException e) {
			logger.error("时间转换错误", e);
		}
		return formatDate;
	}

	/**
	 * 给定时间转换为 yyyy-MM-dd 格式
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:38:05
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(SHORT_FORMAT);
		String dateStr = "";
		try {
			dateStr = df.format(date);
		} catch (Exception e) {
			logger.error("时间转换错误", e);
		}
		return dateStr;
	}

	/**
	 * 给定时间转换为 yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:36:53
	 * @param date
	 * @return
	 */
	public static String dateToLongString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(LONG_FORMAT);
		String dateStr = "";
		try {
			dateStr = df.format(date);
		} catch (Exception e) {
			logger.error("时间转换错误", e);
		}
		return dateStr;
	}

	/**
	 * 把时间转换为指定格式
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:36:01
	 * @param formatStr
	 * @param date
	 * @return
	 */
	public static String dateToString(String formatStr, Date date) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		String dateStr = "";
		try {
			dateStr = df.format(date);
		} catch (Exception e) {
			logger.error("时间转换错误", e);
		}
		return dateStr;
	}

	/**
	 * 获得指定日期所在月的第一天
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:34:46
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获得指定日期所在月的最后一天
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:34:58
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 获得指定日期所在月的第一天
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:35:18
	 * @param cal
	 * @return
	 */
	public static Date getMonthFirstDay(Calendar cal) {
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获得指定日期所在月的最后一天
	 * 
	 * @author panhong
	 * @Date 2015年7月8日 下午5:35:32
	 * @param cal
	 * @return
	 */
	public static Date getMonthLastDay(Calendar cal) {
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	public static Integer getCurrentYearOnly(Date data){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		String c=sdf.format(data);
		int IntYear = Integer.parseInt(c);
		return IntYear; 
	}

    /**
     * 获取当前 日期的 前几天
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获取当前 日期的 后几天 日期
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * 获取当前 日期的 前几天 日期 返回字符串
     * @param d
     * @param day
     * @return
     */
    public static String getDateBeforeToString(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(now.getTime());
    }

    /**
     * 获取当前 日期的 后几天 日期 返回字符串
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfterToString(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(now.getTime());
    }
}
