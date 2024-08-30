package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
		String timestamp = dateFormat.format(new Date()); 
		return timestamp;
	}
}
