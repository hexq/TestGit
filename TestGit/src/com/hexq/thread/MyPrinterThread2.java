package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class MyPrinterThread2 implements Runnable {
	
	    private String name;  
	    private Object prev;  
	    private Object self;  
	  
	    private MyPrinterThread2(String name, Object prev, Object self) {  
	        this.name = name;  
	        this.prev = prev;  
	        this.self = self;  
	    }  
	  
	    @Override  
	    public void run() {  
	        int count = 10;  
	        while (count > 0) {  
	            synchronized (prev) {  
	                synchronized (self) {  
	                    System.out.print(name);  
	                    count--;  
	                    self.notify();  
	                }  
	                try {  
	                    prev.wait();  
	                } catch (InterruptedException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	  
	        }  
	    }  
	  
	    public static void main(String[] args) throws Exception {  
	        Object a = new Object();  
	        Object b = new Object();  
	        Object c = new Object();  
	        MyPrinterThread2 pa = new MyPrinterThread2("A", c, a);  
	        MyPrinterThread2 pb = new MyPrinterThread2("B", a, b);  
	        MyPrinterThread2 pc = new MyPrinterThread2("C", b, c);  
	        new Thread(pa).start();  
	        new Thread(pb).start();  
	        new Thread(pc).start();  
	    }  
	}   
