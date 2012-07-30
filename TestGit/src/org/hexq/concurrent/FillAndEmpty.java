package org.hexq.concurrent;

import java.util.concurrent.Exchanger;

class DataBuffer {
	private int count;
	public static int MAX_SIZE = 100;

	public DataBuffer(int count) {
		this.count = count;
	}

	public synchronized boolean isFull() {
		return count == MAX_SIZE;
	}

	public synchronized boolean isEmpty() {
		return count == 0;
	}

	public void add(int offset) {
		count += offset;
	}

	public void sub(int offset) {
		count -= offset;
	}

	public int size() {
		return count;
	}

}

public class FillAndEmpty {
	private Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
	private DataBuffer initialEmptyBuffer = new DataBuffer(0);
	private DataBuffer initialFullBuffer = new DataBuffer(DataBuffer.MAX_SIZE);
	private static final int step = 2;

	private void addToBuffer(DataBuffer dataBuffer) {
		dataBuffer.add(step);
	}

	private void takeFromBuffer(DataBuffer dataBuffer) {
		dataBuffer.sub(step);
	}

	class FillingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialEmptyBuffer;
			try {
				while (currentBuffer != null) {
					addToBuffer(currentBuffer);
					if (currentBuffer.isFull()) {
						currentBuffer = exchanger.exchange(currentBuffer);
						System.out.println(currentBuffer + "\t"
								+ "currentBuffer.isFull()");
					}
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	class EmptyingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialFullBuffer;
			try {
				while (currentBuffer != null) {
					takeFromBuffer(currentBuffer);
					if (currentBuffer.isEmpty()) {
						Thread.sleep(1000);
						currentBuffer = exchanger.exchange(currentBuffer);
						System.out.println(currentBuffer + "\t"
								+ "currentBuffer.isEmpty()");
					}
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	void start() {
		new Thread(new FillingLoop()).start();
		new Thread(new EmptyingLoop()).start();
	}

	public static void main(String[] args) {
		new FillAndEmpty().start();
	}
}