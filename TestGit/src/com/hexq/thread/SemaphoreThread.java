package com.hexq.thread;

import java.util.concurrent.Semaphore;

/**
 * @author hexq
 * @see http://www.huomo.cn/developer/article-bdab.html
 * 
 */
public class SemaphoreThread {

	private static int len = 10;
	
	static class T extends Thread {
		Semaphore current;
		Semaphore next;

		public T(String str, Semaphore current, Semaphore next) {
			super(str);
			this.current = current;
			this.next = next;
		}

		public void run() {
			for (int i = 0; i < len; i++) {
				try {
					current.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(this.getName());
				next.release();
			}

		}
	}

	public static void main(String[] args) {
		Semaphore aSema = new Semaphore(1);
		Semaphore bSema = new Semaphore(0);
		Semaphore cSema = new Semaphore(0);
		T a = new T("A", aSema, bSema);
		T b = new T("B", bSema, cSema);
		T c = new T("C ", cSema, aSema);
		a.start();
		b.start();
		c.start();
	}
}
