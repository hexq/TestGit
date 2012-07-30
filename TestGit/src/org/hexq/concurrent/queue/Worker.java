package org.hexq.concurrent.queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/** 生产者 
  * @author: yanxuxin 
  * @date: 2010-1-25 
  */
class Worker implements Runnable { 
	/** 假想的产品*/ 
	private static final String PRODUCT = "Thinkpad"; 
	private final LinkedBlockingQueue<String> queue; 
	private final CountDownLatch latch; 
	private final AtomicLong output; 
	
	public Worker(LinkedBlockingQueue<String> queue, AtomicLong output,CountDownLatch latch) {  
		this.queue = queue;
		this.output = output;  
		this.latch = latch; 
	}
	
	public void run() {  
		try {   
			latch.await(); // 放闸之前老实的等待着   
			for (;;) {    
				doWork();    
				Thread.sleep(100);  //0.1s 
			}  
		}  catch (InterruptedException e) {   
			System.out.println("Worker thread will be interrupted...");  
		} 
	} 
	
	private void doWork() throws InterruptedException {  
		boolean success = queue.offer(PRODUCT, 100, TimeUnit.MILLISECONDS);  
		if (success) {  
			long ret = output.incrementAndGet(); //可以声明long型的参数获得返回值,作为日志的参数  
			// 可以在此处生成记录日志  
			System.out.println("worker--->" + ret);
		}
	}
}