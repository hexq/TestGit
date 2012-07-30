package com.hexq.thread;

/**
 * 
 * @author hexq
 * @see http://beyond99.blog.51cto.com/1469451/404900
 *
 */
public class PrintThread {

	public static void main(String[] args) {
		new Thread(new OrderThread(0,"A")).start();
		new Thread(new OrderThread(1,"B")).start();
		new Thread(new OrderThread(2,"C")).start();
	}
}
 
class OrderThread implements Runnable {
	//锁对象
	private static Object o = new Object();
	//用来记录是哪个线程进入运行
	private static int count = 0;
	//每个线程的标识，名称
	private String name;
	//用来控制是线程运行的标识
	private int id;
	//每个线程运行的次数
	private int num = 0;
 
	public OrderThread(int id, String name) {
		this.id = id;
		this.name = name;
	}
 
	public void run() {
		synchronized (o) {
			while (num < 10) {
				if(count % 3 == id){
					System.out.print(name);
					++ count;
					++ num;
					o.notifyAll();
				}
				else{
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
