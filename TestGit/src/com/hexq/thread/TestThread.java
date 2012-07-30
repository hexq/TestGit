package com.hexq.thread;

/**
 * @author hexq
 * @see http://www.iteye.com/topic/754384
 * 
 */
public class TestThread {  
    public static void main(String[] args) throws InterruptedException {  
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 随便改什么字符串  
        int runCount = 500; // 任意次数  
        int strLength = str.length();     //就计算一次长度，避免字符串长了以后，重复计算长度  
        String[] strBuffer = new String[strLength * runCount]; // 字符串输出缓存  
        Thread[] Ths = new Thread[strLength];  
        for (int index = 0; index < strLength; index++) {  
            Ths[index] = new Thread(new Runner(index, strBuffer, str, strLength, runCount));  
            Ths[index].start();  
        }  
                //等待所有线程结束  
        for (int index = 0; index < strLength; index++) {  
            Ths[index].join();  
        }  
                //输出结果  
        for (int i = 0; i < strBuffer.length; i++) {  
            System.out.print(strBuffer[i]);  
            if ((i + 1) % strLength == 0)  
                System.out.println();  
        }  
    }  
}  
  
class Runner implements Runnable {  
    String[] strBuffer;  
    String str;  
    int strLength;  
    int index;  
    int runCount;  
  
    public Runner(int index, String[] strBuffer, String str, int strLength, int runCount) {  
        this.index = index;  
        this.runCount = runCount;  
        this.strBuffer = strBuffer;  
        this.str = str;  
        this.strLength = strLength;  
    }  
  
    @Override  
    public void run() {  
        for (int i = 0; i < runCount; i++) {  
            strBuffer[strLength * i + index] = String.valueOf(str.charAt(index));  
        }  
    }  
}  
