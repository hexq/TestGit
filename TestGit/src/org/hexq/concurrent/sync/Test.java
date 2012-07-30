package org.hexq.concurrent.sync;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public abstract class Test {
	protected String id;
	protected CyclicBarrier barrier;
	protected long count;
	protected int threadNum;
	protected ExecutorService executor;

	public Test(String id, CyclicBarrier barrier, long count, int threadNum,
			ExecutorService executor) {
		this.id = id;
		this.barrier = barrier;
		this.count = count;
		this.threadNum = threadNum;
		this.executor = executor;
	}

	public void startTest() {

		long start = System.currentTimeMillis();

		for (int j = 0; j < threadNum; j++) {
			executor.execute(new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < count; i++) {
						test();
					}

					try {
						barrier.await();

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}

		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		// 所有线程执行完成之后，才会跑到这一步
		long duration = System.currentTimeMillis() - start;
		System.out.println(id + " = " + duration + " ms");
	}

	protected abstract void test();
}