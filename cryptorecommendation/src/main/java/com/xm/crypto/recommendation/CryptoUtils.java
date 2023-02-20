package com.xm.crypto.recommendation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public class CryptoUtils {
	
	public static Date transformDate(String timestampStr) {
		return transformDate(timestampStr, null);
	}
	
	public static Date transformDate(String timestampStr, String dateFormat) {
		Timestamp time = new Timestamp(Long.parseLong(StringUtils.isEmpty(timestampStr) ? StaticVariables.DEFAULT_DATE : timestampStr));
		Date date = new Date(time.getTime());
		if (StringUtils.isEmpty(dateFormat)) {
			return date;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try {
				return new SimpleDateFormat(dateFormat).parse(sdf.format(date));
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
