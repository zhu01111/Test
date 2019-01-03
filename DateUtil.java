package com.huiyou.yigao.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhu
 * @Description: 日期工具类
 * @date 2013-7-5
 */
public class DateUtil
{

	/** yyyy-MM-dd HH:mm:ss */
	public static final String	PATNER_DEFAULT			= "yyyy-MM-dd HH:mm:ss";
	/** HH:mm:ss */
	public static final String	PATNER_ACTIVITY			= "HH:mm:ss";
	/** yyyyMMddHHmmss */
	public static final String	PATNER_NO_MILLSECOND	= "yyyyMMddHHmmss";
	/** yyyyMMddHHmmssSSS */
	public static final String	PATNER_FULL				= "yyyyMMddHHmmssSSS";
	/** yyyy-MM-dd */
	public static final String	PATNER_ISO9985			= "yyyy-MM-dd";
	/** yyyyMMdd */
	public static final String	PATNER_ISO9985_2		= "yyyyMMdd";
	/** yyyy-MM-dd HH:mm */
	public static final String	PATNER_DEFAULT_NOMIN	= "yyyy-MM-dd HH:mm";

	/**
	 * 获取系统的当前时间
	 * 
	 * @return Date
	 */
	public static Date currentDate()
	{
		return new Date();
	}

	/**
	 * @param dates
	 * @return
	 */
	public static int getFiledValue( int field, Date... dates )
	{
		Calendar calendar = Calendar.getInstance();
		if( dates != null && dates.length > 0 )
		{
			calendar.setTime( dates[ 0 ] );
		}
		else
		{
			calendar.setTime( new Date() );
		}
		return calendar.get( field );
	}

	// 获得格式化后的字符串
	public static Date parse( String datestring )
	{
		return parse( datestring, PATNER_ISO9985 );
	}

	public static Date stringToDate( String datestring )
	{
		return parse( datestring, PATNER_DEFAULT );
	}

	public static Date parse( String date, String format )
	{
		if( StringUtils.isBlank( date ) ) { return null; }
		DateFormat parser = new SimpleDateFormat( format );
		try
		{
			return parser.parse( date );
		}
		catch( ParseException e )
		{
			throw new IllegalArgumentException( "格式化日期格式的时候出错:" );
		}
	}

	public static String toString( Date now )
	{
		SimpleDateFormat formatTime = new SimpleDateFormat( PATNER_DEFAULT );
		if( now == null )
			return "";
		return formatTime.format( now );
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String format( Date date, String formatter )
	{
		SimpleDateFormat formatTime = new SimpleDateFormat( formatter );
		return formatTime.format( date );
	}
	
	/**
	 * 清除小时和分钟
	 * 
	 * @param date
	 * @return
	 */
	public static Date clearHourMinute( Date date )
	{
		if( date == null )
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.set( Calendar.HOUR, 0 );
		calendar.set( Calendar.MINUTE, 0 );
		calendar.set( Calendar.MILLISECOND, 0 );
		return calendar.getTime();
	}

	public static String printNow()
	{
		return toString( currentDate() );
	}

	/**
	 * 减去一分钟
	 * 
	 * @param date
	 * @return
	 */
	public static Date cutOneMin( Date date )
	{
		if( date == null )
			return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.set( Calendar.MINUTE, calendar.get( Calendar.MINUTE ) - 1 );
		return calendar.getTime();
	}

	/**
	 * 加天数
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays( Date date, int days )
	{
		if( date == null )
			return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.set( Calendar.DAY_OF_MONTH, calendar.get( Calendar.DAY_OF_MONTH ) + days );
		return calendar.getTime();
	}
	
	public static Date addMinutes( Date date, int minutes )
	{
		if( date == null )
			return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	/**
	 * 控制台简单输出日志
	 * 
	 * @param msg
	 */
	public static void spLog( String msg )
	{
		System.err.println( msg );
	}

	public static String timestampe()
	{
		return format( DateUtil.currentDate(), PATNER_FULL );
	}

	/**
	 * 比较两个时间
	 * 
	 * @param loginTime
	 * @param currentDate
	 * @return
	 */
	public static long compareTwoDates( Date compareDate, Date baseDate )
	{

		if( null == compareDate || null == baseDate ) { return 0l; }

		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime( DateUtil.parse( DateUtil.format( compareDate, PATNER_ISO9985 ), PATNER_ISO9985 ) );
		Calendar baseCal = Calendar.getInstance();
		baseCal.setTime( DateUtil.parse( DateUtil.format( baseDate, PATNER_ISO9985 ), PATNER_ISO9985 ) );

		return ( compareCal.getTimeInMillis() - baseCal.getTimeInMillis() ) / ( 1000 * 60 * 60 * 24 );
	}

	public static long compareTwoMss(Date compareDate, Date baseDate){
		if( null == compareDate || null == baseDate ) { return 0l; }

		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime( DateUtil.parse( DateUtil.format( compareDate, PATNER_DEFAULT ), PATNER_DEFAULT ) );
		Calendar baseCal = Calendar.getInstance();
		baseCal.setTime( DateUtil.parse( DateUtil.format( baseDate, PATNER_DEFAULT ), PATNER_DEFAULT ) );

		return ( compareCal.getTimeInMillis() - baseCal.getTimeInMillis() );
	}
	
	public static void main( String[] args )
	{
		// System.err.println( DateUtil.format( addDays( new Date(), 3 ),
		// DateUtil.PATNER_ISO9985 ));
		System.err.println(compareTwoMss(currentDate(),cutOneMin(currentDate())));
	}
	
	public static String compareTwoTime(Date startTime,Date endTime){
		
		String res = "";
		long time = endTime.getTime()-startTime.getTime();
		
		//天
		int day = (int) (time/(24*60*60*1000));
		if (day>0) {
			res+=(day+"天");
		}
		
		//小时
		time = time%(24*60*60*1000);
		int hour = (int) (time/(60*60*1000));
		if (StringUtils.isBlank(res)) {
			if (hour>0) {
				res+=(hour+"小时");
			}
		}else {
			if (hour>0) {
				res+=(hour+"小时");
			}
		}
		
		//分钟
		time = time%(60*60*1000);
		int min = (int) (time/(60*1000));
		if (StringUtils.isBlank(res)) {
			if (min>0) {
				res+=(min+"分钟");
			}
		}else {
			if (min>0) {
				res+=(min+"分钟");
			}
		}
		
		//秒
		time = time%(60*1000);
		int sec = (int) (time/1000);
		res+=(sec+"秒");
		return res;
	}
	
	
	
}
