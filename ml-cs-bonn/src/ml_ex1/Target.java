package ml_ex1;

public class Target extends Attribute<Boolean> {
	boolean data;
	
	public Target(){}

	public void setData(String s) {
		if (s.equals("yes"))
			this.data=true;
		else
			this.data=false;	
	}

	public Boolean getData() {
		return data;
	}
	
	

}
