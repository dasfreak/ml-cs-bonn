package ml_ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Node {
	
	public static int nodeCounter = 0;
	public String name; //name of the node read from file
	public int index; //column of the node in file
	public int nodeNum;
	public double entropy;
	public int numRecords;
	public double informationGain;
	public Attribute attribute; //attribute of the node

	public ArrayList<Node> children;
    public Node parent=null;
    public Subset maximal = null;
    public List<Subset> subsets;
	private DisjointSets disjointSets; 
    
	
	//do not know yet if class_name is necessary here, we will see later on
	public Node(int index, Attribute attribute, String nodeName, Node parent){
		subsets        = new ArrayList<Subset>();
		this.name      = nodeName;
		this.attribute = attribute;
		this.index     = index; //column in our data set
		this.parent    = parent;
		this.children  = new ArrayList<Node>();
		nodeNum = nodeCounter;
		nodeCounter++;
	}
	
	public String toString(){
		 
		String str = "";
		String test="";
		String children = "";
		if ( maximal.attr instanceof Binary )
		{
			test = FileReader.nodesNames[this.index];
		}
		else if ( maximal.attr instanceof Categorical )
		{
			test = FileReader.nodesNames[this.index] +" in {";
			this.subsets.sort( new Comparator<Subset>() {

				public int compare(Subset o1, Subset o2) {
					return ((String)o1.attr.getData()).compareTo((String)o2.attr.getData());
				}
				});
			for ( Subset s: this.subsets)
			{
				if ( this.subsets.get(this.subsets.size()-1) == s )
				{
					test += s.attr.getData();
				}
				else
				{
					test += s.attr.getData() +",";
				}
			}
			test +="}";
		}
		
		else if ( maximal.attr instanceof Numerical )
		{
			test = FileReader.nodesNames[this.index]+" < " + maximal.attr.getData();
		}
		
		for ( Node n : this.children )
		{
			children += " "+n.nodeNum;
		}
		str += nodeNum + " "+test+children;
		for ( Node n : this.children)
		{
			str += "\n"+n.toString();
		}
		return str;
	}
	
	
	/* extracts segments for certain data column and saves this segment into segments list */
	public void extractSubsets(Attribute[][] attributes){
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
		
		//System.out.println("Sybset length: "+subsets.size());
	}
	
	
	public Attribute[][] sortArray(Attribute[][] attributes){
		
		Arrays.sort(attributes, new Comparator<Attribute[]>() {

			public int compare(Attribute[] o1, Attribute[] o2) {
				Double var1 = (Double) o1[index].getData();
	            Double var2 = (Double) o2[index].getData();
	            return var1.compareTo(var2);
			}
		});
		return attributes;
	}
	
	public List<DisjointSets> extractNumericalSubsets( Attribute[][] attributes ){
		
		int targetColumn = attributes[0].length-1;
		Subset below ;
		Subset above;
		Numerical belowMean;
		Numerical aboveMean;
		double mean;
		List<DisjointSets> subsets = new ArrayList<DisjointSets>();
		

		for ( int i=0; i<attributes.length-1 ; i++ ){
			// if two following rows differ in target data
			if((boolean)attributes[i][targetColumn].getData() != (boolean)attributes[i+1][targetColumn].getData() && !attributes[i][index].getData().equals((Double)attributes[i+1][index].getData())){ //so split can be done if numbers are the same				
					//do the split and calculate the entropy
					mean = ( (double)attributes[i][index].getData() + (double)attributes[i+1][index].getData() )/2.0;
					belowMean=new Numerical();
					aboveMean=new Numerical();
					
					if(mean == 0.0){
						System.out.println("index i: "+i);
					}
					
					belowMean.setData(mean); //set the attribute for mean
					below = new Subset(belowMean); //set the flat of isBelow for true
					below.setCutPlace(i);	//keep index where set was cut
					below.isBelow=true;
					
					aboveMean.setData(mean);	//set the attribute for mean
					above = new Subset(aboveMean);
					above.setCutPlace(i);	//keep index where set was cut
					above.isAbove=true;
					
					for ( int j=0; j<attributes.length; j++){
						//System.out.println("counter: "+j);
						if ( (double)attributes[j][index].getData() > mean ){
							above.numOccurrences++;
							if ((boolean) attributes[j][attributes[0].length-1].getData()) // target column is always boolean
								above.yesCount++;
							else
								above.noCount++;
						}
						else {
							below.numOccurrences++;
							if ((boolean) attributes[j][attributes[0].length-1].getData()) // target column is always boolean
								below.yesCount++;
							else
								below.noCount++;
						}
					}
					
					if(mean == 0.0){
						System.out.println("above nn: "+above.numOccurrences);
					}
					
					above.entropy = Entropy.calcEntropy( above.yesCount, above.noCount );
					below.entropy = Entropy.calcEntropy( below.yesCount,  below.noCount );
//					System.out.println("index: "+i+" numOccurencesBelow = " + below.numOccurrences + " numOccurencesHigher = "+above.numOccurrences + " mean = "+mean);
					subsets.add(new DisjointSets( below, above, mean ) );
			}
		}
		
		return subsets;
	}
	
	public void calculateInformationGain(Attribute[][] attributes, double entropyParent, int range) {	
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary ){ //FOR CATEGORICAL AND BINARY
			extractSubsets(attributes);		
			
			// :TODO: Marek: is this calculation correct we need the maximum information gain and not the maximal entropy !!
			double entropySegment = 0;
			for ( Subset s : subsets) {
				double segmentSize = s.yesCount + s.noCount;
				s.entropy = Entropy.calcEntropy(s.yesCount, s.noCount);
				entropySegment += ((double)segmentSize/(double)range)*s.entropy;
			}
			
			this.informationGain = entropyParent - entropySegment;	
			
			
			
			// TODO: change that
			double maxSubsetEntropy = 0;
			for ( Subset s : subsets) {
				if ( s.entropy > maxSubsetEntropy )
				{
					maxSubsetEntropy = s.entropy;
					this.maximal          = s;
				}
			}
			this.entropy = maximal.entropy;
			
		}
		else if (this.attribute instanceof Numerical) {	//FOR NUMERICAL
			attributes=sortArray(attributes); //SORT ARRAY FIRST
					
//			System.out.println("===================SORTED ARRAY PRINT==================== "+index);
//			for (int it=0; it<attributes.length; it++){
//				for (int jt=0; jt<attributes[0].length; jt++){
//					System.out.print( attributes[it][jt].getData()+" , ");
//				}
//				System.out.println();
//			}
//			System.out.println("===================END SORTED ARRAY PRINT==================== "+ index);
			
			
			List<DisjointSets> subsets = extractNumericalSubsets(attributes);
			
//			System.out.println("=============");
			for ( DisjointSets s : subsets ) {
				// assuming range is the father entropy
				s.entropySubsets = s.set1.entropy * ( (double)s.set1.numOccurrences / (double)(range) ) +
						s.set2.entropy * ( (double)s.set2.numOccurrences / (double)(range) );
				s.informationGain = entropyParent - s.entropySubsets;
//				System.out.println("mean = "+s.mean+ ", set1 Entropy = "+s.set1.entropy + ", set1 num occurences = "+ s.set1.numOccurrences + 
//						", set1 yesCount = " + s.set1.yesCount + ", set1 noCount = "+s.set1.noCount+
//						", set2 Entropy = "+s.set2.entropy +
//						", set2 num occurences = "+ s.set2.numOccurrences + 
//						", set2 yesCount = " + s.set2.yesCount + ", set2 noCount = "+s.set2.noCount );
			}
			
			double maxInformationGain = 0;
			for ( DisjointSets s : subsets) {
				if ( s.informationGain > maxInformationGain )
				{
					maxInformationGain = s.informationGain;
					this.disjointSets = s;
				}
			}
			this.informationGain  = maxInformationGain;
			
//			System.out.println("cut place 1 is: "+disjointSets.set1.getCutPlace());
//			System.out.println("cut place 2 is: "+disjointSets.set2.getCutPlace());
//			System.out.println("occurencies of set 1: "+disjointSets.set1.numOccurrences);
//			System.out.println("occurencies of set 2: "+disjointSets.set2.numOccurrences);
//			System.out.println("mean is: "+disjointSets.set2.attr.getData());
			
			
			
			
			// TODO: change that
			double max1=0;
			if(this.disjointSets.set1.entropy>this.disjointSets.set2.entropy){
				this.entropy = this.disjointSets.set1.entropy;
				this.maximal = this.disjointSets.set1;
			}
			else{
				this.entropy = this.disjointSets.set2.entropy;
				this.maximal = this.disjointSets.set2;
			}
		}
		else{
			System.out.println("some error");
		}

	}
}
