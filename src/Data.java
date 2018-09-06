import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

/**
* Author: Ilnaz Daghighian
* 
* public class Data implements Comparable<Data> 
* The Data class reads in data entries from a specific file and places the entries 
* in a linked list. The format of the file is lines with two items: first the process name 
* and second an integer (0-100) representing the process priority (100 is highest).
* Once the data is read in and placed into the proper position in the linked list, they
* are removed from the head of the list one at a time and output to a .txt file. 
*/
public class Data implements Comparable<Data>{

	private String processName;
	private int priority; 
	private static final String OUTPUTFILENAME = "results.txt";
	
	/**
	 * Constructor for Data
	 * public Data(String processName, int priority)
	 * Constructs a Data object with the specified values. 
	 * @param processName - the name of the process  
  	 * @param priority - the priority of the process
	 * @postcondition The Data object has been initialized with given values.
	 */
	public Data(String processName, int priority){
		this.processName = processName; 
		this.priority = priority;
	}
	
	/**
	 * getProcessName
	 * public String getProcessName()
	 * Get the process name of this data 
	 * @return the process name 
	 */
	public String getProcessName(){
		return processName; 
	}
	
	/**
	 * getPriority
	 * public int getPriority()
	 * Get the priority of this data  
	 * @return the priority 
	 */
	public int getPriority(){
		return priority; 
	}
	
	/**
	 * compareTo 
	 * public int compareTo(Data compareData) 
	 * Overrides the compareTo method of Comparable interface to sort an 
	 * object of type Data by its property,and compares its priority property 
	 * in descending order.
	 * @param compareData - the Data object with which this Data is compared
	 * @return Returns a negative integer, zero, or a positive integer as 
	 * this object is less than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(Data compareData) {
		
		int comparePriority = ((Data)compareData).getPriority();
		
		return comparePriority - this.priority; //descending order
	}
	
	/**
	 * readTextFile
	 * public static LinkedList<Data> readTextFile(String filename)
	 * Reads a given text file with data entries and places data items in a linked list, 
	 * then sorts the linked list in a descending order based on priority.   
	 * @param filename - the name of the text file. 
	 * @precondition - the format of the file has to be lines with two items on each line: 
	 * first item is the process name which cannot contain any numbers and second item is 
	 * an integer (0-100)representing the process priority (100 is highest). 
	 * @return - returns an ordered linked list with a descending priority order. 
	 */
	public static LinkedList<Data> readTextFile(String filename){
		
		LinkedList <Data> priorityList = new LinkedList<Data>();
		
		try {	
			File file = new File(filename); 
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line; 
			//reads file and places data into prioriryList 
			while((line = bufferedReader.readLine()) != null){
				int priority = Integer.parseInt(line.replaceAll("[^0-9]", ""));
				String name = line.replaceAll("[0-9]", "");
				priorityList.add(new Data(name, priority));
			}
			bufferedReader.close();
			//sorts the priorityList in descending order using 
			//the priority property of each data object 
			Collections.sort(priorityList); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priorityList;
	}
	
	/**
	 * writeLinkedListToFile 
	 * public static void writeLinkedListToFile(LinkedList<Data> priorityList)
	 * Removes an item (one at a time) from the given ordered linked list and outputs each entry 
	 * into .txt file. 
	 * @param priorityList - the linked list to be written to .txt file. 
	 * @postcondition - The method has output each data entry into a .txt file with the following format:
     * Name = xxxxx  (where xxxxx is a string, the process name)
     * Priority = ### (where ### is a decimal integer, the priority)
	 */	
	public static void writeLinkedListToFile(LinkedList<Data> priorityList){
		
		ListIterator<Data> listIterator = priorityList.listIterator();
		
		//iterates through linked list, writes to file then removes entry
		while(listIterator.hasNext()){	
			String processNameStr = listIterator.next().processName; 
			int priority =  listIterator.previous().getPriority();
			String outputString = "Name = " + processNameStr +
					"\nPriority = " + String.valueOf(priority) + "\n";
			writeToFile(outputString);
			System.out.println(outputString);
			listIterator.remove(); 
		}
	}
	
	/**
	 * writeToFile 
	 * public static void writeToFile(String inputStr)
	 * Writes given string input into a .txt file followed by a single blank line. 
	 * @param inputStr - the string to be written to .txt file. 
	 */	
    public static void writeToFile(String inputStr){
    	
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUTFILENAME, true))) {
    		bw.write(inputStr);
    		bw.newLine();
		
		} catch (IOException e) {

			e.printStackTrace();
		}   	
    }
    
    //main method for testing 	
	public static void main(String[] args) {
		
		writeLinkedListToFile(readTextFile("A2Data.txt"));
		
	}//end main	

}//end class
