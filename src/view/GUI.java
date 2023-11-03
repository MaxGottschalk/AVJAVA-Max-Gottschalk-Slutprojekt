package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Buffer;
import model.Consumer;
import log.LogInfo;
import model.Producer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GUI {
    private JFrame frame;
    private JProgressBar progressBar;
    private JButton addButton;
    private JButton removeButton;
    private Buffer buffer;
    private JTextArea logTextArea;
    private List<Thread> activeThreads;
    private LinkedList<String> ls = new LinkedList<>();
    private int amountWorkers = 0;
    private String path = "src/Files/message.txt";

    public GUI() {
        Random random = new Random();
        activeThreads = new ArrayList<>();

        // Create a single JProgressBar instance
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        buffer = new Buffer(progressBar);
        changeProgressBar(progressBar);
        
        // Create JTextArea for log messages
        logTextArea = new JTextArea(25, 50);
        logTextArea.setEditable(false);

        // Create producer and consumer threads
        Producer producer = new Producer(buffer, ls);
        Thread pT = new Thread(producer);
        pT.start();
        activeThreads.add(pT);
        amountWorkers++;
        ls.add("Worker Amount Added: " + amountWorkers);
        System.out.println("Workers: " + amountWorkers);
        appendToLog("Worker: " + amountWorkers + " Speed: " + producer.getProducerTime());

        int consumerAmount = 3 + random.nextInt(13);
        System.out.println("Amount of Consumers: " + consumerAmount);
        for (int i = 0; i < consumerAmount; i++) {
            Consumer cs = new Consumer(buffer);
            Thread cT = new Thread(cs);
            cT.start();
        }

        // Initialize GUI components
        frame = new JFrame("Progress Bar Demo");
        addButton = new JButton("Add Worker");
        removeButton = new JButton("Fire Worker");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producer newP = new Producer(buffer, ls);
                Thread newThread = new Thread(newP);
                newThread.start();
                System.out.println(newP.asd);
                System.out.println("TEST " + newP.sleepTime);
                activeThreads.add(newThread);
                amountWorkers++;
                ls.add("Worker Amount Added: " + amountWorkers);
                System.out.println("Workers: " + amountWorkers);
                
                appendToLog("Worker: " + amountWorkers + " Speed: " + newP.getProducerTime());
                
                // Write updated worker count to log file
                LogInfo logI = new LogInfo();
                logI.writeData(ls, path);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the last worker thread
            	if(amountWorkers > 1) {
            		if (!activeThreads.isEmpty()) {
                    	Thread removedThread = activeThreads.remove(activeThreads.size() - 1);
                    	removedThread.interrupt(); 
                    	amountWorkers--;
                    	ls.add("Worker Amount reduced to: " + amountWorkers);
                        System.out.println("Workers: " + amountWorkers);
                        
                        appendToLog("Worker removed, Workers: " + amountWorkers);
                        
                        // Write updated worker count to log file
                        LogInfo logI = new LogInfo();
                        logI.writeData(ls, path);
                	}
            	}else {
            		appendToLog("Only one worker left, can´t fire them all!");
            	}
            }
        });

        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        panel.add(progressBar);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        
        String path = "src/Files/message.txt"; // Corrected file path
        LogInfo logI = new LogInfo();
        logI.writeData(ls, path);
        logI.readData(path);
    }
    
    public void changeProgressBar(JProgressBar progressBar) {
        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = progressBar.getValue();
                if (value < 10) {
                    progressBar.setForeground(Color.RED);
                    ls.add("Warning! Production Low! " + progressBar.getPercentComplete() * 100 + "%");
                    
                    appendToLog("Waring: Production Low");
                    LogInfo logI = new LogInfo();
                    logI.writeData(ls, path);
                    logI.readData(path);
                    
                } else if (value > 90) {
                    progressBar.setForeground(Color.GREEN);
                    ls.add("Production: " + progressBar.getPercentComplete() * 100 + "%");
                    
                    appendToLog("Alert: Production High");
                    
                    LogInfo logI = new LogInfo();
                    logI.writeData(ls, path);
                    logI.readData(path);
                    
                } else {
                    progressBar.setForeground(UIManager.getColor("ProgressBar.foreground"));
                }
            }
        });
    }
    
    public void appendToLog(String message) {
        logTextArea.append(message + "\n");
    }
}



