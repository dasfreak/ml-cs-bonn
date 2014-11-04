package ml_ex1;

public class Numerical extends Attribute<Double> {
	double data;
	
	public Numerical(){}

	public void setData(String s) {
		this.data=Double.parseDouble(s);
		
	}

	public Double getData() {
		return data;
	}

	
}
