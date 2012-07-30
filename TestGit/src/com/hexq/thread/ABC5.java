package com.hexq.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hexq
 * @see http://blog.csdn.net/liu251/article/details/6227763
 * ��jdk1.5���̵߳�Э����ͨ��Lock/Condition��Samephore/CyclicBarriar/CountLatchDownʵ�ֵ�
 * 
 */
public class ABC5 {
	private int index = 1;//ͨ��index��ȷ��A B C�����  
    private Lock lock = new ReentrantLock();//ͨ��JDK5�е�������֤�̵߳ķ��ʵĻ���  
    private Condition condition = lock.newCondition();//�߳�Э�� 
    
    private static int len = 3;
    
    public static void main(String[] args) {  
        ABC5 abc = new ABC5();//�ڲ����߳�ִ�з�ʽjdk1.5  
        ThreadA ta = abc.new ThreadA();//����3��runnable��  
        ThreadB tb = abc.new ThreadB();  
        ThreadC tc = abc.new ThreadC();  
        ExecutorService es = Executors.newFixedThreadPool(3);//ͨ���̳߳�ִ��  
        for (int i = 0; i < 10; i++) {  
            es.execute(ta);  
            es.execute(tb);  
            es.execute(tc);  
        }  
        es.shutdown();//�ر��̳߳�  
    }  
  
    class ThreadA implements Runnable{  
        public void run() {  
            lock.lock();  
            try{  
                while(true){  
                    if (index % len == 1) {  
                        System.out.print("A");  
                        index++;  
                        condition.signalAll();  
                        break;  
                    }  
                    else {  
                        try {  
                            condition.await();  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }  
            }finally{  
                lock.unlock();  
            }  
        }  
    }  
    
    class ThreadB implements Runnable{  
        public void run() {  
            lock.lock();  
            try{  
                while(true){  
                    if (index % len == 2) {  
                        System.out.print("B");  
                        index++;  
                        condition.signalAll();  
                        break;  
                    }  
                    else {  
                        try {  
                            condition.await();  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }  
            }finally{  
                lock.unlock();  
            }  
        }  
    }  
    
    class ThreadC implements Runnable{  
        public void run() {  
            lock.lock();  
            try {  
                while(true){  
                    if (index % len == 0) {  
                        System.out.print("C");  
                        index++;  
                        condition.signalAll();  
                        break;  
                    }  
                    else {  
                        try {  
                            condition.await();  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }  
            }finally{  
                lock.unlock();  
            }  
        }  
    }  
    
}
