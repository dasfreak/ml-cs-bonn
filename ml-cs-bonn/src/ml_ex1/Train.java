package ml_ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class Train {
	
	//list of list of attributes to store the data read from file
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Attribute[][] process_data1 = null; 
		List<List<Attribute>> process_data; 
		
		process_data= new ArrayList<List<Attribute>>();
		
		process_data1=FileReader.loadDatasetFromFile("data_exercise_1.csv", process_data, process_data1);
									   
		FileReader.printReadDataArray(process_data1);
		FileReader.printReadDataArrayList(process_data);
	}

}