package com.hexq.thread;

/**
 * @author hexq
 * @see http://blog.csdn.net/liu251/article/details/6227763
 * ��jdk1.5���̵߳�Э����ͨ��Lock/Condition��Samephore/CyclicBarriar/CountLatchDownʵ�ֵ�
 * 
 */
public class ABC4 {

	private int index = 1;//ͨ��index��ȷ��A B C�����
	private Object obj = new Object();//ͬ������
	
	private static int len = 3;
	
	public static void main(String[] args) {
		//�ڲ��࣬�߳�ִ��ͨ��jdk1.4
		ABC4 abc = new ABC4();
		
		//����3��runnable��
		ThreadA ta = abc.new ThreadA();
		ThreadB tb = abc.new ThreadB();
		ThreadC tc = abc.new ThreadC();
		for (int i = 0; i < 10; i++) {
			new Thread(ta).start();
			new Thread(tb).start();
			new Thread(tc).start();
		}
	}

	class ThreadA implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 1) {
						System.out.print("A");
						index++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	class ThreadB implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 2) {
						System.out.print("B");
						index++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	class ThreadC implements Runnable{
		public void run() {
			synchronized (obj) {
				while(true){
					if (index % len == 0) {
						System.out.print("C");
						index++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
}