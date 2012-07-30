package org.hexq.concurrent.queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/** 
  * 消费者 
  *  @author: yanxuxin 
  *  @date: 2010-1-25 
  */
class Seller implements Runnable { 
	private final LinkedBlockingQueue<String> queue; 
	private final AtomicLong sales; 
	private final CountDownLatch latch; 
	private final boolean clear; 
	
	public Seller(LinkedBlockingQueue<String> queue, AtomicLong sales, CountDownLatch latch, boolean clear) {  
		this.queue = queue;  
		this.sales = sales;  
		this.latch = latch;  
		this.clear = clear; 
	} 
	
	public void run() {  
		try {   
			latch.await(); // 放闸之前老实的等待着  
			for (;;) {    
				sale();    
				Thread.sleep(500);   
			}  
		} catch (InterruptedException e) {   
			if(clear) { // 响应中断请求后,如果有要求则销售完队列的产品后再终止线程   
				cleanWarehouse();   
			} else {    
				System.out.println("Seller Thread will be interrupted...");   
			}  
		} 
	}
	
	 private void sale() throws InterruptedException {  
		 String item = queue.poll(50, TimeUnit.MILLISECONDS);  
		 if (item != null) {   
			 long ret = sales.incrementAndGet(); // 可以声明long型的参数获得返回值,作为日志的参数  
			 // 可以在此处生成记录日志
			 System.out.println("seller--->" + ret);
		 }
	 }
	 
	 /** 销售完队列剩余的产品  */ 
	 private void cleanWarehouse() {  
		 try {   
			 while (queue.size() > 0) {    
				 sale();   
			 }  
		 } catch (InterruptedException ex) {   
			 System.out.println("Seller Thread will be interrupted...");  
		 } 
	 }
}