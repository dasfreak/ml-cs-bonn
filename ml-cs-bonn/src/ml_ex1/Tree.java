package ml_ex1;

public class Tree {
	public Node root;
	public double yes_number;
	public double no_number;
	public double all_records;
	
	public void setRoot(Node root){
		this.root=root; //setting root node
		this.root.all_records=all_records;
	}
	
	/*calculating entropy of the target column*/
	public double calculateTargetEntropy(Attribute[][] process_data1){
		
		for (int i=0; i<process_data1.length; i++){
			if ((boolean) process_data1[i][ process_data1[0].length-1 ].getData()){
				yes_number++; //adding all YESs in target column
			}
			else{
				no_number++; //adding all NOs in target column
			}
		}
		
		all_records=yes_number+no_number; //calculating all records
		
		return Entropy.calcEntropy(yes_number, no_number);
	}
	
	public void go(double entropy, double range){
		Node temp;
		Node nodeHighestEntropy=new Node(0, DataStructureSingleton.getPattern1()[0], DataStructureSingleton.getNodeNames()[0]); //ascribe values of first attribute to the temporary node
		nodeHighestEntropy.calculateInformationGain(entropy, range); //calculate entropy for the first attribute
		
		//finding node with the highest entropy (examining for each attribute)- first step of algorithm
		//the upper range is -1 because the last attribute is the target
		for (int i=1; i<FileReader.pattern1.length-1; i ++){
			temp=new Node(i, DataStructureSingleton.getPattern1()[i], DataStructureSingleton.getNodeNames()[i]); //ascribte subsequent node to the temporary variable
			temp.calculateInformationGain(entropy, range); //calculate entropy for temporary node
			//System.out.println(temp.information_gain);
			
			if (temp.information_gain-nodeHighestEntropy.information_gain>0){ //check if old one is greater than new one (looking for maximum entropy)
				nodeHighestEntropy=temp; //if greater save node with maximum entropy to nodeHighestEntropy
			}
		}
		
		setRoot(nodeHighestEntropy);
		
		
		
	}
}

