package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class MyThread {
	
    public static void main(String[] args) {  
        final int count = 10;  
        String[] array = {"A", "B", "C", "D"};  
        
        for(int i=0; i<array.length; i++) {  
        	final int num = i;
            final String current = array[i];  
            final String next = array[(i+1) % array.length]; //��Ҫ�Ǵ������һ�� 
            new Thread(new Runnable() {  
            	//�����㷨
                public void run() {  
                    for(int j=0; j<count; j++) {  
                        synchronized (current) {  
                            // �ȴ��ź�  
                            try { 
                            	current.wait(); 
                            } catch (InterruptedException e) {
                            	e.printStackTrace();
                            }  
                            if(j%2 != 0){
                            	System.out.println();
                            }
                            if(j%2 == 0){
                            	System.out.println();
                            }
                            System.out.print(" i="+num+",j="+j+"--->"+current);  
                            
                            // ���¸��̷߳��ź�  
                            synchronized ( next ){ 
                            	next.notify(); 
                            }  
                        }  
                    }  
                }  
                
            }).start();  
        }  
        
        // ͨ����һ���̷߳��ź�  
        synchronized ( array[0] ){ 
        	array[0].notify(); 
        }  
    }  

}
