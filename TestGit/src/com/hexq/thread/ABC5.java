package com.hexq.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hexq
 * @see http://blog.csdn.net/liu251/article/details/6227763
 * 在jdk1.5中线程的协作是通过Lock/Condition及Samephore/CyclicBarriar/CountLatchDown实现的
 * 
 */
public class ABC5 {
	private int index = 1;//通过index来确定A B C的输出  
    private Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问的互斥  
    private Condition condition = lock.newCondition();//线程协作 
    
    private static int len = 3;
    
    public static void main(String[] args) {  
        ABC5 abc = new ABC5();//内部类线程执行方式jdk1.5  
        ThreadA ta = abc.new ThreadA();//声明3个runnable类  
        ThreadB tb = abc.new ThreadB();  
        ThreadC tc = abc.new ThreadC();  
        ExecutorService es = Executors.newFixedThreadPool(3);//通过线程池执行  
        for (int i = 0; i < 10; i++) {  
            es.execute(ta);  
            es.execute(tb);  
            es.execute(tc);  
        }  
        es.shutdown();//关闭线程池  
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
