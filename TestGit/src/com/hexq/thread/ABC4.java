package com.hexq.thread;

/**
 * @author hexq
 * @see http://blog.csdn.net/liu251/article/details/6227763
 * 在jdk1.5中线程的协作是通过Lock/Condition及Samephore/CyclicBarriar/CountLatchDown实现的
 * 
 */
public class ABC4 {

	private int index = 1;//通过index来确定A B C的输出
	private Object obj = new Object();//同步对象
	
	private static int len = 3;
	
	public static void main(String[] args) {
		//内部类，线程执行通过jdk1.4
		ABC4 abc = new ABC4();
		
		//声明3个runnable类
		ThreadA ta = abc.new ThreadA();
		ThreadB tb = abc.new ThreadB();
		ThreadC tc = abc.new ThreadC();
		for (int i = 0; i < 10; i++) {
			new Thread(ta).start();
			new Thread(tb).start();
			new Thread(tc).start();
		}
	}

	class ThreadA implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 1) {
						System.out.print("A");
						index++;
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
			}
		}
		
	}
	class ThreadB implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 2) {
						System.out.print("B");
						index++;
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
			}
		}
		
	}
	class ThreadC implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 0) {
						System.out.print("C");
						index++;
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
			}
		}
		
	}
}