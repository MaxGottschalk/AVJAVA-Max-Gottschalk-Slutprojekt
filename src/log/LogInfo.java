package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;


public class LogInfo {

	 	public void readData(String path) {
			
	    	try(BufferedReader br = new BufferedReader(new FileReader(path))){
	    		String line = br.readLine();
	    		while(line != null) {
	    			System.out.println(line);
	    			line = br.readLine();
	    		}
	    		
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	 	}
	 
	 	public void writeData(LinkedList<String> stringList, String path) {
	        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
	            for(String line : stringList) {
	            	br.write(line);
	            	br.newLine();
	            	br.flush();
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
}