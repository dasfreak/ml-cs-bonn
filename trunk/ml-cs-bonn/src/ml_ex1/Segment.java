package ml_ex1;

public class Segment {
	public double numer_of_occurrences; //it is double so no worry if we divide by it
	public Attribute attr;
	public double yes_number;
	public double no_number;
	
	public Segment(Attribute attr){
		this.attr=attr;
		numer_of_occurrences=1;
		yes_number=0;
		no_number=0;
	}
}
