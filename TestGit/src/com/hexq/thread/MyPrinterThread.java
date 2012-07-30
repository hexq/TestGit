package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class MyPrinterThread {
	
	public static void main(String[] args) {  
		  
        MyLock lock = new MyLock();  
  
        Thread a = new Thread(new MyPrinter("A", "B", lock));  
        Thread b = new Thread(new MyPrinter("B", "C", lock));  
        Thread c = new Thread(new MyPrinter("C", "A", lock));  
  
        a.start();  
        b.start();  
        c.start();  
  
        lock.setHolder("A");  
    }  
  
    static class MyPrinter implements Runnable {  
  
        private String myName;  
        private String nextName;  
        private final MyLock lock;  
  
        public MyPrinter(String myName, String nextName, MyLock lock) {  
            this.myName = myName;  
            this.nextName = nextName;  
            this.lock = lock;  
        }  
  
        public void run() {  
            int count = 10;  
            while (count > 0) {  
                if (lock != null && lock.getHolder() != null && lock.getHolder().equals(myName)) {  
                    synchronized (lock) {  
                        System.out.println(myName);  
                        count--;  
                        lock.setHolder(nextName);  
                    }  
                }  
            }  
        }  
    }  
  
    static class MyLock {  
  
        public MyLock() {  
        }  
        private String holder;  
  
        public String getHolder() {  
            return holder;  
        }  
  
        public void setHolder(String holder) {  
            this.holder = holder;  
        }  
    }  
    
}
