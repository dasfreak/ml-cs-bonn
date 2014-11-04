package ml_ex1;

public class Tree {
	public Node root;
	public static double target_entropy; //used in other nodes for calculation
	public static double yes_number;
	public static double no_number;
	
	
	public void setRoot(Node root){
		this.root=root;
	}
	
	static double log(double x, int base)
	{
	    return (double) (Math.log(x) / Math.log(base));
	}
	
	public void calculateTargetEntropy(Attribute[][] process_data1){
		int yes=0;
		int no=0;
		
		for (int i=0; i<process_data1.length; i++){
			if ((boolean) process_data1[i][ process_data1[0].length-1 ].getData()){
				yes_number++;
			}
			else{
				no_number++;
			}
		}
		
		double all_records=yes_number+no_number;
		
		target_entropy=(yes_number/(all_records))*log((1.0/(yes_number/(all_records))),2)+(no_number/(all_records))*log((1.0/(no_number/(all_records))),2);
	}
}

