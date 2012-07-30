// A simple lock example. 
package javaref.chap26;
import java.util.concurrent.locks.ReentrantLock;
 
public class LockDemo { 
 
  public static void main(String args[]) { 
    ReentrantLock lock = new ReentrantLock(); 
 
    new LockThread(lock, "A"); 
    new LockThread(lock, "B"); 
 
  } 
} 
 
// A shared resource. 
class LockShared { 
  static int count = 0; 
} 
 
// A thread of execution that increments count. 
class LockThread implements Runnable { 
  String name; 
  ReentrantLock lock; 
 
  LockThread(ReentrantLock lk, String n) { 
    lock = lk; 
    name = n; 
    new Thread(this).start(); 
  } 
 
  public void run() { 
     
    System.out.println("Starting " + name); 
 
    try { 
      // First, lock count. 
      System.out.println(name + " is waiting to lock count."); 
      lock.lock(); 
      System.out.println(name + " is locking count."); 
 
      LockShared.count++; 
      System.out.println(name + ": " + LockShared.count); 
 
      // Now, allow a context switch -- if possible. 
      System.out.println(name + " is sleeping."); 
      Thread.sleep(1000); 
    } catch (InterruptedException exc) { 
      System.out.println(exc); 
    } finally { 
      // Unlock 
      System.out.println(name + " is unlocking count."); 
      lock.unlock(); 
    } 
  } 
}

