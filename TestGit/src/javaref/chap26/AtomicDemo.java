// A simple example of Atomic. 
package javaref.chap26;
import java.util.concurrent.atomic.AtomicInteger;
 
public class AtomicDemo { 
 
  public static void main(String args[]) { 
    new AtomicThread("A"); 
    new AtomicThread("B"); 
    new AtomicThread("C"); 
  } 
} 
 
class Shared { 
  static AtomicInteger ai = new AtomicInteger(0); 
} 
 
// A thread of execution that increments count. 
class AtomicThread implements Runnable { 
  String name; 
 
  AtomicThread(String n) { 
    name = n; 
    new Thread(this).start(); 
  } 
 
  public void run() { 
     
    System.out.println("Starting " + name); 
 
    for(int i=1; i <= 3; i++) 
      System.out.println(name + " got: " +  
             Shared.ai.getAndSet(i)); 
  } 
}


