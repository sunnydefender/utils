package com.sky.framework.common.id.concurrent;

import java.util.ArrayList;

public class IdConcurrentTest {
	public static void main(String[] args) {
		test();
	}

	public static void test() {
		int threadNum = 10;
		ArrayList<Thread> threads = new ArrayList<>(threadNum);
		for (int i=0; i<threadNum; i++) {
			Thread t = new Thread(new IdConcurrent());
			threads.add(t);
			t.start();
		}
		
		for (int i=0; i<threadNum; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("wait thread error");
				System.exit(-1);
			}
		}
		IdConcurrent.showStat();
	}
}
