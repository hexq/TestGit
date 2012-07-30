package com.hexq;

import java.util.concurrent.CountDownLatch;

public class PerformanceTest {
    
    public static void main(String args[]) throws Throwable {
        final int concurrent = 100;
		final int runs = 10000; //Integer.MAX_VALUE;
        // 并发调用
        final CountDownLatch latch = new CountDownLatch(concurrent);
        for (int i = 0; i < concurrent; i ++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i < runs; i++) {
                            //System.out.println(i);
							Thread.sleep(100);
							//do something
                        }
                    }catch(Exception e) {
						e.printStackTrace();
					}finally {
						System.out.println("ok");
                        latch.countDown();
                    }
                }
            }).start();
        }
        
        new Thread(new Runnable() {
            public void run() {
                try{
                    while (latch.getCount() > 0) {
                        System.out.println("latch.getCount:"+latch.getCount());
						Thread.sleep(100);
                    } 
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        latch.await();
        
        System.out.println("done!!!");
    }
   
}
