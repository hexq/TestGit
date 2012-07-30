package org.hexq.concurrent;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.hexq.util.DateUtils;

/**
 * 
 * java利用FutureTask、ExecutorService 在多核时代充分利用CPU运算
 * 
 * http://hi.baidu.com/ecspell/blog/item/024e37fa87b0cf849e5146b0.html
 * 
 * 1, 测试FutureTask的用法，如果不想分支线程阻塞主线程，又想取得分支线程的执行结果，就用FutureTask
 * 
 * @author hxq8176
 *
 */
public class TutureTaskTest {
	
	/**  * @param args  */ 
	public static void main(String[] args) {  
		CountNum cn = new CountNum(0);  
		
		//FutureTask<Integer> 这里的表示返回的是Integer  
		FutureTask<Integer> ft = new FutureTask<Integer>(cn);  
		Thread td = new Thread(ft);  
		
		System.out.println("futureTask开始执行计算:" + DateUtils.formatTime(new Date()));  
		td.start();  
		
		System.out.println("main 主线程可以做些其他事情:" + DateUtils.formatTime(new Date()));  
		
		try {   
			// futureTask的get方法会阻塞，直到可以取得结果为止   
			Integer result = ft.get();   
			System.out.println("计算的结果是:" + result);  
			
		} catch (InterruptedException e) {   
			e.printStackTrace();  
		} catch (ExecutionException e) {   
			e.printStackTrace();  
		}  
		
		System.out.println("取得分支线程执行的结果后，主线程可以继续处理其他事项"); 
	}
}


class CountNum implements Callable<Integer> { 
	private Integer sum; 
	
	public CountNum(Integer sum) {  
		this.sum = sum; 
	}
	
	public Integer call() throws Exception {  
		for (int i = 0; i < 100; i++) {   
			sum += i;  
		}  
		// 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。  
		Thread.sleep(3000);  
		System.out.println("futureTask 执行完成" + DateUtils.formatTime(new Date()));  
		return sum; 
	}
}
