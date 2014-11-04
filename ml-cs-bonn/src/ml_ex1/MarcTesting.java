package ml_ex1;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MarcTesting {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

		Attribute[][] process_data1 = null; 
		List<List<Attribute>> process_data; 

		process_data= new ArrayList<List<Attribute>>();

		process_data1=FileReader.loadDatasetFromFile("data_exercise_1.csv", process_data, process_data1);


		// FileReader.printReadDataArray(process_data1);
		// FileReader.printReadDataArrayList(process_data);

		// SortNumerical.sort(process_data, 0);
		// System.out.println("List sorted by column 0: ");
		// FileReader.printReadDataArrayList(process_data);

		// super cool 3D List: .get(1) where test is true, .get(0) for false
		List<List<List<Attribute>>> Subsets;
		Subsets = new ArrayList<List<List<Attribute>>>();

		// split dataset at column 4 (boolean), threshold and categorialset S
		// isn't used in this example.
		// c++ has default parameters, java does not :'(
		// Subsets = SplitList.split(process_data, 0, -1.0, null, false);

		// NumericalOperations.sort(process_data, 0);
		//FileReader.printReadDataArrayList(process_data);

		// split at column 0 (numerical) with threshold 49
		//Subsets = SplitList.split(process_data, 0, 49.0, null, false);

		// find possible split thresholds
		List<Double> thresholds = new ArrayList<Double>();
		thresholds = NumericalOperations.findSplitLocations(process_data, 0);

		Double bestThreshold = 0.0;

		for(int i = 0; i < thresholds.size(); i++) {
System.out.println(thresholds.get(i)+",");
			// split for every threshold candidate and don't delete the used Attribute!
			//Subsets = SplitList.split(process_data, 0, thresholds.get(i), null, false);

			// calculate information gain and save best
			//TODO

		}

		// finally split with best Threshold
		//Subsets = SplitList.split(process_data, 0, bestThreshold, null);

		// split at column 1 (categorical) with set S
		//List<String> S = new ArrayList<String>();
		//S.add("bb");
		//S.add("bc");
		//S.add("bh");
		//Subsets = SplitList.split(process_data, 1, -1.0, S, false);

		//FileReader.printReadDataArrayList(Subsets.get(0));

	}
}
