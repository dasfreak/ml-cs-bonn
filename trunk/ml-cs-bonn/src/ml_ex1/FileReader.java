package ml_ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	
	public static List<Attribute> pattern;
	public static Attribute[] pattern1;
	public static String[] nodesNames;
	public int cols_number;
	
	public static List<List<Attribute>> loadDatasetFromFile(String file_name) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		File file = new File(file_name);

		List<List<Attribute>> process_data= new ArrayList<List<Attribute>>();
		
		pattern=new ArrayList<Attribute>(); // pattern for each row (as ArrayList)
		
		//declare reader
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(
				new FileInputStream(file)));

		String line; 
		line=reader.readLine(); // read the first line
		
		String[] line_splitted = line.split(","); //split string into parts by comma to find out what attribute should be in each column
		
		//calculate number of data rows for finding array dimensions
		int rows_number=0;
		while ((line = reader.readLine()) != null) {
			rows_number++;
		}
		
		
		//iterate over all the elements of first line
		for (int i=0; i<line_splitted.length; i++){ 
			String[] s = line_splitted[i].split(":");
			nodesNames[i]=s[0];
			if(s[1].equals("b")){
				pattern.add(new Binary()); // binary pattern for arraryList
			}
			else if (s[1].equals("n")){
				pattern.add(new Numerical()); // numerical pattern for arraryList
			}
			else if (s[1].equals("c")){
				pattern.add(new Categorical());// categorical pattern for arraryList
			}
			else if (s[1].equals("t")){
				pattern.add( new Target()); 	   // target pattern for arraryList
			}
		}
		reader.close();
		
		
		
		/* INITIALIZATION STAGE DONE, NOW WE PUT DATA INTO STRUCTURE */
		
		// declare new reader
		reader = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(file)));

		line=reader.readLine(); // read the first line with information about attributes
		
		//read the lines till the end of file and put data into our structure
		String[] line_read;
		int line_counter=0;
		
		List<Attribute> row; // row as ArrayList
		while ((line = reader.readLine()) != null) {
			row=new ArrayList<Attribute>();
			line_read=line.split(","); //split into columns
			//iterate over each element in certain row
			for (int i=0; i<line_read.length; i++){ 			
				row.add(pattern.get(i).getClass().getDeclaredConstructor().newInstance());
				row.get(i).setData(line_read[i]);
			}
			process_data.add(row);
			
			line_counter++;
		}
				
		reader.close();
		
		return process_data;
	}
	
	
	public static Attribute[][] loadDatasetFromFile1(String file_name) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		File file = new File(file_name);

		Attribute[][] process_data1;
		//declare reader
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(
				new FileInputStream(file)));

		String line; 
		line=reader.readLine(); // read the first line
		
		String[] line_splitted = line.split(","); //split string into parts by comma to find out what attribute should be in each column
		
		//calculate number of data rows for finding array dimensions
		int rows_number=0;
		while ((line = reader.readLine()) != null) {
			rows_number++;
		}
		
		process_data1=new Attribute[rows_number][line_splitted.length]; // array of attributes
		pattern1=new Attribute[line_splitted.length]; // pattern array which keeps attribute for each column
		nodesNames=new String[line_splitted.length];
		
		//iterate over all the elements of first line
		for (int i=0; i<line_splitted.length; i++){ 
			String[] s = line_splitted[i].split(":");
			nodesNames[i]=s[0];
			if(s[1].equals("b")){
				pattern1[i]=new Binary();  //  binary pattern for arrays
			}
			else if (s[1].equals("n")){
				pattern1[i]=new Numerical();  // numerical pattern for arrary
			}
			else if (s[1].equals("c")){
				pattern1[i]=new Categorical(); // categorical pattern for array
			}
			else if (s[1].equals("t")){
				pattern1[i]= new Target(); // target pattern for array
			}
		}
		reader.close();
		
		
		
		/* INITIALIZATION STAGE DONE, NOW WE PUT DATA INTO STRUCTURE */
		
		// declare new reader
		reader = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(file)));

		line=reader.readLine(); // read the first line with information about attributes
		
		//read the lines till the end of file and put data into our structure
		String[] line_read;
		int line_counter=0;
		

		while ((line = reader.readLine()) != null) {
			line_read=line.split(","); //split into columns
			//iterate over each element in certain row
			for (int i=0; i<line_read.length; i++){ 
				process_data1[line_counter][i]=pattern1[i].getClass().getDeclaredConstructor().newInstance();
				process_data1[line_counter][i].setData(line_read[i]);
			}	
			line_counter++;
		}
				
		reader.close();
		return process_data1;
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
	
	/*function which prints data in ArrayList*/
	public static void printReadDataArrayList(List<List<Attribute>> process_data){
		System.out.println("Data in the ArrayList");
		for (int i=0; i<process_data.size(); i++){
			for (int j=0; j<process_data.get(0).size(); j++){
				System.out.print(process_data.get(i).get(j).getData()+",");
			}
			System.out.print("\n");
		}
	}
}
