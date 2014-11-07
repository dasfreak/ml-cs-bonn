package ml_ex1;

public class Subset {
	public int numOccurrences; 
	public Attribute attr;
	public int yesCount;
	public int noCount;
	public double entropy;
	public double informationGain;
	boolean isBelow=false;
	boolean isAbove=false;
	private int cutPlace;
	public boolean finalResult;
	
	
	public Subset(Attribute attr){
		this.attr       = attr;
		numOccurrences  = 0;
		yesCount        = 0;
		noCount         = 0;
		entropy         = 0.0;
		informationGain = 0.0;
	}
	
	public int getCutPlace() {
		return cutPlace;
	}

	public void setCutPlace(int cutPlace) {
		this.cutPlace = cutPlace;
	}
}
