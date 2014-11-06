package ml_ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NumericalOperations {
	
	public static List<List<Attribute>> sort(List<List<Attribute>> process_data, final int column) {

		// sort 2D List at column i, returns the sorted list
		
		// check for object type
		if (process_data.get(0).get(column) instanceof Numerical) {
		} else {
			System.err.println("Error: Column is not numerical, exiting program");
			System.exit(0);
		}

		// sort by value ascending
		process_data.sort(new Comparator<List<Attribute>>() {

			@Override
			public int compare(List<Attribute> o1, List<Attribute> o2) {
				Double data1 = (Double) o1.get(column).getData();
				Double data2 = (Double) o2.get(column).getData();
				return data1.compareTo(data2);
			}
		});

		return process_data;

	}
	
	public static List<Double> findSplitLocations(List<List<Attribute>> process_data, int column){
		List<Double> thresholds;
		thresholds = new ArrayList<Double>();
		
		// sort list by column
		sort(process_data, column);
		
		// for every entry in table, check 
		for( int i = 0; i < process_data.size()-1; i++ ){
			
			// if two following instances differ in target
			if((boolean)process_data.get(i).get(process_data.get(0).size()-1).getData() != (boolean)process_data.get(i+1).get(process_data.get(0).size()-1).getData()){
				
					// then save mean
					double mean = ((double) process_data.get(i).get(column).getData()+(double) process_data.get(i+1).get(column).getData())/2;
					thresholds.add(mean);
				
			}
		}
		
		return thresholds;
	}

}
