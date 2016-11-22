package jp.ne.ravi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class TimeUtil {

	public String getNowAtyyyyMMddHHmmss() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(cal.getTime());
	}
}
