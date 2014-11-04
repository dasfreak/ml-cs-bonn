package ml_ex1;

public abstract class Attribute<T> {
	String data;
	
	public abstract T getData();

	abstract void setData(String s);

}
