package ml_ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

	/*
	 * Static function for reading from file and mapping the file content onto the object structure
	 * @file_name - name of the file to read from
	 */
	
	private Attribute[] pattern;
	private String[] nodesNames;
	private int columnsCount;
	private Attribute[][] data;
	public Attribute[] getPattern(){
		return pattern;
	}
	public String[] getNodesNames(){
		return nodesNames;
	}
	public int getColumnsCount(){
		return columnsCount;
	}
	public Attribute[][] getData() {
		return data;
	}
	public FileReader(String fileName){
		loadDatasetFromFile1(fileName);
	}
	public void loadDatasetFromFile1(String file_name) {
		File file = new File(file_name);
		//declare reader
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line = null; 
		try {
			line=reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // read the first line
		
		String[] line_splitted = line.split(","); //split string into parts by comma to find out what attribute should be in each column
		
		//calculate number of data rows for finding array dimensions
		int rows_number=0;
		try {
			while ((line = reader.readLine()) != null) {
				rows_number++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data       = new Attribute[rows_number][line_splitted.length]; // array of attributes
		pattern    = new Attribute[line_splitted.length]; // pattern array which keeps attribute for each column
		nodesNames = new String[line_splitted.length];
		
		//iterate over all the elements of first line
		for (int i=0; i<line_splitted.length; i++){ 
			String[] s = line_splitted[i].split(":");
			nodesNames[i]=s[0];
			if(s[1].equals("b")){
				pattern[i]=new Binary();  //  binary pattern for arrays
			}
			else if (s[1].equals("n")){
				pattern[i]=new Numerical();  // numerical pattern for arrary
			}
			else if (s[1].equals("c")){
				pattern[i]=new Categorical(); // categorical pattern for array
			}
			else if (s[1].equals("t")){
				pattern[i]= new Target(); // target pattern for array
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/* INITIALIZATION STAGE DONE, NOW WE PUT DATA INTO STRUCTURE */
		
		// declare new reader
		try {
			reader = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			line=reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // read the first line with information about attributes
		
		//read the lines till the end of file and put data into our structure
		String[] line_read;
		int line_counter=0;
		

		try {
			while ((line = reader.readLine()) != null) {
				line_read=line.split(","); //split into columns
				//iterate over each element in certain row
				for (int i=0; i<line_read.length; i++){ 
					data[line_counter][i]=pattern[i].getClass().getDeclaredConstructor().newInstance();
					data[line_counter][i].setData(line_read[i]);
				}	
				line_counter++;
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*function which prints data in array*/
	public static void printReadDataArray(Attribute[][] process_data1){
		System.out.println("Data in the Array");
		for (int i=0; i<process_data1.length; i++){
			for (int j=0; j<process_data1[0].length; j++){
				System.out.print(process_data1[i][j].getData()+",");
			}
			System.out.print("\n");
		}
	}

}
