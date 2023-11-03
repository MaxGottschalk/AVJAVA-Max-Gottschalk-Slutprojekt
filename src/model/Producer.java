package model;

import java.util.LinkedList;
import java.util.Random;

import log.LogInfo;

public class Producer implements Runnable{
	
	Buffer buffer = null;
	boolean isRunning = true;
	private Random random;
	LogInfo logInfo = new LogInfo();
	private LinkedList<String> ls;
	public  int sleepTime;
	public String asd = "hello";
	
	public Producer(Buffer buffer, LinkedList<String> ls) {
		this.buffer = buffer;
		this.random = new Random();
		this.ls = ls;
		this.sleepTime = 1000 + random.nextInt(9001);
		
	}
	
	public void start() {
		this.start();
	}
	
	@Override
	public void run() {
		
		ls.add("Worker Interval: " + getProducerTime());
		
		String path = "src/Files/message.txt"; // Corrected file path
        LogInfo logI = new LogInfo();
        logI.writeData(ls, path);
		while(isRunning) {
			try {
				Thread.sleep(sleepTime);
				System.out.println("Producer " + getProducerTime());
				
				
				buffer.add(new Item("" + (int)(Math.random() * 100)));
			} catch (InterruptedException e) {
                // Handle InterruptedException (e.g., log the exception)
                Thread.currentThread().interrupt(); // Preserve interrupted status
            }
        }
    }

	public synchronized int getProducerTime() {
		return sleepTime / 1000;
	}
	
	
}
