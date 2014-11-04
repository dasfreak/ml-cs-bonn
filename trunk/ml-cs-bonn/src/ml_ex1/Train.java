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
									   
		//FileReader.printReadDataArray(process_data1);
		//FileReader.printReadDataArrayList(process_data);

		//SortNumerical.sort(process_data, 0);
		//System.out.println("List sorted by column 0: ");
		//FileReader.printReadDataArrayList(process_data);
		
		
		// super cool 3D List: 0 where test is true, 1 for false
		List<List<List<Attribute>>> Subsets;
		Subsets = new ArrayList<List<List<Attribute>>>();
		
		// split dataset at column 4 (boolean), threshold and categorialset S isn't used in this example.
		// c++ has default parameters, java does not :'(
		//Subsets = SplitList.split(process_data, 0, -1, null);
		
		// split at column 1 (numerical) with threshold 48
		// Subsets = SplitList.split(process_data, 0, 48, null);
		
		// split at column 2 (categorial) with set S
		List<String> S =  new ArrayList<String>();
		S.add("bb");
		S.add("bc");
		
		Subsets = SplitList.split(process_data, 1, 48, S);
		
		FileReader.printReadDataArrayList(Subsets.get(0));
	}

}