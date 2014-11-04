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
		tree.calculateTargetEntropy(process_data1);
		//for each attribute calculate entropy 
		
		//calculate the entropy of target and later pass it to other nodes where it is necessary for calculations
		//Target target=new Target();
		//double target_entropy=
		
		Node temp;
		Node nodeHighestEntropy=new Node(0, FileReader.pattern1[0], FileReader.nodesNames[0]); //ascribe values of first attribute to the temporary node
		nodeHighestEntropy.calculateEntropy(); //calculate entropy for the first attribute
		
		//finding node with the highest entropy (examining for each attribute)- first step of algorithm
		//the upper range is -1 because the last attribute is the target
		for (int i=1; i<FileReader.pattern1.length-1; i ++){
			temp=new Node(i, FileReader.pattern1[i], FileReader.nodesNames[i]); //ascribte subsequent node to the temporary variable
			temp.calculateEntropy(); //calculate entropy for temporary node
			
			if (temp.entropy-nodeHighestEntropy.entropy>0){ //check if old one is greater than new one (looking for maximum entropy)
				nodeHighestEntropy=temp; //if greater save node with maximum entropy to nodeHighestEntropy
			}
		}
		
	
		tree.setRoot(nodeHighestEntropy); //setting root node for the one with the highest entropy (greedy algorithm)
		
		
//		FileReader.printReadDataArray(process_data1);
//		FileReader.printReadDataArrayList(process_data);


	
		/**
		 IT HAS TO BE MERGED WITH THE NODE CLASS FUNCTION calculateEntropy
		 AS THERE WE WILL MAKE LOGICS OF SPLITS IF NECESSARY AND SAVING ENTROPY TO THE OBJECT PROPERTY.
		 I WILL START WRITING FUNCTION calculateEntropy TODAY AFTER MY LANGUAGE COURSE **/
	}
}