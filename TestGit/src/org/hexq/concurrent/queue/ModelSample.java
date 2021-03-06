package org.hexq.concurrent.queue;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/** 
 * ModelSample是主控，负责生产消费的调度，Worker和Seller分别作为生产者和消费者，并均实现Runnable接口。 
 * ModelSample构造器内的参数clear主要是用于：当主控调用线程池的shutdownNow()方法时，会给池内的所有线程发送中断信号，
 * 使得线程的中断标志置位。这时候对应的Runnable的run()方法使用响应中断的LinkedBlockingQueue的方法(入队，出队)时就会
 * 抛出InterruptedException异常，生产者线程对这个异常的处理是记录信息后终止任务。而消费者线程是记录信息后终止任务，
 * 还是消费完队 列内的产品再终止任务，则取决于这个选项值。
 *  
 * 多线程的一个难点在于适当得销毁线程，这里得益于LinkedBlockingQueue的入队和出队的操作均提供响应中断的API，
 * 使得控制起来相对的 简单一点。在Worker和Seller中共享LinkedBlockingQueue的实例queue时，我没有使用put或者take在queue满 
 * 和空状态时无限制的阻塞线程，而是使用offer(E e, long timeout, TimeUnit unit)和poll(long timeout, TimeUnit unit)
 * 在指定的timeout时间内满足条件时阻塞线程。主要因为在于：先中断生产线程的情况下，如果所有的消费线程之前均被扔到等待集，
 * 那么无法它 们将被唤醒。而后两者在超时后将自行恢复可运行状态。 

 * 再者看看queue的size()方法，这也是选择LinkedBlockingQueue而不选ArrayBlockingQueue作为阻塞队列的原因。
 * 因为前者使用的AtomicInteger的count.get()返回最新值，完全无锁；而后者则需要获取唯一的锁，在此期间无法进行任何出队，
 * 入队操作。而这个例子中clear==true时，主线程和所有的消费线程均需要使用size()方法检查queue的元素个数。
 * 这类的非业务操作本就不该 影响别的操作，所以这里LinkedBlockingQueue使用AtomicInteger计数无疑是个优秀的设计。 

 * 另外编写这个例子时有点玩票的用了CountDownLatch，它的作用很简单。countDown()方法内部计数不为0时，执行了其await() 
 * 方法的线程将会阻塞等待；一旦计数为0，这些线程将恢复可运行状态继续执行。这里用它就像一个发令枪，
 * 线程池submit任务的新线程在run内被阻塞， 主线程一声令下countDown！这些生产消费线程均恢复执行状态。
 * 最后就是命令的实现过于简陋了，如果要响应其他的命令的话可以改造成响应事件处理的 观察者模式，
 * 不过它不是演示的重点就从简了。后面就是试着写写ConcurrentLinkedQueue的分析了。
 * 
 * 
  * @author: yanxuxin 
  * @date: 2010-1-25 
  */
public class ModelSample { 
	/** 线程池提交的任务数*/ 
	private final int taskNum = Runtime.getRuntime().availableProcessors() + 1; 
	
	/** 用于多线程间存取产品的队列*/ 
	private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(16); 
	
	/** 记录产量*/ 
	private final AtomicLong output = new AtomicLong(0); 
	
	/** 记录销量*/ 
	private final AtomicLong sales = new AtomicLong(0); 
	
	/** 简单的线程起步开关*/ 
	private final CountDownLatch latch = new CountDownLatch(1); 
	
	/** 停产后是否售完队列内的产品的选项*/ 
	private final boolean clear; 
	
	/** 用于提交任务的线程池*/ 
	private final ExecutorService pool; 
	
	/** 简陋的命令发送器*/ 
	private Scanner scanner; 
	
	public ModelSample(boolean clear) {  
		this.pool = Executors.newCachedThreadPool();  
		this.clear = clear; 
	} 
	
	/**  * 提交生产和消费任务给线程池,并在准备完毕后等待终止命令  */ 
	public void service() {  
		doService();  
		waitCommand(); 
	}  
	
	/**  * 提交生产和消费任务给线程池,并在准备完毕后同时执行  */ 
	private void doService() {  
		for (int i = 0; i < taskNum; i++) {   
			if (i == 0) {    
				pool.submit(new Worker(queue, output, latch));   
			}   else {    
				pool.submit(new Seller(queue, sales, latch, clear));   
			}  
		}  
		latch.countDown();//开闸放狗,线程池内的线程正式开始工作 } 
	}
	
	/**  * 接收来自终端输入的终止命令  */ 
	private void waitCommand() { 
		scanner = new Scanner(System.in);  
		System.out.println("input: " + scanner.nextLine());
		while (!scanner.nextLine().equals("q")) {   
			try {    
				Thread.sleep(500);   
			}   catch (InterruptedException e) {    
				e.printStackTrace();   
			}  
		}  
		scanner.close();  
		destory(); 
	} 
	
	/**  * 停止一切生产和销售的线程  */ 
	private void destory() {  
		pool.shutdownNow(); //不再接受新任务,同时试图中断池内正在执行的任务  
		while (clear && queue.size() > 0) {   
			try {    
				Thread.sleep(500);   
			}  catch (InterruptedException e) {   
				e.printStackTrace();   
			}  
		}  
		System.out.println("Products:" + output.get() + "; Sales:" + sales.get()); 
	} 
	
	public static void main(String[] args) {  
		ModelSample model = new ModelSample(false);  
		model.service(); 
	}
}