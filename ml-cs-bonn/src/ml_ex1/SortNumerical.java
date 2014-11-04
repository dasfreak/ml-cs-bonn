package ml_ex1;

import java.util.Comparator;
import java.util.List;

public class SortNumerical {
	// sort 2D Arraylist at column i
	public static List<List<Attribute>> sort(List<List<Attribute>> process_data, int column) {

		if (process_data.get(0).get(column) instanceof Numerical) {
			//System.out.println("OK");
		} else {
			System.err.println("Error: Column is nor numerical, exiting program");
			System.exit(0);
		}

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
