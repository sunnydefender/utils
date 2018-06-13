package com.sky.framework.common.id.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.sky.framework.common.id.IdUtils;

public class IdTest {
	public void sessionId() {
		AtomicLong sessionId = new AtomicLong(0);
		System.out.println("start: sessionId = "+sessionId.get());
		int count = 100000000;
		long startTime = System.nanoTime();
		for (int i=0; i< count; i++) {
			sessionId.getAndIncrement();
		}
		System.out.println("stop: sessionId = "+sessionId.get());

		long stopTime = System.nanoTime();
		System.out.printf("counter : %d, elapsed time : %d ns, %.2f ms\n", count, stopTime-startTime, (stopTime-startTime)/1000000.0);
	}
	
	public void flowId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		AtomicInteger flowId = new AtomicInteger(0);
		System.out.println("start: sessionID = "+flowId.get());
		int count = 500000;
		long startTime = System.nanoTime();
		for (int i=0; i< count; i++) {
			Date now=new Date();
			String date=sdf.format(now);
//			String date=String.format("%016X", System.currentTimeMillis());
//			String flowID = String.format("%s%s%s%08X", "BUY", "000", date, sessionID.getAndIncrement());
			String flowID = "BUY"+"000"+date+String.format("%08X", flowId.getAndIncrement());
//			String flowID = String.format("%s%s%08X", "BUY", "000", sessionID.getAndIncrement());
//			String flowID = "BUY"+"000"+String.format("%016X%08X", System.currentTimeMillis(), sessionID.getAndIncrement());

			if (0==i || i==count-1) {
				System.out.println(flowID);
			}
		}
		System.out.println("stop: sessionID = "+flowId.get());

		long stopTime = System.nanoTime();
		System.out.printf("count : %d, elapsed time : %d ns, %.2f ms\n", count, stopTime-startTime, (stopTime-startTime)/1000000.0);
		System.out.printf("avg time : %f ms\n", (stopTime-startTime)/(count*1000000.0));
	}
	
	public void uid() {
		for (int i=0; i< 50; i++) {
			System.out.println(IdUtils.ID_UTILS.createUid());
		}
	}
}
