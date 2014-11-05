package ml_ex1;

public class Subset {
	public int numOccurrences; 
	public Attribute attr;
	public double yesCount;
	public double noCount;
	public double entropy;
	public double informationGain;
	
	public Subset(Attribute attr){
		this.attr       = attr;
		numOccurrences  = 0;
		yesCount        = 0;
		noCount         = 0;
		entropy         = 0.0;
		informationGain = 0.0;
	}
}
