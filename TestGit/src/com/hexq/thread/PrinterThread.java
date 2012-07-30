package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class PrinterThread {
	
    public static void main(String[] args) {  
        String[] names = { "A", "B", "C" };  
        for (int i = 0; i < 3; i++)  
            new MyThread(names[i], i).start();  
    }  
  
    static class MyThread extends Thread {  
  
        private static Integer counter = 0;  
        private String name;  
        private int index;  
  
        public MyThread(String name, int index) {  
            this.name = name;  
            this.index = index;  
        }  
  
        public void run() {  
            while (true) {  
                synchronized (counter) {  
                    if (index == counter) {  
                        System.out.println(name);  
                        counter++;  
                        break;  
                    }  
                }  
            }  
        }  
  
    }  

}
