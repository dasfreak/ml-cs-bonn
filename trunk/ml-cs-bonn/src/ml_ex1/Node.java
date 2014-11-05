package ml_ex1;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public String name; //name of the node read from file
	public int index; //column of the node in file
	public double entropy;
	public int numRecords;
	public double informationGain;
	public Attribute attribute; //attribute of the node

	public ArrayList<Node> children;
    public Node parent=null;
	Subset maximal = null;
    public List<Subset> subsets; 
    
	
	//do not know yet if class_name is necessary here, we will see later on
	public Node(int index, Attribute attribute, String nodeName, Node parent){
		subsets        = new ArrayList<Subset>();
		this.name      = nodeName;
		this.attribute = attribute;
		this.index     = index; //column in our data set
		this.parent    = parent;
		this.children  = new ArrayList<Node>();
	}
	
	
	/* extracts segments for certain data column and saves this segment into segments list */
	public void extractSegments(Attribute[][] attributes){
		for (int i=0; i<attributes.length ; i++){
			boolean isFound=false;
			Subset initial = new Subset(attributes[i][this.index]);
			for( Subset s : subsets) {
				if (s.attr.getData().equals( initial.attr.getData() ) ){
					isFound = true;
					s.numOccurrences++;
					if ((boolean) attributes[i][attributes[0].length-1].getData()) // target column is always boolean
						s.yesCount++;
					else
						s.noCount++;
				}
			}
			if ( !isFound ){
				Subset temp=new Subset(attributes[i][index]);
				temp.numOccurrences++;
				if ((boolean) attributes[i][attributes[0].length-1].getData())
					temp.yesCount++;
				else
					temp.noCount++;
				subsets.add(temp);
			}
		}
	}
	
	public void calculateInformationGain(Attribute[][] attributes, double entropyParent, int range) {	
		
		
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary ){
			extractSegments(attributes);
			
			/**uncomment if you want to see how many yes and no are there for each segment **/
//			System.out.println("column: "+index+" segments size: "+segments.size());
//			for (int s=0; s<segments.size(); s++){
//				System.out.println("segment: "+s+ ", number of yes: "+ segments.get(s).yes_number);
//				System.out.println("segment: "+s+ ", number of no: "+ segments.get(s).no_number);
//			}
//			System.out.print('\n');
			
			double entropySegment = 0;
			for ( Subset s : subsets) {
				double segmentSize = s.yesCount + s.noCount;
				s.entropy = ((double)segmentSize/(double)range)*Entropy.calcEntropy(s.yesCount, s.noCount);
				entropySegment += s.entropy;
//				System.out.println(all_for_current_segment);
			}
			
			double maxSubsetEntropy = 0;
			for ( Subset s : subsets) {
				if ( s.entropy > maxSubsetEntropy )
				{
					maxSubsetEntropy = s.entropy;
					maximal          = s;
				}
			}
			
			this.informationGain = entropyParent - entropySegment;
			
		}
		/**TODO: sort and split on numerical data**/
//		else if (this.attribute instanceof Numerical){
//			System.out.println("2");
//		}

	}
}
