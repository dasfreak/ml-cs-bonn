package ml_ex1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		buildWholeTree( targetEntropy, range );
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
		
		targetEntropy = Entropy.calcEntropy(yesCount, noCount);
		
		return targetEntropy;
	}
 void buildWholeTree( double entropy, double range ){
		Node currentNode        = null;
		Node nodeHighestEntropy = null;
		double highestInformationGain = 0.0;
		Attribute[][] attributes = DataStructureSingleton.getInstance1();

		
		int trgColumn = FileReader.pattern1.length - 1;
		System.out.println();
		Node root = new Node( trgColumn, DataStructureSingleton.getPattern1()[trgColumn], DataStructureSingleton.getNodeNames()[trgColumn], null );
		root.entropy=calculateTargetEntropy(attributes);
		
		Node parent = root;
		String[] columns = new String[FileReader.pattern1.length -1];
		System.arraycopy(FileReader.nodesNames, 0, columns, 0, FileReader.pattern1.length - 1 );
		
		tree = new Tree( root );
		
		for ( int j = 0; j < FileReader.pattern1.length-1; j++ )
		{
			System.out.println();
			System.out.println("Itteration of the main loop: "+j);
			System.out.println("Parent entropy: " + parent.entropy);
			highestInformationGain=0.0;
			boolean isChange=false;
			for ( int i = 0; i < columns.length; i++ ){
				if ( null == columns[i] )
				{
					continue;
				}
				currentNode = new Node(i, DataStructureSingleton.getPattern1()[i], DataStructureSingleton.getNodeNames()[i], parent); //ascribte subsequent node to the temporary variable
				parent.children.add(currentNode);
				
				//System.out.println( currentNode.parent.entropy);
				currentNode.calculateInformationGain( attributes, currentNode.parent.entropy, attributes.length); //calculate entropy for temporary node
				System.out.println("current information gain: " +currentNode.informationGain+" for index: "+i);
				if ( currentNode.informationGain > highestInformationGain ){ //check if old one is greater than new one (looking for maximum entropy)
					isChange=true;
					nodeHighestEntropy = currentNode; //if greater save node with maximum entropy to nodeHighestEntropy
					highestInformationGain = currentNode.informationGain;
				}
			}
			if(isChange==false){
				System.out.println("end of the tree, more splits do not give better results for that branch");
				break;
			}
			
			System.out.println("Attribute selected as a node with index "+ nodeHighestEntropy.index);
			columns[nodeHighestEntropy.index] = null; // done with this column
			
			parent = nodeHighestEntropy;

			attributes = getSubsetOfTable( attributes, nodeHighestEntropy.index, nodeHighestEntropy.maximal );
		}
		
		

	}

	private Attribute[][] getSubsetOfTable( Attribute[][] grandSetAttributes, int index, Subset subset) {
		
		Attribute[][] subArray = new Attribute[subset.numOccurrences][grandSetAttributes[0].length];
		int j = 0;
		for ( int i = 0; i < grandSetAttributes.length; i++ )
		{
			if ( subset.attr.getData().equals(grandSetAttributes[i][index].getData() ) )
			{
				System.arraycopy(grandSetAttributes[i], 0, subArray[j], 0, grandSetAttributes[0].length);
				j++;
			}
		}
		
		//printing subArray for check
		System.out.println();
		System.out.println("Array after narrowing on index: "+index);
		for (int it=0; it<subArray.length; it++){
			for (int jt=0; jt<subArray[0].length; jt++){
				System.out.print( subArray[it][jt].getData()+" , ");
			}
			System.out.println();
		}
		
		return subArray;
	}
}
