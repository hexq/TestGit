package org.hexq.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author hxq8176
 *
 * @see http://www.cnblogs.com/aurawing/articles/1887056.html
 *
 */
public class MyCompletionService implements Callable<String>{
	
	private int id;

	public MyCompletionService(int i){
		this.id = i;
	}
	
	@Override
	public String call() throws Exception {
		Integer time = (int)(Math.random()*10000);
		try{
			System.out.println(this.id + "--->start");
			Thread.sleep(time);
			System.out.println(this.id + "--->end");
		} catch(Exception e){
			e.printStackTrace();
		}
		return this.id + "--->cost: " + time + " millis";
	}
	
	public static void main(String[] args) throws Exception{
		ExecutorService es = Executors.newCachedThreadPool();
		CompletionService<String> cs = new ExecutorCompletionService<String>(es);
		for(int i = 0; i < 10; i++){
			cs.submit(new MyCompletionService(i));
		}
		for(int i = 0; i < 10; i++){
			Future<String> future = cs.take();
			System.out.println(future.get());
		}
		es.shutdown();
	}

}
