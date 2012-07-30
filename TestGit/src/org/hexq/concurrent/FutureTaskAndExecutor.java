package org.hexq.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * java利用FutureTask、ExecutorService 在多核时代充分利用CPU运算
 * 
 * http://hi.baidu.com/ecspell/blog/item/024e37fa87b0cf849e5146b0.html
 * 
 * 2, 测试多核时代，充分的利用CPU来运算数据，并且处理返回的结果,学习API专用
 * 
 * @author hxq8176
 *
 */
public class FutureTaskAndExecutor {
	public static void main(String[] args) {  
		List<FutureTask<Integer>> list = new ArrayList<FutureTask<Integer>>();  
		
		// 创建线程池，线程池的大小和List.size没有啥必然的关系，一般的原则是<=list.size,多出来浪费不好  
//		ExecutorService es = Executors.newFixedThreadPool(10); 
		ExecutorService es = Executors.newCachedThreadPool();
		
		long start = System.currentTimeMillis();
		for (int i = 10; i < 1000; i++) {   
			// 创建对象   
			FutureTask<Integer> futureTask = new FutureTask<Integer>(new GetSum(i));   
			// 添加到list,方便后面取得结果   
			list.add(futureTask);   
			// 一个个提交给线程池，当然也可以一次性的提交给线程池，es.invokeAll(list);   
			es.submit(futureTask);  
		}  
		
		System.out.println("list.size() = " + list.size());
		// 开始统计结果  
		Integer total = 0;  
		for (FutureTask<Integer> ft : list) {   
			try {    
				total += ft.get();   
			} catch (InterruptedException e) {    
				e.printStackTrace();   
			} catch (ExecutionException e) {    
				e.printStackTrace();   
			}  
		}  
		
		// 处理完毕，一定要记住关闭线程池，这个不能在统计之前关闭，因为如果线程多的话,执行中的可能被打断 
		es.shutdown();  
		long end = System.currentTimeMillis();
		System.out.println("多线程计算后的总结果是:" + total + ", cost: " + (end-start) + " millis"); 
	}
}

/** 
 *  这个类很简单，就是统计下简单的加法(从1 到total) 
 * 
 */
class GetSum implements Callable<Integer> { 
	private Integer total; 
	private Integer sum = 0;
	
	public GetSum(Integer total) {  
		this.total = total; 
	} 
	
	public Integer call() throws Exception {  
		for (int i = 1; i < total + 1; i++) {   
			sum += i;  
		}  
		System.out.println(Thread.currentThread().getName() + " sum: " + sum);  
		return sum; 
	}
		
}
