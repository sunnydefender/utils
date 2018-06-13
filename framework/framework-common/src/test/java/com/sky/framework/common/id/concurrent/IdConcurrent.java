package com.sky.framework.common.id.concurrent;

import java.util.HashMap;
import java.util.Map;

import com.sky.framework.common.id.IdUtils;

public class IdConcurrent implements Runnable {
	private static Map<String, Long> mapUid=new HashMap<>();
	private static long count = 0;
	
	public IdConcurrent() {
	}
	
	public static void showStat() {
		System.out.printf("thread id: %s, uidSize=%d, count=%d\n", Thread.currentThread().getName(), mapUid.size(), count);
	}

	@Override
	public void run() {
		int i=10;
		while (i<100 * 10000) {
			i++;
			long id = IdUtils.getInstance().createUid();
			checkId(id);
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//			}
		}

		System.out.printf("thread id: %s, uidSize=%d, count=%d\n", Thread.currentThread().getName(), mapUid.size(), count);
	}

	private static synchronized void checkId(long id) {
		count++;
//		System.out.printf("id=%d\n", id);
		Long oldId=mapUid.get(String.valueOf(id));
		if (null != oldId) {
			System.out.printf("duplicate id, id=%d, oldId=%d\n", id, oldId);
			System.exit(-1);
		}
		mapUid.put(String.valueOf(id), id);
		
	}
}
