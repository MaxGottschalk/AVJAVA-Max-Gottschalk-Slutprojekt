package model;

import java.util.Random;

public class Consumer implements Runnable{
	
	Buffer buffer = null;
	boolean isRunning = true;
	private Random random;
	
	public Consumer(Buffer buffer) {
		this.buffer = buffer;
		this.random = new Random();
	}
	
	
	
	@Override
	public void run() {
		int sleepTime = 1000 + random.nextInt(9001);
		while(isRunning) {
			try {
				Thread.sleep(sleepTime);
				System.out.println("Consumer " + sleepTime);
				System.out.println("Consumed: " + buffer.remove());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
