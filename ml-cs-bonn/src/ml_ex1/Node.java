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
	public int numericalSubset1;
	public int numericalSubset2;
	
	
	private ArrayList<Node> children;
    public Node parent=null;
    public List<Subset> subsets;
	//private DisjointSets disjointSets; 
    
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	//do not know yet if class_name is necessary here, we will see later on
	public Node(int index, Attribute attribute, String nodeName, Node parent){
		subsets        = new ArrayList<Subset>();
		this.name      = nodeName;
		this.attribute = attribute;
		this.index     = index; //column in our data set
		this.parent    = parent;
		this.children  = new ArrayList<Node>();
		nodeNum = nodeCounter;
	}
	public void addChild(Node node)
	{
		children.add(node);
		nodeCounter++;
	}
	public String toString(){
		 
		String str = "";
		String test="";
		String children = "";
		if ( attribute instanceof Binary )
		{
			test = FileReader.nodesNames[this.index];
		}
		else if ( attribute instanceof Categorical )
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
		
		else if ( attribute instanceof Numerical )
		{
			test = FileReader.nodesNames[this.index]+" < " + attribute.getData();
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
		
		//calculating number of yes, no, occurrences for certain array for each subset
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
		
		
		//calculating entropy for each subset
		for (int i=0; i<subsets.size(); i++){ 
			subsets.get(i).entropy = Entropy.calcEntropy( subsets.get(i).yesCount, subsets.get(i).noCount );
		}
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
	
	public void extractNumericalSubsets( Attribute[][] attributes ){
		
		int targetColumn = attributes[0].length-1;
		Subset below ;
		Subset above;
		Numerical belowMean;
		Numerical aboveMean;
		double mean;
		
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
					subsets.add(below);
					subsets.add(above);
			}
		}
	}
	
	
	public List<Subset> getAllSubsets( Attribute[][] attributes){
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary ){ //FOR CATEGORICAL AND BINARY
			extractSubsets(attributes);	 //extract subset for attributes
		}
		else if (this.attribute instanceof Numerical) {
			attributes=sortArray(attributes); //SORT ARRAY FIRST
			extractNumericalSubsets(attributes); //extract subset for each possible split
		}
		else{
			System.err.println("some error"); //else some error, sth wrong
		}
		
		return subsets;
	}
	
	
	
	public void calculateInformationGain(Attribute[][] attributes, double entropyParent, int range) {	
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary ){ //FOR CATEGORICAL AND BINARY
			
			//calculate information gain by summing the entropy of each subset and ubstracting it from parent entropy
			double entropySubsets = 0;
			for ( Subset s : subsets) {
				double segmentSize = s.yesCount + s.noCount;
				s.entropy = Entropy.calcEntropy(s.yesCount, s.noCount);
				entropySubsets += ((double)segmentSize/(double)range)*s.entropy;
			}
			
			this.informationGain = entropyParent - entropySubsets;	
		}
		else if (this.attribute instanceof Numerical) {	//FOR NUMERICAL
			
			List<Double> temporaryInformationGain=new ArrayList<Double>();
			
			// check what is the information gain for each split and save it in the temporaryInformationGain arraylist
			double entropySubsets;
			for ( int j=0; j<subsets.size(); j=j+2 ) { //j=j+2 as on odd index there is entropy for lowerSubset and on even idex there is entropy for upperSubset
				entropySubsets=subsets.get(j).entropy* ( (double)subsets.get(j).numOccurrences / (double)(range) )+
						subsets.get(j+1).entropy * ( (double)subsets.get(j+1).numOccurrences / (double)(range)); 			
				temporaryInformationGain.add(entropyParent-entropySubsets);
			}
			
			
			// find what for each split there is a maximum information gain and ascribe index of numerical subset
			double maxInformationGain = 0;
			for (int j=0; j<temporaryInformationGain.size(); j++){
				if (temporaryInformationGain.get(j) > maxInformationGain){
					maxInformationGain = temporaryInformationGain.get(j);
					numericalSubset1=j*2; //as they are in one dimensional arraylist
					numericalSubset1=j*2+1; //as they are in one dimensional arraylist
				}
			}
			
			Subset temp1=subsets.get(numericalSubset1);
			Subset temp2=subsets.get(numericalSubset2);
			
			subsets=new ArrayList<Subset>();
			subsets.add(temp1);
			subsets.add(temp2);
			
			this.informationGain  = maxInformationGain;  //set information gain
		}
		else{
			System.err.println("some error");
		}

	}
}
