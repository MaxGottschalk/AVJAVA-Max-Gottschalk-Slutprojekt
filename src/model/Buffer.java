package model;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JProgressBar;

public class Buffer {
	
	Queue<Item> buffer = new LinkedList<Item>();
	
	private JProgressBar progressBar;

    public Buffer(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
	
	
	public synchronized void add(Item item) {
		buffer.add(item);
		notify();
		System.out.println(buffer);
		// Update progress bar when an item is added
        progressBar.setValue(buffer.size());
	}
	
	public synchronized Item remove() {
		if(buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		 // Update progress bar when an item is removed
        progressBar.setValue(progressBar.getValue() - 1);
		return buffer.remove();
	}
}
