package ml_ex1;

import java.util.Comparator;
import java.util.List;

public class SortNumerical {
	// sort 2D Arraylist at column i
	public static List<List<Attribute>> sort(List<List<Attribute>> process_data, int column) {

		// check for object type
		if (process_data.get(0).get(column) instanceof Numerical) {
			//System.out.println("OK");
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

}
