package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.4ucode.com/Study/Topic/539235
 * 
 */
public class ThreadABC implements Runnable {
	public static final int RUN_TOTAL_COUNT = 10; // 打印ABC的次数
	public static volatile boolean arun = true; // 开始打印A
	public static volatile boolean brun = false;
	public static volatile boolean crun = false;
	public static final byte[] LOCK = new byte[0]; // 互斥器

	public void run() {
		new Thread(new PrintB()).start();
		new Thread(new PrintC()).start();
		new Thread(new PrintA()).start();
	}

	private static class PrintA implements Runnable {
		private int runCount = 0;

		public void run() {
			synchronized (LOCK) {
				while (runCount < RUN_TOTAL_COUNT)
					if (arun) {
						LOCK.notifyAll();
						System.out.print("A");
						runCount++;
						arun = false;
						brun = true;
						crun = false;
					} else {
						try {
							LOCK.wait();
						} catch (InterruptedException ie) {
							System.out.println("PrintA InterruptedException occured...");
						}
					}
			} // end syn
		}
	}

	private static class PrintB implements Runnable {
		private int runCount = 0;

		public void run() {
			synchronized (LOCK) {
				while (runCount < RUN_TOTAL_COUNT)
					if (brun) {
						LOCK.notifyAll();
						System.out.print("B");
						runCount++;
						arun = false;
						brun = false;
						crun = true;
					} else {
						try {
							LOCK.wait();
						} catch (InterruptedException ie) {
							System.out.println("PrintB InterruptedException occured...");
						}
					}
			} // end syn
		}
	}

	private static class PrintC implements Runnable {
		private int runCount = 0;

		public void run() {
			synchronized (LOCK) {
				while (runCount < RUN_TOTAL_COUNT)
					if (crun) {
						LOCK.notifyAll();
						System.out.print("C");
						runCount++;
						arun = true;
						brun = false;
						crun = false;
					} else {
						try {
							LOCK.wait();
						} catch (InterruptedException ie) {
							System.out.println("PrintC InterruptedException occured...");
						}
					}
			} // end syn
		}
	}

	// test in main
	public static void main(String[] args) {
		new Thread(new ThreadABC()).start();
	}
}
