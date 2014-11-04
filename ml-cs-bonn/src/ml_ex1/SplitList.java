package ml_ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitList {

	public static List<List<List<Attribute>>> split(List<List<Attribute>> process_data, int column, double threshold, List<String> S) {

		//Split depends on datatype, returns 3D list containing the new subsets

		List<List<List<Attribute>>> returnSet;
		List<List<Attribute>> leftSubset;
		List<List<Attribute>> rightSubset;

		returnSet = new ArrayList<List<List<Attribute>>>();
		leftSubset = new ArrayList<List<Attribute>>();
		rightSubset = new ArrayList<List<Attribute>>();

		// check for datatype and create left and right Subset (delete used Attribute)
		if (process_data.get(0).get(column) instanceof Numerical) {

			for (int i=0; i<process_data.size(); i++){
				if((double) process_data.get(i).get(column).getData() >= threshold){	
					process_data.get(i).remove(column);
					rightSubset.add(process_data.get(i));
				}else{
					process_data.get(i).remove(column);
					leftSubset.add(process_data.get(i));
				}
			}
		}
		else if (process_data.get(0).get(column) instanceof Categorical) {
			for (int i=0; i<process_data.size(); i++){
				if(S.contains((String) process_data.get(i).get(column).getData())){	
					process_data.get(i).remove(column);
					rightSubset.add(process_data.get(i));
				}else{
					process_data.get(i).remove(column);
					leftSubset.add(process_data.get(i));
				}
			}
		}
		else if (process_data.get(0).get(column) instanceof Binary) {
			for (int i=0; i<process_data.size(); i++){
				if((boolean) process_data.get(i).get(column).getData() == true){	
					process_data.get(i).remove(column);
					rightSubset.add(process_data.get(i));
				}else{
					process_data.get(i).remove(column);
					leftSubset.add(process_data.get(i));
				}
			}

		}
		else{
			System.err.println("Unknown Attribute, exiting program");
			System.exit(0);
		}				


		returnSet.add(rightSubset); // test was true
		returnSet.add(leftSubset);// test was false

		return returnSet;

	}

}
