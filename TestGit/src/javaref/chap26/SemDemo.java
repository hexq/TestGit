// A simple semaphore example. 
package javaref.chap26; 
import java.util.concurrent.Semaphore;
 
public class SemDemo { 
 
  public static void main(String args[]) { 
    Semaphore sem = new Semaphore(1); 
 
//    new IncThread(sem, "incThread"); 
//    new DecThread(sem, "decThread"); 
    
    IncThread it = new IncThread(sem, "incThread");
    new Thread(it).start();
    
    DecThread dt = new DecThread(sem, "decThread");
    new Thread(dt).start();
    
  } 
} 
 
// A shared resource. 
class SemShared { 
  static int count = 0; 
} 
 
// A thread of execution that increments count. 
class IncThread implements Runnable { 
  String name; 
  Semaphore sem; 
 
  IncThread(Semaphore s, String n) { 
    sem = s; 
    name = n; 
//    new Thread(this).start(); 
  } 
 
  public void run() { 
     
    System.out.println("Starting " + name); 
 
    try { 
      // First, get a permit. 
      System.out.println(name + " is waiting for a permit."); 
      sem.acquire(); 
      System.out.println(name + " gets a permit."); 
 
      // Now, access shared resource. 
      for(int i=0; i < 5; i++) { 
    	SemShared.count++; 
        System.out.println(name + ": " + SemShared.count); 
 
        // Now, allow a context switch -- if possible. 
        Thread.sleep(10); 
      } 
    } catch (InterruptedException exc) { 
      System.out.println(exc); 
    } 
 
    // Release the permit. 
    System.out.println(name + " releases the permit."); 
    sem.release(); 
  } 
} 
 
// A thread of execution that deccrements count. 
class DecThread implements Runnable { 
  String name; 
  Semaphore sem; 
 
  DecThread(Semaphore s, String n) { 
    sem = s; 
    name = n; 
//    new Thread(this).start(); 
  } 
 
  public void run() { 
     
    System.out.println("Starting " + name); 
 
    try { 
      // First, get a permit. 
      System.out.println(name + " is waiting for a permit."); 
      sem.acquire(); 
      System.out.println(name + " gets a permit."); 
 
      // Now, access shared resource. 
      for(int i=0; i < 5; i++) { 
    	SemShared.count--; 
        System.out.println(name + ": " + SemShared.count); 
 
        // Now, allow a context switch -- if possible. 
        Thread.sleep(10); 
      } 
    } catch (InterruptedException exc) { 
      System.out.println(exc); 
    } 
 
    // Release the permit. 
    System.out.println(name + " releases the permit."); 
    sem.release(); 
  } 
}

