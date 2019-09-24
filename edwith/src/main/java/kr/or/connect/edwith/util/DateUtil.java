package kr.or.connect.edwith.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA);
		Date now = new Date();
		System.out.println("##################"+format.format(now));
		return format.format(now);
		
	}
}
