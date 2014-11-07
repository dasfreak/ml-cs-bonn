package ml_ex1;

public class Binary extends Attribute<Boolean> {
	boolean data;
	
	public Binary(){}
	
	public void setData(String s) {
		if (s.equals("yes")){
			this.data=true;
		}
		else
			this.data=false;	
	}

	public Boolean getData() {
		return data;
	}
}
