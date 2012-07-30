package org.hexq.concurrent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MySemaphore extends Thread {
	Semaphore sema;
	private int id;
	
	public MySemaphore(int i,Semaphore s){
		this.id = i;
		this.sema = s;	
	}

	public void run(){
	    try{
	    	if(sema.availablePermits()>0){
	    		System.out.println("顾客["+this.id+"]进入厕所，有空位");
	    	}
	    	else{
	    		System.out.println("顾客["+this.id+"]进入厕所，没空位，排队");
	    	}
	    	sema.acquire();
	    	System.out.println("顾客["+this.id+"]获得坑位");
	    	
	    	Thread.sleep((int)(Math.random()*1000)); //1s搞定拉屎
	    	
	    	System.out.println("顾客["+this.id+"]使用完毕");
	    	sema.release();
	    } catch(Exception e){
	    	e.printStackTrace();
	    } finally{
	    	 // 当无人使用线程时认为，所有线程已结束 
	    	 if(sema.availablePermits() == 2){
	    		 System.out.println("使用完毕，需要清扫了");
	    	 }
	    }
	}
	
	public static void main(String args[]){
		int num = 2;
	    ExecutorService es = Executors.newCachedThreadPool();
	    Semaphore sema = new Semaphore(num);
	    for(int i=0;i<10;i++){
	    	es.submit(new MySemaphore(i+1,sema));
	    }
	    es.shutdown();
	    sema.acquireUninterruptibly(num);
	   
//	    System.out.println("使用完毕，需要清扫了");
	    sema.release(num);
	}
}
