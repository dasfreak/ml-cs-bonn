package ml_ex1;

public class TdidtAlgo {

	private Tree    tree;
	private double  targetEntropy;
	private int     range;
	private int     yesCount;
	private int     noCount;
	private int     numRecords;
	
	public  TdidtAlgo()
	{
		this.range = DataStructureSingleton.getInstance1().length;
		calculateTargetEntropy(DataStructureSingleton.getInstance1());	
	}
	
	/*calculating entropy of the target column*/
	private void calculateTargetEntropy(Attribute[][] process_data1){
		
		for ( int i = 0; i< process_data1.length; i++ ){
			
			// casting to boolean is possible since we know that it is the last column (a bit sloppy)...
			if ( (boolean) process_data1[i][ process_data1[0].length-1 ].getData() ){
				yesCount++; //adding all YESs in target column
			}
			else{
				noCount++; //adding all NOs in target column
			}
		}
		
		numRecords = yesCount + noCount; //calculating all records
		
		targetEntropy = Entropy.calcEntropy(yesCount, noCount);
	}
	
	public void findHighestEntropy( double entropy, double range ){
		Node temp;
		Node nodeHighestEntropy=new Node( 0, DataStructureSingleton.getPattern1()[0], DataStructureSingleton.getNodeNames()[0] ); //ascribe values of first attribute to the temporary node
		nodeHighestEntropy.calculateInformationGain(entropy, range); //calculate entropy for the first attribute
		
		//finding node with the highest entropy (examining for each attribute)- first step of algorithm
		//the upper range is -1 because the last attribute is the target
		for (int i=1; i<FileReader.pattern1.length-1; i ++){
			temp=new Node(i, DataStructureSingleton.getPattern1()[i], DataStructureSingleton.getNodeNames()[i]); //ascribte subsequent node to the temporary variable
			temp.calculateInformationGain(entropy, range); //calculate entropy for temporary node
			//System.out.println(temp.information_gain);
			
			if ( temp.informationGain-nodeHighestEntropy.informationGain > 0 ){ //check if old one is greater than new one (looking for maximum entropy)
				nodeHighestEntropy=temp; //if greater save node with maximum entropy to nodeHighestEntropy
			}
		}
		
		tree = new Tree( nodeHighestEntropy );
	}
}
