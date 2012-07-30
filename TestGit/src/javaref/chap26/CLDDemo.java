// A simple example that uses an Executor. 
 
package javaref.chap26;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
class CLDDemo { 
  public static void main(String args[]) { 
    CountDownLatch cdl1 = new CountDownLatch(5); 
    CountDownLatch cdl2 = new CountDownLatch(5); 
    CountDownLatch cdl3 = new CountDownLatch(5); 
    CountDownLatch cdl4 = new CountDownLatch(5); 
    ExecutorService exec = Executors.newFixedThreadPool(2); 
 
    System.out.println("Starting"); 
 
    // Start the threads. 
    exec.execute(new MyThread(cdl1, "A")); 
    exec.execute(new MyThread(cdl2, "B")); 
    exec.execute(new MyThread(cdl3, "C")); 
    exec.execute(new MyThread(cdl4, "D")); 
 
    try { 
      cdl1.await(); 
      cdl2.await(); 
      cdl3.await(); 
      cdl4.await(); 
    } catch (InterruptedException exc) { 
      System.out.println(exc); 
    } 
 
    exec.shutdown(); 
    System.out.println("\nDone"); 
  } 
} 
 
class MyThread implements Runnable { 
  String name; 
  CountDownLatch latch; 
 
  public MyThread(CountDownLatch c, String n) { 
    latch = c; 
    name = n; 
    new Thread(this); 
  } 
 
  public void run() { 
    for(int i = 0; i < 5; i++) { 
      //System.out.print(name + "(" + i + ") "); 
      System.out.print(name + " ");
      latch.countDown(); 
    } 
  } 
}

