package com.hexq.thread;

/**
 * @author hexq
 * @see http://blog.csdn.net/kay5804/article/details/6525206
 * @description <p>
 *              不用 java.util.concurrent.Semaphore 类来实现线程间的互斥<br/>
 *              这是在csdn.net上看到一个讯雷面试题时写的。非常适合学习，拿上来 跟大家一起分享。如有不对的地方请指出。email:
 *              java_doc@163.com
 *              </p>
 * 
 */
public class PrintThreadABC {

	transient boolean isAThread = true;// A print target, default start A

	transient boolean isBThread = false;// B print target

	transient boolean isCThread = false;// C print target

	private Object[] LOCK = new Object[0];// 互斥器

	private static int PRINTTIMES = 10;// 默认打印次数

	public static void main(String[] args) {
		// 这三个类很类似，为了学习，才写笨重一点，不过比起Semaphore（信号灯）还要好理解一点。
		// 其实我这个类改下，也可以跟信号灯一样牛叉的~!~~
		PrintThreadABC p = new PrintThreadABC();
		PrintThreadABC.PRINTTIMES = 11;// 打印10次
		new Thread(p.new A()).start();// 启动A线程
		new Thread(p.new B()).start();// 启动B线程
		new Thread(p.new C()).start();// 启动C线程
		// 理解了，也就明白，同步与互斥的作用了!~~~~~ ^_^ !!
	}

	/**
	 * A Thread 类 非常容易理解的一个类
	 * 
	 */
	public class A implements Runnable {
		int count = 0;// 打印次数哦

		public void run() {// 退出线程执行的一个标志方法
			while (count < PRINTTIMES)// 当count < 打印次数时 不停止线程的执行
			{
				synchronized (LOCK) {
					if (isAThread)// 轮到A线程打印了
					{
						System.out.print("A");// 打印一下A
						count++;// 计数加1
						isAThread = false;// 我打过了。
						isBThread = true;// 轮到B了
						isCThread = false;// /还没轮到C
						LOCK.notifyAll();// 唤醒所有线程，让他们两个wait的线程去竞争一下。不过还是争不过B的呀
					} else {
						try {
							LOCK.wait();// 不是轮到我，我wait释放锁!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * B Thread 类 非常容易理解的一个类
	 * 
	 */
	public class B implements Runnable {
		int count = 0;// 打印次数哦

		public void run() {// 退出线程执行的一个标志方法
			while (count < PRINTTIMES)// 当count < 打印次数时 不停止线程的执行
			{
				synchronized (LOCK) {
					if (isBThread)// 轮到A线程打印了
					{
						System.out.print("B");// 打印一下B
						count++;// 计数加1
						isAThread = false;// 还没轮到A。
						isBThread = false;// 我打过了
						isCThread = true;// /轮到C
						LOCK.notifyAll();// 唤醒所有线程，让其它两个wait的线程去竞争一下。不过还是争不过C的呀
					} else {
						try {
							LOCK.wait();// 不是轮到我，我wait释放锁!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * C Thread 类 非常容易理解的一个类
	 * 
	 */
	public class C implements Runnable {
		int count = 0;// 打印次数哦

		public void run() {// 退出线程执行的一个标志方法
			while (count < PRINTTIMES)// 当count < 打印次数时 不停止线程的执行
			{
				synchronized (LOCK) {
					if (isCThread)// 轮到A线程打印了
					{
						System.out.print("C");// 打印一下C
						count++;// 计数加1
						isAThread = true;// 轮到A了
						isBThread = false;// 我打过了
						isCThread = false;// /我打过了
						LOCK.notifyAll();// 唤醒所有线程，让其它两个wait的线程去竞争一下。不过还是争不过A的呀
					} else {
						try {
							LOCK.wait();// 不是轮到我，我wait释放锁!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
}
