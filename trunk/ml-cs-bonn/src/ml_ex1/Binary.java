package ml_ex1;

import java.nio.charset.Charset;

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
		//System.out.println(data);
		return data;
	}
}
