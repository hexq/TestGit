package jdk.research;

public class ThreadAB {
	
	private int index = 1;
	private Object obj = new Object();
	private static int len = 2;
	
	public static void main(String[] args) {
		ThreadAB t = new ThreadAB();
		ThreadA ta = t.new ThreadA();
		ThreadB tb = t.new ThreadB();
		for (int i = 0; i < 20; i++) {
			new Thread(ta).start();
			new Thread(tb).start();
		}
	}
	
	class ThreadA implements Runnable{

		@Override
		public void run() {
			synchronized (obj) {
				while (true) {
					if(index % len == 1 ){
						System.out.print("A");
						index++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		
	}
	
	class ThreadB implements Runnable{

		@Override
		public void run() {
			synchronized (obj) {
				while (true) {
					if(index % len == 0 ){
						System.out.print("B");
						index++;
						obj.notifyAll();
						break;
					} else {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		
	}

}
