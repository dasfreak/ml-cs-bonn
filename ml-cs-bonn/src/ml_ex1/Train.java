package ml_ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Train {
	
	//list of list of attributes to store the data read from file
	
	public static Tree tree;
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Attribute[][] process_data1 = null; 
		List<List<Attribute>> process_data; 
		
		process_data= new ArrayList<List<Attribute>>();
		
		process_data1=FileReader.loadDatasetFromFile("data_exercise_1.csv", process_data, process_data1);
			
		
		/** ELIAS- the structure for the tree- I think it is good, Tree object and Node object with Arraylist of children and parent reference **/	
		tree=new Tree();
		//for each attribute calculate entropy 
		
		Node temp;
		Node nodeHighestEntropy=new Node(0, FileReader.pattern1[0], FileReader.nodesNames[0]); //ascribe values of first attribute to the temporary node
		nodeHighestEntropy.calculateEntropy(); //calculate entropy for the first attribute
		
		//finding node with the highest entropy (examining for each attribute)- first step of algorithm
		for (int i=1; i<FileReader.pattern1.length; i ++){
			temp=new Node(i, FileReader.pattern1[i], FileReader.nodesNames[i]); //ascribte subsequent node to the temporary variable
			temp.calculateEntropy(); //calculate entropy for temporary node
			
			if (temp.entropy-nodeHighestEntropy.entropy>0){ //check if old one is greater than new one (looking for maximum entropy)
				nodeHighestEntropy=temp; //if greater save node with maximum entropy to nodeHighestEntropy
			}
		}
		
		tree.setRoot(nodeHighestEntropy); //setting root node for the one with the highest entropy
		
		
//		FileReader.printReadDataArray(process_data1);
//		FileReader.printReadDataArrayList(process_data);


	
		/**MARK'S CODE BELOW- MARK PLEASE CHECK AND MERGE WITH ABOVE !!! 
		 IT HAS TO BE MERGED WITH THE NODE CLASS FUNCTION calculateEntropy
		 AS THERE WE WILL MAKE LOGICS OF SPLITS IF NECESSARY AND SAVING ENTROPY TO THE OBJECT PROPERTY.
		 I WILL START WRITING FUNCTION calculateEntropy TODAY AFTER MY LANGUAGE COURSE **/
		
//		List<List<List<Attribute>>> Subsets;
//		Subsets = new ArrayList<List<List<Attribute>>>();
//
//		// split dataset at column 4 (boolean), threshold and categorialset S
//		// isn't used in this example.
//		// c++ has default parameters, java does not :'(
//		// Subsets = SplitList.split(process_data, 0, -1, null);
//
//		// NumericalOperations.sort(process_data, 0);
//		// FileReader.printReadDataArrayList(process_data);
//
//		// split at column 0 (numerical) with threshold 48
//		// Subsets = SplitList.split(process_data, 0, 48, null);
//
//		// find possible split thresholds
//		List<Double> thresholds;
//		thresholds = new ArrayList<Double>();
//		thresholds = NumericalOperations.findSplitLocations(process_data, 0);
//
//		for (int i = 0; i < thresholds.size(); i++) {
//			
//			// split for every threshold candidate
//			Subsets = SplitList.split(process_data, 0, thresholds.get(i), null);
//			
//			// calculate information gain for every split and save best
//			//TODO
//			
//			Double bestThreshold = 0.0;
//			
//			//System.out.print(thresholds.get(i) + ",");
//		}
//
//		// split at column 1 (categorical) with set S
//		// List<String> S = new ArrayList<String>();
//		// S.add("bb");
//		// S.add("bc");
//		// S.add("bh");
//
//		// Subsets = SplitList.split(process_data, 1, -1, S);
//
//		// FileReader.printReadDataArrayList(Subsets.get(0));

	}
}