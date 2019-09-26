package kr.or.connect.edwith.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
	
	/*
	 * 현재 시간을 문자열로 구하기 위한 함수
	 * */
	public static String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA);
		Date now = new Date();
		System.out.println("##################"+format.format(now));
		return format.format(now);
		
	}
}
