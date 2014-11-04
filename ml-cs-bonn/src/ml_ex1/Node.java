package ml_ex1;

import java.util.ArrayList;

public class Node {
	
	public String name; //name of the node read from file
	public int index; //column of the node in file
	public double entropy;
	public double information_gain;
	public Attribute attribute; //attribute of the node
	
	public ArrayList<Node> children;
    public Node parent;
	
	//do not know yet if class_name is necessary here, we will see later on
	public Node(int index, Attribute attribute, String node_name){
		this.name=node_name;
		this.attribute=attribute;
		this.index=index;
	}
	
	public void calculateEntropy(){
		
		
	}
	
}
