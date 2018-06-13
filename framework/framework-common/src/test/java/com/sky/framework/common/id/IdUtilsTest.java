package com.sky.framework.common.id;

import java.util.Date;

import com.sky.framework.common.utils.DateUtil;
import org.junit.Test;

public class IdUtilsTest {
	@Test
	public void time() {
		System.out.println(System.currentTimeMillis());
		Date date= DateUtil.parseString("9999-12-31 23:59:59.999", "yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(date.getTime());
		date= DateUtil.parseString("9999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		System.out.println(date.getTime());
		date= DateUtil.parseString("9999-12-01 23:59:58.555", "yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(date.getTime());
		
//		253402271999999 9999-12-31 23:59:59.999
		for (int i=0;i<=999;i++) {
			date.setTime((253402271999000L+i)&0x8FFFFFFFFFFFL);
			System.out.println(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS"));
		}

		System.out.println(IdUtils.getInstance().createUid());

		long id = IdUtils.getInstance().createUid();
		date.setTime(id&0xFFFFFFFFFFFF0000L >> 8);
		System.out.println(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS"));

	}
}
