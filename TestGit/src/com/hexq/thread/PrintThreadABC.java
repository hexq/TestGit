package com.hexq.thread;

/**
 * @author hexq
 * @see http://blog.csdn.net/kay5804/article/details/6525206
 * @description <p>
 *              ���� java.util.concurrent.Semaphore ����ʵ���̼߳�Ļ���<br/>
 *              ������csdn.net�Ͽ���һ��Ѷ��������ʱд�ġ��ǳ��ʺ�ѧϰ�������� �����һ��������в��Եĵط���ָ����email:
 *              java_doc@163.com
 *              </p>
 * 
 */
public class PrintThreadABC {

	transient boolean isAThread = true;// A print target, default start A

	transient boolean isBThread = false;// B print target

	transient boolean isCThread = false;// C print target

	private Object[] LOCK = new Object[0];// ������

	private static int PRINTTIMES = 10;// Ĭ�ϴ�ӡ����

	public static void main(String[] args) {
		// ������������ƣ�Ϊ��ѧϰ����д����һ�㣬��������Semaphore���źŵƣ���Ҫ�����һ�㡣
		// ��ʵ���������£�Ҳ���Ը��źŵ�һ��ţ���~!~~
		PrintThreadABC p = new PrintThreadABC();
		PrintThreadABC.PRINTTIMES = 11;// ��ӡ10��
		new Thread(p.new A()).start();// ����A�߳�
		new Thread(p.new B()).start();// ����B�߳�
		new Thread(p.new C()).start();// ����C�߳�
		// ����ˣ�Ҳ�����ף�ͬ���뻥���������!~~~~~ ^_^ !!
	}

	/**
	 * A Thread �� �ǳ���������һ����
	 * 
	 */
	public class A implements Runnable {
		int count = 0;// ��ӡ����Ŷ

		public void run() {// �˳��߳�ִ�е�һ����־����
			while (count < PRINTTIMES)// ��count < ��ӡ����ʱ ��ֹͣ�̵߳�ִ��
			{
				synchronized (LOCK) {
					if (isAThread)// �ֵ�A�̴߳�ӡ��
					{
						System.out.print("A");// ��ӡһ��A
						count++;// ������1
						isAThread = false;// �Ҵ���ˡ�
						isBThread = true;// �ֵ�B��
						isCThread = false;// /��û�ֵ�C
						LOCK.notifyAll();// ���������̣߳�����������wait���߳�ȥ����һ�¡���������������B��ѽ
					} else {
						try {
							LOCK.wait();// �����ֵ��ң���wait�ͷ���!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * B Thread �� �ǳ���������һ����
	 * 
	 */
	public class B implements Runnable {
		int count = 0;// ��ӡ����Ŷ

		public void run() {// �˳��߳�ִ�е�һ����־����
			while (count < PRINTTIMES)// ��count < ��ӡ����ʱ ��ֹͣ�̵߳�ִ��
			{
				synchronized (LOCK) {
					if (isBThread)// �ֵ�A�̴߳�ӡ��
					{
						System.out.print("B");// ��ӡһ��B
						count++;// ������1
						isAThread = false;// ��û�ֵ�A��
						isBThread = false;// �Ҵ����
						isCThread = true;// /�ֵ�C
						LOCK.notifyAll();// ���������̣߳�����������wait���߳�ȥ����һ�¡���������������C��ѽ
					} else {
						try {
							LOCK.wait();// �����ֵ��ң���wait�ͷ���!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * C Thread �� �ǳ���������һ����
	 * 
	 */
	public class C implements Runnable {
		int count = 0;// ��ӡ����Ŷ

		public void run() {// �˳��߳�ִ�е�һ����־����
			while (count < PRINTTIMES)// ��count < ��ӡ����ʱ ��ֹͣ�̵߳�ִ��
			{
				synchronized (LOCK) {
					if (isCThread)// �ֵ�A�̴߳�ӡ��
					{
						System.out.print("C");// ��ӡһ��C
						count++;// ������1
						isAThread = true;// �ֵ�A��
						isBThread = false;// �Ҵ����
						isCThread = false;// /�Ҵ����
						LOCK.notifyAll();// ���������̣߳�����������wait���߳�ȥ����һ�¡���������������A��ѽ
					} else {
						try {
							LOCK.wait();// �����ֵ��ң���wait�ͷ���!
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
}
