package com.hexq.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyTest1 {
	
	private int index = 1;
	//private Object obj = new Object();
	
	private Lock lock = new ReentrantLock();
	private Condition con = lock.newCondition();
	
	public static void main(String[] args) {
		MyTest1 test = new MyTest1();
		
		ThreadA ta = test.new ThreadA();
		ThreadB tb = test.new ThreadB();
		ThreadC tc = test.new ThreadC();
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			/*new Thread(ta).start();
			new Thread(tb).start();
			new Thread(tc).start();*/
			executor.execute(ta);
			executor.execute(tb);
			executor.execute(tc);
		}
		
		executor.shutdown();
	}
	
	class ThreadA implements Runnable{

		@Override
		public void run() {
			/*synchronized (obj) {
				while (true) {
					if(index % 3 == 1){
						System.out.print("A");
						index ++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}*/
			lock.lock();
			try {
				while (true) {
					if(index % 3 == 1){
						System.out.print("A");
						index ++;
						con.signalAll();
						break;
					} else {
						try {
							con.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			} finally {
				lock.unlock();
			}
		}
	}

	
	class ThreadB implements Runnable{

		@Override
		public void run() {
			/*synchronized (obj) {
				while (true) {
					if(index % 3 == 2){
						System.out.print("B");
						index ++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}*/
			lock.lock();
			try {
				while (true) {
					if(index % 3 == 2){
						System.out.print("B");
						index ++;
						con.signalAll();
						break;
					} else {
						try {
							con.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			} finally {
				lock.unlock();
			}
		}
	}

	
	class ThreadC implements Runnable{

		@Override
		public void run() {
			/*synchronized (obj) {
				while (true) {
					if(index % 3 == 0){
						System.out.print("C");
						index ++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}*/
			lock.lock();
			try {
				while (true) {
					if(index % 3 == 0){
						System.out.print("C");
						index ++;
						con.signalAll();
						break;
					} else {
						try {
							con.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			} finally {
				lock.unlock();
			}
		}
	}

}
