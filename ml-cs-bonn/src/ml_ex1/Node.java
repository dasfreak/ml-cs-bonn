package ml_ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    public Subset maximal = null;
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
	
	public List<Subset> extractNumericalSubsets(Attribute[][] attributes, List<Subset> subsetsTemp){
		
		int targetColumn=attributes[0].length-1;
		Subset below ;
		Subset above;
		Binary belowMean;
		Binary notBelowMean;
		double mean;
		
		for (int i=0; i<attributes.length-1 ; i++){
			
			// if two following rows differ in target data
			if((boolean)attributes[i][targetColumn].getData() != (boolean)attributes[i+1][targetColumn].getData()){
					//do the split and calculate the entropy
					mean = ((Double)attributes[i][index].getData() + (Double)attributes[i+1][index].getData())/2.0;
					//Subset initial = new Subset(attributes[i][this.index]);
					belowMean=new Binary();
					belowMean.setData("yes");
					notBelowMean=new Binary();
					notBelowMean.setData("no");
					
					below = new Subset(belowMean);
					above = new Subset(notBelowMean);
					
					for (int j=0; j<attributes.length ; j++){
						//System.out.println("couter: "+j);
						if ((Double)attributes[j][index].getData() > mean ){
							above.numOccurrences++;
							if ((boolean) attributes[j][attributes[0].length-1].getData()) // target column is always boolean
								above.yesCount++;
							else
								above.noCount++;
						}
						else if ((Double)attributes[j][index].getData() <= mean ){
							below.numOccurrences++;
							if ((boolean) attributes[j][attributes[0].length-1].getData()) // target column is always boolean
								below.yesCount++;
							else
								below.noCount++;
						}
						else
						{
							System.out.println("here");
						}
					}
					System.out.println("index: "+i);
					subsetsTemp.add(below);
					//subsetsTemp.add(above);
			}
		}
		
		return subsetsTemp;
	}
	
	public void calculateInformationGain(Attribute[][] attributes, double entropyParent, int range) {	
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary ){
			extractSubsets(attributes);		
			
			double entropySegment = 0;
			for ( Subset s : subsets) {
				double segmentSize = s.yesCount + s.noCount;
				s.entropy = Entropy.calcEntropy(s.yesCount, s.noCount);
				entropySegment += ((double)segmentSize/(double)range)*s.entropy;

			}
					
			double maxSubsetEntropy = 0;
			for ( Subset s : subsets) {
				if ( s.entropy > maxSubsetEntropy )
				{
					maxSubsetEntropy = s.entropy;
					this.maximal          = s;
				}
			}
			this.entropy=maximal.entropy;
			this.informationGain = entropyParent - entropySegment;	
		}
		else if (this.attribute instanceof Numerical) {
//			attributes=sortArray(attributes);
//			System.out.println("===================SORTED ARRAY PRINT====================");
//			for (int it=0; it<attributes.length; it++){
//				for (int jt=0; jt<attributes[0].length; jt++){
//					System.out.print( attributes[it][jt].getData()+" , ");
//				}
//				System.out.println();
//			}
			
//			List<Subset> subsetsTemp  = new ArrayList<Subset>();
//			subsetsTemp=extractNumericalSubsets(attributes , subsetsTemp);
//			
//			System.out.println("=============");
//			for (int i=0; i <subsetsTemp.size(); i++){
//				System.out.println(subsetsTemp.get(i).numOccurrences);
//				
//			}
//			
//			System.exit(0);
		}
		else{
			System.out.println("some error");
		}

	}
}