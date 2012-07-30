package org.hexq.concurrent.sync;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentreLockTest {
	private static long COUNT = 10000;
	private static Lock lock = new ReentrantLock();
	private static long lockCounter = 0;
	private static long syncCounter = 0;
	private static long semaCounter = 0;
	private static AtomicLong atomicCounter = new AtomicLong(0);
	private static Object syncLock = new Object();
	private static Semaphore mutex = new Semaphore(1);

	public static void testLock(int num, int threadCount) {

	}

	static long getLock() {
		lock.lock();
		try {
			return lockCounter;
		} finally {
			lock.unlock();
		}
	}

	static long getSync() {
		synchronized (syncLock) {
			return syncCounter;
		}
	}

	static long getAtom() {
		return atomicCounter.get();
	}

	static long getSemaphore() throws InterruptedException {
		mutex.acquire();

		try {
			return semaCounter;
		} finally {
			mutex.release();
		}
	}

	static long getLockInc() {
		lock.lock();
		try {
			return ++lockCounter;
		} finally {
			lock.unlock();
		}
	}

	static long getSyncInc() {
		synchronized (syncLock) {
			return ++syncCounter;
		}
	}

	static long getAtomInc() {
		return atomicCounter.getAndIncrement();
	}

	static class SemaTest extends Test {

		public SemaTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
		}

		@Override
		protected void test() {
			try {
				getSemaphore();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	static class LockTest extends Test {

		public LockTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
		}

		@Override
		protected void test() {
			getLock();
		}

	}

	static class SyncTest extends Test {

		public SyncTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
		}

		@Override
		protected void test() {
			getSync();
		}

	}

	static class AtomicTest extends Test {

		public AtomicTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
		}

		@Override
		protected void test() {
			getAtom();
		}

	}

	public static void test(String id, long count, int threadNum,
			ExecutorService executor) {

		final CyclicBarrier barrier = new CyclicBarrier(threadNum + 1,
				new Thread() {

					@Override
					public void run() {

					}
				});

		System.out.println("==============================");
		System.out.println("count = " + count + "\t" + "Thread Count = " + threadNum);

		new LockTest("Lock ", barrier, COUNT, threadNum, executor).startTest();
		new SyncTest("Sync ", barrier, COUNT, threadNum, executor).startTest();
		new AtomicTest("Atom ", barrier, COUNT, threadNum, executor).startTest();
		new SemaTest("Sema ", barrier, COUNT, threadNum, executor).startTest();
		
		System.out.println("==============================");
	}

	public static void main(String[] args) {
		for (int i = 1; i < 5; i++) {
			ExecutorService executor = Executors.newFixedThreadPool(10 * i);
			test("", COUNT * i, 10 * i, executor);
		}
	}
}