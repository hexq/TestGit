// An implementation of a producer and consumer 
// that use semaphores to control synchronization. 
 
package javaref.chap26;
import java.util.concurrent.Semaphore;
 
class ShareRes { 
  int num; 
 
  // Start with consumer semaphore unavailable. 
  static Semaphore semCon = new Semaphore(0); 
  static Semaphore semProd = new Semaphore(1); 
 
  void take() { 
    try { 
      semCon.acquire(); 
    } catch(InterruptedException e) { 
      System.out.println("InterruptedException caught"); 
    } 
 
     System.out.println("Got: " + num); 
     semProd.release(); 
  } 
 
  void put(int n) { 
    try { 
      semProd.acquire(); 
    } catch(InterruptedException e) { 
      System.out.println("InterruptedException caught"); 
    } 
 
    this.num = n; 
    System.out.println("Put: " + n); 
    semCon.release(); 
  } 
} 
 
class Producer implements Runnable { 
  ShareRes shareRes; 
 
  Producer(ShareRes shareRes) { 
    this.shareRes = shareRes; 
    new Thread(this, "Producer").start(); 
  } 
 
  public void run() { 
    for(int i=0; i < 20; i++){
    	shareRes.put(i); 
    }
  } 
} 
 
class Consumer implements Runnable { 
  ShareRes shareRes; 
 
  Consumer(ShareRes shareRes) { 
    this.shareRes = shareRes; 
    new Thread(this, "Consumer").start(); 
  } 
 
  public void run() { 
    for(int i=0; i < 20; i++){
    	shareRes.take(); 
    }
  } 
} 
 
public class ProdCon { 
  public static void main(String args[]) { 
    ShareRes shareRes = new ShareRes(); 
    new Consumer(shareRes); 
    new Producer(shareRes); 
  } 
}

