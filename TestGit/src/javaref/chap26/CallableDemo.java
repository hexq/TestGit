// An example that uses a Callable. 
package javaref.chap26; 
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
 
public class CallableDemo { 
  public static void main(String args[]) throws TimeoutException { 
    ExecutorService exec = Executors.newFixedThreadPool(3); 
    Future<Integer> f; 
    Future<Double> f2; 
    Future<Integer> f3; 
 
    System.out.println("Starting"); 
 
    f = exec.submit(new Sum(10)); 
    f2 = exec.submit(new Hypot(3, 4)); 
    f3 = exec.submit(new Factorial(5)); 
 
    try { 
      System.out.println(f.get(10, TimeUnit.MILLISECONDS)); 
      System.out.println(f2.get()); 
      System.out.println(f3.get()); 
    } catch (InterruptedException exc) { 
      System.out.println(exc); 
    } 
    catch (ExecutionException exc) { 
      System.out.println(exc); 
    } 
 
    exec.shutdown(); 
    System.out.println("Done"); 
  } 
} 
 
// Following are three computational threads. 
 
class Sum implements Callable<Integer> {  
  int stop; 
 
  Sum(int v) { 
	  stop = v;
  } 
 
  public Integer call() { 
    int sum = 0; 
    for(int i = 1; i <= stop; i++) { 
      sum += i; 
    } 
    return sum; 
  } 
} 
    
class Hypot implements Callable<Double> {  
  double side1, side2; 
 
  Hypot(double s1, double s2) { 
    side1 = s1; 
    side2 = s2; 
  } 
    
  public Double call() {  
    return Math.sqrt((side1*side1) + (side2*side2)); 
  } 
} 
    
 
class Factorial implements Callable<Integer> {  
  int stop; 
 
  Factorial(int v) { 
	  stop = v;
  } 
 
  public Integer call() {  
    int fact = 1; 
    for(int i = 2; i <= stop; i++) { 
      fact *= i; 
    } 
    return fact; 
  } 
}

