package ml_ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitList {

	public static List<List<List<Attribute>>> split(List<List<Attribute>> dataset, int column, Double threshold, List<String> S, boolean deleteAttribute) {

		//Split depends on datatype, returns 3D list containing the new subsets

		List<List<List<Attribute>>> returnSet;
		List<List<Attribute>> leftSubset;
		List<List<Attribute>> rightSubset;

		returnSet = new ArrayList<List<List<Attribute>>>();
		leftSubset = new ArrayList<List<Attribute>>();
		rightSubset = new ArrayList<List<Attribute>>();

		// check for datatype and create left and right Subset (delete used Attribute)
		if (dataset.get(0).get(column) instanceof Numerical) { // case: numerical

			for (int i=0; i<dataset.size(); i++){
				if((double) dataset.get(i).get(column).getData() >= threshold){	// split with given threshold
						if(deleteAttribute) dataset.get(i).remove(column); // only delete attribut if wanted
					rightSubset.add(dataset.get(i)); // push to new Subset, where tests are true
				}else{
						if(deleteAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i)); // push to new Subset, where tests are false
				}
			}
		}
		else if (dataset.get(0).get(column) instanceof Categorical) { // case: categorial
			for (int i=0; i<dataset.size(); i++){
				if(S.contains((String) dataset.get(i).get(column).getData())){ // split depending on given attributes
					if(deleteAttribute) dataset.get(i).remove(column);
					rightSubset.add(dataset.get(i));
				}else{
					if(deleteAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i));
				}
			}
		}
		else if (dataset.get(0).get(column) instanceof Binary) { // case binary
			for (int i=0; i<dataset.size(); i++){
				if((boolean) dataset.get(i).get(column).getData() == true){	 // split into true and false
					if(deleteAttribute) dataset.get(i).remove(column);
					rightSubset.add(dataset.get(i));
				}else{
					if(deleteAttribute) dataset.get(i).remove(column);
					leftSubset.add(dataset.get(i));
				}
			}

		}
		else{ // if unknown exit with error
			System.err.println("Unknown Attribute, exiting program");
			System.exit(0);
		}				


		returnSet.add(leftSubset);// test was false
		returnSet.add(rightSubset); // test was true

		return returnSet;

	}

}
