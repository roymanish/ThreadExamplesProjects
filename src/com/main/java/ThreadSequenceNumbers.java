/**
 * 
 */
package com.main.java;

/**
 * @author MaRoy
 *
 */

class RunnableThread implements Runnable{

	private volatile int num=0;
	@Override
	public void run() {
		num++;
		System.out.println(Thread.currentThread()+" : "+num);
	}
}

class NumberGenerater{

	private volatile int num = 0;

	public synchronized int getNumber(){

		return ++num;
	}
}

public class ThreadSequenceNumbers {

	private static int num = 1;
	public static void main(String[] args){

		final NumberGenerater ng1 = new NumberGenerater();
		final NumberGenerater ng2 = new NumberGenerater();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (ng1) {


					System.out.println(Thread.currentThread()+" : "+ng1.getNumber());
					try {
						ng1.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t1.setName("Thread1");
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (ng2) {

					if(ng2.getNumber()%2 == 0){
						System.out.println(Thread.currentThread()+" : "+ng2.getNumber());
					}
					try {
						ng2.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t2.setName("Thread2");

		t1.start();
		t2.start();

		for(int i = 1;i<=10;i++){

			if(i%2==0)
				ng2.notify();
			else
				ng1.notify();
		}
	}
}

