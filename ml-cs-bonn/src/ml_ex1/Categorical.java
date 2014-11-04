package ml_ex1;

public class Categorical extends Attribute<String> {
	String data;
	
	public Categorical(){}

	public void setData(String s) {
		this.data=s;	
	}

	public String getData() {
		return data;
	}

}
