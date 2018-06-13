package com.sky.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceTestStats {
	private static final Logger logger=LoggerFactory.getLogger(PerformanceTestStats.class);
	
	private static int total;
	private static int success;
	private static int fail;
	private static long startTime;
	private static long stopTime;

	public static synchronized void addSuccess(int count) {
		success += count;
		total += count;
	}

	public static synchronized void addFail(int count) {
		fail += count;
		total += count;
	}

	public static synchronized void start() {
		startTime = System.nanoTime();
	}

	public static synchronized void stop() {
		stopTime = System.nanoTime();
		showStats();
	}
	
	private static void showStats() {
		long elapsedTime = stopTime - startTime;
		logger.info("\n total={}, success+fail={}\n success={}, fail={}\n elapsed time = {} ms, {} ns",
				total, success+fail, success, fail, elapsedTime/1000000.0, elapsedTime);
	}
}
