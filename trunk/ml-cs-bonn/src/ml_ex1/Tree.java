package ml_ex1;

public class Tree {
	public Node root;
	public double entropy; //used in other nodes for calculation
	public double yes_number;
	public double no_number;
	public double all_records;
	
	public void setRoot(Node root){
		this.root=root; //setting root node
		this.root.entropy=entropy; //ascribing target entropy to note on top of the tree (parent node)
		this.root.all_records=all_records;
	}
	
	/*calculating entropy of the target column*/
	public void calculateTargetEntropy(Attribute[][] process_data1){
		
		for (int i=0; i<process_data1.length; i++){
			if ((boolean) process_data1[i][ process_data1[0].length-1 ].getData()){
				yes_number++; //adding all YESs in target column
			}
			else{
				no_number++; //adding all NOs in target column
			}
		}
		
		all_records=yes_number+no_number; //calculating all records
		
		entropy=Entropy.calcEntropy(yes_number, no_number, all_records );
	}
}

