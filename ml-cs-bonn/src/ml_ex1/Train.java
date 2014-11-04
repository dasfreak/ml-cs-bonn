package ml_ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Train {

	// list of list of attributes to store the data read from file

	public static void main(String[] args) throws FileNotFoundException,
			IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Attribute[][] process_data1 = null;
		List<List<Attribute>> process_data;

		process_data = new ArrayList<List<Attribute>>();

		process_data1 = FileReader.loadDatasetFromFile("data_exercise_1.csv",
				process_data, process_data1);

		// FileReader.printReadDataArray(process_data1);
		// FileReader.printReadDataArrayList(process_data);

		// SortNumerical.sort(process_data, 0);
		// System.out.println("List sorted by column 0: ");
		// FileReader.printReadDataArrayList(process_data);

		// super cool 3D List: 0 where test is true, 1 for false
		List<List<List<Attribute>>> Subsets;
		Subsets = new ArrayList<List<List<Attribute>>>();

		// split dataset at column 4 (boolean), threshold and categorialset S
		// isn't used in this example.
		// c++ has default parameters, java does not :'(
		// Subsets = SplitList.split(process_data, 0, -1, null);

		// NumericalOperations.sort(process_data, 0);
		// FileReader.printReadDataArrayList(process_data);

		// split at column 0 (numerical) with threshold 48
		// Subsets = SplitList.split(process_data, 0, 48, null);

		// find possible split thresholds
		List<Double> thresholds;
		thresholds = new ArrayList<Double>();
		thresholds = NumericalOperations.findSplitLocations(process_data, 0);

		for (int i = 0; i < thresholds.size(); i++) {
			
			// split for every threshold candidate
			Subsets = SplitList.split(process_data, 0, thresholds.get(i), null);
			
			// calculate information gain for every split and save best
			//TODO
			
			Double bestThreshold = 0.0;
			
			//System.out.print(thresholds.get(i) + ",");
		}

		// split at column 1 (categorical) with set S
		// List<String> S = new ArrayList<String>();
		// S.add("bb");
		// S.add("bc");
		// S.add("bh");

		// Subsets = SplitList.split(process_data, 1, -1, S);

		// FileReader.printReadDataArrayList(Subsets.get(0));
	}

}