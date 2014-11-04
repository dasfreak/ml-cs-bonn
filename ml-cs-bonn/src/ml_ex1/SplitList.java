package ml_ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitList {

	public static List<List<List<Attribute>>> split(List<List<Attribute>> dataset, int column, Double threshold, List<String> S, boolean deletAttribute) {

		//Split depends on datatype, returns 3D list containing the new subsets

		List<List<List<Attribute>>> returnSet;
		List<List<Attribute>> leftSubset;
		List<List<Attribute>> rightSubset;

		returnSet = new ArrayList<List<List<Attribute>>>();
		leftSubset = new ArrayList<List<Attribute>>();
		rightSubset = new ArrayList<List<Attribute>>();

		// check for datatype and create left and right Subset (delete used Attribute)
		if (dataset.get(0).get(column) instanceof Numerical) {

			for (int i=0; i<dataset.size(); i++){
				if((double) dataset.get(i).get(column).getData() >= threshold){	
						if(deletAttribute) dataset.get(i).remove(column);
					rightSubset.add(dataset.get(i));
				}else{
						if(deletAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i));
				}
			}
		}
		else if (dataset.get(0).get(column) instanceof Categorical) {
			for (int i=0; i<dataset.size(); i++){
				if(S.contains((String) dataset.get(i).get(column).getData())){	
					if(deletAttribute) dataset.get(i).remove(column);
					rightSubset.add(dataset.get(i));
				}else{
					if(deletAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i));
				}
			}
		}
		else if (dataset.get(0).get(column) instanceof Binary) {
			for (int i=0; i<dataset.size(); i++){
				if((boolean) dataset.get(i).get(column).getData() == true){	
					if(deletAttribute) dataset.get(i).remove(column);
					rightSubset.add(dataset.get(i));
				}else{
					if(deletAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i));
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
