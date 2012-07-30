package org.hexq.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * java concurrent 探秘
 * 
 * http://www.cnblogs.com/aurawing/articles/1887056.html
 * 
 * @author hxq8176
 * 
 */
public class MyBlockingQueue extends Thread {

	public static BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(3);

	private int index;

	public MyBlockingQueue(int i) {
		this.index = i;
	}

	public void run() {
		try {
			//向队列中插入一个元素
//			queue.offer(this.index);
			queue.put(this.index);
			System.out.println("{" + this.index + "} in queue!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
//		ExecutorService service = Executors.newCachedThreadPool();
		ExecutorService service = Executors.newFixedThreadPool(10);
		
		//生成者
		for (int i = 0; i < 10; i++) {
			service.submit(new MyBlockingQueue(i));
		}
		
		//消费者
		Thread thread = new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep((int) (Math.random() * 1000));
						if (MyBlockingQueue.queue.isEmpty())
							break;
						Integer idx = MyBlockingQueue.queue.take();
						System.out.println(idx + " has take!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		service.submit(thread);
		service.shutdown();
	}
}
