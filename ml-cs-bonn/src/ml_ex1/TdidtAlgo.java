package ml_ex1;

import java.util.ArrayList;
import java.util.List;


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
		
		// set the root node
		Attribute[][] attributes = DataStructureSingleton.getInstance1();
		initTree(); //initialize tree
		
		String[] columns = new String[FileReader.pattern1.length -1]; //set 
		System.arraycopy(FileReader.nodesNames, 0, columns, 0, FileReader.pattern1.length - 1 );
		columns[tree.root.index] = null; // done with this column as it is the root one, it will not be used in further tree
		
		//parent - to add somewhere
		calcTree(this.tree.root , attributes ,  range,  columns); //build the tree for the rest of nodes
		
	}
	
	/*calculating entropy of the target column*/
	private double calculateTargetEntropy(Attribute[][] process_data1){
		
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
		return Entropy.calcEntropy(yesCount, noCount);
	}
	
	
	
	/**Initialize tree, select parent node and place it in the root **/
	public void initTree(){
		
		Attribute[][] attributes = DataStructureSingleton.getInstance1(); // get the array read from file
		int trgColumn = FileReader.pattern1.length - 1; //get the last (Target column)
		Node target = new Node( trgColumn, DataStructureSingleton.getPattern1()[trgColumn], DataStructureSingleton.getNodeNames()[trgColumn], null );
		target.entropy=calculateTargetEntropy(attributes); //calculate entropy of the target column for a start
		this.targetEntropy=target.entropy;
		
		double highestInformationGain=0.0;
		Node nodeHighestEntropy = null;
		
		//iterate over all attributes to find for which one there is a maxium information gain
		for ( int i = 0; i < FileReader.pattern1.length-1; i++ )
		{
			Node currentNode = new Node(i, DataStructureSingleton.getPattern1()[i], DataStructureSingleton.getNodeNames()[i], null); //ascribte subsequent node to the temporary variable
			currentNode.getAllSubsets(attributes); //get all subsets of potential root node
			currentNode.calculateInformationGain( attributes, target.entropy, attributes.length); //calculate information gain for temporary node
			System.out.println("Information_Gain{"+ FileReader.nodesNames[currentNode.index]+"} = "+currentNode.informationGain);
			if ( currentNode.informationGain > highestInformationGain ){ //check if old one is greater than new one (looking for maximum entropy)
				nodeHighestEntropy = currentNode; //if greater save node with maximum entropy to nodeHighestEntropy
				highestInformationGain = currentNode.informationGain;
			}
		}
		
		System.out.println("ROOT a node with index "+ nodeHighestEntropy.index);
		
		this.tree = new Tree( nodeHighestEntropy );	//initialize tree with the final root node
		
		System.out.println("==> Highest Information_Gain{"+ FileReader.nodesNames[nodeHighestEntropy.index]+"} = "+nodeHighestEntropy.informationGain);
		
	}
	
	
	/** build the whole tree, for the first call the data are  (tree.root , attributes , targetEntropy, range) **/
	void calcTree(Node parentNode, Attribute[][] attributes ,  double range,  String[] columnsLeft){
		System.out.println("===> [method Entry] ===> calcTree");
		Node newNode=null;
		Attribute[][] newAttributes;
		String[] newColumnsLeft = new String[columnsLeft.length];
		
		for (Subset parentSubset : parentNode.subsets){ //iterate over all subsets for parent node and check which node should be inserted after that
			//narrow down the values in array according for selected subset
			System.out.println("Filtering on: {"+FileReader.nodesNames[parentNode.index]+"->"+parentSubset.attr.getData()+"}"); 
	
			newAttributes = getSubsetOfTable( attributes, parentNode.index , parentSubset );
			System.arraycopy(columnsLeft, 0, newColumnsLeft, 0, columnsLeft.length);
			
			
			double highestInformationGain=0.0;
			Node nodeHighestInformationGain = null;
			
			// if entropy equals 0 for the subset it means we have YES or NO. We save it into the variable of subset and go further
			if (parentSubset.entropy==0){
				System.out.print("\t==> No more branching Result of {"+parentSubset.attr.getData() +"} = ");
				if(parentSubset.noCount>0){
					parentSubset.finalResult=false;
					System.out.println(parentSubset.finalResult);
				}
				else{
					parentSubset.finalResult=true;
					System.out.println(parentSubset.finalResult);
				}
				
				System.out.println();
				Attribute t = new Binary();
			    t.setData( parentSubset.finalResult ? "yes" : "no");
				parentNode.addChild(new Node(0, t, DataStructureSingleton.getNodeNames()[parentNode.index], parentNode));
				continue;
			}
			
			for (int j=0; j<newColumnsLeft.length; j++){ //for each attribute which was not used until now
				if ( null == newColumnsLeft[j] ){
					continue;
				}

				newNode = new Node(j, DataStructureSingleton.getPattern1()[j], DataStructureSingleton.getNodeNames()[j], parentNode); //create new node if it was not used before				
				newNode.getAllSubsets(newAttributes); //extract the subsets for the new node
				newNode.calculateInformationGain( newAttributes, parentSubset.entropy, newAttributes.length); //calculate information gain
				System.out.println("Information_Gain for going on branch: {"+FileReader.nodesNames[parentNode.index]+"->"+FileReader.nodesNames[newNode.index]+"}\t= "+newNode.informationGain);
//				System.out.println("particular information gain is: "+newNode.informationGain);
//				System.out.println("range passed to gain is: "+newAttributes.length);
//				System.out.println("");
				
				
				// check which node we should add so that information gain in reference to parent subset is the highest
				if ( newNode.informationGain > highestInformationGain ){ //check if old one is greater than new one (looking for maximum entropy)
					nodeHighestInformationGain = newNode; //if greater save node with maximum entropy to nodeHighestEntropy
					highestInformationGain = newNode.informationGain;
				}
			}
			
			// if we arrived here that means that we check what node should be added for certain subset and now we add it
			if ( nodeHighestInformationGain == null ){
				System.out.println("nodeHighest is null ");
				continue;
			}
			else{
				System.out.println("==> Highest Information_Gain is going on branch: {"+FileReader.nodesNames[parentNode.index]+"->"+FileReader.nodesNames[nodeHighestInformationGain.index]+"}\t= "+nodeHighestInformationGain.informationGain);
			}
				
			parentNode.addChild(nodeHighestInformationGain);
			newColumnsLeft[nodeHighestInformationGain.index] = null; // done with this column
					
			//execute recursively
			calcTree(nodeHighestInformationGain, newAttributes, newAttributes.length, newColumnsLeft ); 
		}
	}

	
	
	/** gets the smaller table for certain subset **/
	private Attribute[][] getSubsetOfTable( Attribute[][] grandSetAttributes, int index, Subset subset) {
		
//		Attribute[][] subArray = new Attribute[subset.numOccurrences][grandSetAttributes[0].length];
		ArrayList<Attribute[]> subArray = new ArrayList<Attribute[]>();
		
		//System.out.println("number of occurencies fo subset: "+subset.numOccurrences);
		int j = 0;
		
		if(subset.attr instanceof Categorical || subset.attr instanceof Binary){ //create array for categorical or binary comparison
			for ( int i = 0; i < grandSetAttributes.length; i++ )
			{
				//System.out.println(subset.attr.getData());
				//System.out.println(grandSetAttributes[i][index].getData());
				if ( subset.attr.getData().equals(grandSetAttributes[i][index].getData() ) )
				{
					subArray.add(grandSetAttributes[i].clone());
				}
			}
		}
		else{ //create array for numerical comparison	
			for ( int i = 0; i < grandSetAttributes.length; i++ )
			{
				if (subset.isAbove==true){
					//System.out.println("mean of subset: "+(Double)subset.attr.getData());
					//System.out.println("value from arrtributes"+(Double)grandSetAttributes[i][index].getData());
					if ( (double)grandSetAttributes[i][index].getData() > (double)subset.attr.getData() )
					{
						subArray.add(grandSetAttributes[i].clone());
					}
				}
				else if (subset.isBelow==true){
					if ( (double)grandSetAttributes[i][index].getData() <= (double)subset.attr.getData() )
					{
						subArray.add(grandSetAttributes[i].clone());
					}
				}
				else{
					System.err.println("some error with isAbove and isBelow");
				}
			}
		}
		//printing subArray for check
	//	System.out.println("Array after narrowing on index: "+index);
		
		Attribute[][] subArrayConv = new Attribute[subArray.size()][grandSetAttributes[0].length];
		
		int l = 0;
		for ( Attribute[] arr: subArray )
		{
			subArrayConv[l++] = arr;
		}
		
		for (int it=0; it<subArrayConv.length; it++){
			for (int jt=0; jt<subArrayConv[0].length; jt++){
				System.out.print( subArrayConv[it][jt].getData());
				if ( jt != subArrayConv[0].length -1 )
				{
					System.out.print(",\t");
				}
			}
			System.out.println();
		}
		
		System.out.println();
		return subArrayConv;
	}

	public void printTree() {
		System.out.println(" ======== Tree Printing Began ========");
		System.out.println(tree);
		System.out.println(" ======== Tree Printing Ended ========");
	}
}
