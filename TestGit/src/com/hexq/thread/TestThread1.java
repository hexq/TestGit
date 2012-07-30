package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class TestThread1 {

    private final static String code = "ABCD";  
    private static int index = 0;  
      
    private static class PrintThread extends Thread{  
        public void run(){  
            for(;;){  
                synchronized (code) {  
                    if(index == code.length()*10) {
                    	return;  
                    }
                    System.out.print(code.charAt(index++ % code.length()));   
                }  
            }  
        }  
    } 
    
    public static void main(String[] args) {  
        for(int i=0;i<code.length();i++){  
            new PrintThread().start();  
        }  
    }  
    
}  
