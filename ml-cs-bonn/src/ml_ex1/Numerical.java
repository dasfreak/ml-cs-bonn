package ml_ex1;

public class Numerical extends Attribute<Double> {
	double data;
	
	public Numerical(){}

	public void setData(String s) {
		this.data=Double.parseDouble(s);
		
	}
	
	public void setData(Double d) {
		this.data=d;
	}

	public Double getData() {
		return data;
	}
	
}
