package org.hexq.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * http://www.cnblogs.com/aurawing/articles/1887056.html 
 * 
 * 
 * @author hexq
 *
 */
public class TestCountDownLatch {
	
	public static void main(String[] args) {
			// 开始的倒数锁
			final CountDownLatch begin = new CountDownLatch(1);
			// 结束的倒数锁
			final CountDownLatch end = new CountDownLatch(10);
			// 十名选手
			final ExecutorService exec = Executors.newFixedThreadPool(10);
		  
			for (int index = 0; index < 10; index++) {
				final int num = index + 1;
				
				Runnable run = new Runnable() {
					public void run() {
						try {
							begin.await();//一直阻塞
							Thread.sleep((long) (Math.random() * 1000));
							System.out.println("No." + num + " arrived");
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							end.countDown();
						}
					}
				};
				exec.submit(run);
			}
			System.out.println("Game Start");
			//开始干活
			begin.countDown(); //执行该方法后所有阻塞的线程都开始工作
			try {
				end.await(); //Main线程被阻塞，等待释放
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Game Over");
		   
			//执行关闭
			exec.shutdown();
		}

}

